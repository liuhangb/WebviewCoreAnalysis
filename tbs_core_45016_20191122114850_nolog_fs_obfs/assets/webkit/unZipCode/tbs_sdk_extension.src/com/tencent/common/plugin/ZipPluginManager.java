package com.tencent.common.plugin;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.FileUtilsF;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

public class ZipPluginManager
{
  private static ZipPluginManager jdField_a_of_type_ComTencentCommonPluginZipPluginManager;
  private FilenameFilter jdField_a_of_type_JavaIoFilenameFilter = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return paramAnonymousString.endsWith(".zip");
    }
  };
  HashMap<String, QBPluginInfo> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  
  private File a(String paramString, QBPluginItemInfo paramQBPluginItemInfo)
  {
    paramQBPluginItemInfo = paramQBPluginItemInfo.mDownloadDir;
    if (TextUtils.isEmpty(paramQBPluginItemInfo))
    {
      paramQBPluginItemInfo = new StringBuilder();
      paramQBPluginItemInfo.append("getZipAvailable():packageName=");
      paramQBPluginItemInfo.append(paramString);
      paramQBPluginItemInfo.append(",downloadFailed");
      FLogger.d("ZipPluginManager", paramQBPluginItemInfo.toString());
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(ZipPluginCheck.getInstance().genMd5(paramString));
    localStringBuilder.append(".zip");
    paramString = new File(paramQBPluginItemInfo, localStringBuilder.toString());
    if ((paramString.exists()) && (QBPluginServiceImpl.checkPluginSign(paramString.getAbsolutePath()))) {
      return paramString;
    }
    paramString = new File(paramQBPluginItemInfo);
    if (paramString.exists())
    {
      paramString = paramString.listFiles(this.jdField_a_of_type_JavaIoFilenameFilter);
      if ((paramString != null) && (paramString.length > 0)) {
        try
        {
          int j = paramString.length;
          int i = 0;
          while (i < j)
          {
            FileUtilsF.delete(paramString[i]);
            i += 1;
          }
          return null;
        }
        catch (Exception paramString)
        {
          paramString.printStackTrace();
        }
      }
    }
  }
  
  public static ZipPluginManager getInstance()
  {
    if (jdField_a_of_type_ComTencentCommonPluginZipPluginManager == null) {
      jdField_a_of_type_ComTencentCommonPluginZipPluginManager = new ZipPluginManager();
    }
    return jdField_a_of_type_ComTencentCommonPluginZipPluginManager;
  }
  
  /* Error */
  public QBPluginInfo scanPlugin(String paramString, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore 5
    //   5: invokestatic 123	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   8: aload_1
    //   9: iload_2
    //   10: invokevirtual 127	com/tencent/common/plugin/QBPluginServiceImpl:getPluginInfo	(Ljava/lang/String;I)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   13: astore_3
    //   14: goto +14 -> 28
    //   17: astore_1
    //   18: goto +154 -> 172
    //   21: astore_3
    //   22: aload_3
    //   23: invokevirtual 128	android/os/RemoteException:printStackTrace	()V
    //   26: aconst_null
    //   27: astore_3
    //   28: aload_3
    //   29: ifnonnull +7 -> 36
    //   32: aload_0
    //   33: monitorexit
    //   34: aconst_null
    //   35: areturn
    //   36: aload_3
    //   37: ifnull +139 -> 176
    //   40: aload_3
    //   41: getfield 131	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   44: invokestatic 42	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   47: ifne +129 -> 176
    //   50: new 79	java/io/File
    //   53: dup
    //   54: aload_3
    //   55: getfield 131	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   58: invokespecial 97	java/io/File:<init>	(Ljava/lang/String;)V
    //   61: astore 4
    //   63: goto +3 -> 66
    //   66: aload 4
    //   68: ifnull +100 -> 168
    //   71: aload 4
    //   73: invokevirtual 85	java/io/File:exists	()Z
    //   76: ifne +6 -> 82
    //   79: goto +89 -> 168
    //   82: invokestatic 71	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   85: pop
    //   86: aload 5
    //   88: astore_3
    //   89: aload 4
    //   91: aload_1
    //   92: invokestatic 135	com/tencent/common/plugin/ZipPluginCheck:checkLocalPluginNotModified	(Ljava/io/File;Ljava/lang/String;)I
    //   95: ifne +69 -> 164
    //   98: new 137	com/tencent/common/plugin/QBPluginInfo
    //   101: dup
    //   102: invokespecial 138	com/tencent/common/plugin/QBPluginInfo:<init>	()V
    //   105: astore 6
    //   107: aload 6
    //   109: aload_1
    //   110: putfield 141	com/tencent/common/plugin/QBPluginInfo:mPackageName	Ljava/lang/String;
    //   113: aload 6
    //   115: aload 4
    //   117: invokevirtual 88	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   120: putfield 144	com/tencent/common/plugin/QBPluginInfo:mLocalPath	Ljava/lang/String;
    //   123: invokestatic 71	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   126: pop
    //   127: aload 4
    //   129: aload_1
    //   130: invokestatic 148	com/tencent/common/plugin/ZipPluginCheck:getPluginVerCode	(Ljava/io/File;Ljava/lang/String;)Ljava/lang/String;
    //   133: astore_1
    //   134: aload 5
    //   136: astore_3
    //   137: aload_1
    //   138: ifnull +26 -> 164
    //   141: aload 6
    //   143: aload_1
    //   144: invokestatic 154	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   147: putfield 158	com/tencent/common/plugin/QBPluginInfo:mVersionCode	I
    //   150: aload 6
    //   152: astore_3
    //   153: goto +11 -> 164
    //   156: astore_1
    //   157: aload_1
    //   158: invokevirtual 159	java/lang/NumberFormatException:printStackTrace	()V
    //   161: aload 5
    //   163: astore_3
    //   164: aload_0
    //   165: monitorexit
    //   166: aload_3
    //   167: areturn
    //   168: aload_0
    //   169: monitorexit
    //   170: aconst_null
    //   171: areturn
    //   172: aload_0
    //   173: monitorexit
    //   174: aload_1
    //   175: athrow
    //   176: aconst_null
    //   177: astore 4
    //   179: goto -113 -> 66
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	182	0	this	ZipPluginManager
    //   0	182	1	paramString	String
    //   0	182	2	paramInt	int
    //   13	1	3	localQBPluginItemInfo	QBPluginItemInfo
    //   21	2	3	localRemoteException	android.os.RemoteException
    //   27	140	3	localObject1	Object
    //   61	117	4	localFile	File
    //   3	159	5	localObject2	Object
    //   105	46	6	localQBPluginInfo	QBPluginInfo
    // Exception table:
    //   from	to	target	type
    //   5	14	17	finally
    //   22	26	17	finally
    //   40	63	17	finally
    //   71	79	17	finally
    //   82	86	17	finally
    //   89	134	17	finally
    //   141	150	17	finally
    //   157	161	17	finally
    //   5	14	21	android/os/RemoteException
    //   141	150	156	java/lang/NumberFormatException
  }
  
  public boolean scanPluginInDir(String paramString1, String paramString2)
  {
    File localFile = null;
    try
    {
      if (!TextUtils.isEmpty(paramString2)) {
        localFile = new File(paramString2);
      }
      boolean bool2 = false;
      if ((localFile != null) && (localFile.exists()))
      {
        ZipPluginCheck.getInstance();
        boolean bool1 = bool2;
        if (ZipPluginCheck.checkLocalPluginNotModified(localFile, paramString1) == 0)
        {
          ZipPluginCheck.getInstance();
          paramString1 = ZipPluginCheck.getPluginVerCode(localFile, paramString1);
          bool1 = bool2;
          if (paramString1 != null) {
            bool1 = true;
          }
        }
        return bool1;
      }
      return false;
    }
    finally {}
  }
  
  /* Error */
  public QBPluginInfo scanZipLocalPluginInfo(String paramString, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore 8
    //   5: invokestatic 123	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   8: aload_1
    //   9: iload_2
    //   10: invokevirtual 127	com/tencent/common/plugin/QBPluginServiceImpl:getPluginInfo	(Ljava/lang/String;I)Lcom/tencent/common/plugin/QBPluginItemInfo;
    //   13: astore 6
    //   15: goto +17 -> 32
    //   18: astore_1
    //   19: goto +393 -> 412
    //   22: astore 5
    //   24: aload 5
    //   26: invokevirtual 128	android/os/RemoteException:printStackTrace	()V
    //   29: aconst_null
    //   30: astore 6
    //   32: aload 6
    //   34: ifnonnull +7 -> 41
    //   37: aload_0
    //   38: monitorexit
    //   39: aconst_null
    //   40: areturn
    //   41: aload 6
    //   43: ifnull +373 -> 416
    //   46: aload 6
    //   48: getfield 165	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   51: invokestatic 42	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   54: ifne +362 -> 416
    //   57: new 79	java/io/File
    //   60: dup
    //   61: aload 6
    //   63: getfield 165	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   66: invokespecial 97	java/io/File:<init>	(Ljava/lang/String;)V
    //   69: astore 5
    //   71: goto +3 -> 74
    //   74: aload 5
    //   76: ifnull +379 -> 455
    //   79: aload 5
    //   81: invokevirtual 85	java/io/File:exists	()Z
    //   84: ifeq +371 -> 455
    //   87: invokestatic 71	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   90: pop
    //   91: aload 5
    //   93: aload_1
    //   94: invokestatic 135	com/tencent/common/plugin/ZipPluginCheck:checkLocalPluginNotModified	(Ljava/io/File;Ljava/lang/String;)I
    //   97: istore_3
    //   98: iload_3
    //   99: iconst_1
    //   100: if_icmpne +322 -> 422
    //   103: iconst_1
    //   104: istore_2
    //   105: goto +3 -> 108
    //   108: iload_3
    //   109: ifne +332 -> 441
    //   112: new 137	com/tencent/common/plugin/QBPluginInfo
    //   115: dup
    //   116: invokespecial 138	com/tencent/common/plugin/QBPluginInfo:<init>	()V
    //   119: astore 9
    //   121: aload 9
    //   123: aload_1
    //   124: putfield 141	com/tencent/common/plugin/QBPluginInfo:mPackageName	Ljava/lang/String;
    //   127: aload 9
    //   129: aload 5
    //   131: invokevirtual 88	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   134: putfield 144	com/tencent/common/plugin/QBPluginInfo:mLocalPath	Ljava/lang/String;
    //   137: invokestatic 71	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   140: pop
    //   141: aload 5
    //   143: aload_1
    //   144: invokestatic 148	com/tencent/common/plugin/ZipPluginCheck:getPluginVerCode	(Ljava/io/File;Ljava/lang/String;)Ljava/lang/String;
    //   147: astore 7
    //   149: aload 7
    //   151: ifnull +276 -> 427
    //   154: aload 9
    //   156: aload 7
    //   158: invokestatic 154	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   161: putfield 158	com/tencent/common/plugin/QBPluginInfo:mVersionCode	I
    //   164: aload 5
    //   166: astore 7
    //   168: aload 9
    //   170: astore 5
    //   172: goto +24 -> 196
    //   175: astore 7
    //   177: aload 7
    //   179: invokevirtual 159	java/lang/NumberFormatException:printStackTrace	()V
    //   182: aconst_null
    //   183: astore 9
    //   185: aload 5
    //   187: astore 7
    //   189: aload 9
    //   191: astore 5
    //   193: goto +3 -> 196
    //   196: iload_2
    //   197: ifeq +208 -> 405
    //   200: aload_0
    //   201: aload_1
    //   202: aload 6
    //   204: invokespecial 167	com/tencent/common/plugin/ZipPluginManager:a	(Ljava/lang/String;Lcom/tencent/common/plugin/QBPluginItemInfo;)Ljava/io/File;
    //   207: astore 9
    //   209: aload 9
    //   211: ifnull +194 -> 405
    //   214: aload 9
    //   216: invokevirtual 85	java/io/File:exists	()Z
    //   219: ifeq +186 -> 405
    //   222: aload 9
    //   224: invokevirtual 88	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   227: lconst_0
    //   228: sipush 256
    //   231: invokestatic 171	com/tencent/common/utils/FileUtilsF:read	(Ljava/lang/String;JI)Ljava/nio/ByteBuffer;
    //   234: astore 10
    //   236: aload 10
    //   238: invokevirtual 177	java/nio/ByteBuffer:array	()[B
    //   241: iconst_0
    //   242: aload 10
    //   244: invokevirtual 181	java/nio/ByteBuffer:position	()I
    //   247: invokestatic 187	com/tencent/common/utils/Md5Utils:getMD5	([BII)[B
    //   250: invokestatic 193	com/tencent/common/utils/ByteUtils:byteToHexString	([B)Ljava/lang/String;
    //   253: astore 11
    //   255: invokestatic 196	com/tencent/common/utils/FileUtilsF:getInstance	()Lcom/tencent/common/utils/FileUtilsF;
    //   258: aload 10
    //   260: invokevirtual 200	com/tencent/common/utils/FileUtilsF:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   263: pop
    //   264: aload 11
    //   266: invokestatic 42	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   269: istore 4
    //   271: iload 4
    //   273: ifeq +7 -> 280
    //   276: aload_0
    //   277: monitorexit
    //   278: aconst_null
    //   279: areturn
    //   280: aload 6
    //   282: getfield 203	com/tencent/common/plugin/QBPluginItemInfo:mMd5	Ljava/lang/String;
    //   285: invokestatic 42	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   288: ifne +24 -> 312
    //   291: aload 6
    //   293: getfield 203	com/tencent/common/plugin/QBPluginItemInfo:mMd5	Ljava/lang/String;
    //   296: aload 11
    //   298: invokevirtual 209	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   301: istore 4
    //   303: iload 4
    //   305: ifne +7 -> 312
    //   308: aload_0
    //   309: monitorexit
    //   310: aconst_null
    //   311: areturn
    //   312: invokestatic 71	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   315: pop
    //   316: aload 7
    //   318: aload_1
    //   319: invokestatic 148	com/tencent/common/plugin/ZipPluginCheck:getPluginVerCode	(Ljava/io/File;Ljava/lang/String;)Ljava/lang/String;
    //   322: astore 6
    //   324: aload 6
    //   326: invokestatic 42	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   329: istore 4
    //   331: iload 4
    //   333: ifne +72 -> 405
    //   336: new 137	com/tencent/common/plugin/QBPluginInfo
    //   339: dup
    //   340: invokespecial 138	com/tencent/common/plugin/QBPluginInfo:<init>	()V
    //   343: astore 5
    //   345: aload 5
    //   347: aload_1
    //   348: putfield 141	com/tencent/common/plugin/QBPluginInfo:mPackageName	Ljava/lang/String;
    //   351: aload 5
    //   353: aload 9
    //   355: invokevirtual 88	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   358: putfield 212	com/tencent/common/plugin/QBPluginInfo:mLocalZipPath	Ljava/lang/String;
    //   361: aload 5
    //   363: iconst_1
    //   364: putfield 216	com/tencent/common/plugin/QBPluginInfo:mNeedUnzipFromBack	Z
    //   367: aload 5
    //   369: aload 6
    //   371: invokestatic 154	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   374: putfield 158	com/tencent/common/plugin/QBPluginInfo:mVersionCode	I
    //   377: aload 5
    //   379: astore_1
    //   380: goto +28 -> 408
    //   383: astore_1
    //   384: aload_1
    //   385: invokevirtual 159	java/lang/NumberFormatException:printStackTrace	()V
    //   388: aload 8
    //   390: astore_1
    //   391: goto +17 -> 408
    //   394: astore_1
    //   395: aload_1
    //   396: invokevirtual 110	java/lang/Exception:printStackTrace	()V
    //   399: aload 8
    //   401: astore_1
    //   402: goto +6 -> 408
    //   405: aload 5
    //   407: astore_1
    //   408: aload_0
    //   409: monitorexit
    //   410: aload_1
    //   411: areturn
    //   412: aload_0
    //   413: monitorexit
    //   414: aload_1
    //   415: athrow
    //   416: aconst_null
    //   417: astore 5
    //   419: goto -345 -> 74
    //   422: iconst_0
    //   423: istore_2
    //   424: goto -316 -> 108
    //   427: aconst_null
    //   428: astore 9
    //   430: aload 5
    //   432: astore 7
    //   434: aload 9
    //   436: astore 5
    //   438: goto -242 -> 196
    //   441: aconst_null
    //   442: astore 9
    //   444: aload 5
    //   446: astore 7
    //   448: aload 9
    //   450: astore 5
    //   452: goto -256 -> 196
    //   455: aconst_null
    //   456: astore 7
    //   458: aload 7
    //   460: astore 5
    //   462: iconst_0
    //   463: istore_2
    //   464: goto -268 -> 196
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	467	0	this	ZipPluginManager
    //   0	467	1	paramString	String
    //   0	467	2	paramInt	int
    //   97	12	3	i	int
    //   269	63	4	bool	boolean
    //   22	3	5	localRemoteException	android.os.RemoteException
    //   69	392	5	localObject1	Object
    //   13	357	6	localObject2	Object
    //   147	20	7	localObject3	Object
    //   175	3	7	localNumberFormatException	NumberFormatException
    //   187	272	7	localObject4	Object
    //   3	397	8	localObject5	Object
    //   119	330	9	localObject6	Object
    //   234	25	10	localByteBuffer	java.nio.ByteBuffer
    //   253	44	11	str	String
    // Exception table:
    //   from	to	target	type
    //   5	15	18	finally
    //   24	29	18	finally
    //   46	71	18	finally
    //   79	98	18	finally
    //   112	149	18	finally
    //   154	164	18	finally
    //   177	182	18	finally
    //   200	209	18	finally
    //   214	271	18	finally
    //   280	303	18	finally
    //   312	331	18	finally
    //   336	367	18	finally
    //   367	377	18	finally
    //   384	388	18	finally
    //   395	399	18	finally
    //   5	15	22	android/os/RemoteException
    //   154	164	175	java/lang/NumberFormatException
    //   367	377	383	java/lang/NumberFormatException
    //   336	367	394	java/lang/Exception
    //   367	377	394	java/lang/Exception
    //   384	388	394	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\ZipPluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */