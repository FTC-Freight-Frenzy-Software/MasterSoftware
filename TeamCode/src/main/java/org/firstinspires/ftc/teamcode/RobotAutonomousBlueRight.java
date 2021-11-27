package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

// test
@Autonomous (name = "Blue Right", group = "Autonomous")
public class RobotAutonomousBlueRight extends LinearOpMode {
    public DcMotor backLeftMotor = null;
    public DcMotor frontLeftMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor intakeMotor = null;
    public DcMotor liftMotor = null;
    public CRServo carouselServo = null;
    public Servo boxServo = null;

    // vision values
    static final int line = 300;
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };
    private static final String VUFORIA_KEY = "AQQmsVn/////AAABmQjk2+3dZE+Tk5oj3L8j0DJvG4NWcCztbIl7BYnLuRUbBBF7ocAhc5kq25SO33annXS4Vn8kAruErc1ETaO+pralkAh4QcvBa9mL4/g+e01KmfAIBGHsJzRIHoravhIvOhdHODQzQu77u3h/hYmD9MSFE+e5d+yQOmWTl5dKZWUwLMiYY4KEXtOMTkP99vK3Jk8lINPpyDyFp6cDrxSpwz7rs9A8HCD8aXiuK8RDRyc3bTEe7aphVTrEzWADQHMwozaegUBlgtnAtlMHa4Ea8Hl21jWRu00haLb9lVNTsIyak5h8ZeJFcGj17AxYQ+iYt6YihHPw2MOrQzFhSKL+NwjWlDYHjlcehVjQ9Xq2d4xo";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    // motor values
    public final double ticksPerRevolution = 560;  // the ticks per revolution for our motors, the REV HEX Planetary 20:1
    public final double wheelDiameter = 3.77953;  // small mecanum wheels are 75 millimeters in diameter
    public final double power = 0.6;
    public final double ROTATE_CONSTANT = 9;

    public void runOpMode() throws InterruptedException {
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        carouselServo = hardwareMap.get(CRServo.class, "carouselServo");
        boxServo = hardwareMap.get(Servo.class, "boxServo");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        liftMotor.setDirection(DcMotor.Direction.REVERSE);

        //initVuforia();
        //initTfod();

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.05, 16.0/7.0);
        }

        int vision = 2;
        /*
        int vision = sense();
        telemetry.addData("vision", vision);
        telemetry.update();

         */

        waitForStart();

        /*
        // raise lift while moving
        if (vision == 0) {  // lowest
            liftRaise(0);
        }
        else if (vision == 1) {  // middle
            liftRaise(5);
        }
        else {  // highest
            liftRaise(10.5);
        }
         */

        driveBackwards(34);  // pull out from wall
        sleep(500);
        rotateLeft(90);
        sleep(500);
        driveBackwards(5.5);  // go to carousel
        //boxServo.setPosition(0);  // drop
        sleep(2000);
        driveForward(7, 0.6);
        sleep(500);
        rotateRight(63);  // angle towards carousel
        sleep(500);
        driveForward(38, 0.4);  // reach carousel
        spinCarousel(4);
        driveBackwards(7);
        rotateLeft(58);
        driveForward(7, 0.4);
        strafeLeft(17);
    }

    public void resetLift () {
        // 288 ticks = 2.356 inches
        // 1 tick = 0.0082 inches
        // 1 inch = 121.951 ticks

        liftMotor.setPower(1);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        boxServo.setPosition(1);
    }

    public void driveForward (double inches, double speed) {  // drives the robot forward given a distance
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

        frontLeftMotor.setPower(speed);  // set power that will be used, use full speed
        backLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // run to position
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        while (frontLeftMotor.isBusy() && backLeftMotor.isBusy() && frontRightMotor.isBusy() && backRightMotor.isBusy()) {  // wait
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

        while (frontLeftMotor.isBusy() && backLeftMotor.isBusy() && frontRightMotor.isBusy() && backRightMotor.isBusy()) {  // wait
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

        while (frontLeftMotor.isBusy() && backLeftMotor.isBusy() && frontRightMotor.isBusy() && backRightMotor.isBusy()) {  // wait

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

        while (frontLeftMotor.isBusy() && backLeftMotor.isBusy() && frontRightMotor.isBusy() && backRightMotor.isBusy()) {  // wait
            // pause while motors are running
        }

        frontLeftMotor.setPower(0);  // reset power to 0
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void rotateRight (int degrees) {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // the front and the back motors are going in opposite directions and inwards, will go left
        frontLeftMotor.setTargetPosition((int) (degrees * ROTATE_CONSTANT));  // set the target position
        backLeftMotor.setTargetPosition((int) (degrees * ROTATE_CONSTANT));
        frontRightMotor.setTargetPosition((int) (degrees * -ROTATE_CONSTANT));
        backRightMotor.setTargetPosition((int) (degrees * -ROTATE_CONSTANT));

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

    public void rotateLeft (int degrees) {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);  // reset encoders to zero
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // the front and the back motors are going in opposite directions and inwards, will go left
        frontLeftMotor.setTargetPosition((int) (degrees * - ROTATE_CONSTANT));  // set the target position
        backLeftMotor.setTargetPosition((int) (degrees * -  ROTATE_CONSTANT));
        frontRightMotor.setTargetPosition((int) (degrees * ROTATE_CONSTANT));
        backRightMotor.setTargetPosition((int) (degrees * ROTATE_CONSTANT));

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

    public void spinCarousel(double seconds) {
        carouselServo.setPower(0.6);
        sleep((int) (seconds * 1000));  // convert to milliseconds
        carouselServo.setPower(0);
    }

    public ElapsedTime visionTime = new ElapsedTime();

    public int sense () {  // detect what position the duck is on
        visionTime.reset();
        // 0 = Left, 1 = Middle, 2 = Right
        int returnValue = 2;

        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                sleep(500);
                updatedRecognitions = tfod.getUpdatedRecognitions();

                while (updatedRecognitions == null || updatedRecognitions.size() == 0) {  // no recognitions, duck is on rightmost
                    updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (visionTime.seconds() > 5) {
                        return 2;
                    }
                }

                sleep(200);

                for (Recognition recognition : updatedRecognitions) {
                    float leftPosition = recognition.getLeft();
                    float rightPosition = recognition.getLeft();
                    telemetry.addData("left", leftPosition);
                    telemetry.addData("right", rightPosition);
                    telemetry.update();

                    sleep(2000);

                    if (rightPosition < line) {  // the duck is on the leftmost position
                        returnValue = 0;
                        return returnValue;
                    }
                    else {  // middle position
                        returnValue = 1;
                        return returnValue;
                    }
                }
            }
        }
        return returnValue;
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    public void liftHighest () {  // raises the lift to the highest level and deposit
        liftRaise(10.5);
        boxServo.setPosition(0);
    }

    public void liftMiddle () {  // raises the lift to the middle level and deposit
        liftRaise(5);
        boxServo.setPosition(0);
    }

    public void liftLowest () {  // raises the lift to the lowest level and deposit
        liftRaise(0);
        boxServo.setPosition(0);
    }

    public void liftRaise(double inches) { // ticks to inches
        double ticks = 121.951 * inches;
        // 288 ticks = 2.356 inches
        // 1 tick = 0.0082 inches
        // 1 inch = 121.951 ticks

        liftMotor.setPower(1);
        liftMotor.setTargetPosition((int) ticks);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
