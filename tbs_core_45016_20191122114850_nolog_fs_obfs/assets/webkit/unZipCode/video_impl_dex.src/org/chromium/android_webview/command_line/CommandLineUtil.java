package org.chromium.android_webview.command_line;

import android.os.Build;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import org.chromium.base.CommandLine;

public class CommandLineUtil
{
  public static final String CRASH_UPLOADS_ENABLED_FOR_TESTING_SWITCH = "enable-crash-reporter-for-testing";
  public static final String WEBVIEW_COMMAND_LINE_FILE = "/data/local/tmp/webview-command-line";
  
  public static void initCommandLine()
  {
    if (isBuildDebuggable())
    {
      StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskReads();
      CommandLine.initFromFile("/data/local/tmp/webview-command-line");
      StrictMode.setThreadPolicy(localThreadPolicy);
      return;
    }
    CommandLine.init(null);
  }
  
  public static boolean isBuildDebuggable()
  {
    return Build.TYPE.equals("user") ^ true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\command_line\CommandLineUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */