package com.example.bkmiecik.playarena;

import android.widget.Chronometer;

/**
 * Created by bkmiecik on 17.07.17.
 */
public class Player {
    private String name;
    private int number;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
