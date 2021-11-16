package net.sourcewriters.spigot.rwg.legacy.api.data.asset;

import java.io.IOException;

public final class AssetUnavailableException extends RuntimeException {

    private static final long serialVersionUID = 6317343470622479217L;

    public AssetUnavailableException(final String string, final IOException exp) {
        super(string, exp);
    }

}
