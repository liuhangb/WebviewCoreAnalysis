package com.tencent.smtt.webkit.h5video;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.s.a;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class b
  extends FrameLayout
{
  private static SparseArray<s.a> a = new SparseArray();
  
  public b(Context paramContext, TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramContext);
    SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
    boolean bool = paramTencentWebViewProxy instanceof IX5WebView;
    Object localObject = null;
    if (bool) {
      paramTencentWebViewProxy = (IX5WebView)paramTencentWebViewProxy;
    } else {
      paramTencentWebViewProxy = null;
    }
    paramTencentWebViewProxy = localSmttServiceProxy.getFreeWifiGuideView(paramContext, paramTencentWebViewProxy);
    paramContext = (Context)localObject;
    if ((paramTencentWebViewProxy instanceof ViewGroup)) {
      paramContext = (ViewGroup)paramTencentWebViewProxy;
    }
    if (paramContext == null) {
      return;
    }
    paramTencentWebViewProxy = paramContext.getLayoutParams();
    int i = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_video_freewifi_width", "dimen"));
    int j = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_video_freewifi_height", "dimen"));
    int k = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_video_freewifi_paddingleft", "dimen"));
    int m = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_video_freewifi_paddingright", "dimen"));
    if (paramTencentWebViewProxy != null) {
      i = paramTencentWebViewProxy.width;
    }
    if (paramTencentWebViewProxy != null) {
      j = paramTencentWebViewProxy.height;
    }
    paramTencentWebViewProxy = new FrameLayout.LayoutParams(i, j, 51);
    paramTencentWebViewProxy.setMargins(k, m, 0, 0);
    addView(paramContext, paramTencentWebViewProxy);
  }
  
  public static void a(int paramInt, TencentWebViewProxy paramTencentWebViewProxy)
  {
    s.a locala = (s.a)a.get(paramInt);
    if (locala != null)
    {
      paramTencentWebViewProxy.removeViewForce(locala);
      a.remove(paramInt);
    }
  }
  
  public static void a(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TencentWebViewProxy paramTencentWebViewProxy)
  {
    s.a locala = (s.a)a.get(paramInt1);
    int i = paramTencentWebViewProxy.getRealWebView().getContentWidth();
    int j = 1;
    if ((paramInt2 < i) && (paramInt2 + paramInt4 > 0) && (paramInt3 < paramTencentWebViewProxy.getRealWebView().getContentHeight()) && (paramInt3 + paramInt5 > 0)) {
      i = 1;
    } else {
      i = 0;
    }
    if (paramTencentWebViewProxy.isNeeedReportWIFIGuideFromVideo())
    {
      if ("freewififorvideo".equals(paramString)) {
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTWF034");
      }
      if ((!Apn.isWifiMode()) && ("freewififorvideo".equals(paramString))) {
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTWF035");
      }
      paramTencentWebViewProxy.setNeeedReportWIFIGuideFromVideo(false);
    }
    if ((i != 0) && (!SmttServiceProxy.getInstance().isWebUrlInCloudList(paramTencentWebViewProxy.getRealWebView().getUrl(), 169))) {
      i = j;
    } else {
      i = 0;
    }
    if (locala != null)
    {
      if (i != 0)
      {
        locala.c(paramInt2, paramInt3, paramInt4, paramInt5);
        return;
      }
      paramTencentWebViewProxy.removeViewForce(locala);
      a.remove(paramInt1);
      return;
    }
    if (i != 0)
    {
      locala = paramTencentWebViewProxy.addSurfaceOnUIThread(new b(paramTencentWebViewProxy.getContext(), paramTencentWebViewProxy), paramInt2, paramInt3, paramInt4, paramInt5, 0);
      a.put(paramInt1, locala);
      SmttServiceProxy.getInstance().userBehaviorStatistics("AWTWF036");
      if (!Apn.isWifiMode()) {
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTWF037");
      }
      if (!TextUtils.isEmpty(paramTencentWebViewProxy.getRealWebView().getUrl()))
      {
        paramTencentWebViewProxy = UrlUtils.getHost(paramTencentWebViewProxy.getRealWebView().getUrl());
        if (("freewififorvideo".equals(paramString)) && (!TextUtils.isEmpty(paramTencentWebViewProxy)) && (paramTencentWebViewProxy.contains("mp.weixin.qq.com")))
        {
          SmttServiceProxy.getInstance().userBehaviorStatistics("AWTWF050");
          if (!Apn.isWifiMode()) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("AWTWF051");
          }
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\h5video\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */