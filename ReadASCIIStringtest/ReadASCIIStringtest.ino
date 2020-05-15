
// pins for the LEDs:
const int redPin = 13;

char ar[5]={0,0,0,0,0};

void setup() {
  // initialize serial:
  Serial.begin(9600);
  // make the pins outputs:
  pinMode(redPin, OUTPUT);
  
  while (!Serial) {
    ;
  }
}

void loop() {
  // if there's any serial available, read it:
    if (Serial.available() > 0) {
      
   

      for (int index = 0; index < 5; index++) {
    // turn the pin on:
         ar[index] = Serial.read();
      }
      
      if(ar[0] == '1' && ar[1] == '2' && ar[1] == '2'  && ar[1] == '2'  ){
          digitalWrite(redPin, HIGH);
      }
      delay(500);



    }
 }
 
