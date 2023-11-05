package src;

import java.util.Map;
import java.util.Random;

public class Device{

    private int deviceID;
    private int gatewayID;
    private Packet dataPacket;
    private Map<Integer,Gateway> gateways;
    private Configuration configuration;
    
    public Device(Configuration _configuration){
        dataPacket = new Packet();
        configuration = _configuration;
    }

    public void getGateways(Map<Integer,Gateway> gateway){
        gateways = gateway;
    }

    public void connectToGateway(){
        for (Map.Entry<Integer,Gateway> entry : gateways.entrySet()){
            if (entry.getValue().getNumberOfConnectedDevices() < configuration.getNumberOfDevicesInGateway()) {
                entry.getValue().connectDevice(this);
                this.setGatewayID(entry.getValue().getGatewayID());
            }
        }
    }

    public void disconnectFromGateway(){
        if (gateways.containsKey(gatewayID)) {
            gateways.get(this.getGatewayID()).disconnectDevice(this);
        }
    }

    public void setDeviceID(int deviceID){
        this.deviceID = deviceID;
    }

    public int getDeviceID(){
        return deviceID;
    }

    public void setGatewayID(int gatewayID){
        this.gatewayID = gatewayID;
    }

    public int getGatewayID(){
        return gatewayID;
    }

    private void generateRandomPacket(){
        dataPacket.setData(new Random().nextInt(1024));
        dataPacket.setFromDeviceID(this.getDeviceID());
        dataPacket.setFromGatewayID(this.getGatewayID());
        int number = 0;
        while(true){
            number = new Random().nextInt(configuration.getNumberOfGateways());
            if (number != dataPacket.getFromGatewayID()) break;
        }
        dataPacket.setToGatewayID(number);
        Random generator = new Random();
        Object[] values = gateways.get(number).getConnectedDevices().values().toArray();
        Device randomValue = (Device) values[generator.nextInt(values.length)];
        dataPacket.setToDeviceID(randomValue.getDeviceID());

    }

    public void sendDataPacket(){
        generateRandomPacket();
        gateways.get(this.getGatewayID()).recievePacketFromDevice(this.dataPacket);
    }

    public void recieveDataPacket(Packet _dataPacket){
        this.dataPacket = _dataPacket;
    }
    
}