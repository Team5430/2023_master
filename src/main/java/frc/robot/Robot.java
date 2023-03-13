// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Threads
  Drive driveRef =  new Drive();
public Thread drive = new Thread(driveRef); //creates a thread for drivetrain
VariableSpeed safety = new VariableSpeed();
Arm armRef = new Arm();
public Thread arm = new Thread(armRef);
private Thread speedSafety = new Thread(safety);
Camera cameraRef = new Camera();
public Thread camera = new Thread(cameraRef);
Extend extendRef = new Extend();
public Thread extend = new Thread(extendRef);
Gripper gripperRef = new Gripper();
public Thread gripper = new Thread(gripperRef);

// JL, Declares autos as strings
private static final String kDefaultAuto = "Default";
private static final String kUTurnAuto = "Uturn Auto";
private static final String kLoopAuto = "Loop Auto";
private static final String kPanic = "Panic Time";
private static final String middleauto = "Middle";
private static final String shootdock = "Shoot and Dock";


private String m_autoSelected;
private final SendableChooser<String> m_chooser = new SendableChooser<>();



public double speedConstant = 3.22;
public double maxSpeed = 16.4;

public int autoStatus = 0;


  //joysticks
  public static Joystick joystickLeft = new Joystick(1);
  public static Joystick joystickRight = new Joystick(2);
  public static Joystick controller0 = new Joystick(0);

  
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");

    public static Timer s_timer = new Timer();
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  
    //Custom Jio code~ Essentially calculates a distance into travel for x time command.
    //Distance is distance in feet, while multiple is a multiplier for the speed of the robot.
    //1 is regular, 2 is 2x, 0.5 would be half, and so on. Original speed is about 3.22, but the constant is changable
    //previously in the code. Larger multiple value means faster travel. Max multiple should be 5. DO NOT GO PAST 5.
    public void driveInMultiple(double distance, double multiple){
      s_timer.reset();
      while(s_timer.get() < distance/(speedConstant)*2)
      Drive.driveTrain.tankDrive(0.2*multiple , -0.2*multiple);
    }
  
      //Custom Jio code~ Essentially caluculates a velocity to travel using a distance and time given.
      //Distance is distance in feet, time is the time in seconds provided to make the trip.
      //Smaller time = Faster velocity
  
    public void driveInTime(double distance, double time){
      s_timer.reset();
      while(s_timer.get() < time){
        Drive.driveTrain.tankDrive((distance/time)/maxSpeed, -(distance/time)/maxSpeed);
      }
    }
  
      //Custom Jio code~ Temporary placeholder function for 90 degree turns.
  
    public void turn90Degrees(String direction){
      s_timer.reset();
      while(s_timer.get() < 1){
        switch(direction){
          case "left":
          Drive.driveTrain.tankDrive(-0.5, -0.5);
          break;
          case "right":
          Drive.driveTrain.tankDrive(0.5, 0.5);
          break;
  
        }
  
        
       
      }
    }
    /* Relocated to Extend.java
  public void armExtend(double extendforce){
    s_timer.reset();
    while(s_timer.get() < 1){
  
      Arm.extendMotor.set(extendforce); // ControlMode.PercentOutput basically tells next number 
    }
  
  }
  public void armRetract(double extendforce){
    s_timer.reset();
    while(s_timer.get() < 1){
  
      Arm.extendMotor.set(-extendforce); // ControlMode.PercentOutput basically tells next number 
    }
  
  }
  */

  /* Relocated to Gripper.java
  public  void gripperBite(double force){//gripper gets something, parameter force is basically 
  
       s_timer.reset();
    while(s_timer.get() < 1){
  
    Gripper.gripperMotor.set(ControlMode.PercentOutput, force); // ControlMode.PercentOutput basically tells next number 
      
    }
  
  }
  public  void gripperRetract(double force){//gripper gets something, parameter force is basically 
  
    s_timer.reset();
 while(s_timer.get() < 1){

   Gripper.gripperMotor.set(ControlMode.PercentOutput, force); // ControlMode.PercentOutput basically tells next number 
   
 }

}
*/
  @Override
  public void robotInit() {
    //Thread starters 
    drive.start();
    speedSafety.start();
    arm.start();
    camera.start();
    gripper.start();
    extend.start();

    //Puts auto list onto Shuffleboard
    SmartDashboard.putData("Auton Choice",m_chooser);
    m_chooser.addOption("Loop Auto", kLoopAuto);
    m_chooser.addOption("Volt Switch", kUTurnAuto);
    m_chooser.addOption("Middle Position Auto", middleauto);
    m_chooser.addOption("Shoot and Dock Autonomous Option", shootdock);

  }

  @Override
  public void robotPeriodic() {

// updating the value from the encoder 
  SmartDashboard.putNumber("Seat motor Values", Arm.position);

  }
  @Override
  public void autonomousInit() {

    m_autoSelected = m_chooser.getSelected();

  }

  @Override
  public void autonomousPeriodic() {
    switch(m_autoSelected){
      case kDefaultAuto:



         driveInMultiple(18.6666667, 1.0);//driveiintime method just moves in a straight line
      Extend.armExtend(0.3);
      Gripper.gripperBite(0.3);//extend the arm with 30% power but since we dont know yet, well just leave this as a prototype
    /**Ethan here, im proposing if we get the length of the arm by getting distance the robot has traveled 
      *minus 18.666667 (which is the length of the nodes to the game piece but since we are using percentage to extend the arm, we dont know yet.)
      *
      *
      **/
      Extend.armRetract(0.3);
      for(int twice = 0; twice<2; twice++){
      
        turn90Degrees("left"); // turn 180 degrees
      }//changeable depending on how far we want the robot should be from the target
        driveInMultiple(18.6666667, 1.0);
        Extend.armExtend(0.5);
       //insert rotate arm length 
       Gripper.gripperBite(0.0); // opening the gripper by setting power of motor to zero




          break;
      case kUTurnAuto:
       
          break;
      case kLoopAuto:
          break;
      case middleauto:



       driveInMultiple(5.0, 0.5);//eyeballeed the 5.0 feet//just dockkkkk `




          break;
      case shootdock:

      Extend.armExtend(0.5);
      //insert rotate arm length 
        Gripper.gripperBite(0.3);
      Extend.armRetract(0.3);
      for(int twice = 0; twice<2; twice++){
            turn90Degrees("left"); // turn 180 degrees
          }
      driveInMultiple(18.6666667, 1.0);
      turn90Degrees("right");
      driveInMultiple(16.6666667, 1.0);

      Extend.armExtend(0.3);
      Gripper.gripperBite(0.5);
      Extend.armRetract(0.3);

      driveInMultiple(-1.0, 1.0);
      turn90Degrees("right");
      driveInMultiple(4.0, 1.0);
      turn90Degrees("right");
      driveInMultiple(2.0, 0.5); //trying to manually code this to balance the robot -ethan
      /*all of these
       * are just concepts!!!
       */





          break;
        case testauto:
        
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
  return controller0.getRawButton(button);
}

  public void baseauton_basic() { //just moves through measurements 

    s_timer.reset(); //sets timer to 0 so everything can play normally
    s_timer.start(); //starts timer so that everything plays normally
    
      driveInMultiple(18.6666667, 1.0);//driveiintime method just moves in a straight line
      Extend.armExtend(0.3);
      Gripper.gripperBite(0.3);//extend the arm with 30% power but since we dont know yet, well just leave this as a prototype
    /**Ethan here, im proposing if we get the length of the arm by getting distance the robot has traveled 
      *minus 18.666667 (which is the length of the nodes to the game piece but since we are using percentage to extend the arm, we dont know yet.)
      *
      *
      **/
      Extend.armRetract(0.3);
      for(int twice = 0; twice<2; twice++){
      
        turn90Degrees("left"); // turn 180 degrees
      }//changeable depending on how far we want the robot should be from the target
        driveInMultiple(18.6666667, 1.0);
        Extend.armExtend(0.5);
       //insert rotate arm length 
       Gripper.gripperBite(0.0); // opening the gripper by setting power of motor to zero
    
  }
  public void middle(){// if you were to start in the middle 
    s_timer.reset(); //sets timer to 0 so everything can play normally
    s_timer.start(); //starts timer so that everything plays normally
      driveInMultiple(5.0, 0.5);//eyeballeed the 5.0 feet
}
 /* public void planleft(){// if you were to start in the right side
    s_timer.reset(); //sets timer to 0 so everything can play normally
    s_timer.start(); //starts timer so that everything plays normally
    while (controller0.getRawButton(0)){ 
  }*/

  public void planshootndock(){//WIPP //i plan to do 3 of these depending on where the other alliance robots want to be (right left or middle)
    s_timer.reset(); //sets timer to 0 so everything can play normallyz
    s_timer.start(); //starts timer so that everything plays normally
      Extend.armExtend(0.5);
      //insert rotate arm length 
      Gripper.gripperBite(0.3);
      Extend.armRetract(0.3);
      for(int twice = 0; twice<2; twice++){
            turn90Degrees("left"); // turn 180 degrees
          }
      driveInMultiple(18.6666667, 1.0);
      turn90Degrees("right");
      driveInMultiple(16.6666667, 1.0);

      Extend.armExtend(0.3);
      Gripper.gripperBite(0.5);
      Extend.armRetract(0.3);
      driveInMultiple(-1.0, 1.0);
      turn90Degrees("right");
      driveInMultiple(4.0, 1.0);
      turn90Degrees("right");
      driveInMultiple(2.0, 0.5); //trying to manually code this to balance the robot -ethan
      /*all of these
       * are just concepts!!!
       */

      
  
  }
}

