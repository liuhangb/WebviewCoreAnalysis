package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

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

class HttpUtil
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
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 8
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
    //   26: if_icmpne +267 -> 293
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
    //   99: aload_0
    //   100: astore 4
    //   102: aload_0
    //   103: astore 5
    //   105: new 77	java/io/ByteArrayOutputStream
    //   108: dup
    //   109: invokespecial 78	java/io/ByteArrayOutputStream:<init>	()V
    //   112: astore_1
    //   113: aload_0
    //   114: astore 7
    //   116: aload_1
    //   117: astore 6
    //   119: aload_0
    //   120: astore 4
    //   122: aload_1
    //   123: astore 5
    //   125: sipush 128
    //   128: newarray <illegal type>
    //   130: astore 9
    //   132: aload_0
    //   133: astore 7
    //   135: aload_1
    //   136: astore 6
    //   138: aload_0
    //   139: astore 4
    //   141: aload_1
    //   142: astore 5
    //   144: aload_0
    //   145: aload 9
    //   147: invokevirtual 84	java/io/InputStream:read	([B)I
    //   150: istore_3
    //   151: iload_3
    //   152: iconst_m1
    //   153: if_icmpeq +26 -> 179
    //   156: aload_0
    //   157: astore 7
    //   159: aload_1
    //   160: astore 6
    //   162: aload_0
    //   163: astore 4
    //   165: aload_1
    //   166: astore 5
    //   168: aload_1
    //   169: aload 9
    //   171: iconst_0
    //   172: iload_3
    //   173: invokevirtual 88	java/io/ByteArrayOutputStream:write	([BII)V
    //   176: goto -44 -> 132
    //   179: iload_2
    //   180: ifeq +43 -> 223
    //   183: aload_0
    //   184: astore 7
    //   186: aload_1
    //   187: astore 6
    //   189: aload_0
    //   190: astore 4
    //   192: aload_1
    //   193: astore 5
    //   195: new 54	java/lang/String
    //   198: dup
    //   199: invokestatic 94	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/PostEncryption:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/PostEncryption;
    //   202: aload_1
    //   203: invokevirtual 98	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   206: invokevirtual 102	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/PostEncryption:DESDecrypt	([B)[B
    //   209: ldc 11
    //   211: invokespecial 105	java/lang/String:<init>	([BLjava/lang/String;)V
    //   214: astore 9
    //   216: aload 9
    //   218: astore 4
    //   220: goto +38 -> 258
    //   223: aload_0
    //   224: astore 7
    //   226: aload_1
    //   227: astore 6
    //   229: aload_0
    //   230: astore 4
    //   232: aload_1
    //   233: astore 5
    //   235: new 54	java/lang/String
    //   238: dup
    //   239: invokestatic 110	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Post3DESEncryption:getInstance	()Lcom/tencent/tbs/tbsshell/partner/miniqb/upgrade/Post3DESEncryption;
    //   242: aload_1
    //   243: invokevirtual 98	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   246: invokevirtual 113	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/Post3DESEncryption:DesDecrypt	([B)[B
    //   249: invokespecial 116	java/lang/String:<init>	([B)V
    //   252: astore 9
    //   254: aload 9
    //   256: astore 4
    //   258: goto +332 -> 590
    //   261: astore 4
    //   263: aload 7
    //   265: astore_0
    //   266: aload 6
    //   268: astore_1
    //   269: aload 4
    //   271: astore 6
    //   273: goto +302 -> 575
    //   276: astore_0
    //   277: aload 6
    //   279: astore_1
    //   280: goto +325 -> 605
    //   283: astore 6
    //   285: aconst_null
    //   286: astore_1
    //   287: aload 5
    //   289: astore_0
    //   290: goto +285 -> 575
    //   293: iload_3
    //   294: sipush 500
    //   297: if_icmpne +251 -> 548
    //   300: aload_0
    //   301: invokevirtual 119	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   304: astore_1
    //   305: aload_0
    //   306: invokevirtual 50	java/net/HttpURLConnection:getContentEncoding	()Ljava/lang/String;
    //   309: astore_0
    //   310: aload_0
    //   311: ifnull +24 -> 335
    //   314: aload_0
    //   315: ldc 52
    //   317: invokevirtual 58	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   320: ifeq +15 -> 335
    //   323: new 60	java/util/zip/GZIPInputStream
    //   326: dup
    //   327: aload_1
    //   328: invokespecial 63	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   331: astore_0
    //   332: goto +38 -> 370
    //   335: aload_0
    //   336: ifnull +32 -> 368
    //   339: aload_0
    //   340: ldc 65
    //   342: invokevirtual 58	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   345: ifeq +23 -> 368
    //   348: new 67	java/util/zip/InflaterInputStream
    //   351: dup
    //   352: aload_1
    //   353: new 69	java/util/zip/Inflater
    //   356: dup
    //   357: iconst_1
    //   358: invokespecial 72	java/util/zip/Inflater:<init>	(Z)V
    //   361: invokespecial 75	java/util/zip/InflaterInputStream:<init>	(Ljava/io/InputStream;Ljava/util/zip/Inflater;)V
    //   364: astore_0
    //   365: goto +5 -> 370
    //   368: aload_1
    //   369: astore_0
    //   370: aload_0
    //   371: astore 4
    //   373: aload_0
    //   374: astore 5
    //   376: new 77	java/io/ByteArrayOutputStream
    //   379: dup
    //   380: invokespecial 78	java/io/ByteArrayOutputStream:<init>	()V
    //   383: astore_1
    //   384: aload_0
    //   385: astore 7
    //   387: aload_1
    //   388: astore 6
    //   390: aload_0
    //   391: astore 4
    //   393: aload_1
    //   394: astore 5
    //   396: sipush 128
    //   399: newarray <illegal type>
    //   401: astore 9
    //   403: aload_0
    //   404: astore 7
    //   406: aload_1
    //   407: astore 6
    //   409: aload_0
    //   410: astore 4
    //   412: aload_1
    //   413: astore 5
    //   415: aload_0
    //   416: aload 9
    //   418: invokevirtual 84	java/io/InputStream:read	([B)I
    //   421: istore_3
    //   422: iload_3
    //   423: iconst_m1
    //   424: if_icmpeq +26 -> 450
    //   427: aload_0
    //   428: astore 7
    //   430: aload_1
    //   431: astore 6
    //   433: aload_0
    //   434: astore 4
    //   436: aload_1
    //   437: astore 5
    //   439: aload_1
    //   440: aload 9
    //   442: iconst_0
    //   443: iload_3
    //   444: invokevirtual 88	java/io/ByteArrayOutputStream:write	([BII)V
    //   447: goto -44 -> 403
    //   450: aload_0
    //   451: astore 7
    //   453: aload_1
    //   454: astore 6
    //   456: aload_0
    //   457: astore 4
    //   459: aload_1
    //   460: astore 5
    //   462: new 121	java/lang/StringBuilder
    //   465: dup
    //   466: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   469: astore 10
    //   471: aload_0
    //   472: astore 7
    //   474: aload_1
    //   475: astore 6
    //   477: aload_0
    //   478: astore 4
    //   480: aload_1
    //   481: astore 5
    //   483: aload 10
    //   485: ldc 124
    //   487: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   490: pop
    //   491: aload_0
    //   492: astore 7
    //   494: aload_1
    //   495: astore 6
    //   497: aload_0
    //   498: astore 4
    //   500: aload_1
    //   501: astore 5
    //   503: aload 10
    //   505: new 54	java/lang/String
    //   508: dup
    //   509: aload 9
    //   511: invokespecial 116	java/lang/String:<init>	([B)V
    //   514: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   517: pop
    //   518: aload_0
    //   519: astore 7
    //   521: aload_1
    //   522: astore 6
    //   524: aload_0
    //   525: astore 4
    //   527: aload_1
    //   528: astore 5
    //   530: ldc -126
    //   532: aload 10
    //   534: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   537: invokestatic 139	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   540: pop
    //   541: aload 8
    //   543: astore 4
    //   545: goto +45 -> 590
    //   548: aconst_null
    //   549: astore_0
    //   550: aload_0
    //   551: astore_1
    //   552: aload 8
    //   554: astore 4
    //   556: goto +34 -> 590
    //   559: astore_0
    //   560: aconst_null
    //   561: astore 4
    //   563: aload 6
    //   565: astore_1
    //   566: goto +39 -> 605
    //   569: astore 6
    //   571: aconst_null
    //   572: astore_0
    //   573: aload_0
    //   574: astore_1
    //   575: aload_0
    //   576: astore 4
    //   578: aload_1
    //   579: astore 5
    //   581: aload 6
    //   583: invokevirtual 142	java/lang/Throwable:printStackTrace	()V
    //   586: aload 8
    //   588: astore 4
    //   590: aload_0
    //   591: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   594: aload_1
    //   595: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   598: aload 4
    //   600: areturn
    //   601: astore_0
    //   602: aload 5
    //   604: astore_1
    //   605: aload 4
    //   607: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   610: aload_1
    //   611: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   614: aload_0
    //   615: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	616	0	paramHttpURLConnection	HttpURLConnection
    //   0	616	1	paramHttpResponseListener	HttpResponseListener
    //   0	616	2	paramBoolean	boolean
    //   10	434	3	i	int
    //   100	157	4	localObject1	Object
    //   261	9	4	localThrowable1	Throwable
    //   371	235	4	localObject2	Object
    //   103	500	5	localObject3	Object
    //   1	277	6	localObject4	Object
    //   283	1	6	localThrowable2	Throwable
    //   388	176	6	localHttpResponseListener	HttpResponseListener
    //   569	13	6	localThrowable3	Throwable
    //   114	406	7	localHttpURLConnection	HttpURLConnection
    //   4	583	8	localObject5	Object
    //   130	380	9	localObject6	Object
    //   469	64	10	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   125	132	261	java/lang/Throwable
    //   144	151	261	java/lang/Throwable
    //   168	176	261	java/lang/Throwable
    //   195	216	261	java/lang/Throwable
    //   235	254	261	java/lang/Throwable
    //   396	403	261	java/lang/Throwable
    //   415	422	261	java/lang/Throwable
    //   439	447	261	java/lang/Throwable
    //   462	471	261	java/lang/Throwable
    //   483	491	261	java/lang/Throwable
    //   503	518	261	java/lang/Throwable
    //   530	541	261	java/lang/Throwable
    //   105	113	276	finally
    //   376	384	276	finally
    //   105	113	283	java/lang/Throwable
    //   376	384	283	java/lang/Throwable
    //   6	11	559	finally
    //   15	22	559	finally
    //   29	39	559	finally
    //   43	61	559	finally
    //   68	94	559	finally
    //   300	310	559	finally
    //   314	332	559	finally
    //   339	365	559	finally
    //   6	11	569	java/lang/Throwable
    //   15	22	569	java/lang/Throwable
    //   29	39	569	java/lang/Throwable
    //   43	61	569	java/lang/Throwable
    //   68	94	569	java/lang/Throwable
    //   300	310	569	java/lang/Throwable
    //   314	332	569	java/lang/Throwable
    //   339	365	569	java/lang/Throwable
    //   125	132	601	finally
    //   144	151	601	finally
    //   168	176	601	finally
    //   195	216	601	finally
    //   235	254	601	finally
    //   396	403	601	finally
    //   415	422	601	finally
    //   439	447	601	finally
    //   462	471	601	finally
    //   483	491	601	finally
    //   503	518	601	finally
    //   530	541	601	finally
    //   581	586	601	finally
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
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("postData postUrl=");
      localStringBuilder.append((String)localObject1);
      localStringBuilder.toString();
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
    //   0: new 278	java/util/zip/GZIPOutputStream
    //   3: dup
    //   4: new 280	java/io/BufferedOutputStream
    //   7: dup
    //   8: aload_0
    //   9: invokevirtual 269	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   12: ldc_w 281
    //   15: invokespecial 284	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   18: invokespecial 287	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   21: astore_0
    //   22: aload_0
    //   23: astore_2
    //   24: aload_0
    //   25: aload_1
    //   26: invokevirtual 273	java/io/OutputStream:write	([B)V
    //   29: aload_0
    //   30: astore_2
    //   31: aload_0
    //   32: invokevirtual 276	java/io/OutputStream:flush	()V
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
    //   54: invokevirtual 219	java/lang/Exception:printStackTrace	()V
    //   57: aconst_null
    //   58: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   61: aload_0
    //   62: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   65: return
    //   66: astore_0
    //   67: aconst_null
    //   68: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
    //   71: aload_2
    //   72: invokestatic 144	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/HttpUtil:closeStream	(Ljava/io/Closeable;)V
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\HttpUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */