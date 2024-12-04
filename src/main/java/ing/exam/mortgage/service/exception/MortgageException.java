package ing.exam.mortgage.service.exception;

import lombok.Getter;

@Getter
public abstract class MortgageException extends RuntimeException {

    private final String code;
    private final String description;

    protected MortgageException(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
