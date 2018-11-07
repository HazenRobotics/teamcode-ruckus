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
    protected DcMotor extendingMotor, retractingMotor;
    protected Servo flicker;

    //Add all Constants here
    protected final static double SERVO_START = 1.0;
    protected final static double SERVO_END = 0;
    
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
            extendingMotor.setPower(-gamepad2.left_stick_y);
            retractingMotor.setPower(-gamepad2.right_stick_y);
            idle();
        }
        extendingMotor.setPower(0);
        retractingMotor.setPower(0);
        flicker.setPosition(SERVO_START);
    }
    
    protected void setupHardware() {
        //Initializes the motor/servo variables here
        wheels = new TwoWheels(this, new TwoWheels.WheelConfiguration("leftMotor","rightMotor",DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD));
        driving = new TankControlsDrivingController(wheels, gamepad1);
        extendingMotor = getMotor("extendingMotor");
        retractingMotor = getMotor("retractingMotor");
        extendingMotor.setDirection(DcMotor.Direction.FORWARD);
        retractingMotor.setDirection(DcMotor.Direction.REVERSE);
        flicker = getServo("flickerServo");
        flicker.setPosition(SERVO_START);
    }
        
    protected void setupButtons() {
        buttons = new ButtonManager();
        buttons.add(new Toggle() {
            @Override
            public void onActivate() {
                flicker.setPosition(SERVO_END);
            }

            @Override
            public void onDeactivate() {
                flicker.setPosition(SERVO_START);
            }

            @Override
            public boolean isInputPressed() {
                return gamepad2.a;
            }
        });
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
