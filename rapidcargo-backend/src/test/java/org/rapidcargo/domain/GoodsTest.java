package org.rapidcargo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.rapidcargo.domain.enums.ReferenceType;
import org.rapidcargo.domain.exception.BusinessException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GoodsTest {

    private Goods goods;

    @BeforeEach
    void setUp() {
        goods = new Goods();
        goods.setReferenceType(ReferenceType.AWB);
        goods.setReferenceCode("12345678901");
        goods.setQuantity(10);
        goods.setWeight(new BigDecimal("15.500"));
        goods.setTotalRefQuantity(100);
        goods.setTotalRefWeight(new BigDecimal("155.000"));
        goods.setDescription("Test goods");
    }

    @Test
    @DisplayName("Un AWB avec format invalide doit échouer")
    void awbGoods_WithInvalidFormat_ShouldFail() {
        goods.setReferenceCode("123456789");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> goods.validate()
        );

        assertEquals("La référence AWB doit contenir exactement 11 chiffres",
                exception.getMessage());
    }

    @Test
    @DisplayName("Un AWB avec lettres doit échouer")
    void awbGoods_WithLetters_ShouldFail() {
        goods.setReferenceCode("1234567890A");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> goods.validate()
        );

        assertEquals("La référence AWB doit contenir exactement 11 chiffres",
                exception.getMessage());
    }

    @Test
    @DisplayName("Une quantité négative doit échouer")
    void goods_WithNegativeQuantity_ShouldFail() {
        goods.setQuantity(-5);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> goods.validate()
        );

        assertEquals("La quantité doit être positive", exception.getMessage());
    }

    @Test
    @DisplayName("Un poids négatif doit échouer")
    void goods_WithNegativeWeight_ShouldFail() {
        goods.setWeight(new BigDecimal("-10.0"));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> goods.validate()
        );

        assertEquals("Le poids doit être positif", exception.getMessage());
    }

    @Test
    @DisplayName("Une quantité totale inférieure à la quantité mouvement doit échouer")
    void goods_WithTotalQuantityLowerThanQuantity_ShouldFail() {
        goods.setQuantity(50);
        goods.setTotalRefQuantity(30);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> goods.validate()
        );

        assertEquals("La quantité totale de référence doit être >= à la quantité du mouvement",
                exception.getMessage());
    }

    @Test
    @DisplayName("Un poids total inférieur au poids mouvement doit échouer")
    void goods_WithTotalWeightLowerThanWeight_ShouldFail() {
        goods.setWeight(new BigDecimal("50.0"));
        goods.setTotalRefWeight(new BigDecimal("30.0"));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> goods.validate()
        );

        assertEquals("Le poids total de référence doit être >= au poids du mouvement",
                exception.getMessage());
    }
}
