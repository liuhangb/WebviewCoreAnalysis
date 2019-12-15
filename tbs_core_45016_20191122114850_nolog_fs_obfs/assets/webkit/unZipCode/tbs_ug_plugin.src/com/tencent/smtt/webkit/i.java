package com.tencent.smtt.webkit;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webview.chromium.tencent.TencentContentSettingsAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import com.tencent.smtt.listbox.a;
import com.tencent.tbs.core.webkit.WebSettings.PluginState;
import com.tencent.tbs.core.webkit.WebSettings.RenderPriority;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class i
{
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  ImageButton jdField_a_of_type_AndroidWidgetImageButton;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  
  public i(TencentWebViewProxy paramTencentWebViewProxy, boolean paramBoolean)
  {
    Object localObject = null;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
    this.jdField_a_of_type_AndroidViewViewGroup = null;
    this.jdField_a_of_type_AndroidWidgetImageButton = null;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    if (this.jdField_a_of_type_Boolean)
    {
      paramTencentWebViewProxy = (TencentWebViewProxy)localObject;
      if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext() instanceof Activity)) {
        paramTencentWebViewProxy = ((Activity)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()).getWindow();
      }
      if ((paramTencentWebViewProxy != null) && (((Activity)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()).hasWindowFocus()))
      {
        this.jdField_a_of_type_AndroidViewViewGroup = ((ViewGroup)paramTencentWebViewProxy.getDecorView());
        this.b = true;
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().setUseSurfaceView(true);
        paramTencentWebViewProxy = (TencentContentSettingsAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getSettings();
        paramTencentWebViewProxy.setJavaScriptEnabled(true);
        paramTencentWebViewProxy.setAllowFileAccess(true);
        paramTencentWebViewProxy.setSupportZoom(true);
        paramTencentWebViewProxy.setBuiltInZoomControls(true);
        paramTencentWebViewProxy.setUseWideViewPort(true);
        paramTencentWebViewProxy.setSupportMultipleWindows(false);
        paramTencentWebViewProxy.setLoadWithOverviewMode(true);
        paramTencentWebViewProxy.setAppCacheEnabled(true);
        paramTencentWebViewProxy.setDatabaseEnabled(true);
        paramTencentWebViewProxy.setDomStorageEnabled(true);
        paramTencentWebViewProxy.setGeolocationEnabled(true);
        paramTencentWebViewProxy.setAppCacheMaxSize(Long.MAX_VALUE);
        paramTencentWebViewProxy.setPluginState(WebSettings.PluginState.ON_DEMAND);
        paramTencentWebViewProxy.setRenderPriority(WebSettings.RenderPriority.HIGH);
        paramTencentWebViewProxy.setDisplayZoomControls(false);
        paramTencentWebViewProxy.setMediaPlaybackRequiresUserGesture(false);
        paramTencentWebViewProxy.setEnableSupportedHardwareAcceleratedFeatures(true);
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().setARModeEnable(false);
      }
    }
  }
  
  public void a()
  {
    if (this.jdField_a_of_type_AndroidWidgetImageButton != null)
    {
      this.jdField_a_of_type_AndroidViewViewGroup.removeView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView());
      this.jdField_a_of_type_AndroidViewViewGroup.removeView(this.jdField_a_of_type_AndroidWidgetImageButton);
      this.jdField_a_of_type_AndroidWidgetImageButton = null;
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
      this.jdField_a_of_type_AndroidViewViewGroup = null;
      this.jdField_a_of_type_Boolean = false;
    }
  }
  
  public void a(int paramInt)
  {
    if (this.jdField_a_of_type_Boolean)
    {
      if (!this.b) {
        return;
      }
      Object localObject = new FrameLayout.LayoutParams(-1, -1);
      Window localWindow = ((Activity)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()).getWindow();
      localWindow.setFlags(8455424, 8455424);
      localWindow.setSoftInputMode(288);
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setFocusableInTouchMode(true);
      this.jdField_a_of_type_AndroidViewViewGroup.addView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView(), (ViewGroup.LayoutParams)localObject);
      this.jdField_a_of_type_AndroidWidgetImageButton = new ImageButton(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
      localObject = a.a(SmttResource.getResourceContext(), "x5_video_back");
      this.jdField_a_of_type_AndroidWidgetImageButton.setImageDrawable((Drawable)localObject);
      this.jdField_a_of_type_AndroidWidgetImageButton.setBackgroundColor(0);
      this.jdField_a_of_type_AndroidWidgetImageButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          i.a(i.this).getRealWebView().post(new Runnable()
          {
            public void run()
            {
              i.a(i.this).destroy();
            }
          });
        }
      });
      this.jdField_a_of_type_AndroidViewViewGroup.addView(this.jdField_a_of_type_AndroidWidgetImageButton, new FrameLayout.LayoutParams(-2, -2, 51));
      localObject = (TencentContentSettingsAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getSettings();
      ((TencentContentSettingsAdapter)localObject).setJavaScriptEnabled(true);
      ((TencentContentSettingsAdapter)localObject).setAllowFileAccess(true);
      ((TencentContentSettingsAdapter)localObject).setSupportZoom(true);
      ((TencentContentSettingsAdapter)localObject).setBuiltInZoomControls(true);
      ((TencentContentSettingsAdapter)localObject).setUseWideViewPort(true);
      ((TencentContentSettingsAdapter)localObject).setSupportMultipleWindows(false);
      ((TencentContentSettingsAdapter)localObject).setLoadWithOverviewMode(true);
      ((TencentContentSettingsAdapter)localObject).setAppCacheEnabled(true);
      ((TencentContentSettingsAdapter)localObject).setDatabaseEnabled(true);
      ((TencentContentSettingsAdapter)localObject).setDomStorageEnabled(true);
      ((TencentContentSettingsAdapter)localObject).setGeolocationEnabled(true);
      ((TencentContentSettingsAdapter)localObject).setAppCacheMaxSize(Long.MAX_VALUE);
      ((TencentContentSettingsAdapter)localObject).setPluginState(WebSettings.PluginState.ON_DEMAND);
      ((TencentContentSettingsAdapter)localObject).setRenderPriority(WebSettings.RenderPriority.HIGH);
      ((TencentContentSettingsAdapter)localObject).setDisplayZoomControls(false);
      ((TencentContentSettingsAdapter)localObject).setMediaPlaybackRequiresUserGesture(false);
      ((TencentContentSettingsAdapter)localObject).setEnableSupportedHardwareAcceleratedFeatures(true);
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().setARModeEnable(false);
      return;
    }
  }
  
  public boolean a()
  {
    return (this.jdField_a_of_type_Boolean) && (this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */