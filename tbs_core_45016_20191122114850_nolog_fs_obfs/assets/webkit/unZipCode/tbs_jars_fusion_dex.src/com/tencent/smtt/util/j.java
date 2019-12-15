package com.tencent.smtt.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class j
{
  public static Pattern a = Pattern.compile("(.+)(\\.)(.+)[^\\w]*(.*)", 2);
  public static Pattern b = Pattern.compile("(^file://.+)|(.+localhost:?\\d*/.+\\..+)", 2);
  public static Pattern c = Pattern.compile("mtt://(.+)", 2);
  public static Pattern d = Pattern.compile("qb://(.+)", 2);
  public static Pattern e = Pattern.compile("(tenpay|alipay)://(.+)", 2);
  public static Pattern f = Pattern.compile("(\\d){1,3}\\.(\\d){1,3}\\.(\\d){1,3}\\.(\\d){1,3}(:\\d{1,4})?(/(.*))?", 2);
  public static final Pattern g = Pattern.compile("((?:(http|https|Http|Https):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-\\_]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~%\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?");
  
  public static String a(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return null;
      }
      Matcher localMatcher = g.matcher(paramString);
      if (localMatcher.groupCount() == 0) {
        return null;
      }
      if (localMatcher.find()) {
        paramString = localMatcher.group();
      } else {
        paramString = null;
      }
      if (localMatcher.find()) {
        return null;
      }
      return b(paramString);
    }
    return null;
  }
  
  public static Map<String, String> a(String paramString)
  {
    Object localObject = d(paramString);
    paramString = new HashMap();
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      return paramString;
    }
    localObject = ((String)localObject).split(",");
    int j = localObject.length;
    int i = 0;
    while (i < j)
    {
      String[] arrayOfString = localObject[i].split("=", 2);
      if (arrayOfString.length == 2) {
        paramString.put(arrayOfString[0], arrayOfString[1]);
      }
      i += 1;
    }
    return paramString;
  }
  
  public static boolean a(Context paramContext, String paramString)
  {
    Context localContext = paramContext.getApplicationContext();
    Object localObject = localContext.getApplicationInfo().packageName;
    if (!"com.tencent.mm".equals(localObject))
    {
      if ("com.tencent.mobileqq".equals(localObject)) {
        return false;
      }
      if ((paramString.startsWith("mttbrowser://")) && (SmttServiceProxy.getInstance().isShouldInterceptMttbrowser()))
      {
        localObject = a(paramString);
        int i;
        try
        {
          if (((Map)localObject).containsKey("windowType"))
          {
            i = Integer.parseInt((String)((Map)localObject).get("windowType"));
            if (i == 1)
            {
              i = 1;
              break label114;
            }
          }
          i = 0;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          i = 0;
        }
        label114:
        if (SmttServiceProxy.getInstance().openUrlInQQBrowser(localContext, paramString.substring(17), ""))
        {
          if (i != 0) {
            SmttServiceProxy.getInstance().userBehaviorStatistics("BVtbs01");
          }
          return true;
        }
        int j;
        boolean bool;
        if (((Map)localObject).containsKey("miniqbStatus"))
        {
          try
          {
            j = Integer.parseInt((String)((Map)localObject).get("miniqbStatus"));
          }
          catch (Exception paramString)
          {
            paramString.printStackTrace();
            j = 0;
          }
          switch (j)
          {
          default: 
            bool = false;
            break;
          case 2: 
            bool = true;
            break;
          case 1: 
            bool = SmttServiceProxy.getInstance().isShouldInterceptMttbrowserUseMiniQb();
            break;
          }
        }
        else if (i != 0)
        {
          bool = SmttServiceProxy.getInstance().isShouldInterceptMttbrowserUseMiniQb();
        }
        else
        {
          bool = false;
        }
        if (bool)
        {
          j = SmttServiceProxy.getInstance().startMiniQB(paramContext, (String)((Map)localObject).get("url"), "", (Map)localObject);
          if (i != 0)
          {
            if (j == 0)
            {
              SmttServiceProxy.getInstance().userBehaviorStatistics("BVtbs02");
              return true;
            }
            SmttServiceProxy.getInstance().userBehaviorStatistics("BVtbs03");
          }
        }
      }
      return false;
    }
    return false;
  }
  
  public static boolean a(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      int i = paramString.indexOf("://");
      int j = paramString.indexOf('.');
      if ((i > 0) && (j > 0) && (i > j)) {
        return false;
      }
      return paramString.contains("://");
    }
    return false;
  }
  
  public static String b(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0) && (!h(paramString)))
    {
      if (paramString.startsWith(".")) {
        return null;
      }
      paramString = paramString.trim();
      if ((!f(paramString)) && (!d(paramString)))
      {
        if (c(paramString)) {
          return paramString;
        }
        if (g(paramString))
        {
          if (a(paramString)) {
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
  
  public static boolean b(String paramString)
  {
    return (!i(paramString)) && (paramString.startsWith("samsungapps://"));
  }
  
  public static String c(String paramString)
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
  
  public static boolean c(String paramString)
  {
    boolean bool = false;
    if (paramString == null) {
      return false;
    }
    paramString = paramString.toLowerCase();
    if (paramString == null) {
      return false;
    }
    if ((b(paramString)) || (paramString.startsWith("about:blank")) || (paramString.startsWith("data:")) || (e(paramString))) {
      bool = true;
    }
    return bool;
  }
  
  public static String d(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    if (paramString.startsWith("page://"))
    {
      i = paramString.indexOf("http://");
      if (i != -1) {
        return paramString.substring(i);
      }
    }
    int i = paramString.indexOf("://");
    String str = paramString;
    if (i >= 0) {
      str = paramString.substring(i + 3);
    }
    return str;
  }
  
  public static boolean d(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramString != null)
    {
      bool1 = bool2;
      if (paramString.length() > 10)
      {
        bool1 = bool2;
        if (paramString.substring(0, 11).equalsIgnoreCase("javascript:")) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public static boolean e(String paramString)
  {
    return (!i(paramString)) && (paramString.startsWith("qb://plugin/refresh"));
  }
  
  public static boolean f(String paramString)
  {
    if (!i(paramString))
    {
      if (Integer.parseInt(Build.VERSION.SDK) < 9) {
        return false;
      }
      paramString = paramString.trim().toLowerCase();
      return (paramString.startsWith("magnet:")) || (paramString.startsWith("ftp://")) || (paramString.startsWith("thunder://")) || (paramString.startsWith("ed2k://"));
    }
    return false;
  }
  
  public static boolean g(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (paramString.startsWith("data:")) {
        return false;
      }
      if (f(paramString)) {
        return true;
      }
      paramString = paramString.trim();
      if (a.matcher(paramString).find()) {
        return true;
      }
      if (b.matcher(paramString).find()) {
        return true;
      }
      if (f.matcher(paramString).find()) {
        return true;
      }
      if (c.matcher(paramString).find()) {
        return true;
      }
      if (d.matcher(paramString).find()) {
        return true;
      }
      return e.matcher(paramString).find();
    }
    return false;
  }
  
  public static boolean h(String paramString)
  {
    if (i(paramString)) {
      return false;
    }
    int j = paramString.length();
    int i = 0;
    while (i < j)
    {
      int k = paramString.charAt(i);
      if (((k >= 19968) && (k <= 40959)) || ((k >= 65072) && (k <= 65440))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static boolean i(String paramString)
  {
    return (paramString == null) || ("".equals(paramString.trim()));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */