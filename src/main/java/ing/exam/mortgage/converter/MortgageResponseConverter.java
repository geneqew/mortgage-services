package ing.exam.mortgage.converter;

import java.math.RoundingMode;
import java.util.Optional;

import ing.exam.mortgage.dto.MortgageCheckResponseDto;
import ing.exam.mortgage.model.MortgageCheckResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MortgageResponseConverter
        implements Converter<MortgageCheckResponse, MortgageCheckResponseDto> {

    @Override
    public MortgageCheckResponseDto convert(MortgageCheckResponse source) {
        return Optional.ofNullable(source)
                .map(response -> MortgageCheckResponseDto
                        .builder()
                        .feasible(response.isApproved())
                        .monthlyCost(source.getMonthlyCost().setScale(2, RoundingMode.HALF_UP))
                        .build())
                .orElse(null);
    }
}
