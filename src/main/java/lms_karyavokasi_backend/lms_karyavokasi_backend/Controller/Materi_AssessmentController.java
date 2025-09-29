package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment.Materi_AssessmentRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Assessment.Materi_AssessmentResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Materi_AssessmentService;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/materi_assessment")
public class Materi_AssessmentController {
    @Autowired
    Materi_AssessmentService materiAssessmentService;

    // Create materi assessment
    @PreAuthorize("hasRole('PENGAJAR')")
    @PostMapping
    public Materi_AssessmentResponse create(@RequestBody Materi_AssessmentRequest request) {
        return materiAssessmentService.create(request);
    }

    // Get all materi assessment by topik
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/topik/{topikId}")
    public List<Materi_AssessmentResponse> getByTopik(@PathVariable Long topikId) {
        return materiAssessmentService.getByTopik(topikId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/{id}")
    public Materi_AssessmentResponse getById(@PathVariable Long id) {
        return materiAssessmentService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @PutMapping("/{id}")
    public Materi_AssessmentResponse update(@RequestBody Materi_AssessmentRequest request, @PathVariable Long id) {
        return materiAssessmentService.update(request, id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        materiAssessmentService.delete(id);
    }
}
