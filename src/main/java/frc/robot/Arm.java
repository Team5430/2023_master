package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;

public class Arm implements Runnable {

    static boolean loop = true;
    static boolean auton = false;
    // Counter mCounter = new Counter(new DigitalInput(0));
    // declaring motor controllers for Arm
    // motors set into a group to program them together when needed
    // static MotorControllerGroup seatMotors = new
    // MotorControllerGroup(leftSeatMotor, rightSeatMotor); commented
    Counter motorCounter = new Counter(new DigitalInput(1));

    static TalonSRX seatMotors = new TalonSRX(6);

    public static void setSeatMotors(TalonSRX seatMotors) {
        Arm.seatMotors = seatMotors;

    }

    /*
     * Jio note! Hey hey hey! I did a lot of work on encoders so please read ALL of
     * it!
     * 
     * !!! Although, if you're REALLY in a hurry, read the ones marked with a !!!.
     * 
     * This encoder code initializes a VEX On-Shaft encoder based on how its plugged
     * into the RoboRio DIO ports.
     * The order only determines which direction (clockwise,counterclockwise) is
     * positive, but overall when
     * wiring the only thing that matters is the coloring. Yellow/white goes into
     * the S slot (aka source/data),
     * Red goes into 5V (5-volt),Angle and black/brown goes into the three line
     * label (ground).
     * 
     * Got that? Nice! As for the actual encoder inputs, when using the command
     * ".getDistance" it will give
     * an amount of "encoder units." One full axle rotation is 90 units, and the
     * encoder reads in fourths, so
     * you'll often see numbers like 34.25 or -15.75.
     * 
     * !!! Essentially for RAW INPUT, one unit = 4 degrees of rotation, and 1 degree
     * of rotation = 0.25 encoder units. !!!
     * 
     * So for the example listed before hand; 34.25 units would translate to about
     * 137 degrees, and -15.75 units
     * would translate to -63 degrees.
     * 
     * !!! However, this only applies for the raw input. !!!
     * 
     * ".setDistancePerPulse()" will allow you to convert the raw input into a more
     * usable number automatically.
     * Whatever the input is, it will multiply by the number provided in the
     * function input. So, for ease of use;
     * 
     * !!! Our encoder will always return a proper value of the degree of rotation
     * it thinks it is in. !!!!
     * 
     * That's why it is set to 4; the actual degree of rotation would just be 4x the
     * raw input.
     * Of course, rotation is relative, so it will always need some sort of
     * "zero point" to measure off of.
     * A limit switch at the "zero point" or some sort of driver-sent command can
     * establish this.
     * 
     * Use "reset()" to zero the encoder. Good luck~
     */
    // Was edited to be able to use the seat motor's encoder - RL

    // JL, below code establishes the three constants for PID loops plus the
    // variable for armPosition.
    double kP = 1;
    double kI = 1;
    double kD = 1;
    double armPos = 0;
    public static double position;

    public void run() {

        seatMotors.enableVoltageCompensation(true);

        while (loop) {
            position = motorCounter.getDistance();
            // if not auton then,
            if (!auton) {
                // is the left Y axis being moved up or down?
                if (Robot.controller0.getRawAxis(1) > 0.1
                        ||
                        Robot.controller0.getRawAxis(1) < -0.1) {
                    seatMotors.set(
                            // if so, set seatMotors output to the corresponding number on the joystick
                            ControlMode.PercentOutput, Robot.controller0.getRawAxis(1) * 0.5);
                } else {
                    seatMotors.set(ControlMode.PercentOutput, 0);

                }

            }
        }

    }

    }

