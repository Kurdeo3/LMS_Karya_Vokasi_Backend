package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Materi_Video.Materi_VideoResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.Materi_VideoService;

@RestController
@RequestMapping("/materi_video")
@CrossOrigin("http://localhost:3000")
public class Materi_VideoController {
    @Autowired
    private Materi_VideoService materiVideoService;

    @PreAuthorize("hasRole('PENGAJAR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Materi_VideoResponse create(@RequestPart("data") Materi_VideoRequest request,
        @RequestPart("file") MultipartFile file) {
        return materiVideoService.create(request, file);
    }

    @PreAuthorize("hasRole('PENGAJAR')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Materi_VideoResponse update(@PathVariable Long id, @RequestPart("data") Materi_VideoRequest request,
        @RequestPart(value="file", required=false) MultipartFile file) {
        return materiVideoService.update(id, request, file);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @GetMapping("/topik/{topikId}")
    public List<Materi_VideoResponse> getByTopik(@PathVariable Long topikId) {
        return materiVideoService.getByTopik(topikId);
    }

    @PreAuthorize("hasAnyRole('PENGAJAR', 'ADMINISTRATOR')")
    @GetMapping()
    public List<Materi_VideoResponse> getAll() {
        return materiVideoService.getAll();
    }

    @PreAuthorize("hasAnyRole('PENGAJAR', 'ADMINISTRATOR')")
    @GetMapping("/{id}")
    public Materi_VideoResponse getById(@PathVariable long id) {
        return materiVideoService.getById(id);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PENGAJAR')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        materiVideoService.delete(id);
        return "Materi Video with id " + id + " has been deleted";
    }
}
