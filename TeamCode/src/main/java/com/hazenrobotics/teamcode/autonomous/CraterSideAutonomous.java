package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.models.angles.Angle;
import static com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Crater Side", group = "Base Autonomous")
public class CraterSideAutonomous extends BaseAutonomous {

    public CraterSideAutonomous() {
        super(new Angle(45, DEGREES));
    }
}
