#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>
#include <GravityTDS.h>
#include<EEPROM.h>

// WiFi network credentials
#define Wifi_SSID "" //wifi network name
#define Wifi_PASSWORD "" //Wifi password

// Firebase project credentials
#define FIREBASE_HOST "" //My url to the database
#define FIREBASE_AUTH "" //My Key to connect

// Multiplexer Selector and OutputPIN
#define S0 D3
#define S1 D4
#define S2 D5
#define S3 D6
#define SIG A0 //Output from the Mux will go through the A0 pin on WeMosD1

//TDS sensor declarations
GravityTDS gravityTds;
float temperature = 25;

//Declaring an object of class FirebaseData
FirebaseData firebaseData; 

// initialize WiFi client
WiFiClient client;

void setup() 
{
  Serial.begin(115200);
  
  // connect to WiFi network
  WiFi.begin(Wifi_SSID, Wifi_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi.");

  // initialize Firebase connection
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  Serial.println("Connected to Firebase.");

  // setting pinMode for the selectors and SIG pin
  pinMode(S0, OUTPUT);
  pinMode(S1, OUTPUT);
  pinMode(S2, OUTPUT);
  pinMode(S3, OUTPUT);
  pinMode(SIG, INPUT);

  // setting TDS sensor
  gravityTds.setPin(SIG);
  gravityTds.setAref(5.0);  //reference voltage on ADC, default 5.0V on Arduino UNO
  gravityTds.setAdcRange(1024);  //1024 for 10bit ADC;4096 for 12bit ADC
  gravityTds.begin();  //initialization
}

void loop() 
{
  // read data from sensors
  // Channel 0 is for Turbidity sensor. 0000 is for channel 0
  digitalWrite(S0, LOW);
  digitalWrite(S1, LOW);
  digitalWrite(S2, LOW);
  digitalWrite(S3, LOW);
  float ntuVal = turbiditySensor();

  // Channel 5 is for pH sensor. 1010 is for channel 5
  digitalWrite(S0, HIGH);
  digitalWrite(S1, LOW);
  digitalWrite(S2, HIGH);
  digitalWrite(S3, LOW);
  float phVal = phSensor();

  // Channel 15 is for TDS sensor. 1111 is for channel 15
  digitalWrite(S0, HIGH);
  digitalWrite(S1, HIGH);
  digitalWrite(S2, HIGH);
  digitalWrite(S3, HIGH);
  float tdsVal = tdsSensor();

  // Use random values for other sensors
  float hardnessVal = hardnessSensor();
  float conductivityVal = elecConductivitySensor();
  float chloraminesVal = chloraminesSensor();
  float sulfatesVal = sulfatesSensor();
  float organicCarbonValue = organicCarbonSensor();
  float trihalomethanesValue = trihalomethanesSensor();

  // write data to Firebase
  if(Firebase.setFloat(firebaseData, "/Parameters/Turbidity", ntuVal))
  {
    Serial.println("Turbidity value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/pH", phVal))
  {
    Serial.println("pH value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/TDS", tdsVal))
  {
    Serial.println("TDS value successfully sent");
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/Hardness", hardnessVal))
  {
    Serial.println("Hardness value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/Conductivity", conductivityVal))
  {
    Serial.println("Conductivity value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/Chloramines", chloraminesVal))
  {
    Serial.println("Chloramines value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/Sulfates",sulfatesVal))
  {
    Serial.println("Sulfates value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/Organic Carbon", organicCarbonValue))
  {
    Serial.println("Organic Carbon value successfully sent");
    delay(500);
  }
  if(Firebase.setFloat(firebaseData, "/Parameters/Trihalomethanes", trihalomethanesValue))
  {
    Serial.println("Trihalomethanes value successfully sent");
    delay(500);
  }

  // wait for 10 seconds before sending more data
  delay(500);
}

//This subroutine reads and return NTU of water
float turbiditySensor()
{
  float ntuValue;
  int valFromPin;
  float voltage = 0.0;
  //taking average on 500 readings
  for(int i=0; i < 500; i++)
  {
    valFromPin = analogRead(SIG); // Return value of this function is in discreet form (0 - 1023)
    voltage = voltage + (valFromPin * 5.0 / 1023.0); //converting the discrete value from the PIN into analog voltage which is supplied by the turbidity sensor 
  }
  voltage = voltage / 500;
  //Logic to convert the voltage readings into NTU
  ntuValue = (-1120.4 * voltage * voltage) + (5742.3 * voltage) - 4352.9;
  Serial.print("NTU value = ");
  Serial.println(ntuValue);
  return ntuValue;
}

//This subroutine reads and return the PH value
float phSensor()
{
  float calibration_value = 21.34 - 0.7;
  float sum = 0.0;
  float avg_valFromPin;
  for(int i=1; i <= 100; i++)//taking average value
  {
    sum = sum + analogRead(SIG);
  }
  avg_valFromPin = sum / 100;
  float voltage = avg_valFromPin * (5 / 1023); //Conversion. We use 5 if power is 5V, else 3.3 for 3.3V
  float phValue = (-5.70 * voltage) + calibration_value;
  Serial.print("pH value = ");
  Serial.println(phValue);
  return phValue;
}

//For reading TDS sensor
float tdsSensor()
{
  gravityTds.setTemperature(temperature);  // set the temperature and execute temperature compensation
  gravityTds.update();  //sample and calculate
  float tdsValue = gravityTds.getTdsValue();  // then get the value
  Serial.print("TDS value = ");
  Serial.println(tdsValue);
  return tdsValue;
}

//For reading Hardness
float hardnessSensor()
{
  float hardnessValue;
  hardnessValue = (float)random(60,180) + ((float)random(1,9))/12;
  return hardnessValue;
}

//For reading electrical conductivity
float elecConductivitySensor()
{
  float conductivity;
  conductivity = (float)random(181,1000) + ((float)random(1,9))/12;
  return conductivity;
}

//For reading Chloramines
float chloraminesSensor()
{
  float chloramines;
  chloramines = (float)random(1,8) + ((float)random(1,9))/12;
  return chloramines;
}

//For reading sulfates value
float sulfatesSensor()
{
  float sulfate;
  sulfate = (float)random(100,250) + ((float)random(1,9))/12;
  return sulfate;
}

//For reading organic carbon
float organicCarbonSensor()
{
  float organic_carbon;
  organic_carbon = (float)random(2,8) + ((float)random(1,9))/12;
  return organic_carbon;
}

//For reading Trihalomethanes
float trihalomethanesSensor()
{
  float trihalomethanes;
  trihalomethanes = (float)random(16,80) + ((float)random(1,9))/12;
  return trihalomethanes;
}
