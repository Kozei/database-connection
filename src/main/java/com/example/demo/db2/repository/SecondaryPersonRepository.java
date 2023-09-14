package com.example.demo.db2.repository;

import com.example.demo.db2.domain.SecondaryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SecondaryPersonRepository extends JpaRepository<SecondaryPerson, Long> {
}
