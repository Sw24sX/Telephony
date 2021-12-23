package com.example.telephony.service;

import com.example.telephony.domain.Dialing;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.DialingException;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.DialingRepository;
import com.example.telephony.service.asterisk.AriService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DialingService {
    private final AriService ariService;
    private final CallerBaseService callerBaseService;
    private final DialingRepository dialingRepository;

    @Autowired
    public DialingService(AriService ariService, CallerBaseService callerBaseService,
                          DialingRepository dialingRepository) {
        this.ariService = ariService;
        this.callerBaseService = callerBaseService;
        this.dialingRepository = dialingRepository;
    }

    public Dialing getById(Long id) {
        return dialingRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.DIALING_NOT_FOUND.getMessage(), id)));
    }

    public List<Dialing> getAll() {
        return dialingRepository.findAll();
    }

    public Dialing createDialing(Dialing dialing) {
        // TODO: 19.12.2021 run scenario if not scheduled
        // TODO: 19.12.2021 check callers base by confirmed
        if (dialing.getStatus() == DialingStatus.DONE) {
            throw new DialingException(ExceptionMessage.CAN_NOT_CREATE_DONE_DIALING.getMessage());
        }

        if (!callerBaseService.isConfirmed(dialing.getCallersBaseId())) {
            String message = String.format(
                    ExceptionMessage.CALLER_BASE_NOT_CONFIRMED.getMessage(), dialing.getCallersBaseId());
            throw new DialingException(message);
        }

        dialing = setStartDate(dialing);
        if (dialing.getStatus() == DialingStatus.RUN) {
            ariService.startDialingCallersBase(dialing);
        }

        return dialingRepository.save(dialing);
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
            ariService.startDialingCallersBase(dialingDb);
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
}
