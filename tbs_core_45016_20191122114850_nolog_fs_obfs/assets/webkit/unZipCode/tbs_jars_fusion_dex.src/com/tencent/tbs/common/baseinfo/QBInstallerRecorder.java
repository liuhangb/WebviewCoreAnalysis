package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;

public class QBInstallerRecorder
{
  private static final String INSTALLER_FILE = "ins.dat";
  private static final String SYS_SETTING_TBS_QB_INSTALLER = "sys_setting_tbs_qb_installer";
  private static final String TAG = "QBInstallerRecorder";
  private static QBInstallerRecorder mIntance;
  private Context mContext = null;
  
  public static QBInstallerRecorder getInstance()
  {
    if (mIntance == null) {
      mIntance = new QBInstallerRecorder();
    }
    return mIntance;
  }
  
  /* Error */
  private String loadQBInstallerFromSDCard()
  {
    // Byte code:
    //   0: ldc 14
    //   2: ldc 47
    //   4: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: invokestatic 59	com/tencent/common/utils/FileUtils:hasSDcard	()Z
    //   10: ifne +13 -> 23
    //   13: ldc 14
    //   15: ldc 61
    //   17: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   20: ldc 63
    //   22: areturn
    //   23: aconst_null
    //   24: astore 4
    //   26: aconst_null
    //   27: astore 5
    //   29: aconst_null
    //   30: astore_2
    //   31: aload_2
    //   32: astore_1
    //   33: new 65	java/io/File
    //   36: dup
    //   37: invokestatic 69	com/tencent/common/utils/FileUtils:getTesSdcardShareDir	()Ljava/io/File;
    //   40: ldc 8
    //   42: invokespecial 72	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   45: astore_3
    //   46: aload_2
    //   47: astore_1
    //   48: aload_3
    //   49: invokevirtual 75	java/io/File:exists	()Z
    //   52: ifne +15 -> 67
    //   55: aload_2
    //   56: astore_1
    //   57: ldc 14
    //   59: ldc 77
    //   61: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   64: ldc 63
    //   66: areturn
    //   67: aload_2
    //   68: astore_1
    //   69: aload_3
    //   70: invokestatic 81	com/tencent/common/utils/FileUtils:read	(Ljava/io/File;)Ljava/nio/ByteBuffer;
    //   73: astore 6
    //   75: aload 6
    //   77: ifnull +105 -> 182
    //   80: aload_2
    //   81: astore_1
    //   82: aload 6
    //   84: invokevirtual 87	java/nio/ByteBuffer:position	()I
    //   87: ifgt +6 -> 93
    //   90: goto +92 -> 182
    //   93: aload_2
    //   94: astore_1
    //   95: invokestatic 90	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   98: aload 6
    //   100: invokevirtual 94	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   103: pop
    //   104: aload_2
    //   105: astore_1
    //   106: new 96	java/io/DataInputStream
    //   109: dup
    //   110: aload_3
    //   111: invokestatic 100	com/tencent/common/utils/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   114: invokespecial 103	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   117: astore_2
    //   118: aload_2
    //   119: invokevirtual 106	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   122: astore_1
    //   123: new 108	java/lang/StringBuilder
    //   126: dup
    //   127: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   130: astore_3
    //   131: aload_3
    //   132: ldc 111
    //   134: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: pop
    //   138: aload_3
    //   139: aload_1
    //   140: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: pop
    //   144: ldc 14
    //   146: aload_3
    //   147: invokevirtual 118	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   150: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   153: aload_2
    //   154: invokevirtual 121	java/io/DataInputStream:close	()V
    //   157: aload_1
    //   158: areturn
    //   159: astore_2
    //   160: aload_2
    //   161: invokevirtual 124	java/io/IOException:printStackTrace	()V
    //   164: aload_1
    //   165: areturn
    //   166: astore_1
    //   167: goto +96 -> 263
    //   170: astore_1
    //   171: aload_1
    //   172: astore_3
    //   173: goto +44 -> 217
    //   176: astore_1
    //   177: aload_1
    //   178: astore_3
    //   179: goto +59 -> 238
    //   182: aload_2
    //   183: astore_1
    //   184: invokestatic 90	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   187: aload 6
    //   189: invokevirtual 94	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   192: pop
    //   193: aload_2
    //   194: astore_1
    //   195: ldc 14
    //   197: ldc 126
    //   199: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   202: ldc 63
    //   204: areturn
    //   205: astore_3
    //   206: aload_1
    //   207: astore_2
    //   208: aload_3
    //   209: astore_1
    //   210: goto +53 -> 263
    //   213: astore_3
    //   214: aload 4
    //   216: astore_2
    //   217: aload_2
    //   218: astore_1
    //   219: aload_3
    //   220: invokevirtual 127	java/lang/Error:printStackTrace	()V
    //   223: aload_2
    //   224: ifnull +36 -> 260
    //   227: aload_2
    //   228: invokevirtual 121	java/io/DataInputStream:close	()V
    //   231: goto +29 -> 260
    //   234: astore_3
    //   235: aload 5
    //   237: astore_2
    //   238: aload_2
    //   239: astore_1
    //   240: aload_3
    //   241: invokevirtual 128	java/lang/Exception:printStackTrace	()V
    //   244: aload_2
    //   245: ifnull +15 -> 260
    //   248: aload_2
    //   249: invokevirtual 121	java/io/DataInputStream:close	()V
    //   252: goto +8 -> 260
    //   255: astore_1
    //   256: aload_1
    //   257: invokevirtual 124	java/io/IOException:printStackTrace	()V
    //   260: ldc 63
    //   262: areturn
    //   263: aload_2
    //   264: ifnull +15 -> 279
    //   267: aload_2
    //   268: invokevirtual 121	java/io/DataInputStream:close	()V
    //   271: goto +8 -> 279
    //   274: astore_2
    //   275: aload_2
    //   276: invokevirtual 124	java/io/IOException:printStackTrace	()V
    //   279: aload_1
    //   280: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	this	QBInstallerRecorder
    //   32	133	1	localObject1	Object
    //   166	1	1	localObject2	Object
    //   170	2	1	localError1	Error
    //   176	2	1	localException1	Exception
    //   183	57	1	localObject3	Object
    //   255	25	1	localIOException1	java.io.IOException
    //   30	124	2	localDataInputStream	java.io.DataInputStream
    //   159	35	2	localIOException2	java.io.IOException
    //   207	61	2	localObject4	Object
    //   274	2	2	localIOException3	java.io.IOException
    //   45	134	3	localObject5	Object
    //   205	4	3	localObject6	Object
    //   213	7	3	localError2	Error
    //   234	7	3	localException2	Exception
    //   24	191	4	localObject7	Object
    //   27	209	5	localObject8	Object
    //   73	115	6	localByteBuffer	java.nio.ByteBuffer
    // Exception table:
    //   from	to	target	type
    //   153	157	159	java/io/IOException
    //   118	153	166	finally
    //   118	153	170	java/lang/Error
    //   118	153	176	java/lang/Exception
    //   33	46	205	finally
    //   48	55	205	finally
    //   57	64	205	finally
    //   69	75	205	finally
    //   82	90	205	finally
    //   95	104	205	finally
    //   106	118	205	finally
    //   184	193	205	finally
    //   195	202	205	finally
    //   219	223	205	finally
    //   240	244	205	finally
    //   33	46	213	java/lang/Error
    //   48	55	213	java/lang/Error
    //   57	64	213	java/lang/Error
    //   69	75	213	java/lang/Error
    //   82	90	213	java/lang/Error
    //   95	104	213	java/lang/Error
    //   106	118	213	java/lang/Error
    //   184	193	213	java/lang/Error
    //   195	202	213	java/lang/Error
    //   33	46	234	java/lang/Exception
    //   48	55	234	java/lang/Exception
    //   57	64	234	java/lang/Exception
    //   69	75	234	java/lang/Exception
    //   82	90	234	java/lang/Exception
    //   95	104	234	java/lang/Exception
    //   106	118	234	java/lang/Exception
    //   184	193	234	java/lang/Exception
    //   195	202	234	java/lang/Exception
    //   227	231	255	java/io/IOException
    //   248	252	255	java/io/IOException
    //   267	271	274	java/io/IOException
  }
  
