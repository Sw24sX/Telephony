package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.ChannelDtmfReceived;
import ch.loway.oss.ari4java.generated.models.ChannelHangupRequest;
import ch.loway.oss.ari4java.generated.models.StasisEnd;
import ch.loway.oss.ari4java.generated.models.StasisStart;
import ch.loway.oss.ari4java.tools.RestException;
import com.example.telephony.service.CallStatisticService;
import org.springframework.stereotype.Component;

public class MessageCallBackImpl extends MessageCallBack {
    private final CallStatisticService callStatisticService;

    public MessageCallBackImpl(CallStatisticService callStatisticService, ARI ari) {
        super(ari);
        this.callStatisticService = callStatisticService;
    }

    @Override
    public void stasisStart(StasisStart stasisStart) throws RestException {
        String mediaUrl = "sound:http://localhost:8080/hello";
        ari.channels().play(stasisStart.getChannel().getId(), mediaUrl).execute();
        String.format(
                "StasisStart - Channel: %s State: %s", stasisStart.getChannel().getId(), stasisStart.getChannel().getState());
    }

    @Override
    public void channelDtmfReceived(ChannelDtmfReceived channelDtmfReceived) {
        System.out.println(
                String.format("ChannelDtmfReceived received - Channel: %s Digit: %s", channelDtmfReceived.getChannel().getId(), channelDtmfReceived.getDigit()));
        callStatisticService.addDigit(channelDtmfReceived.getChannel().getId(), channelDtmfReceived.getDigit());
    }

    @Override
    public void channelHangupRequest(ChannelHangupRequest channelHangupRequest) {
        System.out.println(
                String.format("ChannelHangupRequest - Channel: %s", channelHangupRequest.getChannel().getId()));
    }

    @Override
    public void stasisEnd(StasisEnd stasisEnd) {
        System.out.println(String.format("Stasis End"));
    }
}
