package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;

public class Gripper implements Runnable {

    static Timer s_timer = new Timer();
    public static int loop = 1;
    public static int gripping = 0;
    public static int mode = 1;
    //declare motors here
    static TalonSRX gripperMotor = new TalonSRX(0);

    public static void gripperBite(double force){//gripper gets something, parameter force is basically 
  
       s_timer.reset();
    while(s_timer.get() < 1){
  
    Gripper.gripperMotor.set(ControlMode.PercentOutput, force); // ControlMode.PercentOutput basically tells next number 
      
    }
  
    }
    public static void gripperRetract(double force){//gripper gets something, parameter force is basically 
  
        s_timer.reset();
    while(s_timer.get() < 1){

    Gripper.gripperMotor.set(ControlMode.PercentOutput, force); // ControlMode.PercentOutput basically tells next number 
   
 }

}

    
//need variables to stop gripper to not dig into carpet, and slam into our components 

//Ex. 0 = minimum it can stay at to not dig into carpet, and 1600(rotations?) to not slam into our electronics 



//run() for testing gripping objects
    @Override
    public void run() {
            if (loop==1){
                if (mode == 1){
          if(Robot.controller0.getRawButton(1)){
            while(!Robot.controller0.getRawButton(1));
            if(gripping == 1){
              gripperRetract(0.1);
         ;;;;//////////////////////////////////////    }else{
              gripperBite(0.1);
            }

                }
            }
                if (mode == 2){
                    if(Robot.controller0.getRawAxis(1) > 0.1 || Robot.controller0.getRawAxis(1) < -0.1){
                    gripperBite(Robot.controller0.getRawAxis(1));
                    }
                }
            

        }
        
    }
    
}
