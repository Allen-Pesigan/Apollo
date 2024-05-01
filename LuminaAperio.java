import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Timer;
import java.util.TimerTask;

public class LuminaAperio {

    public static void main(String[] args) {
        new AppGUI();
    }
}

class AppGUI {
    private JSONObject apiData;

    public AppGUI() {
        JFrame frame = new JFrame("Lumina Aperio");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JTextField search = new JTextField();
        search.setBounds(70, 40, 300, 40);
        search.setFont(new Font("Times New Roman", Font.BOLD, 20));
        frame.add(search);

        JLabel weatherConditionImage = new JLabel();
        weatherConditionImage.setBounds(110, 120, 245, 217);
        frame.add(weatherConditionImage);

        JLabel conditionLabel = new JLabel();
        conditionLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        conditionLabel.setBounds(150, 260, 245, 217);
        conditionLabel.setVisible(false);
        frame.add(conditionLabel);

        JLabel tempLabel = new JLabel();
        tempLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tempLabel.setBounds(150, 290, 245, 217);
        tempLabel.setVisible(false);
        frame.add(tempLabel);

        JLabel humiLabel = new JLabel();
        humiLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        humiLabel.setBounds(150, 320, 245, 217);
        humiLabel.setVisible(false);
        frame.add(humiLabel);

        JLabel heatIndex = new JLabel();
        heatIndex.setFont(new Font("Times New Roman", Font.BOLD, 20));
        heatIndex.setBounds(150, 350, 245, 217);
        heatIndex.setVisible(false);
        frame.add(heatIndex);

        JLabel recommendation = new JLabel();
        recommendation.setFont(new Font("Times New Roman", Font.BOLD, 20));
        recommendation.setBounds(0, 380, 500, 217);
        recommendation.setVisible(false);
        frame.add(recommendation);

        JButton searchButton = new JButton(fileImage("src/assets/search.png"));
        searchButton.setBounds(375, 40, 40, 40);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = search.getText();
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                apiData = weatherForecast.getWeatherData(userInput);

                if (apiData != null) {
                    updateWeatherInfo(apiData, weatherConditionImage, conditionLabel, tempLabel, humiLabel,
                            heatIndex, recommendation);
                } else {
                    System.out.println("No weather data found for location: " + userInput);
                }
            }
        });
        frame.add(searchButton);

        frame.setVisible(true);
    }

    private void updateWeatherInfo(JSONObject apiData, JLabel weatherConditionImage, JLabel conditionLabel,
                                   JLabel tempLabel, JLabel humiLabel, JLabel heatIndex, JLabel recommendation) {
        String weatherCondition = (String) apiData.get("Weather Condition");
        switch (weatherCondition) {
            case "Clear":
                weatherConditionImage.setIcon(fileImage("src/assets/clear.png"));
                break;
            case "Cloudy":
                weatherConditionImage.setIcon(fileImage("src/assets/cloudy.png"));
                break;
            case "Rainy":
                weatherConditionImage.setIcon(fileImage("src/assets/rain.png"));
                break;
            case "Snowy":
                weatherConditionImage.setIcon(fileImage("src/assets/snow.png"));
                break;
        }

        /// display
        double temperature = (double) apiData.get("Temperature");
        double fahrenheit = (temperature * 9/5) + 32;
        DecimalFormat decimal = new DecimalFormat("#0.00");
        String decimalF = decimal.format(fahrenheit);
        tempLabel.setText("Temperature: " + decimalF + " °F");
        tempLabel.setVisible(true);

        conditionLabel.setText("Weather Condition: " + weatherCondition);
        conditionLabel.setVisible(true);

        long humidity = (long) apiData.get("Humidity");
        humiLabel.setText("Humidity:  " + humidity + "%");
        humiLabel.setVisible(true);

        //heat index
        double getTemp = Double.parseDouble(decimalF);
        double getHumidity = humidity;
        double heatIndexVal = weatherForecast.getHeatIndex(getTemp, getHumidity);
        String decimalHI = decimal.format(heatIndexVal);
        heatIndex.setText("Heat Index: " + decimalHI + "°");
        heatIndex.setVisible(true);

        String classification = weatherForecast.classifyHeatIndex(heatIndexVal);
        recommendation.setText(classification);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, classification, "Recommendation", JOptionPane.INFORMATION_MESSAGE);

            }
        }, 0, 10 * 1000);


    }

    private ImageIcon fileImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image not found!");
            return null;
        }
    }
}

