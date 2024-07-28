# IoT-Project
<h3>A "Water Quality Monitoring" IoT device collects water data parameters, namely, ph, Hardness,Solids,Chloramines,Sulfate,Conductivity,Organic Carbon,Trihalomethanes,Turbidity and Potability, through sensors and checks whether the water is potable or not. This repository
contains such codes which does that task.</h3>
<p>This repo is mainly divided into 3 sections:</p>
<ol> 
  <li>Arduino codes (WeMosD1) which reads the sensors data and transmit them to the database (Google Firebase). The file '.ino' belongs this section</li>
  <li>ML jupyter notebook codes containing a model which is trained using Random Forest. The dataset is taken from Kaggle ('Water Potability.csv'). The file '.ipynb' belongs this section</li>
  <li>Android codes (Java) which fetches water parameters data from "Google Firebase" and predicts whether the water is safe or not. The file '.java', '.xml' and '.tflite' belongs this section</li>
</ol>

<p>
  Sensors which are connected in this project are ph, turbidity and TDS. Since the "WeMosD1" microcontroller has only 1 analog input, a multiplexer is used
  to read the sensor values one at a time and then transfer them to the database "Google Firebae".
</p>
<p>
  The WeMosD1 micro-controller has to connect to any Wifi means. A Wifi SSID and password should be filled in the code before the microcontroller is ready to be deployed.
</p>
<p>
  To make the android app intelligent, an ML model, trained using "Random Forest classifier", is inbuilt into it.
</p>
<p>
  The android app page can allow 2 types of users:
  <ul>
    <li>System Admin: He/She can check all the water parameters and even know the status of the water.</li>
    <li>Normal user: He/She can only view some of the water parameters</li>
  </ul>
</p>
<p>
  The app allows registration of users whose personal data is saved in "Google Firebase". While logging in, it fetches users' data and
  try to see whether he/she is a system admin or normal user. Based on that, a particular android page will be displayed.
</p>

<h3>This repository also contains a .pdf file which is a report of this IoT project</h3>
