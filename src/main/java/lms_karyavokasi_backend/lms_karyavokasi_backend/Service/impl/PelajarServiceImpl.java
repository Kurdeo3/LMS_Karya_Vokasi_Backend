package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar.PelajarRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar.PelajarResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pelajar;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.PelajarRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PelajarService;

@Service
public class PelajarServiceImpl implements PelajarService{
    @Autowired
    private PelajarRepository PelajarRepository;

    private PelajarResponse convertToResponse (Pelajar Pelajar){
        PelajarResponse response = new PelajarResponse();
        response.setId(Pelajar.getId());
        response.setNama(Pelajar.getNama());
        response.setEmail(Pelajar.getEmail());
        response.setTelfon(Pelajar.getTelfon());
        response.setAlamat(Pelajar.getAlamat());
        response.setJenisKelamin(Pelajar.getJenisKelamin());
        response.setJenjangPendidikan(Pelajar.getJenjangPendidikan());
        response.setStatus(Pelajar.getStatus());
        response.setFoto(Pelajar.getFoto());
        response.setPassword(Pelajar.getPassword());
        return response;
    }

    @Override
    public PelajarResponse createPelajar(PelajarRequest request) {
        Pelajar Pelajar = new Pelajar();
        Pelajar.setNama(request.getNama());
        Pelajar.setEmail(request.getEmail());
        Pelajar.setTelfon(request.getTelfon());
        Pelajar.setAlamat(request.getAlamat());
        Pelajar.setJenisKelamin(request.getJenisKelamin());
        Pelajar.setJenjangPendidikan(request.getJenjangPendidikan());
        Pelajar.setStatus("Pending"); // Default status saat pertama kali create

        Pelajar.setPassword(request.getPassword());

        return convertToResponse(PelajarRepository.save(Pelajar));
    }

    @Override
    public void deletePelajar(Long id) {
        if (!PelajarRepository.existsById(id)) {
                throw new NotFoundException("Pengajar", id);
        }
        PelajarRepository.deleteById(id);
    }

    @Override
    public List<PelajarResponse> getAllPelajar() {
        return PelajarRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PelajarResponse getPelajarById(Long id) {
        Pelajar Pelajar = PelajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pelajar", id));
        return convertToResponse(Pelajar);
    }

    @Override
    public PelajarResponse updatePelajar(PelajarRequest request, Long id) {
        Pelajar Pelajar = PelajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pelajar", id));

        Pelajar.setNama(request.getNama());
        Pelajar.setEmail(request.getEmail());
        Pelajar.setTelfon(request.getTelfon());
        Pelajar.setAlamat(request.getAlamat());
        Pelajar.setJenisKelamin(request.getJenisKelamin());
        Pelajar.setJenjangPendidikan(request.getJenjangPendidikan());

        Pelajar.setPassword(request.getPassword());

        return convertToResponse(PelajarRepository.save(Pelajar));
    }

    @Override
    public PelajarResponse uploadFotoProfile(Long id, MultipartFile foto) {
        Pelajar Pelajar = PelajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pelajar", id));

        try {

            // Ambil root project directory
            String rootPath = System.getProperty("user.dir");

            //Lokasi Folder Foto Yang Diupload
            String folderPath = rootPath + "/upload/Pelajar/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (Pelajar.getFoto() != null) {
                File oldFile = new File ("." + Pelajar.getFoto());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            String fileName = "Pelajar_" + id + "_" + System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            String filePath = folderPath + fileName;

            foto.transferTo(new File(filePath));

            String fileUrl = "/upload/Pelajar/" + fileName;
            Pelajar.setFoto(fileUrl);
            PelajarRepository.save(Pelajar);

            return convertToResponse(Pelajar);

        } catch (IOException e) {
            throw new RuntimeException("Gagal upload foto", e);
        }
    }
}
