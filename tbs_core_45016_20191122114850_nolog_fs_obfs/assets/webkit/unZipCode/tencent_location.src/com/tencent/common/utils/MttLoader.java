package com.tencent.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.mtt.browser.file.FileProvider;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MttLoader
{
  @Deprecated
  public static final String KEY_ACTIVITY_NAME = "KEY_ACT";
  @Deprecated
  public static final String KEY_APP_NAME = "KEY_APPNAME";
  public static final String KEY_EUSESTAT = "KEY_EUSESTAT";
  @Deprecated
  public static final String KEY_PACKAGE = "KEY_PKG";
  public static final String KEY_PID = "KEY_PID";
  public static final String MTT_ACTION = "com.tencent.QQBrowser.action.VIEW";
  public static final String MTT_ACTION_SP = "com.tencent.QQBrowser.action.VIEWSP";
  public static final String PID_ARTICLE_NEWS = "21272";
  public static final String PID_MOBILE_QQ = "50079";
  public static final String PID_QQPIM = "50190";
  public static final String PID_QZONE = "10494";
  public static final String PID_WECHAT = "10318";
  public static final String QQBROWSER_DIRECT_DOWNLOAD_URL = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=50079";
  public static final String QQBROWSER_DOWNLOAD_URL = "http://mdc.html5.qq.com/mh?channel_id=50079&u=";
  public static final int RESULT_INVALID_CONTEXT = 3;
  public static final int RESULT_INVALID_URL = 2;
  public static final int RESULT_NOT_INSTALL_QQBROWSER = 4;
  public static final int RESULT_OK = 0;
  public static final int RESULT_QQBROWSER_LOW = 5;
  public static final int RESULT_UNKNOWN = 1;
  
  private static int a(Context paramContext)
  {
    paramContext = paramContext.getApplicationInfo().processName;
    if (paramContext.contains("com.tencent.mobileqq")) {
      return 13;
    }
    if (paramContext.contains("com.qzone")) {
      return 14;
    }
    if (paramContext.contains("com.tencent.WBlog")) {
      return 15;
    }
    if (paramContext.contains("com.tencent.mm")) {
      return 24;
    }
    return 26;
  }
  
  private static a a(Context paramContext, Uri paramUri)
  {
    Object localObject = new Intent("com.tencent.QQBrowser.action.VIEW");
    ((Intent)localObject).setData(paramUri);
    paramUri = paramContext.getPackageManager().queryIntentActivities((Intent)localObject, 0);
    if (paramUri.size() <= 0) {
      return null;
    }
    paramContext = new a(null);
    paramUri = paramUri.iterator();
    while (paramUri.hasNext())
    {
      localObject = (ResolveInfo)paramUri.next();
      String str = ((ResolveInfo)localObject).activityInfo.packageName;
      if (str.contains("com.tencent.mtt"))
      {
        paramContext.a = ((ResolveInfo)localObject).activityInfo.name;
        paramContext.b = ((ResolveInfo)localObject).activityInfo.packageName;
        return paramContext;
      }
      if (str.contains("com.tencent.qbx"))
      {
        paramContext.a = ((ResolveInfo)localObject).activityInfo.name;
        paramContext.b = ((ResolveInfo)localObject).activityInfo.packageName;
      }
    }
    return paramContext;
  }
  
  private static String a(Certificate paramCertificate)
    throws CertificateEncodingException
  {
    paramCertificate = paramCertificate.getEncoded();
    int k = paramCertificate.length;
    char[] arrayOfChar = new char[k * 2];
    int i = 0;
    while (i < k)
    {
      int n = paramCertificate[i];
      int j = n >> 4 & 0xF;
      int m = i * 2;
      if (j >= 10) {
        j = j + 97 - 10;
      } else {
        j += 48;
      }
      arrayOfChar[m] = ((char)j);
      j = n & 0xF;
      if (j >= 10) {
        j = j + 97 - 10;
      } else {
        j += 48;
      }
      arrayOfChar[(m + 1)] = ((char)j);
      i += 1;
    }
    return new String(arrayOfChar);
  }
  
  private static boolean a(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return false;
      }
      paramString = paramString.trim();
      int i = paramString.toLowerCase().indexOf("://");
      int j = paramString.toLowerCase().indexOf('.');
      if ((i > 0) && (j > 0) && (i > j)) {
        return false;
      }
      return paramString.toLowerCase().contains("://");
    }
    return false;
  }
  
  public static BrowserInfo getBrowserInfo(Context paramContext)
  {
    BrowserInfo localBrowserInfo = new BrowserInfo();
    for (;;)
    {
      try
      {
        localPackageManager = paramContext.getPackageManager();
        localObject1 = null;
        try
        {
          PackageInfo localPackageInfo = localPackageManager.getPackageInfo("com.tencent.mtt", 0);
          localObject1 = localPackageInfo;
          localBrowserInfo.browserType = 2;
          localObject1 = localPackageInfo;
          localBrowserInfo.quahead = "ADRQB_";
          Object localObject2 = localPackageInfo;
          if (localPackageInfo != null)
          {
            localObject1 = localPackageInfo;
            localObject2 = localPackageInfo;
            if (localPackageInfo.versionCode > 420000)
            {
              localObject1 = localPackageInfo;
              localBrowserInfo.versionName = localPackageInfo.versionName;
              localObject1 = localPackageInfo;
              localBrowserInfo.ver = localPackageInfo.versionCode;
              localObject1 = localPackageInfo;
              localObject2 = new StringBuilder();
              localObject1 = localPackageInfo;
              ((StringBuilder)localObject2).append(localBrowserInfo.quahead);
              localObject1 = localPackageInfo;
              ((StringBuilder)localObject2).append(localPackageInfo.versionName.replaceAll("\\.", ""));
              localObject1 = localPackageInfo;
              localBrowserInfo.quahead = ((StringBuilder)localObject2).toString();
              return localBrowserInfo;
            }
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException3)
        {
          localNameNotFoundException3.printStackTrace();
          localObject3 = localObject1;
        }
      }
      catch (Exception paramContext)
      {
        PackageManager localPackageManager;
        Object localObject1;
        Object localObject3;
        paramContext.printStackTrace();
      }
      try
      {
        localObject1 = localPackageManager.getPackageInfo("com.tencent.qbx", 0);
        localObject3 = localObject1;
        localBrowserInfo.browserType = 0;
        localObject3 = localObject1;
        localBrowserInfo.quahead = "ADRQBX_";
        paramContext = (Context)localObject1;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException1)
      {
        continue;
      }
      try
      {
        localObject1 = localPackageManager.getPackageInfo("com.tencent.qbx5", 0);
        localObject3 = localObject1;
        localBrowserInfo.browserType = 1;
        localObject3 = localObject1;
        localBrowserInfo.quahead = "ADRQBX5_";
        paramContext = (Context)localObject1;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException2) {}
    }
    localObject1 = localObject3;
    for (;;)
    {
      try
      {
        localObject3 = localPackageManager.getPackageInfo("com.tencent.mtt", 0);
        localObject1 = localObject3;
        localBrowserInfo.browserType = 2;
        localObject1 = localObject3;
        localBrowserInfo.quahead = "ADRQB_";
        paramContext = (Context)localObject3;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException4)
      {
        continue;
      }
      try
      {
        localObject3 = localPackageManager.getPackageInfo("com.tencent.mtt.x86", 0);
        localObject1 = localObject3;
        localBrowserInfo.browserType = 2;
        localObject1 = localObject3;
        localBrowserInfo.quahead = "ADRQB_";
        paramContext = (Context)localObject3;
      }
      catch (Exception localException2)
      {
        continue;
      }
      try
      {
        localObject3 = a(paramContext, Uri.parse("http://mdc.html5.qq.com/mh?channel_id=50079&u="));
        paramContext = (Context)localObject1;
        if (localObject3 != null)
        {
          paramContext = (Context)localObject1;
          if (!TextUtils.isEmpty(((a)localObject3).b)) {
            paramContext = localPackageManager.getPackageInfo(((a)localObject3).b, 0);
          }
        }
      }
      catch (Exception paramContext)
      {
        paramContext = localNameNotFoundException2;
        continue;
      }
      try
      {
        localBrowserInfo.browserType = 2;
        localBrowserInfo.quahead = "ADRQB_";
      }
      catch (Exception localException1) {}
    }
    if (paramContext != null)
    {
      localBrowserInfo.versionName = paramContext.versionName;
      localBrowserInfo.ver = paramContext.versionCode;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(localBrowserInfo.quahead);
      ((StringBuilder)localObject1).append(paramContext.versionName.replaceAll("\\.", ""));
      localBrowserInfo.quahead = ((StringBuilder)localObject1).toString();
      return localBrowserInfo;
    }
    return localBrowserInfo;
  }
  
  public static String getDownloadUrlWithQb(String paramString)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://mdc.html5.qq.com/mh?channel_id=50079&u=");
      localStringBuilder.append(URLEncoder.encode(paramString, "UTF-8"));
      paramString = localStringBuilder.toString();
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      paramString.printStackTrace();
    }
    return "http://mdc.html5.qq.com/mh?channel_id=50079&u=";
  }
  
  public static Intent getIntent(Context paramContext, String paramString)
  {
    if (paramContext == null) {
      return null;
    }
    Object localObject2 = paramString;
    Object localObject1;
    if (!a(paramString))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("http://");
      ((StringBuilder)localObject1).append(paramString);
      localObject2 = ((StringBuilder)localObject1).toString();
    }
    try
    {
      paramString = Uri.parse((String)localObject2);
      if (paramString == null) {
        return null;
      }
      localObject1 = paramString;
      if (paramString.getScheme().toLowerCase().equals("qb"))
      {
        localObject1 = paramString;
        if (!isSupportQBScheme(paramContext))
        {
          paramString = new StringBuilder();
          paramString.append("http://mdc.html5.qq.com/mh?channel_id=50079&u=");
          paramString.append(URLEncoder.encode((String)localObject2, "UTF-8"));
          localObject1 = Uri.parse(paramString.toString());
        }
      }
      BrowserInfo localBrowserInfo = getBrowserInfo(paramContext);
      if (localBrowserInfo.browserType == -1) {
        return null;
      }
      if ((localBrowserInfo.browserType == 2) && (localBrowserInfo.ver < 33)) {
        return null;
      }
      localObject2 = new Intent("android.intent.action.VIEW");
      if (localBrowserInfo.browserType == 2)
      {
        if ((localBrowserInfo.ver >= 33) && (localBrowserInfo.ver <= 39))
        {
          ((Intent)localObject2).setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");
          paramString = (String)localObject2;
        }
        else if ((localBrowserInfo.ver >= 40) && (localBrowserInfo.ver <= 45))
        {
          ((Intent)localObject2).setClassName("com.tencent.mtt", "com.tencent.mtt.SplashActivity");
          paramString = (String)localObject2;
        }
        else
        {
          paramString = (String)localObject2;
          if (localBrowserInfo.ver >= 46)
          {
            localObject2 = new Intent("com.tencent.QQBrowser.action.VIEW");
            paramContext = a(paramContext, (Uri)localObject1);
            paramString = (String)localObject2;
            if (paramContext != null)
            {
              paramString = (String)localObject2;
              if (!TextUtils.isEmpty(paramContext.a))
              {
                ((Intent)localObject2).setClassName(paramContext.b, paramContext.a);
                paramString = (String)localObject2;
              }
            }
          }
        }
      }
      else if (localBrowserInfo.browserType == 1)
      {
        if (localBrowserInfo.ver == 1)
        {
          ((Intent)localObject2).setClassName("com.tencent.qbx5", "com.tencent.qbx5.MainActivity");
          paramString = (String)localObject2;
        }
        else
        {
          paramString = (String)localObject2;
          if (localBrowserInfo.ver == 2)
          {
            ((Intent)localObject2).setClassName("com.tencent.qbx5", "com.tencent.qbx5.SplashActivity");
            paramString = (String)localObject2;
          }
        }
      }
      else if (localBrowserInfo.browserType == 0)
      {
        if ((localBrowserInfo.ver >= 4) && (localBrowserInfo.ver <= 6))
        {
          ((Intent)localObject2).setClassName("com.tencent.qbx", "com.tencent.qbx.SplashActivity");
          paramString = (String)localObject2;
        }
        else
        {
          paramString = (String)localObject2;
          if (localBrowserInfo.ver > 6)
          {
            localObject2 = new Intent("com.tencent.QQBrowser.action.VIEW");
            paramContext = a(paramContext, (Uri)localObject1);
            paramString = (String)localObject2;
            if (paramContext != null)
            {
              paramString = (String)localObject2;
              if (!TextUtils.isEmpty(paramContext.a))
              {
                ((Intent)localObject2).setClassName(paramContext.b, paramContext.a);
                paramString = (String)localObject2;
              }
            }
          }
        }
      }
      else
      {
        localObject2 = new Intent("com.tencent.QQBrowser.action.VIEW");
        paramContext = a(paramContext, (Uri)localObject1);
        paramString = (String)localObject2;
        if (paramContext != null)
        {
          paramString = (String)localObject2;
          if (!TextUtils.isEmpty(paramContext.a))
          {
            ((Intent)localObject2).setClassName(paramContext.b, paramContext.a);
            paramString = (String)localObject2;
          }
        }
      }
      paramString.setData((Uri)localObject1);
      paramString.addFlags(268435456);
      return paramString;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  public static String getValidQBUrl(Context paramContext, String paramString)
  {
    if (paramString.toLowerCase().startsWith("qb://"))
    {
      paramContext = getBrowserInfo(paramContext);
      int j = paramContext.browserType;
      int i = 1;
      if ((j != -1) && ((paramContext.browserType != 2) || (paramContext.ver >= 33))) {
        i = 0;
      }
      if (i != 0) {
        return getDownloadUrlWithQb(paramString);
      }
    }
    return paramString;
  }
  
  public static boolean isBrowserInstalled(Context paramContext)
  {
    if (isQbFakeNoInstallation(paramContext)) {
      return false;
    }
    return getBrowserInfo(paramContext).browserType != -1;
  }
  
  public static boolean isQQDownloaderInstalled(Context paramContext)
  {
    paramContext = paramContext.getPackageManager();
    try
    {
      paramContext = paramContext.getPackageInfo("com.tencent.android.qqdownloader", 0);
      return paramContext != null;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static boolean isQbFakeNoInstallation(Context paramContext)
  {
    return paramContext.getApplicationContext().getSharedPreferences("x5_proxy_setting", 0).getBoolean("qb_install_status", false);
  }
  
  public static boolean isSupportQBScheme(Context paramContext)
  {
    paramContext = getBrowserInfo(paramContext);
    if (paramContext.browserType == -1) {
      return false;
    }
    return (paramContext.browserType != 2) || (paramContext.ver >= 42);
  }
  
  public static boolean isSupportingDownloadDataSend(Context paramContext)
  {
    try
    {
      paramContext = getBrowserInfo(paramContext);
      if (paramContext.browserType == 2)
      {
        int i = Integer.parseInt(paramContext.versionName.replaceAll("\\.", ""));
        if (i >= 6400000) {
          return true;
        }
      }
    }
    catch (NumberFormatException paramContext)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean isSupportingDownloadRefer(Context paramContext)
  {
    try
    {
      paramContext = getBrowserInfo(paramContext);
      if (paramContext.browserType == 2)
      {
        int i = Integer.parseInt(paramContext.versionName.replaceAll("\\.", ""));
        if (i >= 6200000) {
          return true;
        }
      }
    }
    catch (NumberFormatException paramContext)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean isSupportingTbsTips(Context paramContext)
  {
    try
    {
      paramContext = getBrowserInfo(paramContext);
      if (paramContext.browserType == 2)
      {
        int i = Integer.parseInt(paramContext.versionName.replaceAll("\\.", ""));
        if (i >= 580000) {
          return true;
        }
      }
    }
    catch (NumberFormatException paramContext)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean openDocWithQb(Context paramContext, String paramString1, int paramInt, String paramString2, HashMap<String, String> paramHashMap)
  {
    try
    {
      Intent localIntent = new Intent("com.tencent.QQBrowser.action.sdk.document");
      if (paramHashMap != null)
      {
        Object localObject = paramHashMap.keySet();
        if (localObject != null)
        {
          localObject = ((Set)localObject).iterator();
          while (((Iterator)localObject).hasNext())
          {
            String str1 = (String)((Iterator)localObject).next();
            String str2 = (String)paramHashMap.get(str1);
            if (!TextUtils.isEmpty(str2)) {
              localIntent.putExtra(str1, str2);
            }
          }
        }
      }
      paramHashMap = new File(paramString1);
      localIntent.putExtra("key_reader_sdk_id", 3);
      localIntent.putExtra("key_reader_sdk_type", paramInt);
      if (paramInt == 0) {
        localIntent.putExtra("key_reader_sdk_path", paramString1);
      } else {
        localIntent.putExtra("key_reader_sdk_url", paramString1);
      }
      localIntent.putExtra("key_reader_sdk_format", paramString2);
      localIntent.setData(FileProvider.fromFile(paramHashMap));
      localIntent.addFlags(3);
      localIntent.putExtra("loginType", a(paramContext));
      paramContext.startActivity(localIntent);
      return true;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static boolean openVideoWithQb(Context paramContext, String paramString, HashMap<String, String> paramHashMap)
  {
    Object localObject = Uri.parse(paramString);
    paramString = new Intent("android.intent.action.VIEW");
    paramString.setFlags(268435456);
    paramString.setDataAndType((Uri)localObject, "video/*");
    if (paramHashMap != null)
    {
      localObject = paramHashMap.keySet();
      if (localObject != null)
      {
        localObject = ((Set)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          String str1 = (String)((Iterator)localObject).next();
          String str2 = (String)paramHashMap.get(str1);
          if (!TextUtils.isEmpty(str2)) {
            paramString.putExtra(str1, str2);
          }
        }
      }
    }
    try
    {
      paramString.putExtra("loginType", a(paramContext));
      paramString.setComponent(new ComponentName("com.tencent.mtt", "com.tencent.mtt.browser.video.H5VideoThrdcallActivity"));
      paramContext.startActivity(paramString);
      i = 1;
    }
    catch (Throwable paramHashMap)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    if (i == 0) {
      try
      {
        paramString.setComponent(null);
        paramContext.startActivity(paramString);
        return true;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        return false;
      }
    }
    return true;
  }
  
  /* Error */
  public static boolean verifySignature(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 6
    //   8: aconst_null
    //   9: astore 5
    //   11: new 457	java/util/jar/JarFile
    //   14: dup
    //   15: aload_0
    //   16: invokespecial 460	java/util/jar/JarFile:<init>	(Ljava/io/File;)V
    //   19: astore_0
    //   20: aload 5
    //   22: astore_3
    //   23: aload 6
    //   25: astore 4
    //   27: aload_0
    //   28: ldc_w 462
    //   31: invokevirtual 466	java/util/jar/JarFile:getJarEntry	(Ljava/lang/String;)Ljava/util/jar/JarEntry;
    //   34: astore 7
    //   36: aload 7
    //   38: ifnonnull +9 -> 47
    //   41: aload_0
    //   42: invokevirtual 469	java/util/jar/JarFile:close	()V
    //   45: iconst_0
    //   46: ireturn
    //   47: aload 5
    //   49: astore_3
    //   50: aload 6
    //   52: astore 4
    //   54: sipush 8192
    //   57: newarray <illegal type>
    //   59: astore 8
    //   61: aload 5
    //   63: astore_3
    //   64: aload 6
    //   66: astore 4
    //   68: aload_0
    //   69: aload 7
    //   71: invokevirtual 473	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   74: astore 5
    //   76: aload 5
    //   78: astore_3
    //   79: aload 5
    //   81: astore 4
    //   83: aload 5
    //   85: aload 8
    //   87: iconst_0
    //   88: aload 8
    //   90: arraylength
    //   91: invokevirtual 479	java/io/InputStream:read	([BII)I
    //   94: iconst_m1
    //   95: if_icmpeq +6 -> 101
    //   98: goto -22 -> 76
    //   101: aload 5
    //   103: astore_3
    //   104: aload 5
    //   106: astore 4
    //   108: aload 5
    //   110: invokevirtual 480	java/io/InputStream:close	()V
    //   113: aload 5
    //   115: astore_3
    //   116: aload 5
    //   118: astore 4
    //   120: aload 7
    //   122: invokevirtual 486	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   125: astore 6
    //   127: aload 5
    //   129: astore_3
    //   130: aload 5
    //   132: astore 4
    //   134: aload 6
    //   136: arraylength
    //   137: istore_1
    //   138: iload_1
    //   139: iconst_1
    //   140: if_icmpge +19 -> 159
    //   143: aload 5
    //   145: ifnull +8 -> 153
    //   148: aload 5
    //   150: invokevirtual 480	java/io/InputStream:close	()V
    //   153: aload_0
    //   154: invokevirtual 469	java/util/jar/JarFile:close	()V
    //   157: iconst_0
    //   158: ireturn
    //   159: aload 5
    //   161: astore_3
    //   162: aload 5
    //   164: astore 4
    //   166: aload 6
    //   168: iconst_0
    //   169: aaload
    //   170: invokestatic 488	com/tencent/common/utils/MttLoader:a	(Ljava/security/cert/Certificate;)Ljava/lang/String;
    //   173: astore 6
    //   175: aload 6
    //   177: ifnull +39 -> 216
    //   180: aload 5
    //   182: astore_3
    //   183: aload 5
    //   185: astore 4
    //   187: aload 6
    //   189: ldc_w 490
    //   192: invokevirtual 299	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   195: istore_2
    //   196: iload_2
    //   197: ifeq +19 -> 216
    //   200: aload 5
    //   202: ifnull +8 -> 210
    //   205: aload 5
    //   207: invokevirtual 480	java/io/InputStream:close	()V
    //   210: aload_0
    //   211: invokevirtual 469	java/util/jar/JarFile:close	()V
    //   214: iconst_1
    //   215: ireturn
    //   216: aload_0
    //   217: astore_3
    //   218: aload 5
    //   220: ifnull +10 -> 230
    //   223: aload 5
    //   225: invokevirtual 480	java/io/InputStream:close	()V
    //   228: aload_0
    //   229: astore_3
    //   230: aload_3
    //   231: invokevirtual 469	java/util/jar/JarFile:close	()V
    //   234: iconst_0
    //   235: ireturn
    //   236: astore 5
    //   238: aload_3
    //   239: astore 4
    //   241: aload_0
    //   242: astore_3
    //   243: aload 5
    //   245: astore_0
    //   246: goto +6 -> 252
    //   249: astore_0
    //   250: aconst_null
    //   251: astore_3
    //   252: aload 4
    //   254: ifnull +8 -> 262
    //   257: aload 4
    //   259: invokevirtual 480	java/io/InputStream:close	()V
    //   262: aload_3
    //   263: ifnull +7 -> 270
    //   266: aload_3
    //   267: invokevirtual 469	java/util/jar/JarFile:close	()V
    //   270: aload_0
    //   271: athrow
    //   272: aconst_null
    //   273: astore_0
    //   274: aload_3
    //   275: astore 4
    //   277: aload 4
    //   279: ifnull +8 -> 287
    //   282: aload 4
    //   284: invokevirtual 480	java/io/InputStream:close	()V
    //   287: aload_0
    //   288: ifnull +8 -> 296
    //   291: aload_0
    //   292: astore_3
    //   293: goto -63 -> 230
    //   296: iconst_0
    //   297: ireturn
    //   298: astore_0
    //   299: goto -27 -> 272
    //   302: astore_3
    //   303: goto -26 -> 277
    //   306: astore_0
    //   307: iconst_0
    //   308: ireturn
    //   309: astore_3
    //   310: goto -157 -> 153
    //   313: astore_0
    //   314: iconst_0
    //   315: ireturn
    //   316: astore_3
    //   317: goto -107 -> 210
    //   320: astore_0
    //   321: iconst_1
    //   322: ireturn
    //   323: astore_3
    //   324: aload_0
    //   325: astore_3
    //   326: goto -96 -> 230
    //   329: astore_0
    //   330: iconst_0
    //   331: ireturn
    //   332: astore 4
    //   334: goto -72 -> 262
    //   337: astore_3
    //   338: goto -68 -> 270
    //   341: astore_3
    //   342: goto -55 -> 287
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	345	0	paramFile	File
    //   137	4	1	i	int
    //   195	2	2	bool	boolean
    //   4	289	3	localObject1	Object
    //   302	1	3	localThrowable	Throwable
    //   309	1	3	localIOException1	java.io.IOException
    //   316	1	3	localIOException2	java.io.IOException
    //   323	1	3	localIOException3	java.io.IOException
    //   325	1	3	localFile	File
    //   337	1	3	localIOException4	java.io.IOException
    //   341	1	3	localIOException5	java.io.IOException
    //   1	282	4	localObject2	Object
    //   332	1	4	localIOException6	java.io.IOException
    //   9	215	5	localInputStream	java.io.InputStream
    //   236	8	5	localObject3	Object
    //   6	182	6	localObject4	Object
    //   34	87	7	localJarEntry	java.util.jar.JarEntry
    //   59	30	8	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   27	36	236	finally
    //   54	61	236	finally
    //   68	76	236	finally
    //   83	98	236	finally
    //   108	113	236	finally
    //   120	127	236	finally
    //   134	138	236	finally
    //   166	175	236	finally
    //   187	196	236	finally
    //   11	20	249	finally
    //   11	20	298	java/lang/Throwable
    //   27	36	302	java/lang/Throwable
    //   54	61	302	java/lang/Throwable
    //   68	76	302	java/lang/Throwable
    //   83	98	302	java/lang/Throwable
    //   108	113	302	java/lang/Throwable
    //   120	127	302	java/lang/Throwable
    //   134	138	302	java/lang/Throwable
    //   166	175	302	java/lang/Throwable
    //   187	196	302	java/lang/Throwable
    //   41	45	306	java/io/IOException
    //   148	153	309	java/io/IOException
    //   153	157	313	java/io/IOException
    //   205	210	316	java/io/IOException
    //   210	214	320	java/io/IOException
    //   223	228	323	java/io/IOException
    //   230	234	329	java/io/IOException
    //   257	262	332	java/io/IOException
    //   266	270	337	java/io/IOException
    //   282	287	341	java/io/IOException
  }
  
  public static class BrowserInfo
  {
    public int browserType = -1;
    public String quahead = "";
    public int ver = -1;
    public String versionName = "";
  }
  
  private static class a
  {
    public String a = "";
    public String b = "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\MttLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */