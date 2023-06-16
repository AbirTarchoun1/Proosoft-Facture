package Tn.proosoftcloud.repository;

import Tn.proosoftcloud.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFactureRepository extends JpaRepository<Facture,Long> {
    @Query("SELECT f FROM Facture f WHERE f.userId = :userId")
    List<Facture> findByUserId(@Param("userId") int userId);
}
