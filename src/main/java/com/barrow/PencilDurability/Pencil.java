package com.barrow.PencilDurability;

public class Pencil {

    private int durability;

    public Pencil(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return this.durability;
    }

    public String write(String paper, String textToWrite) {
        StringBuilder tempString = new StringBuilder(paper);
        for(int i = 0; i < textToWrite.length(); i++) {
            if(this.durability > 0) {
                tempString.append(textToWrite.charAt(i));
                this.durability--;
            }
            else {
                tempString.append(" ");
            }
        }
        return tempString.toString();
    }
}
