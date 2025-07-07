package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Images {

    Image cell_0;
    Image cell_selector;
    Image arrow_right;
    Image checkout;
    Image checkout_empty;
    Image shelf_0;
    Image shelf_0_empty;
    Image smile_happy;
    Image smile_normal;
    Image smile_angry;
   

    Images() {
        try {
            this.cell_0 = Image.createImage("/images/cell.png");
            this.cell_selector = Image.createImage("/images/cursor.png");
            this.arrow_right = Image.createImage("/images/arrow.png");
            this.checkout = Image.createImage("/images/checkout.png");
            this.checkout_empty = Image.createImage("/images/checkout_empty.png");
            this.shelf_0 = Image.createImage("/images/shelf.png");
            this.shelf_0_empty = Image.createImage("/images/shelf_empty.png");
            this.smile_happy =  Image.createImage("/images/happy.png");
            this.smile_normal =  Image.createImage("/images/normal.png");
            this.smile_angry =  Image.createImage("/images/angry.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
