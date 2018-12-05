package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.control.BaseOpMode;
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
public class LiftAdjustment extends BaseOpMode {
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
}
