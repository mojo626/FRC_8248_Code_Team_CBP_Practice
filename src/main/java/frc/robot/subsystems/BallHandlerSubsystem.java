package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotMap;

public class BallHandlerSubsystem extends SubsystemBase {
    private WPI_VictorSPX ballHandlerMotor;

    public BallHandlerSubsystem() {
        ballHandlerMotor = new WPI_VictorSPX(RobotMap.BALLHANDLER_MOTOR_ID);
    }

    public void runBallHandler(double speed, boolean intake, boolean shoot, boolean stop) {
        speed = -(speed+1)/2;
        if (intake) {
            ballHandlerMotor.set(0.6);
        } else if (shoot) {
            ballHandlerMotor.set(speed);
        } else if (stop) {
            ballHandlerMotor.set(0);
        }
    }

    public void stopBallHandler() {
        ballHandlerMotor.set(0);
    }
    
}
