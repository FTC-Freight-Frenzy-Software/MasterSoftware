/*
FTC Dashboard: http://192.168.43.1:8080/dash
Onbot Java: 192.168.43.1:8080
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotHardware { // the hardware map
    // drivetrain motors
    public DcMotor frontLeftMotor = null;  // four drivetrain motors
    public DcMotor frontRightMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor backRightMotor = null;

    static CRServo carouselServo;
    static CRServo boxServo;

    public DcMotor intakeMotor = null;
    public DcMotor liftMotor = null;
    //public DcMotor wheelMotor = null;


    HardwareMap hwMap = null;

    public RobotHardware () { // empty constructor

    }

    public void init (HardwareMap ahwMap) {
        hwMap = ahwMap;

        // front left motor
        frontLeftMotor = hwMap.get(DcMotor.class, "frontLeftMotor");  // initialize the motor
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);  // set as forward
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // do not use encoders for this
        frontLeftMotor.setPower(0.0);  // initialize to no power

        // front right motor
        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setPower(0.0);

        // back left motor
        backLeftMotor = hwMap.get(DcMotor.class, "backLeftMotor");
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setPower(0.0);

        // back right motor
        backRightMotor = hwMap.get(DcMotor.class, "backRightMotor");
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setPower(0.0);

        // box servo
        boxServo = hwMap.get(CRServo.class, "boxServo");
        boxServo.setDirection(CRServo.Direction.FORWARD);


        // carousel servo
        carouselServo = hwMap.get(CRServo.class, "carouselServo");
        carouselServo.setDirection(CRServo.Direction.FORWARD);

        //intake motor
        intakeMotor = hwMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setPower(0.0);

        // lift motor
        liftMotor = hwMap.get(DcMotor.class, "liftMotor");
        liftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setPower(0.0);

        /*
        // wheel motor
        wheelMotor = hwMap.get(DcMotor.class, "wheelMotor");
        wheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wheelMotor.setPower(0.0);
         */

    }

}
