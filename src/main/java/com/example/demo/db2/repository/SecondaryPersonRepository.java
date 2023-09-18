package com.example.demo.db2.repository;

import com.example.demo.db2.domain.SecondaryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public interface SecondaryPersonRepository extends JpaRepository<SecondaryPerson, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM SecondaryPerson p WHERE p.timestamp < :thresholdDate")
    void deleteSecondaryPersonByTimestamp(@Param("thresholdDate") Instant thresholdDate);
}
