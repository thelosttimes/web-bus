package cn.webbus.util;

import java.io.*;
import java.security.PublicKey;

public class FileUtil {
    public static String readAsString(File configFile){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile),"utf-8"));
            String result = null;
            StringBuilder sb = new StringBuilder();
            while ((result = bufferedReader.readLine()) != null){
                sb.append(result.trim().replace(" ",""));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
