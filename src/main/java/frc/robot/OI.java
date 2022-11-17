package frc.robot;

import frc.robot.Constants.RobotMap;
import edu.wpi.first.wpilibj.Joystick;


public class OI {
    private Joystick driveStick; //controls movement
    private Joystick operatorStick; //controls arm
    private boolean lastButtonPressed;

    public OI() {
        driveStick =  new Joystick(RobotMap.JOYSTICK_PORT1);
        operatorStick = new Joystick(RobotMap.JOYSTICK_PORT2);
    }
   
    public Joystick getOperatorStick() {
        return operatorStick;
    }

    public double getDriveStickAxis(int axis) {
        return driveStick.getRawAxis(axis);
    }

    public double getDriveStickThrottle() {
        return driveStick.getThrottle(); 
    }

    public double getOperatorStickAxis(int axis) {
        return operatorStick.getRawAxis(axis);
    }

    public int getOperatorStickSliderAxis() {
        return operatorStick.getAxisCount()-1;
    }

    //drivetrain speed scaling
    public double getDriveMultiplier() {
        double multiplier = getDriveStickAxis((driveStick.getAxisCount()+1))/2;
        if (!getDriverButton(RobotMap.STOP_BUTTON)) {
            multiplier = 0;
        } else if (robotReverse()) {
            multiplier = -1;
        }

        return multiplier;
    }

    private boolean robotReverse() {
        return getDriverButton(2)?true:false;
    }

    public boolean getDriverButton(int button) {
        return driveStick.getRawButton(button);
    }

    public boolean getOperatorButton(int button) {
        return operatorStick.getRawButton(button);
    }

    public boolean getButtonToggle(int b1, int b2) {
        if (operatorStick.getRawButtonPressed(b1)) {
            lastButtonPressed = true;
        } else if (operatorStick.getRawButtonPressed(b2)) {
            lastButtonPressed = false;
        }

        return lastButtonPressed;
    }

    
}
