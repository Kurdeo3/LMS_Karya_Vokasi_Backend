package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Kategori_E_Katalog;

@Repository
public interface Kategori_E_KatalogRepository extends JpaRepository<Kategori_E_Katalog, Long>{

}
