package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectManagement.entities.VerificationToken;

import java.util.Date;
import java.util.List;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("select v from VerificationToken v where v.expiryDate <= :now")
    List<VerificationToken> findAllExpired(
            @Param("now") Date Date);


    VerificationToken findByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from VerificationToken v where v.token = ?1")
    void deleteByToken(String token);
}
