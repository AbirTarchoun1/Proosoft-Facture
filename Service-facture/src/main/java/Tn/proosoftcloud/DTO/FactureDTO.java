package Tn.proosoftcloud.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class FactureDTO {
        private Long idfacture;
        private String designation;
        private String quantite;
        private float prix_unitaire_HT;
        private float prix_total_HT;
        private float total_HT;
        private float total_TTC;
        private String REFERENCE;
        private String client;
        private String TVA;
        private Date date;

    }

