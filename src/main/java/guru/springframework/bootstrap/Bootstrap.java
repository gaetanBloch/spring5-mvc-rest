package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = Category.builder().name("Fruits").build();
        Category dried = Category.builder().name("Dried").build();
        Category fresh = Category.builder().name("Fresh").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category nuts = Category.builder().name("Nuts").build();

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.debug("Categories Loaded, count = " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer john = Customer.builder().firstName("John").lastName("Doe").build();
        Customer jane = Customer.builder().firstName("Jane").lastName("Doe").build();

        customerRepository.save(john);
        customerRepository.save(jane);

        log.debug("Customers Loaded, count = " + customerRepository.count());
    }

    private void loadVendors() {
        Vendor fruits = Vendor.builder().name("Fun Fresh Fruits Ltd.").build();
        Vendor nuts = Vendor.builder().name("Nuts for Nuts Company").build();
        Vendor vegetables = Vendor.builder().name("Vegies A GoGo").build();

        vendorRepository.save(fruits);
        vendorRepository.save(nuts);
        vendorRepository.save(vegetables);

        log.debug("Vendors Loaded, count = " + vendorRepository.count());
    }
}
