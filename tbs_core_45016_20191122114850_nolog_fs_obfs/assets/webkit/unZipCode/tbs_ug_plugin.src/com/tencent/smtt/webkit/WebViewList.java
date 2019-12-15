package com.tencent.smtt.webkit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.core.partner.ad.AdWebView;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.annotations.UsedByReflection;

public class WebViewList
{
  public static String AD_WEBVIEW_CLICK_STAT_ENTRYID_FOR_MINIQB = "ad_webview_click_stat_entryid_for_miniqb";
  public static String AD_WEBVIEW_CLICK_STAT_KEY;
  public static String AD_WEBVIEW_CLICK_STAT_POSID_FOR_QB;
  public static String AD_WEBVIEW_DETAIL_URL;
  private static List<WeakReference<IX5WebViewBase>> mWebViewList = new ArrayList();
  
  static
  {
    AD_WEBVIEW_DETAIL_URL = "ad_webview_detail_url";
    AD_WEBVIEW_CLICK_STAT_KEY = "ad_webview_click_stat_key";
    AD_WEBVIEW_CLICK_STAT_POSID_FOR_QB = "ad_webview_click_stat_posid_for_qb";
  }
  
  public static void addWebView(IX5WebViewBase paramIX5WebViewBase)
  {
    mWebViewList.add(new WeakReference(paramIX5WebViewBase));
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static Bundle getAdWebViewInfo()
  {
    try
    {
      Object localObject1 = mWebViewList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (WeakReference)((Iterator)localObject1).next();
        if ((((WeakReference)localObject2).get() != null) && ((((WeakReference)localObject2).get() instanceof AdWebView)) && (((IX5WebViewBase)((WeakReference)localObject2).get()).getView().getVisibility() == 0) && (((AdWebView)((WeakReference)localObject2).get()).isCurrentAd()))
        {
          String str1 = ((AdWebView)((WeakReference)localObject2).get()).getAdDetailUrl();
          String str2 = ((AdWebView)((WeakReference)localObject2).get()).getAdStatClickKey();
          String str3 = ((AdWebView)((WeakReference)localObject2).get()).getAdStatClickPosIdForQB();
          localObject2 = ((AdWebView)((WeakReference)localObject2).get()).getAdStatClickEntryIdForMiniQB();
          if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
          {
            localObject1 = new Bundle();
            ((Bundle)localObject1).putString(AD_WEBVIEW_DETAIL_URL, str1);
            ((Bundle)localObject1).putString(AD_WEBVIEW_CLICK_STAT_KEY, str2);
            ((Bundle)localObject1).putString(AD_WEBVIEW_CLICK_STAT_POSID_FOR_QB, str3);
            ((Bundle)localObject1).putString(AD_WEBVIEW_CLICK_STAT_ENTRYID_FOR_MINIQB, (String)localObject2);
            return (Bundle)localObject1;
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static List<WeakReference<IX5WebViewBase>> getAllWebView()
  {
    return mWebViewList;
  }
  
  @UsedByReflection("MidPageWebViewWrapper.java")
  public static List<WeakReference<IX5WebViewBase>> getAllWebViewIgnoreMiniQB()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = mWebViewList.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      if ((!(localWeakReference.get() instanceof IX5WebView)) || (!((IX5WebView)localWeakReference.get()).isMiniQB())) {
        localArrayList.add(localWeakReference);
      }
    }
    return localArrayList;
  }
  
  @UsedByReflection("MidPageWebViewWrapper.java")
  public static X5WebViewAdapter getCurrentMainWebviewJustForQQandWechat()
  {
    try
    {
      int i = mWebViewList.size() - 1;
      while (i >= 0)
      {
        Object localObject = (WeakReference)mWebViewList.get(i);
        if ((((WeakReference)localObject).get() != null) && ((((WeakReference)localObject).get() instanceof X5WebViewAdapter)) && (!(((WeakReference)localObject).get() instanceof AdWebView)) && (!((X5WebViewAdapter)((WeakReference)localObject).get()).isMiniQB()) && (!TextUtils.isEmpty(((X5WebViewAdapter)((WeakReference)localObject).get()).getRealWebView().getUrl())))
        {
          localObject = (X5WebViewAdapter)((WeakReference)localObject).get();
          return (X5WebViewAdapter)localObject;
        }
        i -= 1;
      }
      return null;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\WebViewList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */