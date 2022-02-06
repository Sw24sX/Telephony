package com.example.telephony.enums;

public enum FilesFormat {
    RAW(".raw"),
    WAV(".wav");

    private final String extension;

    FilesFormat(String extension) {
        this.extension = extension;
    }

    /**
     * File extension
     * @return
     */
    public String getExtension() {
        return extension;
    }
}
