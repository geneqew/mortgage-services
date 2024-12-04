package ing.exam.mortgage.converter;

import java.util.Optional;

import ing.exam.mortgage.dto.MortgageCheckRequestDto;
import ing.exam.mortgage.model.MortgageCheckRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MortgageCheckRequestConverter
        implements Converter<MortgageCheckRequestDto, MortgageCheckRequest> {

    @Override
    public MortgageCheckRequest convert(MortgageCheckRequestDto source) {
        return Optional.ofNullable(source)
                .map(request -> MortgageCheckRequest.builder()
                        .annualIncome(request.getIncome())
                        .termsInYears(request.getMaturityPeriod())
                        .loanValue(request.getLoanValue())
                        .homeValue(request.getHomeValue())
                        .build())
                .orElse(null);
    }
}
