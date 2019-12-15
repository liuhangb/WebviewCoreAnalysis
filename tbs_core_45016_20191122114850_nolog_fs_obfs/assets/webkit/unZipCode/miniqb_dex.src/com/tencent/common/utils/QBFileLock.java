package com.tencent.common.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.basesupport.FLogger;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class QBFileLock
  implements Runnable
{
  public static String TAG = "QBFileLock";
  private static Handler jdField_a_of_type_AndroidOsHandler = null;
  private static Object jdField_a_of_type_JavaLangObject = new Object();
  private static HashMap<QBFileLock, Object> jdField_a_of_type_JavaUtilHashMap;
  private static Object b = new Object();
  long jdField_a_of_type_Long = 0L;
  File jdField_a_of_type_JavaIoFile = null;
  RandomAccessFile jdField_a_of_type_JavaIoRandomAccessFile = null;
  FileLock jdField_a_of_type_JavaNioChannelsFileLock = null;
  
  static
  {
    jdField_a_of_type_JavaUtilHashMap = null;
  }
  
  public QBFileLock(File paramFile, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString);
    localStringBuilder.append(".lock");
    this.jdField_a_of_type_JavaIoFile = new File(paramFile, localStringBuilder.toString());
  }
  
  public QBFileLock(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(".lock");
    this.jdField_a_of_type_JavaIoFile = new File(paramString1, localStringBuilder.toString());
  }
  
  public static void releaseLocks()
  {
    synchronized (b)
    {
      if ((jdField_a_of_type_JavaUtilHashMap != null) && (!jdField_a_of_type_JavaUtilHashMap.isEmpty()))
      {
        Iterator localIterator = jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
        while (localIterator.hasNext()) {
          ((QBFileLock)((Map.Entry)localIterator.next()).getKey()).releaseLock(false);
        }
        jdField_a_of_type_JavaUtilHashMap.clear();
        return;
      }
      return;
    }
  }
  
  Handler a()
  {
    if (jdField_a_of_type_AndroidOsHandler == null) {
      try
      {
        if (jdField_a_of_type_AndroidOsHandler == null)
        {
          Looper localLooper = BrowserExecutorSupplier.getLooperForRunShortTime();
          Object localObject1 = localLooper;
          if (localLooper == null)
          {
            localObject1 = new HandlerThread("QBFileLock.Thread");
            ((HandlerThread)localObject1).start();
            localObject1 = ((HandlerThread)localObject1).getLooper();
          }
          jdField_a_of_type_AndroidOsHandler = new Handler((Looper)localObject1);
        }
      }
      finally {}
    }
    return jdField_a_of_type_AndroidOsHandler;
  }
  
  void a()
  {
    synchronized (b)
    {
      if (jdField_a_of_type_JavaUtilHashMap == null) {
        jdField_a_of_type_JavaUtilHashMap = new HashMap();
      }
      jdField_a_of_type_JavaUtilHashMap.put(this, jdField_a_of_type_JavaLangObject);
      return;
    }
  }
  
  void b()
  {
    synchronized (b)
    {
      if (jdField_a_of_type_JavaUtilHashMap == null) {
        return;
      }
      jdField_a_of_type_JavaUtilHashMap.remove(this);
      return;
    }
  }
  
  /* Error */
  public void lock()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: new 144	java/io/RandomAccessFile
    //   6: dup
    //   7: aload_0
    //   8: getfield 37	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaIoFile	Ljava/io/File;
    //   11: ldc -110
    //   13: invokespecial 147	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   16: putfield 39	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   19: goto +12 -> 31
    //   22: astore_3
    //   23: goto +210 -> 233
    //   26: astore_3
    //   27: aload_3
    //   28: invokevirtual 150	java/lang/Exception:printStackTrace	()V
    //   31: aload_0
    //   32: getfield 39	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   35: ifnull +184 -> 219
    //   38: aload_0
    //   39: getfield 39	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   42: invokevirtual 154	java/io/RandomAccessFile:getChannel	()Ljava/nio/channels/FileChannel;
    //   45: astore 6
    //   47: aload 6
    //   49: ifnull +170 -> 219
    //   52: aload_0
    //   53: getfield 43	com/tencent/common/utils/QBFileLock:jdField_a_of_type_Long	J
    //   56: lconst_0
    //   57: lcmp
    //   58: ifle +16 -> 74
    //   61: aload_0
    //   62: invokevirtual 156	com/tencent/common/utils/QBFileLock:a	()Landroid/os/Handler;
    //   65: aload_0
    //   66: aload_0
    //   67: getfield 43	com/tencent/common/utils/QBFileLock:jdField_a_of_type_Long	J
    //   70: invokevirtual 160	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
    //   73: pop
    //   74: aconst_null
    //   75: astore 4
    //   77: invokestatic 166	java/lang/System:currentTimeMillis	()J
    //   80: lstore_1
    //   81: aload 6
    //   83: invokevirtual 171	java/nio/channels/FileChannel:lock	()Ljava/nio/channels/FileLock;
    //   86: astore 5
    //   88: aload 5
    //   90: astore_3
    //   91: aload 5
    //   93: ifnull +25 -> 118
    //   96: aload 5
    //   98: astore_3
    //   99: goto +53 -> 152
    //   102: astore_3
    //   103: aload_3
    //   104: invokevirtual 150	java/lang/Exception:printStackTrace	()V
    //   107: getstatic 173	com/tencent/common/utils/QBFileLock:TAG	Ljava/lang/String;
    //   110: ldc -81
    //   112: invokestatic 180	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   115: aload 4
    //   117: astore_3
    //   118: ldc2_w 181
    //   121: invokestatic 188	java/lang/Thread:sleep	(J)V
    //   124: goto +10 -> 134
    //   127: astore 4
    //   129: aload 4
    //   131: invokevirtual 189	java/lang/InterruptedException:printStackTrace	()V
    //   134: aload_3
    //   135: astore 4
    //   137: invokestatic 166	java/lang/System:currentTimeMillis	()J
    //   140: lload_1
    //   141: lsub
    //   142: invokestatic 195	java/lang/Math:abs	(J)J
    //   145: ldc2_w 196
    //   148: lcmp
    //   149: iflt -68 -> 81
    //   152: aload_0
    //   153: aload_3
    //   154: putfield 41	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaNioChannelsFileLock	Ljava/nio/channels/FileLock;
    //   157: getstatic 173	com/tencent/common/utils/QBFileLock:TAG	Ljava/lang/String;
    //   160: astore_3
    //   161: new 45	java/lang/StringBuilder
    //   164: dup
    //   165: invokespecial 46	java/lang/StringBuilder:<init>	()V
    //   168: astore 4
    //   170: aload 4
    //   172: ldc -57
    //   174: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload 4
    //   180: aload_0
    //   181: getfield 37	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaIoFile	Ljava/io/File;
    //   184: invokevirtual 202	java/io/File:getName	()Ljava/lang/String;
    //   187: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: pop
    //   191: aload 4
    //   193: ldc -52
    //   195: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: pop
    //   199: aload 4
    //   201: invokestatic 166	java/lang/System:currentTimeMillis	()J
    //   204: lload_1
    //   205: lsub
    //   206: invokevirtual 207	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   209: pop
    //   210: aload_3
    //   211: aload 4
    //   213: invokevirtual 60	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   216: invokestatic 180	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   219: aload_0
    //   220: getfield 41	com/tencent/common/utils/QBFileLock:jdField_a_of_type_JavaNioChannelsFileLock	Ljava/nio/channels/FileLock;
    //   223: ifnull +7 -> 230
    //   226: aload_0
    //   227: invokevirtual 209	com/tencent/common/utils/QBFileLock:a	()V
    //   230: aload_0
    //   231: monitorexit
    //   232: return
    //   233: aload_0
    //   234: monitorexit
    //   235: aload_3
    //   236: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	QBFileLock
    //   80	125	1	l	long
    //   22	1	3	localObject1	Object
    //   26	2	3	localException1	Exception
    //   90	9	3	localObject2	Object
    //   102	2	3	localException2	Exception
    //   117	119	3	localObject3	Object
    //   75	41	4	localObject4	Object
    //   127	3	4	localInterruptedException	InterruptedException
    //   135	77	4	localObject5	Object
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
    //   137	152	22	finally
    //   152	219	22	finally
    //   219	230	22	finally
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
      localStringBuilder.append(this.jdField_a_of_type_JavaIoFile.getName());
      FLogger.d((String)localObject1, localStringBuilder.toString());
      localObject1 = this.jdField_a_of_type_JavaNioChannelsFileLock;
      if (localObject1 != null)
      {
        try
        {
          this.jdField_a_of_type_JavaNioChannelsFileLock.release();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        this.jdField_a_of_type_JavaNioChannelsFileLock = null;
      }
      RandomAccessFile localRandomAccessFile = this.jdField_a_of_type_JavaIoRandomAccessFile;
      if (localRandomAccessFile != null)
      {
        try
        {
          this.jdField_a_of_type_JavaIoRandomAccessFile.close();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
        this.jdField_a_of_type_JavaIoRandomAccessFile = null;
      }
      if ((jdField_a_of_type_AndroidOsHandler != null) && (this.jdField_a_of_type_Long > 0L)) {
        jdField_a_of_type_AndroidOsHandler.removeCallbacks(this);
      }
      if (paramBoolean) {
        b();
      }
      return;
    }
    finally {}
  }
  
  public void run()
  {
    FLogger.d(TAG, ">>> releaseLock on TimeOut");
    releaseLock();
  }
  
  public void setTimeOut(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QBFileLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */