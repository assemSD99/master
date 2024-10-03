package com.saadaoui.master.service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.saadaoui.master.model.AcademicInfo;
import com.saadaoui.master.model.Document;  // Your custom Document class
import com.saadaoui.master.model.PersonalInfo;
import com.saadaoui.master.repository.AcademicInfoRepository;
import com.saadaoui.master.repository.DocumentRepository;
import com.saadaoui.master.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.Image;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFGeneratorService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private AcademicInfoRepository academicInfoRepository;

    @Autowired
    private DocumentRepository documentRepository;

    // Ajoutez cette méthode dans votre service pour générer le QR code
    private Image generateQRCode(String data) throws WriterException, IOException, BadElementException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        java.awt.Image qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) qrImage, "png", baos);
        return Image.getInstance(baos.toByteArray());
    }

    public void generatePDF(Long userId, String outputPath) throws DocumentException, IOException,WriterException {
        // Fetch personal information
        PersonalInfo personalInfo = personalInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Informations personnelles non trouvées"));

        // Fetch academic information
        List<AcademicInfo> academicInfos = academicInfoRepository.findAll(); // Filtrer par utilisateur si nécessaire

        // Fetch uploaded documents
        List<Document> documents = documentRepository.findAll(); // Filtrer par utilisateur si nécessaire

        // Create PDF document using iText
        com.itextpdf.text.Document pdfDoc = new com.itextpdf.text.Document();  // Fully qualify iText's Document
        PdfWriter.getInstance(pdfDoc, new FileOutputStream(outputPath));

        pdfDoc.open();

        // Génération du QR code avec l'ID de l'utilisateur
        Image qrCode = generateQRCode("Candidat ID: " + userId);
        pdfDoc.add(qrCode);

        // Adding personal information
        pdfDoc.add(new Paragraph("Informations Personnelles"));
        pdfDoc.add(new Paragraph("Prénom: " + personalInfo.getFirstName()));
        pdfDoc.add(new Paragraph("Nom: " + personalInfo.getLastName()));
        pdfDoc.add(new Paragraph("Prénom (Arabe): " + personalInfo.getFirstNameArabic()));
        pdfDoc.add(new Paragraph("Nom (Arabe): " + personalInfo.getLastNameArabic()));
        pdfDoc.add(new Paragraph("Nationalité: " + personalInfo.getNationality()));
        pdfDoc.add(new Paragraph("Profession: " + personalInfo.getProfession()));
        pdfDoc.add(new Paragraph("Téléphone: " + personalInfo.getPhone()));
        pdfDoc.add(new Paragraph("Date de naissance: " + personalInfo.getBirthDate()));
        pdfDoc.add(new Paragraph("Lieu de naissance: " + personalInfo.getBirthPlace()));
        pdfDoc.add(new Paragraph("Genre: " + personalInfo.getGender()));
        pdfDoc.add(new Paragraph("Adresse: " + personalInfo.getAddress()));
        pdfDoc.add(new Paragraph("Ville: " + personalInfo.getCity()));
        pdfDoc.add(new Paragraph(" "));

        // Adding academic information
        pdfDoc.add(new Paragraph("Informations Académiques"));
        for (AcademicInfo info : academicInfos) {
            pdfDoc.add(new Paragraph("Type de diplôme: " + info.getDegree()));
            pdfDoc.add(new Paragraph("Filière: " + info.getField()));
            pdfDoc.add(new Paragraph("Établissement: " + info.getInstitution()));
            pdfDoc.add(new Paragraph("Année d'obtention: " + info.getYear()));
            pdfDoc.add(new Paragraph("Mention: " + info.getGrade()));
            pdfDoc.add(new Paragraph("Moyenne générale: " + info.getGpa()));
            pdfDoc.add(new Paragraph(" "));
        }

        // Adding document information
        pdfDoc.add(new Paragraph("Documents Importés"));
        for (Document doc : documents) {  // This is your custom Document class
            pdfDoc.add(new Paragraph("Nom du fichier: " + doc.getFileName()));
            pdfDoc.add(new Paragraph("Type du fichier: " + doc.getFileType()));
            pdfDoc.add(new Paragraph("Chemin du fichier: " + doc.getFilePath()));
            pdfDoc.add(new Paragraph(" "));
        }

        pdfDoc.close();
    }
}
