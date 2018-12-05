package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.control.BaseOpMode;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.movement.DrivingController;
import com.hazenrobotics.commoncode.movement.TankControlsDrivingController;
import com.hazenrobotics.commoncode.movement.TwoWheels;
import com.hazenrobotics.teamcode.DualPulleyLift;
import com.hazenrobotics.teamcode.DualPulleyLiftController;
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

    //Motors
    protected DcMotor armMotor;
    protected DcMotor hingeMotor;
    protected DcMotor axelMotor;
    protected DcMotor sweeperMotor;
    //Constants
    protected static final double PIVOT_SPEED = 0.3;
    protected static final double ARM_EXTENDING_SPEED = 0.5;
    protected static final double SWEEPER_SPEED = 0.2;

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

            Arm();
            Hinge();
            Axel();
            Sweeper();
            idle();
        }
        liftController.stopMotion();

        armMotor.setPower(0);
        hingeMotor.setPower(0);
        axelMotor.setPower(0);
        sweeperMotor.setPower(0);
    }

    protected void setupHardware() {
        //Initializes the motor/servo variables here
        wheels = new TwoWheels(this, new TwoWheels.WheelConfiguration("leftMotor","rightMotor",DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD));
        driving = new TankControlsDrivingController(wheels, gamepad1);

        lift = new DualPulleyLift(this, "extendingMotor", "retractingMotor", 1.0f);
        liftController = new DualPulleyLiftController(lift, gamepad2, gamepad1, 1f);

        armMotor = getMotor("armMotor");
        hingeMotor = getMotor("hingeMotor");
        axelMotor = getMotor("axelMotor");
        sweeperMotor = getMotor("sweeperMotor");
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        hingeMotor.setDirection(DcMotor.Direction.FORWARD);
        axelMotor.setDirection(DcMotor.Direction.FORWARD);
        sweeperMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    //Method to extend and retract arm. Uses triggers.
    protected void Arm(){
        //Right trigger extends, left trigger retracts.
        if(gamepad2.right_trigger>=0.05){
            armMotor.setPower(gamepad2.right_trigger*ARM_EXTENDING_SPEED);
        }else if(gamepad2.left_trigger>=0.05){
            armMotor.setPower(-gamepad2.left_trigger*ARM_EXTENDING_SPEED);
        }else{
            armMotor.setPower(0);
        }
    }

    //Method to pivot arm. Uses y and a buttons.
    protected void Hinge(){
        //Y lifts arm up, A lowers arm down.
        if(gamepad2.y){
            hingeMotor.setPower(PIVOT_SPEED);
        }else if(gamepad2.a){
            hingeMotor.setPower(-PIVOT_SPEED);
        }else{
            hingeMotor.setPower(0);
        }
    }

    //Method to pivot bucket. Uses b and x buttons.
    protected void Axel(){
        //B lifts bucket up, X lowers bucket down.
        if(gamepad2.dpad_up){
            axelMotor.setPower(PIVOT_SPEED);
        }else if(gamepad2.dpad_down){
            axelMotor.setPower(-PIVOT_SPEED);
        }else{
            axelMotor.setPower(0);
        }
    }

    //Method to rotate sweeper. Uses bumpers.
    protected void Sweeper(){
        //Right bumper sweeps things into scoop, left bumper spits stuff out.
        if(gamepad2.right_bumper){
            sweeperMotor.setPower(-SWEEPER_SPEED);
        }else if(gamepad2.left_bumper){
            sweeperMotor.setPower(SWEEPER_SPEED);
        }else{
            sweeperMotor.setPower(0);
        }
    }
}
