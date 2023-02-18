// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Calendar;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //threads
  Drive driveRef =  new Drive();
public Thread drive = new Thread(driveRef); //creates a thread for drivetrain
VariableSpeed safety = new VariableSpeed();
private Thread speedSafety = new Thread(safety);
private static final String kDefaultAuto = "Default";
private static final String kCustomAuto = "My Auto";
private static final String kNormalAuto = "Normal Auto";
private String m_autoSelected;
private final SendableChooser<String> m_chooser = new SendableChooser<>();


  //joysticks
  public static Joystick joystickLeft = new Joystick(1);
  public static Joystick joystickRight = new Joystick(2);
 
  
    // Constants such as camera and target height stored. Change per robot and goal!
    final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);
    final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);
    // Angle between horizontal and the camera.
    final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

    // How far from the target we want to be
    final double GOAL_RANGE_METERS = Units.feetToMeters(3);

    // Change this to match the name of your camera
    PhotonCamera camera = new PhotonCamera("LED_ringCamera");

    // PID constants should be tuned per robot
    final double LINEAR_P = 0.1;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.1;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

    public static Joystick controller0 = new Joystick(0);

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {
    switch(m_autoSelected){
      case kDefaultAuto:
          break;
      case kCustomAuto:
          break;
      case kNormalAuto:
          break;
    }

  }

  @Override
  public void teleopInit() {
    drive.start();
    speedSafety.start();
  }

  @Override
  public void teleopPeriodic() {
    double rotationSpeed;
    int toggle = 1;
    int toggle_pipeline = 1;

if (joystickButton(3))
   {
    // Vision-alignment mode
    // Query the latest result from PhotonVision
            var result = camera.getLatestResult();
          if(result.hasTargets()) {
    // -1.0 required to ensure positive PID controller effort _increases_ yaw
            rotationSpeed = -turnController.calculate(result.getBestTarget().getYaw(), 0);
  } else {
    // If we have no targets, stay still.
            Drive.driveTrain.tankDrive(Drive.power1, Drive.power2);
            }
  
          }
if(joystickButton(9)){
    //button #9 is the "Start" button on the Logitech F310 controller             
      switch(toggle) 
   {
    case 1:
    //turn driver mode on
           camera.setDriverMode(true);
        toggle = 2;
              break;
    case 2: 
    //turn driver mode off
           camera.setDriverMode(false);
        toggle = 1;
              break;
   }

  }
if(joystickButton(5)){
  while (joystickButton(5)){

    //button #5 is the right bumber on the Logitech F310(aka RB)
      switch(toggle_pipeline)
   {
    case 1:
    //changes pipeline to pipline #1, up to #3 and it goes back to 1.
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
                  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

public static double joystickVal(int port){
    return controller0.getRawAxis(port);}

public static boolean joystickButton(int button){
  return controller0.getRawButton(button);}
}

