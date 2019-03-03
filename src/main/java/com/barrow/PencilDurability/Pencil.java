package com.barrow.PencilDurability;

public class Pencil {

    private final int LOWERCASE_DEGRADATION_VALUE = 1;
    private final int UPPERCASE_DEGRADATION_VALUE = 2;
    private final char PARTIAL_UPPDERCASE_INDICATOR = '~';

    private int durability;
    private int initialDurability;
    private int length;

    public Pencil(int durability, int length) {
        this.durability = durability;
        this.initialDurability = durability;
        this.length = length;
    }

    public int getDurability() {
        return this.durability;
    }

    public int getLength() {
        return this.length;
    }

    public String write(String paper, String textToWrite) {
        StringBuilder tempString = new StringBuilder(paper);
        for (int i = 0; i < textToWrite.length(); i++) {
            char nextChar = textToWrite.charAt(i);
            if (this.durability == 0 || Character.isWhitespace(nextChar)) { // Write whitespace
                tempString.append(" ");
            } else if (Character.isLowerCase(nextChar)) { // Write lowercase
                tempString.append(nextChar);
                this.durability -= this.LOWERCASE_DEGRADATION_VALUE;
            } else if (this.durability - this.UPPERCASE_DEGRADATION_VALUE > 0) { // Write uppercase
                tempString.append(nextChar);
                this.durability -= this.UPPERCASE_DEGRADATION_VALUE;
            } else { // Write 'partial' uppercase character (ran out of durability before finishing character
                tempString.append(this.PARTIAL_UPPDERCASE_INDICATOR);
                this.durability = 0;
            }
        }
        return tempString.toString();
    }

    public void sharpen() {
        if (this.durability < this.initialDurability) {
            this.length--;
        }
        this.durability = this.initialDurability;
    }
}
