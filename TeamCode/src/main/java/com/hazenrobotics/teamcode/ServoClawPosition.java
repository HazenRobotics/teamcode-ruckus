 public enum ServoClawPosition {
      OPEN(10),
      CLOSED(100);

      public final int servoPosition;

      public Position(int servoPosition) {
          this.servoPosition = servoPosition;
      }
  }
