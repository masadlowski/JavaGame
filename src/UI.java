import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Klasa odpowiedzialna za rysowanie interfejsu użytkownika w grze.
 * Obejmuje rysowanie menu, ekranu opcji, zasobów oraz budynków.
 */
public class UI {
    Panel gp;
    Graphics2D g;
    Font arial_80;
    Font arial_40;
    Font arial_32;
    public BufferedImage[] image;

    /**
     * Konstruktor klasy UI.
     * Inicjalizuje panel gry, czcionki oraz tablicę obrazków.
     * 
     * @param gp Panel gry, który przechowuje wszystkie informacje o stanie gry.
     */
    public UI(Panel gp){
        this.gp = gp;
        this.image = new BufferedImage[3];
        arial_80 = new Font("Arial", Font.PLAIN, 80);
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_32 = new Font("Arial", Font.PLAIN, 32);
    }

    /**
     * Metoda rysująca główny interfejs gry.
     * Wyświetla menu, ekran opcji, zasoby oraz budynki w zależności od stanu gry.
     * 
     * @param g Obiekt Graphics2D, który umożliwia rysowanie na ekranie.
     */
    public void draw(Graphics2D g){
        this.g = g;
        drawMenu();
        if(gp.buildState == true){
            drawBuildings();
        }
        if(gp.gameState == gp.gameFinished){
            gp.gameThread = null;
        }
        else if(gp.gameState == gp.optionsState){
            drawOptionScreen();
        }
    }

    /**
     * Metoda rysująca ekran opcji w grze.
     * Wyświetla menu wyboru opcji, w tym opcje „Wybierz”, „OdNowa” oraz „Zakończ”.
     */
    public void drawOptionScreen(){
        Color c = new Color(0, 0, 0, 220);
        g.setColor(c);
        g.setFont(g.getFont().deriveFont(32F));
        int width = 320;
        int height = 400;
        int x = gp.screenWidth / 2 - width / 2;
        int y = 100;
        g.fillRect(x, y, width, height);
        
        try {
            this.image[0] = ImageIO.read(getClass().getResourceAsStream("/res/Wybierz.png"));
            this.image[1] = ImageIO.read(getClass().getResourceAsStream("/res/OdNowa.png"));
            this.image[2] = ImageIO.read(getClass().getResourceAsStream("/res/Zakoncz.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int imageWidth = this.image[0].getWidth();  // 300px
        int imageHeight = this.image[0].getHeight();  // 80px
        
        // Rysowanie obrazków opcji w odpowiednich miejscach
        g.drawImage(this.image[0], x + 10, y + gp.TileSize, imageWidth, imageHeight, null);
        g.drawImage(this.image[1], x + 10, y + 2 * gp.TileSize + imageHeight, imageWidth, imageHeight, null);
        g.drawImage(this.image[2], x + 10, y + 3 * gp.TileSize + 2 * imageHeight, imageWidth, imageHeight, null);
    }

    /**
     * Metoda rysująca główne menu gry.
     * Wyświetla tło menu oraz przyciski do wyboru budynków lub opcji.
     */
    public void drawMenu(){
        g.setColor(Color.white);
        g.fillRect(0, 800, gp.screenWidth, 224);
        
        g.setFont(arial_80);
        g.setColor(Color.black);
        g.drawString("Budynki", 0, 820 + 204 / 2);  // Wyśrodkowanie tekstu
        g.drawString("Menu", 1040, 820 + 204 / 2);
        
        g.fillRect(300, 800, 10, 224);  // Linie oddzielające sekcje
        g.fillRect(970, 800, 10, 224);
        drawResource();
    }

    /**
     * Metoda rysująca informacje o zasobach w grze.
     * Wyświetla ilość zasobów: pieniądze, populację, zanieczyszczenie, szczęście oraz turę.
     */
    public void drawResource(){
        g.setFont(arial_40);
        g.drawString("zasoby:" + gp.resources.money + "$" + " populacja:" + gp.resources.population, 330, 800 + 204 / 2);
        g.drawString("zanieczyszczenia:" + gp.resources.pollution, 330, 840 + 204 / 2);
        g.drawString("szczescie:" + gp.resources.happines, 330, 880 + 204 / 2);
        g.drawString("moc generowana:" + gp.resources.powerGenerated, 330, 725 + 204 / 2);
        g.drawString("moc uzyta:" + gp.resources.powerConstumption, 330, 760 + 204 / 2);
        g.drawString("Tura:" + gp.resources.currentTurn, 700, 880 + 204 / 2);
        g.drawString("(Spacja)", 700, 920 + 204 / 2);
    }

    /**
     * Metoda rysująca dostępne budynki w grze.
     * Wyświetla ikonki budynków oraz ich koszty.
     */
    public void drawBuildings(){
        g.setColor(Color.white);
        g.fillRect(310, 800, 970 - 310, 224);
        
        g.setFont(arial_32);
        g.setColor(Color.black);
        int temp = 30;
        
        // Rysowanie obrazków oraz kosztów budynków
        g.drawImage(gp.background.tile[2].image, 310, 805, gp.TileSize, gp.TileSize, null);
        g.drawString("koszt:" + gp.background.tile[2].cost + "$", 350, 800 + temp);

        g.drawImage(gp.background.tile[3].image, 310, 815 + gp.TileSize, gp.TileSize, gp.TileSize, null);
        g.drawString("koszt:" + gp.background.tile[3].cost + "$", 350, 810 + gp.TileSize + temp);

        g.drawImage(gp.background.tile[4].image, 600, 805, gp.TileSize, gp.TileSize, null);
        g.drawString("koszt:" + gp.background.tile[4].cost + "$", 640, 800 + temp);

        g.drawImage(gp.background.tile[5].image, 600, 815 + gp.TileSize, gp.TileSize, gp.TileSize, null);
        g.drawString("koszt:" + gp.background.tile[5].cost + "$", 640, 810 + gp.TileSize + temp);

        g.drawImage(gp.background.tile[6].image, 600, 825 + 2 * gp.TileSize, gp.TileSize, gp.TileSize, null);
        g.drawString("koszt:" + gp.background.tile[6].cost + "$", 640, 820 + 2 * gp.TileSize + temp);
    }
}
