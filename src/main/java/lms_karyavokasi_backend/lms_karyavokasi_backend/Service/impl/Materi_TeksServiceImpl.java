package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.FileStorageService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Teks.Materi_TeksResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Materi_Teks;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Materi_TeksRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Topik_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Materi_TeksService;

@Service
public class Materi_TeksServiceImpl implements Materi_TeksService{

    @Autowired
    Materi_TeksRepository materiTeksRepository;

    @Autowired
    private Topik_Mata_PelajaranRepository topikMataPelajaranRepository;

    @Autowired
    private FileStorageService fileStorageService;

    private Materi_TeksResponse convertToResponse(Materi_Teks materi_Teks) {
        Materi_TeksResponse materiRes = new Materi_TeksResponse();
        materiRes.setId(materi_Teks.getId());
        materiRes.setJudul(materi_Teks.getJudul());
        materiRes.setDeskripsi(materi_Teks.getDeskripsi());
        materiRes.setUrlTeks(materi_Teks.getUrlTeks());

        if (materi_Teks.getTopik() != null) {
            materiRes.setTopikId(materi_Teks.getTopik().getId());
            materiRes.setNamaTopik(materi_Teks.getTopik().getNama_topik());
        }

        return materiRes;
    }

    @Override
    public Materi_TeksResponse create(Materi_TeksRequest request, MultipartFile file) {

        if (request.getJudul() == null || request.getJudul().isBlank()) {
            throw new BadRequestException("Judul Materi Teks wajib diisi");
        }
        if (request.getDeskripsi() == null || request.getDeskripsi().isBlank()) {
            throw new BadRequestException("Deskripsi wajib diisi");
        }

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File materi wajib diupload");
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null && !(fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
            throw new BadRequestException ("Format Teks tidak didukung. Gunakan file dengan format pdf, doc, docx");
        }

        String filePath;
        try {
            filePath = fileStorageService.saveFile(file, "materi_teks");
        } catch (IOException ex) {
            throw new BadRequestException("Gagal upload file materi");
        }

        Topik_Mata_Pelajaran topik = topikMataPelajaranRepository.findById(request.getTopikId())
            .orElseThrow(() -> new NotFoundException("Topik Mata Pelajaran ", request.getTopikId()));

        Materi_Teks materi = new Materi_Teks();
        materi.setJudul(request.getJudul());
        materi.setDeskripsi(request.getDeskripsi());
        materi.setUrlTeks(filePath);
        materi.setTopik(topik);

        return convertToResponse(materiTeksRepository.save(materi));
    }

    @Override
    public Materi_TeksResponse update(Long id, Materi_TeksRequest request, MultipartFile file) {
        Materi_Teks materi = materiTeksRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Teks ", id));

        if (request.getJudul() != null) materi.setJudul(request.getJudul());
        if (request.getDeskripsi() != null) materi.setDeskripsi(request.getDeskripsi());

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            if (fileName == null || !(fileName.endsWith(".pdf") || fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
                throw new BadRequestException ("Format Teks tidak didukung. Gunakan file dengan format pdf, doc, docx");
            }

            fileStorageService.deleteFile(materi.getUrlTeks());

            try {
                String filePath = fileStorageService.saveFile(file, "materi_teks");
                materi.setUrlTeks(filePath);
            } catch (IOException ex){
                throw new BadRequestException("Gagal update file");
            }
        }

        if (request.getTopikId() != null) {
            Topik_Mata_Pelajaran topik = topikMataPelajaranRepository.findById(request.getTopikId())
                .orElseThrow(() -> new NotFoundException("Topik Mata Pelajaran", request.getTopikId()));
            materi.setTopik(topik);
        }

        return convertToResponse(materiTeksRepository.save(materi));
    }

    @Override
    public void delete(Long id) {
        Materi_Teks materi = materiTeksRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Teks ", id));
        fileStorageService.deleteFile(materi.getUrlTeks());
        materiTeksRepository.delete(materi);
    }

    @Override
    public Materi_TeksResponse getById(Long id) {
        Materi_Teks materi = materiTeksRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Teks ", id));
        return convertToResponse(materi);
    }

    @Override
    public List<Materi_TeksResponse> getByTopik(Long topikId) {
        List<Materi_Teks> materi = materiTeksRepository.findByTopikId(topikId);

        if (materi.isEmpty()) {
            throw new NotFoundException("Mata Pelajaran dengan Topik ", topikId);
        }

        return materi.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<Materi_TeksResponse> getAll() {
        return materiTeksRepository.findAll()
            .stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    
}
