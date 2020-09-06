/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */

  CANSparkMax rightMaster;
  CANSparkMax rightSlave;
  CANSparkMax leftMaster;
  CANSparkMax leftSlave;
  

  public Drivetrain() {

    rightMaster = new CANSparkMax(Constants.RIGHT_MASTER_ID, MotorType.kBrushless);
    rightSlave = new CANSparkMax(Constants.RIGHT_SLAVE_ID, MotorType.kBrushless);

    rightMaster.restoreFactoryDefaults();
    rightSlave.restoreFactoryDefaults();

    rightSlave.follow(rightMaster);


    leftMaster = new CANSparkMax(Constants.LEFT_MASTER_ID, MotorType.kBrushless);
    leftSlave = new CANSparkMax(Constants.LEFT_SLAVE_ID, MotorType.kBrushless);

    leftMaster.restoreFactoryDefaults();
    leftSlave.restoreFactoryDefaults();

    leftSlave.follow(leftMaster);


    rightMaster.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveWithJoysticks(XboxController controller, double speed) {
    // Figure out which drive method:
    // - Differential Drive
    // - Tank drive
    // - Curvature drive
  }
}
