
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

byte rowPins[ROWS] = {13, 12, 11, 10}; 
byte colPins[COLS] = {9, 8, 7, 6}; 

Keypad kpd = Keypad( makeKeymap(hexaKeys), rowPins,colPins , ROWS, COLS );

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  kpd.setDebounceTime(200);
    while(Serial.available() ){
  }
}

void loop() {
  // put your main code here, to run repeatedly:
     char key = kpd.getKey();
  if(key){
   Serial.print(key);
  } 
}
