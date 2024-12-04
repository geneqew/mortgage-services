package ing.exam.mortgage.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The mortgage rate data transfer objects whose
 * purpose is to represent the current mortgage rates
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "The mortgage rate defined by the period, interest and when was it last updated")
public class MortgageRateDto {

    /**
     * The maturity period in years
     */
    @Schema(description = "The maturity period in years")
    private Integer maturityPeriod;

    /**
     * The interest rates in
     */
    @Schema(description = "The interest rate for this mortgage")
    private BigDecimal interestRate;

    /**
     * The last date time rate was updated
     */
    @Schema(description = "The date and time this rate has been updated")
    private LocalDateTime lastUpdate;
}
