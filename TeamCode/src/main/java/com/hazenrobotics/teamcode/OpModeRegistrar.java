package com.hazenrobotics.teamcode;

import android.support.annotation.Nullable;

import com.hazenrobotics.commoncode.testclasses.FourWheelsDebug;
import com.hazenrobotics.commoncode.testclasses.MotorDebug;
import com.hazenrobotics.commoncode.testclasses.ServoDebug;
import com.hazenrobotics.commoncode.testclasses.TwoWheelsDebug;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor;
import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor.*;

public final class OpModeRegistrar {

    private OpModeRegistrar() {}

    private static List<OpModeEntry> opModesList = getOpModes();

    private static List<OpModeEntry> getOpModes() {
        List<OpModeEntry> list = new ArrayList<>();

        list.add(new OpModeEntry("Two Wheels Debug", "Test", TELEOP, new TwoWheelsDebug("leftMotor", "rightMotor")));
        //list.add(new OpModeEntry("Four Wheels Debug", "Test", TELEOP, FourWheelsDebug.class));
        //list.add(new OpModeEntry("Motor Debug", "Test", TELEOP, MotorDebug.class));
        //list.add(new OpModeEntry("Servo Debug", "Test", TELEOP, ServoDebug.class));

        return list;
    }

    @com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar
    public static void register(OpModeManager manager) {
        for(OpModeEntry opMode : opModesList) {
            if (opMode.hasInstance) {
                manager.register(opMode.meta, opMode.instance);
            } else {
                manager.register(opMode.meta, opMode.classType);
            }
        }
    }

    private static class OpModeEntry
    {
        private final OpModeMeta meta;
        private final Class<? extends OpMode> classType;
        private final boolean hasInstance;
        private final OpMode instance;

        private OpModeEntry(OpModeMeta meta, Class<? extends OpMode> classType, @Nullable OpMode instance)
        {
            this.meta = meta;
            this.classType = classType;
            this.hasInstance = instance != null;
            this.instance = instance;
        }

        private OpModeEntry(OpModeMeta meta, Class<? extends OpMode> classType) {
            this(meta, classType, null);
        }

        private OpModeEntry(String name, String group, Flavor type, Class<? extends OpMode> classType) {
            this(new OpModeMeta(name, type, group), classType);
        }

        private OpModeEntry(OpModeMeta meta, OpMode instance) {
            this(meta, instance.getClass(), instance);
        }

        private OpModeEntry(String name, String group, Flavor type, OpMode instance) {
            this(new OpModeMeta(name, type, group), instance);
        }
    }
}

