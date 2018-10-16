package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
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

public abstract class BaseAutonomous extends LinearOpMode implements OpModeInterface {

    I2cColorSensor colorSensorSide;
    I2cColorSensor colorSensorBottom;

    I2cRangeSensor rangeSensorFront;
    I2cGyroSensor gyroSensor;

    TwoWheels wheels;

    public void runOpMode() {
        setupHardware();

        waitForStart();

        step1();
        step2();
        step3();
    }


    protected void step1() {

    }
    protected abstract void step2();

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
