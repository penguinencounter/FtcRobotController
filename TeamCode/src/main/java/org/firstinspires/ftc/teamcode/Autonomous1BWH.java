package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autor1: blue left")
public class Autonomous1BWH extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = Autonomous1.Alliance.BLUE;
        configAutoTarget = Autonomous1.Targets.WAREHOUSE;
        prepareHardwareMapping();
        prepareRobot();
        waitForStart();
        runFromConfig();
        postPrepare();
        waitForStop();
    }
}
