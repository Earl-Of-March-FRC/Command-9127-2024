// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.Routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Drivetrain.Routines.MoveOutOfZoneTimed;
import frc.robot.Subsystems.DrivetrainSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ExitZoneTimed extends SequentialCommandGroup {
  /** Creates a new ExitZoneTimed. */
  public ExitZoneTimed(
    DrivetrainSubsystem driveSub
  ) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new MoveOutOfZoneTimed(driveSub),
      new RoutineLog("Done")
    );
  }
}