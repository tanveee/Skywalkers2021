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

  public void driveWithJoysticks(XboxController controller, double speed, double rotation, boolean isQuickTurn) {
    // Figure out which drive method:
    // - ArcadeDrive
    // arcadeDrive(controller, speed);
    // - Tank drive
    // tankDrive(controller, speed);
    // - Curvature drive (aka Cheesy Drive)
    // curvatureDrive(speed, rotation, isQuickTurn);

  }

  public void arcadeDrive(XboxController controller, double speed) {
    double yValue = controller.getRawAxis(Constants.XBOX_LEFT_Y_AXIS) * speed;
    double xValue = controller.getRawAxis(Constants.XBOX_RIGHT_X_AXIS) * speed;
    double leftPower = yValue - xValue;
    double rightPower = yValue + xValue;
    this.rightMaster.set(rightPower);
    this.leftMaster.set(leftPower);
  }

  public void tankDrive(XboxController controller, double speed) {
    double leftPower = controller.getRawAxis(Constants.XBOX_LEFT_Y_AXIS) * speed;
    double rightPower = controller.getRawAxis(Constants.XBOX_RIGHT_Y_AXIS) * speed;
    this.rightMaster.set(rightPower);
    this.leftMaster.set(leftPower);
  }


  public void curvatureDrive(double speed, double rotation, boolean isQuickTurn){

    // Clamp inputs
    speed = Math.max(-1., Math.min(speed, 1.));
    rotation = Math.max(-1., Math.min(rotation, 1.));
    
    // Calculate constant-curvature vs rate-of-change based on QuickTurn
    double leftSpeed;
    double rightSpeed;

    if (isQuickTurn){

        // Rate-of-change calculation
        leftSpeed = speed + rotation;
        rightSpeed = speed - rotation;
    } else {

        // Constant-curvature calculation
        leftSpeed = speed + Math.abs(speed) * rotation;
        rightSpeed = speed - Math.abs(speed) * rotation;
    }

    // Normalize wheel speeds
    double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
    if (maxMagnitude > 1.0) {
        leftSpeed /= maxMagnitude;
        rightSpeed /= maxMagnitude;
    }

    this.rightMaster.set(rightSpeed);
    this.leftMaster.set(leftSpeed);
  }


  public void stop() {
    rightMaster.set(0);
    leftMaster.set(0);
    
  }
}
