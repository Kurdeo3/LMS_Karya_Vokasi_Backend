package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_Mata_Pelajaran.Kategori_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Kategori_Mata_Pelajaran.Kategori_Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Kategori_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Kategori_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Kategori_Mata_PelajaranService;

@Service
public class Kategori_MataPelajaranServiceImpl implements Kategori_Mata_PelajaranService{

    @Autowired
    private Kategori_Mata_PelajaranRepository kategori_mataPelajaranRepo;

    private Kategori_Mata_PelajaranResponse convertToResponse(Kategori_Mata_Pelajaran kategori) {
        Kategori_Mata_PelajaranResponse response = new Kategori_Mata_PelajaranResponse();
        response.setId(kategori.getId());
        response.setNama_kategori(kategori.getNama_kategori());
        return response;
    }

    @Override
    public Kategori_Mata_PelajaranResponse createKategori_MataPelajaran(Kategori_Mata_PelajaranRequest request) {
        if (request.getNama_kategori() == null || request.getNama_kategori().isBlank()) {
            throw new BadRequestException("Nama kategori wajib diisi");
        }

        Kategori_Mata_Pelajaran kategori = new Kategori_Mata_Pelajaran();
        kategori.setNama_kategori(request.getNama_kategori());

        return convertToResponse(kategori_mataPelajaranRepo.save(kategori));
    }

    @Override
    public List<Kategori_Mata_PelajaranResponse> getAllKategori_MataPelajaran() {
        return kategori_mataPelajaranRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Kategori_Mata_PelajaranResponse getByIdKategori_MataPelajaran(Long id) {
        Kategori_Mata_Pelajaran kategori = kategori_mataPelajaranRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Kategori Mata Pelajaran", id));
        return convertToResponse(kategori);
    }

    @Override
    public Kategori_Mata_PelajaranResponse updateKategori_MataPelajaran(Long id, Kategori_Mata_PelajaranRequest request) {
        Kategori_Mata_Pelajaran kategori = kategori_mataPelajaranRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Kategori Mata Pelajaran", id));

        if (request.getNama_kategori() != null && !request.getNama_kategori().isBlank()) {
            kategori.setNama_kategori(request.getNama_kategori());
        }

        return convertToResponse(kategori_mataPelajaranRepo.save(kategori));
    }

    @Override
    public void deleteKategori_MataPelajaran(Long id) {
        if (!kategori_mataPelajaranRepo.existsById(id)) {
            throw new NotFoundException("Kategori Mata Pelajaran", id);
        }
        kategori_mataPelajaranRepo.deleteById(id);
    }

}
