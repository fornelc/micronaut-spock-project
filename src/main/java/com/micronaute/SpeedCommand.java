package com.micronaute;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class SpeedCommand {
    private int speed;

    public SpeedCommand(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}