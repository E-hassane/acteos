package org.rapidcargo.service;

import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.rapidcargo.repository.MovementRepository;
import org.rapidcargo.repository.entity.MovementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementValidationService {
    private final MovementRepository movementRepository;

    @Autowired
    public MovementValidationService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    /**
     *
     * @param awbNumber
     * @param clientCode
     * @param goodsList
     */
    public void validateEntryMovement(String awbNumber, String clientCode,
                                      List<Goods> goodsList) {
        validateCommonParameters(awbNumber, clientCode, goodsList);

        validateGoodsList(goodsList);
    }

    /**
     *
     * @param awbNumber
     * @param clientCode
     * @param goodsList
     */
    public void validateExitMovement(String awbNumber, String clientCode,
                                     List<Goods> goodsList) {
        validateCommonParameters(awbNumber, clientCode, goodsList);

        if (!hasEntryMovement(awbNumber)) {
            throw new BusinessException(
                    String.format("Impossible de créer une sortie : aucune entrée trouvée pour l'AWB %s", awbNumber)
            );
        }

        validateGoodsList(goodsList);
    }

    /**
     *
     * @param referenceCode
     * @return
     */
        public boolean hasEntryMovement(String referenceCode) {
        if (referenceCode == null || referenceCode.trim().isEmpty()) {
            return false;
        }

        List<MovementEntity> movements = movementRepository.findByReferenceCode(referenceCode);
        return movements.stream()
                .anyMatch(movement -> "ENTRY".equals(movement.getClass().getAnnotation(
                        jakarta.persistence.DiscriminatorValue.class).value()));
    }

    /**
     *
     * @param awbNumber
     * @param clientCode
     * @param goodsList
     */
    private void validateCommonParameters(String awbNumber, String clientCode, List<Goods> goodsList) {
        if (awbNumber == null || awbNumber.trim().isEmpty()) {
            throw new BusinessException("Le numéro AWB est obligatoire");
        }

        if (clientCode == null || clientCode.trim().isEmpty()) {
            throw new BusinessException("Le code client est obligatoire");
        }

        if (goodsList == null || goodsList.isEmpty()) {
            throw new BusinessException("Au moins une marchandise doit être déclarée");
        }
    }

    /**
     *
     * @param goodsList
     */
    private void validateGoodsList(List<Goods> goodsList) {
        for (Goods goods : goodsList) {
            if (goods == null) {
                throw new BusinessException("Les informations de marchandise ne peuvent pas être nulles");
            }

            goods.validate();
        }
    }

    /**
     *
     * @param movement
     */
    public void validateMovementConsistency(Movement movement) {
        if (movement == null) {
            throw new BusinessException("Le mouvement ne peut pas être null");
        }

        movement.validate();
    }
}
