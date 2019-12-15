package com.tencent.smtt.webkit.h5video;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.MediaPlayer.TrackInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewProxy;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.IVideoPlayerHelper;
import com.tencent.mtt.video.export.IX5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.mtt.video.export.VideoProxyDefault.NetworkState;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.internal.interfaces.IX5QQBrowserClient;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebSettingsExtension.a;
import com.tencent.smtt.webkit.s;
import com.tencent.smtt.webkit.s.a;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.http.HttpHost;
import org.chromium.tencent.X5NativeBitmap;
import org.chromium.tencent.X5NativeBitmap.Factory;
import org.chromium.tencent.media.MediaSourceBridge;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.IMediaPlayer.IMediaPlayerListener;

public class c
  extends VideoProxyDefault
  implements IMediaPlayer
{
  private static List<WeakReference<c>> jdField_a_of_type_JavaUtilList = new LinkedList();
  private int jdField_a_of_type_Int = -1;
  private long jdField_a_of_type_Long;
  Handler jdField_a_of_type_AndroidOsHandler;
  private IX5VideoPlayer jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
  private PlayerEnv jdField_a_of_type_ComTencentMttVideoExportPlayerEnv;
  private FakeFullScreenPanel jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
  private a jdField_a_of_type_ComTencentSmttWebkitH5videoC$a;
  private final e jdField_a_of_type_ComTencentSmttWebkitH5videoE;
  private s.a jdField_a_of_type_ComTencentSmttWebkitS$a = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  protected String a;
  private MediaSourceBridge jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
  IMediaPlayer.IMediaPlayerListener jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int = 63536;
  private long jdField_b_of_type_Long;
  private s.a jdField_b_of_type_ComTencentSmttWebkitS$a;
  private Runnable jdField_b_of_type_JavaLangRunnable;
  private String jdField_b_of_type_JavaLangString = null;
  private boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int = 63536;
  private String jdField_c_of_type_JavaLangString;
  private boolean jdField_c_of_type_Boolean;
  private int jdField_d_of_type_Int = 300;
  private String jdField_d_of_type_JavaLangString;
  private boolean jdField_d_of_type_Boolean;
  private int jdField_e_of_type_Int = 150;
  private boolean jdField_e_of_type_Boolean;
  private int jdField_f_of_type_Int;
  private boolean jdField_f_of_type_Boolean;
  private int jdField_g_of_type_Int;
  private boolean jdField_g_of_type_Boolean;
  private int jdField_h_of_type_Int;
  private boolean jdField_h_of_type_Boolean;
  private int jdField_i_of_type_Int;
  private boolean jdField_i_of_type_Boolean;
  private int jdField_j_of_type_Int;
  private boolean jdField_j_of_type_Boolean;
  private int jdField_k_of_type_Int;
  private boolean jdField_k_of_type_Boolean;
  private int jdField_l_of_type_Int;
  private boolean jdField_l_of_type_Boolean;
  private boolean m;
  private boolean n;
  private boolean o;
  private boolean p;
  private boolean q;
  
  public c(TencentWebViewProxy paramTencentWebViewProxy, MediaSourceBridge paramMediaSourceBridge)
  {
    boolean bool2 = false;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_c_of_type_Boolean = false;
    this.jdField_f_of_type_Int = 0;
    this.jdField_g_of_type_Int = 3;
    this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener = null;
    this.jdField_k_of_type_Int = 0;
    this.jdField_a_of_type_Long = 0L;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_f_of_type_Boolean = false;
    this.jdField_g_of_type_Boolean = false;
    this.jdField_h_of_type_Boolean = false;
    this.jdField_l_of_type_Int = -1;
    this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper());
    this.jdField_a_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        c.this.a().switchScreen(101);
        c.this.dispatchPause(3);
      }
    };
    this.o = true;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge = paramMediaSourceBridge;
    paramTencentWebViewProxy = invokeWebViewClientMiscCallBackMethod("isForceFullscreenWhenFakeFullscreen", null);
    if ((paramTencentWebViewProxy instanceof Boolean)) {
      this.jdField_a_of_type_Boolean = ((Boolean)paramTencentWebViewProxy).booleanValue();
    }
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoE = new e(this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge.getMSEMediaPlayerBrige());
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null) {
      i();
    }
    shouldOverrideStandardPlay(false);
    a(this);
    if (Build.VERSION.SDK_INT <= 10)
    {
      paramTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView();
      int i1 = 0;
      while ((paramTencentWebViewProxy.getParent() instanceof View))
      {
        i1 += 1;
        paramTencentWebViewProxy = (View)paramTencentWebViewProxy.getParent();
      }
      if (i1 > 10) {
        this.jdField_h_of_type_Boolean = true;
      }
    }
    boolean bool1 = bool2;
    if (SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 215))
    {
      paramTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
      bool1 = bool2;
      if (paramTencentWebViewProxy != null)
      {
        bool1 = bool2;
        if (paramTencentWebViewProxy.getSettingsExtension() != null) {
          bool1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isForceVideoSameLayer();
        }
      }
    }
    this.q = bool1;
  }
  
  private int a()
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    int i1 = 0;
    if (localObject != null)
    {
      if (((TencentWebViewProxy)localObject).getSettings() == null) {
        return 0;
      }
      localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgent();
      if (localObject == null) {
        return 0;
      }
      if ((((String)localObject).length() != 0) && (!((String)localObject).equals(" ")))
      {
        if (((String)localObject).equalsIgnoreCase("MQQBrowser")) {
          return 200;
        }
        if (((String)localObject).contains("iPhone")) {
          return 201;
        }
        if (((String)localObject).contains("iPad")) {
          return 202;
        }
        if (((String)localObject).contains("Chrome")) {
          return 203;
        }
      }
      else
      {
        i1 = 204;
      }
      return i1;
    }
    return 0;
  }
  
  private int a(int paramInt1, int paramInt2)
  {
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    int i2 = 101;
    int i1 = i2;
    if (localObject != null)
    {
      localObject = ((s.a)localObject).a();
      i1 = i2;
      if (localObject != null)
      {
        i1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewY(this.jdField_c_of_type_Int);
        paramInt1 = i1;
        if (this.jdField_k_of_type_Int == 1) {
          paramInt1 = i1 + this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getScrollY();
        }
        if (paramInt1 >= paramInt2)
        {
          i1 = i2;
          if (paramInt1 + ((View)localObject).getHeight() / 2 <= paramInt2 + this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight()) {}
        }
        else
        {
          i1 = 106;
        }
      }
    }
    return i1;
  }
  
  private Bitmap a()
  {
    int i1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWidth();
    int i2 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight();
    Object localObject = null;
    try
    {
      Bitmap localBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(i1, i2, Bitmap.Config.RGB_565);
      localObject = localBitmap;
      Canvas localCanvas = new Canvas(localBitmap);
      localObject = localBitmap;
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.draw(localCanvas);
      return localBitmap;
    }
    catch (Throwable localThrowable) {}
    return (Bitmap)localObject;
  }
  
  private Object a(String paramString, Bundle paramBundle, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension() != null) {
      return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension().onMiscCallBack(paramString, paramBundle, paramObject1, paramObject2, paramObject3, paramObject4);
    }
    return null;
  }
  
  private void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localTencentWebViewProxy != null) && (this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge != null)) {
      this.jdField_a_of_type_ComTencentSmttWebkitS$a = localTencentWebViewProxy.addSurfaceOnUIThread(paramView, paramInt1, paramInt2, paramInt3, paramInt4, this.jdField_k_of_type_Int);
    }
  }
  
  static void a(c paramc)
  {
    try
    {
      Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = ((WeakReference)localIterator.next()).get();
        if (localObject == paramc) {
          return;
        }
      }
      jdField_a_of_type_JavaUtilList.add(new WeakReference(paramc));
      return;
    }
    finally {}
  }
  
  private void a(int[] paramArrayOfInt)
  {
    int i3 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWidth();
    int i2 = (int)(i3 * (this.jdField_e_of_type_Int / this.jdField_d_of_type_Int));
    DisplayMetrics localDisplayMetrics = getContext().getResources().getDisplayMetrics();
    int i1 = i2;
    if (i2 > localDisplayMetrics.heightPixels / 2) {
      i1 = localDisplayMetrics.heightPixels / 2;
    }
    paramArrayOfInt[0] = i3;
    paramArrayOfInt[1] = i1;
  }
  
  private boolean a()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localTencentWebViewProxy != null) && (localTencentWebViewProxy.getSettingsExtension() != null)) {
      return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isStandardFullScreen();
    }
    return false;
  }
  
  private boolean a(int paramInt)
  {
    return (paramInt == 102) || (paramInt == 104) || (paramInt == 105);
  }
  
  private boolean a(int paramInt1, int paramInt2)
  {
    if (paramInt2 > 1)
    {
      if (paramInt1 <= 1) {
        return false;
      }
      return paramInt2 / paramInt1 > 1.0D;
    }
    return false;
  }
  
  private int b()
  {
    if (a().getScreenMode() == 107) {
      return 102;
    }
    if ((!SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 199)) && (!canPagePlay())) {
      return 104;
    }
    if (this.jdField_e_of_type_Boolean)
    {
      if (a(this.jdField_i_of_type_Int, this.jdField_j_of_type_Int)) {
        return 104;
      }
    }
    else if (a(this.jdField_d_of_type_Int, this.jdField_e_of_type_Int)) {
      return 104;
    }
    return 102;
  }
  
  static void b(c paramc)
  {
    int i1 = 0;
    for (;;)
    {
      try
      {
        Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
        if (localIterator.hasNext()) {
          if (((WeakReference)localIterator.next()).get() != paramc) {
            break label62;
          }
        }
        jdField_a_of_type_JavaUtilList.remove(i1);
        return;
      }
      finally {}
      label62:
      i1 += 1;
    }
  }
  
  private boolean b()
  {
    Activity localActivity;
    if ((getContext() instanceof Activity)) {
      localActivity = (Activity)getContext();
    } else {
      localActivity = null;
    }
    return (localActivity != null) && (!localActivity.isFinishing());
  }
  
  private boolean c()
  {
    return (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isX5HardwareAcceleratedForVideo()) && (Build.VERSION.SDK_INT >= 14);
  }
  
  private void d()
  {
    String str;
    if (isAllowRenderingWithinPage())
    {
      str = SmttServiceProxy.getInstance().getExtendJsRule("FullScreenEventDetect");
      if (str != null)
      {
        MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
        if (localMediaSourceBridge != null) {
          localMediaSourceBridge.evaluateJavascript(str);
        }
      }
    }
    if (SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 213))
    {
      str = SmttServiceProxy.getInstance().getExtendJsRule("ExtractTitle");
      if ((str != null) && (this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge != null)) {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.evaluateJavascript(str, new ValueCallback()
        {
          public void a(String paramAnonymousString)
          {
            paramAnonymousString = paramAnonymousString.replace("\\\"", "\"").replace("\\'", "'");
            if (paramAnonymousString != null)
            {
              if (paramAnonymousString.length() <= 2) {
                return;
              }
              paramAnonymousString = paramAnonymousString.substring(1, paramAnonymousString.length() - 1);
              if ((!TextUtils.isEmpty(paramAnonymousString)) && (!"null".equals(paramAnonymousString))) {
                c.this.a = paramAnonymousString;
              }
              return;
            }
          }
        });
      }
    }
  }
  
  private void d(final View paramView)
  {
    this.jdField_b_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        if (!c.a(c.this)) {
          return;
        }
        c.a(c.this, true);
        c localc = c.this;
        c.a(localc, new FakeFullScreenPanel(localc.getContext(), c.a(c.this), paramView, new FakeFullScreenPanel.IFakeFullSceenPanelClient()
        {
          public boolean onHidePanelView(View paramAnonymous2View)
          {
            paramAnonymous2View = c.a(c.14.this.jdField_a_of_type_ComTencentSmttWebkitH5videoC, "onHideVieoView", null, paramAnonymous2View, null, null, null);
            if ((paramAnonymous2View instanceof Boolean)) {
              return ((Boolean)paramAnonymous2View).booleanValue();
            }
            return false;
          }
          
          public boolean onShowPanelView(View paramAnonymous2View)
          {
            paramAnonymous2View = c.a(c.14.this.jdField_a_of_type_ComTencentSmttWebkitH5videoC, "onShowVieoView", null, paramAnonymous2View, null, null, null);
            if ((paramAnonymous2View instanceof Boolean)) {
              return ((Boolean)paramAnonymous2View).booleanValue();
            }
            return false;
          }
          
          public boolean shouldAttachToWebView()
          {
            Object localObject = c.a(c.14.this.jdField_a_of_type_ComTencentSmttWebkitH5videoC, "shouldAttachToWebView", null, c.14.this.jdField_a_of_type_AndroidViewView, null, null, null);
            if ((localObject instanceof Boolean)) {
              return ((Boolean)localObject).booleanValue();
            }
            return false;
          }
        }));
        c.a(c.this).b(c.a(c.this), c.b(c.this), c.c(c.this), c.d(c.this));
        c.a(c.this).c(c.b(c.this));
        c.a(c.this).b(c.c(c.this));
        c.a(c.this).a(c.d(c.this));
        c.a(c.this).a(c.a(c.this));
        c.a(c.this).a();
        c.a(c.this, false);
        if ((!c.this.isVisible()) && (!c.e(c.this))) {
          c.this.a.postDelayed(c.a(c.this), 1000L);
        }
        c.a(c.this, null);
      }
    };
    paramView = new Bundle();
    paramView.putString("eventName", "shouldWaitContextAvailable");
    paramView = invokeWebViewClientMiscCallBackMethod("onVideoEvent", paramView);
    if (((paramView instanceof Boolean)) && (((Boolean)paramView).booleanValue())) {
      return;
    }
    this.jdField_b_of_type_JavaLangRunnable.run();
  }
  
  private boolean d()
  {
    return SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 173) ^ true;
  }
  
  private void e()
  {
    this.jdField_g_of_type_Int = 3;
  }
  
  private boolean e()
  {
    boolean bool1 = c();
    boolean bool2 = false;
    if (bool1)
    {
      if (g()) {
        return false;
      }
      boolean bool3 = SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 172);
      boolean bool4 = SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 173);
      if (!SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 174))
      {
        bool1 = bool2;
        if (!bool4)
        {
          bool1 = bool2;
          if (bool3)
          {
            bool1 = bool2;
            if (this.jdField_a_of_type_ComTencentSmttWebkitH5videoC$a != a.b) {}
          }
        }
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  private void f()
  {
    FrameLayout localFrameLayout = new FrameLayout(getContext());
    localFrameLayout.setBackgroundColor(-16777216);
    ImageButton localImageButton = new ImageButton(getContext());
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
    localLayoutParams.gravity = 17;
    localImageButton.setLayoutParams(localLayoutParams);
    int i1 = SmttResource.getResources().getIdentifier("video_mid_play_fullscreen", "drawable", SmttResource.getPackageName());
    localImageButton.setImageDrawable(SmttResource.getResources().getDrawable(i1));
    localImageButton.setBackgroundColor(0);
    localImageButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        c.this.start();
      }
    });
    localFrameLayout.addView(localImageButton, new FrameLayout.LayoutParams(-2, -2, 17));
    a(localFrameLayout, this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
  }
  
  private boolean f()
  {
    return (this.p) && (!SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 210));
  }
  
  private void g()
  {
    boolean bool1 = isAllowRenderingWithinPage();
    boolean bool2 = e();
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("isHardwareAccelerated", bool1);
    localBundle.putBoolean("isVR", h());
    localBundle.putBoolean("fakeFullScreen", bool2);
    a().onMiscCallBack("updateVideoInfo", localBundle);
    if ((a().getScreenMode() == 107) && (!bool1)) {
      a().switchScreen(b());
    }
  }
  
  private boolean g()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localTencentWebViewProxy instanceof X5WebViewAdapter)) {
      return ((X5WebViewAdapter)localTencentWebViewProxy).isMiniQB();
    }
    return false;
  }
  
  private void h()
  {
    MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
    if (localMediaSourceBridge != null) {
      localMediaSourceBridge.onVideoRenderModeChanged();
    }
  }
  
  private boolean h()
  {
    return (this.jdField_j_of_type_Boolean) && (!isVisible());
  }
  
  private void i()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 14) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isFloatVideoEnabled()) && (!isAllowRenderingWithinPage())) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_f_of_type_Boolean = bool;
  }
  
  public IX5VideoPlayer a()
  {
    a();
    return this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
  }
  
  public void a()
  {
    if (this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer == null) {}
    for (;;)
    {
      try
      {
        Object localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
        Object localObject4 = null;
        if ((localObject1 == null) || (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getQQBrowserClient() == null)) {
          break label375;
        }
        localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getQQBrowserClient().getVideoPlayerHelper();
        if (localObject1 != null)
        {
          localObject3 = new Bundle();
          ((Bundle)localObject3).putString("eventName", "getPlayerEnv");
          localObject3 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension().onMiscCallBack("onVideoEvent", (Bundle)localObject3);
          if (!(localObject3 instanceof PlayerEnv)) {
            break label380;
          }
          localObject3 = (PlayerEnv)localObject3;
          Object localObject5 = new Bundle();
          ((Bundle)localObject5).putString("eventName", "getFeatureSupport");
          localObject5 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension().onMiscCallBack("onVideoEvent", (Bundle)localObject5);
          if ((localObject5 instanceof FeatureSupport)) {
            localObject4 = (FeatureSupport)localObject5;
          }
          localObject1 = ((IVideoPlayerHelper)localObject1).createVideoPlayer(null, this, null, (FeatureSupport)localObject4, (PlayerEnv)localObject3);
          if (localObject1 != null) {
            this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = ((IX5VideoPlayer)localObject1);
          }
        }
        else if (ContextHolder.getInstance().isTbsDevelopMode())
        {
          localObject1 = new PlayerEnv(getContext())
          {
            public int getPlayerEnvType()
            {
              return 3;
            }
          };
          localObject3 = new FeatureSupport();
          if ((((PlayerEnv)localObject1).isStandardFullScreen()) || (a())) {
            ((FeatureSupport)localObject3).addFeatureFlag(2048L);
          }
          ((FeatureSupport)localObject3).clearFeatrueFlag(256L);
          ((FeatureSupport)localObject3).addFeatureFlag(16L);
          localObject4 = new H5VideoInfo();
          ((H5VideoInfo)localObject4).mWebUrl = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl();
          ((H5VideoInfo)localObject4).mFromWhere = 1;
          ((H5VideoInfo)localObject4).mExtraData.putBoolean("shouldReuseExistPlayerIfExist", true);
          localObject1 = SmttServiceProxy.getInstance().createTbsVideoPlayer(getContext(), this, (H5VideoInfo)localObject4, (FeatureSupport)localObject3, (PlayerEnv)localObject1);
          if (localObject1 != null) {
            this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = ((IX5VideoPlayer)localObject1);
          }
        }
      }
      catch (Exception localException)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("J H5MediaSourceProxy.initPlayerIfNeed exception = ");
        ((StringBuilder)localObject3).append(Log.getStackTraceString(localException));
        Log.e("qbx5media", ((StringBuilder)localObject3).toString());
      }
      if ((this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer == null) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null)) {
        this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = new NullVideoPlayer(getContext());
      }
      return;
      label375:
      Object localObject2 = null;
      continue;
      label380:
      Object localObject3 = null;
    }
  }
  
  public void a(int paramInt)
  {
    final FrameLayout localFrameLayout = new FrameLayout(getContext());
    localFrameLayout.setOnTouchListener(new View.OnTouchListener()
    {
      private int jdField_a_of_type_Int = ViewConfiguration.get(c.this.getContext()).getScaledTouchSlop();
      private ArrayList<MotionEvent> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      private boolean jdField_a_of_type_Boolean = false;
      private boolean b = false;
      
      private void a(boolean paramAnonymousBoolean, float paramAnonymousFloat)
      {
        this.b = true;
        try
        {
          if (!this.jdField_a_of_type_JavaUtilArrayList.isEmpty())
          {
            Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
            while (localIterator.hasNext())
            {
              MotionEvent localMotionEvent = (MotionEvent)localIterator.next();
              if (paramAnonymousBoolean) {
                c.this.a(localMotionEvent, paramAnonymousFloat);
              }
              localMotionEvent.recycle();
            }
            this.jdField_a_of_type_JavaUtilArrayList.clear();
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          this.jdField_a_of_type_JavaUtilArrayList.clear();
        }
        this.b = false;
      }
      
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (this.b) {
          return false;
        }
        int k = paramAnonymousMotionEvent.getAction() & 0xFF;
        int i = (int)paramAnonymousMotionEvent.getX();
        int j = (int)paramAnonymousMotionEvent.getY();
        float f = 0.0F;
        if (k == 0)
        {
          this.jdField_a_of_type_Boolean = false;
          a(false, 0.0F);
        }
        if (this.jdField_a_of_type_Boolean) {
          return false;
        }
        this.jdField_a_of_type_JavaUtilArrayList.add(MotionEvent.obtainNoHistory(paramAnonymousMotionEvent));
        switch (k)
        {
        default: 
          return true;
        case 2: 
          k = (int)((MotionEvent)this.jdField_a_of_type_JavaUtilArrayList.get(0)).getX();
          if ((Math.abs(j - (int)((MotionEvent)this.jdField_a_of_type_JavaUtilArrayList.get(0)).getY()) > this.jdField_a_of_type_Int) || (Math.abs(k - i) > this.jdField_a_of_type_Int))
          {
            this.jdField_a_of_type_Boolean = true;
            if (Build.VERSION.SDK_INT >= 11) {
              f = localFrameLayout.getTranslationY();
            }
            a(true, f);
            return true;
          }
          break;
        case 1: 
        case 3: 
          a(false, 0.0F);
        }
        return true;
      }
    });
    localFrameLayout.setBackgroundColor(-2236188);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
    TextView localTextView = new TextView(getContext());
    if (paramInt == 106) {
      localTextView.setText(SmttResource.getText("x5_play_video_float_mode", "string"));
    } else {
      localTextView.setText(SmttResource.getText("x5_play_video_lite_mode", "string"));
    }
    localTextView.setTextSize(1, 15.0F);
    localTextView.setTextColor(-11711155);
    localFrameLayout.addView(localTextView, localLayoutParams);
    a(localFrameLayout, this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.jdField_f_of_type_Boolean) && (getContext().getResources().getConfiguration().orientation != 2))
    {
      if (!isVisible()) {
        return;
      }
      long l1 = System.currentTimeMillis();
      int i1 = Math.abs(paramInt1 - paramInt3);
      final int i2 = Math.abs(paramInt2 - paramInt4);
      this.jdField_b_of_type_Long += i1 + i2;
      if ((l1 - this.jdField_a_of_type_Long > 100L) || (this.jdField_b_of_type_Long > 50L))
      {
        this.jdField_a_of_type_Long = l1;
        this.jdField_b_of_type_Long = 0L;
        localObject = a();
        i1 = ((IH5VideoPlayer)localObject).getScreenMode();
        if ((i1 == 106) || (i1 == 101))
        {
          i2 = a(paramInt1, paramInt2);
          if ((i1 != i2) && ((((IH5VideoPlayer)localObject).isVideoPlaying()) || (i1 == 106))) {
            this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
            {
              public void run()
              {
                if (c.a(c.this) != null) {
                  localObject.switchScreen(i2);
                }
              }
            });
          }
        }
      }
      final Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
      if (localObject != null) {
        ((FakeFullScreenPanel)localObject).a(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      return;
    }
  }
  
  public void a(MotionEvent paramMotionEvent, float paramFloat)
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null)
    {
      s.a locala = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
      if (locala != null)
      {
        if (locala.a() == null) {
          return;
        }
        float f1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getScrollX() - this.jdField_a_of_type_ComTencentSmttWebkitS$a.a().getLeft();
        float f2 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getScrollY() - this.jdField_a_of_type_ComTencentSmttWebkitS$a.a().getTop();
        paramMotionEvent.offsetLocation(-f1, -(f2 - paramFloat));
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.dispatchTouchEvent(paramMotionEvent);
        return;
      }
    }
  }
  
  public void a(View paramView)
  {
    paramView = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (paramView == null) {
      return;
    }
    paramView.contentToViewX(this.jdField_b_of_type_Int);
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewY(this.jdField_c_of_type_Int);
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_d_of_type_Int);
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_e_of_type_Int);
    if (this.jdField_b_of_type_Boolean)
    {
      a(false);
      this.jdField_b_of_type_Boolean = false;
    }
    paramView = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (paramView != null) {
      paramView.a(false);
    }
  }
  
  public void a(s.a parama)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localTencentWebViewProxy != null) {
      localTencentWebViewProxy.removeViewForce(parama);
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_b_of_type_Boolean = false;
    MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
    if (localMediaSourceBridge != null) {
      localMediaSourceBridge.exitFullsceenByMediaPlayer();
    }
  }
  
  protected void b()
  {
    if (this.jdField_d_of_type_Boolean)
    {
      IX5WebChromeClient localIX5WebChromeClient = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebChromeClient();
      if (localIX5WebChromeClient != null) {
        localIX5WebChromeClient.onHideCustomView();
      }
    }
  }
  
  public void b(final View paramView)
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localObject == null) {
      return;
    }
    ((TencentWebViewProxy)localObject).clearTextEntry();
    if (ContextHolder.getInstance().isTbsDevelopMode())
    {
      localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebChromeClient();
      if (localObject != null)
      {
        paramView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        PlayerEnv localPlayerEnv = this.jdField_a_of_type_ComTencentMttVideoExportPlayerEnv;
        if (((localPlayerEnv != null) && (localPlayerEnv.isStandardFullScreen())) || (a()))
        {
          ((IX5WebChromeClient)localObject).onShowCustomView(paramView, new IX5WebChromeClient.CustomViewCallback() {});
          this.jdField_d_of_type_Boolean = true;
        }
      }
    }
  }
  
  public void b(boolean paramBoolean)
  {
    if (this.jdField_i_of_type_Boolean != paramBoolean) {
      h();
    }
    this.jdField_i_of_type_Boolean = paramBoolean;
  }
  
  public void c()
  {
    this.jdField_k_of_type_Boolean = true;
    FakeFullScreenPanel localFakeFullScreenPanel = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localFakeFullScreenPanel != null)
    {
      localFakeFullScreenPanel.b();
      this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel = null;
    }
    this.jdField_k_of_type_Boolean = false;
  }
  
  protected void c(final View paramView)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        c.this.b(paramView);
      }
    });
  }
  
  public boolean canPagePlay()
  {
    if (a() != 201)
    {
      if (this.jdField_h_of_type_Boolean) {
        return false;
      }
      WebSettingsExtension localWebSettingsExtension = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension();
      if ((localWebSettingsExtension != null) && (localWebSettingsExtension.getVideoScreenMode() == WebSettingsExtension.a.b)) {
        return false;
      }
      if (isAllowRenderingWithinPage()) {
        return true;
      }
      if (f()) {
        return true;
      }
      if (SmttServiceProxy.getInstance().isDefaultVideoFullScreenPlay(g())) {
        return SmttServiceProxy.getInstance().isForceVideoPagePlay(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), g());
      }
      return true;
    }
    return false;
  }
  
  public void createSurfaceTexture()
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (c.a(c.this) != null) {
          c.a(c.this).createSurfaceTexture();
        }
      }
    });
  }
  
  public void dispatchPause(int paramInt)
  {
    this.jdField_g_of_type_Int = paramInt;
    MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
    if (localMediaSourceBridge != null) {
      localMediaSourceBridge.pauseByMediaPlayer(this.jdField_g_of_type_Int, canPagePlay());
    }
    if (!this.jdField_e_of_type_Boolean) {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          c.this.pause();
        }
      });
    }
  }
  
  public void dispatchPlay(int paramInt)
  {
    setPlayType(3);
    this.jdField_g_of_type_Int = paramInt;
    MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
    if (localMediaSourceBridge != null) {
      localMediaSourceBridge.playByMediaPlayer();
    }
    if (!this.jdField_e_of_type_Boolean) {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          c.this.start();
        }
      });
    }
  }
  
  public void dispatchSeek(int paramInt1, int paramInt2)
  {
    MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
    if (localMediaSourceBridge != null) {
      localMediaSourceBridge.seekByMediaPlayer(paramInt1, paramInt2);
    }
  }
  
  public void dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    a(paramMotionEvent, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getViewManager().a());
  }
  
  public void enterFullscreen()
  {
    this.jdField_b_of_type_Boolean = true;
    a().switchScreen(b());
  }
  
  public void exitFullscreen()
  {
    if ((this.jdField_b_of_type_Boolean) && (a(a().getScreenMode())))
    {
      this.jdField_b_of_type_Boolean = false;
      a().switchScreen(101);
    }
  }
  
  public HttpHost getActualQProxy()
  {
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getActualQProxy();
  }
  
  public Context getContext()
  {
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealContext();
  }
  
  public int getCurrentPosition()
  {
    return a().getCurrentPosition();
  }
  
  public int getDuration()
  {
    int i1 = a().getDuration();
    if (i1 > 0) {
      return i1;
    }
    return -1;
  }
  
  public IVideoWebViewProxy getH5VideoWebViewProxy()
  {
    return new H5VideoWebViewProxy(getContext(), this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy);
  }
  
  public int getProxyType()
  {
    return 1;
  }
  
  public int getScreenMode()
  {
    return a().getScreenMode();
  }
  
  public int getSniffVideoID()
  {
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSniffVideoID();
  }
  
  public String getSniffVideoRefer()
  {
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSniffVideoRefer();
  }
  
  public MediaPlayer.TrackInfo[] getTrackInfo()
  {
    return null;
  }
  
  public int getVideoHeight()
  {
    return a().getVideoHeight();
  }
  
  public int getVideoWidth()
  {
    return a().getVideoWidth();
  }
  
  public Object invokeWebViewClientMiscCallBackMethod(String paramString, Bundle paramBundle)
  {
    for (;;)
    {
      try
      {
        if ("urlChanged".equals(paramString))
        {
          paramString = paramBundle.getString("jumpUrl");
          if ((this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge != null) && (!TextUtils.isEmpty(paramString)))
          {
            this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge.videoUrlRedirect(paramString);
            return null;
          }
        }
        else
        {
          if ("onHaveVideoData".equals(paramString))
          {
            if (this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge != null)
            {
              this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge.onHaveVideoData();
              return null;
            }
          }
          else if ("onNoVideoData".equals(paramString))
          {
            if (this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge != null)
            {
              this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge.onNoVideoData();
              return null;
            }
          }
          else
          {
            if ("requestCharacterStatistics".equals(paramString)) {
              return null;
            }
            if (("switchScreen".equals(paramString)) && (paramBundle != null))
            {
              int i1 = paramBundle.getInt("screenMode", 100);
              if (i1 != 100) {
                a().switchScreen(i1);
              }
              if (getScreenMode() != i1) {
                break label282;
              }
              bool = true;
              return Boolean.valueOf(bool);
            }
            if ("contextAvailable".equals(paramString))
            {
              if (this.jdField_b_of_type_JavaLangRunnable != null)
              {
                this.jdField_a_of_type_AndroidOsHandler.post(this.jdField_b_of_type_JavaLangRunnable);
                return null;
              }
            }
            else
            {
              if ("isSoftKeyBoardShowing".equals(paramString)) {
                return Boolean.valueOf(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isSoftKeyBoardShowing());
              }
              if ("getWebView".equals(paramString)) {
                return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
              }
              if ("getPlayer".equals(paramString)) {
                return this.jdField_a_of_type_ComTencentSmttWebkitH5videoE;
              }
              if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension() != null)
              {
                paramString = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension().onMiscCallBack(paramString, paramBundle);
                return paramString;
              }
            }
          }
          return null;
        }
      }
      catch (Throwable paramString)
      {
        return null;
      }
      return null;
      label282:
      boolean bool = false;
    }
  }
  
  public boolean isActive()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    boolean bool2 = false;
    if (localTencentWebViewProxy == null) {
      return false;
    }
    boolean bool1 = bool2;
    if (localTencentWebViewProxy.isActive())
    {
      bool1 = bool2;
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isShown()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public boolean isAllowRenderingWithinPage()
  {
    return (d()) || (e());
  }
  
  public boolean isInPrefetchPage()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public boolean isPlaying()
  {
    return a().isVideoPlaying();
  }
  
  public boolean isVideoPlaying()
  {
    return a().isVideoPlaying();
  }
  
  public boolean isVisible()
  {
    return (this.jdField_b_of_type_Int < this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContentWidth()) && (this.jdField_b_of_type_Int + this.jdField_d_of_type_Int > 0) && (this.jdField_c_of_type_Int < this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContentHeight()) && (this.jdField_c_of_type_Int + this.jdField_e_of_type_Int > 0);
  }
  
  public void loadUrl(String paramString, Map<String, String> paramMap)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localTencentWebViewProxy != null) {
      localTencentWebViewProxy.loadUrl(paramString, paramMap);
    }
  }
  
  public void onAttachVideoView(final View paramView, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 106)
    {
      a(this.jdField_b_of_type_ComTencentSmttWebkitS$a);
      this.jdField_b_of_type_ComTencentSmttWebkitS$a = null;
    }
    c();
    this.jdField_a_of_type_AndroidOsHandler.removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (localObject != null)
    {
      a((s.a)localObject);
      this.jdField_a_of_type_ComTencentSmttWebkitS$a = null;
    }
    int i1 = 1;
    switch (paramInt1)
    {
    default: 
      break;
    case 107: 
      if (a(paramInt2)) {
        a(paramView);
      }
      d(paramView);
      break;
    case 106: 
      a(paramInt1);
      if (this.jdField_b_of_type_Boolean) {
        a(true);
      }
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null)
      {
        localObject = new int[2];
        a((int[])localObject);
        this.jdField_b_of_type_ComTencentSmttWebkitS$a = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.addSurfaceOnUIThread(paramView, 0, 0, localObject[0], localObject[1], 2);
      }
      break;
    case 103: 
      a(paramInt1);
      if (ContextHolder.getInstance().isTbsDevelopMode()) {
        b();
      }
      if (this.jdField_b_of_type_Boolean) {
        a(true);
      }
      break;
    case 102: 
    case 104: 
    case 105: 
      if (ContextHolder.getInstance().isTbsDevelopMode()) {
        c(paramView);
      }
      break;
    case 101: 
      if (a(paramInt2)) {
        a(paramView);
      }
      if ((paramView != null) && (paramView.getParent() == null)) {
        if (this.jdField_h_of_type_Boolean)
        {
          f();
        }
        else
        {
          if ((paramView.getTag(1) instanceof Boolean)) {
            paramInt2 = i1;
          } else {
            paramInt2 = 0;
          }
          if (paramInt2 == 0)
          {
            this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
            {
              public void run()
              {
                c localc = c.this;
                c.a(localc, paramView, c.a(localc), c.b(c.this), c.c(c.this), c.d(c.this));
                if (c.a(c.this) != null) {
                  c.a(c.this).a(false);
                }
              }
            });
          }
          else
          {
            a(paramView, this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
            paramView = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
            if (paramView != null) {
              paramView.a(false);
            }
          }
        }
      }
      if (ContextHolder.getInstance().isTbsDevelopMode()) {
        b();
      }
      break;
    }
    if (paramInt1 != 103) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.pauseTimerForPendingLitePlayExit();
    }
  }
  
  public void onBufferingUpdate(int paramInt)
  {
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null) {
      localIMediaPlayerListener.onBufferingUpdate(this, paramInt);
    }
  }
  
  public void onCompletion()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localObject != null) {
      ((IMediaPlayer.IMediaPlayerListener)localObject).onCompletion(this);
    }
    localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localObject != null) {
      ((TencentWebViewProxy)localObject).setSharedVideoTime(0);
    }
  }
  
  public void onError(int paramInt1, int paramInt2)
  {
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null) {
      localIMediaPlayerListener.onError(this, 1, 64529);
    }
  }
  
  public void onNetworkStateChanged(VideoProxyDefault.NetworkState paramNetworkState) {}
  
  public void onPaused() {}
  
  public void onPlayed() {}
  
  public void onPlayerDestroyed(IH5VideoPlayer paramIH5VideoPlayer) {}
  
  public void onPrepared(int paramInt1, int paramInt2, int paramInt3)
  {
    this.jdField_i_of_type_Int = paramInt2;
    this.jdField_j_of_type_Int = paramInt3;
    this.jdField_h_of_type_Int = paramInt1;
    this.jdField_e_of_type_Boolean = true;
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null) {
      localIMediaPlayerListener.onPrepared(this);
    }
  }
  
  public boolean onScreenModeChangeBefore(int paramInt1, int paramInt2)
  {
    return this.o;
  }
  
  public void onScreenModeChanged(int paramInt1, int paramInt2)
  {
    int i1;
    try
    {
      Bundle localBundle = new Bundle();
      localBundle.putInt("from", paramInt1);
      localBundle.putInt("to", paramInt2);
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension().onMiscCallBack("onVideoScreenModeChanged", localBundle);
      boolean bool = a(paramInt1);
      i1 = 0;
      if (bool) {
        break label124;
      }
      if (107 != paramInt1) {
        break label119;
      }
    }
    catch (Exception localException)
    {
      return;
    }
    if ((!a(paramInt2)) && (107 != paramInt2)) {}
    for (;;)
    {
      if ((paramInt1 != 0) && (i1 == 0)) {
        this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge.onMediaPlayerExitFullScreen();
      }
      if ((paramInt1 == 0) && (i1 != 0)) {
        this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge.onMediaPlayerEnterFullScreen();
      }
      return;
      label119:
      paramInt1 = 0;
      break;
      label124:
      paramInt1 = 1;
      break;
      i1 = 1;
    }
  }
  
  public void onSeekComplete(int paramInt)
  {
    if ((this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener != null) && (!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString))) {
      this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener.onSeekComplete(this);
    }
  }
  
  public void onSniffFailed(String paramString) {}
  
  public void onVideoSizeChanged(int paramInt1, int paramInt2)
  {
    this.jdField_i_of_type_Int = paramInt1;
    this.jdField_j_of_type_Int = paramInt2;
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null) {
      localIMediaPlayerListener.onVideoSizeChanged(this, paramInt1, paramInt2);
    }
  }
  
  public void onVideoStartShowing()
  {
    FakeFullScreenPanel localFakeFullScreenPanel = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localFakeFullScreenPanel != null) {
      localFakeFullScreenPanel.a();
    }
  }
  
  public void onVideoViewMove(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void pause()
  {
    a().pause(this.jdField_g_of_type_Int);
    e();
  }
  
  public void preload(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (paramString1 != null)
    {
      boolean bool = false;
      if (shouldOverrideStandardPlay(false)) {
        return;
      }
      if (paramString1.startsWith("file://")) {
        this.jdField_b_of_type_JavaLangString = paramString1.substring(7);
      } else {
        this.jdField_b_of_type_JavaLangString = paramString1;
      }
      if (!TextUtils.isEmpty(paramString5)) {
        paramString4 = paramString5;
      }
      this.jdField_c_of_type_JavaLangString = paramString4;
      paramString3 = new H5VideoInfo();
      paramString3.mVideoUrl = this.jdField_b_of_type_JavaLangString;
      paramString3.mWebUrl = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl();
      paramString1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings();
      paramString2 = null;
      if (paramString1 != null) {
        paramString1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgent();
      } else {
        paramString1 = null;
      }
      paramString3.mUA = paramString1;
      paramString1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
      if (paramString1 != null) {
        bool = paramString1.isPrivateBrowsingEnabled();
      }
      paramString3.mPrivateBrowsingEnabled = bool;
      paramString3.mSnifferReffer = this.jdField_c_of_type_JavaLangString;
      paramString1 = paramString2;
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings() != null) {
        paramString1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgent();
      }
      paramString3.mUA = paramString1;
      a().preload(paramString3);
      d();
      return;
    }
  }
  
  public void prepareAsync() {}
  
  public void release()
  {
    a().unmountProxy();
    this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge = null;
    this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener = null;
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (localObject != null)
    {
      a((s.a)localObject);
      this.jdField_a_of_type_ComTencentSmttWebkitS$a = null;
    }
    c();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.pauseTimerForPendingLitePlayExit();
    if (a().getScreenMode() == 106)
    {
      a(this.jdField_b_of_type_ComTencentSmttWebkitS$a);
      this.jdField_b_of_type_ComTencentSmttWebkitS$a = null;
    }
    if ((Build.VERSION.SDK_INT >= 11) && ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getParent() instanceof View)))
    {
      localObject = (View)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getParent();
      int i1 = this.jdField_a_of_type_Int;
      if ((i1 != -1) && (i1 != ((View)localObject).getLayerType())) {
        ((View)localObject).setLayerType(this.jdField_a_of_type_Int, null);
      }
    }
    b(this);
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getH5MediaSourceProxy() == this) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setH5MediaSourceProxy(null);
    }
  }
  
  public void releaseSurfaceTexture()
  {
    MediaSourceBridge localMediaSourceBridge = this.jdField_a_of_type_OrgChromiumTencentMediaMediaSourceBridge;
    if (localMediaSourceBridge != null) {
      localMediaSourceBridge.releaseSurfaceTexture();
    }
  }
  
  public void reset(String paramString)
  {
    if (this.jdField_e_of_type_Boolean) {
      this.jdField_g_of_type_Boolean = true;
    }
    this.jdField_b_of_type_JavaLangString = paramString;
    this.jdField_d_of_type_JavaLangString = null;
    this.jdField_l_of_type_Int = -1;
    b(false);
  }
  
  public void seekTo(int paramInt)
  {
    a().seek(paramInt);
  }
  
  public void setDataSource(Context paramContext, Uri paramUri)
    throws IOException
  {}
  
  public void setDataSource(Context paramContext, Uri paramUri, HashMap<String, String> paramHashMap)
  {
    start();
  }
  
  public void setDataSource(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) {}
  
  public void setIsFixedPositionVideo(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void setIsVRVideo(boolean paramBoolean)
  {
    if (this.jdField_j_of_type_Boolean == paramBoolean) {
      return;
    }
    boolean bool = h();
    this.jdField_j_of_type_Boolean = paramBoolean;
    FakeFullScreenPanel localFakeFullScreenPanel = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localFakeFullScreenPanel != null) {
      localFakeFullScreenPanel.c(paramBoolean);
    }
    if (bool != h()) {
      g();
    }
  }
  
  public void setMediaPlayerListener(IMediaPlayer.IMediaPlayerListener paramIMediaPlayerListener)
  {
    this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener = paramIMediaPlayerListener;
  }
  
  public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener paramOnBufferingUpdateListener) {}
  
  public void setOnCompletionListener(MediaPlayer.OnCompletionListener paramOnCompletionListener) {}
  
  public void setOnErrorListener(MediaPlayer.OnErrorListener paramOnErrorListener) {}
  
  public void setOnPreparedListener(MediaPlayer.OnPreparedListener paramOnPreparedListener) {}
  
  public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener paramOnSeekCompleteListener) {}
  
  public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener paramOnVideoSizeChangedListener) {}
  
  public void setOrignalSrc(String paramString)
  {
    this.jdField_d_of_type_JavaLangString = paramString;
  }
  
  public void setPlayType(int paramInt)
  {
    this.jdField_f_of_type_Int = paramInt;
  }
  
  public void setPlaybackRate(double paramDouble) {}
  
  public void setScreenMode(int paramInt) {}
  
  public void setSurface(Surface paramSurface) {}
  
  public void setSurfaceTexture(Object paramObject)
  {
    a().onSurfaceTextureCreated(paramObject);
  }
  
  public void setVideoAttr(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    if ("qb-video-float-mode".equals(paramString1))
    {
      if ("false".equals(paramString2))
      {
        this.jdField_f_of_type_Boolean = false;
        return;
      }
      if ("true".equals(paramString2)) {
        i();
      }
    }
    else
    {
      if ("x5-video-player-type".equals(paramString1))
      {
        this.jdField_a_of_type_ComTencentSmttWebkitH5videoC$a = a.a(paramString2);
        return;
      }
      if ("x5-video-player-fullscreen".equals(paramString1))
      {
        this.jdField_l_of_type_Boolean = "true".equals(paramString2);
        return;
      }
      if ("x5-video-orientation".equals(paramString1))
      {
        this.m = paramString2.contains("landscape");
        this.n = paramString2.contains("portrait");
        paramString1 = new Bundle();
        paramString1.putBoolean("X5VideoOrientationAttrLandscape", this.m);
        paramString1.putBoolean("X5VideoOrientationAttrPortrait", this.n);
        a().onMiscCallBack("setOrientation", paramString1);
        return;
      }
      if ("x5-playsinline".equals(paramString1))
      {
        this.p = true;
        return;
      }
      Bundle localBundle = new Bundle();
      localBundle.putString("key", paramString1);
      localBundle.putString("value", paramString2);
      a().onMiscCallBack("setVideoAttr", localBundle);
    }
  }
  
  public void setVideoAttrs(String paramString)
  {
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "\n");
    while (localStringTokenizer1.hasMoreElements())
    {
      StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), "=");
      int i1 = localStringTokenizer2.countTokens();
      String str2 = null;
      String str1;
      if (i1 >= 1) {
        str1 = localStringTokenizer2.nextToken();
      } else {
        str1 = null;
      }
      if (localStringTokenizer2.countTokens() == 1) {
        str2 = localStringTokenizer2.nextToken();
      }
      try
      {
        if (TextUtils.isEmpty(str1)) {
          continue;
        }
        setVideoAttr(str1, str2);
      }
      catch (Throwable localThrowable)
      {
        for (;;) {}
      }
      if (str1 == null) {
        str1 = "null";
      }
      if (str2 == null) {
        str2 = "null";
      }
      Log.e("qbx5media", String.format("J H5MediaSourceProxy.setVideoAttr exceptions (this = %s, attrs = %s, key= %s, value = %s)", new Object[] { this, paramString, str1, str2 }));
    }
  }
  
  public void setVideoInfo(H5VideoInfo paramH5VideoInfo) {}
  
  public void setVideoPlayer(IH5VideoPlayer paramIH5VideoPlayer) {}
  
  public void setVolume(float paramFloat1, float paramFloat2)
  {
    a().setVolume(paramFloat1, paramFloat2);
  }
  
  public boolean shouldOverrideStandardPlay(boolean paramBoolean)
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    boolean bool = false;
    if ((localObject != null) && (((TencentWebViewProxy)localObject).getQQBrowserClient() != null))
    {
      localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getQQBrowserClient();
      if (this.jdField_f_of_type_Int == 3) {
        bool = true;
      }
      return ((IX5QQBrowserClient)localObject).shouldOverrideStandardPlay(paramBoolean, bool, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getAutoPlayFlag(), a());
    }
    return false;
  }
  
  public void start()
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null) {
      return;
    }
    int i1 = Build.VERSION.SDK_INT;
    Object localObject2 = null;
    if ((i1 >= 11) && ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getParent() instanceof View)))
    {
      localObject1 = (View)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getParent();
      if (((View)localObject1).getLayerType() == 1)
      {
        this.jdField_a_of_type_Int = 1;
        ((View)localObject1).setLayerType(0, null);
      }
    }
    Object localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension();
    if ((localObject1 == null) || ((((WebSettingsExtension)localObject1).getVideoScreenMode() != WebSettingsExtension.a.b) && (((WebSettingsExtension)localObject1).getVideoPlaybackRequiresUserGesture())) || (this.jdField_f_of_type_Int == 0)) {
      i1 = this.jdField_f_of_type_Int;
    }
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setH5MediaSourceProxy(this);
    if (!this.o) {
      i2 = a().getScreenMode();
    } else {
      i2 = 101;
    }
    Object localObject3 = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
    i1 = i2;
    if (localObject3 != null) {
      if ((((IX5VideoPlayer)localObject3).getScreenMode() != 0) && (this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer.getScreenMode() != 100))
      {
        i1 = this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer.getScreenMode();
      }
      else
      {
        i1 = i2;
        if (localObject1 != null)
        {
          i1 = i2;
          if (((WebSettingsExtension)localObject1).getVideoScreenMode() == WebSettingsExtension.a.b) {
            i1 = 102;
          }
        }
      }
    }
    if (a() != 201)
    {
      i2 = i1;
      if (!this.jdField_b_of_type_Boolean) {}
    }
    else
    {
      i2 = i1;
      if (!a(i1))
      {
        i2 = i1;
        if (i1 != 103) {
          i2 = b();
        }
      }
    }
    i1 = i2;
    if (i2 == 101)
    {
      i1 = i2;
      if (!canPagePlay()) {
        i1 = b();
      }
    }
    boolean bool2 = isAllowRenderingWithinPage();
    boolean bool1;
    if ((!d()) && (bool2)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    int i2 = i1;
    if (i1 == 101)
    {
      i2 = i1;
      if (bool1) {
        i2 = 107;
      }
    }
    localObject3 = new H5VideoInfo();
    ((H5VideoInfo)localObject3).mPostion = 0;
    ((H5VideoInfo)localObject3).mFromWhere = 1;
    ((H5VideoInfo)localObject3).mScreenMode = i2;
    ((H5VideoInfo)localObject3).mSnifferReffer = this.jdField_c_of_type_JavaLangString;
    ((H5VideoInfo)localObject3).mVideoUrl = "";
    ((H5VideoInfo)localObject3).mHasClicked = true;
    ((H5VideoInfo)localObject3).mAutoPlayVideo = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getAutoPlayFlag();
    ((H5VideoInfo)localObject3).mExtraData.putBoolean("isHardwareAccelerated", bool2);
    ((H5VideoInfo)localObject3).mExtraData.putBoolean("isVR", h());
    ((H5VideoInfo)localObject3).mExtraData.putBoolean("fakeFullScreen", bool1);
    ((H5VideoInfo)localObject3).mExtraData.putBoolean("canUseHardware", this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isX5HardwareAcceleratedForVideo());
    ((H5VideoInfo)localObject3).mExtraData.putBoolean("selfProvdePlayer", true);
    if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy instanceof X5WebViewAdapter))
    {
      ((H5VideoInfo)localObject3).mExtraData.putBoolean("isMiniQB", g());
      ((H5VideoInfo)localObject3).mExtraData.putInt("miniQBSourcePosID", ((X5WebViewAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy).getMiniQBSourcePosID());
    }
    ((H5VideoInfo)localObject3).mExtraData.putBoolean("qb-video-zorder", this.jdField_i_of_type_Boolean);
    ((H5VideoInfo)localObject3).mExtraData.putInt("qb-video-zorder-duration", this.jdField_l_of_type_Int);
    localObject1 = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (localObject1 != null) {
      ((s.a)localObject1).a(0);
    }
    ((H5VideoInfo)localObject3).mWebUrl = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl();
    if (TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
      localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getTitle();
    } else {
      localObject1 = this.jdField_a_of_type_JavaLangString;
    }
    ((H5VideoInfo)localObject3).mWebTitle = ((String)localObject1);
    localObject1 = localObject2;
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings() != null) {
      localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgent();
    }
    ((H5VideoInfo)localObject3).mUA = ((String)localObject1);
    ((H5VideoInfo)localObject3).mPostion = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSharedVideoTime();
    ((H5VideoInfo)localObject3).mPrivateBrowsingEnabled = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isPrivateBrowsingEnabled();
    if (this.jdField_d_of_type_JavaLangString != null) {
      ((H5VideoInfo)localObject3).mExtraData.putString("backupUrl", this.jdField_d_of_type_JavaLangString);
    }
    this.o = false;
    a().play((H5VideoInfo)localObject3, this.jdField_g_of_type_Int);
    e();
    if (this.jdField_g_of_type_Boolean)
    {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          c localc = c.this;
          localc.onVideoSizeChanged(c.e(localc), c.f(c.this));
          localc = c.this;
          localc.onPrepared(c.g(localc), c.e(c.this), c.f(c.this));
        }
      });
      this.jdField_g_of_type_Boolean = false;
    }
  }
  
  public void updateVideoPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isSoftKeyBoardShowing()) {
      a().onVideoSizeChanged(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(paramInt3), this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(paramInt4));
    }
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localObject != null) {
      ((FakeFullScreenPanel)localObject).b(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    if ((this.jdField_b_of_type_Int == paramInt1) && (this.jdField_c_of_type_Int == paramInt2) && (this.jdField_d_of_type_Int == paramInt3) && (this.jdField_e_of_type_Int == paramInt4)) {
      return;
    }
    boolean bool1 = isVisible();
    this.jdField_b_of_type_Int = paramInt1;
    this.jdField_c_of_type_Int = paramInt2;
    this.jdField_d_of_type_Int = paramInt3;
    this.jdField_e_of_type_Int = paramInt4;
    if (a().getScreenMode() == 106) {
      if (!isVisible())
      {
        a().switchScreen(101);
      }
      else if (this.jdField_b_of_type_ComTencentSmttWebkitS$a != null)
      {
        localObject = new int[2];
        a((int[])localObject);
        this.jdField_b_of_type_ComTencentSmttWebkitS$a.c(0, 0, localObject[0], localObject[1]);
      }
    }
    boolean bool2 = isVisible();
    if (bool2 != bool1)
    {
      g();
      if ((!bool2) && (a().getScreenMode() == 107)) {
        this.jdField_a_of_type_AndroidOsHandler.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 1000L);
      }
      if (bool2) {
        this.jdField_a_of_type_AndroidOsHandler.removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
      }
    }
    localObject = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (localObject != null) {
      ((s.a)localObject).c(this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
    }
  }
  
  public int videoCountOnThePage()
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localObject == null) {
      return 1;
    }
    localObject = ((TencentWebViewProxy)localObject).getAllVideoTime();
    if (localObject != null) {
      return ((ArrayList)localObject).size();
    }
    return 1;
  }
  
  private static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitH5videoC$a = new a("PlayerTypeUnknown", 0);
      b = new a("PlayerTypeH5", 1);
      c = new a("PlayerTypeVR", 2);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitH5videoC$a = new a[] { jdField_a_of_type_ComTencentSmttWebkitH5videoC$a, b, c };
    }
    
    private a() {}
    
    public static a a(String paramString)
    {
      if ("h5".equals(paramString)) {
        return b;
      }
      if ("vr".equals(paramString)) {
        return c;
      }
      return jdField_a_of_type_ComTencentSmttWebkitH5videoC$a;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */