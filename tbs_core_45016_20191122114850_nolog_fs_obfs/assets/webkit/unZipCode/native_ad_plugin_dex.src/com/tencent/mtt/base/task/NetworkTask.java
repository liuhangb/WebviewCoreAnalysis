package com.tencent.mtt.base.task;

import com.tencent.basesupport.FLogger;
import com.tencent.common.http.IPostDataBuf;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.common.http.RequesterFactory;
import com.tencent.mtt.base.net.frame.RequestManager;
import java.util.concurrent.ExecutorService;

public class NetworkTask
  extends Task
{
  public static final String TAG = "NetworkTask";
  int jdField_a_of_type_Int = 1;
  NetworkTaskCallback jdField_a_of_type_ComTencentMttBaseTaskNetworkTask$NetworkTaskCallback = null;
  boolean jdField_a_of_type_Boolean = false;
  int jdField_b_of_type_Int = 0;
  boolean jdField_b_of_type_Boolean = false;
  
  public NetworkTask(String paramString)
  {
    this(paramString, (byte)0, null);
  }
  
  public NetworkTask(String paramString, byte paramByte)
  {
    this(paramString, paramByte, null);
  }
  
  public NetworkTask(String paramString, byte paramByte, NetworkTaskCallback paramNetworkTaskCallback)
  {
    this.mMttRequest = RequesterFactory.getMttRequestBase();
    this.mMttRequest.setUrl(paramString);
    this.mMttRequest.setMethod(paramByte);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask$NetworkTaskCallback = paramNetworkTaskCallback;
    preHandleRequest(this.mMttRequest);
  }
  
  public NetworkTask(String paramString, NetworkTaskCallback paramNetworkTaskCallback)
  {
    this(paramString, (byte)0, paramNetworkTaskCallback);
  }
  
  public NetworkTask(String paramString, byte[] paramArrayOfByte)
  {
    this(paramString, paramArrayOfByte, null);
  }
  
  public NetworkTask(String paramString, byte[] paramArrayOfByte, NetworkTaskCallback paramNetworkTaskCallback)
  {
    this.mMttRequest = RequesterFactory.getMttRequestBase();
    this.mMttRequest.setUrl(paramString);
    this.mMttRequest.setMethod((byte)1);
    this.mMttRequest.setPostData(paramArrayOfByte);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask$NetworkTaskCallback = paramNetworkTaskCallback;
    preHandleRequest(this.mMttRequest);
  }
  
  byte a(MttRequestBase paramMttRequestBase, MttResponse paramMttResponse)
  {
    if ((paramMttResponse != null) && (paramMttResponse.getStatusCode().intValue() != -1)) {
      return 2;
    }
    return 3;
  }
  
  void a(byte paramByte)
  {
    this.mRunningState = paramByte;
  }
  
  public NetworkTask addHeader(String paramString1, String paramString2)
  {
    this.mMttRequest.addHeader(paramString1, paramString2);
    return this;
  }
  
  @Deprecated
  public void addObserver(TaskObserver paramTaskObserver) {}
  
  public void cancel()
  {
    super.cancel();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("cancel [");
    localStringBuilder.append(this.mMttRequest.getUrl());
    localStringBuilder.append("]");
    FLogger.d("NetworkTask", localStringBuilder.toString());
    this.mCanceled = true;
    onResponse(null);
  }
  
  public void closeQuietly()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("closeQuietly [");
    localStringBuilder.append(this.mMttRequest.getUrl());
    localStringBuilder.append("]");
    FLogger.d("NetworkTask", localStringBuilder.toString());
    super.closeQuietly();
  }
  
  public void doRun()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doRun [");
    ((StringBuilder)localObject).append(this.mMttRequest.getUrl());
    ((StringBuilder)localObject).append("]");
    FLogger.d("NetworkTask", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isRetring: ");
    ((StringBuilder)localObject).append(isRetring());
    ((StringBuilder)localObject).append(", retry times: ");
    ((StringBuilder)localObject).append(this.jdField_b_of_type_Int);
    FLogger.d("NetworkTask", ((StringBuilder)localObject).toString());
    a((byte)1);
    if ((!this.mCanceled) && (this.jdField_b_of_type_Int <= this.jdField_a_of_type_Int))
    {
      localObject = performRequest();
      setMttResponse((MttResponse)localObject);
      a(a(this.mMttRequest, (MttResponse)localObject));
    }
    else
    {
      localObject = null;
    }
    if (this.mCanceled)
    {
      setMttResponse(null);
      this.mErrorCode = 100;
      a((byte)2);
      localObject = null;
    }
    if (isRetring())
    {
      int i = this.jdField_b_of_type_Int + 1;
      this.jdField_b_of_type_Int = i;
      if (i <= this.jdField_a_of_type_Int)
      {
        retry();
        return;
      }
    }
    if (!isPending()) {
      onResponse((MttResponse)localObject);
    }
  }
  
  @Deprecated
  public void fireObserverEvent(int paramInt)
  {
    if (5 == paramInt)
    {
      this.mErrorCode = -1;
      onResponse(null);
    }
  }
  
  protected void onResponse(final MttResponse paramMttResponse)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onResponse: ");
    localStringBuilder.append(paramMttResponse);
    FLogger.d("NetworkTask", localStringBuilder.toString());
    paramMttResponse = new Runnable()
    {
      public void run()
      {
        NetworkTask localNetworkTask;
        if (paramMttResponse != null)
        {
          localNetworkTask = NetworkTask.this;
          localNetworkTask.onTaskSuccess(localNetworkTask.mMttRequest, paramMttResponse);
        }
        else
        {
          localNetworkTask = NetworkTask.this;
          localNetworkTask.onTaskFailed(localNetworkTask.mMttRequest, NetworkTask.this.mErrorCode);
        }
        NetworkTask.this.closeQuietly();
      }
    };
    if ((this.mTaskExecutors != null) && (this.mTaskExecutors.getDeliveryExecutor() != null))
    {
      this.mTaskExecutors.getDeliveryExecutor().execute(paramMttResponse);
      return;
    }
    paramMttResponse.run();
  }
  
  protected void onTaskFailed(MttRequestBase paramMttRequestBase, int paramInt)
  {
    NetworkTaskCallback localNetworkTaskCallback = this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask$NetworkTaskCallback;
    if (localNetworkTaskCallback != null) {
      localNetworkTaskCallback.onTaskFailed(paramMttRequestBase, paramInt);
    }
  }
  
  protected void onTaskSuccess(MttRequestBase paramMttRequestBase, MttResponse paramMttResponse)
  {
    NetworkTaskCallback localNetworkTaskCallback = this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask$NetworkTaskCallback;
    if (localNetworkTaskCallback != null) {
      localNetworkTaskCallback.onTaskSuccess(paramMttRequestBase, paramMttResponse);
    }
  }
  
  /* Error */
  protected MttResponse performRequest()
  {
    // Byte code:
    //   0: new 101	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   7: astore 5
    //   9: aload 5
    //   11: ldc -30
    //   13: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 5
    //   19: aload_0
    //   20: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   23: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   26: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: pop
    //   30: aload 5
    //   32: ldc 114
    //   34: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: pop
    //   38: ldc 13
    //   40: aload 5
    //   42: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   48: iconst_m1
    //   49: istore_3
    //   50: iconst_0
    //   51: istore 4
    //   53: iconst_0
    //   54: istore_2
    //   55: aconst_null
    //   56: astore 6
    //   58: iload_2
    //   59: istore_1
    //   60: aload_0
    //   61: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   64: ifnull +12 -> 76
    //   67: iload_2
    //   68: istore_1
    //   69: aload_0
    //   70: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   73: invokevirtual 235	com/tencent/common/http/Requester:close	()V
    //   76: iload_2
    //   77: istore_1
    //   78: aload_0
    //   79: iconst_0
    //   80: invokestatic 239	com/tencent/common/http/RequesterFactory:getRequester	(I)Lcom/tencent/common/http/Requester;
    //   83: putfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   86: iload_2
    //   87: istore_1
    //   88: aload_0
    //   89: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   92: aload_0
    //   93: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   96: invokevirtual 242	com/tencent/common/http/Requester:execute	(Lcom/tencent/common/http/MttRequestBase;)Lcom/tencent/common/http/MttResponse;
    //   99: astore 5
    //   101: iload 4
    //   103: istore_1
    //   104: goto +642 -> 746
    //   107: astore 5
    //   109: goto +750 -> 859
    //   112: astore 5
    //   114: aload 5
    //   116: invokevirtual 245	java/lang/Throwable:printStackTrace	()V
    //   119: aload_0
    //   120: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   123: ifnull +26 -> 149
    //   126: aload_0
    //   127: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   130: ifnull +10 -> 140
    //   133: aload_0
    //   134: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   137: invokevirtual 248	com/tencent/common/http/Requester:abort	()V
    //   140: iconst_m1
    //   141: istore_1
    //   142: aload 6
    //   144: astore 5
    //   146: goto +600 -> 746
    //   149: iconst_m1
    //   150: istore_1
    //   151: aload 6
    //   153: astore 5
    //   155: goto +591 -> 746
    //   158: astore 5
    //   160: iconst_m1
    //   161: istore_1
    //   162: goto +697 -> 859
    //   165: astore 5
    //   167: iload_2
    //   168: istore_1
    //   169: new 101	java/lang/StringBuilder
    //   172: dup
    //   173: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   176: astore 7
    //   178: iload_2
    //   179: istore_1
    //   180: aload 7
    //   182: ldc -6
    //   184: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: iload_2
    //   189: istore_1
    //   190: aload 7
    //   192: aload_0
    //   193: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   196: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   199: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: pop
    //   203: iload_2
    //   204: istore_1
    //   205: aload 7
    //   207: ldc 114
    //   209: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: pop
    //   213: iload_2
    //   214: istore_1
    //   215: ldc 13
    //   217: aload 7
    //   219: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   222: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   225: bipush 9
    //   227: istore_2
    //   228: iload_2
    //   229: istore_1
    //   230: aload 5
    //   232: invokevirtual 251	java/lang/OutOfMemoryError:printStackTrace	()V
    //   235: iload_2
    //   236: istore_1
    //   237: aload 6
    //   239: astore 5
    //   241: aload_0
    //   242: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   245: ifnull +501 -> 746
    //   248: iload_2
    //   249: istore_1
    //   250: aload 6
    //   252: astore 5
    //   254: aload_0
    //   255: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   258: ifnull +488 -> 746
    //   261: iload_2
    //   262: istore_1
    //   263: aload_0
    //   264: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   267: invokevirtual 248	com/tencent/common/http/Requester:abort	()V
    //   270: aload 6
    //   272: astore 5
    //   274: goto +472 -> 746
    //   277: astore 5
    //   279: iload_2
    //   280: istore_1
    //   281: new 101	java/lang/StringBuilder
    //   284: dup
    //   285: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   288: astore 7
    //   290: iload_2
    //   291: istore_1
    //   292: aload 7
    //   294: ldc -3
    //   296: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   299: pop
    //   300: iload_2
    //   301: istore_1
    //   302: aload 7
    //   304: aload_0
    //   305: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   308: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   311: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: iload_2
    //   316: istore_1
    //   317: aload 7
    //   319: ldc 114
    //   321: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   324: pop
    //   325: iload_2
    //   326: istore_1
    //   327: ldc 13
    //   329: aload 7
    //   331: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   334: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   337: iconst_2
    //   338: istore_2
    //   339: iload_2
    //   340: istore_1
    //   341: aload 5
    //   343: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   346: iload_2
    //   347: istore_1
    //   348: aload 6
    //   350: astore 5
    //   352: aload_0
    //   353: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   356: ifnull +390 -> 746
    //   359: iload_2
    //   360: istore_1
    //   361: aload 6
    //   363: astore 5
    //   365: aload_0
    //   366: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   369: ifnull +377 -> 746
    //   372: iload_2
    //   373: istore_1
    //   374: goto -111 -> 263
    //   377: iload_2
    //   378: istore_1
    //   379: new 101	java/lang/StringBuilder
    //   382: dup
    //   383: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   386: astore 5
    //   388: iload_2
    //   389: istore_1
    //   390: aload 5
    //   392: ldc_w 256
    //   395: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   398: pop
    //   399: iload_2
    //   400: istore_1
    //   401: aload 5
    //   403: aload_0
    //   404: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   407: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   410: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   413: pop
    //   414: iload_2
    //   415: istore_1
    //   416: aload 5
    //   418: ldc 114
    //   420: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   423: pop
    //   424: iload_2
    //   425: istore_1
    //   426: ldc 13
    //   428: aload 5
    //   430: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   433: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   436: bipush 8
    //   438: istore_2
    //   439: iload_2
    //   440: istore_1
    //   441: aload 6
    //   443: astore 5
    //   445: aload_0
    //   446: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   449: ifnull +297 -> 746
    //   452: iload_2
    //   453: istore_1
    //   454: aload 6
    //   456: astore 5
    //   458: aload_0
    //   459: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   462: ifnull +284 -> 746
    //   465: iload_2
    //   466: istore_1
    //   467: goto -204 -> 263
    //   470: iload_2
    //   471: istore_1
    //   472: new 101	java/lang/StringBuilder
    //   475: dup
    //   476: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   479: astore 5
    //   481: iload_2
    //   482: istore_1
    //   483: aload 5
    //   485: ldc_w 258
    //   488: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   491: pop
    //   492: iload_2
    //   493: istore_1
    //   494: aload 5
    //   496: aload_0
    //   497: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   500: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   503: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   506: pop
    //   507: iload_2
    //   508: istore_1
    //   509: aload 5
    //   511: ldc 114
    //   513: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   516: pop
    //   517: iload_2
    //   518: istore_1
    //   519: ldc 13
    //   521: aload 5
    //   523: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   526: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   529: iconst_4
    //   530: istore_2
    //   531: iload_2
    //   532: istore_1
    //   533: aload 6
    //   535: astore 5
    //   537: aload_0
    //   538: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   541: ifnull +205 -> 746
    //   544: iload_2
    //   545: istore_1
    //   546: aload 6
    //   548: astore 5
    //   550: aload_0
    //   551: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   554: ifnull +192 -> 746
    //   557: iload_2
    //   558: istore_1
    //   559: goto -296 -> 263
    //   562: iload_2
    //   563: istore_1
    //   564: new 101	java/lang/StringBuilder
    //   567: dup
    //   568: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   571: astore 5
    //   573: iload_2
    //   574: istore_1
    //   575: aload 5
    //   577: ldc_w 260
    //   580: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   583: pop
    //   584: iload_2
    //   585: istore_1
    //   586: aload 5
    //   588: aload_0
    //   589: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   592: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   595: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   598: pop
    //   599: iload_2
    //   600: istore_1
    //   601: aload 5
    //   603: ldc 114
    //   605: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   608: pop
    //   609: iload_2
    //   610: istore_1
    //   611: ldc 13
    //   613: aload 5
    //   615: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   618: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   621: iconst_5
    //   622: istore_2
    //   623: iload_2
    //   624: istore_1
    //   625: aload 6
    //   627: astore 5
    //   629: aload_0
    //   630: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   633: ifnull +113 -> 746
    //   636: iload_2
    //   637: istore_1
    //   638: aload 6
    //   640: astore 5
    //   642: aload_0
    //   643: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   646: ifnull +100 -> 746
    //   649: iload_2
    //   650: istore_1
    //   651: goto -388 -> 263
    //   654: iload_2
    //   655: istore_1
    //   656: new 101	java/lang/StringBuilder
    //   659: dup
    //   660: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   663: astore 5
    //   665: iload_2
    //   666: istore_1
    //   667: aload 5
    //   669: ldc_w 262
    //   672: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   675: pop
    //   676: iload_2
    //   677: istore_1
    //   678: aload 5
    //   680: aload_0
    //   681: getfield 48	com/tencent/mtt/base/task/NetworkTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   684: invokevirtual 112	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   687: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   690: pop
    //   691: iload_2
    //   692: istore_1
    //   693: aload 5
    //   695: ldc 114
    //   697: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   700: pop
    //   701: iload_2
    //   702: istore_1
    //   703: ldc 13
    //   705: aload 5
    //   707: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   710: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   713: iconst_3
    //   714: istore_2
    //   715: iload_2
    //   716: istore_1
    //   717: aload 6
    //   719: astore 5
    //   721: aload_0
    //   722: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   725: ifnull +21 -> 746
    //   728: iload_2
    //   729: istore_1
    //   730: aload 6
    //   732: astore 5
    //   734: aload_0
    //   735: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   738: ifnull +8 -> 746
    //   741: iload_2
    //   742: istore_1
    //   743: goto -480 -> 263
    //   746: aload 5
    //   748: ifnonnull +11 -> 759
    //   751: getstatic 266	com/tencent/common/http/MttResponse:UNKNOWN_STATUS	Ljava/lang/Integer;
    //   754: astore 6
    //   756: goto +10 -> 766
    //   759: aload 5
    //   761: invokevirtual 77	com/tencent/common/http/MttResponse:getStatusCode	()Ljava/lang/Integer;
    //   764: astore 6
    //   766: iload_1
    //   767: ifne +13 -> 780
    //   770: aload 6
    //   772: ifnonnull +8 -> 780
    //   775: iload_3
    //   776: istore_1
    //   777: goto +3 -> 780
    //   780: aload_0
    //   781: iload_1
    //   782: putfield 165	com/tencent/mtt/base/task/NetworkTask:mErrorCode	I
    //   785: new 101	java/lang/StringBuilder
    //   788: dup
    //   789: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   792: astore 7
    //   794: aload 7
    //   796: ldc_w 268
    //   799: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   802: pop
    //   803: aload 7
    //   805: iload_1
    //   806: invokevirtual 151	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   809: pop
    //   810: ldc 13
    //   812: aload 7
    //   814: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   817: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   820: new 101	java/lang/StringBuilder
    //   823: dup
    //   824: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   827: astore 7
    //   829: aload 7
    //   831: ldc_w 270
    //   834: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   837: pop
    //   838: aload 7
    //   840: aload 6
    //   842: invokevirtual 178	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   845: pop
    //   846: ldc 13
    //   848: aload 7
    //   850: invokevirtual 117	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   853: invokestatic 122	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   856: aload 5
    //   858: areturn
    //   859: iload_1
    //   860: ifeq +24 -> 884
    //   863: aload_0
    //   864: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   867: ifnull +17 -> 884
    //   870: aload_0
    //   871: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   874: ifnull +10 -> 884
    //   877: aload_0
    //   878: getfield 230	com/tencent/mtt/base/task/NetworkTask:mRequester	Lcom/tencent/common/http/Requester;
    //   881: invokevirtual 248	com/tencent/common/http/Requester:abort	()V
    //   884: aload 5
    //   886: athrow
    //   887: astore 5
    //   889: goto -235 -> 654
    //   892: astore 5
    //   894: goto -332 -> 562
    //   897: astore 5
    //   899: goto -429 -> 470
    //   902: astore 5
    //   904: goto -527 -> 377
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	907	0	this	NetworkTask
    //   59	801	1	i	int
    //   54	688	2	j	int
    //   49	727	3	k	int
    //   51	51	4	m	int
    //   7	93	5	localObject1	Object
    //   107	1	5	localObject2	Object
    //   112	3	5	localThrowable	Throwable
    //   144	10	5	localObject3	Object
    //   158	1	5	localObject4	Object
    //   165	66	5	localOutOfMemoryError	OutOfMemoryError
    //   239	34	5	localObject5	Object
    //   277	65	5	localIOException	java.io.IOException
    //   350	535	5	localObject6	Object
    //   887	1	5	localUnknownHostException	java.net.UnknownHostException
    //   892	1	5	localSocketTimeoutException	java.net.SocketTimeoutException
    //   897	1	5	localSocketException	java.net.SocketException
    //   902	1	5	localFileNotFoundException	java.io.FileNotFoundException
    //   56	785	6	localInteger	Integer
    //   176	673	7	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   60	67	107	finally
    //   69	76	107	finally
    //   78	86	107	finally
    //   88	101	107	finally
    //   169	178	107	finally
    //   180	188	107	finally
    //   190	203	107	finally
    //   205	213	107	finally
    //   215	225	107	finally
    //   230	235	107	finally
    //   281	290	107	finally
    //   292	300	107	finally
    //   302	315	107	finally
    //   317	325	107	finally
    //   327	337	107	finally
    //   341	346	107	finally
    //   379	388	107	finally
    //   390	399	107	finally
    //   401	414	107	finally
    //   416	424	107	finally
    //   426	436	107	finally
    //   472	481	107	finally
    //   483	492	107	finally
    //   494	507	107	finally
    //   509	517	107	finally
    //   519	529	107	finally
    //   564	573	107	finally
    //   575	584	107	finally
    //   586	599	107	finally
    //   601	609	107	finally
    //   611	621	107	finally
    //   656	665	107	finally
    //   667	676	107	finally
    //   678	691	107	finally
    //   693	701	107	finally
    //   703	713	107	finally
    //   60	67	112	java/lang/Throwable
    //   69	76	112	java/lang/Throwable
    //   78	86	112	java/lang/Throwable
    //   88	101	112	java/lang/Throwable
    //   114	119	158	finally
    //   60	67	165	java/lang/OutOfMemoryError
    //   69	76	165	java/lang/OutOfMemoryError
    //   78	86	165	java/lang/OutOfMemoryError
    //   88	101	165	java/lang/OutOfMemoryError
    //   60	67	277	java/io/IOException
    //   69	76	277	java/io/IOException
    //   78	86	277	java/io/IOException
    //   88	101	277	java/io/IOException
    //   60	67	887	java/net/UnknownHostException
    //   69	76	887	java/net/UnknownHostException
    //   78	86	887	java/net/UnknownHostException
    //   88	101	887	java/net/UnknownHostException
    //   60	67	892	java/net/SocketTimeoutException
    //   69	76	892	java/net/SocketTimeoutException
    //   78	86	892	java/net/SocketTimeoutException
    //   88	101	892	java/net/SocketTimeoutException
    //   60	67	897	java/net/SocketException
    //   69	76	897	java/net/SocketException
    //   78	86	897	java/net/SocketException
    //   88	101	897	java/net/SocketException
    //   60	67	902	java/io/FileNotFoundException
    //   69	76	902	java/io/FileNotFoundException
    //   78	86	902	java/io/FileNotFoundException
    //   88	101	902	java/io/FileNotFoundException
  }
  
  protected void preHandleRequest(MttRequestBase paramMttRequestBase) {}
  
  protected void resetStatus()
  {
    super.resetStatus();
  }
  
  public NetworkTask setCallback(NetworkTaskCallback paramNetworkTaskCallback)
  {
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask$NetworkTaskCallback = paramNetworkTaskCallback;
    return this;
  }
  
  public NetworkTask setConnectTimeout(int paramInt)
  {
    this.mMttRequest.setConnectTimeout(paramInt);
    return this;
  }
  
  public void setInstanceFollowRedirects(boolean paramBoolean)
  {
    this.mMttRequest.setInstanceFollowRedirects(paramBoolean);
  }
  
  public void setPostData(IPostDataBuf paramIPostDataBuf)
  {
    this.mMttRequest.setPostData(paramIPostDataBuf);
  }
  
  public void setPostData(byte[] paramArrayOfByte)
  {
    this.mMttRequest.setPostData(paramArrayOfByte);
  }
  
  public NetworkTask setReadTimeout(int paramInt)
  {
    this.mMttRequest.setReadTimeout(paramInt);
    return this;
  }
  
  public NetworkTask setRequestProperty(String paramString1, String paramString2)
  {
    this.mMttRequest.setRequestProperty(paramString1, paramString2);
    return this;
  }
  
  public void setTaskType(byte paramByte)
  {
    this.mMttRequest.setRequestType(paramByte);
  }
  
  public void setUseCaches(boolean paramBoolean)
  {
    this.mMttRequest.setUseCaches(paramBoolean);
  }
  
  public void start()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    RequestManager.execute(this);
  }
  
  public static abstract interface NetworkTaskCallback
  {
    public abstract void onTaskFailed(MttRequestBase paramMttRequestBase, int paramInt);
    
    public abstract void onTaskSuccess(MttRequestBase paramMttRequestBase, MttResponse paramMttResponse);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\NetworkTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */