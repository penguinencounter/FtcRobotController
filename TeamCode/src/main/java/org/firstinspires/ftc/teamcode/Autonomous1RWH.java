package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Autor1: red right")
public class Autonomous1RWH extends LinearOpMode {
    @Override
    public void runOpMode() {
        Autonomous1 program = new Autonomous1();
        program.configAlliance = Autonomous1.Alliance.RED;
        program.configAutoTarget = Autonomous1.Targets.WAREHOUSE;
        program.prepareHardwareMapping();
        program.prepareRobot();
        waitForStart();
        program.runFromConfig();
    }
}
