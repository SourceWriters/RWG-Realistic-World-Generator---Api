package net.sourcewriters.spigot.rwg.legacy.api.trade;

import org.bukkit.entity.Villager;

import net.sourcewriters.spigot.rwg.legacy.api.version.util.Versions;

public enum NpcProfession {

    UNEMPLOYED("NONE", "FARMER", ""),
    NITWIT("NITWIT", "NITWIT", ""),
    ARMORER("ARMORER", "BLACKSMITH", "minecraft:blast_furnace"),
    BUTCHER("BUTCHER", "BUTCHER", "minecraft:smoker"),
    CARTOGRAPHER("CARTOGRAPHER", "LIBRARIAN", "minecraft:cartography_table"),
    CLERIC("CLERIC", "LIBRARIAN", "minecraft:brewing_stand"),
    FARMER("FARMER", "FARMER", "minecraft:composter"),
    FISHERMAN("FISHERMAN", "FARMER", "minecraft:barrel"),
    FLETCHER("FLETCHER", "BUTCHER", "minecraft:fletching_table"),
    LEATHERWORKER("LEATHERWORKER", "BUTCHER", "minecraft:cauldron"),
    LIBRARIAN("LIBRARIAN", "LIBRARIAN", "minecraft:lectern"),
    MASON("MASON", "BUTCHER", "minecraft:stonecutter"),
    SHEPHERD("SHEPHERD", "FARMER", "minecraft:loom"),
    TOOLSMITH("TOOLSMITH", "BLACKSMITH", "minecraft:smithing_table"),
    WEAPONSMITH("WEAPONSMITH", "BLACKSMITH", "minecraft:grindstone");
    
    private static final NpcProfession[] PROFESSIONS = NpcProfession.values();

    private final Villager.Profession profession;
    private final String blockData;
    
    private NpcProfession(String professionName, String legacyProfessionName, String workstationBlockData) {
        this.profession = findProfession(professionName, legacyProfessionName);
        this.blockData = workstationBlockData;
    }
    
    public boolean isSupported() {
        return profession != null;
    }
    
    public Villager.Profession bukkitProfession() {
        return profession;
    }
    
    public String blockData() {
        return blockData;
    }
    
    public int id() {
        return ordinal();
    }
    
    private Villager.Profession findProfession(String name, String legacyName) {
        try {
            return Villager.Profession.valueOf(Versions.getMinecraft().getMinor() >= 14 ? name : legacyName);
        } catch(IllegalArgumentException exp) {
            return null;
        }
    }
    
    public static NpcProfession getById(int id) {
        return id < 0 || id > PROFESSIONS.length ? null : PROFESSIONS[id];
    }
    
}
