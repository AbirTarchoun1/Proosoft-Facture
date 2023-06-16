package Tn.proosoftcloud.controller;
import Tn.proosoftcloud.entities.Facture;
import Tn.proosoftcloud.entities.User;
import Tn.proosoftcloud.repository.IFactureRepository;
import Tn.proosoftcloud.repository.IUserRepository;
import Tn.proosoftcloud.services.FactureService;
import Tn.proosoftcloud.services.IEmail;
import Tn.proosoftcloud.services.PdfGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService factureService;
    private final IFactureRepository factureRepository;
    private final IEmail iEmail;
    private final IUserRepository userRepository;

    public FactureController(FactureService factureService, IFactureRepository factureRepository, IEmail iEmail, IUserRepository userRepository) {
        this.factureService = factureService;
        this.factureRepository = factureRepository;
        this.iEmail = iEmail;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        Facture createdFacture = factureService.addOrUpdateFacture(facture);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFacture);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFacture(@PathVariable Long id) {
        Facture facture = factureService.retrieveFacture(id);
        return ResponseEntity.ok(facture);
    }

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        List<Facture> factures = factureService.retrieveAllFacture();
        return ResponseEntity.ok(factures);
    }

    @PutMapping("/{factureId}/assign/{userId}")
    public ResponseEntity<Facture> assignFactureToUser(@PathVariable Long factureId, @PathVariable Long userId) {
        Facture assignedFacture = factureService.assignToUser(factureId, userId);
        return ResponseEntity.ok(assignedFacture);
    }


    @GetMapping("/exporttopdf")
    public void generatePdfFilesForFactures(HttpServletResponse response) throws IOException, MessagingException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String currentDateTime = dateFormat.format(new Date());

        List<Facture> factures = factureRepository.findAll(); // Or retrieve the facture data as needed

        String savePath = "C:\\Users\\TPDEV1\\Downloads\\"; // Specify the directory to save the PDF files

        for (Facture facture : factures) {
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=facture_" + facture.getIdfacture() + "_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            PdfGenerator generator = new PdfGenerator();
            generator.generateToClient(Collections.singletonList(facture), response);

            response.getOutputStream().flush();
            response.getOutputStream().close();

            String fileName = headerValue.substring(21);
            String pathToAttachment = savePath + fileName;

            // Save the facture PDF file
            saveFactureToFile((InputStream) response, pathToAttachment);

            // Retrieve the user based on the codeClient from the facture
            User user = userRepository.findByCodeClient(facture.getClient());
            if (user != null) {
                String email = user.getEmail();
                iEmail.sendMessageWithAttachmentClient(pathToAttachment, email);
            } else {
                // Handle the case when user is not found based on codeClient
                // Return an error response or perform appropriate action
            }
        }
    }

    private void saveFactureToFile(InputStream inputStream, String filePath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(new File(filePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            inputStream.close();
        }
    }
}