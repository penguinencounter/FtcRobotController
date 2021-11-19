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
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.MechanumWheelDriveAPI;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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
@Autonomous(name="Auto-r1 config red,left", group="Auto-r1")

public class Autonomous1RL extends LinearOpMode {
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

    private void sleepMs(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }

    @Override
    public void runOpMode() {
        expansion_Hub_1 = hardwareMap.get(Blinker.class, "Expansion Hub 1");
        expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        duck_spinner = hardwareMap.get(DcMotor.class, "duck_spinner");
        front_left = hardwareMap.get(DcMotor.class, "front_left");
        front_right = hardwareMap.get(DcMotor.class, "front_right");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        rear_left = hardwareMap.get(DcMotor.class, "rear_left");
        rear_right = hardwareMap.get(DcMotor.class, "rear_right");
        bnimu = (BNO055IMU)imu;
        BNO055IMU.Parameters imuparams = new BNO055IMU.Parameters();
        bnimu.initialize(imuparams);
        rear_left.setDirection(DcMotor.Direction.FORWARD);
        rear_right.setDirection(DcMotor.Direction.FORWARD);
        front_left.setDirection(DcMotor.Direction.REVERSE);
        rear_left.setDirection(DcMotor.Direction.REVERSE);
        
        api = new MechanumWheelDriveAPI(rear_left, rear_right, front_left, front_right);
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Is it working?", bnimu.getSystemStatus());
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        // Begin acceleration integration for pos tracing.
        bnimu.startAccelerationIntegration(null, null, 50);
        
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Orientation orientation = bnimu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            // telemetry.addData("Angle Z", orientation.thirdAngle);
            // Position pos = bnimu.getPosition();
            // telemetry.addData("Pos", pos);
            // Acceleration accel = bnimu.getAcceleration();
            // Acceleration grav = bnimu.getGravity();
            // Acceleration floating = new Acceleration(DistanceUnit.MM, accel.xAccel - grav.xAccel, accel.yAccel - grav.yAccel, accel.zAccel - grav.zAccel, System.nanoTime());
            // telemetry.addData("Accel", floating);
            telemetry.addData("Status", "Running");
            telemetry.update();
            // rear_left.setPower(1d);
            // front_left.setPower(1d);
            // rear_right.setPower(1d);
            // front_right.setPower(1d);
            api.move(1, 1, 1, 1);
            sleepMs(1200);
            api.move(-1, 1, -1, 1);
            sleepMs(1000);
            api.move(1, 1, 1, 1);
            sleepMs(1000);
            api.stopAll();
            break;
        }
        while (opModeIsActive()) {
            telemetry.addData("Status", "Idle");
            telemetry.addData("......", "Waiting for Stop");
            telemetry.update();
        }
        telemetry.addData("Status", "Stopping");
        telemetry.update();
        bnimu.stopAccelerationIntegration();
        rear_left.setPower(0);
        front_left.setPower(0);
        rear_right.setPower(0);
        front_right.setPower(0);
    }
}
