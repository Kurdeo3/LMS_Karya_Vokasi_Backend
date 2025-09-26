package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator.AdministratorRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Administrator.AdministratorResponse;

public interface AdministratorService {
    AdministratorResponse createAdministrator(AdministratorRequest request);
    List<AdministratorResponse> getAllAdministrator();
    AdministratorResponse getAdministratorById(Long id);
    AdministratorResponse updateAdministrator(AdministratorRequest request, Long id);
    void deleteAdministrator(Long id);
}
