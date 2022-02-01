package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;

@Autonomous(name="Auto r2 (testing)")
public class Autonomous2 extends LinearOpMode {

    IMUtools imutools;
    @Override
    public void runOpMode() {
        Gyroscope gyro = hardwareMap.get(Gyroscope.class, "imu");
        imutools = IMUtools.convert(gyro);
        waitForStart();
        while (opModeIsActive()) {
            imutools.retrieveCacheOrientation();
            imutools.wrapIt();
            telemetry.addData("Heading", imutools.heading);
            telemetry.update();
        }
    }
}
