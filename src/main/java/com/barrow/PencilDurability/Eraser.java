package com.barrow.PencilDurability;

public class Eraser implements IDurable {

    private int durability;

    public int getDurability() {
        return this.durability;
    }

    public void reduceDurability(int amount) {
        this.durability -= amount;
    }

    public Eraser(int durability) {
        this.durability = durability;
    }

    public String erase(String paper, String textToErase) {
        int location = paper.lastIndexOf(textToErase);
        if (location > -1) {
            int position = location + textToErase.length() - 1;
            StringBuilder sb = new StringBuilder(paper);
            while (this.durability > 0 && position >= location) {
                if (!Character.isWhitespace(paper.charAt(position))) {
                    sb.setCharAt(position, ' ');
                    this.reduceDurability(1);
                }
                position--;
            }
            paper = sb.toString();
        }
        return paper;

    }
}
