package com.tencent.smtt.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

public class X5LogUploadManager
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  private int jdField_a_of_type_Int = 0;
  private OnUploadListener jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener = null;
  private boolean jdField_a_of_type_Boolean = false;
  
  public X5LogUploadManager()
  {
    b();
  }
  
  /* Error */
  private static int a(b paramb)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: iconst_m1
    //   5: ireturn
    //   6: new 41	java/io/File
    //   9: dup
    //   10: aload_0
    //   11: invokevirtual 44	com/tencent/smtt/util/X5LogUploadManager$b:a	()Ljava/lang/String;
    //   14: invokespecial 47	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: astore_3
    //   18: aload_3
    //   19: invokevirtual 51	java/io/File:exists	()Z
    //   22: ifeq +302 -> 324
    //   25: aload_3
    //   26: invokevirtual 55	java/io/File:length	()J
    //   29: lconst_0
    //   30: lcmp
    //   31: ifeq +293 -> 324
    //   34: aload_3
    //   35: invokevirtual 58	java/io/File:isFile	()Z
    //   38: ifne +5 -> 43
    //   41: iconst_m1
    //   42: ireturn
    //   43: new 60	java/net/URL
    //   46: dup
    //   47: ldc 62
    //   49: invokespecial 63	java/net/URL:<init>	(Ljava/lang/String;)V
    //   52: invokevirtual 67	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   55: checkcast 69	javax/net/ssl/HttpsURLConnection
    //   58: astore_2
    //   59: aload_2
    //   60: iconst_1
    //   61: invokevirtual 73	javax/net/ssl/HttpsURLConnection:setDoOutput	(Z)V
    //   64: aload_2
    //   65: iconst_1
    //   66: invokevirtual 76	javax/net/ssl/HttpsURLConnection:setDoInput	(Z)V
    //   69: aload_2
    //   70: iconst_0
    //   71: invokevirtual 79	javax/net/ssl/HttpsURLConnection:setUseCaches	(Z)V
    //   74: aload_2
    //   75: ldc 81
    //   77: invokevirtual 84	javax/net/ssl/HttpsURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   80: new 86	java/lang/StringBuilder
    //   83: dup
    //   84: invokespecial 87	java/lang/StringBuilder:<init>	()V
    //   87: astore 4
    //   89: aload 4
    //   91: ldc 89
    //   93: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: pop
    //   97: aload 4
    //   99: aload_3
    //   100: invokevirtual 55	java/io/File:length	()J
    //   103: invokevirtual 96	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   106: pop
    //   107: aload_2
    //   108: ldc 98
    //   110: aload 4
    //   112: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   118: aload_2
    //   119: ldc 107
    //   121: ldc 109
    //   123: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   126: aload_2
    //   127: ldc 111
    //   129: ldc 113
    //   131: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   134: aload_2
    //   135: ldc 115
    //   137: invokestatic 121	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   140: invokevirtual 124	com/tencent/smtt/webkit/service/SmttServiceProxy:getQUA2	()Ljava/lang/String;
    //   143: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   146: aload_2
    //   147: ldc 126
    //   149: invokestatic 121	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   152: invokevirtual 129	com/tencent/smtt/webkit/service/SmttServiceProxy:getGUID	()Ljava/lang/String;
    //   155: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   158: aload_2
    //   159: ldc -125
    //   161: aload_0
    //   162: invokevirtual 133	com/tencent/smtt/util/X5LogUploadManager$b:b	()Ljava/lang/String;
    //   165: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   168: aload_0
    //   169: invokevirtual 136	com/tencent/smtt/util/X5LogUploadManager$b:a	()Lorg/json/JSONObject;
    //   172: ifnull +16 -> 188
    //   175: aload_2
    //   176: ldc -118
    //   178: aload_0
    //   179: invokevirtual 136	com/tencent/smtt/util/X5LogUploadManager$b:a	()Lorg/json/JSONObject;
    //   182: invokevirtual 141	org/json/JSONObject:toString	()Ljava/lang/String;
    //   185: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   188: aload_2
    //   189: ldc -113
    //   191: aload_0
    //   192: invokevirtual 146	com/tencent/smtt/util/X5LogUploadManager$b:c	()Ljava/lang/String;
    //   195: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   198: aload_2
    //   199: ldc -108
    //   201: invokestatic 151	com/tencent/smtt/util/g:b	()Ljava/lang/String;
    //   204: invokevirtual 105	javax/net/ssl/HttpsURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   207: aload_2
    //   208: invokevirtual 155	javax/net/ssl/HttpsURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   211: astore 4
    //   213: sipush 10240
    //   216: newarray <illegal type>
    //   218: astore 5
    //   220: new 157	java/io/FileInputStream
    //   223: dup
    //   224: aload_3
    //   225: invokespecial 160	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   228: astore_0
    //   229: aload_0
    //   230: aload 5
    //   232: iconst_0
    //   233: sipush 10240
    //   236: invokevirtual 164	java/io/FileInputStream:read	([BII)I
    //   239: istore_1
    //   240: iload_1
    //   241: iconst_m1
    //   242: if_icmpeq +15 -> 257
    //   245: aload 4
    //   247: aload 5
    //   249: iconst_0
    //   250: iload_1
    //   251: invokevirtual 170	java/io/OutputStream:write	([BII)V
    //   254: goto -25 -> 229
    //   257: aload_0
    //   258: invokevirtual 173	java/io/FileInputStream:close	()V
    //   261: aload 4
    //   263: invokevirtual 174	java/io/OutputStream:close	()V
    //   266: aload_2
    //   267: invokevirtual 178	javax/net/ssl/HttpsURLConnection:getResponseCode	()I
    //   270: istore_1
    //   271: iload_1
    //   272: ireturn
    //   273: astore_3
    //   274: aload_0
    //   275: astore_2
    //   276: aload_3
    //   277: astore_0
    //   278: goto +9 -> 287
    //   281: goto +26 -> 307
    //   284: astore_0
    //   285: aconst_null
    //   286: astore_2
    //   287: aload_2
    //   288: ifnull +15 -> 303
    //   291: aload_2
    //   292: invokevirtual 173	java/io/FileInputStream:close	()V
    //   295: goto +8 -> 303
    //   298: astore_2
    //   299: aload_2
    //   300: invokevirtual 181	java/io/IOException:printStackTrace	()V
    //   303: aload_0
    //   304: athrow
    //   305: aconst_null
    //   306: astore_0
    //   307: aload_0
    //   308: ifnull +14 -> 322
    //   311: aload_0
    //   312: invokevirtual 173	java/io/FileInputStream:close	()V
    //   315: iconst_m1
    //   316: ireturn
    //   317: astore_0
    //   318: aload_0
    //   319: invokevirtual 181	java/io/IOException:printStackTrace	()V
    //   322: iconst_m1
    //   323: ireturn
    //   324: iconst_m1
    //   325: ireturn
    //   326: astore_0
    //   327: iconst_m1
    //   328: ireturn
    //   329: astore_0
    //   330: goto -25 -> 305
    //   333: astore_2
    //   334: goto -53 -> 281
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	337	0	paramb	b
    //   239	33	1	i	int
    //   58	234	2	localObject1	Object
    //   298	2	2	localIOException	java.io.IOException
    //   333	1	2	localException	Exception
    //   17	208	3	localFile	File
    //   273	4	3	localObject2	Object
    //   87	175	4	localObject3	Object
    //   218	30	5	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   229	240	273	finally
    //   245	254	273	finally
    //   257	261	273	finally
    //   220	229	284	finally
    //   291	295	298	java/io/IOException
    //   311	315	317	java/io/IOException
    //   6	41	326	java/lang/Exception
    //   43	188	326	java/lang/Exception
    //   188	220	326	java/lang/Exception
    //   261	271	326	java/lang/Exception
    //   291	295	326	java/lang/Exception
    //   299	303	326	java/lang/Exception
    //   303	305	326	java/lang/Exception
    //   311	315	326	java/lang/Exception
    //   318	322	326	java/lang/Exception
    //   220	229	329	java/lang/Exception
    //   229	240	333	java/lang/Exception
    //   245	254	333	java/lang/Exception
    //   257	261	333	java/lang/Exception
  }
  
  public static X5LogUploadManager a()
  {
    return a.a();
  }
  
  private void b()
  {
    jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("log-upload-handler");
    jdField_a_of_type_AndroidOsHandlerThread.start();
    jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsHandlerThread.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        case 2: 
          X5LogUploadManager.a();
          return;
        }
        paramAnonymousMessage = (X5LogUploadManager.b)paramAnonymousMessage.obj;
        X5LogUploadManager.a(X5LogUploadManager.this, paramAnonymousMessage);
      }
    };
  }
  
  private void b(b paramb)
  {
    int i = a(paramb);
    int j = this.jdField_a_of_type_Int;
    boolean bool = true;
    this.jdField_a_of_type_Int = (j + 1);
    if ((i != 200) && (this.jdField_a_of_type_Int < 2))
    {
      paramb = jdField_a_of_type_AndroidOsHandler.obtainMessage(1, paramb);
      jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(paramb, 2000L);
    }
    else if (i == 200)
    {
      new File(paramb.a()).delete();
    }
    if (this.jdField_a_of_type_Boolean)
    {
      paramb = this.jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener;
      if (paramb != null)
      {
        this.jdField_a_of_type_Boolean = false;
        if (i != 200) {
          bool = false;
        }
        paramb.onUploadFinished(bool);
        this.jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener = null;
      }
    }
  }
  
  private static boolean b()
  {
    try
    {
      HttpsURLConnection localHttpsURLConnection = (HttpsURLConnection)new URL("https://qprostat.imtt.qq.com/x5corelog").openConnection();
      localHttpsURLConnection.setDoOutput(true);
      localHttpsURLConnection.setDoInput(true);
      localHttpsURLConnection.setUseCaches(false);
      localHttpsURLConnection.setRequestMethod("POST");
      localHttpsURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
      localHttpsURLConnection.setRequestProperty("Charset", "UTF-8");
      localHttpsURLConnection.setRequestProperty("q-ua2", SmttServiceProxy.getInstance().getQUA2());
      localHttpsURLConnection.setRequestProperty("q-guid", SmttServiceProxy.getInstance().getGUID());
      localHttpsURLConnection.setRequestProperty("q-log-type", "guid");
      if (!g.a().equals(""))
      {
        localHttpsURLConnection.setRequestProperty("openrandom", g.a());
        localHttpsURLConnection.setRequestProperty("openmode", g.b());
      }
      int i = localHttpsURLConnection.getResponseCode();
      return 200 == i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public void a()
  {
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage(2);
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void a(b paramb)
  {
    if (paramb == null) {
      return;
    }
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_Boolean = false;
    paramb = jdField_a_of_type_AndroidOsHandler.obtainMessage(1, paramb);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramb);
  }
  
  public void a(b paramb, OnUploadListener paramOnUploadListener)
  {
    if (paramb == null) {
      return;
    }
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_ComTencentSmttUtilX5LogUploadManager$OnUploadListener = paramOnUploadListener;
    paramb = jdField_a_of_type_AndroidOsHandler.obtainMessage(1, paramb);
    jdField_a_of_type_AndroidOsHandler.sendMessage(paramb);
  }
  
  public static abstract interface OnUploadListener
  {
    public abstract void onUploadFinished(boolean paramBoolean);
  }
  
  private static class a
  {
    private static final X5LogUploadManager a = new X5LogUploadManager();
  }
  
  public static class b
  {
    String jdField_a_of_type_JavaLangString;
    JSONObject jdField_a_of_type_OrgJsonJSONObject;
    String b;
    String c;
    
    public b(String paramString1, String paramString2, String paramString3, JSONObject paramJSONObject)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      this.jdField_a_of_type_OrgJsonJSONObject = paramJSONObject;
    }
    
    public String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    public JSONObject a()
    {
      return this.jdField_a_of_type_OrgJsonJSONObject;
    }
    
    public String b()
    {
      return this.b;
    }
    
    public String c()
    {
      return this.c;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("UploadZip [zipPath=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", logType=");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", openRandom=");
      localStringBuilder.append(this.c);
      localStringBuilder.append(", params=");
      localStringBuilder.append(this.jdField_a_of_type_OrgJsonJSONObject);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\X5LogUploadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */