package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous1;

@Autonomous(name="Autor1: red left")
public class Autonomous1RSC extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = Autonomous1.Alliance.RED;
        configAutoTarget = Autonomous1.Targets.SHIPPING_CONTAINER;
        prepareHardwareMapping();
        prepareRobot();
        waitForStart();
        runFromConfig();
        waitForStop();
    }
}
