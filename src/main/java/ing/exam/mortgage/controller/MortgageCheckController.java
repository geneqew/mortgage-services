package ing.exam.mortgage.controller;

import ing.exam.mortgage.dto.ErrorResponseDto;
import ing.exam.mortgage.dto.MortgageCheckRequestDto;
import ing.exam.mortgage.dto.MortgageCheckResponseDto;
import ing.exam.mortgage.model.MortgageCheckRequest;
import ing.exam.mortgage.model.MortgageCheckResponse;
import ing.exam.mortgage.service.MortgageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource to check for mortgage feasibility
 */
@RestController
@RequestMapping("/api/mortgage-check")
@AllArgsConstructor
@Validated
public class MortgageCheckController {

    /**
     * The mortgage service
     */
    private final MortgageService mortgageService;

    /**
     * The conversion service to convert to and from model to dto
     */
    private final ConversionService conversionService;

    /**
     * Calculate the mortgage cost based on the provided request
     * <p>
     * // TODO check for possibility to revise the contract to make this more REST like (confirm with specs provider); it should not be a verb :)
     *
     * @param request The request payload for mortgage calculation
     * @return Response to indicate if mortgage is feasible given the provided request details
     */
    @Operation(summary = "Check Mortgage",
            description = "Check for mortgage availability based on the provided request details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful mortgage check",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MortgageCheckResponseDto.class))),
            @ApiResponse(responseCode = "4xx", description = "One or more property of the request is invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PostMapping
    public ResponseEntity<?> calculateMortgageCheck(
            @Valid @RequestBody MortgageCheckRequestDto request) {

        MortgageCheckResponse response = mortgageService.checkMortgageRates(conversionService
                .convert(request, MortgageCheckRequest.class));

        return ResponseEntity.ok(conversionService.convert(response, MortgageCheckResponseDto.class));
    }
}
