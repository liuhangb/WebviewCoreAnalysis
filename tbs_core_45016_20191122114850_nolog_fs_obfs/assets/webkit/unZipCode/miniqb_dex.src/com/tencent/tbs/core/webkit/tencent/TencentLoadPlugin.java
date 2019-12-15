package com.tencent.tbs.core.webkit.tencent;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.tencent.smtt.export.external.X5Graphics.GraphicsLib;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.X5LoadPlugin.IX5LoadPluginCallback;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.tencent.base.TencentPathUtils;

public class TencentLoadPlugin
{
  private static final String PLUGIN_SHARPP_PACKAGE_NAME = "com.tencent.qb.plugin.sharpP";
  private static final String PLUGIN_TPG_PACKAGE_NAME = "com.tencent.qb.plugin.TPG";
  public static int SoType_SharpP = 1;
  public static int SoType_TPG = 2;
  private static final String TAG = "TencentLoadPlugin";
  private static Lock loadLock;
  private static int[] lockObjectForCount = { 0, 0 };
  private static String sSOSharpPDir = null;
  private static String sSOTPGDir = null;
  public static String soNameSharpP;
  public static String soNameTPG;
  
  static
  {
    loadLock = new ReentrantLock();
    soNameSharpP = "libSharpPDecoder.so";
    soNameTPG = "libTPGDecoder.so";
  }
  
  public static boolean checkExistSO(int paramInt)
  {
    try
    {
      StringBuilder localStringBuilder;
      if (paramInt == SoType_SharpP)
      {
        if ((sSOSharpPDir == null) || (sSOSharpPDir.isEmpty()))
        {
          sSOSharpPDir = SmttServiceProxy.getInstance().getSharpPPath();
          if (sSOSharpPDir == null) {
            break label207;
          }
          if (sSOSharpPDir.isEmpty()) {
            return false;
          }
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(sSOSharpPDir);
        localStringBuilder.append("/");
        localStringBuilder.append(soNameSharpP);
        if (new File(localStringBuilder.toString()).exists()) {
          return true;
        }
      }
      else if (paramInt == SoType_TPG)
      {
        if ((sSOTPGDir == null) || (sSOTPGDir.isEmpty()))
        {
          sSOTPGDir = SmttServiceProxy.getInstance().getTPGPath();
          if (sSOTPGDir == null) {
            break label198;
          }
          if (sSOTPGDir.isEmpty()) {
            return false;
          }
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(sSOTPGDir);
        localStringBuilder.append("/");
        localStringBuilder.append(soNameTPG);
        boolean bool = new File(localStringBuilder.toString()).exists();
        if (bool)
        {
          return true;
          label198:
          return false;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
    label207:
    return false;
  }
  
  @CalledByNative
  private static String getSoDir(int paramInt)
  {
    if (!e.a().m())
    {
      Object localObject = TencentPathUtils.getNativeLibraryDirectory();
      if (localObject != null) {
        return (String)localObject;
      }
      localObject = ContextUtils.getApplicationContext().getApplicationInfo();
      if (((((ApplicationInfo)localObject).flags & 0x80) == 0) && ((((ApplicationInfo)localObject).flags & 0x1) != 0)) {
        return "/system/lib/";
      }
      return ((ApplicationInfo)localObject).nativeLibraryDir;
    }
    if (paramInt == SoType_SharpP) {
      return sSOSharpPDir;
    }
    if (paramInt == SoType_TPG) {
      return sSOTPGDir;
    }
    return "";
  }
  
  public static void loadSO(int paramInt)
  {
    if (loadLock.tryLock())
    {
      int[] arrayOfInt = lockObjectForCount;
      int i = paramInt - 1;
      if (arrayOfInt[i] > 0) {
        return;
      }
      arrayOfInt[i] += 1;
      loadLock.unlock();
    }
    SmttServiceProxy.getInstance().loadX5Plugin(paramInt, new IX5LoadPluginCallback()
    {
      public void onDownloadProgress(int paramAnonymousInt) {}
      
      public void onDownloadStart(int paramAnonymousInt) {}
      
      public void onLoadFailed(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString)
      {
        if (TencentLoadPlugin.loadLock.tryLock())
        {
          TencentLoadPlugin.lockObjectForCount[(paramAnonymousInt1 - 1)] = 0;
          TencentLoadPlugin.loadLock.unlock();
        }
        TencentLoadPlugin.reportDownloadResult(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousString);
      }
      
      public void onLoadSuccess(int paramAnonymousInt, String paramAnonymousString)
      {
        if (paramAnonymousInt == TencentLoadPlugin.SoType_SharpP)
        {
          TencentLoadPlugin.access$002(paramAnonymousString);
          SmttServiceProxy.getInstance().saveSharpPPath(TencentLoadPlugin.sSOSharpPDir);
        }
        for (;;)
        {
          try
          {
            GraphicsLib.setSharpPPath(TencentLoadPlugin.sSOSharpPDir);
          }
          catch (Throwable paramAnonymousString)
          {
            StringBuilder localStringBuilder;
            continue;
          }
          try
          {
            paramAnonymousString = GraphicsLib.getGraphicsLibPath();
            if ((paramAnonymousString == null) || (paramAnonymousString.isEmpty())) {
              continue;
            }
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(paramAnonymousString);
            localStringBuilder.append(File.separator);
            localStringBuilder.append("libwebp_base.so");
            paramAnonymousString = new File(localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("nativeError_Size=");
            localStringBuilder.append(paramAnonymousString.length());
            TencentLoadPlugin.reportDownloadResult(paramAnonymousInt, -1, localStringBuilder.toString());
          }
          catch (Exception paramAnonymousString) {}
        }
        break label146;
        if (paramAnonymousInt == TencentLoadPlugin.SoType_TPG)
        {
          TencentLoadPlugin.access$202(paramAnonymousString);
          SmttServiceProxy.getInstance().saveTPGPath(TencentLoadPlugin.sSOTPGDir);
        }
        label146:
        if (TencentLoadPlugin.loadLock.tryLock())
        {
          TencentLoadPlugin.lockObjectForCount[(paramAnonymousInt - 1)] = 0;
          TencentLoadPlugin.loadLock.unlock();
        }
      }
    });
  }
  
  private static void reportDownloadResult(int paramInt1, int paramInt2, String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("soType", Integer.toString(paramInt1));
    localHashMap.put("errorCode", Integer.toString(paramInt2));
    localHashMap.put("message", paramString);
    SmttServiceProxy.getInstance().upLoadToBeacon("DECODESO_PLUGIN", localHashMap);
  }
  
  @CalledByNative
  private static void startLoadPlugin(int paramInt)
  {
    if (!e.a().m()) {
      return;
    }
    try
    {
      StringBuilder localStringBuilder;
      if (paramInt == SoType_SharpP)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(sSOSharpPDir);
        localStringBuilder.append("/");
        localStringBuilder.append(soNameSharpP);
        if (new File(localStringBuilder.toString()).exists()) {
          return;
        }
        loadSO(SoType_SharpP);
        return;
      }
      if (paramInt == SoType_TPG)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(sSOTPGDir);
        localStringBuilder.append("/");
        localStringBuilder.append(soNameTPG);
        if (!new File(localStringBuilder.toString()).exists()) {
          loadSO(SoType_TPG);
        }
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentLoadPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */