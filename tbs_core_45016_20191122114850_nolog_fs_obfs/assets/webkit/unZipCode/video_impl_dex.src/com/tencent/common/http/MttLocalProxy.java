package com.tencent.common.http;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.base.task.NetworkTask;
import com.tencent.mtt.base.task.NetworkTask.NetworkTaskCallback;
import com.tencent.tbs.common.internal.service.StatServerHolder;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MttLocalProxy
  extends Thread
{
  private static MttLocalProxy jdField_a_of_type_ComTencentCommonHttpMttLocalProxy;
  private int jdField_a_of_type_Int;
  private Thread jdField_a_of_type_JavaLangThread;
  private ServerSocket jdField_a_of_type_JavaNetServerSocket;
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  
  private int a(MttRequestBase paramMttRequestBase)
  {
    paramMttRequestBase = paramMttRequestBase.getHeader("Range");
    if (!TextUtils.isEmpty(paramMttRequestBase)) {
      return Integer.valueOf(paramMttRequestBase.substring(paramMttRequestBase.indexOf("bytes=") + 6, paramMttRequestBase.indexOf("-"))).intValue();
    }
    return 0;
  }
  
  private int a(MttResponse paramMttResponse)
  {
    String str = paramMttResponse.getHeaderField("Content-Range");
    if (str != null) {
      return Integer.valueOf(str.substring(str.indexOf("-") + 1, str.indexOf("/"))).intValue() + 1;
    }
    return Integer.valueOf(paramMttResponse.getHeaderField("Content-Length")).intValue();
  }
  
  private String a(String[] paramArrayOfString)
  {
    try
    {
      paramArrayOfString = new StringTokenizer(paramArrayOfString[0]);
      paramArrayOfString.nextToken();
      paramArrayOfString = paramArrayOfString.nextToken();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" URL-> ");
      localStringBuilder.append(paramArrayOfString);
      localStringBuilder.toString();
      paramArrayOfString = paramArrayOfString.substring(1);
      return paramArrayOfString;
    }
    catch (Exception paramArrayOfString)
    {
      paramArrayOfString.printStackTrace();
    }
    return null;
  }
  
  public static MttLocalProxy getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentCommonHttpMttLocalProxy == null) {
        jdField_a_of_type_ComTencentCommonHttpMttLocalProxy = new MttLocalProxy();
      }
      return jdField_a_of_type_ComTencentCommonHttpMttLocalProxy;
    }
    finally {}
  }
  
  public String getProxyURL(String paramString)
  {
    if (!this.b) {
      return paramString;
    }
    if ((!paramString.startsWith("http://")) && (!paramString.startsWith("https://"))) {
      return paramString;
    }
    return String.format("http://127.0.0.1:%d/%s", new Object[] { Integer.valueOf(this.jdField_a_of_type_Int), paramString });
  }
  
  public void init()
    throws Exception
  {
    this.jdField_a_of_type_JavaNetServerSocket = new ServerSocket(this.jdField_a_of_type_Int, 1, InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }));
    this.jdField_a_of_type_Int = this.jdField_a_of_type_JavaNetServerSocket.getLocalPort();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("port ");
    localStringBuilder.append(this.jdField_a_of_type_Int);
    localStringBuilder.append(" obtained");
    localStringBuilder.toString();
  }
  
  public void initProxyIfNeeded()
  {
    if (!this.b) {
      try
      {
        boolean bool = this.b;
        if (!bool) {
          try
          {
            init();
            startProxy();
            this.b = true;
          }
          catch (Exception localException1)
          {
            HashMap localHashMap = new HashMap();
            localHashMap.put("ERROR_POINT", String.valueOf(2));
            localHashMap.put("STACK", Log.getStackTraceString(localException1));
            StatServerHolder.statWithBeacon("MTT_LOCAL_PROXY_EXCEPTION", localHashMap);
            try
            {
              init();
              startProxy();
              this.b = true;
            }
            catch (Exception localException2)
            {
              this.b = false;
              localHashMap.put("ERROR_POINT1", String.valueOf(1));
              localHashMap.put("STACK1", Log.getStackTraceString(localException2));
              StatServerHolder.statWithBeacon("MTT_LOCAL_PROXY_EXCEPTION", localHashMap);
            }
          }
        }
        return;
      }
      finally {}
    }
  }
  
  public boolean isStarted()
  {
    return this.b;
  }
  
  public void run()
  {
    for (;;)
    {
      int i;
      try
      {
        this.jdField_a_of_type_Boolean = true;
        boolean bool = this.jdField_a_of_type_Boolean;
        final Object localObject2;
        if (bool)
        {
          try
          {
            long l = System.currentTimeMillis();
            this.jdField_a_of_type_JavaNetServerSocket.getReceiveBufferSize();
            localObject2 = this.jdField_a_of_type_JavaNetServerSocket.accept();
            Object localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("looping not skip,cost");
            ((StringBuilder)localObject1).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject1).toString();
            if (localObject2 == null)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("looping skip,cost");
              ((StringBuilder)localObject1).append(l - System.currentTimeMillis());
              ((StringBuilder)localObject1).toString();
              continue;
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("client connected");
            ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaNetServerSocket);
            ((StringBuilder)localObject1).toString();
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("proxy time cost0:");
            ((StringBuilder)localObject1).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject1).toString();
            localObject1 = NetUtils.getRequestPartsFromSocket((Socket)localObject2);
            Object localObject3 = a((String[])localObject1);
            if (TextUtils.isEmpty((CharSequence)localObject3)) {
              continue;
            }
            Object localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("client connected,url=");
            ((StringBuilder)localObject4).append((String)localObject3);
            ((StringBuilder)localObject4).toString();
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("proxy time cost1:");
            ((StringBuilder)localObject4).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject4).toString();
            localObject2 = new NetworkTask((String)localObject3, new NetworkTask.NetworkTaskCallback()
            {
              public void onTaskFailed(MttRequestBase paramAnonymousMttRequestBase, int paramAnonymousInt)
              {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("onTaskFailed,mttRequestBase=");
                localStringBuilder.append(paramAnonymousMttRequestBase);
                localStringBuilder.append("i=");
                localStringBuilder.append(paramAnonymousInt);
                localStringBuilder.toString();
              }
              
              /* Error */
              public void onTaskSuccess(MttRequestBase paramAnonymousMttRequestBase, MttResponse paramAnonymousMttResponse)
              {
                // Byte code:
                //   0: new 27	java/lang/StringBuilder
                //   3: dup
                //   4: invokespecial 28	java/lang/StringBuilder:<init>	()V
                //   7: astore 5
                //   9: aload 5
                //   11: ldc 54
                //   13: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   16: pop
                //   17: aload 5
                //   19: aload_1
                //   20: invokevirtual 37	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
                //   23: pop
                //   24: aload 5
                //   26: ldc 56
                //   28: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   31: pop
                //   32: aload 5
                //   34: aload_2
                //   35: invokevirtual 62	com/tencent/common/http/MttResponse:getStatusCode	()Ljava/lang/Integer;
                //   38: invokevirtual 37	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
                //   41: pop
                //   42: aload 5
                //   44: ldc 64
                //   46: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   49: pop
                //   50: aload 5
                //   52: aload_2
                //   53: invokevirtual 67	com/tencent/common/http/MttResponse:getLocation	()Ljava/lang/String;
                //   56: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   59: pop
                //   60: aload 5
                //   62: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
                //   65: pop
                //   66: aload_0
                //   67: getfield 18	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_ComTencentCommonHttpMttLocalProxy	Lcom/tencent/common/http/MttLocalProxy;
                //   70: aload_1
                //   71: invokestatic 70	com/tencent/common/http/MttLocalProxy:a	(Lcom/tencent/common/http/MttLocalProxy;Lcom/tencent/common/http/MttRequestBase;)I
                //   74: istore_3
                //   75: aload_0
                //   76: getfield 18	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_ComTencentCommonHttpMttLocalProxy	Lcom/tencent/common/http/MttLocalProxy;
                //   79: aload_2
                //   80: invokestatic 73	com/tencent/common/http/MttLocalProxy:a	(Lcom/tencent/common/http/MttLocalProxy;Lcom/tencent/common/http/MttResponse;)I
                //   83: istore 4
                //   85: iload_3
                //   86: iload 4
                //   88: iconst_1
                //   89: isub
                //   90: iload 4
                //   92: invokestatic 79	com/tencent/common/http/NetUtils:genResponseHeader	(III)Ljava/lang/String;
                //   95: invokevirtual 82	java/lang/String:toString	()Ljava/lang/String;
                //   98: invokevirtual 86	java/lang/String:getBytes	()[B
                //   101: astore_1
                //   102: aload_0
                //   103: getfield 20	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_JavaNetSocket	Ljava/net/Socket;
                //   106: invokevirtual 92	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
                //   109: aload_1
                //   110: iconst_0
                //   111: aload_1
                //   112: arraylength
                //   113: invokevirtual 98	java/io/OutputStream:write	([BII)V
                //   116: aload_2
                //   117: invokevirtual 102	com/tencent/common/http/MttResponse:getInputStream	()Lcom/tencent/common/http/MttInputStream;
                //   120: astore_1
                //   121: sipush 1024
                //   124: newarray <illegal type>
                //   126: astore_2
                //   127: aload_1
                //   128: aload_2
                //   129: invokevirtual 108	java/io/InputStream:read	([B)I
                //   132: istore_3
                //   133: iload_3
                //   134: iconst_m1
                //   135: if_icmpeq +79 -> 214
                //   138: new 27	java/lang/StringBuilder
                //   141: dup
                //   142: invokespecial 28	java/lang/StringBuilder:<init>	()V
                //   145: astore 5
                //   147: aload 5
                //   149: ldc 110
                //   151: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   154: pop
                //   155: aload 5
                //   157: iload_3
                //   158: invokevirtual 42	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
                //   161: pop
                //   162: aload 5
                //   164: aload_0
                //   165: invokevirtual 37	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
                //   168: pop
                //   169: aload 5
                //   171: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
                //   174: pop
                //   175: iload_3
                //   176: newarray <illegal type>
                //   178: astore 5
                //   180: aload_2
                //   181: iconst_0
                //   182: aload 5
                //   184: iconst_0
                //   185: iload_3
                //   186: invokestatic 116	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
                //   189: aload_0
                //   190: getfield 20	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_JavaNetSocket	Ljava/net/Socket;
                //   193: invokevirtual 92	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
                //   196: aload 5
                //   198: invokevirtual 119	java/io/OutputStream:write	([B)V
                //   201: aload_0
                //   202: getfield 20	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_JavaNetSocket	Ljava/net/Socket;
                //   205: invokevirtual 92	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
                //   208: invokevirtual 122	java/io/OutputStream:flush	()V
                //   211: goto -84 -> 127
                //   214: ldc 124
                //   216: ldc 126
                //   218: invokestatic 132	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
                //   221: pop
                //   222: aload_0
                //   223: getfield 20	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_JavaNetSocket	Ljava/net/Socket;
                //   226: invokevirtual 135	java/net/Socket:close	()V
                //   229: return
                //   230: astore_1
                //   231: goto +30 -> 261
                //   234: astore_1
                //   235: aload_1
                //   236: invokevirtual 138	java/lang/Exception:printStackTrace	()V
                //   239: ldc 124
                //   241: ldc 126
                //   243: invokestatic 132	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
                //   246: pop
                //   247: aload_0
                //   248: getfield 20	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_JavaNetSocket	Ljava/net/Socket;
                //   251: invokevirtual 135	java/net/Socket:close	()V
                //   254: return
                //   255: astore_1
                //   256: aload_1
                //   257: invokevirtual 139	java/io/IOException:printStackTrace	()V
                //   260: return
                //   261: ldc 124
                //   263: ldc 126
                //   265: invokestatic 132	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
                //   268: pop
                //   269: aload_0
                //   270: getfield 20	com/tencent/common/http/MttLocalProxy$1:jdField_a_of_type_JavaNetSocket	Ljava/net/Socket;
                //   273: invokevirtual 135	java/net/Socket:close	()V
                //   276: goto +8 -> 284
                //   279: astore_2
                //   280: aload_2
                //   281: invokevirtual 139	java/io/IOException:printStackTrace	()V
                //   284: aload_1
                //   285: athrow
                // Local variable table:
                //   start	length	slot	name	signature
                //   0	286	0	this	1
                //   0	286	1	paramAnonymousMttRequestBase	MttRequestBase
                //   0	286	2	paramAnonymousMttResponse	MttResponse
                //   74	112	3	i	int
                //   83	8	4	j	int
                //   7	190	5	localObject	Object
                // Exception table:
                //   from	to	target	type
                //   0	127	230	finally
                //   127	133	230	finally
                //   138	211	230	finally
                //   235	239	230	finally
                //   0	127	234	java/lang/Exception
                //   127	133	234	java/lang/Exception
                //   138	211	234	java/lang/Exception
                //   214	229	255	java/io/IOException
                //   239	254	255	java/io/IOException
                //   261	276	279	java/io/IOException
              }
            });
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("proxy time cost2:");
            ((StringBuilder)localObject3).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject3).toString();
            i = 1;
            if (i < localObject1.length)
            {
              int j = localObject1[i].indexOf(":");
              localObject3 = localObject1[i].substring(0, j).trim();
              localObject4 = localObject1[i].substring(j + 1).trim();
              if (((String)localObject3).equals("Host")) {
                break label715;
              }
              ((NetworkTask)localObject2).addHeader((String)localObject3, (String)localObject4);
              break label715;
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("proxy time cost3:");
            ((StringBuilder)localObject1).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject1).toString();
            ((NetworkTask)localObject2).setInstanceFollowRedirects(true);
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("task0,getHeader is");
            ((StringBuilder)localObject1).append(((NetworkTask)localObject2).getMttRequest().getHeaders());
            ((StringBuilder)localObject1).toString();
            if (((NetworkTask)localObject2).getMttRequest().getHeader("Range") == null) {
              ((NetworkTask)localObject2).addHeader("Range", "bytes=0-");
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("task,getHeader is");
            ((StringBuilder)localObject1).append(((NetworkTask)localObject2).getMttRequest().getHeaders());
            ((StringBuilder)localObject1).toString();
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("proxy time cost4:");
            ((StringBuilder)localObject1).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject1).toString();
            ((NetworkTask)localObject2).start();
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("proxy time cost:");
            ((StringBuilder)localObject1).append(l - System.currentTimeMillis());
            ((StringBuilder)localObject1).toString();
          }
          catch (IOException localIOException) {}catch (SocketTimeoutException localSocketTimeoutException)
          {
            continue;
          }
          Log.e("local_proxy", "Error connecting to client", localSocketTimeoutException);
          continue;
          Log.d("local_proxy", "SocketTimeoutException", localSocketTimeoutException);
          continue;
        }
        return;
      }
      catch (Exception localException)
      {
        this.b = false;
        localObject2 = new HashMap();
        ((HashMap)localObject2).put("ERROR_POINT", String.valueOf(1));
        ((HashMap)localObject2).put("STACK", Log.getStackTraceString(localException));
        StatServerHolder.statWithBeacon("MTT_LOCAL_PROXY_EXCEPTION", (HashMap)localObject2);
      }
      label715:
      i += 1;
    }
  }
  
  public void startProxy()
  {
    this.jdField_a_of_type_JavaLangThread = new Thread(this, "MTT_Local_Proxy");
    this.jdField_a_of_type_JavaLangThread.start();
  }
  
  public void stopProxy()
  {
    if (this.jdField_a_of_type_JavaLangThread != null)
    {
      this.jdField_a_of_type_Boolean = false;
      ServerSocket localServerSocket = this.jdField_a_of_type_JavaNetServerSocket;
      if (localServerSocket != null) {
        try
        {
          localServerSocket.close();
          return;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
      return;
    }
    throw new IllegalStateException("Cannot stop proxy; it has not been started.");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttLocalProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */