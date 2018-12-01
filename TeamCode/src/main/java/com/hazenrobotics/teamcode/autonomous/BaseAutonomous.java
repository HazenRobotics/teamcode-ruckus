package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.angles.Angle;
import static com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit.*;
import static com.hazenrobotics.commoncode.models.angles.directions.RotationDirection.*;

import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.*;

import com.hazenrobotics.commoncode.models.colors.NamedColorList;
import com.hazenrobotics.commoncode.models.colors.SensorColor;
import com.hazenrobotics.commoncode.models.conditions.ColorMatch;
import com.hazenrobotics.commoncode.models.conditions.Condition;
import com.hazenrobotics.commoncode.models.conditions.GyroAngle;
import com.hazenrobotics.commoncode.models.conditions.RangeDistance;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.commoncode.sensors.ColorSensor;
import com.hazenrobotics.commoncode.sensors.GyroSensor;
import com.hazenrobotics.commoncode.sensors.I2cColorSensor;
import com.hazenrobotics.commoncode.sensors.I2cGyroSensor;
import com.hazenrobotics.commoncode.sensors.I2cRangeSensor;
import com.hazenrobotics.commoncode.sensors.InterfaceGyro;
import com.hazenrobotics.commoncode.sensors.RangeSensor;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*;

public abstract class BaseAutonomous extends LinearOpMode implements OpModeInterface {
    protected static final Distance DISTANCE_FROM_LEFT_WALL = new Distance(13.798f, INCH);
    protected static final Distance DISTANCE_FROM_RIGHT_WALL = new Distance(2.971f, INCH);

    protected ColorSensor<SensorColor> colorSensorSide;
    protected ColorSensor<SensorColor> colorSensorBottom;
    protected RangeSensor rangeSensorFront;
    protected GyroSensor gyroSensor;
    protected TwoEncoderWheels wheels;

    protected Angle angleToDepot;

    public BaseAutonomous(Angle angleToDepot) {
        this.angleToDepot = angleToDepot;
    }

    public void runOpMode() {
        setupHardware();
        telemetry.addLine("Initialized");
        telemetry.update();

        waitForStart();

        telemetry.addLine("Started");
        telemetry.update();

        landAndMove();
        sampleMinerals();
        claimDepotAndPark();
    }



    protected void landAndMove() {
        //TODO: Drop down code
        telemetry.addLine("Landing");
        telemetry.update();

        //Move forward to not hit lander
        wheels.move(new Distance(10, INCH), FORWARDS);

        //we are now FACING THE WALL!!!
        wheels.turn(new GyroAngle(new Angle(45, DEGREES), gyroSensor, CLOCKWISE, false), CLOCKWISE);

        //Move towards wall (to line up with minerals)
        wheels.move(new RangeDistance(DISTANCE_FROM_LEFT_WALL, rangeSensorFront, false), FORWARDS);

        //Turn to be parallel with minerals
        wheels.turn(new GyroAngle(new Angle(135, DEGREES), gyroSensor, COUNTER_CLOCKWISE, false), COUNTER_CLOCKWISE);
    }

    protected void sampleMinerals() {
        //Move until found gold mineral
        wheels.move(new ColorMatch(colorSensorSide,
                SensorColor.LIME, SensorColor.LEMON, SensorColor.YELLOW, SensorColor.ORANGE),
                FORWARDS);

        //Turn 360 Degrees clockwise to move mineral
        wheels.turn(new GyroAngle(new Angle(360, DEGREES), gyroSensor, CLOCKWISE, false), CLOCKWISE);

        //Robot moves towards the wall, then turns to go to Depot
        wheels.move(new RangeDistance(DISTANCE_FROM_RIGHT_WALL, rangeSensorFront, false), FORWARDS);
        wheels.turn(new GyroAngle(angleToDepot, gyroSensor, CLOCKWISE, false), CLOCKWISE);
    }
    protected void claimDepotAndPark() {
        //Robot moves to Depot and puts marker down to cliam
        wheels.move(new RangeDistance(new Distance(24, INCH), rangeSensorFront, false), FORWARDS);

        //TODO: Claim depot
        telemetry.addLine("Claim Depot Here");
        telemetry.update();

        //Robot moves to Crater and parks
        wheels.move(new ColorMatch(colorSensorBottom, SensorColor.BLACK), BACKWARDS);
    }

    protected void setupHardware() {
        colorSensorBottom = new I2cColorSensor((I2cDevice) get("colorSensorBottom"), 0x3a);
        colorSensorSide = new I2cColorSensor((I2cDevice) get("colorSensorSide"), 0X3c);

        rangeSensorFront = new I2cRangeSensor((I2cDevice) get("rangeSensor"));

        gyroSensor = new InterfaceGyro(this, "gyroSensor");
        gyroSensor.calibrate();

        telemetry.addLine("Calibrating");
        telemetry.update();
        while (gyroSensor.isCalibrating()) {
            idle();
        }
        telemetry.addLine("Done Calibrating");
        telemetry.update();

        TwoWheels.WheelConfiguration wheelConfiguration = new TwoWheels.WheelConfiguration("leftMotor", "rightMotor", DcMotor.Direction.REVERSE, DcMotor.Direction.FORWARD);
        TwoEncoderWheels.EncoderConfiguration encoderConfiguration = new TwoEncoderWheels.EncoderConfiguration(1680, new Distance(101.06f, MM), new Distance(37.3f, CM));
        TwoWheels.SpeedSettings speeds = new TwoWheels.SpeedSettings(1f, 0.5f, 0.7f);
        wheels = new TwoEncoderWheels(this, wheelConfiguration, encoderConfiguration, speeds);
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

    public Telemetry getTelemetry() {
        return telemetry;
    }
}