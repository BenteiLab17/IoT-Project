# IoT-Project
<h2>A "Water Quality Monitoring" device collects water data parameters, namely, ph, Hardness,Solids,Chloramines,Sulfate,Conductivity,Organic Carbon,Trihalomethanes,Turbidity and Potability, through sensors and checks whether the water is potable or not. This repository
contains such codes which does that task.</h2>
<h3>This repo is mainly divided into 3 sections</h3>
<ol> 
  <li>Arduino codes (WeMosD1) which reads the sensors data and transmit them to the database (Google Firebase)</li>
  <li>ML jupyter notebook codes containing a model which is trained using Random Forest. The dataset is taken from Kaggle ('Water Potability.csv')</li>
  <li>Android codes (Java) which fetches water parameters data from "Google Firebase" and predicts whether the water is safe or not</li>
</ol>

<p>Sensors connected in this project are ph, turbidity and TDS. </p>
