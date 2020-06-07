//dit is de code van de geld dispenser

char commando[3];
char brief10;
char brief20;
char brief50;

void setup() {
  Serial.begin(9600);
  
  commando[0] = 'a';
  commando[1] = 'a';
  commando[2] = 'a';

  pinMode(7, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(13, OUTPUT);
 
  while(!Serial) {}
}

void stuurMotor10()
{
  digitalWrite(10, HIGH);
  delay(2000);
  digitalWrite(10, LOW);
  delay(100);
}
void stuurMotor20()
{
  digitalWrite(13, HIGH);
  delay(2000);
  digitalWrite(13, LOW);
  delay(100);
}
void stuurMotor50()
{
  digitalWrite(7, HIGH);
  delay(2000);
  digitalWrite(7, LOW);
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
    if(brief10 == '6')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }
    if(brief10 == '7')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }
    if(brief10 == '8')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }
    if(brief10 == '9')
    {
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
      stuurMotor10();
    }

    if(brief20 == '1')
    {
      stuurMotor20();
    }
    if(brief20 == '2')
    {
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '3')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '4')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '5')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '6')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '7')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '8')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }
    if(brief20 == '9')
    {
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
      stuurMotor20();
    }

    if(brief50 == '1')
    {
      stuurMotor50();
    }
    if(brief50 == '2')
    {
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '3')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '4')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '5')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '6')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '7')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '8')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    if(brief50 == '9')
    {
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
      stuurMotor50();
    }
    delay(1);
    Serial.read();
  }
  
  
  
  
}
