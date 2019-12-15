package com.tencent.common.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.ZipUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@SuppressLint({"NewApi"})
public class PluginUtils
{
  public static final String TAG = "JarPluginManager";
  
  public static QBPluginInfo convert(Context paramContext, JarFileInfo paramJarFileInfo)
  {
    if (paramJarFileInfo != null)
    {
      if (paramJarFileInfo.mCanonicalPath == null) {
        return null;
      }
      if (JarFileParser.getPackageInfo(paramContext, paramJarFileInfo.mCanonicalPath, 4096) != null)
      {
        paramContext = new QBPluginInfo();
        paramContext.mName = paramJarFileInfo.mAppName;
        paramContext.mPackageName = paramJarFileInfo.mPackageName;
        paramContext.mVersionCode = paramJarFileInfo.mVersionCode;
        paramContext.mVersionName = paramJarFileInfo.mVersionName;
        paramContext.mIcon = paramJarFileInfo.mAppIcon;
        paramContext.mLocalPath = paramJarFileInfo.mCanonicalPath;
        paramContext.mSupportResType = paramJarFileInfo.mSupportResType;
        paramContext.mContextMenuText = paramJarFileInfo.mContextMenuText;
        paramContext.mLaunchMode = paramJarFileInfo.mLaunchMode;
        paramContext.mSoName = paramJarFileInfo.mSoName;
        return paramContext;
      }
      return null;
    }
    return null;
  }
  
