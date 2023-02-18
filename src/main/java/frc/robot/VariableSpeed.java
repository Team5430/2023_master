package frc.robot;

public class VariableSpeed implements Runnable {
    public static double multiplier = .2;
    @Override
    public void run() {
        while(true){
        if(Robot.controller0.getPOV()  == 0 && multiplier != 1.0){
            while(!(Robot.controller0.getPOV() ==-1) ){

            }
multiplier += .2;

        }
        
        if(Robot.controller0.getPOV()  == 180 && multiplier != .2){
             while(!(Robot.controller0.getPOV() ==-1) ){

            }
            multiplier -= .2;
            
                    }
    }

    }
    public static double getMultiplier(){
        return multiplier;
    }

}
