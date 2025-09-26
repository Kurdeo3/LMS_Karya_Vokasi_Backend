package lms_karyavokasi_backend.lms_karyavokasi_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{
    Optional<Administrator> findByEmail(String email);
}
