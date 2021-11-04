package com.example.telephony.service;

import ai.rev.speechtotext.ApiClient;
import ai.rev.speechtotext.models.asynchronous.RevAiAccount;
import ai.rev.speechtotext.models.asynchronous.RevAiJob;
import com.example.telephony.exception.TelephonyException;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechClient;
import com.harium.hci.espeak.Espeak;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TTSService {
    private static final String RU = "ru-RU";
    private static final String EN = "en-US";

    public void tts(String text) {
//        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()){
//
//        } catch (Exception e) {
//            throw new TelephonyException(e.getMessage());
//        }

//        freeTTS(text);
        espeak(text);
    }

    private void revai(String text) {
        String token = "02axXUEqB6PxnHa_0pz_jEpViTgPLFihsYcg_zveOcaLJirjQfONab7V-b9N2SS7AIxZgRtPlxXXj3CfiN99wo8-Y06wI";
        ApiClient apiClient = new ApiClient(token);
        try {
            RevAiAccount revAiAccount = apiClient.getAccount();
            Path path = Paths.get("file\\generate").toAbsolutePath();
            String fileName = "test.wav";
            path = path.resolve(fileName);
            RevAiJob revAiJob = apiClient.submitJobLocalFile(path.toString());
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }

    }

    private void espeak(String text) {
        Espeak espeak = new Espeak();
        espeak.speak(text);

    }

    private void freeTTS(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();
        Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
        voice.deallocate();
    }
}
