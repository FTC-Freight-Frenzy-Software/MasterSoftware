package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

// test 
@Autonomous (name = "Robot Autonomous", group = "Autonomous")
public class RobotAutonomous extends LinearOpMode {
    static RobotHardware robot = new RobotHardware();

    // motor values
    public final double ticksPerRevolution = 537.6;  // the ticks per revolution for our motors, the REV HEX Planetary 20:1
    public final double wheelDiameter = 2.953;  // small mecanum wheels are 75 millimeters in diameter
    public final double power = 1.0;
    public final double ROTATE_CONSTANT = 10.0;

    public void driveForward (double inches) {  // drives the robot forward given a distance
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // TRY NEW ENCODER CABLE
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        robot.frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(encoderDrive);
        robot.frontRightMotor.setTargetPosition(encoderDrive);
        robot.backRightMotor.setTargetPosition(encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait

        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    public void driveBackwards (double inches) {  // given a distance, drive the robot backwards
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int)(rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        robot.frontLeftMotor.setTargetPosition(-encoderDrive);  // set the target position, use negative because we are driving backwards
        robot.backLeftMotor.setTargetPosition(-encoderDrive);
        robot.frontRightMotor.setTargetPosition(-encoderDrive);
        robot.backRightMotor.setTargetPosition(-encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait

        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    /* in mecanum wheels, if the front wheels and the back wheels are
     going in opposite directions and outwards, the robot goes
     to the right
     */

    public void strafeRight (double inches) {  // given a distance, strafe the robot to the right
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back are going in opposite directions and outwards, will go right
        robot.frontLeftMotor.setTargetPosition(encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(-encoderDrive);
        robot.frontRightMotor.setTargetPosition(-encoderDrive);
        robot.backRightMotor.setTargetPosition(encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait

        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    /* in mecanum wheels, if the front wheels and the back wheels are
     going in opposite directions and inwards, the robot goes
     to the left
     */
    public void strafeLeft (double inches) {  // given a distance, strafe the robot to the left by that much
        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = Math.PI * wheelDiameter;
        double rotationsNeeded = inches / circumference;  // calculate the rotations needed based on the circumference
        int encoderDrive = (int) (rotationsNeeded * ticksPerRevolution);  // calculate the total ticks, cast to int

        // the front and the back motors are going in opposite directions and inwards, will go left
        robot.frontLeftMotor.setTargetPosition(-encoderDrive);  // set the target position
        robot.backLeftMotor.setTargetPosition(encoderDrive);
        robot.frontRightMotor.setTargetPosition(encoderDrive);
        robot.backRightMotor.setTargetPosition(-encoderDrive);

        robot.frontLeftMotor.setPower(power);  // set power that will be used, use full speed
        robot.backLeftMotor.setPower(power);
        robot.frontRightMotor.setPower(power);
        robot.backRightMotor.setPower(power);

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.frontLeftMotor.isBusy() || robot.backLeftMotor.isBusy() || robot.frontRightMotor.isBusy() || robot.backRightMotor.isBusy()) {  // wait
            // pause while motors are running
        }

        robot.frontLeftMotor.setPower(0);  // reset power to 0
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    public void rotateRight (int degrees) {
        robot.frontLeftMotor.setPower(1);
        robot.backLeftMotor.setPower(1);
        robot.frontRightMotor.setPower(-1);
        robot.backRightMotor.setPower(-1);

        sleep((int) (degrees * ROTATE_CONSTANT));

        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    public void rotateLeft (int degrees) {
        robot.frontLeftMotor.setPower(-1);
        robot.backLeftMotor.setPower(-1);
        robot.frontRightMotor.setPower(1);
        robot.backRightMotor.setPower(1);

        sleep((int) (degrees * ROTATE_CONSTANT));

        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }

    public void spinWheel(double seconds) {
        robot.carouselServo.setPower(1);
        sleep((int) (seconds * 1000));  // convert to milliseconds
        robot.carouselServo.setPower(0);
    }

    public void redLeftAutonomous () {

    }

    public void redRightAutonomous () {
        driveForward(5);  // go very slightly forward from starting spot
        rotateRight(100);  // rotate 100 degrees to the right
        driveForward(36);  // drive to carousel
        spinWheel(2);  // spin the wheel for 2 seconds

    }

    public void blueLeftAutonomous () {

    }

    public void blueRightAutonomous () {

    }

    public void runOpMode() throws InterruptedException {
        CRServo s = hardwareMap.get(CRServo.class, "servo");

    }
}
