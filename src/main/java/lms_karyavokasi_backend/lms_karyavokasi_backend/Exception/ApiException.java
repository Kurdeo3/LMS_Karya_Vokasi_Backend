package lms_karyavokasi_backend.lms_karyavokasi_backend.Exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException{
    private final HttpStatus status;

    protected ApiException (String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus () {
        return status;
    }
}
