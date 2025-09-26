package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar.PengajarRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar.PengajarResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;

public interface PengajarService {
    PengajarResponse createPengajar(PengajarRequest request);
    List<PengajarResponse> getAllPengajar();
    PengajarResponse getPengajarById(Long id);
    PengajarResponse updatePengajar(PengajarRequest request, Long id);
    void deletePengajar (Long id);
    PengajarResponse updateStatus(Long id, String status);
    PengajarResponse uploadFotoProfile(Long id, MultipartFile foto);
    ///////
    Pengajar getCurrentPengajar();
}
