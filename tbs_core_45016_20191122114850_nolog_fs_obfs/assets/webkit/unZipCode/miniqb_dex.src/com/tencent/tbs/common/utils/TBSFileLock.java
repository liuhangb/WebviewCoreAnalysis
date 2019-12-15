package com.tencent.tbs.common.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class TBSFileLock
  implements Runnable
{
  public static String TAG = "TBSFileLock";
  private static Handler sHandler = null;
  private static HashMap<TBSFileLock, Object> sInstances;
  private static Object sLock;
  private static Object sObject = new Object();
  File mFile = null;
  FileLock mFileLock = null;
  RandomAccessFile mRandomAccessFile = null;
  long mTimeOut = 0L;
  
  static
  {
    sLock = new Object();
    sInstances = null;
  }
  
  public TBSFileLock(File paramFile, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString);
    localStringBuilder.append(".lock");
    this.mFile = new File(paramFile, localStringBuilder.toString());
  }
  
  public TBSFileLock(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(".lock");
    this.mFile = new File(paramString1, localStringBuilder.toString());
  }
  
  public static void releaseLocks()
  {
    synchronized (sLock)
    {
      if ((sInstances != null) && (!sInstances.isEmpty()))
      {
        Iterator localIterator = sInstances.entrySet().iterator();
        while (localIterator.hasNext()) {
          ((TBSFileLock)((Map.Entry)localIterator.next()).getKey()).releaseLock(false);
        }
        sInstances.clear();
        return;
      }
      return;
    }
  }
  
  void cacheLock()
  {
    synchronized (sLock)
    {
      if (sInstances == null) {
        sInstances = new HashMap();
      }
      sInstances.put(this, sObject);
      return;
    }
  }
  
  Handler getHandler()
  {
    if (sHandler == null) {
      try
      {
        if (sHandler == null)
        {
          Looper localLooper = BrowserExecutorSupplier.getLooperForRunShortTime();
          Object localObject1 = localLooper;
          if (localLooper == null)
          {
            localObject1 = new HandlerThread("QBFileLock.Thread");
            ((HandlerThread)localObject1).start();
            localObject1 = ((HandlerThread)localObject1).getLooper();
          }
          sHandler = new Handler((Looper)localObject1);
        }
      }
      finally {}
    }
    return sHandler;
  }
  
  /* Error */
  public void lock()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: new 148	java/io/RandomAccessFile
    //   6: dup
    //   7: aload_0
    //   8: getfield 43	com/tencent/tbs/common/utils/TBSFileLock:mFile	Ljava/io/File;
    //   11: ldc -106
    //   13: invokespecial 151	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   16: putfield 45	com/tencent/tbs/common/utils/TBSFileLock:mRandomAccessFile	Ljava/io/RandomAccessFile;
    //   19: goto +12 -> 31
    //   22: astore_3
    //   23: goto +218 -> 241
    //   26: astore_3
    //   27: aload_3
    //   28: invokevirtual 154	java/lang/Exception:printStackTrace	()V
    //   31: aload_0
    //   32: getfield 45	com/tencent/tbs/common/utils/TBSFileLock:mRandomAccessFile	Ljava/io/RandomAccessFile;
    //   35: ifnull +192 -> 227
    //   38: aload_0
    //   39: getfield 45	com/tencent/tbs/common/utils/TBSFileLock:mRandomAccessFile	Ljava/io/RandomAccessFile;
    //   42: invokevirtual 158	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   45: astore 6
    //   47: aload 6
    //   49: ifnull +178 -> 227
    //   52: aload_0
    //   53: getfield 49	com/tencent/tbs/common/utils/TBSFileLock:mTimeOut	J
    //   56: lconst_0
    //   57: lcmp
    //   58: ifle +16 -> 74
    //   61: aload_0
    //   62: invokevirtual 160	com/tencent/tbs/common/utils/TBSFileLock:getHandler	()Landroid/os/Handler;
    //   65: aload_0
    //   66: aload_0
    //   67: getfield 49	com/tencent/tbs/common/utils/TBSFileLock:mTimeOut	J
    //   70: invokevirtual 164	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
    //   73: pop
    //   74: aconst_null
    //   75: astore 4
    //   77: invokestatic 170	java/lang/System:currentTimeMillis	()J
    //   80: lstore_1
    //   81: aload 6
    //   83: invokevirtual 175	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   86: astore 5
    //   88: aload 5
    //   90: astore_3
    //   91: aload 5
    //   93: ifnull +25 -> 118
    //   96: aload 5
    //   98: astore_3
    //   99: goto +61 -> 160
    //   102: astore_3
    //   103: aload_3
    //   104: invokevirtual 154	java/lang/Exception:printStackTrace	()V
    //   107: getstatic 177	com/tencent/tbs/common/utils/TBSFileLock:TAG	Ljava/lang/String;
    //   110: ldc -77
    //   112: invokestatic 184	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   115: aload 4
    //   117: astore_3
    //   118: ldc2_w 185
    //   121: invokestatic 192	java/lang/Thread:sleep	(J)V
    //   124: goto +10 -> 134
    //   127: astore 4
    //   129: aload 4
    //   131: invokevirtual 193	java/lang/InterruptedException:printStackTrace	()V
    //   134: aload_3
    //   135: astore 4
    //   137: invokestatic 170	java/lang/System:currentTimeMillis	()J
    //   140: lload_1
    //   141: lsub
    //   142: invokestatic 199	java/lang/Math:abs	(J)J
    //   145: ldc2_w 200
    //   148: lcmp
    //   149: iflt -68 -> 81
    //   152: getstatic 177	com/tencent/tbs/common/utils/TBSFileLock:TAG	Ljava/lang/String;
    //   155: ldc -53
    //   157: invokestatic 184	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   160: aload_0
    //   161: aload_3
    //   162: putfield 47	com/tencent/tbs/common/utils/TBSFileLock:mFileLock	Ljava/nio/channels/FileLock;
    //   165: getstatic 177	com/tencent/tbs/common/utils/TBSFileLock:TAG	Ljava/lang/String;
    //   168: astore_3
    //   169: new 51	java/lang/StringBuilder
    //   172: dup
    //   173: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   176: astore 4
    //   178: aload 4
    //   180: ldc -51
    //   182: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: pop
    //   186: aload 4
    //   188: aload_0
    //   189: getfield 43	com/tencent/tbs/common/utils/TBSFileLock:mFile	Ljava/io/File;
    //   192: invokevirtual 208	java/io/File:getName	()Ljava/lang/String;
    //   195: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload 4
    //   201: ldc -46
    //   203: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload 4
    //   209: invokestatic 170	java/lang/System:currentTimeMillis	()J
    //   212: lload_1
    //   213: lsub
    //   214: invokevirtual 213	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   217: pop
    //   218: aload_3
    //   219: aload 4
    //   221: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   224: invokestatic 184	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   227: aload_0
    //   228: getfield 47	com/tencent/tbs/common/utils/TBSFileLock:mFileLock	Ljava/nio/channels/FileLock;
    //   231: ifnull +7 -> 238
    //   234: aload_0
    //   235: invokevirtual 215	com/tencent/tbs/common/utils/TBSFileLock:cacheLock	()V
    //   238: aload_0
    //   239: monitorexit
    //   240: return
    //   241: aload_0
    //   242: monitorexit
    //   243: aload_3
    //   244: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	245	0	this	TBSFileLock
    //   80	133	1	l	long
    //   22	1	3	localObject1	Object
    //   26	2	3	localException1	Exception
    //   90	9	3	localObject2	Object
    //   102	2	3	localException2	Exception
    //   117	127	3	localObject3	Object
    //   75	41	4	localObject4	Object
    //   127	3	4	localInterruptedException	InterruptedException
    //   135	85	4	localObject5	Object
    //   86	11	5	localFileLock	FileLock
    //   45	37	6	localFileChannel	java.nio.channels.FileChannel
    // Exception table:
    //   from	to	target	type
    //   2	19	22	finally
    //   27	31	22	finally
    //   31	47	22	finally
    //   52	74	22	finally
    //   77	81	22	finally
    //   81	88	22	finally
    //   103	115	22	finally
    //   118	124	22	finally
    //   129	134	22	finally
    //   137	160	22	finally
    //   160	227	22	finally
    //   227	238	22	finally
    //   2	19	26	java/lang/Exception
    //   81	88	102	java/lang/Exception
    //   118	124	127	java/lang/InterruptedException
  }
  
  public void releaseLock()
  {
    releaseLock(true);
  }
  
  public void releaseLock(boolean paramBoolean)
  {
    try
    {
      Object localObject1 = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(">>> release lock: ");
      localStringBuilder.append(this.mFile.getName());
      LogUtils.d((String)localObject1, localStringBuilder.toString());
      localObject1 = this.mFileLock;
      if (localObject1 != null)
      {
        try
        {
          this.mFileLock.release();
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
        this.mFileLock = null;
      }
      RandomAccessFile localRandomAccessFile = this.mRandomAccessFile;
      if (localRandomAccessFile != null)
      {
        try
        {
          this.mRandomAccessFile.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        this.mRandomAccessFile = null;
      }
      if ((sHandler != null) && (this.mTimeOut > 0L)) {
        sHandler.removeCallbacks(this);
      }
      if (paramBoolean) {
        removeCache();
      }
      return;
    }
    finally {}
  }
  
  void removeCache()
  {
    synchronized (sLock)
    {
      if (sInstances == null) {
        return;
      }
      sInstances.remove(this);
      return;
    }
  }
  
  public void run()
  {
    LogUtils.d(TAG, ">>> releaseLock on TimeOut");
    releaseLock();
  }
  
  public void setTimeOut(long paramLong)
  {
    this.mTimeOut = paramLong;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\TBSFileLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */