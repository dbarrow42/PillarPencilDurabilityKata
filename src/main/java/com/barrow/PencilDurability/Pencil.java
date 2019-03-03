package com.barrow.PencilDurability;

public class Pencil {

    private int durability;
    private final int LOWERCASE_DEGRADATION_VALUE = 1;
    private final int UPPERCASE_DEGRADATION_VALUE = 2;
    private final char PARTIAL_UPPDERCASE_INDICATOR = '~';

    public Pencil(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return this.durability;
    }

    // TODO Too many conditionals/branching
    public String write(String paper, String textToWrite) {
        StringBuilder tempString = new StringBuilder(paper);
        for(int i = 0; i < textToWrite.length(); i++) {
            char nextChar = textToWrite.charAt(i);
            if(this.durability == 0 || Character.isWhitespace(nextChar)) {
                tempString.append(" ");
            }
            else if(Character.isLowerCase(nextChar)){
                tempString.append(nextChar);
                this.durability -= this.LOWERCASE_DEGRADATION_VALUE;
            }
            else {
                if(this.durability - this.UPPERCASE_DEGRADATION_VALUE > 0) {
                    tempString.append(nextChar);
                    this.durability -= this.UPPERCASE_DEGRADATION_VALUE;
                }
                else {
                    tempString.append(this.PARTIAL_UPPDERCASE_INDICATOR);
                    this.durability = 0;
                }
            }
        }
        return tempString.toString();
    }
}
