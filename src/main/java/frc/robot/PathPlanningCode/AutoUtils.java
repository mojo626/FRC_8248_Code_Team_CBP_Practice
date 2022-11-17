package frc.robot.PathPlanningCode;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoUtils {
    private Trajectory trajectory = new Trajectory();
    private DrivetrainSubsystem drivetrainSubsystem;

    public AutoUtils(DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;
    }

    public SequentialCommandGroup runTrajectory(String trajectoryJSON) {
        try {
            //try to get path from deploy folder
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
          } catch (IOException e) {
            e.printStackTrace();
          }
      
          RamseteCommand ramseteCommand = 
            new RamseteCommand(trajectory, 
            drivetrainSubsystem::getPose, new RamseteController(), 
            new SimpleMotorFeedforward(DriveConstants.kS, DriveConstants.kV, DriveConstants.kA),
            DriveConstants.DRIVE_KINEMATICS, drivetrainSubsystem::getWheelSpeeds, 
            new PIDController(DriveConstants.kD, DriveConstants.kI, 0),
            new PIDController(DriveConstants.kD, DriveConstants.kI, 0),
            drivetrainSubsystem::tankDriveVolts, drivetrainSubsystem);
      
          //stop robot after autonomous trajectory is followed
          return ramseteCommand.andThen(() -> drivetrainSubsystem.tankDriveVolts(0, 0));
    }
}
