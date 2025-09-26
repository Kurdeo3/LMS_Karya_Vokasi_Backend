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

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog.E_KatalogRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.E_Katalog.E_KatalogResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.FileStorageService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.E_Katalog;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Kategori_E_Katalog;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.E_KatalogRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Kategori_E_KatalogRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.E_KatalogService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PengajarService;

@Service
public class E_KatalogServiceImpl implements E_KatalogService{

    @Autowired
    private E_KatalogRepository eKatalogRepository;

    @Autowired
    private Kategori_E_KatalogRepository kategoriRepository;

    @Autowired
    private PengajarService pengajarService;

    @Autowired
    private FileStorageService fileStorageService;

    private void checkPermission(E_Katalog e) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));

        if (isAdmin) return; // admin bebas

        // ambil pengajar login sekarang
        Pengajar currentPengajar = pengajarService.getCurrentPengajar();

        if (!e.getPengajar().getId().equals(currentPengajar.getId())) {
            throw new AccessDeniedException("Anda tidak punya akses ke E_Katalog ini");
        }
    }


    private E_KatalogResponse convertToResponse(E_Katalog e) {
        E_KatalogResponse response = new E_KatalogResponse();
        response.setId(e.getId());
        response.setJudul_e_katalog(e.getJudul_e_katalog());
        response.setDeskripsi(e.getDeskripsi());
        response.setThumbnail(e.getThumbnail());
        response.setPenjelasan_paket(e.getPenjelasan_paket());
        response.setStart_date(e.getStart_date());
        response.setDurasi_bulan(e.getDurasi_bulan());
        response.setStatus(e.getStatus());

        if (e.getKategoriEKatalog() != null) {
            response.setKategoriId(e.getKategoriEKatalog().getId());
            response.setKategoriNama(e.getKategoriEKatalog().getNama_kategori());
        }

        if (e.getPengajar() != null) {
            response.setPengajarId(e.getPengajar().getId());
            response.setPengajarEmail(e.getPengajar().getEmail());
            response.setPengajarNama(e.getPengajar().getNama());
        }

        return response;
    }

    @Override
    public E_KatalogResponse createE_Katalog(E_KatalogRequest request, MultipartFile thumbnailFile) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isPengajar = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_PENGAJAR"));

        if (!isPengajar) {
            throw new AccessDeniedException("Hanya Pengajar yang dapat membuat E_Katalog");
        }

        // 🔥 pakai fungsi dari PengajarService
        Pengajar pengajar = pengajarService.getCurrentPengajar();

        if (request.getJudul_e_katalog() == null || request.getJudul_e_katalog().isBlank()) {
            throw new BadRequestException("Judul E_Katalog wajib diisi");
        }
        if (request.getDeskripsi() == null || request.getDeskripsi().isBlank()) {
            throw new BadRequestException("Deskripsi wajib diisi");
        }
        if (request.getPenjelasan_paket() == null || request.getPenjelasan_paket().isBlank()) {
            throw new BadRequestException("Penjelasan Paket wajib diisi");
        }

        String thumbnailPath = null;
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            try {
                thumbnailPath = fileStorageService.saveFile(thumbnailFile, "e_katalog");
            } catch (IOException ex) {
                throw new BadRequestException("Gagal upload thumbnail");
            }
        }

        Kategori_E_Katalog kategori = kategoriRepository.findById(request.getKategoriId())
                .orElseThrow(() -> new NotFoundException("Kategori_E_Katalog", request.getKategoriId()));

        E_Katalog e = new E_Katalog();
        e.setJudul_e_katalog(request.getJudul_e_katalog());
        e.setDeskripsi(request.getDeskripsi());
        e.setThumbnail(thumbnailPath);
        e.setPenjelasan_paket(request.getPenjelasan_paket());
        e.setStart_date(request.getStart_date());
        e.setDurasi_bulan(request.getDurasi_bulan());
        e.setKategoriEKatalog(kategori);

        // foreign key diset langsung dari pengajar login sekarang
        e.setPengajar(pengajar);

        e.setStatus(request.getStatus() != null ? request.getStatus() : "DRAFT");

        return convertToResponse(eKatalogRepository.save(e));
    }

    @Override
    public E_KatalogResponse updateE_Katalog(Long id, E_KatalogRequest request, MultipartFile thumbnailFile) {
        E_Katalog e = eKatalogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("E_Katalog", id));

        checkPermission(e);

        if (request.getJudul_e_katalog() != null) e.setJudul_e_katalog(request.getJudul_e_katalog());
        if (request.getDeskripsi() != null) e.setDeskripsi(request.getDeskripsi());
        if (request.getThumbnail() != null) e.setThumbnail(request.getThumbnail());
        if (request.getPenjelasan_paket() != null) e.setPenjelasan_paket(request.getPenjelasan_paket());
        if (request.getStart_date() != null) e.setStart_date(request.getStart_date());
        if (request.getDurasi_bulan() != null) e.setDurasi_bulan(request.getDurasi_bulan());

        if (request.getKategoriId() != null) {
            Kategori_E_Katalog kategori = kategoriRepository.findById(request.getKategoriId())
                    .orElseThrow(() -> new NotFoundException("Kategori_E_Katalog", request.getKategoriId()));
            e.setKategoriEKatalog(kategori);
        }

        if (request.getStatus() != null) e.setStatus(request.getStatus());

        // jika ada thumbnail baru → hapus lama + simpan baru
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            fileStorageService.deleteFile(e.getThumbnail());
            try {
                e.setThumbnail(fileStorageService.saveFile(thumbnailFile, "e_katalog"));
            } catch (IOException ex) {
                throw new BadRequestException("Gagal upload thumbnail");
            }
        }

        return convertToResponse(eKatalogRepository.save(e));
    }

    @Override
    public void deleteE_Katalog(Long id) {
        E_Katalog e = eKatalogRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("E_Katalog", id));
            
        checkPermission(e);
        fileStorageService.deleteFile(e.getThumbnail());
        eKatalogRepository.deleteById(id);
    }

    @Override
    public List<E_KatalogResponse> getAllE_Katalog() {
        return eKatalogRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public E_KatalogResponse getByIdE_Katalog(Long id) {
        E_Katalog e = eKatalogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("E_Katalog", id));
        return convertToResponse(e);
    }

}
