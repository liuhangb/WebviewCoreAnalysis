package com.tencent.tbs.common.task;

public class WalledGardenDetectTask
  extends Task
{
  private static final String TAG = "TBSNetworkDetector";
  public static final int TYPE_WIFI_CONN = 1;
  public static final int TYPE_WIFI_LOGIN = 2;
  public static final int TYPE_WIFI_RECONN = 3;
  static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 10000;
  public static final String WALLED_GARDEN_URL = "http://dnet.mb.qq.com/rsp204";
  boolean mNetworkConnectSuccessed = false;
  int mType = 1;
  
  /* Error */
  private void tryWalledGardenConnection()
  {
    // Byte code:
    //   0: ldc 8
    //   2: ldc 37
    //   4: invokestatic 43	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: new 45	java/net/URL
    //   10: dup
    //   11: ldc 20
    //   13: invokespecial 48	java/net/URL:<init>	(Ljava/lang/String;)V
    //   16: invokevirtual 52	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   19: checkcast 54	java/net/HttpURLConnection
    //   22: astore_2
    //   23: iconst_0
    //   24: istore_1
    //   25: aload_2
    //   26: astore_3
    //   27: aload_2
    //   28: iconst_0
    //   29: invokevirtual 58	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   32: aload_2
    //   33: astore_3
    //   34: aload_2
    //   35: sipush 10000
    //   38: invokevirtual 62	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   41: aload_2
    //   42: astore_3
    //   43: aload_2
    //   44: sipush 10000
    //   47: invokevirtual 65	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   50: aload_2
    //   51: astore_3
    //   52: aload_2
    //   53: iconst_0
    //   54: invokevirtual 68	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   57: aload_2
    //   58: astore_3
    //   59: aload_2
    //   60: invokevirtual 72	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   63: pop
    //   64: aload_2
    //   65: astore_3
    //   66: aload_2
    //   67: invokevirtual 76	java/net/HttpURLConnection:getResponseCode	()I
    //   70: sipush 204
    //   73: if_icmpne +5 -> 78
    //   76: iconst_1
    //   77: istore_1
    //   78: aload_2
    //   79: astore_3
    //   80: aload_0
    //   81: iload_1
    //   82: putfield 29	com/tencent/tbs/common/task/WalledGardenDetectTask:mNetworkConnectSuccessed	Z
    //   85: aload_2
    //   86: astore_3
    //   87: ldc 8
    //   89: ldc 78
    //   91: invokestatic 43	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: aload_2
    //   95: astore_3
    //   96: aload_0
    //   97: iconst_3
    //   98: putfield 82	com/tencent/tbs/common/task/WalledGardenDetectTask:mStatus	B
    //   101: aload_2
    //   102: astore_3
    //   103: aload_0
    //   104: invokevirtual 85	com/tencent/tbs/common/task/WalledGardenDetectTask:fireObserverEvent	()V
    //   107: aload_2
    //   108: ifnull +95 -> 203
    //   111: goto +88 -> 199
    //   114: astore 4
    //   116: goto +13 -> 129
    //   119: astore_2
    //   120: aconst_null
    //   121: astore_3
    //   122: goto +83 -> 205
    //   125: astore 4
    //   127: aconst_null
    //   128: astore_2
    //   129: aload_2
    //   130: astore_3
    //   131: new 87	java/lang/StringBuilder
    //   134: dup
    //   135: invokespecial 88	java/lang/StringBuilder:<init>	()V
    //   138: astore 5
    //   140: aload_2
    //   141: astore_3
    //   142: aload 5
    //   144: ldc 90
    //   146: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: pop
    //   150: aload_2
    //   151: astore_3
    //   152: aload 5
    //   154: aload 4
    //   156: invokevirtual 98	java/lang/Throwable:toString	()Ljava/lang/String;
    //   159: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: aload_2
    //   164: astore_3
    //   165: ldc 8
    //   167: aload 5
    //   169: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: invokestatic 43	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   175: aload_2
    //   176: astore_3
    //   177: aload 4
    //   179: invokevirtual 102	java/lang/Throwable:printStackTrace	()V
    //   182: aload_2
    //   183: astore_3
    //   184: aload_0
    //   185: iconst_5
    //   186: putfield 82	com/tencent/tbs/common/task/WalledGardenDetectTask:mStatus	B
    //   189: aload_2
    //   190: astore_3
    //   191: aload_0
    //   192: invokevirtual 85	com/tencent/tbs/common/task/WalledGardenDetectTask:fireObserverEvent	()V
    //   195: aload_2
    //   196: ifnull +7 -> 203
    //   199: aload_2
    //   200: invokevirtual 105	java/net/HttpURLConnection:disconnect	()V
    //   203: return
    //   204: astore_2
    //   205: aload_3
    //   206: ifnull +7 -> 213
    //   209: aload_3
    //   210: invokevirtual 105	java/net/HttpURLConnection:disconnect	()V
    //   213: aload_2
    //   214: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	215	0	this	WalledGardenDetectTask
    //   24	58	1	bool	boolean
    //   22	86	2	localHttpURLConnection	java.net.HttpURLConnection
    //   119	1	2	localObject1	Object
    //   128	72	2	localObject2	Object
    //   204	10	2	localObject3	Object
    //   26	184	3	localObject4	Object
    //   114	1	4	localThrowable1	Throwable
    //   125	53	4	localThrowable2	Throwable
    //   138	30	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   27	32	114	java/lang/Throwable
    //   34	41	114	java/lang/Throwable
    //   43	50	114	java/lang/Throwable
    //   52	57	114	java/lang/Throwable
    //   59	64	114	java/lang/Throwable
    //   66	76	114	java/lang/Throwable
    //   80	85	114	java/lang/Throwable
    //   87	94	114	java/lang/Throwable
    //   96	101	114	java/lang/Throwable
    //   103	107	114	java/lang/Throwable
    //   7	23	119	finally
    //   7	23	125	java/lang/Throwable
    //   27	32	204	finally
    //   34	41	204	finally
    //   43	50	204	finally
    //   52	57	204	finally
    //   59	64	204	finally
    //   66	76	204	finally
    //   80	85	204	finally
    //   87	94	204	finally
    //   96	101	204	finally
    //   103	107	204	finally
    //   131	140	204	finally
    //   142	150	204	finally
    //   152	163	204	finally
    //   165	175	204	finally
    //   177	182	204	finally
    //   184	189	204	finally
    //   191	195	204	finally
  }
  
  public void cancel() {}
  
  public int getType()
  {
    return this.mType;
  }
  
  public boolean isSuccess()
  {
    return this.mNetworkConnectSuccessed;
  }
  
  public void run()
  {
    tryWalledGardenConnection();
  }
  
  public void setType(int paramInt)
  {
    this.mType = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\task\WalledGardenDetectTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */