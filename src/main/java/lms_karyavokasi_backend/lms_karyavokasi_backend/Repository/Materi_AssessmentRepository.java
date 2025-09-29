package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Materi_Assessment;

@Repository
public interface Materi_AssessmentRepository extends JpaRepository<Materi_Assessment, Long>{
    List<Materi_Assessment> findByTopikId(Long topikId);
}
