//#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"
#define TX_PIN 6
#define RX_PIN 5
SoftwareSerial mySerial(RX_PIN, TX_PIN);
//Adafruit_Thermal printer(&mySerial);

// pins for the LEDs:
const int redPin = 13;
const byte size = 30;
char ar[size];
String bedrag;
String rekeningNummer;
String transactieNummer;
String date;
String time1;

void setup() {
  // initialize serial:
  Serial.begin(9600);
  //  mySerial.begin(9600);
  //  printer.begin();
  // make the pins outputs:
  pinMode(redPin, OUTPUT);
  while (!Serial) { }
}

void loop() {
  // if there's any serial available, read it:
  if (Serial.available() > 29) {
    Serial.readBytes(ar, 30);
    bedrag = "";
    rekeningNummer = "";
    transactieNummer = "";
    date = "";

    int i = 0;
    for (i = 0; i < 3; i++) {
       bedrag += ar[i];
    }
    for (i = 3; i < 7; i++) {
      rekeningNummer += ar[i];
    }
    for (i = 7; i < 11; i++) {
      transactieNummer += ar[i];
    }
    for (i = 11; i < 30; i++) {
      date += ar[i];
    }

    Serial.println(date);
    Serial.println(rekeningNummer);
    Serial.println(transactieNummer);
    Serial.println(bedrag);
    
    //    printer.justify('C');
    //    printer.setSize('L');
    //    printer.println(F("Flasche Flocken"));
    //    printer.setSize('S');
    //    printer.justify('C');
    //    printer.println(F("----------------------------"));
    //    printer.justify('L');
    //    printer.println(F("Locatie:"));
    //    printer.justify('C');
    //    printer.println(F("Rotterdam"));

    //    printer.justify('C');
    //    printer.println(F("----------------------------"));
    //    printer.justify('L');
    //    printer.print(F("Datum/"));
    //    printer.println(F("Tijd:"));
    //    printer.justify('C');
    //    printer.println(date);
    //    printer.println(time1);

    //    printer.justify('C');
    //    printer.println(F("----------------------------"));
    //    printer.justify('L');
    //    printer.println(F("Transactienummer:"));
    //    printer.justify('C');
    //    //printer.println(ar[7] + ar[8] + ar[9] + ar[10]);
    //    printer.println(transactieNummer);

    //    printer.justify('C');
    //    printer.println(F("----------------------------"));
    //    printer.justify('L');
    //    printer.println(F("Rekeningnummer:"));
    //    printer.justify('C');
    //    printer.print("**********");
    //printer.println(ar[3] + ar[4] + ar[5] + ar[6]);
    //printer.println(rekeningNummer);


    //    printer.justify('C');
    //    printer.println(F("----------------------------"));
    //    printer.justify('L');
    //    printer.println(F("Opgenomen bedrag:"));
    //    printer.justify('C');
    //printer.print(ar[0] + ar[1] + ar[2]);
    //printer.println(bedrag);

    //    printer.println(" Euro");
  }
}
