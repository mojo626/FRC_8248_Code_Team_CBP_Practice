package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveStyle;
import frc.robot.Constants.JoystickScaling;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveCommand extends CommandBase {
    private DrivetrainSubsystem drivetrainSubsystem;
    private double turn;
    private double forward;
    private double scaling;
  
    
    public DriveCommand(DrivetrainSubsystem drivetrain, double turn, double forward, double scaling) {
        this.drivetrainSubsystem = drivetrain;
        this.turn = turn;
        this.forward = forward;
        this.scaling = scaling;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrainSubsystem.drive(turn, forward, scaling, JoystickScaling.LINEAR, 4, DriveStyle.SATURATED_ARCADE);
    }

    @Override
    public void end(boolean interrupted) {
        //same method call as in execute but the turn speed and forward speed are 0
        drivetrainSubsystem.drive(0, 0, 1, JoystickScaling.LINEAR, 0, DriveStyle.NORMAL_ARCADE);

    }
}
