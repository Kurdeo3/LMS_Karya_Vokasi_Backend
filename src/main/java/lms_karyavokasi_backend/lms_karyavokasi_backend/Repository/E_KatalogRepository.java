package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.E_Katalog;

@Repository
public interface E_KatalogRepository extends  JpaRepository<E_Katalog, Long>{
    
}
