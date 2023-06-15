package Tn.proosoftcloud.entities;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Setter
@Getter
@Entity
@Table( name = "Facture")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idFacture")
    private Long idfacture;
    private String Decription;
    private String Quantite;
    private String Prix_unitaire_HT;
    private String Prix_total_HT;
    private String Total_HT;
    private String Total_TTC;
    private String Reference;
    private String Client;
    private String Tva;
    private String DateFacture;
    private String Titre ;
    /*@ManyToOne
    private User user;*/
    private int userId ;
    public Facture(Long idfacture, String decription, String quantite, String prix_unitaire_HT, String prix_total_HT, String total_HT, String total_TTC, String reference, String client, String tva, String dateFacture, String titre ) {
        this.idfacture = idfacture;
        Decription = decription;
        Quantite = quantite;
        Prix_unitaire_HT = prix_unitaire_HT;
        Prix_total_HT = prix_total_HT;
        Total_HT = total_HT;
        Total_TTC = total_TTC;
        Reference = reference;
        Client = client;
        Tva = tva;
        DateFacture = dateFacture;
        Titre =titre;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getIdfacture() {
        return idfacture;
    }

    public void setIdfacture(Long idfacture) {
        this.idfacture = idfacture;
    }

    public String getDecription() {
        return Decription;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public void setDecription(String decription) {
        Decription = decription;
    }

    public String getQuantite() {
        return Quantite;
    }

    public void setQuantite(String quantite) {
        Quantite = quantite;
    }

    public String getPrix_unitaire_HT() {
        return Prix_unitaire_HT;
    }

    public void setPrix_unitaire_HT(String prix_unitaire_HT) {
        Prix_unitaire_HT = prix_unitaire_HT;
    }

    public String getPrix_total_HT() {
        return Prix_total_HT;
    }

    public void setPrix_total_HT(String prix_total_HT) {
        Prix_total_HT = prix_total_HT;
    }

    public String getTotal_HT() {
        return Total_HT;
    }

    public void setTotal_HT(String total_HT) {
        Total_HT = total_HT;
    }

    public String getTotal_TTC() {
        return Total_TTC;
    }

    public void setTotal_TTC(String total_TTC) {
        Total_TTC = total_TTC;
    }


    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public String getTva() {
        return Tva;
    }

    public void setTva(String tva) {
        Tva = tva;
    }

    public String getDateFacture() {
        return DateFacture;
    }

    public void setDateFacture(String dateFacture) {
        DateFacture = dateFacture;
    }

   /* public User getUser() {
        return user;
    }*/

   /* public void setUser(User user) {
        this.user = user;
    }*/

    public Facture(){
        super();
 }

}
