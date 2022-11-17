package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.BallHandlerSubsystem;

public class MoveArmCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;
    private final BallHandlerSubsystem ballHandlerSubsystem = new BallHandlerSubsystem();
    private final boolean direction;

    public MoveArmCommand(ArmSubsystem armSubsystem, boolean direction) {
        this.armSubsystem = armSubsystem;
        this.direction = direction;

        addRequirements(armSubsystem);
    } 

    @Override
    public void execute() {
        armSubsystem.moveArm(direction);
        if (direction) {
            new IntakeBallCommand(ballHandlerSubsystem).schedule();
        }
    }

    @Override
    public void end(boolean interrupted) {
        armSubsystem.setArmMotorSpeed(0);
    }

}
