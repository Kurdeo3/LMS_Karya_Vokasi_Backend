package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran.Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran.Mata_PelajaranResponse;

public interface Mata_PelajaranService {
    Mata_PelajaranResponse createMataPelajaran(Mata_PelajaranRequest request, MultipartFile thumbnailFile);
    Mata_PelajaranResponse updateMataPelajaran(Long id, Mata_PelajaranRequest request, MultipartFile thumbnailFile);
    void deleteMataPelajaran(Long id);
    Mata_PelajaranResponse getById(Long id);
    List<Mata_PelajaranResponse> getAll();
    List<Mata_PelajaranResponse> getByEKatalog(Long eKatalogId);
}
