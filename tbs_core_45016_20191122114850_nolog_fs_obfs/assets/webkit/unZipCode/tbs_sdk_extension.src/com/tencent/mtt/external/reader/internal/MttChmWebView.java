package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.ReaderConfig;
import com.tencent.mtt.external.reader.base.ReaderTypeView.IViewCommand;
import com.tencent.mtt.external.reader.base.ReaderViewCreator;
import com.tencent.mtt.external.reader.base.ReaderWebView;
import com.tencent.mtt.external.reader.export.IReaderWebViewClient;
import com.tencent.mtt.external.reader.export.IReaderWebViewProxy;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MttChmWebView
  extends ReaderWebView
{
  public static int ReaderToolBarBtn_Chm_Back = 9;
  public static int ReaderToolBarBtn_Chm_Next = 10;
  public static int ReaderToolBarBtn_Chm_Pre = 11;
  String jdField_a_of_type_JavaLangString = "file://";
  private boolean jdField_a_of_type_Boolean = false;
  ReaderTypeView.IViewCommand b = null;
  public IReaderWebViewProxy mWebViewProxy = null;
  
  public MttChmWebView(Context paramContext)
  {
    super(paramContext);
    a();
  }
  
  private void c()
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if ((localIReaderWebViewProxy != null) && (localIReaderWebViewProxy.getView() != null))
    {
      this.mWebViewProxy.setFitScreen(this.jdField_a_of_type_Boolean);
      if (this.mWebViewProxy.getView().getParent() != null) {
        this.mParentLayout.removeView(this.mWebViewProxy.getView());
      }
      this.mWebViewProxy.destroy();
      this.mWebViewProxy = null;
    }
  }
  
  String a()
  {
    String str = this.mReaderConfig.tempPath;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append("/chm");
    return localStringBuilder.toString();
  }
  
  String a(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
    ((StringBuilder)localObject).append(a());
    paramString = paramString.replace(((StringBuilder)localObject).toString(), "");
    localObject = new Bundle();
    ((Bundle)localObject).putString("chmUrl", paramString);
    a(301, null, localObject);
    return ((Bundle)localObject).getString("urlResult");
  }
  
  void a()
  {
    setViewCommand(new ReaderTypeView.IViewCommand()
    {
      public void doAction(int paramAnonymousInt, Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        StringBuilder localStringBuilder;
        if (MttChmWebView.ReaderToolBarBtn_Chm_Pre == paramAnonymousInt)
        {
          paramAnonymousObject1 = MttChmWebView.this.getPrevUrl();
          if (paramAnonymousObject1 != null)
          {
            paramAnonymousObject2 = MttChmWebView.this;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(MttChmWebView.this.a());
            localStringBuilder.append((String)paramAnonymousObject1);
            ((MttChmWebView)paramAnonymousObject2).b(localStringBuilder.toString());
            MttChmWebView.this.a((String)paramAnonymousObject1);
          }
        }
        else if (MttChmWebView.ReaderToolBarBtn_Chm_Back == paramAnonymousInt)
        {
          if (MttChmWebView.this.mWebViewProxy.canGoBack()) {
            MttChmWebView.this.mWebViewProxy.goBack();
          }
        }
        else if (MttChmWebView.ReaderToolBarBtn_Chm_Next == paramAnonymousInt)
        {
          paramAnonymousObject1 = MttChmWebView.this.getNextUrl();
          if (paramAnonymousObject1 != null)
          {
            paramAnonymousObject2 = MttChmWebView.this;
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(MttChmWebView.this.a());
            localStringBuilder.append((String)paramAnonymousObject1);
            ((MttChmWebView)paramAnonymousObject2).b(localStringBuilder.toString());
            MttChmWebView.this.a((String)paramAnonymousObject1);
          }
        }
      }
    });
  }
  
  void a(int paramInt, Object paramObject1, Object paramObject2)
  {
    postEvent(3011, Integer.valueOf(paramInt), paramObject2);
  }
  
  void a(int paramInt, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("index", paramInt);
    localBundle.putBoolean("enable", paramBoolean);
    postEvent(3010, localBundle, null);
  }
  
  void a(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("chmUrl", paramString);
    a(302, null, localBundle);
  }
  
  boolean a(String paramString)
  {
    return paramString.startsWith(this.jdField_a_of_type_JavaLangString);
  }
  
  public void addJSI(Object paramObject, String paramString)
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null) {
      localIReaderWebViewProxy.addJavascriptInterface(paramObject, paramString);
    }
  }
  
  void b()
  {
    this.mWebViewProxy = MttViewCreator.getInstance().getWebViewProxy();
    this.mWebViewProxy.creatWebView();
    this.mWebViewProxy.setReaderWebViewClient(new a());
    this.jdField_a_of_type_Boolean = this.mWebViewProxy.getFitScreen();
    this.mWebViewProxy.setFitScreen(true);
    this.mParentLayout.addView(this.mWebViewProxy.getView(), new FrameLayout.LayoutParams(-1, -1));
  }
  
  void b(String paramString)
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(paramString);
      localStringBuilder.append("?from_file_reader=1");
      localIReaderWebViewProxy.loadUrl(localStringBuilder.toString());
    }
  }
  
  boolean b(String paramString)
  {
    return new File(paramString.replace(this.jdField_a_of_type_JavaLangString, "")).exists();
  }
  
  public boolean canGoNextUrl()
  {
    Bundle localBundle = new Bundle();
    a(304, null, localBundle);
    return localBundle.getBoolean("canGoNext", false);
  }
  
  public boolean canGoPreUrl()
  {
    Bundle localBundle = new Bundle();
    a(303, null, localBundle);
    return localBundle.getBoolean("canGoPrev", false);
  }
  
  public int create()
  {
    b();
    return 0;
  }
  
  public void destroy()
  {
    c();
    setEvent(null);
    super.destroy();
  }
  
  public String getNextUrl()
  {
    Bundle localBundle = new Bundle();
    a(306, null, localBundle);
    return localBundle.getString("next_url");
  }
  
  public String getPrevUrl()
  {
    Bundle localBundle = new Bundle();
    a(305, null, localBundle);
    return localBundle.getString("prev_url");
  }
  
  public void loadUrl(String paramString)
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append("?from_file_reader=1");
      localIReaderWebViewProxy.loadUrl(localStringBuilder.toString());
    }
  }
  
  public void notifySkinChanged() {}
  
  public void setBackgroundColor(int paramInt) {}
  
  class a
    implements IReaderWebViewClient
  {
    a() {}
    
    public void onPageFinished(String paramString)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(MttChmWebView.this.a);
      ((StringBuilder)localObject).append(MttChmWebView.this.a());
      paramString = paramString.replace(((StringBuilder)localObject).toString(), "");
      try
      {
        localObject = URLDecoder.decode(paramString, "UTF-8");
        paramString = (String)localObject;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
      MttChmWebView.this.a(paramString);
      boolean bool = MttChmWebView.this.mWebViewProxy.canGoBack();
      MttChmWebView.this.a(MttChmWebView.ReaderToolBarBtn_Chm_Pre, MttChmWebView.this.canGoPreUrl());
      MttChmWebView.this.a(MttChmWebView.ReaderToolBarBtn_Chm_Back, bool);
      MttChmWebView.this.a(MttChmWebView.ReaderToolBarBtn_Chm_Next, MttChmWebView.this.canGoNextUrl());
    }
    
    public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4, View paramView) {}
    
    public void onSingleTaped()
    {
      MttChmWebView.this.postEvent(3000, null, null);
    }
    
    public void onWebviewCreated() {}
    
    public boolean overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      MttChmWebView.this.postEvent(3001, null, null);
      return false;
    }
    
    public WebResourceResponse shouldInterceptRequest(String paramString)
    {
      LogUtils.d("shouldInterceptRequest", paramString);
      if (!MttChmWebView.this.a(paramString)) {
        return null;
      }
      if (MttChmWebView.this.b(paramString)) {
        return null;
      }
      if (MttChmWebView.this.a(paramString) == null) {
        return null;
      }
      try
      {
        paramString = new FileInputStream(paramString.replace(MttChmWebView.this.a, ""));
        return new WebResourceResponse("text/css", "UTF-8", paramString);
      }
      catch (Exception paramString) {}
      return null;
    }
    
    public boolean shouldOverrideUrlLoading(String paramString)
    {
      if (!MttChmWebView.this.a(paramString)) {
        return false;
      }
      if (!MttChmWebView.this.b(paramString)) {
        MttChmWebView.this.a(paramString);
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttChmWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */