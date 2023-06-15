package Tn.proosoftcloud.controller;
import Tn.proosoftcloud.entities.Facture;
import Tn.proosoftcloud.services.FactureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
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


}