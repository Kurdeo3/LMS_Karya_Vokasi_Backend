package lms_karyavokasi_backend.lms_karyavokasi_backend.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment.Materi_AssessmentRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment.Materi_AssessmentResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Opsi_Jawaban.Opsi_JawabanRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Opsi_Jawaban.Opsi_JawabanResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Soal_Assessment.Soal_AssessmentRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Soal_Assessment.Soal_AssessmentResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Exception.NotFoundException;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Materi_Assessment;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Opsi_Jawaban;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Soal_Assessment;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Model.Topik_Mata_Pelajaran;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Materi_AssessmentRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Repository.Topik_Mata_PelajaranRepository;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Materi_AssessmentService;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PermissionChecker;

@Service
public class Materi_AssessmentServiceImpl implements Materi_AssessmentService{
    @Autowired
    private Materi_AssessmentRepository materiAssessmentRepository;

    @Autowired
    private Topik_Mata_PelajaranRepository topikMataPelajaranRepository;

    @Autowired
    private PermissionChecker permissionChecker;

    private Materi_AssessmentResponse convertToResponse(Materi_Assessment materi) {
        Materi_AssessmentResponse res = new Materi_AssessmentResponse();
        res.setId(materi.getId());
        res.setJudul(materi.getJudul());
        res.setDeskripsi(materi.getDeskripsi());
        res.setTipe(materi.getTipe());
        if (materi.getTopik() != null) {
            res.setTopikId(materi.getTopik().getId());
            res.setNamaTopik(materi.getTopik().getNama_topik());
        }

        List<Soal_AssessmentResponse> soalResponses = new ArrayList<>();
        for (Soal_Assessment soal : materi.getSoalList()) {
            Soal_AssessmentResponse soalRes = new Soal_AssessmentResponse();
            soalRes.setId(soal.getId());
            soalRes.setPertanyaan(soal.getPertanyaan());
            soalRes.setTipeSoal(soal.getTipeSoal());
            soalRes.setKunciJawabanEssay(soal.getKunciJawabanEssay());

            List<Opsi_JawabanResponse> opsiResponses = soal.getOpsiJawabanList().stream().map(opsi -> {
                Opsi_JawabanResponse opsiRes = new Opsi_JawabanResponse();
                opsiRes.setId(opsi.getId());
                opsiRes.setOpsi(opsi.getOpsi());
                opsiRes.setTeksJawaban(opsi.getTeksJawaban());
                opsiRes.setSkor(opsi.getSkor());
                return opsiRes;
            }).collect(Collectors.toList());

            soalRes.setOpsiJawabanList(opsiResponses);
            soalResponses.add(soalRes);
        }
        res.setSoalList(soalResponses);

        return res;
    }

    @Override
    public Materi_AssessmentResponse create(Materi_AssessmentRequest request) {
        permissionChecker.checkMateriAssessmentPengajar(request.getTopikId());

        Materi_Assessment materi = new Materi_Assessment();
        materi.setJudul(request.getJudul());
        materi.setDeskripsi(request.getDeskripsi());
        materi.setTipe(request.getTipe());

        Topik_Mata_Pelajaran topik = topikMataPelajaranRepository.findById(request.getTopikId())
            .orElseThrow(() -> new NotFoundException("Topik", request.getTopikId()));
        materi.setTopik(topik);

        if (request.getSoalList() != null) {
            for (Soal_AssessmentRequest soalRequest : request.getSoalList()) {
                Soal_Assessment soal = new Soal_Assessment();
                soal.setPertanyaan(soalRequest.getPertanyaan());
                soal.setTipeSoal(soalRequest.getTipeSoal());
                soal.setKunciJawabanEssay(soalRequest.getKunciJawabanEssay());
                soal.setMateriAssessment(materi);

                if (soalRequest.getOpsiJawabanList() != null) {
                    for (Opsi_JawabanRequest opsiReq : soalRequest.getOpsiJawabanList()) {
                        Opsi_Jawaban opsi = new Opsi_Jawaban();
                        opsi.setOpsi(opsiReq.getOpsi());
                        opsi.setTeksJawaban(opsiReq.getTeksJawaban());
                        opsi.setSkor(opsiReq.getSkor());
                        opsi.setSoal(soal);
                        soal.getOpsiJawabanList().add(opsi);
                    }
                }
                materi.getSoalList().add(soal);
            }
        }
        return convertToResponse(materiAssessmentRepository.save(materi));
    }

