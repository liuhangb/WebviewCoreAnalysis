package org.chromium.content.app;

import android.os.Process;
import org.chromium.base.BuildInfo;
import org.chromium.base.annotations.MainDex;

@MainDex
class KillChildUncaughtExceptionHandler
  implements Thread.UncaughtExceptionHandler
{
  private boolean a;
  
  static void a()
  {
    if (BuildInfo.isDebugAndroid()) {
      return;
    }
    Thread.setDefaultUncaughtExceptionHandler(new KillChildUncaughtExceptionHandler());
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    if (this.a) {
      return;
    }
    this.a = true;
    Process.killProcess(Process.myPid());
    System.exit(10);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\app\KillChildUncaughtExceptionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */