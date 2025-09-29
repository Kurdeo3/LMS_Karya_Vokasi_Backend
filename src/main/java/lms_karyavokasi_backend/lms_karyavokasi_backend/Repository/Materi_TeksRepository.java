package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Materi_Teks;

@Repository
public interface Materi_TeksRepository extends JpaRepository<Materi_Teks, Long>{
    List<Materi_Teks> findByTopikId(Long topikId);
}
