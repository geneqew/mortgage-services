package ing.exam.mortgage.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortgageRate {

    /**
     * Entity ID as unique identifier
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private Long version;

    /**
     * The maturity period in years
     * TODO: is maturity period unique? if it is we can use it as id
     */
    @Column(unique = true, nullable = false)
    private Integer maturityPeriod;

    /**
     * The interest rate at 2 decimal places precision
     */
    private BigDecimal interestRate;

    /**
     * The last update for this rate
     */
    private LocalDateTime lastUpdate;


    /**
     * Calculate the monthly cost using this rate given the mortgage check request
     *
     * @param request The request
     * @return The monthly rate
     */
    public BigDecimal calculateMonthlyCost(MortgageCheckRequest request) {
        BigDecimal loanAmount = request.getLoanValue();
        BigDecimal annualInterestRate = getInterestRate().divide(valueOf(100), 5, HALF_UP);
        BigDecimal monthlyInterestRate = annualInterestRate.divide(valueOf(12), 5, HALF_UP);

        int totalPayments = request.getTermsInYears() * 12;

        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            return loanAmount.divide(valueOf(totalPayments), 2, HALF_UP);
        }

        BigDecimal onePlusRToThePowerOfN = monthlyInterestRate.add(BigDecimal.ONE).pow(totalPayments);
        BigDecimal numerator = monthlyInterestRate.multiply(onePlusRToThePowerOfN);
        BigDecimal denominator = onePlusRToThePowerOfN.subtract(BigDecimal.ONE);

        return loanAmount.multiply(numerator).divide(denominator, 2, HALF_UP);
    }

}
