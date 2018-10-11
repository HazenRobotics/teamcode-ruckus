package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.input.ButtonManager;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.movement.DrivingController;
import com.hazenrobotics.commoncode.movement.TankControlsDrivingController;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleOp", group="TeleOp")
public class RobotTeleOp extends LinearOpMode implements OpModeInterface {
    //Add all global objects and lists
    protected ButtonManager buttons = new ButtonManager();

    //Add Motors, Servos, Sensors, etc here
    protected TwoWheels wheels;
    protected DrivingController driving;
    protected DcMotor lift1,lift2;


    //Add all Constants here
    //EX: protected final double MOTOR_POWER = 0.5;
    @Override
    public void runOpMode() {
        setupHardware();
        setupButtons();
        //Add any further initialization (methods) here

        waitForStart();


        while (opModeIsActive()) {
            buttons.update();

            lift1.setPower(gamepad2.right_stick_y);
            lift2.setPower(gamepad2.left_stick_y);

            telemetry.update();
            idle();
        }
    }
    protected void setupHardware() {
        lift1 = getMotor("lift1");
        lift2 = getMotor("lift2");
    }

    protected void setupButtons() {
        buttons = new ButtonManager();
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
}
