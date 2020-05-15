
// pins for the LEDs:
const int redPin = 13;
const byte size = 5;
char ar[size];

void setup() {
  // initialize serial:
  Serial.begin(9600);
  // make the pins outputs:
  pinMode(redPin, OUTPUT);
  
  while (!Serial) { }
  
}

void loop() {
  // if there's any serial available, read it:
    if (Serial.available() > 0) {
   

      for (int index = 0; index < size; index++) {
    // turn the pin on:
         Serial.readBytes(ar, size);
      }
      
      if(ar[0] == '1' && ar[1] == '2' && ar[2] == '3'  && ar[3] == '4' &&  ar[4] == '5' ){
          digitalWrite(redPin, HIGH);
      }

    }
 }
 
