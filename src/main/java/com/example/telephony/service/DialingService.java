package com.example.telephony.service;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.DialingException;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.DialingCallerResultRepository;
import com.example.telephony.repository.DialingRepository;
import com.example.telephony.service.scenario.dialing.DialingManager;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DialingService {
    private final ScenarioManagerService scenarioManagerService;
    private final CallerBaseService callerBaseService;
    private final DialingRepository dialingRepository;
    private final DialingManager dialingManager;
    private final DialingCallerResultRepository dialingCallerResultRepository;

    @Autowired
    public DialingService(ScenarioManagerService scenarioManagerService, CallerBaseService callerBaseService,
                          DialingRepository dialingRepository, DialingManager dialingManager,
                          DialingCallerResultRepository dialingCallerResultRepository) {
        this.scenarioManagerService = scenarioManagerService;
        this.callerBaseService = callerBaseService;
        this.dialingRepository = dialingRepository;
        this.dialingManager = dialingManager;
        this.dialingCallerResultRepository = dialingCallerResultRepository;
    }

    public Dialing getById(Long id) {
        return dialingRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.DIALING_NOT_FOUND.getMessage(), id)));
    }

    public Page<Dialing> getPage(int number, int size, FieldsPageSort fieldsPageSort,
                                 Sort.Direction direction, String name, DialingStatus status) {
        Sort sort = Sort.by(direction, fieldsPageSort.getFieldName());
        Pageable pageable = PageRequest.of(number, size, sort);
        return status == null ?
                dialingRepository.findAllByName("%" + name + "%", pageable) :
                dialingRepository.findAllByNameAndStatus("%" + name + "%", status, pageable);
    }

    public Dialing createDialing(Dialing dialing) {
        // TODO: 19.12.2021 run scenario if not scheduled
        // TODO: 19.12.2021 check callers base by confirmed
        if (dialing.getStatus() == DialingStatus.DONE) {
            throw new DialingException(ExceptionMessage.CAN_NOT_CREATE_DONE_DIALING.getMessage());
        }

        dialing = dialingRepository.save(setStartDate(dialing));

        if (dialing.getStatus() == DialingStatus.RUN) {
            startDialing(dialing);
        }

        return dialing;
    }

    private void startDialing(Dialing dialing) {
        dialingManager.addDialing(dialing, callerBaseService.getCountCallers(dialing.getCallersBaseId()));
        scenarioManagerService.startDialingCallersBase(dialing);
    }

    private Dialing setStartDate(Dialing dialing) {
        if (dialing.getStartDate() == null) {
            if (dialing.getStatus() != DialingStatus.RUN) {
                throw new DialingException(ExceptionMessage.DIALING_DATE_NOT_VALID.getMessage());
            }

            dialing.setStartDate(new Date());
        }
        // TODO: 20.12.2021 check scheduled dialing date

        return dialing;
    }

    public Dialing updateDialing(Dialing dialing, Long id) {
        // TODO: 20.12.2021 stop dialing if change from run to scheduled

        Dialing dialingDb = getById(id);
        if (dialingDb.getStatus() != DialingStatus.SCHEDULED) {
            throw new DialingException(ExceptionMessage.CAN_NOT_CHANGE_DIALING.getMessage());
        }

        BeanUtils.copyProperties(dialing, dialingDb, "id", "created");
        dialingDb = dialingRepository.save(dialingDb);
        if (dialingDb.getStatus() == DialingStatus.RUN) {
            scenarioManagerService.startDialingCallersBase(dialingDb);
        }

        return dialingDb;
    }

    public void deleteDialing(Long id) {
        Dialing dialingDb = getById(id);
        // TODO: 19.12.2021 stop if running
        dialingRepository.delete(dialingDb);
    }

    public void addScheduledDialing() {
        //TODO
        throw new NotImplementedException();
    }

    public Integer getCountEndDialingCallers(Dialing dialing) {
        return dialingCallerResultRepository.getCountDialingCallers(dialing.getId());
    }

    public Integer getCountDialingCallers(Dialing dialing) {
        return callerBaseService.getCountCallers(dialing.getCallersBaseId());
    }

    public List<Dialing> getDialingsByCallersBaseId(Long callersBaseId) {
        return dialingRepository.findAllByCallersBaseId(callersBaseId);
    }

    public void startScheduledDialingNow(Long id) {
        Dialing dialing = getById(id);
        dialing.setStartDate(new Date());
        dialing.setStatus(DialingStatus.RUN);
        dialing = dialingRepository.save(dialing);

        startDialing(dialing);
    }
}