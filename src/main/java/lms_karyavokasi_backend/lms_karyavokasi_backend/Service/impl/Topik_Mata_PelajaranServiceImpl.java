package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Topik_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PengajarService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Topik_Mata_PelajaranService;

@Service
public class Topik_Mata_PelajaranServiceImpl implements Topik_Mata_PelajaranService{

    @Autowired
    private Topik_Mata_PelajaranRepository topikRepository;

    @Autowired
    private Mata_PelajaranRepository mataPelajaranRepository;

    @Autowired
    private PengajarService pengajarService;

    private void checkPermission(Topik_Mata_Pelajaran tmp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));

        if (isAdmin) return; // admin bebas

        if (tmp.getMataPelajaran() == null || tmp.getMataPelajaran().getEKatalog().getPengajar() == null) {
            throw new AccessDeniedException("Topik tidak memiliki pengajar yang valid (hanya admin yang dapat mengubah)");
        }

        Long currentPengajarId = pengajarService.getCurrentPengajar().getId();
        if (!tmp.getMataPelajaran().getEKatalog().getPengajar().getId().equals(currentPengajarId)) {
            throw new AccessDeniedException("Anda tidak punya akses ke Topik ini");
        }
    }

    private void checkPermissionFromMataPelajaranKatalog(Long mataPelajaranId) {
        Mata_Pelajaran mataPelajaran = mataPelajaranRepository.findById(mataPelajaranId)
                .orElseThrow(() -> new NotFoundException("Mata Pelajaran", mataPelajaranId));
        if (mataPelajaran.getEKatalog().getPengajar() == null) {
            // jika tidak ada pengajar → hanya admin boleh
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));
            if (!isAdmin) throw new AccessDeniedException("Mata Pelajaran tidak punya pengajar, hanya admin bisa menautkan");
            return;
        }

        Long ownerPengajarId = mataPelajaran.getEKatalog().getPengajar().getId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"));
        if (isAdmin) return;

        Long currentPengajarId = pengajarService.getCurrentPengajar().getId();
        if (!currentPengajarId.equals(ownerPengajarId)) {
            throw new AccessDeniedException("Anda tidak punya akses terhadap Mata Pelajaran ini");
        }
    }

    private Topik_Mata_PelajaranResponse convertToResponse(Topik_Mata_Pelajaran mp) {
        Topik_Mata_PelajaranResponse response = new Topik_Mata_PelajaranResponse();
        response.setId(mp.getId());
        response.setNama_topik(mp.getNama_topik());

        if (mp.getMataPelajaran() != null) {
            response.setMataPelajaranId(mp.getMataPelajaran().getId());
            response.setNama_mataPelajaranId(mp.getMataPelajaran().getNamaMataPelajaran());
        }

        return response;
    }

    @Override
    public Topik_Mata_PelajaranResponse createTopik(Topik_Mata_PelajaranRequest request) {
        Topik_Mata_Pelajaran tpk = new Topik_Mata_Pelajaran();
        tpk.setNama_topik(request.getNama_topik());

        if (request.getMataPelajaranId() == null) {
            throw new BadRequestException("mataPelajaranId wajib diisi saat membuat Topik");
        }

        Mata_Pelajaran mataPelajaran = mataPelajaranRepository.findById(request.getMataPelajaranId())
                .orElseThrow(() -> new NotFoundException("Mata Pelajaran", request.getMataPelajaranId()));

        checkPermissionFromMataPelajaranKatalog(mataPelajaran.getId());
        tpk.setMataPelajaran(mataPelajaran);
        
        if (request.getMataPelajaranId() != null) {
            tpk.setMataPelajaran(
                mataPelajaranRepository.findById(request.getMataPelajaranId())
                    .orElseThrow(() -> new NotFoundException("Mata Pelajaran", request.getMataPelajaranId()))
            );
        }

        return convertToResponse(topikRepository.save(tpk));
    }

    @Override
    public Topik_Mata_PelajaranResponse updateTopik(Long id, Topik_Mata_PelajaranRequest request) {
        Topik_Mata_Pelajaran tpk = topikRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Topik", id));

        checkPermission(tpk);

        if (request.getNama_topik() != null ) tpk.setNama_topik(request.getNama_topik());

        if (request.getMataPelajaranId() != null) {
            tpk.setMataPelajaran(
                mataPelajaranRepository.findById(request.getMataPelajaranId())
                    .orElseThrow(() -> new NotFoundException("Mata Pelajaran", request.getMataPelajaranId()))
            );
        }

        return convertToResponse(topikRepository.save(tpk));
    }

    @Override
    public void deleteTopik(Long id) {
        Topik_Mata_Pelajaran tpk = topikRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Topik", id));

        checkPermission(tpk);
        topikRepository.delete(tpk);
    }

    @Override
    public Topik_Mata_PelajaranResponse getByIdTopik(Long id) {
        Topik_Mata_Pelajaran tpk = topikRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Topik", id));
        return convertToResponse(tpk);
    }

    @Override
    public List<Topik_Mata_PelajaranResponse> getAllTopik() {
        return topikRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<Topik_Mata_PelajaranResponse> getByMataPelajaran(Long mataPelajaranId) {
        return topikRepository.findByMataPelajaranId(mataPelajaranId)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

}
