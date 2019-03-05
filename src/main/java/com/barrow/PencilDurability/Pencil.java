package com.barrow.PencilDurability;

public class Pencil {

    private final int LOWERCASE_DEGRADATION_VALUE = 1;
    private final int UPPERCASE_DEGRADATION_VALUE = 2;
    private final char PARTIAL_UPPERCASE_INDICATOR = '~';

    private int durability;
    private int initialDurability;
    private int length;
    private int eraserDurability;

    public Pencil(int durability, int length, int eraserDurability) {
        this.durability = durability;
        this.initialDurability = durability;
        this.length = length;
        this.eraserDurability = eraserDurability;
    }

    public int getPencilDurability() {
        return this.durability;
    }

    public int getEraserDurability() {
        return this.eraserDurability;
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
                tempString.append(this.PARTIAL_UPPERCASE_INDICATOR);
                this.durability = 0;
            }
        }
        return tempString.toString();
    }

    public void sharpen() {
        if (this.length > 0 && this.durability < this.initialDurability) {
            this.length--;
            this.durability = this.initialDurability;
        }
    }

    public String erase(String paper, String textToErase) {
        StringBuilder whitespace = new StringBuilder();
        for (int i = 0; i < textToErase.length(); i++) {
            whitespace.append(" ");
        }
        int location = paper.lastIndexOf(textToErase);
        if (location != -1) {
            StringBuilder sb = new StringBuilder(paper);
            paper = sb.replace(location, location + textToErase.length(), whitespace.toString()).toString();
            this.eraserDurability -= textToErase.length();
        }
        return paper;
    }

}
