package com.hazenrobotics.teamcode;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="LiftAutonomous",group="Autonomous")
public class LiftAutonomous extends LinearOpMode implements OpModeInterface {
    protected DcMotor robotUp;
    protected DcMotor liftUp;
    protected double startingTime;
    protected double timer=-1;
    protected double endTime=0;

    @Override
    public void runOpMode(){
        setupHardware();
        waitForStart();
        startingTime=getRuntime();
        timer=getRuntime()-startingTime;
        while(opModeIsActive()&&(timer<endTime)){
            robotUp.setPower(-0.5);
            liftUp.setPower(0.55);
            timer=getRuntime()-startingTime;
            idle();
        }
        robotUp.setPower(0);
        liftUp.setPower(0);
    }

    public void setupHardware(){
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
