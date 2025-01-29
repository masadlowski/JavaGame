import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * Klasa reprezentująca tło gry.
 */
public class Background extends Entity {
    Panel gp;
    Entity[] tile;
    int[][] mapBackgroundNum;
    Graphics2D g;
    String filePath;

    /**
     * Konstruktor klasy Background.
     * 
     * @param gp Panel gry, z którym tło jest powiązane.
     */
    public Background(Panel gp) {
        this.gp = gp;
        this.tile = new Entity[7];
        mapBackgroundNum = new int[gp.gameScreenRows][gp.gameScreenCol];
        getTileType();
    }

    /**
     * Inicjalizuje różne typy kafelków i ich właściwości.
     */
    public void getTileType() {
        try {
            tile[0] = new Entity();
            tile[0].type = 0;

            tile[1] = new Entity();
            tile[1].type = 1;
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tear.png"));

            tile[2] = new Entity();
            tile[2].type = 2;
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/house.png"));
            tile[2].cost = 50;
            

            tile[3] = new Entity();
            tile[3].type = 3;
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/res/market.png"));
            tile[3].cost = 200;
           

            tile[4] = new Entity();
            tile[4].type = 4;
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/res/electric1.png"));
            tile[4].cost = 100;
            

            tile[5] = new Entity();
            tile[5].type = 5;
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/res/electric2.png"));
            tile[5].cost = 400;
            

            tile[6] = new Entity();
            tile[6].type = 6;
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/res/electric3.png"));
            tile[6].cost = 400;
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Aktualizuje stan tła (na razie pusta metoda).
     */
    public void update() {
        // Aktualizacja stanu tła - brak implementacji.
    }

    /**
     * Wczytuje mapę tła z pliku.
     */
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int row = 0; row < gp.gameScreenRows; row++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                for (int col = 0; col < gp.gameScreenCol; col++) {
                    mapBackgroundNum[row][col] = Integer.parseInt(numbers[col]);
                }
            }
            br.close();

        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Błąd formatu danych w pliku: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Plik nie ma wystarczającej liczby wierszy/kolumn!");
        }
    }

    /**
     * Rysuje tło gry.
     * 
     * @param g Obiekt Graphics2D do rysowania.
     */
    public void drawBackground(Graphics2D g) {
        int x = 0;
        int y = 0;
        int rows = 0;
        int col = 0;
        int num = mapBackgroundNum[rows][col];
        this.g = g;

        while (x < gp.screenWidth && y < (gp.screenHeight - 224)) {
            num = mapBackgroundNum[rows][col];
            while (x < gp.screenWidth) {
                num = mapBackgroundNum[rows][col];
                if (tile[num].type == 0) { // pusta przestrzeń
                    g.setColor(Color.gray);
                    g.fillRect(x, y, gp.TileSize, gp.TileSize);
                } else if (tile[num].type == 1 || tile[num].type == 2 || tile[num].type == 3) { // woda
                    g.setColor(Color.gray);
                    g.fillRect(x, y, gp.TileSize, gp.TileSize);
                    g.drawImage(tile[num].image, x, y, gp.TileSize, gp.TileSize, null);
                } else if (tile[num].type == 4) {
                    g.setColor(Color.green);
                    g.fillRect(x, y, gp.TileSize, gp.TileSize);
                    g.drawImage(tile[num].image, x, y, gp.TileSize, gp.TileSize, null);
                } else if (tile[num].type == 5) {
                    g.setColor(Color.blue);
                    g.fillRect(x, y, gp.TileSize, gp.TileSize);
                    g.drawImage(tile[num].image, x, y, gp.TileSize, gp.TileSize, null);
                } else {
                    g.setColor(Color.red);
                    g.fillRect(x, y, gp.TileSize, gp.TileSize);
                    g.drawImage(tile[num].image, x, y, gp.TileSize, gp.TileSize, null);
                }
                x += gp.TileSize;
                g.setColor(Color.black);
                g.fillRect(x, y, 4 * gp.scale, gp.TileSize);
                x += 4 * gp.scale;
                col += 1;
            }
            x = 0;
            col = 0;
            y += gp.TileSize;
            g.setColor(Color.black);
            g.fillRect(x, y, gp.screenWidth, 4 * gp.scale);
            y += 4 * gp.scale;
            rows += 1;
        }
    }
}
