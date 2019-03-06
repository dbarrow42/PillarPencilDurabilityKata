package com.barrow.PencilDurability;

public class Pencil implements IDurable {

    private final int LOWERCASE_DEGRADATION_VALUE = 1;
    private final int UPPERCASE_DEGRADATION_VALUE = 2;
    private final char PARTIAL_UPPERCASE_INDICATOR = '~';

    private int durability;
    private int initialDurability;
    private int length;
    private Eraser eraser;

    public Pencil(int durability, int length, int eraserDurability) {
        this.durability = durability;
        this.initialDurability = durability;
        this.length = length;
        this.eraser = new Eraser(eraserDurability);
    }

    public int getDurability() {
        return this.durability;
    }

    public void reduceDurability(int amount) {
        this.durability -= amount;
    }

    public int getEraserDurability() {
        return this.eraser.getDurability();
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
                this.reduceDurability(this.LOWERCASE_DEGRADATION_VALUE);
            } else if (this.durability - this.UPPERCASE_DEGRADATION_VALUE > 0) { // Write uppercase
                tempString.append(nextChar);
                this.reduceDurability(this.UPPERCASE_DEGRADATION_VALUE);
            } else { // Write 'partial' uppercase character (ran out of durability before finishing character)
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
        return this.eraser.erase(paper, textToErase);

    }

    public String edit(String paper, int startingLocation, String textToWrite) {
        StringBuilder sb = new StringBuilder(paper);
        int position = startingLocation;
        int textIndex = 0;
        while (this.durability > 0 && position < startingLocation + textToWrite.length()) {
            char currentPaperChar = sb.charAt(position);
            char currentCharToBeWritten = textToWrite.charAt(textIndex);
            if (currentPaperChar != currentCharToBeWritten) {
                if (!Character.isWhitespace(currentPaperChar)) {
                    sb.setCharAt(position, '@');
                    this.reduceDurability(this.LOWERCASE_DEGRADATION_VALUE);
                } else if (!Character.isLowerCase(currentCharToBeWritten)){
                    if (this.durability - this.UPPERCASE_DEGRADATION_VALUE < 0) {
                        currentCharToBeWritten = this.PARTIAL_UPPERCASE_INDICATOR;
                        this.durability = this.UPPERCASE_DEGRADATION_VALUE; // durability will be zero after next two statements
                    }
                    sb.setCharAt(position, currentCharToBeWritten);
                    this.reduceDurability(this.UPPERCASE_DEGRADATION_VALUE);
                } else {
                    sb.setCharAt(position, currentCharToBeWritten);
                    this.reduceDurability(this.LOWERCASE_DEGRADATION_VALUE);
                }
            }
            position++;
            textIndex++;
        }
        return sb.toString();
    }
}
