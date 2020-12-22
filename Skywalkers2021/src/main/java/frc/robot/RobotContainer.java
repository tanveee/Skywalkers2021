/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutonomousOne;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.commands.ForwardTelescopingArm;
import frc.robot.commands.ReverseTelescopingArm;
import frc.robot.commands.StopPneumatics;
import frc.robot.commands.ToggleIntake;
import frc.robot.commands.TogglePneumatics;
import frc.robot.commands.ToggleQuickTurn;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.Transfer;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...


  public static XboxController driverJoystick;

  private final Drivetrain drivetrain;
  private final DriveWithJoysticks driveWithJoysticks;
  private final ToggleQuickTurn toggleQuickTurn;


  private final Intake intake;
  private final TogglePneumatics togglePneumatics;
  private final StopPneumatics stopPneumatics;
  private final ToggleIntake toggleIntake;

  private final Shooter shooter;
  
  private final Climber climber;
  private final ForwardTelescopingArm forwardTelescopingArm;
  private final ReverseTelescopingArm reverseTelescopingArm;

  private final Spinner spinner;

  private final Transfer transfer;

  private final AutonomousOne autonomousOne;



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // subsystems
    climber = new Climber();
    drivetrain = new Drivetrain();
    intake = new Intake();
    shooter = new Shooter();
    spinner = new Spinner();
    transfer = new Transfer();

    // drivetrain commands
    driveWithJoysticks = new DriveWithJoysticks(drivetrain);
    driveWithJoysticks.addRequirements(drivetrain);
    drivetrain.setDefaultCommand(driveWithJoysticks);
    toggleQuickTurn = new ToggleQuickTurn(drivetrain);
    toggleQuickTurn.addRequirements(drivetrain);

    // climber commands
    forwardTelescopingArm = new ForwardTelescopingArm(climber);
    forwardTelescopingArm.addRequirements(climber);
    reverseTelescopingArm = new ReverseTelescopingArm(climber);
    reverseTelescopingArm.addRequirements(climber);

    // intake commands
    togglePneumatics = new TogglePneumatics(intake);
    togglePneumatics.addRequirements(intake);
    stopPneumatics = new StopPneumatics(intake);
    stopPneumatics.addRequirements(intake);
    toggleIntake = new ToggleIntake(intake);
    toggleIntake.addRequirements(intake);

    
    // autonomous
    autonomousOne = new AutonomousOne();

    driverJoystick = new XboxController(Constants.JOYSTICK_NUMBER);



    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Toggle Quick Turn with A
    JoystickButton aButton = new JoystickButton(driverJoystick, XboxController.Button.kA.value);
    aButton.whenPressed(new ToggleQuickTurn(drivetrain));
    
    // Turn Climber Motor forward direction when X is pressed
    JoystickButton xButton = new JoystickButton(driverJoystick, XboxController.Button.kX.value);
    xButton.whenPressed(new ForwardTelescopingArm(climber));

    // Turn Climber Motor reverse direction when Right Bumper is pressed
    JoystickButton rightBumper = new JoystickButton(driverJoystick, XboxController.Button.kBumperRight.value);
    rightBumper.whenPressed(new ReverseTelescopingArm(climber));

    // Toggle Intake Pneumatics with B
    JoystickButton bButton = new JoystickButton(driverJoystick, XboxController.Button.kB.value);
    bButton.whenPressed(new TogglePneumatics(intake));

    // Toggle Intake Roller with Left Bumper
    JoystickButton leftBumper = new JoystickButton(driverJoystick, XboxController.Button.kBumperLeft.value);
    leftBumper.whenPressed(new ToggleIntake(intake));

    // Stop Pneumatics with Y
    JoystickButton yButton = new JoystickButton(driverJoystick, XboxController.Button.kY.value);
    yButton.whenPressed(new StopPneumatics(intake));


    
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autonomousOne;

  }
}
