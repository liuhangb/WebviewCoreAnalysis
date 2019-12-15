package com.tencent.downloadprovider;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.manifest.EventEmiter;
import com.tencent.common.manifest.EventMessage;
import com.tencent.common.utils.DBUtils;
import com.tencent.mtt.base.utils.DLReporter;
import com.tencent.mtt.base.utils.DLReporterManager;
import com.tencent.mtt.browser.download.engine.DownloadSpeedData;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class QBDownloadProvider
{
  static a a;
  public static String a = "QBDownloadProvider";
  
  static boolean a(a parama)
  {
    a locala = a;
    if (locala != null) {
      return locala.a(parama);
    }
    return false;
  }
  
  private static class TaskInsertException
    extends RuntimeException
  {
    public TaskInsertException(String paramString)
    {
      super();
    }
  }
  
  static abstract interface a
  {
    public abstract boolean a(a parama);
  }
  
  public static class b
  {
    QBDownloadProvider.d a;
    
    public b(Context paramContext, String paramString, int paramInt, boolean paramBoolean)
    {
      this.a = new QBDownloadProvider.d(paramContext, paramString, null, paramInt, paramBoolean);
    }
    
    public int a(String paramString, ContentValues paramContentValues)
      throws Exception
    {
      return DBUtils.insert(a(), paramString, paramContentValues);
    }
    
    public int a(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
      throws Exception
    {
      return DBUtils.update(a(), paramString1, paramContentValues, paramString2, paramArrayOfString);
    }
    
    public int a(String paramString1, String paramString2, String[] paramArrayOfString)
      throws Exception
    {
      return DBUtils.delete(a(), paramString1, paramString2, paramArrayOfString);
    }
    
    public Cursor a(String paramString)
      throws Exception
    {
      return DBUtils.query(a(), paramString);
    }
    
    public Cursor a(String paramString1, String paramString2, String paramString3)
      throws Exception
    {
      return DBUtils.query(a(), paramString1, paramString2, paramString3);
    }
    
    public Cursor a(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3)
      throws Exception
    {
      return DBUtils.query(a(), paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, paramString3);
    }
    
    public SQLiteDatabase a()
      throws Exception
    {
      return this.a.getWritableDatabase();
    }
    
    public void a()
      throws Exception
    {
      DBUtils.beginTransaction(a());
    }
    
    public void b()
      throws Exception
    {
      DBUtils.endTransaction(a());
    }
  }
  
  public static class c
  {
    static c jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$c;
    int jdField_a_of_type_Int = 0;
    Handler.Callback jdField_a_of_type_AndroidOsHandler$Callback = new Handler.Callback()
    {
      public boolean handleMessage(Message paramAnonymousMessage)
      {
        int i;
        switch (paramAnonymousMessage.what)
        {
        default: 
          return true;
        case 4: 
          if (QBDownloadProvider.c.this.a == null)
          {
            FLogger.d("ZHTAG", "mDBHelper is null, syn");
            return true;
          }
          try
          {
            QBDownloadProvider.c.this.a.b();
            return true;
          }
          catch (Exception paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return true;
          }
        case 3: 
          if (QBDownloadProvider.c.this.a == null)
          {
            FLogger.d("ZHTAG", "mDBHelper is null, syn");
            return true;
          }
          try
          {
            QBDownloadProvider.c.this.a.a();
            return true;
          }
          catch (Exception paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return true;
          }
        case 2: 
          if (QBDownloadProvider.c.this.a == null)
          {
            FLogger.d("ZHTAG", "mDBHelper is null, syn");
            return true;
          }
          try
          {
            i = paramAnonymousMessage.arg1;
            paramAnonymousMessage = (ContentValues)paramAnonymousMessage.obj;
            QBDownloadProvider.c.this.a.a("download", paramAnonymousMessage, "id=?", new String[] { String.valueOf(i) });
            return true;
          }
          catch (Exception paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return true;
          }
        case 1: 
          if (QBDownloadProvider.c.this.a == null)
          {
            FLogger.d("ZHTAG", "mDBHelper is null, syn");
            return true;
          }
          try
          {
            i = paramAnonymousMessage.arg1;
            QBDownloadProvider.c.this.a.a("download", "id=?", new String[] { String.valueOf(i) });
            return true;
          }
          catch (Exception paramAnonymousMessage)
          {
            paramAnonymousMessage.printStackTrace();
            return true;
          }
        }
        if (QBDownloadProvider.c.this.a == null)
        {
          FLogger.d("ZHTAG", "mDBHelper is null, syn");
          return true;
        }
        try
        {
          paramAnonymousMessage = (ContentValues)paramAnonymousMessage.obj;
          QBDownloadProvider.c.this.a.a("download", paramAnonymousMessage);
          return true;
        }
        catch (Exception paramAnonymousMessage)
        {
          paramAnonymousMessage.printStackTrace();
        }
        return true;
      }
    };
    Handler jdField_a_of_type_AndroidOsHandler;
    QBDownloadProvider.b jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$b;
    public QBDownloadProvider.b b;
    
    c(Context paramContext)
    {
      long l1 = System.currentTimeMillis();
      if (a(paramContext))
      {
        this.jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$b = new QBDownloadProvider.b(paramContext, "download_database", 2, false);
        FLogger.d("ZHTAG", "will put data to file");
      }
      this.b = new QBDownloadProvider.b(paramContext, null, 1, true);
      a();
      long l2 = System.currentTimeMillis();
      paramContext = new StringBuilder();
      paramContext.append("DownloadDbInitUseTime=");
      paramContext.append(l2 - l1);
      FLogger.d("DownloadDBHelper", paramContext.toString());
      paramContext = new HandlerThread("WriteToFileDbThread");
      paramContext.start();
      try
      {
        for (;;)
        {
          Looper localLooper = paramContext.getLooper();
          if (localLooper != null) {
            break;
          }
          try
          {
            wait();
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
        }
        this.jdField_a_of_type_AndroidOsHandler = new Handler(paramContext.getLooper(), this.jdField_a_of_type_AndroidOsHandler$Callback);
        return;
      }
      finally {}
    }
    
    public static c a(Context paramContext)
    {
      try
      {
        if (jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$c == null) {
          jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$c = new c(paramContext);
        }
        paramContext = jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$c;
        return paramContext;
      }
      finally {}
    }
    
    private boolean a(Context paramContext)
    {
      paramContext = a(paramContext);
      if ("com.tencent.mtt".equals(paramContext)) {
        return true;
      }
      if ("com.tencent.mm:tools".equals(paramContext)) {
        return true;
      }
      return "com.tencent.mobileqq:tool".equals(paramContext);
    }
    
    /* Error */
    int a()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 25	com/tencent/downloadprovider/QBDownloadProvider$c:jdField_a_of_type_Int	I
      //   4: ifne +94 -> 98
      //   7: aconst_null
      //   8: astore_1
      //   9: aconst_null
      //   10: astore_2
      //   11: aload_0
      //   12: getfield 60	com/tencent/downloadprovider/QBDownloadProvider$c:b	Lcom/tencent/downloadprovider/QBDownloadProvider$b;
      //   15: ldc -123
      //   17: invokevirtual 136	com/tencent/downloadprovider/QBDownloadProvider$b:a	(Ljava/lang/String;)Landroid/database/Cursor;
      //   20: astore_3
      //   21: aload_3
      //   22: ifnull +31 -> 53
      //   25: aload_3
      //   26: astore_2
      //   27: aload_3
      //   28: astore_1
      //   29: aload_3
      //   30: invokeinterface 142 1 0
      //   35: ifeq +18 -> 53
      //   38: aload_3
      //   39: astore_2
      //   40: aload_3
      //   41: astore_1
      //   42: aload_0
      //   43: aload_3
      //   44: iconst_0
      //   45: invokeinterface 146 2 0
      //   50: putfield 25	com/tencent/downloadprovider/QBDownloadProvider$c:jdField_a_of_type_Int	I
      //   53: aload_3
      //   54: ifnull +44 -> 98
      //   57: aload_3
      //   58: astore_1
      //   59: goto +18 -> 77
      //   62: astore_1
      //   63: goto +23 -> 86
      //   66: astore_3
      //   67: aload_1
      //   68: astore_2
      //   69: aload_3
      //   70: invokevirtual 147	java/lang/Exception:printStackTrace	()V
      //   73: aload_1
      //   74: ifnull +24 -> 98
      //   77: aload_1
      //   78: invokeinterface 150 1 0
      //   83: goto +15 -> 98
      //   86: aload_2
      //   87: ifnull +9 -> 96
      //   90: aload_2
      //   91: invokeinterface 150 1 0
      //   96: aload_1
      //   97: athrow
      //   98: aload_0
      //   99: aload_0
      //   100: getfield 25	com/tencent/downloadprovider/QBDownloadProvider$c:jdField_a_of_type_Int	I
      //   103: iconst_1
      //   104: iadd
      //   105: putfield 25	com/tencent/downloadprovider/QBDownloadProvider$c:jdField_a_of_type_Int	I
      //   108: aload_0
      //   109: getfield 25	com/tencent/downloadprovider/QBDownloadProvider$c:jdField_a_of_type_Int	I
      //   112: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	113	0	this	c
      //   8	51	1	localObject1	Object
      //   62	35	1	localObject2	Object
      //   10	81	2	localObject3	Object
      //   20	38	3	localCursor	Cursor
      //   66	4	3	localException	Exception
      // Exception table:
      //   from	to	target	type
      //   11	21	62	finally
      //   29	38	62	finally
      //   42	53	62	finally
      //   69	73	62	finally
      //   11	21	66	java/lang/Exception
      //   29	38	66	java/lang/Exception
      //   42	53	66	java/lang/Exception
    }
    
    public int a(int paramInt)
    {
      int i = -1;
      try
      {
        int j = this.b.a("download", "id=?", new String[] { String.valueOf(paramInt) });
        i = j;
        Message localMessage = new Message();
        i = j;
        localMessage.what = 1;
        i = j;
        localMessage.arg1 = paramInt;
        i = j;
        this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
        return j;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return i;
    }
    
    int a(ContentValues paramContentValues, int paramInt, boolean paramBoolean)
    {
      if (paramContentValues == null) {
        return 0;
      }
      int i = -1;
      try
      {
        int j = this.b.a("download", paramContentValues, "id=?", new String[] { String.valueOf(paramInt) });
        i = j;
        Object localObject = new Message();
        i = j;
        ((Message)localObject).what = 2;
        i = j;
        ((Message)localObject).arg1 = paramInt;
        i = j;
        ((Message)localObject).obj = paramContentValues;
        i = j;
        this.jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject);
        i = j;
        localObject = new a();
        i = j;
        ((a)localObject).jdField_a_of_type_ComTencentDownloadproviderA$a = a.a.b;
        i = j;
        ((a)localObject).jdField_a_of_type_Int = paramInt;
        i = j;
        ((a)localObject).jdField_a_of_type_JavaLangString = paramContentValues.getAsString("filename");
        i = j;
        if (paramBoolean)
        {
          i = j;
          QBDownloadProvider.a((a)localObject);
          return j;
        }
      }
      catch (Exception paramContentValues)
      {
        paramContentValues.printStackTrace();
      }
      return i;
    }
    
    public int a(ContentValues paramContentValues, DLReporter paramDLReporter)
    {
      if (paramContentValues == null) {
        return -1;
      }
      if (paramDLReporter != null) {
        paramDLReporter.addStep(":A11[");
      }
      int i = a();
      if (paramDLReporter != null) {}
      try
      {
        paramDLReporter.addStep("-1");
        paramContentValues.put("id", Integer.valueOf(i));
        this.b.a("download", paramContentValues);
        if (paramDLReporter != null)
        {
          paramDLReporter.setId(i);
          DLReporterManager.addReporter(paramDLReporter);
        }
        Object localObject = new Message();
        ((Message)localObject).what = 0;
        ((Message)localObject).obj = paramContentValues;
        this.jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject);
        localObject = new a();
        ((a)localObject).jdField_a_of_type_ComTencentDownloadproviderA$a = a.a.jdField_a_of_type_ComTencentDownloadproviderA$a;
        ((a)localObject).jdField_a_of_type_Int = i;
        ((a)localObject).jdField_a_of_type_JavaLangString = paramContentValues.getAsString("filename");
        boolean bool = QBDownloadProvider.a((a)localObject);
        if (paramDLReporter != null)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("(");
          ((StringBuilder)localObject).append(bool);
          ((StringBuilder)localObject).append(")");
          paramDLReporter.addStep(((StringBuilder)localObject).toString());
        }
      }
      catch (Exception localException)
      {
        if (paramDLReporter != null) {
          paramDLReporter.addStep("-2");
        }
        localException.printStackTrace();
        StringBuilder localStringBuilder = new StringBuilder();
        if (TextUtils.isEmpty(localException.getMessage())) {
          str = "NULL";
        } else {
          str = str.getMessage();
        }
        localStringBuilder.append(str);
        localStringBuilder.append("\n CONTENT_VALUE:");
        String str = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(str);
        localStringBuilder.append(paramContentValues.toString());
        paramContentValues = localStringBuilder.toString();
        paramContentValues = new EventMessage("com.tencent.mtt.external.rqd.RQDManager.handleCatchException", new Object[] { Thread.currentThread(), new QBDownloadProvider.TaskInsertException("DOWNLOAD_ADD_TASK Error"), paramContentValues });
        EventEmiter.getDefault().emit(paramContentValues);
      }
      if (paramDLReporter != null) {
        paramDLReporter.addStep("-3]");
      }
      return i;
    }
    
    public Cursor a()
      throws Exception
    {
      QBDownloadProvider.b localb = this.jdField_a_of_type_ComTencentDownloadproviderQBDownloadProvider$b;
      if (localb == null)
      {
        FLogger.d("ZHTAG", "mDBHelper is null, getAllDownloadListFromFileDB");
        return null;
      }
      return localb.a("download", null, null);
    }
    
    String a(Context paramContext)
    {
      int i = Process.myPid();
      paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      while (paramContext.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)paramContext.next();
        if (localRunningAppProcessInfo.pid == i) {
          return localRunningAppProcessInfo.processName;
        }
      }
      return null;
    }
    
    /* Error */
    void a()
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_2
      //   2: aconst_null
      //   3: astore_1
      //   4: aload_0
      //   5: invokevirtual 349	com/tencent/downloadprovider/QBDownloadProvider$c:a	()Landroid/database/Cursor;
      //   8: astore_3
      //   9: aload_3
      //   10: ifnull +56 -> 66
      //   13: aload_3
      //   14: astore_1
      //   15: aload_3
      //   16: astore_2
      //   17: aload_0
      //   18: getfield 60	com/tencent/downloadprovider/QBDownloadProvider$c:b	Lcom/tencent/downloadprovider/QBDownloadProvider$b;
      //   21: invokevirtual 350	com/tencent/downloadprovider/QBDownloadProvider$b:a	()V
      //   24: aload_3
      //   25: astore_1
      //   26: aload_3
      //   27: astore_2
      //   28: aload_3
      //   29: invokeinterface 353 1 0
      //   34: ifeq +32 -> 66
      //   37: aload_3
      //   38: astore_1
      //   39: aload_3
      //   40: astore_2
      //   41: aload_3
      //   42: invokestatic 359	com/tencent/mtt/base/utils/DLConvertTools:cursor2ContentValues	(Landroid/database/Cursor;)Landroid/content/ContentValues;
      //   45: astore 4
      //   47: aload_3
      //   48: astore_1
      //   49: aload_3
      //   50: astore_2
      //   51: aload_0
      //   52: getfield 60	com/tencent/downloadprovider/QBDownloadProvider$c:b	Lcom/tencent/downloadprovider/QBDownloadProvider$b;
      //   55: ldc -104
      //   57: aload 4
      //   59: invokevirtual 234	com/tencent/downloadprovider/QBDownloadProvider$b:a	(Ljava/lang/String;Landroid/content/ContentValues;)I
      //   62: pop
      //   63: goto -39 -> 24
      //   66: aload_3
      //   67: ifnull +9 -> 76
      //   70: aload_3
      //   71: invokeinterface 150 1 0
      //   76: aload_0
      //   77: getfield 60	com/tencent/downloadprovider/QBDownloadProvider$c:b	Lcom/tencent/downloadprovider/QBDownloadProvider$b;
      //   80: invokevirtual 361	com/tencent/downloadprovider/QBDownloadProvider$b:b	()V
      //   83: return
      //   84: astore_1
      //   85: aload_1
      //   86: invokevirtual 147	java/lang/Exception:printStackTrace	()V
      //   89: return
      //   90: astore_2
      //   91: goto +28 -> 119
      //   94: astore_3
      //   95: aload_2
      //   96: astore_1
      //   97: aload_3
      //   98: invokevirtual 147	java/lang/Exception:printStackTrace	()V
      //   101: aload_2
      //   102: ifnull +9 -> 111
      //   105: aload_2
      //   106: invokeinterface 150 1 0
      //   111: aload_0
      //   112: getfield 60	com/tencent/downloadprovider/QBDownloadProvider$c:b	Lcom/tencent/downloadprovider/QBDownloadProvider$b;
      //   115: invokevirtual 361	com/tencent/downloadprovider/QBDownloadProvider$b:b	()V
      //   118: return
      //   119: aload_1
      //   120: ifnull +9 -> 129
      //   123: aload_1
      //   124: invokeinterface 150 1 0
      //   129: aload_0
      //   130: getfield 60	com/tencent/downloadprovider/QBDownloadProvider$c:b	Lcom/tencent/downloadprovider/QBDownloadProvider$b;
      //   133: invokevirtual 361	com/tencent/downloadprovider/QBDownloadProvider$b:b	()V
      //   136: goto +8 -> 144
      //   139: astore_1
      //   140: aload_1
      //   141: invokevirtual 147	java/lang/Exception:printStackTrace	()V
      //   144: aload_2
      //   145: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	146	0	this	c
      //   3	46	1	localObject1	Object
      //   84	2	1	localException1	Exception
      //   96	28	1	localObject2	Object
      //   139	2	1	localException2	Exception
      //   1	50	2	localObject3	Object
      //   90	55	2	localObject4	Object
      //   8	63	3	localCursor	Cursor
      //   94	4	3	localException3	Exception
      //   45	13	4	localContentValues	ContentValues
      // Exception table:
      //   from	to	target	type
      //   76	83	84	java/lang/Exception
      //   111	118	84	java/lang/Exception
      //   4	9	90	finally
      //   17	24	90	finally
      //   28	37	90	finally
      //   41	47	90	finally
      //   51	63	90	finally
      //   97	101	90	finally
      //   4	9	94	java/lang/Exception
      //   17	24	94	java/lang/Exception
      //   28	37	94	java/lang/Exception
      //   41	47	94	java/lang/Exception
      //   51	63	94	java/lang/Exception
      //   129	136	139	java/lang/Exception
    }
    
    public void b()
    {
      try
      {
        this.b.b();
        Message localMessage = new Message();
        localMessage.what = 4;
        this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    
    public void c()
    {
      try
      {
        this.b.a();
        Message localMessage = new Message();
        localMessage.what = 3;
        this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public static class d
    extends SQLiteOpenHelper
  {
    boolean a = false;
    
    public d(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt, boolean paramBoolean)
    {
      super(paramString, paramCursorFactory, paramInt);
      this.a = paramBoolean;
    }
    
    /* Error */
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 16	com/tencent/downloadprovider/QBDownloadProvider$d:a	Z
      //   4: ifeq +935 -> 939
      //   7: new 23	java/lang/StringBuffer
      //   10: dup
      //   11: invokespecial 26	java/lang/StringBuffer:<init>	()V
      //   14: astore 4
      //   16: aload 4
      //   18: ldc 28
      //   20: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   23: pop
      //   24: aload 4
      //   26: ldc 34
      //   28: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   31: pop
      //   32: aload 4
      //   34: ldc 36
      //   36: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   39: pop
      //   40: aload 4
      //   42: ldc 38
      //   44: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   47: pop
      //   48: aload 4
      //   50: ldc 40
      //   52: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   55: pop
      //   56: aload 4
      //   58: ldc 42
      //   60: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   63: pop
      //   64: aload 4
      //   66: ldc 44
      //   68: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   71: pop
      //   72: aload 4
      //   74: ldc 46
      //   76: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   79: pop
      //   80: aload 4
      //   82: ldc 44
      //   84: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   87: pop
      //   88: aload 4
      //   90: ldc 48
      //   92: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   95: pop
      //   96: aload 4
      //   98: ldc 44
      //   100: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   103: pop
      //   104: aload 4
      //   106: ldc 50
      //   108: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   111: pop
      //   112: aload 4
      //   114: ldc 52
      //   116: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   119: pop
      //   120: aload 4
      //   122: ldc 54
      //   124: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   127: pop
      //   128: aload 4
      //   130: ldc 52
      //   132: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   135: pop
      //   136: aload 4
      //   138: ldc 56
      //   140: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   143: pop
      //   144: aload 4
      //   146: ldc 52
      //   148: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   151: pop
      //   152: aload 4
      //   154: ldc 58
      //   156: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   159: pop
      //   160: aload 4
      //   162: ldc 60
      //   164: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   167: pop
      //   168: aload 4
      //   170: ldc 62
      //   172: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   175: pop
      //   176: aload 4
      //   178: ldc 64
      //   180: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   183: pop
      //   184: aload 4
      //   186: ldc 66
      //   188: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   191: pop
      //   192: aload 4
      //   194: ldc 64
      //   196: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   199: pop
      //   200: aload 4
      //   202: ldc 68
      //   204: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   207: pop
      //   208: aload 4
      //   210: ldc 64
      //   212: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   215: pop
      //   216: aload 4
      //   218: ldc 70
      //   220: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   223: pop
      //   224: aload 4
      //   226: ldc 72
      //   228: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   231: pop
      //   232: aload 4
      //   234: ldc 74
      //   236: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   239: pop
      //   240: aload 4
      //   242: ldc 76
      //   244: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   247: pop
      //   248: aload 4
      //   250: ldc 78
      //   252: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   255: pop
      //   256: aload 4
      //   258: ldc 64
      //   260: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   263: pop
      //   264: aload 4
      //   266: ldc 80
      //   268: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   271: pop
      //   272: aload 4
      //   274: ldc 44
      //   276: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   279: pop
      //   280: aload 4
      //   282: ldc 82
      //   284: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   287: pop
      //   288: aload 4
      //   290: ldc 76
      //   292: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   295: pop
      //   296: aload 4
      //   298: ldc 84
      //   300: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   303: pop
      //   304: aload 4
      //   306: ldc 44
      //   308: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   311: pop
      //   312: aload 4
      //   314: ldc 86
      //   316: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   319: pop
      //   320: aload 4
      //   322: ldc 44
      //   324: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   327: pop
      //   328: aload 4
      //   330: ldc 88
      //   332: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   335: pop
      //   336: aload 4
      //   338: ldc 90
      //   340: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   343: pop
      //   344: aload 4
      //   346: ldc 92
      //   348: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   351: pop
      //   352: aload 4
      //   354: ldc 90
      //   356: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   359: pop
      //   360: aload 4
      //   362: ldc 94
      //   364: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   367: pop
      //   368: aload 4
      //   370: ldc 90
      //   372: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   375: pop
      //   376: aload 4
      //   378: ldc 96
      //   380: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   383: pop
      //   384: aload 4
      //   386: ldc 90
      //   388: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   391: pop
      //   392: aload 4
      //   394: ldc 98
      //   396: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   399: pop
      //   400: aload 4
      //   402: ldc 90
      //   404: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   407: pop
      //   408: aload 4
      //   410: ldc 100
      //   412: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   415: pop
      //   416: aload 4
      //   418: ldc 90
      //   420: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   423: pop
      //   424: aload 4
      //   426: ldc 102
      //   428: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   431: pop
      //   432: aload 4
      //   434: ldc 90
      //   436: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   439: pop
      //   440: aload 4
      //   442: ldc 104
      //   444: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   447: pop
      //   448: aload 4
      //   450: ldc 90
      //   452: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   455: pop
      //   456: aload 4
      //   458: ldc 106
      //   460: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   463: pop
      //   464: aload 4
      //   466: ldc 90
      //   468: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   471: pop
      //   472: aload 4
      //   474: ldc 108
      //   476: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   479: pop
      //   480: aload 4
      //   482: ldc 110
      //   484: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   487: pop
      //   488: aload 4
      //   490: ldc 112
      //   492: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   495: pop
      //   496: aload 4
      //   498: ldc 114
      //   500: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   503: pop
      //   504: aload 4
      //   506: ldc 116
      //   508: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   511: pop
      //   512: aload 4
      //   514: ldc 118
      //   516: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   519: pop
      //   520: aload 4
      //   522: ldc 120
      //   524: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   527: pop
      //   528: aload 4
      //   530: ldc 114
      //   532: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   535: pop
      //   536: aload 4
      //   538: ldc 122
      //   540: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   543: pop
      //   544: aload 4
      //   546: ldc 114
      //   548: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   551: pop
      //   552: aload 4
      //   554: ldc 124
      //   556: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   559: pop
      //   560: aload 4
      //   562: ldc 118
      //   564: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   567: pop
      //   568: aload 4
      //   570: ldc 126
      //   572: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   575: pop
      //   576: aload 4
      //   578: ldc 114
      //   580: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   583: pop
      //   584: aload 4
      //   586: ldc -128
      //   588: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   591: pop
      //   592: aload 4
      //   594: ldc 114
      //   596: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   599: pop
      //   600: aload 4
      //   602: ldc -126
      //   604: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   607: pop
      //   608: aload 4
      //   610: ldc 118
      //   612: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   615: pop
      //   616: aload 4
      //   618: ldc -124
      //   620: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   623: pop
      //   624: aload 4
      //   626: ldc 90
      //   628: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   631: pop
      //   632: aload 4
      //   634: ldc -122
      //   636: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   639: pop
      //   640: aload 4
      //   642: ldc -120
      //   644: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   647: pop
      //   648: aload 4
      //   650: ldc -118
      //   652: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   655: pop
      //   656: aload 4
      //   658: ldc -120
      //   660: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   663: pop
      //   664: aload 4
      //   666: ldc -116
      //   668: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   671: pop
      //   672: aload 4
      //   674: ldc -120
      //   676: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   679: pop
      //   680: aload 4
      //   682: ldc -114
      //   684: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   687: pop
      //   688: aload 4
      //   690: ldc -120
      //   692: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   695: pop
      //   696: aload 4
      //   698: ldc -112
      //   700: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   703: pop
      //   704: aload 4
      //   706: ldc -110
      //   708: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   711: pop
      //   712: aload 4
      //   714: ldc -108
      //   716: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   719: pop
      //   720: aload 4
      //   722: ldc 44
      //   724: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   727: pop
      //   728: aload 4
      //   730: ldc -106
      //   732: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   735: pop
      //   736: aload 4
      //   738: ldc 44
      //   740: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   743: pop
      //   744: aload 4
      //   746: ldc -104
      //   748: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   751: pop
      //   752: aload 4
      //   754: ldc 44
      //   756: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   759: pop
      //   760: aload 4
      //   762: ldc -102
      //   764: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   767: pop
      //   768: aload 4
      //   770: ldc 44
      //   772: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   775: pop
      //   776: aload 4
      //   778: ldc -100
      //   780: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   783: pop
      //   784: aload 4
      //   786: ldc -98
      //   788: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   791: pop
      //   792: aload 4
      //   794: ldc -96
      //   796: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   799: pop
      //   800: aload 4
      //   802: ldc -94
      //   804: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   807: pop
      //   808: aload 4
      //   810: ldc -92
      //   812: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   815: pop
      //   816: aload 4
      //   818: ldc -90
      //   820: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   823: pop
      //   824: aload 4
      //   826: ldc -88
      //   828: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   831: pop
      //   832: aload_1
      //   833: aload 4
      //   835: invokevirtual 172	java/lang/StringBuffer:toString	()Ljava/lang/String;
      //   838: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   841: goto +48 -> 889
      //   844: astore 4
      //   846: new 180	java/lang/StringBuilder
      //   849: dup
      //   850: invokespecial 181	java/lang/StringBuilder:<init>	()V
      //   853: astore 5
      //   855: aload 5
      //   857: ldc -73
      //   859: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   862: pop
      //   863: aload 5
      //   865: aload 4
      //   867: invokevirtual 189	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   870: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   873: pop
      //   874: ldc -65
      //   876: aload 5
      //   878: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   881: invokestatic 197	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   884: aload 4
      //   886: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   889: aload_1
      //   890: ldc -54
      //   892: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   895: return
      //   896: astore_1
      //   897: new 180	java/lang/StringBuilder
      //   900: dup
      //   901: invokespecial 181	java/lang/StringBuilder:<init>	()V
      //   904: astore 4
      //   906: aload 4
      //   908: ldc -52
      //   910: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   913: pop
      //   914: aload 4
      //   916: aload_1
      //   917: invokevirtual 189	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   920: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   923: pop
      //   924: ldc -65
      //   926: aload 4
      //   928: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   931: invokestatic 197	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   934: aload_1
      //   935: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   938: return
      //   939: iconst_0
      //   940: istore_2
      //   941: aload_1
      //   942: ldc 34
      //   944: invokestatic 208	com/tencent/common/utils/DBUtils:exist	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
      //   947: istore_3
      //   948: iload_3
      //   949: istore_2
      //   950: goto +48 -> 998
      //   953: astore 4
      //   955: new 180	java/lang/StringBuilder
      //   958: dup
      //   959: invokespecial 181	java/lang/StringBuilder:<init>	()V
      //   962: astore 5
      //   964: aload 5
      //   966: ldc -46
      //   968: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   971: pop
      //   972: aload 5
      //   974: aload 4
      //   976: invokevirtual 189	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   979: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   982: pop
      //   983: ldc -65
      //   985: aload 5
      //   987: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   990: invokestatic 197	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   993: aload 4
      //   995: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   998: iload_2
      //   999: ifne +1085 -> 2084
      //   1002: new 23	java/lang/StringBuffer
      //   1005: dup
      //   1006: invokespecial 26	java/lang/StringBuffer:<init>	()V
      //   1009: astore 4
      //   1011: aload 4
      //   1013: ldc 28
      //   1015: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1018: pop
      //   1019: aload 4
      //   1021: ldc 34
      //   1023: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1026: pop
      //   1027: aload 4
      //   1029: ldc 36
      //   1031: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1034: pop
      //   1035: aload 4
      //   1037: ldc 38
      //   1039: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1042: pop
      //   1043: aload 4
      //   1045: ldc 40
      //   1047: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1050: pop
      //   1051: aload 4
      //   1053: ldc 42
      //   1055: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1058: pop
      //   1059: aload 4
      //   1061: ldc 44
      //   1063: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1066: pop
      //   1067: aload 4
      //   1069: ldc 46
      //   1071: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1074: pop
      //   1075: aload 4
      //   1077: ldc 44
      //   1079: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1082: pop
      //   1083: aload 4
      //   1085: ldc 48
      //   1087: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1090: pop
      //   1091: aload 4
      //   1093: ldc 44
      //   1095: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1098: pop
      //   1099: aload 4
      //   1101: ldc 50
      //   1103: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1106: pop
      //   1107: aload 4
      //   1109: ldc 52
      //   1111: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1114: pop
      //   1115: aload 4
      //   1117: ldc 54
      //   1119: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1122: pop
      //   1123: aload 4
      //   1125: ldc 52
      //   1127: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1130: pop
      //   1131: aload 4
      //   1133: ldc 56
      //   1135: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1138: pop
      //   1139: aload 4
      //   1141: ldc 52
      //   1143: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1146: pop
      //   1147: aload 4
      //   1149: ldc 58
      //   1151: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1154: pop
      //   1155: aload 4
      //   1157: ldc 60
      //   1159: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1162: pop
      //   1163: aload 4
      //   1165: ldc 62
      //   1167: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1170: pop
      //   1171: aload 4
      //   1173: ldc 64
      //   1175: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1178: pop
      //   1179: aload 4
      //   1181: ldc 66
      //   1183: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1186: pop
      //   1187: aload 4
      //   1189: ldc 64
      //   1191: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1194: pop
      //   1195: aload 4
      //   1197: ldc 68
      //   1199: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1202: pop
      //   1203: aload 4
      //   1205: ldc 64
      //   1207: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1210: pop
      //   1211: aload 4
      //   1213: ldc 70
      //   1215: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1218: pop
      //   1219: aload 4
      //   1221: ldc 72
      //   1223: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1226: pop
      //   1227: aload 4
      //   1229: ldc 74
      //   1231: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1234: pop
      //   1235: aload 4
      //   1237: ldc 76
      //   1239: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1242: pop
      //   1243: aload 4
      //   1245: ldc 78
      //   1247: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1250: pop
      //   1251: aload 4
      //   1253: ldc 64
      //   1255: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1258: pop
      //   1259: aload 4
      //   1261: ldc 80
      //   1263: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1266: pop
      //   1267: aload 4
      //   1269: ldc 44
      //   1271: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1274: pop
      //   1275: aload 4
      //   1277: ldc 82
      //   1279: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1282: pop
      //   1283: aload 4
      //   1285: ldc 76
      //   1287: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1290: pop
      //   1291: aload 4
      //   1293: ldc 84
      //   1295: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1298: pop
      //   1299: aload 4
      //   1301: ldc 44
      //   1303: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1306: pop
      //   1307: aload 4
      //   1309: ldc 86
      //   1311: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1314: pop
      //   1315: aload 4
      //   1317: ldc 44
      //   1319: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1322: pop
      //   1323: aload 4
      //   1325: ldc 88
      //   1327: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1330: pop
      //   1331: aload 4
      //   1333: ldc 90
      //   1335: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1338: pop
      //   1339: aload 4
      //   1341: ldc 92
      //   1343: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1346: pop
      //   1347: aload 4
      //   1349: ldc 90
      //   1351: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1354: pop
      //   1355: aload 4
      //   1357: ldc 94
      //   1359: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1362: pop
      //   1363: aload 4
      //   1365: ldc 90
      //   1367: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1370: pop
      //   1371: aload 4
      //   1373: ldc 96
      //   1375: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1378: pop
      //   1379: aload 4
      //   1381: ldc 90
      //   1383: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1386: pop
      //   1387: aload 4
      //   1389: ldc 98
      //   1391: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1394: pop
      //   1395: aload 4
      //   1397: ldc 90
      //   1399: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1402: pop
      //   1403: aload 4
      //   1405: ldc 100
      //   1407: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1410: pop
      //   1411: aload 4
      //   1413: ldc 90
      //   1415: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1418: pop
      //   1419: aload 4
      //   1421: ldc 102
      //   1423: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1426: pop
      //   1427: aload 4
      //   1429: ldc 90
      //   1431: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1434: pop
      //   1435: aload 4
      //   1437: ldc 104
      //   1439: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1442: pop
      //   1443: aload 4
      //   1445: ldc 90
      //   1447: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1450: pop
      //   1451: aload 4
      //   1453: ldc 106
      //   1455: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1458: pop
      //   1459: aload 4
      //   1461: ldc 90
      //   1463: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1466: pop
      //   1467: aload 4
      //   1469: ldc 108
      //   1471: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1474: pop
      //   1475: aload 4
      //   1477: ldc 110
      //   1479: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1482: pop
      //   1483: aload 4
      //   1485: ldc 112
      //   1487: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1490: pop
      //   1491: aload 4
      //   1493: ldc 114
      //   1495: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1498: pop
      //   1499: aload 4
      //   1501: ldc 116
      //   1503: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1506: pop
      //   1507: aload 4
      //   1509: ldc 118
      //   1511: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1514: pop
      //   1515: aload 4
      //   1517: ldc 120
      //   1519: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1522: pop
      //   1523: aload 4
      //   1525: ldc 114
      //   1527: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1530: pop
      //   1531: aload 4
      //   1533: ldc 122
      //   1535: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1538: pop
      //   1539: aload 4
      //   1541: ldc 114
      //   1543: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1546: pop
      //   1547: aload 4
      //   1549: ldc 124
      //   1551: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1554: pop
      //   1555: aload 4
      //   1557: ldc 118
      //   1559: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1562: pop
      //   1563: aload 4
      //   1565: ldc 126
      //   1567: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1570: pop
      //   1571: aload 4
      //   1573: ldc 114
      //   1575: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1578: pop
      //   1579: aload 4
      //   1581: ldc -128
      //   1583: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1586: pop
      //   1587: aload 4
      //   1589: ldc 114
      //   1591: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1594: pop
      //   1595: aload 4
      //   1597: ldc -126
      //   1599: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1602: pop
      //   1603: aload 4
      //   1605: ldc 118
      //   1607: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1610: pop
      //   1611: aload 4
      //   1613: ldc -124
      //   1615: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1618: pop
      //   1619: aload 4
      //   1621: ldc 90
      //   1623: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1626: pop
      //   1627: aload 4
      //   1629: ldc -122
      //   1631: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1634: pop
      //   1635: aload 4
      //   1637: ldc -120
      //   1639: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1642: pop
      //   1643: aload 4
      //   1645: ldc -118
      //   1647: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1650: pop
      //   1651: aload 4
      //   1653: ldc -120
      //   1655: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1658: pop
      //   1659: aload 4
      //   1661: ldc -116
      //   1663: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1666: pop
      //   1667: aload 4
      //   1669: ldc -120
      //   1671: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1674: pop
      //   1675: aload 4
      //   1677: ldc -114
      //   1679: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1682: pop
      //   1683: aload 4
      //   1685: ldc -120
      //   1687: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1690: pop
      //   1691: aload 4
      //   1693: ldc -112
      //   1695: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1698: pop
      //   1699: aload 4
      //   1701: ldc -110
      //   1703: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1706: pop
      //   1707: aload 4
      //   1709: ldc -108
      //   1711: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1714: pop
      //   1715: aload 4
      //   1717: ldc 44
      //   1719: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1722: pop
      //   1723: aload 4
      //   1725: ldc -106
      //   1727: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1730: pop
      //   1731: aload 4
      //   1733: ldc 44
      //   1735: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1738: pop
      //   1739: aload 4
      //   1741: ldc -104
      //   1743: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1746: pop
      //   1747: aload 4
      //   1749: ldc 44
      //   1751: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1754: pop
      //   1755: aload 4
      //   1757: ldc -102
      //   1759: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1762: pop
      //   1763: aload 4
      //   1765: ldc 44
      //   1767: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1770: pop
      //   1771: aload 4
      //   1773: ldc -100
      //   1775: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1778: pop
      //   1779: aload 4
      //   1781: ldc -98
      //   1783: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1786: pop
      //   1787: aload 4
      //   1789: ldc -96
      //   1791: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1794: pop
      //   1795: aload 4
      //   1797: ldc -94
      //   1799: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1802: pop
      //   1803: aload 4
      //   1805: ldc -92
      //   1807: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1810: pop
      //   1811: aload 4
      //   1813: ldc -90
      //   1815: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1818: pop
      //   1819: aload 4
      //   1821: ldc -88
      //   1823: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   1826: pop
      //   1827: aload_1
      //   1828: aload 4
      //   1830: invokevirtual 172	java/lang/StringBuffer:toString	()Ljava/lang/String;
      //   1833: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   1836: invokestatic 215	com/tencent/downloadprovider/DownloadproviderHelper:a	()Lcom/tencent/downloadprovider/DownloadproviderHelper$GetPublicDB;
      //   1839: ifnull +245 -> 2084
      //   1842: invokestatic 215	com/tencent/downloadprovider/DownloadproviderHelper:a	()Lcom/tencent/downloadprovider/DownloadproviderHelper$GetPublicDB;
      //   1845: invokeinterface 221 1 0
      //   1850: ldc 34
      //   1852: invokestatic 208	com/tencent/common/utils/DBUtils:exist	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
      //   1855: istore_2
      //   1856: iload_2
      //   1857: ifeq +227 -> 2084
      //   1860: aconst_null
      //   1861: astore 5
      //   1863: aconst_null
      //   1864: astore 4
      //   1866: invokestatic 215	com/tencent/downloadprovider/DownloadproviderHelper:a	()Lcom/tencent/downloadprovider/DownloadproviderHelper$GetPublicDB;
      //   1869: invokeinterface 221 1 0
      //   1874: ldc 34
      //   1876: aconst_null
      //   1877: aconst_null
      //   1878: invokestatic 225	com/tencent/common/utils/DBUtils:query	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   1881: astore 6
      //   1883: aload 6
      //   1885: ifnull +56 -> 1941
      //   1888: aload 6
      //   1890: astore 4
      //   1892: aload 6
      //   1894: astore 5
      //   1896: aload_1
      //   1897: invokestatic 228	com/tencent/common/utils/DBUtils:beginTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   1900: aload 6
      //   1902: astore 4
      //   1904: aload 6
      //   1906: astore 5
      //   1908: aload 6
      //   1910: invokeinterface 234 1 0
      //   1915: ifeq +26 -> 1941
      //   1918: aload 6
      //   1920: astore 4
      //   1922: aload 6
      //   1924: astore 5
      //   1926: aload_1
      //   1927: ldc 34
      //   1929: aload 6
      //   1931: invokestatic 240	com/tencent/mtt/base/utils/DLConvertTools:cursor2ContentValues	(Landroid/database/Cursor;)Landroid/content/ContentValues;
      //   1934: invokestatic 244	com/tencent/common/utils/DBUtils:insert	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Landroid/content/ContentValues;)I
      //   1937: pop
      //   1938: goto -38 -> 1900
      //   1941: aload 6
      //   1943: ifnull +10 -> 1953
      //   1946: aload 6
      //   1948: invokeinterface 247 1 0
      //   1953: aload_1
      //   1954: invokestatic 250	com/tencent/common/utils/DBUtils:endTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   1957: goto +127 -> 2084
      //   1960: astore 4
      //   1962: aload 4
      //   1964: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   1967: goto +117 -> 2084
      //   1970: astore 5
      //   1972: goto +38 -> 2010
      //   1975: astore 6
      //   1977: aload 5
      //   1979: astore 4
      //   1981: aload 6
      //   1983: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   1986: aload 5
      //   1988: ifnull +10 -> 1998
      //   1991: aload 5
      //   1993: invokeinterface 247 1 0
      //   1998: aload_1
      //   1999: invokestatic 250	com/tencent/common/utils/DBUtils:endTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   2002: goto +82 -> 2084
      //   2005: astore 4
      //   2007: goto -45 -> 1962
      //   2010: aload 4
      //   2012: ifnull +10 -> 2022
      //   2015: aload 4
      //   2017: invokeinterface 247 1 0
      //   2022: aload_1
      //   2023: invokestatic 250	com/tencent/common/utils/DBUtils:endTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   2026: goto +10 -> 2036
      //   2029: astore 4
      //   2031: aload 4
      //   2033: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   2036: aload 5
      //   2038: athrow
      //   2039: astore 4
      //   2041: new 180	java/lang/StringBuilder
      //   2044: dup
      //   2045: invokespecial 181	java/lang/StringBuilder:<init>	()V
      //   2048: astore 5
      //   2050: aload 5
      //   2052: ldc -4
      //   2054: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   2057: pop
      //   2058: aload 5
      //   2060: aload 4
      //   2062: invokevirtual 189	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   2065: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   2068: pop
      //   2069: ldc -65
      //   2071: aload 5
      //   2073: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   2076: invokestatic 197	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   2079: aload 4
      //   2081: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   2084: aload_1
      //   2085: ldc -54
      //   2087: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   2090: return
      //   2091: astore_1
      //   2092: new 180	java/lang/StringBuilder
      //   2095: dup
      //   2096: invokespecial 181	java/lang/StringBuilder:<init>	()V
      //   2099: astore 4
      //   2101: aload 4
      //   2103: ldc -2
      //   2105: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   2108: pop
      //   2109: aload 4
      //   2111: aload_1
      //   2112: invokevirtual 189	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   2115: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   2118: pop
      //   2119: ldc -65
      //   2121: aload 4
      //   2123: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   2126: invokestatic 197	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   2129: aload_1
      //   2130: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   2133: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	2134	0	this	d
      //   0	2134	1	paramSQLiteDatabase	SQLiteDatabase
      //   940	917	2	bool1	boolean
      //   947	2	3	bool2	boolean
      //   14	820	4	localStringBuffer	StringBuffer
      //   844	41	4	localException1	Exception
      //   904	23	4	localStringBuilder1	StringBuilder
      //   953	41	4	localException2	Exception
      //   1009	912	4	localObject1	Object
      //   1960	3	4	localException3	Exception
      //   1979	1	4	localObject2	Object
      //   2005	11	4	localException4	Exception
      //   2029	3	4	localException5	Exception
      //   2039	41	4	localException6	Exception
      //   2099	23	4	localStringBuilder2	StringBuilder
      //   853	1072	5	localObject3	Object
      //   1970	67	5	localObject4	Object
      //   2048	24	5	localStringBuilder3	StringBuilder
      //   1881	66	6	localCursor	Cursor
      //   1975	7	6	localException7	Exception
      // Exception table:
      //   from	to	target	type
      //   7	841	844	java/lang/Exception
      //   889	895	896	java/lang/Exception
      //   941	948	953	java/lang/Exception
      //   1953	1957	1960	java/lang/Exception
      //   1866	1883	1970	finally
      //   1896	1900	1970	finally
      //   1908	1918	1970	finally
      //   1926	1938	1970	finally
      //   1981	1986	1970	finally
      //   1866	1883	1975	java/lang/Exception
      //   1896	1900	1975	java/lang/Exception
      //   1908	1918	1975	java/lang/Exception
      //   1926	1938	1975	java/lang/Exception
      //   1998	2002	2005	java/lang/Exception
      //   2022	2026	2029	java/lang/Exception
      //   1002	1856	2039	java/lang/Exception
      //   1946	1953	2039	java/lang/Exception
      //   1962	1967	2039	java/lang/Exception
      //   1991	1998	2039	java/lang/Exception
      //   2015	2022	2039	java/lang/Exception
      //   2031	2036	2039	java/lang/Exception
      //   2036	2039	2039	java/lang/Exception
      //   2084	2090	2091	java/lang/Exception
    }
    
    public void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
    
    /* Error */
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      // Byte code:
      //   0: new 23	java/lang/StringBuffer
      //   3: dup
      //   4: invokespecial 26	java/lang/StringBuffer:<init>	()V
      //   7: astore 4
      //   9: aload 4
      //   11: ldc 28
      //   13: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   16: pop
      //   17: aload 4
      //   19: ldc 34
      //   21: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   24: pop
      //   25: aload 4
      //   27: ldc 36
      //   29: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   32: pop
      //   33: aload 4
      //   35: ldc 38
      //   37: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   40: pop
      //   41: aload 4
      //   43: ldc 40
      //   45: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   48: pop
      //   49: aload 4
      //   51: ldc 42
      //   53: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   56: pop
      //   57: aload 4
      //   59: ldc 44
      //   61: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   64: pop
      //   65: aload 4
      //   67: ldc 46
      //   69: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   72: pop
      //   73: aload 4
      //   75: ldc 44
      //   77: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   80: pop
      //   81: aload 4
      //   83: ldc 48
      //   85: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   88: pop
      //   89: aload 4
      //   91: ldc 44
      //   93: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   96: pop
      //   97: aload 4
      //   99: ldc 50
      //   101: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   104: pop
      //   105: aload 4
      //   107: ldc 52
      //   109: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   112: pop
      //   113: aload 4
      //   115: ldc 54
      //   117: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   120: pop
      //   121: aload 4
      //   123: ldc 52
      //   125: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   128: pop
      //   129: aload 4
      //   131: ldc 56
      //   133: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   136: pop
      //   137: aload 4
      //   139: ldc 52
      //   141: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   144: pop
      //   145: aload 4
      //   147: ldc 58
      //   149: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   152: pop
      //   153: aload 4
      //   155: ldc 60
      //   157: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   160: pop
      //   161: aload 4
      //   163: ldc 62
      //   165: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   168: pop
      //   169: aload 4
      //   171: ldc 64
      //   173: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   176: pop
      //   177: aload 4
      //   179: ldc 66
      //   181: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   184: pop
      //   185: aload 4
      //   187: ldc 64
      //   189: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   192: pop
      //   193: aload 4
      //   195: ldc 68
      //   197: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   200: pop
      //   201: aload 4
      //   203: ldc 64
      //   205: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   208: pop
      //   209: aload 4
      //   211: ldc 70
      //   213: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   216: pop
      //   217: aload 4
      //   219: ldc 72
      //   221: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   224: pop
      //   225: aload 4
      //   227: ldc 74
      //   229: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   232: pop
      //   233: aload 4
      //   235: ldc 76
      //   237: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   240: pop
      //   241: aload 4
      //   243: ldc 78
      //   245: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   248: pop
      //   249: aload 4
      //   251: ldc 64
      //   253: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   256: pop
      //   257: aload 4
      //   259: ldc 80
      //   261: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   264: pop
      //   265: aload 4
      //   267: ldc 44
      //   269: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   272: pop
      //   273: aload 4
      //   275: ldc 82
      //   277: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   280: pop
      //   281: aload 4
      //   283: ldc 76
      //   285: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   288: pop
      //   289: aload 4
      //   291: ldc 84
      //   293: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   296: pop
      //   297: aload 4
      //   299: ldc 44
      //   301: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   304: pop
      //   305: aload 4
      //   307: ldc 86
      //   309: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   312: pop
      //   313: aload 4
      //   315: ldc 44
      //   317: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   320: pop
      //   321: aload 4
      //   323: ldc 88
      //   325: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   328: pop
      //   329: aload 4
      //   331: ldc 90
      //   333: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   336: pop
      //   337: aload 4
      //   339: ldc 92
      //   341: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   344: pop
      //   345: aload 4
      //   347: ldc 90
      //   349: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   352: pop
      //   353: aload 4
      //   355: ldc 94
      //   357: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   360: pop
      //   361: aload 4
      //   363: ldc 90
      //   365: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   368: pop
      //   369: aload 4
      //   371: ldc 96
      //   373: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   376: pop
      //   377: aload 4
      //   379: ldc 90
      //   381: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   384: pop
      //   385: aload 4
      //   387: ldc 98
      //   389: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   392: pop
      //   393: aload 4
      //   395: ldc 90
      //   397: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   400: pop
      //   401: aload 4
      //   403: ldc 100
      //   405: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   408: pop
      //   409: aload 4
      //   411: ldc 90
      //   413: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   416: pop
      //   417: aload 4
      //   419: ldc 102
      //   421: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   424: pop
      //   425: aload 4
      //   427: ldc 90
      //   429: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   432: pop
      //   433: aload 4
      //   435: ldc 104
      //   437: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   440: pop
      //   441: aload 4
      //   443: ldc 90
      //   445: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   448: pop
      //   449: aload 4
      //   451: ldc 106
      //   453: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   456: pop
      //   457: aload 4
      //   459: ldc 90
      //   461: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   464: pop
      //   465: aload 4
      //   467: ldc 108
      //   469: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   472: pop
      //   473: aload 4
      //   475: ldc 110
      //   477: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   480: pop
      //   481: aload 4
      //   483: ldc 112
      //   485: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   488: pop
      //   489: aload 4
      //   491: ldc 114
      //   493: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   496: pop
      //   497: aload 4
      //   499: ldc 116
      //   501: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   504: pop
      //   505: aload 4
      //   507: ldc 118
      //   509: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   512: pop
      //   513: aload 4
      //   515: ldc 120
      //   517: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   520: pop
      //   521: aload 4
      //   523: ldc 114
      //   525: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   528: pop
      //   529: aload 4
      //   531: ldc 122
      //   533: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   536: pop
      //   537: aload 4
      //   539: ldc 114
      //   541: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   544: pop
      //   545: aload 4
      //   547: ldc 124
      //   549: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   552: pop
      //   553: aload 4
      //   555: ldc 118
      //   557: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   560: pop
      //   561: aload 4
      //   563: ldc 126
      //   565: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   568: pop
      //   569: aload 4
      //   571: ldc 114
      //   573: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   576: pop
      //   577: aload 4
      //   579: ldc -128
      //   581: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   584: pop
      //   585: aload 4
      //   587: ldc 114
      //   589: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   592: pop
      //   593: aload 4
      //   595: ldc -126
      //   597: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   600: pop
      //   601: aload 4
      //   603: ldc 118
      //   605: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   608: pop
      //   609: aload 4
      //   611: ldc -124
      //   613: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   616: pop
      //   617: aload 4
      //   619: ldc 90
      //   621: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   624: pop
      //   625: aload 4
      //   627: ldc -122
      //   629: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   632: pop
      //   633: aload 4
      //   635: ldc -120
      //   637: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   640: pop
      //   641: aload 4
      //   643: ldc -118
      //   645: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   648: pop
      //   649: aload 4
      //   651: ldc -120
      //   653: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   656: pop
      //   657: aload 4
      //   659: ldc -116
      //   661: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   664: pop
      //   665: aload 4
      //   667: ldc -120
      //   669: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   672: pop
      //   673: aload 4
      //   675: ldc -114
      //   677: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   680: pop
      //   681: aload 4
      //   683: ldc -120
      //   685: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   688: pop
      //   689: aload 4
      //   691: ldc -112
      //   693: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   696: pop
      //   697: aload 4
      //   699: ldc -110
      //   701: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   704: pop
      //   705: aload 4
      //   707: ldc -108
      //   709: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   712: pop
      //   713: aload 4
      //   715: ldc 44
      //   717: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   720: pop
      //   721: aload 4
      //   723: ldc -106
      //   725: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   728: pop
      //   729: aload 4
      //   731: ldc 44
      //   733: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   736: pop
      //   737: aload 4
      //   739: ldc -104
      //   741: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   744: pop
      //   745: aload 4
      //   747: ldc 44
      //   749: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   752: pop
      //   753: aload 4
      //   755: ldc -102
      //   757: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   760: pop
      //   761: aload 4
      //   763: ldc 44
      //   765: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   768: pop
      //   769: aload 4
      //   771: ldc -100
      //   773: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   776: pop
      //   777: aload 4
      //   779: ldc -98
      //   781: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   784: pop
      //   785: aload 4
      //   787: ldc -96
      //   789: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   792: pop
      //   793: aload 4
      //   795: ldc -94
      //   797: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   800: pop
      //   801: aload 4
      //   803: ldc -92
      //   805: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   808: pop
      //   809: aload 4
      //   811: ldc -90
      //   813: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   816: pop
      //   817: aload 4
      //   819: ldc -88
      //   821: invokevirtual 32	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   824: pop
      //   825: aload_1
      //   826: ldc_w 259
      //   829: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   832: aload_1
      //   833: aload 4
      //   835: invokevirtual 172	java/lang/StringBuffer:toString	()Ljava/lang/String;
      //   838: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   841: aconst_null
      //   842: astore 5
      //   844: aconst_null
      //   845: astore 4
      //   847: aload_1
      //   848: ldc_w 261
      //   851: aconst_null
      //   852: aconst_null
      //   853: invokestatic 225	com/tencent/common/utils/DBUtils:query	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
      //   856: astore 6
      //   858: aload 6
      //   860: ifnull +56 -> 916
      //   863: aload 6
      //   865: astore 4
      //   867: aload 6
      //   869: astore 5
      //   871: aload_1
      //   872: invokestatic 228	com/tencent/common/utils/DBUtils:beginTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   875: aload 6
      //   877: astore 4
      //   879: aload 6
      //   881: astore 5
      //   883: aload 6
      //   885: invokeinterface 234 1 0
      //   890: ifeq +26 -> 916
      //   893: aload 6
      //   895: astore 4
      //   897: aload 6
      //   899: astore 5
      //   901: aload_1
      //   902: ldc 34
      //   904: aload 6
      //   906: invokestatic 240	com/tencent/mtt/base/utils/DLConvertTools:cursor2ContentValues	(Landroid/database/Cursor;)Landroid/content/ContentValues;
      //   909: invokestatic 244	com/tencent/common/utils/DBUtils:insert	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Landroid/content/ContentValues;)I
      //   912: pop
      //   913: goto -38 -> 875
      //   916: aload 6
      //   918: ifnull +10 -> 928
      //   921: aload 6
      //   923: invokeinterface 247 1 0
      //   928: aload_1
      //   929: invokestatic 250	com/tencent/common/utils/DBUtils:endTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   932: goto +53 -> 985
      //   935: astore 4
      //   937: aload 4
      //   939: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   942: goto +43 -> 985
      //   945: astore 5
      //   947: goto +48 -> 995
      //   950: astore 6
      //   952: aload 5
      //   954: astore 4
      //   956: aload 6
      //   958: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   961: aload 5
      //   963: ifnull +10 -> 973
      //   966: aload 5
      //   968: invokeinterface 247 1 0
      //   973: aload_1
      //   974: invokestatic 250	com/tencent/common/utils/DBUtils:endTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   977: goto +8 -> 985
      //   980: astore 4
      //   982: goto -45 -> 937
      //   985: aload_1
      //   986: ldc_w 263
      //   989: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   992: goto +39 -> 1031
      //   995: aload 4
      //   997: ifnull +10 -> 1007
      //   1000: aload 4
      //   1002: invokeinterface 247 1 0
      //   1007: aload_1
      //   1008: invokestatic 250	com/tencent/common/utils/DBUtils:endTransaction	(Landroid/database/sqlite/SQLiteDatabase;)V
      //   1011: goto +10 -> 1021
      //   1014: astore 4
      //   1016: aload 4
      //   1018: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   1021: aload 5
      //   1023: athrow
      //   1024: astore 4
      //   1026: aload 4
      //   1028: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   1031: aload_1
      //   1032: ldc -54
      //   1034: invokestatic 178	com/tencent/common/utils/DBUtils:execSQL	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)V
      //   1037: return
      //   1038: astore_1
      //   1039: new 180	java/lang/StringBuilder
      //   1042: dup
      //   1043: invokespecial 181	java/lang/StringBuilder:<init>	()V
      //   1046: astore 4
      //   1048: aload 4
      //   1050: ldc_w 265
      //   1053: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1056: pop
      //   1057: aload 4
      //   1059: aload_1
      //   1060: invokevirtual 189	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   1063: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1066: pop
      //   1067: ldc -65
      //   1069: aload 4
      //   1071: invokevirtual 192	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1074: invokestatic 197	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   1077: aload_1
      //   1078: invokevirtual 200	java/lang/Exception:printStackTrace	()V
      //   1081: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1082	0	this	d
      //   0	1082	1	paramSQLiteDatabase	SQLiteDatabase
      //   0	1082	2	paramInt1	int
      //   0	1082	3	paramInt2	int
      //   7	889	4	localObject1	Object
      //   935	3	4	localException1	Exception
      //   954	1	4	localObject2	Object
      //   980	21	4	localException2	Exception
      //   1014	3	4	localException3	Exception
      //   1024	3	4	localException4	Exception
      //   1046	24	4	localStringBuilder	StringBuilder
      //   842	58	5	localObject3	Object
      //   945	77	5	localObject4	Object
      //   856	66	6	localCursor	Cursor
      //   950	7	6	localException5	Exception
      // Exception table:
      //   from	to	target	type
      //   928	932	935	java/lang/Exception
      //   847	858	945	finally
      //   871	875	945	finally
      //   883	893	945	finally
      //   901	913	945	finally
      //   956	961	945	finally
      //   847	858	950	java/lang/Exception
      //   871	875	950	java/lang/Exception
      //   883	893	950	java/lang/Exception
      //   901	913	950	java/lang/Exception
      //   973	977	980	java/lang/Exception
      //   1007	1011	1014	java/lang/Exception
      //   0	841	1024	java/lang/Exception
      //   921	928	1024	java/lang/Exception
      //   937	942	1024	java/lang/Exception
      //   966	973	1024	java/lang/Exception
      //   985	992	1024	java/lang/Exception
      //   1000	1007	1024	java/lang/Exception
      //   1016	1021	1024	java/lang/Exception
      //   1021	1024	1024	java/lang/Exception
      //   1031	1037	1038	java/lang/Exception
    }
  }
  
  public static class e
  {
    static ConcurrentHashMap<Integer, LinkedList<DownloadSpeedData>> a = new ConcurrentHashMap();
    static ConcurrentHashMap<Integer, Float> b = new ConcurrentHashMap();
    
    public static Float a(int paramInt)
    {
      return (Float)b.get(Integer.valueOf(paramInt));
    }
    
    public static LinkedList<DownloadSpeedData> a(int paramInt)
    {
      return (LinkedList)a.get(Integer.valueOf(paramInt));
    }
    
    public static void a(int paramInt)
    {
      if (!a.containsKey(Integer.valueOf(paramInt)))
      {
        LinkedList localLinkedList = new LinkedList();
        a.put(Integer.valueOf(paramInt), localLinkedList);
      }
    }
    
    public static void a(int paramInt, long paramLong)
    {
      a(paramInt);
      LinkedList localLinkedList = (LinkedList)a.get(Integer.valueOf(paramInt));
      if (localLinkedList != null) {
        try
        {
          if (localLinkedList.size() > 3) {
            localLinkedList.poll();
          }
          localLinkedList.offer(new DownloadSpeedData(paramLong, System.currentTimeMillis()));
          return;
        }
        finally {}
      }
    }
    
    public static void a(int paramInt, Float paramFloat)
    {
      if (b.containsKey(Integer.valueOf(paramInt)))
      {
        b.replace(Integer.valueOf(paramInt), paramFloat);
        return;
      }
      b.put(Integer.valueOf(paramInt), paramFloat);
    }
    
    public static void b(int paramInt)
    {
      b.remove(Integer.valueOf(paramInt));
      List localList = (List)a.get(Integer.valueOf(paramInt));
      if (localList != null)
      {
        if (localList != null) {}
        try
        {
          localList.clear();
          return;
        }
        finally {}
      }
    }
    
    public static void c(int paramInt)
    {
      b.remove(Integer.valueOf(paramInt));
      a.remove(Integer.valueOf(paramInt));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\downloadprovider\QBDownloadProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */