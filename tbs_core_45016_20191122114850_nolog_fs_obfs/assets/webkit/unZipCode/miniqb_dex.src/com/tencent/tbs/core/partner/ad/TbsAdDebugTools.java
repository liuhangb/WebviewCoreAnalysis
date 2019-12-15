package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import org.chromium.base.ThreadUtils;

public class TbsAdDebugTools
{
  private static final String TAG = "TbsAdDebugTools";
  public static final String TBSADDEBUG = "debugtbsad.html";
  AdRequest adRequest = null;
  private Context mContext;
  private TencentWebViewProxy mWebView;
  
  public TbsAdDebugTools(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mContext = paramTencentWebViewProxy.getContext();
    this.mWebView = paramTencentWebViewProxy;
  }
  
  public boolean checkDebugTbsAdWhiteList()
  {
    if (SmttServiceProxy.getInstance().isInDebugTbsAdWhiteList()) {
      return true;
    }
    ThreadUtils.runOnUiThreadBlocking(new Runnable()
    {
      public void run()
      {
        ((X5WebViewAdapter)TbsAdDebugTools.this.mWebView).evaluateJavascript("javascript:alert('no permission! please contact with chunxue!')", null);
      }
    });
    return false;
  }
  
  public boolean checkDomain()
  {
    String str = this.mWebView.getUrl();
    X5Log.isEnableLog();
    if ((!TextUtils.isEmpty(str)) && (str.contains("debugtbsad.html"))) {
      return true;
    }
    ThreadUtils.runOnUiThreadBlocking(new Runnable()
    {
      public void run()
      {
        ((X5WebViewAdapter)TbsAdDebugTools.this.mWebView).evaluateJavascript("javascript:alert('host is not debugtbsad page!')", null);
      }
    });
    return false;
  }
  
  @JavascriptInterface
  public int checkDomainWhiteList(String paramString, int paramInt)
  {
    if ((checkDebugTbsAdWhiteList()) && (checkDomain()))
    {
      int i = -1;
      if (paramInt == 1)
      {
        if (SmttServiceProxy.getInstance().needRequestTbsAd(paramString)) {
          return 1;
        }
        return 2;
      }
      if (paramInt == 2)
      {
        if (SmttServiceProxy.getInstance().isScreenAdDomainWhiteListMatched(paramString)) {
          return 1;
        }
        return 3;
      }
      if (paramInt == 3)
      {
        if (SmttServiceProxy.getInstance().canInsertTbsAdInPage(paramString)) {
          return 1;
        }
        i = 4;
      }
      return i;
    }
    return -2;
  }
  
  @JavascriptInterface
  public int checkFrequencyControl(String paramString)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @JavascriptInterface
  public void requestAd(String paramString, int paramInt)
  {
    if (checkDebugTbsAdWhiteList())
    {
      if (!checkDomain()) {
        return;
      }
      X5Log.isEnableLog();
      if (paramInt == 1)
      {
        if (this.adRequest == null) {
          this.adRequest = new AdRequest(new AdRequest.IAdRequestInterface()
          {
            public String getMiniProgramPkgName()
            {
              return null;
            }
            
            public void loadAdWithData(String paramAnonymousString1, ContentInfo paramAnonymousContentInfo, String paramAnonymousString2)
            {
              X5Log.isEnableLog();
              if ((!TextUtils.isEmpty(paramAnonymousString1)) && (!"0".equals(paramAnonymousString1)) && (paramAnonymousContentInfo != null) && (paramAnonymousString2.equals("0")))
              {
                X5Log.isEnableLog();
                TbsAdDebugTools.this.mWebView.loadUrl("javascript:requestAdsAccessNodeSuccessByZeroDev()");
                return;
              }
              X5Log.isEnableLog();
              paramAnonymousString1 = TbsAdDebugTools.this.mWebView;
              paramAnonymousContentInfo = new StringBuilder();
              paramAnonymousContentInfo.append("javascript:requestAdsAccessNodeFail('");
              paramAnonymousContentInfo.append(paramAnonymousString2);
              paramAnonymousContentInfo.append("')");
              paramAnonymousString1.loadUrl(paramAnonymousContentInfo.toString());
            }
          });
        }
        this.adRequest.RequestAd(paramString);
        return;
      }
      if (paramInt == 2) {
        SmttServiceProxy.getInstance().showScreenAd(this.mContext, (X5WebViewAdapter)this.mWebView, null, false, true);
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\TbsAdDebugTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */