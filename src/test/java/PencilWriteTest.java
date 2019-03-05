import com.barrow.PencilDurability.Pencil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PencilWriteTest {

    String paper;
    Pencil pencil;

    @BeforeEach
    public void setup() {
        paper = "";
        pencil = new Pencil(30, 2, 30);
    }

    @Test
    public void PencilWriteToEmptyPaper() {
        paper = pencil.write(paper, "Pencils are used for writing");
        assertEquals("Pencils are used for writing", paper);
    }

    @Test
    public void PencilWriteToPaperWithExistingContents() {
        paper = "Pencils are: ";
        paper = pencil.write(paper, "used for writing");
        assertEquals("Pencils are: used for writing", paper);
    }

    @Test
    public void PencilWritesOnlyUpToAsManyCharactersAsItsDurability() {
        paper = pencil.write(paper, "The quick brown fox jumps over the lazy dog.");
        assertEquals("The quick brown fox jumps over the l        ", paper);
        assertEquals(0, pencil.getPencilDurability());
    }

    @Test
    public void PencilDurabilityDegradesQuickerWithCapitalLetters() {
        paper = pencil.write(paper, "text");
        assertEquals(26, pencil.getPencilDurability());
        paper = pencil.write(paper, "TEXT");
        assertEquals(18, pencil.getPencilDurability());
    }

    @Test
    public void PencilDurabilityTryToWriteCapitalLetterWithOnlyOneDurabilityRemaining() {
        paper = pencil.write(paper, "The quick brown fox jumps over the Lazy dog.");
        assertEquals("The quick brown fox jumps over the ~        ", paper);
        assertEquals(0, pencil.getPencilDurability());
    }

    @Test
    public void PencilWriteNewLineCharactersDoesNotRemoveDurability() {
        paper = pencil.write(paper, "The quick brown fox jumps\n over the lazy dog.");
        assertEquals(0, pencil.getPencilDurability());
    }

    @Test
    public void SharpeningPencilCausesItToRegainInitialDurability() {
        paper = pencil.write(paper, "text");
        pencil.sharpen();
        assertEquals(30, pencil.getPencilDurability());
    }

    @Test
    public void SharpeningPencilShortensItsOverallLength() {
        paper = pencil.write(paper, "text");
        pencil.sharpen();
        assertEquals(1, pencil.getLength());
    }

    @Test
    public void SharpeningAnAlreadySharpPencilDoesNotReduceLength() {
        pencil.sharpen();
        assertEquals(2, pencil.getLength());
    }

    @Test
    public void CannotSharpenWhenLengthIsZero() {
        pencil = new Pencil(30, 0, 30);
        pencil.write(paper, "testing");
        pencil.sharpen();
        assertEquals(23, pencil.getPencilDurability());
        assertEquals(0, pencil.getLength());
    }

    @Test
    public void PencilWritingNonLetterCharactersAreTreatedAsUppercase() {
        pencil.write(paper, "123@!#.,;'");
        assertEquals(10, pencil.getPencilDurability());
    }

    @Test
    public void EraseTextFromPaper() {
        paper = "The quick brown fox";
        paper = pencil.erase(paper, "brown");
        assertEquals("The quick       fox", paper);
    }

    @Test
    public void ErasingTextThatDoesNotExistFromPaperDoesNothing() {
        paper = "The quick brown fox";
        paper = pencil.erase(paper, "chipmunk");
        assertEquals("The quick brown fox", paper);
    }

    @Test
    public void ErasingTextContainingPartOfExistingTextDoesNothing() {
        paper = "The quick brown fox";
        paper = pencil.erase(paper, "brown bear");
        assertEquals("The quick brown fox", paper);
    }

    @Test
    public void ErasingTextThatIsPartOfAWordDeletesThatPart() {
        paper = "woodchuck";
        paper = pencil.erase(paper, "chuck");
        assertEquals("wood     ", paper);
    }

    @Test
    public void ErasingTextOnlyErasesLastOccurrence() {
        paper = "woodchuck could chuck";
        paper = pencil.erase(paper, "chuck");
        assertEquals("woodchuck could      ", paper);
    }

    @Test
    public void ErasingTextCausesEraserToDegrade() {
        paper = "woodchuck could chuck";
        paper = pencil.erase(paper, "chuck");
        assertEquals(25, pencil.getEraserDurability());
    }

    @Test
    public void ErasingWhitespaceDoesNotReduceEraserDurability() {
        paper = "woodchuck could chuck";
        paper = pencil.erase(paper, "could chuck");
        assertEquals(20, pencil.getEraserDurability());
    }

    @Test
    public void EraserWithoutDurabilityCannotEraseAnyFurther() {
        pencil = new Pencil(30, 2, 3);
        paper = "woodchuck could chuck";
        paper = pencil.erase(paper, "chuck");
        assertEquals(0, pencil.getEraserDurability());
        assertEquals("woodchuck could ch   ", paper);
    }

    @Test
    public void EditTextWithoutOverwritingAnything() {
        paper = "An       a day keeps the doctor away";
        paper = pencil.edit(paper, 3 , "onion");
        assertEquals("An onion a day keeps the doctor away", paper);
    }

    @Test
    public void EditingTextReducesPencilDurability() {
        paper = "An       a day keeps the doctor away";
        paper = pencil.edit(paper, 3 , "onion");
        assertEquals(25, pencil.getPencilDurability());
    }

    @Test
    public void EditingOverTextWithCollision() {
        paper = "An       a day keeps the doctor away";
        paper = pencil.edit(paper, 3 , "onion b");
        assertEquals(24, pencil.getPencilDurability());
        assertEquals("An onion @ day keeps the doctor away", paper);
    }

    @Test
    public void EditingOverTextWithMultipleCollisions() {
        paper = "An       a day keeps the doctor away";
        paper = pencil.edit(paper, 3 , "artichoke");
        assertEquals(21, pencil.getPencilDurability());
        assertEquals("An artich@k@ay keeps the doctor away", paper);
    }
}
