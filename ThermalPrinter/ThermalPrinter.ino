#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"
#define TX_PIN 6
#define RX_PIN 5
SoftwareSerial mySerial(RX_PIN, TX_PIN);
Adafruit_Thermal printer(&mySerial);

int transactieNummer = 2525;
int rekeningsNummer = 4189; //laatste 4 cijfers
String bedrag = "14";
char printUit; //0 is nee 1 is ja

void setup() {
  Serial.begin(9600);
  mySerial.begin(9600);
  printer.begin();
  pinMode(9, OUTPUT);
}

void loop() {
  if (Serial.available()) {
    delay(100);
    while (Serial.available()) {
      printUit = Serial.read();
      //bedrag = Serial.read();
      if (printUit == '1') {
        Serial.println(printUit);

        digitalWrite(9, HIGH);
        printer.justify('C');
        printer.setSize('L');
        printer.println(F("Flasche Flocken"));
        printer.setSize('S');
        printer.justify('C');
        printer.println(F("----------------------------"));
        printer.justify('L');
        printer.println(F("Locatie:"));
        printer.justify('C');
        printer.println(F("Rotterdam"));

        printer.justify('C');
        printer.println(F("----------------------------"));
        printer.justify('L');
        printer.print(F("Datum/"));
        printer.println(F("Tijd:"));
        printer.justify('C');
        printer.println(F("31/03/2020")); //verander dit naar de datum op de raspberry pi
        printer.println(F("15:07")); //verander dit naar de tijd op de raspberry pi

        printer.justify('C');
        printer.println(F("----------------------------"));
        printer.justify('L');
        printer.println(F("Transactienummer:"));
        printer.justify('C');
        printer.println(transactieNummer);

        printer.justify('C');
        printer.println(F("----------------------------"));
        printer.justify('L');
        printer.println(F("Rekeningsnummer:"));
        printer.justify('C');
        printer.print("**********");
        printer.println(rekeningsNummer);

        printer.justify('C');
        printer.println(F("----------------------------"));
        printer.justify('L');
        printer.println(F("Opgenomen bedrag:"));
        printer.justify('C');
        printer.print(bedrag);
        printer.println(" Euro");
      }
    }
  } 
}
