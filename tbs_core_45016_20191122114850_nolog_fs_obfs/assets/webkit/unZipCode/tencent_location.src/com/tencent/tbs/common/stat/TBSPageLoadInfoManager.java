package com.tencent.tbs.common.stat;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TbsFileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class TBSPageLoadInfoManager
{
  public static final int MAX_CACHED_FILE_SIZE = 10;
  public static final int MAX_NEED_UPLOAD_FILE_COUNT = 5;
  public static final int MAX_UPLOAD_CURRENT_DATA_SIZE = 20;
  private static final String TAG = "TBSPageLoadInfo";
  public static final String WUP_TBS_DATA_VERSION = "000001";
  private static Context mAppContext;
  private static TBSPageLoadInfoManager mInstance;
  private Object dataLock = new byte[0];
  Object loadLock = new Object();
  private PageLoadInfoData mCurrentData = null;
  private boolean mCurrentDataAllNew = true;
  private boolean mHasDataChangedSinceLastSave = false;
  private boolean mIsLoading = false;
  private RecorderWorkerThread mRecorderWorker = null;
  private ArrayList<PageLoadInfoData> preDataList = null;
  
  private TBSPageLoadInfoManager()
  {
    synchronized (this.dataLock)
    {
      this.preDataList = new ArrayList();
      initData();
      try
      {
        this.mRecorderWorker = new RecorderWorkerThread();
        return;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        for (;;) {}
      }
      this.mRecorderWorker = null;
      return;
    }
  }
  
  public static TBSPageLoadInfoManager getInstance(Context paramContext)
  {
    if (mInstance == null)
    {
      mAppContext = paramContext;
      mInstance = new TBSPageLoadInfoManager();
    }
    return mInstance;
  }
  
  private void initData()
  {
    synchronized (this.dataLock)
    {
      this.mCurrentData = new PageLoadInfoData();
      this.mCurrentData.mTime = System.currentTimeMillis();
      this.mCurrentData.mId = PublicSettingManager.getInstance().getWUPPliDataId();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("init stat data, id=");
      localStringBuilder.append(this.mCurrentData.mId);
      LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("init stat data, time=");
      localStringBuilder.append(this.mCurrentData.mTime);
      LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
      return;
    }
  }
  
  public void SaveCurrentAndMakeNewStatData()
  {
    if (this.mRecorderWorker == null) {
      return;
    }
    if (!save())
    {
      LogUtils.d("TBSPageLoadInfo", "save data fail, put this stat data in memory");
      return;
    }
    LogUtils.d("TBSPageLoadInfo", "save data succ, remve this stat data from memory");
    synchronized (this.dataLock)
    {
      this.preDataList.add(this.mCurrentData.copy());
      initData();
      return;
    }
  }
  
  public void deleteFileByID(int paramInt)
  {
    synchronized (this.dataLock)
    {
      Iterator localIterator = this.preDataList.iterator();
      while (localIterator.hasNext())
      {
        PageLoadInfoData localPageLoadInfoData = (PageLoadInfoData)localIterator.next();
        if ((localPageLoadInfoData != null) && (localPageLoadInfoData.mId == paramInt)) {
          localIterator.remove();
        }
      }
      ??? = TbsFileUtils.getTBSinfoFile(paramInt % 10);
      if ((??? != null) && (((File)???).exists())) {
        FileUtils.deleteQuietly((File)???);
      }
      return;
    }
  }
  
  public PageLoadInfoData getCurrentData()
  {
    synchronized (this.dataLock)
    {
      if (this.mCurrentData == null) {
        initData();
      }
      return this.mCurrentData;
    }
  }
  
  public ArrayList<PageLoadInfoData> getPreDataList()
  {
    synchronized (this.dataLock)
    {
      ArrayList localArrayList = new ArrayList(this.preDataList);
      return localArrayList;
    }
  }
  
  public void load()
  {
    if (this.mRecorderWorker == null) {
      return;
    }
    ??? = new StringBuilder();
    ((StringBuilder)???).append("load thread=");
    ((StringBuilder)???).append(Thread.currentThread().getName());
    LogUtils.d("TBSPageLoadInfo", ((StringBuilder)???).toString());
    synchronized (this.loadLock)
    {
      if (this.mIsLoading) {
        return;
      }
      this.mIsLoading = true;
      this.mRecorderWorker.post(new Runnable()
      {
        /* Error */
        public void run()
        {
          // Byte code:
          //   0: iconst_0
          //   1: istore_1
          //   2: iload_1
          //   3: bipush 10
          //   5: if_icmpge +939 -> 944
          //   8: iload_1
          //   9: invokestatic 37	com/tencent/tbs/common/utils/TbsFileUtils:getTBSinfoFile	(I)Ljava/io/File;
          //   12: astore 7
          //   14: aload 7
          //   16: ifnull +879 -> 895
          //   19: aload 7
          //   21: invokevirtual 43	java/io/File:exists	()Z
          //   24: ifne +6 -> 30
          //   27: goto +868 -> 895
          //   30: aconst_null
          //   31: astore 6
          //   33: aconst_null
          //   34: astore 5
          //   36: new 45	java/io/DataInputStream
          //   39: dup
          //   40: new 47	java/io/BufferedInputStream
          //   43: dup
          //   44: aload 7
          //   46: invokestatic 53	com/tencent/common/utils/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
          //   49: invokespecial 56	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
          //   52: invokespecial 57	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
          //   55: astore 4
          //   57: aload 4
          //   59: astore 5
          //   61: new 59	com/tencent/tbs/common/stat/PageLoadInfoData
          //   64: dup
          //   65: invokespecial 60	com/tencent/tbs/common/stat/PageLoadInfoData:<init>	()V
          //   68: astore 6
          //   70: aload 4
          //   72: astore 5
          //   74: ldc 62
          //   76: aload 4
          //   78: invokevirtual 66	java/io/DataInputStream:readUTF	()Ljava/lang/String;
          //   81: invokevirtual 72	java/lang/String:equals	(Ljava/lang/Object;)Z
          //   84: ifne +107 -> 191
          //   87: aload 4
          //   89: astore 5
          //   91: new 74	java/lang/StringBuilder
          //   94: dup
          //   95: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   98: astore 6
          //   100: aload 4
          //   102: astore 5
          //   104: aload 6
          //   106: ldc 77
          //   108: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   111: pop
          //   112: aload 4
          //   114: astore 5
          //   116: aload 6
          //   118: iload_1
          //   119: invokevirtual 84	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   122: pop
          //   123: aload 4
          //   125: astore 5
          //   127: aload 6
          //   129: ldc 86
          //   131: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   134: pop
          //   135: aload 4
          //   137: astore 5
          //   139: aload 6
          //   141: invokestatic 92	java/lang/Thread:currentThread	()Ljava/lang/Thread;
          //   144: invokevirtual 95	java/lang/Thread:getName	()Ljava/lang/String;
          //   147: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   150: pop
          //   151: aload 4
          //   153: astore 5
          //   155: ldc 97
          //   157: aload 6
          //   159: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   162: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   165: aload 4
          //   167: astore 5
          //   169: aload_0
          //   170: getfield 17	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$2:this$0	Lcom/tencent/tbs/common/stat/TBSPageLoadInfoManager;
          //   173: iload_1
          //   174: invokevirtual 110	com/tencent/tbs/common/stat/TBSPageLoadInfoManager:deleteFileByID	(I)V
          //   177: aload 4
          //   179: invokevirtual 113	java/io/DataInputStream:close	()V
          //   182: return
          //   183: astore 4
          //   185: aload 4
          //   187: invokevirtual 116	java/io/IOException:printStackTrace	()V
          //   190: return
          //   191: aload 4
          //   193: astore 5
          //   195: aload 6
          //   197: aload 4
          //   199: invokevirtual 120	java/io/DataInputStream:readInt	()I
          //   202: putfield 124	com/tencent/tbs/common/stat/PageLoadInfoData:mId	I
          //   205: aload 4
          //   207: astore 5
          //   209: aload 6
          //   211: aload 4
          //   213: invokevirtual 128	java/io/DataInputStream:readLong	()J
          //   216: putfield 132	com/tencent/tbs/common/stat/PageLoadInfoData:mTime	J
          //   219: aload 4
          //   221: astore 5
          //   223: aload 4
          //   225: invokevirtual 136	java/io/DataInputStream:readShort	()S
          //   228: istore_3
          //   229: iconst_0
          //   230: istore_2
          //   231: iload_2
          //   232: iload_3
          //   233: if_icmpge +110 -> 343
          //   236: aload 4
          //   238: astore 5
          //   240: aload 4
          //   242: invokevirtual 66	java/io/DataInputStream:readUTF	()Ljava/lang/String;
          //   245: astore 8
          //   247: aload 4
          //   249: astore 5
          //   251: new 74	java/lang/StringBuilder
          //   254: dup
          //   255: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   258: astore 9
          //   260: aload 4
          //   262: astore 5
          //   264: aload 9
          //   266: ldc -118
          //   268: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   271: pop
          //   272: aload 4
          //   274: astore 5
          //   276: aload 9
          //   278: iload_1
          //   279: invokevirtual 84	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   282: pop
          //   283: aload 4
          //   285: astore 5
          //   287: aload 9
          //   289: ldc -116
          //   291: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   294: pop
          //   295: aload 4
          //   297: astore 5
          //   299: aload 9
          //   301: aload 8
          //   303: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   306: pop
          //   307: aload 4
          //   309: astore 5
          //   311: ldc 97
          //   313: aload 9
          //   315: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   318: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   321: aload 4
          //   323: astore 5
          //   325: aload 6
          //   327: getfield 144	com/tencent/tbs/common/stat/PageLoadInfoData:mRecordInfos	Ljava/util/ArrayList;
          //   330: aload 8
          //   332: invokevirtual 149	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   335: pop
          //   336: iload_2
          //   337: iconst_1
          //   338: iadd
          //   339: istore_2
          //   340: goto -109 -> 231
          //   343: aload 4
          //   345: astore 5
          //   347: aload_0
          //   348: getfield 17	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$2:this$0	Lcom/tencent/tbs/common/stat/TBSPageLoadInfoManager;
          //   351: invokestatic 153	com/tencent/tbs/common/stat/TBSPageLoadInfoManager:access$000	(Lcom/tencent/tbs/common/stat/TBSPageLoadInfoManager;)Ljava/lang/Object;
          //   354: astore 8
          //   356: aload 4
          //   358: astore 5
          //   360: aload 8
          //   362: monitorenter
          //   363: new 74	java/lang/StringBuilder
          //   366: dup
          //   367: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   370: astore 5
          //   372: aload 5
          //   374: ldc -118
          //   376: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   379: pop
          //   380: aload 5
          //   382: iload_1
          //   383: invokevirtual 84	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   386: pop
          //   387: aload 5
          //   389: ldc -101
          //   391: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   394: pop
          //   395: ldc 97
          //   397: aload 5
          //   399: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   402: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   405: aload_0
          //   406: getfield 17	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$2:this$0	Lcom/tencent/tbs/common/stat/TBSPageLoadInfoManager;
          //   409: invokestatic 159	com/tencent/tbs/common/stat/TBSPageLoadInfoManager:access$100	(Lcom/tencent/tbs/common/stat/TBSPageLoadInfoManager;)Ljava/util/ArrayList;
          //   412: aload 6
          //   414: invokevirtual 149	java/util/ArrayList:add	(Ljava/lang/Object;)Z
          //   417: pop
          //   418: aload 8
          //   420: monitorexit
          //   421: aload 4
          //   423: astore 5
          //   425: new 74	java/lang/StringBuilder
          //   428: dup
          //   429: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   432: astore 6
          //   434: aload 4
          //   436: astore 5
          //   438: aload 6
          //   440: ldc -95
          //   442: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   445: pop
          //   446: aload 4
          //   448: astore 5
          //   450: aload 6
          //   452: iload_1
          //   453: invokevirtual 84	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   456: pop
          //   457: aload 4
          //   459: astore 5
          //   461: aload 6
          //   463: ldc -93
          //   465: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   468: pop
          //   469: aload 4
          //   471: astore 5
          //   473: ldc 97
          //   475: aload 6
          //   477: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   480: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   483: aload 4
          //   485: invokevirtual 113	java/io/DataInputStream:close	()V
          //   488: goto +449 -> 937
          //   491: astore 6
          //   493: aload 8
          //   495: monitorexit
          //   496: aload 4
          //   498: astore 5
          //   500: aload 6
          //   502: athrow
          //   503: astore 6
          //   505: goto +33 -> 538
          //   508: astore 6
          //   510: goto +65 -> 575
          //   513: astore 6
          //   515: goto +153 -> 668
          //   518: astore 6
          //   520: goto +256 -> 776
          //   523: astore 4
          //   525: goto +347 -> 872
          //   528: astore 5
          //   530: aload 6
          //   532: astore 4
          //   534: aload 5
          //   536: astore 6
          //   538: aload 4
          //   540: astore 5
          //   542: aload 6
          //   544: invokevirtual 164	java/lang/NoClassDefFoundError:printStackTrace	()V
          //   547: aload 4
          //   549: ifnull +388 -> 937
          //   552: aload 4
          //   554: invokevirtual 113	java/io/DataInputStream:close	()V
          //   557: goto +380 -> 937
          //   560: astore 4
          //   562: aload 4
          //   564: invokevirtual 116	java/io/IOException:printStackTrace	()V
          //   567: goto +370 -> 937
          //   570: astore 6
          //   572: aconst_null
          //   573: astore 4
          //   575: aload 4
          //   577: astore 5
          //   579: new 74	java/lang/StringBuilder
          //   582: dup
          //   583: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   586: astore 8
          //   588: aload 4
          //   590: astore 5
          //   592: aload 8
          //   594: ldc -95
          //   596: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   599: pop
          //   600: aload 4
          //   602: astore 5
          //   604: aload 8
          //   606: aload 7
          //   608: invokevirtual 167	java/io/File:getPath	()Ljava/lang/String;
          //   611: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   614: pop
          //   615: aload 4
          //   617: astore 5
          //   619: aload 8
          //   621: ldc -87
          //   623: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   626: pop
          //   627: aload 4
          //   629: astore 5
          //   631: ldc 97
          //   633: aload 8
          //   635: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   638: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   641: aload 4
          //   643: astore 5
          //   645: aload 6
          //   647: invokevirtual 170	java/lang/OutOfMemoryError:printStackTrace	()V
          //   650: aload 4
          //   652: ifnull +285 -> 937
          //   655: aload 4
          //   657: invokevirtual 113	java/io/DataInputStream:close	()V
          //   660: goto +277 -> 937
          //   663: astore 6
          //   665: aconst_null
          //   666: astore 4
          //   668: aload 4
          //   670: astore 5
          //   672: new 74	java/lang/StringBuilder
          //   675: dup
          //   676: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   679: astore 8
          //   681: aload 4
          //   683: astore 5
          //   685: aload 8
          //   687: ldc -95
          //   689: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   692: pop
          //   693: aload 4
          //   695: astore 5
          //   697: aload 8
          //   699: aload 7
          //   701: invokevirtual 167	java/io/File:getPath	()Ljava/lang/String;
          //   704: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   707: pop
          //   708: aload 4
          //   710: astore 5
          //   712: aload 8
          //   714: ldc -84
          //   716: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   719: pop
          //   720: aload 4
          //   722: astore 5
          //   724: aload 8
          //   726: aload 6
          //   728: invokevirtual 173	java/lang/Exception:toString	()Ljava/lang/String;
          //   731: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   734: pop
          //   735: aload 4
          //   737: astore 5
          //   739: ldc 97
          //   741: aload 8
          //   743: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   746: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   749: aload 4
          //   751: astore 5
          //   753: aload 6
          //   755: invokevirtual 174	java/lang/Exception:printStackTrace	()V
          //   758: aload 4
          //   760: ifnull +177 -> 937
          //   763: aload 4
          //   765: invokevirtual 113	java/io/DataInputStream:close	()V
          //   768: goto +169 -> 937
          //   771: astore 6
          //   773: aconst_null
          //   774: astore 4
          //   776: aload 4
          //   778: astore 5
          //   780: new 74	java/lang/StringBuilder
          //   783: dup
          //   784: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   787: astore 8
          //   789: aload 4
          //   791: astore 5
          //   793: aload 8
          //   795: ldc -95
          //   797: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   800: pop
          //   801: aload 4
          //   803: astore 5
          //   805: aload 8
          //   807: aload 7
          //   809: invokevirtual 167	java/io/File:getPath	()Ljava/lang/String;
          //   812: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   815: pop
          //   816: aload 4
          //   818: astore 5
          //   820: aload 8
          //   822: ldc -80
          //   824: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   827: pop
          //   828: aload 4
          //   830: astore 5
          //   832: aload 8
          //   834: aload 6
          //   836: invokevirtual 177	java/io/EOFException:toString	()Ljava/lang/String;
          //   839: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   842: pop
          //   843: aload 4
          //   845: astore 5
          //   847: ldc 97
          //   849: aload 8
          //   851: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   854: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   857: aload 4
          //   859: ifnull +78 -> 937
          //   862: aload 4
          //   864: invokevirtual 113	java/io/DataInputStream:close	()V
          //   867: goto +70 -> 937
          //   870: astore 4
          //   872: aload 5
          //   874: ifnull +18 -> 892
          //   877: aload 5
          //   879: invokevirtual 113	java/io/DataInputStream:close	()V
          //   882: goto +10 -> 892
          //   885: astore 5
          //   887: aload 5
          //   889: invokevirtual 116	java/io/IOException:printStackTrace	()V
          //   892: aload 4
          //   894: athrow
          //   895: new 74	java/lang/StringBuilder
          //   898: dup
          //   899: invokespecial 75	java/lang/StringBuilder:<init>	()V
          //   902: astore 4
          //   904: aload 4
          //   906: ldc -77
          //   908: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   911: pop
          //   912: aload 4
          //   914: iload_1
          //   915: invokevirtual 84	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
          //   918: pop
          //   919: aload 4
          //   921: ldc -75
          //   923: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   926: pop
          //   927: ldc 97
          //   929: aload 4
          //   931: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   934: invokestatic 106	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
          //   937: iload_1
          //   938: iconst_1
          //   939: iadd
          //   940: istore_1
          //   941: goto -939 -> 2
          //   944: aload_0
          //   945: getfield 17	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$2:this$0	Lcom/tencent/tbs/common/stat/TBSPageLoadInfoManager;
          //   948: invokevirtual 184	com/tencent/tbs/common/stat/TBSPageLoadInfoManager:notifyLoadEnd	()V
          //   951: return
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	952	0	this	2
          //   1	940	1	i	int
          //   230	110	2	j	int
          //   228	6	3	k	int
          //   55	123	4	localDataInputStream	java.io.DataInputStream
          //   183	314	4	localIOException1	java.io.IOException
          //   523	1	4	localObject1	Object
          //   532	21	4	localEOFException1	java.io.EOFException
          //   560	3	4	localIOException2	java.io.IOException
          //   573	290	4	localObject2	Object
          //   870	23	4	localObject3	Object
          //   902	28	4	localStringBuilder1	StringBuilder
          //   34	465	5	localObject4	Object
          //   528	7	5	localNoClassDefFoundError1	NoClassDefFoundError
          //   540	338	5	localObject5	Object
          //   885	3	5	localIOException3	java.io.IOException
          //   31	445	6	localObject6	Object
          //   491	10	6	localObject7	Object
          //   503	1	6	localNoClassDefFoundError2	NoClassDefFoundError
          //   508	1	6	localOutOfMemoryError1	OutOfMemoryError
          //   513	1	6	localException1	Exception
          //   518	13	6	localEOFException2	java.io.EOFException
          //   536	7	6	localObject8	Object
          //   570	76	6	localOutOfMemoryError2	OutOfMemoryError
          //   663	91	6	localException2	Exception
          //   771	64	6	localEOFException3	java.io.EOFException
          //   12	796	7	localFile	File
          //   245	605	8	localObject9	Object
          //   258	56	9	localStringBuilder2	StringBuilder
          // Exception table:
          //   from	to	target	type
          //   177	182	183	java/io/IOException
          //   363	421	491	finally
          //   493	496	491	finally
          //   61	70	503	java/lang/NoClassDefFoundError
          //   74	87	503	java/lang/NoClassDefFoundError
          //   91	100	503	java/lang/NoClassDefFoundError
          //   104	112	503	java/lang/NoClassDefFoundError
          //   116	123	503	java/lang/NoClassDefFoundError
          //   127	135	503	java/lang/NoClassDefFoundError
          //   139	151	503	java/lang/NoClassDefFoundError
          //   155	165	503	java/lang/NoClassDefFoundError
          //   169	177	503	java/lang/NoClassDefFoundError
          //   195	205	503	java/lang/NoClassDefFoundError
          //   209	219	503	java/lang/NoClassDefFoundError
          //   223	229	503	java/lang/NoClassDefFoundError
          //   240	247	503	java/lang/NoClassDefFoundError
          //   251	260	503	java/lang/NoClassDefFoundError
          //   264	272	503	java/lang/NoClassDefFoundError
          //   276	283	503	java/lang/NoClassDefFoundError
          //   287	295	503	java/lang/NoClassDefFoundError
          //   299	307	503	java/lang/NoClassDefFoundError
          //   311	321	503	java/lang/NoClassDefFoundError
          //   325	336	503	java/lang/NoClassDefFoundError
          //   347	356	503	java/lang/NoClassDefFoundError
          //   360	363	503	java/lang/NoClassDefFoundError
          //   425	434	503	java/lang/NoClassDefFoundError
          //   438	446	503	java/lang/NoClassDefFoundError
          //   450	457	503	java/lang/NoClassDefFoundError
          //   461	469	503	java/lang/NoClassDefFoundError
          //   473	483	503	java/lang/NoClassDefFoundError
          //   500	503	503	java/lang/NoClassDefFoundError
          //   61	70	508	java/lang/OutOfMemoryError
          //   74	87	508	java/lang/OutOfMemoryError
          //   91	100	508	java/lang/OutOfMemoryError
          //   104	112	508	java/lang/OutOfMemoryError
          //   116	123	508	java/lang/OutOfMemoryError
          //   127	135	508	java/lang/OutOfMemoryError
          //   139	151	508	java/lang/OutOfMemoryError
          //   155	165	508	java/lang/OutOfMemoryError
          //   169	177	508	java/lang/OutOfMemoryError
          //   195	205	508	java/lang/OutOfMemoryError
          //   209	219	508	java/lang/OutOfMemoryError
          //   223	229	508	java/lang/OutOfMemoryError
          //   240	247	508	java/lang/OutOfMemoryError
          //   251	260	508	java/lang/OutOfMemoryError
          //   264	272	508	java/lang/OutOfMemoryError
          //   276	283	508	java/lang/OutOfMemoryError
          //   287	295	508	java/lang/OutOfMemoryError
          //   299	307	508	java/lang/OutOfMemoryError
          //   311	321	508	java/lang/OutOfMemoryError
          //   325	336	508	java/lang/OutOfMemoryError
          //   347	356	508	java/lang/OutOfMemoryError
          //   360	363	508	java/lang/OutOfMemoryError
          //   425	434	508	java/lang/OutOfMemoryError
          //   438	446	508	java/lang/OutOfMemoryError
          //   450	457	508	java/lang/OutOfMemoryError
          //   461	469	508	java/lang/OutOfMemoryError
          //   473	483	508	java/lang/OutOfMemoryError
          //   500	503	508	java/lang/OutOfMemoryError
          //   61	70	513	java/lang/Exception
          //   74	87	513	java/lang/Exception
          //   91	100	513	java/lang/Exception
          //   104	112	513	java/lang/Exception
          //   116	123	513	java/lang/Exception
          //   127	135	513	java/lang/Exception
          //   139	151	513	java/lang/Exception
          //   155	165	513	java/lang/Exception
          //   169	177	513	java/lang/Exception
          //   195	205	513	java/lang/Exception
          //   209	219	513	java/lang/Exception
          //   223	229	513	java/lang/Exception
          //   240	247	513	java/lang/Exception
          //   251	260	513	java/lang/Exception
          //   264	272	513	java/lang/Exception
          //   276	283	513	java/lang/Exception
          //   287	295	513	java/lang/Exception
          //   299	307	513	java/lang/Exception
          //   311	321	513	java/lang/Exception
          //   325	336	513	java/lang/Exception
          //   347	356	513	java/lang/Exception
          //   360	363	513	java/lang/Exception
          //   425	434	513	java/lang/Exception
          //   438	446	513	java/lang/Exception
          //   450	457	513	java/lang/Exception
          //   461	469	513	java/lang/Exception
          //   473	483	513	java/lang/Exception
          //   500	503	513	java/lang/Exception
          //   61	70	518	java/io/EOFException
          //   74	87	518	java/io/EOFException
          //   91	100	518	java/io/EOFException
          //   104	112	518	java/io/EOFException
          //   116	123	518	java/io/EOFException
          //   127	135	518	java/io/EOFException
          //   139	151	518	java/io/EOFException
          //   155	165	518	java/io/EOFException
          //   169	177	518	java/io/EOFException
          //   195	205	518	java/io/EOFException
          //   209	219	518	java/io/EOFException
          //   223	229	518	java/io/EOFException
          //   240	247	518	java/io/EOFException
          //   251	260	518	java/io/EOFException
          //   264	272	518	java/io/EOFException
          //   276	283	518	java/io/EOFException
          //   287	295	518	java/io/EOFException
          //   299	307	518	java/io/EOFException
          //   311	321	518	java/io/EOFException
          //   325	336	518	java/io/EOFException
          //   347	356	518	java/io/EOFException
          //   360	363	518	java/io/EOFException
          //   425	434	518	java/io/EOFException
          //   438	446	518	java/io/EOFException
          //   450	457	518	java/io/EOFException
          //   461	469	518	java/io/EOFException
          //   473	483	518	java/io/EOFException
          //   500	503	518	java/io/EOFException
          //   36	57	523	finally
          //   542	547	523	finally
          //   36	57	528	java/lang/NoClassDefFoundError
          //   483	488	560	java/io/IOException
          //   552	557	560	java/io/IOException
          //   655	660	560	java/io/IOException
          //   763	768	560	java/io/IOException
          //   862	867	560	java/io/IOException
          //   36	57	570	java/lang/OutOfMemoryError
          //   36	57	663	java/lang/Exception
          //   36	57	771	java/io/EOFException
          //   61	70	870	finally
          //   74	87	870	finally
          //   91	100	870	finally
          //   104	112	870	finally
          //   116	123	870	finally
          //   127	135	870	finally
          //   139	151	870	finally
          //   155	165	870	finally
          //   169	177	870	finally
          //   195	205	870	finally
          //   209	219	870	finally
          //   223	229	870	finally
          //   240	247	870	finally
          //   251	260	870	finally
          //   264	272	870	finally
          //   276	283	870	finally
          //   287	295	870	finally
          //   299	307	870	finally
          //   311	321	870	finally
          //   325	336	870	finally
          //   347	356	870	finally
          //   360	363	870	finally
          //   425	434	870	finally
          //   438	446	870	finally
          //   450	457	870	finally
          //   461	469	870	finally
          //   473	483	870	finally
          //   500	503	870	finally
          //   579	588	870	finally
          //   592	600	870	finally
          //   604	615	870	finally
          //   619	627	870	finally
          //   631	641	870	finally
          //   645	650	870	finally
          //   672	681	870	finally
          //   685	693	870	finally
          //   697	708	870	finally
          //   712	720	870	finally
          //   724	735	870	finally
          //   739	749	870	finally
          //   753	758	870	finally
          //   780	789	870	finally
          //   793	801	870	finally
          //   805	816	870	finally
          //   820	828	870	finally
          //   832	843	870	finally
          //   847	857	870	finally
          //   877	882	885	java/io/IOException
        }
      });
      return;
    }
  }
  
  protected void notifyLoadEnd()
  {
    synchronized (this.dataLock)
    {
      if ((TbsWupManager.getInstance().isNewDay()) && (((this.preDataList != null) && (this.preDataList.size() > 0)) || (!this.mCurrentDataAllNew)))
      {
        LogUtils.d("TBSPageLoadInfo", "this is a new day, and we have some data, upload");
        upload();
      }
      if ((this.preDataList != null) && (this.preDataList.size() >= 5))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("this is not a new day since last boot, Oh, yes, we have got ");
        localStringBuilder.append(this.preDataList.size());
        localStringBuilder.append(" stat files, upload");
        LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
        upload();
        return;
      }
      return;
    }
  }
  
  public void onReportTBSPageLoadInfo(final String paramString, final long paramLong)
  {
    RecorderWorkerThread localRecorderWorkerThread = this.mRecorderWorker;
    if (localRecorderWorkerThread == null) {
      return;
    }
    localRecorderWorkerThread.post(new Runnable()
    {
      public void run()
      {
        if (TBSPageLoadInfoManager.this.getCurrentData() == null)
        {
          LogUtils.d("TBSPageLoadInfo", "save pli info but getCurrentData=null");
          return;
        }
        Object localObject = QBUrlUtils.getStatDomain(paramString);
        if ((localObject != null) && ((((String)localObject).equalsIgnoreCase("circle.html5.qq.com")) || (((String)localObject).equalsIgnoreCase("v.html5.qq.com"))))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append("|");
          ((StringBuilder)localObject).append(paramLong);
          localObject = ((StringBuilder)localObject).toString();
        }
        else
        {
          if (localObject == null) {
            return;
          }
          localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject);
          localStringBuilder.append("|");
          localStringBuilder.append(paramLong);
          localObject = localStringBuilder.toString();
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReportTBSPageLoadInfo info=");
        localStringBuilder.append((String)localObject);
        LogUtils.d("TBSPageLoadInfo", localStringBuilder.toString());
        TBSPageLoadInfoManager.this.getCurrentData().mRecordInfos.add(localObject);
        TBSPageLoadInfoManager.access$302(TBSPageLoadInfoManager.this, false);
        TBSPageLoadInfoManager.access$402(TBSPageLoadInfoManager.this, true);
        if (TBSPageLoadInfoManager.this.getCurrentData().mRecordInfos.size() >= 20) {
          TBSPageLoadInfoUploader.getInstance(TBSPageLoadInfoManager.mAppContext).upload();
        }
        return;
      }
    });
  }
  
  public void save(PageLoadInfoData paramPageLoadInfoData)
  {
    LogUtils.d("TBSPageLoadInfo", " \tdo save()");
    paramPageLoadInfoData = new SaveWupDataRunnable(paramPageLoadInfoData.copy());
    this.mRecorderWorker.post(paramPageLoadInfoData);
  }
  
  public boolean save()
  {
    if (this.mRecorderWorker == null) {
      return false;
    }
    LogUtils.d("TBSPageLoadInfo", "\tsave begin()");
    if ((!this.mCurrentDataAllNew) && (this.mHasDataChangedSinceLastSave))
    {
      this.mHasDataChangedSinceLastSave = false;
      save(getCurrentData());
      LogUtils.d("TBSPageLoadInfo", "\tsave done()");
      return true;
    }
    LogUtils.d("TBSPageLoadInfo", "\tsave return false because already saved");
    return false;
  }
  
  public void saveRunable()
  {
    RecorderWorkerThread localRecorderWorkerThread = this.mRecorderWorker;
    if (localRecorderWorkerThread == null) {
      return;
    }
    localRecorderWorkerThread.post(new Runnable()
    {
      public void run()
      {
        LogUtils.d("TBSPageLoadInfo", "\tsaveRunable");
        TBSPageLoadInfoManager.this.save();
      }
    });
  }
  
  public void upload()
  {
    RecorderWorkerThread localRecorderWorkerThread = this.mRecorderWorker;
    if (localRecorderWorkerThread == null) {
      return;
    }
    localRecorderWorkerThread.post(new Runnable()
    {
      public void run()
      {
        TBSPageLoadInfoUploader.getInstance(TBSPageLoadInfoManager.mAppContext).upload();
      }
    });
  }
  
  private class RecorderWorkerThread
  {
    private Handler mHandler = null;
    private Looper mLooper = null;
    
    public RecorderWorkerThread() {}
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.mHandler.post(paramRunnable);
    }
  }
  
  class SaveWupDataRunnable
    implements Runnable
  {
    PageLoadInfoData mPliData = null;
    
    public SaveWupDataRunnable(PageLoadInfoData paramPageLoadInfoData)
    {
      this.mPliData = paramPageLoadInfoData;
    }
    
    /* Error */
    public boolean doRun()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 22	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$SaveWupDataRunnable:mPliData	Lcom/tencent/tbs/common/stat/PageLoadInfoData;
      //   4: getfield 37	com/tencent/tbs/common/stat/PageLoadInfoData:mId	I
      //   7: bipush 10
      //   9: irem
      //   10: istore_1
      //   11: iload_1
      //   12: invokestatic 43	com/tencent/tbs/common/utils/TbsFileUtils:getTBSinfoFile	(I)Ljava/io/File;
      //   15: astore 6
      //   17: new 45	java/lang/StringBuilder
      //   20: dup
      //   21: invokespecial 46	java/lang/StringBuilder:<init>	()V
      //   24: astore_2
      //   25: aload_2
      //   26: ldc 48
      //   28: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   31: pop
      //   32: aload_2
      //   33: invokestatic 58	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   36: invokevirtual 62	java/lang/Thread:getName	()Ljava/lang/String;
      //   39: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   42: pop
      //   43: aload_2
      //   44: ldc 64
      //   46: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   49: pop
      //   50: aload_2
      //   51: aload 6
      //   53: invokevirtual 69	java/io/File:getPath	()Ljava/lang/String;
      //   56: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   59: pop
      //   60: aload_2
      //   61: ldc 71
      //   63: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   66: pop
      //   67: ldc 73
      //   69: aload_2
      //   70: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   73: invokestatic 82	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   76: aload 6
      //   78: ifnull +378 -> 456
      //   81: aconst_null
      //   82: astore 4
      //   84: aconst_null
      //   85: astore 5
      //   87: aconst_null
      //   88: astore_3
      //   89: aload_3
      //   90: astore_2
      //   91: aload 6
      //   93: invokevirtual 85	java/io/File:exists	()Z
      //   96: ifne +58 -> 154
      //   99: aload_3
      //   100: astore_2
      //   101: new 45	java/lang/StringBuilder
      //   104: dup
      //   105: invokespecial 46	java/lang/StringBuilder:<init>	()V
      //   108: astore 7
      //   110: aload_3
      //   111: astore_2
      //   112: aload 7
      //   114: ldc 87
      //   116: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   119: pop
      //   120: aload_3
      //   121: astore_2
      //   122: aload 7
      //   124: invokestatic 58	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   127: invokevirtual 62	java/lang/Thread:getName	()Ljava/lang/String;
      //   130: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   133: pop
      //   134: aload_3
      //   135: astore_2
      //   136: ldc 73
      //   138: aload 7
      //   140: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   143: invokestatic 82	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   146: aload_3
      //   147: astore_2
      //   148: aload 6
      //   150: invokevirtual 90	java/io/File:createNewFile	()Z
      //   153: pop
      //   154: aload_3
      //   155: astore_2
      //   156: new 92	java/io/DataOutputStream
      //   159: dup
      //   160: new 94	java/io/BufferedOutputStream
      //   163: dup
      //   164: aload 6
      //   166: invokestatic 100	com/tencent/common/utils/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
      //   169: invokespecial 103	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   172: invokespecial 104	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   175: astore_3
      //   176: aload_3
      //   177: aload_0
      //   178: getfield 22	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$SaveWupDataRunnable:mPliData	Lcom/tencent/tbs/common/stat/PageLoadInfoData;
      //   181: getfield 108	com/tencent/tbs/common/stat/PageLoadInfoData:version	Ljava/lang/String;
      //   184: invokevirtual 112	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
      //   187: aload_3
      //   188: aload_0
      //   189: getfield 22	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$SaveWupDataRunnable:mPliData	Lcom/tencent/tbs/common/stat/PageLoadInfoData;
      //   192: getfield 37	com/tencent/tbs/common/stat/PageLoadInfoData:mId	I
      //   195: invokevirtual 116	java/io/DataOutputStream:writeInt	(I)V
      //   198: aload_3
      //   199: aload_0
      //   200: getfield 22	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$SaveWupDataRunnable:mPliData	Lcom/tencent/tbs/common/stat/PageLoadInfoData;
      //   203: getfield 120	com/tencent/tbs/common/stat/PageLoadInfoData:mTime	J
      //   206: invokevirtual 124	java/io/DataOutputStream:writeLong	(J)V
      //   209: aload_3
      //   210: aload_0
      //   211: getfield 22	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$SaveWupDataRunnable:mPliData	Lcom/tencent/tbs/common/stat/PageLoadInfoData;
      //   214: getfield 128	com/tencent/tbs/common/stat/PageLoadInfoData:mRecordInfos	Ljava/util/ArrayList;
      //   217: invokevirtual 134	java/util/ArrayList:size	()I
      //   220: invokevirtual 137	java/io/DataOutputStream:writeShort	(I)V
      //   223: aload_0
      //   224: getfield 22	com/tencent/tbs/common/stat/TBSPageLoadInfoManager$SaveWupDataRunnable:mPliData	Lcom/tencent/tbs/common/stat/PageLoadInfoData;
      //   227: getfield 128	com/tencent/tbs/common/stat/PageLoadInfoData:mRecordInfos	Ljava/util/ArrayList;
      //   230: invokevirtual 141	java/util/ArrayList:iterator	()Ljava/util/Iterator;
      //   233: astore_2
      //   234: aload_2
      //   235: invokeinterface 146 1 0
      //   240: ifeq +101 -> 341
      //   243: aload_2
      //   244: invokeinterface 150 1 0
      //   249: checkcast 152	java/lang/String
      //   252: astore 4
      //   254: aload 4
      //   256: ifnonnull +6 -> 262
      //   259: goto -25 -> 234
      //   262: aload_3
      //   263: aload 4
      //   265: invokevirtual 112	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
      //   268: new 45	java/lang/StringBuilder
      //   271: dup
      //   272: invokespecial 46	java/lang/StringBuilder:<init>	()V
      //   275: astore 5
      //   277: aload 5
      //   279: ldc -102
      //   281: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   284: pop
      //   285: aload 5
      //   287: iload_1
      //   288: invokevirtual 157	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   291: pop
      //   292: aload 5
      //   294: ldc -97
      //   296: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   299: pop
      //   300: aload 5
      //   302: invokestatic 58	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   305: invokevirtual 62	java/lang/Thread:getName	()Ljava/lang/String;
      //   308: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   311: pop
      //   312: aload 5
      //   314: ldc -95
      //   316: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   319: pop
      //   320: aload 5
      //   322: aload 4
      //   324: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   327: pop
      //   328: ldc 73
      //   330: aload 5
      //   332: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   335: invokestatic 82	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   338: goto -104 -> 234
      //   341: aload_3
      //   342: invokevirtual 164	java/io/DataOutputStream:flush	()V
      //   345: aload_3
      //   346: invokevirtual 167	java/io/DataOutputStream:close	()V
      //   349: iconst_1
      //   350: ireturn
      //   351: astore_2
      //   352: aload_2
      //   353: invokevirtual 170	java/io/IOException:printStackTrace	()V
      //   356: iconst_1
      //   357: ireturn
      //   358: astore_2
      //   359: goto +79 -> 438
      //   362: astore 4
      //   364: goto +25 -> 389
      //   367: astore 4
      //   369: goto +43 -> 412
      //   372: astore 4
      //   374: aload_2
      //   375: astore_3
      //   376: aload 4
      //   378: astore_2
      //   379: goto +59 -> 438
      //   382: astore_2
      //   383: aload 4
      //   385: astore_3
      //   386: aload_2
      //   387: astore 4
      //   389: aload_3
      //   390: astore_2
      //   391: aload 4
      //   393: invokevirtual 171	java/lang/OutOfMemoryError:printStackTrace	()V
      //   396: aload_3
      //   397: ifnull +59 -> 456
      //   400: aload_3
      //   401: invokevirtual 167	java/io/DataOutputStream:close	()V
      //   404: goto +52 -> 456
      //   407: astore 4
      //   409: aload 5
      //   411: astore_3
      //   412: aload_3
      //   413: astore_2
      //   414: aload 4
      //   416: invokevirtual 172	java/lang/Exception:printStackTrace	()V
      //   419: aload_3
      //   420: ifnull +36 -> 456
      //   423: aload_3
      //   424: invokevirtual 167	java/io/DataOutputStream:close	()V
      //   427: goto +29 -> 456
      //   430: astore_2
      //   431: aload_2
      //   432: invokevirtual 170	java/io/IOException:printStackTrace	()V
      //   435: goto +21 -> 456
      //   438: aload_3
      //   439: ifnull +15 -> 454
      //   442: aload_3
      //   443: invokevirtual 167	java/io/DataOutputStream:close	()V
      //   446: goto +8 -> 454
      //   449: astore_3
      //   450: aload_3
      //   451: invokevirtual 170	java/io/IOException:printStackTrace	()V
      //   454: aload_2
      //   455: athrow
      //   456: iconst_0
      //   457: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	458	0	this	SaveWupDataRunnable
      //   10	278	1	i	int
      //   24	220	2	localObject1	Object
      //   351	2	2	localIOException1	java.io.IOException
      //   358	17	2	localObject2	Object
      //   378	1	2	localObject3	Object
      //   382	5	2	localOutOfMemoryError1	OutOfMemoryError
      //   390	24	2	localObject4	Object
      //   430	25	2	localIOException2	java.io.IOException
      //   88	355	3	localObject5	Object
      //   449	2	3	localIOException3	java.io.IOException
      //   82	241	4	str	String
      //   362	1	4	localOutOfMemoryError2	OutOfMemoryError
      //   367	1	4	localException1	Exception
      //   372	12	4	localObject6	Object
      //   387	5	4	localOutOfMemoryError3	OutOfMemoryError
      //   407	8	4	localException2	Exception
      //   85	325	5	localStringBuilder1	StringBuilder
      //   15	150	6	localFile	File
      //   108	31	7	localStringBuilder2	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   345	349	351	java/io/IOException
      //   176	234	358	finally
      //   234	254	358	finally
      //   262	338	358	finally
      //   341	345	358	finally
      //   176	234	362	java/lang/OutOfMemoryError
      //   234	254	362	java/lang/OutOfMemoryError
      //   262	338	362	java/lang/OutOfMemoryError
      //   341	345	362	java/lang/OutOfMemoryError
      //   176	234	367	java/lang/Exception
      //   234	254	367	java/lang/Exception
      //   262	338	367	java/lang/Exception
      //   341	345	367	java/lang/Exception
      //   91	99	372	finally
      //   101	110	372	finally
      //   112	120	372	finally
      //   122	134	372	finally
      //   136	146	372	finally
      //   148	154	372	finally
      //   156	176	372	finally
      //   391	396	372	finally
      //   414	419	372	finally
      //   91	99	382	java/lang/OutOfMemoryError
      //   101	110	382	java/lang/OutOfMemoryError
      //   112	120	382	java/lang/OutOfMemoryError
      //   122	134	382	java/lang/OutOfMemoryError
      //   136	146	382	java/lang/OutOfMemoryError
      //   148	154	382	java/lang/OutOfMemoryError
      //   156	176	382	java/lang/OutOfMemoryError
      //   91	99	407	java/lang/Exception
      //   101	110	407	java/lang/Exception
      //   112	120	407	java/lang/Exception
      //   122	134	407	java/lang/Exception
      //   136	146	407	java/lang/Exception
      //   148	154	407	java/lang/Exception
      //   156	176	407	java/lang/Exception
      //   400	404	430	java/io/IOException
      //   423	427	430	java/io/IOException
      //   442	446	449	java/io/IOException
    }
    
    public void run()
    {
      if (doRun())
      {
        LogUtils.d("TBSPageLoadInfo", "SaveStatData first save ok, good");
        return;
      }
      LogUtils.d("TBSPageLoadInfo", "SaveStatData first save fail, try again");
      if (doRun())
      {
        LogUtils.d("TBSPageLoadInfo", "SaveStatData second save ok, good");
        return;
      }
      LogUtils.d("TBSPageLoadInfo", "SaveStatData second save fail, this time realy fail");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\TBSPageLoadInfoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */