package com.tencent.smtt.downloader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.tencent.smtt.downloader.c;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class b
{
  public static String a = "";
  public static String b = "";
  public static String c = "";
  public static String d = "";
  public static String e = "";
  
  public static int a(Context paramContext)
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
  
  private static PackageInfo a(String paramString, int paramInt)
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
          break label349;
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
      label349:
      Method localMethod1 = null;
    }
  }
  
  /* Error */
  public static String a()
  {
    // Byte code:
    //   0: getstatic 131	com/tencent/smtt/downloader/utils/b:c	Ljava/lang/String;
    //   3: invokestatic 137	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: ifne +7 -> 13
    //   9: getstatic 131	com/tencent/smtt/downloader/utils/b:c	Ljava/lang/String;
    //   12: areturn
    //   13: new 139	java/io/InputStreamReader
    //   16: dup
    //   17: invokestatic 145	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   20: ldc -109
    //   22: invokevirtual 151	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   25: invokevirtual 157	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   28: invokespecial 160	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   31: astore_1
    //   32: new 162	java/io/BufferedReader
    //   35: dup
    //   36: aload_1
    //   37: invokespecial 165	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   40: astore_0
    //   41: aload_0
    //   42: astore_2
    //   43: aload_1
    //   44: astore_3
    //   45: aload_0
    //   46: invokevirtual 168	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   49: ldc -86
    //   51: invokevirtual 173	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   54: ifeq +20 -> 74
    //   57: aload_0
    //   58: astore_2
    //   59: aload_1
    //   60: astore_3
    //   61: ldc -81
    //   63: invokestatic 178	com/tencent/smtt/downloader/utils/b:a	(Ljava/lang/String;)Ljava/lang/String;
    //   66: astore 4
    //   68: aload 4
    //   70: astore_2
    //   71: goto +20 -> 91
    //   74: aload_0
    //   75: astore_2
    //   76: aload_1
    //   77: astore_3
    //   78: ldc -76
    //   80: invokestatic 185	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   83: invokestatic 178	com/tencent/smtt/downloader/utils/b:a	(Ljava/lang/String;)Ljava/lang/String;
    //   86: astore 4
    //   88: aload 4
    //   90: astore_2
    //   91: aload_0
    //   92: invokevirtual 188	java/io/BufferedReader:close	()V
    //   95: aload_1
    //   96: invokevirtual 189	java/io/InputStreamReader:close	()V
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
    //   137: ldc -76
    //   139: invokestatic 185	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   142: invokestatic 178	com/tencent/smtt/downloader/utils/b:a	(Ljava/lang/String;)Ljava/lang/String;
    //   145: astore 5
    //   147: aload_0
    //   148: astore_2
    //   149: aload_1
    //   150: astore_3
    //   151: aload 4
    //   153: invokevirtual 190	java/lang/Throwable:printStackTrace	()V
    //   156: aload_0
    //   157: ifnull +7 -> 164
    //   160: aload_0
    //   161: invokevirtual 188	java/io/BufferedReader:close	()V
    //   164: aload_1
    //   165: ifnull +7 -> 172
    //   168: aload_1
    //   169: invokevirtual 189	java/io/InputStreamReader:close	()V
    //   172: aload 5
    //   174: areturn
    //   175: astore_0
    //   176: aload_3
    //   177: astore_1
    //   178: aload_2
    //   179: ifnull +7 -> 186
    //   182: aload_2
    //   183: invokevirtual 188	java/io/BufferedReader:close	()V
    //   186: aload_1
    //   187: ifnull +7 -> 194
    //   190: aload_1
    //   191: invokevirtual 189	java/io/InputStreamReader:close	()V
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
  
  public static String a(Context paramContext)
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
  
  private static String a(Context paramContext, File paramFile, boolean paramBoolean)
  {
    if (paramBoolean) {}
    try
    {
      paramContext = a(paramFile.getAbsolutePath(), 65);
      break label31;
      paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramFile.getAbsolutePath(), 65);
      label31:
      if (paramContext == null) {
        break label124;
      }
      if ((paramContext.signatures != null) && (paramContext.signatures.length > 0)) {
        paramContext = paramContext.signatures[0];
      } else {
        h.c("AppUtil", "[getSignatureFromApk] pkgInfo is not null BUT signatures is null!");
      }
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        continue;
        paramContext = null;
      }
    }
    if (paramContext != null)
    {
      paramContext = paramContext.toCharsString();
      return paramContext;
      paramContext = new StringBuilder();
      paramContext.append("getSign ");
      paramContext.append(paramFile);
      paramContext.append("failed");
      h.a("AppUtil", paramContext.toString());
    }
    return null;
  }
  
  public static String a(Context paramContext, String paramString)
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
  
  /* Error */
  public static String a(Context paramContext, boolean paramBoolean, File paramFile)
  {
    // Byte code:
    //   0: aload_2
    //   1: ifnull +343 -> 344
    //   4: aload_2
    //   5: invokevirtual 272	java/io/File:exists	()Z
    //   8: ifne +6 -> 14
    //   11: goto +333 -> 344
    //   14: iload_1
    //   15: ifeq +132 -> 147
    //   18: iconst_2
    //   19: newarray <illegal type>
    //   21: astore 5
    //   23: new 274	java/io/RandomAccessFile
    //   26: dup
    //   27: aload_2
    //   28: ldc_w 276
    //   31: invokespecial 279	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   34: astore 4
    //   36: aload 4
    //   38: astore_3
    //   39: aload 4
    //   41: aload 5
    //   43: invokevirtual 283	java/io/RandomAccessFile:read	([B)I
    //   46: pop
    //   47: aload 4
    //   49: astore_3
    //   50: new 59	java/lang/String
    //   53: dup
    //   54: aload 5
    //   56: invokespecial 286	java/lang/String:<init>	([B)V
    //   59: ldc_w 288
    //   62: invokevirtual 292	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   65: ifne +19 -> 84
    //   68: aload 4
    //   70: invokevirtual 293	java/io/RandomAccessFile:close	()V
    //   73: ldc 8
    //   75: areturn
    //   76: astore_0
    //   77: aload_0
    //   78: invokevirtual 294	java/io/IOException:printStackTrace	()V
    //   81: ldc 8
    //   83: areturn
    //   84: aload 4
    //   86: invokevirtual 293	java/io/RandomAccessFile:close	()V
    //   89: goto +58 -> 147
    //   92: astore 5
    //   94: goto +14 -> 108
    //   97: astore_0
    //   98: aconst_null
    //   99: astore_3
    //   100: goto +33 -> 133
    //   103: astore 5
    //   105: aconst_null
    //   106: astore 4
    //   108: aload 4
    //   110: astore_3
    //   111: aload 5
    //   113: invokevirtual 125	java/lang/Exception:printStackTrace	()V
    //   116: aload 4
    //   118: invokevirtual 293	java/io/RandomAccessFile:close	()V
    //   121: goto +26 -> 147
    //   124: astore_3
    //   125: aload_3
    //   126: invokevirtual 294	java/io/IOException:printStackTrace	()V
    //   129: goto +18 -> 147
    //   132: astore_0
    //   133: aload_3
    //   134: invokevirtual 293	java/io/RandomAccessFile:close	()V
    //   137: goto +8 -> 145
    //   140: astore_2
    //   141: aload_2
    //   142: invokevirtual 294	java/io/IOException:printStackTrace	()V
    //   145: aload_0
    //   146: athrow
    //   147: aload_0
    //   148: invokevirtual 298	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   151: invokevirtual 24	android/content/Context:getPackageName	()Ljava/lang/String;
    //   154: ldc_w 300
    //   157: invokevirtual 173	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   160: ifeq +38 -> 198
    //   163: ldc -47
    //   165: ldc_w 302
    //   168: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   171: aload_2
    //   172: invokestatic 305	com/tencent/smtt/downloader/utils/b:a	(Ljava/io/File;)Ljava/lang/String;
    //   175: astore_3
    //   176: aload_3
    //   177: ifnull +21 -> 198
    //   180: ldc -47
    //   182: ldc_w 307
    //   185: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   188: aload_3
    //   189: areturn
    //   190: ldc -47
    //   192: ldc_w 309
    //   195: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   198: ldc -47
    //   200: ldc_w 311
    //   203: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   206: aload_0
    //   207: aload_2
    //   208: iconst_0
    //   209: invokestatic 313	com/tencent/smtt/downloader/utils/b:a	(Landroid/content/Context;Ljava/io/File;Z)Ljava/lang/String;
    //   212: astore 4
    //   214: new 223	java/lang/StringBuilder
    //   217: dup
    //   218: invokespecial 224	java/lang/StringBuilder:<init>	()V
    //   221: astore_3
    //   222: aload_3
    //   223: ldc_w 315
    //   226: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload_3
    //   231: aload 4
    //   233: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: ldc -47
    //   239: aload_3
    //   240: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   243: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   246: aload 4
    //   248: astore_3
    //   249: aload 4
    //   251: ifnonnull +43 -> 294
    //   254: aload_2
    //   255: invokestatic 305	com/tencent/smtt/downloader/utils/b:a	(Ljava/io/File;)Ljava/lang/String;
    //   258: astore_3
    //   259: new 223	java/lang/StringBuilder
    //   262: dup
    //   263: invokespecial 224	java/lang/StringBuilder:<init>	()V
    //   266: astore 4
    //   268: aload 4
    //   270: ldc_w 317
    //   273: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: aload 4
    //   279: aload_3
    //   280: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: pop
    //   284: ldc -47
    //   286: aload 4
    //   288: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   294: aload_3
    //   295: astore 4
    //   297: aload_3
    //   298: ifnonnull +43 -> 341
    //   301: aload_0
    //   302: aload_2
    //   303: iconst_1
    //   304: invokestatic 313	com/tencent/smtt/downloader/utils/b:a	(Landroid/content/Context;Ljava/io/File;Z)Ljava/lang/String;
    //   307: astore 4
    //   309: new 223	java/lang/StringBuilder
    //   312: dup
    //   313: invokespecial 224	java/lang/StringBuilder:<init>	()V
    //   316: astore_0
    //   317: aload_0
    //   318: ldc_w 319
    //   321: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   324: pop
    //   325: aload_0
    //   326: aload 4
    //   328: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: pop
    //   332: ldc -47
    //   334: aload_0
    //   335: invokevirtual 238	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   338: invokestatic 240	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   341: aload 4
    //   343: areturn
    //   344: ldc 8
    //   346: areturn
    //   347: astore_3
    //   348: goto -158 -> 190
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	351	0	paramContext	Context
    //   0	351	1	paramBoolean	boolean
    //   0	351	2	paramFile	File
    //   38	73	3	localObject1	Object
    //   124	10	3	localIOException	java.io.IOException
    //   175	123	3	localObject2	Object
    //   347	1	3	localThrowable	Throwable
    //   34	308	4	localObject3	Object
    //   21	34	5	arrayOfByte	byte[]
    //   92	1	5	localException1	Exception
    //   103	9	5	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   68	73	76	java/io/IOException
    //   39	47	92	java/lang/Exception
    //   50	68	92	java/lang/Exception
    //   18	36	97	finally
    //   18	36	103	java/lang/Exception
    //   84	89	124	java/io/IOException
    //   116	121	124	java/io/IOException
    //   39	47	132	finally
    //   50	68	132	finally
    //   111	116	132	finally
    //   133	137	140	java/io/IOException
    //   147	176	347	java/lang/Throwable
    //   180	188	347	java/lang/Throwable
  }
  
  private static String a(File paramFile)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[getSignatureFromApk]## file=");
    ((StringBuilder)localObject).append(paramFile);
    h.d("AppUtil", ((StringBuilder)localObject).toString());
    try
    {
      localObject = new JarFile(paramFile);
      paramFile = ((JarFile)localObject).getJarEntry("AndroidManifest.xml");
      byte[] arrayOfByte = new byte[' '];
      String str1 = a(a(localObject, paramFile, arrayOfByte)[0].getEncoded());
      Enumeration localEnumeration = ((JarFile)localObject).entries();
      while (localEnumeration.hasMoreElements())
      {
        paramFile = (JarEntry)localEnumeration.nextElement();
        String str2 = paramFile.getName();
        if (str2 != null)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("[getSignatureFromApk]## loadCertificates & check:");
          localStringBuilder.append(str2);
          h.d("AppUtil", localStringBuilder.toString());
          paramFile = a((JarFile)localObject, paramFile, arrayOfByte);
          if (paramFile != null)
          {
            paramFile = a(paramFile[0].getEncoded());
          }
          else
          {
            h.d("AppUtil", "[getSignatureFromApk]## certs2 is null!");
            paramFile = null;
          }
          if (paramFile == null)
          {
            h.d("AppUtil", "[getSignatureFromApk]## loadCertificates failed!");
            if (!str2.startsWith("META-INF/")) {
              return null;
            }
          }
          else
          {
            boolean bool = paramFile.equals(str1);
            paramFile = new StringBuilder();
            paramFile.append("[getSignatureFromApk]## loadCertificates check:");
            paramFile.append(bool);
            h.d("AppUtil", paramFile.toString());
            if (!bool) {
              return null;
            }
          }
        }
      }
      return str1;
    }
    catch (Exception paramFile)
    {
      paramFile.printStackTrace();
    }
    return null;
  }
  
  private static String a(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    return str;
  }
  
  private static String a(byte[] paramArrayOfByte)
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
  
  public static boolean a()
  {
    return true;
  }
  
  private static Certificate[] a(JarFile paramJarFile, JarEntry paramJarEntry, byte[] paramArrayOfByte)
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
  
  public static String b(Context paramContext)
  {
    Object localObject = "";
    try
    {
      paramContext = c.a(paramContext).a.getString("tbs_guid", "");
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("getGuid guid is ");
      ((StringBuilder)localObject).append(paramContext);
      ((StringBuilder)localObject).toString();
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        paramContext = (Context)localObject;
      }
    }
  }
  
  public static String c(Context paramContext)
  {
    if (!TextUtils.isEmpty(a)) {
      return a;
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
  
  public static String d(Context paramContext)
  {
    if (!TextUtils.isEmpty(b)) {
      return b;
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
  
  public static String e(Context paramContext)
  {
    if (!TextUtils.isEmpty(e)) {
      return e;
    }
    try
    {
      e = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return e;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */