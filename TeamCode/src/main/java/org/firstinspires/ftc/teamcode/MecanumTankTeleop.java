package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Mecanum Tank Teleop", group = "LinearOpMode")

public class MecanumTankTeleop extends LinearOpMode {

    public DcMotor backLeftMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor intakeMotor = null;
    public DcMotor liftMotor = null;
    public CRServo carouselServo = null;
    public Servo boxServo = null;

    static int[] direction = {1, -1};
    static double[] speed = {1.0, 0.25};

    public void runOpMode () {

        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        carouselServo = hardwareMap.get(CRServo.class, "carouselServo");
        boxServo = hardwareMap.get(Servo.class, "boxServo");
        // initialize motors
        //RobotHardware robot = new RobotHardware();
        //init(hardwareMap);

        waitForStart();

        int directionPointer = 0;
        int speedPointer = 0;

        while (opModeIsActive()) {
            // Normalize the values so neither exceed +/- 1.0

            double gamepad1LeftStickX = -gamepad1.left_stick_x;
            double gamepad1LeftStickY = gamepad1.left_stick_y;
            double gamepad1RightStickX = -gamepad1.right_stick_x;
            double gamepad1RightStickY = gamepad1.right_stick_y;


            double r = Math.hypot((4 * gamepad1LeftStickX), gamepad1LeftStickY);
            double r2 = Math.hypot(gamepad1RightStickX, gamepad1RightStickY);
            double robotAngle = Math.atan2(gamepad1LeftStickY, (4 * gamepad1LeftStickX)) - Math.PI / 4;
            double robotAngle2 = Math.atan2(gamepad1RightStickY, gamepad1RightStickX) - Math.PI / 4;
            double rightX = gamepad1RightStickX;
            double leftX = (4 * gamepad1LeftStickX);

            double v3 = r * Math.sin(robotAngle) + leftX;
            double v4 = r * Math.cos(robotAngle) - leftX;
            double v1 = r2 * Math.cos(robotAngle2) + rightX;
            double v2 = r2 * Math.sin(robotAngle2) - rightX;

            // add comment
            frontLeftMotor.setPower(speed[speedPointer] * direction[directionPointer] * v3);
            backLeftMotor.setPower(speed[speedPointer] * direction[directionPointer] * v4);
            frontRightMotor.setPower(speed[speedPointer] * direction[directionPointer] * v2);
            backRightMotor.setPower(speed[speedPointer] * direction[directionPointer] * -v1);


            while (gamepad1.dpad_left) {  // strafe left macro
                frontLeftMotor.setPower(-speed[speedPointer]);
                backLeftMotor.setPower(speed[speedPointer]);
                frontRightMotor.setPower(speed[speedPointer]);
                backRightMotor.setPower(-speed[speedPointer]);
            }

            while (gamepad1.dpad_right) {  // strafe right macro
                frontLeftMotor.setPower(speed[speedPointer]);
                backLeftMotor.setPower(-speed[speedPointer]);
                frontRightMotor.setPower(-speed[speedPointer]);
                backRightMotor.setPower(speed[speedPointer]);
            }

            while (gamepad1.dpad_up) {  // forward macro
                frontLeftMotor.setPower(speed[speedPointer]);
                backLeftMotor.setPower(speed[speedPointer]);
                frontRightMotor.setPower(speed[speedPointer]);
                backRightMotor.setPower(speed[speedPointer]);
            }

            while (gamepad1.dpad_down) {  // backwards macro
                frontLeftMotor.setPower(-speed[speedPointer]);
                backLeftMotor.setPower(-speed[speedPointer]);
                frontRightMotor.setPower(-speed[speedPointer]);
                backRightMotor.setPower(-speed[speedPointer]);
            }

            if (gamepad2.right_trigger > 0) {  // lift up
                liftMotor.setPower(0.6);
            }
            else if (gamepad2.right_bumper) {  // lift down
                liftMotor.setPower(-0.6);
            }
            else {
                liftMotor.setPower(0);
            }

            if (gamepad2.left_trigger > 0) {  // intake
                intakeMotor.setPower(1);
            }
            else if (gamepad2.left_bumper) {  // extake
                intakeMotor.setPower(-1);
            }
            else {
                intakeMotor.setPower(0);
            }

            if (gamepad2.b) {  // spin carousel
                carouselServo.setPower(1);
            }
            else {
                carouselServo.setPower(0);
            }

            if (gamepad2.left_trigger > 0) {
                boxServo.setPosition(1);
                sleep(1000);
                boxServo.setPosition(0);
            }
        }
    }
}
