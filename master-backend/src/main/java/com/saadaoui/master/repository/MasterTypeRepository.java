package com.saadaoui.master.repository;

import com.saadaoui.master.model.MasterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTypeRepository extends JpaRepository<MasterType, Long> {
}
