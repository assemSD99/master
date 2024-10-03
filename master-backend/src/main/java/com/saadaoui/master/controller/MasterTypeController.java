package com.saadaoui.master.controller;

import com.saadaoui.master.model.MasterType;
import com.saadaoui.master.repository.MasterTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/master-type")
public class MasterTypeController {

    private final MasterTypeRepository masterTypeRepository;

    public MasterTypeController(MasterTypeRepository masterTypeRepository) {
        this.masterTypeRepository = masterTypeRepository;
    }

    // Créer un nouveau type de master
    @PostMapping("/save")
    public ResponseEntity<String> saveMasterType(@RequestBody MasterType masterType) {
        masterTypeRepository.save(masterType);
        return ResponseEntity.ok("Type de Master enregistré avec succès !");
    }

    // Lire tous les types de master
    @GetMapping("/all")
    public ResponseEntity<List<MasterType>> getAllMasterTypes() {
        List<MasterType> masterTypes = masterTypeRepository.findAll();
        return ResponseEntity.ok(masterTypes);
    }

    // Lire un type de master spécifique par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMasterTypeById(@PathVariable Long id) {
        Optional<MasterType> masterTypeOptional = masterTypeRepository.findById(id);
        if (masterTypeOptional.isPresent()) {
            return ResponseEntity.ok(masterTypeOptional.get());
        } else {
            return ResponseEntity.status(404).body("Type de Master non trouvé");
        }
    }

    // Mettre à jour un type de master existant
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMasterType(@PathVariable Long id, @RequestBody MasterType updatedMasterType) {
        Optional<MasterType> masterTypeOptional = masterTypeRepository.findById(id);
        if (masterTypeOptional.isPresent()) {
            MasterType existingMasterType = masterTypeOptional.get();
            existingMasterType.setMasterType(updatedMasterType.getMasterType());
            masterTypeRepository.save(existingMasterType);
            return ResponseEntity.ok("Type de Master mis à jour avec succès !");
        } else {
            return ResponseEntity.status(404).body("Type de Master non trouvé");
        }
    }

    // Supprimer un type de master
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMasterType(@PathVariable Long id) {
        Optional<MasterType> masterTypeOptional = masterTypeRepository.findById(id);
        if (masterTypeOptional.isPresent()) {
            masterTypeRepository.deleteById(id);
            return ResponseEntity.ok("Type de Master supprimé avec succès !");
        } else {
            return ResponseEntity.status(404).body("Type de Master non trouvé");
        }
    }
}
