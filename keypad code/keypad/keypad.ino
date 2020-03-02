
#include <Keypad.h>

#define password_length 5

const byte ROWS = 4;
const byte COLS = 4;

char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};// layout van keypad

byte rowPins[ROWS] = {9, 8, 7, 6};
byte colPins[COLS] = {5, 4, 3, 2};

char pass[password_length] = "1234"; // hard-coded pincode
char passdata[password_length] ; // de array waar de invoer in weggezet wordt

Keypad kpd = Keypad( makeKeymap(hexaKeys), rowPins, colPins , ROWS, COLS );

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  char key = kpd.getKey()
  if (key) {
      delay(100);
      Serial.println(key);
  }
}
