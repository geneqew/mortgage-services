package ing.exam.mortgage.dto;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MortgageCheckRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationOnValidRequest() {
        MortgageCheckRequestDto validDto = MortgageCheckRequestDto.builder()
                .income(new BigDecimal("50000"))
                .maturityPeriod(10)
                .loanValue(new BigDecimal("200000"))
                .homeValue(new BigDecimal("250000"))
                .build();

        Set<ConstraintViolation<MortgageCheckRequestDto>> violations = validator.validate(validDto);

        assertTrue(violations.isEmpty(), "Expected no validation errors");
    }

    @Test
    void shouldFailValidationForRequiredFields() {
        MortgageCheckRequestDto invalidDto = MortgageCheckRequestDto.builder().build();

        Set<ConstraintViolation<MortgageCheckRequestDto>> violations = validator.validate(invalidDto);

        assertEquals(4, violations.size(), "Expected validation errors for all fields");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("income is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("maturityPeriod is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("loanValue is required")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("homeValue is required")));
    }

    @Test
    void shouldFailValidationForInvalidValues() {
        MortgageCheckRequestDto invalidDto = MortgageCheckRequestDto.builder()
                .income(new BigDecimal("0.50")) // Less than 1.00
                .maturityPeriod(0)             // Less than 1
                .loanValue(new BigDecimal("0.99")) // Less than 1.00
                .homeValue(new BigDecimal("0.75")) // Less than 1.00
                .build();

        Set<ConstraintViolation<MortgageCheckRequestDto>> violations = validator.validate(invalidDto);

        assertEquals(4, violations.size(), "Expected validation errors for all invalid fields");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("The minimum income value is 1.00")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("minimum maturityPeriod is 1 year")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("loanValue minimum is 1.00")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("homeValue minimum is 1.00")));
    }


}