  /* Error */
  public static boolean installCopyTypePlugin(Context paramContext1, Context paramContext2, File paramFile, String paramString1, String paramString2, int paramInt1, QBPluginItemInfo paramQBPluginItemInfo, IInstallPluginCallback paramIInstallPluginCallback, int paramInt2)
  {
    // Byte code:
    //   0: new 85	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 86	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc 88
    //   11: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_3
    //   16: aload 7
    //   18: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   21: pop
    //   22: ldc 13
    //   24: aload_3
    //   25: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokestatic 105	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   31: invokestatic 111	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   34: aload 4
    //   36: invokevirtual 115	com/tencent/common/plugin/QBPluginServiceImpl:getPluginConfigInfo	(Ljava/lang/String;)Lcom/tencent/common/plugin/QBPluginServiceImpl$PluginConfigInfo;
    //   39: astore 11
    //   41: aconst_null
    //   42: astore 7
    //   44: aload 11
    //   46: ifnull +587 -> 633
    //   49: aload 11
    //   51: getfield 120	com/tencent/common/plugin/QBPluginServiceImpl$PluginConfigInfo:installPath	Ljava/lang/String;
    //   54: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   57: ifne +576 -> 633
    //   60: new 128	java/io/File
    //   63: dup
    //   64: aload 11
    //   66: getfield 120	com/tencent/common/plugin/QBPluginServiceImpl$PluginConfigInfo:installPath	Ljava/lang/String;
    //   69: invokespecial 131	java/io/File:<init>	(Ljava/lang/String;)V
    //   72: astore_3
    //   73: aload_3
    //   74: invokevirtual 135	java/io/File:exists	()Z
    //   77: ifne +550 -> 627
    //   80: aload_3
    //   81: invokevirtual 138	java/io/File:mkdirs	()Z
    //   84: pop
    //   85: goto +542 -> 627
    //   88: iload 9
    //   90: ifne +11 -> 101
    //   93: aload_0
    //   94: aload 4
    //   96: iconst_1
    //   97: invokestatic 142	com/tencent/common/plugin/QBPluginServiceImpl:getPluginInstallDir	(Landroid/content/Context;Ljava/lang/String;Z)Ljava/io/File;
    //   100: astore_3
    //   101: new 85	java/lang/StringBuilder
    //   104: dup
    //   105: invokespecial 86	java/lang/StringBuilder:<init>	()V
    //   108: astore 12
    //   110: aload 12
    //   112: ldc -112
    //   114: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: pop
    //   118: aload_3
    //   119: ifnonnull +9 -> 128
    //   122: ldc -110
    //   124: astore_0
    //   125: goto +8 -> 133
    //   128: aload_3
    //   129: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   132: astore_0
    //   133: aload 12
    //   135: aload_0
    //   136: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: pop
    //   140: ldc 13
    //   142: aload 12
    //   144: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   147: invokestatic 105	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   150: aload_3
    //   151: ifnull +443 -> 594
    //   154: aload_3
    //   155: invokevirtual 135	java/io/File:exists	()Z
    //   158: ifne +6 -> 164
    //   161: goto +433 -> 594
    //   164: aload 4
    //   166: iconst_3
    //   167: aload_3
    //   168: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   171: invokestatic 155	com/tencent/common/plugin/PluginStatBehavior:setInstallDir	(Ljava/lang/String;ILjava/lang/String;)V
    //   174: aload 7
    //   176: astore_0
    //   177: aload 11
    //   179: ifnull +23 -> 202
    //   182: aload 7
    //   184: astore_0
    //   185: aload 11
    //   187: getfield 158	com/tencent/common/plugin/QBPluginServiceImpl$PluginConfigInfo:installFileName	Ljava/lang/String;
    //   190: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   193: ifne +9 -> 202
    //   196: aload 11
    //   198: getfield 158	com/tencent/common/plugin/QBPluginServiceImpl$PluginConfigInfo:installFileName	Ljava/lang/String;
    //   201: astore_0
    //   202: aload_0
    //   203: astore 7
    //   205: aload_0
    //   206: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   209: ifeq +10 -> 219
    //   212: aload 6
    //   214: getfield 163	com/tencent/common/plugin/QBPluginItemInfo:mDownloadFileName	Ljava/lang/String;
    //   217: astore 7
    //   219: new 85	java/lang/StringBuilder
    //   222: dup
    //   223: invokespecial 86	java/lang/StringBuilder:<init>	()V
    //   226: astore_0
    //   227: aload_0
    //   228: ldc -91
    //   230: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: pop
    //   234: aload_0
    //   235: aload 7
    //   237: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: pop
    //   241: ldc 13
    //   243: aload_0
    //   244: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   247: invokestatic 105	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   250: aload 7
    //   252: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   255: ifeq +23 -> 278
    //   258: aload 4
    //   260: iconst_3
    //   261: sipush 525
    //   264: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   267: aload 4
    //   269: iconst_3
    //   270: sipush 525
    //   273: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   276: iconst_0
    //   277: ireturn
    //   278: new 128	java/io/File
    //   281: dup
    //   282: aload_3
    //   283: aload 7
    //   285: invokespecial 175	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   288: astore_0
    //   289: aload_2
    //   290: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   293: aload_0
    //   294: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   297: invokevirtual 181	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   300: ifne +15 -> 315
    //   303: aload_0
    //   304: invokevirtual 135	java/io/File:exists	()Z
    //   307: ifeq +8 -> 315
    //   310: aload_0
    //   311: invokevirtual 184	java/io/File:delete	()Z
    //   314: pop
    //   315: aload_2
    //   316: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   319: aload_0
    //   320: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   323: invokevirtual 188	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   326: ifne +19 -> 345
    //   329: aload_2
    //   330: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   333: aload_0
    //   334: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   337: invokestatic 194	com/tencent/common/utils/FileUtilsF:copyFile	(Ljava/lang/String;Ljava/lang/String;)Z
    //   340: istore 10
    //   342: goto +6 -> 348
    //   345: iconst_1
    //   346: istore 10
    //   348: iload 10
    //   350: ifne +36 -> 386
    //   353: aload 4
    //   355: iconst_3
    //   356: sipush 563
    //   359: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   362: aload_3
    //   363: invokestatic 198	com/tencent/common/utils/FileUtilsF:cleanDirectory	(Ljava/io/File;)V
    //   366: goto +8 -> 374
    //   369: astore_0
    //   370: aload_0
    //   371: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   374: aload 4
    //   376: iconst_3
    //   377: sipush 563
    //   380: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   383: iload 10
    //   385: ireturn
    //   386: aload_2
    //   387: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   390: aload_0
    //   391: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   394: invokevirtual 181	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   397: ifne +8 -> 405
    //   400: aload_2
    //   401: invokestatic 205	com/tencent/common/utils/FileUtilsF:deleteQuietly	(Ljava/io/File;)Z
    //   404: pop
    //   405: invokestatic 210	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   408: pop
    //   409: aload_3
    //   410: invokevirtual 214	java/io/File:getAbsoluteFile	()Ljava/io/File;
    //   413: iload 5
    //   415: aload 4
    //   417: iconst_1
    //   418: anewarray 128	java/io/File
    //   421: dup
    //   422: iconst_0
    //   423: aload_0
    //   424: aastore
    //   425: invokestatic 218	com/tencent/common/plugin/ZipPluginCheck:genCheckList	(Ljava/io/File;ILjava/lang/String;[Ljava/io/File;)Z
    //   428: ifne +23 -> 451
    //   431: aload 4
    //   433: iconst_3
    //   434: sipush 567
    //   437: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   440: aload 4
    //   442: iconst_3
    //   443: sipush 567
    //   446: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   449: iconst_0
    //   450: ireturn
    //   451: invokestatic 111	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   454: aload_3
    //   455: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   458: aload 4
    //   460: iload 5
    //   462: iload 8
    //   464: invokevirtual 222	com/tencent/common/plugin/QBPluginServiceImpl:addPluginInfoToLocalHashMap	(Ljava/lang/String;Ljava/lang/String;II)Z
    //   467: pop
    //   468: aload_3
    //   469: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   472: astore_0
    //   473: aload 6
    //   475: aload_0
    //   476: putfield 225	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   479: aload 6
    //   481: aload_0
    //   482: putfield 228	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   485: new 85	java/lang/StringBuilder
    //   488: dup
    //   489: invokespecial 86	java/lang/StringBuilder:<init>	()V
    //   492: astore_0
    //   493: aload_0
    //   494: iload 5
    //   496: invokevirtual 231	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   499: pop
    //   500: aload_0
    //   501: ldc -23
    //   503: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   506: pop
    //   507: aload 6
    //   509: aload_0
    //   510: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   513: putfield 236	com/tencent/common/plugin/QBPluginItemInfo:mInstallVersion	Ljava/lang/String;
    //   516: aload 6
    //   518: iconst_1
    //   519: putfield 239	com/tencent/common/plugin/QBPluginItemInfo:mIsInstall	I
    //   522: aload_1
    //   523: invokestatic 244	com/tencent/common/plugin/QBPluginDBHelper:getInstance	(Landroid/content/Context;)Lcom/tencent/common/plugin/QBPluginDBHelper;
    //   526: aload 4
    //   528: aload 6
    //   530: getfield 225	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   533: iload 8
    //   535: invokevirtual 248	com/tencent/common/plugin/QBPluginDBHelper:updatePluginInstallDir	(Ljava/lang/String;Ljava/lang/String;I)V
    //   538: aload_1
    //   539: invokestatic 244	com/tencent/common/plugin/QBPluginDBHelper:getInstance	(Landroid/content/Context;)Lcom/tencent/common/plugin/QBPluginDBHelper;
    //   542: aload 4
    //   544: aload 6
    //   546: getfield 228	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   549: iload 8
    //   551: invokevirtual 251	com/tencent/common/plugin/QBPluginDBHelper:updatePluginUnzipDir	(Ljava/lang/String;Ljava/lang/String;I)V
    //   554: invokestatic 111	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   557: aload 4
    //   559: iconst_1
    //   560: iload 8
    //   562: invokevirtual 255	com/tencent/common/plugin/QBPluginServiceImpl:setIsPluginInstall	(Ljava/lang/String;ZI)Z
    //   565: pop
    //   566: aload 4
    //   568: iconst_3
    //   569: aload 6
    //   571: getfield 225	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   574: invokestatic 155	com/tencent/common/plugin/PluginStatBehavior:setInstallDir	(Ljava/lang/String;ILjava/lang/String;)V
    //   577: aload 4
    //   579: iconst_3
    //   580: bipush 50
    //   582: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   585: aload 4
    //   587: iconst_3
    //   588: iconst_0
    //   589: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   592: iconst_1
    //   593: ireturn
    //   594: aload 4
    //   596: iconst_3
    //   597: sipush 518
    //   600: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   603: aload 4
    //   605: iconst_3
    //   606: sipush 518
    //   609: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   612: iconst_0
    //   613: ireturn
    //   614: astore_0
    //   615: goto +10 -> 625
    //   618: astore_0
    //   619: aload_0
    //   620: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   623: iconst_0
    //   624: ireturn
    //   625: aload_0
    //   626: athrow
    //   627: iconst_1
    //   628: istore 9
    //   630: goto -542 -> 88
    //   633: aconst_null
    //   634: astore_3
    //   635: iconst_0
    //   636: istore 9
    //   638: goto -550 -> 88
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	641	0	paramContext1	Context
    //   0	641	1	paramContext2	Context
    //   0	641	2	paramFile	File
    //   0	641	3	paramString1	String
    //   0	641	4	paramString2	String
    //   0	641	5	paramInt1	int
    //   0	641	6	paramQBPluginItemInfo	QBPluginItemInfo
    //   0	641	7	paramIInstallPluginCallback	IInstallPluginCallback
    //   0	641	8	paramInt2	int
    //   88	549	9	i	int
    //   340	44	10	bool	boolean
    //   39	158	11	localPluginConfigInfo	QBPluginServiceImpl.PluginConfigInfo
    //   108	35	12	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   353	366	369	java/lang/Exception
    //   0	41	614	finally
    //   49	85	614	finally
    //   93	101	614	finally
    //   101	118	614	finally
    //   128	133	614	finally
    //   133	150	614	finally
    //   154	161	614	finally
    //   164	174	614	finally
    //   185	202	614	finally
    //   205	219	614	finally
    //   219	276	614	finally
    //   278	315	614	finally
    //   315	342	614	finally
    //   353	366	614	finally
    //   370	374	614	finally
    //   374	383	614	finally
    //   386	405	614	finally
    //   405	449	614	finally
    //   451	592	614	finally
    //   594	612	614	finally
    //   619	623	614	finally
    //   0	41	618	java/lang/Exception
    //   49	85	618	java/lang/Exception
    //   93	101	618	java/lang/Exception
    //   101	118	618	java/lang/Exception
    //   128	133	618	java/lang/Exception
    //   133	150	618	java/lang/Exception
    //   154	161	618	java/lang/Exception
    //   164	174	618	java/lang/Exception
    //   185	202	618	java/lang/Exception
    //   205	219	618	java/lang/Exception
    //   219	276	618	java/lang/Exception
    //   278	315	618	java/lang/Exception
    //   315	342	618	java/lang/Exception
    //   370	374	618	java/lang/Exception
    //   374	383	618	java/lang/Exception
    //   386	405	618	java/lang/Exception
    //   405	449	618	java/lang/Exception
    //   451	592	618	java/lang/Exception
    //   594	612	618	java/lang/Exception
  }
  
