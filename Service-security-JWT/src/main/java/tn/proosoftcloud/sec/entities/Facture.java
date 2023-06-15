package tn.proosoftcloud.sec.entities;

import lombok.*;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.Date;

@ToString
@Setter
@NoArgsConstructor
@Getter
@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFacture")
    private Long idFacture;
    private String Designation;
    private String Quantite;
    private float Prix_unitaire_HT;
    private float Prix_total_HT;
    private float Total_HT;
    private float Total_TTC;
    private String REFERENCE;
    private String Client;
    private String TVA;
    private Date Date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Facture(Long idFacture, String designation, String quantite, float prix_unitaire_HT, float prix_total_HT, float total_HT, float total_TTC, String REFERENCE, String client, String TVA, java.util.Date date, User user) {
        this.idFacture = idFacture;
        Designation = designation;
        Quantite = quantite;
        Prix_unitaire_HT = prix_unitaire_HT;
        Prix_total_HT = prix_total_HT;
        Total_HT = total_HT;
        Total_TTC = total_TTC;
        this.REFERENCE = REFERENCE;
        Client = client;
        this.TVA = TVA;
        Date = date;
        this.user = user;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public Facture(Long idFacture) {
        this.idFacture = idFacture;
    }

    public String getQuantite() {
        return Quantite;
    }

    public void setQuantite(String quantite) {
        Quantite = quantite;
    }

    public float getPrix_unitaire_HT() {
        return Prix_unitaire_HT;
    }

    public void setPrix_unitaire_HT(float prix_unitaire_HT) {
        Prix_unitaire_HT = prix_unitaire_HT;
    }

    public float getPrix_total_HT() {
        return Prix_total_HT;
    }

    public void setPrix_total_HT(float prix_total_HT) {
        Prix_total_HT = prix_total_HT;
    }

    public float getTotal_HT() {
        return Total_HT;
    }

    public void setTotal_HT(float total_HT) {
        Total_HT = total_HT;
    }

    public float getTotal_TTC() {
        return Total_TTC;
    }

    public void setTotal_TTC(float total_TTC) {
        Total_TTC = total_TTC;
    }

    public String getREFERENCE() {
        return REFERENCE;
    }

    public void setREFERENCE(String REFERENCE) {
        this.REFERENCE = REFERENCE;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public String getTVA() {
        return TVA;
    }

    public void setTVA(String TVA) {
        this.TVA = TVA;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
