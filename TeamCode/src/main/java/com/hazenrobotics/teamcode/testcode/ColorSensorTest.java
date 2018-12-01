package com.hazenrobotics.teamcode.testcode;

import com.hazenrobotics.commoncode.sensors.I2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;

@Autonomous(name = "color Sensor Test", group = "Test")
public class ColorSensorTest extends LinearOpMode {
    private I2cColorSensor colorSensorBottom;
    @Override
    public void runOpMode() {
        colorSensorBottom = new I2cColorSensor((I2cDevice) hardwareMap.get("colorSensor"), I2cAddr.create8bit(0x3c));

        //ModernRoboticsI2cColorSensor colorSensorSide = (ModernRoboticsI2cColorSensor)hardwareMap.colorSensor.get("colorSensorSide");
        //colorSensorSide.setI2cAddress(new I2cAddr(0x3c));//side sensor
        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Color Value", colorSensorBottom.getColorValue());
            telemetry.addData("Color", colorSensorBottom.getColor());
            //telemetry.addData("MR Color", colorSensorSide.green());
            //telemetry.addData("ColorA", colorSensorSide.read8(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
            //telemetry.addData("Math", ((byte) -1) & 0xFF);
            telemetry.update();
        }
    }
}
