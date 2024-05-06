# Apollo's Lumina Aperio
# Introduction
<p>Lumina Aperio is a Heat Index Weather application based on Java. This application is designed to keep users informed about heat index and potential health risks. It offers a user-friendly interface for easy location search and retrieval of comprehensive weather data, including real-time updates, detailed temperature, humidity, and heat index information. This comprehensive data empowers users to make informed decisions about outdoor activities and avoid potential health risks associated with high temperatures.

 Moreover, Lumina Aperio prioritizes user safety by going beyond just providing data. It incorporates real-time notifications that alert users to potential dangers from high heat indices. This empowers them to take appropriate precautions, such as applying sunscreen and staying hydrated, to mitigate the risks of heat-related illnesses. 

 In Essence, Lumina Aperio, meaning "Light Opener" in Latin, lives up to its name by illuminating the potential dangers of heat and empowering informed decisions. This Heat Index Weather application represents a paradigm shift in weather forecasting. Combining advanced technology with user-centric design, Lumina Aperio delivers a powerful tool that not only informs you about the weather but also protects your well-being.
</p>

# SDG (Good Health and Well-Being)
<p> Lumina Aperio, with its array of features and functionalities, significantly contributes to the sustainable development goals by advancing good health and well-being. Serving as a proactive tool amidst rising temperatures and extreme heat events, it offers real-time insights into the heat index and associated health risks, empowering users to make informed decisions for themselves and their loved ones. Through its user-friendly interface and tailored recommendations, Lumina Aperio not only raises awareness about high-temperature dangers but also provides practical guidance on mitigating risks like staying hydrated and applying sunscreen. Prioritizing user safety and health, the application directly promotes good health and well-being, ensuring individuals can thrive even in challenging environmental conditions. </p>

Here are the features of Lumina Aperio:

- **Sun Protection and Hydration Advisories:** Recommend sun protection methods like use of sunscreen, staying hydrated, and looking for shade in hours with high intensity of sun rays.

- **Real-Time Heat Index Calculation:** Provide the real-time calculation of the heat index by having algorithms with the capacity to utilize the temperature and humidity data.

- **Location-Based Weather Data:** Utilize a geolocation API or user input to get location-specific weather data that is accurate for the user's present position.

- **User-Friendly Interface:** Ensure user safety by providing and incorporating features that actively alert users for possible threats and allow the to take precautions.
-------
# Application GUI
- ![search](https://github.com/Allen-Pesigan/Apollo/assets/167183934/9656bcc7-e3db-44f5-a70b-980eb90f775a)
> Search for a location.

- ![result](https://github.com/Allen-Pesigan/Apollo/assets/167183934/d6bb4afa-6d18-4c92-8fb3-6c7e677d9eb2)
 > Result shown after search.

- ![result2](https://github.com/Allen-Pesigan/Apollo/assets/167183934/ec95a103-a9fa-4ab7-9829-65efcf7b5622)
  > Temperature unit conversion.

- ![alert](https://github.com/Allen-Pesigan/Apollo/assets/167183934/ccedd176-9109-4589-b3ab-288800292e3d)
  > Notifying the user.


# Class Summaries
- **Class LuminaAperio**
  <p>This class contains the main method and serves as the entry point of the application. It initializes the Graphical User Interface (GUI) by creating an instance of the AppGUI class, allowing users to interact with the weather information application.</p>
- **Class appGUI**
  <p>This class is responsible for creating the graphical user interface (GUI) of the application. It creates a window where users can input a location to get weather information. The GUI includes text fields, buttons, and labels to display weather data such as weather condition, temperature, humidity, heat index, and recommendations.</p>
- **Class WeatherInfoDisplay**
  <p>This class updates the weather information displayed on the GUI based on the user's input location. It retrieves weather data from the Open-Meteo API and updates the GUI with weather condition icons, temperature (in both Celsius and Fahrenheit), humidity, heat index, and recommendations. Additionally, it provides a functionality to switch between Celsius and Fahrenheit temperature units.</p>
- **Class WeatherDataFetcher**
  <p>This class serves to retrieve and access JSON information from the weather and geolocation APIs. The retrieved data, including weather condition, temperature, humidity, heat index, and user recommendations, is then passed back to the appGUI class. Additionally, this class manages the obtaining and calculating the heat index using the variables of temperature and humidity, aligning with the app's objective to warn the users about the current heat index.</p>
- **Class HeatIndexCalculator**
  <p>It contains the parameters for solving the heat index with the help of variables getTemp (Temperature data) and getHumidity (Humidity data). This is where the heat index is calculated then returns it on the ‘private void updateWeatherInfo ‘ from class appGUI.</p>
- **Class HeatIndexClassifier**
  <p>This class is responsible for categorizing the heat index into four categories: Caution, Extreme Caution, Danger, and Extreme Danger alert. It warns the user about the potential effects of each category of heat index and reminds them to apply and/or reapply sunscreen and stay hydrated.</p>

# Highlights
- **Heat Index**
  <p>The heat index is calculated using the Rothfusz regression equation, which takes into account temperature (in Fahrenheit) and relative humidity. The equation uses coefficients to calculate the heat index, which is a measure of how hot it really feels when relative humidity is factored in with the actual air temperature. The calculateHeatIndex method in the HeatIndexCalculator class takes temperature and humidity as input parameters and returns the calculated heat index.<p>

- **Heat Index Table**<p>
  ![heatindexchart](https://github.com/Allen-Pesigan/Apollo/assets/168507604/a9a33018-4ff3-45b4-8dd7-ba314fc52461)

- **Notifying the user**
  <p>Lumina Aperio includes a feature that reminds users to apply or reapply sunscreen and stay hydrated. This proactive measure aims to safeguard users from potential risks associated with prolonged exposure to extreme heat and sunlight.</p>

- JSON
  <p>JavaScript Object Notation (JSON) is a lightweight data format that can be easily read by humans. It typically consists of objects and arrays, which can be accessed through web applications with servers. This project fully utilizes JSON obtained from the Geolocation and Weather Forecast APIs to display necessary information in our application. A JSON object consists of key-value pairs, such as 'group: “Apollo”', which we use to retrieve information from the Weather Forecast API. On the other hand, a JSON array is an ordered set of values, like '[Allen, Alross, Jenny, Francine, Eunice]', and we use arrays to obtain the overall set of location information from the Geolocation API.</p>

# Links
- Weather Forecast API
  https://open-meteo.com/en/docs#latitude=33.767&longitude=-118.1892
- Geolocation API
  https://open-meteo.com/en/docs/geocoding-api

