# Apollo's Lumina Aperio
# Introduction
<p>Lumina Aperio is a Heat Index Weather application based on Java. This application is designed to keep users informed about heat index and potential health risks. It offers a user-friendly interface for easy location search and retrieval of comprehensive weather data, including real-time updates, detailed temperature, humidity, and heat index information. This comprehensive data empowers users to make informed decisions about outdoor activities and avoid potential health risks associated with high temperatures.

 Moreover, Lumina Aperio prioritizes user safety by going beyond just providing data. It incorporates real-time notifications that alert users to potential dangers from high heat indices. This empowers them to take appropriate precautions, such as applying sunscreen and staying hydrated, to mitigate the risks of heat-related illnesses. 

 In Essence, Lumina Aperio, meaning "Light Opener" in Latin, lives up to its name by illuminating the potential dangers of heat and empowering informed decisions. This Heat Index Weather application represents a paradigm shift in weather forecasting. Combining advanced technology with user-centric design, Lumina Aperio delivers a powerful tool that not only informs you about the weather but also protects your well-being.
</p>

# Application GUI
- ![search](https://github.com/Allen-Pesigan/Apollo/assets/167183934/940f01bb-cefc-4334-acee-32a1489ba42d)
> Search for a location.

- ![result](https://github.com/Allen-Pesigan/Apollo/assets/167183934/c72864ed-75b2-4036-be4a-cef492528535)
 > Result shown after search.

- ![alert](https://github.com/Allen-Pesigan/Apollo/assets/167183934/fa716457-889a-4e9b-bb70-ae2646e88093)
  > Notifying the user.



# Class Summaries
- **Class appGUI**
  <p>This is the class where the app's GUI is coded, as well as the class responsible for implementing the functionalities of labels, text fields, and buttons. Within this class, the results returned by the *weatherForecast* class are also handled and displayed after the user enters a location.</p>
- **Class weatherForecast**
  <p>This class serves to retrieve and access JSON information from the weather and geolocation APIs. The retrieved data, including weather condition, temperature, humidity, heat index, and user recommendations, is then passed back to the *appGUI* class. Additionally, this class manages the parameters required for obtaining and calculating the heat index, aligning with the app's objectives</p>

# Highlights
- **Heat Index**
  <p>The heat index is calculated using a combination of temperature and humidity values. The code first checks if the input temperature and humidity values fall within the predefined ranges. If not, it adjusts them to the nearest valid value. Then, it finds the indices of the nearest valid temperature and humidity values in the predefined arrays. Using these indices, it retrieves the corresponding heat index values from the Heat Index Table. Next, the code performs linear interpolation to calculate the heat index for the exact input temperature and humidity. Linear interpolation is used to estimate values that lie between two known values. Finally, the calculated heat index is classified into different categories based on its value.<p>

- **Heat Index Table**<p>
  ![heatindexchart](https://github.com/Allen-Pesigan/Apollo/assets/168507604/a9a33018-4ff3-45b4-8dd7-ba314fc52461)

- **Notifying the user**
  <p>Lumina Aperio includes a feature that reminds users to apply or reapply sunscreen and stay hydrated. This proactive measure aims to safeguard users from potential risks associated with prolonged exposure to extreme heat and sunlight.</p>

