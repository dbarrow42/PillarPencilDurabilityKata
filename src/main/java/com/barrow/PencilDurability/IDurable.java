package com.barrow.PencilDurability;

public interface IDurable {
    public final int LOWERCASE_DEGRADATION_VALUE = 1;
    public final int UPPERCASE_DEGRADATION_VALUE = 2;

    int getDurability();
    void reduceDurability(int amount);
}
