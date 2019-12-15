package android.webview.chromium;

import android.os.Build.VERSION;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebSettings.LayoutAlgorithm;
import com.tencent.tbs.core.webkit.WebSettings.PluginState;
import com.tencent.tbs.core.webkit.WebSettings.RenderPriority;
import com.tencent.tbs.core.webkit.WebSettings.ZoomDensity;
import org.chromium.android_webview.AwSettings;

public class ContentSettingsAdapter
  extends WebSettings
{
  private AwSettings mAwSettings;
  
  public ContentSettingsAdapter(AwSettings paramAwSettings)
  {
    this.mAwSettings = paramAwSettings;
  }
  
  public boolean enableSmoothTransition()
  {
    return false;
  }
  
  public boolean getAcceptThirdPartyCookies()
  {
    return this.mAwSettings.getAcceptThirdPartyCookies();
  }
  
  public boolean getAllowContentAccess()
  {
    return this.mAwSettings.getAllowContentAccess();
  }
  
  public boolean getAllowFileAccess()
  {
    return this.mAwSettings.getAllowFileAccess();
  }
  
  public boolean getAllowFileAccessFromFileURLs()
  {
    return this.mAwSettings.getAllowFileAccessFromFileURLs();
  }
  
  public boolean getAllowUniversalAccessFromFileURLs()
  {
    return this.mAwSettings.getAllowUniversalAccessFromFileURLs();
  }
  
  public AwSettings getAwSettings()
  {
    return this.mAwSettings;
  }
  
  public boolean getBlockNetworkImage()
  {
    try
    {
      boolean bool = this.mAwSettings.getImagesEnabled();
      return bool ^ true;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getBlockNetworkLoads()
  {
    try
    {
      boolean bool = this.mAwSettings.getBlockNetworkLoads();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getBuiltInZoomControls()
  {
    return this.mAwSettings.getBuiltInZoomControls();
  }
  
  public int getCacheMode()
  {
    return this.mAwSettings.getCacheMode();
  }
  
  public String getCursiveFontFamily()
  {
    try
    {
      String str = this.mAwSettings.getCursiveFontFamily();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getDatabaseEnabled()
  {
    try
    {
      boolean bool = this.mAwSettings.getDatabaseEnabled();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getDatabasePath()
  {
    return "";
  }
  
  public int getDefaultFixedFontSize()
  {
    try
    {
      int i = this.mAwSettings.getDefaultFixedFontSize();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getDefaultFontSize()
  {
    try
    {
      int i = this.mAwSettings.getDefaultFontSize();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getDefaultTextEncodingName()
  {
    try
    {
      String str = this.mAwSettings.getDefaultTextEncodingName();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public WebSettings.ZoomDensity getDefaultZoom()
  {
    return WebSettings.ZoomDensity.MEDIUM;
  }
  
  public int getDisabledActionModeMenuItems()
  {
    return this.mAwSettings.getDisabledActionModeMenuItems();
  }
  
  public boolean getDisplayZoomControls()
  {
    return this.mAwSettings.getDisplayZoomControls();
  }
  
  public boolean getDomStorageEnabled()
  {
    try
    {
      boolean bool = this.mAwSettings.getDomStorageEnabled();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getFantasyFontFamily()
  {
    try
    {
      String str = this.mAwSettings.getFantasyFontFamily();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getFixedFontFamily()
  {
    try
    {
      String str = this.mAwSettings.getFixedFontFamily();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getJavaScriptCanOpenWindowsAutomatically()
  {
    try
    {
      boolean bool = this.mAwSettings.getJavaScriptCanOpenWindowsAutomatically();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getJavaScriptEnabled()
  {
    try
    {
      boolean bool = this.mAwSettings.getJavaScriptEnabled();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public WebSettings.LayoutAlgorithm getLayoutAlgorithm()
  {
    for (;;)
    {
      try
      {
        int i = this.mAwSettings.getLayoutAlgorithm();
        Object localObject1;
        switch (i)
        {
        case 3: 
          continue;
          localObject1 = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;
          return (WebSettings.LayoutAlgorithm)localObject1;
        case 2: 
          localObject1 = WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
          return (WebSettings.LayoutAlgorithm)localObject1;
        case 1: 
          localObject1 = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
          return (WebSettings.LayoutAlgorithm)localObject1;
        case 0: 
          localObject1 = WebSettings.LayoutAlgorithm.NORMAL;
          return (WebSettings.LayoutAlgorithm)localObject1;
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Unsupported value: ");
          ((StringBuilder)localObject1).append(i);
          throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
        }
      }
      finally {}
    }
  }
  
  public boolean getLightTouchEnabled()
  {
    return false;
  }
  
  public boolean getLoadWithOverviewMode()
  {
    return this.mAwSettings.getLoadWithOverviewMode();
  }
  
  public boolean getLoadsImagesAutomatically()
  {
    try
    {
      boolean bool = this.mAwSettings.getLoadsImagesAutomatically();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getMediaPlaybackRequiresUserGesture()
  {
    return this.mAwSettings.getMediaPlaybackRequiresUserGesture();
  }
  
  public int getMinimumFontSize()
  {
    try
    {
      int i = this.mAwSettings.getMinimumFontSize();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getMinimumLogicalFontSize()
  {
    try
    {
      int i = this.mAwSettings.getMinimumLogicalFontSize();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getMixedContentMode()
  {
    return this.mAwSettings.getMixedContentMode();
  }
  
  @Deprecated
  public boolean getNavDump()
  {
    return false;
  }
  
  public boolean getOffscreenPreRaster()
  {
    return this.mAwSettings.getOffscreenPreRaster();
  }
  
  public WebSettings.PluginState getPluginState()
  {
    for (;;)
    {
      try
      {
        int i = this.mAwSettings.getPluginState();
        Object localObject1;
        switch (i)
        {
        case 2: 
          continue;
          localObject1 = WebSettings.PluginState.OFF;
          return (WebSettings.PluginState)localObject1;
        case 1: 
          localObject1 = WebSettings.PluginState.ON_DEMAND;
          return (WebSettings.PluginState)localObject1;
        case 0: 
          localObject1 = WebSettings.PluginState.ON;
          return (WebSettings.PluginState)localObject1;
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Unsupported value: ");
          ((StringBuilder)localObject1).append(i);
          throw new IllegalArgumentException(((StringBuilder)localObject1).toString());
        }
      }
      finally {}
    }
  }
  
  public boolean getPluginsEnabled()
  {
    try
    {
      boolean bool = this.mAwSettings.getPluginsEnabled();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getSafeBrowsingEnabled()
  {
    return this.mAwSettings.getSafeBrowsingEnabled();
  }
  
  public String getSansSerifFontFamily()
  {
    try
    {
      String str = this.mAwSettings.getSansSerifFontFamily();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getSaveFormData()
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return false;
    }
    return this.mAwSettings.getSaveFormData();
  }
  
  public boolean getSavePassword()
  {
    return false;
  }
  
  public String getSerifFontFamily()
  {
    try
    {
      String str = this.mAwSettings.getSerifFontFamily();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getStandardFontFamily()
  {
    try
    {
      String str = this.mAwSettings.getStandardFontFamily();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getTextZoom()
  {
    try
    {
      int i = this.mAwSettings.getTextZoom();
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getUseWebViewBackgroundForOverscrollBackground()
  {
    return false;
  }
  
  public boolean getUseWideViewPort()
  {
    try
    {
      boolean bool = this.mAwSettings.getUseWideViewPort();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getUserAgent()
  {
    try
    {
      boolean bool = AwSettings.getDefaultUserAgent().equals(getUserAgentString());
      int i;
      if (bool) {
        i = 0;
      } else {
        i = -1;
      }
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getUserAgentString()
  {
    try
    {
      String str = this.mAwSettings.getUserAgentString();
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean getVideoOverlayForEmbeddedEncryptedVideoEnabled()
  {
    return false;
  }
  
  public void setAcceptThirdPartyCookies(boolean paramBoolean)
  {
    this.mAwSettings.setAcceptThirdPartyCookies(paramBoolean);
  }
  
  public void setAllowContentAccess(boolean paramBoolean)
  {
    this.mAwSettings.setAllowContentAccess(paramBoolean);
  }
  
  public void setAllowFileAccess(boolean paramBoolean)
  {
    this.mAwSettings.setAllowFileAccess(paramBoolean);
  }
  
  public void setAllowFileAccessFromFileURLs(boolean paramBoolean)
  {
    this.mAwSettings.setAllowFileAccessFromFileURLs(paramBoolean);
  }
  
  public void setAllowUniversalAccessFromFileURLs(boolean paramBoolean)
  {
    this.mAwSettings.setAllowUniversalAccessFromFileURLs(paramBoolean);
  }
  
  public void setAppCacheEnabled(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setAppCacheEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setAppCacheMaxSize(long paramLong) {}
  
  public void setAppCachePath(String paramString)
  {
    try
    {
      this.mAwSettings.setAppCachePath(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setBlockNetworkImage(boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        AwSettings localAwSettings = this.mAwSettings;
        if (!paramBoolean)
        {
          paramBoolean = true;
          localAwSettings.setImagesEnabled(paramBoolean);
          return;
        }
      }
      finally {}
      paramBoolean = false;
    }
  }
  
  public void setBlockNetworkLoads(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setBlockNetworkLoads(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setBuiltInZoomControls(boolean paramBoolean)
  {
    this.mAwSettings.setBuiltInZoomControls(paramBoolean);
  }
  
  public void setCacheMode(int paramInt)
  {
    this.mAwSettings.setCacheMode(paramInt);
  }
  
  public void setCursiveFontFamily(String paramString)
  {
    try
    {
      this.mAwSettings.setCursiveFontFamily(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setDatabaseEnabled(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setDatabaseEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setDatabasePath(String paramString) {}
  
  public void setDefaultFixedFontSize(int paramInt)
  {
    try
    {
      this.mAwSettings.setDefaultFixedFontSize(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setDefaultFontSize(int paramInt)
  {
    try
    {
      this.mAwSettings.setDefaultFontSize(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setDefaultTextEncodingName(String paramString)
  {
    try
    {
      this.mAwSettings.setDefaultTextEncodingName(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setDefaultZoom(WebSettings.ZoomDensity paramZoomDensity) {}
  
  public void setDisabledActionModeMenuItems(int paramInt)
  {
    this.mAwSettings.setDisabledActionModeMenuItems(paramInt);
  }
  
  public void setDisplayZoomControls(boolean paramBoolean)
  {
    this.mAwSettings.setDisplayZoomControls(paramBoolean);
  }
  
  public void setDomStorageEnabled(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setDomStorageEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setEnableSmoothTransition(boolean paramBoolean) {}
  
  public void setFantasyFontFamily(String paramString)
  {
    try
    {
      this.mAwSettings.setFantasyFontFamily(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setFixedFontFamily(String paramString)
  {
    try
    {
      this.mAwSettings.setFixedFontFamily(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setGeolocationDatabasePath(String paramString) {}
  
  public void setGeolocationEnabled(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setGeolocationEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setJavaScriptCanOpenWindowsAutomatically(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setJavaScriptCanOpenWindowsAutomatically(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setJavaScriptEnabled(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setJavaScriptEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setLayoutAlgorithm(WebSettings.LayoutAlgorithm paramLayoutAlgorithm)
  {
    for (;;)
    {
      try
      {
        switch (1.$SwitchMap$com$tencent$tbs$core$webkit$WebSettings$LayoutAlgorithm[paramLayoutAlgorithm.ordinal()])
        {
        case 4: 
          continue;
          this.mAwSettings.setLayoutAlgorithm(3);
          return;
        case 3: 
          this.mAwSettings.setLayoutAlgorithm(2);
          return;
        case 2: 
          this.mAwSettings.setLayoutAlgorithm(1);
          return;
        case 1: 
          this.mAwSettings.setLayoutAlgorithm(0);
          return;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unsupported value: ");
          localStringBuilder.append(paramLayoutAlgorithm);
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
      }
      finally {}
    }
  }
  
  public void setLightTouchEnabled(boolean paramBoolean) {}
  
  public void setLoadWithOverviewMode(boolean paramBoolean)
  {
    this.mAwSettings.setLoadWithOverviewMode(paramBoolean);
  }
  
  public void setLoadsImagesAutomatically(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setLoadsImagesAutomatically(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setMediaPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    this.mAwSettings.setMediaPlaybackRequiresUserGesture(paramBoolean);
  }
  
  public void setMinimumFontSize(int paramInt)
  {
    try
    {
      this.mAwSettings.setMinimumFontSize(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setMinimumLogicalFontSize(int paramInt)
  {
    try
    {
      this.mAwSettings.setMinimumLogicalFontSize(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setMixedContentMode(int paramInt)
  {
    this.mAwSettings.setMixedContentMode(paramInt);
  }
  
  @Deprecated
  public void setNavDump(boolean paramBoolean) {}
  
  public void setNeedInitialFocus(boolean paramBoolean)
  {
    this.mAwSettings.setShouldFocusFirstNode(paramBoolean);
  }
  
  public void setOffscreenPreRaster(boolean paramBoolean)
  {
    this.mAwSettings.setOffscreenPreRaster(paramBoolean);
  }
  
  public void setPluginState(WebSettings.PluginState paramPluginState)
  {
    for (;;)
    {
      try
      {
        switch (paramPluginState)
        {
        case ???: 
          continue;
          this.mAwSettings.setPluginState(1);
          return;
        case ???: 
          this.mAwSettings.setPluginState(0);
          return;
        case ???: 
          this.mAwSettings.setPluginState(2);
          return;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unsupported value: ");
          localStringBuilder.append(paramPluginState);
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
      }
      finally {}
    }
  }
  
  public void setPluginsEnabled(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setPluginsEnabled(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setRenderPriority(WebSettings.RenderPriority paramRenderPriority) {}
  
  public void setSafeBrowsingEnabled(boolean paramBoolean)
  {
    this.mAwSettings.setSafeBrowsingEnabled(paramBoolean);
  }
  
  public void setSansSerifFontFamily(String paramString)
  {
    try
    {
      this.mAwSettings.setSansSerifFontFamily(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setSaveFormData(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return;
    }
    this.mAwSettings.setSaveFormData(paramBoolean);
  }
  
  public void setSavePassword(boolean paramBoolean) {}
  
  public void setSerifFontFamily(String paramString)
  {
    try
    {
      this.mAwSettings.setSerifFontFamily(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setStandardFontFamily(String paramString)
  {
    try
    {
      this.mAwSettings.setStandardFontFamily(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setSupportMultipleWindows(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setSupportMultipleWindows(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setSupportZoom(boolean paramBoolean)
  {
    this.mAwSettings.setSupportZoom(paramBoolean);
  }
  
  public void setTextZoom(int paramInt)
  {
    try
    {
      this.mAwSettings.setTextZoom(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setUseWebViewBackgroundForOverscrollBackground(boolean paramBoolean) {}
  
  public void setUseWideViewPort(boolean paramBoolean)
  {
    try
    {
      this.mAwSettings.setUseWideViewPort(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setUserAgent(int paramInt)
  {
    try
    {
      this.mAwSettings.setUserAgent(paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setUserAgentString(String paramString)
  {
    try
    {
      this.mAwSettings.setUserAgentString(paramString);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setVideoOverlayForEmbeddedEncryptedVideoEnabled(boolean paramBoolean) {}
  
  public boolean supportMultipleWindows()
  {
    try
    {
      boolean bool = this.mAwSettings.supportMultipleWindows();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean supportZoom()
  {
    return this.mAwSettings.supportZoom();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\ContentSettingsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */