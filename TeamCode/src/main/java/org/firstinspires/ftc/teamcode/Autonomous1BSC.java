package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autor1: blue right")
public class Autonomous1BSC extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = Autonomous1.Alliance.BLUE;
        configAutoTarget = Autonomous1.Targets.SHIPPING_CONTAINER;
        prepareHardwareMapping();
        prepareRobot1();
        waitForStart();
        prepareRobot2();
        runFromConfig();
        postPrepare();
        waitForStop();
    }
}
