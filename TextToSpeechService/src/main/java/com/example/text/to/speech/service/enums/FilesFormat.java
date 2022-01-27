package com.example.text.to.speech.service.enums;

/**
 * Enum with used extension file
 */
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
