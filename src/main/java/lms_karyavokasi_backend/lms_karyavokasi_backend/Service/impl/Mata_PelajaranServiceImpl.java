package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran.Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Mata_Pelajaran.Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.FileStorageService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.E_Katalog;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.E_KatalogRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Kategori_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Mata_PelajaranService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PengajarService;

@Service
public class Mata_PelajaranServiceImpl implements Mata_PelajaranService{
    @Autowired
    private Mata_PelajaranRepository mataPelajaranRepository;

    @Autowired
    private Kategori_Mata_PelajaranRepository kategoriRepository;

    @Autowired
    private E_KatalogRepository eKatalogRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PengajarService pengajarService;

    private void checkPermission(Mata_Pelajaran mp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));

        if (isAdmin) return; // admin bebas

        if (mp.getEKatalog() == null || mp.getEKatalog().getPengajar() == null) {
            throw new AccessDeniedException("Mata Pelajaran tidak memiliki pengajar yang valid (hanya admin yang dapat mengubah)");
        }

        Long currentPengajarId = pengajarService.getCurrentPengajar().getId();
        if (!mp.getEKatalog().getPengajar().getId().equals(currentPengajarId)) {
            throw new AccessDeniedException("Anda tidak punya akses ke Mata Pelajaran ini");
        }
    }

    private void checkPermissionFromEKatalog(Long eKatalogId) {
        E_Katalog eKatalog = eKatalogRepository.findById(eKatalogId)
                .orElseThrow(() -> new NotFoundException("E_Katalog", eKatalogId));
        if (eKatalog.getPengajar() == null) {
            // jika tidak ada pengajar → hanya admin boleh
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));
            if (!isAdmin) throw new AccessDeniedException("E_Katalog tidak punya pengajar, hanya admin bisa menautkan");
            return;
        }

        Long ownerPengajarId = eKatalog.getPengajar().getId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));
        if (isAdmin) return;

        Long currentPengajarId = pengajarService.getCurrentPengajar().getId();
        if (!currentPengajarId.equals(ownerPengajarId)) {
            throw new AccessDeniedException("Anda tidak punya akses terhadap E_Katalog ini");
        }
    }


    private Mata_PelajaranResponse convertToResponse(Mata_Pelajaran mp) {
        Mata_PelajaranResponse response = new Mata_PelajaranResponse();
        response.setId(mp.getId());
        response.setNamaMataPelajaran(mp.getNamaMataPelajaran());
        response.setThumbnail(mp.getThumbnail());
        response.setDeskripsi(mp.getDeskripsi());

        if (mp.getKategoriMataPelajaran() != null) {
            response.setKategoriId(mp.getKategoriMataPelajaran().getId());
            response.setKategoriNama(mp.getKategoriMataPelajaran().getNama_kategori());
        }

        if (mp.getEKatalog() != null) {
            response.setEKatalogId(mp.getEKatalog().getId());
            response.setEKatalogJudul(mp.getEKatalog().getJudul_e_katalog());
        }

        return response;
    }

    @Override
    public Mata_PelajaranResponse createMataPelajaran(Mata_PelajaranRequest request, MultipartFile thumbnailFile) {
        // 1️⃣ Buat dulu object baru
        Mata_Pelajaran mp = new Mata_Pelajaran();
        mp.setNamaMataPelajaran(request.getNamaMataPelajaran());
        mp.setDeskripsi(request.getDeskripsi());

        // 2️⃣ Upload thumbnail dulu (jika ada)
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            try {
                mp.setThumbnail(fileStorageService.saveFile(thumbnailFile, "mata_pelajaran"));
            } catch (IOException ex) {
                throw new BadRequestException("Gagal upload thumbnail");
            }
        } else {
            throw new BadRequestException("Thumbnail wajib diisi");
        }

        // 3️⃣ Ambil E_Katalog terkait
        if (request.getEKatalogId() == null) {
            throw new BadRequestException("eKatalogId wajib diisi saat membuat Mata Pelajaran");
        }

        E_Katalog eKatalog = eKatalogRepository.findById(request.getEKatalogId())
                .orElseThrow(() -> new NotFoundException("E_Katalog", request.getEKatalogId()));

        checkPermissionFromEKatalog(eKatalog.getId());
        mp.setEKatalog(eKatalog);

        // 4️⃣ Ambil Kategori jika ada
        if (request.getKategoriId() != null) {
            mp.setKategoriMataPelajaran(
                    kategoriRepository.findById(request.getKategoriId())
                            .orElseThrow(() -> new NotFoundException("Kategori Mata Pelajaran", request.getKategoriId()))
            );
        }

        // 5️⃣ Simpan ke DB
        Mata_Pelajaran saved = mataPelajaranRepository.save(mp);
        return convertToResponse(saved);
    }

    @Override
    public Mata_PelajaranResponse updateMataPelajaran(Long id, Mata_PelajaranRequest request, MultipartFile thumbnailFile) {
        Mata_Pelajaran mp = mataPelajaranRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Mata Pelajaran", id));

        checkPermission(mp);

        if (request.getNamaMataPelajaran() != null) mp.setNamaMataPelajaran(request.getNamaMataPelajaran());
        if (request.getDeskripsi() != null) mp.setDeskripsi(request.getDeskripsi());

        if (request.getKategoriId() != null) {
            mp.setKategoriMataPelajaran(
                kategoriRepository.findById(request.getKategoriId())
                    .orElseThrow(() -> new NotFoundException("Kategori Mata Pelajaran", request.getKategoriId()))
            );
        }

        if (request.getEKatalogId() != null) {
            var eKatalog = eKatalogRepository.findById(request.getEKatalogId())
                    .orElseThrow(() -> new NotFoundException("E_Katalog", request.getEKatalogId()));
            checkPermissionFromEKatalog(eKatalog.getId());
            mp.setEKatalog(eKatalog);
        }

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            fileStorageService.deleteFile(mp.getThumbnail());
            try {
                mp.setThumbnail(fileStorageService.saveFile(thumbnailFile, "mata_pelajaran"));
            } catch (IOException ex) {
                throw new BadRequestException("Gagal upload thumbnail");
            }
        }

        return convertToResponse(mataPelajaranRepository.save(mp));
    }

    @Override
    public void deleteMataPelajaran(Long id) {
        Mata_Pelajaran mp = mataPelajaranRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Mata Pelajaran", id));
        checkPermission(mp); 
        fileStorageService.deleteFile(mp.getThumbnail());
        mataPelajaranRepository.delete(mp);
    }

    @Override
    public Mata_PelajaranResponse getById(Long id) {
        Mata_Pelajaran mp = mataPelajaranRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Mata Pelajaran", id));
        return convertToResponse(mp);
    }

    @Override
    public List<Mata_PelajaranResponse> getAll() {
        return mataPelajaranRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<Mata_PelajaranResponse> getByEKatalog(Long eKatalogId) {
        return mataPelajaranRepository.findByeKatalog_Id(eKatalogId)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
}
