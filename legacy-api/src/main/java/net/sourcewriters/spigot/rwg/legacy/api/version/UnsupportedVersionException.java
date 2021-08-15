package net.sourcewriters.spigot.rwg.legacy.api.version;

public class UnsupportedVersionException extends RuntimeException {
    private static final long serialVersionUID = 57213891281775896L;

    public UnsupportedVersionException(final Throwable cause, final String message) {
        super(message, cause);
    }

    public UnsupportedVersionException(final Throwable cause) {
        super(cause);
    }

    public UnsupportedVersionException(final String message) {
        super(message);
    }

    public UnsupportedVersionException() {}
}
