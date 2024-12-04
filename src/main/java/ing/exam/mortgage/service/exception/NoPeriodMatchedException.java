package ing.exam.mortgage.service.exception;

/**
 * Exception thrown when we cant find any matching rate for a requested  mortgage application/check
 */
public class NoPeriodMatchedException extends MortgageException {

    public NoPeriodMatchedException() {
        super("NO_MATCHING_MATURITY_PERIOD", "No maturity period matched, refer to rates list");
    }

}
