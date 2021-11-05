/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.android.AndroidTextToSpeech;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.MechanumWheelDriveAPI;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="MechanumWheelTest", group="Test Scripts")

public class MechanumWheelTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rearLeft = null;
    private DcMotor rearRight = null;
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor duckSpinner = null;
    private DcMotor armVert = null;
    private AndroidTextToSpeech tts;
    private MechanumWheelDriveAPI driveAPI;

    @Override
    public void runOpMode() {
        tts = new AndroidTextToSpeech();
        tts.initialize();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        rearLeft = hardwareMap.get(DcMotor.class, "rear_left");
        rearRight = hardwareMap.get(DcMotor.class, "rear_right");
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        duckSpinner = hardwareMap.get(DcMotor.class, "duck_spinner");
        armVert = hardwareMap.get(DcMotor.class, "arm_vert");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        armVert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeft.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        armVert.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // armVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        duckSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        
        driveAPI = new MechanumWheelDriveAPI(rearLeft, rearRight, frontLeft, frontRight);

        // Wait for the game to start (driver presses PLAY)
        tts.speak("Ready");
        waitForStart();
        runtime.reset();
        tts.speak("Running");
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftY = -gamepad1.left_stick_y;
            double leftX = gamepad1.left_stick_x * 1.1;
            double rightX = gamepad1.right_stick_x;
            
            double[] output = driveAPI.convertInputsToPowers(leftX, leftY, rightX);

            // Send calculated power to wheels
            rearLeft.setPower(output[0]);
            frontLeft.setPower(output[1]);
            rearRight.setPower(output[2]);
            frontRight.setPower(output[3]);
            
            if (gamepad2.y == true) {
                duckSpinner.setPower(1);
            } else if (gamepad2.x == true) {
                duckSpinner.setPower(-1);
            } else {
                duckSpinner.setPower(0);
            }
            
            if (gamepad2.a == true) {
                armVert.setPower(0.2);
            } else if (gamepad2.b == true) {
                armVert.setPower(-0.25);
            } else {
                armVert.setPower(0);
            }

            int vpos = armVert.getCurrentPosition();
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Running");
            telemetry.addData("Arm Position", vpos);
            telemetry.addData("Speeds", "rearLeft (%.2f) rearRight (%.2f) frontLeft (%.2f) frontRight (%.2f)", output[0], output[1], output[2], output[3]);
            telemetry.update();
        }
    }
}
