package com.example.text.to.speech.service.enums;

public enum FilesFormat {
    RAW(".raw"),
    WAV(".wav");

    private final String format;

    FilesFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
