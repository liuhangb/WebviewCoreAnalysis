package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.tencent.tbs.common.utils.ApkUtil;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AppUtil
{
  private static final String LOGTAG = "AppUtil";
  public static String mAndroidID = "";
  public static String mCpu = "";
  public static String mImei = "";
  public static String mImsi = "";
  public static String mMac = "";
  
  public static String getAndroidID(Context paramContext)
  {
    if (!TextUtils.isEmpty(mAndroidID)) {
      return mAndroidID;
    }
    try
    {
      mAndroidID = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return mAndroidID;
  }
  
  public static String getAppMetadata(Context paramContext, String paramString)
  {
    try
    {
      String str = paramContext.getPackageName();
      paramContext = String.valueOf(paramContext.getPackageManager().getApplicationInfo(str, 128).metaData.get(paramString));
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    try
    {
      paramString = String.valueOf(Integer.toHexString(Integer.parseInt(paramContext)));
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return paramContext;
  }
  
  public static int getAppVersionCode(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageName();
      int i = paramContext.getPackageManager().getPackageInfo(str, 0).versionCode;
      return i;
    }
    catch (Exception paramContext) {}
    return 0;
  }
  
  public static String getAppVersionName(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageName();
      paramContext = paramContext.getPackageManager().getPackageInfo(str, 0).versionName;
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static String getCurrentNativeLibraryDir(Context paramContext)
  {
    int i = Build.VERSION.SDK_INT;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[getCurrentNativeLibraryDir] -- your current sdk_version is:");
    localStringBuilder.append(i);
    localStringBuilder.toString();
    if (i >= 9)
    {
      paramContext = paramContext.getApplicationInfo().nativeLibraryDir;
    }
    else if (i > 4)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getApplicationInfo().dataDir);
      localStringBuilder.append("/lib");
      paramContext = localStringBuilder.toString();
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getFilesDir().getParentFile().getPath());
      localStringBuilder.append("/lib");
      paramContext = localStringBuilder.toString();
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("[getCurrentNativeLibraryDir] -- nativeLibraryDir is:");
    localStringBuilder.append(paramContext);
    localStringBuilder.toString();
    return paramContext;
  }
  
  /* Error */
  public static String getDeviceCpuabi()
  {
    // Byte code:
    //   0: getstatic 163	com/tencent/tbs/common/download/qb/AppUtil:mCpu	Ljava/lang/String;
    //   3: invokestatic 33	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: ifne +7 -> 13
    //   9: getstatic 163	com/tencent/tbs/common/download/qb/AppUtil:mCpu	Ljava/lang/String;
    //   12: areturn
    //   13: new 165	java/io/InputStreamReader
    //   16: dup
    //   17: invokestatic 171	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   20: ldc -83
    //   22: invokevirtual 177	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   25: invokevirtual 183	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   28: invokespecial 186	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   31: astore_1
    //   32: new 188	java/io/BufferedReader
    //   35: dup
    //   36: aload_1
    //   37: invokespecial 191	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   40: astore_0
    //   41: aload_0
    //   42: astore_2
    //   43: aload_1
    //   44: astore_3
    //   45: aload_0
    //   46: invokevirtual 194	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   49: ldc -60
    //   51: invokevirtual 199	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   54: ifeq +20 -> 74
    //   57: aload_0
    //   58: astore_2
    //   59: aload_1
    //   60: astore_3
    //   61: ldc -55
    //   63: invokestatic 205	com/tencent/tbs/common/download/qb/AppUtil:notNullString	(Ljava/lang/String;)Ljava/lang/String;
    //   66: astore 4
    //   68: aload 4
    //   70: astore_2
    //   71: goto +20 -> 91
    //   74: aload_0
    //   75: astore_2
    //   76: aload_1
    //   77: astore_3
    //   78: ldc -49
    //   80: invokestatic 212	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   83: invokestatic 205	com/tencent/tbs/common/download/qb/AppUtil:notNullString	(Ljava/lang/String;)Ljava/lang/String;
    //   86: astore 4
    //   88: aload 4
    //   90: astore_2
    //   91: aload_0
    //   92: invokevirtual 215	java/io/BufferedReader:close	()V
    //   95: aload_1
    //   96: invokevirtual 216	java/io/InputStreamReader:close	()V
    //   99: aload_2
    //   100: areturn
    //   101: astore 4
    //   103: goto +30 -> 133
    //   106: astore_0
    //   107: aconst_null
    //   108: astore_2
    //   109: goto +69 -> 178
    //   112: astore 4
    //   114: aconst_null
    //   115: astore_0
    //   116: goto +17 -> 133
    //   119: astore_0
    //   120: aconst_null
    //   121: astore_1
    //   122: aload_1
    //   123: astore_2
    //   124: goto +54 -> 178
    //   127: astore 4
    //   129: aconst_null
    //   130: astore_1
    //   131: aload_1
    //   132: astore_0
    //   133: aload_0
    //   134: astore_2
    //   135: aload_1
    //   136: astore_3
    //   137: ldc -49
    //   139: invokestatic 212	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   142: invokestatic 205	com/tencent/tbs/common/download/qb/AppUtil:notNullString	(Ljava/lang/String;)Ljava/lang/String;
    //   145: astore 5
    //   147: aload_0
    //   148: astore_2
    //   149: aload_1
    //   150: astore_3
    //   151: aload 4
    //   153: invokevirtual 217	java/lang/Throwable:printStackTrace	()V
    //   156: aload_0
    //   157: ifnull +7 -> 164
    //   160: aload_0
    //   161: invokevirtual 215	java/io/BufferedReader:close	()V
    //   164: aload_1
    //   165: ifnull +7 -> 172
    //   168: aload_1
    //   169: invokevirtual 216	java/io/InputStreamReader:close	()V
    //   172: aload 5
    //   174: areturn
    //   175: astore_0
    //   176: aload_3
    //   177: astore_1
    //   178: aload_2
    //   179: ifnull +7 -> 186
    //   182: aload_2
    //   183: invokevirtual 215	java/io/BufferedReader:close	()V
    //   186: aload_1
    //   187: ifnull +7 -> 194
    //   190: aload_1
    //   191: invokevirtual 216	java/io/InputStreamReader:close	()V
    //   194: aload_0
    //   195: athrow
    //   196: astore_0
    //   197: goto -102 -> 95
    //   200: astore_0
    //   201: aload_2
    //   202: areturn
    //   203: astore_0
    //   204: goto -40 -> 164
    //   207: astore_0
    //   208: goto -36 -> 172
    //   211: astore_2
    //   212: goto -26 -> 186
    //   215: astore_1
    //   216: goto -22 -> 194
    // Local variable table:
    //   start	length	slot	name	signature
    //   40	52	0	localBufferedReader	java.io.BufferedReader
    //   106	1	0	localObject1	Object
    //   115	1	0	localObject2	Object
    //   119	1	0	localObject3	Object
    //   132	29	0	localObject4	Object
    //   175	20	0	localObject5	Object
    //   196	1	0	localIOException1	java.io.IOException
    //   200	1	0	localIOException2	java.io.IOException
    //   203	1	0	localIOException3	java.io.IOException
    //   207	1	0	localIOException4	java.io.IOException
    //   31	160	1	localObject6	Object
    //   215	1	1	localIOException5	java.io.IOException
    //   42	160	2	localObject7	Object
    //   211	1	2	localIOException6	java.io.IOException
    //   44	133	3	localObject8	Object
    //   66	23	4	str1	String
    //   101	1	4	localThrowable1	Throwable
    //   112	1	4	localThrowable2	Throwable
    //   127	25	4	localThrowable3	Throwable
    //   145	28	5	str2	String
    // Exception table:
    //   from	to	target	type
    //   45	57	101	java/lang/Throwable
    //   61	68	101	java/lang/Throwable
    //   78	88	101	java/lang/Throwable
    //   32	41	106	finally
    //   32	41	112	java/lang/Throwable
    //   13	32	119	finally
    //   13	32	127	java/lang/Throwable
    //   45	57	175	finally
    //   61	68	175	finally
    //   78	88	175	finally
    //   137	147	175	finally
    //   151	156	175	finally
    //   91	95	196	java/io/IOException
    //   95	99	200	java/io/IOException
    //   160	164	203	java/io/IOException
    //   168	172	207	java/io/IOException
    //   182	186	211	java/io/IOException
    //   190	194	215	java/io/IOException
  }
  
  public static String getImei(Context paramContext)
  {
    if (!TextUtils.isEmpty(mImei)) {
      return mImei;
    }
    try
    {
      paramContext = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return "";
  }
  
  public static String getImsi(Context paramContext)
  {
    if (!TextUtils.isEmpty(mImsi)) {
      return mImsi;
    }
    try
    {
      paramContext = ((TelephonyManager)paramContext.getSystemService("phone")).getSubscriberId();
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return "";
  }
  
  public static String getMacAddress(Context paramContext)
  {
    if (!TextUtils.isEmpty(mMac)) {
      return mMac;
    }
    try
    {
      paramContext = (WifiManager)paramContext.getApplicationContext().getSystemService("wifi");
      if (paramContext == null) {
        paramContext = null;
      } else {
        paramContext = paramContext.getConnectionInfo();
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      return "";
    }
    for (paramContext = paramContext.getMacAddress();; paramContext = "")
    {
      return paramContext;
      if (paramContext != null) {
        break;
      }
    }
  }
  
  /* Error */
  public static String getMd5(File paramFile)
  {
    // Byte code:
    //   0: bipush 16
    //   2: newarray <illegal type>
    //   4: astore 6
    //   6: aload 6
    //   8: dup
    //   9: iconst_0
    //   10: ldc_w 258
    //   13: castore
    //   14: dup
    //   15: iconst_1
    //   16: ldc_w 259
    //   19: castore
    //   20: dup
    //   21: iconst_2
    //   22: ldc_w 260
    //   25: castore
    //   26: dup
    //   27: iconst_3
    //   28: ldc_w 261
    //   31: castore
    //   32: dup
    //   33: iconst_4
    //   34: ldc_w 262
    //   37: castore
    //   38: dup
    //   39: iconst_5
    //   40: ldc_w 263
    //   43: castore
    //   44: dup
    //   45: bipush 6
    //   47: ldc_w 264
    //   50: castore
    //   51: dup
    //   52: bipush 7
    //   54: ldc_w 265
    //   57: castore
    //   58: dup
    //   59: bipush 8
    //   61: ldc_w 266
    //   64: castore
    //   65: dup
    //   66: bipush 9
    //   68: ldc_w 267
    //   71: castore
    //   72: dup
    //   73: bipush 10
    //   75: ldc_w 268
    //   78: castore
    //   79: dup
    //   80: bipush 11
    //   82: ldc_w 269
    //   85: castore
    //   86: dup
    //   87: bipush 12
    //   89: ldc_w 270
    //   92: castore
    //   93: dup
    //   94: bipush 13
    //   96: ldc_w 271
    //   99: castore
    //   100: dup
    //   101: bipush 14
    //   103: ldc_w 272
    //   106: castore
    //   107: dup
    //   108: bipush 15
    //   110: ldc_w 273
    //   113: castore
    //   114: pop
    //   115: bipush 32
    //   117: newarray <illegal type>
    //   119: astore 7
    //   121: ldc_w 275
    //   124: invokestatic 281	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   127: astore 8
    //   129: new 283	java/io/FileInputStream
    //   132: dup
    //   133: aload_0
    //   134: invokespecial 286	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   137: astore 5
    //   139: aload 5
    //   141: astore_0
    //   142: sipush 8192
    //   145: newarray <illegal type>
    //   147: astore 9
    //   149: aload 5
    //   151: astore_0
    //   152: aload 5
    //   154: aload 9
    //   156: invokevirtual 290	java/io/FileInputStream:read	([B)I
    //   159: istore_1
    //   160: iconst_0
    //   161: istore_2
    //   162: iload_1
    //   163: iconst_m1
    //   164: if_icmpeq +18 -> 182
    //   167: aload 5
    //   169: astore_0
    //   170: aload 8
    //   172: aload 9
    //   174: iconst_0
    //   175: iload_1
    //   176: invokevirtual 294	java/security/MessageDigest:update	([BII)V
    //   179: goto -30 -> 149
    //   182: aload 5
    //   184: astore_0
    //   185: aload 8
    //   187: invokevirtual 298	java/security/MessageDigest:digest	()[B
    //   190: astore 8
    //   192: iconst_0
    //   193: istore_1
    //   194: goto +98 -> 292
    //   197: aload 5
    //   199: astore_0
    //   200: new 80	java/lang/String
    //   203: dup
    //   204: aload 7
    //   206: invokespecial 301	java/lang/String:<init>	([C)V
    //   209: astore 6
    //   211: aload 5
    //   213: invokevirtual 302	java/io/FileInputStream:close	()V
    //   216: aload 6
    //   218: areturn
    //   219: astore_0
    //   220: aload_0
    //   221: invokevirtual 303	java/io/IOException:printStackTrace	()V
    //   224: aload 6
    //   226: areturn
    //   227: astore 6
    //   229: goto +15 -> 244
    //   232: astore 5
    //   234: aconst_null
    //   235: astore_0
    //   236: goto +37 -> 273
    //   239: astore 6
    //   241: aconst_null
    //   242: astore 5
    //   244: aload 5
    //   246: astore_0
    //   247: aload 6
    //   249: invokevirtual 50	java/lang/Exception:printStackTrace	()V
    //   252: aload 5
    //   254: ifnull +15 -> 269
    //   257: aload 5
    //   259: invokevirtual 302	java/io/FileInputStream:close	()V
    //   262: aconst_null
    //   263: areturn
    //   264: astore_0
    //   265: aload_0
    //   266: invokevirtual 303	java/io/IOException:printStackTrace	()V
    //   269: aconst_null
    //   270: areturn
    //   271: astore 5
    //   273: aload_0
    //   274: ifnull +15 -> 289
    //   277: aload_0
    //   278: invokevirtual 302	java/io/FileInputStream:close	()V
    //   281: goto +8 -> 289
    //   284: astore_0
    //   285: aload_0
    //   286: invokevirtual 303	java/io/IOException:printStackTrace	()V
    //   289: aload 5
    //   291: athrow
    //   292: iload_2
    //   293: bipush 16
    //   295: if_icmpge -98 -> 197
    //   298: aload 8
    //   300: iload_2
    //   301: baload
    //   302: istore_3
    //   303: iload_1
    //   304: iconst_1
    //   305: iadd
    //   306: istore 4
    //   308: aload 7
    //   310: iload_1
    //   311: aload 6
    //   313: iload_3
    //   314: iconst_4
    //   315: iushr
    //   316: bipush 15
    //   318: iand
    //   319: caload
    //   320: castore
    //   321: iload 4
    //   323: iconst_1
    //   324: iadd
    //   325: istore_1
    //   326: aload 7
    //   328: iload 4
    //   330: aload 6
    //   332: iload_3
    //   333: bipush 15
    //   335: iand
    //   336: caload
    //   337: castore
    //   338: iload_2
    //   339: iconst_1
    //   340: iadd
    //   341: istore_2
    //   342: goto -50 -> 292
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	345	0	paramFile	File
    //   159	167	1	i	int
    //   161	181	2	j	int
    //   302	34	3	k	int
    //   306	23	4	m	int
    //   137	75	5	localFileInputStream	java.io.FileInputStream
    //   232	1	5	localObject1	Object
    //   242	16	5	localObject2	Object
    //   271	19	5	localObject3	Object
    //   4	221	6	localObject4	Object
    //   227	1	6	localException1	Exception
    //   239	92	6	localException2	Exception
    //   119	208	7	arrayOfChar	char[]
    //   127	172	8	localObject5	Object
    //   147	26	9	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   211	216	219	java/io/IOException
    //   142	149	227	java/lang/Exception
    //   152	160	227	java/lang/Exception
    //   170	179	227	java/lang/Exception
    //   185	192	227	java/lang/Exception
    //   200	211	227	java/lang/Exception
    //   121	139	232	finally
    //   121	139	239	java/lang/Exception
    //   257	262	264	java/io/IOException
    //   142	149	271	finally
    //   152	160	271	finally
    //   170	179	271	finally
    //   185	192	271	finally
    //   200	211	271	finally
    //   247	252	271	finally
    //   277	281	284	java/io/IOException
  }
  
  private static PackageInfo getPackageArchiveInfo(String paramString, int paramInt)
  {
    for (;;)
    {
      int i;
      try
      {
        Object localObject2 = Class.forName("android.content.pm.PackageParser");
        Object localObject1 = ((Class)localObject2).getDeclaredClasses();
        int j = localObject1.length;
        i = 0;
        if (i >= j) {
          break label356;
        }
        localMethod1 = localObject1[i];
        if (localMethod1.getName().compareTo("android.content.pm.PackageParser$Package") == 0)
        {
          Object localObject3 = ((Class)localObject2).getConstructor(new Class[] { String.class });
          Method localMethod2 = ((Class)localObject2).getDeclaredMethod("parsePackage", new Class[] { File.class, String.class, DisplayMetrics.class, Integer.TYPE });
          localObject1 = ((Class)localObject2).getDeclaredMethod("collectCertificates", new Class[] { localMethod1, Integer.TYPE });
          localMethod1 = ((Class)localObject2).getDeclaredMethod("generatePackageInfo", new Class[] { localMethod1, int[].class, Integer.TYPE, Long.TYPE, Long.TYPE });
          ((Constructor)localObject3).setAccessible(true);
          localMethod2.setAccessible(true);
          ((Method)localObject1).setAccessible(true);
          localMethod1.setAccessible(true);
          localObject2 = ((Constructor)localObject3).newInstance(new Object[] { paramString });
          localObject3 = new DisplayMetrics();
          ((DisplayMetrics)localObject3).setToDefaults();
          paramString = localMethod2.invoke(localObject2, new Object[] { new File(paramString), paramString, localObject3, Integer.valueOf(0) });
          if (paramString == null) {
            return null;
          }
          if ((paramInt & 0x40) != 0) {
            ((Method)localObject1).invoke(localObject2, new Object[] { paramString, Integer.valueOf(0) });
          }
          paramString = (PackageInfo)localMethod1.invoke(null, new Object[] { paramString, null, Integer.valueOf(paramInt), Integer.valueOf(0), Integer.valueOf(0) });
          return paramString;
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return null;
      }
      i += 1;
      continue;
      label356:
      Method localMethod1 = null;
    }
  }
  
  public static String getSignatureFromApk(Context paramContext, File paramFile)
  {
    if (!ApkUtil.isRealApkFile(paramFile)) {
      return "";
    }
    try
    {
      if (paramContext.getApplicationContext().getPackageName().contains("com.jd.jrapp"))
      {
        localObject1 = getSignatureFromApk(paramFile);
        if (localObject1 != null) {
          return (String)localObject1;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      Object localObject2;
      for (;;) {}
    }
    localObject2 = getSignatureFromApk(paramContext, paramFile, false);
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[AppUtil.getSignatureFromApk]  android api signature=");
    ((StringBuilder)localObject1).append((String)localObject2);
    ((StringBuilder)localObject1).toString();
    localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = getSignatureFromApk(paramFile);
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("[AppUtil.getSignatureFromApk]  java get signature=");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).toString();
    }
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject2 = getSignatureFromApk(paramContext, paramFile, true);
      paramContext = new StringBuilder();
      paramContext.append("[AppUtil.getSignatureFromApk]  android reflection signature=");
      paramContext.append((String)localObject2);
      paramContext.toString();
    }
    return (String)localObject2;
  }
  
  private static String getSignatureFromApk(Context paramContext, File paramFile, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramContext = getPackageArchiveInfo(paramFile.getAbsolutePath(), 65);
    } else {
      paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 65);
    }
    paramFile = null;
    if (paramContext != null)
    {
      if ((paramContext.signatures != null) && (paramContext.signatures.length > 0)) {
        paramContext = paramContext.signatures[0];
      } else {
        Log.w("AppUtil", "[getSignatureFromApk] pkgInfo is not null BUT signatures is null!");
      }
    }
    else {
      paramContext = null;
    }
    if (paramContext != null) {
      paramFile = paramContext.toCharsString();
    }
    return paramFile;
  }
  
  private static String getSignatureFromApk(File paramFile)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[getSignatureFromApk]## file=");
    ((StringBuilder)localObject).append(paramFile);
    ((StringBuilder)localObject).toString();
    for (;;)
    {
      try
      {
        localObject = new JarFile(paramFile);
        paramFile = ((JarFile)localObject).getJarEntry("AndroidManifest.xml");
        byte[] arrayOfByte = new byte[' '];
        String str1 = toCharsString(loadCertificates(localObject, paramFile, arrayOfByte)[0].getEncoded());
        Enumeration localEnumeration = ((JarFile)localObject).entries();
        if (localEnumeration.hasMoreElements())
        {
          paramFile = (JarEntry)localEnumeration.nextElement();
          String str2 = paramFile.getName();
          if (str2 == null) {
            continue;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("[getSignatureFromApk]## loadCertificates & check:");
          localStringBuilder.append(str2);
          localStringBuilder.toString();
          paramFile = loadCertificates((JarFile)localObject, paramFile, arrayOfByte);
          if (paramFile != null)
          {
            paramFile = toCharsString(paramFile[0].getEncoded());
            if (paramFile == null)
            {
              if (str2.startsWith("META-INF/")) {
                continue;
              }
              return null;
            }
            boolean bool = paramFile.equals(str1);
            paramFile = new StringBuilder();
            paramFile.append("[getSignatureFromApk]## loadCertificates check:");
            paramFile.append(bool);
            paramFile.toString();
            if (bool) {
              continue;
            }
            return null;
          }
        }
        else
        {
          return str1;
        }
      }
      catch (Exception paramFile)
      {
        paramFile.printStackTrace();
        return null;
      }
      paramFile = null;
    }
  }
  
  public static boolean isAppInstalled(Context paramContext, String paramString)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo(paramString, 0);
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      paramContext.printStackTrace();
      paramContext = null;
    }
    return paramContext != null;
  }
  
  private static Certificate[] loadCertificates(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
    throws Exception
  {
    paramJarFile = paramJarFile.getInputStream(paramJarEntry);
    while (paramJarFile.read(paramArrayOfByte, 0, paramArrayOfByte.length) != -1) {}
    paramJarFile.close();
    if (paramJarEntry != null) {
      return paramJarEntry.getCertificates();
    }
    return null;
  }
  
  private static String notNullString(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    return str;
  }
  
  private static String toCharsString(byte[] paramArrayOfByte)
  {
    int k = paramArrayOfByte.length;
    char[] arrayOfChar = new char[k * 2];
    int i = 0;
    while (i < k)
    {
      int n = paramArrayOfByte[i];
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
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\AppUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */