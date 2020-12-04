/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANEncoder;
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

  CANEncoder rmEncoder;
  CANEncoder rsEncoder;
  CANEncoder lmEncoder;
  CANEncoder lsEncoder;

  int cpr;

  boolean isQuickTurn = true;
  
  

  public Drivetrain() {
    
    // Right Motors Init
    rightMaster = new CANSparkMax(Constants.RIGHT_MASTER_ID, MotorType.kBrushless);
    rightSlave = new CANSparkMax(Constants.RIGHT_SLAVE_ID, MotorType.kBrushless);
    rightMaster.restoreFactoryDefaults();
    rightSlave.restoreFactoryDefaults();
    rightSlave.follow(rightMaster);

    // Left Motor Init
    leftMaster = new CANSparkMax(Constants.LEFT_MASTER_ID, MotorType.kBrushless);
    leftSlave = new CANSparkMax(Constants.LEFT_SLAVE_ID, MotorType.kBrushless);
    leftMaster.restoreFactoryDefaults();
    leftSlave.restoreFactoryDefaults();
    leftSlave.follow(leftMaster);

    leftMaster.setInverted(true);
    rightMaster.setInverted(false);

    // Encoder Init
    rmEncoder = rightMaster.getEncoder();
    rsEncoder = rightSlave.getEncoder();
    lmEncoder = leftMaster.getEncoder();
    lsEncoder = leftSlave.getEncoder();

    cpr = rmEncoder.getCountsPerRevolution();


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveWithJoysticks(XboxController controller, double speed) {
    // Figure out which drive method:
    // - ArcadeDrive
    //arcadeDrive(controller, speed);
    // - Tank drive
    //tankDrive(controller, speed);
    // - Curvature drive (aka Cheesy Drive)
    curvatureDrive(controller, speed);

  }

  //Drive Methods

  public void arcadeDrive(XboxController controller, double speed) {
    double yValue = controller.getRawAxis(Constants.XBOX_LEFT_Y_AXIS) * speed;
    double xValue = controller.getRawAxis(Constants.XBOX_RIGHT_X_AXIS) * speed;
    double leftPower = yValue - xValue;
    double rightPower = yValue + xValue;
    this.rightMaster.set(rightPower);
    this.leftMaster.set(leftPower);
  }

  public void arcadeDrive(double useOutput, double fwd, double rot) {
    double lefPower = fwd - rot;
    double rightPower = fwd + rot;
    this.rightMaster.set(rightPower);
    this.leftMaster.set(lefPower);
  }

  public void tankDrive(XboxController controller, double speed) {
    double leftPower = controller.getRawAxis(Constants.XBOX_LEFT_Y_AXIS) * speed;
    double rightPower = controller.getRawAxis(Constants.XBOX_RIGHT_Y_AXIS) * speed;
    this.rightMaster.set(rightPower);
    this.leftMaster.set(leftPower);
  }


  public void curvatureDrive(XboxController controller, double speed){

    // Clamp inputs
    double forwardVelocity = controller.getRawAxis(Constants.XBOX_LEFT_Y_AXIS) * speed;
    double rotation = controller.getRawAxis(Constants.XBOX_RIGHT_X_AXIS) * speed;
    forwardVelocity = Math.max(-1., Math.min(forwardVelocity, 1.));
    rotation = Math.max(-1., Math.min(rotation, 1.));
    
    // Calculate constant-curvature vs rate-of-change based on QuickTurn
    double leftPower;
    double rightPower;

    if (this.isQuickTurn){


        // Rate-of-change calculation
        leftPower = forwardVelocity + rotation;
        rightPower = forwardVelocity - rotation;
    } else {


        // Constant-curvature calculation
        leftPower = forwardVelocity + Math.abs(forwardVelocity) * rotation;
        System.out.println("left power: " + leftPower);
        rightPower = forwardVelocity - Math.abs(forwardVelocity) * rotation;
        System.out.println("right power: " + rightPower);
    }

    // Normalize wheel speeds
    double maxMagnitude = Math.max(Math.abs(leftPower), Math.abs(rightPower));
    if (maxMagnitude > 1.0) {
        leftPower /= maxMagnitude;
        rightPower /= maxMagnitude;
    }

    this.rightMaster.set(rightPower);
    this.leftMaster.set(leftPower);
  }

  public void quickTurn(){
    this.isQuickTurn = !this.isQuickTurn;
  }

  public void verify(){
    //Test out hardware
  }

  public void stop() {
    rightMaster.set(0);
    leftMaster.set(0);
  }



  public double getRightPos() { return (rmEncoder.getPosition() + rsEncoder.getPosition()) / 2; }

  public double getLeftPos() { return (lmEncoder.getPosition() + lsEncoder.getPosition()) / 2; }

  public double getRightVel() { return (rmEncoder.getVelocity() + rsEncoder.getVelocity()) / 2; }

  public double getLeftVel() {  return (lmEncoder.getVelocity() + lsEncoder.getVelocity()) / 2; }

  public double getRightDistance() {
    return getRightPos() / cpr / Constants.DRIVE_GEAR_RATIO * 2 * Math.PI * Units.inchesToMeters(Constants.DRIVE_WHEEL_RADIUS);
  }

  public double getLeftDistance() {
    return getLeftPos() / cpr / Constants.DRIVE_GEAR_RATIO * 2 * Math.PI * Units.inchesToMeters(Constants.DRIVE_WHEEL_RADIUS);
  }

  public double getRobotDistance() { return (getLeftDistance() + getRightDistance())/ 2; }

  public DifferentialDriveWheelSpeeds getSpeeds() {
    return new DifferentialDriveWheelSpeeds(
      getLeftVel() / Constants.DRIVE_GEAR_RATIO * 2 * Math.PI * Units.inchesToMeters(Constants.DRIVE_WHEEL_RADIUS) / 60,
      getRightVel() / Constants.DRIVE_GEAR_RATIO * 2 * Math.PI * Units.inchesToMeters(Constants.DRIVE_WHEEL_RADIUS) / 60
    );
  }

  public double getRightPower() { return this.rightMaster.getAppliedOutput(); }

  public double getLeftPower() { return this.leftMaster.getAppliedOutput(); }



}
