// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;




/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private DifferentialDrive drive;
  // private MotorControllerGroup left;
  // private MotorControllerGroup right;
  private TalonSRX m_right;
  private TalonSRX m_left;
  private WPI_VictorSPX rightBack;
  private WPI_VictorSPX leftBack;
  // private WPI_VictorSPX intake1;
  // private WPI_VictorSPX intake2;
  private CANSparkMax thingy;
  private Joystick right;
  private Joystick left;
  private Solenoid sol;
  private boolean solState;
  private boolean motorState;
//rightmaster + swing motor
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    // rightFront = new WPI_VictorSPX(6);
    m_left = new TalonSRX(9);//
    m_right = new TalonSRX(2);//1
    // leftBack = new WPI_VictorSPX(9);
    // intake1 = new WPI_VictorSPX(6);
    // intake2 = new WPI_VictorSPX(3);//opp + top
    thingy = new CANSparkMax(5, MotorType.kBrushless);
    sol = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    solState = false;
    motorState = false;

    // MotorControllerGroup m_right = new MotorControllerGroup(rightFront, rightBack);
    // MotorControllerGroup m_left = new MotorControllerGroup(leftFront, leftBack);


    // drive = new DifferentialDrive(m_left, m_right);
    // m_left.setInverted(true);
    m_right.setInverted(true);
    right = new Joystick(0);
    left = new Joystick(1);
  }


  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}


  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }


  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }


  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
     
  }


  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //  drive.arcadeDrive(-1*right.getX(),-1*left.getY());//left.getY(), -1*right.getX()
    m_left.set(ControlMode.PercentOutput, -left.getY());
    m_right.set(ControlMode.PercentOutput, -right.getY());
    if (left.getRawButtonPressed(1)) {
      solState = !solState;
    }
    sol.set(solState);
    if (right.getRawButtonPressed(1)) {
      motorState = !motorState;
    }
    if (motorState) {
      thingy.set(0.3);
    } else {
      thingy.set(0);
    }
    System.out.println("sol: " + solState + ", motor: " + motorState);
  }


  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    thingy.set(0);
    m_left.set(ControlMode.PercentOutput, 0);
    m_right.set(ControlMode.PercentOutput, 0);
  }


  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
      thingy.set(0);
      m_left.set(ControlMode.PercentOutput, 0);
      m_right.set(ControlMode.PercentOutput, 0);
  }


  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}


  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}


  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}


  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}


