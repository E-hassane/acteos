package org.rapidcargo.enums;

public enum CustomsStatus {
    X("Status X"),
    Y("Status Y"),
    Z("Status Z");

    private final String label;

    CustomsStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
