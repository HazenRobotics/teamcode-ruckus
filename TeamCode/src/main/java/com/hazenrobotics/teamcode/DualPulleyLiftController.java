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

        lift.pulleyMotor.setPower(-controller.right_stick_y);
    }

    public void stopMotion() {
        lift.stop();
    }
}
