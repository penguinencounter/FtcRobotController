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
    public static AxesOrder defaultAxisOrder = AxesOrder.XYZ;   // Z is headingOffset
    public static AngleUnit defaultAngleUnit = AngleUnit.DEGREES;

    Orientation lastOrient = null;
    Orientation currOrient = null;

    float headingOffset = 0;
    float heading = 0;

    // Screw typecasting; do it automagically
    public IMUtools(BNO055IMU imu) {
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu.initialize(params);
        this.imu = imu;
    }
    public static IMUtools convert(Gyroscope imuConv) {
        return new IMUtools((BNO055IMU) imuConv);
    }

    public Orientation retrieveOrientation() {
        return imu.getAngularOrientation(defaultAxisRef, defaultAxisOrder, defaultAngleUnit);
    }

    public Orientation retrieveCacheOrientation() {
        lastOrient = currOrient;
        currOrient = retrieveOrientation();
        return currOrient;
    }

    public boolean wrapIt() {
        if (lastOrient == null || currOrient == null) {
            return false;
        }
        if (lastOrient.thirdAngle > 90 && currOrient.thirdAngle < -90) {
            headingOffset += 360;
        }
        if (lastOrient.thirdAngle < -90 && currOrient.thirdAngle > 90) {
            headingOffset -= 360;
        }
        heading = currOrient.thirdAngle + headingOffset;
        return true;
    }
}
