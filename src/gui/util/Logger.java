package gui.util;

public class Logger {

  private static Logger instance = null;

  private Logger() {
    
  }

  public static Logger getInstance() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }
  
  public void log(String txt) {
    System.out.print(txt);
  }
}
