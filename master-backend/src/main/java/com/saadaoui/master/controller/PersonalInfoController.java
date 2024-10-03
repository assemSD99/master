package com.saadaoui.master.controller;

import com.saadaoui.master.model.PersonalInfo;
import com.saadaoui.master.repository.PersonalInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personal-info")
public class PersonalInfoController {

    private final PersonalInfoRepository personalInfoRepository;

    public PersonalInfoController(PersonalInfoRepository personalInfoRepository) {
        this.personalInfoRepository = personalInfoRepository;
    }

    // Créer une nouvelle information personnelle
    @PostMapping("/save")
    public ResponseEntity<String> savePersonalInfo(@RequestBody PersonalInfo personalInfo) {
        personalInfoRepository.save(personalInfo);
        return ResponseEntity.ok("Informations personnelles enregistrées avec succès !");
    }

    // Lire toutes les informations personnelles
    @GetMapping("/all")
    public ResponseEntity<List<PersonalInfo>> getAllPersonalInfo() {
        List<PersonalInfo> personalInfos = personalInfoRepository.findAll();
        return ResponseEntity.ok(personalInfos);
    }

    // Lire une information personnelle spécifique par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPersonalInfoById(@PathVariable Long id) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoRepository.findById(id);
        if (personalInfoOptional.isPresent()) {
            return ResponseEntity.ok(personalInfoOptional.get());
        } else {
            return ResponseEntity.status(404).body("Information personnelle non trouvée");
        }
    }

    // Mettre à jour une information personnelle existante
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePersonalInfo(@PathVariable Long id, @RequestBody PersonalInfo personalInfo) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoRepository.findById(id);
        if (personalInfoOptional.isPresent()) {
            PersonalInfo existingInfo = personalInfoOptional.get();
            existingInfo.setFirstName(personalInfo.getFirstName());
            existingInfo.setLastName(personalInfo.getLastName());
            existingInfo.setFirstNameArabic(personalInfo.getFirstNameArabic());
            existingInfo.setLastNameArabic(personalInfo.getLastNameArabic());
            existingInfo.setNationality(personalInfo.getNationality());
            existingInfo.setProfession(personalInfo.getProfession());
            existingInfo.setPhone(personalInfo.getPhone());
            existingInfo.setBirthDate(personalInfo.getBirthDate());
            existingInfo.setBirthPlace(personalInfo.getBirthPlace());
            existingInfo.setGender(personalInfo.getGender());
            existingInfo.setAddress(personalInfo.getAddress());
            existingInfo.setCity(personalInfo.getCity());
            personalInfoRepository.save(existingInfo);
            return ResponseEntity.ok("Informations personnelles mises à jour avec succès !");
        } else {
            return ResponseEntity.status(404).body("Information personnelle non trouvée");
        }
    }

    // Supprimer une information personnelle
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonalInfo(@PathVariable Long id) {
        Optional<PersonalInfo> personalInfoOptional = personalInfoRepository.findById(id);
        if (personalInfoOptional.isPresent()) {
            personalInfoRepository.deleteById(id);
            return ResponseEntity.ok("Information personnelle supprimée avec succès !");
        } else {
            return ResponseEntity.status(404).body("Information personnelle non trouvée");
        }
    }
}

