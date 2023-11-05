import src.Cloud;
import src.Configuration;
import src.Login;
import src.Gateway;
import src.Device;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit; 

public class App extends Thread{

    private String username, password;
    Cloud cloud;
    Map<Integer,Gateway> gateways;
    Configuration configuration;
    
    App(){
        loginProcess();
        initialiseGateways();
    }

    private void loginProcess(){
        //Login login = new Login();
        // try {
        //     username = login.getUsername();
        //     password = login.getPassword();
        // } catch (InterruptedException interruptedException) {
        //     System.out.println("Error: " + interruptedException);
        // }
        configuration = new Configuration("root", "1Skghmhnnv.");
    }

    private void initialiseGateways(){
        gateways = new HashMap<>();
        for(int i = 0; i < configuration.getNumberOfGateways(); i++) {
            Gateway gateway = new Gateway(configuration);
            gateway.setGatewayID(i);
            gateways.put(gateway.getGatewayID(), gateway);
        }
        initialiseDevices();
    }

    private void initialiseDevices(){
        int deviceID = 0;
        for(Entry<Integer,Gateway> entry: gateways.entrySet()) {
            entry.getValue().setListOfGateways(gateways);
            for (int i = 0; i < configuration.getNumberOfDevicesInGateway(); i++) {
                Gateway gateway = entry.getValue();
                Device device = new Device(configuration);
                device.setDeviceID(deviceID++);
                device.setGatewayID(gateway.getGatewayID());
                device.getGateways(gateways);
                gateway.connectDevice(device);
            }
        }
        initialiseCloud();
    }

    private void initialiseCloud(){
        cloud = new Cloud(configuration, gateways);
    }

    private void printStatus() {
        System.out.println("App started.");
    }

    private void simulate(long startTime){
        for(Entry<Integer,Gateway> entry: gateways.entrySet()) {
            for(int i = 0; i < 256; i++) {
                for(Entry<Integer,Device> devices: entry.getValue().getConnectedDevices().entrySet()){
                    devices.getValue().sendDataPacket();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        long endTime = System.nanoTime();  
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    public static void main(String args[]){
        App app = new App();
        app.setDaemon(true);
        app.printStatus();
                    
        long startTime = System.nanoTime();     
        for(int i = 0; i < 12; i++) {
            new Thread(new Runnable(){
                public void run(){
                    app.simulate(startTime);
                }
            }).start();
        }
        
    }
}

