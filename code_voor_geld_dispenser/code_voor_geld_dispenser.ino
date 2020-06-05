char commando[3];
char brief10;
char brief20;
char brief50;

void setup() {
  Serial.begin(9600);
  
  commando[0] = 'a';
  commando[1] = 'a';
  commando[2] = 'a';
  
  pinMode(10, OUTPUT);
 
  while(!Serial) {}
}

void stuurMotor10()
{
  digitalWrite(10, HIGH);
  delay(2000);
  digitalWrite(10, LOW);
  delay(100);
}



void loop() {
  
 
  if(Serial.available() >= 3)
  {  
    Serial.readBytes(commando, 3);   
    
    brief10 = commando[0];
    brief20 = commando[1];
    brief50 = commando[2];

    Serial.print(brief10);
    Serial.print(brief20);
    Serial.println(brief50);
    
    if(brief10 == '1')
    {
      stuurMotor10();
    }
    if(brief10 == '2')
    {
      stuurMotor10();
      stuurMotor10();
    }
    if(brief10 == '3')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }
    if(brief10 == '4')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }
    if(brief10 == '5')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }
    delay(1);
    Serial.read();
  }
  
  
  
  
}
