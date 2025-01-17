package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autor1: red right")
public class Autonomous1RWH extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = StaticFields.Alliance.RED;
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
