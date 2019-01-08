package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.control.BaseOpMode;
import com.hazenrobotics.commoncode.models.conditions.Timer;
import com.hazenrobotics.commoncode.models.distances.Distance;
import com.hazenrobotics.commoncode.movement.TwoEncoderWheels;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.hazenrobotics.commoncode.models.angles.directions.SimpleDirection.FORWARDS;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.CM;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;

//Static Imports for different Units

@Autonomous(name = "lAND", group = "Autonomous")
public class JustLaND extends BaseOpMode {

    protected TwoEncoderWheels wheels;
    protected DualPulleyLift lift;

    protected final static double LIFT_POWER = 0.15;

    @Override
    public void runOpMode() {
        setupHardware();
        lift.initPower(LIFT_POWER);

        waitForStart();

        land();
    }

    protected void land() {

        lift.slide(new Timer( 3000), DualPulleyLift.Direction.EXTEND,0.5f);
        wheels.move(new Timer(200), FORWARDS);
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
    }
}
