package com.hazenrobotics.teamcode.autonomous;

import com.hazenrobotics.commoncode.models.angles.Angle;
import static com.hazenrobotics.commoncode.models.angles.UnnormalizedAngleUnit.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "Depot Side", group = "Base Autonomous")
public class DepotSideAutonomous extends BaseAutonomous {

    public DepotSideAutonomous() {
        super(new Angle(-135, DEGREES));
    }
}
