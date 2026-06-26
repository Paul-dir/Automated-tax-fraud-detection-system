package com.mor.tfis.repository;

import com.mor.tfis.entity.Informant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface InformantRepository extends JpaRepository<Informant, UUID> {
}