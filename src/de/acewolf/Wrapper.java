package de.acewolf;

public class Wrapper {

    private Device controller;

    private double lastRefreshed = 0;

    private DeviceStatus status;


    public Wrapper(Device controller){
        this.controller = controller;
        this.readControllerStatus();
    }


    public boolean readControllerStatus(){
        try {
            DeviceStatus status = this.controller.getStatus();
            this.status = status;
            this.lastRefreshed = System.nanoTime();
            return true;
        }catch (MHException ex){
            return false;
        }
    }


    public boolean setPower(boolean state)
    {
        try {
            if(state) {
                this.controller.turnOn();
            }else{
                this.controller.turnOff();
            }
            this.status.setOn(state);
            return true;
        }catch (MHException ex) {
            return false;
        }
    }


    public boolean setMode(int mode, int speed)
    {
        try {
            this.controller.sendPresetFunction(mode, speed);
            this.status.setMode(mode);
            this.status.setSpeed(speed);
            return true;
        }catch (MHException ex) {
            return false;
        }
    }

    public boolean setR(int r)
    {
        try {
            DeviceStatus newStatus = this.status;
            newStatus.setR(r);
            this.controller.updateDevice(newStatus);
            this.status = newStatus;
            return true;
        }catch (MHException ex) {
            return false;
        }
    }

    public boolean setG(int g)
    {
        try {
            DeviceStatus newStatus = this.status;
            newStatus.setG(g);
            this.controller.updateDevice(newStatus);
            this.status = newStatus;
            return true;
        }catch (MHException ex) {
            return false;
        }
    }

    public boolean setB(int b)
    {
        try {
            DeviceStatus newStatus = this.status;
            newStatus.setB(b);
            this.controller.updateDevice(newStatus);
            this.status = newStatus;
            return true;
        }catch (MHException $ex) {
            return false;
        }
    }


    public boolean setW1(int w1)
    {
        try {
            DeviceStatus newStatus = this.status;
            newStatus.setW1(w1);
            this.controller.updateDevice(newStatus);
            this.status = newStatus;
            return true;
        }catch (MHException ex) {
            return false;
        }
    }


    public boolean setW2(int w2)
    {
        try {
            DeviceStatus newStatus = this.status;
            newStatus.setW2(w2);
            this.controller.updateDevice(newStatus);
            this.status = newStatus;
            return true;
        }catch (MHException ex) {
            return false;
        }
    }


    public Device getController() {
        return controller;
    }

    public double getLastRefreshed() {
        return lastRefreshed;
    }

    public DeviceStatus getStatus() {
        return status;
    }
}
