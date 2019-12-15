package com.tencent.smtt.webkit.h5video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.os.Message;
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
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.mtt.video.browser.export.player.IVideoWebViewProxy;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.H5VideoTime;
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
import com.tencent.smtt.webkit.e;
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
import org.chromium.tencent.TencentAwContent;
import org.chromium.tencent.X5NativeBitmap;
import org.chromium.tencent.X5NativeBitmap.Factory;
import org.chromium.tencent.media.TencentMediaPlayerBridge;
import org.chromium.tencent.video.IMediaPlayer;
import org.chromium.tencent.video.IMediaPlayer.IMediaPlayerListener;

public class d
  extends VideoProxyDefault
  implements IMediaPlayer
{
  private static List<WeakReference<d>> jdField_a_of_type_JavaUtilList = new LinkedList();
  private int jdField_a_of_type_Int = -1;
  private long jdField_a_of_type_Long;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private IX5VideoPlayer jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
  private FakeFullScreenPanel jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
  private a jdField_a_of_type_ComTencentSmttWebkitH5videoD$a;
  private b jdField_a_of_type_ComTencentSmttWebkitH5videoD$b;
  private s.a jdField_a_of_type_ComTencentSmttWebkitS$a = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private Object jdField_a_of_type_JavaLangObject;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private String jdField_a_of_type_JavaLangString;
  private TencentMediaPlayerBridge jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
  private IMediaPlayer.IMediaPlayerListener jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
  private int jdField_b_of_type_Int = 63536;
  private long jdField_b_of_type_Long;
  private s.a jdField_b_of_type_ComTencentSmttWebkitS$a;
  private Runnable jdField_b_of_type_JavaLangRunnable;
  private String jdField_b_of_type_JavaLangString;
  private boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int = 63536;
  private String jdField_c_of_type_JavaLangString;
  private boolean jdField_c_of_type_Boolean;
  private int jdField_d_of_type_Int = 300;
  private String jdField_d_of_type_JavaLangString;
  private boolean jdField_d_of_type_Boolean;
  private int jdField_e_of_type_Int = 150;
  private String jdField_e_of_type_JavaLangString;
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
  private final int jdField_l_of_type_Int;
  private boolean jdField_l_of_type_Boolean;
  private boolean m;
  private boolean n;
  private boolean o;
  private boolean p;
  private boolean q;
  private boolean r;
  private boolean s;
  private boolean t;
  
  public d(TencentWebViewProxy paramTencentWebViewProxy, TencentMediaPlayerBridge paramTencentMediaPlayerBridge)
  {
    boolean bool2 = false;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_f_of_type_Int = 0;
    this.jdField_g_of_type_Int = 3;
    this.jdField_a_of_type_JavaLangString = null;
    this.jdField_b_of_type_JavaLangString = null;
    this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener = null;
    this.jdField_k_of_type_Int = 0;
    this.jdField_a_of_type_Long = 0L;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_e_of_type_Boolean = false;
    this.jdField_f_of_type_Boolean = false;
    this.jdField_g_of_type_Boolean = false;
    this.jdField_l_of_type_Int = 0;
    this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 0) {
          return;
        }
        d.this.start();
      }
    };
    this.jdField_a_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        d.a(d.this);
      }
    };
    this.jdField_k_of_type_Boolean = true;
    this.jdField_l_of_type_Boolean = true;
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$a = null;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge = paramTencentMediaPlayerBridge;
    paramTencentWebViewProxy = invokeWebViewClientMiscCallBackMethod("isForceFullscreenWhenFakeFullscreen", null);
    if ((paramTencentWebViewProxy instanceof Boolean)) {
      this.q = ((Boolean)paramTencentWebViewProxy).booleanValue();
    }
    shouldOverrideStandardPlay(false);
    c(this);
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
        this.jdField_g_of_type_Boolean = true;
      }
    }
    this.r = SmttServiceProxy.getInstance().isEnableVideoSameLayer(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), l());
    this.s = SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 173);
    paramTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    boolean bool1 = bool2;
    if (paramTencentWebViewProxy != null)
    {
      bool1 = bool2;
      if (paramTencentWebViewProxy.getSettingsExtension() != null)
      {
        bool1 = bool2;
        if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isForceVideoSameLayer()) {
          bool1 = true;
        }
      }
    }
    this.p = bool1;
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null) {
      l();
    }
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
      localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgentString();
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
    s.a locala = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    int i2 = 101;
    int i1 = i2;
    if (locala != null)
    {
      i1 = i2;
      if (locala.a() != null)
      {
        i1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewY(this.jdField_c_of_type_Int);
        int i3 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(this.jdField_e_of_type_Int);
        paramInt1 = i1;
        if (this.jdField_k_of_type_Int == 1) {
          paramInt1 = i1 + this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getScrollY();
        }
        if (i3 + paramInt1 >= paramInt2)
        {
          i1 = i2;
          if (paramInt1 <= paramInt2 + this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight()) {}
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
    int i3 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getViewManager().a();
    for (;;)
    {
      try
      {
        localBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(i1, i2 - i3, Bitmap.Config.RGB_565);
      }
      catch (Throwable localThrowable1)
      {
        Bitmap localBitmap;
        continue;
        return localThrowable1;
      }
      try
      {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.snapshotVisibleWithBitmap(localBitmap, false, false, false, 1.0F, 1.0F);
        return localBitmap;
      }
      catch (Throwable localThrowable2) {}
    }
    return null;
  }
  
  private IX5VideoPlayer a()
  {
    c();
    return this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer;
  }
  
  private TencentWebViewProxy a()
  {
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  }
  
  private Object a(String paramString, Bundle paramBundle, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension() != null) {
      return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewClientExtension().onMiscCallBack(paramString, paramBundle, paramObject1, paramObject2, paramObject3, paramObject4);
    }
    return null;
  }
  
  private String a()
  {
    Object localObject = new Bundle();
    ((Bundle)localObject).putString("url", this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl());
    localObject = invokeWebViewClientMiscCallBackMethod("interceptURL", (Bundle)localObject);
    if ((localObject instanceof String)) {
      return (String)localObject;
    }
    return this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl();
  }
  
  public static ArrayList<H5VideoTime> a(TencentWebViewProxy paramTencentWebViewProxy)
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        d locald = (d)((WeakReference)localIterator.next()).get();
        if ((locald != null) && (locald.a() == paramTencentWebViewProxy))
        {
          H5VideoTime localH5VideoTime = new H5VideoTime();
          localH5VideoTime.mCurrentPlayTime = locald.getCurrentPosition();
          localH5VideoTime.mDuration = locald.getDuration();
          localH5VideoTime.mUrl = locald.b();
          localArrayList.add(localH5VideoTime);
        }
      }
      return localArrayList;
    }
    finally {}
  }
  
  private void a(int paramInt)
  {
    if (!isVisible()) {
      return;
    }
    final FrameLayout local7 = new FrameLayout(getContext())
    {
      public boolean performClick()
      {
        return super.performClick();
      }
    };
    local7.setOnTouchListener(new View.OnTouchListener()
    {
      private int jdField_a_of_type_Int = ViewConfiguration.get(d.this.getContext()).getScaledTouchSlop();
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
                d.a(d.this, localMotionEvent, paramAnonymousFloat);
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
      
      @SuppressLint({"ClickableViewAccessibility"})
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
              f = local7.getTranslationY();
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
    local7.setBackgroundColor(-14408668);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
    TextView localTextView = new TextView(getContext());
    if (paramInt == 106) {
      localTextView.setText(SmttResource.getText("x5_play_video_float_mode", "string"));
    } else {
      localTextView.setText(SmttResource.getText("x5_play_video_lite_mode", "string"));
    }
    localTextView.setTextSize(1, 15.0F);
    localTextView.setTextColor(-11711155);
    local7.addView(localTextView, localLayoutParams);
    a(local7, this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
  }
  
  private void a(MotionEvent paramMotionEvent, float paramFloat)
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
  
  private void a(View paramView)
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
      b(false);
      this.jdField_b_of_type_Boolean = false;
    }
    paramView = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (paramView != null) {
      paramView.a(false);
    }
  }
  
  private void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localTencentWebViewProxy != null) && (this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge != null)) {
      this.jdField_a_of_type_ComTencentSmttWebkitS$a = localTencentWebViewProxy.addSurfaceOnUIThread(paramView, paramInt1, paramInt2, paramInt3, paramInt4, this.jdField_k_of_type_Int);
    }
  }
  
  public static void a(TencentWebViewProxy paramTencentWebViewProxy)
  {
    Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext())
    {
      d locald = (d)((WeakReference)localIterator.next()).get();
      if ((locald != null) && (locald.a() == paramTencentWebViewProxy)) {
        locald.n();
      }
    }
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
  
  private boolean a(int paramInt)
  {
    return (paramInt == 102) || (paramInt == 104) || (paramInt == 105);
  }
  
  private boolean a(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramString.startsWith("h5tenvideo"));
  }
  
  private int b()
  {
    if (a().getScreenMode() == 107) {
      return 102;
    }
    if ((!SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 199)) && (!canPagePlay())) {
      return 104;
    }
    if (this.jdField_d_of_type_Boolean)
    {
      if (b(this.jdField_i_of_type_Int, this.jdField_j_of_type_Int)) {
        return 104;
      }
    }
    else if (b(this.jdField_d_of_type_Int, this.jdField_e_of_type_Int)) {
      return 104;
    }
    return 102;
  }
  
  private String b()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  private void b(final View paramView)
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
        if (d())
        {
          ((IX5WebChromeClient)localObject).onShowCustomView(paramView, new IX5WebChromeClient.CustomViewCallback() {});
          this.jdField_c_of_type_Boolean = true;
        }
      }
    }
  }
  
  public static void b(TencentWebViewProxy paramTencentWebViewProxy)
  {
    a(paramTencentWebViewProxy);
  }
  
  private void b(boolean paramBoolean)
  {
    this.jdField_b_of_type_Boolean = false;
    TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
    if (localTencentMediaPlayerBridge != null) {
      localTencentMediaPlayerBridge.exitFullsceenByMediaPlayer();
    }
  }
  
  private boolean b(int paramInt1, int paramInt2)
  {
    return (paramInt2 > 1) && (paramInt1 > 1) && (paramInt2 / paramInt1 > 1.0D);
  }
  
  private void c()
  {
    if (this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer == null) {}
    for (;;)
    {
      try
      {
        if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null) || (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getQQBrowserClient() == null)) {
          break label376;
        }
        Object localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getQQBrowserClient().getVideoPlayerHelper();
        if (localObject1 != null)
        {
          localObject3 = new Bundle();
          ((Bundle)localObject3).putString("eventName", "getPlayerEnv");
          localObject3 = invokeWebViewClientMiscCallBackMethod("onVideoEvent", (Bundle)localObject3);
          if (!(localObject3 instanceof PlayerEnv)) {
            break label381;
          }
          localObject3 = (PlayerEnv)localObject3;
          localObject4 = new Bundle();
          ((Bundle)localObject4).putString("eventName", "getFeatureSupport");
          localObject4 = invokeWebViewClientMiscCallBackMethod("onVideoEvent", (Bundle)localObject4);
          if (!(localObject4 instanceof FeatureSupport)) {
            break label386;
          }
          localObject4 = (FeatureSupport)localObject4;
          this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = new a(getContext(), this, (H5VideoInfo)null, (FeatureSupport)localObject4, (PlayerEnv)localObject3, (IVideoPlayerHelper)localObject1);
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
          if ((((PlayerEnv)localObject1).isStandardFullScreen()) || (d())) {
            ((FeatureSupport)localObject3).addFeatureFlag(2048L);
          }
          if ((c()) && (!((FeatureSupport)localObject3).support(2048L))) {
            ((FeatureSupport)localObject3).addFeatureFlag(256L);
          }
          ((FeatureSupport)localObject3).addFeatureFlag(16L);
          localObject4 = new H5VideoInfo();
          ((H5VideoInfo)localObject4).mWebUrl = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl();
          ((H5VideoInfo)localObject4).mVideoUrl = this.jdField_a_of_type_JavaLangString;
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
        ((StringBuilder)localObject3).append("J H5VideoProxy.initPlayerIfNeed exception = ");
        ((StringBuilder)localObject3).append(Log.getStackTraceString(localException));
        Log.e("qbx5media", ((StringBuilder)localObject3).toString());
      }
      if ((this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer == null) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null)) {
        this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = new NullVideoPlayer(getContext());
      }
      return;
      label376:
      Object localObject2 = null;
      continue;
      label381:
      Object localObject3 = null;
      continue;
      label386:
      Object localObject4 = null;
    }
  }
  
  private void c(final View paramView)
  {
    this.jdField_b_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        if (!d.a(d.this)) {
          return;
        }
        d.a(d.this, true);
        d locald = d.this;
        d.a(locald, new FakeFullScreenPanel(locald.getContext(), d.a(d.this), paramView, new FakeFullScreenPanel.IFakeFullSceenPanelClient()
        {
          public boolean onHidePanelView(View paramAnonymous2View)
          {
            paramAnonymous2View = d.a(d.6.this.jdField_a_of_type_ComTencentSmttWebkitH5videoD, "onHideVieoView", null, paramAnonymous2View, null, null, null);
            if ((paramAnonymous2View instanceof Boolean)) {
              return ((Boolean)paramAnonymous2View).booleanValue();
            }
            return false;
          }
          
          public boolean onShowPanelView(View paramAnonymous2View)
          {
            paramAnonymous2View = d.a(d.6.this.jdField_a_of_type_ComTencentSmttWebkitH5videoD, "onShowVieoView", null, paramAnonymous2View, null, null, null);
            if ((paramAnonymous2View instanceof Boolean)) {
              return ((Boolean)paramAnonymous2View).booleanValue();
            }
            return false;
          }
          
          public boolean shouldAttachToWebView()
          {
            Object localObject = d.a(d.6.this.jdField_a_of_type_ComTencentSmttWebkitH5videoD, "shouldAttachToWebView", null, d.6.this.jdField_a_of_type_AndroidViewView, null, null, null);
            if ((localObject instanceof Boolean)) {
              return ((Boolean)localObject).booleanValue();
            }
            return false;
          }
        }));
        d.a(d.this).b(d.a(d.this), d.b(d.this), d.c(d.this), d.d(d.this));
        d.a(d.this).c(d.b(d.this));
        d.a(d.this).b(d.c(d.this));
        d.a(d.this).a(d.d(d.this));
        if (Build.VERSION.SDK_INT >= 21) {
          d.a(d.this).a(d.a(d.this));
        }
        d.a(d.this).a();
        d.a(d.this, false);
        if ((!d.this.isVisible()) && (!d.e(d.this))) {
          d.a(d.this).postDelayed(d.a(d.this), 1000L);
        }
        d.a(d.this, null);
        d.b(d.this);
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
  
  private static void c(d paramd)
  {
    try
    {
      Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = ((WeakReference)localIterator.next()).get();
        if (localObject == paramd) {
          return;
        }
      }
      jdField_a_of_type_JavaUtilList.add(new WeakReference(paramd));
      return;
    }
    finally {}
  }
  
  private boolean c()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    return (localTencentWebViewProxy != null) && (localTencentWebViewProxy.getSettingsExtension() != null) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isSupportLiteWnd());
  }
  
  private void d()
  {
    if (this.jdField_c_of_type_Boolean)
    {
      IX5WebChromeClient localIX5WebChromeClient = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebChromeClient();
      if (localIX5WebChromeClient != null) {
        localIX5WebChromeClient.onHideCustomView();
      }
    }
  }
  
  private void d(final View paramView)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        d.a(d.this, paramView);
      }
    });
  }
  
  private static void d(d paramd)
  {
    int i1 = 0;
    for (;;)
    {
      try
      {
        Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
        if (localIterator.hasNext()) {
          if (((WeakReference)localIterator.next()).get() != paramd) {
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
  
  private boolean d()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    return (localTencentWebViewProxy != null) && (localTencentWebViewProxy.getSettingsExtension() != null) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isStandardFullScreen());
  }
  
  private void e()
  {
    String str;
    if (isAllowRenderingWithinPage())
    {
      str = SmttServiceProxy.getInstance().getExtendJsRule("FullScreenEventDetect");
      if (str != null)
      {
        TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
        if (localTencentMediaPlayerBridge != null) {
          localTencentMediaPlayerBridge.evaluateJavascript(str);
        }
      }
    }
    if (SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 213))
    {
      str = SmttServiceProxy.getInstance().getExtendJsRule("ExtractTitle");
      if ((str != null) && (this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge != null)) {
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
                d.a(d.this, paramAnonymousString);
              }
              return;
            }
          }
        });
      }
    }
  }
  
  private boolean e()
  {
    Activity localActivity;
    if ((getContext() instanceof Activity)) {
      localActivity = (Activity)getContext();
    } else {
      localActivity = null;
    }
    return (localActivity != null) && (!localActivity.isFinishing());
  }
  
  private void f()
  {
    if (SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 256))
    {
      String str = SmttServiceProxy.getInstance().getExtendJsRule("ExtractPageInfo");
      if ((str != null) && (this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge != null)) {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.evaluateJavascript(str, new ValueCallback()
        {
          public void a(String paramAnonymousString)
          {
            if (d.a(d.this) != null)
            {
              Bundle localBundle = new Bundle();
              localBundle.putString("pageInfo", paramAnonymousString);
              d.a(d.this).onMiscCallBack("onPageInfoExtracted", localBundle);
            }
          }
        });
      }
    }
  }
  
  @TargetApi(13)
  private boolean f()
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$a;
    if (localObject1 != null) {
      return ((a)localObject1).jdField_a_of_type_Boolean;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$a = new a();
    boolean bool1 = getContext() instanceof Activity;
    boolean bool2 = false;
    Object localObject2;
    if (bool1)
    {
      localObject2 = (Activity)getContext();
      localObject1 = ((Activity)localObject2).getPackageManager();
      localObject2 = ((Activity)localObject2).getComponentName();
    }
    try
    {
      localObject1 = ((PackageManager)localObject1).getActivityInfo((ComponentName)localObject2, 128);
      localObject2 = this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$a;
      bool1 = bool2;
      if ((0x80 & ((ActivityInfo)localObject1).configChanges) != 0)
      {
        bool1 = bool2;
        if ((((ActivityInfo)localObject1).configChanges & 0x400) != 0)
        {
          bool1 = bool2;
          if ((((ActivityInfo)localObject1).configChanges & 0x20) != 0) {
            bool1 = true;
          }
        }
      }
      ((a)localObject2).jdField_a_of_type_Boolean = bool1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$a.jdField_a_of_type_Boolean = false;
    return this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$a.jdField_a_of_type_Boolean;
  }
  
  private void g()
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.evaluateJavascript("javascript:var h5Videos = document.getElementsByTagName('video');var overflowMap = new Map();for(var i=0;i<h5Videos.length;i++) {\tif(!h5Videos[i].paused) {\t\tvar parentElement = h5Videos[i];\t\tfor(; parentElement != document.body;) {\t\t\toverflowMap.set(parentElement, parentElement.style.overflow);\t\t\tparentElement.style.overflow = 'hidden';\t\t\tparentElement = parentElement.parentElement;}\t\toverflowMap.set(document.body, document.body.style.overflow);\t\tdocument.body.style.overflow = 'hidden';break;}}", new ValueCallback()
    {
      public void a(String paramAnonymousString) {}
    });
  }
  
  private boolean g()
  {
    boolean bool1;
    if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isX5HardwareAcceleratedForVideo()) && (Build.VERSION.SDK_INT >= 14)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2 = bool1;
    if (!bool1)
    {
      Object localObject = a("forceHardware", null, null, null, null, null);
      bool2 = bool1;
      if ((localObject instanceof Boolean)) {
        bool2 = ((Boolean)localObject).booleanValue();
      }
    }
    return bool2;
  }
  
  private void h()
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.evaluateJavascript("javascript:if(overflowMap && overflowMap.size>0){\toverflowMap.forEach(function (value, key, map) {\tkey.style.overflow = value;});\toverflowMap = '';}", new ValueCallback()
    {
      public void a(String paramAnonymousString) {}
    });
  }
  
  private boolean h()
  {
    boolean bool1 = TencentAwContent.ENABLE_SW_DRAW;
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (!g()) {
      return false;
    }
    boolean bool3 = i();
    boolean bool4 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.videoModeDisabled();
    bool1 = bool2;
    if (!this.o)
    {
      bool1 = bool2;
      if (!j())
      {
        bool1 = bool2;
        if (!bool3)
        {
          bool1 = bool2;
          if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null) {
            if ((!this.r) && (!this.p) && (!bool4))
            {
              bool1 = bool2;
              if (!k()) {}
            }
            else
            {
              bool1 = true;
            }
          }
        }
      }
    }
    return bool1;
  }
  
  private void i()
  {
    this.jdField_g_of_type_Int = 3;
  }
  
  private boolean i()
  {
    boolean bool1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.videoModeDisabled();
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (g())
    {
      if (l()) {
        return false;
      }
      bool1 = bool2;
      if (e())
      {
        bool1 = bool2;
        if (!this.s)
        {
          bool1 = bool2;
          if (this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$b == b.b) {
            bool1 = true;
          }
        }
      }
      return bool1;
    }
    return false;
  }
  
  private void j()
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
        d.this.start();
      }
    });
    localFrameLayout.addView(localImageButton, new FrameLayout.LayoutParams(-2, -2, 17));
    a(localFrameLayout, this.jdField_b_of_type_Int, this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
  }
  
  private boolean j()
  {
    return (this.n) && (!SmttServiceProxy.getInstance().isWebUrlInCloudList(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), 210));
  }
  
  private void k()
  {
    boolean bool1 = isAllowRenderingWithinPage();
    boolean bool2 = i();
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("isHardwareAccelerated", bool1);
    localBundle.putBoolean("isVR", m());
    localBundle.putBoolean("fakeFullScreen", bool2);
    a().onMiscCallBack("updateVideoInfo", localBundle);
    if ((a().getScreenMode() == 107) && (!bool1)) {
      a().switchScreen(b());
    }
  }
  
  private boolean k()
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$b == b.d) && (!this.s);
  }
  
  private void l()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 14) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().isFloatVideoEnabled()) && (!isAllowRenderingWithinPage())) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_e_of_type_Boolean = bool;
  }
  
  private boolean l()
  {
    TencentWebViewProxy localTencentWebViewProxy = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    return ((localTencentWebViewProxy instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)localTencentWebViewProxy).isMiniQB());
  }
  
  private void m()
  {
    this.jdField_i_of_type_Boolean = true;
    FakeFullScreenPanel localFakeFullScreenPanel = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localFakeFullScreenPanel != null)
    {
      localFakeFullScreenPanel.b();
      this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel = null;
      h();
    }
    this.jdField_i_of_type_Boolean = false;
  }
  
  private boolean m()
  {
    return (this.jdField_h_of_type_Boolean) && (!isVisible());
  }
  
  private void n()
  {
    this.m = true;
  }
  
  private void o()
  {
    a().switchScreen(101);
    dispatchPause(3);
  }
  
  public void a()
  {
    a().active();
  }
  
  public void a(float paramFloat1, float paramFloat2)
  {
    FakeFullScreenPanel localFakeFullScreenPanel = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localFakeFullScreenPanel != null) {
      localFakeFullScreenPanel.a(paramFloat1, paramFloat2);
    }
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    k();
    if (isVisible()) {
      this.jdField_a_of_type_AndroidOsHandler.removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.jdField_e_of_type_Boolean) && (getContext().getResources().getConfiguration().orientation != 2))
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
                if (d.a(d.this) != null) {
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
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle(9);
    localBundle.putInt("deltaX", paramInt1);
    localBundle.putInt("deltaY", paramInt2);
    localBundle.putInt("scrollX", paramInt3);
    localBundle.putInt("scrollY", paramInt4);
    localBundle.putInt("scrollRangeX", paramInt5);
    localBundle.putInt("scrollRangeY", paramInt6);
    localBundle.putInt("maxOverScrollX", paramInt7);
    localBundle.putInt("maxOverScrollY", paramInt8);
    localBundle.putBoolean("isTouchEvent", paramBoolean);
    a().onMiscCallBack("fakeFullScreenOnOverScroll", localBundle);
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
    if (paramBoolean)
    {
      a();
      return;
    }
    if (a().getScreenMode() == 107) {
      o();
    }
    b();
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel != null;
  }
  
  public boolean a(int paramInt1, int paramInt2)
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel != null)
    {
      Object localObject = new Bundle();
      ((Bundle)localObject).putString("eventName", "preventPageScrollTo");
      ((Bundle)localObject).putInt("x", paramInt1);
      ((Bundle)localObject).putInt("y", paramInt2);
      localObject = invokeWebViewClientMiscCallBackMethod("onVideoEvent", (Bundle)localObject);
      if ((localObject instanceof Boolean)) {
        return ((Boolean)localObject).booleanValue();
      }
      return true;
    }
    return false;
  }
  
  public void b()
  {
    a().deactive();
  }
  
  public boolean b()
  {
    return this.jdField_i_of_type_Boolean;
  }
  
  public boolean canPagePlay()
  {
    boolean bool1 = f();
    boolean bool2 = true;
    if (!bool1) {
      return true;
    }
    if (a() != 201)
    {
      if (this.jdField_g_of_type_Boolean) {
        return false;
      }
      WebSettingsExtension localWebSettingsExtension = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension();
      if ((localWebSettingsExtension == null) || (localWebSettingsExtension.getVideoScreenMode() != WebSettingsExtension.a.b))
      {
        bool1 = bool2;
        if (isAllowRenderingWithinPage()) {
          break label111;
        }
        bool1 = bool2;
        if (j()) {
          break label111;
        }
        bool1 = bool2;
        if (!SmttServiceProxy.getInstance().isDefaultVideoFullScreenPlay(l())) {
          break label111;
        }
        if (SmttServiceProxy.getInstance().isForceVideoPagePlay(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl(), l())) {
          return true;
        }
      }
      bool1 = false;
      label111:
      return bool1;
    }
    return false;
  }
  
  public void createSurfaceTexture()
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (d.a(d.this) != null)
        {
          d.a(d.this).onSurfaceTextureCreated(d.a(d.this));
          return;
        }
        if (d.a(d.this) != null) {
          d.a(d.this).createSurfaceTexture();
        }
      }
    });
  }
  
  public void dispatchPause(int paramInt)
  {
    this.jdField_g_of_type_Int = paramInt;
    this.t = false;
    TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
    if (localTencentMediaPlayerBridge != null) {
      localTencentMediaPlayerBridge.pauseByMediaPlayer(this.jdField_g_of_type_Int, canPagePlay());
    }
    if (!this.jdField_d_of_type_Boolean) {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          d.this.pause();
        }
      });
    }
  }
  
  public void dispatchPlay(int paramInt)
  {
    setPlayType(3);
    this.t = true;
    this.jdField_g_of_type_Int = paramInt;
    TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
    if (localTencentMediaPlayerBridge != null) {
      localTencentMediaPlayerBridge.playByMediaPlayer();
    }
    if (!this.jdField_d_of_type_Boolean) {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          d.this.start();
        }
      });
    }
  }
  
  public void dispatchSeek(int paramInt1, int paramInt2)
  {
    TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
    if (localTencentMediaPlayerBridge != null) {
      localTencentMediaPlayerBridge.seekByMediaPlayer(paramInt1, paramInt2);
    }
  }
  
  public void dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    a(paramMotionEvent, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getViewManager().a());
  }
  
  public void enterFullscreen()
  {
    this.jdField_b_of_type_Boolean = true;
    if (!a(a().getScreenMode())) {
      a().switchScreen(b());
    }
  }
  
  public void exitFullscreen()
  {
    if ((this.jdField_b_of_type_Boolean) && (a(a().getScreenMode())) && (e.a().n())) {
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
          if ((this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge != null) && (!TextUtils.isEmpty(paramString)))
          {
            this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge.videoUrlRedirect(paramString);
            return null;
          }
        }
        else
        {
          if ("onHaveVideoData".equals(paramString))
          {
            if (this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge != null)
            {
              this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge.onHaveVideoData();
              return null;
            }
          }
          else if ("onNoVideoData".equals(paramString))
          {
            if (this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge != null)
            {
              this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge.onNoVideoData();
              return null;
            }
          }
          else
          {
            if (("switchScreen".equals(paramString)) && (paramBundle != null))
            {
              int i1 = paramBundle.getInt("screenMode", 100);
              if (i1 != 100) {
                a().switchScreen(i1);
              }
              if (getScreenMode() != i1) {
                break label255;
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
      label255:
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
    boolean bool2 = a(this.jdField_a_of_type_JavaLangString);
    boolean bool1 = false;
    if (bool2) {
      return false;
    }
    if ((h()) || (i())) {
      bool1 = true;
    }
    return bool1;
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
    m();
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
      c(paramView);
      break;
    case 106: 
      a(paramInt1);
      if (this.jdField_b_of_type_Boolean) {
        b(true);
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
        d();
      }
      if (this.jdField_b_of_type_Boolean) {
        b(true);
      }
      break;
    case 102: 
    case 104: 
    case 105: 
      if (ContextHolder.getInstance().isTbsDevelopMode()) {
        d(paramView);
      }
      break;
    case 101: 
      if (a(paramInt2)) {
        a(paramView);
      }
      if ((paramView != null) && (paramView.getParent() == null)) {
        if (this.jdField_g_of_type_Boolean)
        {
          j();
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
                d locald = d.this;
                d.a(locald, paramView, d.a(locald), d.b(d.this), d.c(d.this), d.d(d.this));
                if (d.a(d.this) != null) {
                  d.a(d.this).a(false);
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
        d();
      }
      break;
    }
    if (paramInt1 != 103)
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null)) {
        throw new AssertionError();
      }
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
  
  public void onError()
  {
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null) {
      localIMediaPlayerListener.onError(this, 1, 64529);
    }
  }
  
  public void onError(int paramInt1, int paramInt2)
  {
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null)
    {
      if ((paramInt1 >= 43537) && (paramInt1 <= 45536))
      {
        localIMediaPlayerListener.onError(this, 100000, paramInt2);
        return;
      }
      this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener.onError(this, 1, 64529);
    }
  }
  
  public void onInfo(int paramInt1, int paramInt2)
  {
    TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
    if (localTencentMediaPlayerBridge != null) {
      localTencentMediaPlayerBridge.onInfo(paramInt1, paramInt2);
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
    this.jdField_d_of_type_Boolean = true;
    IMediaPlayer.IMediaPlayerListener localIMediaPlayerListener = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localIMediaPlayerListener != null) {
      localIMediaPlayerListener.onPrepared(this);
    }
    this.jdField_a_of_type_AndroidOsHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            Object localObject = d.a(d.this).onMiscCallBack("getCurrentBitmapFrame", new Bundle());
            if ((localObject instanceof Bitmap))
            {
              localObject = (Bitmap)localObject;
              int i = ((Bitmap)localObject).getWidth();
              int j = ((Bitmap)localObject).getHeight();
              int k = Math.min(360, i);
              try
              {
                final Bitmap localBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(k, k * j / i, Bitmap.Config.ARGB_8888);
                Canvas localCanvas = new Canvas(localBitmap);
                Paint localPaint = new Paint();
                localPaint.setColor(-16777216);
                localCanvas.drawBitmap((Bitmap)localObject, new Rect(0, 0, i, j), new Rect(0, 0, localBitmap.getWidth(), localBitmap.getHeight()), localPaint);
                ((Bitmap)localObject).recycle();
                d.a(d.this).post(new Runnable()
                {
                  public void run()
                  {
                    if (d.a(d.this) != null) {
                      d.a(d.this).onSnapshotReady(localBitmap);
                    }
                  }
                });
                return;
              }
              catch (Throwable localThrowable)
              {
                localThrowable.printStackTrace();
              }
            }
          }
        });
      }
    }, 1000L);
  }
  
  public boolean onScreenModeChangeBefore(int paramInt1, int paramInt2)
  {
    Object localObject = new Bundle();
    ((Bundle)localObject).putInt("from", paramInt1);
    ((Bundle)localObject).putInt("to", paramInt2);
    localObject = invokeWebViewClientMiscCallBackMethod("onVideoScreenModeChangeBefore", (Bundle)localObject);
    if ((localObject instanceof Boolean)) {
      return ((Boolean)localObject).booleanValue();
    }
    return this.jdField_l_of_type_Boolean;
  }
  
  public void onScreenModeChanged(int paramInt1, int paramInt2)
  {
    int i1;
    try
    {
      Bundle localBundle = new Bundle();
      localBundle.putInt("from", paramInt1);
      localBundle.putInt("to", paramInt2);
      invokeWebViewClientMiscCallBackMethod("onVideoScreenModeChanged", localBundle);
      boolean bool = a(paramInt1);
      i1 = 0;
      if (bool) {
        break label116;
      }
      if (107 != paramInt1) {
        break label111;
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
        this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge.onMediaPlayerExitFullScreen();
      }
      if ((paramInt1 == 0) && (i1 != 0)) {
        this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge.onMediaPlayerEnterFullScreen();
      }
      return;
      label111:
      paramInt1 = 0;
      break;
      label116:
      paramInt1 = 1;
      break;
      i1 = 1;
    }
  }
  
  public void onSeekComplete(int paramInt)
  {
    if ((this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener != null) && (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString))) {
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
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localObject != null) {
      ((FakeFullScreenPanel)localObject).a();
    }
    localObject = this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener;
    if (localObject != null) {
      ((IMediaPlayer.IMediaPlayerListener)localObject).onVideoStartShowing(this);
    }
    localObject = new Bundle();
    ((Bundle)localObject).putString("eventName", "onVideoStartShowing");
    invokeWebViewClientMiscCallBackMethod("onVideoEvent", (Bundle)localObject);
  }
  
  public void onVideoViewMove(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void pause()
  {
    a().pause(this.jdField_g_of_type_Int);
    i();
  }
  
  public void preload(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (paramString1 != null)
    {
      boolean bool2 = false;
      if (shouldOverrideStandardPlay(false)) {
        return;
      }
      if (paramString1.startsWith("file://")) {
        this.jdField_a_of_type_JavaLangString = paramString1.substring(7);
      } else {
        this.jdField_a_of_type_JavaLangString = paramString1;
      }
      if (!TextUtils.isEmpty(paramString5)) {
        paramString4 = paramString5;
      }
      this.jdField_c_of_type_JavaLangString = paramString4;
      paramString2 = new H5VideoInfo();
      paramString2.mVideoUrl = this.jdField_a_of_type_JavaLangString;
      paramString2.mWebUrl = a();
      paramString1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
      boolean bool1 = bool2;
      if (paramString1 != null)
      {
        bool1 = bool2;
        if (paramString1.isPrivateBrowsingEnabled()) {
          bool1 = true;
        }
      }
      paramString2.mPrivateBrowsingEnabled = bool1;
      paramString2.mSnifferReffer = this.jdField_c_of_type_JavaLangString;
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null)) {
        throw new AssertionError();
      }
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings() != null) {
        paramString1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgentString();
      } else {
        paramString1 = null;
      }
      paramString2.mUA = paramString1;
      paramString2.mExtraData.putBoolean("fakeFullScreen", i());
      a().preload(paramString2);
      e();
      return;
    }
  }
  
  public void prepareAsync() {}
  
  @TargetApi(11)
  public void release()
  {
    i();
    a().unmountProxy();
    this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge = null;
    this.jdField_a_of_type_OrgChromiumTencentVideoIMediaPlayer$IMediaPlayerListener = null;
    Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
    if (localObject != null)
    {
      a((s.a)localObject);
      this.jdField_a_of_type_ComTencentSmttWebkitS$a = null;
    }
    m();
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
    d(this);
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getH5VideoProxy() == this) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setH5VideoProxy(null);
    }
  }
  
  public void releaseSurfaceTexture()
  {
    TencentMediaPlayerBridge localTencentMediaPlayerBridge = this.jdField_a_of_type_OrgChromiumTencentMediaTencentMediaPlayerBridge;
    if (localTencentMediaPlayerBridge != null) {
      localTencentMediaPlayerBridge.releaseSurfaceTexture();
    }
    this.jdField_a_of_type_JavaLangObject = null;
  }
  
  public void reset(String paramString)
  {
    if ((TextUtils.equals(this.jdField_a_of_type_JavaLangString, paramString)) && (this.jdField_d_of_type_Boolean)) {
      this.jdField_f_of_type_Boolean = true;
    }
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_d_of_type_JavaLangString = null;
    this.jdField_d_of_type_Boolean = false;
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
    this.jdField_a_of_type_JavaLangString = paramUri.toString();
    paramContext = new Bundle();
    paramContext.putString("url", this.jdField_a_of_type_JavaLangString);
    paramContext = invokeWebViewClientMiscCallBackMethod("shouldInterceptMediaUrl", paramContext);
    if ((paramContext instanceof String)) {
      this.jdField_a_of_type_JavaLangString = ((String)paramContext);
    }
    paramContext = SmttServiceProxy.getInstance();
    paramUri = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    paramContext.interceptVideoPlay(paramUri, paramUri.getContext(), this.jdField_a_of_type_AndroidOsHandler);
  }
  
  public void setDataSource(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) {}
  
  public void setIsFixedPositionVideo(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void setIsVRVideo(boolean paramBoolean)
  {
    if (this.jdField_h_of_type_Boolean == paramBoolean) {
      return;
    }
    boolean bool = m();
    this.jdField_h_of_type_Boolean = paramBoolean;
    FakeFullScreenPanel localFakeFullScreenPanel = this.jdField_a_of_type_ComTencentSmttWebkitH5videoFakeFullScreenPanel;
    if (localFakeFullScreenPanel != null) {
      localFakeFullScreenPanel.c(paramBoolean);
    }
    if (bool != m()) {
      k();
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
  
  public void setPlaybackRate(double paramDouble)
  {
    a().setPlaybackRate(paramDouble);
  }
  
  public void setSurface(Surface paramSurface) {}
  
  public void setSurfaceTexture(Object paramObject)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject;
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
        this.jdField_e_of_type_Boolean = false;
        return;
      }
      if ("true".equals(paramString2)) {
        l();
      }
    }
    else
    {
      if ("x5-video-player-type".equals(paramString1))
      {
        this.jdField_a_of_type_ComTencentSmttWebkitH5videoD$b = b.a(paramString2);
        return;
      }
      if ("x5-video-player-fullscreen".equals(paramString1))
      {
        this.jdField_j_of_type_Boolean = "true".equals(paramString2);
        return;
      }
      if ("origin_src".equals(paramString1))
      {
        this.jdField_d_of_type_JavaLangString = paramString2;
        return;
      }
      if ("x5-video-orientation".equals(paramString1))
      {
        boolean bool1 = paramString2.contains("landscape");
        boolean bool2 = paramString2.contains("portrait");
        paramString1 = new Bundle();
        paramString1.putBoolean("X5VideoOrientationAttrLandscape", bool1);
        paramString1.putBoolean("X5VideoOrientationAttrPortrait", bool2);
        a().onMiscCallBack("setOrientation", paramString1);
        return;
      }
      if ("x5-playsinline".equals(paramString1))
      {
        this.n = true;
        return;
      }
      if ("x5-record-play-position".equals(paramString1))
      {
        this.jdField_k_of_type_Boolean = "true".equals(paramString2);
        return;
      }
      if (("x5-video-vr-type".equals(paramString1)) || ("x5-video-vr-mode".equals(paramString1))) {
        this.o = true;
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
      Log.e("qbx5media", String.format("J H5VideoProxy.setVideoAttr exceptions (this = %s, attrs = %s, key= %s, value = %s)", new Object[] { this, paramString, str1, str2 }));
    }
  }
  
  public void setVideoInfo(H5VideoInfo paramH5VideoInfo) {}
  
  public void setVideoPlayer(IH5VideoPlayer paramIH5VideoPlayer)
  {
    super.setVideoPlayer(paramIH5VideoPlayer);
    if (paramIH5VideoPlayer == null)
    {
      a(this.jdField_a_of_type_ComTencentSmttWebkitS$a);
      paramIH5VideoPlayer = new TempLoseVideoPlayer(getContext());
      paramIH5VideoPlayer.a(a().getVideoWidth(), a().getVideoHeight());
      paramIH5VideoPlayer.a(a().getDuration());
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = paramIH5VideoPlayer;
      return;
    }
    if ((paramIH5VideoPlayer instanceof IX5VideoPlayer)) {
      this.jdField_a_of_type_ComTencentMttVideoExportIX5VideoPlayer = ((IX5VideoPlayer)paramIH5VideoPlayer);
    }
  }
  
  public void setVolume(float paramFloat1, float paramFloat2)
  {
    a().setVolume(paramFloat1, paramFloat2);
  }
  
  public boolean shouldOverrideStandardPlay(boolean paramBoolean)
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localObject != null) && (((TencentWebViewProxy)localObject).getQQBrowserClient() != null))
    {
      localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getQQBrowserClient();
      boolean bool;
      if (this.jdField_f_of_type_Int == 3) {
        bool = true;
      } else {
        bool = false;
      }
      if (((IX5QQBrowserClient)localObject).shouldOverrideStandardPlay(paramBoolean, bool, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getAutoPlayFlag(), a())) {
        return true;
      }
    }
    return false;
  }
  
  @TargetApi(11)
  public void start()
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null)
    {
      if (TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
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
      boolean bool2 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getAutoPlayFlag();
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setAutoPlayNextVideo(null, false);
      if (((localObject1 == null) || ((((WebSettingsExtension)localObject1).getVideoScreenMode() != WebSettingsExtension.a.b) && (((WebSettingsExtension)localObject1).getVideoPlaybackRequiresUserGesture()) && (!this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.videoModeDisabled())) || (this.jdField_f_of_type_Int == 0)) && (this.jdField_f_of_type_Int != 3) && (!bool2)) {
        i1 = 0;
      } else {
        i1 = 1;
      }
      boolean bool3 = i1 | this.t;
      if (bool3) {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.setH5VideoProxy(this);
      }
      if ((this.jdField_l_of_type_Boolean) && (!bool2)) {
        i1 = 101;
      } else {
        i1 = a().getScreenMode();
      }
      int i2 = i1;
      if (i1 == 0) {
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
        if (bool3)
        {
          i1 = i2;
          if (!canPagePlay()) {
            i1 = b();
          }
        }
      }
      boolean bool4 = isAllowRenderingWithinPage();
      boolean bool1;
      if ((!h()) && (bool4)) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      i2 = i1;
      if (i1 == 101)
      {
        i2 = i1;
        if (bool1) {
          i2 = 107;
        }
      }
      localObject3 = new H5VideoInfo();
      ((H5VideoInfo)localObject3).mVideoUrl = this.jdField_a_of_type_JavaLangString;
      ((H5VideoInfo)localObject3).mPostion = 0;
      ((H5VideoInfo)localObject3).mFromWhere = 1;
      ((H5VideoInfo)localObject3).mScreenMode = i2;
      ((H5VideoInfo)localObject3).mSnifferReffer = this.jdField_c_of_type_JavaLangString;
      ((H5VideoInfo)localObject3).mHasClicked = bool3;
      ((H5VideoInfo)localObject3).mAutoPlayVideo = bool2;
      ((H5VideoInfo)localObject3).mExtraData.putBoolean("isHardwareAccelerated", bool4);
      ((H5VideoInfo)localObject3).mExtraData.putBoolean("isVR", m());
      ((H5VideoInfo)localObject3).mExtraData.putBoolean("fakeFullScreen", bool1);
      ((H5VideoInfo)localObject3).mExtraData.putBoolean("canUseHardware", this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isX5HardwareAcceleratedForVideo());
      ((H5VideoInfo)localObject3).mExtraData.putBoolean("webviewsurfaceviewmdoe", this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isSurfaceviewMode());
      if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy instanceof X5WebViewAdapter))
      {
        ((H5VideoInfo)localObject3).mExtraData.putBoolean("isMiniQB", l());
        ((H5VideoInfo)localObject3).mExtraData.putInt("miniQBSourcePosID", ((X5WebViewAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy).getMiniQBSourcePosID());
      }
      if (!this.jdField_k_of_type_Boolean) {
        ((H5VideoInfo)localObject3).mExtraData.putString("disableSavePlayPosition", "true");
      }
      if ((!bool4) && (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isX5HardwareAcceleratedForVideo())) {
        ((H5VideoInfo)localObject3).mExtraData.putBoolean("useTextureView", true);
      }
      localObject1 = this.jdField_a_of_type_ComTencentSmttWebkitS$a;
      if (localObject1 != null) {
        ((s.a)localObject1).a(0);
      }
      ((H5VideoInfo)localObject3).mWebUrl = a();
      if (TextUtils.isEmpty(this.jdField_e_of_type_JavaLangString)) {
        localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getTitle();
      } else {
        localObject1 = this.jdField_e_of_type_JavaLangString;
      }
      ((H5VideoInfo)localObject3).mWebTitle = ((String)localObject1);
      localObject1 = localObject2;
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings() != null) {
        localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettings().getUserAgentString();
      }
      ((H5VideoInfo)localObject3).mUA = ((String)localObject1);
      ((H5VideoInfo)localObject3).mPostion = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSharedVideoTime();
      ((H5VideoInfo)localObject3).mPrivateBrowsingEnabled = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.isPrivateBrowsingEnabled();
      if (this.jdField_d_of_type_JavaLangString != null) {
        ((H5VideoInfo)localObject3).mExtraData.putString("backupUrl", this.jdField_d_of_type_JavaLangString);
      }
      if (this.jdField_l_of_type_Boolean) {
        f();
      }
      this.jdField_l_of_type_Boolean = false;
      a().play((H5VideoInfo)localObject3, this.jdField_g_of_type_Int);
      i();
      if (TextUtils.equals(this.jdField_a_of_type_JavaLangString, this.jdField_b_of_type_JavaLangString))
      {
        if (this.jdField_f_of_type_Boolean)
        {
          this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
          {
            public void run()
            {
              d locald = d.this;
              locald.onVideoSizeChanged(d.e(locald), d.f(d.this));
              locald = d.this;
              locald.onPrepared(d.g(locald), d.e(d.this), d.f(d.this));
            }
          });
          this.jdField_f_of_type_Boolean = false;
        }
      }
      else {
        this.jdField_d_of_type_Boolean = false;
      }
      this.jdField_b_of_type_JavaLangString = this.jdField_a_of_type_JavaLangString;
      return;
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
      k();
      if ((!this.q) && (!bool2) && (a().getScreenMode() == 107)) {
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
  
  class a
  {
    boolean jdField_a_of_type_Boolean = true;
    
    a() {}
  }
  
  private static enum b
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitH5videoD$b = new b("PlayerTypeUnknown", 0);
      b = new b("PlayerTypeH5", 1);
      c = new b("PlayerTypeVR", 2);
      d = new b("PlayerTypeH5Page", 3);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitH5videoD$b = new b[] { jdField_a_of_type_ComTencentSmttWebkitH5videoD$b, b, c, d };
    }
    
    private b() {}
    
    public static b a(String paramString)
    {
      if ("h5".equals(paramString)) {
        return b;
      }
      if ("vr".equals(paramString)) {
        return c;
      }
      if ("h5-page".equals(paramString)) {
        return d;
      }
      return jdField_a_of_type_ComTencentSmttWebkitH5videoD$b;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */