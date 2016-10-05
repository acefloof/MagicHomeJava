package de.acewolf;

import org.w3c.dom.Element;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

public class Device {
    private static int apiPort = 5577;
    private static int delay = 50; //milliseconds

    private InetAddress ip;
    private DeviceType type;
    private Socket socket;
    private OutputStream outStream;
    private InputStream inStream;

    public Device(InetAddress ip, DeviceType type) throws MHException{
        this.ip = ip;
        this.type = type;
        if(!this.connectToController()) throw new MHException("Could not connect to controller");
    }

    public DeviceStatus getStatus() throws MHException {

        if (type == DeviceType.RGBWWCW)
            throw new UnsupportedOperationException();

        byte[] message = new byte[] {(byte)0x81, (byte)0x8A, (byte)0x8B, (byte)0x96};

        byte[] response = SendMessage(message, false);

        if(response.length != 14)
            throw new MHException("Controller sent wrong number of bytes while getting status");

        return new DeviceStatus((response[2] & 0xFF) == 0x23,
                (response[3] & 0xFF),
                (response[5] & 0xFF),
                (response[6] & 0xFF),
                (response[7] & 0xFF),
                (response[8] & 0xFF),
                (response[9] & 0xFF),
                0);

    }

    public void turnOn() throws MHException{
        if (type == DeviceType.Bulb3)
            SendMessage(new byte[] {(byte)0xCC, (byte)0x23, (byte)0x33}, false);
        else
            SendMessage(new byte[] {(byte)0x71, (byte)0x23, (byte)0x0F, (byte)0xA3}, false);
    }

    public void turnOff() throws MHException{
        if (type == DeviceType.Bulb3)
            SendMessage(new byte[] {(byte)0xCC, (byte)0x24, (byte)0x33}, false);
        else
            SendMessage(new byte[] {(byte)0x71, (byte)0x24, (byte)0x0F, (byte)0xA4}, false);
    }

    public void sendPresetFunction(int mode, int speed) throws MHException {
        if (speed > 24)
            speed = 24;
        if (speed < 1)
            speed = 1;

        if (type == DeviceType.Bulb3)
        {
            SendMessage(new byte[] {(byte)0xBB, (byte)mode, (byte)speed, (byte)0x44}, false);
        }
        else
        {
            SendMessage(new byte[] {(byte)0x61, (byte)mode, (byte)speed, (byte)0x0F}, true);
        }
    }

    public void updateDevice(DeviceStatus status) throws MHException {
        switch (type){
            case RGB:
            case RGBWW:
                byte[] message = new byte[] {(byte)0x31,
                    Helper.checkNumberRange(status.getR()),
                    Helper.checkNumberRange(status.getG()),
                    Helper.checkNumberRange(status.getB()),
                    Helper.checkNumberRange(status.getW1()),
                        (byte)0x00, (byte)0x0f};
                SendMessage(message, true);
                break;
            case RGBWWCW:
                break;
            case Bulb4:
                break;
            case Bulb3:
                break;
            default:
                throw new MHException("Incompatible device type received");
        }
    }

    private boolean connectToController(){
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, apiPort), 1000);
            outStream = socket.getOutputStream();
            inStream = socket.getInputStream();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private byte[] SendMessage(byte[] bytes, boolean sendChecksum) throws MHException
    {
        try {

            if (sendChecksum)
            {
                byte checksum = Helper.calculateChecksum(bytes);
                bytes = Arrays.copyOf(bytes, bytes.length + 1);
                bytes[bytes.length - 1] = checksum;
            }

            byte[] buffer = new byte[2048];

            outStream.write(bytes);
            outStream.flush();
            Thread.sleep(delay);

            int readBytes = inStream.read(buffer);

            buffer = Arrays.copyOf(buffer, readBytes);

            return buffer;

        }catch (Exception ex){
            throw new MHException("Could not connect to controller");
        }
    }



}