import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Klasa odpowiedzialna za rysowanie ekranu startowego gry.
 * Wyświetla powitanie oraz instrukcje dotyczące wyboru poziomu trudności lub mapy.
 */
public class StartScreen {
    Panel gp;
    int mapBackgroundNum[][];
    Graphics2D g;

    /**
     * Konstruktor klasy StartScreen.
     * Inicjalizuje panel gry.
     * 
     * @param gp Panel gry, który przechowuje informacje o stanie gry.
     */
    public StartScreen(Panel gp){
        this.gp = gp;
    }

    /**
     * Metoda rysująca ekran startowy gry.
     * Wyświetla powitanie oraz instrukcje dla gracza.
     * Jeśli aktualna mapa to 0, wyświetla instrukcje dotyczące wyboru mapy.
     * Jeśli mapa jest inna, wyświetla instrukcje dotyczące wyboru poziomu trudności.
     * 
     * @param g Obiekt Graphics2D, który umożliwia rysowanie na ekranie.
     */
    public void draw(Graphics2D g){
        this.g = g;

        // Ustawienie koloru tekstu na biały
        g.setColor(Color.WHITE);
        
        // Ustawienie czcionki na pogrubioną Arial, rozmiar 30
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Witaj w grze!", 100, 50);

        // Instrukcje
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Sprawdzanie, czy aktualna mapa to 0
        if (gp.currentMap == 0) {
            g.drawString("Wybierz poziom(mape) (1-3):", 100, 100);
        } else {
            g.drawString("Wybierz poziom trudności (1-3):", 100, 100);
            g.drawString("1.Latwy", 100, 130);
            g.drawString("2.Sredni", 100, 160);
            g.drawString("3.Trudny", 100, 190);
        }
    }
}
