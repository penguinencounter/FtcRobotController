package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autor0: red left")
public class Autonomous0RSC extends Autonomous0 {
    @Override
    public void runOpMode() {
        allianceConfig = StaticFields.Alliance.RED;
        targetConfig = StaticFields.Targets.SHIPPING_CONTAINER;
        setupHardware();
        run();
    }
}
