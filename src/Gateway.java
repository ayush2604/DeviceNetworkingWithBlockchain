package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gateway{

    int gatewayID;
    private int numberOfConnectedDevices;
    Map<Integer,Device> connectedDevices;
    Map<Integer,Gateway> gateways;
    Configuration configuration;
    ArrayList<Block> blockchain;

    public Gateway(Configuration _configuration){
        connectedDevices = new HashMap<>();
        configuration = _configuration;
        numberOfConnectedDevices = 0;
        blockchain = new ArrayList<>();
        blockchain.add(new Block("dummy block","0"));
    }

    public void setListOfGateways(Map<Integer,Gateway> _gateways){
        gateways = _gateways;
    }

    public Map<Integer,Device> getConnectedDevices(){
        return connectedDevices;
    }

    public int getNumberOfConnectedDevices(){
        return numberOfConnectedDevices;
    }

    public void connectDevice(Device device){
        if (!connectedDevices.containsKey(device.getDeviceID())) {
            connectedDevices.put(device.getDeviceID(), device);
            numberOfConnectedDevices++;
        }   
    }

    public void disconnectDevice(Device device){
        if (connectedDevices.containsKey(device.getDeviceID())){
            connectedDevices.remove(device.getDeviceID());
            numberOfConnectedDevices--;
        }
    }

    public void sendPacketToDevice (Packet packet){
        if (this.connectedDevices.containsKey(packet.getToDeviceID())) {
            this.connectedDevices.get(packet.getToDeviceID()).recieveDataPacket(packet);
            String transaction = "Sending data from device " + packet.getFromDeviceID() + " to device " + packet.getToDeviceID();
            blockchain.add(new Block(transaction, this.blockchain.get(this.blockchain.size()-1).hash));
            boolean isValid = crypt.isChainValid(this.blockchain);
            //System.out.println(transaction);
        }
    }

    public void recievePacketFromDevice (Packet packet){
        sendPacketToGateway(packet);
        //update ledger here
        // gatewayDatabase.updateGatewayDataLedger(packet, configuration.getGatewayDatabaseTableName(this.getGatewayID()));
        String transaction = "Recieving data from device " + packet.getFromDeviceID() + " to device " + packet.getToDeviceID();
        boolean isValid = crypt.isChainValid(this.blockchain);
        blockchain.add(new Block(transaction, this.blockchain.get(this.blockchain.size()-1).hash));
        //System.out.println(transaction);
    }

    public void sendPacketToGateway (Packet packet){
        gateways.get(packet.getToGatewayID()).recievePacketFromGateway(packet);
    }

    public void recievePacketFromGateway (Packet packet){
        this.sendPacketToDevice(packet);
    }

    public void setGatewayID(int id){
        this.gatewayID = id;
    }

    public int getGatewayID(){
        return this.gatewayID;
    }

}