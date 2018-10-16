package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

//basic idea
@Disabled
@TeleOp(name = "LiftTest",group = "TeleOp")
public class LiftTele extends LinearOpMode implements OpModeInterface {
    protected DcMotor robotUp;
    protected DcMotor liftUp;
    boolean toggle = true;
    double tension = 0;

    @Override
    public void runOpMode() {
        setupHardware();
        waitForStart();
        while(opModeIsActive()){
            if(toggle){
                robotUp.setPower(gamepad2.right_stick_y);
                liftUp.setPower(-gamepad2.right_stick_y);
            }
            else{
                robotUp.setPower(tension);
                liftUp.setPower(0);
            }
            if(gamepad2.x){
                toggle = false;
            }
            else if(gamepad2.y){
                toggle = true;
            }
            idle();
        }
    }
    protected void setupHardware(){
        robotUp = getMotor("lift1");
        liftUp = getMotor("lift2");
    }
    @Override
    public Gamepad getGamepad1() {
        return null;
    }

    @Override
    public Gamepad getGamepad2() {
        return null;
    }

    @Override
    public DcMotor getMotor(String name) {
        return null;
    }

    @Override
    public Servo getServo(String name) {
        return null;
    }

    @Override
    public DigitalChannel getDigitalChannel(String name) {
        return null;
    }

    @Override
    public HardwareDevice get(String name) {
        return null;
    }
}
