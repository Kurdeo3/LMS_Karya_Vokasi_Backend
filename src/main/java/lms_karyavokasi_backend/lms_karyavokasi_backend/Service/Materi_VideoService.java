package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoResponse;


public interface Materi_VideoService {
    Materi_VideoResponse create(Materi_VideoRequest request, MultipartFile file);
    Materi_VideoResponse update(Long id, Materi_VideoRequest request, MultipartFile file);
    void delete (Long id);
    Materi_VideoResponse getById(Long id);
    List<Materi_VideoResponse> getByTopik(Long topikId);
    List<Materi_VideoResponse> getAll();
}
