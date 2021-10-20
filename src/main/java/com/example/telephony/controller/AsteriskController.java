package com.example.telephony.controller;

import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.service.AsteriskService;
import com.example.telephony.service.CallerService;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.cfg.NotYetImplementedException;
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
    private final DialController dialController;

    @Autowired
    public AsteriskController(AsteriskService asteriskService, CallerService callerService,
                              DialController dialController) {
        this.asteriskService = asteriskService;
        this.callerService = callerService;
        this.dialController = dialController;
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
