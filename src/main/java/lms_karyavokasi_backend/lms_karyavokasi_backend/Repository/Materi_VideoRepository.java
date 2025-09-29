package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Materi_Video;

@Repository
public interface Materi_VideoRepository extends JpaRepository<Materi_Video, Long>{
    List<Materi_Video> findByTopikId(Long topikId);
}
