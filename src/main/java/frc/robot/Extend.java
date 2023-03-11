package frc.robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Extend implements Runnable {
    
    static Timer s_timer = new Timer();
    static MotorController extendMotor = new WPI_TalonSRX(Drive.motorPorts[1]); 
    static int extending = 0;
    static DigitalInput backLimitSwitch = new DigitalInput(0);
    static int loop = 1; 
    static int mode = 1;

    public static void zeroExtend(){
    
    while(backLimitSwitch.get() == false){
        extendMotor.set(-0.05);
    }
        extending = 0;
    }


    public static void armExtend(double extendforce){
    s_timer.reset();
    while(s_timer.get() < 1){
  
      Arm.extendMotor.set(extendforce); // ControlMode.PercentOutput basically tells next number 
    }
    extending = 1;
  
  }
  public static void armRetract(double extendforce){
    s_timer.reset();
    while(s_timer.get() < 1){
  
      Arm.extendMotor.set(-extendforce); // ControlMode.PercentOutput basically tells next number 
    }
    extending = 0;
  }


    public void run(){
        while(loop==1){
          switch(mode){

            case 1:

          if(Robot.controller0.getRawButton(0)){
            while(!Robot.controller0.getRawButton(0));
            if(extending == 1){
              armRetract(0.1);
            }else{
              armExtend(0.1);
            }

          }

            break;

            case 2:

              if(Robot.controller0.getRawAxis(1) > 0.1 || Robot.controller0.getRawAxis(1) < -0.1){
                    armExtend(Robot.controller0.getRawAxis(1));
                    }

          }

        }
    }

}
