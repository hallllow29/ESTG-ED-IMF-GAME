package entities;

/**
 * Exception thrown to indicate that a backpack is full
 * and cannot accommodate additional items.
 *
 * This exception extends the {@code RuntimeException},
 * implying that it is an unchecked exception.
 */
public class BackPackFullException extends RuntimeException {

    /**
     * Constructs a new {@code BackPackFullException} with the specified detail message.
     *
     * @param message the detail message that provides more information about the exception.
     */
    public BackPackFullException(String message) {
        super(message);
    }
}
