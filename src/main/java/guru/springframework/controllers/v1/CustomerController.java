package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(CustomerController.URL_CUSTOMERS)
public final class CustomerController {
    public static final String URL_CUSTOMERS = "/api/v1/customers";

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return new ResponseEntity<>(
                new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(
                customerService.createNewCustomer(customerDTO), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> saveCustomer(@PathVariable Long id,
                                                    @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(
                customerService.saveCustomer(id, customerDTO), HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
                                                      @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(
                customerService.updateCustomer(id, customerDTO), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
