package com.hazenrobotics.teamcode;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.conditions.Condition;
import com.hazenrobotics.commoncode.models.conditions.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//basic idea
@Autonomous(name="LiftAutonomous",group="Autonomous")
public class LiftAutonomous extends LinearOpMode implements OpModeInterface {
    protected DualPulleyLift lift;

    @Override
    public void runOpMode(){
        setupHardware();
        waitForStart();

        //The timer is variable(2000-2500)
        lift.slide(new Timer(2500), DualPulleyLift.Direction.EXTEND);
        for(Condition t = new Timer(3000); !t.isTrue(); idle());
        lift.slide(new Timer(2500), DualPulleyLift.Direction.RETRACT);
    }

    public void setupHardware(){
        lift = new DualPulleyLift(this, "pulley", 0.5f);
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
