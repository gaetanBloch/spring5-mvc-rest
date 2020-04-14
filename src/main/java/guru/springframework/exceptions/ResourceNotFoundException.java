package guru.springframework.exceptions;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