  /* Error */
  public static boolean installFontPlugin(Context paramContext1, Context paramContext2, String paramString1, String paramString2, int paramInt1, QBPluginItemInfo paramQBPluginItemInfo, IInstallPluginCallback paramIInstallPluginCallback, int paramInt2)
  {
    // Byte code:
    //   0: new 128	java/io/File
    //   3: dup
    //   4: aload_2
    //   5: invokespecial 131	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_0
    //   9: aload_0
    //   10: invokevirtual 260	java/io/File:getParentFile	()Ljava/io/File;
    //   13: astore_2
    //   14: aload_3
    //   15: iconst_3
    //   16: aload_2
    //   17: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   20: invokestatic 155	com/tencent/common/plugin/PluginStatBehavior:setInstallDir	(Ljava/lang/String;ILjava/lang/String;)V
    //   23: aload 5
    //   25: getfield 163	com/tencent/common/plugin/QBPluginItemInfo:mDownloadFileName	Ljava/lang/String;
    //   28: invokestatic 126	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   31: ifeq +21 -> 52
    //   34: aload_3
    //   35: iconst_3
    //   36: sipush 525
    //   39: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   42: aload_3
    //   43: iconst_3
    //   44: sipush 525
    //   47: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   50: iconst_0
    //   51: ireturn
    //   52: aload_0
    //   53: invokevirtual 135	java/io/File:exists	()Z
    //   56: istore 8
    //   58: iload 8
    //   60: ifne +34 -> 94
    //   63: aload_3
    //   64: iconst_3
    //   65: sipush 563
    //   68: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   71: aload_2
    //   72: invokestatic 198	com/tencent/common/utils/FileUtilsF:cleanDirectory	(Ljava/io/File;)V
    //   75: goto +8 -> 83
    //   78: astore_0
    //   79: aload_0
    //   80: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   83: aload_3
    //   84: iconst_3
    //   85: sipush 563
    //   88: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   91: iload 8
    //   93: ireturn
    //   94: invokestatic 210	com/tencent/common/plugin/ZipPluginCheck:getInstance	()Lcom/tencent/common/plugin/ZipPluginCheck;
    //   97: pop
    //   98: aload_2
    //   99: invokevirtual 214	java/io/File:getAbsoluteFile	()Ljava/io/File;
    //   102: iload 4
    //   104: aload_3
    //   105: iconst_1
    //   106: anewarray 128	java/io/File
    //   109: dup
    //   110: iconst_0
    //   111: aload_0
    //   112: aastore
    //   113: invokestatic 218	com/tencent/common/plugin/ZipPluginCheck:genCheckList	(Ljava/io/File;ILjava/lang/String;[Ljava/io/File;)Z
    //   116: ifne +21 -> 137
    //   119: aload_3
    //   120: iconst_3
    //   121: sipush 567
    //   124: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   127: aload_3
    //   128: iconst_3
    //   129: sipush 567
    //   132: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   135: iconst_0
    //   136: ireturn
    //   137: invokestatic 111	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   140: aload_2
    //   141: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   144: aload_3
    //   145: iload 4
    //   147: iload 7
    //   149: invokevirtual 222	com/tencent/common/plugin/QBPluginServiceImpl:addPluginInfoToLocalHashMap	(Ljava/lang/String;Ljava/lang/String;II)Z
    //   152: pop
    //   153: aload_2
    //   154: invokevirtual 149	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   157: astore_0
    //   158: aload 5
    //   160: aload_0
    //   161: putfield 225	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   164: aload 5
    //   166: aload_0
    //   167: putfield 228	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   170: new 85	java/lang/StringBuilder
    //   173: dup
    //   174: invokespecial 86	java/lang/StringBuilder:<init>	()V
    //   177: astore_0
    //   178: aload_0
    //   179: iload 4
    //   181: invokevirtual 231	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: aload_0
    //   186: ldc -23
    //   188: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: aload 5
    //   194: aload_0
    //   195: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   198: putfield 236	com/tencent/common/plugin/QBPluginItemInfo:mInstallVersion	Ljava/lang/String;
    //   201: aload 5
    //   203: iconst_1
    //   204: putfield 239	com/tencent/common/plugin/QBPluginItemInfo:mIsInstall	I
    //   207: aload_1
    //   208: invokestatic 244	com/tencent/common/plugin/QBPluginDBHelper:getInstance	(Landroid/content/Context;)Lcom/tencent/common/plugin/QBPluginDBHelper;
    //   211: aload_3
    //   212: aload 5
    //   214: getfield 225	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   217: iload 7
    //   219: invokevirtual 248	com/tencent/common/plugin/QBPluginDBHelper:updatePluginInstallDir	(Ljava/lang/String;Ljava/lang/String;I)V
    //   222: aload_1
    //   223: invokestatic 244	com/tencent/common/plugin/QBPluginDBHelper:getInstance	(Landroid/content/Context;)Lcom/tencent/common/plugin/QBPluginDBHelper;
    //   226: aload_3
    //   227: aload 5
    //   229: getfield 228	com/tencent/common/plugin/QBPluginItemInfo:mUnzipDir	Ljava/lang/String;
    //   232: iload 7
    //   234: invokevirtual 251	com/tencent/common/plugin/QBPluginDBHelper:updatePluginUnzipDir	(Ljava/lang/String;Ljava/lang/String;I)V
    //   237: invokestatic 111	com/tencent/common/plugin/QBPluginServiceImpl:getInstance	()Lcom/tencent/common/plugin/QBPluginServiceImpl;
    //   240: aload_3
    //   241: iconst_1
    //   242: iload 7
    //   244: invokevirtual 255	com/tencent/common/plugin/QBPluginServiceImpl:setIsPluginInstall	(Ljava/lang/String;ZI)Z
    //   247: pop
    //   248: aload_3
    //   249: iconst_3
    //   250: aload 5
    //   252: getfield 225	com/tencent/common/plugin/QBPluginItemInfo:mInstallDir	Ljava/lang/String;
    //   255: invokestatic 155	com/tencent/common/plugin/PluginStatBehavior:setInstallDir	(Ljava/lang/String;ILjava/lang/String;)V
    //   258: aload_3
    //   259: iconst_3
    //   260: bipush 50
    //   262: invokestatic 169	com/tencent/common/plugin/PluginStatBehavior:addLogPath	(Ljava/lang/String;II)V
    //   265: aload_3
    //   266: iconst_3
    //   267: iconst_0
    //   268: invokestatic 172	com/tencent/common/plugin/PluginStatBehavior:setFinCode	(Ljava/lang/String;II)V
    //   271: iconst_1
    //   272: ireturn
    //   273: astore_0
    //   274: goto +10 -> 284
    //   277: astore_0
    //   278: aload_0
    //   279: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   282: iconst_0
    //   283: ireturn
    //   284: aload_0
    //   285: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	286	0	paramContext1	Context
    //   0	286	1	paramContext2	Context
    //   0	286	2	paramString1	String
    //   0	286	3	paramString2	String
    //   0	286	4	paramInt1	int
    //   0	286	5	paramQBPluginItemInfo	QBPluginItemInfo
    //   0	286	6	paramIInstallPluginCallback	IInstallPluginCallback
    //   0	286	7	paramInt2	int
    //   56	36	8	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   63	75	78	java/lang/Exception
    //   0	50	273	finally
    //   52	58	273	finally
    //   63	75	273	finally
    //   79	83	273	finally
    //   83	91	273	finally
    //   94	135	273	finally
    //   137	271	273	finally
    //   278	282	273	finally
    //   0	50	277	java/lang/Exception
    //   52	58	277	java/lang/Exception
    //   79	83	277	java/lang/Exception
    //   83	91	277	java/lang/Exception
    //   94	135	277	java/lang/Exception
    //   137	271	277	java/lang/Exception
  }
  
