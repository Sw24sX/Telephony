package com.example.telephony.service;

import ch.loway.oss.ari4java.generated.models.Dial;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.Dialing;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.enums.DialingStatus;
import com.example.telephony.enums.messages.ExceptionMessage;
import com.example.telephony.exception.DialingException;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.ScenarioBuildException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.CallerRepository;
import com.example.telephony.repository.DialingRepository;
import com.example.telephony.service.asterisk.AriService;
import com.example.telephony.service.scenario.ScenarioBuilder;
import com.example.telephony.service.scenario.dialing.ScenarioManager;
import com.example.telephony.service.scenario.steps.ScenarioStep;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DialingService {
    private final AriService ariService;
    private final ScenarioService scenarioService;
    private final CallerService callerService;
    private final CallerBaseService callerBaseService;
    private final ScenarioManager scenarioManager;
    private final CallerRepository callerRepository;
    private final ScenarioPreparationService scenarioPreparationService;
    private final DialingRepository dialingRepository;

    @Autowired
    public DialingService(AriService ariService, ScenarioService scenarioService, CallerService callerService,
                          CallerBaseService callerBaseService, ScenarioManager scenarioManager,
                          CallerRepository callerRepository, ScenarioPreparationService scenarioPreparationService,
                          DialingRepository dialingRepository) {
        this.ariService = ariService;
        this.scenarioService = scenarioService;
        this.callerService = callerService;
        this.callerBaseService = callerBaseService;
        this.scenarioManager = scenarioManager;
        this.callerRepository = callerRepository;
        this.scenarioPreparationService = scenarioPreparationService;
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
            startDialingCallersBase(dialing.getCallersBaseId(), dialing.getScenario());
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
            startDialingCallersBase(dialingDb.getCallersBaseId(), dialingDb.getScenario());
        }

        return dialingDb;
    }

    public void deleteDialing(Long id) {
        Dialing dialingDb = getById(id);
        // TODO: 19.12.2021 stop if running
        dialingRepository.delete(dialingDb);
    }

    private void startDialingCallersBase(Long callersBaseId, Scenario scenario) {
        CallersBase callersBase = callerBaseService.getById(callersBaseId);
        ScenarioStep scenarioStep = ScenarioBuilder.build(scenario, ariService.getAri());
        // TODO: 19.12.2021 get callers base by page
        for(Caller caller : callersBase.getCallers()) {
            try {
                addCallerToScenarioExecute(caller, scenarioStep);
            } catch (ScenarioBuildException e) {
                // TODO: 17.12.2021 add to statistic
                continue;
            } catch (RestException e) {
                // TODO: 17.12.2021 add to statistic
                continue;
            }
        }
    }

    private void addCallerToScenarioExecute(Caller caller, ScenarioStep scenarioStep) throws ScenarioBuildException, RestException {
        String channelId = UUID.randomUUID().toString();
        Map<ScenarioStep, GeneratedSound> sounds = scenarioPreparationService.voiceOverScenarioByCaller(scenarioStep, caller);
        scenarioManager.addCallScenario(channelId, scenarioStep, sounds);
        ariService.createChannel(callerRepository.getCallerNumber(caller.getId()), channelId);
    }

    public void addScheduledDialing() {
        //TODO
        throw new NotImplementedException();
    }
}
