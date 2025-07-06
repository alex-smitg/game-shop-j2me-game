package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Images {

    Image cell_0;
    Image cell_selector;
    Image arrow_right;

    Images() {
        try {
            this.cell_0 = Image.createImage("/images/cell.png");
            this.cell_selector = Image.createImage("/images/cursor.png");
            this.arrow_right = Image.createImage("/images/arrow.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
