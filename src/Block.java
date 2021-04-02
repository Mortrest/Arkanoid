import java.util.Random;

public class Block {
    int blockX;
    int blockY;
    int power;
    int type;
    boolean visible;
    // 1 Glassy
    // 2 Wooden
    // 3 Invisible
    // 4 Winky
    // 0 Prize

    Block(int x, int y){
        this.blockX = x;
        this.blockY = y;
        visible = true;
        Random random = new Random();
        type = random.nextInt(5);
        if (type == 0){
            power = random.nextInt(8);
        }
    }
    Block(int x, int y, int power, int type, boolean visible){
        this.blockX = x;
        this.blockY = y;
        this.power = power;
        this.type = type;
        this.visible = visible;
    }
}
