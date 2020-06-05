/*
 * 
 * All the resources for this project: https://randomnerdtutorials.com/
 * Modified by Rui Santos
 * 
 * Created by FILIPEFLOP
 * 
 */
 
#include <SPI.h>
#include <MFRC522.h>
 
#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

  
String content= "";
String previusContent= "";

uint16_t prevTime = 0;

void setup() 
{
  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522

}
void loop() 
{
  String content= "";
  
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  //Show UID on serial monitor
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
    
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : ""));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  
  content.toUpperCase();

 
  if(content.equals(previusContent)){
     
     if(millis() - prevTime >= 60000){
      prevTime = millis();
      previusContent = "";
    }
  }else{
      Serial.println(content);
      previusContent = content;
      prevTime = millis();
  }
 
 
  
} 
