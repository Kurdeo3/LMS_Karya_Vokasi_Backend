package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar.PelajarRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar.PelajarResponse;

public interface PelajarService {
    PelajarResponse createPelajar(PelajarRequest request);
    List<PelajarResponse> getAllPelajar();
    PelajarResponse getPelajarById(Long id);
    PelajarResponse updatePelajar(PelajarRequest request, Long id);
    void deletePelajar (Long id);
    PelajarResponse uploadFotoProfile(Long id, MultipartFile foto);
}
