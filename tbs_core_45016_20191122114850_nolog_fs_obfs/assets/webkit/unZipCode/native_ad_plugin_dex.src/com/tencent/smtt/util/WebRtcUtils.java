package com.tencent.smtt.util;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class WebRtcUtils
{
  private static int jdField_a_of_type_Int;
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static a jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static String jdField_a_of_type_JavaLangString;
  private static HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
  private static boolean jdField_a_of_type_Boolean;
  private static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "*.qcloud.com" };
  private static int jdField_b_of_type_Int;
  private static final Object jdField_b_of_type_JavaLangObject = new Object();
  private static String jdField_b_of_type_JavaLangString;
  private static HashMap<String, String> jdField_b_of_type_JavaUtilHashMap;
  private static boolean jdField_b_of_type_Boolean;
  private static int jdField_c_of_type_Int;
  private static final Object jdField_c_of_type_JavaLangObject = new Object();
  private static boolean jdField_c_of_type_Boolean;
  private static int jdField_d_of_type_Int;
  private static boolean jdField_d_of_type_Boolean;
  private static boolean e;
  private static boolean f;
  private static boolean g;
  
  static
  {
    jdField_a_of_type_Int = 0;
    jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a = a.jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a;
    jdField_b_of_type_Int = 1;
    jdField_c_of_type_Int = -1;
    jdField_a_of_type_JavaLangString = "";
    jdField_a_of_type_AndroidOsHandler = null;
    jdField_a_of_type_Boolean = true;
    jdField_b_of_type_Boolean = false;
    jdField_c_of_type_Boolean = false;
    jdField_d_of_type_Boolean = false;
    jdField_d_of_type_Int = 0;
    e = false;
    jdField_a_of_type_JavaUtilHashMap = new HashMap();
    f = false;
    jdField_b_of_type_JavaUtilHashMap = new HashMap();
    jdField_b_of_type_JavaLangString = null;
    g = false;
  }
  
  public static int a()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      int i = jdField_b_of_type_Int;
      jdField_b_of_type_Int += 1;
      return i;
    }
  }
  
  /* Error */
  private static int a(String paramString)
  {
    // Byte code:
    //   0: new 117	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 120	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_0
    //   9: aload_0
    //   10: invokevirtual 124	java/io/File:exists	()Z
    //   13: ifeq +271 -> 284
    //   16: aload_0
    //   17: invokevirtual 128	java/io/File:length	()J
    //   20: lconst_0
    //   21: lcmp
    //   22: ifeq +262 -> 284
    //   25: aload_0
    //   26: invokevirtual 131	java/io/File:isFile	()Z
    //   29: ifne +5 -> 34
    //   32: iconst_m1
    //   33: ireturn
    //   34: new 133	java/net/URL
    //   37: dup
    //   38: ldc -121
    //   40: invokespecial 136	java/net/URL:<init>	(Ljava/lang/String;)V
    //   43: invokevirtual 140	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   46: checkcast 142	java/net/HttpURLConnection
    //   49: astore_2
    //   50: aload_2
    //   51: iconst_1
    //   52: invokevirtual 146	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   55: aload_2
    //   56: iconst_1
    //   57: invokevirtual 149	java/net/HttpURLConnection:setDoInput	(Z)V
    //   60: aload_2
    //   61: iconst_0
    //   62: invokevirtual 152	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   65: aload_2
    //   66: ldc -102
    //   68: invokevirtual 157	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   71: new 159	java/lang/StringBuilder
    //   74: dup
    //   75: invokespecial 160	java/lang/StringBuilder:<init>	()V
    //   78: astore_3
    //   79: aload_3
    //   80: ldc 73
    //   82: invokevirtual 164	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: pop
    //   86: aload_3
    //   87: aload_0
    //   88: invokevirtual 128	java/io/File:length	()J
    //   91: invokevirtual 167	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   94: pop
    //   95: aload_2
    //   96: ldc -87
    //   98: aload_3
    //   99: invokevirtual 173	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   102: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   105: aload_2
    //   106: ldc -77
    //   108: ldc -75
    //   110: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   113: aload_2
    //   114: ldc -73
    //   116: ldc -71
    //   118: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   121: aload_2
    //   122: ldc -69
    //   124: ldc -67
    //   126: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   129: aload_2
    //   130: ldc -65
    //   132: invokestatic 197	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   135: invokevirtual 200	com/tencent/smtt/webkit/service/SmttServiceProxy:getQUA2	()Ljava/lang/String;
    //   138: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   141: aload_2
    //   142: ldc -54
    //   144: invokestatic 197	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   147: invokevirtual 205	com/tencent/smtt/webkit/service/SmttServiceProxy:getGUID	()Ljava/lang/String;
    //   150: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   153: aload_2
    //   154: ldc -49
    //   156: ldc -47
    //   158: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   161: aload_2
    //   162: ldc -45
    //   164: invokestatic 215	com/tencent/smtt/util/g:b	()Ljava/lang/String;
    //   167: invokevirtual 177	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   170: aload_2
    //   171: invokevirtual 219	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   174: astore_3
    //   175: sipush 10240
    //   178: newarray <illegal type>
    //   180: astore 4
    //   182: new 221	java/io/FileInputStream
    //   185: dup
    //   186: aload_0
    //   187: invokespecial 224	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   190: astore_0
    //   191: aload_0
    //   192: aload 4
    //   194: iconst_0
    //   195: sipush 10240
    //   198: invokevirtual 228	java/io/FileInputStream:read	([BII)I
    //   201: istore_1
    //   202: iload_1
    //   203: iconst_m1
    //   204: if_icmpeq +14 -> 218
    //   207: aload_3
    //   208: aload 4
    //   210: iconst_0
    //   211: iload_1
    //   212: invokevirtual 234	java/io/OutputStream:write	([BII)V
    //   215: goto -24 -> 191
    //   218: aload_0
    //   219: invokevirtual 237	java/io/FileInputStream:close	()V
    //   222: aload_3
    //   223: invokevirtual 238	java/io/OutputStream:close	()V
    //   226: aload_2
    //   227: invokevirtual 241	java/net/HttpURLConnection:getResponseCode	()I
    //   230: istore_1
    //   231: iload_1
    //   232: ireturn
    //   233: astore_3
    //   234: aload_0
    //   235: astore_2
    //   236: aload_3
    //   237: astore_0
    //   238: goto +9 -> 247
    //   241: goto +26 -> 267
    //   244: astore_0
    //   245: aconst_null
    //   246: astore_2
    //   247: aload_2
    //   248: ifnull +15 -> 263
    //   251: aload_2
    //   252: invokevirtual 237	java/io/FileInputStream:close	()V
    //   255: goto +8 -> 263
    //   258: astore_2
    //   259: aload_2
    //   260: invokevirtual 244	java/io/IOException:printStackTrace	()V
    //   263: aload_0
    //   264: athrow
    //   265: aconst_null
    //   266: astore_0
    //   267: aload_0
    //   268: ifnull +14 -> 282
    //   271: aload_0
    //   272: invokevirtual 237	java/io/FileInputStream:close	()V
    //   275: iconst_m1
    //   276: ireturn
    //   277: astore_0
    //   278: aload_0
    //   279: invokevirtual 244	java/io/IOException:printStackTrace	()V
    //   282: iconst_m1
    //   283: ireturn
    //   284: iconst_m1
    //   285: ireturn
    //   286: astore_0
    //   287: iconst_m1
    //   288: ireturn
    //   289: astore_0
    //   290: goto -25 -> 265
    //   293: astore_2
    //   294: goto -53 -> 241
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	297	0	paramString	String
    //   201	31	1	i	int
    //   49	203	2	localObject1	Object
    //   258	2	2	localIOException	java.io.IOException
    //   293	1	2	localException	Exception
    //   78	145	3	localObject2	Object
    //   233	4	3	localObject3	Object
    //   180	29	4	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   191	202	233	finally
    //   207	215	233	finally
    //   218	222	233	finally
    //   182	191	244	finally
    //   251	255	258	java/io/IOException
    //   271	275	277	java/io/IOException
    //   0	32	286	java/lang/Exception
    //   34	182	286	java/lang/Exception
    //   222	231	286	java/lang/Exception
    //   251	255	286	java/lang/Exception
    //   259	263	286	java/lang/Exception
    //   263	265	286	java/lang/Exception
    //   271	275	286	java/lang/Exception
    //   278	282	286	java/lang/Exception
    //   182	191	289	java/lang/Exception
    //   191	202	293	java/lang/Exception
    //   207	215	293	java/lang/Exception
    //   218	222	293	java/lang/Exception
  }
  
  private static Handler a()
  {
    ??? = jdField_a_of_type_AndroidOsHandler;
    if (??? != null) {
      return (Handler)???;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_AndroidOsHandler == null)
      {
        jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), new Handler.Callback()
        {
          public boolean handleMessage(Message paramAnonymousMessage)
          {
            switch (paramAnonymousMessage.what)
            {
            default: 
              return false;
            case 10005: 
              WebRtcUtils.e();
              return true;
            case 10004: 
              WebRtcUtils.a((String)paramAnonymousMessage.obj);
              return true;
            case 10003: 
              WebRtcUtils.a();
              return true;
            case 10002: 
              WebRtcUtils.d();
              return true;
            case 10001: 
              WebRtcUtils.c();
              return true;
            }
            WebRtcUtils.b();
            return true;
          }
        });
        jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(10000);
      }
      return jdField_a_of_type_AndroidOsHandler;
    }
  }
  
  public static String a()
  {
    if (jdField_b_of_type_JavaLangString == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(FileUtils.getSDcardDir());
      localStringBuilder.append(File.separator);
      localStringBuilder.append("Tencent");
      localStringBuilder.append(File.separator);
      localStringBuilder.append("tbs_audio_data");
      jdField_b_of_type_JavaLangString = localStringBuilder.toString();
    }
    return jdField_b_of_type_JavaLangString;
  }
  
  public static void a()
  {
    n();
    Object localObject1 = a();
    Object localObject2 = new File((String)localObject1);
    if (!((File)localObject2).exists()) {
      return;
    }
    Object localObject3 = ((File)localObject2).list();
    if (localObject3 != null)
    {
      if (localObject3.length == 0) {
        return;
      }
      localObject2 = new ArrayList();
      int j = localObject3.length;
      int i = 0;
      while (i < j)
      {
        String str = localObject3[i];
        if ((str.endsWith(".opus")) || (str.endsWith(".pcm")))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append(File.separator);
          localStringBuilder.append(str);
          ((ArrayList)localObject2).add(localStringBuilder.toString());
        }
        i += 1;
      }
      if (((ArrayList)localObject2).size() == 0) {
        return;
      }
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append("AudioData.zip");
      localObject1 = ((StringBuilder)localObject3).toString();
      new MttTimingLog.c((String[])((ArrayList)localObject2).toArray(new String[((ArrayList)localObject2).size()]), (String)localObject1).a();
      localObject1 = jdField_a_of_type_AndroidOsHandler.obtainMessage(10004, localObject1);
      jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject1);
      return;
    }
  }
  
  public static void a(int paramInt)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.a(this.a);
        }
      });
      return;
    }
    if (jdField_c_of_type_Int == paramInt)
    {
      g();
      h();
      jdField_c_of_type_Int = -1;
      jdField_a_of_type_JavaLangString = "";
    }
  }
  
  public static void a(int paramInt, final String paramString)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.a(this.jdField_a_of_type_Int, paramString);
        }
      });
      return;
    }
    if ((!TextUtils.isEmpty(paramString)) && ((paramString.toLowerCase().startsWith("https")) || (paramString.toLowerCase().startsWith("http"))))
    {
      g();
      h();
      jdField_a_of_type_JavaLangString = paramString;
      jdField_c_of_type_Int = paramInt;
      synchronized (jdField_b_of_type_JavaLangObject)
      {
        paramString = j.c(paramString);
        List localList = Arrays.asList(jdField_a_of_type_ArrayOfJavaLangString);
        b localb = new b();
        localb.a(localList);
        boolean bool2 = localb.a(paramString);
        boolean bool1 = bool2;
        if (!bool2) {
          bool1 = SmttServiceProxy.getInstance().isWebRTCOptimizationWhiteList(paramString);
        }
        jdField_b_of_type_Boolean = bool1;
        jdField_c_of_type_Boolean = SmttServiceProxy.getInstance().isWebRTCPluginLoadBlackList(paramString);
        jdField_a_of_type_Boolean = true;
        return;
      }
    }
  }
  
  public static void a(View paramView, final int paramInt)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.a(this.jdField_a_of_type_AndroidViewView, paramInt);
        }
      });
      return;
    }
    if (getDumpAudioDataEnabled()) {
      return;
    }
    switch (paramInt)
    {
    default: 
      return;
    case 25: 
      if (jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a == a.b) {
        jdField_a_of_type_Int = 0;
      }
      jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a = a.b;
      k();
      return;
    }
    if (jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a == a.b)
    {
      jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a = a.c;
      jdField_a_of_type_Int += 1;
    }
    if (jdField_a_of_type_Int == 3)
    {
      j();
      a(true);
      m();
      c(SmttResource.getString("x5_start_record_audio_data", "string"));
      return;
    }
    k();
  }
  
  private static void a(HashMap<String, String> paramHashMap)
  {
    paramHashMap.put("url", jdField_a_of_type_JavaLangString);
    paramHashMap.put("device_module", Build.MODEL);
    paramHashMap.put("device_version", String.valueOf(Build.VERSION.SDK_INT));
    paramHashMap.put("guid", SmttServiceProxy.getInstance().getGUID());
  }
  
  public static void a(boolean paramBoolean)
  {
    synchronized (jdField_c_of_type_JavaLangObject)
    {
      jdField_d_of_type_Boolean = paramBoolean;
      return;
    }
  }
  
  private static boolean a()
  {
    return a().getLooper() != Looper.myLooper();
  }
  
  private static void b(String paramString)
  {
    int i = a(paramString);
    jdField_d_of_type_Int += 1;
    if ((i != 200) && (jdField_d_of_type_Int < 2))
    {
      paramString = jdField_a_of_type_AndroidOsHandler.obtainMessage(10004, paramString);
      jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(paramString, 2000L);
      return;
    }
    if (i == 200)
    {
      ContextHolder.getInstance().getCurrentContext();
      c(SmttResource.getString("x5_audio_data_upload_success", "string"));
      new File(paramString).delete();
      return;
    }
    c(SmttResource.getString("x5_audio_data_upload_failed", "string"));
  }
  
  private static void b(boolean paramBoolean)
  {
    if ((paramBoolean) || (e))
    {
      jdField_a_of_type_JavaUtilHashMap.clear();
      e = false;
    }
  }
  
  private static void c(String paramString)
  {
    if (ContextHolder.getInstance().getCurrentContext() != null) {
      Toast.makeText(ContextHolder.getInstance().getCurrentContext(), paramString, 1).show();
    }
  }
  
  private static void c(boolean paramBoolean)
  {
    if ((paramBoolean) || (f))
    {
      jdField_b_of_type_JavaUtilHashMap.clear();
      f = false;
    }
  }
  
  private static void f()
  {
    b(true);
    c(true);
  }
  
  private static void g()
  {
    if ((e) && (!TextUtils.isEmpty(jdField_a_of_type_JavaLangString)))
    {
      a(jdField_a_of_type_JavaUtilHashMap);
      SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_WEBRTC_INTERVAL_DATA_V1", jdField_a_of_type_JavaUtilHashMap);
    }
    b(false);
  }
  
  @CalledByNative
  public static String getAudioPreDecodeOpusFile()
  {
    String str = a();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tbs_audio_preDecode.opus");
    return localStringBuilder.toString();
  }
  
  @CalledByNative
  public static String getAuidoEncodeOpusFile()
  {
    String str = a();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tbs_audio_encode.opus");
    return localStringBuilder.toString();
  }
  
  @CalledByNative
  public static String getAuidoInputPcmFile()
  {
    String str = a();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tbs_audio_input.pcm");
    return localStringBuilder.toString();
  }
  
  @CalledByNative
  public static String getCurrentPageHost()
  {
    if (!jdField_a_of_type_JavaLangString.equals("")) {
      return j.c(jdField_a_of_type_JavaLangString);
    }
    return "";
  }
  
  @CalledByNative
  public static boolean getDumpAudioDataEnabled()
  {
    synchronized (jdField_c_of_type_JavaLangObject)
    {
      boolean bool = jdField_d_of_type_Boolean;
      return bool;
    }
  }
  
  @CalledByNative
  public static boolean getUseOptimization()
  {
    synchronized (jdField_b_of_type_JavaLangObject)
    {
      boolean bool = jdField_a_of_type_Boolean;
      return bool;
    }
  }
  
  @CalledByNative
  private static String getWebRtcPluginPath()
  {
    synchronized (jdField_b_of_type_JavaLangObject)
    {
      if (jdField_c_of_type_Boolean) {
        return null;
      }
      return q.a().a();
    }
  }
  
  private static void h()
  {
    if ((f) && (!TextUtils.isEmpty(jdField_a_of_type_JavaLangString)))
    {
      a(jdField_b_of_type_JavaUtilHashMap);
      SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_WEBRTC_ENTIRE_DATA_V1", jdField_b_of_type_JavaUtilHashMap);
    }
    c(false);
  }
  
  private static void i()
  {
    if (!jdField_a_of_type_AndroidOsHandler.hasMessages(10001)) {
      jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(10001, 5000L);
    }
  }
  
  private static void j()
  {
    if (jdField_a_of_type_AndroidOsHandler.hasMessages(10005)) {
      jdField_a_of_type_AndroidOsHandler.removeMessages(10005);
    }
  }
  
  private static void k()
  {
    j();
    jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(10005, 3000L);
  }
  
  private static void l() {}
  
  private static void m()
  {
    if (!jdField_a_of_type_AndroidOsHandler.hasMessages(10003)) {
      jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(10003, 8000L);
    }
  }
  
  private static void n()
  {
    a(false);
    jdField_a_of_type_Int = 0;
    jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a = a.jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a;
  }
  
  @CalledByNative
  public static void removeAudioFiles()
  {
    Object localObject = a();
    try
    {
      localObject = new File((String)localObject);
      if (!((File)localObject).exists())
      {
        ((File)localObject).mkdirs();
        return;
      }
      localObject = new File(getAuidoInputPcmFile());
      if (((File)localObject).exists()) {
        ((File)localObject).delete();
      }
      localObject = new File(getAuidoEncodeOpusFile());
      if (((File)localObject).exists()) {
        ((File)localObject).delete();
      }
      localObject = new File(getAudioPreDecodeOpusFile());
      if (((File)localObject).exists())
      {
        ((File)localObject).delete();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  @CalledByNative
  public static void reportEntireSilentRate(float paramFloat)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportEntireSilentRate(this.a);
        }
      });
      return;
    }
    int i = (int)(paramFloat * 100.0F);
    jdField_b_of_type_JavaUtilHashMap.put("silent_rate", String.valueOf(i));
    f = true;
  }
  
  @CalledByNative
  public static void reportIntervalPackageDiscard(float paramFloat)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportIntervalPackageDiscard(this.a);
        }
      });
      return;
    }
    int i = (int)(paramFloat * 100.0F);
    jdField_a_of_type_JavaUtilHashMap.put("package_discard", String.valueOf(i));
    e = true;
    i();
  }
  
  @CalledByNative
  public static void reportIntervalRtcpPackageLostAndRtt(float paramFloat1, final float paramFloat2)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportIntervalRtcpPackageLostAndRtt(this.a, paramFloat2);
        }
      });
      return;
    }
    int i = (int)(paramFloat1 * 100.0F);
    int j = (int)paramFloat2;
    jdField_a_of_type_JavaUtilHashMap.put("package_lost", String.valueOf(i));
    jdField_a_of_type_JavaUtilHashMap.put("rtt", String.valueOf(j));
    e = true;
    i();
  }
  
  @CalledByNative
  public static void reportSendBitrate(float paramFloat1, final float paramFloat2)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportSendBitrate(this.a, paramFloat2);
        }
      });
      return;
    }
    int i = (int)paramFloat1;
    int j = (int)paramFloat2;
    jdField_a_of_type_JavaUtilHashMap.put("send_bitrate", String.valueOf(i));
    jdField_a_of_type_JavaUtilHashMap.put("encode_bitrate", String.valueOf(j));
    i();
  }
  
  @CalledByNative
  public static void reportVideoCaptureFrameCallbackState(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @CalledByNative
  public static void reportVideoDecodeFrameRate(float paramFloat)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportVideoDecodeFrameRate(this.a);
        }
      });
      return;
    }
    int i = (int)paramFloat;
    jdField_a_of_type_JavaUtilHashMap.put("decode_frame_rate", String.valueOf(i));
    e = true;
    i();
  }
  
  @CalledByNative
  public static void reportVideoEncodeFrameRate(float paramFloat)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportVideoEncodeFrameRate(this.a);
        }
      });
      return;
    }
    int i = (int)paramFloat;
    jdField_a_of_type_JavaUtilHashMap.put("encode_frame_rate", String.valueOf(i));
    e = true;
    i();
  }
  
  @CalledByNative
  public static void reportVideoEncodeSize(int paramInt1, final int paramInt2)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.reportVideoEncodeSize(this.a, paramInt2);
        }
      });
      return;
    }
    jdField_b_of_type_JavaUtilHashMap.put("encode_width", String.valueOf(paramInt1));
    jdField_b_of_type_JavaUtilHashMap.put("encode_height", String.valueOf(paramInt2));
    f = true;
  }
  
  @CalledByNative
  public static void setUseOptimization(boolean paramBoolean)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebRtcUtils.setUseOptimization(this.a);
        }
      });
      return;
    }
    for (;;)
    {
      synchronized (jdField_b_of_type_JavaLangObject)
      {
        if ((!jdField_b_of_type_Boolean) && (jdField_a_of_type_Boolean) && (jdField_a_of_type_JavaLangString != ""))
        {
          jdField_a_of_type_Boolean = paramBoolean;
          if (paramBoolean)
          {
            i = 1;
            jdField_b_of_type_JavaUtilHashMap.put("webrtc_optimization", String.valueOf(i));
            f = true;
          }
        }
        else
        {
          return;
        }
      }
      int i = 0;
    }
  }
  
  private static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a = new a("KEY_NONE", 0);
      b = new a("KEY_VOLUME_DOWN", 1);
      c = new a("KEY_VOLUME_UP", 2);
      jdField_a_of_type_ArrayOfComTencentSmttUtilWebRtcUtils$a = new a[] { jdField_a_of_type_ComTencentSmttUtilWebRtcUtils$a, b, c };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\WebRtcUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */