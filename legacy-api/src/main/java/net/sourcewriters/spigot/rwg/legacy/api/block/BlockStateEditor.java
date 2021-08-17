package net.sourcewriters.spigot.rwg.legacy.api.block;

import java.util.HashMap;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Function;

import net.sourcewriters.spigot.rwg.legacy.api.block.state.Rotation;
import net.sourcewriters.spigot.rwg.legacy.api.util.Checks;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.Unsafe;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe.UnsafeStatus;

@Unsafe(status = UnsafeStatus.SUBJECT_TO_CHANGE, useable = true)
public class BlockStateEditor {

    private final HashMap<String, String> states = new HashMap<>();
    private final String namespace;
    private String id;

    private final String properties;

    private BlockStateEditor(@NonNull String blockdata) {
        String[] parts = Objects.requireNonNull(blockdata, "String blockdata can't be null!").split(":", 2);
        namespace = parts[0];
        if (parts[1].contains("[")) {
            parts = parts[1].split("\\[", 2);
            id = parts[0];
            String[] tmp = parts[1].split("\\]", 2);
            properties = !tmp[1].isBlank() ? tmp[1] : null;
            parts = tmp[0].split(",");
            for (String part : parts) {
                String[] state = part.trim().split("=");
                states.put(state[0], state[1]);
            }
            return;
        }
        if (parts[1].contains("?")) {
            parts = parts[1].split("\\?", 2);
            id = parts[0];
            properties = '?' + parts[1];
            return;
        }
        id = parts[1];
        properties = null;
    }

    public String getProperties() {
        return properties;
    }

    public boolean hasProperties() {
        return properties != null;
    }

    @NonNull
    public String getNamespace() {
        return namespace;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public HashMap<String, String> getStates() {
        return states;
    }

    @NonNull
    public BlockStateEditor rename(@NonNull String name) {
        this.id = Objects.requireNonNull(name, "String name can't be null!").toLowerCase();
        return this;
    }

    @NonNull
    public BlockStateEditor map(@NonNull Function<String, String> mapper, @NonNull String... states) {
        Objects.requireNonNull(mapper, "String name can't be null!");
        Checks.isNotNullOrEmpty(states, "String[] states");
        for (String state : states) {
            this.states.computeIfPresent(state, (key, value) -> mapper.apply(value));
        }
        return this;
    }

    @NonNull
    public BlockStateEditor put(@NonNull String name, String state) {
        Objects.requireNonNull(name, "String name can't be null!");
        if (state == null) {
            return remove(name);
        }
        states.put(name, state);
        return this;
    }

    @NonNull
    public BlockStateEditor apply(@NonNull Rotation rotation) {
        return put("facing", Objects.requireNonNull(rotation, "Rotation can't be null!").name().toLowerCase());
    }

    public Rotation getRotation() {
        return has("facing") ? Rotation.fromString(get("facing")) : null;
    }

    @NonNull
    public boolean has(String state) {
        return states.containsKey(state);
    }

    public String get(String state) {
        return states.get(state);
    }

    @NonNull
    public BlockStateEditor remove(String state) {
        states.remove(state);
        return this;
    }

    @NonNull
    private String statesAsString() {
        StringBuilder builder = new StringBuilder("[");
        for (Entry<String, String> entry : states.entrySet()) {
            builder.append(entry.getKey()).append('=').append(entry.getValue()).append(", ");
        }
        return builder.substring(0, builder.length() - 2) + ']';
    }

    @NonNull
    public String asBlockData() {
        if (states.isEmpty()) {
            return namespace + ':' + id;
        }
        return namespace + ':' + id + statesAsString();
    }

    @NonNull
    public static BlockStateEditor of(@NonNull String value) {
        return new BlockStateEditor(Objects.requireNonNull(value));
    }

    @NonNull
    public static BlockStateEditor of(@NonNull IBlockData data) {
        return Objects.requireNonNull(data).asEditor();
    }

}
