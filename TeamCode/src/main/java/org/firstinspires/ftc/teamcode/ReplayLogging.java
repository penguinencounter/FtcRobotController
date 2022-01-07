package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.ArrayList;

public class ReplayLogging {
    ArrayList<Gamepad> gamepadTracking = new ArrayList<>();
    ArrayList<GamepadHistory> gamepadDiff = new ArrayList<>();
    ArrayList<DcMotor> encoderTracking = new ArrayList<>();
    public ReplayLogging(Gamepad gamepad1, Gamepad gamepad2) {
        gamepadTracking.add(gamepad1);
        gamepadTracking.add(gamepad2);
    }

    public boolean captureGamepadFrame() {
        Gamepad active;
        GamepadHistory activeh;
        for (int i = 0; i < gamepadTracking.size(); i++) {
            active = gamepadTracking.get(i);
            activeh = new GamepadHistory(active);
        }
        for (GamepadHistory diff : gamepadDiff) {
            diff.capture();
        }
    }
}

class GamepadHistory {
    Gamepad tracking;
    boolean a = false;
    boolean b = false;
    boolean x = false;
    boolean y = false;
    boolean left_bumper = false;
    boolean right_bumper = false;

    boolean dpad_up = false;
    boolean dpad_down = false;
    boolean dpad_left = false;
    boolean dpad_right = false;

    boolean guide = false;
    boolean start = false;
    boolean back = false;

    float left_stick_x = 0;
    float left_stick_y = 0;

    float right_stick_x = 0;
    float right_stick_y = 0;

    float left_trigger = 0;
    float right_trigger = 0;

    GamepadHistory(Gamepad gamepad) {
        tracking = gamepad;
        capture();
    }
    public void capture() {
        a = tracking.a;
        b = tracking.b;
        x = tracking.x;
        y = tracking.y;
        left_bumper = tracking.left_bumper;
        right_bumper = tracking.right_bumper;
        dpad_up = tracking.dpad_up;
        dpad_down = tracking.dpad_down;
        dpad_left = tracking.dpad_left;
        dpad_right = tracking.dpad_right;
        guide = tracking.guide;
        start = tracking.start;
        back = tracking.back;

        left_stick_x = tracking.left_stick_x;
        left_stick_y = tracking.left_stick_y;
        right_stick_x = tracking.right_stick_x;
        right_stick_y = tracking.right_stick_y;
        left_trigger = tracking.left_trigger;
        right_trigger = tracking.right_trigger;
    }
    public String compareButtons(boolean self, boolean other, String name) {
        if (self != other && other) {
            return "+"+name;
        } else if (self != other) {
            return "-"+name;
        }
        return "";
    }
    public ArrayList<String> compare(GamepadHistory other) {
        ArrayList<String> messages = new ArrayList<>();
        String m = compareButtons(a, other.a, "a");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(b, other.b, "b");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(x, other.x, "x");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(y, other.y, "y");
        if (!m.isEmpty()) {
            messages.add(m);
        }

        m = compareButtons(left_bumper, other.left_bumper, "lb");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(right_bumper, other.right_bumper, "rb");
        if (!m.isEmpty()) {
            messages.add(m);
        }

        m = compareButtons(dpad_up, other.dpad_up, "dpad_up");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(dpad_down, other.dpad_down, "dpad_down");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(dpad_left, other.dpad_left, "dpad_left");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(dpad_right, other.dpad_right, "dpad_right");
        if (!m.isEmpty()) {
            messages.add(m);
        }

        m = compareButtons(guide, other.guide, "guide");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(start, other.start, "start");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        m = compareButtons(back, other.back, "back");
        if (!m.isEmpty()) {
            messages.add(m);
        }
        return messages;
    }
}
