package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinCommand extends CommandBase {
    private DrivetrainSubsystem drivetrain;
    private double startingRot;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public SpinCommand(DrivetrainSubsystem driveTrain) {
    this.driveTrain = driveTrain;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startingRot = drivetrain.getHeading();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drive(1, 0, 1, JoystickScaling.LINEAR, 4, DriveStyle.SATURATED_ARCADE);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.drive(0, 0, 1, JoystickScaling.LINEAR, 0, DriveStyle.NORMAL_ARCADE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.getHeading() == startingRot;
  }
}
