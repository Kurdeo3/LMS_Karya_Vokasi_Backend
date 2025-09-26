package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;

@Repository
public interface Topik_Mata_PelajaranRepository extends JpaRepository<Topik_Mata_Pelajaran, Long>{
    List<Topik_Mata_Pelajaran> findByMataPelajaranId(Long mataPelajaranId);
}
