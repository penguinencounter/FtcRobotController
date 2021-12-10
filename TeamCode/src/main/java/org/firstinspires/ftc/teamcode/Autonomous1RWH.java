package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Autor1: red right")
public class Autonomous1RWH extends Autonomous1 {
    @Override
    public void runOpMode() {
        configAlliance = Autonomous1.Alliance.RED;
        configAutoTarget = Autonomous1.Targets.WAREHOUSE;
        prepareHardwareMapping();
        prepareRobot();
        waitForStart();
        runFromConfig();
        waitForStop();
    }
}
