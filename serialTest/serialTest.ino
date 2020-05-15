int incomingByte = 0; // for incoming serial data
String readString, servo1, servo2;
void setup() {
  Serial.begin(9600); // opens serial port, sets data rate to 9600 bps

  while(!Serial) {
    ;
  }
}

void loop() {
  while (Serial.available()) {
   delay(1);  
   if (Serial.available() >0) {
     char c = Serial.read();  //gets one byte from serial buffer
     readString += c; //makes the string readString
   }
 }

 if (readString.length() >0) {     
     // expect a string like 07002100 containing the two servo positions      
     servo1 = readString.substring(0, 2); //get the first four characters
     servo2 = readString.substring(2, 4); //get the next four characters

     if (servo1 == "ha"){
       digitalWrite(10,HIGH);
       
     } else {
      digitalWrite(10,LOW);
     }

     if(servo2 == "lo") {
      digitalWrite(9,HIGH);
     }else{
      digitalWrite(9,LOW);
     }
  }
}
