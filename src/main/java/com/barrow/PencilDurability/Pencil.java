package com.barrow.PencilDurability;

public class Pencil {

    private int durability;
    private final int LOWERCASE_DEGRADTION_VALUE = 1;
    private final int UPPERCASE_DEGRADTION_VALUE = 1;

    public Pencil(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return this.durability;
    }

    public String write(String paper, String textToWrite) {
        StringBuilder tempString = new StringBuilder(paper);
        for(int i = 0; i < textToWrite.length(); i++) {
            char nextChar = textToWrite.charAt(i);
            if(this.durability > 0 && !Character.isWhitespace(nextChar)) {
                tempString.append(nextChar);
                this.durability--;
            }
            else {
                tempString.append(" ");
            }
        }
        return tempString.toString();
    }
}
