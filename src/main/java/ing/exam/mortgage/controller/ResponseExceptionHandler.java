package ing.exam.mortgage.controller;

import java.util.List;

import ing.exam.mortgage.dto.ErrorResponseDto;
import ing.exam.mortgage.service.exception.MortgageException;
import ing.exam.mortgage.service.exception.NoPeriodMatchedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(NoPeriodMatchedException.class)
    public ResponseEntity<ErrorResponseDto> onNoPeriodMatched(NoPeriodMatchedException exception) {
        log.warn("exception caught", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(composeErrorFromException(exception));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> onInvalidMethodArguments(
            MethodArgumentNotValidException exception) {

        List<ErrorResponseDto.ErrorDetail> details = exception.getFieldErrors()
                .stream()
                .map(e -> ErrorResponseDto.ErrorDetail.builder()
                        .property(e.getField())
                        .message(e.getDefaultMessage())
                        .build())
                .toList();

        return ResponseEntity.badRequest().body(ErrorResponseDto
                .builder()
                .code("INVALID_PAYLOAD")
                .description("One or more attribute of the payload is invalid")
                .details(details)
                .build());
    }

    private ErrorResponseDto composeErrorFromException(MortgageException exception) {
        return ErrorResponseDto.builder()
                .code(exception.getCode())
                .description(exception.getDescription())
                .build();
    }
}
