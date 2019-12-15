package com.tencent.tbs.tbsshell.partner.debug;

import android.os.Build.VERSION;
import java.io.Closeable;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtil
{
  public static final String DEFAULT_ENCODE_NAME = "utf-8";
  private static final int DEFAULT_TIME_OUT = 20000;
  private static final String LOGTAG = "tbsshell";
  
  private static void closeStream(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception paramCloseable) {}
  }
  
  /* Error */
  private static String httpPost(HttpURLConnection paramHttpURLConnection, HttpResponseListener paramHttpResponseListener)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aload_0
    //   6: invokevirtual 41	java/net/HttpURLConnection:getResponseCode	()I
    //   9: istore_2
    //   10: aload_1
    //   11: ifnull +10 -> 21
    //   14: aload_1
    //   15: iload_2
    //   16: invokeinterface 45 2 0
    //   21: iload_2
    //   22: sipush 200
    //   25: if_icmpne +159 -> 184
    //   28: aload_0
    //   29: invokevirtual 49	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   32: astore_1
    //   33: aload_0
    //   34: invokevirtual 53	java/net/HttpURLConnection:getContentEncoding	()Ljava/lang/String;
    //   37: astore_0
    //   38: aload_0
    //   39: ifnull +24 -> 63
    //   42: aload_0
    //   43: ldc 55
    //   45: invokevirtual 61	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   48: ifeq +15 -> 63
    //   51: new 63	java/util/zip/GZIPInputStream
    //   54: dup
    //   55: aload_1
    //   56: invokespecial 66	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   59: astore_0
    //   60: goto +38 -> 98
    //   63: aload_0
    //   64: ifnull +32 -> 96
    //   67: aload_0
    //   68: ldc 68
    //   70: invokevirtual 61	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   73: ifeq +23 -> 96
    //   76: new 70	java/util/zip/InflaterInputStream
    //   79: dup
    //   80: aload_1
    //   81: new 72	java/util/zip/Inflater
    //   84: dup
    //   85: iconst_1
    //   86: invokespecial 75	java/util/zip/Inflater:<init>	(Z)V
    //   89: invokespecial 78	java/util/zip/InflaterInputStream:<init>	(Ljava/io/InputStream;Ljava/util/zip/Inflater;)V
    //   92: astore_0
    //   93: goto +5 -> 98
    //   96: aload_1
    //   97: astore_0
    //   98: new 80	java/io/ByteArrayOutputStream
    //   101: dup
    //   102: invokespecial 81	java/io/ByteArrayOutputStream:<init>	()V
    //   105: astore_3
    //   106: sipush 128
    //   109: newarray <illegal type>
    //   111: astore_1
    //   112: aload_0
    //   113: aload_1
    //   114: invokevirtual 87	java/io/InputStream:read	([B)I
    //   117: istore_2
    //   118: iload_2
    //   119: iconst_m1
    //   120: if_icmpeq +13 -> 133
    //   123: aload_3
    //   124: aload_1
    //   125: iconst_0
    //   126: iload_2
    //   127: invokevirtual 91	java/io/ByteArrayOutputStream:write	([BII)V
    //   130: goto -18 -> 112
    //   133: new 57	java/lang/String
    //   136: dup
    //   137: aload_3
    //   138: invokevirtual 95	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   141: ldc 11
    //   143: invokespecial 98	java/lang/String:<init>	([BLjava/lang/String;)V
    //   146: astore_1
    //   147: goto +44 -> 191
    //   150: astore_1
    //   151: goto +94 -> 245
    //   154: astore 4
    //   156: aload_0
    //   157: astore_1
    //   158: aload_3
    //   159: astore_0
    //   160: aload 4
    //   162: astore_3
    //   163: goto +55 -> 218
    //   166: astore_1
    //   167: aconst_null
    //   168: astore_3
    //   169: goto +76 -> 245
    //   172: astore_3
    //   173: aconst_null
    //   174: astore 4
    //   176: aload_0
    //   177: astore_1
    //   178: aload 4
    //   180: astore_0
    //   181: goto +37 -> 218
    //   184: aconst_null
    //   185: astore_0
    //   186: aload_0
    //   187: astore_3
    //   188: aload 4
    //   190: astore_1
    //   191: aload_0
    //   192: invokestatic 100	com/tencent/tbs/tbsshell/partner/debug/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   195: aload_3
    //   196: invokestatic 100	com/tencent/tbs/tbsshell/partner/debug/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   199: aload_1
    //   200: areturn
    //   201: astore_1
    //   202: aconst_null
    //   203: astore 4
    //   205: aload_3
    //   206: astore_0
    //   207: aload 4
    //   209: astore_3
    //   210: goto +35 -> 245
    //   213: astore_3
    //   214: aconst_null
    //   215: astore_1
    //   216: aload_1
    //   217: astore_0
    //   218: aload_3
    //   219: invokevirtual 103	java/lang/Throwable:printStackTrace	()V
    //   222: aload_1
    //   223: invokestatic 100	com/tencent/tbs/tbsshell/partner/debug/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   226: aload_0
    //   227: invokestatic 100	com/tencent/tbs/tbsshell/partner/debug/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   230: aconst_null
    //   231: areturn
    //   232: astore 5
    //   234: aload_1
    //   235: astore 4
    //   237: aload_0
    //   238: astore_3
    //   239: aload 5
    //   241: astore_1
    //   242: aload 4
    //   244: astore_0
    //   245: aload_0
    //   246: invokestatic 100	com/tencent/tbs/tbsshell/partner/debug/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   249: aload_3
    //   250: invokestatic 100	com/tencent/tbs/tbsshell/partner/debug/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   253: aload_1
    //   254: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	255	0	paramHttpURLConnection	HttpURLConnection
    //   0	255	1	paramHttpResponseListener	HttpResponseListener
    //   9	118	2	i	int
    //   1	168	3	localObject1	Object
    //   172	1	3	localThrowable1	Throwable
    //   187	23	3	localObject2	Object
    //   213	6	3	localThrowable2	Throwable
    //   238	12	3	localHttpURLConnection	HttpURLConnection
    //   3	1	4	localObject3	Object
    //   154	7	4	localThrowable3	Throwable
    //   174	69	4	localHttpResponseListener	HttpResponseListener
    //   232	8	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   106	112	150	finally
    //   112	118	150	finally
    //   123	130	150	finally
    //   133	147	150	finally
    //   106	112	154	java/lang/Throwable
    //   112	118	154	java/lang/Throwable
    //   123	130	154	java/lang/Throwable
    //   133	147	154	java/lang/Throwable
    //   98	106	166	finally
    //   98	106	172	java/lang/Throwable
    //   5	10	201	finally
    //   14	21	201	finally
    //   28	38	201	finally
    //   42	60	201	finally
    //   67	93	201	finally
    //   5	10	213	java/lang/Throwable
    //   14	21	213	java/lang/Throwable
    //   28	38	213	java/lang/Throwable
    //   42	60	213	java/lang/Throwable
    //   67	93	213	java/lang/Throwable
    //   218	222	232	finally
  }
  
  private static HttpURLConnection initHttpPostURLConnection(String paramString, Map<String, String> paramMap)
  {
    Map.Entry localEntry = null;
    try
    {
      paramString = (HttpURLConnection)new URL(paramString).openConnection();
      try
      {
        paramString.setRequestMethod("POST");
        paramString.setDoOutput(true);
        paramString.setDoInput(true);
        paramString.setUseCaches(false);
        paramString.setConnectTimeout(20000);
        if (Build.VERSION.SDK_INT > 13) {
          paramString.setRequestProperty("Connection", "close");
        } else {
          paramString.setRequestProperty("http.keepAlive", "false");
        }
        paramMap = paramMap.entrySet().iterator();
        while (paramMap.hasNext())
        {
          localEntry = (Map.Entry)paramMap.next();
          paramString.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
        }
        return paramString;
      }
      catch (Exception paramMap) {}
      paramMap.printStackTrace();
    }
    catch (Exception paramMap)
    {
      paramString = localEntry;
    }
    return paramString;
  }
  
  public static String postData(String paramString, byte[] paramArrayOfByte, HttpResponseListener paramHttpResponseListener)
  {
    Object localObject1 = null;
    try
    {
      PostEncryption localPostEncryption = new PostEncryption(true);
      Object localObject2 = localPostEncryption.getRSAKeyValue();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append((String)localObject2);
      localObject2 = localStringBuilder.toString();
      try
      {
        paramString = localPostEncryption.DESEncrypt(paramArrayOfByte);
        paramArrayOfByte = paramString;
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      if (paramArrayOfByte == null) {
        return null;
      }
      paramString = new StringBuilder();
      paramString.append("[HttpUtil.postData] postUrl:");
      paramString.append((String)localObject2);
      paramString.toString();
      paramString = new HashMap();
      paramString.put("Content-Type", "application/x-www-form-urlencoded");
      paramString.put("Content-Length", String.valueOf(paramArrayOfByte.length));
      localObject2 = initHttpPostURLConnection((String)localObject2, paramString);
      paramString = (String)localObject1;
      if (localObject2 != null)
      {
        writePostData((HttpURLConnection)localObject2, paramArrayOfByte);
        paramString = httpPost((HttpURLConnection)localObject2, paramHttpResponseListener);
      }
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  private static void writePostData(HttpURLConnection paramHttpURLConnection, byte[] paramArrayOfByte)
  {
    try
    {
      paramHttpURLConnection = paramHttpURLConnection.getOutputStream();
      paramHttpURLConnection.write(paramArrayOfByte);
      paramHttpURLConnection.flush();
      return;
    }
    catch (Exception paramHttpURLConnection)
    {
      paramHttpURLConnection.printStackTrace();
    }
  }
  
  public static abstract interface HttpResponseListener
  {
    public abstract void onHttpResponseCode(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\debug\HttpUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */