package ing.exam.mortgage.controller;

import java.util.List;

import ing.exam.mortgage.converter.MortgageRateConverter;
import ing.exam.mortgage.dto.MortgageRateDto;
import ing.exam.mortgage.service.MortgageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for interest rates. This class is intended for
 * interest rate related operations.
 * TODO: create a feign client or something similar for easier integration adn testing
 */
@RestController
@RequestMapping("/api/interest-rates")
@Slf4j
@RequiredArgsConstructor
public class InterestRatesController {

    /**
     * The mortgage service
     */
    private final MortgageService mortgageService;

    /**
     * The converter from model to dto
     */
    private final MortgageRateConverter mortgageRateConverter;

    /**
     * Retrieve the list of all interest rates
     *
     * @return The interest rates
     */
    @Operation(summary = "Get Interest Rates",
            description = "Retrieve the list of supported interest rates")
    @ApiResponse(responseCode = "200",
            description = "Successfully Retrieve all supported interest rates",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MortgageRateDto.class))))
    @GetMapping
    public ResponseEntity<?> getInterestRates() {
        List<MortgageRateDto> rates = mortgageService.getRates()
                .stream()
                .map(mortgageRateConverter::convert)
                .toList();
        return ResponseEntity.ok(rates);
    }

}
