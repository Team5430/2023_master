package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;

public class Gripper implements Runnable {

    static Timer s_timer = new Timer();
    // JL, loop is a safety variable and will prevent everything inside the loop when set to 0
    public static int loop = 1;
    // JL, mode will determine how input will work.  1 = B-button toggle, 2 = Triggers Control
    public static int mode = 1;
    // JL, stores whether the robot believes its (0) not gripping or (1) gripping.
    public static int gripping = 0;
    //declare motor here
    static TalonSRX gripperMotor = new TalonSRX(Drive.motorPorts[7]);

    // JL, function to grip with a set power for a second. Trial and error required to determine
    // best force setting.
    public static void gripperBite(double force){//gripper gets something, parameter force is basically 
  
       s_timer.reset();
          while(s_timer.get() < 1){
  
           gripperMotor.set(ControlMode.PercentOutput, force); // ControlMode.PercentOutput basically tells next number 
           gripping = 1;
    }
    
    }
    // JL, function to retract arm with a set power for a second. Can be replaced with gripperBite as
    // long as gripperBite's force is set to a negative value. Same purpose as extend.
    public static void gripperRetract(double force){//gripper gets something, parameter force is basically 
  
        s_timer.reset();
    while(s_timer.get() < 1){

    gripperMotor.set(ControlMode.PercentOutput, -force); // ControlMode.PercentOutput basically tells next number 
    gripping = 0;
 }

}

    
//need variables to stop gripper to not dig into carpet, and slam into our components 

//Ex. 0 = minimum it can stay at to not dig into carpet, and 1600(rotations?) to not slam into our electronics 



//run() for testing gripping objects
    @Override
    public void run() {
     if (loop==1){
                if (mode == 1){ // JL, B-button Toggle mode, B will toggle between gripping and non-gripping
          if(Robot.controller0.getRawButton(2)){
            while(!Robot.controller0.getRawButton(2));
            if(gripping == 1){
              gripperRetract(0.1);
              
            }else{
              gripperBite(0.1);
              
            }

                }
            }
                if (mode == 2){ // JL, Triggers Control mode, use triggers to manually reel gripper in/out
                     // JL, deadzone code, only allows input to gripperBite if input for either trigger is greater than 0.1
                    if(Robot.controller0.getRawAxis(2) > 0.1 || Robot.controller0.getRawAxis(3) > 0.1){
                    gripperBite((Robot.controller0.getRawAxis(3) - Robot.controller0.getRawAxis(2)) * 0.5); // JL, "* 0.5" halves speed for safety
                    }

                    /* Jio's Guide on How to use Triggers Control Mode

                    Right trigger will grip with force equivalent to half of how hard you press.
                    Left trigger attempts to release with force equivalent to half of how hard you press.
                    I know it isn't the most intuitive, but we were seriously running out of usable axises.

                    */

                }
            

        }
        
    }
    
}
