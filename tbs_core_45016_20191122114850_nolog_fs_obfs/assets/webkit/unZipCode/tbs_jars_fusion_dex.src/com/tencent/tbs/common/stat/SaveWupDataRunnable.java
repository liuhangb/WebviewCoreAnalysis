package com.tencent.tbs.common.stat;

import com.tencent.common.utils.LogUtils;

public class SaveWupDataRunnable
  implements Runnable
{
  boolean mForSimpleQB = false;
  WUPStatData mWupData = null;
  
  public SaveWupDataRunnable(WUPStatData paramWUPStatData, boolean paramBoolean)
  {
    this.mWupData = paramWUPStatData;
    this.mForSimpleQB = paramBoolean;
  }
  
  /* Error */
  private boolean doRun()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   4: getfield 34	com/tencent/tbs/common/stat/WUPStatData:mId	I
    //   7: bipush 10
    //   9: irem
    //   10: aload_0
    //   11: getfield 19	com/tencent/tbs/common/stat/SaveWupDataRunnable:mForSimpleQB	Z
    //   14: invokestatic 40	com/tencent/tbs/common/utils/TbsFileUtils:getStatFile	(IZ)Ljava/io/File;
    //   17: astore_2
    //   18: iconst_0
    //   19: istore_1
    //   20: aload_2
    //   21: ifnull +1002 -> 1023
    //   24: new 42	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 43	java/lang/StringBuilder:<init>	()V
    //   31: astore_3
    //   32: aload_3
    //   33: ldc 45
    //   35: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload_3
    //   40: aload_2
    //   41: invokevirtual 55	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   44: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload_3
    //   49: ldc 57
    //   51: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload_3
    //   56: aload_0
    //   57: getfield 19	com/tencent/tbs/common/stat/SaveWupDataRunnable:mForSimpleQB	Z
    //   60: invokevirtual 60	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: ldc 62
    //   66: aload_3
    //   67: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: invokestatic 71	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   73: aload_2
    //   74: invokevirtual 74	java/io/File:exists	()Z
    //   77: ifne +8 -> 85
    //   80: aload_2
    //   81: invokevirtual 77	java/io/File:createNewFile	()Z
    //   84: pop
    //   85: new 42	java/lang/StringBuilder
    //   88: dup
    //   89: invokespecial 43	java/lang/StringBuilder:<init>	()V
    //   92: astore_3
    //   93: aload_3
    //   94: ldc 79
    //   96: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload_3
    //   101: aload_0
    //   102: getfield 19	com/tencent/tbs/common/stat/SaveWupDataRunnable:mForSimpleQB	Z
    //   105: invokevirtual 60	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: ldc 62
    //   111: aload_3
    //   112: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokestatic 71	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   118: new 81	java/io/DataOutputStream
    //   121: dup
    //   122: new 83	java/io/BufferedOutputStream
    //   125: dup
    //   126: aload_2
    //   127: invokestatic 89	com/tencent/common/utils/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   130: invokespecial 92	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   133: invokespecial 93	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   136: astore_3
    //   137: aload_3
    //   138: astore_2
    //   139: aload_3
    //   140: aload_0
    //   141: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   144: getfield 97	com/tencent/tbs/common/stat/WUPStatData:version	Ljava/lang/String;
    //   147: invokevirtual 101	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   150: aload_3
    //   151: astore_2
    //   152: aload_3
    //   153: aload_0
    //   154: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   157: getfield 34	com/tencent/tbs/common/stat/WUPStatData:mId	I
    //   160: invokevirtual 105	java/io/DataOutputStream:writeInt	(I)V
    //   163: aload_3
    //   164: astore_2
    //   165: aload_3
    //   166: aload_0
    //   167: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   170: getfield 109	com/tencent/tbs/common/stat/WUPStatData:mTime	J
    //   173: invokevirtual 113	java/io/DataOutputStream:writeLong	(J)V
    //   176: aload_3
    //   177: astore_2
    //   178: aload_3
    //   179: aload_0
    //   180: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   183: getfield 117	com/tencent/tbs/common/stat/WUPStatData:mTotal	Ljava/util/HashMap;
    //   186: invokevirtual 123	java/util/HashMap:size	()I
    //   189: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   192: aload_3
    //   193: astore_2
    //   194: aload_0
    //   195: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   198: getfield 117	com/tencent/tbs/common/stat/WUPStatData:mTotal	Ljava/util/HashMap;
    //   201: invokevirtual 130	java/util/HashMap:values	()Ljava/util/Collection;
    //   204: invokeinterface 136 1 0
    //   209: astore 4
    //   211: aload_3
    //   212: astore_2
    //   213: aload 4
    //   215: invokeinterface 141 1 0
    //   220: ifeq +44 -> 264
    //   223: aload_3
    //   224: astore_2
    //   225: aload 4
    //   227: invokeinterface 145 1 0
    //   232: checkcast 147	com/tencent/tbs/common/MTT/STTotal
    //   235: invokestatic 153	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   238: astore 5
    //   240: aload_3
    //   241: astore_2
    //   242: aload_3
    //   243: aload 5
    //   245: arraylength
    //   246: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   249: aload_3
    //   250: astore_2
    //   251: aload_3
    //   252: aload 5
    //   254: iconst_0
    //   255: aload 5
    //   257: arraylength
    //   258: invokevirtual 157	java/io/DataOutputStream:write	([BII)V
    //   261: goto -50 -> 211
    //   264: aload_3
    //   265: astore_2
    //   266: aload_3
    //   267: aload_0
    //   268: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   271: getfield 160	com/tencent/tbs/common/stat/WUPStatData:mOuterPV	Ljava/util/HashMap;
    //   274: invokevirtual 123	java/util/HashMap:size	()I
    //   277: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   280: aload_3
    //   281: astore_2
    //   282: aload_0
    //   283: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   286: getfield 160	com/tencent/tbs/common/stat/WUPStatData:mOuterPV	Ljava/util/HashMap;
    //   289: invokevirtual 130	java/util/HashMap:values	()Ljava/util/Collection;
    //   292: invokeinterface 136 1 0
    //   297: astore 4
    //   299: aload_3
    //   300: astore_2
    //   301: aload 4
    //   303: invokeinterface 141 1 0
    //   308: ifeq +44 -> 352
    //   311: aload_3
    //   312: astore_2
    //   313: aload 4
    //   315: invokeinterface 145 1 0
    //   320: checkcast 162	com/tencent/tbs/common/MTT/STPV
    //   323: invokestatic 153	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   326: astore 5
    //   328: aload_3
    //   329: astore_2
    //   330: aload_3
    //   331: aload 5
    //   333: arraylength
    //   334: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   337: aload_3
    //   338: astore_2
    //   339: aload_3
    //   340: aload 5
    //   342: iconst_0
    //   343: aload 5
    //   345: arraylength
    //   346: invokevirtual 157	java/io/DataOutputStream:write	([BII)V
    //   349: goto -50 -> 299
    //   352: aload_3
    //   353: astore_2
    //   354: aload_3
    //   355: aload_0
    //   356: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   359: getfield 165	com/tencent/tbs/common/stat/WUPStatData:mUserBehaviorPV	Ljava/util/HashMap;
    //   362: invokevirtual 123	java/util/HashMap:size	()I
    //   365: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   368: aload_3
    //   369: astore_2
    //   370: aload_0
    //   371: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   374: getfield 165	com/tencent/tbs/common/stat/WUPStatData:mUserBehaviorPV	Ljava/util/HashMap;
    //   377: invokevirtual 130	java/util/HashMap:values	()Ljava/util/Collection;
    //   380: invokeinterface 136 1 0
    //   385: astore 4
    //   387: aload_3
    //   388: astore_2
    //   389: aload 4
    //   391: invokeinterface 141 1 0
    //   396: ifeq +44 -> 440
    //   399: aload_3
    //   400: astore_2
    //   401: aload 4
    //   403: invokeinterface 145 1 0
    //   408: checkcast 167	com/tencent/tbs/common/MTT/UserBehaviorPV
    //   411: invokestatic 153	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   414: astore 5
    //   416: aload_3
    //   417: astore_2
    //   418: aload_3
    //   419: aload 5
    //   421: arraylength
    //   422: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   425: aload_3
    //   426: astore_2
    //   427: aload_3
    //   428: aload 5
    //   430: iconst_0
    //   431: aload 5
    //   433: arraylength
    //   434: invokevirtual 157	java/io/DataOutputStream:write	([BII)V
    //   437: goto -50 -> 387
    //   440: aload_3
    //   441: astore_2
    //   442: new 42	java/lang/StringBuilder
    //   445: dup
    //   446: invokespecial 43	java/lang/StringBuilder:<init>	()V
    //   449: astore 4
    //   451: aload_3
    //   452: astore_2
    //   453: aload 4
    //   455: ldc -87
    //   457: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: pop
    //   461: aload_3
    //   462: astore_2
    //   463: aload 4
    //   465: aload_0
    //   466: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   469: getfield 172	com/tencent/tbs/common/stat/WUPStatData:mPerformanceStat	Ljava/util/HashMap;
    //   472: invokevirtual 123	java/util/HashMap:size	()I
    //   475: invokevirtual 175	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   478: pop
    //   479: aload_3
    //   480: astore_2
    //   481: aload 4
    //   483: ldc 57
    //   485: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: pop
    //   489: aload_3
    //   490: astore_2
    //   491: aload 4
    //   493: aload_0
    //   494: getfield 19	com/tencent/tbs/common/stat/SaveWupDataRunnable:mForSimpleQB	Z
    //   497: invokevirtual 60	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   500: pop
    //   501: aload_3
    //   502: astore_2
    //   503: ldc -79
    //   505: aload 4
    //   507: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   510: invokestatic 71	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   513: aload_3
    //   514: astore_2
    //   515: aload_3
    //   516: aload_0
    //   517: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   520: getfield 172	com/tencent/tbs/common/stat/WUPStatData:mPerformanceStat	Ljava/util/HashMap;
    //   523: invokevirtual 123	java/util/HashMap:size	()I
    //   526: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   529: aload_3
    //   530: astore_2
    //   531: aload_0
    //   532: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   535: getfield 172	com/tencent/tbs/common/stat/WUPStatData:mPerformanceStat	Ljava/util/HashMap;
    //   538: invokevirtual 130	java/util/HashMap:values	()Ljava/util/Collection;
    //   541: invokeinterface 136 1 0
    //   546: astore 4
    //   548: aload_3
    //   549: astore_2
    //   550: aload 4
    //   552: invokeinterface 141 1 0
    //   557: ifeq +115 -> 672
    //   560: aload_3
    //   561: astore_2
    //   562: aload 4
    //   564: invokeinterface 145 1 0
    //   569: checkcast 179	com/tencent/tbs/common/MTT/PerformanceStat
    //   572: astore 5
    //   574: aload_3
    //   575: astore_2
    //   576: new 42	java/lang/StringBuilder
    //   579: dup
    //   580: invokespecial 43	java/lang/StringBuilder:<init>	()V
    //   583: astore 6
    //   585: aload_3
    //   586: astore_2
    //   587: aload 6
    //   589: ldc -75
    //   591: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: pop
    //   595: aload_3
    //   596: astore_2
    //   597: aload 6
    //   599: aload 5
    //   601: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   604: pop
    //   605: aload_3
    //   606: astore_2
    //   607: aload 6
    //   609: ldc 57
    //   611: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   614: pop
    //   615: aload_3
    //   616: astore_2
    //   617: aload 6
    //   619: aload_0
    //   620: getfield 19	com/tencent/tbs/common/stat/SaveWupDataRunnable:mForSimpleQB	Z
    //   623: invokevirtual 60	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   626: pop
    //   627: aload_3
    //   628: astore_2
    //   629: ldc -79
    //   631: aload 6
    //   633: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   636: invokestatic 71	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   639: aload_3
    //   640: astore_2
    //   641: aload 5
    //   643: invokevirtual 188	com/tencent/tbs/common/MTT/PerformanceStat:serialize	()[B
    //   646: astore 5
    //   648: aload_3
    //   649: astore_2
    //   650: aload_3
    //   651: aload 5
    //   653: arraylength
    //   654: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   657: aload_3
    //   658: astore_2
    //   659: aload_3
    //   660: aload 5
    //   662: iconst_0
    //   663: aload 5
    //   665: arraylength
    //   666: invokevirtual 157	java/io/DataOutputStream:write	([BII)V
    //   669: goto -121 -> 548
    //   672: aload_3
    //   673: astore_2
    //   674: aload_3
    //   675: aload_0
    //   676: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   679: getfield 192	com/tencent/tbs/common/stat/WUPStatData:mCommonAppInfos	Ljava/util/ArrayList;
    //   682: invokevirtual 195	java/util/ArrayList:size	()I
    //   685: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   688: aload_3
    //   689: astore_2
    //   690: aload_0
    //   691: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   694: getfield 192	com/tencent/tbs/common/stat/WUPStatData:mCommonAppInfos	Ljava/util/ArrayList;
    //   697: invokevirtual 196	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   700: astore 4
    //   702: aload_3
    //   703: astore_2
    //   704: aload 4
    //   706: invokeinterface 141 1 0
    //   711: ifeq +58 -> 769
    //   714: aload_3
    //   715: astore_2
    //   716: aload 4
    //   718: invokeinterface 145 1 0
    //   723: checkcast 198	com/tencent/tbs/common/MTT/STCommonAppInfo
    //   726: astore 5
    //   728: aload 5
    //   730: ifnonnull +6 -> 736
    //   733: goto -31 -> 702
    //   736: aload_3
    //   737: astore_2
    //   738: aload 5
    //   740: invokestatic 153	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   743: astore 5
    //   745: aload_3
    //   746: astore_2
    //   747: aload_3
    //   748: aload 5
    //   750: arraylength
    //   751: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   754: aload_3
    //   755: astore_2
    //   756: aload_3
    //   757: aload 5
    //   759: iconst_0
    //   760: aload 5
    //   762: arraylength
    //   763: invokevirtual 157	java/io/DataOutputStream:write	([BII)V
    //   766: goto -64 -> 702
    //   769: aload_3
    //   770: astore_2
    //   771: aload_3
    //   772: aload_0
    //   773: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   776: getfield 201	com/tencent/tbs/common/stat/WUPStatData:mCommStatDatas	Ljava/util/HashMap;
    //   779: invokevirtual 123	java/util/HashMap:size	()I
    //   782: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   785: aload_3
    //   786: astore_2
    //   787: aload_0
    //   788: getfield 17	com/tencent/tbs/common/stat/SaveWupDataRunnable:mWupData	Lcom/tencent/tbs/common/stat/WUPStatData;
    //   791: getfield 201	com/tencent/tbs/common/stat/WUPStatData:mCommStatDatas	Ljava/util/HashMap;
    //   794: invokevirtual 130	java/util/HashMap:values	()Ljava/util/Collection;
    //   797: invokeinterface 136 1 0
    //   802: astore 4
    //   804: aload_3
    //   805: astore_2
    //   806: aload 4
    //   808: invokeinterface 141 1 0
    //   813: ifeq +44 -> 857
    //   816: aload_3
    //   817: astore_2
    //   818: aload 4
    //   820: invokeinterface 145 1 0
    //   825: checkcast 203	com/tencent/tbs/common/MTT/CommStatData
    //   828: invokestatic 153	com/tencent/tbs/common/push/PushDataParserUtils:jce2Bytes	(Lcom/taf/JceStruct;)[B
    //   831: astore 5
    //   833: aload_3
    //   834: astore_2
    //   835: aload_3
    //   836: aload 5
    //   838: arraylength
    //   839: invokevirtual 126	java/io/DataOutputStream:writeShort	(I)V
    //   842: aload_3
    //   843: astore_2
    //   844: aload_3
    //   845: aload 5
    //   847: iconst_0
    //   848: aload 5
    //   850: arraylength
    //   851: invokevirtual 157	java/io/DataOutputStream:write	([BII)V
    //   854: goto -50 -> 804
    //   857: aload_3
    //   858: astore_2
    //   859: aload_3
    //   860: invokevirtual 206	java/io/DataOutputStream:flush	()V
    //   863: iconst_1
    //   864: istore_1
    //   865: aload_3
    //   866: invokevirtual 209	java/io/DataOutputStream:close	()V
    //   869: iconst_1
    //   870: ireturn
    //   871: astore_2
    //   872: aload_2
    //   873: invokevirtual 212	java/io/IOException:printStackTrace	()V
    //   876: iload_1
    //   877: ireturn
    //   878: astore 4
    //   880: goto +18 -> 898
    //   883: astore 4
    //   885: goto +34 -> 919
    //   888: astore_3
    //   889: aconst_null
    //   890: astore_2
    //   891: goto +114 -> 1005
    //   894: astore 4
    //   896: aconst_null
    //   897: astore_3
    //   898: aload_3
    //   899: astore_2
    //   900: aload 4
    //   902: invokevirtual 213	java/lang/OutOfMemoryError:printStackTrace	()V
    //   905: aload_3
    //   906: ifnull +117 -> 1023
    //   909: aload_3
    //   910: invokevirtual 209	java/io/DataOutputStream:close	()V
    //   913: iconst_0
    //   914: ireturn
    //   915: astore 4
    //   917: aconst_null
    //   918: astore_3
    //   919: aload_3
    //   920: astore_2
    //   921: new 42	java/lang/StringBuilder
    //   924: dup
    //   925: invokespecial 43	java/lang/StringBuilder:<init>	()V
    //   928: astore 5
    //   930: aload_3
    //   931: astore_2
    //   932: aload 5
    //   934: ldc -41
    //   936: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   939: pop
    //   940: aload_3
    //   941: astore_2
    //   942: aload 5
    //   944: aload 4
    //   946: invokevirtual 218	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   949: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   952: pop
    //   953: aload_3
    //   954: astore_2
    //   955: aload 5
    //   957: ldc 57
    //   959: invokevirtual 49	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   962: pop
    //   963: aload_3
    //   964: astore_2
    //   965: aload 5
    //   967: aload_0
    //   968: getfield 19	com/tencent/tbs/common/stat/SaveWupDataRunnable:mForSimpleQB	Z
    //   971: invokevirtual 60	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   974: pop
    //   975: aload_3
    //   976: astore_2
    //   977: ldc 62
    //   979: aload 5
    //   981: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   984: invokestatic 71	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   987: aload_3
    //   988: astore_2
    //   989: aload 4
    //   991: invokevirtual 219	java/lang/Exception:printStackTrace	()V
    //   994: aload_3
    //   995: ifnull +28 -> 1023
    //   998: aload_3
    //   999: invokevirtual 209	java/io/DataOutputStream:close	()V
    //   1002: iconst_0
    //   1003: ireturn
    //   1004: astore_3
    //   1005: aload_2
    //   1006: ifnull +15 -> 1021
    //   1009: aload_2
    //   1010: invokevirtual 209	java/io/DataOutputStream:close	()V
    //   1013: goto +8 -> 1021
    //   1016: astore_2
    //   1017: aload_2
    //   1018: invokevirtual 212	java/io/IOException:printStackTrace	()V
    //   1021: aload_3
    //   1022: athrow
    //   1023: iconst_0
    //   1024: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1025	0	this	SaveWupDataRunnable
    //   19	858	1	bool	boolean
    //   17	842	2	localObject1	Object
    //   871	2	2	localIOException1	java.io.IOException
    //   890	120	2	localObject2	Object
    //   1016	2	2	localIOException2	java.io.IOException
    //   31	835	3	localObject3	Object
    //   888	1	3	localObject4	Object
    //   897	102	3	localObject5	Object
    //   1004	18	3	localObject6	Object
    //   209	610	4	localObject7	Object
    //   878	1	4	localOutOfMemoryError1	OutOfMemoryError
    //   883	1	4	localException1	Exception
    //   894	7	4	localOutOfMemoryError2	OutOfMemoryError
    //   915	75	4	localException2	Exception
    //   238	742	5	localObject8	Object
    //   583	49	6	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   865	869	871	java/io/IOException
    //   909	913	871	java/io/IOException
    //   998	1002	871	java/io/IOException
    //   139	150	878	java/lang/OutOfMemoryError
    //   152	163	878	java/lang/OutOfMemoryError
    //   165	176	878	java/lang/OutOfMemoryError
    //   178	192	878	java/lang/OutOfMemoryError
    //   194	211	878	java/lang/OutOfMemoryError
    //   213	223	878	java/lang/OutOfMemoryError
    //   225	240	878	java/lang/OutOfMemoryError
    //   242	249	878	java/lang/OutOfMemoryError
    //   251	261	878	java/lang/OutOfMemoryError
    //   266	280	878	java/lang/OutOfMemoryError
    //   282	299	878	java/lang/OutOfMemoryError
    //   301	311	878	java/lang/OutOfMemoryError
    //   313	328	878	java/lang/OutOfMemoryError
    //   330	337	878	java/lang/OutOfMemoryError
    //   339	349	878	java/lang/OutOfMemoryError
    //   354	368	878	java/lang/OutOfMemoryError
    //   370	387	878	java/lang/OutOfMemoryError
    //   389	399	878	java/lang/OutOfMemoryError
    //   401	416	878	java/lang/OutOfMemoryError
    //   418	425	878	java/lang/OutOfMemoryError
    //   427	437	878	java/lang/OutOfMemoryError
    //   442	451	878	java/lang/OutOfMemoryError
    //   453	461	878	java/lang/OutOfMemoryError
    //   463	479	878	java/lang/OutOfMemoryError
    //   481	489	878	java/lang/OutOfMemoryError
    //   491	501	878	java/lang/OutOfMemoryError
    //   503	513	878	java/lang/OutOfMemoryError
    //   515	529	878	java/lang/OutOfMemoryError
    //   531	548	878	java/lang/OutOfMemoryError
    //   550	560	878	java/lang/OutOfMemoryError
    //   562	574	878	java/lang/OutOfMemoryError
    //   576	585	878	java/lang/OutOfMemoryError
    //   587	595	878	java/lang/OutOfMemoryError
    //   597	605	878	java/lang/OutOfMemoryError
    //   607	615	878	java/lang/OutOfMemoryError
    //   617	627	878	java/lang/OutOfMemoryError
    //   629	639	878	java/lang/OutOfMemoryError
    //   641	648	878	java/lang/OutOfMemoryError
    //   650	657	878	java/lang/OutOfMemoryError
    //   659	669	878	java/lang/OutOfMemoryError
    //   674	688	878	java/lang/OutOfMemoryError
    //   690	702	878	java/lang/OutOfMemoryError
    //   704	714	878	java/lang/OutOfMemoryError
    //   716	728	878	java/lang/OutOfMemoryError
    //   738	745	878	java/lang/OutOfMemoryError
    //   747	754	878	java/lang/OutOfMemoryError
    //   756	766	878	java/lang/OutOfMemoryError
    //   771	785	878	java/lang/OutOfMemoryError
    //   787	804	878	java/lang/OutOfMemoryError
    //   806	816	878	java/lang/OutOfMemoryError
    //   818	833	878	java/lang/OutOfMemoryError
    //   835	842	878	java/lang/OutOfMemoryError
    //   844	854	878	java/lang/OutOfMemoryError
    //   859	863	878	java/lang/OutOfMemoryError
    //   139	150	883	java/lang/Exception
    //   152	163	883	java/lang/Exception
    //   165	176	883	java/lang/Exception
    //   178	192	883	java/lang/Exception
    //   194	211	883	java/lang/Exception
    //   213	223	883	java/lang/Exception
    //   225	240	883	java/lang/Exception
    //   242	249	883	java/lang/Exception
    //   251	261	883	java/lang/Exception
    //   266	280	883	java/lang/Exception
    //   282	299	883	java/lang/Exception
    //   301	311	883	java/lang/Exception
    //   313	328	883	java/lang/Exception
    //   330	337	883	java/lang/Exception
    //   339	349	883	java/lang/Exception
    //   354	368	883	java/lang/Exception
    //   370	387	883	java/lang/Exception
    //   389	399	883	java/lang/Exception
    //   401	416	883	java/lang/Exception
    //   418	425	883	java/lang/Exception
    //   427	437	883	java/lang/Exception
    //   442	451	883	java/lang/Exception
    //   453	461	883	java/lang/Exception
    //   463	479	883	java/lang/Exception
    //   481	489	883	java/lang/Exception
    //   491	501	883	java/lang/Exception
    //   503	513	883	java/lang/Exception
    //   515	529	883	java/lang/Exception
    //   531	548	883	java/lang/Exception
    //   550	560	883	java/lang/Exception
    //   562	574	883	java/lang/Exception
    //   576	585	883	java/lang/Exception
    //   587	595	883	java/lang/Exception
    //   597	605	883	java/lang/Exception
    //   607	615	883	java/lang/Exception
    //   617	627	883	java/lang/Exception
    //   629	639	883	java/lang/Exception
    //   641	648	883	java/lang/Exception
    //   650	657	883	java/lang/Exception
    //   659	669	883	java/lang/Exception
    //   674	688	883	java/lang/Exception
    //   690	702	883	java/lang/Exception
    //   704	714	883	java/lang/Exception
    //   716	728	883	java/lang/Exception
    //   738	745	883	java/lang/Exception
    //   747	754	883	java/lang/Exception
    //   756	766	883	java/lang/Exception
    //   771	785	883	java/lang/Exception
    //   787	804	883	java/lang/Exception
    //   806	816	883	java/lang/Exception
    //   818	833	883	java/lang/Exception
    //   835	842	883	java/lang/Exception
    //   844	854	883	java/lang/Exception
    //   859	863	883	java/lang/Exception
    //   73	85	888	finally
    //   85	137	888	finally
    //   73	85	894	java/lang/OutOfMemoryError
    //   85	137	894	java/lang/OutOfMemoryError
    //   73	85	915	java/lang/Exception
    //   85	137	915	java/lang/Exception
    //   139	150	1004	finally
    //   152	163	1004	finally
    //   165	176	1004	finally
    //   178	192	1004	finally
    //   194	211	1004	finally
    //   213	223	1004	finally
    //   225	240	1004	finally
    //   242	249	1004	finally
    //   251	261	1004	finally
    //   266	280	1004	finally
    //   282	299	1004	finally
    //   301	311	1004	finally
    //   313	328	1004	finally
    //   330	337	1004	finally
    //   339	349	1004	finally
    //   354	368	1004	finally
    //   370	387	1004	finally
    //   389	399	1004	finally
    //   401	416	1004	finally
    //   418	425	1004	finally
    //   427	437	1004	finally
    //   442	451	1004	finally
    //   453	461	1004	finally
    //   463	479	1004	finally
    //   481	489	1004	finally
    //   491	501	1004	finally
    //   503	513	1004	finally
    //   515	529	1004	finally
    //   531	548	1004	finally
    //   550	560	1004	finally
    //   562	574	1004	finally
    //   576	585	1004	finally
    //   587	595	1004	finally
    //   597	605	1004	finally
    //   607	615	1004	finally
    //   617	627	1004	finally
    //   629	639	1004	finally
    //   641	648	1004	finally
    //   650	657	1004	finally
    //   659	669	1004	finally
    //   674	688	1004	finally
    //   690	702	1004	finally
    //   704	714	1004	finally
    //   716	728	1004	finally
    //   738	745	1004	finally
    //   747	754	1004	finally
    //   756	766	1004	finally
    //   771	785	1004	finally
    //   787	804	1004	finally
    //   806	816	1004	finally
    //   818	833	1004	finally
    //   835	842	1004	finally
    //   844	854	1004	finally
    //   859	863	1004	finally
    //   900	905	1004	finally
    //   921	930	1004	finally
    //   932	940	1004	finally
    //   942	953	1004	finally
    //   955	963	1004	finally
    //   965	975	1004	finally
    //   977	987	1004	finally
    //   989	994	1004	finally
    //   1009	1013	1016	java/io/IOException
  }
  
  public void run()
  {
    if (doRun())
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("first save ok, good; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("SaveStatData", localStringBuilder.toString());
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("first save fail, try again; isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("SaveStatData", localStringBuilder.toString());
    if (doRun())
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("second save ok, good; isSimpleQB=");
      localStringBuilder.append(this.mForSimpleQB);
      LogUtils.d("SaveStatData", localStringBuilder.toString());
      return;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("second save fail, this time realy fail; isSimpleQB=");
    localStringBuilder.append(this.mForSimpleQB);
    LogUtils.d("SaveStatData", localStringBuilder.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\SaveWupDataRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */