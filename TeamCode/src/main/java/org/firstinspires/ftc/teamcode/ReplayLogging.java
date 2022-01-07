package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

public class ReplayLogging {
    ArrayList<Gamepad> gamepadTracking = new ArrayList<>();
    ArrayList<DcMotor> encoderTracking = new ArrayList<>();
    public ReplayLogging(Gamepad gamepad1, Gamepad gamepad2) {
        gamepadTracking.add(gamepad1);
        gamepadTracking.add(gamepad2);
    }
}
