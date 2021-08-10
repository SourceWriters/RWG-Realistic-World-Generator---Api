package net.sourcewriters.spigot.rwg.legacy.api.util.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import com.syntaxphoenix.syntaxapi.json.JsonArray;
import com.syntaxphoenix.syntaxapi.json.JsonEntry;
import com.syntaxphoenix.syntaxapi.json.JsonObject;
import com.syntaxphoenix.syntaxapi.json.JsonValue;
import com.syntaxphoenix.syntaxapi.json.ValueType;
import com.syntaxphoenix.syntaxapi.json.io.JsonParser;
import com.syntaxphoenix.syntaxapi.json.value.JsonBigDecimal;
import com.syntaxphoenix.syntaxapi.json.value.JsonBigInteger;
import com.syntaxphoenix.syntaxapi.json.value.JsonByte;
import com.syntaxphoenix.syntaxapi.json.value.JsonDouble;
import com.syntaxphoenix.syntaxapi.json.value.JsonFloat;
import com.syntaxphoenix.syntaxapi.json.value.JsonInteger;
import com.syntaxphoenix.syntaxapi.json.value.JsonLong;
import com.syntaxphoenix.syntaxapi.json.value.JsonNull;
import com.syntaxphoenix.syntaxapi.json.value.JsonShort;
import com.syntaxphoenix.syntaxapi.json.value.JsonString;
import com.syntaxphoenix.syntaxapi.nbt.NbtBigDecimal;
import com.syntaxphoenix.syntaxapi.nbt.NbtBigInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtByte;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtDouble;
import com.syntaxphoenix.syntaxapi.nbt.NbtFloat;
import com.syntaxphoenix.syntaxapi.nbt.NbtInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtLong;
import com.syntaxphoenix.syntaxapi.nbt.NbtShort;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;

import net.sourcewriters.spigot.rwg.legacy.api.data.property.IProperties;
import net.sourcewriters.spigot.rwg.legacy.api.data.property.IProperty;
import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public final class JsonIO {

    private static final JsonParser PARSER = new JsonParser();

    private JsonIO() {}

    @NonNull
    public static JsonValue<?> parse(@NonNull String string) {
        Objects.requireNonNull(string, "String can't be null!");
        try {
            return PARSER.fromString(string);
        } catch (IOException e) {
            return JsonNull.get();
        }
    }

    public static JsonObject parseProperties(@NonNull String string) {
        Objects.requireNonNull(string, "String can't be null!");
        if (string.charAt(0) == '?') {
            string = string.substring(1);
        }
        JsonValue<?> value = parse(string);
        if (!value.hasType(ValueType.OBJECT)) {
            return null;
        }
        return (JsonObject) value;
    }

    @NonNull
    public static JsonObject toJson(@NonNull IProperties properties) {
        Objects.requireNonNull(properties, "IProperties can't be null!");
        IProperty<?>[] array = properties.asArray();
        JsonObject object = new JsonObject();
        for (IProperty<?> property : array) {
            JsonValue<?> value = JsonValue.fromPrimitive(property.getValue());
            if (value.hasType(ValueType.NULL)) {
                continue;
            }
            object.set(property.getKey(), value);
        }
        return object;
    }

    @NonNull
    public static IProperties fromJson(@NonNull JsonObject object) {
        Objects.requireNonNull(object, "JsonObject can't be null!");
        IProperties properties = IProperties.create();
        fromJson(properties, object);
        return properties;
    }

    public static void fromJson(@NonNull IProperties properties, @NonNull JsonObject object) {
        Objects.requireNonNull(properties, "IProperties can't be null!");
        Objects.requireNonNull(object, "JsonObject can't be null!");
        properties.clear();
        for (JsonEntry<?> entry : object) {
            JsonValue<?> value = entry.getValue();
            if (value == null) {
                continue;
            }
            properties.set(IProperty.of(entry.getKey(), value.getValue()));
        }
    }

    @NonNull
    public static String toString(@NonNull IProperties properties) {
        return '?' + toJson(properties).toString();
    }

    @NonNull
    public static IProperties fromString(@NonNull String string) {
        IProperties properties = IProperties.create();
        fromString(properties, string);
        return properties;
    }

    public static void fromString(@NonNull IProperties properties, @NonNull String string) {
        Objects.requireNonNull(properties, "IProperties can't be null!");
        Objects.requireNonNull(string, "String can't be null!");
        if (string.charAt(0) == '?') {
            string = string.substring(1);
        }
        JsonValue<?> value = parse(string);
        if (!value.hasType(ValueType.OBJECT)) {
            properties.clear();
            return;
        }
        fromJson(properties, (JsonObject) value);
    }

    public static NbtTag toNbt(JsonValue<?> value) {
        if (value == null) {
            return null;
        }
        switch (value.getType()) {
        case BIG_DECIMAL:
            return new NbtBigDecimal((BigDecimal) value.getValue());
        case BIG_INTEGER:
            return new NbtBigInt((BigInteger) value.getValue());
        case BOOLEAN:
            return new NbtByte((byte) (((boolean) value.getValue()) ? 1 : 0));
        case BYTE:
            return new NbtByte((byte) value.getValue());
        case DOUBLE:
            return new NbtDouble((double) value.getValue());
        case FLOAT:
            return new NbtFloat((float) value.getValue());
        case INTEGER:
            return new NbtInt((int) value.getValue());
        case LONG:
            return new NbtLong((long) value.getValue());
        case SHORT:
            return new NbtShort((short) value.getValue());
        case STRING:
            return new NbtString((String) value.getValue());
        case OBJECT:
            NbtCompound compound = new NbtCompound();
            JsonObject object = (JsonObject) value;
            for (JsonEntry<?> entry : object) {
                NbtTag tag = toNbt(entry.getValue());
                if (tag == null) {
                    continue;
                }
                compound.set(entry.getKey(), tag);
            }
            return compound;
        case ARRAY:
            JsonArray array = (JsonArray) value;
            int size = array.size();
            if (size == 0) {
                return new NbtList<>();
            }
            NbtCompound compound0 = new NbtCompound();
            int idx = 0;
            int types = 0;
            NbtType type = null;
            for (int index = 0; index < size; index++) {
                NbtTag tag = toNbt(array.get(index));
                if (tag == null) {
                    continue;
                }
                if (tag.getType() != type) {
                    types++;
                    type = tag.getType();
                }
                compound0.set((idx++) + "", tag);
            }

            if (types == 1) {
                NbtList<?> list = new NbtList<>();
                for (int index = 0; index < idx; index++) {
                    list.addTag(compound0.get("" + index));
                }
                return list;
            }
            return compound0;
        default:
            return null;
        }
    }

    @NonNull
    public static JsonValue<?> fromNbt(NbtTag tag) {
        if (tag == null) {
            return JsonNull.get();
        }
        switch (tag.getType()) {
        case BYTE:
            return new JsonByte((byte) tag.getValue());
        case BYTE_ARRAY:
            JsonArray array = new JsonArray();
            byte[] bytes = (byte[]) tag.getValue();
            for (byte value : bytes) {
                array.add(new JsonByte(value));
            }
            return array;
        case COMPOUND:
            NbtCompound compound = (NbtCompound) tag;
            if (compound.hasKey("ARRAY", NbtType.BYTE) && compound.getBoolean("ARRAY")) {
                JsonArray array0 = new JsonArray();
                int size = compound.getKeys().size();
                for (int index = 0; index < size; index++) {
                    if (!compound.hasKey("" + index)) {
                        break;
                    }
                    array0.add(fromNbt(compound.get("" + index)));
                }
                return array0;
            }
            JsonObject object = new JsonObject();
            for (String key : compound.getKeys()) {
                NbtTag current = compound.get(key);
                if (current == null) {
                    continue;
                }
                object.set(key, fromNbt(current));
            }
            return object;
        case DOUBLE:
            return new JsonDouble((double) tag.getValue());
        case FLOAT:
            return new JsonFloat((float) tag.getValue());
        case INT:
            return new JsonInteger((int) tag.getValue());
        case INT_ARRAY:
            JsonArray array0 = new JsonArray();
            int[] ints = (int[]) tag.getValue();
            for (int value : ints) {
                array0.add(new JsonInteger(value));
            }
            return array0;
        case LIST:
            JsonArray array1 = new JsonArray();
            NbtList<?> list = (NbtList<?>) tag;
            for (NbtTag value : list) {
                array1.add(fromNbt(value));
            }
            return array1;
        case LONG:
            return new JsonLong((long) tag.getValue());
        case LONG_ARRAY:
            JsonArray array2 = new JsonArray();
            long[] longs = (long[]) tag.getValue();
            for (long value : longs) {
                array2.add(new JsonLong(value));
            }
            return array2;
        case SHORT:
            return new JsonShort((short) tag.getValue());
        case STRING:
            if (tag instanceof NbtBigInt) {
                return new JsonBigInteger(((NbtBigInt) tag).getInteger());
            }
            if (tag instanceof NbtBigDecimal) {
                return new JsonBigDecimal(((NbtBigDecimal) tag).getDecimal());
            }
            return new JsonString((String) tag.getValue());
        default:
            return JsonNull.get();
        }
    }

}
