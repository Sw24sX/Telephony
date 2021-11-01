package com.example.telephony.controller;

import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.service.AsteriskService;
import com.example.telephony.service.CallerService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("asterisk")
public class AsteriskController {
    private final AsteriskService asteriskService;
    private final CallerService callerService;
    private final CallerBaseController callerBaseController;

    @Autowired
    public AsteriskController(AsteriskService asteriskService, CallerService callerService,
                              CallerBaseController callerBaseController) {
        this.asteriskService = asteriskService;
        this.callerService = callerService;
        this.callerBaseController = callerBaseController;
    }

    @PostMapping("call/caller/{id}")
    public void callToCallerById(@PathVariable("id") Long id) throws RestException {
        asteriskService.callByCaller(callerService.getById(id));
    }

    @PostMapping("call/dial/{id}")
    public void callToDialById(@PathVariable("id") Long id) {
        throw new NotImplementedException();
    }
}
