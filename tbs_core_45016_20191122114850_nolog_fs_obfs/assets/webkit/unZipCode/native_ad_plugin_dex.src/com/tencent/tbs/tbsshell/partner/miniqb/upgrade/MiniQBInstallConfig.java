package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

class MiniQBInstallConfig
{
  private static final String CORE_COPY_RETRY_TIMES = "copy_retry_times";
  private static final String CORE_INSTALL_FILE = "coreinstall.txt";
  private static final String CORE_INSTALL_RETRY_TIMES = "install_retry_times";
  private static final String CORE_INSTALL_STATUS = "install_status";
  private static final String CORE_INSTALL_VERSION = "install_core_version";
  static final int MAX_RETRY_TIMES = 10;
  private static Context mContext;
  private static MiniQBInstallConfig mInstance;
  
  /* Error */
  private Properties getCoreInstallProp()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore_2
    //   10: aload_0
    //   11: invokevirtual 43	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallScheduleFile	()Ljava/io/File;
    //   14: astore_1
    //   15: new 45	java/util/Properties
    //   18: dup
    //   19: invokespecial 46	java/util/Properties:<init>	()V
    //   22: astore 6
    //   24: aload_1
    //   25: ifnull +65 -> 90
    //   28: new 48	java/io/FileInputStream
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 51	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   36: astore_1
    //   37: aload 5
    //   39: astore_3
    //   40: aload_1
    //   41: astore 5
    //   43: new 53	java/io/BufferedInputStream
    //   46: dup
    //   47: aload_1
    //   48: invokespecial 56	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   51: astore_2
    //   52: aload 6
    //   54: aload_2
    //   55: invokevirtual 59	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   58: goto +34 -> 92
    //   61: astore 4
    //   63: aload_2
    //   64: astore_3
    //   65: aload 4
    //   67: astore_2
    //   68: goto +123 -> 191
    //   71: astore_3
    //   72: aload_2
    //   73: astore 4
    //   75: aload_3
    //   76: astore_2
    //   77: goto +63 -> 140
    //   80: astore_2
    //   81: goto +59 -> 140
    //   84: astore_2
    //   85: aconst_null
    //   86: astore_1
    //   87: goto +53 -> 140
    //   90: aconst_null
    //   91: astore_1
    //   92: aload_2
    //   93: ifnull +15 -> 108
    //   96: aload_2
    //   97: invokevirtual 62	java/io/BufferedInputStream:close	()V
    //   100: goto +8 -> 108
    //   103: astore_2
    //   104: aload_2
    //   105: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   108: aload_1
    //   109: ifnull +15 -> 124
    //   112: aload_1
    //   113: invokevirtual 66	java/io/FileInputStream:close	()V
    //   116: aload 6
    //   118: areturn
    //   119: astore_1
    //   120: aload_1
    //   121: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   124: aload 6
    //   126: areturn
    //   127: astore_2
    //   128: aconst_null
    //   129: astore_1
    //   130: goto +61 -> 191
    //   133: astore_2
    //   134: aconst_null
    //   135: astore 6
    //   137: aload 6
    //   139: astore_1
    //   140: aload 4
    //   142: astore_3
    //   143: aload_1
    //   144: astore 5
    //   146: aload_2
    //   147: invokevirtual 67	java/lang/Exception:printStackTrace	()V
    //   150: aload 4
    //   152: ifnull +16 -> 168
    //   155: aload 4
    //   157: invokevirtual 62	java/io/BufferedInputStream:close	()V
    //   160: goto +8 -> 168
    //   163: astore_2
    //   164: aload_2
    //   165: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   168: aload_1
    //   169: ifnull +15 -> 184
    //   172: aload_1
    //   173: invokevirtual 66	java/io/FileInputStream:close	()V
    //   176: aload 6
    //   178: areturn
    //   179: astore_1
    //   180: aload_1
    //   181: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   184: aload 6
    //   186: areturn
    //   187: astore_2
    //   188: aload 5
    //   190: astore_1
    //   191: aload_3
    //   192: ifnull +15 -> 207
    //   195: aload_3
    //   196: invokevirtual 62	java/io/BufferedInputStream:close	()V
    //   199: goto +8 -> 207
    //   202: astore_3
    //   203: aload_3
    //   204: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   207: aload_1
    //   208: ifnull +15 -> 223
    //   211: aload_1
    //   212: invokevirtual 66	java/io/FileInputStream:close	()V
    //   215: goto +8 -> 223
    //   218: astore_1
    //   219: aload_1
    //   220: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   223: aload_2
    //   224: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	225	0	this	MiniQBInstallConfig
    //   14	99	1	localObject1	Object
    //   119	2	1	localIOException1	IOException
    //   129	44	1	localObject2	Object
    //   179	2	1	localIOException2	IOException
    //   190	22	1	localObject3	Object
    //   218	2	1	localIOException3	IOException
    //   9	68	2	localObject4	Object
    //   80	1	2	localException1	Exception
    //   84	13	2	localException2	Exception
    //   103	2	2	localIOException4	IOException
    //   127	1	2	localObject5	Object
    //   133	14	2	localException3	Exception
    //   163	2	2	localIOException5	IOException
    //   187	37	2	localObject6	Object
    //   7	58	3	localObject7	Object
    //   71	5	3	localException4	Exception
    //   142	54	3	localObject8	Object
    //   202	2	3	localIOException6	IOException
    //   1	1	4	localObject9	Object
    //   61	5	4	localObject10	Object
    //   73	83	4	localObject11	Object
    //   4	185	5	localObject12	Object
    //   22	163	6	localProperties	Properties
    // Exception table:
    //   from	to	target	type
    //   52	58	61	finally
    //   52	58	71	java/lang/Exception
    //   43	52	80	java/lang/Exception
    //   28	37	84	java/lang/Exception
    //   96	100	103	java/io/IOException
    //   112	116	119	java/io/IOException
    //   10	24	127	finally
    //   28	37	127	finally
    //   10	24	133	java/lang/Exception
    //   155	160	163	java/io/IOException
    //   172	176	179	java/io/IOException
    //   43	52	187	finally
    //   146	150	187	finally
    //   195	199	202	java/io/IOException
    //   211	215	218	java/io/IOException
  }
  
  static MiniQBInstallConfig getInstance(Context paramContext)
  {
    if (mInstance == null)
    {
      mInstance = new MiniQBInstallConfig();
      mContext = paramContext.getApplicationContext();
    }
    return mInstance;
  }
  
  void clearConfig()
  {
    File localFile = getCoreInstallScheduleFile();
    if ((localFile != null) && (localFile.exists())) {
      localFile.delete();
    }
  }
  
  int getCoreCopyRetryTimes()
  {
    Properties localProperties = getCoreInstallProp();
    if ((localProperties != null) && (localProperties.getProperty("copy_retry_times") != null)) {
      return Integer.parseInt(localProperties.getProperty("copy_retry_times"));
    }
    return 0;
  }
  
  int getCoreInstallRetryTimes()
  {
    Properties localProperties = getCoreInstallProp();
    if ((localProperties != null) && (localProperties.getProperty("install_retry_times") != null)) {
      return Integer.parseInt(localProperties.getProperty("install_retry_times"));
    }
    return 0;
  }
  
  File getCoreInstallScheduleFile()
  {
    File localFile = new File(MiniQBUpgradeManager.getInstance().getMiniQBUpgradeDir(mContext), "coreinstall.txt");
    if (!localFile.exists()) {
      try
      {
        localFile.createNewFile();
        return localFile;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return null;
      }
    }
    return localIOException;
  }
  
  int getCoreInstallStatus()
  {
    Properties localProperties = getCoreInstallProp();
    if ((localProperties != null) && (localProperties.getProperty("install_status") != null)) {
      return Integer.parseInt(localProperties.getProperty("install_status"));
    }
    return -1;
  }
  
  int getCoreInstallVersion()
  {
    Properties localProperties = getCoreInstallProp();
    if ((localProperties != null) && (localProperties.getProperty("install_core_version") != null)) {
      return Integer.parseInt(localProperties.getProperty("install_core_version"));
    }
    return 0;
  }
  
  /* Error */
  void setCoreCopyRetryTimes(int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore_2
    //   10: aload_0
    //   11: invokespecial 94	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallProp	()Ljava/util/Properties;
    //   14: astore 6
    //   16: aload 6
    //   18: ifnull +90 -> 108
    //   21: aload 6
    //   23: ldc 8
    //   25: iload_1
    //   26: invokestatic 130	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   29: invokevirtual 134	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   32: pop
    //   33: aload_0
    //   34: invokevirtual 43	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallScheduleFile	()Ljava/io/File;
    //   37: astore 5
    //   39: aload 5
    //   41: ifnull +67 -> 108
    //   44: new 136	java/io/FileOutputStream
    //   47: dup
    //   48: aload 5
    //   50: invokespecial 137	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   53: astore_2
    //   54: aload 4
    //   56: astore_3
    //   57: aload_2
    //   58: astore 4
    //   60: new 139	java/io/BufferedOutputStream
    //   63: dup
    //   64: aload_2
    //   65: invokespecial 142	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   68: astore 5
    //   70: aload 6
    //   72: aload 5
    //   74: ldc -112
    //   76: invokevirtual 148	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   79: goto +32 -> 111
    //   82: astore 4
    //   84: aload 5
    //   86: astore_3
    //   87: aload 4
    //   89: astore 5
    //   91: goto +111 -> 202
    //   94: astore 6
    //   96: goto +63 -> 159
    //   99: astore 6
    //   101: aload 7
    //   103: astore 5
    //   105: goto +54 -> 159
    //   108: aconst_null
    //   109: astore 5
    //   111: aload 5
    //   113: ifnull +16 -> 129
    //   116: aload 5
    //   118: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   121: goto +8 -> 129
    //   124: astore_3
    //   125: aload_3
    //   126: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   129: aload_2
    //   130: ifnull +66 -> 196
    //   133: aload_2
    //   134: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   137: return
    //   138: astore_2
    //   139: aload_2
    //   140: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   143: return
    //   144: astore 5
    //   146: aconst_null
    //   147: astore_2
    //   148: goto +54 -> 202
    //   151: astore 6
    //   153: aconst_null
    //   154: astore_2
    //   155: aload 7
    //   157: astore 5
    //   159: aload 5
    //   161: astore_3
    //   162: aload_2
    //   163: astore 4
    //   165: aload 6
    //   167: invokevirtual 67	java/lang/Exception:printStackTrace	()V
    //   170: aload 5
    //   172: ifnull +16 -> 188
    //   175: aload 5
    //   177: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   180: goto +8 -> 188
    //   183: astore_3
    //   184: aload_3
    //   185: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   188: aload_2
    //   189: ifnull +7 -> 196
    //   192: aload_2
    //   193: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   196: return
    //   197: astore 5
    //   199: aload 4
    //   201: astore_2
    //   202: aload_3
    //   203: ifnull +15 -> 218
    //   206: aload_3
    //   207: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   210: goto +8 -> 218
    //   213: astore_3
    //   214: aload_3
    //   215: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   218: aload_2
    //   219: ifnull +15 -> 234
    //   222: aload_2
    //   223: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   226: goto +8 -> 234
    //   229: astore_2
    //   230: aload_2
    //   231: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   234: aload 5
    //   236: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	MiniQBInstallConfig
    //   0	237	1	paramInt	int
    //   9	125	2	localFileOutputStream	java.io.FileOutputStream
    //   138	2	2	localIOException1	IOException
    //   147	76	2	localObject1	Object
    //   229	2	2	localIOException2	IOException
    //   7	80	3	localObject2	Object
    //   124	2	3	localIOException3	IOException
    //   161	1	3	localObject3	Object
    //   183	24	3	localIOException4	IOException
    //   213	2	3	localIOException5	IOException
    //   4	55	4	localObject4	Object
    //   82	6	4	localObject5	Object
    //   163	37	4	localObject6	Object
    //   37	80	5	localObject7	Object
    //   144	1	5	localObject8	Object
    //   157	19	5	localObject9	Object
    //   197	38	5	localObject10	Object
    //   14	57	6	localProperties	Properties
    //   94	1	6	localException1	Exception
    //   99	1	6	localException2	Exception
    //   151	15	6	localException3	Exception
    //   1	155	7	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   70	79	82	finally
    //   70	79	94	java/lang/Exception
    //   60	70	99	java/lang/Exception
    //   116	121	124	java/io/IOException
    //   133	137	138	java/io/IOException
    //   192	196	138	java/io/IOException
    //   10	16	144	finally
    //   21	39	144	finally
    //   44	54	144	finally
    //   10	16	151	java/lang/Exception
    //   21	39	151	java/lang/Exception
    //   44	54	151	java/lang/Exception
    //   175	180	183	java/io/IOException
    //   60	70	197	finally
    //   165	170	197	finally
    //   206	210	213	java/io/IOException
    //   222	226	229	java/io/IOException
  }
  
  /* Error */
  void setCoreInstallRetryTimes(int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore_3
    //   8: aconst_null
    //   9: astore_2
    //   10: aload_0
    //   11: invokespecial 94	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallProp	()Ljava/util/Properties;
    //   14: astore 6
    //   16: aload 6
    //   18: ifnull +90 -> 108
    //   21: aload 6
    //   23: ldc 14
    //   25: iload_1
    //   26: invokestatic 130	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   29: invokevirtual 134	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   32: pop
    //   33: aload_0
    //   34: invokevirtual 43	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallScheduleFile	()Ljava/io/File;
    //   37: astore 5
    //   39: aload 5
    //   41: ifnull +67 -> 108
    //   44: new 136	java/io/FileOutputStream
    //   47: dup
    //   48: aload 5
    //   50: invokespecial 137	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   53: astore_2
    //   54: aload 4
    //   56: astore_3
    //   57: aload_2
    //   58: astore 4
    //   60: new 139	java/io/BufferedOutputStream
    //   63: dup
    //   64: aload_2
    //   65: invokespecial 142	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   68: astore 5
    //   70: aload 6
    //   72: aload 5
    //   74: ldc -112
    //   76: invokevirtual 148	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   79: goto +32 -> 111
    //   82: astore 4
    //   84: aload 5
    //   86: astore_3
    //   87: aload 4
    //   89: astore 5
    //   91: goto +111 -> 202
    //   94: astore 6
    //   96: goto +63 -> 159
    //   99: astore 6
    //   101: aload 7
    //   103: astore 5
    //   105: goto +54 -> 159
    //   108: aconst_null
    //   109: astore 5
    //   111: aload 5
    //   113: ifnull +16 -> 129
    //   116: aload 5
    //   118: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   121: goto +8 -> 129
    //   124: astore_3
    //   125: aload_3
    //   126: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   129: aload_2
    //   130: ifnull +66 -> 196
    //   133: aload_2
    //   134: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   137: return
    //   138: astore_2
    //   139: aload_2
    //   140: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   143: return
    //   144: astore 5
    //   146: aconst_null
    //   147: astore_2
    //   148: goto +54 -> 202
    //   151: astore 6
    //   153: aconst_null
    //   154: astore_2
    //   155: aload 7
    //   157: astore 5
    //   159: aload 5
    //   161: astore_3
    //   162: aload_2
    //   163: astore 4
    //   165: aload 6
    //   167: invokevirtual 67	java/lang/Exception:printStackTrace	()V
    //   170: aload 5
    //   172: ifnull +16 -> 188
    //   175: aload 5
    //   177: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   180: goto +8 -> 188
    //   183: astore_3
    //   184: aload_3
    //   185: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   188: aload_2
    //   189: ifnull +7 -> 196
    //   192: aload_2
    //   193: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   196: return
    //   197: astore 5
    //   199: aload 4
    //   201: astore_2
    //   202: aload_3
    //   203: ifnull +15 -> 218
    //   206: aload_3
    //   207: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   210: goto +8 -> 218
    //   213: astore_3
    //   214: aload_3
    //   215: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   218: aload_2
    //   219: ifnull +15 -> 234
    //   222: aload_2
    //   223: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   226: goto +8 -> 234
    //   229: astore_2
    //   230: aload_2
    //   231: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   234: aload 5
    //   236: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	MiniQBInstallConfig
    //   0	237	1	paramInt	int
    //   9	125	2	localFileOutputStream	java.io.FileOutputStream
    //   138	2	2	localIOException1	IOException
    //   147	76	2	localObject1	Object
    //   229	2	2	localIOException2	IOException
    //   7	80	3	localObject2	Object
    //   124	2	3	localIOException3	IOException
    //   161	1	3	localObject3	Object
    //   183	24	3	localIOException4	IOException
    //   213	2	3	localIOException5	IOException
    //   4	55	4	localObject4	Object
    //   82	6	4	localObject5	Object
    //   163	37	4	localObject6	Object
    //   37	80	5	localObject7	Object
    //   144	1	5	localObject8	Object
    //   157	19	5	localObject9	Object
    //   197	38	5	localObject10	Object
    //   14	57	6	localProperties	Properties
    //   94	1	6	localException1	Exception
    //   99	1	6	localException2	Exception
    //   151	15	6	localException3	Exception
    //   1	155	7	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   70	79	82	finally
    //   70	79	94	java/lang/Exception
    //   60	70	99	java/lang/Exception
    //   116	121	124	java/io/IOException
    //   133	137	138	java/io/IOException
    //   192	196	138	java/io/IOException
    //   10	16	144	finally
    //   21	39	144	finally
    //   44	54	144	finally
    //   10	16	151	java/lang/Exception
    //   21	39	151	java/lang/Exception
    //   44	54	151	java/lang/Exception
    //   175	180	183	java/io/IOException
    //   60	70	197	finally
    //   165	170	197	finally
    //   206	210	213	java/io/IOException
    //   222	226	229	java/io/IOException
  }
  
  /* Error */
  void setCoreInstallStatus(int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: new 155	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc -98
    //   11: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_3
    //   16: iload_1
    //   17: invokevirtual 165	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   20: pop
    //   21: aload_3
    //   22: invokevirtual 169	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   25: pop
    //   26: new 155	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   33: astore_3
    //   34: aload_3
    //   35: ldc -85
    //   37: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload_3
    //   42: iload_2
    //   43: invokevirtual 165	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload_3
    //   48: invokevirtual 169	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: pop
    //   52: aconst_null
    //   53: astore 5
    //   55: aconst_null
    //   56: astore 4
    //   58: aconst_null
    //   59: astore 8
    //   61: aload_0
    //   62: invokespecial 94	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallProp	()Ljava/util/Properties;
    //   65: astore 7
    //   67: aload 7
    //   69: ifnull +101 -> 170
    //   72: aload 7
    //   74: ldc 20
    //   76: iload_1
    //   77: invokestatic 130	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   80: invokevirtual 134	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   83: pop
    //   84: aload 7
    //   86: ldc 17
    //   88: iload_2
    //   89: invokestatic 130	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   92: invokevirtual 134	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   95: pop
    //   96: aload_0
    //   97: invokevirtual 43	com/tencent/tbs/tbsshell/partner/miniqb/upgrade/MiniQBInstallConfig:getCoreInstallScheduleFile	()Ljava/io/File;
    //   100: astore_3
    //   101: aload_3
    //   102: ifnull +68 -> 170
    //   105: new 136	java/io/FileOutputStream
    //   108: dup
    //   109: aload_3
    //   110: invokespecial 137	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   113: astore_3
    //   114: aload 5
    //   116: astore 4
    //   118: aload_3
    //   119: astore 5
    //   121: new 139	java/io/BufferedOutputStream
    //   124: dup
    //   125: aload_3
    //   126: invokespecial 142	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   129: astore 6
    //   131: aload 7
    //   133: aload 6
    //   135: ldc -83
    //   137: invokevirtual 148	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   140: aload 6
    //   142: astore 4
    //   144: goto +32 -> 176
    //   147: astore 5
    //   149: aload 6
    //   151: astore 4
    //   153: goto +123 -> 276
    //   156: astore 7
    //   158: goto +68 -> 226
    //   161: astore 7
    //   163: aload 8
    //   165: astore 6
    //   167: goto +59 -> 226
    //   170: aconst_null
    //   171: astore 4
    //   173: aload 4
    //   175: astore_3
    //   176: aload 4
    //   178: ifnull +18 -> 196
    //   181: aload 4
    //   183: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   186: goto +10 -> 196
    //   189: astore 4
    //   191: aload 4
    //   193: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   196: aload_3
    //   197: ifnull +69 -> 266
    //   200: aload_3
    //   201: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   204: return
    //   205: astore_3
    //   206: aload_3
    //   207: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   210: return
    //   211: astore 5
    //   213: aconst_null
    //   214: astore_3
    //   215: goto +61 -> 276
    //   218: astore 7
    //   220: aconst_null
    //   221: astore_3
    //   222: aload 8
    //   224: astore 6
    //   226: aload 6
    //   228: astore 4
    //   230: aload_3
    //   231: astore 5
    //   233: aload 7
    //   235: invokevirtual 67	java/lang/Exception:printStackTrace	()V
    //   238: aload 6
    //   240: ifnull +18 -> 258
    //   243: aload 6
    //   245: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   248: goto +10 -> 258
    //   251: astore 4
    //   253: aload 4
    //   255: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   258: aload_3
    //   259: ifnull +7 -> 266
    //   262: aload_3
    //   263: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   266: return
    //   267: astore 6
    //   269: aload 5
    //   271: astore_3
    //   272: aload 6
    //   274: astore 5
    //   276: aload 4
    //   278: ifnull +18 -> 296
    //   281: aload 4
    //   283: invokevirtual 149	java/io/BufferedOutputStream:close	()V
    //   286: goto +10 -> 296
    //   289: astore 4
    //   291: aload 4
    //   293: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   296: aload_3
    //   297: ifnull +15 -> 312
    //   300: aload_3
    //   301: invokevirtual 150	java/io/FileOutputStream:close	()V
    //   304: goto +8 -> 312
    //   307: astore_3
    //   308: aload_3
    //   309: invokevirtual 65	java/io/IOException:printStackTrace	()V
    //   312: aload 5
    //   314: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	315	0	this	MiniQBInstallConfig
    //   0	315	1	paramInt1	int
    //   0	315	2	paramInt2	int
    //   7	194	3	localObject1	Object
    //   205	2	3	localIOException1	IOException
    //   214	87	3	localObject2	Object
    //   307	2	3	localIOException2	IOException
    //   56	126	4	localObject3	Object
    //   189	3	4	localIOException3	IOException
    //   228	1	4	localObject4	Object
    //   251	31	4	localIOException4	IOException
    //   289	3	4	localIOException5	IOException
    //   53	67	5	localObject5	Object
    //   147	1	5	localObject6	Object
    //   211	1	5	localObject7	Object
    //   231	82	5	localObject8	Object
    //   129	115	6	localObject9	Object
    //   267	6	6	localObject10	Object
    //   65	67	7	localProperties	Properties
    //   156	1	7	localException1	Exception
    //   161	1	7	localException2	Exception
    //   218	16	7	localException3	Exception
    //   59	164	8	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   131	140	147	finally
    //   131	140	156	java/lang/Exception
    //   121	131	161	java/lang/Exception
    //   181	186	189	java/io/IOException
    //   200	204	205	java/io/IOException
    //   262	266	205	java/io/IOException
    //   61	67	211	finally
    //   72	101	211	finally
    //   105	114	211	finally
    //   61	67	218	java/lang/Exception
    //   72	101	218	java/lang/Exception
    //   105	114	218	java/lang/Exception
    //   243	248	251	java/io/IOException
    //   121	131	267	finally
    //   233	238	267	finally
    //   281	286	289	java/io/IOException
    //   300	304	307	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBInstallConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */