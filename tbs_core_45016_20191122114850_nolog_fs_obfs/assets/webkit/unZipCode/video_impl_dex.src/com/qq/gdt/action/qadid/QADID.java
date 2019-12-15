package com.qq.gdt.action.qadid;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.CRC32;

public class QADID
{
  private static final String[] a = { "Y29tLnRlbmNlbnQubW0=", "Y29tLnRlbmNlbnQubW9iaWxlcXE=" };
  
  private static long a()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    if (Build.VERSION.SDK_INT >= 18) {
      return localStatFs.getBlockSizeLong() * localStatFs.getBlockCountLong();
    }
    return localStatFs.getBlockSize() * localStatFs.getBlockCount();
  }
  
  /* Error */
  private static long a(Context paramContext)
  {
    // Byte code:
    //   0: getstatic 44	android/os/Build$VERSION:SDK_INT	I
    //   3: istore_1
    //   4: lconst_0
    //   5: lstore 4
    //   7: iload_1
    //   8: bipush 16
    //   10: if_icmplt +38 -> 48
    //   13: aload_0
    //   14: ldc 64
    //   16: invokevirtual 70	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   19: checkcast 72	android/app/ActivityManager
    //   22: astore_0
    //   23: new 74	android/app/ActivityManager$MemoryInfo
    //   26: dup
    //   27: invokespecial 75	android/app/ActivityManager$MemoryInfo:<init>	()V
    //   30: astore 6
    //   32: aload_0
    //   33: ifnull +233 -> 266
    //   36: aload_0
    //   37: aload 6
    //   39: invokevirtual 79	android/app/ActivityManager:getMemoryInfo	(Landroid/app/ActivityManager$MemoryInfo;)V
    //   42: aload 6
    //   44: getfield 83	android/app/ActivityManager$MemoryInfo:totalMem	J
    //   47: lreturn
    //   48: new 85	java/io/FileReader
    //   51: dup
    //   52: ldc 87
    //   54: invokespecial 88	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   57: astore_0
    //   58: new 90	java/io/BufferedReader
    //   61: dup
    //   62: aload_0
    //   63: sipush 4096
    //   66: invokespecial 93	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   69: astore 6
    //   71: aload 6
    //   73: invokevirtual 96	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   76: astore 7
    //   78: aload 7
    //   80: ifnull +13 -> 93
    //   83: aload 7
    //   85: ldc 98
    //   87: invokevirtual 102	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   90: ifeq -19 -> 71
    //   93: lload 4
    //   95: lstore_2
    //   96: aload 7
    //   98: ifnull +25 -> 123
    //   101: aload 7
    //   103: ldc 104
    //   105: invokevirtual 108	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   108: iconst_1
    //   109: aaload
    //   110: invokestatic 114	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   113: invokevirtual 117	java/lang/Long:longValue	()J
    //   116: lstore_2
    //   117: lload_2
    //   118: ldc2_w 118
    //   121: lmul
    //   122: lstore_2
    //   123: aload 6
    //   125: invokevirtual 122	java/io/BufferedReader:close	()V
    //   128: aload_0
    //   129: invokevirtual 123	java/io/FileReader:close	()V
    //   132: lload_2
    //   133: lreturn
    //   134: astore 8
    //   136: aload_0
    //   137: astore 7
    //   139: aload 8
    //   141: astore_0
    //   142: goto +43 -> 185
    //   145: goto +70 -> 215
    //   148: goto +95 -> 243
    //   151: astore 8
    //   153: aconst_null
    //   154: astore 6
    //   156: aload_0
    //   157: astore 7
    //   159: aload 8
    //   161: astore_0
    //   162: goto +23 -> 185
    //   165: aconst_null
    //   166: astore 6
    //   168: goto +47 -> 215
    //   171: aconst_null
    //   172: astore 6
    //   174: goto +69 -> 243
    //   177: astore_0
    //   178: aconst_null
    //   179: astore 6
    //   181: aload 6
    //   183: astore 7
    //   185: aload 6
    //   187: ifnull +11 -> 198
    //   190: aload 6
    //   192: invokevirtual 122	java/io/BufferedReader:close	()V
    //   195: goto +3 -> 198
    //   198: aload 7
    //   200: ifnull +8 -> 208
    //   203: aload 7
    //   205: invokevirtual 123	java/io/FileReader:close	()V
    //   208: aload_0
    //   209: athrow
    //   210: aconst_null
    //   211: astore_0
    //   212: aload_0
    //   213: astore 6
    //   215: aload 6
    //   217: ifnull +11 -> 228
    //   220: aload 6
    //   222: invokevirtual 122	java/io/BufferedReader:close	()V
    //   225: goto +3 -> 228
    //   228: aload_0
    //   229: ifnull +37 -> 266
    //   232: lload 4
    //   234: lstore_2
    //   235: goto -107 -> 128
    //   238: aconst_null
    //   239: astore_0
    //   240: aload_0
    //   241: astore 6
    //   243: aload 6
    //   245: ifnull +11 -> 256
    //   248: aload 6
    //   250: invokevirtual 122	java/io/BufferedReader:close	()V
    //   253: goto +3 -> 256
    //   256: aload_0
    //   257: ifnull +9 -> 266
    //   260: lload 4
    //   262: lstore_2
    //   263: goto -135 -> 128
    //   266: lconst_0
    //   267: lreturn
    //   268: astore_0
    //   269: goto -31 -> 238
    //   272: astore_0
    //   273: goto -63 -> 210
    //   276: astore 6
    //   278: goto -107 -> 171
    //   281: astore 6
    //   283: goto -118 -> 165
    //   286: astore 7
    //   288: goto -140 -> 148
    //   291: astore 7
    //   293: goto -148 -> 145
    //   296: astore 6
    //   298: goto -170 -> 128
    //   301: astore_0
    //   302: lload_2
    //   303: lreturn
    //   304: astore 6
    //   306: goto -108 -> 198
    //   309: astore 6
    //   311: goto -103 -> 208
    //   314: astore 6
    //   316: goto -88 -> 228
    //   319: astore 6
    //   321: goto -65 -> 256
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	324	0	paramContext	Context
    //   3	8	1	i	int
    //   95	208	2	l1	long
    //   5	256	4	l2	long
    //   30	219	6	localObject1	Object
    //   276	1	6	localFileNotFoundException1	java.io.FileNotFoundException
    //   281	1	6	localIOException1	java.io.IOException
    //   296	1	6	localIOException2	java.io.IOException
    //   304	1	6	localIOException3	java.io.IOException
    //   309	1	6	localIOException4	java.io.IOException
    //   314	1	6	localIOException5	java.io.IOException
    //   319	1	6	localIOException6	java.io.IOException
    //   76	128	7	localObject2	Object
    //   286	1	7	localFileNotFoundException2	java.io.FileNotFoundException
    //   291	1	7	localIOException7	java.io.IOException
    //   134	6	8	localObject3	Object
    //   151	9	8	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   71	78	134	finally
    //   83	93	134	finally
    //   101	117	134	finally
    //   58	71	151	finally
    //   48	58	177	finally
    //   48	58	268	java/io/FileNotFoundException
    //   48	58	272	java/io/IOException
    //   58	71	276	java/io/FileNotFoundException
    //   58	71	281	java/io/IOException
    //   71	78	286	java/io/FileNotFoundException
    //   83	93	286	java/io/FileNotFoundException
    //   101	117	286	java/io/FileNotFoundException
    //   71	78	291	java/io/IOException
    //   83	93	291	java/io/IOException
    //   101	117	291	java/io/IOException
    //   123	128	296	java/io/IOException
    //   128	132	301	java/io/IOException
    //   190	195	304	java/io/IOException
    //   203	208	309	java/io/IOException
    //   220	225	314	java/io/IOException
    //   248	253	319	java/io/IOException
  }
  
  private static long a(char[] paramArrayOfChar)
  {
    Object localObject = new StringBuilder();
    int j = paramArrayOfChar.length;
    int i = 0;
    while (i < j)
    {
      ((StringBuilder)localObject).append(paramArrayOfChar[i]);
      i += 1;
    }
    paramArrayOfChar = a(((StringBuilder)localObject).toString());
    localObject = new CRC32();
    ((CRC32)localObject).update(paramArrayOfChar);
    return ((CRC32)localObject).getValue();
  }
  
  private static Pair<Integer, Integer> a(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramContext = (WindowManager)paramContext.getSystemService("window");
    if (paramContext != null) {
      if (Build.VERSION.SDK_INT >= 17) {
        paramContext.getDefaultDisplay().getRealMetrics(localDisplayMetrics);
      } else {
        paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      }
    }
    return new Pair(Integer.valueOf(Math.min(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels)), Integer.valueOf(Math.max(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels)));
  }
  
  private static String a()
  {
    return Build.VERSION.RELEASE;
  }
  
  private static String a(Context paramContext)
  {
    return paramContext.getApplicationContext().getResources().getConfiguration().locale.getCountry();
  }
  
  private static String a(String paramString)
  {
    return new String(Base64.decode(paramString, 2));
  }
  
  private static String a(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    while (i < 32)
    {
      if (i < 16) {
        localStringBuilder.append(paramArrayOfChar1[i]);
      } else {
        localStringBuilder.append(paramArrayOfChar2[(i - 16)]);
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  private static List<Long> a(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    paramContext = paramContext.getPackageManager();
    String[] arrayOfString = a;
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      String str = arrayOfString[i];
      try
      {
        localArrayList.add(Long.valueOf(paramContext.getPackageInfo(a(str), 0).firstInstallTime));
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
      localArrayList.add(Long.valueOf(-1L));
      i += 1;
    }
    return localArrayList;
  }
  
  private static void a(char[] paramArrayOfChar)
  {
    String str = Integer.toHexString(1).toUpperCase();
    if (str.length() < 2)
    {
      paramArrayOfChar[0] = '0';
      paramArrayOfChar[1] = str.charAt(0);
      return;
    }
    paramArrayOfChar[0] = str.charAt(0);
    paramArrayOfChar[1] = str.charAt(1);
  }
  
  private static void a(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    char[] arrayOfChar = new char[30];
    int i = 0;
    while (i < 30)
    {
      if (i < 16) {
        arrayOfChar[i] = paramArrayOfChar1[i];
      } else {
        arrayOfChar[i] = paramArrayOfChar2[(i - 16)];
      }
      i += 1;
    }
    paramArrayOfChar1 = Integer.toHexString((int)(a(arrayOfChar) % 256L)).toUpperCase();
    if (paramArrayOfChar1.length() < 2)
    {
      paramArrayOfChar2[14] = '0';
      paramArrayOfChar2[15] = paramArrayOfChar1.charAt(0);
      return;
    }
    paramArrayOfChar2[14] = paramArrayOfChar1.charAt(0);
    paramArrayOfChar2[15] = paramArrayOfChar1.charAt(1);
  }
  
  private static byte[] a(String paramString)
  {
    int j = paramString.length();
    byte[] arrayOfByte = new byte[j / 2];
    int i = 0;
    while (i < j)
    {
      arrayOfByte[(i / 2)] = ((byte)((Character.digit(paramString.charAt(i), 16) << 4) + Character.digit(paramString.charAt(i + 1), 16)));
      i += 2;
    }
    return arrayOfByte;
  }
  
  private static char[] a(String paramString)
  {
    paramString = b(paramString);
    char[] arrayOfChar = new char[16];
    int i = 8;
    while (i < 24)
    {
      arrayOfChar[(i - 8)] = paramString.charAt(i);
      i += 1;
    }
    return arrayOfChar;
  }
  
  private static String b()
  {
    return Locale.getDefault().getLanguage();
  }
  
  private static String b(String paramString)
  {
    try
    {
      Object localObject = MessageDigest.getInstance("MD5");
      ((MessageDigest)localObject).update(paramString.getBytes());
      paramString = ((MessageDigest)localObject).digest();
      localObject = new StringBuilder();
      int j = paramString.length;
      int i = 0;
      while (i < j)
      {
        int k = paramString[i] & 0xFF;
        if (k < 16) {
          ((StringBuilder)localObject).append("0");
        }
        ((StringBuilder)localObject).append(Integer.toHexString(k));
        i += 1;
      }
      paramString = ((StringBuilder)localObject).toString().toUpperCase();
      return paramString;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      for (;;) {}
    }
    return "";
  }
  
  private static String c()
  {
    try
    {
      String str = TimeZone.getDefault().getDisplayName(false, 0);
      return str;
    }
    catch (AssertionError|Exception localAssertionError) {}
    return "";
  }
  
  public static String createQADID(Context paramContext)
  {
    try
    {
      Object localObject2 = paramContext.getApplicationContext();
      String str2 = a();
      Pair localPair = a((Context)localObject2);
      String str3 = d();
      long l1 = a((Context)localObject2);
      long l2 = a();
      paramContext = b();
      localObject1 = a((Context)localObject2);
      String str1 = c();
      localObject2 = a((Context)localObject2);
      str2 = String.format(Locale.US, "%s|%d|%d|%s|%d|%d", new Object[] { str2, localPair.first, localPair.second, str3, Long.valueOf(l1), Long.valueOf(l2) });
      paramContext = String.format(Locale.US, "%s|%s|%s|%s", new Object[] { paramContext, localObject1, str1, TextUtils.join("|", (Iterable)localObject2) });
      localObject1 = a(str2);
      paramContext = a(paramContext);
      a((char[])localObject1);
      a((char[])localObject1, paramContext);
      paramContext = a((char[])localObject1, paramContext);
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("createQADID exception: ");
      ((StringBuilder)localObject1).append(paramContext.getMessage());
      Log.w("AMS-QADID", ((StringBuilder)localObject1).toString());
      return "01000000000000000000000000000000";
    }
    catch (Error paramContext)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("createQADID error: ");
      ((StringBuilder)localObject1).append(paramContext.getMessage());
      Log.w("AMS-QADID", ((StringBuilder)localObject1).toString());
    }
    return "01000000000000000000000000000000";
  }
  
  /* Error */
  private static String d()
  {
    // Byte code:
    //   0: ldc_w 422
    //   3: invokestatic 427	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   6: astore_2
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore 5
    //   13: aconst_null
    //   14: astore_3
    //   15: new 85	java/io/FileReader
    //   18: dup
    //   19: ldc_w 429
    //   22: invokespecial 88	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   25: astore_0
    //   26: new 90	java/io/BufferedReader
    //   29: dup
    //   30: aload_0
    //   31: sipush 8192
    //   34: invokespecial 93	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   37: astore_1
    //   38: aload_1
    //   39: invokevirtual 96	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   42: astore_3
    //   43: aload_3
    //   44: ifnull +5 -> 49
    //   47: aload_3
    //   48: astore_2
    //   49: aload_1
    //   50: invokevirtual 122	java/io/BufferedReader:close	()V
    //   53: aload_0
    //   54: invokevirtual 123	java/io/FileReader:close	()V
    //   57: aload_2
    //   58: areturn
    //   59: astore_3
    //   60: aload_1
    //   61: astore_2
    //   62: aload_3
    //   63: astore_1
    //   64: goto +32 -> 96
    //   67: goto +55 -> 122
    //   70: goto +75 -> 145
    //   73: astore_1
    //   74: aload_3
    //   75: astore_2
    //   76: goto +20 -> 96
    //   79: aload 4
    //   81: astore_1
    //   82: goto +40 -> 122
    //   85: aload 5
    //   87: astore_1
    //   88: goto +57 -> 145
    //   91: astore_1
    //   92: aconst_null
    //   93: astore_0
    //   94: aload_3
    //   95: astore_2
    //   96: aload_2
    //   97: ifnull +10 -> 107
    //   100: aload_2
    //   101: invokevirtual 122	java/io/BufferedReader:close	()V
    //   104: goto +3 -> 107
    //   107: aload_0
    //   108: ifnull +7 -> 115
    //   111: aload_0
    //   112: invokevirtual 123	java/io/FileReader:close	()V
    //   115: aload_1
    //   116: athrow
    //   117: aconst_null
    //   118: astore_0
    //   119: aload 4
    //   121: astore_1
    //   122: aload_1
    //   123: ifnull +10 -> 133
    //   126: aload_1
    //   127: invokevirtual 122	java/io/BufferedReader:close	()V
    //   130: goto +3 -> 133
    //   133: aload_0
    //   134: ifnull +29 -> 163
    //   137: goto -84 -> 53
    //   140: aconst_null
    //   141: astore_0
    //   142: aload 5
    //   144: astore_1
    //   145: aload_1
    //   146: ifnull +10 -> 156
    //   149: aload_1
    //   150: invokevirtual 122	java/io/BufferedReader:close	()V
    //   153: goto +3 -> 156
    //   156: aload_0
    //   157: ifnull +6 -> 163
    //   160: goto -107 -> 53
    //   163: aload_2
    //   164: areturn
    //   165: astore_0
    //   166: goto -26 -> 140
    //   169: astore_0
    //   170: goto -53 -> 117
    //   173: astore_1
    //   174: goto -89 -> 85
    //   177: astore_1
    //   178: goto -99 -> 79
    //   181: astore_3
    //   182: goto -112 -> 70
    //   185: astore_3
    //   186: goto -119 -> 67
    //   189: astore_1
    //   190: goto -137 -> 53
    //   193: astore_0
    //   194: aload_2
    //   195: areturn
    //   196: astore_2
    //   197: goto -90 -> 107
    //   200: astore_0
    //   201: goto -86 -> 115
    //   204: astore_1
    //   205: goto -72 -> 133
    //   208: astore_1
    //   209: goto -53 -> 156
    // Local variable table:
    //   start	length	slot	name	signature
    //   25	132	0	localFileReader	java.io.FileReader
    //   165	1	0	localFileNotFoundException1	java.io.FileNotFoundException
    //   169	1	0	localIOException1	java.io.IOException
    //   193	1	0	localIOException2	java.io.IOException
    //   200	1	0	localIOException3	java.io.IOException
    //   37	27	1	localObject1	Object
    //   73	1	1	localObject2	Object
    //   81	7	1	localObject3	Object
    //   91	25	1	localObject4	Object
    //   121	29	1	localObject5	Object
    //   173	1	1	localFileNotFoundException2	java.io.FileNotFoundException
    //   177	1	1	localIOException4	java.io.IOException
    //   189	1	1	localIOException5	java.io.IOException
    //   204	1	1	localIOException6	java.io.IOException
    //   208	1	1	localIOException7	java.io.IOException
    //   6	189	2	localObject6	Object
    //   196	1	2	localIOException8	java.io.IOException
    //   14	34	3	str	String
    //   59	36	3	localObject7	Object
    //   181	1	3	localFileNotFoundException3	java.io.FileNotFoundException
    //   185	1	3	localIOException9	java.io.IOException
    //   8	112	4	localObject8	Object
    //   11	132	5	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   38	43	59	finally
    //   26	38	73	finally
    //   15	26	91	finally
    //   15	26	165	java/io/FileNotFoundException
    //   15	26	169	java/io/IOException
    //   26	38	173	java/io/FileNotFoundException
    //   26	38	177	java/io/IOException
    //   38	43	181	java/io/FileNotFoundException
    //   38	43	185	java/io/IOException
    //   49	53	189	java/io/IOException
    //   53	57	193	java/io/IOException
    //   100	104	196	java/io/IOException
    //   111	115	200	java/io/IOException
    //   126	130	204	java/io/IOException
    //   149	153	208	java/io/IOException
  }
  
  public static Map<String, Object> getQADIDDebugInfo(Context paramContext)
  {
    HashMap localHashMap1 = new HashMap();
    localHashMap1.put("alv", Integer.valueOf(1));
    localHashMap1.put("ov", a());
    localHashMap1.put("lg", b());
    localHashMap1.put("cc", a(paramContext));
    localHashMap1.put("tz", c());
    localHashMap1.put("sw", a(paramContext).first);
    localHashMap1.put("sh", a(paramContext).second);
    localHashMap1.put("rs", Long.valueOf(a(paramContext)));
    localHashMap1.put("is", Long.valueOf(a()));
    localHashMap1.put("cv", d());
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("fit", a(paramContext));
    localHashMap1.put("al", localHashMap2);
    return localHashMap1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\qq\gdt\action\qadid\QADID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */