/*
Copyright 2021 FIRST Tech Challenge Team 12778

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.concurrent.TimeUnit;



/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@Autonomous(name="Auto-r1 (TESTING ONLY)", group="Auto-r1")

public class Autonomous1 extends LinearOpMode {
    private Blinker expansion_Hub_1;
    private Blinker expansion_Hub_2;
    private DcMotor duck_spinner;
    private DcMotor front_left;
    private DcMotor front_right;
    private Gyroscope imu;
    private DcMotor rear_left;
    private DcMotor rear_right;
    private MechanumWheelDriveAPI api;
    private BNO055IMU bnimu;
    private DcMotor armVert;
    private Servo claw;
    public enum Alliance {
        BLUE, RED
    }
    public enum Targets {
        SHIPPING_CONTAINER, WAREHOUSE
    }
    public Targets configAutoTarget = Targets.SHIPPING_CONTAINER;
    public Alliance configAlliance = Alliance.RED;

    private void sleepMs(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ie) {
            RobotLog.e(ie.getMessage());
        }
    }

    void prepareHardwareMapping() {
        // Connect to devices        deviceName is the actual name on the hardware configuration
        expansion_Hub_1 = hardwareMap.get(Blinker.class, "Expansion Hub 1");
        expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        duck_spinner = hardwareMap.get(DcMotor.class, "duck_spinner");
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        rear_left = hardwareMap.get(DcMotor.class, "rear_left");
        rear_right = hardwareMap.get(DcMotor.class, "rear_right");
        armVert = hardwareMap.get(DcMotor.class, "arm_vert");
        claw = hardwareMap.get(Servo.class, "claw");

        // Reset encoder values
        armVert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Enable and configure imu
        bnimu = (BNO055IMU)imu;   // wrong data type but ok
        BNO055IMU.Parameters imuparams = new BNO055IMU.Parameters();
        bnimu.initialize(imuparams);

        // Configure motors
        rear_left.setDirection(DcMotor.Direction.FORWARD);
        rear_right.setDirection(DcMotor.Direction.FORWARD);
        front_left.setDirection(DcMotor.Direction.REVERSE);
        rear_left.setDirection(DcMotor.Direction.REVERSE);
        // SEE MechanumWheelDriveAPI.java
        api = new MechanumWheelDriveAPI(rear_left, rear_right, front_left, front_right);
    }

    void prepareRobot1() {
        // Close claw (Driver pressed INIT, not START yet)
        claw.setPosition(1.0d);
    }

    void prepareRobot2() {
        // Lift arm to prevent dragging (Driver pressed START)
        if (configAutoTarget == Targets.WAREHOUSE) {
            armVert.setTargetPosition(115);
            armVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armVert.setPower(0.2);
        }
    }

    void postPrepare() {
        // Drop arm to prepare for TeleOp
        if (configAutoTarget == Targets.WAREHOUSE) {
            armVert.setTargetPosition(0);
        }
    }

    void sendStepUpdate(int step) {
        telemetry.addData("Step", step);
        telemetry.update();
    }
    
    void shippingContainer() {
        /*
        1. Forward a little (clear wall)
        2. Turn _toward_ carousel
        3. Forward until parallel with Shipping Container
        ~~ NEW RED:
            4. Turn Right 45
            5. Strafe Left
            6. Spin Duck 5s
            7. Strafe Right
            8. Turn Right 45
        ~~ NEW BLUE:
            4. Turn Left 45
            5. Strafe Right
            6. Spin Duck 5s
            7. Strafe Left
            8. Turn Left 45
        9. Move forward into shipping container
         */
        int duckDirection = -1;
        switch (configAlliance) {
            case RED:
                api.move(1, 1, 1, 1);    // 1
                sendStepUpdate(1);
                sleepMs(500);
                api.move(-1, 1, -1, 1);  // 2
                sendStepUpdate(2);
                sleepMs(1000);
                api.move(1, 1, 1, 1);    // 3
                sendStepUpdate(3);
                sleepMs(1000);
                // DUCK SPINNER
                api.move(1, -1, 1, -1);  // 4
                sendStepUpdate(4);
                sleepMs(800);
                api.move(1, -1, -1, 1);  // 5
                sendStepUpdate(5);
                sleepMs(400);
                api.stopAll();
                duck_spinner.setPower(duckDirection);                        // 6
                sendStepUpdate(6);
                sleepMs(5000);
                duck_spinner.setPower(0);
                api.move(-1, 1, 1, -1);  // 7
                sendStepUpdate(7);
                sleepMs(300);
                api.move(1, -1, 1, -1);  // 8
                sendStepUpdate(8);
                sleepMs(430);
                // END DUCK SPINNER
                api.move(1, 1, 1, 1);    // 9
                sendStepUpdate(9);
                sleepMs(800);
                api.stopAll();
                break;
            case BLUE:
                duckDirection = -1;
                api.move(1, 1, 1, 1);   // 1
                sendStepUpdate(1);
                sleepMs(500);
                api.move(1, -1, 1, -1); // 2
                sendStepUpdate(2);
                sleepMs(1000);
                api.move(1, 1, 1, 1);   // 3
                sendStepUpdate(3);
                sleepMs(1100);
                // DUCK SPINNER
                api.move(1, -1, 1, -1);  // 4
                sendStepUpdate(4);
                sleepMs(1500);
                api.move(1, -1, -1, 1);  // 5
                sendStepUpdate(5);
                sleepMs(500);
                api.stopAll();
                duck_spinner.setPower(duckDirection);                        // 6
                sendStepUpdate(6);
                sleepMs(5000);
                duck_spinner.setPower(0);
                api.move(-1, 1, 1, -1);  // 7
                sendStepUpdate(7);
                sleepMs(100);
                api.move(1, -1, 1, -1);  // 8
                sendStepUpdate(8);
                sleepMs(800);
                // END DUCK SPINNER
                api.move(1, 1, 1, 1);    // 9
                sendStepUpdate(9);
                sleepMs(800);
                api.stopAll();
                break;
        }
    }

    void warehouse() {
        /*
        1. Move forward a little (clear wall)
        2. Turn towards warehouse
        3. Barge into warehouse
        4. Turn left (or right, alliance dependant)
           to have the arm not get stuck on debris on the floor
         */
        switch (configAlliance) {
            case RED:
                api.move(1, 1, 1, 1);
                sleepMs(400);
                api.move(1, -1, 1, -1);
                sleepMs(1000);
                api.move(1, 1, 1, 1);
                sleepMs(2000);
                api.move(-1, 1, -1, 1);
                sleepMs(1000);
                api.stopAll();
                break;
            case BLUE:
                api.move(1, 1, 1, 1);
                sleepMs(400);
                api.move(-1, 1, -1, 1);
                sleepMs(1000);
                api.move(1, 1, 1, 1);
                sleepMs(2000);
                api.move(1, -1, 1, -1);
                sleepMs(1000);
                api.stopAll();
                break;
        }
    }

    public void runFromConfig() {
        // Run Autonomous
        if (configAutoTarget == Targets.SHIPPING_CONTAINER) {
            shippingContainer();
        }
        else if (configAutoTarget == Targets.WAREHOUSE) {
            warehouse();
        }
    }

    public void waitForStop() {
        // Wait until the Autonomous mode is over to stop so we don't pre-emptively start TeleOp
        while (opModeIsActive()) {
            telemetry.addData("Status", "Idle");
            telemetry.addData("......", "Waiting for Stop");
            telemetry.update();
        }
    }

    @Override
    public void runOpMode() {

        // !!! DON'T USE IN COMPETITION !!!
        // (gamepad-based configuration code)

        prepareHardwareMapping();
        prepareRobot2();
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Is it working?", bnimu.getSystemStatus());
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        boolean prevXButton = false;
        boolean prevYButton = false;
        RobotLog.i("Initialization complete; now in configuration mode.");
        while (!isStarted()) {
            telemetry.addData("Status", "Initialized; Configuring");
            telemetry.addData("Alliance", configAlliance);
            telemetry.addData(">", "Press X (GP1) to change");
            telemetry.addData("Program Target", configAutoTarget);
            telemetry.addData(">", "Press Y (GP1) to change");
            telemetry.update();
            if (gamepad1.x && gamepad1.x != prevXButton) {
                switch (configAlliance) {
                    case RED:
                        configAlliance = Alliance.BLUE;
                        RobotLog.i("Alliance switched to BLUE.");
                        break;
                    case BLUE:
                        configAlliance = Alliance.RED;
                        RobotLog.i("Alliance switched to RED.");
                        break;
                }
            }
            if (gamepad1.y && gamepad1.y != prevYButton) {
                switch (configAutoTarget) {
                    case SHIPPING_CONTAINER:
                        configAutoTarget = Targets.WAREHOUSE;
                        RobotLog.i("Autonomous target switched to WAREHOUSE.");
                        break;
                    case WAREHOUSE:
                        configAutoTarget = Targets.SHIPPING_CONTAINER;
                        RobotLog.i("Autonomous target switched to SHIPPING_CONTAINER.");
                        break;
                }
            }
            
            prevXButton = gamepad1.x;
            prevYButton = gamepad1.y;
        }
        RobotLog.i("Exited configuration mode. Verifying start...");
        waitForStart();
        RobotLog.i("Started.");
        // run if the match is started
        if (opModeIsActive()) {
            runFromConfig();
        }
        postPrepare();
        RobotLog.i("Autonomous complete. Idling.");
        while (opModeIsActive()) {
            telemetry.addData("Status", "Idle");
            telemetry.addData("......", "Waiting for Stop");
            telemetry.update();
        }
        RobotLog.i("Stopping.");
        telemetry.addData("Status", "Stopping");
        telemetry.update();
        bnimu.stopAccelerationIntegration();
        rear_left.setPower(0);
        front_left.setPower(0);
        rear_right.setPower(0);
        front_right.setPower(0);
    }
}
