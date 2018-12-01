package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.input.ButtonManager;
import com.hazenrobotics.commoncode.input.Toggle;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.movement.DrivingController;
import com.hazenrobotics.commoncode.movement.TankControlsDrivingController;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    protected TwoWheels wheels;
    protected DrivingController driving;


    //Add all Constants here
    //EX: protected final double MOTOR_POWER = 0.5;
    @Override
    public void runOpMode() {
        setupHardware();
        setupButtons();
        //Add any further initialization (methods) here

        telemetry.addLine("Inited");
        telemetry.update();

        waitForStart();

        telemetry.addLine("Started");
        telemetry.update();

        while (opModeIsActive()) {
            buttons.update();

            driving.updateMotion();
            extendingMotor.setPower(-gamepad2.left_stick_y);
            retractingMotor.setPower(-gamepad2.right_stick_y);
            idle();
        }
        extendingMotor.setPower(0);
        retractingMotor.setPower(0);
    }

    protected void setupHardware() {
        //Initializes the motor/servo variables here
        TwoWheels.WheelConfiguration configuration = new TwoWheels.WheelConfiguration("leftMotor","rightMotor", DcMotor.Direction.FORWARD, DcMotor.Direction.REVERSE);
        wheels = new TwoWheels(this, configuration);
        driving = new TankControlsDrivingController(wheels, gamepad1);
        extendingMotor = getMotor("extendingMotor");
        retractingMotor = getMotor("retractingMotor");
        extendingMotor.setDirection(DcMotor.Direction.FORWARD);
        retractingMotor.setDirection(DcMotor.Direction.REVERSE);
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
