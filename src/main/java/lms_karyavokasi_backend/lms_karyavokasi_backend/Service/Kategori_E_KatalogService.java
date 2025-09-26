package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import java.util.List;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_E_Katalog.Kategori_E_KatalogRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_E_Katalog.Kategori_E_KatalogResponse;

public interface Kategori_E_KatalogService {
    Kategori_E_KatalogResponse createKategori_EKatalog(Kategori_E_KatalogRequest kategori);
    List<Kategori_E_KatalogResponse> getAllKategori_EKatalog();
    Kategori_E_KatalogResponse getByIdKategori_EKatalog(Long id);
    Kategori_E_KatalogResponse updateKategori_EKatalog(Long id, Kategori_E_KatalogRequest kategori);
    void deleteKategori_EKatalog(Long id);
}
