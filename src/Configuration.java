package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static src.Constants.*;

public class Configuration {

    FileReader file;
    JSONObject jsonobj;
    private String username, password;

    public Configuration(String _username, String _password){
        username = _username;
        password = _password;
        initialise();
    }

    private void initialise(){
        JSONParser jsonparser = new JSONParser();
        try { file = new FileReader(CONFIGURATION_FILE_PATH); }
        catch(FileNotFoundException fileNotFoundException){
            System.out.println("Configuration file not found.");
        }
        try { jsonobj = (JSONObject)jsonparser.parse(file); }
        catch(Exception exception){
            jsonobj = null;
            System.out.println("Error while reading configurations. Error: " + exception);
        }
    }

    private String getTableName(){
        return (String) jsonobj.get(TABLE_NAME);
    }

    private String dateToday(){
        return String.format("%s", DateTimeFormatter.ofPattern("ddMMYYYY").format(LocalDate.now()));
    }

    private Map<String,String> getSchema (String key){
        Map<String,String> schema = new HashMap<>();
        JSONArray columns = (JSONArray) jsonobj.get(key);
        for (int j = 0; j < columns.size(); j++){
            try { 
                JSONObject columnName = (JSONObject) columns.get(j); 
                schema.put( (String)columnName.get(COLUMN_NAME), (String)columnName.get(DATATYPE));
            }
            catch (JSONException jsonException){
                System.out.println("JSON Exception.\nError: " + jsonException);
            }
        }
        return schema;
    }

    public String getCentralDatabaseName(){
        return (String) jsonobj.get(CENTRAL_DATABASE_NAME);
    }

    public String getCentralDatabaseTableName(){
        return getCentralDatabaseName() + "_" + getTableName() + "_" + dateToday();
    }

    public String getGatewayDatabaseName(int gatewayID){
        return (String) jsonobj.get(GATEWAY_DATABASE_NAME) + "_" + Integer.toString(gatewayID);
    }

    public String getGatewayDatabaseTableName(int gatewayID){
        return getGatewayDatabaseName(gatewayID) + "_" + getTableName() + "_" + dateToday();
    }

    public Map<String,String> getCentralDatabaseSchema(){
        return getSchema(CENTRAL_LEDGER_SCHEMA);
    }

    public Map<String,String> getGatewayDatabaseSchema(){
         return getSchema(GATEWAY_LEDGER_SCHEMA);
    }

    public String getUsername (){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public long getPortNumber(){
        return (long) jsonobj.get(PORT_NUMBER);
    }

    public int getNumberOfGateways(){
        return ((Long) jsonobj.get(NUMBER_OF_GATEWAYS)).intValue();
    }

    public int getNumberOfDevicesInGateway(){
        return ((Long) jsonobj.get(NUMBER_OF_DEVICES_IN_GATEWAY)).intValue();
    }
}