package Utils.PropertyUtils;
import Enums.ConfigProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public final class ReadProperty{

    private ReadProperty(){

    }

    private static Properties property = new Properties();

    static{
            String env;
            String path = null;
                env = System.getProperty("env");
                if(env == null){
                    path = System.getProperty("user.dir") + "/resources/configuration/Data.properties";
                } else{
                    switch (env){
                        case "staging":
                            path = System.getProperty("user.dir") + "/resources/configuration/Qa.Data.properties";
                            break;

                        case "Production":
                            path = System.getProperty("user.dir") + "/resources/configuration/Prod.Data.properties";
                            break;
                        default:
                            System.out.println("Please pass the correct env value----> " + env);
                            break;
                    }
                }
                try(FileInputStream fis = new FileInputStream(path)){
                property.load(fis);
            } catch (IOException e) {
                    e.printStackTrace();
                }
    }

        //getting the property value as enum
    public static String getProperty(ConfigProperties key){
        if(Objects.isNull(property.getProperty(String.valueOf(key).toLowerCase())) || Objects.isNull(key)){
            throw new IllegalArgumentException("Property name = " + key + " is not found. Please check config.properties");
        }
      return property.getProperty(String.valueOf(key).toLowerCase());
    }

    //for string params
    public static String getProperty(String key){
        if(Objects.isNull(property.getProperty(String.valueOf(key).toLowerCase())) || Objects.isNull(key)){
            throw new IllegalArgumentException("Property name = " + key + " is not found. Please check config.properties");
        }
        return property.getProperty(key);
    }

    //setting property
    public static void setProperty(String key, String value){
//        if(Objects.isNull(prop.getProperty(key)) || Objects.isNull(key)){
//            throw new IllegalArgumentException("Property name = " + key + " is not found. Please check config.properties");
//        }
        property.setProperty(key,value);
    }

}
