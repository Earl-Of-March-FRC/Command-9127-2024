// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.Autos.ScoreInAmpTimed;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Autos.AutoLog;
import frc.robot.Commands.Drivetrain.Autos.Timed.TurnToAmpTimed;
import frc.robot.Commands.Drivetrain.Autos.Timed.MoveOutOfZoneTimed.MoveOutOfZoneTimed3;
import frc.robot.Commands.Drivetrain.Autos.Timed.MoveToAmpTimed.MoveToAmpTimed3;
import frc.robot.Commands.IntakeShooter.Autos.DownShootAmpTimed;
//import frc.robot.Subsystems.ArmSubsystem;
import frc.robot.Subsystems.DrivetrainSubsystem;
import frc.robot.Subsystems.IntakeShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreInAmpTimed3 extends SequentialCommandGroup {
  /** Creates a new AutonomousBackup. */
  public ScoreInAmpTimed3(
    DrivetrainSubsystem driveSub,
    IntakeShooterSubsystem intakeShooterSub
    //ArmSubsystem armSub
  ) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new MoveOutOfZoneTimed3(driveSub),
      new TurnToAmpTimed(driveSub),
      new MoveToAmpTimed3(driveSub),
      new DownShootAmpTimed(intakeShooterSub),
      new AutoLog("Done, pos.3")
    );
  }
}
