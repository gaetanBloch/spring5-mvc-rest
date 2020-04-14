package guru.springframework.repositories;

import guru.springframework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
