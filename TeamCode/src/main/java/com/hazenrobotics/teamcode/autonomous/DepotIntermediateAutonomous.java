package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.control.BaseOpMode;
import com.hazenrobotics.commoncode.models.angles.Angle;
import com.hazenrobotics.commoncode.models.angles.AngleUnit;
import com.hazenrobotics.commoncode.models.angles.directions.RotationDirection;
import com.hazenrobotics.commoncode.models.angles.directions.SideDirection;
import com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection;
import com.hazenrobotics.commoncode.models.conditions.Condition;
import com.hazenrobotics.commoncode.models.conditions.GyroAngle;
import com.hazenrobotics.commoncode.models.conditions.Timer;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.commoncode.sensors.GyroSensor;
import com.hazenrobotics.commoncode.sensors.InterfaceGyro;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.FORWARDS;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.CM;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;

//Static Imports for different Units

@Autonomous(name = "Depot Intermediate Autonomous", group = "Intermediate")
public class DepotIntermediateAutonomous extends BaseOpMode {
    protected TwoEncoderWheels wheels;
    protected DualPulleyLift lift;
    protected DcMotor sweeperMotor;
    protected GyroSensor gyroSensor;

    protected final static double LIFT_POWER = 0.15;
    protected final static double SWEEPER_SPEED = 0.25;


    @Override
    public void runOpMode() {
        setupHardware();
        lift.initPower(LIFT_POWER);

        waitForStart();

        land();

        sweep();

        park();
    }

    protected void land() {

        lift.slide(new Timer( 3300), DualPulleyLift.Direction.EXTEND,0.5f);
        wheels.move(new Timer(2500), FORWARDS);
    }

    protected void sweep(){
        sweeperMotor.setPower(SWEEPER_SPEED);
        sleep(1000);
        sweeperMotor.setPower(0);
    }

    protected void park(){
        /*wheels.turn(new GyroAngle(new Angle(125f,AngleUnit.DEGREES), gyroSensor, RotationDirection.CLOCKWISE), RotationDirection.CLOCKWISE);
        //wheels.turn(new Timer(1500),RotationDirection.CLOCKWISE);
        wheels.move(new Timer(3700), FORWARDS);
        wheels.move(new Timer(300), FORWARDS, 0.3f);*/

        wheels.turn(new GyroAngle(new Angle(90,AngleUnit.DEGREES), gyroSensor, RotationDirection.COUNTER_CLOCKWISE), RotationDirection.COUNTER_CLOCKWISE);
        wheels.move(new Timer(800), FORWARDS);
        wheels.turn(new GyroAngle(new Angle(24, AngleUnit.DEGREES), gyroSensor, RotationDirection.COUNTER_CLOCKWISE), RotationDirection.COUNTER_CLOCKWISE, 0.2f);
        //wheels.curve(new Timer(1500), FORWARDS, SideDirection.RIGHT, 0.2f, 0.4f);
        wheels.curve(new Timer(3400),  FORWARDS, SideDirection.LEFT, 0.8f, 0.7f);
        //wheels.move(new Timer(3400), FORWARDS);
        wheels.move(new Timer(300), FORWARDS, 0.3f);
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

        gyroSensor = new InterfaceGyro(this, "gyroSensor");
        gyroSensor.calibrate();

        sweeperMotor = getMotor("sweeperMotor");
        sweeperMotor.setDirection(DcMotor.Direction.FORWARD);
    }
}