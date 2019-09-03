package main.java.codes.biscuit.skyblockaddons.utils;

public enum Rarity {
    COMMON("§f"),
    UNCOMMON("§a"),
    RARE("§9"),
    EPIC("§5"),
    LEGENDARY("§6"),
    SPECIAL("§d"),
    UNKNOWN("§§");

    private String tag;

    Rarity(String s) {
        this.tag = s;
    }

    public String getRarity(){
        return tag;
    }
}
