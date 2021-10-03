package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "DrivetrainExercise", group = "LinearOpMode")

public class DrivetrainExercise extends LinearOpMode {
    // declare motors
    DcMotor leftMotor;
    DcMotor rightMotor;

    public void runOpMode () {
        // initialize motors
        leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");

        waitForStart();

        // set the motors to controller input
        while (opModeIsActive()) {
            leftMotor.setPower(-1 * gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);
        }
    }
}
