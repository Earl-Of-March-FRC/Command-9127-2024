// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands.Arm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ArmSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ArmPIDCmd extends Command {
  private ArmSubsystem armSub;
  private DoubleSupplier raiseP, raiseI, raiseD, dropP, dropI, dropD, setpoint, tolerance, clamp;
  private PIDController controller;

  /** Creates a new ArmRaise. */
  public ArmPIDCmd(
    ArmSubsystem armSub,
    DoubleSupplier raiseP,
    DoubleSupplier raiseI,
    DoubleSupplier raiseD,
    DoubleSupplier dropP,
    DoubleSupplier dropI,
    DoubleSupplier dropD,
    DoubleSupplier setpoint,
    DoubleSupplier tolerance,
    DoubleSupplier clamp
  ) {
    // System.out.println(armSub.getAngle());
    this.armSub = armSub;
    this.raiseP = raiseP;
    this.raiseI = raiseI;
    this.raiseD = raiseD;
    this.dropP = dropP;
    this.dropI = dropI;
    this.dropD = dropD;
    this.setpoint = setpoint;
    this.tolerance = tolerance;
    this.clamp = clamp;

    controller = armSub.getController();
    addRequirements(armSub);
  }


  @Override
  public void initialize() {
    controller.setTolerance(tolerance.getAsDouble());
    controller.setSetpoint(setpoint.getAsDouble());
  }

  @Override
  public void execute() {
    double speed = -MathUtil.clamp(controller.calculate(armSub.getAngle() % 360), -clamp.getAsDouble(), clamp.getAsDouble());
    /*If arm is raising, and raise limit switch isn't switched.
     * or
     * If arm is dropping, and drop limit switch isn't switched.
    */
    System.out.println("Arm velocity recieved (experimenting, currently does nothing):" + armSub.getSensorVelocity());
    if ((speed < 0)) { // Raising
      if (!armSub.raiseLimitSwitch()) {
        armSub.setMotor(speed);
      } else {
        armSub.setMotor(0);
      }
      controller.setP(raiseP.getAsDouble());
      controller.setI(raiseI.getAsDouble());
      controller.setD(raiseD.getAsDouble());
      SmartDashboard.putNumber("Arm P", raiseP.getAsDouble());
      SmartDashboard.putNumber("Arm I", raiseI.getAsDouble());
      SmartDashboard.putNumber("Arm D", raiseD.getAsDouble());
    } else if (speed > 0) { // Dropping
      if (!armSub.dropLimitSwitch()) {
        armSub.setMotor(speed);
      } else {
        armSub.setMotor(0);
      }
      controller.setP(dropP.getAsDouble());
      controller.setI(dropI.getAsDouble());
      controller.setD(dropD.getAsDouble());
      SmartDashboard.putNumber("Arm P", dropP.getAsDouble());
      SmartDashboard.putNumber("Arm I", dropI.getAsDouble());
      SmartDashboard.putNumber("Arm D", dropD.getAsDouble());
    }

    controller.setSetpoint(setpoint.getAsDouble() + SmartDashboard.getNumber("Arm Setpoint Offset", 0));
    controller.setTolerance(tolerance.getAsDouble());

    // Pushing number to SmartDashboard
    SmartDashboard.putNumber("Arm PID Output", controller.calculate(armSub.getAngle() % 360));
    SmartDashboard.putNumber("Arm PID Setpoint", setpoint.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {
    armSub.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    /* This should probably not be set to controller.atSetpoint() or limit.getAsBoolean()
    Because, it would be nice if the arm would be able to go back to the setpoint on its own if something (like an collision) moved the arm a little.
    The motor speed is automatically set to 0 when the limit returns true, and the motors shouldn't be able to move when settled at the setpoint */
    return false;
    // return (controller.atSetpoint() || limit.getAsBoolean());
  }
}