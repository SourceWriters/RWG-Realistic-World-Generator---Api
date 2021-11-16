package net.sourcewriters.spigot.rwg.legacy.api.compatibility;

public class AddonInitializationException extends RuntimeException {

    static final long serialVersionUID = -3387516993124229948L;

    /**
     * Constructs a new AddonInitializationException with {@code null} as its detail
     * message. The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     */
    public AddonInitializationException() {}

    /**
     * Constructs a new AddonInitializationException with the specified detail
     * message. The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later
     *                    retrieval by the {@link #getMessage()} method.
     */
    public AddonInitializationException(final String message) {
        super(message);
    }

    /**
     * Constructs a new AddonInitializationException with the specified detail
     * message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i>
     * automatically incorporated in this AddonInitializationException detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                    {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                    {@link #getCause()} method). (A <tt>null</tt> value is
     *                    permitted, and indicates that the cause is nonexistent or
     *                    unknown.)
     * 
     * @since         1.4
     */
    public AddonInitializationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AddonInitializationException with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>). This
     * constructor is useful for AddonInitializationExceptions that are little more
     * than wrappers for other throwables (for example,
     * {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *                  {@link #getCause()} method). (A <tt>null</tt> value is
     *                  permitted, and indicates that the cause is nonexistent or
     *                  unknown.)
     * 
     * @since       1.4
     */
    public AddonInitializationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new AddonInitializationException with the specified detail
     * message, cause, suppression enabled or disabled, and writable stack trace
     * enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause. (A {@code null} value is permitted, and
     *                               indicates that the cause is nonexistent or
     *                               unknown.)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     * 
     * @since                    1.7
     */
    protected AddonInitializationException(final String message, final Throwable cause, final boolean enableSuppression,
        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
