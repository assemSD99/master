package com.saadaoui.master.controller;

import com.saadaoui.master.model.AcademicInfo;
import com.saadaoui.master.repository.AcademicInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/academic-info")
public class AcademicInfoController {

    private final AcademicInfoRepository academicInfoRepository;

    public AcademicInfoController(AcademicInfoRepository academicInfoRepository) {
        this.academicInfoRepository = academicInfoRepository;
    }

    // Créer une nouvelle information académique
    @PostMapping("/save")
    public ResponseEntity<String> saveAcademicInfo(@RequestBody AcademicInfo academicInfo) {
        academicInfoRepository.save(academicInfo);
        return ResponseEntity.ok("Informations académiques enregistrées avec succès !");
    }

    // Lire toutes les informations académiques
    @GetMapping("/all")
    public ResponseEntity<List<AcademicInfo>> getAllAcademicInfo() {
        List<AcademicInfo> academicInfos = academicInfoRepository.findAll();
        return ResponseEntity.ok(academicInfos);
    }

    // Lire une information académique spécifique par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAcademicInfoById(@PathVariable Long id) {
        Optional<AcademicInfo> academicInfoOptional = academicInfoRepository.findById(id);
        if (academicInfoOptional.isPresent()) {
            return ResponseEntity.ok(academicInfoOptional.get());
        } else {
            return ResponseEntity.status(404).body("Information académique non trouvée");
        }
    }

    // Mettre à jour une information académique existante
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAcademicInfo(@PathVariable Long id, @RequestBody AcademicInfo academicInfo) {
        Optional<AcademicInfo> academicInfoOptional = academicInfoRepository.findById(id);
        if (academicInfoOptional.isPresent()) {
            AcademicInfo existingInfo = academicInfoOptional.get();
            existingInfo.setDegree(academicInfo.getDegree());
            existingInfo.setField(academicInfo.getField());
            existingInfo.setInstitution(academicInfo.getInstitution());
            existingInfo.setYear(academicInfo.getYear());
            existingInfo.setGrade(academicInfo.getGrade());
            existingInfo.setGpa(academicInfo.getGpa());
            academicInfoRepository.save(existingInfo);
            return ResponseEntity.ok("Informations académiques mises à jour avec succès !");
        } else {
            return ResponseEntity.status(404).body("Information académique non trouvée");
        }
    }

    // Supprimer une information académique
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAcademicInfo(@PathVariable Long id) {
        Optional<AcademicInfo> academicInfoOptional = academicInfoRepository.findById(id);
        if (academicInfoOptional.isPresent()) {
            academicInfoRepository.deleteById(id);
            return ResponseEntity.ok("Informations académiques supprimées avec succès !");
        } else {
            return ResponseEntity.status(404).body("Information académique non trouvée");
        }
    }
}
