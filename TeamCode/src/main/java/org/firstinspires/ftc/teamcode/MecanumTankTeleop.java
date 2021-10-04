package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Mecanum Tank Teleop", group = "LinearOpMode")

public class MecanumTankTeleop extends LinearOpMode {
    RobotHardware robot = new RobotHardware();


    static int[] direction = {1, -1};
    static double[] speed = {1.0, 0.25};

    public void runOpMode () {
        // initialize motors
        RobotHardware robot = new RobotHardware();
        robot.init(hardwareMap);

        waitForStart();

        int directionPointer = 0;
        int speedPointer = 0;

        // set the motors to controller input
        while (opModeIsActive()) {
            // Normalize the values so neither exceed +/- 1.0
            double r = Math.hypot((4 * gamepad1.left_stick_x), gamepad1.left_stick_y);
            double r2 = Math.hypot(gamepad1.right_stick_x, gamepad1.right_stick_y);
            double robotAngle = Math.atan2(gamepad1.left_stick_y, (4 * gamepad1.left_stick_x)) - Math.PI / 4;
            double robotAngle2 = Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            double leftX = (4 * gamepad1.left_stick_x);

            double v3 = r * Math.sin(robotAngle) + leftX;
            double v4 = r * Math.cos(robotAngle) - leftX;
            double v1 = r2 * Math.cos(robotAngle2) + rightX;
            double v2 = r2 * Math.sin(robotAngle2) - rightX;

            // add comment
            robot.frontLeftMotor.setPower(speed[speedPointer] * direction[directionPointer] * v3);
            robot.backLeftMotor.setPower(speed[speedPointer] * direction[directionPointer] * v4);
            robot.frontRightMotor.setPower(speed[speedPointer] * direction[directionPointer] * v2);
            robot.backRightMotor.setPower(speed[speedPointer] * direction[directionPointer] * v1);

            while (gamepad1.dpad_left) {  // while dpad left is held down
                robot.frontLeftMotor.setPower(-1);  // set the target position
                robot.backLeftMotor.setPower(1);
                robot.frontRightMotor.setPower(1);
                robot.backRightMotor.setPower(-1);
            }

            while (gamepad1.dpad_right) {  // while dpad right is held down
                robot.frontLeftMotor.setPower(1);  // set the target position
                robot.backLeftMotor.setPower(-1);
                robot.frontRightMotor.setPower(-1);
                robot.backRightMotor.setPower(1);
            }

            if (gamepad1.x) {  // toggle speedPointer
                speedPointer = (speedPointer + 1) % 2;
            }

            if (gamepad1.b) {  // toggle directionPointer
                directionPointer = (directionPointer + 1) % 2;
            }

            robot.frontRightMotor.setPower(0);
            robot.frontLeftMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.backRightMotor.setPower(0);
        }
    }
}
