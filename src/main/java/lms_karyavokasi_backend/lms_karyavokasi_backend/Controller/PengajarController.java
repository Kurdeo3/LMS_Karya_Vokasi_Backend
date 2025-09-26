package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar.PengajarRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pengajar.PengajarResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PengajarService;

@RestController
@RequestMapping("/pengajar")
@CrossOrigin("http://localhost:3000")
public class PengajarController {

    @Autowired
    private PengajarService pengajarService;

    // CREATE
    @PostMapping
    public PengajarResponse createPengajar(@RequestBody PengajarRequest request) {
        return pengajarService.createPengajar(request);
    }

    // READ ALL
    // Search hanya Admin

    @GetMapping
    public List<PengajarResponse> getAllPengajar(){
        return pengajarService.getAllPengajar();
    }

    // READ BY ID
    // Admin boleh baca semua, pengajar hanya boleh baca dirinya sendiri

    @GetMapping("/{id}")
    public PengajarResponse getPengajarById(@PathVariable Long id){
        return pengajarService.getPengajarById(id);
    }
    
    // UPDATE
    @PutMapping("/{id}")
    // Admin boleh update semua, pengajar hanya boleh baca dirinya sendiri
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMINISTRATOR')")
    public PengajarResponse updatePengajar(@PathVariable Long id, @RequestBody PengajarRequest request) {
        return pengajarService.updatePengajar(request, id);
    }

    // DELETE
    // Delete hanya boleh Admin
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public String deletePengajar(@PathVariable long id){
        pengajarService.deletePengajar(id);
        return "Pengajar with id " + id + "has been deleted";
    }

    // STATUS
    // Ubah Status hanya Admin
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}/status")
    public PengajarResponse updateStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        return pengajarService.updateStatus(id, status);
    }

    @PreAuthorize("#id == authentication.principal.id")
    @PutMapping("/{id}/foto")
    public PengajarResponse uploadFotoProfile(@PathVariable Long id, @RequestParam("foto") MultipartFile foto) {
        return pengajarService.uploadFotoProfile(id, foto);
    }
}
