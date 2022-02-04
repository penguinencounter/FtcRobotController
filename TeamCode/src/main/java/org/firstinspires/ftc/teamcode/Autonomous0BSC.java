package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Autor0: blue right")
public class Autonomous0BSC extends Autonomous0 {
    @Override
    public void runOpMode() {
        allianceConfig = StaticFields.Alliance.BLUE;
        targetConfig = StaticFields.Targets.SHIPPING_CONTAINER;
        setupHardware();
        run();
    }
}
