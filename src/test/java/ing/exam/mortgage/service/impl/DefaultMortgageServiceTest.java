package ing.exam.mortgage.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ing.exam.mortgage.model.MortgageCheckRequest;
import ing.exam.mortgage.model.MortgageCheckResponse;
import ing.exam.mortgage.model.MortgageRate;
import ing.exam.mortgage.repository.MortgageRateRepository;
import ing.exam.mortgage.service.exception.NoPeriodMatchedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultMortgageServiceTest {

    @InjectMocks
    private DefaultMortgageService classUnderTest;

    @Mock
    private MortgageRateRepository mortgageRateRepository;

    @Captor
    private ArgumentCaptor<Integer> maturityPeriodCaptor;

    @Test
    public void getRatesDelegatesRetrievalFromRepository() {
        when(mortgageRateRepository.findAll())
                .thenReturn(List.of(
                        MortgageRate.builder().id(1L)
                                .interestRate(new BigDecimal("1.2"))
                                .maturityPeriod(10).lastUpdate(LocalDateTime.now())
                                .build(),
                        MortgageRate.builder().id(2L)
                                .interestRate(new BigDecimal("1.5"))
                                .maturityPeriod(25).lastUpdate(LocalDateTime.now())
                                .build()));

        List<MortgageRate> rates = classUnderTest.getRates();

        verify(mortgageRateRepository, times(1)).findAll();
        assertThat(rates, is(notNullValue()));
        assertThat(rates.size(), is(2));
    }

    @Test
    public void checkMortgageIsDisapprovedIfLoadValueExceeds4xIncome() {
        MortgageCheckRequest requesWithExceedingIncome = MortgageCheckRequest.builder()
                .annualIncome(new BigDecimal("100000.00"))
                .termsInYears(10)
                .loanValue(new BigDecimal("9000000.00"))
                .homeValue(new BigDecimal("3000000.00"))
                .build();

        MortgageCheckResponse response = classUnderTest.checkMortgageRates(requesWithExceedingIncome);

        verify(mortgageRateRepository, never()).findByMaturityPeriod(anyInt());
        assertThat(response, is(notNullValue()));
        assertThat(response.isApproved(), is(false));
    }

    @Test
    public void checkMortgageIsDisapprovedIfLoanValueExceedsHomeValue() {
        MortgageCheckRequest requestWithExceedingHomeValue = MortgageCheckRequest.builder()
                .annualIncome(new BigDecimal("100000.00"))
                .termsInYears(10)
                .loanValue(new BigDecimal("300000.00"))
                .homeValue(new BigDecimal("250000.00"))
                .build();

        MortgageCheckResponse response = classUnderTest.checkMortgageRates(requestWithExceedingHomeValue);

        verify(mortgageRateRepository, never()).findByMaturityPeriod(anyInt());
        assertThat(response, is(notNullValue()));
        assertThat(response.isApproved(), is(false));
    }

    @Test
    public void exceptionIsThrownOnNoMatchingRate() {
        MortgageCheckRequest requestWithNonMatchingRate = MortgageCheckRequest.builder()
                .annualIncome(new BigDecimal("100000.00"))
                .termsInYears(100)
                .loanValue(new BigDecimal("250000.00"))
                .homeValue(new BigDecimal("300000.00"))
                .build();

        when(mortgageRateRepository.findByMaturityPeriod(anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(NoPeriodMatchedException.class,
                () -> classUnderTest.checkMortgageRates(requestWithNonMatchingRate));

        verify(mortgageRateRepository, times(1)).findByMaturityPeriod(anyInt());
    }

    @Test
    public void shouldReturnResponseOnValidRequest() {
        MortgageCheckRequest requestWithNonMatchingRate = MortgageCheckRequest.builder()
                .annualIncome(new BigDecimal("100000.00"))
                .termsInYears(10)
                .loanValue(new BigDecimal("250000.00"))
                .homeValue(new BigDecimal("300000.00"))
                .build();
        when(mortgageRateRepository.findByMaturityPeriod(eq(10)))
                .thenReturn(Optional.of(MortgageRate
                        .builder()
                        .id(1L)
                        .interestRate(new BigDecimal("3.5"))
                        .build()
                ));

        MortgageCheckResponse response = classUnderTest.checkMortgageRates(requestWithNonMatchingRate);

        verify(mortgageRateRepository, times(1))
                .findByMaturityPeriod(maturityPeriodCaptor.capture());
        assertThat(response, is(notNullValue()));
        assertThat(response.isApproved(), is(true));
        assertThat(response.getMonthlyCost(), comparesEqualTo(new BigDecimal("2472.62")));
        assertThat(maturityPeriodCaptor.getValue(), comparesEqualTo(10));
    }

}