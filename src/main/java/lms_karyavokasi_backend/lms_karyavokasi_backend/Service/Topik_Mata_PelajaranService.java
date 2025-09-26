package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranResponse;

public interface Topik_Mata_PelajaranService {
    Topik_Mata_PelajaranResponse createTopik(Topik_Mata_PelajaranRequest request);
    Topik_Mata_PelajaranResponse updateTopik(Long id, Topik_Mata_PelajaranRequest request);
    void deleteTopik(Long id);
    Topik_Mata_PelajaranResponse getByIdTopik(Long id);
    List<Topik_Mata_PelajaranResponse> getAllTopik();
    List<Topik_Mata_PelajaranResponse> getByMataPelajaran(Long mataPelajaranId);
}
