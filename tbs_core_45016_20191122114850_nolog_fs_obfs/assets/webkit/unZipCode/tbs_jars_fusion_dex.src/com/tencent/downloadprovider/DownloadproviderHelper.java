package com.tencent.downloadprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.tencent.common.manifest.AppManifest;
import com.tencent.common.manifest.annotation.Extension;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.utils.DLConvertTools;
import com.tencent.mtt.base.utils.DLReporter;
import com.tencent.mtt.browser.download.engine.DownloadSpeedData;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class DownloadproviderHelper
{
  private static GetPublicDB a;
  
  /* Error */
  public static byte a(int paramInt)
  {
    // Byte code:
    //   0: iconst_m1
    //   1: istore_2
    //   2: aconst_null
    //   3: astore 4
    //   5: aconst_null
    //   6: astore 5
    //   8: iload_0
    //   9: invokestatic 16	com/tencent/downloadprovider/DownloadproviderHelper:c	(I)Landroid/database/Cursor;
    //   12: astore 6
    //   14: iload_2
    //   15: istore_1
    //   16: aload 6
    //   18: ifnull +51 -> 69
    //   21: iload_2
    //   22: istore_1
    //   23: aload 6
    //   25: astore 5
    //   27: aload 6
    //   29: astore 4
    //   31: aload 6
    //   33: invokeinterface 22 1 0
    //   38: ifeq +31 -> 69
    //   41: aload 6
    //   43: astore 5
    //   45: aload 6
    //   47: astore 4
    //   49: aload 6
    //   51: aload 6
    //   53: ldc 24
    //   55: invokeinterface 28 2 0
    //   60: invokeinterface 32 2 0
    //   65: istore_0
    //   66: iload_0
    //   67: i2b
    //   68: istore_1
    //   69: iload_1
    //   70: istore_3
    //   71: aload 6
    //   73: ifnull +44 -> 117
    //   76: aload 6
    //   78: astore 4
    //   80: aload 4
    //   82: invokeinterface 36 1 0
    //   87: iload_1
    //   88: ireturn
    //   89: astore 4
    //   91: goto +28 -> 119
    //   94: astore 6
    //   96: aload 4
    //   98: astore 5
    //   100: aload 6
    //   102: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   105: iload_2
    //   106: istore_3
    //   107: aload 4
    //   109: ifnull +8 -> 117
    //   112: iload_2
    //   113: istore_1
    //   114: goto -34 -> 80
    //   117: iload_3
    //   118: ireturn
    //   119: aload 5
    //   121: ifnull +10 -> 131
    //   124: aload 5
    //   126: invokeinterface 36 1 0
    //   131: aload 4
    //   133: athrow
    //   134: astore 4
    //   136: iload_1
    //   137: ireturn
    //   138: astore 5
    //   140: goto -9 -> 131
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	143	0	paramInt	int
    //   15	122	1	b1	byte
    //   1	112	2	b2	byte
    //   70	48	3	b3	byte
    //   3	78	4	localObject1	Object
    //   89	43	4	localObject2	Object
    //   134	1	4	localException1	Exception
    //   6	119	5	localObject3	Object
    //   138	1	5	localException2	Exception
    //   12	65	6	localCursor	Cursor
    //   94	7	6	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   8	14	89	finally
    //   31	41	89	finally
    //   49	66	89	finally
    //   100	105	89	finally
    //   8	14	94	java/lang/Exception
    //   31	41	94	java/lang/Exception
    //   49	66	94	java/lang/Exception
    //   80	87	134	java/lang/Exception
    //   124	131	138	java/lang/Exception
  }
  
  public static int a(DownloadTask paramDownloadTask, DLReporter paramDLReporter)
  {
    int i = -1;
    if (paramDownloadTask == null) {
      return -1;
    }
    if (paramDLReporter != null) {
      paramDLReporter.addStep(":A1[");
    }
    int j;
    try
    {
      j = QBDownloadProvider.c.a(ContextHolder.getAppContext()).a(DLConvertTools.downloadTast2ContentValues(paramDownloadTask), paramDLReporter);
      if (paramDLReporter != null)
      {
        i = j;
        paramDLReporter.addStep("-1");
      }
      i = j;
      paramDownloadTask.setDownloadTaskId(j);
    }
    catch (Exception paramDownloadTask)
    {
      paramDownloadTask.printStackTrace();
      j = i;
      if (paramDLReporter != null)
      {
        paramDLReporter.addStep("-2");
        j = i;
      }
    }
    if (paramDLReporter != null) {
      paramDLReporter.addStep("-3]");
    }
    return j;
  }
  
  private static Cursor a()
    throws Exception
  {
    try
    {
      Cursor localCursor = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "status!=3 AND status!=7 AND status!=8 AND status!=9", null, "createdate ASC");
      return localCursor;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public static Cursor a(int paramInt)
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("id=");
    ((StringBuilder)localObject).append(paramInt);
    localObject = ((StringBuilder)localObject).toString();
    return QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", new String[] { "extend_7" }, (String)localObject, null, null);
  }
  
  private static Cursor a(String paramString)
    throws Exception
  {
    try
    {
      paramString = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "url=? AND status!=3 AND status!=7 AND status!=8 AND status!=9", new String[] { paramString }, null);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public static GetPublicDB a()
  {
    GetPublicDB localGetPublicDB = a;
    if (localGetPublicDB != null) {
      return localGetPublicDB;
    }
    try
    {
      localGetPublicDB = a;
      if (localGetPublicDB == null) {
        try
        {
          a = (GetPublicDB)AppManifest.getInstance().queryExtension(GetPublicDB.class, null);
        }
        catch (Throwable localThrowable)
        {
          a = null;
          localThrowable.printStackTrace();
        }
      }
      return a;
    }
    finally {}
  }
  
  /* Error */
  public static DownloadTask a(int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore_3
    //   8: iload_0
    //   9: invokestatic 145	com/tencent/downloadprovider/DownloadproviderHelper:d	(I)Landroid/database/Cursor;
    //   12: astore_1
    //   13: aload_3
    //   14: astore_2
    //   15: aload_1
    //   16: ifnull +36 -> 52
    //   19: aload_3
    //   20: astore_2
    //   21: aload_1
    //   22: astore_3
    //   23: aload_1
    //   24: invokeinterface 22 1 0
    //   29: ifeq +23 -> 52
    //   32: aload_1
    //   33: astore_3
    //   34: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   37: aload_1
    //   38: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   41: astore_2
    //   42: goto +10 -> 52
    //   45: goto +29 -> 74
    //   48: astore_2
    //   49: goto +41 -> 90
    //   52: aload_2
    //   53: astore_3
    //   54: aload_1
    //   55: ifnull +54 -> 109
    //   58: aload_1
    //   59: invokeinterface 36 1 0
    //   64: aload_2
    //   65: areturn
    //   66: astore_1
    //   67: aconst_null
    //   68: astore_3
    //   69: goto +43 -> 112
    //   72: aconst_null
    //   73: astore_1
    //   74: aload 5
    //   76: astore_3
    //   77: aload_1
    //   78: ifnull +31 -> 109
    //   81: aload 4
    //   83: astore_2
    //   84: goto -26 -> 58
    //   87: astore_2
    //   88: aconst_null
    //   89: astore_1
    //   90: aload_1
    //   91: astore_3
    //   92: aload_2
    //   93: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   96: aload 5
    //   98: astore_3
    //   99: aload_1
    //   100: ifnull +9 -> 109
    //   103: aload 4
    //   105: astore_2
    //   106: goto -48 -> 58
    //   109: aload_3
    //   110: areturn
    //   111: astore_1
    //   112: aload_3
    //   113: ifnull +9 -> 122
    //   116: aload_3
    //   117: invokeinterface 36 1 0
    //   122: aload_1
    //   123: athrow
    //   124: astore_1
    //   125: goto -53 -> 72
    //   128: astore_2
    //   129: goto -84 -> 45
    //   132: astore_1
    //   133: aload_2
    //   134: areturn
    //   135: astore_2
    //   136: goto -14 -> 122
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	paramInt	int
    //   12	47	1	localCursor	Cursor
    //   66	1	1	localObject1	Object
    //   73	27	1	localObject2	Object
    //   111	12	1	localObject3	Object
    //   124	1	1	localOutOfMemoryError1	OutOfMemoryError
    //   132	1	1	localException1	Exception
    //   14	28	2	localObject4	Object
    //   48	17	2	localException2	Exception
    //   83	1	2	localObject5	Object
    //   87	6	2	localException3	Exception
    //   105	1	2	localObject6	Object
    //   128	6	2	localOutOfMemoryError2	OutOfMemoryError
    //   135	1	2	localException4	Exception
    //   7	110	3	localObject7	Object
    //   1	103	4	localObject8	Object
    //   4	93	5	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   23	32	48	java/lang/Exception
    //   34	42	48	java/lang/Exception
    //   8	13	66	finally
    //   8	13	87	java/lang/Exception
    //   23	32	111	finally
    //   34	42	111	finally
    //   92	96	111	finally
    //   8	13	124	java/lang/OutOfMemoryError
    //   23	32	128	java/lang/OutOfMemoryError
    //   34	42	128	java/lang/OutOfMemoryError
    //   58	64	132	java/lang/Exception
    //   116	122	135	java/lang/Exception
  }
  
  /* Error */
  public static DownloadTask a(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore_2
    //   8: aload_0
    //   9: invokestatic 152	com/tencent/downloadprovider/DownloadproviderHelper:c	(Ljava/lang/String;)Landroid/database/Cursor;
    //   12: astore_1
    //   13: aload_2
    //   14: astore_0
    //   15: aload_1
    //   16: ifnull +35 -> 51
    //   19: aload_2
    //   20: astore_0
    //   21: aload_1
    //   22: astore_2
    //   23: aload_1
    //   24: invokeinterface 22 1 0
    //   29: ifeq +22 -> 51
    //   32: aload_1
    //   33: astore_2
    //   34: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   37: aload_1
    //   38: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   41: astore_0
    //   42: goto +9 -> 51
    //   45: astore_3
    //   46: aload_1
    //   47: astore_0
    //   48: goto +30 -> 78
    //   51: aload_0
    //   52: astore_2
    //   53: aload_1
    //   54: ifnull +45 -> 99
    //   57: aload_1
    //   58: astore_2
    //   59: aload_0
    //   60: astore_1
    //   61: aload_2
    //   62: invokeinterface 36 1 0
    //   67: aload_1
    //   68: areturn
    //   69: astore_0
    //   70: aconst_null
    //   71: astore_2
    //   72: goto +30 -> 102
    //   75: astore_3
    //   76: aconst_null
    //   77: astore_0
    //   78: aload_0
    //   79: astore_2
    //   80: aload_3
    //   81: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   84: aload 5
    //   86: astore_2
    //   87: aload_0
    //   88: ifnull +11 -> 99
    //   91: aload 4
    //   93: astore_1
    //   94: aload_0
    //   95: astore_2
    //   96: goto -35 -> 61
    //   99: aload_2
    //   100: areturn
    //   101: astore_0
    //   102: aload_2
    //   103: ifnull +9 -> 112
    //   106: aload_2
    //   107: invokeinterface 36 1 0
    //   112: aload_0
    //   113: athrow
    //   114: astore_0
    //   115: aload_1
    //   116: areturn
    //   117: astore_1
    //   118: goto -6 -> 112
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	paramString	String
    //   12	104	1	localObject1	Object
    //   117	1	1	localException1	Exception
    //   7	100	2	localObject2	Object
    //   45	1	3	localException2	Exception
    //   75	6	3	localException3	Exception
    //   1	91	4	localObject3	Object
    //   4	81	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   23	32	45	java/lang/Exception
    //   34	42	45	java/lang/Exception
    //   8	13	69	finally
    //   8	13	75	java/lang/Exception
    //   23	32	101	finally
    //   34	42	101	finally
    //   80	84	101	finally
    //   61	67	114	java/lang/Exception
    //   106	112	117	java/lang/Exception
  }
  
  public static Float a(int paramInt)
  {
    return QBDownloadProvider.e.a(paramInt);
  }
  
  /* Error */
  public static String a(int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore_3
    //   8: iload_0
    //   9: invokestatic 160	com/tencent/downloadprovider/DownloadproviderHelper:a	(I)Landroid/database/Cursor;
    //   12: astore_2
    //   13: aload_3
    //   14: astore_1
    //   15: aload_2
    //   16: ifnull +43 -> 59
    //   19: aload_3
    //   20: astore_1
    //   21: aload_2
    //   22: astore_3
    //   23: aload_2
    //   24: invokeinterface 22 1 0
    //   29: ifeq +30 -> 59
    //   32: aload_2
    //   33: astore_3
    //   34: aload_2
    //   35: aload_2
    //   36: ldc 120
    //   38: invokeinterface 28 2 0
    //   43: invokeinterface 163 2 0
    //   48: astore_1
    //   49: goto +10 -> 59
    //   52: astore 4
    //   54: aload_2
    //   55: astore_1
    //   56: goto +31 -> 87
    //   59: aload_1
    //   60: astore_3
    //   61: aload_2
    //   62: ifnull +47 -> 109
    //   65: aload_2
    //   66: astore_3
    //   67: aload_1
    //   68: astore_2
    //   69: aload_3
    //   70: invokeinterface 36 1 0
    //   75: aload_2
    //   76: areturn
    //   77: astore_1
    //   78: aconst_null
    //   79: astore_3
    //   80: goto +32 -> 112
    //   83: astore 4
    //   85: aconst_null
    //   86: astore_1
    //   87: aload_1
    //   88: astore_3
    //   89: aload 4
    //   91: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   94: aload 6
    //   96: astore_3
    //   97: aload_1
    //   98: ifnull +11 -> 109
    //   101: aload 5
    //   103: astore_2
    //   104: aload_1
    //   105: astore_3
    //   106: goto -37 -> 69
    //   109: aload_3
    //   110: areturn
    //   111: astore_1
    //   112: aload_3
    //   113: ifnull +9 -> 122
    //   116: aload_3
    //   117: invokeinterface 36 1 0
    //   122: aload_1
    //   123: athrow
    //   124: astore_1
    //   125: aload_2
    //   126: areturn
    //   127: astore_2
    //   128: goto -6 -> 122
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	paramInt	int
    //   14	54	1	localObject1	Object
    //   77	1	1	localObject2	Object
    //   86	19	1	localObject3	Object
    //   111	12	1	localObject4	Object
    //   124	1	1	localException1	Exception
    //   12	114	2	localObject5	Object
    //   127	1	2	localException2	Exception
    //   7	110	3	localObject6	Object
    //   52	1	4	localException3	Exception
    //   83	7	4	localException4	Exception
    //   1	101	5	localObject7	Object
    //   4	91	6	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   23	32	52	java/lang/Exception
    //   34	49	52	java/lang/Exception
    //   8	13	77	finally
    //   8	13	83	java/lang/Exception
    //   23	32	111	finally
    //   34	49	111	finally
    //   89	94	111	finally
    //   69	75	124	java/lang/Exception
    //   116	122	127	java/lang/Exception
  }
  
  public static ArrayList<Integer> a(ArrayList<ContentValues> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      QBDownloadProvider.c.a(ContextHolder.getAppContext()).c();
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        ContentValues localContentValues = (ContentValues)paramArrayList.next();
        int i = QBDownloadProvider.c.a(ContextHolder.getAppContext()).a(localContentValues, localContentValues.getAsInteger("id").intValue(), false);
        if (i != -1) {
          localArrayList.add(Integer.valueOf(i));
        }
      }
      QBDownloadProvider.c.a(ContextHolder.getAppContext()).b();
      return localArrayList;
    }
    catch (Exception paramArrayList)
    {
      paramArrayList.printStackTrace();
    }
    return localArrayList;
  }
  
  public static LinkedList<DownloadSpeedData> a(int paramInt)
  {
    return QBDownloadProvider.e.a(paramInt);
  }
  
  /* Error */
  public static java.util.List<DownloadTask> a()
  {
    // Byte code:
    //   0: new 166	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 167	java/util/ArrayList:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_1
    //   11: aconst_null
    //   12: astore_2
    //   13: invokestatic 218	com/tencent/downloadprovider/DownloadproviderHelper:d	()Landroid/database/Cursor;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull +38 -> 56
    //   21: aload_3
    //   22: astore_2
    //   23: aload_3
    //   24: astore_1
    //   25: aload_3
    //   26: invokeinterface 221 1 0
    //   31: istore_0
    //   32: iload_0
    //   33: ifeq +23 -> 56
    //   36: aload_3
    //   37: astore_2
    //   38: aload 4
    //   40: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   43: aload_3
    //   44: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   47: invokeinterface 224 2 0
    //   52: pop
    //   53: goto -32 -> 21
    //   56: aload_3
    //   57: ifnull +32 -> 89
    //   60: aload_3
    //   61: astore_1
    //   62: aload_1
    //   63: invokeinterface 36 1 0
    //   68: aload 4
    //   70: areturn
    //   71: astore_1
    //   72: goto +20 -> 92
    //   75: astore_3
    //   76: aload_1
    //   77: astore_2
    //   78: aload_3
    //   79: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   82: aload_1
    //   83: ifnull +6 -> 89
    //   86: goto -24 -> 62
    //   89: aload 4
    //   91: areturn
    //   92: aload_2
    //   93: ifnull +9 -> 102
    //   96: aload_2
    //   97: invokeinterface 36 1 0
    //   102: aload_1
    //   103: athrow
    //   104: astore_1
    //   105: goto -84 -> 21
    //   108: astore_1
    //   109: aload 4
    //   111: areturn
    //   112: astore_2
    //   113: goto -11 -> 102
    // Local variable table:
    //   start	length	slot	name	signature
    //   31	2	0	bool	boolean
    //   10	53	1	localObject1	Object
    //   71	32	1	localObject2	Object
    //   104	1	1	localException1	Exception
    //   108	1	1	localException2	Exception
    //   12	85	2	localObject3	Object
    //   112	1	2	localException3	Exception
    //   16	45	3	localCursor	Cursor
    //   75	4	3	localException4	Exception
    //   7	103	4	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   13	17	71	finally
    //   25	32	71	finally
    //   38	53	71	finally
    //   78	82	71	finally
    //   13	17	75	java/lang/Exception
    //   25	32	75	java/lang/Exception
    //   38	53	104	java/lang/Exception
    //   62	68	108	java/lang/Exception
    //   96	102	112	java/lang/Exception
  }
  
  public static void a(int paramInt)
  {
    try
    {
      QBDownloadProvider.c.a(ContextHolder.getAppContext()).a(paramInt);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static void a(int paramInt, long paramLong)
  {
    QBDownloadProvider.e.a(paramInt, paramLong);
  }
  
  public static void a(int paramInt, Float paramFloat)
  {
    QBDownloadProvider.e.a(paramInt, paramFloat);
  }
  
  public static void a(ContentValues paramContentValues)
  {
    a(paramContentValues, false);
  }
  
  public static void a(ContentValues paramContentValues, boolean paramBoolean)
  {
    if (paramContentValues == null) {
      return;
    }
    try
    {
      QBDownloadProvider.c.a(ContextHolder.getAppContext()).a(paramContentValues, paramContentValues.getAsInteger("id").intValue(), paramBoolean);
      return;
    }
    catch (Exception paramContentValues)
    {
      paramContentValues.printStackTrace();
    }
  }
  
  public static void a(DownloadTask paramDownloadTask)
  {
    a(paramDownloadTask, false);
  }
  
  public static void a(DownloadTask paramDownloadTask, boolean paramBoolean)
  {
    if (paramDownloadTask == null) {
      return;
    }
    Object localObject = DownloadTask.sStatusLocks.get(Integer.valueOf(paramDownloadTask.getDownloadTaskId()));
    if (localObject != null) {
      try
      {
        if ((paramDownloadTask.getDownloadTaskId() != -1) && (a(paramDownloadTask.getDownloadTaskId()) == 10) && (paramDownloadTask.getStatus() != 0) && (paramDownloadTask.getStatus() != 10)) {
          return;
        }
      }
      finally {}
    }
    localObject = a(paramDownloadTask.getDownloadTaskId());
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      paramDownloadTask.mHijackInfo = ((String)localObject);
    }
    localObject = DLConvertTools.downloadTast2ContentValues(paramDownloadTask);
    try
    {
      QBDownloadProvider.c.a(ContextHolder.getAppContext()).a((ContentValues)localObject, paramDownloadTask.getDownloadTaskId(), paramBoolean);
      return;
    }
    catch (Exception paramDownloadTask)
    {
      paramDownloadTask.printStackTrace();
    }
  }
  
  private static Cursor b()
    throws Exception
  {
    try
    {
      Cursor localCursor = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "status!=7 AND status!=8 AND status!=9", null, "createdate DESC");
      return localCursor;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  private static Cursor b(int paramInt)
    throws Exception
  {
    try
    {
      Object localObject = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramInt);
      localStringBuilder.append("");
      localObject = ((QBDownloadProvider.b)localObject).a("download", null, "id=? AND status!=3 AND status!=7 AND status!=8 AND status!=9", new String[] { localStringBuilder.toString() }, null);
      return (Cursor)localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  private static Cursor b(String paramString)
    throws Exception
  {
    return QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "url=? AND status!=7 AND status!=8 AND status!=9", new String[] { paramString }, null);
  }
  
  /* Error */
  public static DownloadTask b(int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore_3
    //   8: iload_0
    //   9: invokestatic 284	com/tencent/downloadprovider/DownloadproviderHelper:b	(I)Landroid/database/Cursor;
    //   12: astore_2
    //   13: aload_3
    //   14: astore_1
    //   15: aload_2
    //   16: ifnull +36 -> 52
    //   19: aload_3
    //   20: astore_1
    //   21: aload_2
    //   22: astore_3
    //   23: aload_2
    //   24: invokeinterface 22 1 0
    //   29: ifeq +23 -> 52
    //   32: aload_2
    //   33: astore_3
    //   34: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   37: aload_2
    //   38: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   41: astore_1
    //   42: goto +10 -> 52
    //   45: astore 4
    //   47: aload_2
    //   48: astore_1
    //   49: goto +31 -> 80
    //   52: aload_1
    //   53: astore_3
    //   54: aload_2
    //   55: ifnull +47 -> 102
    //   58: aload_2
    //   59: astore_3
    //   60: aload_1
    //   61: astore_2
    //   62: aload_3
    //   63: invokeinterface 36 1 0
    //   68: aload_2
    //   69: areturn
    //   70: astore_1
    //   71: aconst_null
    //   72: astore_3
    //   73: goto +32 -> 105
    //   76: astore 4
    //   78: aconst_null
    //   79: astore_1
    //   80: aload_1
    //   81: astore_3
    //   82: aload 4
    //   84: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   87: aload 6
    //   89: astore_3
    //   90: aload_1
    //   91: ifnull +11 -> 102
    //   94: aload 5
    //   96: astore_2
    //   97: aload_1
    //   98: astore_3
    //   99: goto -37 -> 62
    //   102: aload_3
    //   103: areturn
    //   104: astore_1
    //   105: aload_3
    //   106: ifnull +9 -> 115
    //   109: aload_3
    //   110: invokeinterface 36 1 0
    //   115: aload_1
    //   116: athrow
    //   117: astore_1
    //   118: aload_2
    //   119: areturn
    //   120: astore_2
    //   121: goto -6 -> 115
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	124	0	paramInt	int
    //   14	47	1	localObject1	Object
    //   70	1	1	localObject2	Object
    //   79	19	1	localObject3	Object
    //   104	12	1	localObject4	Object
    //   117	1	1	localException1	Exception
    //   12	107	2	localObject5	Object
    //   120	1	2	localException2	Exception
    //   7	103	3	localObject6	Object
    //   45	1	4	localException3	Exception
    //   76	7	4	localException4	Exception
    //   1	94	5	localObject7	Object
    //   4	84	6	localObject8	Object
    // Exception table:
    //   from	to	target	type
    //   23	32	45	java/lang/Exception
    //   34	42	45	java/lang/Exception
    //   8	13	70	finally
    //   8	13	76	java/lang/Exception
    //   23	32	104	finally
    //   34	42	104	finally
    //   82	87	104	finally
    //   62	68	117	java/lang/Exception
    //   109	115	120	java/lang/Exception
  }
  
  /* Error */
  public static DownloadTask b(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aconst_null
    //   6: astore_2
    //   7: aload_0
    //   8: invokestatic 286	com/tencent/downloadprovider/DownloadproviderHelper:b	(Ljava/lang/String;)Landroid/database/Cursor;
    //   11: astore_0
    //   12: aload_2
    //   13: astore_1
    //   14: aload_0
    //   15: ifnull +36 -> 51
    //   18: aload_2
    //   19: astore_1
    //   20: aload_0
    //   21: astore_2
    //   22: aload_0
    //   23: invokeinterface 22 1 0
    //   28: ifeq +23 -> 51
    //   31: aload_0
    //   32: astore_2
    //   33: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   36: aload_0
    //   37: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   40: astore_1
    //   41: goto +10 -> 51
    //   44: goto +29 -> 73
    //   47: astore_1
    //   48: goto +40 -> 88
    //   51: aload_1
    //   52: astore_2
    //   53: aload_0
    //   54: ifnull +52 -> 106
    //   57: aload_0
    //   58: invokeinterface 36 1 0
    //   63: aload_1
    //   64: areturn
    //   65: astore_0
    //   66: aconst_null
    //   67: astore_2
    //   68: goto +41 -> 109
    //   71: aconst_null
    //   72: astore_0
    //   73: aload 4
    //   75: astore_2
    //   76: aload_0
    //   77: ifnull +29 -> 106
    //   80: aload_3
    //   81: astore_1
    //   82: goto -25 -> 57
    //   85: astore_1
    //   86: aconst_null
    //   87: astore_0
    //   88: aload_0
    //   89: astore_2
    //   90: aload_1
    //   91: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   94: aload 4
    //   96: astore_2
    //   97: aload_0
    //   98: ifnull +8 -> 106
    //   101: aload_3
    //   102: astore_1
    //   103: goto -46 -> 57
    //   106: aload_2
    //   107: areturn
    //   108: astore_0
    //   109: aload_2
    //   110: ifnull +9 -> 119
    //   113: aload_2
    //   114: invokeinterface 36 1 0
    //   119: aload_0
    //   120: athrow
    //   121: astore_0
    //   122: goto -51 -> 71
    //   125: astore_1
    //   126: goto -82 -> 44
    //   129: astore_0
    //   130: aload_1
    //   131: areturn
    //   132: astore_1
    //   133: goto -14 -> 119
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	136	0	paramString	String
    //   13	28	1	localObject1	Object
    //   47	17	1	localException1	Exception
    //   81	1	1	localObject2	Object
    //   85	6	1	localException2	Exception
    //   102	1	1	localObject3	Object
    //   125	6	1	localOutOfMemoryError	OutOfMemoryError
    //   132	1	1	localException3	Exception
    //   6	108	2	localObject4	Object
    //   1	101	3	localObject5	Object
    //   3	92	4	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   22	31	47	java/lang/Exception
    //   33	41	47	java/lang/Exception
    //   7	12	65	finally
    //   7	12	85	java/lang/Exception
    //   22	31	108	finally
    //   33	41	108	finally
    //   90	94	108	finally
    //   7	12	121	java/lang/OutOfMemoryError
    //   22	31	125	java/lang/OutOfMemoryError
    //   33	41	125	java/lang/OutOfMemoryError
    //   57	63	129	java/lang/Exception
    //   113	119	132	java/lang/Exception
  }
  
  /* Error */
  public static java.util.List<DownloadTask> b()
  {
    // Byte code:
    //   0: new 166	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 167	java/util/ArrayList:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_1
    //   11: aconst_null
    //   12: astore_2
    //   13: invokestatic 289	com/tencent/downloadprovider/DownloadproviderHelper:e	()Landroid/database/Cursor;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull +38 -> 56
    //   21: aload_3
    //   22: astore_2
    //   23: aload_3
    //   24: astore_1
    //   25: aload_3
    //   26: invokeinterface 221 1 0
    //   31: istore_0
    //   32: iload_0
    //   33: ifeq +23 -> 56
    //   36: aload_3
    //   37: astore_2
    //   38: aload 4
    //   40: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   43: aload_3
    //   44: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   47: invokeinterface 224 2 0
    //   52: pop
    //   53: goto -32 -> 21
    //   56: aload_3
    //   57: ifnull +32 -> 89
    //   60: aload_3
    //   61: astore_1
    //   62: aload_1
    //   63: invokeinterface 36 1 0
    //   68: aload 4
    //   70: areturn
    //   71: astore_1
    //   72: goto +20 -> 92
    //   75: astore_3
    //   76: aload_1
    //   77: astore_2
    //   78: aload_3
    //   79: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   82: aload_1
    //   83: ifnull +6 -> 89
    //   86: goto -24 -> 62
    //   89: aload 4
    //   91: areturn
    //   92: aload_2
    //   93: ifnull +9 -> 102
    //   96: aload_2
    //   97: invokeinterface 36 1 0
    //   102: aload_1
    //   103: athrow
    //   104: astore_1
    //   105: goto -84 -> 21
    //   108: astore_1
    //   109: aload 4
    //   111: areturn
    //   112: astore_2
    //   113: goto -11 -> 102
    // Local variable table:
    //   start	length	slot	name	signature
    //   31	2	0	bool	boolean
    //   10	53	1	localObject1	Object
    //   71	32	1	localObject2	Object
    //   104	1	1	localException1	Exception
    //   108	1	1	localException2	Exception
    //   12	85	2	localObject3	Object
    //   112	1	2	localException3	Exception
    //   16	45	3	localCursor	Cursor
    //   75	4	3	localException4	Exception
    //   7	103	4	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   13	17	71	finally
    //   25	32	71	finally
    //   38	53	71	finally
    //   78	82	71	finally
    //   13	17	75	java/lang/Exception
    //   25	32	75	java/lang/Exception
    //   38	53	104	java/lang/Exception
    //   62	68	108	java/lang/Exception
    //   96	102	112	java/lang/Exception
  }
  
  public static void b(int paramInt)
  {
    QBDownloadProvider.e.b(paramInt);
  }
  
  private static Cursor c()
    throws Exception
  {
    try
    {
      Cursor localCursor = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "status=1 OR status=0 OR status=2", null, "createdate DESC");
      return localCursor;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  private static Cursor c(int paramInt)
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("id=");
    ((StringBuilder)localObject).append(paramInt);
    localObject = ((StringBuilder)localObject).toString();
    return QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", new String[] { "status" }, (String)localObject, null, null);
  }
  
  private static Cursor c(String paramString)
    throws Exception
  {
    try
    {
      paramString = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "url=?", new String[] { paramString }, null);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  public static DownloadTask c(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore_2
    //   8: aload_0
    //   9: invokestatic 297	com/tencent/downloadprovider/DownloadproviderHelper:d	(Ljava/lang/String;)Landroid/database/Cursor;
    //   12: astore_1
    //   13: aload_2
    //   14: astore_0
    //   15: aload_1
    //   16: ifnull +35 -> 51
    //   19: aload_2
    //   20: astore_0
    //   21: aload_1
    //   22: astore_2
    //   23: aload_1
    //   24: invokeinterface 22 1 0
    //   29: ifeq +22 -> 51
    //   32: aload_1
    //   33: astore_2
    //   34: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   37: aload_1
    //   38: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   41: astore_0
    //   42: goto +9 -> 51
    //   45: astore_3
    //   46: aload_1
    //   47: astore_0
    //   48: goto +30 -> 78
    //   51: aload_0
    //   52: astore_2
    //   53: aload_1
    //   54: ifnull +45 -> 99
    //   57: aload_1
    //   58: astore_2
    //   59: aload_0
    //   60: astore_1
    //   61: aload_2
    //   62: invokeinterface 36 1 0
    //   67: aload_1
    //   68: areturn
    //   69: astore_0
    //   70: aconst_null
    //   71: astore_2
    //   72: goto +30 -> 102
    //   75: astore_3
    //   76: aconst_null
    //   77: astore_0
    //   78: aload_0
    //   79: astore_2
    //   80: aload_3
    //   81: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   84: aload 5
    //   86: astore_2
    //   87: aload_0
    //   88: ifnull +11 -> 99
    //   91: aload 4
    //   93: astore_1
    //   94: aload_0
    //   95: astore_2
    //   96: goto -35 -> 61
    //   99: aload_2
    //   100: areturn
    //   101: astore_0
    //   102: aload_2
    //   103: ifnull +9 -> 112
    //   106: aload_2
    //   107: invokeinterface 36 1 0
    //   112: aload_0
    //   113: athrow
    //   114: astore_0
    //   115: aload_1
    //   116: areturn
    //   117: astore_1
    //   118: goto -6 -> 112
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	paramString	String
    //   12	104	1	localObject1	Object
    //   117	1	1	localException1	Exception
    //   7	100	2	localObject2	Object
    //   45	1	3	localException2	Exception
    //   75	6	3	localException3	Exception
    //   1	91	4	localObject3	Object
    //   4	81	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   23	32	45	java/lang/Exception
    //   34	42	45	java/lang/Exception
    //   8	13	69	finally
    //   8	13	75	java/lang/Exception
    //   23	32	101	finally
    //   34	42	101	finally
    //   80	84	101	finally
    //   61	67	114	java/lang/Exception
    //   106	112	117	java/lang/Exception
  }
  
  /* Error */
  public static java.util.List<DownloadTask> c()
  {
    // Byte code:
    //   0: new 166	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 167	java/util/ArrayList:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_1
    //   11: aconst_null
    //   12: astore_2
    //   13: invokestatic 299	com/tencent/downloadprovider/DownloadproviderHelper:a	()Landroid/database/Cursor;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull +38 -> 56
    //   21: aload_3
    //   22: astore_2
    //   23: aload_3
    //   24: astore_1
    //   25: aload_3
    //   26: invokeinterface 221 1 0
    //   31: istore_0
    //   32: iload_0
    //   33: ifeq +23 -> 56
    //   36: aload_3
    //   37: astore_2
    //   38: aload 4
    //   40: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   43: aload_3
    //   44: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   47: invokeinterface 224 2 0
    //   52: pop
    //   53: goto -32 -> 21
    //   56: aload_3
    //   57: ifnull +32 -> 89
    //   60: aload_3
    //   61: astore_1
    //   62: aload_1
    //   63: invokeinterface 36 1 0
    //   68: aload 4
    //   70: areturn
    //   71: astore_1
    //   72: goto +20 -> 92
    //   75: astore_3
    //   76: aload_1
    //   77: astore_2
    //   78: aload_3
    //   79: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   82: aload_1
    //   83: ifnull +6 -> 89
    //   86: goto -24 -> 62
    //   89: aload 4
    //   91: areturn
    //   92: aload_2
    //   93: ifnull +9 -> 102
    //   96: aload_2
    //   97: invokeinterface 36 1 0
    //   102: aload_1
    //   103: athrow
    //   104: astore_1
    //   105: goto -84 -> 21
    //   108: astore_1
    //   109: aload 4
    //   111: areturn
    //   112: astore_2
    //   113: goto -11 -> 102
    // Local variable table:
    //   start	length	slot	name	signature
    //   31	2	0	bool	boolean
    //   10	53	1	localObject1	Object
    //   71	32	1	localObject2	Object
    //   104	1	1	localException1	Exception
    //   108	1	1	localException2	Exception
    //   12	85	2	localObject3	Object
    //   112	1	2	localException3	Exception
    //   16	45	3	localCursor	Cursor
    //   75	4	3	localException4	Exception
    //   7	103	4	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   13	17	71	finally
    //   25	32	71	finally
    //   38	53	71	finally
    //   78	82	71	finally
    //   13	17	75	java/lang/Exception
    //   25	32	75	java/lang/Exception
    //   38	53	104	java/lang/Exception
    //   62	68	108	java/lang/Exception
    //   96	102	112	java/lang/Exception
  }
  
  public static void c(int paramInt)
  {
    QBDownloadProvider.e.c(paramInt);
  }
  
  private static Cursor d()
    throws Exception
  {
    try
    {
      Cursor localCursor = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "status!=7", null, null);
      return localCursor;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  private static Cursor d(int paramInt)
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("id=");
    ((StringBuilder)localObject).append(paramInt);
    localObject = ((StringBuilder)localObject).toString();
    return QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, (String)localObject, null, null);
  }
  
  private static Cursor d(String paramString)
    throws Exception
  {
    try
    {
      paramString = QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "url=? AND status=3", new String[] { paramString }, null);
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  public static DownloadTask d(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore_2
    //   8: aload_0
    //   9: invokestatic 307	com/tencent/downloadprovider/DownloadproviderHelper:a	(Ljava/lang/String;)Landroid/database/Cursor;
    //   12: astore_1
    //   13: aload_2
    //   14: astore_0
    //   15: aload_1
    //   16: ifnull +35 -> 51
    //   19: aload_2
    //   20: astore_0
    //   21: aload_1
    //   22: astore_2
    //   23: aload_1
    //   24: invokeinterface 22 1 0
    //   29: ifeq +22 -> 51
    //   32: aload_1
    //   33: astore_2
    //   34: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   37: aload_1
    //   38: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   41: astore_0
    //   42: goto +9 -> 51
    //   45: astore_3
    //   46: aload_1
    //   47: astore_0
    //   48: goto +30 -> 78
    //   51: aload_0
    //   52: astore_2
    //   53: aload_1
    //   54: ifnull +45 -> 99
    //   57: aload_1
    //   58: astore_2
    //   59: aload_0
    //   60: astore_1
    //   61: aload_2
    //   62: invokeinterface 36 1 0
    //   67: aload_1
    //   68: areturn
    //   69: astore_0
    //   70: aconst_null
    //   71: astore_2
    //   72: goto +30 -> 102
    //   75: astore_3
    //   76: aconst_null
    //   77: astore_0
    //   78: aload_0
    //   79: astore_2
    //   80: aload_3
    //   81: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   84: aload 5
    //   86: astore_2
    //   87: aload_0
    //   88: ifnull +11 -> 99
    //   91: aload 4
    //   93: astore_1
    //   94: aload_0
    //   95: astore_2
    //   96: goto -35 -> 61
    //   99: aload_2
    //   100: areturn
    //   101: astore_0
    //   102: aload_2
    //   103: ifnull +9 -> 112
    //   106: aload_2
    //   107: invokeinterface 36 1 0
    //   112: aload_0
    //   113: athrow
    //   114: astore_0
    //   115: aload_1
    //   116: areturn
    //   117: astore_1
    //   118: goto -6 -> 112
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	paramString	String
    //   12	104	1	localObject1	Object
    //   117	1	1	localException1	Exception
    //   7	100	2	localObject2	Object
    //   45	1	3	localException2	Exception
    //   75	6	3	localException3	Exception
    //   1	91	4	localObject3	Object
    //   4	81	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   23	32	45	java/lang/Exception
    //   34	42	45	java/lang/Exception
    //   8	13	69	finally
    //   8	13	75	java/lang/Exception
    //   23	32	101	finally
    //   34	42	101	finally
    //   80	84	101	finally
    //   61	67	114	java/lang/Exception
    //   106	112	117	java/lang/Exception
  }
  
  /* Error */
  public static java.util.List<DownloadTask> d()
  {
    // Byte code:
    //   0: new 166	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 167	java/util/ArrayList:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_1
    //   11: aconst_null
    //   12: astore_2
    //   13: invokestatic 309	com/tencent/downloadprovider/DownloadproviderHelper:c	()Landroid/database/Cursor;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull +38 -> 56
    //   21: aload_3
    //   22: astore_2
    //   23: aload_3
    //   24: astore_1
    //   25: aload_3
    //   26: invokeinterface 221 1 0
    //   31: istore_0
    //   32: iload_0
    //   33: ifeq +23 -> 56
    //   36: aload_3
    //   37: astore_2
    //   38: aload 4
    //   40: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   43: aload_3
    //   44: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   47: invokeinterface 224 2 0
    //   52: pop
    //   53: goto -32 -> 21
    //   56: aload_3
    //   57: ifnull +32 -> 89
    //   60: aload_3
    //   61: astore_1
    //   62: aload_1
    //   63: invokeinterface 36 1 0
    //   68: aload 4
    //   70: areturn
    //   71: astore_1
    //   72: goto +20 -> 92
    //   75: astore_3
    //   76: aload_1
    //   77: astore_2
    //   78: aload_3
    //   79: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   82: aload_1
    //   83: ifnull +6 -> 89
    //   86: goto -24 -> 62
    //   89: aload 4
    //   91: areturn
    //   92: aload_2
    //   93: ifnull +9 -> 102
    //   96: aload_2
    //   97: invokeinterface 36 1 0
    //   102: aload_1
    //   103: athrow
    //   104: astore_1
    //   105: goto -84 -> 21
    //   108: astore_1
    //   109: aload 4
    //   111: areturn
    //   112: astore_2
    //   113: goto -11 -> 102
    // Local variable table:
    //   start	length	slot	name	signature
    //   31	2	0	bool	boolean
    //   10	53	1	localObject1	Object
    //   71	32	1	localObject2	Object
    //   104	1	1	localException1	Exception
    //   108	1	1	localException2	Exception
    //   12	85	2	localObject3	Object
    //   112	1	2	localException3	Exception
    //   16	45	3	localCursor	Cursor
    //   75	4	3	localException4	Exception
    //   7	103	4	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   13	17	71	finally
    //   25	32	71	finally
    //   38	53	71	finally
    //   78	82	71	finally
    //   13	17	75	java/lang/Exception
    //   25	32	75	java/lang/Exception
    //   38	53	104	java/lang/Exception
    //   62	68	108	java/lang/Exception
    //   96	102	112	java/lang/Exception
  }
  
  private static Cursor e()
    throws Exception
  {
    return QBDownloadProvider.c.a(ContextHolder.getAppContext()).b.a("download", null, "(status=8 OR status=9 OR status=10 OR status=6 OR status=11 OR status=0 OR download_operations IS NOT NULL)", null, null);
  }
  
  /* Error */
  public static java.util.List<DownloadTask> e()
  {
    // Byte code:
    //   0: new 166	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 167	java/util/ArrayList:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore_1
    //   11: aconst_null
    //   12: astore_2
    //   13: invokestatic 313	com/tencent/downloadprovider/DownloadproviderHelper:b	()Landroid/database/Cursor;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull +38 -> 56
    //   21: aload_3
    //   22: astore_2
    //   23: aload_3
    //   24: astore_1
    //   25: aload_3
    //   26: invokeinterface 221 1 0
    //   31: istore_0
    //   32: iload_0
    //   33: ifeq +23 -> 56
    //   36: aload_3
    //   37: astore_2
    //   38: aload 4
    //   40: invokestatic 55	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   43: aload_3
    //   44: invokestatic 149	com/tencent/mtt/base/utils/DLConvertTools:cursor2DownloadTask	(Landroid/content/Context;Landroid/database/Cursor;)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   47: invokeinterface 224 2 0
    //   52: pop
    //   53: goto -32 -> 21
    //   56: aload_3
    //   57: ifnull +32 -> 89
    //   60: aload_3
    //   61: astore_1
    //   62: aload_1
    //   63: invokeinterface 36 1 0
    //   68: aload 4
    //   70: areturn
    //   71: astore_1
    //   72: goto +20 -> 92
    //   75: astore_3
    //   76: aload_1
    //   77: astore_2
    //   78: aload_3
    //   79: invokevirtual 39	java/lang/Exception:printStackTrace	()V
    //   82: aload_1
    //   83: ifnull +6 -> 89
    //   86: goto -24 -> 62
    //   89: aload 4
    //   91: areturn
    //   92: aload_2
    //   93: ifnull +9 -> 102
    //   96: aload_2
    //   97: invokeinterface 36 1 0
    //   102: aload_1
    //   103: athrow
    //   104: astore_1
    //   105: goto -84 -> 21
    //   108: astore_1
    //   109: aload 4
    //   111: areturn
    //   112: astore_2
    //   113: goto -11 -> 102
    // Local variable table:
    //   start	length	slot	name	signature
    //   31	2	0	bool	boolean
    //   10	53	1	localObject1	Object
    //   71	32	1	localObject2	Object
    //   104	1	1	localException1	Exception
    //   108	1	1	localException2	Exception
    //   12	85	2	localObject3	Object
    //   112	1	2	localException3	Exception
    //   16	45	3	localCursor	Cursor
    //   75	4	3	localException4	Exception
    //   7	103	4	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   13	17	71	finally
    //   25	32	71	finally
    //   38	53	71	finally
    //   78	82	71	finally
    //   13	17	75	java/lang/Exception
    //   25	32	75	java/lang/Exception
    //   38	53	104	java/lang/Exception
    //   62	68	108	java/lang/Exception
    //   96	102	112	java/lang/Exception
  }
  
  @Extension
  public static abstract interface GetPublicDB
  {
    public abstract SQLiteDatabase getPublicDB();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\downloadprovider\DownloadproviderHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */