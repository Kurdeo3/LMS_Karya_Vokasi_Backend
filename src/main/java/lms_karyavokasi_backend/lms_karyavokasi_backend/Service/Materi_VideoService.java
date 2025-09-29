package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoResponse;


public interface Materi_VideoService {
    Materi_VideoResponse create(Materi_VideoRequest request);
    List<Materi_VideoResponse> getByTopik(Long topikId);
}
