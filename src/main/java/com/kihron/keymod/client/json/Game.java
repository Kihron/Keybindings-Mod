package com.kihron.keymod.client.json;

public class Game {

    private String gameString;
    private String translateString;

    public Game(String gameString, String translateString) {
        this.gameString = gameString;
        this.translateString = translateString;
    }

    @Override
    public String toString() {
        return "[gameString=" + gameString + ",translateString=" + translateString + "]";
    }

    public String getGameString() {
        return gameString;
    }

    public void setGameString(String gameString) {
        this.gameString = gameString;
    }

    public String getTranslateString() {
        return translateString;
    }

    public void setTranslateString(String translateString) {
        this.translateString = translateString;
    }
}
