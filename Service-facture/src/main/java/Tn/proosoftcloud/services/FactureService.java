package Tn.proosoftcloud.services;

import Tn.proosoftcloud.entities.Facture;
import Tn.proosoftcloud.entities.User;
import Tn.proosoftcloud.exception.FactureNotFoundException;
import Tn.proosoftcloud.exception.UserNotFoundException;
import Tn.proosoftcloud.repository.IFactureRepository;
import Tn.proosoftcloud.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FactureService implements IFacture {
    @Autowired
    private RestTemplate restTemplate;
    private final IFactureRepository factureRepository;
    private final IUserRepository userRepository;

    public Facture addOrUpdateTestResult(Facture facture) {
        return factureRepository.save(facture);
    }

    public Facture saveFacture(Facture facture) {
        // Save the facture to the database
        Facture savedFacture = factureRepository.save(facture);

        // Make an API call to retrieve user details from the User service
        User user = restTemplate.getForObject("http://User-Service/api/users/{userId}", User.class, facture.getUser().getIduser());

        return savedFacture;
    }


    @Override
    public Facture addOrUpdateFacture(Facture facture) {
        return null;
    }


    @Override
    public List<Facture> readFature(MultipartFile file) throws IOException {
        return null;
    }


    @Override
    public Facture retriveTFactureBYAcounnt(int id) {
        return null;
    }

    @Override
    public Facture assignToUser(Long idfacture, Long iduser) {
        Facture facture = factureRepository.findById((long) idfacture)
                .orElseThrow(() -> new FactureNotFoundException("Facture not found with id: " + idfacture));

        User user = restTemplate.getForObject("http://user-service/api/users/{userId}", User.class, iduser);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + iduser);
        }

        return facture;
    }

    @Override
    public Facture retrieveFacture(Long idfacture) {
        return factureRepository.findById((Long) idfacture)
                .orElseThrow(() -> new FactureNotFoundException("Facture not found with id: " + idfacture));
    }

    @Override
    public List<Facture> retrieveAllFacture() {
        return factureRepository.findAll();
    }

    @Override
    public Map<Integer, String[]> divide_table(String table, int idfacture) {
        String[] lines = table.split("\n");
        Map<String, String> extractedData = verifyData(table);
        Map<Integer, String[]> map = new HashMap<>();
        int tableStartIndex = -1;

        // Find the start index of the table
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].strip();
            if (line.startsWith("Quantité") && line.contains("Désignation") && line.contains("Prix unitaire HT") && line.contains("Prix total HT")) {
                tableStartIndex = i;
                break;
            }
        }

        // If the table start index is found, extract the table data
        if (tableStartIndex != -1) {
            for (int i = tableStartIndex + 1; i < lines.length; i++) {
                String[] words = lines[i].split("\\s+", 4);
                if (words.length == 4) {
                    map.put(i, words);
                }
            }
        }

        Facture facture = new Facture();
        facture.setIdfacture((long) idfacture);
        facture.setDateFacture(extractedData.get("DateFacture"));
        facture.setClient(extractedData.get("CodeClient"));
        facture.setReference(extractedData.get("Reference"));
        facture.setTotal_HT(extractedData.get("Total_HT"));
        facture.setTva(extractedData.get("tva"));
        facture.setTotal_TTC(extractedData.get("Total_TTC"));
        facture.setTitre(extractedData.get("Titre"));

        for (String[] line : map.values()) {
            if (line.length > 0) {
                String quantite = line[0].trim();
                facture.setQuantite(quantite);
            }

            if (line.length > 1) {
                String description = line[1].trim();
                facture.setDecription(description);
            }

            if (line.length > 2) {
                String prix_unitaire_HT = line[2].trim();
                facture.setPrix_unitaire_HT(prix_unitaire_HT);
            }

            if (line.length > 3) {
                String prix_total_HT = line[3].trim();
                facture.setPrix_total_HT(prix_total_HT);
            }
        }
        factureRepository.save(facture);
        return map;
    }

    public Map<String, String> verifyData(String extractedText) {
        String[] lines = extractedText.split("\n");
        Map<String, String> output = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].strip();
            if (line.startsWith("Client:")) {
                output.put("CodeClient", line.replace("Client:", "").trim());
            } else if (line.startsWith("Date:")) {
                output.put("DateFacture", line.replace("Date:", "").trim());
            } else if (line.startsWith("Intitulé:")) {
                output.put("Titre", line.replace("Intitulé:", "").trim());
            } else if (line.startsWith("Référence:")) {
                output.put("Reference", line.replace("Référence:", "").trim());
            } else if (line.startsWith("Total TTC (en euros)")) {
                output.put("Total_TTC", line.replace("Total TTC (en euros)", "").trim());
            } else if (line.startsWith("Total HT")) {
                output.put("Total_HT", line.replace("Total HT", "").trim());
            } else if (line.startsWith("TVA (20%)")) {
                output.put("tva", line.replace("TVA (20%)", "").trim());
            }
            else if (line.startsWith("Désignation")) {
                output.put("description", line.replace("Désignation", "").trim());
            }
            else if (line.startsWith("Prix unitaire HT")) {
                output.put("Prix_unitaire_HT", line.replace("Prix unitaire HT", "").trim());
            }
            else if (line.startsWith("Prix total HT")) {
                output.put("prix_total_HT", line.replace("Prix total HT", "").trim());
            }
        }
            return output;
        }

}
    /*private Map<String, String> getPosElement(String po) {
        Map<String, String> element = new HashMap<>();

        int firstSpaceIndex = po.indexOf(' ');
        if (firstSpaceIndex != -1) {
            element.put("quantite", po.substring(0, firstSpaceIndex).trim());
            po = po.substring(firstSpaceIndex + 1);

            int lastSpaceIndex = po.lastIndexOf(' ');
            if (lastSpaceIndex != -1) {
                element.put("prixtotht", po.substring(lastSpaceIndex + 1).trim());
                po = po.substring(0, lastSpaceIndex);

                lastSpaceIndex = po.lastIndexOf(' ');
                if (lastSpaceIndex != -1) {
                    element.put("prixunitht", po.substring(lastSpaceIndex + 1).trim());
                    po = po.substring(0, lastSpaceIndex);

                    element.put("decription", po.trim());
                }
            }
        }

        return element;
    }*/


