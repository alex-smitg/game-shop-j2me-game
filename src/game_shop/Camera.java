package game_shop;

public class Camera {
    private Vector2d position = new Vector2d(0, 0);
    private Vector2d target_position = new Vector2d(0, 0);
    private Vector2d offset = new Vector2d(0, 0);
    private float speed = 0.1f;
    
    public Camera(Vector2d offset) {
        this.offset = offset;
    } 
    
    void update() {
        position.x += (target_position.x - position.x - offset.x) * speed;
        position.y += (target_position.y - position.y - offset.y) * speed;
    }
    
    void setTargetPosition(Vector2d target_position) {
        this.target_position = target_position;
    }
    
    Vector2d getPosition() {
        return position;
    }
}
