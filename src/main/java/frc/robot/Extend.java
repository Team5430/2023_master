package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Extend implements Runnable {

  static Timer s_timer = new Timer();
  static TalonSRX extendMotor = new TalonSRX(Drive.motorPorts[8]);

  // JL, Change input in DigitalInput() to reflect which port the limit switch is
  // located
  static DigitalInput backLimitSwitch = new DigitalInput(0);

  // JL, loop is a safety variable and will prevent what's inside the loop when
  // set to 0
  static int loop = 1;
  // JL, mode will determine how input will work. 1 = A-button toggle, 2 =
  // leftStick control
  static int mode = 2;
  // JL, stores whether the robot believes its (0) unextended or (1) extended.
  static int extending = 0;

  // JL, function to "zero" arm. Usable only with limit switch attached to back of
  // arm.
  public static void zeroExtend() {

    while (backLimitSwitch.get() == false) {
      extendMotor.set(ControlMode.PercentOutput, -0.05);
    }
    extending = 0;
  }

  // JL, function to extend arm with a set power for a second. Trial and error
  // required to determine
  // best force setting.
  public static void armExtend(double extendforce) {
    s_timer.reset();
    s_timer.start();
    while (s_timer.get() < 1) {

      extendMotor.set(ControlMode.PercentOutput, extendforce); // ControlMode.PercentOutput basically tells next number
    }
    extendMotor.set(ControlMode.PercentOutput, 0);
    extending = 1;

  }

  // JL, function to retract arm with a set power for a second. Can be replaced
  // with armExtend as
  // long as armExtend's force is set to a negative value. Same purpose as extend.
  public static void armRetract(double extendforce) {
    s_timer.reset();
    s_timer.start();
    while (s_timer.get() < 1) {

      extendMotor.set(ControlMode.PercentOutput, -extendforce); // ControlMode.PercentOutput basically tells next number
    }
    extendMotor.set(ControlMode.PercentOutput, 0);
    extending = 0;
  }

  public void run() {

    extendMotor.setNeutralMode(NeutralMode.Brake);
    extendMotor.enableVoltageCompensation(true);

    while (loop == 1) {
      switch (mode) {

        case 1: // JL, A-button toggle mode, toggle bet

          if (Robot.controller0.getRawButton(1)) {
            // while(Robot.controller0.getRawButton(1)); // JL, while statement will prevent
            // action until A is let go
            if (extending == 1) { // i
              armRetract(0.1);
            } else {
              armExtend(0.1);
            }
            while (!Robot.controller0.getRawButton(1))
              ;
          }

          break;

        case 2: // JL, Left-stick control mode
          // JL, deadzone code, only allows input to armExtend if input is higher than 0.1
          // or below -0.1
          if (Robot.controller0.getRawAxis(5) > 0.1 || Robot.controller0.getRawAxis(5) < -0.1) {
            extendMotor.set(ControlMode.PercentOutput, Robot.controller0.getRawAxis(5) * 0.25); // JL, "* 0.5" halves speed for safety
          } else {
            extendMotor.set(ControlMode.PercentOutput, 0);
          }

          break;

      }

    }
  }

}
