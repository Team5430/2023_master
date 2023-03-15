package frc.robot;

public class VariableSpeed implements Runnable {
    public static double multiplier = .2;
    @Override
    public void run() {
        while(true){
        if(Robot.joystickRight.getRawButton(2) && multiplier != 1.0){
            while(!(Robot.joystickRight.getRawButton(2)) ){

            }
multiplier += .2;

        }
        
        if(Robot.joystickLeft.getRawButton(2) && multiplier != 1.0){
             while(!(Robot.joystickLeft.getRawButton(2)) ){

            }
            multiplier -= .2;
            
                    }
    }

    }
    public static double getMultiplier(){
        return multiplier;
    }

}
