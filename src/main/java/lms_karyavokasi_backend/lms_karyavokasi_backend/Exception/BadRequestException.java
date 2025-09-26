package lms_karyavokasi_backend.lms_karyavokasi_backend.Exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException{
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
