package Utils;
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
        FileInputStream fis = null;

        try{
            String env = null;
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
                fis = new FileInputStream(path);
                property.load(fis);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }

        //getting the property value
    public static String getProperty(String key){
        if(Objects.isNull(property.getProperty(key)) || Objects.isNull(key)){
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
