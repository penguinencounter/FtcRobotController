package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Gyroscope;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMUtools {
    public BNO055IMU imu;
    public static AxesReference defaultAxisRef = AxesReference.INTRINSIC;
    public static AxesOrder defaultAxisOrder = AxesOrder.XYZ;
    public static AngleUnit defaultAngleUnit = AngleUnit.DEGREES;

    // Screw typecasting; do it automagically
    public IMUtools(BNO055IMU imu) {
        this.imu = imu;
    }
    public IMUtools(Gyroscope imuConv) {
        this.imu = (BNO055IMU) imuConv;
    }

    public Orientation retrieveOrientation() {
        return imu.getAngularOrientation(defaultAxisRef, defaultAxisOrder, defaultAngleUnit);
    }


}
