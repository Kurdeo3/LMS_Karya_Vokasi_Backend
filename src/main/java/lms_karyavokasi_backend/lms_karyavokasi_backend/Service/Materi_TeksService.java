package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksResponse;

public interface Materi_TeksService {
    Materi_TeksResponse create(Materi_TeksRequest request);
    List<Materi_TeksResponse> getByTopik(Long topikId);
}
