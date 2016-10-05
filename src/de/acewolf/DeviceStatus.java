package de.acewolf;

public class DeviceStatus {
    private boolean on;
    private int mode;
    private int speed;
    private int r;
    private int g;
    private int b;
    private int w1;
    private int w2;

    public DeviceStatus(boolean on, int mode, int speed, int r, int g, int b, int w1, int w2){
        this.on = on;
        this.mode = mode;
        this.speed = speed;
        this.r = r;
        this.g = g;
        this.b = b;
        this.w1 = w1;
        this.w2 = w2;
    }

    public boolean isOn() {
        return on;
    }

    public int getMode() {
        return mode;
    }

    public int getSpeed() {
        return speed;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getW1() {
        return w1;
    }

    public int getW2() {
        return w2;
    }


    public void setOn(boolean on) {
        this.on = on;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setW1(int w1) {
        this.w1 = w1;
    }

    public void setW2(int w2) {
        this.w2 = w2;
    }

}
