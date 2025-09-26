package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog.E_KatalogRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog.E_KatalogResponse;

public interface E_KatalogService {
    E_KatalogResponse createE_Katalog(E_KatalogRequest request, MultipartFile thumbnailFile);
    E_KatalogResponse updateE_Katalog(Long id, E_KatalogRequest request, MultipartFile thumbnailFile);
    void deleteE_Katalog(Long id);
    List<E_KatalogResponse> getAllE_Katalog();
    E_KatalogResponse getByIdE_Katalog(Long id);
}
