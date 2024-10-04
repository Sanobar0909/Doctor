package uz.pdp.Doctor.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.Doctor.dto.ErrorBodyDTO;
import uz.pdp.Doctor.exception.EmailOrPasswordWrong;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EmailOrPasswordWrong.class})
    public ErrorBodyDTO usernameOrPasswordWrong(HttpServletRequest request, EmailOrPasswordWrong exception) {
        return new ErrorBodyDTO(
                exception.getStatus().value(),
                request.getRequestURI(),
                request.getRequestURL().toString(),
                exception.getClass().toString(),
                exception.getMessage(),
                LocalDateTime.now());
    }
}
