package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Autor1: blue right")
public class Autonomous1BSC extends LinearOpMode {
    @Override
    public void runOpMode() {
        Autonomous1 program = new Autonomous1();
        program.configAlliance = Autonomous1.Alliance.BLUE;
        program.configAutoTarget = Autonomous1.Targets.SHIPPING_CONTAINER;
        program.prepareHardwareMapping();
        program.prepareRobot();
        waitForStart();
        program.runFromConfig();
    }
}
