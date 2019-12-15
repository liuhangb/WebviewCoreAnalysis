package org.chromium.components.crash.browser;

import java.io.File;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;

public class CrashDumpManager
{
  private static UploadMinidumpCallback jdField_a_of_type_OrgChromiumComponentsCrashBrowserCrashDumpManager$UploadMinidumpCallback;
  
  @CalledByNative
  public static void tryToUploadMinidump(String paramString)
  {
    
    if (paramString == null)
    {
      Log.e("CrashDumpManager", "Minidump path should be non-null! Bailing...", new Object[0]);
      return;
    }
    Object localObject = new File(paramString);
    if ((((File)localObject).exists()) && (((File)localObject).isFile()))
    {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          if (CrashDumpManager.a() == null) {
            return;
          }
          CrashDumpManager.a().tryToUploadMinidump(this.a);
        }
      });
      return;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Minidump path '");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("' should describe a file that exists! Bailing...");
    Log.e("CrashDumpManager", ((StringBuilder)localObject).toString(), new Object[0]);
  }
  
  public static abstract interface UploadMinidumpCallback
  {
    public abstract void tryToUploadMinidump(File paramFile);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\crash\browser\CrashDumpManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */