package com.hazenrobotics.teamcode;

import com.hazenrobotics.commoncode.models.angles.Angle;
import com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;

public class InterfaceGyro {

    ModernRoboticsI2cGyro internalGyro;

    InterfaceGyro(ModernRoboticsI2cGyro internalGyro) {
        this.internalGyro = internalGyro;
    }

    /**
     * Gets the current integrated Z value held by the sensor, which counts up or down as you rotate,
     * as an Angle
     * @return The Integrated Z value as an Angle in the
     */
    public Angle getIntegratedZ() {
        return new Angle(internalGyro.getIntegratedZValue(), UnnormalizedAngleUnit.DEGREES);
    }

    /**
     * Gets the current heading value held by the sensor, which loops back to 0 after you make one
     * full rotation, as an Angle
     * @return The Heading value as an Angle in the
     */
    public Angle getHeading() {
        return new Angle(internalGyro.getHeading(), UnnormalizedAngleUnit.DEGREES);
    }

    /**
     * Calibrates the Gyro, which may take some time to fully complete; consider checking {@link #isCalibrating()}
     * after.
     */
    public void calibrate() {
        internalGyro.calibrate();
    }

    /**
     * Resets the Gyro's Z heading to zero
     */
    public void resetHeading() {
        internalGyro.resetZAxisIntegrator();
    }

    /**
     * Checks if the gyroDevice is still calibrating, either by a full calibration or heading reset
     * @return If the Gyro is currently calibrating
     */
    public boolean isCalibrating() {
        return internalGyro.isCalibrating();
    }
}
