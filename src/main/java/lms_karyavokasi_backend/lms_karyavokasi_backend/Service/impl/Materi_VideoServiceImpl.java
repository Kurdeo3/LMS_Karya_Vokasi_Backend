package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.FileStorageService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Materi_Video;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Materi_VideoRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Topik_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Materi_VideoService;

@Service
public class Materi_VideoServiceImpl implements Materi_VideoService{

    @Autowired
    private Materi_VideoRepository materiVideoRepository;

    @Autowired
    private Topik_Mata_PelajaranRepository topikMataPelajaranRepository;

    @Autowired
    private FileStorageService fileStorageService;

    private Materi_VideoResponse convertToResponse(Materi_Video materi) {
        Materi_VideoResponse materiRespon = new Materi_VideoResponse();
        materiRespon.setId(materi.getId());
        materiRespon.setJudul(materi.getJudul());
        materiRespon.setDeskripsi(materi.getDeskripsi());
        materiRespon.setUrlVideo(materi.getUrlVideo());

        if (materi.getTopik() != null) {
            materiRespon.setTopikId(materi.getTopik().getId());
            materiRespon.setNamaTopik(materi.getTopik().getNama_topik());
        }

        return materiRespon;
    }

    @Override
    public Materi_VideoResponse create(Materi_VideoRequest request, MultipartFile file) {
        if (request.getJudul() == null || request.getJudul().isBlank()){
            throw new BadRequestException ("Judul wajib diisi");
        }

        if (request.getDeskripsi() == null || request.getDeskripsi().isBlank()){
            throw new BadRequestException ("Deskripsi wajib diisi");
        }

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File materi wajib diupload");
        }

        String fileName = file.getOriginalFilename();
        if (fileName != null && !(fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mkv"))) {
            throw new BadRequestException ("Format video tidak didukung. Gunakan file dengan format mp4, avi, mkv");
        }

        String filePath;
        try {
            filePath = fileStorageService.saveFile(file, "materi_video");
        } catch (IOException ex) {
            throw new BadRequestException("Gagal upload file video");
        }

        Topik_Mata_Pelajaran topik = topikMataPelajaranRepository.findById(request.getTopikId())
            .orElseThrow(() -> new NotFoundException("Topik Mata Pelajaran ", request.getTopikId()));
        
        Materi_Video materi = new Materi_Video();
        materi.setJudul(request.getJudul());
        materi.setDeskripsi(request.getDeskripsi());
        materi.setUrlVideo(filePath);
        materi.setTopik(topik);

        return convertToResponse(materiVideoRepository.save(materi));
    }

    @Override
    public Materi_VideoResponse update(Long id, Materi_VideoRequest request, MultipartFile file) {
        Materi_Video materi = materiVideoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Video ", id));

        if (request.getJudul() != null) materi.setJudul(request.getJudul());
        if (request.getDeskripsi() != null) materi.setDeskripsi(request.getDeskripsi());

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            if (fileName == null || !(fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mkv"))) {
                throw new BadRequestException ("Format video tidak didukung. Gunakan file dengan format mp4, avi, mkv");
            }

            fileStorageService.deleteFile(materi.getUrlVideo());
            
            try {
                String filePath = fileStorageService.saveFile(file, "materi_video");
                materi.setUrlVideo(filePath);
            } catch (IOException ex){
                throw new BadRequestException("Gagal update file");
            }
        }

        if (request.getTopikId() != null) {
            Topik_Mata_Pelajaran topik = topikMataPelajaranRepository.findById(request.getTopikId())
                .orElseThrow(() -> new NotFoundException("Topik Mata Pelajaran ", request.getTopikId()));
            materi.setTopik(topik);
        }

        return convertToResponse (materiVideoRepository.save(materi));
    }

    @Override
    public void delete(Long id) {
        Materi_Video materi = materiVideoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Video ", id));
        fileStorageService.deleteFile(materi.getUrlVideo());
        materiVideoRepository.delete(materi);
    }

    @Override
    public Materi_VideoResponse getById(Long id) {
        Materi_Video materi = materiVideoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Video ", id));
        return convertToResponse(materi);
    }

    @Override
    public List<Materi_VideoResponse> getByTopik(Long topikId) {
        List<Materi_Video> materi = materiVideoRepository.findByTopikId(topikId);

        if (materi.isEmpty()) {
            throw new NotFoundException("Mata Pelajaran dengan Topik, ", topikId);
        }
        return materi.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<Materi_VideoResponse> getAll() {
        return materiVideoRepository.findAll()
            .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

}
