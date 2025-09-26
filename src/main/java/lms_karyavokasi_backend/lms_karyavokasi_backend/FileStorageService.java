package lms_karyavokasi_backend.lms_karyavokasi_backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final String UPLOAD_DIR = "uploads";

    public String saveFile(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Path uploadPath = Paths.get(UPLOAD_DIR, folder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        return "/" + UPLOAD_DIR + "/" + folder + "/" + fileName;
    }

    public void deleteFile(String filePath) {
        if (filePath != null) {
            File file = new File("." + filePath);
            if (file.exists()) file.delete();
        }
    }
}
