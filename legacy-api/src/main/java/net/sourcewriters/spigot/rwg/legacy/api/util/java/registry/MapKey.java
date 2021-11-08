package net.sourcewriters.spigot.rwg.legacy.api.util.java.registry;

import com.syntaxphoenix.syntaxapi.utils.key.IKey;

public final class MapKey {

    private final IKey key;

    public MapKey(IKey key) {
        this.key = key;
    }

    public IKey getKey() {
        return key;
    }
    
    @Override
    public int hashCode() {
        return key.hash();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof MapKey) {
            obj = ((MapKey) obj).getKey();
        }
        if (!(obj instanceof IKey)) {
            return false;
        }
        return key.isSimilar((IKey) obj);
    }

}
