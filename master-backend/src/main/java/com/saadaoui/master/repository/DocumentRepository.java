package com.saadaoui.master.repository;

import com.saadaoui.master.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
