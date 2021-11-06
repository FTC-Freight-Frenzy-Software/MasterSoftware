package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

// test 
@Autonomous (name = "Robot Autonomous", group = "Autonomous")
public class RobotAutonomous extends LinearOpMode {

    public DcMotor backLeftMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor intakeMotor = null;
    public DcMotor liftMotor = null;
    public CRServo carouselServo = null;
    public Servo boxServo = null;

    // motor values
    public final double ticksPerRevolution = 537.6;  // the ticks per revolution for our motors, the REV HEX Planetary 20:1
    public final double wheelDiameter = 2.953;  // small mecanum wheels are 75 millimeters in diameter
    public final double power = 1.0;
    public final double ROTATE_CONSTANT = 10.0;

    public void driveForward (double inches) {  // drives the robot forward given a distance
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // TRY NEW ENCODER CABLE
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        backLeftMotor.setTargetPosition(encoderDrive);
        frontRightMotor.setTargetPosition(encoderDrive);
        backRightMotor.setTargetPosition(encoderDrive);

        frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (frontLeftMotor.isBusy() || backLeftMotor.isBusy() || frontRightMotor.isBusy() || backRightMotor.isBusy()) {  // wait
            telemetry.addData("Front Left Motor", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right Motor", frontRightMotor.getCurrentPosition());
            telemetry.addData("Back Left Motor", backLeftMotor.getCurrentPosition());
            telemetry.addData("Back Right Motor", backRightMotor.getCurrentPosition());

            telemetry.update();
        }

        frontLeftMotor.setPower(0);  // reset power to 0
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void driveBackwards (double inches) {  // given a distance, drive the robot backwards
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int)(rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        frontLeftMotor.setTargetPosition(-encoderDrive);  // set the target position, use negative because we are driving backwards
        backLeftMotor.setTargetPosition(-encoderDrive);
        frontRightMotor.setTargetPosition(-encoderDrive);
        backRightMotor.setTargetPosition(-encoderDrive);

        frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontLeftMotor.isBusy() || backLeftMotor.isBusy() || frontRightMotor.isBusy() || backRightMotor.isBusy()) {  // wait
            telemetry.addData("Front Left Motor", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right Motor", frontRightMotor.getCurrentPosition());
            telemetry.addData("Back Left Motor", backLeftMotor.getCurrentPosition());
            telemetry.addData("Back Right Motor", backRightMotor.getCurrentPosition());

            telemetry.update();
        }

        frontLeftMotor.setPower(0);  // reset power to 0
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    /* in mecanum wheels, if the front wheels and the back wheels are
     going in opposite directions and outwards, the robot goes
     to the right
     */

    public void strafeRight (double inches) {  // given a distance, strafe the robot to the right
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back are going in opposite directions and outwards, will go right
        frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        backLeftMotor.setTargetPosition(-encoderDrive);
        frontRightMotor.setTargetPosition(-encoderDrive);
        backRightMotor.setTargetPosition(encoderDrive);

        frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontLeftMotor.isBusy() || backLeftMotor.isBusy() || frontRightMotor.isBusy() || backRightMotor.isBusy()) {  // wait

        }

        frontLeftMotor.setPower(0);  // reset power to 0
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    /* in mecanum wheels, if the front wheels and the back wheels are
     going in opposite directions and inwards, the robot goes
     to the left
     */
    public void strafeLeft (double inches) {  // given a distance, strafe the robot to the left by that much
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back motors are going in opposite directions and inwards, will go left
        frontLeftMotor.setTargetPosition(-encoderDrive);  // set the target position
        backLeftMotor.setTargetPosition(encoderDrive);
        frontRightMotor.setTargetPosition(encoderDrive);
        backRightMotor.setTargetPosition(-encoderDrive);

        frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontLeftMotor.isBusy() || backLeftMotor.isBusy() || frontRightMotor.isBusy() || backRightMotor.isBusy()) {  // wait
            // pause while motors are running
        }

        frontLeftMotor.setPower(0);  // reset power to 0
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void rotateRight (int degrees) {
        frontLeftMotor.setPower(1);
        backLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        backRightMotor.setPower(-1);

        sleep((int) (degrees * ROTATE_CONSTANT));

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void rotateLeft (int degrees) {
        frontLeftMotor.setPower(-1);
        backLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        backRightMotor.setPower(1);

        sleep((int) (degrees * ROTATE_CONSTANT));

        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void spinWheel(double seconds) {
        carouselServo.setPower(1);
        sleep((int) (seconds * 1000));  // convert to milliseconds
        carouselServo.setPower(0);
    }

    public void redRightAutonomous () {
        driveForward(5);  // go very slightly forward from starting spot
        rotateRight(100);  // rotate 100 degrees to the right
        driveForward(36);  // drive to carousel
        spinWheel(2);  // spin the wheel for 2 seconds

    }

    public void runOpMode() throws InterruptedException {
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        carouselServo = hardwareMap.get(CRServo.class, "carouselServo");
        boxServo = hardwareMap.get(Servo.class, "boxServo");

        driveForward(10);
        driveBackwards(10);
    }
}
