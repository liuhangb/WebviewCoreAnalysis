package com.tencent.smtt.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.smtt.webkit.ContextHolder;
import java.io.File;

public class o
{
  private static boolean jdField_a_of_type_Boolean = false;
  private static boolean b = false;
  private X5LogIPCBroadcastReceiver jdField_a_of_type_ComTencentSmttUtilX5LogIPCBroadcastReceiver;
  
  public static o a()
  {
    return a.a();
  }
  
  public static void a()
  {
    if (b)
    {
      X5LogManager.setLogWrite2FileFlag(true, "init_toolsmp");
      b = false;
    }
  }
  
  private void a(Context paramContext, boolean paramBoolean)
  {
    if (paramContext == null) {
      return;
    }
    Intent localIntent = new Intent("com.tencent.x5.x5log");
    int i;
    if (paramBoolean) {
      i = 1;
    } else {
      i = 2;
    }
    localIntent.putExtra("x5log_broadcast", i);
    paramContext.sendBroadcast(localIntent);
  }
  
  private void a(String paramString)
  {
    Context localContext = ContextHolder.getInstance().getApplicationContext();
    if (localContext == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(FileUtils.getFilesDir(localContext));
    localStringBuilder.append(paramString);
    paramString = localStringBuilder.toString();
    try
    {
      paramString = new File(paramString);
      if (paramString.exists()) {
        paramString.delete();
      }
      if ((paramString.getParentFile() != null) && (!paramString.getParentFile().exists())) {
        paramString.getParentFile().mkdirs();
      }
      paramString.createNewFile();
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  private boolean a(Context paramContext, String paramString)
  {
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool4 = false;
    if (paramContext == null) {
      return false;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(FileUtils.getFilesDir(paramContext));
    localStringBuilder.append(paramString);
    paramContext = localStringBuilder.toString();
    boolean bool1 = bool3;
    try
    {
      paramContext = new File(paramContext);
      bool1 = bool3;
      if (paramContext.exists())
      {
        bool2 = bool4;
        bool1 = bool3;
        if (System.currentTimeMillis() - paramContext.lastModified() < 300000L) {
          bool2 = true;
        }
        bool1 = bool2;
        paramContext.delete();
        return bool2;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      bool2 = bool1;
    }
    return bool2;
  }
  
  private void b(Context paramContext)
  {
    if ("com.tencent.mm_toolsmp".equals(ThreadUtils.getCurrentProcessNameIngoreColon(paramContext)))
    {
      jdField_a_of_type_Boolean = true;
      e(paramContext);
      d(paramContext);
    }
  }
  
  private void b(String paramString)
  {
    Context localContext = ContextHolder.getInstance().getApplicationContext();
    if (localContext == null) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(FileUtils.getFilesDir(localContext));
    localStringBuilder.append(paramString);
    paramString = localStringBuilder.toString();
    try
    {
      paramString = new File(paramString);
      if (paramString.exists())
      {
        paramString.delete();
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  private void c(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    Intent localIntent = new Intent("com.tencent.x5.x5log");
    localIntent.putExtra("x5log_broadcast", 3);
    paramContext.sendBroadcast(localIntent);
  }
  
  private void d()
  {
    a("/live_log/x5log.mark");
  }
  
  private void d(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    paramContext = paramContext.getApplicationContext();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.tencent.x5.x5log");
    if (this.jdField_a_of_type_ComTencentSmttUtilX5LogIPCBroadcastReceiver == null) {
      this.jdField_a_of_type_ComTencentSmttUtilX5LogIPCBroadcastReceiver = new X5LogIPCBroadcastReceiver();
    }
    paramContext.registerReceiver(this.jdField_a_of_type_ComTencentSmttUtilX5LogIPCBroadcastReceiver, localIntentFilter);
  }
  
  private void e()
  {
    a("/live_log/kill.mark");
  }
  
  private void e(Context paramContext)
  {
    if (a(paramContext)) {
      b = true;
    }
  }
  
  public void a(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    b(paramContext);
  }
  
  public void a(boolean paramBoolean)
  {
    Context localContext = ContextHolder.getInstance().getApplicationContext();
    if (localContext == null) {
      return;
    }
    if ("com.tencent.mm_tools".equals(ThreadUtils.getCurrentProcessNameIngoreColon(localContext)))
    {
      if (paramBoolean) {
        d();
      } else {
        c();
      }
      a(localContext, paramBoolean);
    }
  }
  
  public boolean a()
  {
    return jdField_a_of_type_Boolean;
  }
  
  public boolean a(Context paramContext)
  {
    if (paramContext == null) {
      return false;
    }
    return a(paramContext, "/live_log/x5log.mark");
  }
  
  public void b()
  {
    Context localContext = ContextHolder.getInstance().getApplicationContext();
    if (localContext == null) {
      return;
    }
    if ("com.tencent.mm_tools".equals(ThreadUtils.getCurrentProcessNameIngoreColon(localContext)))
    {
      e();
      c(localContext);
    }
  }
  
  public boolean b(Context paramContext)
  {
    if (paramContext == null) {
      return false;
    }
    return a(paramContext, "/live_log/kill.mark");
  }
  
  public void c()
  {
    b("/live_log/x5log.mark");
  }
  
  private static class a
  {
    private static final o a = new o(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */