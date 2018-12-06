package com.hazenrobotics.teamcode;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ExtendyArm {
    protected OpModeInterface opModeInterface;

    //Motor on the arm which extends and retracts the triangular arm mechanism.
    protected DcMotor extendingArm;
    //Motor on the arm which pivots the arm as a whole up and down on the robot.
    protected DcMotor pivotArm;
    //Motor on the bucket which pivots the bucket up and down on the arm.
    protected DcMotor pivotBucket;
    //Motor on the bucket which can sweep minerals in and out of the bucket.
    protected DcMotor bucketSweeper;

    public ExtendyArm(OpModeInterface opModeInterface, String extendingArmMotorName, String pivotArmMotorName, String pivotBucketMotorName, String bucketSweeperMotorName){
        this.opModeInterface = opModeInterface;

        extendingArm = opModeInterface.getMotor(extendingArmMotorName);
        pivotArm = opModeInterface.getMotor(pivotArmMotorName);
        pivotBucket = opModeInterface.getMotor(pivotBucketMotorName);
        bucketSweeper = opModeInterface.getMotor(bucketSweeperMotorName);

        extendingArm.setDirection(DcMotor.Direction.FORWARD);
        pivotArm.setDirection(DcMotor.Direction.FORWARD);
        pivotBucket.setDirection(DcMotor.Direction.FORWARD);
        bucketSweeper.setDirection(DcMotor.Direction.FORWARD);
    }

    public void setPowerToExtendingArm(float speed){
        extendingArm.setPower(speed);
    }

    public void setPowerToPivotArm(double speed){
        pivotArm.setPower(speed);
    }

    public void setPowerToPivotBucket(double speed){
        pivotBucket.setPower(speed);
    }

    public void setPowerToBucketSweeper(double speed){
        bucketSweeper.setPower(speed);
    }

    public void stop() {
        setPowerToExtendingArm(0);
        setPowerToPivotArm(0);
        setPowerToPivotBucket(0);
        setPowerToBucketSweeper(0);
    }
}
