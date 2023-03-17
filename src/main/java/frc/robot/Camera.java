package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;

import org.photonvision.PhotonCamera;

public class Camera implements Runnable {
    public static PhotonCamera camera = new PhotonCamera("LED_ringCamera");

    private boolean loop = true;
    // Constants such as camera and target height stored. Change per robot and goal!
    final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);
    final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);
    // Angle between horizontal and the camera.
    final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

    // How far from the target we want to be
    final double GOAL_RANGE_METERS = Units.feetToMeters(3);

    // Change this to match the name of your camera

    // PID constants should be tuned per robot
    final double LINEAR_P = 0.1;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.1;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

    @Override
    public void run() {

        double rotationSpeed;
        double forwardSpeed;
        int toggle_pipeline = 1;

        forwardSpeed = -Robot.joystickRight.getRawAxis(1);

        while (loop) {
            if (joystickButton(5)) {
                while (joystickButton(5)) {
                    // button #5 is the right bumber on the Logitech F310(aka RB)
                    switch (toggle_pipeline) {
                        case 1:
                            // changes pipeline to pipline #1, up to #3 and it goes back to 1.
                            camera.setPipelineIndex(1);
                            toggle_pipeline = 2;
                            break;
                        case 2:
                            camera.setPipelineIndex(2);
                            toggle_pipeline = 3;
                            break;
                        case 3:
                            camera.setPipelineIndex(3);
                            toggle_pipeline = 1;
                            break;
                    }
                }
            }
            // WIP
            if (joystickButton(3)) {
                while (joystickButton(3)) {
                    // Vision-alignment mode
                    // Query the latest result from PhotonVision
                    var result = camera.getLatestResult();
                    if (result.hasTargets()) {
                        // -1.0 required to ensure positive PID controller effort _increases_ yaw
                        rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0);
                    } else {
                        // If we have no targets, stay still.
                        rotationSpeed = 0;
                    }
                     {
                        // detecting manual drive
                        rotationSpeed = Robot.joystickLeft.getRawAxis(3);
                    }
                 Drive.driveTrain.tankDrive(forwardSpeed, rotationSpeed);


                }
            }
        }

    }

    private boolean joystickButton(int button) {
        return false;
    }

}
