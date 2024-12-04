package ing.exam.mortgage.converter;

import java.math.BigDecimal;

import ing.exam.mortgage.dto.MortgageCheckRequestDto;
import ing.exam.mortgage.model.MortgageCheckRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

// TODO plain setup will do since no mocks needed
@ExtendWith(MockitoExtension.class)
public class MortgageCheckRequestConverterTest {

    @InjectMocks
    private MortgageCheckRequestConverter classUnderTest;

    @Test
    public void shouldMapAllFieldsCorrectly() {
        MortgageCheckRequestDto source = MortgageCheckRequestDto.builder()
                .homeValue(new BigDecimal("450000"))
                .maturityPeriod(10)
                .income(new BigDecimal(120000))
                .loanValue(new BigDecimal("300000"))
                .build();

        MortgageCheckRequest result = classUnderTest.convert(source);

        assertThat(result, is(notNullValue()));
        assertThat(result.getLoanValue(), is(comparesEqualTo(new BigDecimal("300000.00"))));
        assertThat(result.getAnnualIncome(), is(comparesEqualTo(new BigDecimal("120000.00"))));
        assertThat(result.getHomeValue(), is(comparesEqualTo(new BigDecimal("450000.00"))));
        assertThat(result.getTermsInYears(), is(10));
    }


    @Test
    public void shouldReturnNUllWhenSourceIsNull() {
        MortgageCheckRequestDto source = null;

        MortgageCheckRequest result = classUnderTest.convert(source);

        assertThat(result, is(nullValue()));
    }


}