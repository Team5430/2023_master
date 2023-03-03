package frc.robot;

import javax.swing.text.Position;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.motorcontrol.MotorController;
//import java.beans.Encoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Counter.Mode;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Arm implements Runnable{

    static boolean loop = true;
    //Counter mCounter = new Counter(new DigitalInput(0));
    //declaring motor controllers for Arm
    static MotorController seatMotors = new WPI_TalonSRX(Drive.motorPorts[0]);
    static MotorController extendMotor = new WPI_TalonSRX(Drive.motorPorts[1]);  
    //motors set into a group to program them together when needed   
    //static MotorControllerGroup seatMotors = new MotorControllerGroup(leftSeatMotor, rightSeatMotor); commented
   // static TalonSRX armMotor1 = new TalonSRX(Drive.motorPorts[0]);
   // static TalonSRX armMotor2 = new TalonSRX(Drive.motorPorts[1]);
   // static Encoder encoder = new Encoder(2, 3);
    Counter motorCounter = new Counter(new DigitalInput(1));
    
    
        /* Jio note! Hey hey hey! I did a lot of work on encoders so please read ALL of it!
         
         * !!! Although, if you're REALLY in a hurry, read the ones marked with a !!!.

         * This encoder code initializes a VEX On-Shaft encoder based on how its plugged into the RoboRio DIO ports.
         * The order only determines which direction (clockwise,counterclockwise) is positive, but overall when
         * wiring the only thing that matters is the coloring. Yellow/white goes into the S slot (aka source/data),
         * Red goes into 5V (5-volt),Angle and black/brown goes into the three line label (ground). 
         * 
         * Got that? Nice! As for the actual encoder inputs, when using the command ".getDistance" it will give
         * an amount of "encoder units." One full axle rotation is 90 units, and the encoder reads in fourths, so
         * you'll often see numbers like 34.25 or -15.75. 
         * 
         * !!! Essentially for RAW INPUT, one unit = 4 degrees of rotation, and 1 degree of rotation = 0.25 encoder units. !!!
         * 
         * So for the example listed before hand; 34.25 units would translate to about 137 degrees, and -15.75 units
         * would translate to -63 degrees.
         * 
         * !!! However, this only applies for the raw input. !!! 
         * 
         * ".setDistancePerPulse()" will allow you to convert the raw input into a more usable number automatically.
         * Whatever the input is, it will multiply by the number provided in the function input. So, for ease of use;
         * 
         * !!! Our encoder will always return a proper value of the degree of rotation it thinks it is in. !!!!
         * 
         * That's why it is set to 4; the actual degree of rotation would just be 4x the raw input.
         * Of course, rotation is relative, so it will always need some sort of "zero point" to measure off of.
         * A limit switch at the "zero point" or some sort of driver-sent command can establish this.
         * 
         * Use "reset()" to zero the encoder. Good luck~
        */ 
            //Was edited to be able to use the seat motor's encoder - RL

        // JL, below code establishes the three constants for PID loops plus the variable for armPosition.
    double kP = 1;
    double kI = 1;
    double kD = 1;
    double armPos = 0;  
    public static double position;
   

    public void run(){
            
        while(loop){
        
     if(Robot.controller0.getRawAxis(3) > .5){position += motorCounter.get();} // if joystick for arm is going up, position goes up in numbers
        if(Robot.controller0.getRawAxis(3) < .5){position -= motorCounter.get();}// if joystick is going downwards, position goes down in numbers
            if(Robot.controller0.getRawAxis(3) > .5){
             //if directing the right joystick upwards, it shall move the arm outwards
                seatMotors.set(0.9);
       }
                if(Robot.controller0.getRawAxis(3) <.5){
            //if directing the right joystick downwards, it shall move the arm inwards
                seatMotors.set(-0.9);
            //needed safety position needed useing armPos to prevent floor damage and/or robot damage 
            }
            //armPos = encoder.getDistance();
            //no using button 0 as wpilib returns errors on how it doesn't like that; unless you delcare it;  controller0(0) == true
            if(Robot.controller0.getRawButton(1)){
                while(Robot.controller0.getRawButton(1)){

                }
                
            }



            
            
            
            //armMotor.set
            

            //two buttons one goes forward and one goes back; it controls; controls arm
            }
        }


    private Mode DigitalInput(int i) {
        return null;
    }
    }








