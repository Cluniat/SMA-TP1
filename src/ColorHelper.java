import java.awt.*;
import java.util.Random;

public class ColorHelper {

    public static Color generateColor() {
        Random rand = new Random();
        int rand_num = rand.nextInt(0xffffff + 1);
        return Color.decode(String.format("#%06x", rand_num));
    }


}
