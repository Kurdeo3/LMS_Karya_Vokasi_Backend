package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_Mata_Pelajaran.Kategori_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_Mata_Pelajaran.Kategori_Mata_PelajaranResponse;

public interface Kategori_Mata_PelajaranService {
    Kategori_Mata_PelajaranResponse createKategori_MataPelajaran(Kategori_Mata_PelajaranRequest request);
    List<Kategori_Mata_PelajaranResponse> getAllKategori_MataPelajaran();
    Kategori_Mata_PelajaranResponse getByIdKategori_MataPelajaran(Long id);
    Kategori_Mata_PelajaranResponse updateKategori_MataPelajaran(Long id, Kategori_Mata_PelajaranRequest request);
    void deleteKategori_MataPelajaran(Long id);
}
