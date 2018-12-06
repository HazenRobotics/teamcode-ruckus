package com.hazenrobotics.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ExtendyArmController {
    protected Gamepad controller;
    protected ExtendyArm arm;

    protected static final double PIVOT_SPEED = 0.3;
    protected static final double SWEEPER_SPEED = 0.2;
    protected static final float EXTENDING_SPEED = 0.5f;

    public ExtendyArmController(ExtendyArm arm, Gamepad controller){
        this.arm = arm;
        this.controller = controller;
    }

    public void updateMotion(){
        //Extends and retracts arm. Uses triggers. Right trigger extends, left trigger retracts.
        if(controller.right_trigger>=0.05){
            arm.setPowerToExtendingArm(controller.right_trigger*EXTENDING_SPEED);
        }else if(controller.left_trigger>=0.05){
            arm.setPowerToExtendingArm(-controller.left_trigger*EXTENDING_SPEED);
        }
        else{
            arm.setPowerToExtendingArm(0);
        }

        //Pivots arm. Uses y and a buttons. Y lifts arm up, A lowers arm down.
        if(controller.y){
            arm.setPowerToPivotArm(PIVOT_SPEED);
        }else if(controller.a){
            arm.setPowerToPivotArm(-PIVOT_SPEED);
        }else{
            arm.setPowerToPivotArm(0);
        }

        //Pivots bucket. Uses d-pad. Up arrow lifts bucket up, down arrow lowers bucket down.
        if(controller.dpad_up){
            arm.setPowerToPivotBucket(PIVOT_SPEED);
        }else if(controller.dpad_down){
            arm.setPowerToPivotBucket(-PIVOT_SPEED);
        }else{
            arm.setPowerToPivotBucket(0);
        }

        //Rotates sweeper. Uses bumpers. Right bumper sweeps things into scoop, left bumper spits stuff out.
        if(controller.right_bumper){
            arm.setPowerToBucketSweeper(-SWEEPER_SPEED);
        }else if(controller.left_bumper){
            arm.setPowerToBucketSweeper(SWEEPER_SPEED);
        }else{
            arm.setPowerToBucketSweeper(0);
        }
    }

    public void stopMotion() {
        arm.stop();
    }
}
