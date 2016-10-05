package de.acewolf;

import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DeviceFinder {


    private static int BroadcastPort = 48899;

    public static ArrayList<DeviceFinderResult> FindDevices(InetAddress endPoint, int timeout) throws Exception
    {
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(timeout);

        InetSocketAddress address = new InetSocketAddress(endPoint, BroadcastPort);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(200);

        ArrayList<DeviceFinderResult> result = new ArrayList<DeviceFinderResult>();
        byte[] message = new byte[] {(byte)0x48, (byte)0x46, (byte)0x2d, (byte)0x41, (byte)0x31, (byte)0x31, (byte)0x41, (byte)0x53, (byte)0x53, (byte)0x49, (byte)0x53, (byte)0x54, (byte)0x48, (byte)0x52, (byte)0x45, (byte)0x41, (byte)0x44};
        byte[] buffer = new byte[64];
        while (endTime.isAfter(LocalDateTime.now()))
        {
            DatagramPacket packet = new DatagramPacket(message, message.length, address);
            socket.send(packet);
            while (endTime.isAfter(LocalDateTime.now()))
            {
                int numBytes;
                try
                {
                    DatagramPacket rcvBuff = new DatagramPacket(buffer, buffer.length);
                    socket.receive(rcvBuff);
                    numBytes = rcvBuff.getLength();
                }
                catch (SocketTimeoutException ex)
                {
                    break;
                }

                String response = new String(buffer, 0, numBytes);

                String[] splitResponse = response.split(",");

                if (splitResponse.length != 3)
                    continue;

                DeviceFinderResult res = new DeviceFinderResult();
                res.IpAddress = InetAddress.getByName(splitResponse[0]);
                res.MacAddress = splitResponse[1];
                res.Model = splitResponse[2];
                if(!result.stream().anyMatch(x -> x.MacAddress.equals(res.MacAddress)))
                    result.add(res);
            }
        }

        return result;
    }
}
