package ing.exam.mortgage.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * The mortgage request payload
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents a mortgage check request payload")
public class MortgageCheckRequestDto {

    /**
     * The annual income.
     * // TODO: rename this as annualIncome for better meaning? confirm with contract/product
     */
    @Schema(description = "The annual income", requiredMode = REQUIRED)
    @NotNull(message = "income is required")
    @DecimalMin(value = "1.00", message = "The minimum income value is 1.00")
    private BigDecimal income;

    /**
     * The maturity period
     * // TODO: rename to maturityPeriod in years? or replace with @JsonProperty?
     */
    @Schema(description = "The mortgage maturity period in years, refer to the rates endpoint for list of presets", requiredMode = REQUIRED)
    @NotNull(message = "maturityPeriod is required")
    @Min(value = 1, message = "minimum maturityPeriod is 1 year")
    private Integer maturityPeriod;

    /**
     * The total amount of loan
     */
    @Schema(description = "The loan value", requiredMode = REQUIRED)
    @NotNull(message = "loanValue is required")
    @DecimalMin(value = "1.00", message = "loanValue minimum is 1.00")
    private BigDecimal loanValue;

    /**
     * The actual value of the property being purchased
     */
    @Schema(description = "The value of the property/home being purchased", requiredMode = REQUIRED)
    @NotNull(message = "homeValue is required")
    @DecimalMin(value = "1.00", message = "homeValue minimum is 1.00")
    private BigDecimal homeValue;

}
