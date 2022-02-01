package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "IMU test", group = "Debug")
public class IMUtest extends LinearOpMode {
    IMUtools imutools;
    @Override
    public void runOpMode() {
        Gyroscope imu = hardwareMap.get(Gyroscope.class, "imu");
        imutools = IMUtools.convert(imu);
        waitForStart();
        while (opModeIsActive()) {
            Orientation o = imutools.retrieveOrientation();
            telemetry.addData("IMU Orientation", "1: %f.1 2: %f.1 3: %f.1",
                    o.firstAngle, o.secondAngle, o.thirdAngle);
            telemetry.update();
        }
    }
}
