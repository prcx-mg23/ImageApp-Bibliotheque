package com.imglibrary.imageapp;

import java.util.Map;

public class TransformationInfo {
    private String type;
    private Map<String, String> parameters;

    public TransformationInfo(String type, Map<String, String> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
