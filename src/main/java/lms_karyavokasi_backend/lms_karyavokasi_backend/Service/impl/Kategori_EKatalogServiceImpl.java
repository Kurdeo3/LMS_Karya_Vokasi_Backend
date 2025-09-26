package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_E_Katalog.Kategori_E_KatalogRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_E_Katalog.Kategori_E_KatalogResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Kategori_E_Katalog;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Kategori_E_KatalogRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Kategori_E_KatalogService;

@Service
public class Kategori_EKatalogServiceImpl implements Kategori_E_KatalogService{

    @Autowired
    private Kategori_E_KatalogRepository kategori_ekatalogRepo;

    private Kategori_E_KatalogResponse convertToResponse(Kategori_E_Katalog kategori) {
        Kategori_E_KatalogResponse response = new Kategori_E_KatalogResponse();
        response.setId(kategori.getId());
        response.setNama_kategori(kategori.getNama_kategori());
        return response;
    }

    @Override
    public Kategori_E_KatalogResponse createKategori_EKatalog(Kategori_E_KatalogRequest request) {
        if (request.getNama_kategori() == null || request.getNama_kategori().isBlank()) {
            throw new BadRequestException("Nama kategori wajib diisi");
        }

        Kategori_E_Katalog kategori = new Kategori_E_Katalog();
        kategori.setNama_kategori(request.getNama_kategori());

        return convertToResponse(kategori_ekatalogRepo.save(kategori));
    }

    @Override
    public List<Kategori_E_KatalogResponse> getAllKategori_EKatalog() {
        return kategori_ekatalogRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Kategori_E_KatalogResponse getByIdKategori_EKatalog(Long id) {
        Kategori_E_Katalog kategori = kategori_ekatalogRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("KategoriEKatalog", id));
        return convertToResponse(kategori);
    }

    @Override
    public Kategori_E_KatalogResponse updateKategori_EKatalog(Long id, Kategori_E_KatalogRequest request) {
        Kategori_E_Katalog kategori = kategori_ekatalogRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("KategoriEKatalog", id));

        if (request.getNama_kategori() != null && !request.getNama_kategori().isBlank()) {
            kategori.setNama_kategori(request.getNama_kategori());
        }

        return convertToResponse(kategori_ekatalogRepo.save(kategori));
    }

    @Override
    public void deleteKategori_EKatalog(Long id) {
        if (!kategori_ekatalogRepo.existsById(id)) {
            throw new NotFoundException("KategoriEKatalog", id);
        }
        kategori_ekatalogRepo.deleteById(id);
    }
    
}
