package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallHandlerSubsystem;

public class IntakeBallCommand extends CommandBase {
    private final BallHandlerSubsystem ballHandlerSubsystem;
    private final double speed;
    private final boolean intake;
    private final boolean shoot;
    private final boolean stop;

    
    public IntakeBallCommand(BallHandlerSubsystem ballHandlerSubsystem, double speed, boolean intake, boolean shoot, boolean stop) {
        this.ballHandlerSubsystem = ballHandlerSubsystem;
        this.speed = speed;
        this.intake = intake;
        this.shoot = shoot;
        this.stop = stop;

        addRequirements(ballHandlerSubsystem);
    }

    public IntakeBallCommand(BallHandlerSubsystem ballHandlerSubsystem) {
        this(ballHandlerSubsystem, 0, true, false, false);
    }


    @Override
    public void execute() {
        ballHandlerSubsystem.runBallHandler(speed, intake, shoot, stop);
    }

    @Override
    public void end(boolean interrupted) {
        ballHandlerSubsystem.stopBallHandler();
    }
}
