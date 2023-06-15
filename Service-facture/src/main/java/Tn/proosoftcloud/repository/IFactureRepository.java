package Tn.proosoftcloud.repository;

import Tn.proosoftcloud.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFactureRepository extends JpaRepository<Facture,Long> {
}
