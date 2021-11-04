package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Mecanum Arcade TeleOp", group = "Mecanum")

public class MecanumArcadeTeleop extends LinearOpMode {

    public void liftRaise (double inches) { // ticks to inches

        double ticks = 122.241 * inches;
        // 288 ticks = 2.356 inches
        // 1 tick = 0.0082 inches
        // 1 inch = 122.241 ticks

        liftMotor.setPower(.5);
        liftMotor.setTargetPosition((int) ticks);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (liftMotor.isBusy()) {

        }

        liftMotor.setPower(0);
    }

    public DcMotor backLeftMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor intakeMotor = null;
    public DcMotor liftMotor = null;
    public CRServo carouselServo = null;
    public Servo boxServo = null;

    // motor power values
    static final double shooterPower = 1.0;

    // toggle buttons
    static final double[] speed = {1.0, 0.15};  // full speed and 15 percent speed
    static int speedPointer = 0;
    static final double[] toggleDirection = {1.0, -1.0};  // default directions and reversed directions
    static int directionPointer = 0;

    @Override

    public void runOpMode() {

        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        carouselServo = hardwareMap.get(CRServo.class, "carouselServo");
        boxServo = hardwareMap.get(Servo.class, "boxServo");

        waitForStart(); // wait for the start button

        while (opModeIsActive()) {  // keep going until the stop button is pressed
            //shooterMotor.setPower(shooterPower);

            double horizontal = gamepad1.left_stick_x;
            double vertical = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;

            backLeftMotor.setPower(toggleDirection[directionPointer] * speed[speedPointer] * (vertical + turn - horizontal));  // arcade drive algorithm for mecanum wheels
            frontLeftMotor.setPower(toggleDirection[directionPointer] * speed[speedPointer] * (vertical + turn + horizontal));
            backRightMotor.setPower(toggleDirection[directionPointer] * speed[speedPointer] * (vertical - turn + horizontal));
            frontRightMotor.setPower(toggleDirection[directionPointer] * speed[speedPointer] * (vertical - turn - horizontal));

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
                liftRaise(13);
            }
            else if (gamepad2.right_bumper) {  // lift down
                liftRaise(-13);
            }
            else {
                liftMotor.setPower(0);
            }

            if (gamepad1.left_trigger > 0) {  // intake
                intakeMotor.setPower(1);
            }
            else if (gamepad1.left_bumper) {  // extake
                intakeMotor.setPower(-1);
            }
            else {
                intakeMotor.setPower(0);
            }

            if (gamepad2.a) {  // spin carousel
                carouselServo.setPower(1);
            }
            else if (gamepad2.b) {
                carouselServo.setPower(-1);
            }
            else {
                carouselServo.setPower(0);
            }

            if (gamepad2.x) {
                boxServo.setPosition(1);
                sleep(1000);
                boxServo.setPosition(0);
            }

            if (gamepad1.a) {
                speedPointer = (speedPointer + 1) % 2;
            }

            if (gamepad1.b) {
                directionPointer = (directionPointer + 1) % 2;
            }
        }
    }
}