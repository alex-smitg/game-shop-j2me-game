package game_shop;

public class MenuItem {
    String text = "";
    
    int action = 0;
    int price = 0;
    

    
    
    public MenuItem(String text, int action, int price) {
        this.text = text;
        this.action = action;
        this.price = price;
    }
    
    public MenuItem(String text, int action) {
        this.text = text;
        this.action = action;
    }
    
    public MenuItem(String text) {
        this.text = text;
    }
}
