package lms_karyavokasi_backend.lms_karyavokasi_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.E_Katalog;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Pengajar;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.E_KatalogRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Topik_Mata_PelajaranRepository;

@Component
public class PermissionChecker {
    @Autowired
    private PengajarService pengajarService;

    @Autowired 
    private E_KatalogRepository eKatalogRepository;

    @Autowired
    private Mata_PelajaranRepository mataPelajaranRepository;

    @Autowired
    private Topik_Mata_PelajaranRepository topikMataPelajaranRepository;

    // Check Permission for Admin
    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));
    }

    // Check Permission for E_Katalog belongs to current Pengajar
    public void checkEKatalogPengajar(Long eKatalogId) {
        if (isAdmin()) return;

        Pengajar current = pengajarService.getCurrentPengajar();
        E_Katalog e = eKatalogRepository.findById(eKatalogId)
            .orElseThrow(() -> new NotFoundException("E Katalog ", eKatalogId));
        
        if (null == e.getPengajar() || !e.getPengajar().getId().equals(current.getId())) {
            throw new AccessDeniedException("Anda tidak punya akses ke E_Katalog ini");
        }
    }

    // Check Permission for Mata Pelajaran that belongs to Current Pengajar's E Katalog
    public void checkMataPelajaranPengajar(Long mataPelajaranId) {
        if (isAdmin()) return;

        Mata_Pelajaran mp = mataPelajaranRepository.findById(mataPelajaranId)
            .orElseThrow(() -> new NotFoundException("Mata Pelajaran ", mataPelajaranId));

        if (mp.getEKatalog() == null || mp.getEKatalog().getPengajar() == null) {
            throw new AccessDeniedException("Mata Pelajaran ini tidak punya pengajar, hanya admin bisa akses");
        }

        Long pengajarId = mp.getEKatalog().getPengajar().getId();
        if (!pengajarService.getCurrentPengajar().getId().equals(pengajarId)){
            throw new AccessDeniedException("Anda tidak punya akses ke Mata Pelajaran ini");
        }
    }
    
    // Check Permission for Topik that belongs to Current Pengajar's Mata Pelajaran & E_Katalog
    public void checkTopikPengajar(Long topikId) {
        if (isAdmin()) return;

        Topik_Mata_Pelajaran topik = topikMataPelajaranRepository.findById(topikId)
            .orElseThrow(() -> new NotFoundException("Topik Mata Pelajaran ", topikId));
        
        if (topik.getMataPelajaran() == null 
            || topik.getMataPelajaran().getEKatalog() == null 
            || topik.getMataPelajaran().getEKatalog().getPengajar() == null) {
            throw new AccessDeniedException("Topik ini tidak punya pengajar, hanya admin bisa akses");
        }

        Long pengajarId = topik.getMataPelajaran().getEKatalog().getPengajar().getId();
        if (!pengajarService.getCurrentPengajar().getId().equals(pengajarId)){
            throw new AccessDeniedException("Anda tidak punya akses ke Topik ini");
        }
    }

    // Check Permission for Materi Teks by Topik
    public void checkMateriTeksPengajar(Long topikId) {
        checkTopikPengajar(topikId);
    }

    // Check Permission for Materi Video by Topik
    public void checkMateriVideoPengajar(Long topikId) {
        checkTopikPengajar(topikId);
    }

    // Check Permission for Materi Assessment by Topik
    public void checkMateriAssessmentPengajar(Long topikId) {
        checkTopikPengajar(topikId);
    }
}
