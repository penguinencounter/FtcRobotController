package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autor1: blue left")
public class Autonomous1BWH extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = StaticFields.Alliance.BLUE;
        configAutoTarget = StaticFields.Targets.WAREHOUSE;
        prepareHardwareMapping();
        prepareRobot1();
        waitForStart();
        prepareRobot2();
        runFromConfig();
        postPrepare();
        waitForStop();
    }
}
