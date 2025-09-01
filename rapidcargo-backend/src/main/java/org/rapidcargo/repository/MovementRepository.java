package org.rapidcargo.repository;

import org.rapidcargo.repository.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Long> {
    @Query("SELECT m FROM MovementEntity m WHERE m.goods.referenceCode = :refCode ORDER BY m.createdAt DESC")
    List<MovementEntity> findByReferenceCode(@Param("refCode") String referenceCode);
}
