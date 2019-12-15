package com.tencent.smtt.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.JavascriptInterface;
import com.tencent.common.http.Apn;
import com.tencent.smtt.net.network.connectionclass.ConnectionClassManager.ConnectionClassStateChangeListener;
import com.tencent.smtt.net.network.connectionclass.a;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.MttTimingLog;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.i;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.chromium.net.NetworkChangeNotifier;
import org.chromium.net.NetworkChangeNotifier.ConnectionTypeObserver;

public class PageLoadDetector
  implements NetworkChangeNotifier.ConnectionTypeObserver
{
  private static PageLoadDetector jdField_a_of_type_ComTencentSmttNetPageLoadDetector;
  private int jdField_a_of_type_Int = 0;
  private long jdField_a_of_type_Long = 0L;
  private Context jdField_a_of_type_AndroidContentContext;
  private a jdField_a_of_type_ComTencentSmttNetPageLoadDetector$a = new a(null);
  private String jdField_a_of_type_JavaLangString = "";
  private WeakReference<WebViewChromiumExtension> jdField_a_of_type_JavaLangRefWeakReference = null;
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int = 0;
  private long jdField_b_of_type_Long = 0L;
  private String jdField_b_of_type_JavaLangString = "unkown";
  private boolean jdField_b_of_type_Boolean = false;
  private int jdField_c_of_type_Int = -1;
  private String jdField_c_of_type_JavaLangString = "";
  private String d = "";
  
  private PageLoadDetector()
  {
    NetworkChangeNotifier.addConnectionTypeObserver(this);
    this.jdField_a_of_type_AndroidContentContext = ContextHolder.getInstance().getApplicationContext();
  }
  
  public static PageLoadDetector a()
  {
    if (jdField_a_of_type_ComTencentSmttNetPageLoadDetector == null) {
      jdField_a_of_type_ComTencentSmttNetPageLoadDetector = new PageLoadDetector();
    }
    return jdField_a_of_type_ComTencentSmttNetPageLoadDetector;
  }
  
  private String a()
  {
    if (!a()) {
      return "";
    }
    return SmttServiceProxy.getInstance().getGUID();
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_Int != 0;
  }
  
  private boolean b()
  {
    Object localObject = this.jdField_a_of_type_JavaLangRefWeakReference;
    if ((localObject != null) && (((WeakReference)localObject).get() != null))
    {
      localObject = ((WebViewChromiumExtension)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getSettingsExtension();
      if (localObject != null)
      {
        int i = ((WebSettingsExtension)localObject).getSecurityInfoSecurityLevel();
        int j = ((WebSettingsExtension)localObject).getSecurityInfoEvilType();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
        ((StringBuilder)localObject).append(",level:");
        ((StringBuilder)localObject).append(i);
        ((StringBuilder)localObject).append(",type");
        ((StringBuilder)localObject).append(j);
        X5Log.i("PageLoadDetector-isSpecialSite", ((StringBuilder)localObject).toString());
        if (i != 3) {
          return false;
        }
        return -1 != Arrays.binarySearch(new int[] { 19, 20, 22, 29, 34, 248, 255, 257, 258, 268, 269 }, j);
      }
    }
    return false;
  }
  
  public void a(int paramInt, String paramString1, String paramString2)
  {
    this.jdField_b_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_JavaLangString = paramString2;
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_b_of_type_Long = System.currentTimeMillis();
    boolean bool = false;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_b_of_type_Boolean = false;
    paramString1 = i.a();
    if (Apn.sApnTypeS == 4) {
      bool = true;
    }
    this.jdField_b_of_type_Int = paramString1.a(bool);
    paramString1 = new StringBuilder();
    paramString1.append("PageLoadDetector  [errorUrl=");
    paramString1.append(this.jdField_a_of_type_JavaLangString);
    paramString1.append("] [Options=SET_ERROR_PAGE_INFO] [erroCode=");
    paramString1.append(this.jdField_a_of_type_Int);
    paramString1.append("] [mErrorDesc=");
    paramString1.append(this.jdField_b_of_type_JavaLangString);
    paramString1.append("] [SingalStrenth=");
    paramString1.append(this.jdField_b_of_type_Int);
    paramString1.append("]");
    MttLog.e("PageLoad", paramString1.toString());
  }
  
  public void a(WebViewChromiumExtension paramWebViewChromiumExtension)
  {
    this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramWebViewChromiumExtension);
  }
  
  @JavascriptInterface
  public void controlLogSwitch(boolean paramBoolean)
  {
    if (!a()) {}
  }
  
  @JavascriptInterface
  public String getErrorDetail()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  @JavascriptInterface
  public String getErrorUrl()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  @JavascriptInterface
  public int getFailPhase()
  {
    int i = this.jdField_a_of_type_Int;
    switch (i)
    {
    default: 
      switch (i)
      {
      default: 
        switch (i)
        {
        default: 
          switch (i)
          {
          default: 
            switch (i)
            {
            default: 
              switch (i)
              {
              default: 
                switch (i)
                {
                default: 
                  return 0;
                }
                break;
              }
              break;
            }
            break;
          }
          break;
        }
        break;
      }
      break;
    }
    return 1;
  }
  
  @JavascriptInterface
  public int getGlobalErrorCode()
  {
    return this.jdField_a_of_type_Int;
  }
  
  @JavascriptInterface
  public String getNetworkStatus()
  {
    if (!a()) {
      return "";
    }
    boolean bool = WebSettingsExtension.getQProxyEnabled();
    NetworkInfo localNetworkInfo = ContextHolder.getInstance().getConnectivityManager().getActiveNetworkInfo();
    if (bool) {
      str1 = "x5_error_proxy_enabled";
    } else {
      str1 = "x5_error_proxy_disabled";
    }
    String str1 = SmttResource.getString(str1, "string");
    String str2 = SmttResource.getString("x5_error_network_type", "string");
    String str3 = SmttResource.getString("x5_error_apn_name", "string");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str1);
    localStringBuilder.append("</br>\n");
    localStringBuilder.append(str2);
    localStringBuilder.append(localNetworkInfo.getTypeName());
    localStringBuilder.append("</br>\n");
    localStringBuilder.append(str3);
    localStringBuilder.append(localNetworkInfo.getExtraInfo());
    return localStringBuilder.toString();
  }
  
  @JavascriptInterface
  public int getPageType()
  {
    if (b()) {
      return 6;
    }
    return 0;
  }
  
  @JavascriptInterface
  public int getSummaryReason()
  {
    int i = this.jdField_a_of_type_Int;
    switch (i)
    {
    default: 
      switch (i)
      {
      default: 
        switch (i)
        {
        default: 
          switch (i)
          {
          default: 
            switch (i)
            {
            default: 
              switch (i)
              {
              default: 
                switch (i)
                {
                default: 
                  switch (i)
                  {
                  default: 
                    switch (i)
                    {
                    default: 
                      switch (i)
                      {
                      default: 
                        switch (i)
                        {
                        default: 
                          switch (i)
                          {
                          default: 
                            switch (i)
                            {
                            default: 
                              switch (i)
                              {
                              default: 
                                switch (i)
                                {
                                default: 
                                  switch (i)
                                  {
                                  default: 
                                    switch (i)
                                    {
                                    default: 
                                      switch (i)
                                      {
                                      default: 
                                        switch (i)
                                        {
                                        default: 
                                          switch (i)
                                          {
                                          default: 
                                            switch (i)
                                            {
                                            default: 
                                              return 6;
                                            }
                                            break;
                                          }
                                          break;
                                        }
                                        break;
                                      }
                                      break;
                                    }
                                    break;
                                  }
                                case -159: 
                                case -157: 
                                case -156: 
                                  return 1;
                                }
                                break;
                              }
                              break;
                            }
                            return 2;
                          }
                          break;
                        }
                        break;
                      }
                    case -339: 
                      return 3;
                    case -348: 
                    case -344: 
                    case -343: 
                      return 6;
                    }
                    break;
                  }
                case -363: 
                case -362: 
                case -361: 
                case -360: 
                  return 4;
                }
                return 0;
              }
              return 7;
            case -501: 
              return 5;
            }
            break;
          }
          return 8;
        }
        break;
      }
      return 9;
    }
    return 10;
  }
  
  @JavascriptInterface
  public boolean isNetworkReachable()
  {
    if (!a()) {
      return false;
    }
    SmttServiceProxy.getInstance().reportErrorPageLearn(this.jdField_a_of_type_JavaLangString, this.jdField_a_of_type_Int);
    return true;
  }
  
  public void onConnectionTypeChanged(int paramInt)
  {
    this.jdField_a_of_type_Long = System.currentTimeMillis();
  }
  
  @JavascriptInterface
  public void reportComicKey(String paramString)
  {
    SmttServiceProxy.getInstance().userBehaviorStatistics(paramString);
  }
  
  @JavascriptInterface
  public boolean uploadErrorInfo()
  {
    if (!a())
    {
      SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_error_already_uploaded", "string"), 0);
      return false;
    }
    boolean bool2 = WebSettingsExtension.getQProxyEnabled();
    Object localObject = ContextHolder.getInstance().getConnectivityManager().getActiveNetworkInfo();
    if (localObject == null)
    {
      SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_error_upload_failed", "string"), 0);
      return false;
    }
    SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
    String str1 = a();
    int i = this.jdField_a_of_type_Int;
    String str2 = this.jdField_b_of_type_JavaLangString;
    String str3 = this.jdField_a_of_type_JavaLangString;
    String str4 = ((NetworkInfo)localObject).getTypeName();
    localObject = ((NetworkInfo)localObject).getExtraInfo();
    boolean bool3 = this.jdField_a_of_type_Boolean;
    boolean bool4 = this.jdField_b_of_type_Boolean;
    String str5 = this.d;
    int j = this.jdField_b_of_type_Int;
    String str6 = this.jdField_c_of_type_JavaLangString;
    boolean bool1;
    if (Math.abs(this.jdField_b_of_type_Long - this.jdField_a_of_type_Long) < 10000L) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (localSmttServiceProxy.uploadPageErrorMetaInfo(str1, i, str2, str3, bool2, str4, (String)localObject, bool3, bool4, str5, j, str6, bool1, Apn.sApnType, this.jdField_c_of_type_Int, MttTimingLog.logSize, MttTimingLog.uploaded, MttTimingLog.responseCode))
    {
      SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_error_upload_success", "string"), 0);
      this.jdField_a_of_type_Int = 0;
      return true;
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_error_upload_failed", "string"), 0);
    return false;
  }
  
  private class a
    implements ConnectionClassManager.ConnectionClassStateChangeListener
  {
    private a() {}
    
    public void onBandwidthStateChange(a parama)
    {
      PageLoadDetector.a(PageLoadDetector.this, System.currentTimeMillis());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\PageLoadDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */