/**
 * Klasa reprezentująca encję w grze.
 */
import java.awt.image.BufferedImage;

public class Entity {
    /**
     * Współrzędna x encji.
     */
    public int x;

    /**
     * Współrzędna y encji.
     */
    public int y;

    /**
     * Typ encji.
     */
    public int type;

    /**
     * Obraz reprezentujący encję.
     */
    public BufferedImage image;

    /**
     * Koszt encji w grze.
     */
    public int cost;
}
