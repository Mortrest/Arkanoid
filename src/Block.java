import java.util.Random;

public class Block {
    int blockX;
    int blockY;
    int power;
    int type;

    // 1 Glassy
    // 2 Wooden
    // 3 Invisible
    // 4 Winky
    // 5 Prize

    Block(int x, int y){
        this.blockX = x;
        this.blockY = y;
        Random random = new Random();
        type = random.nextInt(6);
        type = 2;
//        if ()power = random.nextInt(10);

    }
}
