package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.colors.NamedColorList;
import com.hazenrobotics.commoncode.models.colors.SensorColor;
import com.hazenrobotics.commoncode.models.conditions.Condition;
import com.hazenrobotics.commoncode.models.conditions.RangeDistance;
import com.hazenrobotics.commoncode.models.conditions.Timer;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.commoncode.sensors.I2cColorSensor;
import com.hazenrobotics.commoncode.sensors.I2cRangeSensor;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.Servo;

//Static Imports for different Units
import static com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit.*;
import static com.hazenrobotics.commoncode.models.angles.directions.RotationDirection.*;
import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.*;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DepotSimpleAutonomous extends LinearOpMode implements OpModeInterface {

    protected I2cRangeSensor rangeSensor;
    protected I2cColorSensor colorSensorBottom;
    protected TwoEncoderWheels wheels;
    protected DualPulleyLift lift;


    @Override
    public void runOpMode() {
        setupHardware();

        waitForStart();

        land();

        wheels.move(new RangeDistance(new Distance((float) (12 * 2 * Math.sqrt(2)), INCH), rangeSensor, false), FORWARDS);
    }

    protected void land() {
        Condition alternative = new Condition() {
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
                return colorList.contains(colorSensorBottom.getColor());
            }
        };

        lift.slide(new Timer(2500), DualPulleyLift.Direction.EXTEND);
        wheels.move(new Timer(3000), FORWARDS);
        lift.slide(new Timer(2500), DualPulleyLift.Direction.RETRACT);
    }

    protected void setupHardware() {
        rangeSensor = new I2cRangeSensor((I2cDevice) get("rangeSensor"));
        colorSensorBottom = new I2cColorSensor((I2cDevice) get("colorSensorBottom"));

        TwoWheels.WheelConfiguration wheelConfiguration = new TwoWheels.WheelConfiguration(
                "leftMotor", //left name
                "rightMotor",  //right name
                DcMotor.Direction.FORWARD,  //left direction
                DcMotor.Direction.REVERSE); //right direction
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
