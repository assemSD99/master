package com.saadaoui.master.controller;

import com.saadaoui.master.model.Document;
import com.saadaoui.master.repository.DocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private static final String UPLOAD_DIRECTORY = "C:\\Users\\assem\\Desktop\\master\\master-backend\\uploads/";

    private final DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // Télécharger et sauvegarder les documents dans le système de fichiers et la base de données
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("idCard") MultipartFile idCard,
            @RequestParam("cv") MultipartFile cv) {
        try {
            // Sauvegarde des fichiers sur le disque
            Files.write(Paths.get(UPLOAD_DIRECTORY + photo.getOriginalFilename()), photo.getBytes());
            Files.write(Paths.get(UPLOAD_DIRECTORY + idCard.getOriginalFilename()), idCard.getBytes());
            Files.write(Paths.get(UPLOAD_DIRECTORY + cv.getOriginalFilename()), cv.getBytes());

            // Sauvegarde des informations dans la base de données
            Document photoDoc = new Document(photo.getOriginalFilename(), photo.getContentType(), UPLOAD_DIRECTORY + photo.getOriginalFilename());
            Document idCardDoc = new Document(idCard.getOriginalFilename(), idCard.getContentType(), UPLOAD_DIRECTORY + idCard.getOriginalFilename());
            Document cvDoc = new Document(cv.getOriginalFilename(), cv.getContentType(), UPLOAD_DIRECTORY + cv.getOriginalFilename());

            documentRepository.save(photoDoc);
            documentRepository.save(idCardDoc);
            documentRepository.save(cvDoc);

            return ResponseEntity.ok("Documents uploadés et enregistrés avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du téléchargement des fichiers.");
        }
    }


    @PostMapping("/upload-motivation-letter")
    public ResponseEntity<String> uploadMotivationLetter(@RequestParam("motivationLetter") MultipartFile motivationLetter) {
        try {
            // Sauvegarder le fichier sur le serveur
            Files.write(Paths.get("uploads/" + motivationLetter.getOriginalFilename()), motivationLetter.getBytes());
            return ResponseEntity.ok("Lettre de motivation importée avec succès !");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l’importation");
        }
    }


    @PostMapping("/upload-transcripts")
    public ResponseEntity<String> uploadTranscripts(@RequestParam("transcripts") MultipartFile[] transcripts) {
        try {
            for (MultipartFile transcript : transcripts) {
                Files.write(Paths.get("uploads/" + transcript.getOriginalFilename()), transcript.getBytes());
            }
            return ResponseEntity.ok("Relevés de notes importés avec succès !");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l’importation");
        }
    }

    // Lister tous les fichiers stockés dans la base de données
    @GetMapping("/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return ResponseEntity.ok(documents);
    }

    // Lire un fichier spécifique par ID
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDocumentById(@PathVariable Long id) {
        return (ResponseEntity<byte[]>) documentRepository.findById(id)
                .map(document -> {
                    Path filePath = Paths.get(document.getFilePath());
                    try {
                        byte[] fileBytes = Files.readAllBytes(filePath);
                        return ResponseEntity.ok()
                                .header("Content-Disposition", "attachment; filename=\"" + document.getFileName() + "\"")
                                .body(fileBytes);  // Correctement retourner byte[]
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Assurez-vous que le type de retour est byte[]
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));  // Retourne 404 avec null byte[]
    }

    // Mettre à jour un fichier existant
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile newFile) {
        return documentRepository.findById(id)
                .map(document -> {
                    Path filePath = Paths.get(document.getFilePath());
                    try {
                        Files.write(filePath, newFile.getBytes());  // Remplacer le fichier existant
                        document.setFileName(newFile.getOriginalFilename());
                        document.setFileType(newFile.getContentType());
                        documentRepository.save(document);
                        return ResponseEntity.ok("Fichier mis à jour avec succès !");
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du fichier.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fichier non trouvé."));
    }

    // Supprimer un fichier
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        return documentRepository.findById(id)
                .map(document -> {
                    Path filePath = Paths.get(document.getFilePath());
                    try {
                        Files.delete(filePath);  // Supprimer le fichier sur le disque
                        documentRepository.deleteById(id);  // Supprimer de la base de données
                        return ResponseEntity.ok("Fichier supprimé avec succès !");
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression du fichier.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fichier non trouvé."));
    }
}
