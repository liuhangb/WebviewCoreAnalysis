package com.tencent.tbs.tbsshell;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Build.VERSION;
import com.tencent.tbs.tbsshell.common.TbsLog;
import java.io.File;

public class WebHook
{
  private static final String lib_name = "libwebhook.so";
  private static boolean soInitedSuccess = false;
  
  public WebHook(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("#");
    localStringBuilder.append(Thread.currentThread().getStackTrace()[2].getMethodName());
    localStringBuilder.append("...");
    TbsLog.d("WebHook", localStringBuilder.toString());
    try
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(File.separator);
      localStringBuilder.append("libwebhook.so");
      System.load(localStringBuilder.toString());
      soInitedSuccess = true;
      return;
    }
    catch (Throwable paramString)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("libwebhook.so load error:");
      localStringBuilder.append(paramString.getLocalizedMessage());
      TbsLog.e("WebHook", localStringBuilder.toString());
      soInitedSuccess = false;
    }
  }
  
  @SuppressLint({"DefaultLocale"})
  public static boolean shouldDoHook()
  {
    int i;
    if (Build.VERSION.SDK_INT < 21) {
      i = 1;
    } else {
      i = 0;
    }
    return (i != 0) && (Build.MANUFACTURER != null) && (Build.MANUFACTURER.contains("Xiaomi"));
  }
  
  public void hookDvmDlopen()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("#");
    localStringBuilder.append(Thread.currentThread().getStackTrace()[2].getMethodName());
    localStringBuilder.append("...");
    TbsLog.d("WebHook", localStringBuilder.toString());
    if (soInitedSuccess)
    {
      nativeHookSoEx("libdvm.so", "dlopen");
      return;
    }
    TbsLog.e("WebHook", "so not found. No need to do hook!");
  }
  
  public native int nativeHookSoEx(String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\WebHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */