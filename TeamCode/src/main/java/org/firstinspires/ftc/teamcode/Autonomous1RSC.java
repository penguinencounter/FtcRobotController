package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomous1;

@TeleOp(name="Autor1: red left")
public class Autonomous1RSC extends LinearOpMode {
    @Override
    public void runOpMode() {
        Autonomous1 program = new Autonomous1();
        program.configAlliance = Autonomous1.Alliance.RED;
        program.configAutoTarget = Autonomous1.Targets.SHIPPING_CONTAINER;
        program.prepareHardwareMapping();
        program.prepareRobot();
        waitForStart();
        program.runFromConfig();
    }
}
