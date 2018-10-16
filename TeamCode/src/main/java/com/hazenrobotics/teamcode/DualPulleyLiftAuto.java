package com.hazenrobotics.teamcode;

public class DualPulleyLiftAuto {
    protected DualPulleyLift lift;
    protected float maxSpeed;

    public DualPulleyLiftAuto(DualPulleyLift lift, float maxSpeed) {
        this.lift = lift;
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void updateMotion() {
        float movement = (float)-0.5;

        //The controller stick being pushed up (positive movement values) will set the direction to extending
        DualPulleyLift.Direction direction = movement > 0 ? DualPulleyLift.Direction.EXTEND : DualPulleyLift.Direction.RETRACT;
        float speed = Math.abs(movement) * maxSpeed;

        lift.setPower(lift.calculateSlide(direction), speed);
    }

    public void stopMotion() {
        lift.stop();
    }
}
