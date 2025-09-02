package org.rapidcargo.enums;

public enum ReferenceType {
    AWB("AWB"),
    OTHER("Autre");

    private final String label;

    ReferenceType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