  private String loadQBInstallerFromSysSetting()
  {
    LogUtils.d("QBInstallerRecorder", "load QBInstaller From sys setting");
    Object localObject = "";
    try
    {
      String str = Settings.System.getString(this.mContext.getContentResolver(), "sys_setting_tbs_qb_installer");
      localObject = str;
      if (TextUtils.isEmpty(str))
      {
        localObject = str;
        LogUtils.d("QBInstallerRecorder", "can not find installer in system setting");
        return "";
      }
      localObject = str;
      StringBuilder localStringBuilder = new StringBuilder();
      localObject = str;
      localStringBuilder.append("load installer from sys setting end, installer=");
      localObject = str;
      localStringBuilder.append(str);
      localObject = str;
      LogUtils.d("QBInstallerRecorder", localStringBuilder.toString());
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return (String)localObject;
  }
  
  /* Error */
  private void recordInstallerToSDCard(String paramString)
  {
    // Byte code:
    //   0: ldc 14
    //   2: ldc -99
    //   4: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: invokestatic 59	com/tencent/common/utils/FileUtils:hasSDcard	()Z
    //   10: ifne +11 -> 21
    //   13: ldc 14
    //   15: ldc 61
    //   17: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   20: return
    //   21: aconst_null
    //   22: astore 4
    //   24: aconst_null
    //   25: astore_3
    //   26: aload_3
    //   27: astore_2
    //   28: new 65	java/io/File
    //   31: dup
    //   32: invokestatic 69	com/tencent/common/utils/FileUtils:getTesSdcardShareDir	()Ljava/io/File;
    //   35: ldc 8
    //   37: invokespecial 72	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   40: astore 5
    //   42: aload_3
    //   43: astore_2
    //   44: aload 5
    //   46: invokevirtual 75	java/io/File:exists	()Z
    //   49: ifne +20 -> 69
    //   52: aload_3
    //   53: astore_2
    //   54: ldc 14
    //   56: ldc -97
    //   58: invokestatic 53	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   61: aload_3
    //   62: astore_2
    //   63: aload 5
    //   65: invokevirtual 162	java/io/File:createNewFile	()Z
    //   68: pop
    //   69: aload_3
    //   70: astore_2
    //   71: new 164	java/io/DataOutputStream
    //   74: dup
    //   75: aload 5
    //   77: invokestatic 168	com/tencent/common/utils/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   80: invokespecial 171	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   83: astore_3
    //   84: aload_3
    //   85: aload_1
    //   86: invokevirtual 174	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   89: aload_3
    //   90: invokevirtual 177	java/io/DataOutputStream:flush	()V
    //   93: aload_3
    //   94: invokevirtual 178	java/io/DataOutputStream:close	()V
    //   97: return
    //   98: astore_1
    //   99: aload_3
    //   100: astore_2
    //   101: goto +40 -> 141
    //   104: astore_2
    //   105: aload_3
    //   106: astore_1
    //   107: aload_2
    //   108: astore_3
    //   109: goto +11 -> 120
    //   112: astore_1
    //   113: goto +28 -> 141
    //   116: astore_3
    //   117: aload 4
    //   119: astore_1
    //   120: aload_1
    //   121: astore_2
    //   122: aload_3
    //   123: invokevirtual 128	java/lang/Exception:printStackTrace	()V
    //   126: aload_1
    //   127: ifnull +13 -> 140
    //   130: aload_1
    //   131: invokevirtual 178	java/io/DataOutputStream:close	()V
    //   134: return
    //   135: astore_1
    //   136: aload_1
    //   137: invokevirtual 124	java/io/IOException:printStackTrace	()V
    //   140: return
    //   141: aload_2
    //   142: ifnull +15 -> 157
    //   145: aload_2
    //   146: invokevirtual 178	java/io/DataOutputStream:close	()V
    //   149: goto +8 -> 157
    //   152: astore_2
    //   153: aload_2
    //   154: invokevirtual 124	java/io/IOException:printStackTrace	()V
    //   157: aload_1
    //   158: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	159	0	this	QBInstallerRecorder
    //   0	159	1	paramString	String
    //   27	74	2	localObject1	Object
    //   104	4	2	localException1	Exception
    //   121	25	2	str	String
    //   152	2	2	localIOException	java.io.IOException
    //   25	84	3	localObject2	Object
    //   116	7	3	localException2	Exception
    //   22	96	4	localObject3	Object
    //   40	36	5	localFile	java.io.File
    // Exception table:
    //   from	to	target	type
    //   84	93	98	finally
    //   84	93	104	java/lang/Exception
    //   28	42	112	finally
    //   44	52	112	finally
    //   54	61	112	finally
    //   63	69	112	finally
    //   71	84	112	finally
    //   122	126	112	finally
    //   28	42	116	java/lang/Exception
    //   44	52	116	java/lang/Exception
    //   54	61	116	java/lang/Exception
    //   63	69	116	java/lang/Exception
    //   71	84	116	java/lang/Exception
    //   93	97	135	java/io/IOException
    //   130	134	135	java/io/IOException
    //   145	149	152	java/io/IOException
  }
  
  private void recordInstallerToSysSetting(String paramString)
  {
    LogUtils.d("QBInstallerRecorder", "record installer to sys setting");
    try
    {
      Settings.System.putString(this.mContext.getContentResolver(), "sys_setting_tbs_qb_installer", paramString);
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public String getQBInstaller()
  {
    LogUtils.d("QBInstallerRecorder", "load installer begin");
    String str = loadQBInstallerFromSDCard();
    if (!TextUtils.isEmpty(str))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("load installer from sdCard=");
      localStringBuilder.append(str);
      localStringBuilder.append(", it is validate, this is installer");
      LogUtils.d("QBInstallerRecorder", localStringBuilder.toString());
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("load installer from sdCard=");
    localStringBuilder.append(str);
    localStringBuilder.append(", it is not validate, load from sysSetting");
    LogUtils.d("QBInstallerRecorder", localStringBuilder.toString());
    str = loadQBInstallerFromSysSetting();
    LogUtils.d("QBInstallerRecorder", "load installer end");
    return str;
  }
  
  public void recordQBInstaller(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("recordQBInstaller installer=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", begin");
    LogUtils.d("QBInstallerRecorder", localStringBuilder.toString());
    if (TextUtils.isEmpty(paramString))
    {
      LogUtils.d("QBInstallerRecorder", "recordQBInstaller parameter invalidate, ignore");
      return;
    }
    recordInstallerToSDCard(paramString);
    recordInstallerToSysSetting(paramString);
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("recordQBInstaller installer=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", end");
    LogUtils.d("QBInstallerRecorder", localStringBuilder.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\QBInstallerRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */