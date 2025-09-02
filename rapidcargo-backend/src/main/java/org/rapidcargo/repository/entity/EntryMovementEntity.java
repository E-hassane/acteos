package org.rapidcargo.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.rapidcargo.enums.MovementType;

@Entity
@DiscriminatorValue("ENTRY")
public class EntryMovementEntity extends MovementEntity{

    @Column(name = "from_warehouse_code", length = 20)
    private String fromWarehouseCode;

    @Column(name = "from_warehouse_label", length = 100)
    private String fromWarehouseLabel;

    public EntryMovementEntity() {
        super();
    }

    @Override
    public MovementType getType() {
        return MovementType.ENTRY;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }
    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode;
    }

    public String getFromWarehouseLabel() {
        return fromWarehouseLabel;
    }
    public void setFromWarehouseLabel(String fromWarehouseLabel) {
        this.fromWarehouseLabel = fromWarehouseLabel;
    }
}
