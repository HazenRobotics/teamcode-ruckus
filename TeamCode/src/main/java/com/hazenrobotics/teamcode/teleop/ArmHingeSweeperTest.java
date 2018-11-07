package com.hazenrobotics.teamcode.teleop;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "ArmHingeSweeperTest", group = "Test")
public class ArmHingeSweeperTest extends LinearOpMode implements OpModeInterface {
    //Motors
    protected DcMotor armMotor;
    protected DcMotor hingeMotor;
    protected DcMotor sweeperMotor;
    //Constants
    protected static final double speed = 1;

    @Override
    public void runOpMode(){
        setupHardware();
        waitForStart();
        while(opModeIsActive()){
            Arm();
            Hinge();
            Sweeper();
            idle();
        }
        armMotor.setPower(0);
        hingeMotor.setPower(0);
        sweeperMotor.setPower(0);
    }

    protected void setupHardware(){
        //initializes motor variables
        armMotor = getMotor("armMotor");
        hingeMotor = getMotor("hingeMotor");
        sweeperMotor = getMotor("sweeperMotor");
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        hingeMotor.setDirection(DcMotor.Direction.FORWARD);
        sweeperMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    //Method to extend and retract arm. Uses triggers.
    protected void Arm(){
        //Right trigger extends, left trigger retracts.
        if(gamepad2.right_trigger>=0.05){
            armMotor.setPower(gamepad2.right_trigger);
        }else if(gamepad2.left_trigger>=0.05){
            armMotor.setPower(gamepad2.left_trigger);
        }else{
            armMotor.setPower(0);
        }
    }

    //Method to pivot arm. Uses y and a buttons.
    protected void Hinge(){
        //Y lifts arm up, A lowers arm down.
        if(gamepad2.y){
            hingeMotor.setPower(speed);
        }else if(gamepad2.a){
            hingeMotor.setPower(-speed);
        }else{
            hingeMotor.setPower(0);
        }
    }

    //Method to rotate sweeper. Uses bumpers.
    protected void Sweeper(){
        //Right bumper sweeps things into scoop, left bumper spits stuff out.
        if(gamepad2.right_bumper){
            sweeperMotor.setPower(speed);
        }else if(gamepad2.left_bumper){
            sweeperMotor.setPower(-speed);
        }else{
            sweeperMotor.setPower(0);
        }
    }

    @Override
    public Telemetry getTelemetry() {
        return telemetry;
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
