// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveForTime extends CommandBase {
  private DrivetrainSubsystem driveTrain;
  private double time;
  private Timer timer;
  private double turn;
  private double forward;
  private double scaling;

  /** Creates a new DriveForTime. */
  public DriveForTime(DrivetrainSubsystem driveTrain, double time, double forward, double turn, double scaling) {
    this.driveTrain = driveTrain;
    this.time = time;
    this.turn = turn;
    this.forward = forward;
    this.scaling = scaling;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = new Timer();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrainSubsystem.drive(turn, forward, scaling, JoystickScaling.LINEAR, 4, DriveStyle.SATURATED_ARCADE);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrainSubsystem.drive(0, 0, 1, JoystickScaling.LINEAR, 0, DriveStyle.NORMAL_ARCADE);
    timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(time);
  }
}
