package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.angles.Angle;
import static com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit.*;
import static com.hazenrobotics.commoncode.models.angles.directions.RotationDirection.*;

import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.*;
import com.hazenrobotics.commoncode.models.conditions.GyroAngle;
import com.hazenrobotics.commoncode.models.conditions.RangeDistance;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.commoncode.sensors.I2cColorSensor;
import com.hazenrobotics.commoncode.sensors.I2cGyroSensor;
import com.hazenrobotics.commoncode.sensors.I2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*;

public abstract class BaseAutonomous extends LinearOpMode implements OpModeInterface {

    protected I2cColorSensor colorSensorSide;
    protected I2cColorSensor colorSensorBottom;

    protected I2cRangeSensor rangeSensorFront;
    protected I2cGyroSensor gyroSensor;

    protected Angle angleToDepot;

    public BaseAutonomous(Angle angleToDeopt) {
        this.angleToDepot = angleToDeopt;
    }

    protected TwoEncoderWheels wheels;

    public void runOpMode() {
        setupHardware();

        waitForStart();

        step1();
        step2();
        step3();
    }


    protected void step1() {

        //TODO: Drop down code
        wheels.move(new Distance(10, INCH),BACKWARDS);
        wheels.turn(new Angle(135, DEGREES),COUNTER_CLOCKWISE);



    }

    protected void step2() {
        ///.....

        wheels.move(new RangeDistance(new Distance(10, INCH), rangeSensorFront, true), FORWARDS);
        wheels.turn(new GyroAngle(angleToDepot, gyroSensor, CLOCKWISE), CLOCKWISE);
    }

    protected void step3 () {

    }

    protected void setupHardware() {
        wheels = new TwoWheels(this, "leftMotor", "rightMotor");

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
