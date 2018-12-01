package com.hazenrobotics.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DualPulleyLiftController {
    public static final float BRAKE_POWER = 0.15f;

    protected Gamepad moveController;
    protected Gamepad brakeController;
    protected DualPulleyLift lift;
    protected float maxSpeed;
    protected boolean liftLimited;

    public DualPulleyLiftController(DualPulleyLift lift, Gamepad moveController, Gamepad brakeController, float maxSpeed) {
        this.moveController = moveController;
        this.brakeController = brakeController;
        this.lift = lift;
        this.maxSpeed = maxSpeed;
        liftLimited = false;
    }

    public void setSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void updateMotion() {
        //default: lift is controlled by gamepad 2 sticks; if y is hit, maintains power to end.
        DualPulleyLift.Coefficients coefficients = new DualPulleyLift.Coefficients();
        if (liftLimited){
            //coefficients.extending = 0; //This happens by default when making coefficients
            coefficients.retracting = BRAKE_POWER;
            if(brakeController.x){
                liftLimited = false;
            }
        }
        else {
            coefficients.extending = moveController.left_stick_y;
            coefficients.retracting = moveController.right_stick_y;
            if(brakeController.y){
                liftLimited = true;
            }
        }
        lift.setPower(coefficients, maxSpeed);
    }

    public void stopMotion() {
        lift.stop();
    }
}