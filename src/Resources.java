/**
 * Klasa reprezentująca zasoby w grze.
 * Odpowiada za zarządzanie takimi zasobami jak pieniądze, szczęście, zanieczyszczenie, populacja oraz turą gry.
 */
public class Resources {
    int money, happines, pollution, population, currentTurn, powerConstumption, powerGenerated, powerBilans;
    Panel gp;

    /**
     * Konstruktor klasy Resources.
     * Inicjalizuje panel gry oraz ustawia początkową wartość zanieczyszczenia na 0.
     * 
     * @param gp Panel gry, który zawiera wszystkie niezbędne informacje o stanie gry.
     */
    public Resources(Panel gp){
        this.gp = gp;
        this.pollution = 0;
    }

    /**
     * Metoda ustawia trudność gry oraz początkowe wartości zasobów w zależności od wybranej trudności.
     * - Jeśli trudność to 1, początkowa ilość pieniędzy wynosi 1000.
     * - Jeśli trudność to 2, początkowa ilość pieniędzy wynosi 500.
     * - W przeciwnym razie (trudność 3) początkowa ilość pieniędzy wynosi 250.
     */
    public void setup_difficulty(){
        if(gp.difficulty == 1){
            money = 1000;
        }
        else if(gp.difficulty == 2){
            money = 500;
        }
        else{
            money = 250;
        }
        this.pollution = 0;
        this.currentTurn = 0;
        this.powerConstumption=0;
        this.powerGenerated=0;
        happines = 50;
        gp.buildState = false;
    }

    /**
     * Metoda aktualizuje stan zasobów gry na podstawie różnych warunków.
     * - Sprawdza, czy populacja, szczęście, zanieczyszczenie i pieniądze spełniają warunki zakończenia gry.
     * - Jeśli pieniądze spadną poniżej 0, gra kończy się.
     */
    public void update(){
        population = gp.building.houses * 100;
        powerConstumption=gp.building.houses*100+gp.building.markets*300;
        powerGenerated=gp.building.windPowerStation*100+gp.building.waterPowerStation*1000+gp.building.coalPowerStation*3000;
        if(population > 2000 && happines > 70 && pollution < 50 && money > 10000){
            gp.gameState = gp.mapFinishedState;
        }
        if(money < 0){
            gp.gameState = gp.gameFinished;
        }
    }

    /**
     * Metoda przechodzi do następnej tury w grze.
     * - Zwiększa turę o 1.
     * - Zaktualizowuje ilość pieniędzy w zależności od zbudowanych obiektów.
     * - Zwiększa zanieczyszczenie na podstawie liczby elektrowni węglowych.
     * - Zmienia szczęście w zależności od poziomu zanieczyszczenia.
     */
    public void nextTurn(){
        currentTurn += 1;
        money = money + gp.building.houses * 10 + gp.building.markets * 50 - gp.building.windPowerStation * 2 - gp.building.waterPowerStation * 20 - gp.building.coalPowerStation * 30;
        pollution += gp.building.coalPowerStation;
        powerBilans=powerConstumption-powerGenerated;
        if(pollution >= 50){
            happines -= 1;
        }
        else if(happines < 100 && pollution < 50){
            happines += 1;
        }
        if(powerBilans>0){
            money-=powerBilans*2;
        }
    }
}
