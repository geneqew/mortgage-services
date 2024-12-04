package ing.exam.mortgage.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MortgageCheckResponse {

    private boolean approved;
    private BigDecimal monthlyCost;

}
