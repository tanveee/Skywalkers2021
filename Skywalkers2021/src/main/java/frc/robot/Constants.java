/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	//CAN Drivetrain IDs
	public static final int RIGHT_MASTER_ID = 2;
	public static final int RIGHT_SLAVE_ID = 3;
	public static final int LEFT_MASTER_ID = 1;
	public static final int LEFT_SLAVE_ID = 4;

	//Intake IDs
	public static final int INTAKE_ID = 0;

	//Climber IDs
	public static final int CLIMBER_ID = 5;
	
	//XBOX Constants
	public static final int JOYSTICK_NUMBER = 1;
	public static final int XBOX_LEFT_Y_AXIS = 1;
	public static final int XBOX_RIGHT_X_AXIS = 4;
	public static final int XBOX_RIGHT_Y_AXIS = 5;
	public static final int RIGHT_TRIGGER = 3;
	public static final int UP_BUTTON = 0;
	public static final int DOWN_BUTTON = 0;

	//SPEEDS
	public static final double INTAKE_SPEED = 0;
	public static final double DRIVETRAIN_SPEED = 0.1;
	public static final double TELESCOPING_ARM_SPEED = 0.5;
}
