package com.tencent.beacontbs.upload;

import android.content.Context;
import android.net.Proxy;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.c.a;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public abstract class d
{
  private static d a;
  
  public static d a(Context paramContext)
  {
    try
    {
      boolean bool = com.tencent.beacontbs.a.b.d.a().b();
      if ((a == null) && (paramContext != null))
      {
        if (bool) {
          a = new b();
        } else {
          a = new a(paramContext);
        }
      }
      else if ((a != null) && (a.a() != bool)) {
        if (bool) {
          a = new b();
        } else {
          a = new a(paramContext);
        }
      }
      paramContext = a;
      return paramContext;
    }
    finally {}
  }
  
  public abstract boolean a();
  
  public byte[] a(String paramString, int paramInt, byte[] paramArrayOfByte, b paramb)
    throws Exception
  {
    return null;
  }
  
  public byte[] a(String paramString, byte[] paramArrayOfByte, b paramb)
    throws Exception
  {
    return null;
  }
  
  static final class a
    extends d
  {
    private Context a;
    
    public a(Context paramContext)
    {
      this.a = paramContext;
    }
    
    private static f a(String paramString1, byte[] paramArrayOfByte, String paramString2, int paramInt)
      throws Exception
    {
      if (paramString1 == null)
      {
        a.d("no destUrl!", new Object[0]);
        return null;
      }
      if (paramArrayOfByte != null) {}
      try
      {
        paramArrayOfByte = new ByteArrayEntity(paramArrayOfByte);
        break label44;
        paramArrayOfByte = new ByteArrayEntity("".getBytes());
        label44:
        paramString2 = a(paramString2, paramInt);
        if (paramString2 == null) {}
        try
        {
          a.d("no httpClient!", new Object[0]);
          return null;
        }
        catch (Throwable paramString1)
        {
          HttpPost localHttpPost;
          paramArrayOfByte = null;
        }
        localHttpPost = new HttpPost(paramString1);
        try
        {
          localHttpPost.setHeader("wup_version", "3.0");
          localHttpPost.setHeader("TYPE_COMPRESS", "2");
          localHttpPost.setHeader("encr_type", "rsapost");
          paramString1 = com.tencent.beacontbs.a.b.d.a();
          if (paramString1 != null) {
            localHttpPost.setHeader("bea_key", paramString1.b());
          }
          localHttpPost.setEntity(paramArrayOfByte);
          paramString1 = new BasicHttpContext();
          paramArrayOfByte = paramString2.execute(localHttpPost, paramString1);
          a.h("execute request:\n %s", new Object[] { ((HttpRequest)paramString1.getAttribute("http.request")).getRequestLine().toString() });
          paramString1 = new f(paramArrayOfByte, localHttpPost, paramString2);
          return paramString1;
        }
        catch (Throwable paramString1)
        {
          paramArrayOfByte = localHttpPost;
        }
        a.a(paramString1);
      }
      catch (Throwable paramString1)
      {
        paramString2 = null;
        paramArrayOfByte = paramString2;
      }
      a.d("execute error:%s", new Object[] { paramString1.toString() });
      if (paramString2 != null) {
        paramString2.getConnectionManager().shutdown();
      }
      if (paramString2 != null) {
        paramString2.getConnectionManager().shutdown();
      }
      if (paramArrayOfByte != null) {
        paramArrayOfByte.abort();
      }
      throw new Exception(paramString1.toString());
    }
    
    private static HttpClient a(String paramString, int paramInt)
    {
      try
      {
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 30000);
        HttpConnectionParams.setSoTimeout(localBasicHttpParams, 20000);
        HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 2000);
        localBasicHttpParams.setBooleanParameter("http.protocol.handle-redirects", false);
        Object localObject = new DefaultHttpClient(localBasicHttpParams);
        try
        {
          HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, false);
          ((DefaultHttpClient)localObject).setHttpRequestRetryHandler(new HttpRequestRetryHandler()
          {
            public final boolean retryRequest(IOException paramAnonymousIOException, int paramAnonymousInt, HttpContext paramAnonymousHttpContext)
            {
              if (paramAnonymousInt >= 3) {
                return false;
              }
              if ((paramAnonymousIOException instanceof NoHttpResponseException)) {
                return true;
              }
              return (paramAnonymousIOException instanceof ClientProtocolException);
            }
          });
          if ((paramString != null) && (paramString.toLowerCase(Locale.US).contains("wap")) && (paramInt != 2))
          {
            a.a("use proxy: %s, try time: %s", new Object[] { paramString, Integer.valueOf(paramInt) });
            paramString = new HttpHost(Proxy.getDefaultHost(), Proxy.getDefaultPort());
            ((DefaultHttpClient)localObject).getParams().setParameter("http.route.default-proxy", paramString);
            return (HttpClient)localObject;
          }
          if (paramString != null) {
            a.a("Don't use proxy: %s, try time: %s", new Object[] { paramString, Integer.valueOf(paramInt) });
          }
          ((DefaultHttpClient)localObject).getParams().removeParameter("http.route.default-proxy");
          return (HttpClient)localObject;
        }
        catch (Throwable localThrowable2)
        {
          paramString = (String)localObject;
          localObject = localThrowable2;
        }
        a.a(localThrowable1);
      }
      catch (Throwable localThrowable1)
      {
        paramString = null;
      }
      a.d("httpclient error!", new Object[0]);
      if (paramString != null) {
        paramString.getConnectionManager().shutdown();
      }
      return null;
    }
    
    private static void a(f paramf)
    {
      try
      {
        if (paramf.jdField_a_of_type_OrgApacheHttpClientHttpClient != null) {
          paramf.jdField_a_of_type_OrgApacheHttpClientHttpClient.getConnectionManager().shutdown();
        }
        if (paramf.jdField_a_of_type_OrgApacheHttpClientMethodsHttpPost != null) {
          paramf.jdField_a_of_type_OrgApacheHttpClientMethodsHttpPost.abort();
        }
        return;
      }
      catch (Exception paramf) {}
    }
    
    /* Error */
    private byte[] a(HttpResponse paramHttpResponse, HttpPost paramHttpPost)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 7
      //   3: aconst_null
      //   4: astore 6
      //   6: aload_1
      //   7: ifnonnull +5 -> 12
      //   10: aconst_null
      //   11: areturn
      //   12: aload_1
      //   13: invokeinterface 253 1 0
      //   18: invokeinterface 258 1 0
      //   23: istore_3
      //   24: iload_3
      //   25: sipush 200
      //   28: if_icmpeq +31 -> 59
      //   31: ldc_w 260
      //   34: iconst_2
      //   35: anewarray 25	java/lang/Object
      //   38: dup
      //   39: iconst_0
      //   40: iload_3
      //   41: invokestatic 202	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   44: aastore
      //   45: dup
      //   46: iconst_1
      //   47: aload_1
      //   48: invokeinterface 253 1 0
      //   53: aastore
      //   54: invokestatic 263	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   57: aconst_null
      //   58: areturn
      //   59: aload_1
      //   60: ldc_w 265
      //   63: invokeinterface 268 2 0
      //   68: ifeq +61 -> 129
      //   71: aload_1
      //   72: ldc_w 270
      //   75: invokeinterface 268 2 0
      //   80: ifeq +49 -> 129
      //   83: aload_1
      //   84: ldc_w 265
      //   87: invokeinterface 274 2 0
      //   92: invokeinterface 279 1 0
      //   97: astore 4
      //   99: aload_1
      //   100: ldc_w 270
      //   103: invokeinterface 274 2 0
      //   108: invokeinterface 279 1 0
      //   113: astore 5
      //   115: invokestatic 75	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
      //   118: aload_0
      //   119: getfield 15	com/tencent/beacontbs/upload/d$a:a	Landroid/content/Context;
      //   122: aload 4
      //   124: aload 5
      //   126: invokevirtual 282	com/tencent/beacontbs/a/b/d:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
      //   129: aload_1
      //   130: invokeinterface 286 1 0
      //   135: astore_1
      //   136: aload_1
      //   137: ifnonnull +15 -> 152
      //   140: ldc_w 288
      //   143: iconst_0
      //   144: anewarray 25	java/lang/Object
      //   147: invokestatic 31	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   150: aconst_null
      //   151: areturn
      //   152: new 290	java/io/BufferedInputStream
      //   155: dup
      //   156: new 292	java/io/DataInputStream
      //   159: dup
      //   160: aload_1
      //   161: invokeinterface 298 1 0
      //   166: invokespecial 301	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   169: invokespecial 302	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
      //   172: astore 4
      //   174: aload 4
      //   176: astore_1
      //   177: new 304	java/io/ByteArrayOutputStream
      //   180: dup
      //   181: sipush 128
      //   184: invokespecial 307	java/io/ByteArrayOutputStream:<init>	(I)V
      //   187: astore 5
      //   189: aload 4
      //   191: astore_1
      //   192: aload 4
      //   194: invokevirtual 310	java/io/BufferedInputStream:read	()I
      //   197: istore_3
      //   198: iload_3
      //   199: iconst_m1
      //   200: if_icmpeq +15 -> 215
      //   203: aload 4
      //   205: astore_1
      //   206: aload 5
      //   208: iload_3
      //   209: invokevirtual 313	java/io/ByteArrayOutputStream:write	(I)V
      //   212: goto -23 -> 189
      //   215: aload 4
      //   217: astore_1
      //   218: aload 5
      //   220: invokevirtual 316	java/io/ByteArrayOutputStream:flush	()V
      //   223: aload 4
      //   225: astore_1
      //   226: aload 5
      //   228: invokevirtual 319	java/io/ByteArrayOutputStream:toByteArray	()[B
      //   231: astore 5
      //   233: aload 4
      //   235: invokevirtual 322	java/io/BufferedInputStream:close	()V
      //   238: goto +8 -> 246
      //   241: astore_1
      //   242: aload_1
      //   243: invokestatic 124	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
      //   246: aload 5
      //   248: astore_1
      //   249: aload_2
      //   250: ifnull +88 -> 338
      //   253: aload_2
      //   254: invokevirtual 139	org/apache/http/client/methods/HttpPost:abort	()V
      //   257: aload 5
      //   259: areturn
      //   260: astore 5
      //   262: goto +15 -> 277
      //   265: astore 4
      //   267: aconst_null
      //   268: astore_1
      //   269: goto +73 -> 342
      //   272: astore 5
      //   274: aconst_null
      //   275: astore 4
      //   277: aload 4
      //   279: astore_1
      //   280: aload 5
      //   282: invokestatic 124	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
      //   285: aload 4
      //   287: astore_1
      //   288: ldc_w 324
      //   291: iconst_1
      //   292: anewarray 25	java/lang/Object
      //   295: dup
      //   296: iconst_0
      //   297: aload 5
      //   299: invokevirtual 127	java/lang/Throwable:toString	()Ljava/lang/String;
      //   302: aastore
      //   303: invokestatic 31	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   306: aload 4
      //   308: ifnull +16 -> 324
      //   311: aload 4
      //   313: invokevirtual 322	java/io/BufferedInputStream:close	()V
      //   316: goto +8 -> 324
      //   319: astore_1
      //   320: aload_1
      //   321: invokestatic 124	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
      //   324: aload 7
      //   326: astore_1
      //   327: aload_2
      //   328: ifnull +10 -> 338
      //   331: aload 6
      //   333: astore 5
      //   335: goto -82 -> 253
      //   338: aload_1
      //   339: areturn
      //   340: astore 4
      //   342: aload_1
      //   343: ifnull +15 -> 358
      //   346: aload_1
      //   347: invokevirtual 322	java/io/BufferedInputStream:close	()V
      //   350: goto +8 -> 358
      //   353: astore_1
      //   354: aload_1
      //   355: invokestatic 124	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
      //   358: aload_2
      //   359: ifnull +7 -> 366
      //   362: aload_2
      //   363: invokevirtual 139	org/apache/http/client/methods/HttpPost:abort	()V
      //   366: aload 4
      //   368: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	369	0	this	a
      //   0	369	1	paramHttpResponse	HttpResponse
      //   0	369	2	paramHttpPost	HttpPost
      //   23	186	3	i	int
      //   97	137	4	localObject1	Object
      //   265	1	4	localObject2	Object
      //   275	37	4	localObject3	Object
      //   340	27	4	localObject4	Object
      //   113	145	5	localObject5	Object
      //   260	1	5	localThrowable1	Throwable
      //   272	26	5	localThrowable2	Throwable
      //   333	1	5	localObject6	Object
      //   4	328	6	localObject7	Object
      //   1	324	7	localObject8	Object
      // Exception table:
      //   from	to	target	type
      //   233	238	241	java/lang/Throwable
      //   177	189	260	java/lang/Throwable
      //   192	198	260	java/lang/Throwable
      //   206	212	260	java/lang/Throwable
      //   218	223	260	java/lang/Throwable
      //   226	233	260	java/lang/Throwable
      //   152	174	265	finally
      //   152	174	272	java/lang/Throwable
      //   311	316	319	java/lang/Throwable
      //   177	189	340	finally
      //   192	198	340	finally
      //   206	212	340	finally
      //   218	223	340	finally
      //   226	233	340	finally
      //   280	285	340	finally
      //   288	306	340	finally
      //   346	350	353	java/lang/Throwable
    }
    
    public final boolean a()
    {
      return false;
    }
    
    public final byte[] a(String paramString, byte[] paramArrayOfByte, b paramb)
      throws Exception
    {
      if (paramString == null)
      {
        a.d("no destUrl!", new Object[0]);
        return null;
      }
      long l1;
      if (paramArrayOfByte == null) {
        l1 = 0L;
      } else {
        l1 = paramArrayOfByte.length;
      }
      int k = 2;
      a.h("start req: %s sz:%d", new Object[] { paramString, Long.valueOf(l1) });
      String str = "";
      Object localObject1 = null;
      Object localObject2 = "";
      int n = 0;
      int i = 0;
      int j = 0;
      for (;;)
      {
        int m = n + 1;
        if ((n >= 3) || (i >= k)) {
          break label752;
        }
        if (j != 0) {
          break;
        }
        if (m > 1)
        {
          StringBuilder localStringBuilder = new StringBuilder("try time:");
          localStringBuilder.append(m);
          a.h(localStringBuilder.toString(), new Object[0]);
          if (m == k) {
            if (paramb.a() == k) {
              paramb.b(false);
            } else {}
          }
          try
          {
            Thread.sleep(5000L);
          }
          catch (InterruptedException localInterruptedException)
          {
            a.a(localInterruptedException);
          }
        }
        Object localObject3 = e.g(this.a);
        new Date().getTime();
        Object localObject4;
        try
        {
          localObject3 = a(paramString, paramArrayOfByte, (String)localObject3, m);
          localObject1 = localObject3;
        }
        catch (Exception localException)
        {
          localObject4 = localException.toString();
          if (((String)localObject2).equals(localObject4))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(str);
            ((StringBuilder)localObject2).append(m);
            ((StringBuilder)localObject2).append(":same ");
            str = ((StringBuilder)localObject2).toString();
          }
          else
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(str);
            ((StringBuilder)localObject2).append(m);
            ((StringBuilder)localObject2).append(":");
            ((StringBuilder)localObject2).append((String)localObject4);
            ((StringBuilder)localObject2).append(" ");
            str = ((StringBuilder)localObject2).toString();
          }
          localObject2 = localObject4;
        }
        new Date().getTime();
        if (localObject1 != null)
        {
          localObject4 = ((f)localObject1).jdField_a_of_type_OrgApacheHttpHttpResponse;
          HttpEntity localHttpEntity = ((HttpResponse)localObject4).getEntity();
          if (localHttpEntity != null)
          {
            long l2 = localHttpEntity.getContentLength();
            l1 = l2;
            if (l2 < 0L) {
              l1 = 0L;
            }
          }
          else
          {
            l1 = 0L;
          }
          n = ((HttpResponse)localObject4).getStatusLine().getStatusCode();
          a.h("response code:%d ", new Object[] { Integer.valueOf(n) });
          a.h("contentLength:%d ", new Object[] { Long.valueOf(l1) });
          if (n == 200)
          {
            if (((HttpResponse)localObject4).containsHeader("encrypt-status"))
            {
              a((f)localObject1);
              paramString = new StringBuilder("svr encry failed: ");
              paramString.append(((HttpResponse)localObject4).getFirstHeader("encrypt-status").getValue());
              a.d(paramString.toString(), new Object[0]);
              return null;
            }
            paramString = a((HttpResponse)localObject4, ((f)localObject1).jdField_a_of_type_OrgApacheHttpClientMethodsHttpPost);
            a((f)localObject1);
            return paramString;
          }
          if ((n != 301) && (n != 302) && (n != 303) && (n != 307)) {
            k = 0;
          } else {
            k = 1;
          }
          if (k != 0)
          {
            paramString = ((HttpResponse)localObject4).getFirstHeader("Location");
            if (paramString == null)
            {
              paramString = new StringBuilder("redirect code:");
              paramString.append(n);
              paramString.append(" Location is null! return");
              a.d(paramString.toString(), new Object[0]);
              a((f)localObject1);
              return null;
            }
            i += 1;
            paramString = paramString.getValue();
            a.h("redirect code:%d , to:%s", new Object[] { Integer.valueOf(n), paramString });
            j = 1;
            k = 0;
          }
          else
          {
            k = m;
          }
          m = 2;
          a((f)localObject1);
        }
        else
        {
          n = m;
          m = k;
          k = n;
        }
        n = k;
        k = m;
      }
      throw new Exception("net redirect");
      label752:
      throw new Exception(str);
    }
  }
  
  static final class b
    extends d
  {
    private static byte[] a(Socket paramSocket)
      throws IOException
    {
      a.b("[net] begin waiting server response.", new Object[0]);
      paramSocket = paramSocket.getInputStream();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte['ࠀ'];
      for (;;)
      {
        int i = paramSocket.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      localByteArrayOutputStream.flush();
      arrayOfByte = localByteArrayOutputStream.toByteArray();
      a.b("[net] server response length: %d [need >= 4]", new Object[] { Integer.valueOf(arrayOfByte.length) });
      ByteBuffer localByteBuffer = ByteBuffer.allocate(arrayOfByte.length - 4);
      localByteBuffer.put(arrayOfByte, 2, arrayOfByte.length - 4);
      arrayOfByte = localByteBuffer.array();
      localByteArrayOutputStream.close();
      paramSocket.close();
      return arrayOfByte;
    }
    
    public final boolean a()
    {
      return true;
    }
    
    /* Error */
    public final byte[] a(String paramString, int paramInt, byte[] paramArrayOfByte, b paramb)
      throws Exception
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +1099 -> 1100
      //   4: iload_2
      //   5: ifgt +6 -> 11
      //   8: goto +1092 -> 1100
      //   11: ldc 87
      //   13: iconst_3
      //   14: anewarray 18	java/lang/Object
      //   17: dup
      //   18: iconst_0
      //   19: aload 4
      //   21: getfield 92	com/tencent/beacontbs/upload/b:a	I
      //   24: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   27: aastore
      //   28: dup
      //   29: iconst_1
      //   30: aload 4
      //   32: getfield 94	com/tencent/beacontbs/upload/b:b	I
      //   35: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   38: aastore
      //   39: dup
      //   40: iconst_2
      //   41: aload 4
      //   43: getfield 97	com/tencent/beacontbs/upload/b:c	I
      //   46: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   49: aastore
      //   50: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   53: ldc 99
      //   55: astore 10
      //   57: iconst_0
      //   58: istore 5
      //   60: iload 5
      //   62: iconst_1
      //   63: iadd
      //   64: istore 6
      //   66: iload 5
      //   68: iconst_3
      //   69: if_icmpge +1021 -> 1090
      //   72: iload 6
      //   74: iconst_1
      //   75: if_icmple +71 -> 146
      //   78: new 101	java/lang/StringBuilder
      //   81: dup
      //   82: ldc 103
      //   84: invokespecial 106	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   87: astore 8
      //   89: aload 8
      //   91: iload 6
      //   93: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   96: pop
      //   97: aload 8
      //   99: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   102: iconst_0
      //   103: anewarray 18	java/lang/Object
      //   106: invokestatic 117	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   109: iload 6
      //   111: iconst_2
      //   112: if_icmpne +18 -> 130
      //   115: aload 4
      //   117: invokevirtual 120	com/tencent/beacontbs/upload/b:a	()I
      //   120: iconst_2
      //   121: if_icmpne +9 -> 130
      //   124: aload 4
      //   126: iconst_0
      //   127: invokevirtual 123	com/tencent/beacontbs/upload/b:b	(Z)V
      //   130: ldc2_w 124
      //   133: invokestatic 131	java/lang/Thread:sleep	(J)V
      //   136: goto +10 -> 146
      //   139: astore 8
      //   141: aload 8
      //   143: invokestatic 134	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
      //   146: new 101	java/lang/StringBuilder
      //   149: dup
      //   150: invokespecial 135	java/lang/StringBuilder:<init>	()V
      //   153: astore 8
      //   155: aload 8
      //   157: aload_1
      //   158: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   161: pop
      //   162: aload 8
      //   164: ldc -116
      //   166: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   169: pop
      //   170: aload 8
      //   172: iload_2
      //   173: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   176: pop
      //   177: new 142	java/util/Date
      //   180: dup
      //   181: invokespecial 143	java/util/Date:<init>	()V
      //   184: invokevirtual 147	java/util/Date:getTime	()J
      //   187: pop2
      //   188: aload_1
      //   189: invokestatic 153	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
      //   192: astore 8
      //   194: ldc -101
      //   196: iconst_2
      //   197: anewarray 18	java/lang/Object
      //   200: dup
      //   201: iconst_0
      //   202: aload 8
      //   204: invokevirtual 158	java/net/InetAddress:getHostAddress	()Ljava/lang/String;
      //   207: aastore
      //   208: dup
      //   209: iconst_1
      //   210: iload_2
      //   211: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   214: aastore
      //   215: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   218: new 25	java/net/Socket
      //   221: dup
      //   222: aload 8
      //   224: invokevirtual 158	java/net/InetAddress:getHostAddress	()Ljava/lang/String;
      //   227: iload_2
      //   228: invokespecial 161	java/net/Socket:<init>	(Ljava/lang/String;I)V
      //   231: astore 9
      //   233: aload 9
      //   235: astore 8
      //   237: aload 9
      //   239: sipush 30000
      //   242: invokevirtual 165	java/net/Socket:setSoTimeout	(I)V
      //   245: aload 9
      //   247: astore 8
      //   249: ldc -89
      //   251: iconst_3
      //   252: anewarray 18	java/lang/Object
      //   255: dup
      //   256: iconst_0
      //   257: aload 9
      //   259: invokevirtual 171	java/net/Socket:getLocalAddress	()Ljava/net/InetAddress;
      //   262: invokevirtual 174	java/net/InetAddress:getHostName	()Ljava/lang/String;
      //   265: aastore
      //   266: dup
      //   267: iconst_1
      //   268: aload 9
      //   270: invokevirtual 177	java/net/Socket:getLocalPort	()I
      //   273: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   276: aastore
      //   277: dup
      //   278: iconst_2
      //   279: aload_3
      //   280: arraylength
      //   281: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   284: aastore
      //   285: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   288: aload 9
      //   290: astore 8
      //   292: new 179	java/util/HashMap
      //   295: dup
      //   296: invokespecial 180	java/util/HashMap:<init>	()V
      //   299: astore 11
      //   301: aload 9
      //   303: astore 8
      //   305: aload 4
      //   307: invokevirtual 182	com/tencent/beacontbs/upload/b:a	()Ljava/lang/String;
      //   310: astore 12
      //   312: aload 12
      //   314: ifnull +36 -> 350
      //   317: aload 9
      //   319: astore 8
      //   321: ldc 99
      //   323: aload 12
      //   325: invokevirtual 188	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   328: ifne +22 -> 350
      //   331: aload 9
      //   333: astore 8
      //   335: aload 11
      //   337: ldc -66
      //   339: aload 4
      //   341: invokevirtual 182	com/tencent/beacontbs/upload/b:a	()Ljava/lang/String;
      //   344: invokeinterface 195 3 0
      //   349: pop
      //   350: aload 9
      //   352: astore 8
      //   354: invokestatic 200	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
      //   357: invokevirtual 203	com/tencent/beacontbs/a/b/d:d	()Ljava/lang/String;
      //   360: astore 12
      //   362: aload 12
      //   364: ifnull +33 -> 397
      //   367: aload 9
      //   369: astore 8
      //   371: ldc 99
      //   373: aload 12
      //   375: invokevirtual 188	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   378: ifne +19 -> 397
      //   381: aload 9
      //   383: astore 8
      //   385: aload 11
      //   387: ldc -51
      //   389: aload 12
      //   391: invokeinterface 195 3 0
      //   396: pop
      //   397: aload 9
      //   399: astore 8
      //   401: aload 11
      //   403: ldc -49
      //   405: ldc -47
      //   407: invokeinterface 195 3 0
      //   412: pop
      //   413: aload 9
      //   415: astore 8
      //   417: aload 11
      //   419: ldc -45
      //   421: ldc -43
      //   423: invokeinterface 195 3 0
      //   428: pop
      //   429: aload 9
      //   431: astore 8
      //   433: aload 11
      //   435: ldc -41
      //   437: ldc -39
      //   439: invokeinterface 195 3 0
      //   444: pop
      //   445: aload 9
      //   447: astore 8
      //   449: invokestatic 200	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
      //   452: astore 12
      //   454: aload 12
      //   456: ifnull +22 -> 478
      //   459: aload 9
      //   461: astore 8
      //   463: aload 11
      //   465: ldc -37
      //   467: aload 12
      //   469: invokevirtual 221	com/tencent/beacontbs/a/b/d:b	()Ljava/lang/String;
      //   472: invokeinterface 195 3 0
      //   477: pop
      //   478: aload 9
      //   480: astore 8
      //   482: new 223	com/tencent/beacontbs/b/a/d
      //   485: dup
      //   486: aload 11
      //   488: aload_3
      //   489: invokespecial 226	com/tencent/beacontbs/b/a/d:<init>	(Ljava/util/Map;[B)V
      //   492: astore 11
      //   494: aload 9
      //   496: astore 8
      //   498: new 228	com/tencent/beacontbs/d/b
      //   501: dup
      //   502: invokespecial 229	com/tencent/beacontbs/d/b:<init>	()V
      //   505: astore 12
      //   507: aload 9
      //   509: astore 8
      //   511: aload 11
      //   513: aload 12
      //   515: invokevirtual 232	com/tencent/beacontbs/b/a/d:a	(Lcom/tencent/beacontbs/d/b;)V
      //   518: aload 9
      //   520: astore 8
      //   522: aload 9
      //   524: invokevirtual 236	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
      //   527: astore 11
      //   529: aload 9
      //   531: astore 8
      //   533: aload 12
      //   535: invokevirtual 238	com/tencent/beacontbs/d/b:a	()[B
      //   538: astore 12
      //   540: aload 9
      //   542: astore 8
      //   544: aload 12
      //   546: arraylength
      //   547: istore 5
      //   549: aload 9
      //   551: astore 8
      //   553: ldc -16
      //   555: iconst_2
      //   556: anewarray 18	java/lang/Object
      //   559: dup
      //   560: iconst_0
      //   561: aload_3
      //   562: arraylength
      //   563: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   566: aastore
      //   567: dup
      //   568: iconst_1
      //   569: iload 5
      //   571: invokestatic 57	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   574: aastore
      //   575: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   578: iload 5
      //   580: iconst_4
      //   581: iadd
      //   582: istore 7
      //   584: aload 9
      //   586: astore 8
      //   588: iload 7
      //   590: invokestatic 63	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
      //   593: astore 13
      //   595: aload 9
      //   597: astore 8
      //   599: aload 13
      //   601: getstatic 246	java/nio/ByteOrder:BIG_ENDIAN	Ljava/nio/ByteOrder;
      //   604: invokevirtual 250	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
      //   607: pop
      //   608: aload 9
      //   610: astore 8
      //   612: aload 13
      //   614: iload 7
      //   616: i2s
      //   617: invokevirtual 254	java/nio/ByteBuffer:putShort	(S)Ljava/nio/ByteBuffer;
      //   620: pop
      //   621: aload 9
      //   623: astore 8
      //   625: aload 13
      //   627: aload 12
      //   629: invokevirtual 257	java/nio/ByteBuffer:put	([B)Ljava/nio/ByteBuffer;
      //   632: pop
      //   633: aload 9
      //   635: astore 8
      //   637: aload 13
      //   639: bipush 13
      //   641: invokevirtual 260	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
      //   644: pop
      //   645: aload 9
      //   647: astore 8
      //   649: aload 13
      //   651: bipush 10
      //   653: invokevirtual 260	java/nio/ByteBuffer:put	(B)Ljava/nio/ByteBuffer;
      //   656: pop
      //   657: iload 5
      //   659: ldc_w 261
      //   662: if_icmplt +17 -> 679
      //   665: aload 9
      //   667: astore 8
      //   669: ldc_w 263
      //   672: iconst_0
      //   673: anewarray 18	java/lang/Object
      //   676: invokestatic 265	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   679: aload 9
      //   681: astore 8
      //   683: aload 11
      //   685: aload 13
      //   687: invokevirtual 70	java/nio/ByteBuffer:array	()[B
      //   690: invokevirtual 270	java/io/OutputStream:write	([B)V
      //   693: aload 9
      //   695: astore 8
      //   697: aload 11
      //   699: invokevirtual 271	java/io/OutputStream:flush	()V
      //   702: aload 9
      //   704: astore 8
      //   706: aload 9
      //   708: invokestatic 273	com/tencent/beacontbs/upload/d$b:a	(Ljava/net/Socket;)[B
      //   711: astore 11
      //   713: ldc_w 275
      //   716: iconst_0
      //   717: anewarray 18	java/lang/Object
      //   720: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   723: aload 9
      //   725: invokevirtual 276	java/net/Socket:close	()V
      //   728: aload 11
      //   730: astore 8
      //   732: goto +309 -> 1041
      //   735: aload 11
      //   737: astore 8
      //   739: goto +302 -> 1041
      //   742: astore 11
      //   744: goto +15 -> 759
      //   747: astore_1
      //   748: aconst_null
      //   749: astore 8
      //   751: goto +317 -> 1068
      //   754: astore 11
      //   756: aconst_null
      //   757: astore 9
      //   759: aload 9
      //   761: astore 8
      //   763: new 101	java/lang/StringBuilder
      //   766: dup
      //   767: invokespecial 135	java/lang/StringBuilder:<init>	()V
      //   770: astore 12
      //   772: aload 9
      //   774: astore 8
      //   776: aload 12
      //   778: aload 10
      //   780: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   783: pop
      //   784: aload 9
      //   786: astore 8
      //   788: aload 12
      //   790: ldc -116
      //   792: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   795: pop
      //   796: aload 9
      //   798: astore 8
      //   800: aload 12
      //   802: aload 11
      //   804: invokevirtual 279	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   807: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   810: pop
      //   811: aload 9
      //   813: astore 8
      //   815: aload 12
      //   817: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   820: astore 10
      //   822: ldc_w 275
      //   825: iconst_0
      //   826: anewarray 18	java/lang/Object
      //   829: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   832: aload 10
      //   834: astore 8
      //   836: aload 9
      //   838: ifnull +192 -> 1030
      //   841: aload 10
      //   843: astore 8
      //   845: goto +180 -> 1025
      //   848: aconst_null
      //   849: astore 9
      //   851: aload 9
      //   853: astore 8
      //   855: new 101	java/lang/StringBuilder
      //   858: dup
      //   859: invokespecial 135	java/lang/StringBuilder:<init>	()V
      //   862: astore 11
      //   864: aload 9
      //   866: astore 8
      //   868: aload 11
      //   870: aload 10
      //   872: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   875: pop
      //   876: aload 9
      //   878: astore 8
      //   880: aload 11
      //   882: iload 6
      //   884: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   887: pop
      //   888: aload 9
      //   890: astore 8
      //   892: aload 11
      //   894: ldc_w 281
      //   897: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   900: pop
      //   901: aload 9
      //   903: astore 8
      //   905: aload 11
      //   907: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   910: astore 10
      //   912: ldc_w 275
      //   915: iconst_0
      //   916: anewarray 18	java/lang/Object
      //   919: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   922: aload 10
      //   924: astore 8
      //   926: aload 9
      //   928: ifnull +102 -> 1030
      //   931: aload 10
      //   933: astore 8
      //   935: goto +90 -> 1025
      //   938: aconst_null
      //   939: astore 9
      //   941: aload 9
      //   943: astore 8
      //   945: new 101	java/lang/StringBuilder
      //   948: dup
      //   949: invokespecial 135	java/lang/StringBuilder:<init>	()V
      //   952: astore 11
      //   954: aload 9
      //   956: astore 8
      //   958: aload 11
      //   960: aload 10
      //   962: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   965: pop
      //   966: aload 9
      //   968: astore 8
      //   970: aload 11
      //   972: iload 6
      //   974: invokevirtual 110	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   977: pop
      //   978: aload 9
      //   980: astore 8
      //   982: aload 11
      //   984: ldc_w 283
      //   987: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   990: pop
      //   991: aload 9
      //   993: astore 8
      //   995: aload 11
      //   997: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1000: astore 10
      //   1002: ldc_w 275
      //   1005: iconst_0
      //   1006: anewarray 18	java/lang/Object
      //   1009: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   1012: aload 10
      //   1014: astore 8
      //   1016: aload 9
      //   1018: ifnull +12 -> 1030
      //   1021: aload 10
      //   1023: astore 8
      //   1025: aload 9
      //   1027: invokevirtual 276	java/net/Socket:close	()V
      //   1030: aconst_null
      //   1031: astore 9
      //   1033: aload 8
      //   1035: astore 10
      //   1037: aload 9
      //   1039: astore 8
      //   1041: new 142	java/util/Date
      //   1044: dup
      //   1045: invokespecial 143	java/util/Date:<init>	()V
      //   1048: invokevirtual 147	java/util/Date:getTime	()J
      //   1051: pop2
      //   1052: aload 8
      //   1054: ifnull +6 -> 1060
      //   1057: aload 8
      //   1059: areturn
      //   1060: iload 6
      //   1062: istore 5
      //   1064: goto -1004 -> 60
      //   1067: astore_1
      //   1068: ldc_w 275
      //   1071: iconst_0
      //   1072: anewarray 18	java/lang/Object
      //   1075: invokestatic 23	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   1078: aload 8
      //   1080: ifnull +8 -> 1088
      //   1083: aload 8
      //   1085: invokevirtual 276	java/net/Socket:close	()V
      //   1088: aload_1
      //   1089: athrow
      //   1090: new 85	java/net/ConnectException
      //   1093: dup
      //   1094: aload 10
      //   1096: invokespecial 284	java/net/ConnectException:<init>	(Ljava/lang/String;)V
      //   1099: athrow
      //   1100: ldc_w 286
      //   1103: iconst_0
      //   1104: anewarray 18	java/lang/Object
      //   1107: invokestatic 265	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   1110: aconst_null
      //   1111: areturn
      //   1112: astore 8
      //   1114: goto -176 -> 938
      //   1117: astore 8
      //   1119: goto -271 -> 848
      //   1122: astore 8
      //   1124: goto -183 -> 941
      //   1127: astore 8
      //   1129: goto -278 -> 851
      //   1132: astore 8
      //   1134: goto -399 -> 735
      //   1137: astore 9
      //   1139: goto -109 -> 1030
      //   1142: astore_3
      //   1143: goto -55 -> 1088
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1146	0	this	b
      //   0	1146	1	paramString	String
      //   0	1146	2	paramInt	int
      //   0	1146	3	paramArrayOfByte	byte[]
      //   0	1146	4	paramb	b
      //   58	1005	5	i	int
      //   64	997	6	j	int
      //   582	33	7	k	int
      //   87	11	8	localStringBuilder1	StringBuilder
      //   139	3	8	localInterruptedException	InterruptedException
      //   153	931	8	localObject1	Object
      //   1112	1	8	localSocketTimeoutException1	java.net.SocketTimeoutException
      //   1117	1	8	localConnectException1	java.net.ConnectException
      //   1122	1	8	localSocketTimeoutException2	java.net.SocketTimeoutException
      //   1127	1	8	localConnectException2	java.net.ConnectException
      //   1132	1	8	localIOException1	IOException
      //   231	807	9	localSocket	Socket
      //   1137	1	9	localIOException2	IOException
      //   55	1040	10	localObject2	Object
      //   299	437	11	localObject3	Object
      //   742	1	11	localException1	Exception
      //   754	49	11	localException2	Exception
      //   862	134	11	localStringBuilder2	StringBuilder
      //   310	506	12	localObject4	Object
      //   593	93	13	localByteBuffer	ByteBuffer
      // Exception table:
      //   from	to	target	type
      //   130	136	139	java/lang/InterruptedException
      //   237	245	742	java/lang/Exception
      //   249	288	742	java/lang/Exception
      //   292	301	742	java/lang/Exception
      //   305	312	742	java/lang/Exception
      //   321	331	742	java/lang/Exception
      //   335	350	742	java/lang/Exception
      //   354	362	742	java/lang/Exception
      //   371	381	742	java/lang/Exception
      //   385	397	742	java/lang/Exception
      //   401	413	742	java/lang/Exception
      //   417	429	742	java/lang/Exception
      //   433	445	742	java/lang/Exception
      //   449	454	742	java/lang/Exception
      //   463	478	742	java/lang/Exception
      //   482	494	742	java/lang/Exception
      //   498	507	742	java/lang/Exception
      //   511	518	742	java/lang/Exception
      //   522	529	742	java/lang/Exception
      //   533	540	742	java/lang/Exception
      //   544	549	742	java/lang/Exception
      //   553	578	742	java/lang/Exception
      //   588	595	742	java/lang/Exception
      //   599	608	742	java/lang/Exception
      //   612	621	742	java/lang/Exception
      //   625	633	742	java/lang/Exception
      //   637	645	742	java/lang/Exception
      //   649	657	742	java/lang/Exception
      //   669	679	742	java/lang/Exception
      //   683	693	742	java/lang/Exception
      //   697	702	742	java/lang/Exception
      //   706	713	742	java/lang/Exception
      //   218	233	747	finally
      //   218	233	754	java/lang/Exception
      //   237	245	1067	finally
      //   249	288	1067	finally
      //   292	301	1067	finally
      //   305	312	1067	finally
      //   321	331	1067	finally
      //   335	350	1067	finally
      //   354	362	1067	finally
      //   371	381	1067	finally
      //   385	397	1067	finally
      //   401	413	1067	finally
      //   417	429	1067	finally
      //   433	445	1067	finally
      //   449	454	1067	finally
      //   463	478	1067	finally
      //   482	494	1067	finally
      //   498	507	1067	finally
      //   511	518	1067	finally
      //   522	529	1067	finally
      //   533	540	1067	finally
      //   544	549	1067	finally
      //   553	578	1067	finally
      //   588	595	1067	finally
      //   599	608	1067	finally
      //   612	621	1067	finally
      //   625	633	1067	finally
      //   637	645	1067	finally
      //   649	657	1067	finally
      //   669	679	1067	finally
      //   683	693	1067	finally
      //   697	702	1067	finally
      //   706	713	1067	finally
      //   763	772	1067	finally
      //   776	784	1067	finally
      //   788	796	1067	finally
      //   800	811	1067	finally
      //   815	822	1067	finally
      //   855	864	1067	finally
      //   868	876	1067	finally
      //   880	888	1067	finally
      //   892	901	1067	finally
      //   905	912	1067	finally
      //   945	954	1067	finally
      //   958	966	1067	finally
      //   970	978	1067	finally
      //   982	991	1067	finally
      //   995	1002	1067	finally
      //   218	233	1112	java/net/SocketTimeoutException
      //   218	233	1117	java/net/ConnectException
      //   237	245	1122	java/net/SocketTimeoutException
      //   249	288	1122	java/net/SocketTimeoutException
      //   292	301	1122	java/net/SocketTimeoutException
      //   305	312	1122	java/net/SocketTimeoutException
      //   321	331	1122	java/net/SocketTimeoutException
      //   335	350	1122	java/net/SocketTimeoutException
      //   354	362	1122	java/net/SocketTimeoutException
      //   371	381	1122	java/net/SocketTimeoutException
      //   385	397	1122	java/net/SocketTimeoutException
      //   401	413	1122	java/net/SocketTimeoutException
      //   417	429	1122	java/net/SocketTimeoutException
      //   433	445	1122	java/net/SocketTimeoutException
      //   449	454	1122	java/net/SocketTimeoutException
      //   463	478	1122	java/net/SocketTimeoutException
      //   482	494	1122	java/net/SocketTimeoutException
      //   498	507	1122	java/net/SocketTimeoutException
      //   511	518	1122	java/net/SocketTimeoutException
      //   522	529	1122	java/net/SocketTimeoutException
      //   533	540	1122	java/net/SocketTimeoutException
      //   544	549	1122	java/net/SocketTimeoutException
      //   553	578	1122	java/net/SocketTimeoutException
      //   588	595	1122	java/net/SocketTimeoutException
      //   599	608	1122	java/net/SocketTimeoutException
      //   612	621	1122	java/net/SocketTimeoutException
      //   625	633	1122	java/net/SocketTimeoutException
      //   637	645	1122	java/net/SocketTimeoutException
      //   649	657	1122	java/net/SocketTimeoutException
      //   669	679	1122	java/net/SocketTimeoutException
      //   683	693	1122	java/net/SocketTimeoutException
      //   697	702	1122	java/net/SocketTimeoutException
      //   706	713	1122	java/net/SocketTimeoutException
      //   237	245	1127	java/net/ConnectException
      //   249	288	1127	java/net/ConnectException
      //   292	301	1127	java/net/ConnectException
      //   305	312	1127	java/net/ConnectException
      //   321	331	1127	java/net/ConnectException
      //   335	350	1127	java/net/ConnectException
      //   354	362	1127	java/net/ConnectException
      //   371	381	1127	java/net/ConnectException
      //   385	397	1127	java/net/ConnectException
      //   401	413	1127	java/net/ConnectException
      //   417	429	1127	java/net/ConnectException
      //   433	445	1127	java/net/ConnectException
      //   449	454	1127	java/net/ConnectException
      //   463	478	1127	java/net/ConnectException
      //   482	494	1127	java/net/ConnectException
      //   498	507	1127	java/net/ConnectException
      //   511	518	1127	java/net/ConnectException
      //   522	529	1127	java/net/ConnectException
      //   533	540	1127	java/net/ConnectException
      //   544	549	1127	java/net/ConnectException
      //   553	578	1127	java/net/ConnectException
      //   588	595	1127	java/net/ConnectException
      //   599	608	1127	java/net/ConnectException
      //   612	621	1127	java/net/ConnectException
      //   625	633	1127	java/net/ConnectException
      //   637	645	1127	java/net/ConnectException
      //   649	657	1127	java/net/ConnectException
      //   669	679	1127	java/net/ConnectException
      //   683	693	1127	java/net/ConnectException
      //   697	702	1127	java/net/ConnectException
      //   706	713	1127	java/net/ConnectException
      //   723	728	1132	java/io/IOException
      //   1025	1030	1137	java/io/IOException
      //   1083	1088	1142	java/io/IOException
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\upload\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */