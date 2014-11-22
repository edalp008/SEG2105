/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.20.2.4572 modeling language!*/


import java.util.*;

// line 1 "window.ump"
public class WindowOpener
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WindowOpener State Machines
  enum Window { Closed, Open, Opening, PartiallyOpen, ObstructedOpening, ObstructedClosing, RepeatedObstruction, Closing, FullOpening, FullClosing }
  private Window window;
  enum Motor { Stopped, OnForward, OnReverse }
  private Motor motor;
  enum StateMachine1 { topLevel }
  enum StateMachine1TopLevel { Null, thread1 }
  private StateMachine1 stateMachine1;
  private StateMachine1TopLevel stateMachine1TopLevel;

  //WindowOpener Do Activity Threads
  Thread doActivityWindowClosedThread = null;
  Thread doActivityWindowOpenThread = null;
  Thread doActivityWindowOpeningThread = null;
  Thread doActivityWindowPartiallyOpenThread = null;
  Thread doActivityWindowObstructedOpeningThread = null;
  Thread doActivityWindowObstructedClosingThread = null;
  Thread doActivityWindowRepeatedObstructionThread = null;
  Thread doActivityWindowClosingThread = null;
  Thread doActivityWindowFullOpeningThread = null;
  Thread doActivityWindowFullClosingThread = null;
  Thread doActivityStateMachine1TopLevelThread1Thread = null;

  //Helper Variables
  private TimedEventHandler timeoutRepeatedObstructionToPartiallyOpenHandler;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WindowOpener()
  {
    setWindow(Window.Closed);
    setMotor(Motor.Stopped);
    setStateMachine1TopLevel(StateMachine1TopLevel.Null);
    setStateMachine1(StateMachine1.topLevel);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getWindowFullName()
  {
    String answer = window.toString();
    return answer;
  }

  public String getMotorFullName()
  {
    String answer = motor.toString();
    return answer;
  }

  public String getStateMachine1FullName()
  {
    String answer = stateMachine1.toString();
    if (stateMachine1TopLevel != StateMachine1TopLevel.Null) { answer += "." + stateMachine1TopLevel.toString(); }
    return answer;
  }

  public Window getWindow()
  {
    return window;
  }

  public Motor getMotor()
  {
    return motor;
  }

  public StateMachine1 getStateMachine1()
  {
    return stateMachine1;
  }

  public StateMachine1TopLevel getStateMachine1TopLevel()
  {
    return stateMachine1TopLevel;
  }

  public boolean lightDown()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Closed:
        exitWindow();
        setWindow(Window.Opening);
        wasEventProcessed = true;
        break;
      case PartiallyOpen:
        exitWindow();
        setWindow(Window.Opening);
        wasEventProcessed = true;
        break;
      case Closing:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      case FullClosing:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean hardDown()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Closed:
        exitWindow();
        setWindow(Window.FullOpening);
        wasEventProcessed = true;
        break;
      case PartiallyOpen:
        exitWindow();
        setWindow(Window.FullOpening);
        wasEventProcessed = true;
        break;
      case Closing:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      case FullClosing:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean lightUp()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Open:
        exitWindow();
        setWindow(Window.Closing);
        wasEventProcessed = true;
        break;
      case Opening:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      case PartiallyOpen:
        exitWindow();
        setWindow(Window.Closing);
        wasEventProcessed = true;
        break;
      case FullOpening:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean hardUp()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Open:
        exitWindow();
        setWindow(Window.FullClosing);
        wasEventProcessed = true;
        break;
      case Opening:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      case PartiallyOpen:
        exitWindow();
        setWindow(Window.FullClosing);
        wasEventProcessed = true;
        break;
      case FullOpening:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean obstruction()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Opening:
        exitWindow();
        setWindow(Window.ObstructedOpening);
        wasEventProcessed = true;
        break;
      case Closing:
        exitWindow();
        setWindow(Window.ObstructedClosing);
        wasEventProcessed = true;
        break;
      case FullOpening:
        exitWindow();
        setWindow(Window.ObstructedOpening);
        wasEventProcessed = true;
        break;
      case FullClosing:
        exitWindow();
        setWindow(Window.ObstructedClosing);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean release()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Opening:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      case Closing:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean reachesBottom()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Opening:
        exitWindow();
        setWindow(Window.Open);
        wasEventProcessed = true;
        break;
      case FullOpening:
        exitWindow();
        setWindow(Window.Open);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition1__()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case ObstructedOpening:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition2__()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case ObstructedClosing:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean timeoutRepeatedObstructionToPartiallyOpen()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case RepeatedObstruction:
        exitWindow();
        setWindow(Window.PartiallyOpen);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean reachesTop()
  {
    boolean wasEventProcessed = false;
    
    Window aWindow = window;
    switch (aWindow)
    {
      case Closing:
        exitWindow();
        setWindow(Window.Closed);
        wasEventProcessed = true;
        break;
      case FullClosing:
        exitWindow();
        setWindow(Window.Closed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startForward()
  {
    boolean wasEventProcessed = false;
    
    Motor aMotor = motor;
    switch (aMotor)
    {
      case Stopped:
        setMotor(Motor.OnForward);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startReverse()
  {
    boolean wasEventProcessed = false;
    
    Motor aMotor = motor;
    switch (aMotor)
    {
      case Stopped:
        setMotor(Motor.OnReverse);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean stop()
  {
    boolean wasEventProcessed = false;
    
    Motor aMotor = motor;
    switch (aMotor)
    {
      case Stopped:
        setMotor(Motor.Stopped);
        wasEventProcessed = true;
        break;
      case OnForward:
        setMotor(Motor.Stopped);
        wasEventProcessed = true;
        break;
      case OnReverse:
        setMotor(Motor.Stopped);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean enterTopLevel()
  {
    boolean wasEventProcessed = false;
    
    StateMachine1TopLevel aStateMachine1TopLevel = stateMachine1TopLevel;
    switch (aStateMachine1TopLevel)
    {
      case Null:
        setStateMachine1TopLevel(StateMachine1TopLevel.thread1);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean exitTopLevel()
  {
    boolean wasEventProcessed = false;
    
    StateMachine1TopLevel aStateMachine1TopLevel = stateMachine1TopLevel;
    switch (aStateMachine1TopLevel)
    {
      case thread1:
        setStateMachine1TopLevel(StateMachine1TopLevel.Null);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitWindow()
  {
    switch(window)
    {
      case Closed:
        if (doActivityWindowClosedThread != null) { doActivityWindowClosedThread.interrupt(); }
        break;
      case Open:
        if (doActivityWindowOpenThread != null) { doActivityWindowOpenThread.interrupt(); }
        break;
      case Opening:
        if (doActivityWindowOpeningThread != null) { doActivityWindowOpeningThread.interrupt(); }
        break;
      case PartiallyOpen:
        if (doActivityWindowPartiallyOpenThread != null) { doActivityWindowPartiallyOpenThread.interrupt(); }
        break;
      case ObstructedOpening:
        if (doActivityWindowObstructedOpeningThread != null) { doActivityWindowObstructedOpeningThread.interrupt(); }
        break;
      case ObstructedClosing:
        if (doActivityWindowObstructedClosingThread != null) { doActivityWindowObstructedClosingThread.interrupt(); }
        break;
      case RepeatedObstruction:
        if (doActivityWindowRepeatedObstructionThread != null) { doActivityWindowRepeatedObstructionThread.interrupt(); }
        stopTimeoutRepeatedObstructionToPartiallyOpenHandler();
        break;
      case Closing:
        if (doActivityWindowClosingThread != null) { doActivityWindowClosingThread.interrupt(); }
        break;
      case FullOpening:
        if (doActivityWindowFullOpeningThread != null) { doActivityWindowFullOpeningThread.interrupt(); }
        break;
      case FullClosing:
        if (doActivityWindowFullClosingThread != null) { doActivityWindowFullClosingThread.interrupt(); }
        break;
    }
  }

  private void setWindow(Window aWindow)
  {
    window = aWindow;

    // entry actions and do activities
    switch(window)
    {
      case Closed:
        // line 4 "window.ump"
        System.out.println("Window closed.");
        doActivityWindowClosedThread = new DoActivityThread(this,"doActivityWindowClosed");
        break;
      case Open:
        // line 12 "window.ump"
        System.out.println("Window open.");
        doActivityWindowOpenThread = new DoActivityThread(this,"doActivityWindowOpen");
        break;
      case Opening:
        // line 20 "window.ump"
        System.out.println("Window opening.");
        doActivityWindowOpeningThread = new DoActivityThread(this,"doActivityWindowOpening");
        break;
      case PartiallyOpen:
        // line 31 "window.ump"
        System.out.println("Window partially open.");
        doActivityWindowPartiallyOpenThread = new DoActivityThread(this,"doActivityWindowPartiallyOpen");
        break;
      case ObstructedOpening:
        // line 41 "window.ump"
        System.out.println("Window obstructed.");
        doActivityWindowObstructedOpeningThread = new DoActivityThread(this,"doActivityWindowObstructedOpening");
        break;
      case ObstructedClosing:
        // line 49 "window.ump"
        System.out.println("Window obstructed.");
        doActivityWindowObstructedClosingThread = new DoActivityThread(this,"doActivityWindowObstructedClosing");
        break;
      case RepeatedObstruction:
        // line 57 "window.ump"
        System.out.println("Repeated obstruction. Window locked for 5s.");
        startTimeoutRepeatedObstructionToPartiallyOpenHandler();
        doActivityWindowRepeatedObstructionThread = new DoActivityThread(this,"doActivityWindowRepeatedObstruction");
        break;
      case Closing:
        // line 64 "window.ump"
        System.out.println("Window closing.");
        doActivityWindowClosingThread = new DoActivityThread(this,"doActivityWindowClosing");
        break;
      case FullOpening:
        // line 75 "window.ump"
        System.out.println("Window opening.");
        doActivityWindowFullOpeningThread = new DoActivityThread(this,"doActivityWindowFullOpening");
        break;
      case FullClosing:
        // line 85 "window.ump"
        System.out.println("Window closing.");
        doActivityWindowFullClosingThread = new DoActivityThread(this,"doActivityWindowFullClosing");
        break;
    }
  }

  private void setMotor(Motor aMotor)
  {
    motor = aMotor;

    // entry actions and do activities
    switch(motor)
    {
      case Stopped:
        // line 98 "window.ump"
        System.out.println("Motor stopped.");
        break;
      case OnForward:
        // line 104 "window.ump"
        System.out.println("Motor running forwards.");
        break;
      case OnReverse:
        // line 108 "window.ump"
        System.out.println("Motor running in reverse.");
        break;
    }
  }

  private void exitStateMachine1()
  {
    switch(stateMachine1)
    {
      case topLevel:
        exitTopLevel();
        break;
    }
  }

  private void setStateMachine1(StateMachine1 aStateMachine1)
  {
    stateMachine1 = aStateMachine1;

    // entry actions and do activities
    switch(stateMachine1)
    {
      case topLevel:
        if (stateMachine1TopLevel == StateMachine1TopLevel.Null) { setStateMachine1TopLevel(StateMachine1TopLevel.thread1); }
        break;
    }
  }

  private void exitStateMachine1TopLevel()
  {
    switch(stateMachine1TopLevel)
    {
      case thread1:
        if (doActivityStateMachine1TopLevelThread1Thread != null) { doActivityStateMachine1TopLevelThread1Thread.interrupt(); }
        break;
    }
  }

  private void setStateMachine1TopLevel(StateMachine1TopLevel aStateMachine1TopLevel)
  {
    stateMachine1TopLevel = aStateMachine1TopLevel;
    if (stateMachine1 != StateMachine1.topLevel && aStateMachine1TopLevel != StateMachine1TopLevel.Null) { setStateMachine1(StateMachine1.topLevel); }

    // entry actions and do activities
    switch(stateMachine1TopLevel)
    {
      case thread1:
        doActivityStateMachine1TopLevelThread1Thread = new DoActivityThread(this,"doActivityStateMachine1TopLevelThread1");
        break;
    }
  }

  private void doActivityWindowClosed()
  {
    try
    {
      stop();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowOpen()
  {
    try
    {
      stop();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowOpening()
  {
    try
    {
      startForward();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowPartiallyOpen()
  {
    try
    {
      stop();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowObstructedOpening()
  {
    try
    {
      stop();
        startReverse();
      Thread.sleep(1);
      __autotransition1__();
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowObstructedClosing()
  {
    try
    {
      stop();
        startForward();
      Thread.sleep(1);
      __autotransition2__();
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowRepeatedObstruction()
  {
    try
    {
      stop();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowClosing()
  {
    try
    {
      startReverse();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowFullOpening()
  {
    try
    {
      startForward();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityWindowFullClosing()
  {
    try
    {
      startReverse();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private void doActivityStateMachine1TopLevelThread1()
  {
    try
    {
      System.out.println("System started.");
    lightDown();
    reachesBottom();
    lightUp();
    lightDown();
    lightUp();
    release();
    hardDown();
    release();
    obstruction();
      Thread.sleep(1);
    }
    catch (InterruptedException e)
    {

    }
  }

  private static class DoActivityThread extends Thread
  {
    WindowOpener controller;
    String doActivityMethodName;
    
    public DoActivityThread(WindowOpener aController,String aDoActivityMethodName)
    {
      controller = aController;
      doActivityMethodName = aDoActivityMethodName;
      start();
    }
    
    public void run()
    {
      if ("doActivityWindowClosed".equals(doActivityMethodName))
      {
        controller.doActivityWindowClosed();
      }
        else if ("doActivityWindowOpen".equals(doActivityMethodName))
      {
        controller.doActivityWindowOpen();
      }
        else if ("doActivityWindowOpening".equals(doActivityMethodName))
      {
        controller.doActivityWindowOpening();
      }
        else if ("doActivityWindowPartiallyOpen".equals(doActivityMethodName))
      {
        controller.doActivityWindowPartiallyOpen();
      }
        else if ("doActivityWindowObstructedOpening".equals(doActivityMethodName))
      {
        controller.doActivityWindowObstructedOpening();
      }
        else if ("doActivityWindowObstructedClosing".equals(doActivityMethodName))
      {
        controller.doActivityWindowObstructedClosing();
      }
        else if ("doActivityWindowRepeatedObstruction".equals(doActivityMethodName))
      {
        controller.doActivityWindowRepeatedObstruction();
      }
        else if ("doActivityWindowClosing".equals(doActivityMethodName))
      {
        controller.doActivityWindowClosing();
      }
        else if ("doActivityWindowFullOpening".equals(doActivityMethodName))
      {
        controller.doActivityWindowFullOpening();
      }
        else if ("doActivityWindowFullClosing".equals(doActivityMethodName))
      {
        controller.doActivityWindowFullClosing();
      }
        else if ("doActivityStateMachine1TopLevelThread1".equals(doActivityMethodName))
      {
        controller.doActivityStateMachine1TopLevelThread1();
      }
    }
  }

  private void startTimeoutRepeatedObstructionToPartiallyOpenHandler()
  {
    timeoutRepeatedObstructionToPartiallyOpenHandler = new TimedEventHandler(this,"timeoutRepeatedObstructionToPartiallyOpen",5);
  }

  private void stopTimeoutRepeatedObstructionToPartiallyOpenHandler()
  {
    timeoutRepeatedObstructionToPartiallyOpenHandler.stop();
  }

  public static class TimedEventHandler extends TimerTask  
  {
    private WindowOpener controller;
    private String timeoutMethodName;
    private double howLongInSeconds;
    private Timer timer;
    
    public TimedEventHandler(WindowOpener aController, String aTimeoutMethodName, double aHowLongInSeconds)
    {
      controller = aController;
      timeoutMethodName = aTimeoutMethodName;
      howLongInSeconds = aHowLongInSeconds;
      timer = new Timer();
      timer.schedule(this, (long)howLongInSeconds*1000);
    }
    
    public void stop()
    {
      timer.cancel();
    }
    
    public void run ()
    {
      if ("timeoutRepeatedObstructionToPartiallyOpen".equals(timeoutMethodName))
      {
        boolean shouldRestart = !controller.timeoutRepeatedObstructionToPartiallyOpen();
        if (shouldRestart)
        {
          controller.startTimeoutRepeatedObstructionToPartiallyOpenHandler();
        }
        return;
      }
    }
  }

  public void delete()
  {}

  // line 127 "window.ump"
   public static  void main(String [] args){
    Thread.currentThread().setUncaughtExceptionHandler(new UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new UmpleExceptionHandler());
    new WindowOpener();
  }

  public static class UmpleExceptionHandler implements Thread.UncaughtExceptionHandler
  {
    public void uncaughtException(Thread t, Throwable e)
    {
      translate(e);
      if(e.getCause()!=null)
      {
        translate(e.getCause());
      }
      e.printStackTrace();
    }
    public void translate(Throwable e)
    {
      java.util.List<StackTraceElement> result = new java.util.ArrayList<StackTraceElement>();
      StackTraceElement[] elements = e.getStackTrace();
      try
      {
        for(StackTraceElement element:elements)
        {
          String className = element.getClassName();
          String methodName = element.getMethodName();
          boolean methodFound = false;
          int index = className.lastIndexOf('.')+1;
          try {
            java.lang.reflect.Method query = this.getClass().getMethod(className.substring(index)+"_"+methodName,new Class[]{});
            UmpleSourceData sourceInformation = (UmpleSourceData)query.invoke(this,new Object[]{});
            for(int i=0;i<sourceInformation.size();++i)
            {
              int distanceFromStart = element.getLineNumber()-sourceInformation.getJavaLine(i)-(("main".equals(methodName))?2:0);
              if(distanceFromStart>=0&&distanceFromStart<=sourceInformation.getLength(i))
              {
                result.add(new StackTraceElement(element.getClassName(),element.getMethodName(),sourceInformation.getFileName(i),sourceInformation.getUmpleLine(i)+distanceFromStart));
                methodFound = true;
                break;
              }
            }
          }
          catch (Exception e2){}
          if(!methodFound)
          {
            result.add(element);
          }
        }
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
      e.setStackTrace(result.toArray(new StackTraceElement[0]));
    }
  //The following methods Map Java lines back to their original Umple file / line    
    public UmpleSourceData WindowOpener_doActivityWindowObstructedClosing(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(49).setJavaLines(734).setLengths(2);}
    public UmpleSourceData WindowOpener_doActivityWindowPartiallyOpen(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(31).setJavaLines(706).setLengths(1);}
    public UmpleSourceData WindowOpener_doActivityWindowObstructedOpening(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(41).setJavaLines(719).setLengths(2);}
    public UmpleSourceData WindowOpener_doActivityWindowOpen(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(12).setJavaLines(680).setLengths(1);}
    public UmpleSourceData WindowOpener_doActivityWindowRepeatedObstruction(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(57).setJavaLines(749).setLengths(1);}
    public UmpleSourceData WindowOpener_main(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(126).setJavaLines(926).setLengths(3);}
    public UmpleSourceData WindowOpener_doActivityWindowOpening(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(20).setJavaLines(693).setLengths(1);}
    public UmpleSourceData WindowOpener_setWindow(){ return new UmpleSourceData().setFileNames("window.ump"," window.ump"," window.ump"," window.ump"," window.ump"," window.ump"," window.ump"," window.ump"," window.ump"," window.ump").setUmpleLines(3, 11, 19, 30, 40, 48, 56, 63, 74, 84).setJavaLines(540, 545, 550, 555, 560, 565, 570, 576, 581, 586).setLengths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);}
    public UmpleSourceData WindowOpener_doActivityWindowClosing(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(64).setJavaLines(762).setLengths(1);}
    public UmpleSourceData WindowOpener_doActivityWindowClosed(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(4).setJavaLines(667).setLengths(1);}
    public UmpleSourceData WindowOpener_doActivityWindowFullOpening(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(75).setJavaLines(775).setLengths(1);}
    public UmpleSourceData WindowOpener_setMotor(){ return new UmpleSourceData().setFileNames("window.ump"," window.ump"," window.ump").setUmpleLines(97, 103, 107).setJavaLines(601, 605, 609).setLengths(1, 1, 1);}
    public UmpleSourceData WindowOpener_doActivityWindowFullClosing(){ return new UmpleSourceData().setFileNames("window.ump").setUmpleLines(85).setJavaLines(788).setLengths(1);}

  }
  public static class UmpleSourceData
  {
    String[] umpleFileNames;
    Integer[] umpleLines;
    Integer[] umpleJavaLines;
    Integer[] umpleLengths;
    
    public UmpleSourceData(){
    }
    public String getFileName(int i){
      return umpleFileNames[i];
    }
    public Integer getUmpleLine(int i){
      return umpleLines[i];
    }
    public Integer getJavaLine(int i){
      return umpleJavaLines[i];
    }
    public Integer getLength(int i){
      return umpleLengths[i];
    }
    public UmpleSourceData setFileNames(String... filenames){
      umpleFileNames = filenames;
      return this;
    }
    public UmpleSourceData setUmpleLines(Integer... umplelines){
      umpleLines = umplelines;
      return this;
    }
    public UmpleSourceData setJavaLines(Integer... javalines){
      umpleJavaLines = javalines;
      return this;
    }
    public UmpleSourceData setLengths(Integer... lengths){
      umpleLengths = lengths;
      return this;
    }
    public int size(){
      return umpleFileNames.length;
    }
  } 
}