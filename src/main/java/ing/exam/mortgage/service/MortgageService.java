package ing.exam.mortgage.service;

import java.util.List;

import ing.exam.mortgage.model.MortgageCheckRequest;
import ing.exam.mortgage.model.MortgageCheckResponse;
import ing.exam.mortgage.model.MortgageRate;

/**
 * The mortgage service containing the basic services
 * intended for mortgage related operations
 */
public interface MortgageService {

    /**
     * Retrieve the list of predefined mortgage rates
     *
     * @return The list of rates
     */
    List<MortgageRate> getRates();


    /**
     * Check the mortgage rates based on the provided request
     *
     * @param checkRequest The request
     * @return The response
     */
    MortgageCheckResponse checkMortgageRates(MortgageCheckRequest checkRequest);

}
