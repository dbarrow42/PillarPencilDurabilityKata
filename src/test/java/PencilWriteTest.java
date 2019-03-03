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
        pencil = new Pencil(30);
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
        assertEquals("The quick brown fox jumps over the la       ", paper);
        assertEquals(0, pencil.getDurability());
    }

    @Test
    public void PencilDurabilityDegradesQuickerWithCapitalLetters() {
        paper = pencil.write(paper, "text");
        assertEquals(26, pencil.getDurability());
        paper = pencil.write(paper, "TEXT");
        assertEquals(18, pencil.getDurability());
    }

}
