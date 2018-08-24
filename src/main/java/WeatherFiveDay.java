import org.json.JSONArray;
import org.json.JSONObject;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherFiveDay {
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://samples.openweathermap.org/data/2.5/forecast?q=" + message + "&appid=8f1a33a55508e0ae4b6de00abd059628");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);


        JSONArray getArray = object.getJSONArray("list");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject main = object.getJSONObject("main");
            model.setTemp(main.getDouble("temp"));
            JSONObject obj = getArray.getJSONObject(i);
            model.setDate(obj.getString("dt_txt"));
            JSONArray getArray2 = object.getJSONArray("weather");
            for (int j = 0; j < getArray2.length(); j++) {
                JSONObject obj2 = getArray2.getJSONObject(j);
                model.setIcon((String) obj2.get("icon"));
                model.setMain((String) obj2.get("main"));
            }
        }

        return model.getSpeed() + model.getTemp() + model.getDate() + model.getIcon();
    }
}
