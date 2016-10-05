package de.acewolf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception{

        //5ECF7F224C1F

        ArrayList<DeviceFinderResult> results =  DeviceFinder.FindDevices(InetAddress.getByName("255.255.255.255"), 5);

        for (DeviceFinderResult result: results) {
            try {
                Device device = new Device(result.IpAddress, DeviceType.RGBWW);
                Wrapper controller = new Wrapper(device);
                controller.setPower(true);
                System.out.println(controller.getStatus().getR());
                System.out.println(controller.getStatus().getG());
                System.out.println(controller.getStatus().getB());
                controller.setMode(37, 10);
                controller.setPower(false);

            }catch (MHException ex){
                System.out.println(ex.getMessage());
            }
        }


    }
}
