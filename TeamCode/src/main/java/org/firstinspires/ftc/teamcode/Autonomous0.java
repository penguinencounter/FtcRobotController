package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.StaticFields.*;


public class Autonomous0 extends LinearOpMode {
    public Alliance allianceConfig;
    public Targets targetConfig;

    private DcMotor arm_vert;

    private MechanumWheelDriveAPI api;

    public void waitMS(int ms) {
        sleep(ms);
    }

    public void setupHardware() {
        DcMotor front_left = hardwareMap.get(DcMotor.class, "front_left");
        DcMotor front_right = hardwareMap.get(DcMotor.class, "front_right");
        DcMotor rear_left = hardwareMap.get(DcMotor.class, "rear_left");
        DcMotor rear_right = hardwareMap.get(DcMotor.class, "rear_right");
        arm_vert = hardwareMap.get(DcMotor.class, "arm_vert");
        Servo claw = hardwareMap.get(Servo.class, "claw");

        rear_left.setDirection(DcMotor.Direction.FORWARD);
        rear_right.setDirection(DcMotor.Direction.FORWARD);
        front_left.setDirection(DcMotor.Direction.REVERSE);
        rear_left.setDirection(DcMotor.Direction.REVERSE);

        api = new MechanumWheelDriveAPI(rear_left, rear_right, front_left, front_right);
        api.stopAll();

        claw.setPosition(1.0d);
        arm_vert.setTargetPosition(0);
        arm_vert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm_vert.setPower(1);
    }

    public void shippingContainer() {
        switch (allianceConfig) {
            case RED:
                // arm up
                arm_vert.setTargetPosition(90);
                api.move(1, 1, 1, 1);
                waitMS(1400);
                api.move(-1, 1, -1, 1);
                waitMS(1000);
                api.move(1, 1, 1, 1);
                waitMS(1000);
                api.stopAll();
                arm_vert.setTargetPosition(0);
                break;
            case BLUE:
                // arm up
                arm_vert.setTargetPosition(90);
                api.move(1, 1, 1, 1);
                waitMS(1400);
                api.move(1, -1, 1, -1);
                waitMS(1000);
                api.move(1, 1, 1, 1);
                waitMS(1000);
                api.stopAll();
                arm_vert.setTargetPosition(0);
                break;
        }
    }


    public void run() {
        telemetry.addData("Waiting for start", "Hold on");
        telemetry.update();
        waitForStart();
        telemetry.addData("Running", "(okay?)");
        telemetry.update();
        if (targetConfig == Targets.SHIPPING_CONTAINER) {
            shippingContainer();
        }
        while (opModeIsActive()) {
            telemetry.addData("Waiting for stop", "Hold on");
            telemetry.update();
        }
    }


    public void runOpMode() {
        telemetry.addData("error", "Don't use this program!");
        telemetry.update();
        telemetry.speak("oh no");
        waitForStart();
    }
}
