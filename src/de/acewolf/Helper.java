package de.acewolf;

import java.net.InetAddress;

class Helper extends Device {

    public Helper(InetAddress ip, DeviceType type) throws MHException {
        super(ip, type);
    }

    protected static byte checkNumberRange(int number) {
        if(number < 0) {
            return 0;
        } else if (number > 255) {
            return (byte)255;
        } else {
            return (byte)number;
        }
    }

    protected static byte calculateChecksum(byte[] bytes) {
        byte checksum = 0;

        for (byte _byte: bytes) {
            checksum += _byte;
        }

        return checksum;
    }



}
