package ing.exam.mortgage.controller;

import java.math.BigDecimal;

import ing.exam.mortgage.dto.ErrorResponseDto;
import ing.exam.mortgage.dto.MortgageCheckRequestDto;
import ing.exam.mortgage.dto.MortgageCheckResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MortgageCheckControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    private String url;
    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        url = "http://localhost:" + port + "/api/mortgage-check";

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    private ResponseEntity<MortgageCheckResponseDto> executeRequestWithExpectedSuccess(MortgageCheckRequestDto request) {
        HttpEntity<MortgageCheckRequestDto> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                MortgageCheckResponseDto.class);
    }

    private ResponseEntity<ErrorResponseDto> executeRequestWithExpectedFailure(MortgageCheckRequestDto request) {
        HttpEntity<MortgageCheckRequestDto> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ErrorResponseDto.class);
    }

    @Test
    public void shouldReturnCorrectlyResponseOnValidRequest() throws Exception {
        MortgageCheckRequestDto requestDto = MortgageCheckRequestDto.builder()
                .income(new BigDecimal("120000"))
                .maturityPeriod(10)
                .loanValue(new BigDecimal("250000"))
                .homeValue(new BigDecimal("300000"))
                .build();

        ResponseEntity<MortgageCheckResponseDto> responseEntity = executeRequestWithExpectedSuccess(requestDto);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        assertThat(responseEntity.getBody().getFeasible(), is(true));
        assertThat(responseEntity.getBody().getMonthlyCost(), is(comparesEqualTo(new BigDecimal("2397.44"))));
    }

    @Test
    public void shouldResultToErrorOnMissingFields() {
        MortgageCheckRequestDto requestDto = MortgageCheckRequestDto.builder()
                .income(null) //no income
                .maturityPeriod(10)
                .loanValue(null) // no load value that is required
                .homeValue(new BigDecimal("300000"))
                .build();

        ResponseEntity<ErrorResponseDto> responseEntity = executeRequestWithExpectedFailure(requestDto);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(responseEntity.getBody().getCode(), is("INVALID_PAYLOAD"));
        assertThat(responseEntity.getBody().getDetails(), hasSize(2));
    }

    @Test
    public void shouldResultToErrorOnNoAvailableRate() {
        MortgageCheckRequestDto requestDto = MortgageCheckRequestDto.builder()
                .income(new BigDecimal("120000"))
                .maturityPeriod(150) // this does not exist
                .loanValue(new BigDecimal("250000"))
                .homeValue(new BigDecimal("300000"))
                .build();

        ResponseEntity<ErrorResponseDto> responseEntity = executeRequestWithExpectedFailure(requestDto);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(responseEntity.getBody().getCode(), is("NO_MATCHING_MATURITY_PERIOD"));
    }


}