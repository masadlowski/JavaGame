/**
 * Klasa Panel reprezentuje główny panel gry, w którym renderowane są grafiki i zarządzane są stany gry.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{
    /**
     * Skala grafiki.
     */
    final int scale = 2;

    /**
     * Rozmiar pojedynczego kafelka (Tile) w pikselach.
     */
    final int TileSize = scale * 16;

    /**
     * Maksymalna liczba wierszy ekranu.
     */
    final int maxScreenRow = 64 / scale;

    /**
     * Maksymalna liczba kolumn ekranu.
     */
    final int maxScreenCol = 80 / scale;

    /**
     * Szerokość ekranu w pikselach.
     */
    final int screenWidth = TileSize * maxScreenCol; // 1280px

    /**
     * Wysokość ekranu w pikselach.
     */
    final int screenHeight = TileSize * maxScreenRow; // 1024px

    /**
     * Liczba wierszy w grze.
     */
    final int gameScreenRows = 20; // 1024px

    /**
     * Liczba kolumn w grze.
     */
    final int gameScreenCol = 32; // 1280px

    /**
     * Liczba klatek na sekundę (FPS).
     */
    int FPS = 60;

    /**
     * Obiekt do obsługi klawiatury.
     */
    Keyboard keyboard = new Keyboard(this);       

    /**
     * Obiekt do obsługi myszy.
     */
    Mouse mouse = new Mouse(this);

    /**
     * Wątek gry.
     */
    Thread gameThread;

    /**
     * Obiekt do obsługi budynków w grze.
     */
    Buildings building = new Buildings(this, keyboard, mouse);

    /**
     * Obiekt tła gry.
     */
    Background background = new Background(this);

    /**
     * Obiekt ekranu początkowego gry.
     */
    StartScreen startScreen = new StartScreen(this);

    /**
     * Obiekt zarządzający zasobami w grze.
     */
    Resources resources = new Resources(this);

    /**
     * Obiekt interfejsu użytkownika.
     */
    public UI ui = new UI(this);

    /**
     * Stan gry - aktualny stan.
     */
    public int gameState;

    /**
     * Stała reprezentująca stan gry: rozgrywka.
     */
    public int playState = 1;

    /**
     * Stała reprezentująca stan gry: opcje.
     */
    public int optionsState = 2;

    /**
     * Stała reprezentująca stan gry: zakończenie gry.
     */
    public int gameFinished = 3;

    /**
     * Stała reprezentująca stan gry: ekran początkowy.
     */
    public int startState = 4;

    /**
     * Stała reprezentująca stan gry: reset.
     */
    public int resetState = 5;

    /**
     * Stała reprezentująca stan gry: zakończenie mapy.
     */
    public int mapFinishedState = 6;

    /**
     * Zmienna określająca, czy gra została wygrana.
     */
    public boolean gameWon = false;

    /**
     * Numer aktualnej mapy.
     */
    public int currentMap = 0;

    /**
     * Poziom trudności gry.
     */
    public int difficulty;

    /**
     * Liczba ukończonych map.
     */
    public int completedMaps;

    /**
     * Zmienna określająca, czy tryb budowy jest aktywny.
     */
    public boolean buildState;

    /**
     * Konstruktor klasy Panel. Ustawia podstawowe właściwości panelu gry.
     */
    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyboard);
        this.addMouseListener(mouse);
        this.setFocusable(true);
    }

    /**
     * Metoda przygotowująca grę do działania (inicjalizacja mapy).
     */
    public void setupGame() {
        background.loadMap();
    }

    /**
     * Metoda uruchamiająca główny wątek gry.
     */
    public void startGamethread() {
        gameThread = new Thread(this);
        gameThread.start();
        gameState = startState;
        buildState = false;
    }

    /**
     * Główna pętla gry, która aktualizuje stany i odświeża ekran.
     */
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Aktualizacja stanu gry w zależności od aktualnego stanu gry.
     */
    public void update() {
        if (gameState == playState) {
            building.update();
            resources.update();

            if (keyboard.spacePressed == true) {
                resources.nextTurn();
                keyboard.spacePressed = false;
            }
        }

        if (gameState == resetState) {
            background.loadMap();
            resources.setup_difficulty();
            buildState = false;
            gameState = playState;
        }

        if (gameState == mapFinishedState) {
            completedMaps += 1;

            if (completedMaps >= 3) {
                gameWon = true;
            }

            if (currentMap == 1) {
                changeMap(2);
            } else if (currentMap == 2) {
                changeMap(3);
            } else {
                changeMap(1);
            }

            changeMap(currentMap);
            background.loadMap();
            resources.setup_difficulty();
            buildState = false;
            gameState = playState;
        }

        if (gameState == optionsState) {
            // Opcjonalne logiki dla stanu opcji
        }
    }

    /**
     * Metoda zmieniająca aktywną mapę na nową.
     * 
     * @param newMap numer nowej mapy
     */
    public void changeMap(int newMap) {
        currentMap = newMap;

        if (currentMap == 1) {
            background.filePath = "/res/map01.txt";
        } else if (currentMap == 2) {
            background.filePath = "/res/map02.txt";
        } else {
            background.filePath = "/res/map03.txt";
        }
    }

    /**
     * Metoda renderująca komponenty na ekranie.
     * 
     * @param g obiekt klasy Graphics służący do rysowania na panelu
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == startState) {
            startScreen.draw(g2);
        } else if (gameWon) {
            g2.setFont(new Font("Arial", Font.PLAIN, 80));
            g2.setColor(Color.yellow);
            g2.drawString("Gra skończona pomyślnie", screenWidth / 2 - 400, screenHeight / 2);
            gameThread = null;
        } else {
            background.drawBackground(g2);
            ui.draw(g2);
            g2.dispose();
        }
    }
}