class weatherForecast extends AppGUI {
    public static JSONObject getWeatherData(String location) {
        JSONArray locationData = getLocationData(location);

        JSONObject location1 = (JSONObject) locationData.get(0);
        double latitude = (double) location1.get("latitude");
        double longitude = (double) location1.get("longitude");

        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code&timezone=auto";

        try{
            HttpURLConnection connection = fetchAPI(urlString);
            if(connection.getResponseCode() != 200){
                System.out.println("Can't connect to API");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner input = new Scanner(connection.getInputStream());
            while(input.hasNext()){
                resultJson.append(input.nextLine());
            }
            input.close();
            connection.disconnect();

            JSONParser parse = new JSONParser();
            JSONObject obJson = (JSONObject) parse.parse(String.valueOf(resultJson));

            JSONObject hourly = (JSONObject) obJson.get("hourly");
            JSONArray time = (JSONArray) hourly.get("time");
            int index = getCurrentTimeIndex(time);

            JSONArray dataTemp = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) dataTemp.get(index);

            JSONArray weatherCode = (JSONArray) hourly.get("weather_code");
            String weather = convertWeatherCode((long) weatherCode.get(index));

            JSONArray relHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relHumidity.get(index);

            JSONObject apiData = new JSONObject();
            apiData.put("Temperature", temperature);
            apiData.put("Weather Condition", weather);
            apiData.put("Humidity", humidity);

            return apiData;


        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static JSONArray getLocationData(String location) {
        location = location.replaceAll(" ", "+");
        String urlAPI = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                location + "&count=1&language=en&format=json";
        try{
            HttpURLConnection connection = fetchAPI(urlAPI);

            if(connection.getResponseCode() != 200){
                System.out.println("Unable to connect at API...");
                return null;
            } else{
                StringBuilder json = new StringBuilder();
                Scanner input = new Scanner(connection.getInputStream());
                while(input.hasNext()){
                    json.append(input.nextLine());
                }

                input.close();
                connection.disconnect();

                JSONParser jsonParse = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParse.parse(String.valueOf(json));

                JSONArray locName = (JSONArray) jsonObj.get("results");
                return locName;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchAPI(String urlAPI) {
        try{
            URL url = new URL(urlAPI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();
            return connection;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static int getCurrentTimeIndex(JSONArray timeArray){
        String currentTime = getCurrentTime();

        for(int i = 0; i < timeArray.size(); i++){
            String time = (String) timeArray.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                return i;
            }
        }

        return 0;
    }

    private static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formatted = currentDateTime.format(format);
        return formatted;
    }

    private static String convertWeatherCode(long weatherCode){
        String weatherCondition = "";
        if(weatherCode == 0L){
            weatherCondition = "Clear";
        }else if (weatherCode > 0L && weatherCode <= 3L){
            weatherCondition = "Cloudy";
        }else if((weatherCode >= 51L && weatherCode <= 67L) || (weatherCode >= 80L && weatherCode <=99L)){
            weatherCondition = "Rainy";
        }else if(weatherCode >= 71L && weatherCode <= 77L){
            weatherCondition = "Snowy";
        }
        return weatherCondition;
    }

    private static final int[][] heatIndexTable = {
            {80, 81, 83, 85, 88, 91, 94, 97, 101, 105, 109, 114, 119, 124, 130, 136},
            {80, 82, 84, 87, 89, 93, 96, 100, 104, 109, 114, 119, 124, 130, 137, 142},
            {81, 83, 85, 88, 91, 95, 99, 103, 108, 113, 118, 124, 131, 137, 144, 151},
            {81, 84, 86, 89, 93, 97, 101, 106, 112, 117, 124, 130, 137, 144, 151, 158},
            {82, 84, 88, 91, 95, 100, 105, 110, 116, 123, 129, 137, 144, 151, 158, 165},
            {82, 85, 89, 91, 98, 103, 108, 114, 121, 128, 136, 143, 150, 157, 164, 171},
            {83, 86, 90, 95, 100, 105, 112, 119, 126, 134,141, 148, 155, 162, 169, 176},
            {84, 88, 92, 97, 103, 109, 116, 124, 132, 139, 146, 153, 160, 167, 174, 181},
            {84, 89, 94, 100, 106, 113, 121, 129, 136, 143, 150, 157, 164, 171, 178, 185},
            {85, 90, 96, 102, 110, 117, 126, 135, 142, 149, 156, 163, 170, 177, 184, 191},
            {86, 91, 98, 105, 113, 122, 131, 138, 145, 152, 159, 166, 173, 180, 187, 194},
            {86, 93, 100, 108, 117, 127, 134, 141, 148, 155, 162, 169, 176, 184, 191, 198},
            {87, 95, 103, 112, 121, 132, 139, 146, 153, 160, 167, 174, 181, 188, 195, 202},
    };

    private static final int[] temperatures = {80, 82, 84, 86, 88, 90, 92, 94, 96, 98, 100, 102, 104, 106, 108, 110};
    private static final int[] humidities = {40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};

    public static double getHeatIndex(double getTemp, double getHumidity) {
        if (getTemp < temperatures[0]) {
            getTemp = temperatures[0];
        } else if (getTemp > temperatures[temperatures.length - 1]) {
            getTemp = temperatures[temperatures.length - 1];
        }

        if (getHumidity < humidities[0]) {
            getHumidity = humidities[0];
        } else if (getHumidity > humidities[humidities.length - 1]) {
            getHumidity = humidities[humidities.length - 1];
        }

        int[] tempIndices = findNearestIndices(temperatures, getTemp);
        int tempLowerIndex = tempIndices[0];
        int tempUpperIndex = tempIndices[1];

        int[] humidityIndices = findNearestIndices(humidities, getHumidity);
        int humidityLowerIndex = humidityIndices[0];
        int humidityUpperIndex = humidityIndices[1];


        double heatIndex00 = heatIndexTable[humidityLowerIndex][tempLowerIndex];
        double heatIndex01 = heatIndexTable[humidityLowerIndex][tempUpperIndex];
        double heatIndex10 = heatIndexTable[humidityUpperIndex][tempLowerIndex];
        double heatIndex11 = heatIndexTable[humidityUpperIndex][tempUpperIndex];

        double heatIndex0 = linearInterpolate(getTemp, temperatures[tempLowerIndex], temperatures[tempUpperIndex], heatIndex00, heatIndex01);
        double heatIndex1 = linearInterpolate(getTemp, temperatures[tempLowerIndex], temperatures[tempUpperIndex], heatIndex10, heatIndex11);

        return linearInterpolate(getHumidity, humidities[humidityLowerIndex], humidities[humidityUpperIndex], heatIndex0, heatIndex1);
    }

    private static int[] findNearestIndices(int[] array, double value) {
        int lowerIndex = -1;
        int upperIndex = -1;

        for (int i = 0; i < array.length - 1; i++) {
            if (value >= array[i] && value <= array[i + 1]) {
                lowerIndex = i;
                upperIndex = i + 1;
                break;
            }
        }

        if (lowerIndex == -1) {
            if (value < array[0]) {
                lowerIndex = 0;
                upperIndex = 0;
            } else if (value > array[array.length - 1]) {
                lowerIndex = array.length - 1;
                upperIndex = array.length - 1;
            }
        }

        return new int[]{lowerIndex, upperIndex};
    }

    private static double linearInterpolate(double x, double x1, double x2, double y1, double y2) {
        return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
    }
    public static String classifyHeatIndex(double heatIndex) {
        if (heatIndex >= 80 && heatIndex < 90) {
            return "<html><font face = 'Arial'><i>Caution:</i> Fatigue possible with prolonged exposure and/or physical activity." +
                    "<br><center>Please apply your sunscreen and stay hydrated!</center></font></html>";
        } else if (heatIndex >= 90 && heatIndex < 103) {
            return "<html><font face = 'Arial'><i>Extreme Caution:</i> Heat stroke, heat cramps, or heat exhaustion possible with prolonged exposure and/or physical activity." +
                    "<br><center>Please apply your sunscreen and stay hydrated!</center></font></html>";
        } else if (heatIndex >= 103 && heatIndex < 124) {
            return "<html><font face = 'Arial'><i>Danger:</i> Heat stroke, heat cramps, or heat exhaustion possible with prolonged exposure and/or physical activity." +
                    "<br><center>Please apply your sunscreen and stay hydrated!</center></font></html>";
        } else if (heatIndex >= 124) {
            return "<html><font face = 'Arial'><i>Extreme Danger:</i> Heat stroke highly likely." +
                    "<br><center>Please apply your sunscreen and stay hydrated!</center></font></html>";
        } else {
            return null;
        }
    }



}
