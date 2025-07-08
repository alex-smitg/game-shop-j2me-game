package game_shop;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Images {

    Image cell_0;
    Image cell_0_grass;
    Image cell_selector;
    Image arrow_right;
    Image checkout_0;
    Image checkout_0_empty;
    Image checkout_1;
    Image checkout_1_empty;
    Image shelf_0;
    Image shelf_0_empty;
    Image smile_happy;
    Image smile_normal;
    Image smile_angry;
    Image flower_pot;
    Image shelf_1;
    Image shelf_1_empty;
    Image arrow_down;
    Image fox;
    Image vending_machine;

    Images() {
        try {
            this.cell_0 = Image.createImage("/images/cell.png");
            this.cell_0_grass = Image.createImage("/images/grass.png");
            this.cell_selector = Image.createImage("/images/cursor.png");
            this.arrow_right = Image.createImage("/images/arrow.png");
            this.checkout_0 = Image.createImage("/images/checkout_0.png");
            this.checkout_0_empty = Image.createImage("/images/checkout_0_empty.png");
            this.checkout_1 = Image.createImage("/images/checkout_1.png");
            this.checkout_1_empty = Image.createImage("/images/checkout_1_empty.png");
            this.shelf_0 = Image.createImage("/images/shelf.png");
            this.shelf_0_empty = Image.createImage("/images/shelf_empty.png");
            this.smile_happy = Image.createImage("/images/happy.png");
            this.smile_normal = Image.createImage("/images/normal.png");
            this.smile_angry = Image.createImage("/images/angry.png");
            this.flower_pot = Image.createImage("/images/flowerpot.png");
            this.shelf_1 = Image.createImage("/images/shelf_1.png");
            this.shelf_1_empty = Image.createImage("/images/shelf_1_empty.png");
            this.arrow_down = Image.createImage("/images/arrow_down.png");
            this.fox = Image.createImage("/images/fox.png");
            this.vending_machine = Image.createImage("/images/vending_machine.png");
            System.out.println("Images: OK");
        } catch (IOException e) {
            System.out.println("Images: ERROR");
            e.printStackTrace();
            
        }
    }
}
