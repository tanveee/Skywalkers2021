/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  /**
   * Creates a new Climber.
   */

  CANSparkMax telescopingArm;
  CANSparkMax climber; 

  public Climber() {

    telescopingArm = new CANSparkMax(Constants.TELESCOPING_ARM_ID, MotorType.kBrushless);
    climber = new CANSparkMax(Constants.CLIMBER_ID, MotorType.kBrushless);

    telescopingArm.restoreFactoryDefaults();
    climber.restoreFactoryDefaults();

    telescopingArm.setInverted(true);
    climber.setInverted(false);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void liftArm(double speed) {
    telescopingArm.set(speed);

  }

  public void climb(double speed) {
    climber.set(speed);
  }

  public void verify(){
    //Test out hardware
  }
}
