package src;

import java.util.Map;

public class Cloud {
    
    Map<Integer,Gateway> gateways;
    int numberOfConnectedGateways;

    Configuration configuration;

    public Cloud(Configuration _configuration, Map<Integer, Gateway> _gateways){
        configuration = _configuration;
        numberOfConnectedGateways = 0;
        gateways = _gateways;
    }

    public void connectGateway(Gateway gateway){
        if (configuration.getNumberOfGateways() > numberOfConnectedGateways){
            gateways.put(gateway.getGatewayID(), gateway);
            gateway.setListOfGateways(gateways);
            numberOfConnectedGateways++;
        }
    }

    public void removeGateway(Gateway gateway){
        if (numberOfConnectedGateways > 0){
            if (gateways.containsKey(gateway.getGatewayID())) {
                gateways.remove(gateway.getGatewayID());
            }
        }
    }
 
}