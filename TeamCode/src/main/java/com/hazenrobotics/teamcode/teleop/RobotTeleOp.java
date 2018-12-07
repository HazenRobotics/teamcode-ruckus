package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.control.BaseOpMode;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.movement.DrivingController;
import com.hazenrobotics.commoncode.movement.TankControlsDrivingController;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.hazenrobotics.teamcode.DualPulleyLiftController;
import com.hazenrobotics.teamcode.ExtendyArm;
import com.hazenrobotics.teamcode.ExtendyArmController;
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
public class RobotTeleOp extends BaseOpMode {
    //Add all global objects and lists

    //Add Motors, Servos, Sensors, etc here
    protected TwoWheels wheels;
    protected DrivingController driving;
    protected DualPulleyLift lift;
    protected DualPulleyLiftController liftController;
    protected ExtendyArm arm;
    protected ExtendyArmController armController;


    @Override
    public void runOpMode() {
        setupHardware();
        //Add any further initialization (methods) here

        telemetry.addData("Inited", "");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Started", "");
            telemetry.update();
            driving.updateMotion();

            liftController.updateMotion();
            armController.updateMotion();

            idle();
        }
        liftController.stopMotion();
        armController.stopMotion();
    }

    protected void setupHardware() {
        //Initializes the motor/servo variables here
        wheels = new TwoWheels(this, new TwoWheels.WheelConfiguration("leftMotor","rightMotor",DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD));
        driving = new TankControlsDrivingController(wheels, gamepad1);
        ((TankControlsDrivingController) driving).reverseDirection();


        lift = new DualPulleyLift(this, "extendingMotor", "retractingMotor", 1.0f);
        liftController = new DualPulleyLiftController(lift, gamepad2, gamepad1, 1f);

        arm = new ExtendyArm(this, "armMotor", "hingeMotor", "axelMotor", "sweeperMotor");
        armController = new ExtendyArmController(arm, gamepad2);
    }
}
