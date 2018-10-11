package com.hazenrobotics.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DualPulleyLiftController {
    protected Gamepad controller;
    protected DualPulleyLift lift;
    protected float maxSpeed;

    public DualPulleyLiftController(DualPulleyLift lift, Gamepad controller, float maxSpeed) {
        this.controller = controller;
        this.lift = lift;
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void updateMotion() {
        float movement = -controller.left_stick_y;

        //The controller stick being pushed up (positive movement values) will set the direction to extending
        DualPulleyLift.Direction direction = movement > 0 ? DualPulleyLift.Direction.EXTEND : DualPulleyLift.Direction.RETRACT;
        float speed = Math.abs(movement) * maxSpeed;

        lift.setPower(lift.calculateSlide(direction), speed);
    }

    public void stopMotion() {
        lift.stop();
    }
}
