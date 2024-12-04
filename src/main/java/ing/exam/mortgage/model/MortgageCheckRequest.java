package ing.exam.mortgage.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageCheckRequest {

    private BigDecimal annualIncome;
    private Integer termsInYears;
    private BigDecimal loanValue;
    private BigDecimal homeValue;

    public boolean isLoanValueExceeding4xTheIncome() {
        BigDecimal incomeX4 = getAnnualIncome().multiply(BigDecimal.valueOf(4));
        return getLoanValue().compareTo(incomeX4) > 0;
    }

    public boolean isLoanValueMoreThanHomeValue() {
        return getLoanValue().compareTo(getHomeValue()) > 0;
    }

}
