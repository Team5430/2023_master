package frc.robot;

public class VariableSpeed implements Runnable {
    public static double multiplier = 0;
    @Override
    public void run() {
        while(true){
            if(Robot.joystickRight.getRawButton(3) && multiplier != 0.99){
                while((Robot.joystickRight.getRawButton(3)) ){ } // occupies code while being pressed to delay multiplier
             multiplier += .1;
            }
        
            if(Robot.joystickLeft.getRawButton(3) && multiplier != -0.99){
                while((Robot.joystickLeft.getRawButton(3)) ){ } // occupies code while being pressed to delay multiplier
            multiplier -= .1;
            }
        }
    }
    public static double getMultiplier(){return multiplier;}
}
