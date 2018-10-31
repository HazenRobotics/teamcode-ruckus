package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.input.ButtonManager;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.movement.DrivingController;
import com.hazenrobotics.commoncode.movement.TankControlsDrivingController;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.hazenrobotics.teamcode.DualPulleyLiftController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
    protected DcMotor extendingMotor, retractingMotor;


    //Add all Constants here
    //EX: protected final double MOTOR_POWER = 0.5;
    protected final double TIGHTEN_COEFFICIENT = -0.5625;
    protected final double LOOSEN_COEFFICIENT = 0.75;
    
    
    @Override
    public void runOpMode() {
        setupHardware();
        setupButtons();
        //Add any further initialization (methods) here

        telemetry.addData("Inited","");
        telemetry.update();

        waitForStart();



        while (opModeIsActive()) {
            buttons.update();
            telemetry.addData("Started","");
            telemetry.update();
            driving.updateMotion();
            lift();
            idle();
        }
    }
    
    protected void setupHardware() {
        //Initializes the motor/servo variables here
        wheels = new TwoWheels(this, new TwoWheels.WheelConfiguration("leftMotor","rightMotor",DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD));
        driving = new TankControlsDrivingController(wheels, gamepad1);
        extendingMotor = hardwareMap.DcMotor.get("extendingMotor");
        retractingMotor = hardwareMap.DcMotor.get("retractingMotor");
        extendingMotor.setDirection(Direction.FORWARD);
        retractingMotor.setDirection(Direction.REVERSE);
    }

    protected void lift() {
        if(gamepad2.dpad_up){
            extendingMotor.setPower(TIGHTEN_COEFFICIENT);
            retractingMotor.setPower(LOOSEN_COEFFICIENT);
        }
        else if(gamepad2.dpad_down){
            extendingMotor.setPower(LOOSEN_COEFFICIENT);
            retractingMotor.setPower(TIGHTEN_COEFFICIENT);
        }
        else{
            extendingMotor.setPower(0);
            retractingMotor.setPower(0);
        }
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
