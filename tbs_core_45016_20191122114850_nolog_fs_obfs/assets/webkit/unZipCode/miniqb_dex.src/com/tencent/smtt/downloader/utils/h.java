package com.tencent.smtt.downloader.utils;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;

public class h
{
  public static int a = 0;
  private static TbsLogClient jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
  public static List<String> a;
  private static boolean jdField_a_of_type_Boolean = true;
  private static boolean b = true;
  
  static
  {
    jdField_a_of_type_JavaUtilList = new LinkedList();
    jdField_a_of_type_Int = 10;
  }
  
  public static void a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient == null) {
        a(new TbsLogClient(paramContext));
      }
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  public static void a(String paramString, Context paramContext)
  {
    for (;;)
    {
      int i;
      try
      {
        localObject = paramContext.getApplicationContext();
        paramContext = new String[6];
        i = 0;
        paramContext[0] = "com.tencent.tbs";
        paramContext[1] = "com.tencent.mtt";
        paramContext[2] = "com.tencent.mm";
        paramContext[3] = "com.tencent.mobileqq";
        paramContext[4] = "com.tencent.mtt.sdk.test";
        paramContext[5] = "com.qzone";
        if (i < paramContext.length)
        {
          if (!((Context)localObject).getPackageName().contains(paramContext[i])) {
            break label231;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("app_extra pid:");
          ((StringBuilder)localObject).append(Process.myPid());
          ((StringBuilder)localObject).append("; APP_TAG:");
          ((StringBuilder)localObject).append(new String[] { "DEMO", "QB", "WX", "QQ", "TEST", "QZ" }[i]);
          ((StringBuilder)localObject).append("!");
          a(paramString, ((StringBuilder)localObject).toString());
        }
        if (i == paramContext.length)
        {
          paramContext = new StringBuilder();
          paramContext.append("app_extra pid:");
          paramContext.append(Process.myPid());
          paramContext.append("; APP_TAG:OTHER!");
          a(paramString, paramContext.toString());
          return;
        }
      }
      catch (Throwable paramContext)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("app_extra exception:");
        ((StringBuilder)localObject).append(Log.getStackTraceString(paramContext));
        c(paramString, ((StringBuilder)localObject).toString());
      }
      return;
      label231:
      i += 1;
    }
  }
  
  public static void a(String paramString1, String paramString2)
  {
    TbsLogClient localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    if (localTbsLogClient == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.a(paramString1, localStringBuilder.toString());
    localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("(I)-");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("-TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.a(localStringBuilder.toString());
  }
  
  public static void a(String paramString1, String paramString2, boolean paramBoolean)
  {
    a(paramString1, paramString2);
    TbsLogClient localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    if ((localTbsLogClient != null) && (jdField_a_of_type_Boolean) && (paramBoolean))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append(": ");
      localStringBuilder.append(paramString2);
      localTbsLogClient.b(localStringBuilder.toString());
    }
  }
  
  public static boolean a(TbsLogClient paramTbsLogClient)
  {
    if (paramTbsLogClient == null) {
      return false;
    }
    jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient = paramTbsLogClient;
    paramTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    TbsLogClient.a(b);
    return true;
  }
  
  public static void b(String paramString1, String paramString2)
  {
    TbsLogClient localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    if (localTbsLogClient == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.b(paramString1, localStringBuilder.toString());
    localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("(E)-");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("-TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.a(localStringBuilder.toString());
  }
  
  public static void b(String paramString1, String paramString2, boolean paramBoolean)
  {
    b(paramString1, paramString2);
    TbsLogClient localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    if ((localTbsLogClient != null) && (jdField_a_of_type_Boolean) && (paramBoolean))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append(": ");
      localStringBuilder.append(paramString2);
      localTbsLogClient.b(localStringBuilder.toString());
    }
  }
  
  public static void c(String paramString1, String paramString2)
  {
    TbsLogClient localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    if (localTbsLogClient == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.c(paramString1, localStringBuilder.toString());
    localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("(W)-");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("-TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.a(localStringBuilder.toString());
  }
  
  public static void d(String paramString1, String paramString2)
  {
    TbsLogClient localTbsLogClient = jdField_a_of_type_ComTencentSmttDownloaderUtilsTbsLogClient;
    if (localTbsLogClient == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TBS:");
    localStringBuilder.append(paramString2);
    localTbsLogClient.d(paramString1, localStringBuilder.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */