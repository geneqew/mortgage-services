package ing.exam.mortgage.converter;

import java.util.Optional;

import ing.exam.mortgage.dto.MortgageRateDto;
import ing.exam.mortgage.model.MortgageRate;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.math.RoundingMode.HALF_UP;

@Component
public class MortgageRateConverter
        implements Converter<MortgageRate, MortgageRateDto> {

    @Override
    public MortgageRateDto convert(MortgageRate source) {
        return Optional.ofNullable(source)
                .map(rate -> MortgageRateDto.builder()
                        .maturityPeriod(rate.getMaturityPeriod())
                        .lastUpdate(rate.getLastUpdate())
                        .interestRate(rate.getInterestRate().setScale(2, HALF_UP))
                        .build())
                .orElse(null);
    }
}
