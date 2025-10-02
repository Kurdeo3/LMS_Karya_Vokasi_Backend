package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Topik_Mata_Pelajaran.Topik_Mata_PelajaranResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.BadRequestException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Topik_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PermissionChecker;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Topik_Mata_PelajaranService;

@Service
public class Topik_Mata_PelajaranServiceImpl implements Topik_Mata_PelajaranService{

    @Autowired
    private Topik_Mata_PelajaranRepository topikRepository;

    @Autowired
    private Mata_PelajaranRepository mataPelajaranRepository;

    @Autowired
    private PermissionChecker permissionChecker;

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

        permissionChecker.checkMataPelajaranPengajar(mataPelajaran.getId());
        tpk.setMataPelajaran(mataPelajaran);
        return convertToResponse(topikRepository.save(tpk));
    }

    @Override
    public Topik_Mata_PelajaranResponse updateTopik(Long id, Topik_Mata_PelajaranRequest request) {
        Topik_Mata_Pelajaran tpk = topikRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Topik", id));

        permissionChecker.checkTopikPengajar(id);

        if (request.getNama_topik() != null ) tpk.setNama_topik(request.getNama_topik());
        return convertToResponse(topikRepository.save(tpk));
    }

    @Override
    public void deleteTopik(Long id) {
        Topik_Mata_Pelajaran tpk = topikRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Topik", id));

        permissionChecker.checkTopikPengajar(id);
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
