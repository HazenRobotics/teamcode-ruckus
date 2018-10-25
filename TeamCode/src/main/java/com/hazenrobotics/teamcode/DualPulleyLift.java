package com.hazenrobotics.teamcode;

import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.models.conditions.Condition;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DualPulleyLift {
    protected OpModeInterface opModeInterface;

    //Motor on the lift that has the mechanical ability to move the lift extension upwards
    protected DcMotor extendingMotor;
    //Motor on the lift that has the mechanical ability to close the lift (an theoretically pulling the robot up with it)
    protected DcMotor retractingMotor;

    public final float slideSpeed;

    protected static final Coefficients ZEROED_COEFFICIENTS = new Coefficients(0f, 0f);

    public DualPulleyLift(OpModeInterface opModeInterface, String extendingMotorName, String retractingMotorName, float slideSpeed) {
        this.opModeInterface = opModeInterface;

        extendingMotor = opModeInterface.getMotor(extendingMotorName);
        retractingMotor = opModeInterface.getMotor(retractingMotorName);

        extendingMotor.setDirection(DcMotor.Direction.FORWARD);
        retractingMotor.setDirection(DcMotor.Direction.REVERSE);
        this.slideSpeed = slideSpeed;
    }

    public void slide(Condition condition, Direction direction) {
        slide(condition, direction, slideSpeed);
    }

    public void slide(Condition condition, Direction direction, float speed) {
        runByCoefficients(condition, calculateSlide(direction), speed);
    }

    public Coefficients calculateSlide(Direction direction) {
        Coefficients coefficients = new Coefficients();

        //TODO: Might need more complex control over motors here (maybe even be based off of their current pos.)
        coefficients.extending = direction.equals(Direction.EXTEND) ? 1f : -1f;
        coefficients.retracting = direction.equals(Direction.EXTEND) ? -1f : 1f;
        return coefficients;
    }

    void runByCoefficients(Condition condition, Coefficients coefficients, float speed) {
        setPower(coefficients, speed);
        while (!condition.isTrue()) {
            opModeInterface.idle();
        }
        stop();
    }

    public void setPower(Coefficients coefficients, float speed) {
        extendingMotor.setPower(coefficients.extending * speed);
        retractingMotor.setPower(coefficients.retracting * speed);
    }

    public void stop() {
        setPower(ZEROED_COEFFICIENTS, 1f);
    }

    public enum Direction {
        EXTEND,
        RETRACT
    }

    public static class Coefficients {
        public float extending;
        public float retracting;

        public Coefficients() {}

        public Coefficients(float extendingCoefficient, float retractingCoefficient) {
            this.extending = extendingCoefficient;
            this.retracting = retractingCoefficient;
        }
    }
}
