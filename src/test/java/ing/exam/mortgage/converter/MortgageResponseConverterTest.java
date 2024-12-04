package ing.exam.mortgage.converter;

import java.math.BigDecimal;

import ing.exam.mortgage.dto.MortgageCheckResponseDto;
import ing.exam.mortgage.model.MortgageCheckResponse;
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
public class MortgageResponseConverterTest {

    @InjectMocks
    private MortgageResponseConverter classUnderTests;

    @Test
    public void shouldReturnNullWhenSourceIsNull() {
        MortgageCheckResponse given = null;

        MortgageCheckResponseDto result = classUnderTests.convert(given);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void shouldReturnMatchingResponse() {
        MortgageCheckResponse given = MortgageCheckResponse.builder()
                .approved(true)
                .monthlyCost(new BigDecimal("800"))
                .build();

        MortgageCheckResponseDto result = classUnderTests.convert(given);

        assertThat(result, is(notNullValue()));
        assertThat(result.getFeasible(), is(true));
        assertThat(result.getMonthlyCost(), is(comparesEqualTo(new BigDecimal("800.00"))));
    }

}