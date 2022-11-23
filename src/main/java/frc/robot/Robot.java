
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


public class Robot extends TimedRobot {
  //get instances of all subsystems (w/ a subsystem manager for many subsystems)

  private RobotContainer m_robotContainer;

  private Command m_autonomousCommand;

  //leave constructor empty for now, unless something needs to be done every time robot is called
  public Robot() {

  }

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    //register subsystems into a list to minimize redundancy if logging inputs/outputs for all
  }


  @Override
  public void robotPeriodic() {
    //continue to run commands after initialization
    CommandScheduler.getInstance().run();
    //perform additional periodic calculations that don't fit inside the available commands
    //e.g., pose calculations, outputting logs to smart dashboard, outputting telemetry
  }

  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  //e.g., turn off coprocessor stuff
  @Override
  public void disabledPeriodic() {}


//initialize auto code (commands are periodically scheduled in robotPeriodic)
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }


  //schedule commands for what the robot should do after auto period ends
  @Override
  public void teleopInit() {
    new SequentialCommandGroup(
      new DriveCommand(m_robotContainer.drivetrainSubsystem.drive(0, 1, 1, JoystickScaling.LINEAR, 4, Constants.DriveStyle.CUSTOM_TANK).withTimeout(5),
      new DriveCommand(m_robotContainer.drivetrainSubsystem.drive(0, 1, 1, JoystickScaling.LINEAR, 4, Constants.DriveStyle.NORMAL_ARCADE).withTimeout(5),
      new DriveCommand(m_robotContainer.drivetrainSubsystem.drive(0, 1, 1, JoystickScaling.LINEAR, 4, Constants.DriveStyle.SATURATED_ARCADE).withTimeout(5),
      new DriveCommand(m_robotContainer.drivetrainSubsystem.drive(0, 1, 1, JoystickScaling.LINEAR, 4, Constants.DriveStyle.ARCADE_TANK).withTimeout(5)
    );

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

}
