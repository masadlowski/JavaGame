/**
 * Klasa obsługująca wejście klawiatury w grze.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    /**
     * Zmienna informująca, czy klawisz spacji jest wciśnięty.
     */
    public boolean spacePressed;

    /**
     * Panel gry, z którym klawiatura jest powiązana.
     */
    Panel gp;

    /**
     * Konstruktor klasy Keyboard.
     * 
     * @param gp obiekt Panel, który obsługuje logikę gry
     */
    public Keyboard(Panel gp) {
        this.gp = gp;
    }

    /**
     * Metoda obsługująca zdarzenie wpisania znaku z klawiatury.
     * 
     * @param e zdarzenie KeyEvent
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metoda obsługująca zdarzenie wciśnięcia klawisza.
     * 
     * @param e zdarzenie KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) { // Jeśli wciśnięto spację
            spacePressed = true;
        }
        if (gp.gameState == gp.startState) {
            startInputs(code);
        }
    }

    /**
     * Metoda obsługująca zdarzenie zwolnienia klawisza.
     * 
     * @param e zdarzenie KeyEvent
     */
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) { // Jeśli zwolniono spację
            spacePressed = false;
        }
    }

    /**
     * Metoda obsługująca wejścia w stanie początkowym gry.
     * 
     * @param code kod klawisza, który został wciśnięty
     */
    public void startInputs(int code) {
        if (gp.currentMap == 0) {
            if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_3) {
                gp.currentMap = code - KeyEvent.VK_0; // Zapisz wybór mapy
            }
        } else {
            if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_3) {
                gp.difficulty = code - KeyEvent.VK_0; // Zapisz wybór trudności
                gp.resources.setup_difficulty();
                gp.gameState = gp.playState;

                if (gp.currentMap == 1) {
                    gp.background.filePath = "/res/map01.txt";
                } else if (gp.currentMap == 2) {
                    gp.background.filePath = "/res/map02.txt";
                } else {
                    gp.background.filePath = "/res/map03.txt";
                }

                gp.completedMaps = 0;
                gp.background.loadMap();
            }
        }
    }
}