    @Override
    public List<Materi_AssessmentResponse> getByTopik(Long topikId) {
        return materiAssessmentRepository.findByTopikId(topikId)
            .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public Materi_AssessmentResponse getById(Long id) {
        Materi_Assessment materi = materiAssessmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Assessment ", id));
        return convertToResponse(materi);
    }

    @Override
    public Materi_AssessmentResponse update(Materi_AssessmentRequest request, Long id) {
        Materi_Assessment materi = materiAssessmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Assessment ", id));

        permissionChecker.checkMateriAssessmentPengajar(materi.getTopik().getId());

        if (request.getJudul() != null ) materi.setJudul(request.getJudul());
        if (request.getDeskripsi() != null ) materi.setDeskripsi(request.getDeskripsi());
        if (request.getTipe() != null ) materi.setTipe(request.getTipe());

        if (request.getSoalList() != null) {
            List<Soal_Assessment> soalFinalList = new ArrayList<>();

            for (Soal_AssessmentRequest soalReq : request.getSoalList()) {
                Soal_Assessment soal;

                if (soalReq.getId() != null) {
                    soal = materi.getSoalList().stream()
                            .filter(s -> s.getId().equals(soalReq.getId()))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException("Soal Assessment", soalReq.getId()));
                    
                    if (soalReq.getPertanyaan() != null ) soal.setPertanyaan(soalReq.getPertanyaan());
                    if (soalReq.getTipeSoal() != null ) soal.setTipeSoal(soalReq.getTipeSoal());
                    if (soalReq.getKunciJawabanEssay() != null) soal.setKunciJawabanEssay(soalReq.getKunciJawabanEssay());
                    
                    //Replace opsi jawaban
                    if (soalReq.getOpsiJawabanList() != null) {
                        soal.getOpsiJawabanList().clear();
                        for (Opsi_JawabanRequest opsiReq : soalReq.getOpsiJawabanList()) {
                            Opsi_Jawaban opsi = new Opsi_Jawaban();
                            opsi.setOpsi(opsiReq.getOpsi());
                            opsi.setTeksJawaban(opsiReq.getTeksJawaban());
                            opsi.setSkor(opsiReq.getSkor());
                            opsi.setSoal(soal);
                            soal.getOpsiJawabanList().add(opsi);
                        }
                    }
                } else {
                    //Kalau belum ada soal
                    soal = new Soal_Assessment();
                    soal.setPertanyaan(soalReq.getPertanyaan());
                    soal.setTipeSoal(soalReq.getTipeSoal());
                    soal.setKunciJawabanEssay(soalReq.getKunciJawabanEssay());
                    soal.setMateriAssessment(materi);

                    if (soalReq.getOpsiJawabanList() != null) {
                        for (Opsi_JawabanRequest opsiReq : soalReq.getOpsiJawabanList()) {
                            Opsi_Jawaban opsi = new Opsi_Jawaban();
                            opsi.setOpsi(opsiReq.getOpsi());
                            opsi.setTeksJawaban(opsiReq.getTeksJawaban());
                            opsi.setSkor(opsiReq.getSkor());
                            opsi.setSoal(soal);
                            soal.getOpsiJawabanList().add(opsi);
                        }
                    }
                }

                soalFinalList.add(soal);
            }

            materi.getSoalList().clear();
            materi.getSoalList().addAll(soalFinalList);
        }
        return convertToResponse(materiAssessmentRepository.save(materi));
    }

    @Override
    public void delete (Long id) {
        Materi_Assessment materi = materiAssessmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Materi Assessment ", id));
        permissionChecker.checkMateriAssessmentPengajar(materi.getTopik().getId());
        materiAssessmentRepository.delete(materi);
    }
}
