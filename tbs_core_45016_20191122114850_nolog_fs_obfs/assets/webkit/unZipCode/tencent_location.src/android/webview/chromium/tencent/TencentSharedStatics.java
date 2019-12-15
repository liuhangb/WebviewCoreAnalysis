package android.webview.chromium.tencent;

import android.app.ActivityManager;
import android.webview.chromium.SharedStatics;
import com.tencent.smtt.memory.MemoryChecker;

public class TencentSharedStatics
  extends SharedStatics
{
  public void freeMemoryForTests()
  {
    if (ActivityManager.isRunningInTestHarness()) {
      MemoryChecker.trim(80);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentSharedStatics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */