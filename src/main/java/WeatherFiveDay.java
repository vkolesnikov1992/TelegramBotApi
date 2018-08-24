import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.Scanner;

public class WeatherFiveDay {

    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://samples.openweathermap.org/data/2.5/forecast?q=" + message + "&appid=8f1a33a55508e0ae4b6de00abd059628");
        String finalResult = "";
        String head = "Дата     Температура     Погода     Ветер";

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);


        JSONArray getArray = object.getJSONArray("list");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject mainObj = getArray.getJSONObject(i);
            JSONObject main = mainObj.getJSONObject("main");
            JSONObject wind = mainObj.getJSONObject("wind");
            model.setTemp(main.getDouble("temp"));
            model.setDate(mainObj.getString("dt_txt"));
            model.setSpeed(wind.getDouble("speed"));
            JSONArray getArray2 = mainObj.getJSONArray("weather");
            for (int j = 0; j < getArray2.length(); j++) {
                JSONObject obj2 = getArray2.getJSONObject(j);
                model.setIcon((String) obj2.get("icon"));
                model.setMain((String) obj2.get("main"));
            }
            finalResult = finalResult + model.getDate() + "    " + Math.round((model.getTemp() - 32) * 5/9) + "   " + model.getMain()+ "   " + model.getSpeed() + "\n";
        }


        return head + "\n" + finalResult;
    }
}
