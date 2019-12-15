package com.tencent.tbs.tbsshell.partner.reader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.mtt.external.reader.export.IReaderWebViewClient;
import com.tencent.mtt.external.reader.export.IReaderWebViewProxy;
import com.tencent.mtt.video.export.IVideoPlayerHelper;
import com.tencent.mtt.video.export.IX5VideoPlayer;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.extension.proxy.X5ProxyWebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.FindListener;
import com.tencent.smtt.export.external.proxy.ProxyWebViewClient;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.export.internal.interfaces.SecurityLevelBase;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.tbsshell.WebCoreProxy;

public class TbsReaderWebViewProxy
  implements IReaderWebViewProxy
{
  private static final int TITLE_BAR_HEIGHT = 200;
  GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener()
  {
    public boolean onSingleTapConfirmed(MotionEvent paramAnonymousMotionEvent)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onSingleTapConfirmed ...");
      localStringBuilder.append(paramAnonymousMotionEvent);
      LogUtils.d("TbsReaderWebViewProxy", localStringBuilder.toString());
      if (TbsReaderWebViewProxy.this.mReaderWebViewClient != null) {
        TbsReaderWebViewProxy.this.mReaderWebViewClient.onSingleTaped();
      }
      return true;
    }
  };
  private Context mContext;
  GestureDetector mGestureDetector = new GestureDetector(this.gestureListener);
  private Drawable mLogoDrawable = null;
  private TbsReaderEventProxy mReaderEventProxy;
  private IReaderWebViewClient mReaderWebViewClient;
  private int mVisiableTitlebarHeight = 200;
  private IX5WebView mX5WebView;
  
  public TbsReaderWebViewProxy(Context paramContext, TbsReaderEventProxy paramTbsReaderEventProxy)
  {
    this.mContext = paramContext;
    this.mReaderEventProxy = paramTbsReaderEventProxy;
  }
  
  private void initWebViewClient()
  {
    this.mX5WebView.setWebViewClient(new ProxyWebViewClient()
    {
      public void onPageFinished(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString)
      {
        LogUtils.d("TbsReaderWebViewProxy", "onPageFinished ...");
        if (TbsReaderWebViewProxy.this.mReaderWebViewClient != null) {
          TbsReaderWebViewProxy.this.mReaderWebViewClient.onPageFinished(paramAnonymousString);
        }
        super.onPageFinished(paramAnonymousIX5WebViewBase, paramAnonymousString);
      }
      
      public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString)
      {
        LogUtils.d("TbsReaderWebViewProxy", "shouldInterceptRequest ...");
        if (TbsReaderWebViewProxy.this.mReaderWebViewClient != null)
        {
          android.webkit.WebResourceResponse localWebResourceResponse = TbsReaderWebViewProxy.this.mReaderWebViewClient.shouldInterceptRequest(paramAnonymousString);
          if (localWebResourceResponse != null) {
            return new com.tencent.smtt.export.external.interfaces.WebResourceResponse(localWebResourceResponse.getMimeType(), localWebResourceResponse.getEncoding(), localWebResourceResponse.getData());
          }
          return super.shouldInterceptRequest(paramAnonymousIX5WebViewBase, paramAnonymousString);
        }
        return super.shouldInterceptRequest(paramAnonymousIX5WebViewBase, paramAnonymousString);
      }
      
      public boolean shouldOverrideUrlLoading(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString)
      {
        LogUtils.d("TbsReaderWebViewProxy", "shouldOverrideUrlLoading ...");
        if (TbsReaderWebViewProxy.this.mReaderWebViewClient != null) {
          TbsReaderWebViewProxy.this.mReaderWebViewClient.shouldOverrideUrlLoading(paramAnonymousString);
        }
        return super.shouldOverrideUrlLoading(paramAnonymousIX5WebViewBase, paramAnonymousString);
      }
    });
    Object localObject = new Bundle();
    ((Bundle)localObject).putBoolean("flag", true);
    this.mX5WebView.getX5WebViewExtension().invokeMiscMethod("setWebViewCallbackClientFlag", (Bundle)localObject);
    localObject = new X5ProxyWebViewClientExtension(WebCoreProxy.createDefaultX5WebChromeClientExtension())
    {
      public static final int READER_CHANNEL_SEARCH_ID = 10612;
      
      public void computeScroll(View paramAnonymousView)
      {
        try
        {
          ReflectionUtils.invokeInstance(paramAnonymousView, "super_computeScroll");
          return;
        }
        catch (Throwable paramAnonymousView)
        {
          paramAnonymousView.printStackTrace();
        }
      }
      
      public boolean dispatchTouchEvent(MotionEvent paramAnonymousMotionEvent, View paramAnonymousView)
      {
        TbsReaderWebViewProxy.this.mGestureDetector.onTouchEvent(paramAnonymousMotionEvent);
        try
        {
          paramAnonymousMotionEvent = ReflectionUtils.invokeInstance(paramAnonymousView, "super_dispatchTouchEvent", new Class[] { MotionEvent.class }, new Object[] { paramAnonymousMotionEvent });
          if (paramAnonymousMotionEvent == null) {
            return false;
          }
          boolean bool = ((Boolean)paramAnonymousMotionEvent).booleanValue();
          return bool;
        }
        catch (Throwable paramAnonymousMotionEvent) {}
        return false;
      }
      
      public void invalidate() {}
      
      public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent, View paramAnonymousView)
      {
        try
        {
          paramAnonymousMotionEvent = ReflectionUtils.invokeInstance(paramAnonymousView, "super_onInterceptTouchEvent", new Class[] { MotionEvent.class }, new Object[] { paramAnonymousMotionEvent });
          if (paramAnonymousMotionEvent == null) {
            return false;
          }
          boolean bool = ((Boolean)paramAnonymousMotionEvent).booleanValue();
          return bool;
        }
        catch (Throwable paramAnonymousMotionEvent) {}
        return false;
      }
      
      public Object onMiscCallBack(String paramAnonymousString, Bundle paramAnonymousBundle)
      {
        if ((paramAnonymousString.equalsIgnoreCase("openBrowserList")) && (TbsReaderWebViewProxy.this.mReaderEventProxy != null))
        {
          paramAnonymousBundle = TBSResources.getString("reader_search_intall_browser");
          if (!TextUtils.isEmpty(paramAnonymousBundle))
          {
            paramAnonymousString = new Bundle();
            paramAnonymousString.putString("tip", paramAnonymousBundle);
            paramAnonymousString.putInt("channel_id", 10612);
          }
          else
          {
            paramAnonymousString = null;
          }
          TbsReaderWebViewProxy.this.mReaderEventProxy.doCallBackEvent(5011, paramAnonymousString, null);
          return null;
        }
        return super.onMiscCallBack(paramAnonymousString, paramAnonymousBundle);
      }
      
      public void onOverScrolled(int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, View paramAnonymousView)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onOverScrolled ...");
        localStringBuilder.append(paramAnonymousView);
        LogUtils.d("TbsReaderWebViewProxy", localStringBuilder.toString());
        try
        {
          ReflectionUtils.invokeInstance(paramAnonymousView, "super_onOverScrolled", new Class[] { Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE }, new Object[] { Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Boolean.valueOf(paramAnonymousBoolean1), Boolean.valueOf(paramAnonymousBoolean2) });
          return;
        }
        catch (Throwable paramAnonymousView)
        {
          paramAnonymousView.printStackTrace();
        }
      }
      
      public void onScrollChanged(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, View paramAnonymousView)
      {
        if (TbsReaderWebViewProxy.this.mReaderWebViewClient != null) {
          TbsReaderWebViewProxy.this.mReaderWebViewClient.onScrollChanged(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousView);
        }
        try
        {
          ReflectionUtils.invokeInstance(paramAnonymousView, "super_onScrollChanged", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE }, new Object[] { Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Integer.valueOf(paramAnonymousInt3), Integer.valueOf(paramAnonymousInt4) });
          return;
        }
        catch (Throwable paramAnonymousView)
        {
          paramAnonymousView.printStackTrace();
        }
      }
      
      public boolean onTouchEvent(MotionEvent paramAnonymousMotionEvent, View paramAnonymousView)
      {
        try
        {
          paramAnonymousMotionEvent = ReflectionUtils.invokeInstance(paramAnonymousView, "super_onTouchEvent", new Class[] { MotionEvent.class }, new Object[] { paramAnonymousMotionEvent });
          if (paramAnonymousMotionEvent == null) {
            return false;
          }
          boolean bool = ((Boolean)paramAnonymousMotionEvent).booleanValue();
          return bool;
        }
        catch (Throwable paramAnonymousMotionEvent) {}
        return false;
      }
      
      public boolean overScrollBy(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8, boolean paramAnonymousBoolean, View paramAnonymousView)
      {
        if ((TbsReaderWebViewProxy.this.mReaderWebViewClient != null) && (TbsReaderWebViewProxy.this.mReaderWebViewClient.overScrollBy(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5, paramAnonymousInt6, paramAnonymousInt7, paramAnonymousInt8, paramAnonymousBoolean))) {}
        try
        {
          paramAnonymousView = ReflectionUtils.invokeInstance(paramAnonymousView, "super_overScrollBy", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE }, new Object[] { Integer.valueOf(paramAnonymousInt1), Integer.valueOf(0), Integer.valueOf(paramAnonymousInt3), Integer.valueOf(paramAnonymousInt6), Integer.valueOf(paramAnonymousInt5), Integer.valueOf(paramAnonymousInt6), Integer.valueOf(paramAnonymousInt7), Integer.valueOf(paramAnonymousInt8), Boolean.valueOf(paramAnonymousBoolean) });
          if (paramAnonymousView == null) {
            return false;
          }
          paramAnonymousBoolean = ((Boolean)paramAnonymousView).booleanValue();
          return paramAnonymousBoolean;
        }
        catch (Throwable paramAnonymousView)
        {
          try
          {
            paramAnonymousView = ReflectionUtils.invokeInstance(paramAnonymousView, "super_overScrollBy", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE }, new Object[] { Integer.valueOf(paramAnonymousInt1), Integer.valueOf(paramAnonymousInt2), Integer.valueOf(paramAnonymousInt3), Integer.valueOf(paramAnonymousInt4), Integer.valueOf(paramAnonymousInt5), Integer.valueOf(paramAnonymousInt6), Integer.valueOf(paramAnonymousInt7), Integer.valueOf(paramAnonymousInt8), Boolean.valueOf(paramAnonymousBoolean) });
            if (paramAnonymousView == null) {
              return false;
            }
            paramAnonymousBoolean = ((Boolean)paramAnonymousView).booleanValue();
            return paramAnonymousBoolean;
          }
          catch (Throwable paramAnonymousView) {}
          paramAnonymousView = paramAnonymousView;
          return false;
        }
        return false;
      }
    };
    this.mX5WebView.setWebViewClientExtension((IX5WebViewClientExtension)localObject);
    this.mX5WebView.setQQBrowserClient(new IX5QQBrowserClient()
    {
      public void checkSecurityLevel(String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt) {}
      
      public void fingerSearchRequest(String paramAnonymousString1, int paramAnonymousInt1, String paramAnonymousString2, int paramAnonymousInt2, int[] paramAnonymousArrayOfInt, String paramAnonymousString3, String paramAnonymousString4) {}
      
      public int getTitleHeight()
      {
        LogUtils.d("allenhan", "getTitleHeight: 200");
        Bundle localBundle = new Bundle();
        if (TbsReaderWebViewProxy.this.mReaderEventProxy != null) {
          TbsReaderWebViewProxy.this.mReaderEventProxy.doCallBackEvent(5045, null, localBundle);
        }
        return localBundle.getInt("TitleHeight", 0);
      }
      
      public IVideoPlayerHelper getVideoPlayerHelper()
      {
        return null;
      }
      
      public int getVisbleTitleHeight()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getVisbleTitleHeight: ");
        ((StringBuilder)localObject).append(TbsReaderWebViewProxy.this.mVisiableTitlebarHeight);
        LogUtils.d("allenhan", ((StringBuilder)localObject).toString());
        localObject = new Bundle();
        if (TbsReaderWebViewProxy.this.mReaderEventProxy != null) {
          TbsReaderWebViewProxy.this.mReaderEventProxy.doCallBackEvent(5047, null, localObject);
        }
        return ((Bundle)localObject).getInt("VisbleTitleHeigh", 0);
      }
      
      public void onReportMainresourceInDirectMode(String paramAnonymousString) {}
      
      public void onVisbleTitleHeightChanged(int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onVisbleTitleHeightChanged: ");
        localStringBuilder.append(paramAnonymousInt);
        LogUtils.d("allenhan", localStringBuilder.toString());
        if (TbsReaderWebViewProxy.this.mReaderEventProxy != null) {
          TbsReaderWebViewProxy.this.mReaderEventProxy.doCallBackEvent(5046, new Integer(paramAnonymousInt), null);
        }
      }
      
      public void setSecurityLevel(SecurityLevelBase paramAnonymousSecurityLevelBase) {}
      
      public boolean shouldOverrideStandardPlay(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, boolean paramAnonymousBoolean3, IX5VideoPlayer paramAnonymousIX5VideoPlayer)
      {
        return false;
      }
    });
    this.mX5WebView.setAddressbarDisplayMode(5, true);
  }
  
  public void activityPause()
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      localIX5WebView.onPause();
    }
  }
  
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      localIX5WebView.addJavascriptInterface(paramObject, paramString);
    }
  }
  
  public boolean canGoBack()
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      return localIX5WebView.canGoBack();
    }
    return false;
  }
  
  public void clearMatches()
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      localIX5WebView.clearMatches();
    }
  }
  
  public void creatWebView()
  {
    if (this.mX5WebView == null)
    {
      this.mX5WebView = ((IX5WebView)WebCoreProxy.createSDKWebview(this.mContext));
      Object localObject = this.mX5WebView;
      if (localObject != null)
      {
        localObject = ((IX5WebView)localObject).getSettings();
        ((IX5WebSettings)localObject).setAllowFileAccess(true);
        ((IX5WebSettings)localObject).setJavaScriptEnabled(true);
        ((IX5WebSettings)localObject).setSupportZoom(true);
        ((IX5WebSettings)localObject).setBuiltInZoomControls(true);
        ((IX5WebSettings)localObject).setDisplayZoomControls(false);
        ((IX5WebSettings)localObject).setLoadsImagesAutomatically(true);
        ((IX5WebSettings)localObject).setUseWideViewPort(false);
        this.mX5WebView.active();
        initWebViewClient();
        if (this.mLogoDrawable != null)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("setLogo: ");
          ((StringBuilder)localObject).append(this.mLogoDrawable);
          LogUtils.d("allenhan", ((StringBuilder)localObject).toString());
          this.mX5WebView.setOverScrollParams(2, 0, 0, 0, 715827882, 0, null, this.mLogoDrawable, null);
        }
        localObject = this.mReaderWebViewClient;
        if (localObject != null) {
          ((IReaderWebViewClient)localObject).onWebviewCreated();
        }
      }
    }
  }
  
  public void destroy()
  {
    this.mReaderEventProxy = null;
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null)
    {
      try
      {
        localIX5WebView.clearCache(true);
      }
      catch (Throwable localThrowable1)
      {
        localThrowable1.printStackTrace();
      }
      try
      {
        this.mX5WebView.deactive();
      }
      catch (Throwable localThrowable2)
      {
        localThrowable2.printStackTrace();
      }
      try
      {
        this.mX5WebView.destroy();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
    this.mContext = null;
  }
  
  public void doSearch(Context paramContext, String paramString)
  {
    creatWebView();
    WebCoreProxy.doSearch(paramContext, paramString, this.mX5WebView, "4");
  }
  
  public void findAllAsync(String paramString)
  {
    if (this.mX5WebView != null)
    {
      IX5WebViewBase.FindListener local5 = new IX5WebViewBase.FindListener()
      {
        public void onFindResultReceived(int paramAnonymousInt1, int paramAnonymousInt2, boolean paramAnonymousBoolean)
        {
          if (TbsReaderWebViewProxy.this.mReaderEventProxy != null)
          {
            Bundle localBundle = new Bundle();
            localBundle.putInt("curIdx", paramAnonymousInt1);
            localBundle.putInt("total", paramAnonymousInt2);
            localBundle.putBoolean("isSearchFinished", paramAnonymousBoolean);
            TbsReaderWebViewProxy.this.mReaderEventProxy.doCallBackEvent(5042, localBundle, null);
          }
        }
      };
      this.mX5WebView.setFindListener(local5);
      this.mX5WebView.findAllAsync(paramString);
    }
  }
  
  public void findNext(boolean paramBoolean)
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      localIX5WebView.findNext(paramBoolean);
    }
  }
  
  public void fingerSearchRequest(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4) {}
  
  public void fling(int paramInt1, int paramInt2)
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      localIX5WebView.flingScroll(paramInt1, paramInt2);
    }
  }
  
  public boolean getFitScreen()
  {
    LogUtils.d("TbsReaderWebViewProxy", "getFitScreen");
    IX5WebSettingsExtension localIX5WebSettingsExtension = this.mX5WebView.getX5WebViewExtension().getSettingsExtension();
    if (localIX5WebSettingsExtension != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getFitScreen");
      localStringBuilder.append(localIX5WebSettingsExtension.isFitScreen());
      LogUtils.d("TbsReaderWebViewProxy", localStringBuilder.toString());
      return localIX5WebSettingsExtension.isFitScreen();
    }
    return false;
  }
  
  public IX5WebSettingsExtension getSettingsExtension()
  {
    return this.mX5WebView.getX5WebViewExtension().getSettingsExtension();
  }
  
  public float getVelocity()
  {
    return ((Float)this.mX5WebView.invokeMiscMethod("getCurrScrollVelocity", new Bundle())).floatValue();
  }
  
  public View getView()
  {
    LogUtils.d("TbsReaderWebViewProxy", "getView ...");
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      return localIX5WebView.getView();
    }
    return null;
  }
  
  public void goBack()
  {
    IX5WebView localIX5WebView = this.mX5WebView;
    if (localIX5WebView != null) {
      localIX5WebView.goBack();
    }
  }
  
  public void loadUrl(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("loadUrl:");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("TbsReaderWebViewProxy", ((StringBuilder)localObject).toString());
    localObject = this.mX5WebView;
    if (localObject != null) {
      ((IX5WebView)localObject).loadUrl(paramString);
    }
  }
  
  public void setFitScreen(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("setFitScreen:");
    ((StringBuilder)localObject).append(paramBoolean);
    LogUtils.d("TbsReaderWebViewProxy", ((StringBuilder)localObject).toString());
    localObject = this.mX5WebView.getX5WebViewExtension().getSettingsExtension();
    if (localObject != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setFitScreen:");
      localStringBuilder.append(paramBoolean);
      LogUtils.d("TbsReaderWebViewProxy", localStringBuilder.toString());
      ((IX5WebSettingsExtension)localObject).setFitScreen(paramBoolean);
    }
  }
  
  public void setLogo(Drawable paramDrawable)
  {
    this.mLogoDrawable = paramDrawable;
  }
  
  public void setReaderWebViewClient(IReaderWebViewClient paramIReaderWebViewClient)
  {
    this.mReaderWebViewClient = paramIReaderWebViewClient;
  }
  
  public void setWebViewClientExtension(IX5WebViewClientExtension paramIX5WebViewClientExtension)
  {
    this.mX5WebView.getX5WebViewExtension().setWebViewClientExtension(paramIX5WebViewClientExtension);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\TbsReaderWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */