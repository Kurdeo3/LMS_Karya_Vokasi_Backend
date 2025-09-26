package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar.PengajarRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar.PengajarResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.PengajarRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PengajarService;

@Service
public class PengajarServiceImpl implements PengajarService{
    @Autowired
    private PengajarRepository pengajarRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    ////////////
    @Override
    public Pengajar getCurrentPengajar() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Not authenticated");
        }
        
        String email = (String) auth.getPrincipal(); // dari filter

        return pengajarRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Pengajar dengan email " + email));
    }
    ////////////

    private PengajarResponse convertToResponse (Pengajar pengajar){
        PengajarResponse response = new PengajarResponse();
        response.setId(pengajar.getId());
        response.setNama(pengajar.getNama());
        response.setEmail(pengajar.getEmail());
        response.setTelfon(pengajar.getTelfon());
        response.setAlamat(pengajar.getAlamat());
        response.setDeskripsi(pengajar.getDeskripsi());
        response.setJenisKelamin(pengajar.getJenisKelamin());
        response.setJenjangPendidikan(pengajar.getJenjangPendidikan());
        response.setStatus(pengajar.getStatus());
        response.setFoto(pengajar.getFoto());
        return response;
    }

    @Override
    public PengajarResponse createPengajar(PengajarRequest request) {

        if (request.getNama() == null || request.getNama().isBlank()) {
            throw new BadRequestException("Nama wajib diisi");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email wajib diisi");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password wajib diisi");
        }

        // Field lain bisa optional atau default
        Pengajar pengajar = new Pengajar();
        pengajar.setNama(request.getNama());
        pengajar.setEmail(request.getEmail());
        pengajar.setTelfon(request.getTelfon());
        pengajar.setAlamat(request.getAlamat());
        pengajar.setDeskripsi(request.getDeskripsi());
        pengajar.setJenisKelamin(request.getJenisKelamin());
        pengajar.setJenjangPendidikan(request.getJenjangPendidikan());
        pengajar.setStatus("Pending"); // Default status saat pertama kali create

        pengajar.setPassword(passwordEncoder.encode(request.getPassword()));

        return convertToResponse(pengajarRepository.save(pengajar));
    }

    @Override
    public void deletePengajar(Long id) {
        if (!pengajarRepository.existsById(id)) {
                throw new NotFoundException("Pengajar", id);
        }
        pengajarRepository.deleteById(id);
    }

    @Override
    public List<PengajarResponse> getAllPengajar() {
        return pengajarRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PengajarResponse getPengajarById(Long id) {
        Pengajar pengajar = pengajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pengajar", id));
        return convertToResponse(pengajar);
    }

    @Override
    public PengajarResponse updatePengajar(PengajarRequest request, Long id) {
        Pengajar pengajar = pengajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pengajar", id));

         // Hanya update jika field dikirim dan tidak null
        if (request.getNama() != null) {
            pengajar.setNama(request.getNama());
        }

        if (request.getEmail() != null) {
            // Pastikan email tidak kosong string
            if (request.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email tidak boleh kosong");
            }
            pengajar.setEmail(request.getEmail());
        }

        if (request.getTelfon() != null) {
            pengajar.setTelfon(request.getTelfon());
        }

        if (request.getAlamat() != null) {
            pengajar.setAlamat(request.getAlamat());
        }

        if (request.getJenisKelamin() != null) {
            pengajar.setJenisKelamin(request.getJenisKelamin());
        }

        if (request.getJenjangPendidikan() != null) {
            pengajar.setJenjangPendidikan(request.getJenjangPendidikan());
        }

        if (request.getDeskripsi() != null) {
            pengajar.setDeskripsi(request.getDeskripsi());
        }

        if (request.getPassword() != null) {
            if (request.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password tidak boleh kosong");
            }
            pengajar.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return convertToResponse(pengajarRepository.save(pengajar));
    }

    @Override
    public PengajarResponse updateStatus(Long id, String status) {
        Pengajar pengajar = pengajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pengajar", id));

        if (status == null || (!status.equalsIgnoreCase("Confirmed") && !status.equalsIgnoreCase("Deny"))) {
            throw new IllegalArgumentException("Status harus 'Confirmed' atau 'Deny' ");
        }

        pengajar.setStatus(status);
        return convertToResponse(pengajarRepository.save(pengajar));
    }

    @Override
    public PengajarResponse uploadFotoProfile(Long id, MultipartFile foto) {
        Pengajar pengajar = pengajarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pengajar", id));

        try {

            // Ambil root project directory
            String rootPath = System.getProperty("user.dir");

            //Lokasi Folder Foto Yang Diupload
            String folderPath = rootPath + "/upload/pengajar/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (pengajar.getFoto() != null) {
                File oldFile = new File ("." + pengajar.getFoto());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            String fileName = "pengajar_" + id + "_" + System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            String filePath = folderPath + fileName;

            foto.transferTo(new File(filePath));

            String fileUrl = "/upload/pengajar/" + fileName;
            pengajar.setFoto(fileUrl);
            pengajarRepository.save(pengajar);

            return convertToResponse(pengajar);

        } catch (IOException e) {
            throw new RuntimeException("Gagal upload foto", e);
        }
    }
}
