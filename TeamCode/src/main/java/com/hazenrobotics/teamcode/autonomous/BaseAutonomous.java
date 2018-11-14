package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.angles.Angle;
import static com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit.*;
import static com.hazenrobotics.commoncode.models.angles.directions.RotationDirection.*;

import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.*;

import com.hazenrobotics.commoncode.models.colors.NamedColorList;
import com.hazenrobotics.commoncode.models.colors.SensorColor;
import com.hazenrobotics.commoncode.models.conditions.Condition;
import com.hazenrobotics.commoncode.models.conditions.GyroAngle;
import com.hazenrobotics.commoncode.models.conditions.RangeDistance;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.commoncode.sensors.I2cColorSensor;
import com.hazenrobotics.commoncode.sensors.I2cGyroSensor;
import com.hazenrobotics.commoncode.sensors.I2cRangeSensor;
import com.hazenrobotics.teamcode.testcode.InterfaceGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
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

    protected I2cColorSensor colorSensorSide;
    protected I2cColorSensor colorSensorBottom;

    protected I2cRangeSensor rangeSensorFront;
    protected I2cGyroSensor gyroSensor;

    protected Angle angleToDepot;

    protected static final Distance DISTANCE_FROM_LEFT_WALL = new Distance(13.798f, INCH);
    protected static final Distance DISTANCE_FROM_RIGHT_WALL = new Distance(2.971f, INCH);

    public BaseAutonomous(Angle angleToDeopt) {
        this.angleToDepot = angleToDeopt;
    }

    protected TwoEncoderWheels wheels;

    public void runOpMode() {
        setupHardware();

        waitForStart();

        landAndMove();
        sampleMinerals();
        claimDepotAndPark();
    }


    protected void landAndMove() {

        //TODO: Drop down code

        //Backs up to not hit lander
        wheels.move(new Distance(3, INCH),BACKWARDS);
        //we are now FACING THE WALL!!!
        wheels.turn(new Angle(135, DEGREES),COUNTER_CLOCKWISE);
        //Move towards wall (to line up with minerals)
        wheels.move(new RangeDistance(DISTANCE_FROM_LEFT_WALL, rangeSensorFront, false), FORWARDS);
        //Turn to be parallel with minerals
        wheels.turn(new Angle(135, DEGREES), COUNTER_CLOCKWISE);
    }

    protected void sampleMinerals() {
        //Move until found gold mineral
        wheels.move(new Condition() {
            NamedColorList colorList = getColorList();
            private NamedColorList getColorList() {
                NamedColorList list = new NamedColorList();
                list.addColor(SensorColor.LIME);
                list.addColor(SensorColor.LEMON);
                list.addColor(SensorColor.YELLOW);
                list.addColor(SensorColor.ORANGE);
                return list;
            }

            @Override
            protected boolean condition() {
                telemetry.addData("Color", colorSensorSide.getColor());
                return colorList.contains(colorSensorSide.getColor());
            }

            //Turn 360 Degrees clockwise to move mineral
        }, FORWARDS);
        wheels.turn(new Angle(360, DEGREES), CLOCKWISE);

        //Robot moves towards the wall, then turns to go to Depot
        //wheels.move(new RangeDistance(DISTANCE_FROM_RIGHT_WALL, rangeSensorFront, false), FORWARDS);
        //wheels.turn(angleToDepot, CLOCKWISE);

                //new GyroAngle(angleToDepot, gyroSensor, CLOCKWISE), CLOCKWISE);
    }
    protected void claimDepotAndPark() {

        //Robot moves to Depot and puts marker down to cliam
        wheels.move(new RangeDistance(new Distance(24, INCH),rangeSensorFront, false), FORWARDS);
        //TODO: Claim depot

        //Robot moves to Crater and parks
        wheels.move(new Condition() {
            @Override
            protected boolean condition() {
                return colorSensorSide.getColor() == SensorColor.BLACK;
                }
            }, BACKWARDS);
    }

    protected void setupHardware() {
        rangeSensorFront = new I2cRangeSensor((I2cDevice) get("rangeSensor"));
        colorSensorBottom = new I2cColorSensor((I2cDevice) get("colorSensorBottom"), new I2cAddr(0x3a));
        colorSensorSide = new I2cColorSensor((I2cDevice) get("colorSensorSide"), new I2cAddr(0X3c));
        getMotor("leftMotor").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        getMotor("rightMotor").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        getMotor("leftMotor").setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        getMotor("rightMotor").setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //gyroSensor = new InterfaceGyro((ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyroSensor"));
        //gyroSensor.calibrate();
        telemetry.addData("Calibrating", "");
        telemetry.update();
        /*while (gyroSensor.isCalibrating()) {
            idle();
        } */
        telemetry.update();

        TwoWheels.WheelConfiguration wheelConfiguration = new TwoWheels.WheelConfiguration("leftMotor", "rightMotor", DcMotor.Direction.FORWARD, DcMotor.Direction.REVERSE);
        TwoEncoderWheels.EncoderConfiguration encoderConfiguration = new TwoEncoderWheels.EncoderConfiguration(1680, new Distance(101.06f, MM), new Distance(37.3f, CM));
        TwoWheels.SpeedSettings speeds = new TwoWheels.SpeedSettings(1f, 0.5f, 0.7f);

        wheels = new TwoEncoderWheels(this, wheelConfiguration, encoderConfiguration, speeds);

        /*new TwoEncoderWheels(this, "leftMotor", "rightMotor",
                new TwoEncoderWheels.EncoderConfiguration(1680, new Distance(37f, MM),
                        new Distance(37.3f, CM)));*/
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
