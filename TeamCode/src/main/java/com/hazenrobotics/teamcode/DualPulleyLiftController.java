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
        if(controller.dpad_up) {
            lift.setPower(lift.calculateSlide(DualPulleyLift.Direction.EXTEND), maxSpeed);
        }
        else if(controller.dpad_down) {
            lift.setPower(lift.calculateSlide(DualPulleyLift.Direction.RETRACT), maxSpeed);
        }
        else {
            lift.stop();
        }
    }

    public void stopMotion() {
        lift.stop();
    }
}
