# Apollo's Lumina Aperio
# Introduction
<p>Lumina Aperio is a Heat Index Weather application based on Java. Explain niyo here yung app like ano mga features niya like to remind the user na ganto ganyan </p>


# Class Summaries
- **Class appGUI**
  <p>This is where the app's GUI is coded. It is also the class where the code for functionalities of text fields and buttons are coded.</p>
- **Class weatherForecast**
  <p>Basically, this class is used for getting and accessing the JSON informations about the weather and geolocation API. The informations are then returned to the class appGUI. Those informations are the Weather Condition, Temperature, Humidity, Heat Index, and the Recommendation for user.</p>

# Highlights
- **Heat Index**
  <p>The heat index is calculated using a combination of temperature and humidity values. The code first checks if the input temperature and humidity values fall within the predefined ranges. If not, it adjusts them to the nearest valid value. Then, it finds the indices of the nearest valid temperature and humidity values in the predefined arrays. Using these indices, it retrieves the corresponding heat index values from the Heat Index Table. Next, the code performs linear interpolation to calculate the heat index for the exact input temperature and humidity. Linear interpolation is used to estimate values that lie between two known values. Finally, the calculated heat index is classified into different categories based on its value.<p>

- **Heat Index Table**<p>
  ![heatindexchart](https://github.com/Allen-Pesigan/Apollo/assets/168507604/a9a33018-4ff3-45b4-8dd7-ba314fc52461)

  
- (palagay dito kung pano nakuha yung heat index, alross) isama mo na rin yung values or table ng heat index, pwede naman ata maglagay ng photo here search mo na lang how thru chatgpt

