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
    protected DcMotor armMotor;
    protected DcMotor hingeMotor;
    protected DcMotor sweeperMotor;
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
        armMotor = getMotor("armMotor");
        hingeMotor = getMotor("hingeMotor");
        sweeperMotor = getMotor("sweeperMotor");

        armMotor.setDirection(DcMotor.Direction.FORWARD);
        hingeMotor.setDirection(DcMotor.Direction.FORWARD);
        sweeperMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    protected void Arm(){
        if(gamepad2.right_trigger>=0.05){
            armMotor.setPower(gamepad2.right_trigger);
        }else if(gamepad2.left_trigger>=0.05){
            armMotor.setPower(gamepad2.left_trigger);
        }else{
            armMotor.setPower(0);
        }
    }

    protected void Hinge(){
        if(gamepad2.y){
            hingeMotor.setPower(speed);
        }else if(gamepad2.a){
            hingeMotor.setPower(-speed);
        }else{
            hingeMotor.setPower(0);
        }
    }

    protected void Sweeper(){
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
