package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Kategori_Mata_Pelajaran;

@Repository
public interface Kategori_Mata_PelajaranRepository extends JpaRepository<Kategori_Mata_Pelajaran, Long>{

}
