package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;

@Repository
public interface PengajarRepository extends JpaRepository<Pengajar, Long>{
    Optional<Pengajar> findByEmail(String email);
}
