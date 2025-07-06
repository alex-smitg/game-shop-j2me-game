package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Images {

    Image cell_0;
    Image cell_selector;
    Image arrow_right;
    Image checkout;
    Image shelf_0;

    Images() {
        try {
            this.cell_0 = Image.createImage("/images/cell.png");
            this.cell_selector = Image.createImage("/images/cursor.png");
            this.arrow_right = Image.createImage("/images/arrow.png");
            this.checkout = Image.createImage("/images/checkout.png");
            this.shelf_0 = Image.createImage("/images/shelf.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
