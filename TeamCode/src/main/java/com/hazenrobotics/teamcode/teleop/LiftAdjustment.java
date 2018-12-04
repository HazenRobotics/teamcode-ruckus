package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.hazenrobotics.teamcode.DualPulleyLiftController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Lift Adjustment (NOT TELEOP!)",group = "Utility")
public class LiftAdjustment extends LinearOpMode implements OpModeInterface {
    protected DualPulleyLift lift;
    protected DualPulleyLiftController controller;

    @Override
    public void runOpMode() {
        setupHardware();
        waitForStart();
        while(opModeIsActive()){
            controller.updateMotion();
            idle();
        }
        controller.stopMotion();
    }
    protected void setupHardware(){
        lift = new DualPulleyLift(this, "extendingMotor", "retractingMotor", 1.0f);
        controller = new DualPulleyLiftController(lift, gamepad2, gamepad1, 1f);
    }
    @Override
    public Gamepad getGamepad1() {
        return gamepad1;
    }

    @Override
    public Gamepad getGamepad2() {
        return gamepad2;
    }

    @Override
    public DcMotor getMotor(String name) {
        return hardwareMap.dcMotor.get(name);
    }

    @Override
    public Servo getServo(String name) {
        return hardwareMap.servo.get(name);
    }

    @Override
    public DigitalChannel getDigitalChannel(String name) {
        return hardwareMap.digitalChannel.get(name);
    }

    @Override
    public HardwareDevice get(String name) {
        return hardwareMap.get(name);
    }

    @Override
    public Telemetry getTelemetry() {
        return telemetry;
    }
}
