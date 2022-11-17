package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.RobotMap;

public class ArmSubsystem extends SubsystemBase {
    private CANSparkMax armMotor;
    private boolean armStatus = true;
    private Timer armTimer;
    
    public ArmSubsystem() {
        armMotor = new CANSparkMax(RobotMap.ROBOT_ARM_MOTOR_ID, MotorType.kBrushless);
        armMotor.setIdleMode(IdleMode.kBrake);
        otherConfigs();
    }

    public void otherConfigs() {
        armTimer = new Timer();
        armTimer.start();
    }

    public void moveArm(boolean goUp) {
        if (goUp) {
            if (armTimer.get() < 2) {
                armMotor.set(0.3);
            } else {
                armMotor.set(0.1);
            }
            if (!armStatus) { //if down button is pressed, reset timer
                armTimer.reset();
            }
            armStatus = true;

        } else {
            if (armTimer.get() < 2) {
                armMotor.set(-0.3);
            } else {
                armMotor.set(-0.1);
            }
            if (armStatus) {
                armTimer.reset();
            }
            armStatus = false;
        }
    }

    public void setArmMotorSpeed(double speed) {
        armMotor.set(speed);
    }

}