  public static boolean installJarPlugin(Context paramContext1, Context paramContext2, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, QBPluginItemInfo paramQBPluginItemInfo, int paramInt3)
  {
    if (!paramString1.toLowerCase().endsWith(".jar"))
    {
      PluginStatBehavior.addLogPath(paramString2, 3, 524);
      PluginStatBehavior.setFinCode(paramString2, 3, 524);
      return false;
    }
    for (;;)
    {
      try
      {
        QBPluginServiceImpl.getInstance().removePluginJarFileFromPluginInfoList(paramString2, paramInt3);
        paramString3 = QBPluginServiceImpl.getPluginInstallDir(paramContext1, paramString2, true);
        if ((paramString3 != null) && (paramString3.exists()))
        {
          localObject = new File(paramString1);
          paramString1 = new StringBuilder();
          paramString1.append(paramString2);
          paramString1.append(".jar");
          paramString1 = new File(paramString3, paramString1.toString());
          if (paramString1.exists())
          {
            boolean bool = ((File)localObject).getAbsolutePath().equalsIgnoreCase(paramString1.getAbsolutePath());
            if (bool) {}
          }
        }
      }
      catch (OutOfMemoryError paramContext1)
      {
        Object localObject;
        continue;
      }
      try
      {
        FileUtilsF.delete(paramString1);
      }
      catch (Exception paramContext1) {}
    }
    PluginStatBehavior.addLogPath(paramString2, 3, 525);
    PluginStatBehavior.setFinCode(paramString2, 3, 525);
    return false;
    if (!FileUtilsF.renameTo((File)localObject, paramString1))
    {
      PluginStatBehavior.addLogPath(paramString2, 3, 526);
      PluginStatBehavior.setFinCode(paramString2, 3, 526);
      return false;
    }
    if (!unZipJarSo(paramContext1, paramString1.getAbsolutePath(), paramString2, true))
    {
      PluginStatBehavior.addLogPath(paramString2, 3, 527);
      PluginStatBehavior.setFinCode(paramString2, 3, 527);
      return false;
    }
    paramContext1 = FileUtilsF.createDir(PluginFileUtils.getPluginDir(paramContext1), paramString2);
    new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return (paramAnonymousString.endsWith(".so")) || (paramAnonymousString.endsWith(".jar")) || (paramAnonymousString.endsWith(".zip"));
      }
    };
    localObject = paramString3.listFiles();
    ZipPluginCheck.getInstance();
    if (!ZipPluginCheck.genCheckList(paramString3.getAbsoluteFile(), paramInt2, paramString2, (File[])localObject))
    {
      PluginStatBehavior.addLogPath(paramString2, 3, 567);
      PluginStatBehavior.setFinCode(paramString2, 3, 567);
      return false;
    }
    paramQBPluginItemInfo.mUnzipDir = paramContext1.getAbsolutePath();
    paramQBPluginItemInfo.mInstallDir = paramString3.getAbsolutePath();
    paramContext1 = new StringBuilder();
    paramContext1.append(paramInt2);
    paramContext1.append("");
    paramQBPluginItemInfo.mInstallVersion = paramContext1.toString();
    paramQBPluginItemInfo.mIsInstall = 1;
    QBPluginDBHelper.getInstance(paramContext2).updatePluginInstallDir(paramString2, paramQBPluginItemInfo.mInstallDir, paramInt3);
    QBPluginDBHelper.getInstance(paramContext2).updatePluginUnzipDir(paramString2, paramQBPluginItemInfo.mUnzipDir, paramInt3);
    QBPluginServiceImpl.getInstance().addPluginJarFile2PluginInfoList(paramString1.getAbsolutePath(), paramInt3);
    QBPluginServiceImpl.getInstance().setIsPluginInstall(paramString2, true, paramInt3);
    PluginStatBehavior.setInstallDir(paramString2, 3, paramString3.getAbsolutePath());
    PluginStatBehavior.addLogPath(paramString2, 3, 50);
    PluginStatBehavior.setFinCode(paramString2, 3, 0);
    return true;
    PluginStatBehavior.addLogPath(paramString2, 3, 518);
    PluginStatBehavior.setFinCode(paramString2, 3, 518);
    return false;
    System.gc();
    return false;
  }
  
  public static boolean installNovelPlugin(Context paramContext1, Context paramContext2, String paramString1, String paramString2, int paramInt1, QBPluginItemInfo paramQBPluginItemInfo, IInstallPluginCallback paramIInstallPluginCallback, int paramInt2)
  {
    if (paramIInstallPluginCallback == null) {
      return false;
    }
    try
    {
      boolean bool = paramIInstallPluginCallback.userInstallPlugin(paramString2, paramQBPluginItemInfo, paramInt1);
      return bool;
    }
    catch (RemoteException paramContext1)
    {
      paramContext1.printStackTrace();
    }
    return false;
  }
  
  public static boolean isJarExist(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    QBPluginServiceImpl.getInstance().loadDiskPluginInfoList();
    paramString = QBPluginServiceImpl.getInstance().getLocalPluginInfo(paramString, 2, paramInt);
    if (paramString == null) {
      return false;
    }
    paramString = paramString.mLocalPath;
    if (paramString == null) {
      return false;
    }
    return new File(paramString).exists();
  }
  
  public static boolean isPluginInstall(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramContext = QBPluginDBHelper.getInstance(paramContext).getPluginItemInfo(paramString, 1);
    if (paramContext != null) {
      return paramContext.mIsInstall == 1;
    }
    return false;
  }
  
  public static void resetPluginStatus(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    QBPluginServiceImpl.getInstance().setIsPluginInstall(paramString, false, paramInt);
    QBPluginServiceImpl.getInstance().removePluginJarFileFromPluginInfoList(paramString, paramInt);
  }
  
  public static HashMap<String, JarFileInfo> scanLocalPluginJarFile(Context paramContext, ArrayList<QBPluginItemInfo> paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    if (paramArrayList == null) {
      return localHashMap;
    }
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      Object localObject1 = (QBPluginItemInfo)paramArrayList.next();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(((QBPluginItemInfo)localObject1).mInstallDir);
      ((StringBuilder)localObject2).append("/");
      ((StringBuilder)localObject2).append(((QBPluginItemInfo)localObject1).mPackageName);
      ((StringBuilder)localObject2).append(".jar");
      localObject1 = new File(((StringBuilder)localObject2).toString());
      if (((File)localObject1).exists())
      {
        localObject2 = new JarFileInfo();
        boolean bool = JarFileParser.parseAPKFile(paramContext, ((File)localObject1).getAbsolutePath(), (JarFileInfo)localObject2);
        if ((!((JarFileInfo)localObject2).mBrokenJarFile) && (bool))
        {
          ((JarFileInfo)localObject2).mCanonicalPath = ((File)localObject1).getAbsolutePath();
          localHashMap.put(((JarFileInfo)localObject2).mPackageName, localObject2);
        }
      }
    }
    return localHashMap;
  }
  
  public static boolean unZipJarSo(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString1)))
    {
      paramContext = FileUtilsF.createDir(PluginFileUtils.getPluginDir(paramContext), paramString2);
      if (paramContext == null)
      {
        PluginStatBehavior.addLogPath(paramString2, 3, 523);
        return false;
      }
      if (paramBoolean)
      {
        if ((paramContext.exists()) && (!paramString1.contains(paramContext.getAbsolutePath()))) {
          try
          {
            FileUtilsF.delete(paramContext);
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            PluginStatBehavior.addLogPath(paramString2, 3, 519);
          }
        }
        try
        {
          FileUtilsF.forceMkdir(paramContext);
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          PluginStatBehavior.addLogPath(paramString2, 3, 520);
        }
      }
      if (FileUtilsF.createDir(paramContext, paramString2) == null)
      {
        PluginStatBehavior.addLogPath(paramString2, 3, 520);
        return false;
      }
      try
      {
        FileUtilsF.forceMkdir(paramContext);
      }
      catch (IOException paramString2)
      {
        paramString2.printStackTrace();
      }
      return ZipUtils.UnZip(paramString1, paramContext.getAbsolutePath(), false, null);
    }
    PluginStatBehavior.addLogPath(paramString2, 3, 511);
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\PluginUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */