package net.sourcewriters.spigot.rwg.legacy.api.block.state;

import org.bukkit.block.structure.StructureRotation;

import net.sourcewriters.spigot.rwg.legacy.api.util.annotation.source.NonNull;

public enum Rotation {

    NORTH(StructureRotation.NONE, -1, 1, false),
    EAST(StructureRotation.CLOCKWISE_90, -1, 1, true),
    SOUTH(StructureRotation.CLOCKWISE_180, 1, -1, false),
    WEST(StructureRotation.COUNTERCLOCKWISE_90, 1, -1, true);

    private final StructureRotation structure;
    private final int left, right;
    private final boolean axisZ;

    Rotation(final StructureRotation structure, final int left, final int right, final boolean axisZ) {
        this.structure = structure;
        this.left = left;
        this.right = right;
        this.axisZ = axisZ;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public boolean isAxisZ() {
        return axisZ;
    }

    @NonNull
    public StructureRotation asStructure() {
        return structure;
    }

    @NonNull
    public static Rotation fromStructure(final StructureRotation structure) {
        switch (structure) {
        case CLOCKWISE_90:
            return EAST;
        case CLOCKWISE_180:
            return SOUTH;
        case COUNTERCLOCKWISE_90:
            return WEST;
        default:
            return NORTH;
        }
    }

    @NonNull
    public static Rotation fromString(final String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ignore) {
            return NORTH;
        }
    }

}