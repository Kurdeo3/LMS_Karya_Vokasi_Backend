package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.TokenRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Token.PengajarToken;

public interface PengajarTokenRepository extends JpaRepository<PengajarToken, Long> {
    Optional<PengajarToken> findByToken(String token);
    void deleteByToken(String token);
}
