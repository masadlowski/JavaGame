/**
 * Klasa reprezentująca budynki w grze.
 */
public class Buildings extends Entity {
    Panel gp;
    Keyboard keyboard;
    Mouse mouse;
    int houses;
    int markets;
    int windPowerStation;
    int waterPowerStation;
    int coalPowerStation;
    int chosedBuilding;

    /**
     * Konstruktor klasy Buildings.
     * 
     * @param gp Panel gry, z którym budynki są powiązane.
     * @param keyboard Obiekt klawiatury do obsługi wejścia.
     * @param mouse Obiekt myszy do obsługi wejścia.
     */
    public Buildings(Panel gp, Keyboard keyboard, Mouse mouse) {
        this.gp = gp;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.chosedBuilding = 0;
        x = 100;
        y = 100;
    }

    /**
     * Aktualizuje stan budynków w grze.
     */
    public void update() {
        if (keyboard.spacePressed) {
            x += 10;
        }
        if (gp.buildState) {
            chooseBuilding();
        }
        if (gp.buildState && this.chosedBuilding != 0) {
            makeBuilding(this.chosedBuilding);
        }
        if (!gp.buildState) {
            this.chosedBuilding = 0;
        }
        countBuildings();
    }

    /**
     * Zlicza liczbę różnych typów budynków na mapie.
     */
    public void countBuildings() {
        this.houses = 0;
        this.markets = 0;
        this.windPowerStation = 0;
        this.waterPowerStation = 0;
        this.coalPowerStation = 0;
        for (int i = 0; i < gp.gameScreenRows; i++) {
            for (int j = 0; j < gp.gameScreenCol; j++) {
                int num = gp.background.mapBackgroundNum[i][j];
                if (num == 2) {
                    houses++;
                } else if (num == 3) {
                    markets++;
                } else if (num == 4) {
                    windPowerStation++;
                } else if (num == 5) {
                    waterPowerStation++;
                } else if (num == 6) {
                    coalPowerStation++;
                }
            }
        }
    }

    /**
     * Pozwala wybrać budynek do budowy na podstawie pozycji myszy.
     */
    public void chooseBuilding() {
        if (mouse.leftClickPressed) {
            if (310 < mouse.x && mouse.x < 310 + gp.TileSize && 805 < mouse.y && mouse.y < (805 + gp.TileSize)) {
                this.chosedBuilding = 2; // Mieszkanie
            } else if (310 < mouse.x && mouse.x < 310 + gp.TileSize && 815 + gp.TileSize < mouse.y && mouse.y < (815 + 2 * gp.TileSize)) {
                this.chosedBuilding = 3; // Sklep
            } else if (600 < mouse.x && mouse.x < 600 + gp.TileSize && 805 < mouse.y && mouse.y < (805 + gp.TileSize)) {
                this.chosedBuilding = 4; // Elektrownia wiatrowa
            } else if (600 < mouse.x && mouse.x < 600 + gp.TileSize && 815 + gp.TileSize < mouse.y && mouse.y < (815 + 2 * gp.TileSize)) {
                this.chosedBuilding = 5; // Elektrownia wodna
            } else if (600 < mouse.x && mouse.x < 600 + gp.TileSize && 825 + 2 * gp.TileSize < mouse.y && mouse.y < (825 + 3 * gp.TileSize)) {
                this.chosedBuilding = 6; // Elektrownia węglowa
            }
        }
    }

    /**
     * Tworzy budynek w wybranym miejscu na mapie.
     * 
     * @param num Typ budynku do wybudowania.
     */
    public void makeBuilding(int num) {
        int xPosition;
        int yPosition;
        int cost;

        if (mouse.leftClickPressed && mouse.x < gp.screenWidth && mouse.y < (gp.screenHeight - 224)) {
            xPosition = mouse.x / 40;
            yPosition = mouse.y / 40;
            cost = gp.background.tile[num].cost;

            if (gp.resources.money >= cost) {
                if (num != 5 && gp.background.mapBackgroundNum[yPosition][xPosition] != 1 &&
                    gp.background.mapBackgroundNum[yPosition][xPosition] != 5 &&
                    num != gp.background.mapBackgroundNum[yPosition][xPosition]) {
                    gp.background.mapBackgroundNum[yPosition][xPosition] = num;
                    gp.resources.money -= cost;
                } else if (num == 5 && gp.background.mapBackgroundNum[yPosition][xPosition] == 1 &&
                           num != gp.background.mapBackgroundNum[yPosition][xPosition]) {
                    gp.background.mapBackgroundNum[yPosition][xPosition] = num;
                    gp.resources.money -= cost;
                }
            }
            mouse.leftClickPressed = false;
            System.out.println("Kliknięto na x:" + xPosition + " i y:" + yPosition);
        } else {
            mouse.leftClickPressed = false;
        }
    }
}
