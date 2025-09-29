package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment.Materi_AssessmentRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment.Materi_AssessmentResponse;

public interface Materi_AssessmentService {
    Materi_AssessmentResponse create(Materi_AssessmentRequest request);
    Materi_AssessmentResponse update(Materi_AssessmentRequest request, Long id);
    List<Materi_AssessmentResponse> getByTopik(Long topikId);
    Materi_AssessmentResponse getById(Long id);
    void delete(Long id);
}
