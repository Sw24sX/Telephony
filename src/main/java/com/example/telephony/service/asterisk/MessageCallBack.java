package com.example.telephony.service.asterisk;

import ch.loway.oss.ari4java.generated.models.*;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import org.springframework.stereotype.Component;

@Component
public class MessageCallBack implements AriCallback<Message> {
    private final AsteriskEventPublisher asteriskEventPublisher;

    public MessageCallBack(AsteriskEventPublisher asteriskEventPublisher) {
        this.asteriskEventPublisher = asteriskEventPublisher;
    }

    @Override
    public void onSuccess(Message result) {
        System.out.println(result.getType());
        if(result instanceof Dial){
            Dial dial = (Dial) result;
            System.out.println(dial.getForwarded().getId());
        }
        if(result instanceof Event){
            asteriskEventPublisher.publishAsteriskEvent((Event) result);
        }
//
//        if (result instanceof StasisStart) {
//            StasisStart stasisStart = (StasisStart)result;
//            try {
//                stasisStart(stasisStart);
//            } catch (RestException e) {
//                e.printStackTrace();
//            }
//
//        } else if (result instanceof ChannelDtmfReceived) {
//            ChannelDtmfReceived channelDtmfReceived = (ChannelDtmfReceived)result;
//            channelDtmfReceived(channelDtmfReceived);
//
//        } else if (result instanceof ChannelHangupRequest) {
//            ChannelHangupRequest channelHangupRequest = (ChannelHangupRequest)result;
//            channelHangupRequest(channelHangupRequest);
//
//        } else if (result instanceof StasisEnd) {
//            StasisEnd stasisEnd = (StasisEnd)result;
//            stasisEnd(stasisEnd);
//        } else if (result instanceof PlaybackStarted) {
//            PlaybackStarted playbackStarted = (PlaybackStarted) result;
//            playbackStarted(playbackStarted);
//        } else if (result instanceof PlaybackFinished) {
//            PlaybackFinished playbackFinished = (PlaybackFinished) result;
//            playbackFinished(playbackFinished);
//        }
    }
//
//    public abstract void stasisStart(StasisStart stasisStart) throws RestException;
//
//    public abstract void channelDtmfReceived(ChannelDtmfReceived channelDtmfReceived);
//
//    public abstract void channelHangupRequest(ChannelHangupRequest channelHangupRequest);
//
//    public abstract void stasisEnd(StasisEnd stasisEnd);
//
//    public abstract void playbackStarted(PlaybackStarted playbackStarted);
//
//    public abstract void playbackFinished(PlaybackFinished playbackFinished);

    @Override
    public void onFailure(RestException e) {
        System.out.println("fail callback message");
    }
}
