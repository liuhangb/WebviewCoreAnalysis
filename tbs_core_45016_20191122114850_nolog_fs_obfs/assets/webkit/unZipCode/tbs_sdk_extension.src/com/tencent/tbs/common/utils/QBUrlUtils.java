package com.tencent.tbs.common.utils;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.base.utils.DLQBUrlUtils;
import com.tencent.tbs.common.MTT.TBSJSApiApiNames;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.baseinfo.TbsDomainWhiteListManager;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QBUrlUtils
{
  public static final int CHECK_JSAPI_DOMAIN_STATUS_FAIL = 0;
  public static final int CHECK_JSAPI_DOMAIN_STATUS_NOAPI = -1;
  public static final int CHECK_JSAPI_DOMAIN_STATUS_SUCC = 1;
  private static final String TAG = "QBUrlUtils";
  public static final int URL_MAX_LENGTH = 1048576;
  
  private static int checkJsApiPermission(Map<String, TBSJSApiApiNames> paramMap, String paramString1, String paramString2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkJsApiPermission url:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", apiName: ");
    ((StringBuilder)localObject).append(paramString2);
    LogUtils.d("QBUrlUtils", ((StringBuilder)localObject).toString());
    if (paramString2 != null)
    {
      if ((paramString2 != null) && (paramString2.length() <= 0)) {
        return 0;
      }
      try
      {
        localObject = new URL(paramString1).getHost();
        if (TextUtils.isEmpty((CharSequence)localObject)) {
          return 0;
        }
        paramMap = paramMap.entrySet().iterator();
        while (paramMap.hasNext())
        {
          paramString1 = (Map.Entry)paramMap.next();
          String str = (String)paramString1.getKey();
          if ((!TextUtils.isEmpty(str)) && (matchHostDomain((String)localObject, str)))
          {
            paramString1 = (TBSJSApiApiNames)paramString1.getValue();
            if ((paramString1 != null) && (paramString1.vecApiNames != null) && (paramString1.vecApiNames.size() > 0))
            {
              int i = 0;
              while (i < paramString1.vecApiNames.size())
              {
                if (paramString2.equals(paramString1.vecApiNames.get(i))) {
                  return 1;
                }
                i += 1;
              }
            }
          }
        }
        return 0;
      }
      catch (Exception paramMap)
      {
        paramString2 = new StringBuilder();
        paramString2.append("isJsApiDomain url:");
        paramString2.append(paramString1);
        LogUtils.d("QBUrlUtils", paramString2.toString());
        paramMap.printStackTrace();
        return 0;
      }
    }
    return 0;
  }
  
  public static String getHost(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = paramString.indexOf("://");
      if (i != -1) {
        i += 3;
      } else {
        i = 0;
      }
      int j = paramString.indexOf('/', i);
      if (j != -1)
      {
        paramString = paramString.substring(i, j);
      }
      else
      {
        j = paramString.indexOf('?', i);
        if (j != -1) {
          paramString = paramString.substring(i, j);
        } else {
          paramString = paramString.substring(i);
        }
      }
      i = paramString.indexOf(":");
      String str = paramString;
      if (i >= 0) {
        str = paramString.substring(0, i);
      }
      return str;
    }
    return null;
  }
  
  public static String getRootDomain(String paramString)
  {
    paramString = UrlUtils.getHost(paramString);
    if ((paramString != null) && (!"".equals(paramString)))
    {
      int i = paramString.lastIndexOf('.');
      if (i != -1)
      {
        String str2 = paramString.substring(i + 1);
        String str3 = paramString.substring(0, i);
        String str1;
        if ((str2 == null) || (!str2.equalsIgnoreCase("cn")))
        {
          str1 = str2;
          paramString = str3;
          if (!str2.equalsIgnoreCase("hk")) {}
        }
        else
        {
          i = str3.lastIndexOf('.');
          str1 = str2;
          paramString = str3;
          if (i != -1)
          {
            String str4 = str3.substring(i + 1);
            str1 = str2;
            paramString = str3;
            if (str4 != null)
            {
              str1 = str2;
              paramString = str3;
              if (str4.length() > 0) {
                if ((!str4.equalsIgnoreCase("com")) && (!str4.equalsIgnoreCase("edu")))
                {
                  str1 = str2;
                  paramString = str3;
                  if (!str4.equalsIgnoreCase("gov")) {}
                }
                else
                {
                  str1 = str4.concat(String.valueOf('.')).concat(str2);
                  paramString = str3.substring(0, i);
                }
              }
            }
          }
        }
        i = paramString.lastIndexOf('.');
        str2 = paramString;
        if (i != -1) {
          str2 = paramString.substring(i + 1);
        }
        if ((str2 != null) && (str2.length() > 0)) {
          return str2.concat(String.valueOf('.')).concat(str1);
        }
      }
    }
    return null;
  }
  
  public static String getStatDomain(String paramString)
  {
    if (paramString != null) {}
    try
    {
      if (paramString.length() >= 1048576) {
        return null;
      }
      String str = UrlUtils.getHost(resolvValidUrl(paramString));
      if (str != null)
      {
        paramString = str;
        if (str.indexOf("qq.com") != -1) {
          paramString = str.replaceAll("(?<=(\\D))\\d*(?=(\\.\\w*){3})", "");
        }
        return paramString;
      }
      return null;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  public static boolean isCandidateUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0) && (!paramString.startsWith("data:")))
    {
      if (paramString.startsWith("jsbridge:")) {
        return false;
      }
      if (isXunleiDownloadUrl(paramString)) {
        return true;
      }
      Object localObject = paramString.trim();
      paramString = UrlUtils.VALID_URL().matcher((CharSequence)localObject);
      Matcher localMatcher1 = UrlUtils.VALID_LOCAL_URL().matcher((CharSequence)localObject);
      Matcher localMatcher2 = UrlUtils.VALID_IP_ADDRESS().matcher((CharSequence)localObject);
      Matcher localMatcher3 = UrlUtils.VALID_MTT_URL().matcher((CharSequence)localObject);
      Matcher localMatcher4 = UrlUtils.VALID_QB_URL().matcher((CharSequence)localObject);
      localObject = UrlUtils.VALID_PAY_URL().matcher((CharSequence)localObject);
      if ((!paramString.find()) && (!localMatcher1.find()) && (!localMatcher2.find()) && (!localMatcher3.find()) && (!localMatcher4.find())) {
        return ((Matcher)localObject).find();
      }
      return true;
    }
    return false;
  }
  
  public static int isJsApiDomain(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return 0;
    }
    Log.e("minafang", " isJsApiDomain");
    Object localObject2 = TbsUserInfo.getInstance().getJSApiDomainList();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(" cached jsApiWhiteList:");
    ((StringBuilder)localObject1).append(localObject2);
    LogUtils.d("jsApi", ((StringBuilder)localObject1).toString());
    if (localObject2 != null)
    {
      localObject1 = localObject2;
      if (localObject2 != null)
      {
        localObject1 = localObject2;
        if (((Map)localObject2).size() > 0) {}
      }
    }
    else
    {
      localObject1 = TbsDomainWhiteListManager.loadJsApiWhiteList();
      TbsUserInfo.setJSApiDomainList((Map)localObject1);
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(" file jsApiWhiteList:");
    ((StringBuilder)localObject2).append(localObject1);
    LogUtils.d("jsApi", ((StringBuilder)localObject2).toString());
    if (localObject1 != null)
    {
      if ((localObject1 != null) && (((Map)localObject1).size() == 0)) {
        return 0;
      }
      if ((localObject1 != null) && (((Map)localObject1).size() > 0)) {
        return checkJsApiPermission((Map)localObject1, paramString1, paramString2);
      }
      return 0;
    }
    return 0;
  }
  
  public static boolean isPluginRefresh(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramString.startsWith("qb://plugin/refresh"));
  }
  
  public static boolean isPluginUrl(String paramString)
  {
    if (paramString != null)
    {
      paramString = paramString.toLowerCase().trim();
      if ((paramString.startsWith("qb://player/")) || (paramString.startsWith("qb://addon/")) || ((paramString.startsWith("qb://app/")) && (!paramString.startsWith("qb://app/id")))) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isQQDomain(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = paramString.toLowerCase();
    if (!paramString.equalsIgnoreCase("qq.com"))
    {
      if (paramString.endsWith(".qq.com")) {
        return true;
      }
      if (!paramString.equalsIgnoreCase("soso.com"))
      {
        if (paramString.endsWith(".soso.com")) {
          return true;
        }
        if ((!paramString.endsWith(".myapp.com")) && (!paramString.endsWith(".qzone.com")))
        {
          if (paramString.endsWith(".wenwen.com")) {
            return true;
          }
          if (!paramString.equalsIgnoreCase("tencent.com"))
          {
            if (paramString.endsWith(".tencent.com")) {
              return true;
            }
            localObject = TbsUserInfo.getInstance().getWupProxyList();
            if ((localObject == null) || (((Vector)localObject).size() <= 0)) {}
          }
        }
      }
    }
    try
    {
      j = ((Vector)localObject).size();
      i = 0;
      while (i < j)
      {
        bool = paramString.equalsIgnoreCase((String)((Vector)localObject).get(i));
        if (bool) {
          return true;
        }
        i += 1;
      }
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException1)
    {
      int j;
      int i;
      boolean bool;
      for (;;) {}
    }
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(0);
    if ((localObject != null) && (((ArrayList)localObject).size() > 0)) {}
    try
    {
      j = ((ArrayList)localObject).size();
      i = 0;
      while (i < j)
      {
        String str = (String)((ArrayList)localObject).get(i);
        if (str != null) {
          if (str.startsWith("*."))
          {
            if (paramString.endsWith(str.substring(1).toLowerCase())) {
              return true;
            }
          }
          else
          {
            bool = paramString.equalsIgnoreCase(str);
            if (bool) {
              return true;
            }
          }
        }
        i += 1;
      }
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException2)
    {
      for (;;) {}
    }
    return paramString.equalsIgnoreCase("http://14.17.41.197:18000");
    return true;
    return true;
    return true;
    return true;
  }
  
  public static boolean isQQDomain(String paramString, boolean paramBoolean)
  {
    if (!PublicSettingManager.getInstance().getIsCheckJsDomain()) {
      return true;
    }
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (!paramString.startsWith("qb://home"))
    {
      if (paramString.startsWith("qb://ext/read")) {
        return true;
      }
      String str = paramString;
      if (!paramBoolean) {
        try
        {
          str = new URL(paramString).getHost();
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("isQQDomain url:");
          localStringBuilder.append(paramString);
          LogUtils.d("QBUrlUtils", localStringBuilder.toString());
          localException.printStackTrace();
          return false;
        }
      }
      return isQQDomain(localException);
    }
    return true;
  }
  
  public static boolean isQQDomain(URL paramURL)
  {
    if (paramURL == null) {
      return false;
    }
    return isQQDomain(paramURL.getHost());
  }
  
  public static boolean isSamsungUrl(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramString.startsWith("samsungapps://"));
  }
  
  public static boolean isSpecialUrl(String paramString)
  {
    boolean bool = false;
    if (paramString == null) {
      return false;
    }
    paramString = paramString.toLowerCase();
    if ((isSamsungUrl(paramString)) || (paramString.startsWith("about:blank")) || (paramString.startsWith("data:")) || (isPluginRefresh(paramString))) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isXunleiDownloadUrl(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (DeviceUtils.getSdkVersion() < 9) {
        return false;
      }
      paramString = paramString.trim().toLowerCase();
      return (paramString.startsWith("magnet:")) || (paramString.startsWith("ftp://")) || (paramString.startsWith("thunder://")) || (paramString.startsWith("ed2k://"));
    }
    return false;
  }
  
  private static boolean matchHostDomain(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return false;
    }
    if (paramString1.equalsIgnoreCase(paramString2)) {
      return true;
    }
    if (paramString2.startsWith("*."))
    {
      paramString2 = paramString2.substring(1, paramString2.length());
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(".");
      localStringBuilder.append(paramString2);
      paramString2 = localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString1);
    return localStringBuilder.toString().endsWith(paramString2);
  }
  
  public static String resolvValidUrl(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return null;
      }
      paramString = paramString.trim();
      if ((!isXunleiDownloadUrl(paramString)) && (!UrlUtils.isJavascript(paramString)))
      {
        if (isSpecialUrl(paramString)) {
          return paramString;
        }
        if (isCandidateUrl(paramString))
        {
          if (UrlUtils.hasValidProtocal(paramString)) {
            return paramString;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("http://");
          localStringBuilder.append(paramString);
          return localStringBuilder.toString();
        }
        return null;
      }
      return paramString;
    }
    return null;
  }
  
  public static String resolveBase(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2)) {
      return paramString1;
    }
    if (paramString2.startsWith("#")) {
      return paramString2;
    }
    if (DLQBUrlUtils.isMobileQQUrl(paramString2)) {
      return paramString2;
    }
    return UrlUtils.resolveBase(paramString1, paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\QBUrlUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */