package com.example.telephony.dto.scenario;

import lombok.Data;

@Data
public class Node {
    private int id;
    private String type;
    private boolean isPositive;
    private boolean isNegative;
    private String pathToFile;
    private String question;
    private Position position;
}
