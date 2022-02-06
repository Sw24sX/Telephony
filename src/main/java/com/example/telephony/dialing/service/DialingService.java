package com.example.telephony.dialing.service;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.dialing.persistance.model.Dialing;
import com.example.telephony.dialing.persistance.model.DialingCallerResult;
import com.example.telephony.dialing.persistance.enums.DialCallerStatus;
import com.example.telephony.dialing.persistance.enums.DialingStatus;
import com.example.telephony.enums.FieldsPageSort;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.dialing.exception.DialingException;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.dialing.persistance.repository.DialingCallerResultRepository;
import com.example.telephony.dialing.persistance.repository.DialingRepository;
import com.example.telephony.service.CallerBaseService;
import com.example.telephony.dialing.service.scenario.dialing.DialingManager;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DialingService {
    private final ScenarioManagerService scenarioManagerService;
    private final CallerBaseService callerBaseService;
    private final DialingRepository dialingRepository;
    private final DialingManager dialingManager;
    private final DialingCallerResultRepository dialingCallerResultRepository;
    private final CallerRepository callerRepository;

    @Autowired
    public DialingService(ScenarioManagerService scenarioManagerService, CallerBaseService callerBaseService,
                          DialingRepository dialingRepository, DialingManager dialingManager,
                          DialingCallerResultRepository dialingCallerResultRepository, CallerRepository callerRepository) {
        this.scenarioManagerService = scenarioManagerService;
        this.callerBaseService = callerBaseService;
        this.dialingRepository = dialingRepository;
        this.dialingManager = dialingManager;
        this.dialingCallerResultRepository = dialingCallerResultRepository;
        this.callerRepository = callerRepository;
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

        Dialing newDialing = dialingRepository.save(setStartDate(dialing));

        if (newDialing.getStatus() == DialingStatus.RUN) {
            startDialing(newDialing);
        }

        return newDialing;
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

    public Integer getAllCountDialingsCallers() {
        Integer result = 0;
        for (Dialing dialing : dialingRepository.findAll()) {
            result += callerBaseService.getCountCallers(dialing.getCallersBaseId());
        }
        return result;
    }

    public List<Dialing> getDialingsByCallersBaseId(Long callersBaseId) {
        return dialingRepository.findAllByCallersBaseId(callersBaseId);
    }

    public List<Dialing> getDialingsByScenarioId(Long scenarioId) {
        return dialingRepository.findAllByScenario_Id(scenarioId);
    }

    public void startScheduledDialingNow(Long id) {
        Dialing dialing = getById(id);
        if (dialing.getStatus() != DialingStatus.SCHEDULED) {
            throw new DialingException(ExceptionMessage.DIALING_NOT_SCHEDULED_STATUS.getMessage());
        }

        dialing.setStartDate(new Date());
        dialing.setStatus(DialingStatus.RUN);
        dialing = dialingRepository.save(dialing);

        startDialing(dialing);
    }

    public Integer getCountDialsByStatus(Dialing dialing, DialCallerStatus status) {
        return dialingCallerResultRepository.getCountDialingCallersByStatus(dialing.getId(), status);
    }

    public Integer getAllCountDialsByStatus(DialCallerStatus status) {
        return dialingCallerResultRepository.getCountCallersByStatus(status);
    }

    public List<LocalTime> getAllStartCallTimeByDialing(Dialing dialing) {
        return dialingCallerResultRepository.findAllStartCallTimeByDialingId(dialing.getId(), DialCallerStatus.CORRECT);
    }

    public List<LocalTime> getAllStartCallTime() {
        return dialingCallerResultRepository.findAllStartCallTime(DialCallerStatus.CORRECT);
    }

    public Optional<DialingCallerResult> getDialResultByDialingAndCaller(Dialing dialing, Caller caller) {
        return dialingCallerResultRepository.getDialingCallerResultByDialingIdAndCallerId(dialing.getId(), caller.getId());
    }

    public Page<Caller> getPageCallersResult(Long dialingId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return callerRepository.getCallersByDialingId(dialingId, pageable);
    }

    public List<DialingCallerResult> getResultsCallerByDialing(Dialing dialing) {
        return dialingCallerResultRepository.findAllByDialing(dialing);
    }
}