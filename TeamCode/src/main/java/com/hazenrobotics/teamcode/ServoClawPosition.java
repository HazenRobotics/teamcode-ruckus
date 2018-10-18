package com.hazenrobotics.teamcode;

public enum ServoClawPosition {
      OPEN(10),
      CLOSED(100);

      public int servoPosition =0;

       ServoClawPosition(int servoPosition) {
          this.servoPosition = servoPosition;
      }
  }
