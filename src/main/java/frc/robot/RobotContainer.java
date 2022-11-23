// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.Constants.RobotMap;
import frc.robot.PathPlanningCode.AutoUtils;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeBallCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.RandomTrajectoryGeneratorCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.BallHandlerSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  //instantiate subsystems and commands
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  public final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  private final BallHandlerSubsystem ballHandlerSubsystem = new BallHandlerSubsystem();

  //auto stuff
  private String trajectoryJSON = "Pathweaver/output/exit1.wpilib.json";
  private AutoUtils autoUtils = new AutoUtils(drivetrainSubsystem);
  
  private final OI oi = new OI();

  private final MoveArmCommand moveArmCommand = new MoveArmCommand(armSubsystem, oi.getButtonToggle(RobotMap.ARM_UP_BUTTON, RobotMap.ARM_DOWN_BUTTON));

  private final DriveCommand driveCommand = new DriveCommand(drivetrainSubsystem, oi.getDriveStickAxis(1), 
  oi.getDriverButton(2)?1:oi.getDriveStickAxis(0), oi.getDriveMultiplier());

  private final IntakeBallCommand intakeBallCommand = new IntakeBallCommand(ballHandlerSubsystem, oi.getOperatorStickSliderAxis(), oi.getOperatorButton(RobotMap.BALL_INTAKE_BUTTON),
   oi.getOperatorButton(RobotMap.BALL_SHOOTER_BUTTON), oi.getOperatorButton(RobotMap.STOP_BUTTON));


  public RobotContainer() {
    //register subsystems and set default commands
    CommandScheduler.getInstance().registerSubsystem(armSubsystem);
    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
    CommandScheduler.getInstance().registerSubsystem(ballHandlerSubsystem);

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Joystick operatorStick = oi.getOperatorStick();
    JoystickButton button9 = new JoystickButton(operatorStick, 9);
    JoystickButton button5 = new JoystickButton(operatorStick, 5);
    button9.whenPressed(new SequentialCommandGroup(new DriveForTime(drivetrainSubsystem, 3, 1, 0, 1), new MoveArmCommand(armSubsystem, false), new IntakeBallCommand(ballHandlerSubsystem, 1, true, false, false), new SpinCommand(drivetrainSubsystem)));
    button5.whenPressed(new SequentialCommandGroup(new ParallelRaceGroup(new BallHandlerSubsystem(ballHandlerSubsystem, 1, true, false, false), new WaitCommand(1)), new RandomTrajectoryGeneratorCommand(drivetrainSubsystem))); //What to do for filePath?
  }

  
  public Command getAutonomousCommand() {
    return autoUtils.runTrajectory(trajectoryJSON);
  }

}
