package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
