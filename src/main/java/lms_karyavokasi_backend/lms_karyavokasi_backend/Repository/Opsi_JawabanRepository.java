package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Opsi_Jawaban;

@Repository
public interface Opsi_JawabanRepository extends JpaRepository<Opsi_Jawaban, Long>{
}
