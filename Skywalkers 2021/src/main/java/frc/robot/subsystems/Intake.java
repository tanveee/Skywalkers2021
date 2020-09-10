/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  
  CANSparkMax intake;

  boolean on;

  public Intake() {

    on = false;
    intake = new CANSparkMax(Constants.INTAKE_ID, MotorType.kBrushless);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void toggleIntake(double speed) {
    on = !on;
    if (on) {
      stop();
    } else {
      intake.set(speed);
    }
  }

  public void intake(double speed) {
    intake.set(speed);
  }

  public void adjustIntake(XboxController controller, double speed) {
    intake.set(controller.getRawAxis(Constants.RIGHT_TRIGGER) * speed);
  }

  public void stop() {
    intake.set(0);
  }
}
