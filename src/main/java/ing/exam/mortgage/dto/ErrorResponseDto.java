package ing.exam.mortgage.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Response containing the error details")
public class ErrorResponseDto {

    @Schema(description = "The error code", example = "NO_PERIOD_MATCHED")
    private String code;
    @Schema(description = "The error description", example = "No maturity period matched, refer to rates list")
    private String description;
    @Schema(description = "Provides more information of the error")
    private List<ErrorDetail> details;

    @Data
    @Builder
    @AllArgsConstructor
    public static class ErrorDetail {

        @Schema(description = "The field name or property that is either missing or has error")
        private String property;
        @Schema(description = "The error associated to the field that has an error")
        private String message;

    }
}
