package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.generated.models.*;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;

public abstract class MessageCallBack implements AriCallback<Message> {
    protected final ARI ari;

    protected MessageCallBack(ARI ari) {
        this.ari = ari;
    }


    @Override
    public void onSuccess(Message result) {
        if (result instanceof StasisStart) {
            StasisStart stasisStart = (StasisStart)result;
            try {
                stasisStart(stasisStart);
            } catch (RestException e) {
                e.printStackTrace();
            }

        } else if (result instanceof ChannelDtmfReceived) {
            ChannelDtmfReceived channelDtmfReceived = (ChannelDtmfReceived)result;
            channelDtmfReceived(channelDtmfReceived);

        } else if (result instanceof ChannelHangupRequest) {
            ChannelHangupRequest channelHangupRequest = (ChannelHangupRequest)result;
            channelHangupRequest(channelHangupRequest);

        } else if (result instanceof StasisEnd) {
            StasisEnd stasisEnd = (StasisEnd)result;
            stasisEnd(stasisEnd);
        }
    }

    public abstract void stasisStart(StasisStart stasisStart) throws RestException;

    public abstract void channelDtmfReceived(ChannelDtmfReceived channelDtmfReceived);

    public abstract void channelHangupRequest(ChannelHangupRequest channelHangupRequest);

    public abstract void stasisEnd(StasisEnd stasisEnd);

    @Override
    public void onFailure(RestException e) {
        System.out.println("fail callback message");
    }
}
