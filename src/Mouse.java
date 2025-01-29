/**
 * Klasa obsługująca wejście myszy w grze.
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class Mouse implements MouseListener {
    /**
     * Zmienna informująca, czy lewy przycisk myszy został kliknięty.
     */
    public boolean leftClickPressed;

    /**
     * Pozycja X kursora myszy podczas kliknięcia.
     */
    public int x;

    /**
     * Pozycja Y kursora myszy podczas kliknięcia.
     */
    public int y;

    /**
     * Panel gry, z którym mysz jest powiązana.
     */
    Panel gp;

    /**
     * Konstruktor klasy Mouse.
     * 
     * @param gp obiekt Panel, który obsługuje logikę gry
     */
    public Mouse(Panel gp) {
        this.gp = gp;
    }

    /**
     * Metoda obsługująca zdarzenie kliknięcia myszy.
     * 
     * @param e zdarzenie MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) { // Sprawdzenie, czy kliknięto lewym przyciskiem
            x = e.getX();
            y = e.getY();
            leftClickPressed = true;
            checkGameState();
        }
    }

    /**
     * Metoda sprawdzająca i aktualizująca stan gry w zależności od pozycji kliknięcia.
     */
    public void checkGameState() {
        if (gp.gameState == gp.playState && 800 < y && y < 1024 && 1000 < x && x < 1280) {
            gp.gameState = gp.optionsState;
        } else if (gp.gameState == gp.optionsState && 800 < y && y < 1024 && 980 < x && x < 1280) {
            gp.gameState = gp.playState;
        }

        if (gp.gameState == gp.optionsState && 100 + gp.TileSize < y && y < 180 + gp.TileSize && gp.screenWidth / 2 - 170 < x && x < gp.screenWidth / 2 + 130) {
            gp.currentMap = 0;
            gp.gameState = gp.startState;
        }

        if (gp.gameState == gp.optionsState && 180 + 2 * gp.TileSize < y && y < 180 + 2 * gp.TileSize + 80 && gp.screenWidth / 2 - 170 < x && x < gp.screenWidth / 2 + 130) {
            gp.gameState = gp.resetState;
        }

        if (gp.gameState == gp.optionsState && 260 + 3 * gp.TileSize < y && y < 180 + 3 * gp.TileSize + 160 && gp.screenWidth / 2 - 170 < x && x < gp.screenWidth / 2 + 130) {
            gp.gameState = gp.gameFinished;
        }

        if (gp.buildState == false && 800 < y && y < 1024 && 0 < x && x < 300) {
            gp.buildState = true;
        } else if (gp.buildState == true && 800 < y && y < 1024 && 0 < x && x < 300) {
            gp.buildState = false;
        }
    }

    /**
     * Metoda obsługująca zdarzenie naciśnięcia przycisku myszy.
     * 
     * @param e zdarzenie MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Nie zaimplementowano
    }

    /**
     * Metoda obsługująca zdarzenie zwolnienia przycisku myszy.
     * 
     * @param e zdarzenie MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Nie zaimplementowano
    }

    /**
     * Metoda obsługująca zdarzenie wejścia kursora myszy na komponent.
     * 
     * @param e zdarzenie MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Nie zaimplementowano
    }

    /**
     * Metoda obsługująca zdarzenie wyjścia kursora myszy z komponentu.
     * 
     * @param e zdarzenie MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Nie zaimplementowano
    }
}
