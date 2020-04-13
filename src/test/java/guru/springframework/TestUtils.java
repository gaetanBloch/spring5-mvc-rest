package guru.springframework;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
public final class TestUtils {

    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;
    public static final String NAME1 = "foo";
    public static final String NAME2 = "bar";

    private TestUtils() {
        // To prevent instantiation
        throw new UnsupportedOperationException();
    }
}
