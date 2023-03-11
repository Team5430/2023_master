package frc.robot;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Encoder implements Runnable{
 // Initializes a duty cycle encoder on DIO pins 0
DutyCycleEncoder driveEncoder = new DutyCycleEncoder(0);
/* 

// Configures the encoder to return a distance of 4 for every rotation
encoder.setDistancePerRotation(4.0);
// Gets the distance traveled
encoder.getDistance();
// Gets if the encoder is connected
encoder.isConnected();  
// Resets the encoder to read a distance of zero at the current position
encoder.reset();

// get the position offset from when the encoder was reset
encoder.getPositionOffset();

// set the position offset to half a rotation
encoder.setPositionOffset(0.5);  
*/

@Override
public void run(){
    //every rotation, it returns a value of 4
 driveEncoder.setDistancePerRotation(4.0);
//if there are less then 5 rotations, then 
 if(driveEncoder.get() <= 20){
// drive forward at half speed
    Drive.driveTrain.tankDrive(0.5, 0.5);
 } 
 else 
 {//and stop
    Drive.driveTrain.tankDrive(0, 0);}
 
}
    }