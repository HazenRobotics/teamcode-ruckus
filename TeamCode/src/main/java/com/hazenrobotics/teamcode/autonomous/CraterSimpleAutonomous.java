package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.conditions.Timer;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//Static Imports for different Units
import static com.hazenrobotics.commoncode.models.angles.directions.RotationDirection.*;
import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.*;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*;

@Autonomous(name = "Crater Simple Autonomous", group = "Autonomous")
public class CraterSimpleAutonomous extends LinearOpMode implements OpModeInterface {

    protected TwoEncoderWheels wheels;
    protected DualPulleyLift lift;
    protected DcMotor sweeperMotor;
    protected DcMotor axelMotor;

    protected final static double LIFT_POWER = 0.15;
    protected final static double SWEEPER_SPEED = 0.5;

    @Override
    public void runOpMode() {
        setupHardware();
        lift.initPower(LIFT_POWER);

        waitForStart();

        land();

        //sweep();
    }

    protected void land() {

        lift.slide(new Timer( 3000), DualPulleyLift.Direction.EXTEND,0.5f);
        wheels.move(new Timer(2200), BACKWARDS);
    }

    protected void sweep(){
        sweeperMotor.setPower(SWEEPER_SPEED);
        sleep(1000);
        sweeperMotor.setPower(0);
    }

    protected void setupHardware() {
        TwoWheels.WheelConfiguration wheelConfiguration = new TwoWheels.WheelConfiguration(
                "leftMotor", //left name
                "rightMotor",  //right name
                DcMotor.Direction.REVERSE,  //left direction
                DcMotor.Direction.FORWARD); //right direction
        TwoEncoderWheels.EncoderConfiguration encoderConfiguration = new TwoEncoderWheels.EncoderConfiguration(
                1680, //counts per revolution
                new Distance(101.06f, MM), //wheel diameter
                new Distance(37.3f, CM)); //robot diameter
        TwoWheels.SpeedSettings speeds = new TwoWheels.SpeedSettings(
                1f, //move speed
                0.5f, //curve speed
                0.7f); //turn speed
        wheels = new TwoEncoderWheels(this, wheelConfiguration, encoderConfiguration, speeds);

        lift = new DualPulleyLift(this,
                "extendingMotor", //Extending name
                "retractingMotor", //Retracting name
                1.0f); //Speed

        sweeperMotor = getMotor("sweeperMotor");
        sweeperMotor.setDirection(DcMotor.Direction.FORWARD);
        axelMotor = getMotor("axelMotor");
        axelMotor.setDirection(DcMotor.Direction.FORWARD);
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
