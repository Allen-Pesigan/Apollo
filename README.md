# Apollo's Lumina Aperio
# Introduction
<p>Lumina Aperio is a Heat Index Weather application based on Java. This application is designed to keep users informed about heat index and potential health risks. It offers a user-friendly interface for easy location search and retrieval of comprehensive weather data, including real-time updates, detailed temperature, humidity, and heat index information. This comprehensive data empowers users to make informed decisions about outdoor activities and avoid potential health risks associated with high temperatures.

 Moreover, Lumina Aperio prioritizes user safety by going beyond just providing data. It incorporates real-time notifications that alert users to potential dangers from high heat indices. This empowers them to take appropriate precautions, such as applying sunscreen and staying hydrated, to mitigate the risks of heat-related illnesses. 

 In Essence, Lumina Aperio, meaning "Light Opener" in Latin, lives up to its name by illuminating the potential dangers of heat and empowering informed decisions. This Heat Index Weather application represents a paradigm shift in weather forecasting. Combining advanced technology with user-centric design, Lumina Aperio delivers a powerful tool that not only informs you about the weather but also protects your well-being.
</p>

#Sustainable Development Goals
<p>  Lumina Aperio has several features and functionalities that can contribute to the sustainable development goals. This makes a significant strides toward advancing Good health and Well-being by serving as a proactive tool for safeguarding individuals' health and well being in the face of rising temperatures and extreme heat events. By offering real time insights into the heat index and associated health risks, the application empowers users to make informed decisions to protect themselves and their loved ones. 

Through its user friendly interface and tailored recommendations, Lumina Aperio not only raises awareness about the potential dangers of high temperatures but also provides practical guidance on mitigating these risks, such as staying hydrated and applying sunscreen. By prioritizing user safety and health, the application contributes directly to the promotion of good health and well-being, ensuring that individuals can thrive even in challenging environmental conditions. </p>

# Application GUI
- ![search](https://github.com/Allen-Pesigan/Apollo/assets/167183934/940f01bb-cefc-4334-acee-32a1489ba42d)
> Search for a location.

- ![result](https://github.com/Allen-Pesigan/Apollo/assets/167183934/c72864ed-75b2-4036-be4a-cef492528535)
 > Result shown after search.

- ![alert](https://github.com/Allen-Pesigan/Apollo/assets/167183934/fa716457-889a-4e9b-bb70-ae2646e88093)
  > Notifying the user.


# Class Summaries
- **Class appGUI**
  <p>This is the class where the app's GUI is coded, as well as the class responsible for implementing the functionalities of labels, text fields, and buttons. Within this class, the results returned by the weatherForecast class are also handled and displayed after the user enters a location.</p>
- **Class weatherForecast**
  <p>This class serves to retrieve and access JSON information from the weather and geolocation APIs. The retrieved data, including weather condition, temperature, humidity, heat index, and user recommendations, is then passed back to the appGUI class. Additionally, this class manages the obtaining and calculating the heat index using the variables of temperature and humidity, aligning with the app's objective to warn the users about the current heat index.</p>

# Highlights
- **Heat Index**
  <p>The heat index is calculated using the Rothfusz regression equation, which takes into account temperature (in Fahrenheit) and relative humidity. The equation uses coefficients to calculate the heat index, which is a measure of how hot it really feels when relative humidity is factored in with the actual air temperature. The getHeatIndex method in the weatherForecast class takes temperature and humidity as input parameters and returns the calculated heat index. This heat index is then used to classify the level of heat risk, providing recommendations accordingly.<p>

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

