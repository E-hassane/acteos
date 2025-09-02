package org.rapidcargo.enums;

public enum MovementType {
    ENTRY("Entrée"),
    EXIT("Sortie");

    private final String label;

    MovementType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
