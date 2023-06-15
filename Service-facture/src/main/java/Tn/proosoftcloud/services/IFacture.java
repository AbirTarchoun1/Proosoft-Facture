package Tn.proosoftcloud.services;

import Tn.proosoftcloud.entities.Facture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IFacture {

    Facture addOrUpdateFacture(Facture facture);
    public Facture assignToUser(Long idfacture, Long iduser);

    /*void removeFacture (int idFacture );*/

    Map<Integer,String[]> divide_table(String table);
    Facture retrieveFacture(Long idFacture);

    List<Facture> retrieveAllFacture();

    List<Facture> readFature(MultipartFile file) throws IOException;


    Facture  retriveTFactureBYAcounnt(int id);
}
