package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Gripper implements Runnable {

    //declare motors here
    static TalonSRX gripperMotor = new TalonSRX(0);
//need variables to stop gripper to not dig into carpet, and slam into our components 
//Ex. 0 = minimum it can stay at to not dig into carpet, and 1600(rotations?) to not slam into our electronics 




    @Override
    public void run() {

        
    }
    
}
