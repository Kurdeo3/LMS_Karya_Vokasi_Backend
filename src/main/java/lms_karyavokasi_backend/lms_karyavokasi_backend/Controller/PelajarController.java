package lms_karyavokasi_backend.lms_karyavokasi_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar.PelajarRequest;
import lms_karyavokasi_backend.lms_karyavokasi_backend.DTO.Pelajar.PelajarResponse;
import lms_karyavokasi_backend.lms_karyavokasi_backend.Service.PelajarService;

@RestController
@RequestMapping("/pelajar")
@CrossOrigin("http://localhost:3000")
public class PelajarController {

    @Autowired
    private PelajarService PelajarService;

    // CREATE
    @PostMapping
    public PelajarResponse createPelajar(@RequestBody PelajarRequest request) {
        return PelajarService.createPelajar(request);
    }

    // READ ALL
    @GetMapping
    public List<PelajarResponse> getAllPelajar(){
        return PelajarService.getAllPelajar();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public PelajarResponse getPelajarById(@PathVariable Long id){
        return PelajarService.getPelajarById(id);
    }
    
    // UPDATE
    @PutMapping("/{id}")
    public PelajarResponse updatePelajar(@PathVariable Long id, @RequestBody PelajarRequest request) {
        return PelajarService.updatePelajar(request, id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deletePelajar(@PathVariable long id){
        PelajarService.deletePelajar(id);
        return "Pelajar with id " + id + "has been deleted";
    }

    @PutMapping("/{id}/foto")
    public PelajarResponse uploadFotoProfile(@PathVariable Long id, @RequestParam("foto") MultipartFile foto) {
        return PelajarService.uploadFotoProfile(id, foto);
    }
}
