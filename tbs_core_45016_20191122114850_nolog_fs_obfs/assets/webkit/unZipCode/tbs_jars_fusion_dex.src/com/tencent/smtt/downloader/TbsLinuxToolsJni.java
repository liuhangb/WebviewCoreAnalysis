package com.tencent.smtt.downloader;

import android.content.Context;
import com.tencent.smtt.downloader.utils.h;
import java.io.File;

class TbsLinuxToolsJni
{
  private static boolean a = false;
  private static boolean b = false;
  
  public TbsLinuxToolsJni(Context paramContext)
  {
    a(paramContext);
  }
  
  private native int ChmodInner(String paramString1, String paramString2);
  
  private void a(Context paramContext)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("TbsLinuxToolsJni init mbIsInited is ");
      localStringBuilder.append(b);
      h.a("TbsLinuxToolsJni", localStringBuilder.toString());
      if (b) {
        return;
      }
      b = true;
      try
      {
        paramContext = a(paramContext);
        if (paramContext != null)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(paramContext.getAbsolutePath());
          localStringBuilder.append(File.separator);
          localStringBuilder.append("liblinuxtoolsfortbssdk_jni.so");
          new File(localStringBuilder.toString()).exists();
          if (paramContext != null)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("TbsLinuxToolsJni init tbsSharePath is ");
            localStringBuilder.append(paramContext.getAbsolutePath());
            h.a("TbsLinuxToolsJni", localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(paramContext.getAbsolutePath());
            localStringBuilder.append(File.separator);
            localStringBuilder.append("liblinuxtoolsfortbssdk_jni.so");
            System.load(localStringBuilder.toString());
            a = true;
          }
        }
        ChmodInner("/checkChmodeExists", "700");
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        a = false;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("TbsLinuxToolsJni init error !!! ");
        localStringBuilder.append(paramContext.getMessage());
        localStringBuilder.append(" ## ");
        localStringBuilder.append(paramContext.getCause());
        h.a("TbsLinuxToolsJni", localStringBuilder.toString());
      }
      return;
    }
    finally {}
  }
  
  public int a(String paramString1, String paramString2)
  {
    if (!a)
    {
      h.b("TbsLinuxToolsJni", "jni not loaded!", true);
      return -1;
    }
    return ChmodInner(paramString1, paramString2);
  }
  
  File a(Context paramContext)
  {
    File localFile = new File(paramContext.getDir("tbs", 0), "core_share");
    if ((!localFile.isDirectory()) && (paramContext == null) && (!localFile.mkdir()))
    {
      h.a("TbsLinuxToolsJni", "getTbsCoreShareDir,mkdir false");
      return null;
    }
    return localFile;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\TbsLinuxToolsJni.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */