package ing.exam.mortgage.converter;

import java.math.BigDecimal;

import ing.exam.mortgage.dto.MortgageRateDto;
import ing.exam.mortgage.model.MortgageRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(MockitoExtension.class)
public class MortgageRateConverterTest {

    @InjectMocks
    private MortgageRateConverter classUnderTest;

    @Test
    public void shouldReturnCorrectlyConvertedValue() {
        MortgageRate given = MortgageRate.builder()
                .id(1L)
                .interestRate(new BigDecimal("1.3"))
                .maturityPeriod(10)
                .build();


        MortgageRateDto result = classUnderTest.convert(given);

        assertThat(result, is(notNullValue()));
        assertThat(result.getInterestRate(), is(comparesEqualTo(new BigDecimal("01.30"))));
        assertThat(result.getMaturityPeriod(), is(comparesEqualTo(10)));
    }

    @Test
    public void shouldReturnNullWhenSourceIsNull() {
        MortgageRate given = null;

        MortgageRateDto result = classUnderTest.convert(given);

        assertThat(result, is(nullValue()));
    }

}