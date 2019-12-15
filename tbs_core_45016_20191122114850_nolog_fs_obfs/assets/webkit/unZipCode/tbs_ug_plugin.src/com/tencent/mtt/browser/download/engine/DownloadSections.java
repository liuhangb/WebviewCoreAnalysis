package com.tencent.mtt.browser.download.engine;

import android.content.ContentValues;
import com.tencent.basesupport.FLogger;
import com.tencent.downloadprovider.DownloadproviderHelper;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DownloadSections
{
  private DownloadTask jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
  Map<Integer, DownloadSection> jdField_a_of_type_JavaUtilMap = new ConcurrentHashMap();
  
  public void clear(boolean paramBoolean)
  {
    FLogger.d("DownloadSections", "clear, clearFiles=true");
    resetSections();
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadTaskId()));
      localContentValues.put("downloadsize", Long.valueOf(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.mWrittenSize));
      localContentValues.put("downloadedsize", Long.valueOf(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.mDownloadedSize));
      DownloadSection localDownloadSection;
      if (size() > 0)
      {
        localDownloadSection = getSection(0);
        localContentValues.put("startpos1", localDownloadSection.getSectionStartPos());
        localContentValues.put("endpos1", localDownloadSection.getSectionEndPos());
        localContentValues.put("writepos1", localDownloadSection.getSectionWriteLen());
      }
      else
      {
        localContentValues.put("startpos1", Integer.valueOf(0));
        localContentValues.put("endpos1", Integer.valueOf(-1));
        localContentValues.put("writepos1", Integer.valueOf(0));
      }
      if (size() > 1)
      {
        localDownloadSection = getSection(1);
        localContentValues.put("startpos2", localDownloadSection.getSectionStartPos());
        localContentValues.put("endpos2", localDownloadSection.getSectionEndPos());
        localContentValues.put("writepos2", localDownloadSection.getSectionWriteLen());
      }
      else
      {
        localContentValues.put("startpos2", Integer.valueOf(0));
        localContentValues.put("endpos2", Integer.valueOf(-1));
        localContentValues.put("writepos2", Integer.valueOf(0));
      }
      if (size() > 2)
      {
        localDownloadSection = getSection(2);
        localContentValues.put("startpos3", localDownloadSection.getSectionStartPos());
        localContentValues.put("endpos3", localDownloadSection.getSectionEndPos());
        localContentValues.put("writepos3", localDownloadSection.getSectionWriteLen());
      }
      else
      {
        localContentValues.put("startpos3", Integer.valueOf(0));
        localContentValues.put("endpos3", Integer.valueOf(-1));
        localContentValues.put("writepos3", Integer.valueOf(0));
      }
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public DownloadSection createSection(int paramInt, DownloadDataBuffer paramDownloadDataBuffer)
  {
    paramDownloadDataBuffer = new DownloadSection(paramInt, paramDownloadDataBuffer);
    this.jdField_a_of_type_JavaUtilMap.put(Integer.valueOf(paramInt), paramDownloadDataBuffer);
    return paramDownloadDataBuffer;
  }
  
  protected File getConfigFile(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(".dltmp");
    return new File(paramString1, localStringBuilder.toString());
  }
  
  public DownloadSection getSection(int paramInt)
  {
    return (DownloadSection)this.jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(paramInt));
  }
  
  public boolean hasValidConfigData(int paramInt)
  {
    boolean bool3 = getConfigFile(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileFolderPath(), this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileName()).exists();
    boolean bool1 = true;
    boolean bool2 = true;
    if (!bool3)
    {
      DownloadTask localDownloadTask = DownloadproviderHelper.a(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadTaskId());
      int i = 0;
      while (i < paramInt)
      {
        DownloadSection localDownloadSection = localDownloadTask.getSectionData().getSection(i);
        bool1 = bool2;
        if (localDownloadSection.getCurrentSectionStartPos() != 0L) {
          break label114;
        }
        bool1 = bool2;
        if (localDownloadSection.getCurrentSectionEndPos() != 0L) {
          break label114;
        }
        if (localDownloadSection.getCurrentSectionWriteLen() != 0L)
        {
          bool1 = bool2;
          break label114;
        }
        i += 1;
      }
      bool1 = false;
      label114:
      bool2 = bool1;
      if (bool1)
      {
        restoreConfigData(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileFolderPath(), this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileName(), paramInt);
        return bool1;
      }
    }
    else
    {
      restoreConfigData(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileFolderPath(), this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getFileName(), paramInt);
      bool2 = bool1;
    }
    return bool2;
  }
  
  protected boolean isValidSectionData(long paramLong, int paramInt)
  {
    Object localObject = this.jdField_a_of_type_JavaUtilMap;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (localObject != null)
    {
      bool1 = bool2;
      if (((Map)localObject).size() == paramInt)
      {
        int i = 0;
        while (i < paramInt)
        {
          localObject = (DownloadSection)this.jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(i));
          if (localObject != null)
          {
            bool1 = bool2;
            if (((DownloadSection)localObject).getCurrentSectionEndPos() < 0L) {
              break label106;
            }
            if (((DownloadSection)localObject).getCurrentSectionEndPos() > paramLong - 1L) {
              return false;
            }
          }
          i += 1;
        }
        bool1 = true;
      }
    }
    label106:
    return bool1;
  }
  
  public boolean isWriteFinish(long paramLong)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilMap.values().iterator();
    boolean bool2 = false;
    int j = 1;
    int i = 0;
    while (localIterator.hasNext())
    {
      DownloadSection localDownloadSection = (DownloadSection)localIterator.next();
      int k = j;
      if (localDownloadSection.getCurrentSectionStartPos() + localDownloadSection.getCurrentSectionWriteLen() <= localDownloadSection.getCurrentSectionEndPos()) {
        k = 0;
      }
      j = k;
      if (localDownloadSection.getCurrentSectionEndPos() >= paramLong - 1L)
      {
        i = 1;
        j = k;
      }
    }
    boolean bool1 = bool2;
    if (j != 0)
    {
      bool1 = bool2;
      if (i != 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public void putSection(int paramInt, DownloadSection paramDownloadSection)
  {
    this.jdField_a_of_type_JavaUtilMap.put(Integer.valueOf(paramInt), paramDownloadSection);
  }
  
  protected void resetSections()
  {
    int j = this.jdField_a_of_type_JavaUtilMap.size();
    int i = 0;
    while (i < j)
    {
      DownloadSection localDownloadSection = (DownloadSection)this.jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(i));
      localDownloadSection.setSectionStartPos(String.valueOf(0));
      localDownloadSection.setSectionEndPos(String.valueOf(-1));
      localDownloadSection.setSectionWriteLen(String.valueOf(0));
      localDownloadSection.setSectionDownloadLen(0L);
      i += 1;
    }
  }
  
  /* Error */
  public void restoreConfigData(String paramString1, String paramString2, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokevirtual 163	com/tencent/mtt/browser/download/engine/DownloadSections:getConfigFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   6: astore 13
    //   8: aload 13
    //   10: invokevirtual 167	java/io/File:exists	()Z
    //   13: istore 5
    //   15: iconst_0
    //   16: istore 4
    //   18: iload 5
    //   20: ifne +121 -> 141
    //   23: aload_0
    //   24: getfield 40	com/tencent/mtt/browser/download/engine/DownloadSections:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   27: invokevirtual 46	com/tencent/mtt/browser/download/engine/DownloadTask:getDownloadTaskId	()I
    //   30: invokestatic 170	com/tencent/downloadprovider/DownloadproviderHelper:a	(I)Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   33: astore_1
    //   34: aload_1
    //   35: ifnull +336 -> 371
    //   38: aload_0
    //   39: getfield 17	com/tencent/mtt/browser/download/engine/DownloadSections:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   42: invokeinterface 239 1 0
    //   47: iconst_0
    //   48: istore 4
    //   50: iload 4
    //   52: iload_3
    //   53: if_icmpge +318 -> 371
    //   56: aload_1
    //   57: invokevirtual 174	com/tencent/mtt/browser/download/engine/DownloadTask:getSectionData	()Lcom/tencent/mtt/browser/download/engine/DownloadSections;
    //   60: iload 4
    //   62: invokevirtual 82	com/tencent/mtt/browser/download/engine/DownloadSections:getSection	(I)Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   65: astore_2
    //   66: aload_2
    //   67: ifnull +17 -> 84
    //   70: aload_2
    //   71: invokevirtual 242	com/tencent/mtt/browser/download/engine/DownloadSection:correctCurrentIndex	()V
    //   74: aload_0
    //   75: iload 4
    //   77: aload_2
    //   78: invokevirtual 244	com/tencent/mtt/browser/download/engine/DownloadSections:putSection	(ILcom/tencent/mtt/browser/download/engine/DownloadSection;)V
    //   81: goto +51 -> 132
    //   84: new 86	com/tencent/mtt/browser/download/engine/DownloadSection
    //   87: dup
    //   88: iload 4
    //   90: new 246	com/tencent/mtt/browser/download/engine/DownloadDataBuffer
    //   93: dup
    //   94: invokespecial 247	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:<init>	()V
    //   97: invokespecial 124	com/tencent/mtt/browser/download/engine/DownloadSection:<init>	(ILcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;)V
    //   100: astore_2
    //   101: aload_2
    //   102: iconst_0
    //   103: invokestatic 219	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   106: invokevirtual 223	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionStartPos	(Ljava/lang/String;)V
    //   109: aload_2
    //   110: iconst_m1
    //   111: invokestatic 219	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   114: invokevirtual 226	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionEndPos	(Ljava/lang/String;)V
    //   117: aload_2
    //   118: iconst_0
    //   119: invokestatic 219	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   122: invokevirtual 229	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionWriteLen	(Ljava/lang/String;)V
    //   125: aload_0
    //   126: iload 4
    //   128: aload_2
    //   129: invokevirtual 244	com/tencent/mtt/browser/download/engine/DownloadSections:putSection	(ILcom/tencent/mtt/browser/download/engine/DownloadSection;)V
    //   132: iload 4
    //   134: iconst_1
    //   135: iadd
    //   136: istore 4
    //   138: goto -88 -> 50
    //   141: new 249	java/io/RandomAccessFile
    //   144: dup
    //   145: aload 13
    //   147: ldc -5
    //   149: invokespecial 254	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   152: astore_2
    //   153: aload_2
    //   154: astore_1
    //   155: aload_2
    //   156: invokevirtual 257	java/io/RandomAccessFile:readLong	()J
    //   159: lstore 6
    //   161: aload_2
    //   162: astore_1
    //   163: aload_0
    //   164: getfield 40	com/tencent/mtt/browser/download/engine/DownloadSections:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   167: lload 6
    //   169: iconst_0
    //   170: invokevirtual 261	com/tencent/mtt/browser/download/engine/DownloadTask:setWrittenSize	(JZ)V
    //   173: aload_2
    //   174: astore_1
    //   175: aload_0
    //   176: getfield 40	com/tencent/mtt/browser/download/engine/DownloadSections:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   179: lload 6
    //   181: iconst_0
    //   182: invokevirtual 264	com/tencent/mtt/browser/download/engine/DownloadTask:setDownloadedSize	(JZ)V
    //   185: aload_2
    //   186: astore_1
    //   187: aload_0
    //   188: getfield 17	com/tencent/mtt/browser/download/engine/DownloadSections:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   191: invokeinterface 239 1 0
    //   196: iload 4
    //   198: iload_3
    //   199: if_icmpge +119 -> 318
    //   202: aload_2
    //   203: astore_1
    //   204: new 86	com/tencent/mtt/browser/download/engine/DownloadSection
    //   207: dup
    //   208: iload 4
    //   210: new 246	com/tencent/mtt/browser/download/engine/DownloadDataBuffer
    //   213: dup
    //   214: invokespecial 247	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:<init>	()V
    //   217: invokespecial 124	com/tencent/mtt/browser/download/engine/DownloadSection:<init>	(ILcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;)V
    //   220: astore 12
    //   222: aload_2
    //   223: astore_1
    //   224: aload_2
    //   225: invokevirtual 257	java/io/RandomAccessFile:readLong	()J
    //   228: lstore 6
    //   230: aload_2
    //   231: astore_1
    //   232: aload_2
    //   233: invokevirtual 257	java/io/RandomAccessFile:readLong	()J
    //   236: lstore 8
    //   238: aload_2
    //   239: astore_1
    //   240: aload_2
    //   241: invokevirtual 257	java/io/RandomAccessFile:readLong	()J
    //   244: lstore 10
    //   246: aload_2
    //   247: astore_1
    //   248: aload 12
    //   250: lload 6
    //   252: invokestatic 267	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   255: invokevirtual 223	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionStartPos	(Ljava/lang/String;)V
    //   258: aload_2
    //   259: astore_1
    //   260: aload 12
    //   262: lload 8
    //   264: invokestatic 267	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   267: invokevirtual 226	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionEndPos	(Ljava/lang/String;)V
    //   270: aload_2
    //   271: astore_1
    //   272: aload 12
    //   274: lload 10
    //   276: invokestatic 267	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   279: invokevirtual 229	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionWriteLen	(Ljava/lang/String;)V
    //   282: aload_2
    //   283: astore_1
    //   284: aload_0
    //   285: iload 4
    //   287: aload 12
    //   289: invokevirtual 244	com/tencent/mtt/browser/download/engine/DownloadSections:putSection	(ILcom/tencent/mtt/browser/download/engine/DownloadSection;)V
    //   292: iload 4
    //   294: iconst_1
    //   295: iadd
    //   296: istore 4
    //   298: goto -102 -> 196
    //   301: aload_2
    //   302: invokevirtual 270	java/io/RandomAccessFile:close	()V
    //   305: aload 13
    //   307: invokevirtual 273	java/io/File:delete	()Z
    //   310: pop
    //   311: return
    //   312: astore_1
    //   313: aload_1
    //   314: invokevirtual 276	java/io/IOException:printStackTrace	()V
    //   317: return
    //   318: aload_2
    //   319: invokevirtual 270	java/io/RandomAccessFile:close	()V
    //   322: aload 13
    //   324: invokevirtual 273	java/io/File:delete	()Z
    //   327: pop
    //   328: return
    //   329: astore 12
    //   331: goto +13 -> 344
    //   334: astore_2
    //   335: aconst_null
    //   336: astore_1
    //   337: goto +36 -> 373
    //   340: astore 12
    //   342: aconst_null
    //   343: astore_2
    //   344: aload_2
    //   345: astore_1
    //   346: aload 12
    //   348: invokevirtual 277	java/lang/Exception:printStackTrace	()V
    //   351: aload_2
    //   352: ifnull +19 -> 371
    //   355: aload_2
    //   356: invokevirtual 270	java/io/RandomAccessFile:close	()V
    //   359: aload 13
    //   361: invokevirtual 273	java/io/File:delete	()Z
    //   364: pop
    //   365: return
    //   366: astore_1
    //   367: aload_1
    //   368: invokevirtual 276	java/io/IOException:printStackTrace	()V
    //   371: return
    //   372: astore_2
    //   373: aload_1
    //   374: ifnull +21 -> 395
    //   377: aload_1
    //   378: invokevirtual 270	java/io/RandomAccessFile:close	()V
    //   381: aload 13
    //   383: invokevirtual 273	java/io/File:delete	()Z
    //   386: pop
    //   387: goto +8 -> 395
    //   390: astore_1
    //   391: aload_1
    //   392: invokevirtual 276	java/io/IOException:printStackTrace	()V
    //   395: aload_2
    //   396: athrow
    //   397: astore_1
    //   398: goto -97 -> 301
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	401	0	this	DownloadSections
    //   0	401	1	paramString1	String
    //   0	401	2	paramString2	String
    //   0	401	3	paramInt	int
    //   16	281	4	i	int
    //   13	6	5	bool	boolean
    //   159	92	6	l1	long
    //   236	27	8	l2	long
    //   244	31	10	l3	long
    //   220	68	12	localDownloadSection	DownloadSection
    //   329	1	12	localException1	Exception
    //   340	7	12	localException2	Exception
    //   6	376	13	localFile	File
    // Exception table:
    //   from	to	target	type
    //   301	311	312	java/io/IOException
    //   155	161	329	java/lang/Exception
    //   163	173	329	java/lang/Exception
    //   175	185	329	java/lang/Exception
    //   187	196	329	java/lang/Exception
    //   141	153	334	finally
    //   141	153	340	java/lang/Exception
    //   318	328	366	java/io/IOException
    //   355	365	366	java/io/IOException
    //   155	161	372	finally
    //   163	173	372	finally
    //   175	185	372	finally
    //   187	196	372	finally
    //   204	222	372	finally
    //   224	230	372	finally
    //   232	238	372	finally
    //   240	246	372	finally
    //   248	258	372	finally
    //   260	270	372	finally
    //   272	282	372	finally
    //   284	292	372	finally
    //   346	351	372	finally
    //   377	387	390	java/io/IOException
    //   204	222	397	java/lang/Exception
    //   224	230	397	java/lang/Exception
    //   232	238	397	java/lang/Exception
    //   240	246	397	java/lang/Exception
    //   248	258	397	java/lang/Exception
    //   260	270	397	java/lang/Exception
    //   272	282	397	java/lang/Exception
    //   284	292	397	java/lang/Exception
  }
  
  public void setDownloadTask(DownloadTask paramDownloadTask)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
  }
  
  public int size()
  {
    return this.jdField_a_of_type_JavaUtilMap.size();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadSections.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */