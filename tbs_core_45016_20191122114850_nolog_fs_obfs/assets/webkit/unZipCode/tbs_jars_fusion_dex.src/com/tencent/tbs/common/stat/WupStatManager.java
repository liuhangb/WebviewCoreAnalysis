package com.tencent.tbs.common.stat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.MultiWUPRequestBase;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.wup.MultiWUPRequest;
import com.tencent.tbs.common.wup.WUPRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WupStatManager
  implements IWUPRequestCallBack
{
  private static final String FILE_WUP_STAT_REQ = "wup_stat_req_file_ui";
  private static final int MAGIC_NUMBER = 254;
  private static int MAX_CACHE_WUP_SIZE = 25;
  private static final int MAX_STAT_OPER_TIME = 20;
  public static final String TAG = "WUPStatManagerUI";
  private static int sRequestId = (int)(System.currentTimeMillis() % 5000L + 10000L);
  boolean mCurrentDataChanged = false;
  byte[] mCurrentLock = new byte[0];
  WupStatReqWrapper mCurrentRequests = null;
  boolean mHasLoaded = false;
  boolean mIsLoading = false;
  byte[] mLoadLock = new byte[0];
  byte[] mPartialLock = new byte[0];
  WupStatReqWrapper mPartialRequests = null;
  int mStatOperationCount = 0;
  WupStatWorkerThread mWorkerThrd = null;
  
  private WupStatManager()
  {
    synchronized (this.mCurrentLock)
    {
      this.mCurrentRequests = new WupStatReqWrapper();
      loadFailList();
      return;
    }
  }
  
  private void addToCurrentList(WUPRequest paramWUPRequest)
  {
    if (paramWUPRequest == null) {
      return;
    }
    synchronized (this.mCurrentLock)
    {
      if (this.mCurrentRequests == null) {
        this.mCurrentRequests = new WupStatReqWrapper();
      }
      StringBuilder localStringBuilder;
      if (this.mCurrentRequests.addData(paramWUPRequest))
      {
        this.mCurrentDataChanged = true;
        incOperationCount();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("addToCurrentList: add request to pending list (size = ");
        localStringBuilder.append(this.mCurrentRequests.getSize());
        localStringBuilder.append(") , request funcName = ");
        localStringBuilder.append(paramWUPRequest.getFuncName());
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("addToCurrentList: request already exists in pending list, request funcName = ");
        localStringBuilder.append(paramWUPRequest.getFuncName());
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
      }
      return;
    }
  }
  
  private void deletePartialStatReq(int paramInt)
  {
    if (paramInt < 0) {
      return;
    }
    ??? = new StringBuilder();
    ((StringBuilder)???).append("deletePartialStatReq: deletePartialStatReq called, id=");
    ((StringBuilder)???).append(paramInt);
    LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
    synchronized (this.mPartialLock)
    {
      if (this.mPartialRequests == null) {
        return;
      }
      StringBuilder localStringBuilder;
      if (this.mPartialRequests.removeDataById(paramInt))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("deletePartialStatReq: removeDataById returns true, id=");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", curr size=");
        localStringBuilder.append(this.mPartialRequests.getSize());
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
        savePartialFailList();
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("deletePartialStatReq: removeDataById returns false, id=");
        localStringBuilder.append(paramInt);
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
      }
      return;
    }
  }
  
  /* Error */
  private void doSaveFailReqs(WupStatReqWrapper paramWupStatReqWrapper, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: iload_2
    //   6: iconst_1
    //   7: ixor
    //   8: invokestatic 185	com/tencent/tbs/common/stat/WupStatManager:getStatWUPRequestFile	(Z)Ljava/io/File;
    //   11: astore 6
    //   13: aload 6
    //   15: ifnull +262 -> 277
    //   18: aconst_null
    //   19: astore 5
    //   21: aconst_null
    //   22: astore 4
    //   24: aload 4
    //   26: astore_3
    //   27: aload 6
    //   29: invokevirtual 191	java/io/File:exists	()Z
    //   32: ifne +12 -> 44
    //   35: aload 4
    //   37: astore_3
    //   38: aload 6
    //   40: invokevirtual 194	java/io/File:createNewFile	()Z
    //   43: pop
    //   44: aload 4
    //   46: astore_3
    //   47: new 196	java/io/DataOutputStream
    //   50: dup
    //   51: aload 6
    //   53: invokestatic 202	com/tencent/common/utils/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   56: invokespecial 205	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   59: astore 4
    //   61: aload 4
    //   63: sipush 254
    //   66: invokevirtual 208	java/io/DataOutputStream:writeInt	(I)V
    //   69: aload 4
    //   71: aload_1
    //   72: invokevirtual 140	com/tencent/tbs/common/stat/WupStatReqWrapper:getSize	()I
    //   75: invokevirtual 208	java/io/DataOutputStream:writeInt	(I)V
    //   78: aload_1
    //   79: getfield 212	com/tencent/tbs/common/stat/WupStatReqWrapper:mPendingRequests	Ljava/util/List;
    //   82: ifnull +94 -> 176
    //   85: aload_1
    //   86: getfield 212	com/tencent/tbs/common/stat/WupStatReqWrapper:mPendingRequests	Ljava/util/List;
    //   89: invokeinterface 218 1 0
    //   94: astore_1
    //   95: aload_1
    //   96: invokeinterface 223 1 0
    //   101: ifeq +75 -> 176
    //   104: aload_1
    //   105: invokeinterface 227 1 0
    //   110: checkcast 147	com/tencent/tbs/common/wup/WUPRequest
    //   113: astore_3
    //   114: aload_3
    //   115: aload 4
    //   117: invokestatic 230	com/tencent/tbs/common/stat/WupStatManager:getRequestId	()I
    //   120: invokevirtual 234	com/tencent/tbs/common/wup/WUPRequest:writeTo	(Ljava/io/DataOutputStream;I)Z
    //   123: ifne +13 -> 136
    //   126: ldc 36
    //   128: ldc -20
    //   130: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: goto -38 -> 95
    //   136: new 129	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   143: astore 5
    //   145: aload 5
    //   147: ldc -18
    //   149: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: pop
    //   153: aload 5
    //   155: aload_3
    //   156: invokevirtual 241	com/tencent/tbs/common/wup/WUPRequest:getRequstID	()I
    //   159: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: ldc 36
    //   165: aload 5
    //   167: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   170: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   173: goto -78 -> 95
    //   176: new 129	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   183: astore_1
    //   184: aload_1
    //   185: ldc -13
    //   187: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload_1
    //   192: aload 6
    //   194: invokevirtual 246	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   197: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: aload_1
    //   202: ldc -8
    //   204: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: ldc 36
    //   210: aload_1
    //   211: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   217: aload 4
    //   219: invokestatic 252	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   222: return
    //   223: astore_1
    //   224: goto +46 -> 270
    //   227: astore_3
    //   228: aload 4
    //   230: astore_1
    //   231: aload_3
    //   232: astore 4
    //   234: goto +15 -> 249
    //   237: astore_1
    //   238: aload_3
    //   239: astore 4
    //   241: goto +29 -> 270
    //   244: astore 4
    //   246: aload 5
    //   248: astore_1
    //   249: aload_1
    //   250: astore_3
    //   251: ldc 36
    //   253: ldc -2
    //   255: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   258: aload_1
    //   259: astore_3
    //   260: aload 4
    //   262: invokevirtual 257	java/lang/Throwable:printStackTrace	()V
    //   265: aload_1
    //   266: invokestatic 252	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   269: return
    //   270: aload 4
    //   272: invokestatic 252	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   275: aload_1
    //   276: athrow
    //   277: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	278	0	this	WupStatManager
    //   0	278	1	paramWupStatReqWrapper	WupStatReqWrapper
    //   0	278	2	paramBoolean	boolean
    //   26	130	3	localObject1	Object
    //   227	12	3	localThrowable1	Throwable
    //   250	10	3	localWupStatReqWrapper	WupStatReqWrapper
    //   22	218	4	localObject2	Object
    //   244	27	4	localThrowable2	Throwable
    //   19	228	5	localStringBuilder	StringBuilder
    //   11	182	6	localFile	File
    // Exception table:
    //   from	to	target	type
    //   61	95	223	finally
    //   95	133	223	finally
    //   136	173	223	finally
    //   176	217	223	finally
    //   61	95	227	java/lang/Throwable
    //   95	133	227	java/lang/Throwable
    //   136	173	227	java/lang/Throwable
    //   176	217	227	java/lang/Throwable
    //   27	35	237	finally
    //   38	44	237	finally
    //   47	61	237	finally
    //   251	258	237	finally
    //   260	265	237	finally
    //   27	35	244	java/lang/Throwable
    //   38	44	244	java/lang/Throwable
    //   47	61	244	java/lang/Throwable
  }
  
  private void doSendPrevFailReqs(WupStatReqWrapper paramWupStatReqWrapper)
  {
    if ((paramWupStatReqWrapper != null) && (paramWupStatReqWrapper.getSize() > 0))
    {
      paramWupStatReqWrapper = paramWupStatReqWrapper.getMultiWupRequest(this);
      int i;
      if (paramWupStatReqWrapper.getRequests() == null) {
        i = 0;
      } else {
        i = paramWupStatReqWrapper.getRequests().size();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("doSendPrevFailReqs: getMultiWupRequest of current pending, ");
      localStringBuilder.append(i);
      localStringBuilder.append(" requests has been loaded");
      LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
      if (i > 0)
      {
        paramWupStatReqWrapper.setRequestName("multi_wup_stat");
        WUPTaskProxy.send(paramWupStatReqWrapper);
      }
    }
  }
  
  public static WupStatManager getInstance()
  {
    return WupStatManagerHolder.INSTANCE;
  }
  
  private static int getRequestId()
  {
    try
    {
      int i = sRequestId;
      sRequestId = i + 1;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static File getStatWUPRequestFile(boolean paramBoolean)
  {
    Object localObject2 = ThreadUtils.getCurrentProcessName(ContextHolder.getAppContext());
    Object localObject1 = localObject2;
    if (!TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = ((String)localObject2).replace(":", "_");
    }
    localObject2 = FileUtils.getDataDir(ContextHolder.getAppContext());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject1);
    localStringBuilder.append("_");
    localStringBuilder.append("wup_stat_req_file_ui");
    localStringBuilder.append(".dat");
    if (paramBoolean) {
      localObject1 = ".partial";
    } else {
      localObject1 = "";
    }
    localStringBuilder.append((String)localObject1);
    return new File((File)localObject2, localStringBuilder.toString());
  }
  
  private void incOperationCount()
  {
    this.mStatOperationCount += 1;
    if (this.mStatOperationCount > 20)
    {
      LogUtils.d("WUPStatManagerUI", "current data has been operated for  20 times, try save");
      saveCurrentFailList();
      this.mStatOperationCount = 0;
    }
  }
  
  /* Error */
  private List<WUPRequest> loadPrevDataFromFile(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 8
    //   3: aconst_null
    //   4: astore 4
    //   6: aload_1
    //   7: ifnull +852 -> 859
    //   10: aload_1
    //   11: invokevirtual 191	java/io/File:exists	()Z
    //   14: ifne +6 -> 20
    //   17: goto +842 -> 859
    //   20: new 269	java/util/ArrayList
    //   23: dup
    //   24: invokespecial 340	java/util/ArrayList:<init>	()V
    //   27: astore 5
    //   29: new 342	java/io/DataInputStream
    //   32: dup
    //   33: aload_1
    //   34: invokestatic 346	com/tencent/common/utils/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   37: invokespecial 349	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   40: astore 7
    //   42: aload 5
    //   44: astore 4
    //   46: aload 7
    //   48: invokevirtual 352	java/io/DataInputStream:readInt	()I
    //   51: sipush 254
    //   54: if_icmpne +686 -> 740
    //   57: aload 5
    //   59: astore 4
    //   61: aload 7
    //   63: invokevirtual 352	java/io/DataInputStream:readInt	()I
    //   66: istore_3
    //   67: iconst_0
    //   68: istore_2
    //   69: iload_2
    //   70: iload_3
    //   71: if_icmpge +230 -> 301
    //   74: aload 5
    //   76: astore 4
    //   78: new 147	com/tencent/tbs/common/wup/WUPRequest
    //   81: dup
    //   82: invokespecial 353	com/tencent/tbs/common/wup/WUPRequest:<init>	()V
    //   85: astore 6
    //   87: aload 5
    //   89: astore 4
    //   91: aload 6
    //   93: aload 7
    //   95: invokevirtual 357	com/tencent/tbs/common/wup/WUPRequest:readFrom	(Ljava/io/DataInputStream;)Z
    //   98: ifeq +79 -> 177
    //   101: aload 5
    //   103: astore 4
    //   105: new 129	java/lang/StringBuilder
    //   108: dup
    //   109: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   112: astore 8
    //   114: aload 5
    //   116: astore 4
    //   118: aload 8
    //   120: ldc_w 359
    //   123: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload 5
    //   129: astore 4
    //   131: aload 8
    //   133: aload 6
    //   135: invokevirtual 241	com/tencent/tbs/common/wup/WUPRequest:getRequstID	()I
    //   138: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: aload 5
    //   144: astore 4
    //   146: ldc 36
    //   148: aload 8
    //   150: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   156: aload 5
    //   158: astore 4
    //   160: aload 5
    //   162: aload 6
    //   164: invokeinterface 363 2 0
    //   169: pop
    //   170: iload_2
    //   171: iconst_1
    //   172: iadd
    //   173: istore_2
    //   174: goto -105 -> 69
    //   177: aload 5
    //   179: astore 4
    //   181: new 129	java/lang/StringBuilder
    //   184: dup
    //   185: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   188: astore 6
    //   190: aload 5
    //   192: astore 4
    //   194: aload 6
    //   196: ldc_w 365
    //   199: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: pop
    //   203: aload 5
    //   205: astore 4
    //   207: aload 6
    //   209: aload_1
    //   210: invokevirtual 368	java/io/File:getName	()Ljava/lang/String;
    //   213: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: pop
    //   217: aload 5
    //   219: astore 4
    //   221: aload 6
    //   223: ldc_w 370
    //   226: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload 5
    //   232: astore 4
    //   234: ldc 36
    //   236: aload 6
    //   238: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   241: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   244: aload 5
    //   246: astore 4
    //   248: new 129	java/lang/StringBuilder
    //   251: dup
    //   252: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   255: astore 6
    //   257: aload 5
    //   259: astore 4
    //   261: aload 6
    //   263: ldc_w 372
    //   266: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload 5
    //   272: astore 4
    //   274: aload 6
    //   276: aload_1
    //   277: invokevirtual 368	java/io/File:getName	()Ljava/lang/String;
    //   280: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: pop
    //   284: aload 5
    //   286: astore 4
    //   288: new 374	java/lang/RuntimeException
    //   291: dup
    //   292: aload 6
    //   294: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: invokespecial 376	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   300: athrow
    //   301: aload 5
    //   303: astore 4
    //   305: new 129	java/lang/StringBuilder
    //   308: dup
    //   309: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   312: astore 6
    //   314: aload 5
    //   316: astore 4
    //   318: aload 6
    //   320: ldc_w 378
    //   323: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   326: pop
    //   327: aload 5
    //   329: astore 4
    //   331: aload 6
    //   333: aload 5
    //   335: invokeinterface 379 1 0
    //   340: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   343: pop
    //   344: aload 5
    //   346: astore 4
    //   348: aload 6
    //   350: ldc_w 381
    //   353: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: pop
    //   357: aload 5
    //   359: astore 4
    //   361: aload 6
    //   363: aload_1
    //   364: invokevirtual 368	java/io/File:getName	()Ljava/lang/String;
    //   367: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   370: pop
    //   371: aload 5
    //   373: astore 4
    //   375: ldc 36
    //   377: aload 6
    //   379: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   382: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   385: aload 5
    //   387: astore 6
    //   389: aload 5
    //   391: astore 4
    //   393: aload 5
    //   395: invokeinterface 379 1 0
    //   400: getstatic 67	com/tencent/tbs/common/stat/WupStatManager:MAX_CACHE_WUP_SIZE	I
    //   403: if_icmple +232 -> 635
    //   406: aload 5
    //   408: astore 4
    //   410: aload 5
    //   412: invokeinterface 379 1 0
    //   417: getstatic 67	com/tencent/tbs/common/stat/WupStatManager:MAX_CACHE_WUP_SIZE	I
    //   420: isub
    //   421: istore_2
    //   422: aload 5
    //   424: astore 4
    //   426: aload 5
    //   428: invokeinterface 379 1 0
    //   433: istore_3
    //   434: aload 5
    //   436: astore 4
    //   438: new 129	java/lang/StringBuilder
    //   441: dup
    //   442: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   445: astore 6
    //   447: aload 5
    //   449: astore 4
    //   451: aload 6
    //   453: ldc_w 383
    //   456: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   459: pop
    //   460: aload 5
    //   462: astore 4
    //   464: aload 6
    //   466: getstatic 67	com/tencent/tbs/common/stat/WupStatManager:MAX_CACHE_WUP_SIZE	I
    //   469: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   472: pop
    //   473: aload 5
    //   475: astore 4
    //   477: aload 6
    //   479: ldc_w 385
    //   482: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   485: pop
    //   486: aload 5
    //   488: astore 4
    //   490: aload 6
    //   492: iload_2
    //   493: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   496: pop
    //   497: aload 5
    //   499: astore 4
    //   501: aload 6
    //   503: ldc_w 387
    //   506: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   509: pop
    //   510: aload 5
    //   512: astore 4
    //   514: aload 6
    //   516: iload_3
    //   517: iconst_1
    //   518: isub
    //   519: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   522: pop
    //   523: aload 5
    //   525: astore 4
    //   527: aload 6
    //   529: ldc_w 389
    //   532: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   535: pop
    //   536: aload 5
    //   538: astore 4
    //   540: ldc 36
    //   542: aload 6
    //   544: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   547: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   550: aload 5
    //   552: astore 4
    //   554: aload 5
    //   556: iload_2
    //   557: iload_3
    //   558: invokeinterface 393 3 0
    //   563: astore 6
    //   565: aload 6
    //   567: astore 4
    //   569: new 129	java/lang/StringBuilder
    //   572: dup
    //   573: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   576: astore 5
    //   578: aload 6
    //   580: astore 4
    //   582: aload 5
    //   584: ldc_w 395
    //   587: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   590: pop
    //   591: aload 6
    //   593: astore 4
    //   595: aload 5
    //   597: aload 6
    //   599: invokeinterface 379 1 0
    //   604: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   607: pop
    //   608: aload 6
    //   610: astore 4
    //   612: aload 5
    //   614: ldc_w 397
    //   617: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   620: pop
    //   621: aload 6
    //   623: astore 4
    //   625: ldc 36
    //   627: aload 5
    //   629: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   632: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   635: aload 6
    //   637: astore 4
    //   639: new 129	java/lang/StringBuilder
    //   642: dup
    //   643: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   646: astore 5
    //   648: aload 6
    //   650: astore 4
    //   652: aload 5
    //   654: ldc_w 399
    //   657: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   660: pop
    //   661: aload 6
    //   663: astore 4
    //   665: aload 5
    //   667: aload_1
    //   668: invokevirtual 368	java/io/File:getName	()Ljava/lang/String;
    //   671: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   674: pop
    //   675: aload 6
    //   677: astore 4
    //   679: aload 5
    //   681: ldc_w 401
    //   684: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   687: pop
    //   688: aload 6
    //   690: astore 4
    //   692: aload 5
    //   694: aload 6
    //   696: invokeinterface 379 1 0
    //   701: invokevirtual 143	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   704: pop
    //   705: aload 6
    //   707: astore 4
    //   709: aload 5
    //   711: ldc_w 403
    //   714: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   717: pop
    //   718: aload 6
    //   720: astore 4
    //   722: ldc 36
    //   724: aload 5
    //   726: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   729: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   732: aload 7
    //   734: invokestatic 252	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   737: aload 6
    //   739: areturn
    //   740: aload 5
    //   742: astore 4
    //   744: new 129	java/lang/StringBuilder
    //   747: dup
    //   748: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   751: astore 6
    //   753: aload 5
    //   755: astore 4
    //   757: aload 6
    //   759: ldc_w 405
    //   762: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   765: pop
    //   766: aload 5
    //   768: astore 4
    //   770: aload 6
    //   772: aload_1
    //   773: invokevirtual 368	java/io/File:getName	()Ljava/lang/String;
    //   776: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   779: pop
    //   780: aload 5
    //   782: astore 4
    //   784: new 374	java/lang/RuntimeException
    //   787: dup
    //   788: aload 6
    //   790: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   793: invokespecial 376	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   796: athrow
    //   797: astore_1
    //   798: goto +54 -> 852
    //   801: astore 6
    //   803: aload 7
    //   805: astore_1
    //   806: aload 4
    //   808: astore 5
    //   810: goto +16 -> 826
    //   813: astore_1
    //   814: aload 4
    //   816: astore 7
    //   818: goto +34 -> 852
    //   821: astore 6
    //   823: aload 8
    //   825: astore_1
    //   826: aload_1
    //   827: astore 4
    //   829: ldc 36
    //   831: ldc_w 407
    //   834: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   837: aload_1
    //   838: astore 4
    //   840: aload 6
    //   842: invokevirtual 257	java/lang/Throwable:printStackTrace	()V
    //   845: aload_1
    //   846: invokestatic 252	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   849: aload 5
    //   851: areturn
    //   852: aload 7
    //   854: invokestatic 252	com/tencent/common/utils/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   857: aload_1
    //   858: athrow
    //   859: new 129	java/lang/StringBuilder
    //   862: dup
    //   863: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   866: astore 4
    //   868: aload 4
    //   870: ldc_w 409
    //   873: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   876: pop
    //   877: aload_1
    //   878: ifnonnull +10 -> 888
    //   881: ldc_w 411
    //   884: astore_1
    //   885: goto +8 -> 893
    //   888: aload_1
    //   889: invokevirtual 368	java/io/File:getName	()Ljava/lang/String;
    //   892: astore_1
    //   893: aload 4
    //   895: aload_1
    //   896: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   899: pop
    //   900: aload 4
    //   902: ldc_w 413
    //   905: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   908: pop
    //   909: ldc 36
    //   911: aload 4
    //   913: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   916: invokestatic 160	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   919: aconst_null
    //   920: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	921	0	this	WupStatManager
    //   0	921	1	paramFile	File
    //   68	489	2	i	int
    //   66	492	3	j	int
    //   4	908	4	localObject1	Object
    //   27	823	5	localObject2	Object
    //   85	704	6	localObject3	Object
    //   801	1	6	localThrowable1	Throwable
    //   821	20	6	localThrowable2	Throwable
    //   40	813	7	localObject4	Object
    //   1	823	8	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   46	57	797	finally
    //   61	67	797	finally
    //   78	87	797	finally
    //   91	101	797	finally
    //   105	114	797	finally
    //   118	127	797	finally
    //   131	142	797	finally
    //   146	156	797	finally
    //   160	170	797	finally
    //   181	190	797	finally
    //   194	203	797	finally
    //   207	217	797	finally
    //   221	230	797	finally
    //   234	244	797	finally
    //   248	257	797	finally
    //   261	270	797	finally
    //   274	284	797	finally
    //   288	301	797	finally
    //   305	314	797	finally
    //   318	327	797	finally
    //   331	344	797	finally
    //   348	357	797	finally
    //   361	371	797	finally
    //   375	385	797	finally
    //   393	406	797	finally
    //   410	422	797	finally
    //   426	434	797	finally
    //   438	447	797	finally
    //   451	460	797	finally
    //   464	473	797	finally
    //   477	486	797	finally
    //   490	497	797	finally
    //   501	510	797	finally
    //   514	523	797	finally
    //   527	536	797	finally
    //   540	550	797	finally
    //   554	565	797	finally
    //   569	578	797	finally
    //   582	591	797	finally
    //   595	608	797	finally
    //   612	621	797	finally
    //   625	635	797	finally
    //   639	648	797	finally
    //   652	661	797	finally
    //   665	675	797	finally
    //   679	688	797	finally
    //   692	705	797	finally
    //   709	718	797	finally
    //   722	732	797	finally
    //   744	753	797	finally
    //   757	766	797	finally
    //   770	780	797	finally
    //   784	797	797	finally
    //   46	57	801	java/lang/Throwable
    //   61	67	801	java/lang/Throwable
    //   78	87	801	java/lang/Throwable
    //   91	101	801	java/lang/Throwable
    //   105	114	801	java/lang/Throwable
    //   118	127	801	java/lang/Throwable
    //   131	142	801	java/lang/Throwable
    //   146	156	801	java/lang/Throwable
    //   160	170	801	java/lang/Throwable
    //   181	190	801	java/lang/Throwable
    //   194	203	801	java/lang/Throwable
    //   207	217	801	java/lang/Throwable
    //   221	230	801	java/lang/Throwable
    //   234	244	801	java/lang/Throwable
    //   248	257	801	java/lang/Throwable
    //   261	270	801	java/lang/Throwable
    //   274	284	801	java/lang/Throwable
    //   288	301	801	java/lang/Throwable
    //   305	314	801	java/lang/Throwable
    //   318	327	801	java/lang/Throwable
    //   331	344	801	java/lang/Throwable
    //   348	357	801	java/lang/Throwable
    //   361	371	801	java/lang/Throwable
    //   375	385	801	java/lang/Throwable
    //   393	406	801	java/lang/Throwable
    //   410	422	801	java/lang/Throwable
    //   426	434	801	java/lang/Throwable
    //   438	447	801	java/lang/Throwable
    //   451	460	801	java/lang/Throwable
    //   464	473	801	java/lang/Throwable
    //   477	486	801	java/lang/Throwable
    //   490	497	801	java/lang/Throwable
    //   501	510	801	java/lang/Throwable
    //   514	523	801	java/lang/Throwable
    //   527	536	801	java/lang/Throwable
    //   540	550	801	java/lang/Throwable
    //   554	565	801	java/lang/Throwable
    //   569	578	801	java/lang/Throwable
    //   582	591	801	java/lang/Throwable
    //   595	608	801	java/lang/Throwable
    //   612	621	801	java/lang/Throwable
    //   625	635	801	java/lang/Throwable
    //   639	648	801	java/lang/Throwable
    //   652	661	801	java/lang/Throwable
    //   665	675	801	java/lang/Throwable
    //   679	688	801	java/lang/Throwable
    //   692	705	801	java/lang/Throwable
    //   709	718	801	java/lang/Throwable
    //   722	732	801	java/lang/Throwable
    //   744	753	801	java/lang/Throwable
    //   757	766	801	java/lang/Throwable
    //   770	780	801	java/lang/Throwable
    //   784	797	801	java/lang/Throwable
    //   29	42	813	finally
    //   829	837	813	finally
    //   840	845	813	finally
    //   29	42	821	java/lang/Throwable
  }
  
  private void removeFromCurrentList(WUPRequestBase paramWUPRequestBase)
  {
    if (!(paramWUPRequestBase instanceof WUPRequest)) {
      return;
    }
    synchronized (this.mCurrentLock)
    {
      StringBuilder localStringBuilder;
      if (this.mCurrentRequests == null)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeFromCurrentList: current pending list is empty, ignore, request funcName = ");
        localStringBuilder.append(paramWUPRequestBase.getFuncName());
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
        return;
      }
      if (this.mCurrentRequests.removeData((WUPRequest)paramWUPRequestBase))
      {
        this.mCurrentDataChanged = true;
        incOperationCount();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeFromCurrentList: remove request from pending list (size = ");
        localStringBuilder.append(this.mCurrentRequests.getSize());
        localStringBuilder.append(") , request funcName = ");
        localStringBuilder.append(paramWUPRequestBase.getFuncName());
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("removeFromCurrentList: data not existing pending list, ignore, request funcName = ");
        localStringBuilder.append(paramWUPRequestBase.getFuncName());
        LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
      }
      return;
    }
  }
  
  private void savePartialFailList()
  {
    LogUtils.d("WUPStatManagerUI", "savePartialFailList: Begin to schedule saving current data to file");
    this.mWorkerThrd.post(new Runnable()
    {
      public void run()
      {
        LogUtils.d("WUPStatManagerUI", "savePartialFailList: Saving partial data to file in thread");
        for (;;)
        {
          synchronized (WupStatManager.this.mPartialLock)
          {
            if (WupStatManager.this.mPartialRequests != null)
            {
              WupStatReqWrapper localWupStatReqWrapper = WupStatManager.this.mPartialRequests.makeCopy();
              if (localWupStatReqWrapper != null) {
                WupStatManager.this.doSaveFailReqs(localWupStatReqWrapper, false);
              }
              LogUtils.d("WUPStatManagerUI", "savePartialFailList: Save complete ");
              return;
            }
          }
          Object localObject2 = null;
        }
      }
    });
  }
  
  public void loadFailList()
  {
    LogUtils.d("WUPStatManagerUI", "loadFailList: loadFailList called");
    synchronized (this.mLoadLock)
    {
      if (this.mIsLoading)
      {
        LogUtils.d("WUPStatManagerUI", "loadFailList: we have load data already, ignore this request");
        return;
      }
      this.mIsLoading = true;
      LogUtils.d("WUPStatManagerUI", "loadFailList: begin load data from file");
      this.mWorkerThrd.post(new Runnable()
      {
        public void run()
        {
          LogUtils.d("WUPStatManagerUI", "loadFailList: load stat file begins");
          File localFile = WupStatManager.getStatWUPRequestFile(false);
          List localList = WupStatManager.this.loadPrevDataFromFile(localFile);
          if (localList != null) {
            try
            {
              synchronized (WupStatManager.this.mCurrentLock)
              {
                if (WupStatManager.this.mCurrentRequests == null) {
                  WupStatManager.this.mCurrentRequests = new WupStatReqWrapper();
                }
                WupStatManager.this.mCurrentDataChanged = WupStatManager.this.mCurrentRequests.addData(localList);
                ??? = new StringBuilder();
                ((StringBuilder)???).append("loadFailList: load data from file ");
                ((StringBuilder)???).append(localFile.getName());
                ((StringBuilder)???).append(" SUCCESS, ");
                ((StringBuilder)???).append(localList.size());
                ((StringBuilder)???).append(" requests are loaded");
                LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
              }
              WupStatManager.this.mHasLoaded = true;
            }
            catch (Throwable localThrowable)
            {
              ??? = new StringBuilder();
              ((StringBuilder)???).append("loadFailList: load file Exception, path=");
              ((StringBuilder)???).append(localFile.getName());
              LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
              localThrowable.printStackTrace();
              WupStatManager.this.onLoadDataEnd();
            }
          }
          LogUtils.d("WUPStatManagerUI", "loadFailList: load stat file ends");
          LogUtils.d("WUPStatManagerUI", "loadFailList: load partial file begins");
          localFile = WupStatManager.getStatWUPRequestFile(true);
          Object localObject2 = WupStatManager.this.loadPrevDataFromFile(localFile);
          if ((localObject2 != null) && (!((List)localObject2).isEmpty()))
          {
            ??? = new StringBuilder();
            ((StringBuilder)???).append("loadFailList: load partial data from file ");
            ((StringBuilder)???).append(localFile.getName());
            ((StringBuilder)???).append(" SUCCESS, ");
            ((StringBuilder)???).append(((List)localObject2).size());
            ((StringBuilder)???).append(" requests are loaded");
            LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
            localObject2 = ((List)localObject2).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              ??? = (WUPRequest)((Iterator)localObject2).next();
              WupStatManager.this.sendWUPStatReq((WUPRequest)???);
            }
          }
          FileUtils.deleteQuietly(localFile);
          LogUtils.d("WUPStatManagerUI", "loadFailList: load partial file ends");
        }
      });
      return;
    }
  }
  
  protected void onLoadDataEnd()
  {
    LogUtils.d("WUPStatManagerUI", "onLoadDataEnd called");
    synchronized (this.mCurrentLock)
    {
      WupStatReqWrapper localWupStatReqWrapper;
      if ((this.mCurrentRequests != null) && (this.mCurrentRequests.getSize() > 0))
      {
        LogUtils.d("WUPStatManagerUI", "onLoadDataEnd: mCurrentRequests not empty, begin getting requests");
        localWupStatReqWrapper = this.mCurrentRequests.makeCopy();
      }
      else
      {
        LogUtils.d("WUPStatManagerUI", "onLoadDataEnd: mPrevFailedRequests empty, ignore");
        localWupStatReqWrapper = null;
      }
      if (localWupStatReqWrapper != null)
      {
        LogUtils.d("WUPStatManagerUI", "onLoadDataEnd: send prev requests begins");
        doSendPrevFailReqs(localWupStatReqWrapper);
      }
      this.mHasLoaded = true;
      return;
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Send CURRENT requests FAILED, serventName=");
    localStringBuilder.append(paramWUPRequestBase.getServerName());
    localStringBuilder.append(", funcName=");
    localStringBuilder.append(paramWUPRequestBase.getFuncName());
    LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPRequestBase != null)
    {
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("Send CURRENT request send SUCCESS, serventName=");
      paramWUPResponseBase.append(paramWUPRequestBase.getServerName());
      paramWUPResponseBase.append(", funcName=");
      paramWUPResponseBase.append(paramWUPRequestBase.getFuncName());
      LogUtils.d("WUPStatManagerUI", paramWUPResponseBase.toString());
      removeFromCurrentList(paramWUPRequestBase);
    }
  }
  
  public void saveAllAndUpload()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("saveAllAndUpload: saveAllAndUpload called, current data size = ");
    Object localObject = this.mCurrentRequests;
    if (localObject == null) {
      localObject = "empty";
    } else {
      localObject = Integer.valueOf(((WupStatReqWrapper)localObject).getSize());
    }
    localStringBuilder.append(localObject);
    LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
    this.mWorkerThrd.post(new Runnable()
    {
      public void run()
      {
        LogUtils.d("WUPStatManagerUI", "saveAllAndUpload: begin save current data in thread");
        for (;;)
        {
          synchronized (WupStatManager.this.mCurrentLock)
          {
            if (WupStatManager.this.mCurrentRequests != null)
            {
              WupStatReqWrapper localWupStatReqWrapper = WupStatManager.this.mCurrentRequests.makeCopy();
              boolean bool = WupStatManager.this.mCurrentDataChanged;
              if (localWupStatReqWrapper != null)
              {
                ??? = new StringBuilder();
                ((StringBuilder)???).append("saveAllAndUpload: current pending list not null, save! current size=");
                ((StringBuilder)???).append(localWupStatReqWrapper.getSize());
                LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
                if (bool) {
                  WupStatManager.this.doSaveFailReqs(localWupStatReqWrapper, true);
                }
                ??? = WupStatManager.this;
                ((WupStatManager)???).mCurrentDataChanged = false;
                ((WupStatManager)???).doSendPrevFailReqs(localWupStatReqWrapper);
              }
              return;
            }
          }
          Object localObject2 = null;
        }
      }
    });
  }
  
  public void saveCurrentFailList()
  {
    LogUtils.d("WUPStatManagerUI", "saveCurrentFailList: Begin to schedule saving current data to file");
    if (!this.mHasLoaded)
    {
      LogUtils.d("WUPStatManagerUI", "saveCurrentFailList: Begin to schedule saving, but data load is not ready, ignore");
      return;
    }
    this.mWorkerThrd.post(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("saveCurrentFailList: Saving current data to file in thread, current data changed ?");
        ((StringBuilder)localObject1).append(WupStatManager.this.mCurrentDataChanged);
        LogUtils.d("WUPStatManagerUI", ((StringBuilder)localObject1).toString());
        for (;;)
        {
          synchronized (WupStatManager.this.mCurrentLock)
          {
            if ((WupStatManager.this.mCurrentDataChanged) && (WupStatManager.this.mCurrentRequests != null))
            {
              localObject1 = WupStatManager.this.mCurrentRequests.makeCopy();
              if (localObject1 != null)
              {
                WupStatManager.this.doSaveFailReqs((WupStatReqWrapper)localObject1, true);
                WupStatManager.this.mCurrentDataChanged = false;
              }
              LogUtils.d("WUPStatManagerUI", "saveCurrentFailList: Save complete ");
              return;
            }
          }
          Object localObject3 = null;
        }
      }
    });
  }
  
  public int sendPartialStatReq(WUPRequestBase paramWUPRequestBase)
  {
    if ((paramWUPRequestBase != null) && (!(paramWUPRequestBase instanceof MultiWUPRequest)))
    {
      int i = getRequestId();
      ??? = new StringBuilder();
      ((StringBuilder)???).append("sendPartialStatReq: sendWUPStatReq called, current reqID=");
      ((StringBuilder)???).append(i);
      LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
      synchronized (this.mPartialLock)
      {
        if (this.mPartialRequests == null) {
          this.mPartialRequests = new WupStatReqWrapper();
        }
        paramWUPRequestBase.setBindObject(Integer.valueOf(i));
        this.mPartialRequests.addData((WUPRequest)paramWUPRequestBase);
        paramWUPRequestBase = new StringBuilder();
        paramWUPRequestBase.append("sendPartialStatReq: add to mPartialRequests, size=");
        paramWUPRequestBase.append(this.mPartialRequests.getSize());
        LogUtils.d("WUPStatManagerUI", paramWUPRequestBase.toString());
        savePartialFailList();
        return i;
      }
    }
    return -1;
  }
  
  public void sendWUPStatReq(WUPRequestBase paramWUPRequestBase, int paramInt)
  {
    LogUtils.d("WUPStatManagerUI", "sendWUPStatReq: sendWUPStatReq called");
    if (paramWUPRequestBase == null) {
      return;
    }
    if ((paramWUPRequestBase instanceof MultiWUPRequest))
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("sendWUPStatReq: Multiple WUPRequest got, size = ");
      localObject1 = (MultiWUPRequest)paramWUPRequestBase;
      ((StringBuilder)localObject2).append(((MultiWUPRequest)localObject1).getRequests().size());
      LogUtils.d("WUPStatManagerUI", ((StringBuilder)localObject2).toString());
      localObject2 = ((MultiWUPRequest)localObject1).getRequests();
      if (localObject2 == null)
      {
        LogUtils.d("WUPStatManagerUI", "sendWUPStatReq: multiReq.getRequests() return null, ignore sending");
        return;
      }
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        WUPRequestBase localWUPRequestBase = (WUPRequestBase)((Iterator)localObject2).next();
        localWUPRequestBase.setRequestCallBack(this);
        addToCurrentList((WUPRequest)localWUPRequestBase);
      }
      WUPTaskProxy.send((MultiWUPRequestBase)localObject1);
    }
    else if ((paramWUPRequestBase instanceof WUPRequest))
    {
      LogUtils.d("WUPStatManagerUI", "sendWUPStatReq: single WUPRequest got");
      paramWUPRequestBase.setRequestCallBack(this);
      localObject1 = (WUPRequest)paramWUPRequestBase;
      if (WUPTaskProxy.send((WUPRequestBase)localObject1)) {
        addToCurrentList((WUPRequest)localObject1);
      }
    }
    deletePartialStatReq(paramInt);
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("sendWUPStatReq: sendWUPStatReq ends, serventName=");
    ((StringBuilder)localObject1).append(paramWUPRequestBase.getServerName());
    ((StringBuilder)localObject1).append(", funcName=");
    ((StringBuilder)localObject1).append(paramWUPRequestBase.getFuncName());
    LogUtils.d("WUPStatManagerUI", ((StringBuilder)localObject1).toString());
  }
  
  public void sendWUPStatReq(WUPRequest paramWUPRequest)
  {
    sendWUPStatReq(paramWUPRequest, -1);
  }
  
  public void shutDown()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shutDown: Shut down called, current data size = ");
    Object localObject = this.mCurrentRequests;
    if (localObject == null) {
      localObject = "empty";
    } else {
      localObject = Integer.valueOf(((WupStatReqWrapper)localObject).getSize());
    }
    localStringBuilder.append(localObject);
    LogUtils.d("WUPStatManagerUI", localStringBuilder.toString());
    this.mWorkerThrd.post(new Runnable()
    {
      public void run()
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("shutDown: Saving current data to file in thread, current data changed ?");
        ((StringBuilder)???).append(WupStatManager.this.mCurrentDataChanged);
        LogUtils.d("WUPStatManagerUI", ((StringBuilder)???).toString());
        for (;;)
        {
          synchronized (WupStatManager.this.mCurrentLock)
          {
            if ((WupStatManager.this.mCurrentDataChanged) && (WupStatManager.this.mCurrentRequests != null))
            {
              ??? = WupStatManager.this.mCurrentRequests.makeCopy();
              WupStatManager.this.mCurrentRequests = null;
              if (??? != null)
              {
                WupStatManager.this.doSaveFailReqs((WupStatReqWrapper)???, true);
                WupStatManager.this.mCurrentDataChanged = false;
              }
              ??? = WupStatManager.this;
              ((WupStatManager)???).mHasLoaded = false;
              synchronized (((WupStatManager)???).mLoadLock)
              {
                WupStatManager.this.mIsLoading = false;
                LogUtils.d("WUPStatManagerUI", "shutDown: Shutdown complete!!!");
                return;
              }
            }
          }
          Object localObject3 = null;
        }
      }
    });
  }
  
  private static class WupStatManagerHolder
  {
    private static final WupStatManager INSTANCE = new WupStatManager(null);
  }
  
  private static class WupStatWorkerThread
  {
    private Handler mHandler = null;
    private Looper mLooper = null;
    
    public WupStatWorkerThread()
    {
      if (this.mLooper == null)
      {
        HandlerThread localHandlerThread = new HandlerThread("WupStatWorker", 11);
        localHandlerThread.start();
        this.mLooper = localHandlerThread.getLooper();
      }
      this.mHandler = new Handler(this.mLooper);
    }
    
    public final boolean post(Runnable paramRunnable)
    {
      Handler localHandler = this.mHandler;
      if (localHandler != null) {
        return localHandler.post(paramRunnable);
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\WupStatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */