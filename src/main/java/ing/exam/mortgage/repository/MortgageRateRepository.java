package ing.exam.mortgage.repository;

import java.util.Optional;

import ing.exam.mortgage.model.MortgageRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for mortgage rates
 */
@Repository
public interface MortgageRateRepository
        extends JpaRepository<MortgageRate, Long> {

    /**
     * Retrieve a mortgage rate based on maturity period
     * @param maturityPeriod
     * @return
     */
    Optional<MortgageRate> findByMaturityPeriod(Integer maturityPeriod);

}
