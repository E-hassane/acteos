package org.rapidcargo.domain;

import org.rapidcargo.domain.enums.CustomsStatus;
import org.rapidcargo.domain.enums.MovementType;

import java.time.LocalDateTime;

public abstract class Movement {
    private Long id;
    private LocalDateTime movementTime;
    private String createdBy;
    private LocalDateTime createdAt;

    private final String declaredInCode = "CDGRC1";
    private final String getDeclaredInLabel = "RapidCargo CDG";

    private Goods goods;
    private CustomsStatus customsStatus;

    public Movement() {
        this.createdAt = LocalDateTime.now();
    }

    public Movement(Long id, LocalDateTime movementTime, String createdBy, LocalDateTime createdAt) {
        this();
        this.id = id;
        this.movementTime = movementTime;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public abstract MovementType getType();

}
