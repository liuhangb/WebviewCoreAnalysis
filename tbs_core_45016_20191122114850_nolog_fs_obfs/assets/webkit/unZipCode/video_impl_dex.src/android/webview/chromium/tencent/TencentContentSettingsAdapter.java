package android.webview.chromium.tencent;

import android.webview.chromium.ContentSettingsAdapter;
import com.tencent.tbs.core.webkit.WebSettings.TextSize;
import java.lang.reflect.Field;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.TencentAwSettings;

public class TencentContentSettingsAdapter
  extends ContentSettingsAdapter
{
  public static boolean X5RenderPerformDebug = false;
  private static Field sTextSizeValueField;
  private static String sUserAgent = "";
  private boolean mAllowSavePassword = true;
  private TencentAwSettings mAwSettings;
  
  @UsedByReflection("WebCoreProxy.java")
  public TencentContentSettingsAdapter(TencentAwSettings paramTencentAwSettings)
  {
    super(paramTencentAwSettings);
    this.mAwSettings = paramTencentAwSettings;
    this.mAwSettings.setSaveFormData(true);
  }
  
  public static String getStaticUserAgent()
  {
    try
    {
      String str = sUserAgent;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  static int getTextSizeValue(WebSettings.TextSize paramTextSize)
  {
    try
    {
      if (sTextSizeValueField == null)
      {
        sTextSizeValueField = WebSettings.TextSize.class.getDeclaredField("value");
        sTextSizeValueField.setAccessible(true);
      }
      int i = sTextSizeValueField.getInt(paramTextSize);
      return i;
    }
    catch (Exception paramTextSize)
    {
      paramTextSize.printStackTrace();
    }
    return 100;
  }
  
  public static void setStaticUserAgent(String paramString)
  {
    try
    {
      sUserAgent = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public boolean getAppCacheEnabled()
  {
    return false;
  }
  
  public long getAppCacheMaxSize()
  {
    return 0L;
  }
  
  public String getAppCachePath()
  {
    return null;
  }
  
  public TencentAwSettings getAwSettings()
  {
    return this.mAwSettings;
  }
  
  public String getGeolocationDatabasePath()
  {
    return null;
  }
  
  public boolean getGeolocationEnabled()
  {
    return false;
  }
  
  public boolean getSaveFormData()
  {
    return this.mAwSettings.getSaveFormData();
  }
  
  public boolean getSavePassword()
  {
    return this.mAllowSavePassword;
  }
  
  public WebSettings.TextSize getTextSize()
  {
    int n = getTextZoom();
    WebSettings.TextSize[] arrayOfTextSize = WebSettings.TextSize.values();
    int i1 = arrayOfTextSize.length;
    Object localObject = null;
    int j = Integer.MAX_VALUE;
    int i = 0;
    while (i < i1)
    {
      WebSettings.TextSize localTextSize = arrayOfTextSize[i];
      int m = Math.abs(n - getTextSizeValue(localTextSize));
      if (m == 0) {
        return localTextSize;
      }
      int k = j;
      if (m < j)
      {
        localObject = localTextSize;
        k = m;
      }
      i += 1;
      j = k;
    }
    if (localObject != null) {
      return (WebSettings.TextSize)localObject;
    }
    return WebSettings.TextSize.NORMAL;
  }
  
  public boolean getVideoOverlayForEmbeddedEncryptedVideoEnabled()
  {
    return this.mAwSettings.getVideoOverlayForEmbeddedVideoEnabled();
  }
  
  public void setDefaultDatabasePath(boolean paramBoolean) {}
  
  public void setEnableSupportedHardwareAcceleratedFeatures(boolean paramBoolean)
  {
    this.mAwSettings.setEnableSupportedHardwareAcceleratedFeatures(paramBoolean);
  }
  
  public void setMediaPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    TencentAwSettings localTencentAwSettings = this.mAwSettings;
    if ((localTencentAwSettings instanceof TencentAwSettings)) {
      localTencentAwSettings.setVideoPlaybackRequiresUserGesture(paramBoolean);
    }
    this.mAwSettings.setMediaPlaybackRequiresUserGesture(paramBoolean);
  }
  
  public void setPluginEnabled(boolean paramBoolean) {}
  
  public void setSaveFormData(boolean paramBoolean)
  {
    this.mAwSettings.setSaveFormData(paramBoolean);
  }
  
  public void setSavePassword(boolean paramBoolean)
  {
    this.mAllowSavePassword = paramBoolean;
  }
  
  public void setTextSize(WebSettings.TextSize paramTextSize)
  {
    setTextZoom(getTextSizeValue(paramTextSize));
  }
  
  public void setUserAgent(String paramString)
  {
    this.mAwSettings.setUserAgentString(paramString);
  }
  
  public void setUserAgent(String paramString, boolean paramBoolean)
  {
    this.mAwSettings.setUserAgentString(paramString, paramBoolean);
  }
  
  public void setVideoOverlayForEmbeddedEncryptedVideoEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setVideoOverlayForEmbeddedVideoEnabled(paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentContentSettingsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */