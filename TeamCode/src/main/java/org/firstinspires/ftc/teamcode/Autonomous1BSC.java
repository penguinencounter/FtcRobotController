package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="Autor1: blue right")
public class Autonomous1BSC extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = StaticFields.Alliance.BLUE;
        configAutoTarget = StaticFields.Targets.SHIPPING_CONTAINER;
        prepareHardwareMapping();
        prepareRobot1();
        waitForStart();
        prepareRobot2();
        runFromConfig();
        postPrepare();
        waitForStop();
    }
}
