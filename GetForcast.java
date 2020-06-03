import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetForcast {

    public static void main(String args[]){

        String urlString="https://api.weather.gov/points/"+getLatitudeAndLongitude();
        JSONParser parse = new JSONParser();
        try{
            JSONObject jobj_1=null;
            JSONArray jsonarr_1=null;
            JSONObject jobj = (JSONObject)parse.parse(getJSONResponse(urlString));
            if(jobj!=null)
                jobj_1 = (JSONObject) jobj.get("properties");
            if(jobj_1!=null)
                jobj=(JSONObject)parse.parse(getJSONResponse((String)jobj_1.get("forecast")));
            if(jobj!=null)
                jobj_1 = (JSONObject) jobj.get("properties");
            if(jobj_1!=null)
                jsonarr_1 = (JSONArray) jobj_1.get("periods");
            for (Object jsonobject:jsonarr_1) {
                System.out.println(((JSONObject)jsonobject).get("name") + " :: "+((JSONObject)jsonobject).get("detailedForecast"));

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public static String getLatitudeAndLongitude(){
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        //  prompt for the user's name
        System.out.print("Enter latitude: ");

        // get their input as a String
        StringBuffer coordinates = new StringBuffer(scanner.next());

        // prompt for their age
        System.out.print("Enter longitude: ");

        // get the age as an int
        String longitude = scanner.next();
        coordinates.append(",").append(longitude);

        return coordinates.toString();

    }

    public static String getJSONResponse(String urlString){

        String inline = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set the request to GET or POST as per the requirements
            conn.setRequestMethod("GET");
            //Use the connect method to create the connection bridge
            conn.connect();
            //Get the response status of the Rest API
            int responsecode = conn.getResponseCode();
            System.out.println("Response code is: " + responsecode);

            //Iterating condition to if response code is not 200 then throw a runtime exception
            //else continue the actual process of getting the JSON data
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                //Scanner functionality will read the JSON data from the stream
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                System.out.println("\nJSON Response in String format");
                System.out.println(inline);
                //Close the stream when reading the data has been finished
                sc.close();
                conn.disconnect();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return inline;

    }



}
