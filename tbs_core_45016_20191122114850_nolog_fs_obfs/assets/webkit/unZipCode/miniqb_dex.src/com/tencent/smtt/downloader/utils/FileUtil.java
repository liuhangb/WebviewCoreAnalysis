package com.tencent.smtt.downloader.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import com.tencent.smtt.downloader.a;
import com.tencent.smtt.downloader.c;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.zip.ZipEntry;

@SuppressLint({"NewApi"})
public class FileUtil
{
  private static final int jdField_a_of_type_Int = 4;
  public static final FileComparator a;
  private static RandomAccessFile jdField_a_of_type_JavaIoRandomAccessFile;
  public static String a;
  
  static
  {
    jdField_a_of_type_ComTencentSmttDownloaderUtilsFileUtil$FileComparator = new FileComparator()
    {
      public boolean equals(File paramAnonymousFile1, File paramAnonymousFile2)
      {
        return (paramAnonymousFile1.length() == paramAnonymousFile2.length()) && (paramAnonymousFile1.lastModified() == paramAnonymousFile2.lastModified());
      }
    };
  }
  
  public static File a(Context paramContext, boolean paramBoolean, String paramString)
  {
    if (paramBoolean) {
      paramContext = b(paramContext);
    } else {
      paramContext = a(paramContext);
    }
    if (paramContext == null) {
      return null;
    }
    paramContext = new File(paramContext);
    if (!paramContext.exists()) {
      paramContext.mkdirs();
    }
    if (!paramContext.canWrite()) {
      return null;
    }
    paramContext = new File(paramContext, paramString);
    if (!paramContext.exists()) {
      try
      {
        paramContext.createNewFile();
        return paramContext;
      }
      catch (IOException paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
    }
    return paramContext;
  }
  
  public static FileOutputStream a(Context paramContext, boolean paramBoolean, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TbsInstaller--getLockFos of filename: ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    paramContext = a(paramContext, paramBoolean, paramString);
    if (paramContext != null) {
      try
      {
        paramContext = new FileOutputStream(paramContext);
        return paramContext;
      }
      catch (FileNotFoundException paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static String a(Context paramContext)
  {
    paramContext = new StringBuilder();
    paramContext.append(Environment.getExternalStorageDirectory());
    paramContext.append(File.separator);
    paramContext.append("tbs");
    paramContext.append(File.separator);
    paramContext.append("file_locks");
    return paramContext.toString();
  }
  
  public static String a(Context paramContext, int paramInt)
  {
    return a(paramContext, paramContext.getApplicationInfo().packageName, paramInt, true);
  }
  
  private static String a(Context paramContext, String paramString)
  {
    if (paramContext == null) {
      return "";
    }
    Object localObject = paramContext.getApplicationContext();
    if (localObject != null) {
      paramContext = (Context)localObject;
    }
    try
    {
      localObject = paramContext.getExternalFilesDir(paramString).getAbsolutePath();
      return (String)localObject;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(Environment.getExternalStorageDirectory());
        localStringBuilder.append(File.separator);
        localStringBuilder.append("Android");
        localStringBuilder.append(File.separator);
        localStringBuilder.append("data");
        localStringBuilder.append(File.separator);
        localStringBuilder.append(paramContext.getApplicationInfo().packageName);
        localStringBuilder.append(File.separator);
        localStringBuilder.append("files");
        localStringBuilder.append(File.separator);
        localStringBuilder.append(paramString);
        paramContext = localStringBuilder.toString();
        return paramContext;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return "";
  }
  
  public static String a(Context paramContext, String paramString, int paramInt, boolean paramBoolean)
  {
    if (paramContext == null) {
      return "";
    }
    Object localObject1 = "";
    try
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(Environment.getExternalStorageDirectory());
      ((StringBuilder)localObject2).append(File.separator);
      localObject2 = ((StringBuilder)localObject2).toString();
      localObject1 = localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    switch (paramInt)
    {
    default: 
      return "";
    case 7: 
      if (((String)localObject1).equals("")) {
        return (String)localObject1;
      }
      paramContext = new StringBuilder();
      paramContext.append((String)localObject1);
      paramContext.append("tencent");
      paramContext.append(File.separator);
      paramContext.append("tbs");
      paramContext.append(File.separator);
      paramContext.append("backup");
      paramContext.append(File.separator);
      paramContext.append("core");
      return paramContext.toString();
    case 6: 
      paramString = jdField_a_of_type_JavaLangString;
      if (paramString != null) {
        return paramString;
      }
      jdField_a_of_type_JavaLangString = a(paramContext, "tbslog");
      return jdField_a_of_type_JavaLangString;
    case 5: 
      if (((String)localObject1).equals("")) {
        return (String)localObject1;
      }
      paramContext = new StringBuilder();
      paramContext.append((String)localObject1);
      paramContext.append("tencent");
      paramContext.append(File.separator);
      paramContext.append("tbs");
      paramContext.append(File.separator);
      paramContext.append(paramString);
      return paramContext.toString();
    case 4: 
      if (((String)localObject1).equals("")) {
        return a(paramContext, "backup");
      }
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).append("tencent");
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append("tbs");
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append("backup");
      ((StringBuilder)localObject3).append(File.separator);
      ((StringBuilder)localObject3).append(paramString);
      localObject1 = ((StringBuilder)localObject3).toString();
      paramString = (String)localObject1;
      if (paramBoolean)
      {
        localObject3 = new File((String)localObject1);
        if (((File)localObject3).exists())
        {
          paramString = (String)localObject1;
          if (((File)localObject3).canWrite()) {}
        }
        else if (!((File)localObject3).exists())
        {
          ((File)localObject3).mkdirs();
          paramString = (String)localObject1;
          if (!((File)localObject3).canWrite()) {
            return a(paramContext, "backup");
          }
        }
        else
        {
          paramString = a(paramContext, "backup");
        }
      }
      return paramString;
    case 3: 
      if (((String)localObject1).equals("")) {
        return (String)localObject1;
      }
      paramContext = new StringBuilder();
      paramContext.append((String)localObject1);
      paramContext.append("tencent");
      paramContext.append(File.separator);
      paramContext.append("tbs");
      paramContext.append(File.separator);
      paramContext.append("backup");
      paramContext.append(File.separator);
      paramContext.append(paramString);
      return paramContext.toString();
    case 2: 
      if (((String)localObject1).equals("")) {
        return (String)localObject1;
      }
      paramContext = new StringBuilder();
      paramContext.append((String)localObject1);
      paramContext.append("tbs");
      paramContext.append(File.separator);
      paramContext.append("backup");
      paramContext.append(File.separator);
      paramContext.append(paramString);
      return paramContext.toString();
    }
    if (((String)localObject1).equals("")) {
      return (String)localObject1;
    }
    paramContext = new StringBuilder();
    paramContext.append((String)localObject1);
    paramContext.append("tencent");
    paramContext.append(File.separator);
    paramContext.append("tbs");
    paramContext.append(File.separator);
    paramContext.append(paramString);
    return paramContext.toString();
  }
  
  public static FileLock a(Context paramContext, FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return null;
    }
    try
    {
      paramContext = paramFileOutputStream.getChannel().tryLock();
      boolean bool = paramContext.isValid();
      if (bool) {
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      return null;
    }
    catch (OverlappingFileLockException paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static void a(File paramFile)
  {
    a(paramFile, false);
  }
  
  public static void a(File paramFile, boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("delete file,ignore=");
    ((StringBuilder)localObject).append(paramBoolean);
    ((StringBuilder)localObject).append(paramFile);
    ((StringBuilder)localObject).append(Log.getStackTraceString(new Throwable()));
    h.a("FileUtils", ((StringBuilder)localObject).toString());
    if (paramFile != null)
    {
      if (!paramFile.exists()) {
        return;
      }
      if (paramFile.isFile())
      {
        paramFile.delete();
        return;
      }
      localObject = paramFile.listFiles();
      if (localObject == null) {
        return;
      }
      int j = localObject.length;
      int i = 0;
      while (i < j)
      {
        a(localObject[i], paramBoolean);
        i += 1;
      }
      if (!paramBoolean) {
        paramFile.delete();
      }
      return;
    }
  }
  
  public static void a(FileLock paramFileLock, FileOutputStream paramFileOutputStream)
  {
    if (paramFileLock != null) {
      try
      {
        FileChannel localFileChannel = paramFileLock.channel();
        if ((localFileChannel != null) && (localFileChannel.isOpen())) {
          paramFileLock.release();
        }
      }
      catch (Exception paramFileLock)
      {
        paramFileLock.printStackTrace();
      }
    }
    if (paramFileOutputStream != null) {
      try
      {
        paramFileOutputStream.close();
        return;
      }
      catch (Exception paramFileLock)
      {
        paramFileLock.printStackTrace();
      }
    }
  }
  
  public static boolean a(Context paramContext)
  {
    long l = j.a();
    boolean bool;
    if (l >= c.a(paramContext).c()) {
      bool = true;
    } else {
      bool = false;
    }
    if (!bool)
    {
      paramContext = new StringBuilder();
      paramContext.append("[TbsApkDwonloader.hasEnoughFreeSpace] freeSpace too small,  freeSpace = ");
      paramContext.append(l);
      h.b("TbsDownload", paramContext.toString());
    }
    return bool;
  }
  
  public static boolean a(File paramFile)
  {
    if (paramFile == null) {
      return false;
    }
    if ((paramFile.exists()) && (paramFile.isDirectory())) {
      return true;
    }
    a(paramFile);
    return paramFile.mkdirs();
  }
  
  public static boolean a(File paramFile1, File paramFile2)
    throws Exception
  {
    return a(paramFile1.getPath(), paramFile2.getPath());
  }
  
  public static boolean a(File paramFile1, File paramFile2, FileFilter paramFileFilter)
    throws Exception
  {
    return a(paramFile1, paramFile2, paramFileFilter, jdField_a_of_type_ComTencentSmttDownloaderUtilsFileUtil$FileComparator);
  }
  
  public static boolean a(File paramFile1, File paramFile2, FileFilter paramFileFilter, FileComparator paramFileComparator)
    throws Exception
  {
    if (paramFile1 != null)
    {
      if (paramFile2 == null) {
        return false;
      }
      if (!paramFile1.exists()) {
        return false;
      }
      if (paramFile1.isFile()) {
        return b(paramFile1, paramFile2, paramFileFilter, paramFileComparator);
      }
      paramFile1 = paramFile1.listFiles(paramFileFilter);
      if (paramFile1 == null) {
        return false;
      }
      int j = paramFile1.length;
      int i = 0;
      boolean bool = true;
      while (i < j)
      {
        paramFileComparator = paramFile1[i];
        if (!a(paramFileComparator, new File(paramFile2, paramFileComparator.getName()), paramFileFilter)) {
          bool = false;
        }
        i += 1;
      }
      return bool;
    }
    return false;
  }
  
  /* Error */
  private static boolean a(String paramString, long paramLong1, long paramLong2, long paramLong3)
    throws Exception
  {
    // Byte code:
    //   0: new 42	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 45	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_0
    //   9: aload_0
    //   10: invokevirtual 285	java/io/File:length	()J
    //   13: lload_1
    //   14: lcmp
    //   15: ifeq +60 -> 75
    //   18: new 69	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   25: astore 8
    //   27: aload 8
    //   29: ldc_w 287
    //   32: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload 8
    //   38: aload_0
    //   39: invokevirtual 285	java/io/File:length	()J
    //   42: invokevirtual 246	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 8
    //   48: ldc_w 289
    //   51: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload 8
    //   57: lload_1
    //   58: invokevirtual 246	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: ldc_w 291
    //   65: aload 8
    //   67: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: invokestatic 250	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   73: iconst_1
    //   74: ireturn
    //   75: new 293	java/io/FileInputStream
    //   78: dup
    //   79: aload_0
    //   80: invokespecial 294	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   83: astore 8
    //   85: new 296	java/util/zip/CRC32
    //   88: dup
    //   89: invokespecial 297	java/util/zip/CRC32:<init>	()V
    //   92: astore 9
    //   94: sipush 8192
    //   97: newarray <illegal type>
    //   99: astore 10
    //   101: aload 8
    //   103: aload 10
    //   105: invokevirtual 301	java/io/FileInputStream:read	([B)I
    //   108: istore 7
    //   110: iload 7
    //   112: ifle +16 -> 128
    //   115: aload 9
    //   117: aload 10
    //   119: iconst_0
    //   120: iload 7
    //   122: invokevirtual 305	java/util/zip/CRC32:update	([BII)V
    //   125: goto -24 -> 101
    //   128: aload 9
    //   130: invokevirtual 308	java/util/zip/CRC32:getValue	()J
    //   133: lstore_1
    //   134: new 69	java/lang/StringBuilder
    //   137: dup
    //   138: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   141: astore 9
    //   143: aload 9
    //   145: ldc 126
    //   147: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload 9
    //   153: aload_0
    //   154: invokevirtual 276	java/io/File:getName	()Ljava/lang/String;
    //   157: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload 9
    //   163: ldc_w 310
    //   166: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload 9
    //   172: lload_1
    //   173: invokevirtual 246	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   176: pop
    //   177: aload 9
    //   179: ldc_w 312
    //   182: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: pop
    //   186: aload 9
    //   188: lload 5
    //   190: invokevirtual 246	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   193: pop
    //   194: ldc_w 291
    //   197: aload 9
    //   199: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   202: invokestatic 204	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   205: lload_1
    //   206: lload 5
    //   208: lcmp
    //   209: ifeq +10 -> 219
    //   212: aload 8
    //   214: invokevirtual 313	java/io/FileInputStream:close	()V
    //   217: iconst_1
    //   218: ireturn
    //   219: aload 8
    //   221: invokevirtual 313	java/io/FileInputStream:close	()V
    //   224: iconst_0
    //   225: ireturn
    //   226: astore_0
    //   227: goto +7 -> 234
    //   230: astore_0
    //   231: aconst_null
    //   232: astore 8
    //   234: aload 8
    //   236: ifnull +8 -> 244
    //   239: aload 8
    //   241: invokevirtual 313	java/io/FileInputStream:close	()V
    //   244: aload_0
    //   245: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	246	0	paramString	String
    //   0	246	1	paramLong1	long
    //   0	246	3	paramLong2	long
    //   0	246	5	paramLong3	long
    //   108	13	7	i	int
    //   25	215	8	localObject1	Object
    //   92	106	9	localObject2	Object
    //   99	19	10	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   85	101	226	finally
    //   101	110	226	finally
    //   115	125	226	finally
    //   128	205	226	finally
    //   75	85	230	finally
  }
  
  @SuppressLint({"InlinedApi"})
  public static boolean a(String paramString1, String paramString2)
    throws Exception
  {
    String str2 = Build.CPU_ABI;
    String str1;
    if (Build.VERSION.SDK_INT >= 8) {
      str1 = Build.CPU_ABI2;
    } else {
      str1 = null;
    }
    return a(paramString1, paramString2, str2, str1, "arm64-v8a");
  }
  
  /* Error */
  private static boolean a(String paramString1, String paramString2, String paramString3, String paramString4, IterateHandler paramIterateHandler)
    throws Exception
  {
    // Byte code:
    //   0: new 69	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   7: astore 9
    //   9: aload 9
    //   11: ldc_w 336
    //   14: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload 9
    //   20: aload_0
    //   21: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: ldc_w 291
    //   28: aload 9
    //   30: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: invokestatic 339	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   36: new 341	java/util/zip/ZipFile
    //   39: dup
    //   40: aload_0
    //   41: invokespecial 342	java/util/zip/ZipFile:<init>	(Ljava/lang/String;)V
    //   44: astore_0
    //   45: aload_0
    //   46: invokevirtual 346	java/util/zip/ZipFile:entries	()Ljava/util/Enumeration;
    //   49: astore 9
    //   51: iconst_0
    //   52: istore 6
    //   54: iconst_0
    //   55: istore 5
    //   57: aload 9
    //   59: invokeinterface 351 1 0
    //   64: ifeq +620 -> 684
    //   67: aload 9
    //   69: invokeinterface 355 1 0
    //   74: checkcast 357	java/util/zip/ZipEntry
    //   77: astore 11
    //   79: aload 11
    //   81: invokevirtual 358	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   84: astore 10
    //   86: aload 10
    //   88: ifnonnull +6 -> 94
    //   91: goto -34 -> 57
    //   94: aload 10
    //   96: ldc_w 360
    //   99: invokevirtual 364	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   102: ifeq +6 -> 108
    //   105: goto -48 -> 57
    //   108: aload 10
    //   110: ldc_w 366
    //   113: invokevirtual 370	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   116: ifne +17 -> 133
    //   119: aload 10
    //   121: ldc_w 372
    //   124: invokevirtual 370	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   127: ifne +6 -> 133
    //   130: goto -73 -> 57
    //   133: aload 10
    //   135: aload 10
    //   137: bipush 47
    //   139: invokevirtual 376	java/lang/String:lastIndexOf	(I)I
    //   142: invokevirtual 380	java/lang/String:substring	(I)Ljava/lang/String;
    //   145: astore 12
    //   147: iload 6
    //   149: istore 7
    //   151: iload 5
    //   153: istore 8
    //   155: aload 12
    //   157: ldc_w 382
    //   160: invokevirtual 385	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   163: ifeq +401 -> 564
    //   166: aload 10
    //   168: getstatic 387	com/tencent/smtt/downloader/utils/FileUtil:jdField_a_of_type_Int	I
    //   171: aload_1
    //   172: iconst_0
    //   173: aload_1
    //   174: invokevirtual 390	java/lang/String:length	()I
    //   177: invokevirtual 394	java/lang/String:regionMatches	(ILjava/lang/String;II)Z
    //   180: ifeq +31 -> 211
    //   183: aload 10
    //   185: getstatic 387	com/tencent/smtt/downloader/utils/FileUtil:jdField_a_of_type_Int	I
    //   188: aload_1
    //   189: invokevirtual 390	java/lang/String:length	()I
    //   192: iadd
    //   193: invokevirtual 398	java/lang/String:charAt	(I)C
    //   196: bipush 47
    //   198: if_icmpne +13 -> 211
    //   201: iconst_1
    //   202: istore 7
    //   204: iload 5
    //   206: istore 8
    //   208: goto +356 -> 564
    //   211: aload_2
    //   212: ifnull +131 -> 343
    //   215: aload 10
    //   217: getstatic 387	com/tencent/smtt/downloader/utils/FileUtil:jdField_a_of_type_Int	I
    //   220: aload_2
    //   221: iconst_0
    //   222: aload_2
    //   223: invokevirtual 390	java/lang/String:length	()I
    //   226: invokevirtual 394	java/lang/String:regionMatches	(ILjava/lang/String;II)Z
    //   229: ifeq +114 -> 343
    //   232: aload 10
    //   234: getstatic 387	com/tencent/smtt/downloader/utils/FileUtil:jdField_a_of_type_Int	I
    //   237: aload_2
    //   238: invokevirtual 390	java/lang/String:length	()I
    //   241: iadd
    //   242: invokevirtual 398	java/lang/String:charAt	(I)C
    //   245: bipush 47
    //   247: if_icmpne +96 -> 343
    //   250: iload 6
    //   252: ifeq +45 -> 297
    //   255: new 69	java/lang/StringBuilder
    //   258: dup
    //   259: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   262: astore 10
    //   264: aload 10
    //   266: ldc_w 400
    //   269: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: pop
    //   273: aload 10
    //   275: aload_2
    //   276: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: pop
    //   280: ldc_w 291
    //   283: aload 10
    //   285: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   288: invokestatic 339	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   291: iconst_1
    //   292: istore 5
    //   294: goto -237 -> 57
    //   297: new 69	java/lang/StringBuilder
    //   300: dup
    //   301: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   304: astore 13
    //   306: aload 13
    //   308: ldc_w 402
    //   311: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: aload 13
    //   317: aload_2
    //   318: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: pop
    //   322: ldc_w 291
    //   325: aload 13
    //   327: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   330: invokestatic 339	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   333: iconst_1
    //   334: istore 8
    //   336: iload 6
    //   338: istore 7
    //   340: goto +224 -> 564
    //   343: aload_3
    //   344: ifnull +137 -> 481
    //   347: aload 10
    //   349: getstatic 387	com/tencent/smtt/downloader/utils/FileUtil:jdField_a_of_type_Int	I
    //   352: aload_3
    //   353: iconst_0
    //   354: aload_3
    //   355: invokevirtual 390	java/lang/String:length	()I
    //   358: invokevirtual 394	java/lang/String:regionMatches	(ILjava/lang/String;II)Z
    //   361: ifeq +120 -> 481
    //   364: aload 10
    //   366: getstatic 387	com/tencent/smtt/downloader/utils/FileUtil:jdField_a_of_type_Int	I
    //   369: aload_3
    //   370: invokevirtual 390	java/lang/String:length	()I
    //   373: iadd
    //   374: invokevirtual 398	java/lang/String:charAt	(I)C
    //   377: bipush 47
    //   379: if_icmpne +102 -> 481
    //   382: iload 6
    //   384: ifne +58 -> 442
    //   387: iload 5
    //   389: ifeq +6 -> 395
    //   392: goto +50 -> 442
    //   395: new 69	java/lang/StringBuilder
    //   398: dup
    //   399: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   402: astore 13
    //   404: aload 13
    //   406: ldc_w 404
    //   409: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   412: pop
    //   413: aload 13
    //   415: aload_3
    //   416: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   419: pop
    //   420: ldc_w 291
    //   423: aload 13
    //   425: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   428: invokestatic 339	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   431: iload 6
    //   433: istore 7
    //   435: iload 5
    //   437: istore 8
    //   439: goto +125 -> 564
    //   442: new 69	java/lang/StringBuilder
    //   445: dup
    //   446: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   449: astore 10
    //   451: aload 10
    //   453: ldc_w 406
    //   456: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   459: pop
    //   460: aload 10
    //   462: aload_3
    //   463: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   466: pop
    //   467: ldc_w 291
    //   470: aload 10
    //   472: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   475: invokestatic 339	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   478: goto -421 -> 57
    //   481: new 69	java/lang/StringBuilder
    //   484: dup
    //   485: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   488: astore 11
    //   490: aload 11
    //   492: ldc_w 408
    //   495: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: pop
    //   499: aload 11
    //   501: aload 10
    //   503: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   506: pop
    //   507: aload 11
    //   509: ldc_w 410
    //   512: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   515: pop
    //   516: aload 11
    //   518: aload_1
    //   519: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   522: pop
    //   523: aload 11
    //   525: ldc_w 412
    //   528: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   531: pop
    //   532: aload 11
    //   534: aload_2
    //   535: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   538: pop
    //   539: aload 11
    //   541: ldc_w 414
    //   544: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: pop
    //   548: aload 11
    //   550: aload_3
    //   551: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   554: pop
    //   555: aload 11
    //   557: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   560: pop
    //   561: goto -504 -> 57
    //   564: aload_0
    //   565: aload 11
    //   567: invokevirtual 418	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   570: astore 13
    //   572: aload 4
    //   574: aload 13
    //   576: aload 11
    //   578: aload 12
    //   580: iconst_1
    //   581: invokevirtual 380	java/lang/String:substring	(I)Ljava/lang/String;
    //   584: invokeinterface 422 4 0
    //   589: ifne +53 -> 642
    //   592: new 69	java/lang/StringBuilder
    //   595: dup
    //   596: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   599: astore_1
    //   600: aload_1
    //   601: ldc_w 424
    //   604: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   607: pop
    //   608: aload_1
    //   609: aload 10
    //   611: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   614: pop
    //   615: ldc_w 291
    //   618: aload_1
    //   619: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   622: invokestatic 428	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   625: pop
    //   626: aload 13
    //   628: ifnull +8 -> 636
    //   631: aload 13
    //   633: invokevirtual 431	java/io/InputStream:close	()V
    //   636: aload_0
    //   637: invokevirtual 432	java/util/zip/ZipFile:close	()V
    //   640: iconst_0
    //   641: ireturn
    //   642: iload 7
    //   644: istore 6
    //   646: iload 8
    //   648: istore 5
    //   650: aload 13
    //   652: ifnull -595 -> 57
    //   655: aload 13
    //   657: invokevirtual 431	java/io/InputStream:close	()V
    //   660: iload 7
    //   662: istore 6
    //   664: iload 8
    //   666: istore 5
    //   668: goto -611 -> 57
    //   671: astore_1
    //   672: aload 13
    //   674: ifnull +8 -> 682
    //   677: aload 13
    //   679: invokevirtual 431	java/io/InputStream:close	()V
    //   682: aload_1
    //   683: athrow
    //   684: aload_0
    //   685: invokevirtual 432	java/util/zip/ZipFile:close	()V
    //   688: iconst_1
    //   689: ireturn
    //   690: astore_1
    //   691: goto +6 -> 697
    //   694: astore_1
    //   695: aconst_null
    //   696: astore_0
    //   697: aload_0
    //   698: ifnull +7 -> 705
    //   701: aload_0
    //   702: invokevirtual 432	java/util/zip/ZipFile:close	()V
    //   705: aload_1
    //   706: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	707	0	paramString1	String
    //   0	707	1	paramString2	String
    //   0	707	2	paramString3	String
    //   0	707	3	paramString4	String
    //   0	707	4	paramIterateHandler	IterateHandler
    //   55	612	5	i	int
    //   52	611	6	j	int
    //   149	512	7	k	int
    //   153	512	8	m	int
    //   7	61	9	localObject1	Object
    //   84	526	10	localObject2	Object
    //   77	500	11	localObject3	Object
    //   145	434	12	str	String
    //   304	374	13	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   572	626	671	finally
    //   45	51	690	finally
    //   57	86	690	finally
    //   94	105	690	finally
    //   108	130	690	finally
    //   133	147	690	finally
    //   155	201	690	finally
    //   215	250	690	finally
    //   255	291	690	finally
    //   297	333	690	finally
    //   347	382	690	finally
    //   395	431	690	finally
    //   442	478	690	finally
    //   481	561	690	finally
    //   564	572	690	finally
    //   631	636	690	finally
    //   655	660	690	finally
    //   677	682	690	finally
    //   682	684	690	finally
    //   36	45	694	finally
  }
  
  private static boolean a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws Exception
  {
    a(paramString1, paramString3, paramString4, paramString5, new IterateHandler()
    {
      public boolean handleEntry(InputStream paramAnonymousInputStream, ZipEntry paramAnonymousZipEntry, String paramAnonymousString)
        throws Exception
      {
        try
        {
          boolean bool = FileUtil.a(paramAnonymousInputStream, paramAnonymousZipEntry, this.a, paramAnonymousString);
          return bool;
        }
        catch (Exception paramAnonymousInputStream)
        {
          throw new Exception("copyFileIfChanged Exception", paramAnonymousInputStream);
        }
      }
    });
  }
  
  static String b(Context paramContext)
  {
    paramContext = new File(a.a(paramContext), "core_private");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext.getAbsolutePath();
  }
  
  public static boolean b(File paramFile)
  {
    return (paramFile != null) && (paramFile.exists()) && (paramFile.isFile()) && (paramFile.length() > 0L);
  }
  
  public static boolean b(File paramFile1, File paramFile2)
    throws Exception
  {
    return a(paramFile1, paramFile2, null);
  }
  
  private static boolean b(File paramFile1, File paramFile2, FileFilter paramFileFilter, FileComparator paramFileComparator)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("performCopyFile: ");
    localStringBuilder.append(paramFile1);
    localStringBuilder.append(" --> ");
    localStringBuilder.append(paramFile2);
    localStringBuilder.append(", filter: ");
    localStringBuilder.append(paramFileFilter);
    localStringBuilder.toString();
    if (paramFile1 != null)
    {
      if (paramFile2 == null) {
        return false;
      }
      if ((paramFileFilter != null) && (!paramFileFilter.accept(paramFile1))) {
        return false;
      }
      paramFileFilter = null;
      localStringBuilder = null;
      try
      {
        if (paramFile1.exists())
        {
          if (!paramFile1.isFile()) {
            return false;
          }
          if (paramFile2.exists())
          {
            if ((paramFileComparator != null) && (paramFileComparator.equals(paramFile1, paramFile2))) {
              return true;
            }
            a(paramFile2);
          }
          paramFileComparator = paramFile2.getParentFile();
          if (paramFileComparator.isFile()) {
            a(paramFileComparator);
          }
          if ((!paramFileComparator.exists()) && (!paramFileComparator.mkdirs())) {
            return false;
          }
          paramFileComparator = new FileInputStream(paramFile1).getChannel();
          paramFile1 = localStringBuilder;
          try
          {
            paramFileFilter = new FileOutputStream(paramFile2).getChannel();
            paramFile1 = paramFileFilter;
            long l = paramFileComparator.size();
            paramFile1 = paramFileFilter;
            if (paramFileFilter.transferFrom(paramFileComparator, 0L, l) != l)
            {
              paramFile1 = paramFileFilter;
              a(paramFile2);
              if (paramFileComparator != null) {
                paramFileComparator.close();
              }
              if (paramFileFilter != null) {
                paramFileFilter.close();
              }
              return false;
            }
            if (paramFileComparator != null) {
              paramFileComparator.close();
            }
            if (paramFileFilter != null) {
              paramFileFilter.close();
            }
            return true;
          }
          finally
          {
            paramFile2 = paramFileComparator;
            paramFileComparator = paramFile1;
            paramFile1 = paramFileFilter;
            paramFileFilter = paramFileComparator;
            break label284;
          }
        }
        return false;
      }
      finally
      {
        paramFileComparator = null;
        paramFile2 = paramFileFilter;
        paramFileFilter = paramFileComparator;
        label284:
        if (paramFile2 != null) {
          paramFile2.close();
        }
        if (paramFileFilter != null) {
          paramFileFilter.close();
        }
      }
    }
    return false;
  }
  
  /* Error */
  @SuppressLint({"NewApi"})
  private static boolean b(InputStream paramInputStream, ZipEntry paramZipEntry, String paramString1, String paramString2)
    throws Exception
  {
    // Byte code:
    //   0: new 42	java/io/File
    //   3: dup
    //   4: aload_2
    //   5: invokespecial 45	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: invokestatic 472	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)Z
    //   11: pop
    //   12: new 69	java/lang/StringBuilder
    //   15: dup
    //   16: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   19: astore 5
    //   21: aload 5
    //   23: aload_2
    //   24: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload 5
    //   30: getstatic 100	java/io/File:separator	Ljava/lang/String;
    //   33: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: aload 5
    //   39: aload_3
    //   40: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 5
    //   46: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: astore 7
    //   51: new 42	java/io/File
    //   54: dup
    //   55: aload 7
    //   57: invokespecial 45	java/io/File:<init>	(Ljava/lang/String;)V
    //   60: astore 6
    //   62: aconst_null
    //   63: astore 5
    //   65: aconst_null
    //   66: astore_2
    //   67: new 84	java/io/FileOutputStream
    //   70: dup
    //   71: aload 6
    //   73: invokespecial 87	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   76: astore_3
    //   77: sipush 8192
    //   80: newarray <illegal type>
    //   82: astore_2
    //   83: aload_0
    //   84: aload_2
    //   85: invokevirtual 473	java/io/InputStream:read	([B)I
    //   88: istore 4
    //   90: iload 4
    //   92: ifle +14 -> 106
    //   95: aload_3
    //   96: aload_2
    //   97: iconst_0
    //   98: iload 4
    //   100: invokevirtual 476	java/io/FileOutputStream:write	([BII)V
    //   103: goto -20 -> 83
    //   106: aload_3
    //   107: invokevirtual 227	java/io/FileOutputStream:close	()V
    //   110: aload 7
    //   112: aload_1
    //   113: invokevirtual 479	java/util/zip/ZipEntry:getSize	()J
    //   116: aload_1
    //   117: invokevirtual 482	java/util/zip/ZipEntry:getTime	()J
    //   120: aload_1
    //   121: invokevirtual 485	java/util/zip/ZipEntry:getCrc	()J
    //   124: invokestatic 487	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/lang/String;JJJ)Z
    //   127: ifeq +38 -> 165
    //   130: new 69	java/lang/StringBuilder
    //   133: dup
    //   134: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   137: astore_0
    //   138: aload_0
    //   139: ldc_w 489
    //   142: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: aload_0
    //   147: aload 7
    //   149: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: pop
    //   153: ldc_w 291
    //   156: aload_0
    //   157: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: invokestatic 250	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   163: iconst_0
    //   164: ireturn
    //   165: aload 6
    //   167: aload_1
    //   168: invokevirtual 482	java/util/zip/ZipEntry:getTime	()J
    //   171: invokevirtual 493	java/io/File:setLastModified	(J)Z
    //   174: ifne +36 -> 210
    //   177: new 69	java/lang/StringBuilder
    //   180: dup
    //   181: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   184: astore_0
    //   185: aload_0
    //   186: ldc_w 495
    //   189: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload_0
    //   194: aload 6
    //   196: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: ldc_w 291
    //   203: aload_0
    //   204: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   207: invokestatic 250	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   210: iconst_1
    //   211: ireturn
    //   212: astore_0
    //   213: aload_3
    //   214: astore_2
    //   215: goto +111 -> 326
    //   218: astore_1
    //   219: aload_3
    //   220: astore_0
    //   221: goto +11 -> 232
    //   224: astore_0
    //   225: goto +101 -> 326
    //   228: astore_1
    //   229: aload 5
    //   231: astore_0
    //   232: aload_0
    //   233: astore_2
    //   234: new 69	java/lang/StringBuilder
    //   237: dup
    //   238: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   241: astore_3
    //   242: aload_0
    //   243: astore_2
    //   244: aload_3
    //   245: ldc_w 497
    //   248: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: pop
    //   252: aload_0
    //   253: astore_2
    //   254: aload_3
    //   255: aload 6
    //   257: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   260: pop
    //   261: aload_0
    //   262: astore_2
    //   263: ldc_w 291
    //   266: aload_3
    //   267: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   270: aload_1
    //   271: invokestatic 500	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   274: pop
    //   275: aload_0
    //   276: astore_2
    //   277: aload 6
    //   279: invokestatic 256	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)V
    //   282: aload_0
    //   283: astore_2
    //   284: new 69	java/lang/StringBuilder
    //   287: dup
    //   288: invokespecial 70	java/lang/StringBuilder:<init>	()V
    //   291: astore_3
    //   292: aload_0
    //   293: astore_2
    //   294: aload_3
    //   295: ldc_w 497
    //   298: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: pop
    //   302: aload_0
    //   303: astore_2
    //   304: aload_3
    //   305: aload 6
    //   307: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload_0
    //   312: astore_2
    //   313: new 34	java/io/IOException
    //   316: dup
    //   317: aload_3
    //   318: invokevirtual 80	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   321: aload_1
    //   322: invokespecial 503	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   325: athrow
    //   326: aload_2
    //   327: ifnull +7 -> 334
    //   330: aload_2
    //   331: invokevirtual 227	java/io/FileOutputStream:close	()V
    //   334: aload_0
    //   335: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	336	0	paramInputStream	InputStream
    //   0	336	1	paramZipEntry	ZipEntry
    //   0	336	2	paramString1	String
    //   0	336	3	paramString2	String
    //   88	11	4	i	int
    //   19	211	5	localStringBuilder	StringBuilder
    //   60	246	6	localFile	File
    //   49	99	7	str	String
    // Exception table:
    //   from	to	target	type
    //   77	83	212	finally
    //   83	90	212	finally
    //   95	103	212	finally
    //   77	83	218	java/io/IOException
    //   83	90	218	java/io/IOException
    //   95	103	218	java/io/IOException
    //   67	77	224	finally
    //   234	242	224	finally
    //   244	252	224	finally
    //   254	261	224	finally
    //   263	275	224	finally
    //   277	282	224	finally
    //   284	292	224	finally
    //   294	302	224	finally
    //   304	311	224	finally
    //   313	326	224	finally
    //   67	77	228	java/io/IOException
  }
  
  public static abstract interface FileComparator
  {
    public abstract boolean equals(File paramFile1, File paramFile2);
  }
  
  public static abstract interface IterateHandler
  {
    public abstract boolean handleEntry(InputStream paramInputStream, ZipEntry paramZipEntry, String paramString)
      throws Exception;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\FileUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */