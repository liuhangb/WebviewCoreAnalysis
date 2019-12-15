package com.tencent.tbs.common.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.common.utils.DesUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DeviceUtils
{
  private static String mAndroidId;
  public static String mCpu = "";
  public static String mImei = "";
  public static String mImsi = "";
  private static boolean mIsTablet = false;
  public static String mMac = "";
  private static boolean mPadCheckFinished = false;
  private static float sDensity = -1.0F;
  public static boolean sLessHoneycomb = false;
  private static int sSdkVersion = -1;
  private static int totalRAM;
  
  static
  {
    boolean bool;
    if (getSdkVersion() < 11) {
      bool = true;
    } else {
      bool = false;
    }
    sLessHoneycomb = bool;
    mAndroidId = "";
    totalRAM = 0;
  }
  
  public static String getAndroidId()
  {
    Context localContext = TbsBaseModuleShell.getCallerContext();
    if (localContext == null) {
      return "";
    }
    if (TextUtils.isEmpty(mAndroidId)) {
      try
      {
        mAndroidId = Settings.Secure.getString(localContext.getContentResolver(), "android_id");
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    return mAndroidId;
  }
  
  public static String getAndroidOsSystemProperties(String paramString)
  {
    try
    {
      paramString = (String)Class.forName("android.os.SystemProperties").getMethod("get", new Class[] { String.class }).invoke(null, new Object[] { paramString });
      if (paramString != null) {
        return paramString;
      }
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  /* Error */
  public static int getAvailRAM()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: iconst_0
    //   3: istore_2
    //   4: new 108	java/io/FileReader
    //   7: dup
    //   8: ldc 110
    //   10: invokespecial 113	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   13: astore_3
    //   14: new 115	java/io/BufferedReader
    //   17: dup
    //   18: aload_3
    //   19: sipush 8192
    //   22: invokespecial 118	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   25: astore 6
    //   27: aload 6
    //   29: astore 4
    //   31: aload_3
    //   32: astore 5
    //   34: aload 6
    //   36: invokevirtual 121	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   39: astore 7
    //   41: iload_2
    //   42: istore_0
    //   43: aload 7
    //   45: ifnull +118 -> 163
    //   48: aload 6
    //   50: astore 4
    //   52: aload_3
    //   53: astore 5
    //   55: aload 7
    //   57: ldc 123
    //   59: invokevirtual 127	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   62: istore_0
    //   63: iconst_m1
    //   64: iload_0
    //   65: if_icmpeq -38 -> 27
    //   68: aload 6
    //   70: astore 4
    //   72: aload_3
    //   73: astore 5
    //   75: aload 7
    //   77: iload_0
    //   78: bipush 8
    //   80: iadd
    //   81: invokevirtual 131	java/lang/String:substring	(I)Ljava/lang/String;
    //   84: invokevirtual 134	java/lang/String:trim	()Ljava/lang/String;
    //   87: astore 7
    //   89: iload_2
    //   90: istore_0
    //   91: aload 7
    //   93: ifnull +70 -> 163
    //   96: iload_2
    //   97: istore_0
    //   98: aload 6
    //   100: astore 4
    //   102: aload_3
    //   103: astore 5
    //   105: aload 7
    //   107: invokevirtual 137	java/lang/String:length	()I
    //   110: ifeq +53 -> 163
    //   113: iload_2
    //   114: istore_0
    //   115: aload 6
    //   117: astore 4
    //   119: aload_3
    //   120: astore 5
    //   122: aload 7
    //   124: ldc -117
    //   126: invokevirtual 142	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   129: ifeq +34 -> 163
    //   132: aload 6
    //   134: astore 4
    //   136: aload_3
    //   137: astore 5
    //   139: aload 7
    //   141: iconst_0
    //   142: aload 7
    //   144: ldc -117
    //   146: invokevirtual 127	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   149: invokevirtual 145	java/lang/String:substring	(II)Ljava/lang/String;
    //   152: invokevirtual 134	java/lang/String:trim	()Ljava/lang/String;
    //   155: invokestatic 150	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   158: sipush 1024
    //   161: idiv
    //   162: istore_0
    //   163: aload_3
    //   164: invokevirtual 153	java/io/FileReader:close	()V
    //   167: goto +8 -> 175
    //   170: astore_3
    //   171: aload_3
    //   172: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   175: iload_0
    //   176: istore_1
    //   177: aload 6
    //   179: invokevirtual 155	java/io/BufferedReader:close	()V
    //   182: iload_0
    //   183: ireturn
    //   184: astore_3
    //   185: aload_3
    //   186: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   189: iload_1
    //   190: ireturn
    //   191: astore 7
    //   193: goto +49 -> 242
    //   196: astore 7
    //   198: goto +91 -> 289
    //   201: astore 5
    //   203: aconst_null
    //   204: astore 4
    //   206: goto +132 -> 338
    //   209: astore 7
    //   211: aconst_null
    //   212: astore 6
    //   214: goto +28 -> 242
    //   217: astore 7
    //   219: aconst_null
    //   220: astore 6
    //   222: goto +67 -> 289
    //   225: astore 5
    //   227: aconst_null
    //   228: astore_3
    //   229: aload_3
    //   230: astore 4
    //   232: goto +106 -> 338
    //   235: astore 7
    //   237: aconst_null
    //   238: astore_3
    //   239: aload_3
    //   240: astore 6
    //   242: aload 6
    //   244: astore 4
    //   246: aload_3
    //   247: astore 5
    //   249: aload 7
    //   251: invokevirtual 76	java/lang/Throwable:printStackTrace	()V
    //   254: aload_3
    //   255: ifnull +15 -> 270
    //   258: aload_3
    //   259: invokevirtual 153	java/io/FileReader:close	()V
    //   262: goto +8 -> 270
    //   265: astore_3
    //   266: aload_3
    //   267: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   270: aload 6
    //   272: ifnull +55 -> 327
    //   275: aload 6
    //   277: invokevirtual 155	java/io/BufferedReader:close	()V
    //   280: iconst_0
    //   281: ireturn
    //   282: astore 7
    //   284: aconst_null
    //   285: astore_3
    //   286: aload_3
    //   287: astore 6
    //   289: aload 6
    //   291: astore 4
    //   293: aload_3
    //   294: astore 5
    //   296: aload 7
    //   298: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   301: aload_3
    //   302: ifnull +15 -> 317
    //   305: aload_3
    //   306: invokevirtual 153	java/io/FileReader:close	()V
    //   309: goto +8 -> 317
    //   312: astore_3
    //   313: aload_3
    //   314: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   317: aload 6
    //   319: ifnull +8 -> 327
    //   322: aload 6
    //   324: invokevirtual 155	java/io/BufferedReader:close	()V
    //   327: iconst_0
    //   328: ireturn
    //   329: astore 6
    //   331: aload 5
    //   333: astore_3
    //   334: aload 6
    //   336: astore 5
    //   338: aload_3
    //   339: ifnull +15 -> 354
    //   342: aload_3
    //   343: invokevirtual 153	java/io/FileReader:close	()V
    //   346: goto +8 -> 354
    //   349: astore_3
    //   350: aload_3
    //   351: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   354: aload 4
    //   356: ifnull +16 -> 372
    //   359: aload 4
    //   361: invokevirtual 155	java/io/BufferedReader:close	()V
    //   364: goto +8 -> 372
    //   367: astore_3
    //   368: aload_3
    //   369: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   372: aload 5
    //   374: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   42	141	0	i	int
    //   1	189	1	j	int
    //   3	111	2	k	int
    //   13	151	3	localFileReader1	java.io.FileReader
    //   170	2	3	localIOException1	java.io.IOException
    //   184	2	3	localIOException2	java.io.IOException
    //   228	31	3	localObject1	Object
    //   265	2	3	localIOException3	java.io.IOException
    //   285	21	3	localObject2	Object
    //   312	2	3	localIOException4	java.io.IOException
    //   333	10	3	localObject3	Object
    //   349	2	3	localIOException5	java.io.IOException
    //   367	2	3	localIOException6	java.io.IOException
    //   29	331	4	localObject4	Object
    //   32	106	5	localFileReader2	java.io.FileReader
    //   201	1	5	localObject5	Object
    //   225	1	5	localObject6	Object
    //   247	126	5	localObject7	Object
    //   25	298	6	localObject8	Object
    //   329	6	6	localObject9	Object
    //   39	104	7	str	String
    //   191	1	7	localThrowable1	Throwable
    //   196	1	7	localIOException7	java.io.IOException
    //   209	1	7	localThrowable2	Throwable
    //   217	1	7	localIOException8	java.io.IOException
    //   235	15	7	localThrowable3	Throwable
    //   282	15	7	localIOException9	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   163	167	170	java/io/IOException
    //   177	182	184	java/io/IOException
    //   275	280	184	java/io/IOException
    //   322	327	184	java/io/IOException
    //   34	41	191	java/lang/Throwable
    //   55	63	191	java/lang/Throwable
    //   75	89	191	java/lang/Throwable
    //   105	113	191	java/lang/Throwable
    //   122	132	191	java/lang/Throwable
    //   139	163	191	java/lang/Throwable
    //   34	41	196	java/io/IOException
    //   55	63	196	java/io/IOException
    //   75	89	196	java/io/IOException
    //   105	113	196	java/io/IOException
    //   122	132	196	java/io/IOException
    //   139	163	196	java/io/IOException
    //   14	27	201	finally
    //   14	27	209	java/lang/Throwable
    //   14	27	217	java/io/IOException
    //   4	14	225	finally
    //   4	14	235	java/lang/Throwable
    //   258	262	265	java/io/IOException
    //   4	14	282	java/io/IOException
    //   305	309	312	java/io/IOException
    //   34	41	329	finally
    //   55	63	329	finally
    //   75	89	329	finally
    //   105	113	329	finally
    //   122	132	329	finally
    //   139	163	329	finally
    //   249	254	329	finally
    //   296	301	329	finally
    //   342	346	349	java/io/IOException
    //   359	364	367	java/io/IOException
  }
  
  public static float getDensity(Context paramContext)
  {
    if (sDensity < 0.0F)
    {
      paramContext = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sDensity = localDisplayMetrics.density;
    }
    return sDensity;
  }
  
  public static int getDensityDpi(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.densityDpi;
  }
  
  public static String getDeviceBrand()
  {
    String str = Build.BRAND;
    if (str == null) {
      return "";
    }
    return str.toLowerCase();
  }
  
  /* Error */
  public static String getDeviceCpuabi()
  {
    // Byte code:
    //   0: getstatic 198	com/tencent/tbs/common/utils/DeviceUtils:mCpu	Ljava/lang/String;
    //   3: invokestatic 59	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   6: ifne +7 -> 13
    //   9: getstatic 198	com/tencent/tbs/common/utils/DeviceUtils:mCpu	Ljava/lang/String;
    //   12: areturn
    //   13: aconst_null
    //   14: astore 5
    //   16: aconst_null
    //   17: astore_3
    //   18: aconst_null
    //   19: astore_2
    //   20: aconst_null
    //   21: astore_1
    //   22: aconst_null
    //   23: astore_0
    //   24: getstatic 203	android/os/Build$VERSION:SDK_INT	I
    //   27: bipush 19
    //   29: if_icmpne +41 -> 70
    //   32: getstatic 203	android/os/Build$VERSION:SDK_INT	I
    //   35: bipush 21
    //   37: if_icmpge +17 -> 54
    //   40: getstatic 206	android/os/Build:CPU_ABI	Ljava/lang/String;
    //   43: astore 6
    //   45: aconst_null
    //   46: astore_1
    //   47: aload_0
    //   48: astore_3
    //   49: aload_1
    //   50: astore_0
    //   51: goto +60 -> 111
    //   54: getstatic 210	android/os/Build:SUPPORTED_ABIS	[Ljava/lang/String;
    //   57: iconst_0
    //   58: aaload
    //   59: astore 6
    //   61: aconst_null
    //   62: astore_1
    //   63: aload_0
    //   64: astore_3
    //   65: aload_1
    //   66: astore_0
    //   67: goto +44 -> 111
    //   70: new 212	java/io/InputStreamReader
    //   73: dup
    //   74: invokestatic 218	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   77: ldc -36
    //   79: invokevirtual 224	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   82: invokevirtual 230	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   85: invokespecial 233	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   88: astore_0
    //   89: aload_0
    //   90: astore 4
    //   92: aload_2
    //   93: astore_1
    //   94: aload_0
    //   95: astore_2
    //   96: new 115	java/io/BufferedReader
    //   99: dup
    //   100: aload_0
    //   101: invokespecial 236	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   104: astore_3
    //   105: aload_3
    //   106: invokevirtual 121	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   109: astore 6
    //   111: aload_3
    //   112: astore 5
    //   114: aload_0
    //   115: astore 4
    //   117: aload_3
    //   118: astore_1
    //   119: aload_0
    //   120: astore_2
    //   121: aload 6
    //   123: ldc -18
    //   125: invokevirtual 142	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   128: ifeq +9 -> 137
    //   131: ldc -16
    //   133: astore_1
    //   134: goto +31 -> 165
    //   137: aload_3
    //   138: astore 5
    //   140: aload_0
    //   141: astore 4
    //   143: aload_3
    //   144: astore_1
    //   145: aload_0
    //   146: astore_2
    //   147: ldc -14
    //   149: invokestatic 247	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   152: astore 6
    //   154: aload 6
    //   156: astore_1
    //   157: aload 6
    //   159: ifnonnull +6 -> 165
    //   162: ldc 9
    //   164: astore_1
    //   165: aload_3
    //   166: ifnull +7 -> 173
    //   169: aload_3
    //   170: invokevirtual 155	java/io/BufferedReader:close	()V
    //   173: aload_1
    //   174: astore_2
    //   175: aload_0
    //   176: ifnull +95 -> 271
    //   179: aload_0
    //   180: invokevirtual 248	java/io/InputStreamReader:close	()V
    //   183: aload_1
    //   184: areturn
    //   185: astore_0
    //   186: aload 5
    //   188: astore_3
    //   189: aload_0
    //   190: astore 5
    //   192: aload 4
    //   194: astore_0
    //   195: goto +24 -> 219
    //   198: astore_2
    //   199: aload_3
    //   200: astore_1
    //   201: goto +77 -> 278
    //   204: astore 5
    //   206: goto +13 -> 219
    //   209: astore_2
    //   210: aconst_null
    //   211: astore_0
    //   212: goto +66 -> 278
    //   215: astore 5
    //   217: aconst_null
    //   218: astore_0
    //   219: aload_3
    //   220: astore_1
    //   221: aload_0
    //   222: astore_2
    //   223: ldc -14
    //   225: invokestatic 247	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   228: astore 6
    //   230: aload 6
    //   232: astore 4
    //   234: aload 6
    //   236: ifnonnull +7 -> 243
    //   239: ldc 9
    //   241: astore 4
    //   243: aload_3
    //   244: astore_1
    //   245: aload_0
    //   246: astore_2
    //   247: aload 5
    //   249: invokevirtual 76	java/lang/Throwable:printStackTrace	()V
    //   252: aload_3
    //   253: ifnull +7 -> 260
    //   256: aload_3
    //   257: invokevirtual 155	java/io/BufferedReader:close	()V
    //   260: aload_0
    //   261: ifnull +7 -> 268
    //   264: aload_0
    //   265: invokevirtual 248	java/io/InputStreamReader:close	()V
    //   268: aload 4
    //   270: astore_2
    //   271: aload_2
    //   272: areturn
    //   273: astore_3
    //   274: aload_2
    //   275: astore_0
    //   276: aload_3
    //   277: astore_2
    //   278: aload_1
    //   279: ifnull +7 -> 286
    //   282: aload_1
    //   283: invokevirtual 155	java/io/BufferedReader:close	()V
    //   286: aload_0
    //   287: ifnull +7 -> 294
    //   290: aload_0
    //   291: invokevirtual 248	java/io/InputStreamReader:close	()V
    //   294: aload_2
    //   295: athrow
    //   296: astore_2
    //   297: goto -124 -> 173
    //   300: astore_0
    //   301: aload_1
    //   302: areturn
    //   303: astore_1
    //   304: goto -44 -> 260
    //   307: astore_0
    //   308: goto -40 -> 268
    //   311: astore_1
    //   312: goto -26 -> 286
    //   315: astore_0
    //   316: goto -22 -> 294
    // Local variable table:
    //   start	length	slot	name	signature
    //   23	157	0	localObject1	Object
    //   185	5	0	localThrowable1	Throwable
    //   194	97	0	localObject2	Object
    //   300	1	0	localIOException1	java.io.IOException
    //   307	1	0	localIOException2	java.io.IOException
    //   315	1	0	localIOException3	java.io.IOException
    //   21	281	1	localObject3	Object
    //   303	1	1	localIOException4	java.io.IOException
    //   311	1	1	localIOException5	java.io.IOException
    //   19	156	2	localObject4	Object
    //   198	1	2	localObject5	Object
    //   209	1	2	localObject6	Object
    //   222	73	2	localObject7	Object
    //   296	1	2	localIOException6	java.io.IOException
    //   17	240	3	localObject8	Object
    //   273	4	3	localObject9	Object
    //   90	179	4	localObject10	Object
    //   14	177	5	localObject11	Object
    //   204	1	5	localThrowable2	Throwable
    //   215	33	5	localThrowable3	Throwable
    //   43	192	6	str	String
    // Exception table:
    //   from	to	target	type
    //   96	105	185	java/lang/Throwable
    //   121	131	185	java/lang/Throwable
    //   147	154	185	java/lang/Throwable
    //   105	111	198	finally
    //   105	111	204	java/lang/Throwable
    //   24	45	209	finally
    //   54	61	209	finally
    //   70	89	209	finally
    //   24	45	215	java/lang/Throwable
    //   54	61	215	java/lang/Throwable
    //   70	89	215	java/lang/Throwable
    //   96	105	273	finally
    //   121	131	273	finally
    //   147	154	273	finally
    //   223	230	273	finally
    //   247	252	273	finally
    //   169	173	296	java/io/IOException
    //   179	183	300	java/io/IOException
    //   256	260	303	java/io/IOException
    //   264	268	307	java/io/IOException
    //   282	286	311	java/io/IOException
    //   290	294	315	java/io/IOException
  }
  
  public static String getDeviceManufacturer()
  {
    String str = Build.MANUFACTURER;
    if (str == null) {
      return "";
    }
    return str.toLowerCase();
  }
  
  public static String getDeviceName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" ");
    localStringBuilder.append(Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", ""));
    localStringBuilder.append(" ");
    return localStringBuilder.toString();
  }
  
  public static int getHeight(Context paramContext)
  {
    try
    {
      int i = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getHeight();
      return i;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return 0;
  }
  
  public static String getIMEI(Context paramContext)
  {
    if (!TextUtils.isEmpty(mImei)) {
      return mImei;
    }
    try
    {
      paramContext = getTelephonyManager(paramContext);
      if (paramContext == null) {
        return "";
      }
      paramContext = paramContext.getDeviceId();
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static String getIMSI(Context paramContext)
  {
    if (!TextUtils.isEmpty(mImsi)) {
      return mImsi;
    }
    try
    {
      paramContext = getTelephonyManager(paramContext);
      if (paramContext == null) {
        return "";
      }
      paramContext = paramContext.getSubscriberId();
      return paramContext;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static byte[] getMacAddress()
  {
    return getMacAddress(TbsBaseModuleShell.getContext());
  }
  
  public static byte[] getMacAddress(Context paramContext)
  {
    paramContext = getMacAddressString(paramContext);
    if (!TextUtils.isEmpty(paramContext)) {
      return DesUtils.DesEncrypt(DesUtils.MAC_KEY, paramContext.getBytes(), 1);
    }
    return null;
  }
  
  public static String getMacAddressByNetworkInterfaceName()
  {
    try
    {
      Object localObject1 = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (NetworkInterface)((Iterator)localObject1).next();
        if (((NetworkInterface)localObject2).getName().equalsIgnoreCase("wlan0"))
        {
          localObject1 = ((NetworkInterface)localObject2).getHardwareAddress();
          if (localObject1 == null) {
            return "";
          }
          localObject2 = new StringBuilder();
          int j = localObject1.length;
          int i = 0;
          while (i < j)
          {
            ((StringBuilder)localObject2).append(String.format("%02X:", new Object[] { Byte.valueOf(localObject1[i]) }));
            i += 1;
          }
          if (((StringBuilder)localObject2).length() > 0) {
            ((StringBuilder)localObject2).deleteCharAt(((StringBuilder)localObject2).length() - 1);
          }
          localObject1 = ((StringBuilder)localObject2).toString();
          return (String)localObject1;
        }
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static String getMacAddressString(Context paramContext)
  {
    if (TextUtils.isEmpty(mMac)) {
      if (Build.VERSION.SDK_INT < 23) {
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
        }
      }
    }
    for (paramContext = paramContext.getMacAddress();; paramContext = "")
    {
      mMac = paramContext;
      break label74;
      mMac = getMacAddressByNetworkInterfaceName();
      label74:
      return mMac;
      if (paramContext != null) {
        break;
      }
    }
  }
  
  public static String getNewBeeROMName()
  {
    return getAndroidOsSystemProperties("ro.build.version.newbee.display");
  }
  
  public static String getQIMEI()
  {
    Object localObject = TbsBaseModuleShell.getCallerContext();
    String str = "";
    if (localObject != null) {
      str = X5CoreBeaconUploader.getQIMEI();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getQIME, return ");
    ((StringBuilder)localObject).append(str);
    LogUtils.d("QIMEI", ((StringBuilder)localObject).toString());
    localObject = str;
    if (TextUtils.isEmpty(str)) {
      localObject = "";
    }
    return (String)localObject;
  }
  
  public static int getSdkVersion()
  {
    if (-1 == sSdkVersion) {
      sSdkVersion = Integer.parseInt(Build.VERSION.SDK);
    }
    return sSdkVersion;
  }
  
  public static String getSysVersion()
  {
    try
    {
      String str = Build.DISPLAY.replaceAll("[&=]", ".");
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return Build.DISPLAY;
  }
  
  public static TelephonyManager getTelephonyManager()
  {
    return getTelephonyManager(TbsBaseModuleShell.getContext());
  }
  
  public static TelephonyManager getTelephonyManager(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        paramContext = (TelephonyManager)paramContext.getSystemService("phone");
        return paramContext;
      }
      catch (SecurityException paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  /* Error */
  public static int getTotalRAM()
  {
    // Byte code:
    //   0: getstatic 36	com/tencent/tbs/common/utils/DeviceUtils:totalRAM	I
    //   3: istore_0
    //   4: iload_0
    //   5: ifle +5 -> 10
    //   8: iload_0
    //   9: ireturn
    //   10: iconst_0
    //   11: istore_1
    //   12: iconst_0
    //   13: istore_2
    //   14: iconst_0
    //   15: istore_3
    //   16: new 108	java/io/FileReader
    //   19: dup
    //   20: ldc 110
    //   22: invokespecial 113	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   25: astore 4
    //   27: new 115	java/io/BufferedReader
    //   30: dup
    //   31: aload 4
    //   33: sipush 8192
    //   36: invokespecial 118	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   39: astore 7
    //   41: aload 7
    //   43: astore 5
    //   45: aload 4
    //   47: astore 6
    //   49: aload 7
    //   51: invokevirtual 121	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   54: astore 8
    //   56: iload_3
    //   57: istore_0
    //   58: aload 8
    //   60: ifnull +124 -> 184
    //   63: aload 7
    //   65: astore 5
    //   67: aload 4
    //   69: astore 6
    //   71: aload 8
    //   73: ldc_w 439
    //   76: invokevirtual 127	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   79: istore_0
    //   80: iconst_m1
    //   81: iload_0
    //   82: if_icmpeq -41 -> 41
    //   85: aload 7
    //   87: astore 5
    //   89: aload 4
    //   91: astore 6
    //   93: aload 8
    //   95: iload_0
    //   96: bipush 9
    //   98: iadd
    //   99: invokevirtual 131	java/lang/String:substring	(I)Ljava/lang/String;
    //   102: invokevirtual 134	java/lang/String:trim	()Ljava/lang/String;
    //   105: astore 8
    //   107: iload_3
    //   108: istore_0
    //   109: aload 8
    //   111: ifnull +73 -> 184
    //   114: iload_3
    //   115: istore_0
    //   116: aload 7
    //   118: astore 5
    //   120: aload 4
    //   122: astore 6
    //   124: aload 8
    //   126: invokevirtual 137	java/lang/String:length	()I
    //   129: ifeq +55 -> 184
    //   132: iload_3
    //   133: istore_0
    //   134: aload 7
    //   136: astore 5
    //   138: aload 4
    //   140: astore 6
    //   142: aload 8
    //   144: ldc -117
    //   146: invokevirtual 142	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   149: ifeq +35 -> 184
    //   152: aload 7
    //   154: astore 5
    //   156: aload 4
    //   158: astore 6
    //   160: aload 8
    //   162: iconst_0
    //   163: aload 8
    //   165: ldc -117
    //   167: invokevirtual 127	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   170: invokevirtual 145	java/lang/String:substring	(II)Ljava/lang/String;
    //   173: invokevirtual 134	java/lang/String:trim	()Ljava/lang/String;
    //   176: invokestatic 150	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   179: sipush 1024
    //   182: idiv
    //   183: istore_0
    //   184: aload 4
    //   186: invokevirtual 153	java/io/FileReader:close	()V
    //   189: goto +10 -> 199
    //   192: astore 4
    //   194: aload 4
    //   196: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   199: iload_0
    //   200: istore_1
    //   201: aload 7
    //   203: invokevirtual 155	java/io/BufferedReader:close	()V
    //   206: goto +176 -> 382
    //   209: astore 4
    //   211: aload 4
    //   213: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   216: iload_1
    //   217: istore_0
    //   218: goto +164 -> 382
    //   221: astore 8
    //   223: goto +53 -> 276
    //   226: astore 8
    //   228: goto +107 -> 335
    //   231: astore 6
    //   233: aconst_null
    //   234: astore 5
    //   236: goto +164 -> 400
    //   239: astore 8
    //   241: aconst_null
    //   242: astore 7
    //   244: goto +32 -> 276
    //   247: astore 8
    //   249: aconst_null
    //   250: astore 7
    //   252: goto +83 -> 335
    //   255: astore 6
    //   257: aconst_null
    //   258: astore 4
    //   260: aload 4
    //   262: astore 5
    //   264: goto +136 -> 400
    //   267: astore 8
    //   269: aconst_null
    //   270: astore 4
    //   272: aload 4
    //   274: astore 7
    //   276: aload 7
    //   278: astore 5
    //   280: aload 4
    //   282: astore 6
    //   284: aload 8
    //   286: invokevirtual 76	java/lang/Throwable:printStackTrace	()V
    //   289: aload 4
    //   291: ifnull +18 -> 309
    //   294: aload 4
    //   296: invokevirtual 153	java/io/FileReader:close	()V
    //   299: goto +10 -> 309
    //   302: astore 4
    //   304: aload 4
    //   306: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   309: iload_2
    //   310: istore_0
    //   311: aload 7
    //   313: ifnull +69 -> 382
    //   316: aload 7
    //   318: invokevirtual 155	java/io/BufferedReader:close	()V
    //   321: iload_2
    //   322: istore_0
    //   323: goto +59 -> 382
    //   326: astore 8
    //   328: aconst_null
    //   329: astore 4
    //   331: aload 4
    //   333: astore 7
    //   335: aload 7
    //   337: astore 5
    //   339: aload 4
    //   341: astore 6
    //   343: aload 8
    //   345: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   348: aload 4
    //   350: ifnull +18 -> 368
    //   353: aload 4
    //   355: invokevirtual 153	java/io/FileReader:close	()V
    //   358: goto +10 -> 368
    //   361: astore 4
    //   363: aload 4
    //   365: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   368: iload_2
    //   369: istore_0
    //   370: aload 7
    //   372: ifnull +10 -> 382
    //   375: aload 7
    //   377: invokevirtual 155	java/io/BufferedReader:close	()V
    //   380: iload_2
    //   381: istore_0
    //   382: iload_0
    //   383: putstatic 36	com/tencent/tbs/common/utils/DeviceUtils:totalRAM	I
    //   386: getstatic 36	com/tencent/tbs/common/utils/DeviceUtils:totalRAM	I
    //   389: ireturn
    //   390: astore 7
    //   392: aload 6
    //   394: astore 4
    //   396: aload 7
    //   398: astore 6
    //   400: aload 4
    //   402: ifnull +18 -> 420
    //   405: aload 4
    //   407: invokevirtual 153	java/io/FileReader:close	()V
    //   410: goto +10 -> 420
    //   413: astore 4
    //   415: aload 4
    //   417: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   420: aload 5
    //   422: ifnull +18 -> 440
    //   425: aload 5
    //   427: invokevirtual 155	java/io/BufferedReader:close	()V
    //   430: goto +10 -> 440
    //   433: astore 4
    //   435: aload 4
    //   437: invokevirtual 154	java/io/IOException:printStackTrace	()V
    //   440: aload 6
    //   442: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   3	380	0	i	int
    //   11	206	1	j	int
    //   13	368	2	k	int
    //   15	118	3	m	int
    //   25	160	4	localFileReader1	java.io.FileReader
    //   192	3	4	localIOException1	java.io.IOException
    //   209	3	4	localIOException2	java.io.IOException
    //   258	37	4	localObject1	Object
    //   302	3	4	localIOException3	java.io.IOException
    //   329	25	4	localObject2	Object
    //   361	3	4	localIOException4	java.io.IOException
    //   394	12	4	localObject3	Object
    //   413	3	4	localIOException5	java.io.IOException
    //   433	3	4	localIOException6	java.io.IOException
    //   43	383	5	localObject4	Object
    //   47	112	6	localFileReader2	java.io.FileReader
    //   231	1	6	localObject5	Object
    //   255	1	6	localObject6	Object
    //   282	159	6	localObject7	Object
    //   39	337	7	localObject8	Object
    //   390	7	7	localObject9	Object
    //   54	110	8	str	String
    //   221	1	8	localThrowable1	Throwable
    //   226	1	8	localIOException7	java.io.IOException
    //   239	1	8	localThrowable2	Throwable
    //   247	1	8	localIOException8	java.io.IOException
    //   267	18	8	localThrowable3	Throwable
    //   326	18	8	localIOException9	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   184	189	192	java/io/IOException
    //   201	206	209	java/io/IOException
    //   316	321	209	java/io/IOException
    //   375	380	209	java/io/IOException
    //   49	56	221	java/lang/Throwable
    //   71	80	221	java/lang/Throwable
    //   93	107	221	java/lang/Throwable
    //   124	132	221	java/lang/Throwable
    //   142	152	221	java/lang/Throwable
    //   160	184	221	java/lang/Throwable
    //   49	56	226	java/io/IOException
    //   71	80	226	java/io/IOException
    //   93	107	226	java/io/IOException
    //   124	132	226	java/io/IOException
    //   142	152	226	java/io/IOException
    //   160	184	226	java/io/IOException
    //   27	41	231	finally
    //   27	41	239	java/lang/Throwable
    //   27	41	247	java/io/IOException
    //   16	27	255	finally
    //   16	27	267	java/lang/Throwable
    //   294	299	302	java/io/IOException
    //   16	27	326	java/io/IOException
    //   353	358	361	java/io/IOException
    //   49	56	390	finally
    //   71	80	390	finally
    //   93	107	390	finally
    //   124	132	390	finally
    //   142	152	390	finally
    //   160	184	390	finally
    //   284	289	390	finally
    //   343	348	390	finally
    //   405	410	413	java/io/IOException
    //   425	430	433	java/io/IOException
  }
  
  public static int getWidth(Context paramContext)
  {
    try
    {
      int i = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getWidth();
      return i;
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return 0;
  }
  
  public static boolean isRealPad(Context paramContext)
  {
    if (mPadCheckFinished) {
      return mIsTablet;
    }
    try
    {
      if (Math.min(getWidth(paramContext), getHeight(paramContext)) * 160 / getDensityDpi(paramContext) < 700) {
        break label57;
      }
      bool = true;
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        continue;
        boolean bool = false;
      }
    }
    mIsTablet = bool;
    mPadCheckFinished = true;
    return mIsTablet;
  }
  
  public static void putInfo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    mImei = paramString1;
    mImsi = paramString2;
    mCpu = paramString3;
    mMac = paramString4;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\DeviceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */