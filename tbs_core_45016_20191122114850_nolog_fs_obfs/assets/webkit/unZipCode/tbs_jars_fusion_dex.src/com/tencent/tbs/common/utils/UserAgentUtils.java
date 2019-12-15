package com.tencent.tbs.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.tencent.common.utils.UrlUtils;
import java.util.Locale;

public class UserAgentUtils
{
  public static final String CHROMIUM_USERAGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.20 (KHTML, like Gecko) Chrome/11.0.672.0 Safari/534.20";
  public static final String DEFAULT_USERAGENT_MODEL = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0%s Mobile Safari/537.36";
  public static final String IE_USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)";
  public static final String IPAD_USERAGENT = "Mozilla/5.0 (iPad; U; CPU OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
  public static final String IPHONE_USERAGENT = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
  public static final int TYPE_CHROMIUM = 3;
  public static final int TYPE_DEFAULT = 0;
  public static final int TYPE_IPAD = 2;
  public static final int TYPE_IPHONE = 1;
  public static final int TYPE_NONE = 4;
  private static String mMttDefaultUA;
  private static String mSystemDefaultUA;
  
  private static String getDefaultUserAgent(String paramString)
  {
    Locale localLocale;
    StringBuffer localStringBuffer;
    String str;
    label44:
    try
    {
      if (mMttDefaultUA == null)
      {
        localLocale = Locale.getDefault();
        localStringBuffer = new StringBuffer();
        localObject = Build.VERSION.RELEASE;
      }
    }
    finally {}
    try
    {
      str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException1)
    {
      break label44;
    }
    if (((String)localObject).length() > 0) {
      localStringBuffer.append((String)localObject);
    } else {
      localStringBuffer.append("1.0");
    }
    localStringBuffer.append("; ");
    localObject = localLocale.getLanguage();
    if (localObject != null)
    {
      localStringBuffer.append(((String)localObject).toLowerCase());
      localObject = localLocale.getCountry();
      if (localObject != null)
      {
        localStringBuffer.append("-");
        localStringBuffer.append(((String)localObject).toLowerCase());
      }
    }
    else
    {
      localStringBuffer.append("en");
    }
    if ("REL".equals(Build.VERSION.CODENAME)) {
      localObject = Build.MODEL;
    }
    try
    {
      str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException2)
    {
      for (;;)
      {
        continue;
        localObject = "";
      }
    }
    if (((String)localObject).length() > 0)
    {
      localStringBuffer.append("; ");
      localStringBuffer.append((String)localObject);
    }
    localObject = Build.ID.replaceAll("[一-龥]", "");
    if (((String)localObject).length() > 0)
    {
      localStringBuffer.append(" Build/");
      localStringBuffer.append((String)localObject);
    }
    if ((paramString != null) && (paramString.length() != 0))
    {
      localObject = paramString;
      if (!paramString.startsWith(" "))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(" ");
        ((StringBuilder)localObject).append(paramString);
        localObject = ((StringBuilder)localObject).toString();
      }
      mMttDefaultUA = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0%s Mobile Safari/537.36", new Object[] { localStringBuffer, localObject });
      paramString = mMttDefaultUA;
      return paramString;
    }
  }
  
  public static String getLiteUserAgent(Context paramContext)
  {
    return getLiteUserAgent(getSystemDefaultUA(paramContext));
  }
  
  public static String getLiteUserAgent(String paramString)
  {
    StringBuilder localStringBuilder;
    switch (0)
    {
    default: 
      localStringBuilder = new StringBuilder();
      if (!paramString.toLowerCase().contains("android"))
      {
        localStringBuilder.append("Android");
        localStringBuilder.append('/');
      }
      break;
    case 4: 
      return " ";
    case 3: 
      return "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.20 (KHTML, like Gecko) Chrome/11.0.672.0 Safari/534.20";
    case 2: 
      return "Mozilla/5.0 (iPad; U; CPU OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
    case 1: 
      return "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
    }
    localStringBuilder.append(paramString);
    localStringBuilder.append(" MQQBrowser/");
    localStringBuilder.append("6.5");
    return UrlUtils.escapeAllChineseChar(localStringBuilder.toString());
  }
  
  @SuppressLint({"NewApi"})
  public static String getSystemDefaultUA(Context paramContext)
  {
    if (mSystemDefaultUA == null) {
      if (Looper.myLooper() == Looper.getMainLooper())
      {
        paramContext = new WebView(paramContext);
        if (Build.VERSION.SDK_INT >= 11)
        {
          paramContext.removeJavascriptInterface("searchBoxJavaBridge_");
          paramContext.removeJavascriptInterface("accessibility");
          paramContext.removeJavascriptInterface("accessibilityTraversal");
        }
        mSystemDefaultUA = paramContext.getSettings().getUserAgentString();
      }
      else
      {
        return getUAString(0);
      }
    }
    return mSystemDefaultUA;
  }
  
  public static String getUAString()
  {
    return getUAString(-1);
  }
  
  public static String getUAString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return getDefaultUserAgent("MQQBrowser/6.5");
    case 4: 
      return "";
    case 3: 
      return "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.20 (KHTML, like Gecko) Chrome/11.0.672.0 Safari/534.20";
    case 2: 
      return "Mozilla/5.0 (iPad; U; CPU OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
    }
    return "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\UserAgentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */