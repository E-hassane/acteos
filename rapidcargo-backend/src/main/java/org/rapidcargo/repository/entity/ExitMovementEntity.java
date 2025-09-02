package org.rapidcargo.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.rapidcargo.domain.enums.MovementType;

@Entity
@DiscriminatorValue("EXIT")
public class ExitMovementEntity extends MovementEntity {

    @Column(name = "to_warehouse_code", length = 20)
    private String toWarehouseCode;

    @Column(name = "to_warehouse_label", length = 100)
    private String toWarehouseLabel;

    @Column(name = "customs_document_type", length = 10)
    private String customsDocumentType;

    @Column(name = "customs_document_ref", length = 50)
    private String customsDocumentRef;

    public ExitMovementEntity() {
        super();
    }

    @Override
    public MovementType getType() {
        return MovementType.EXIT;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }
    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getToWarehouseLabel() {
        return toWarehouseLabel;
    }
    public void setToWarehouseLabel(String toWarehouseLabel) {
        this.toWarehouseLabel = toWarehouseLabel;
    }

    public String getCustomsDocumentType() {
        return customsDocumentType;
    }
    public void setCustomsDocumentType(String customsDocumentType) {
        this.customsDocumentType = customsDocumentType;
    }

    public String getCustomsDocumentRef() {
        return customsDocumentRef;
    }
    public void setCustomsDocumentRef(String customsDocumentRef) {
        this.customsDocumentRef = customsDocumentRef;
    }
}
