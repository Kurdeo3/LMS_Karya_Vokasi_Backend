package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksResponse;

public interface Materi_TeksService {
    Materi_TeksResponse create(Materi_TeksRequest request, MultipartFile file);
    Materi_TeksResponse update(Long id, Materi_TeksRequest request, MultipartFile file);
    void delete (Long id);
    Materi_TeksResponse getById(Long id);
    List<Materi_TeksResponse> getByTopik(Long topikId);
    List<Materi_TeksResponse> getAll();
}
