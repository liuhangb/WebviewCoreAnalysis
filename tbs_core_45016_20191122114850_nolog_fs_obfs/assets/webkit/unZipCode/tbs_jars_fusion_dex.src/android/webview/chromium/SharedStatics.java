package android.webview.chromium;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import java.util.List;
import org.chromium.android_webview.AwContentsClient;
import org.chromium.android_webview.AwContentsStatics;
import org.chromium.android_webview.AwDevToolsServer;
import org.chromium.android_webview.AwSettings;
import org.chromium.android_webview.command_line.CommandLineUtil;
import org.chromium.base.Callback;
import org.chromium.base.MemoryPressureListener;
import org.chromium.base.ThreadUtils;

public class SharedStatics
{
  private AwDevToolsServer mDevToolsServer;
  
  public void clearClientCertPreferences(final Runnable paramRunnable)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        AwContentsStatics.clearClientCertPreferences(paramRunnable);
      }
    });
  }
  
  public void enableSlowWholeDocumentDraw() {}
  
  public String findAddress(String paramString)
  {
    return AwContentsStatics.findAddress(paramString);
  }
  
  public void freeMemoryForTests()
  {
    if (ActivityManager.isRunningInTestHarness()) {
      MemoryPressureListener.onMemoryPressure(2);
    }
  }
  
  public String getDefaultUserAgent(Context paramContext)
  {
    return AwSettings.getDefaultUserAgent();
  }
  
  public Uri getSafeBrowsingPrivacyPolicyUrl()
  {
    return AwContentsStatics.getSafeBrowsingPrivacyPolicyUrl();
  }
  
  public void initSafeBrowsing(final Context paramContext, final Callback<Boolean> paramCallback)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        AwContentsStatics.initSafeBrowsing(paramContext, paramCallback);
      }
    });
  }
  
  public Uri[] parseFileChooserResult(int paramInt, Intent paramIntent)
  {
    return AwContentsClient.parseFileChooserResult(paramInt, paramIntent);
  }
  
  public void setSafeBrowsingWhitelist(final List<String> paramList, final Callback<Boolean> paramCallback)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        AwContentsStatics.setSafeBrowsingWhitelist(paramList, paramCallback);
      }
    });
  }
  
  public void setWebContentsDebuggingEnabled(boolean paramBoolean)
  {
    if (!CommandLineUtil.isBuildDebuggable()) {
      setWebContentsDebuggingEnabledUnconditionally(paramBoolean);
    }
  }
  
  public void setWebContentsDebuggingEnabledUnconditionally(boolean paramBoolean)
  {
    if (Looper.myLooper() == ThreadUtils.getUiThreadLooper())
    {
      if (this.mDevToolsServer == null)
      {
        if (!paramBoolean) {
          return;
        }
        this.mDevToolsServer = new AwDevToolsServer();
      }
      this.mDevToolsServer.setRemoteDebuggingEnabled(paramBoolean);
      return;
    }
    throw new RuntimeException("Toggling of Web Contents Debugging must be done on the UI thread");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\SharedStatics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */