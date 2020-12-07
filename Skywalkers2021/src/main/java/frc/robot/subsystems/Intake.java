/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers.DoubleSerializer;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  
  CANSparkMax intake;
  CANSparkMax motion_left;
  CANSparkMax motion_right;
  DoubleSolenoid intakesolenoid;

  boolean on;
  boolean up;
  boolean airflow;

  public Intake() {

    on = false;
    up = true;
    airflow = false;
    intake = new CANSparkMax(Constants.INTAKE_ID, MotorType.kBrushless);
    // v if we're using motors
    //motion_left = new CANSparkMax(Constants.INTAKE_MOTION_LEFT_ID, MotorType.kBrushless);
    //motion_right = new CANSparkMax(Constants.INTAKE_MOTION_RIGHT_ID, MotorType.kBrushless);
    //motion_left.restoreFactoryDefaults();
    //motion_right.restoreFactoryDefaults();
    //motion_right.setInverted(true);
    Compressor c = new Compressor(0);
    c.setClosedLoopControl(true);
    boolean enabled = c.enabled();
    boolean pressureSwitch = c.getPressureSwitchValue();
    double current = c.getCompressorCurrent();
    DoubleSolenoid intakesolenoid = new DoubleSolenoid(1, 2);
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void toggleIntake(double speed) {
    on = !on;
    if (on) {
      intake.set(speed);
    } else {
      intake.set(0);
    }
  }

  public void adjustIntake(XboxController controller, double speed) {
    intake.set(controller.getRawAxis(Constants.RIGHT_TRIGGER) * speed);
  }

  public void pneumaticarm(){
    airflow=!airflow;
    if (airflow){
      intakesolenoid.set(Value.kForward);
    } else{
      intakesolenoid.set(Value.kReverse);
    }
  }  
  public void stop(){
    intakesolenoid.set(Value.kOff);
  }
  

  public void toggleArm(double speed){
    if (up){
      // For a certain time period, degrees, or rotations, lower the arm until it reaches the horizontal position
      motion_right.set(-speed);
      motion_left.set(-speed);
    } else {
      // For a certain time period, degrees, or rotations, raise the arm until it reaches the vertical position
      motion_right.set(speed);
      motion_left.set(speed);
    }
    up=!up;
  }

  public void verify(){
    //Test out hardware
  }
}
