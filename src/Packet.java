package src;

public class Packet {
    
    private int data;
    private int fromDeviceID, toDeviceID;
    private int fromGatewayID, toGatewayID;

    Packet(){

    }

    public int getData(){
        return data;
    }

    public void setData(int _data){
        data = _data;
    }

    public int getFromDeviceID(){
        return fromDeviceID;
    }

    public void setFromDeviceID(int _fromDeviceID){
        fromDeviceID = _fromDeviceID;
    }

    public int getToDeviceID(){
        return toDeviceID;
    }

    public void setToDeviceID(int _toDeviceID){
        toDeviceID = _toDeviceID;
    }

    public int getFromGatewayID(){
        return fromGatewayID;
    }

    public void setFromGatewayID(int _fromGatewayID){
        fromGatewayID = _fromGatewayID;
    }

    public int getToGatewayID(){
        return toGatewayID;
    }

    public void setToGatewayID(int _toGatewayID){
        toGatewayID = _toGatewayID;
    }
}
