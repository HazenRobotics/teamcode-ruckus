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
    private I2cColorSensor colorSensor;
    @Override
    public void runOpMode() {
        //colorSensor = new I2cColorSensor((I2cDevice) hardwareMap.get("colorSensorBottom"), new I2cAddr(0x3a));

        ModernRoboticsI2cColorSensor colorSensorA = (ModernRoboticsI2cColorSensor) hardwareMap.colorSensor.get("colorSensorSide");
        colorSensorA.enableLed(true);
        colorSensorA.setI2cAddress(new I2cAddr(0x3c));//side sensor
        waitForStart();

        while(opModeIsActive()){
            //telemetry.addData("Color", colorSensor.getColorValue());
            telemetry.addData("MR Color", colorSensorA.green());
            //telemetry.addData("ColorA", colorSensorA.read8(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
            //telemetry.addData("Math", ((byte) -1) & 0xFF);
            telemetry.update();
        }
    }
}
