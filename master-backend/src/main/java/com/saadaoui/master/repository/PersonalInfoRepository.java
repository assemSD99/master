package com.saadaoui.master.repository;

import com.saadaoui.master.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    // JpaRepository fournit déjà les méthodes CRUD de base (save, findById, findAll, delete, etc.)
}
