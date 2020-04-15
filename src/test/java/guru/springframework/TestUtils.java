package guru.springframework;

import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.controllers.v1.VendorController;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
public final class TestUtils {

    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;
    public static final String NAME1 = "foo";
    public static final String NAME2 = "bar";
    public static final String LAST_NAME1 = "baz";
    public static final String LAST_NAME2 = "qux";
    public static final String CUSTOMER_URL = CustomerController.URL_CUSTOMERS + "/" + ID1;
    public static final String VENDOR_URL = VendorController.URL_VENDORS + "/" + ID1;

    private TestUtils() {
        // To prevent instantiation
        throw new UnsupportedOperationException();
    }
}
