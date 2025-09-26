package lms_karyavokasi_backend.lms_karyavokasi_backend.Exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException{
    public NotFoundException(String resource, Long id){
        super(resource + " dengan id " + id + " tidak ditemukan ", HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
