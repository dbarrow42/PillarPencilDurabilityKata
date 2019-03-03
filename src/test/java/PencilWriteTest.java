import com.barrow.PencilDurability.Pencil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PencilWriteTest {

    @Test
    public void PencilWriteToEmptyPaper() {
        String paper = "";
        Pencil pencil = new Pencil();
        paper = pencil.write(paper, "Pencils are used for writing");
        assertEquals("Pencils are used for writing", paper);
    }

}
