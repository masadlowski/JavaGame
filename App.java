/**
 * Główna klasa aplikacji, która inicjuje i uruchamia grę.
 */
import javax.swing.JFrame;

public class App {

    /**
     * Punkt wejścia do aplikacji.
     *
     * @param args Argumenty wiersza poleceń (nieużywane w tej aplikacji).
     */
    public static void main(String[] args) {

        // Utworzenie i skonfigurowanie głównego okna aplikacji.
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Hapcity");

        // Utworzenie i dodanie głównego panelu gry do okna.
        Panel panel = new Panel();
        window.add(panel);

        // Dostosowanie rozmiaru okna do preferowanego rozmiaru jego komponentów.
        window.pack();

        // Wyśrodkowanie okna na ekranie.
        window.setLocationRelativeTo(null);

        // Ustawienie okna jako widocznego.
        window.setVisible(true);

        // Uruchomienie wątku gry odpowiedzialnego za pętlę gry.
        panel.startGamethread();
    }
}
