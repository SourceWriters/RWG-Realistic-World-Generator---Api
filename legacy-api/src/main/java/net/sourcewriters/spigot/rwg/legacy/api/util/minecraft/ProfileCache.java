package net.sourcewriters.spigot.rwg.legacy.api.util.minecraft;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.syntaxphoenix.syntaxapi.random.Keys;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public final class ProfileCache {

    public static final String SIGNATURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    public static final int SIGNATURE_LENGTH = SIGNATURE.length();
    private static final ConcurrentHashMap<String, GameProfile> PROFILE_CACHE = new ConcurrentHashMap<>();

    private ProfileCache() {}

    @NonNull
    public static GameProfile asProfile(@NonNull final String texture) {
        return PROFILE_CACHE.computeIfAbsent(texture, ProfileCache::buildProfile);
    }

    public static String asTexture(@NonNull final GameProfile profile) {
        final Collection<Property> property = profile.getProperties().get("textures");
        if (property.size() == 0) {
            return null;
        }
        return asShortTexture(property.iterator().next().getValue());
    }

    @NonNull
    public static String asFullTexture(@NonNull final String texture) {
        return texture.startsWith(SIGNATURE) ? texture : SIGNATURE + texture;
    }

    @NonNull
    public static String asShortTexture(@NonNull final String texture) {
        return texture.startsWith(SIGNATURE) ? texture.substring(SIGNATURE_LENGTH) : texture;
    }

    @NonNull
    private static GameProfile buildProfile(@NonNull final String texture) {
        final String value = texture.startsWith(SIGNATURE) ? texture : SIGNATURE + texture;
        final GameProfile profile = new GameProfile(UUID.randomUUID(), Keys.generateKey(12));
        profile.getProperties().put("textures", new Property("textures", value));
        return profile;
    }

}
