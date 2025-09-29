package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Mata_Pelajaran;

@Repository
public interface Mata_PelajaranRepository extends JpaRepository<Mata_Pelajaran, Long>{
    List<Mata_Pelajaran> findByeKatalog_Id(Long eKatalogId);
    List<Mata_Pelajaran> findByeKatalog_Pengajar_Id(Long pengajarId);
}
