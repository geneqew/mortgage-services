package ing.exam.mortgage.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The mortgage Response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response when executing a mortgage check")
public class MortgageCheckResponseDto {

    /**
     * Flag to indicate if the mortgage request is feasible
     */
    @Schema(description = "Identifies if the request satisfies the mortgage requirements")
    private Boolean feasible;

    /**
     * The monthly cost of the mortgage if approved
     */
    @Schema(description = "The monthly payment for the mortgage. Please note that this field is null when mortgage is not feasible")
    private BigDecimal monthlyCost;

}
