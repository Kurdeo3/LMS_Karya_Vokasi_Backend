package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.TokenRepository;

import java.util.Optional;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.AdministratorToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorTokenRepository extends JpaRepository<AdministratorToken, Long>{
    Optional<AdministratorToken> findByToken(String token);
    void deleteByToken(String token);
}
