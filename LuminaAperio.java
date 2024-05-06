import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.image.BufferedImage;
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
        conditionLabel.setBounds(100, 260, 245, 217);
        conditionLabel.setVisible(false);
        frame.add(conditionLabel);

        JLabel tempLabel = new JLabel();
        tempLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tempLabel.setBounds(100, 290, 245, 217);
        tempLabel.setVisible(false);
        frame.add(tempLabel);

        JButton switchTemp = new JButton("Convert?");
        switchTemp.setFont(new Font("Times New Roman", Font.BOLD, 20));
        switchTemp.setBounds(305, 390, 115, 20);
        switchTemp.setVisible(false);
        frame.add(switchTemp);

        JLabel humiLabel = new JLabel();
        humiLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        humiLabel.setBounds(100, 320, 245, 217);
        humiLabel.setVisible(false);
        frame.add(humiLabel);

        JLabel heatIndex = new JLabel();
        heatIndex.setFont(new Font("Times New Roman", Font.BOLD, 20));
        heatIndex.setBounds(100, 350, 245, 217);
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
                    JOptionPane.showMessageDialog(null, "Please enter a valid location.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                apiData = WeatherDataFetcher.getWeatherData(userInput);

                if (apiData != null) {
                    new WeatherInfoDisplay(apiData, weatherConditionImage, conditionLabel, tempLabel, humiLabel,
                            heatIndex, recommendation, switchTemp);
                } else {
                    JOptionPane.showMessageDialog(null, "No weather data found for location: " + userInput, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.add(searchButton);

        frame.setVisible(true);
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

class WeatherInfoDisplay {
    private JSONObject apiData;
    private boolean isOriginal = true;

    public WeatherInfoDisplay(JSONObject apiData, JLabel weatherConditionImage, JLabel conditionLabel,
                              JLabel tempLabel, JLabel humiLabel, JLabel heatIndex, JLabel recommendation,
                              JButton switchTemp) {
        this.apiData = apiData;

        updateWeatherInfo(apiData, weatherConditionImage, conditionLabel, tempLabel, humiLabel,
                heatIndex, recommendation, switchTemp);
    }

    private void updateWeatherInfo(JSONObject apiData, JLabel weatherConditionImage, JLabel conditionLabel,
                                   JLabel tempLabel, JLabel humiLabel, JLabel heatIndex, JLabel recommendation,
                                   JButton switchTemp) {
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

        double temperature = (double) apiData.get("Temperature");
        double fahrenheit = (temperature * 9/5) + 32;
        double celcius = (fahrenheit - 32) * 5/9;
        DecimalFormat decimal = new DecimalFormat("#0.00");
        String decimalF = decimal.format(fahrenheit);
        String decimalC = decimal.format(celcius);
        tempLabel.setText("Temperature: " + decimalF + " °F");
        tempLabel.setVisible(true);

        switchTemp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isOriginal){
                    tempLabel.setText("Temperature: " + decimalC + " °C");
                    isOriginal = false;
                } else {
                    tempLabel.setText("Temperature: " + decimalF + " °F");
                    isOriginal = true;
                }
            }
        });
        switchTemp.setVisible(true);

        conditionLabel.setText("Weather Condition: " + weatherCondition);
        conditionLabel.setVisible(true);

        long humidity = (long) apiData.get("Humidity");
        humiLabel.setText("Humidity:  " + humidity + "%");
        humiLabel.setVisible(true);

        double getTemp = Double.parseDouble(decimalF);
        double getHumidity = humidity;
        double heatIndexVal = HeatIndexCalculator.calculateHeatIndex(getTemp, getHumidity);
        String decimalHI = decimal.format(heatIndexVal);
        heatIndex.setText("Heat Index: " + decimalHI + "°");
        heatIndex.setVisible(true);

        String classification = HeatIndexClassifier.classifyHeatIndex(heatIndexVal);
        recommendation.setText(classification);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, classification, "Recommendation", JOptionPane.INFORMATION_MESSAGE);

            }
        }, 0, 10 * 1000); // for showcasing time
//        real display time
//        , 2 * 60 * 60 * 1000);
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

class WeatherDataFetcher {
    public static JSONObject getWeatherData(String location) {
        JSONArray locationData = getLocationData(location);

        if (locationData == null || locationData.isEmpty()) {
            return null;
        }

        JSONObject location1 = (JSONObject) locationData.get(0);
        double latitude = (double) location1.get("latitude");
        double longitude = (double) location1.get("longitude");

        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code&timezone=auto";

        try {
            HttpURLConnection connection = fetchAPI(urlString);
            if (connection.getResponseCode() != 200) {
                System.out.println("Can't connect to API");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner input = new Scanner(connection.getInputStream());
            while (input.hasNext()) {
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


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static JSONArray getLocationData(String location) {
        location = location.replaceAll(" ", "+");
        String urlAPI = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                location + "&count=1&language=en&format=json";
        try {
            HttpURLConnection connection = fetchAPI(urlAPI);

            if (connection.getResponseCode() != 200) {
                System.out.println("Unable to connect at API...");
                return null;
            } else {
                StringBuilder json = new StringBuilder();
                Scanner input = new Scanner(connection.getInputStream());
                while (input.hasNext()) {
                    json.append(input.nextLine());
                }

                input.close();
                connection.disconnect();

                JSONParser jsonParse = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParse.parse(String.valueOf(json));

                JSONArray locName = (JSONArray) jsonObj.get("results");
                return locName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchAPI(String urlAPI) {
        try {
            URL url = new URL(urlAPI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getCurrentTimeIndex(JSONArray timeArray) {
        String currentTime = getCurrentTime();

        for (int i = 0; i < timeArray.size(); i++) {
            String time = (String) timeArray.get(i);
            if (time.equalsIgnoreCase(currentTime)) {
                return i;
            }
        }

        return 0;
    }

    private static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formatted = currentDateTime.format(format);
        return formatted;
    }

    private static String convertWeatherCode(long weatherCode) {
        String weatherCondition = "";
        if (weatherCode == 0L) {
            weatherCondition = "Clear";
        } else if (weatherCode > 0L && weatherCode <= 3L) {
            weatherCondition = "Cloudy";
        } else if ((weatherCode >= 51L && weatherCode <= 67L) || (weatherCode >= 80L && weatherCode <= 99L)) {
            weatherCondition = "Rainy";
        } else if (weatherCode >= 71L && weatherCode <= 77L) {
            weatherCondition = "Snowy";
        }
        return weatherCondition;
    }
}

class HeatIndexCalculator {
    public static double calculateHeatIndex(double getTemp, double getHumidity) {
        double T = getTemp;
        double RH = getHumidity;

        double c1 = -42.379;
        double c2 = 2.04901523;
        double c3 = 10.14333127;
        double c4 = -0.22475541;
        double c5 = -6.83783 * Math.pow(10, -3);
        double c6 = -5.481717 * Math.pow(10, -2);
        double c7 = 1.22874 * Math.pow(10, -3);
        double c8 = 8.5282 * Math.pow(10, -4);
        double c9 = -1.99 * Math.pow(10, -6);

        double T2 = T * T;
        double RH2 = RH * RH;

        double heatIndex = c1 + c2 * T + c3 * RH + c4 * T * RH + c5 * T2 + c6 * RH2 + c7 * T2 * RH + c8 * T * RH2 + c9 * T2 * RH2;

        return Math.round(heatIndex * 100.0) / 100.0;
    }
}

class HeatIndexClassifier {
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

public class LuminaAperio {
    public static void main(String[] args) {
        new AppGUI();
    }
}