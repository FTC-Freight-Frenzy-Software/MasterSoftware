package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Mecanum Arcade TeleOp", group = "Mecanum")

public class MecanumArcadeTeleop extends LinearOpMode {
    RobotHardware robot = new RobotHardware();

    // motor power values
    static final double shooterPower = 1.0;

    // toggle buttons
    static final double[] toggleSpeeds = {1.0, 0.15};  // full speed and 15 percent speed
    static int speedPointer = 0;
    static final double[] toggleDirection = {1.0, -1.0};  // default directions and reversed directions
    static int directionPointer = 0;

    @Override

    public void runOpMode() {
        robot.init(hardwareMap);  // initialize before the start button

        waitForStart(); // wait for the start button

        while (opModeIsActive()) {  // keep going until the stop button is pressed
            //robot.shooterMotor.setPower(shooterPower);

            double horizontal = gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;

            robot.backLeftMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical + turn - horizontal));  // arcade drive algorithm for mecanum wheels
            robot.frontLeftMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical + turn + horizontal));
            robot.backRightMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical - turn + horizontal));
            robot.frontRightMotor.setPower(toggleDirection[directionPointer] * toggleSpeeds[speedPointer] * (vertical - turn - horizontal));

            if (gamepad1.x) {  // toggle direction
                directionPointer = (directionPointer + 1) % 2;
            }

            if (gamepad1.b) {  // toggle speed
                speedPointer = (speedPointer + 1) % 2;
            }

            telemetry.update();  // update the prints at the end of each cycle
        }
    }
}