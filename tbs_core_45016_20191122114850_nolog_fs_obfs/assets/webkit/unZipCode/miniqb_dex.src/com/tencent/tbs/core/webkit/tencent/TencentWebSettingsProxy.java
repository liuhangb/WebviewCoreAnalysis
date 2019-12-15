package com.tencent.tbs.core.webkit.tencent;

import android.webview.chromium.ContentSettingsAdapter;
import android.webview.chromium.tencent.TencentContentSettingsAdapter;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.LayoutAlgorithm;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.PluginState;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.RenderPriority;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.TextSize;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.ZoomDensity;
import com.tencent.smtt.util.X5Log;
import com.tencent.tbs.core.webkit.WebSettings.LayoutAlgorithm;
import com.tencent.tbs.core.webkit.WebSettings.PluginState;
import com.tencent.tbs.core.webkit.WebSettings.RenderPriority;
import com.tencent.tbs.core.webkit.WebSettings.TextSize;
import com.tencent.tbs.core.webkit.WebSettings.ZoomDensity;

public class TencentWebSettingsProxy
  implements IX5WebSettings
{
  private TencentContentSettingsAdapter mTencentWebSettings;
  private ContentSettingsAdapter mWebSettings;
  
  public TencentWebSettingsProxy(ContentSettingsAdapter paramContentSettingsAdapter)
  {
    if ((paramContentSettingsAdapter instanceof TencentContentSettingsAdapter))
    {
      this.mTencentWebSettings = ((TencentContentSettingsAdapter)paramContentSettingsAdapter);
      return;
    }
    this.mWebSettings = paramContentSettingsAdapter;
  }
  
  public boolean enableSmoothTransition()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.enableSmoothTransition();
    }
    return this.mWebSettings.enableSmoothTransition();
  }
  
  public boolean getAllowContentAccess()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getAllowContentAccess();
    }
    return this.mWebSettings.getAllowContentAccess();
  }
  
  public boolean getAllowFileAccess()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getAllowFileAccess();
    }
    return this.mWebSettings.getAllowFileAccess();
  }
  
  public boolean getBlockNetworkImage()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getBlockNetworkImage();
    }
    return this.mWebSettings.getBlockNetworkImage();
  }
  
  public boolean getBlockNetworkLoads()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getBlockNetworkLoads();
    }
    return this.mWebSettings.getBlockNetworkLoads();
  }
  
  public boolean getBuiltInZoomControls()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getBuiltInZoomControls();
    }
    return this.mWebSettings.getBuiltInZoomControls();
  }
  
  public int getCacheMode()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getCacheMode();
    }
    return this.mWebSettings.getCacheMode();
  }
  
  public String getCursiveFontFamily()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getCursiveFontFamily();
    }
    return this.mWebSettings.getCursiveFontFamily();
  }
  
  public boolean getDatabaseEnabled()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDatabaseEnabled();
    }
    return this.mWebSettings.getDatabaseEnabled();
  }
  
  public String getDatabasePath()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDatabasePath();
    }
    return this.mWebSettings.getDatabasePath();
  }
  
  public int getDefaultFixedFontSize()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDefaultFixedFontSize();
    }
    return this.mWebSettings.getDefaultFixedFontSize();
  }
  
  public int getDefaultFontSize()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDefaultFontSize();
    }
    return this.mWebSettings.getDefaultFontSize();
  }
  
  public String getDefaultTextEncodingName()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDefaultTextEncodingName();
    }
    return this.mWebSettings.getDefaultTextEncodingName();
  }
  
  public IX5WebSettings.ZoomDensity getDefaultZoom()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return IX5WebSettings.ZoomDensity.valueOf(localTencentContentSettingsAdapter.getDefaultZoom().name());
    }
    return IX5WebSettings.ZoomDensity.valueOf(this.mWebSettings.getDefaultZoom().name());
  }
  
  public boolean getDisplayZoomControls()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDisplayZoomControls();
    }
    return this.mWebSettings.getDisplayZoomControls();
  }
  
  public boolean getDomStorageEnabled()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getDomStorageEnabled();
    }
    return this.mWebSettings.getDomStorageEnabled();
  }
  
  public String getFantasyFontFamily()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getFantasyFontFamily();
    }
    return this.mWebSettings.getFantasyFontFamily();
  }
  
  public String getFixedFontFamily()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getFixedFontFamily();
    }
    return this.mWebSettings.getFixedFontFamily();
  }
  
  public boolean getJavaScriptCanOpenWindowsAutomatically()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getJavaScriptCanOpenWindowsAutomatically();
    }
    return this.mWebSettings.getJavaScriptCanOpenWindowsAutomatically();
  }
  
  public boolean getJavaScriptEnabled()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getJavaScriptEnabled();
    }
    return this.mWebSettings.getJavaScriptEnabled();
  }
  
  public IX5WebSettings.LayoutAlgorithm getLayoutAlgorithm()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return IX5WebSettings.LayoutAlgorithm.valueOf(localTencentContentSettingsAdapter.getLayoutAlgorithm().name());
    }
    return IX5WebSettings.LayoutAlgorithm.valueOf(this.mWebSettings.getLayoutAlgorithm().name());
  }
  
  public boolean getLightTouchEnabled()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getLightTouchEnabled();
    }
    return this.mWebSettings.getLightTouchEnabled();
  }
  
  public boolean getLoadWithOverviewMode()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getLoadWithOverviewMode();
    }
    return this.mWebSettings.getLoadWithOverviewMode();
  }
  
  public boolean getLoadsImagesAutomatically()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getLoadsImagesAutomatically();
    }
    return this.mWebSettings.getLoadsImagesAutomatically();
  }
  
  public boolean getMediaPlaybackRequiresUserGesture()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getMediaPlaybackRequiresUserGesture();
    }
    return this.mWebSettings.getMediaPlaybackRequiresUserGesture();
  }
  
  public int getMinimumFontSize()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getMinimumFontSize();
    }
    return this.mWebSettings.getMinimumFontSize();
  }
  
  public int getMinimumLogicalFontSize()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getMinimumLogicalFontSize();
    }
    return this.mWebSettings.getMinimumLogicalFontSize();
  }
  
  public int getMixedContentMode()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getMixedContentMode();
    }
    return this.mWebSettings.getMixedContentMode();
  }
  
  public boolean getNavDump()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getNavDump();
    }
    return this.mWebSettings.getNavDump();
  }
  
  public IX5WebSettings.PluginState getPluginState()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return IX5WebSettings.PluginState.valueOf(localTencentContentSettingsAdapter.getPluginState().name());
    }
    return IX5WebSettings.PluginState.valueOf(this.mWebSettings.getPluginState().name());
  }
  
  public boolean getPluginsEnabled()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getPluginsEnabled();
    }
    return this.mWebSettings.getPluginsEnabled();
  }
  
  public String getPluginsPath()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getPluginsPath();
    }
    return this.mWebSettings.getPluginsPath();
  }
  
  public boolean getSafeBrowsingEnabled()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getSafeBrowsingEnabled();
    }
    return this.mWebSettings.getSafeBrowsingEnabled();
  }
  
  public String getSansSerifFontFamily()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getSansSerifFontFamily();
    }
    return this.mWebSettings.getSansSerifFontFamily();
  }
  
  public boolean getSaveFormData()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getSaveFormData();
    }
    return this.mWebSettings.getSaveFormData();
  }
  
  public boolean getSavePassword()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getSavePassword();
    }
    return this.mWebSettings.getSavePassword();
  }
  
  public String getSerifFontFamily()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getSerifFontFamily();
    }
    return this.mWebSettings.getSerifFontFamily();
  }
  
  public String getStandardFontFamily()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getStandardFontFamily();
    }
    return this.mWebSettings.getStandardFontFamily();
  }
  
  public IX5WebSettings.TextSize getTextSize()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return IX5WebSettings.TextSize.valueOf(localTencentContentSettingsAdapter.getTextSize().name());
    }
    return IX5WebSettings.TextSize.valueOf(this.mWebSettings.getTextSize().name());
  }
  
  public int getTextZoom()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getTextZoom();
    }
    return this.mWebSettings.getTextZoom();
  }
  
  public boolean getUseWebViewBackgroundForOverscrollBackground()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getUseWebViewBackgroundForOverscrollBackground();
    }
    return this.mWebSettings.getUseWebViewBackgroundForOverscrollBackground();
  }
  
  public boolean getUseWideViewPort()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getUseWideViewPort();
    }
    return this.mWebSettings.getUseWideViewPort();
  }
  
  public String getUserAgent()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getUserAgentString();
    }
    return this.mWebSettings.getUserAgentString();
  }
  
  public String getUserAgentString()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.getUserAgentString();
    }
    return this.mWebSettings.getUserAgentString();
  }
  
  public void setAllowContentAccess(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAllowContentAccess(paramBoolean);
      return;
    }
    this.mWebSettings.setAllowContentAccess(paramBoolean);
  }
  
  public void setAllowFileAccess(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAllowFileAccess(paramBoolean);
      return;
    }
    this.mWebSettings.setAllowFileAccess(paramBoolean);
  }
  
  public void setAllowFileAccessFromFileURLs(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAllowFileAccessFromFileURLs(paramBoolean);
      return;
    }
    this.mWebSettings.setAllowFileAccessFromFileURLs(paramBoolean);
  }
  
  public void setAllowUniversalAccessFromFileURLs(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAllowUniversalAccessFromFileURLs(paramBoolean);
      return;
    }
    this.mWebSettings.setAllowUniversalAccessFromFileURLs(paramBoolean);
  }
  
  public void setAppCacheEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAppCacheEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setAppCacheEnabled(paramBoolean);
  }
  
  public void setAppCacheMaxSize(long paramLong)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAppCacheMaxSize(paramLong);
      return;
    }
    this.mWebSettings.setAppCacheMaxSize(paramLong);
  }
  
  public void setAppCachePath(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setAppCachePath(paramString);
      return;
    }
    this.mWebSettings.setAppCachePath(paramString);
  }
  
  public void setBlockNetworkImage(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setBlockNetworkImage(paramBoolean);
      return;
    }
    this.mWebSettings.setBlockNetworkImage(paramBoolean);
  }
  
  public void setBlockNetworkLoads(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setBlockNetworkLoads(paramBoolean);
      return;
    }
    this.mWebSettings.setBlockNetworkLoads(paramBoolean);
  }
  
  public void setBuiltInZoomControls(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setBuiltInZoomControls(paramBoolean);
      return;
    }
    this.mWebSettings.setBuiltInZoomControls(paramBoolean);
  }
  
  public void setCacheMode(int paramInt)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setCacheMode(paramInt);
      return;
    }
    this.mWebSettings.setCacheMode(paramInt);
  }
  
  public void setCursiveFontFamily(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setCursiveFontFamily(paramString);
      return;
    }
    this.mWebSettings.setCursiveFontFamily(paramString);
  }
  
  public void setDatabaseEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDatabaseEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setDatabaseEnabled(paramBoolean);
  }
  
  public void setDatabasePath(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDatabasePath(paramString);
      return;
    }
    this.mWebSettings.setDatabasePath(paramString);
  }
  
  public void setDefaultDatabasePath(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      localTencentContentSettingsAdapter.setDefaultDatabasePath(paramBoolean);
    }
  }
  
  public void setDefaultFixedFontSize(int paramInt)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDefaultFixedFontSize(paramInt);
      return;
    }
    this.mWebSettings.setDefaultFixedFontSize(paramInt);
  }
  
  public void setDefaultFontSize(int paramInt)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDefaultFontSize(paramInt);
      return;
    }
    this.mWebSettings.setDefaultFontSize(paramInt);
  }
  
  public void setDefaultTextEncodingName(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDefaultTextEncodingName(paramString);
      return;
    }
    this.mWebSettings.setDefaultTextEncodingName(paramString);
  }
  
  public void setDefaultZoom(IX5WebSettings.ZoomDensity paramZoomDensity)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDefaultZoom(WebSettings.ZoomDensity.valueOf(paramZoomDensity.name()));
      return;
    }
    this.mWebSettings.setDefaultZoom(WebSettings.ZoomDensity.valueOf(paramZoomDensity.name()));
  }
  
  public void setDisplayZoomControls(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDisplayZoomControls(paramBoolean);
      return;
    }
    this.mWebSettings.setDisplayZoomControls(paramBoolean);
  }
  
  public void setDomStorageEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setDomStorageEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setDomStorageEnabled(paramBoolean);
  }
  
  public void setEnableSmoothTransition(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setEnableSmoothTransition(paramBoolean);
      return;
    }
    this.mWebSettings.setEnableSmoothTransition(paramBoolean);
  }
  
  public void setFantasyFontFamily(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setFantasyFontFamily(paramString);
      return;
    }
    this.mWebSettings.setFantasyFontFamily(paramString);
  }
  
  public void setFixedFontFamily(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setFixedFontFamily(paramString);
      return;
    }
    this.mWebSettings.setFixedFontFamily(paramString);
  }
  
  public void setGeolocationDatabasePath(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setGeolocationDatabasePath(paramString);
      return;
    }
    this.mWebSettings.setGeolocationDatabasePath(paramString);
  }
  
  public void setGeolocationEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setGeolocationEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setGeolocationEnabled(paramBoolean);
  }
  
  public void setJavaScriptCanOpenWindowsAutomatically(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setJavaScriptCanOpenWindowsAutomatically(paramBoolean);
      return;
    }
    this.mWebSettings.setJavaScriptCanOpenWindowsAutomatically(paramBoolean);
  }
  
  public void setJavaScriptEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setJavaScriptEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setJavaScriptEnabled(paramBoolean);
  }
  
  public void setLayoutAlgorithm(IX5WebSettings.LayoutAlgorithm paramLayoutAlgorithm)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.valueOf(paramLayoutAlgorithm.name()));
      return;
    }
    this.mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.valueOf(paramLayoutAlgorithm.name()));
  }
  
  public void setLightTouchEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setLightTouchEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setLightTouchEnabled(paramBoolean);
  }
  
  public void setLoadWithOverviewMode(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setLoadWithOverviewMode(paramBoolean);
      return;
    }
    this.mWebSettings.setLoadWithOverviewMode(paramBoolean);
  }
  
  public void setLoadsImagesAutomatically(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setLoadsImagesAutomatically(paramBoolean);
      return;
    }
    this.mWebSettings.setLoadsImagesAutomatically(paramBoolean);
  }
  
  public void setMediaPlaybackRequiresUserGesture(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setMediaPlaybackRequiresUserGesture(paramBoolean);
      return;
    }
    this.mWebSettings.setMediaPlaybackRequiresUserGesture(paramBoolean);
  }
  
  public void setMinimumFontSize(int paramInt)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setMinimumFontSize(paramInt);
      return;
    }
    this.mWebSettings.setMinimumFontSize(paramInt);
  }
  
  public void setMinimumLogicalFontSize(int paramInt)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setMinimumLogicalFontSize(paramInt);
      return;
    }
    this.mWebSettings.setMinimumLogicalFontSize(paramInt);
  }
  
  public void setNavDump(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setNavDump(paramBoolean);
      return;
    }
    this.mWebSettings.setNavDump(paramBoolean);
  }
  
  public void setNeedInitialFocus(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setNeedInitialFocus(paramBoolean);
      return;
    }
    this.mWebSettings.setNeedInitialFocus(paramBoolean);
  }
  
  public void setPluginEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      localTencentContentSettingsAdapter.setPluginEnabled(paramBoolean);
    }
  }
  
  public void setPluginState(IX5WebSettings.PluginState paramPluginState)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setPluginState(WebSettings.PluginState.valueOf(paramPluginState.name()));
      return;
    }
    this.mWebSettings.setPluginState(WebSettings.PluginState.valueOf(paramPluginState.name()));
  }
  
  public void setPluginsEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setPluginsEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setPluginsEnabled(paramBoolean);
  }
  
  public void setPluginsPath(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setPluginsPath(paramString);
      return;
    }
    this.mWebSettings.setPluginsPath(paramString);
  }
  
  public void setRenderPriority(IX5WebSettings.RenderPriority paramRenderPriority)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setRenderPriority(WebSettings.RenderPriority.valueOf(paramRenderPriority.name()));
      return;
    }
    this.mWebSettings.setRenderPriority(WebSettings.RenderPriority.valueOf(paramRenderPriority.name()));
  }
  
  public void setSafeBrowsingEnabled(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSafeBrowsingEnabled(paramBoolean);
      return;
    }
    this.mWebSettings.setSafeBrowsingEnabled(paramBoolean);
  }
  
  public void setSansSerifFontFamily(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSansSerifFontFamily(paramString);
      return;
    }
    this.mWebSettings.setSansSerifFontFamily(paramString);
  }
  
  public void setSaveFormData(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSaveFormData(paramBoolean);
      return;
    }
    this.mWebSettings.setSaveFormData(paramBoolean);
  }
  
  public void setSavePassword(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSavePassword(paramBoolean);
      return;
    }
    this.mWebSettings.setSavePassword(paramBoolean);
  }
  
  public void setSerifFontFamily(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSerifFontFamily(paramString);
      return;
    }
    this.mWebSettings.setSerifFontFamily(paramString);
  }
  
  public void setStandardFontFamily(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setStandardFontFamily(paramString);
      return;
    }
    this.mWebSettings.setStandardFontFamily(paramString);
  }
  
  public void setSupportMultipleWindows(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSupportMultipleWindows(paramBoolean);
      return;
    }
    this.mWebSettings.setSupportMultipleWindows(paramBoolean);
  }
  
  public void setSupportZoom(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setSupportZoom(paramBoolean);
      return;
    }
    this.mWebSettings.setSupportZoom(paramBoolean);
  }
  
  public void setTextSize(IX5WebSettings.TextSize paramTextSize)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setTextSize(WebSettings.TextSize.valueOf(paramTextSize.name()));
      return;
    }
    this.mWebSettings.setTextSize(WebSettings.TextSize.valueOf(paramTextSize.name()));
  }
  
  public void setTextZoom(int paramInt)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setTextZoom(paramInt);
      return;
    }
    this.mWebSettings.setTextZoom(paramInt);
  }
  
  public void setUseWebViewBackgroundForOverscrollBackground(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setUseWebViewBackgroundForOverscrollBackground(paramBoolean);
      return;
    }
    this.mWebSettings.setUseWebViewBackgroundForOverscrollBackground(paramBoolean);
  }
  
  public void setUseWideViewPort(boolean paramBoolean)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setUseWideViewPort(paramBoolean);
      return;
    }
    this.mWebSettings.setUseWideViewPort(paramBoolean);
  }
  
  public void setUserAgent(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("setUserAgent=");
    ((StringBuilder)localObject).append(paramString);
    X5Log.i("USERAGENT", ((StringBuilder)localObject).toString());
    localObject = this.mTencentWebSettings;
    if (localObject != null)
    {
      ((TencentContentSettingsAdapter)localObject).setUserAgent(paramString);
      return;
    }
    this.mWebSettings.setUserAgentString(paramString);
  }
  
  public void setUserAgent(String paramString, boolean paramBoolean)
  {
    this.mTencentWebSettings.setUserAgent(paramString, paramBoolean);
  }
  
  public void setUserAgentString(String paramString)
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null)
    {
      localTencentContentSettingsAdapter.setUserAgentString(paramString);
      return;
    }
    this.mWebSettings.setUserAgentString(paramString);
  }
  
  public boolean supportMultipleWindows()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.supportMultipleWindows();
    }
    return this.mWebSettings.supportMultipleWindows();
  }
  
  public boolean supportZoom()
  {
    TencentContentSettingsAdapter localTencentContentSettingsAdapter = this.mTencentWebSettings;
    if (localTencentContentSettingsAdapter != null) {
      return localTencentContentSettingsAdapter.supportZoom();
    }
    return this.mWebSettings.supportZoom();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebSettingsProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */