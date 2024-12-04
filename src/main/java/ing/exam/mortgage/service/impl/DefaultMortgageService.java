package ing.exam.mortgage.service.impl;

import java.math.BigDecimal;
import java.util.List;

import ing.exam.mortgage.model.MortgageCheckRequest;
import ing.exam.mortgage.model.MortgageCheckResponse;
import ing.exam.mortgage.model.MortgageRate;
import ing.exam.mortgage.repository.MortgageRateRepository;
import ing.exam.mortgage.service.MortgageService;
import ing.exam.mortgage.service.exception.NoPeriodMatchedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Default implementation for the mortgage service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultMortgageService
        implements MortgageService {

    private final MortgageRateRepository mortgageRateRepository;

    @Override
    public List<MortgageRate> getRates() {
        return mortgageRateRepository.findAll();
    }

    @Override
    public MortgageCheckResponse checkMortgageRates(MortgageCheckRequest checkRequest) {
        // TODO move this to a separate validation
        if (checkRequest.isLoanValueExceeding4xTheIncome() || checkRequest.isLoanValueMoreThanHomeValue()) {
            log.debug("request denied due to unsatisfied request details");
            return MortgageCheckResponse.builder().approved(false).build();
        }

        BigDecimal monthlyCost = mortgageRateRepository.findByMaturityPeriod(checkRequest.getTermsInYears())
                .map(rate -> rate.calculateMonthlyCost(checkRequest))
                .orElseThrow(NoPeriodMatchedException::new);

        return MortgageCheckResponse.builder().approved(true).monthlyCost(monthlyCost).build();
    }

}
