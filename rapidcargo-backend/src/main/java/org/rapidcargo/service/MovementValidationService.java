package org.rapidcargo.service;

import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.rapidcargo.repository.MovementRepository;
import org.rapidcargo.repository.entity.MovementEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementValidationService {

    private final MovementRepository repository;

    public MovementValidationService(MovementRepository repository) {
        this.repository = repository;
    }

    public void validateEntryMovement(String awbNumber, String clientCode, List<Goods> goodsList) {
        checkBasicParams(awbNumber, clientCode, goodsList);
        validateGoods(goodsList);
    }

    public void validateExitMovement(String awbNumber, String clientCode, List<Goods> goodsList) {
        checkBasicParams(awbNumber, clientCode, goodsList);

        if (!hasEntryMovement(awbNumber)) {
            throw new BusinessException("Pas d'entrée trouvée pour l'AWB " + awbNumber);
        }

        validateGoods(goodsList);
    }

    public boolean hasEntryMovement(String referenceCode) {
        if (referenceCode == null || referenceCode.trim().isEmpty()) {
            return false;
        }

        List<MovementEntity> movements = repository.findByReferenceCode(referenceCode);
        return movements.stream()
                .anyMatch(movement -> "ENTRY".equals(movement.getClass().getAnnotation(
                        jakarta.persistence.DiscriminatorValue.class).value()));
    }

    public void validateMovementConsistency(Movement movement) {
        if (movement == null) {
            throw new BusinessException("Mouvement null");
        }

        movement.validate();
    }

    private void checkBasicParams(String awbNumber, String clientCode, List<Goods> goodsList) {
        if (awbNumber == null || awbNumber.trim().isEmpty()) {
            throw new BusinessException("AWB obligatoire");
        }

        if (clientCode == null || clientCode.trim().isEmpty()) {
            throw new BusinessException("Code client obligatoire");
        }

        if (goodsList == null || goodsList.isEmpty()) {
            throw new BusinessException("Au moins une marchandise obligatoire");
        }
    }

    private void validateGoods(List<Goods> goodsList) {
        for (Goods goods : goodsList) {
            if (goods == null) {
                throw new BusinessException("Marchandise null");
            }
            goods.validate();
        }
    }
}
