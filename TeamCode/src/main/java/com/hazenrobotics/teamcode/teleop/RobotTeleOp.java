package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.input.ButtonManager;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.hazenrobotics.teamcode.DualPulleyLiftController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="TeleOp", group="TeleOp")
public class RobotTeleOp extends LinearOpMode implements OpModeInterface {
    //Add all global objects and lists
    protected ButtonManager buttons = new ButtonManager();

    //Add Motors, Servos, Sensors, etc here
    protected DualPulleyLiftController liftController;
    protected DualPulleyLift lift;

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
            liftController.updateMotion();
            telemetry.update();
            idle();
        }
    }
    protected void setupHardware() {
        lift = new DualPulleyLift(this,"pulley",1f);
        liftController = new DualPulleyLiftController(lift,gamepad2,1f);
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

    @Override
    public Telemetry getTelemetry() {
        return telemetry;
    }
}
