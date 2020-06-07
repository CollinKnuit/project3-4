#include <SPI.h>
#include <MFRC522.h>
 
#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);

  
String content= "";
String previusContent= "";

uint16_t prevTime = 0;

void setup() 
{
  Serial.begin(9600);  
  SPI.begin();  
  mfrc522.PCD_Init(); 

}
void loop() 
{
  String content= "";
  
  
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
 
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
 
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
