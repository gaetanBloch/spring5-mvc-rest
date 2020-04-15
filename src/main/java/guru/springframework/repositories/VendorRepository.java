package guru.springframework.repositories;

import guru.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
