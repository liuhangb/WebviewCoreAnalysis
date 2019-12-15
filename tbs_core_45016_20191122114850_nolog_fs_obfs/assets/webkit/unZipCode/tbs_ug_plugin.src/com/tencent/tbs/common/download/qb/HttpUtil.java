package com.tencent.tbs.common.download.qb;

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
  private static String httpPost(HttpURLConnection paramHttpURLConnection, HttpResponseListener paramHttpResponseListener, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aload_0
    //   7: invokevirtual 38	java/net/HttpURLConnection:getResponseCode	()I
    //   10: istore_3
    //   11: aload_1
    //   12: ifnull +10 -> 22
    //   15: aload_1
    //   16: iload_3
    //   17: invokeinterface 42 2 0
    //   22: iload_3
    //   23: sipush 200
    //   26: if_icmpne +184 -> 210
    //   29: aload_0
    //   30: invokevirtual 46	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   33: astore_1
    //   34: aload_0
    //   35: invokevirtual 50	java/net/HttpURLConnection:getContentEncoding	()Ljava/lang/String;
    //   38: astore_0
    //   39: aload_0
    //   40: ifnull +24 -> 64
    //   43: aload_0
    //   44: ldc 52
    //   46: invokevirtual 58	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   49: ifeq +15 -> 64
    //   52: new 60	java/util/zip/GZIPInputStream
    //   55: dup
    //   56: aload_1
    //   57: invokespecial 63	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   60: astore_0
    //   61: goto +38 -> 99
    //   64: aload_0
    //   65: ifnull +32 -> 97
    //   68: aload_0
    //   69: ldc 65
    //   71: invokevirtual 58	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   74: ifeq +23 -> 97
    //   77: new 67	java/util/zip/InflaterInputStream
    //   80: dup
    //   81: aload_1
    //   82: new 69	java/util/zip/Inflater
    //   85: dup
    //   86: iconst_1
    //   87: invokespecial 72	java/util/zip/Inflater:<init>	(Z)V
    //   90: invokespecial 75	java/util/zip/InflaterInputStream:<init>	(Ljava/io/InputStream;Ljava/util/zip/Inflater;)V
    //   93: astore_0
    //   94: goto +5 -> 99
    //   97: aload_1
    //   98: astore_0
    //   99: new 77	java/io/ByteArrayOutputStream
    //   102: dup
    //   103: invokespecial 78	java/io/ByteArrayOutputStream:<init>	()V
    //   106: astore 4
    //   108: sipush 128
    //   111: newarray <illegal type>
    //   113: astore_1
    //   114: aload_0
    //   115: aload_1
    //   116: invokevirtual 84	java/io/InputStream:read	([B)I
    //   119: istore_3
    //   120: iload_3
    //   121: iconst_m1
    //   122: if_icmpeq +14 -> 136
    //   125: aload 4
    //   127: aload_1
    //   128: iconst_0
    //   129: iload_3
    //   130: invokevirtual 88	java/io/ByteArrayOutputStream:write	([BII)V
    //   133: goto -19 -> 114
    //   136: iload_2
    //   137: ifeq +21 -> 158
    //   140: new 54	java/lang/String
    //   143: dup
    //   144: aload 4
    //   146: invokevirtual 92	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   149: ldc 11
    //   151: invokespecial 95	java/lang/String:<init>	([BLjava/lang/String;)V
    //   154: astore_1
    //   155: goto +63 -> 218
    //   158: new 54	java/lang/String
    //   161: dup
    //   162: invokestatic 101	com/tencent/tbs/common/download/qb/Post3DESEncryption:getInstance	()Lcom/tencent/tbs/common/download/qb/Post3DESEncryption;
    //   165: aload 4
    //   167: invokevirtual 92	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   170: invokevirtual 105	com/tencent/tbs/common/download/qb/Post3DESEncryption:DesDecrypt	([B)[B
    //   173: invokespecial 108	java/lang/String:<init>	([B)V
    //   176: astore_1
    //   177: goto +41 -> 218
    //   180: astore_1
    //   181: goto +91 -> 272
    //   184: astore 5
    //   186: aload 4
    //   188: astore_1
    //   189: aload 5
    //   191: astore 4
    //   193: goto +56 -> 249
    //   196: astore_1
    //   197: aconst_null
    //   198: astore 4
    //   200: goto +72 -> 272
    //   203: astore 4
    //   205: aconst_null
    //   206: astore_1
    //   207: goto +42 -> 249
    //   210: aconst_null
    //   211: astore_0
    //   212: aload_0
    //   213: astore 4
    //   215: aload 5
    //   217: astore_1
    //   218: aload_0
    //   219: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   222: aload 4
    //   224: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   227: aload_1
    //   228: areturn
    //   229: astore_1
    //   230: aconst_null
    //   231: astore 5
    //   233: aload 4
    //   235: astore_0
    //   236: aload 5
    //   238: astore 4
    //   240: goto +32 -> 272
    //   243: astore 4
    //   245: aconst_null
    //   246: astore_0
    //   247: aload_0
    //   248: astore_1
    //   249: aload 4
    //   251: invokevirtual 113	java/lang/Throwable:printStackTrace	()V
    //   254: aload_0
    //   255: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   258: aload_1
    //   259: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   262: aconst_null
    //   263: areturn
    //   264: astore 5
    //   266: aload_1
    //   267: astore 4
    //   269: aload 5
    //   271: astore_1
    //   272: aload_0
    //   273: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   276: aload 4
    //   278: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   281: aload_1
    //   282: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	283	0	paramHttpURLConnection	HttpURLConnection
    //   0	283	1	paramHttpResponseListener	HttpResponseListener
    //   0	283	2	paramBoolean	boolean
    //   10	120	3	i	int
    //   1	198	4	localObject1	Object
    //   203	1	4	localThrowable1	Throwable
    //   213	26	4	localObject2	Object
    //   243	7	4	localThrowable2	Throwable
    //   267	10	4	localHttpResponseListener	HttpResponseListener
    //   4	1	5	localObject3	Object
    //   184	32	5	localThrowable3	Throwable
    //   231	6	5	localObject4	Object
    //   264	6	5	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   108	114	180	finally
    //   114	120	180	finally
    //   125	133	180	finally
    //   140	155	180	finally
    //   158	177	180	finally
    //   108	114	184	java/lang/Throwable
    //   114	120	184	java/lang/Throwable
    //   125	133	184	java/lang/Throwable
    //   140	155	184	java/lang/Throwable
    //   158	177	184	java/lang/Throwable
    //   99	108	196	finally
    //   99	108	203	java/lang/Throwable
    //   6	11	229	finally
    //   15	22	229	finally
    //   29	39	229	finally
    //   43	61	229	finally
    //   68	94	229	finally
    //   6	11	243	java/lang/Throwable
    //   15	22	243	java/lang/Throwable
    //   29	39	243	java/lang/Throwable
    //   43	61	243	java/lang/Throwable
    //   68	94	243	java/lang/Throwable
    //   249	254	264	finally
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
  
  public static String postData(String paramString, Map<String, String> paramMap, byte[] paramArrayOfByte, HttpResponseListener paramHttpResponseListener, boolean paramBoolean)
  {
    Object localObject = null;
    if (paramArrayOfByte == null) {
      return null;
    }
    paramMap = initHttpPostURLConnection(paramString, paramMap);
    paramString = (String)localObject;
    if (paramMap != null)
    {
      if (paramBoolean) {
        writeZipPostData(paramMap, paramArrayOfByte);
      } else {
        writePostData(paramMap, paramArrayOfByte);
      }
      paramString = httpPost(paramMap, paramHttpResponseListener, false);
    }
    return paramString;
  }
  
  public static String postData(String paramString, byte[] paramArrayOfByte, HttpResponseListener paramHttpResponseListener, boolean paramBoolean)
  {
    Object localObject2 = null;
    if (paramBoolean) {}
    try
    {
      Object localObject1 = PostEncryption.getInstance().initRSAKey();
      break label26;
      localObject1 = Post3DESEncryption.getInstance().getRSAKeyValue();
      label26:
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append((String)localObject1);
      localObject1 = localStringBuilder.toString();
      if (paramBoolean) {}
      try
      {
        paramString = PostEncryption.getInstance().DESEncrypt(paramArrayOfByte);
        paramArrayOfByte = paramString;
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      paramString = Post3DESEncryption.getInstance().DESEncrypt(paramArrayOfByte);
      paramArrayOfByte = paramString;
      if (paramArrayOfByte == null) {
        return null;
      }
      paramString = new HashMap();
      paramString.put("Content-Type", "application/x-www-form-urlencoded");
      paramString.put("Content-Length", String.valueOf(paramArrayOfByte.length));
      localObject1 = initHttpPostURLConnection((String)localObject1, paramString);
      paramString = (String)localObject2;
      if (localObject1 != null)
      {
        writePostData((HttpURLConnection)localObject1, paramArrayOfByte);
        paramString = httpPost((HttpURLConnection)localObject1, paramHttpResponseListener, paramBoolean);
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
  
  /* Error */
  private static void writeZipPostData(HttpURLConnection paramHttpURLConnection, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 260	java/util/zip/GZIPOutputStream
    //   3: dup
    //   4: new 262	java/io/BufferedOutputStream
    //   7: dup
    //   8: aload_0
    //   9: invokevirtual 251	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   12: ldc_w 263
    //   15: invokespecial 266	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   18: invokespecial 269	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   21: astore_0
    //   22: aload_0
    //   23: astore_2
    //   24: aload_0
    //   25: aload_1
    //   26: invokevirtual 255	java/io/OutputStream:write	([B)V
    //   29: aload_0
    //   30: astore_2
    //   31: aload_0
    //   32: invokevirtual 258	java/io/OutputStream:flush	()V
    //   35: goto +22 -> 57
    //   38: astore_1
    //   39: goto +12 -> 51
    //   42: astore_0
    //   43: aconst_null
    //   44: astore_2
    //   45: goto +22 -> 67
    //   48: astore_1
    //   49: aconst_null
    //   50: astore_0
    //   51: aload_0
    //   52: astore_2
    //   53: aload_1
    //   54: invokevirtual 188	java/lang/Exception:printStackTrace	()V
    //   57: aconst_null
    //   58: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   61: aload_0
    //   62: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   65: return
    //   66: astore_0
    //   67: aconst_null
    //   68: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   71: aload_2
    //   72: invokestatic 110	com/tencent/tbs/common/download/qb/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   75: aload_0
    //   76: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	paramHttpURLConnection	HttpURLConnection
    //   0	77	1	paramArrayOfByte	byte[]
    //   23	49	2	localHttpURLConnection	HttpURLConnection
    // Exception table:
    //   from	to	target	type
    //   24	29	38	java/lang/Exception
    //   31	35	38	java/lang/Exception
    //   0	22	42	finally
    //   0	22	48	java/lang/Exception
    //   24	29	66	finally
    //   31	35	66	finally
    //   53	57	66	finally
  }
  
  public static abstract interface HttpResponseListener
  {
    public abstract void onHttpResponseCode(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\HttpUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */