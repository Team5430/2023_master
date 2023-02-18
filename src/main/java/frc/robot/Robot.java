// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
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
Arm armRef = new Arm();
public Thread arm = new Thread(armRef);
private Thread speedSafety = new Thread(safety);
Camera cameraRef = new Camera();
public Thread camera = new Thread(cameraRef);
private static final String kDefaultAuto = "Default";
private static final String kCustomAuto = "My Auto";
private static final String kNormalAuto = "Normal Auto";
private String m_autoSelected;
private final SendableChooser<String> m_chooser = new SendableChooser<>();


  //joysticks
  public static Joystick joystickLeft = new Joystick(1);
  public static Joystick joystickRight = new Joystick(2);
  public static Joystick controller0 = new Joystick(0);

  
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //Thread starters 
    drive.start();
    speedSafety.start();
    arm.start();
    camera.start();

    //Puts auto list onto Shuffleboard
    SmartDashboard.putData("Auton Choice",m_chooser);

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
  public void teleopInit() {}
  
  @Override
  public void teleopPeriodic() {}

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

