package com.hazenrobotics.teamcode.teleop;

public enum Position {
OPEN(10),
CLOSED(100);

  int servoPosition;
  
  Position(int servoPosition){
    this.servoPosition = servoPosition;
  }
}
