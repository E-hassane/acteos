package org.rapidcargo.repository.entity;

import jakarta.persistence.*;
import org.rapidcargo.domain.enums.CustomsStatus;
import org.rapidcargo.domain.enums.MovementType;

import java.time.LocalDate;

@Entity
@Table(name = "movements")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "movement_type", discriminatorType = DiscriminatorType.STRING)
public abstract class MovementEntity extends BaseEntity {

    @Column(name = "movement_time", nullable = false)
    private LocalDate movementTime;

    @Column(name = "declared_in_code", nullable = false, length = 20)
    private String declaredInCode = "CDGRC1";

    @Column(name = "declared_in_label", nullable = false, length = 100)
    private String declaredInLabel = "RapidCargo CDG";

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id", nullable = false)
    private GoodsEntity goods;

    @Enumerated(EnumType.STRING)
    @Column(name = "customs_status", nullable = false, length = 1)
    private CustomsStatus customsStatus;

    public MovementEntity() {}

    public abstract MovementType getType();

    public LocalDate getMovementTime() {
        return movementTime;
    }
    public void setMovementTime(LocalDate movementTime) {
        this.movementTime = movementTime;
    }

    public String getDeclaredInCode() {
        return declaredInCode;
    }
    public void setDeclaredInCode(String declaredInCode) {
        this.declaredInCode = declaredInCode;
    }

    public String getDeclaredInLabel() {
        return declaredInLabel;
    }
    public void setDeclaredInLabel(String declaredInLabel) {
        this.declaredInLabel = declaredInLabel;
    }

    public GoodsEntity getGoods() {
        return goods;
    }
    public void setGoods(GoodsEntity goods) {
        this.goods = goods;
    }

    public CustomsStatus getCustomsStatus() {
        return customsStatus;
    }
    public void setCustomsStatus(CustomsStatus customsStatus) {
        this.customsStatus = customsStatus;
    }
}
