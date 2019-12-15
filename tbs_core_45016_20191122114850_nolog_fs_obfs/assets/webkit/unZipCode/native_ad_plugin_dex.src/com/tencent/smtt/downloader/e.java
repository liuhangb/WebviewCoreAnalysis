package com.tencent.smtt.downloader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.tencent.smtt.downloader.utils.FileUtil;
import com.tencent.smtt.downloader.utils.h;
import com.tencent.smtt.downloader.utils.i;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class e
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static e jdField_a_of_type_ComTencentSmttDownloaderE;
  static final FileFilter jdField_a_of_type_JavaIoFileFilter = new FileFilter()
  {
    public boolean accept(File paramAnonymousFile)
    {
      paramAnonymousFile = paramAnonymousFile.getName();
      if (paramAnonymousFile == null) {
        return false;
      }
      if (paramAnonymousFile.endsWith(".jar_is_first_load_dex_flag_file")) {
        return false;
      }
      if ((Build.VERSION.SDK_INT >= 21) && (paramAnonymousFile.endsWith(".dex"))) {
        return false;
      }
      if ((Build.VERSION.SDK_INT >= 26) && (paramAnonymousFile.endsWith(".prof"))) {
        return false;
      }
      return (Build.VERSION.SDK_INT < 26) || (!paramAnonymousFile.equals("oat"));
    }
  };
  public static ThreadLocal<Integer> a;
  private static final Lock jdField_a_of_type_JavaUtilConcurrentLocksLock;
  private static final ReentrantLock jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock = new ReentrantLock();
  static boolean jdField_a_of_type_Boolean;
  private static final Long[][] jdField_a_of_type_Array2dOfJavaLangLong;
  private static int jdField_b_of_type_Int = 0;
  private static FileLock jdField_b_of_type_JavaNioChannelsFileLock;
  private static boolean d = false;
  private int jdField_a_of_type_Int = 0;
  private FileOutputStream jdField_a_of_type_JavaIoFileOutputStream;
  private FileLock jdField_a_of_type_JavaNioChannelsFileLock;
  private boolean jdField_b_of_type_Boolean = false;
  private boolean c = false;
  
  static
  {
    jdField_a_of_type_JavaUtilConcurrentLocksLock = new ReentrantLock();
    jdField_b_of_type_JavaNioChannelsFileLock = null;
    jdField_a_of_type_JavaLangThreadLocal = new ThreadLocal()
    {
      public Integer a()
      {
        return Integer.valueOf(0);
      }
    };
    jdField_a_of_type_AndroidOsHandler = null;
    jdField_a_of_type_Array2dOfJavaLangLong = new Long[][] { { Long.valueOf(44006L), Long.valueOf(39094008L) }, { Long.valueOf(44005L), Long.valueOf(39094008L) }, { Long.valueOf(43910L), Long.valueOf(38917816L) }, { Long.valueOf(44027L), Long.valueOf(39094008L) }, { Long.valueOf(44028L), Long.valueOf(39094008L) }, { Long.valueOf(44029L), Long.valueOf(39094008L) }, { Long.valueOf(44030L), Long.valueOf(39094008L) }, { Long.valueOf(44032L), Long.valueOf(39094008L) }, { Long.valueOf(44033L), Long.valueOf(39094008L) }, { Long.valueOf(44034L), Long.valueOf(39094008L) }, { Long.valueOf(43909L), Long.valueOf(38917816L) } };
    jdField_a_of_type_Boolean = false;
  }
  
  private e()
  {
    if (jdField_a_of_type_AndroidOsHandler == null) {
      jdField_a_of_type_AndroidOsHandler = new Handler(d.a().getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default: 
            return;
          case 4: 
            h.a("TbsInstaller", "TbsInstaller--handleMessage--MSG_UNZIP_TBS_CORE");
            Object[] arrayOfObject = (Object[])paramAnonymousMessage.obj;
            a.a(false);
            super.handleMessage(paramAnonymousMessage);
            return;
          case 3: 
            h.a("TbsInstaller", "TbsInstaller--handleMessage--MSG_INSTALL_TBS_CORE_EX");
            paramAnonymousMessage = (Object[])paramAnonymousMessage.obj;
            return;
          case 2: 
            h.a("TbsInstaller", "TbsInstaller--handleMessage--MSG_COPY_TBS_CORE");
            paramAnonymousMessage = (Object[])paramAnonymousMessage.obj;
            return;
          }
          h.a("TbsInstaller", "TbsInstaller--handleMessage--MSG_INSTALL_TBS_CORE");
          paramAnonymousMessage = (Object[])paramAnonymousMessage.obj;
          e.a(e.this, (Context)paramAnonymousMessage[0], (String)paramAnonymousMessage[1], ((Integer)paramAnonymousMessage[2]).intValue());
        }
      };
    }
  }
  
  static e a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttDownloaderE == null) {
        try
        {
          if (jdField_a_of_type_ComTencentSmttDownloaderE == null) {
            jdField_a_of_type_ComTencentSmttDownloaderE = new e();
          }
        }
        finally {}
      }
      e locale = jdField_a_of_type_ComTencentSmttDownloaderE;
      return locale;
    }
    finally {}
  }
  
  private static void a(Context paramContext, TbsLinuxToolsJni paramTbsLinuxToolsJni, File paramFile)
  {
    h.a("TbsInstaller", "shareAllDirsAndFiles #1");
    if ((paramFile != null) && (paramFile.exists()))
    {
      if (!paramFile.isDirectory()) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("shareAllDirsAndFiles dir is ");
      localStringBuilder.append(paramFile.getAbsolutePath());
      h.a("TbsInstaller", localStringBuilder.toString());
      paramTbsLinuxToolsJni.a(paramFile.getAbsolutePath(), "755");
      paramFile = paramFile.listFiles();
      int j = paramFile.length;
      int i = 0;
      while (i < j)
      {
        localStringBuilder = paramFile[i];
        if (localStringBuilder.isFile())
        {
          if (localStringBuilder.getAbsolutePath().indexOf(".so") > 0) {
            paramTbsLinuxToolsJni.a(localStringBuilder.getAbsolutePath(), "755");
          } else {
            paramTbsLinuxToolsJni.a(localStringBuilder.getAbsolutePath(), "644");
          }
        }
        else if (localStringBuilder.isDirectory()) {
          a(paramContext, paramTbsLinuxToolsJni, localStringBuilder);
        } else {
          h.b("TbsInstaller", "unknown file type.", true);
        }
        i += 1;
      }
      return;
    }
  }
  
  private void a(Context paramContext, boolean paramBoolean)
  {
    if (paramContext == null)
    {
      i.a(paramContext).a(225, "setTmpFolderCoreToRead context is null");
      return;
    }
    try
    {
      File localFile = new File(a.a(paramContext), "tmp_folder_core_to_read.conf");
      if (paramBoolean)
      {
        if (localFile.exists()) {
          return;
        }
        localFile.createNewFile();
        return;
      }
      FileUtil.a(localFile);
      return;
    }
    catch (Exception localException)
    {
      paramContext = i.a(paramContext);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setTmpFolderCoreToRead Exception message is ");
      localStringBuilder.append(localException.getMessage());
      localStringBuilder.append(" Exception cause is ");
      localStringBuilder.append(localException.getCause());
      paramContext.a(225, localStringBuilder.toString());
    }
  }
  
  private boolean a(Context paramContext, File paramFile)
  {
    return a(paramContext, paramFile, false);
  }
  
  /* Error */
  private boolean a(Context paramContext, File paramFile, boolean paramBoolean)
  {
    // Byte code:
    //   0: ldc 116
    //   2: ldc -18
    //   4: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: aload_2
    //   8: invokestatic 241	com/tencent/smtt/downloader/utils/FileUtil:b	(Ljava/io/File;)Z
    //   11: ifne +27 -> 38
    //   14: aload_1
    //   15: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   18: sipush 204
    //   21: ldc -13
    //   23: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   26: aload_1
    //   27: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   30: sipush 65016
    //   33: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   36: iconst_0
    //   37: ireturn
    //   38: aload_1
    //   39: invokestatic 197	com/tencent/smtt/downloader/a:a	(Landroid/content/Context;)Ljava/io/File;
    //   42: astore 9
    //   44: iload_3
    //   45: ifeq +19 -> 64
    //   48: new 125	java/io/File
    //   51: dup
    //   52: aload 9
    //   54: ldc -3
    //   56: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   59: astore 9
    //   61: goto +16 -> 77
    //   64: new 125	java/io/File
    //   67: dup
    //   68: aload 9
    //   70: ldc -1
    //   72: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   75: astore 9
    //   77: aload 9
    //   79: invokevirtual 129	java/io/File:exists	()Z
    //   82: ifeq +59 -> 141
    //   85: aload_1
    //   86: invokestatic 260	com/tencent/smtt/downloader/TbsDownloader:a	(Landroid/content/Context;)Z
    //   89: ifne +52 -> 141
    //   92: aload 9
    //   94: invokestatic 210	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)V
    //   97: goto +44 -> 141
    //   100: astore 9
    //   102: new 134	java/lang/StringBuilder
    //   105: dup
    //   106: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   109: astore 10
    //   111: aload 10
    //   113: ldc_w 262
    //   116: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: pop
    //   120: aload 10
    //   122: aload 9
    //   124: invokestatic 268	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   127: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: ldc 116
    //   133: aload 10
    //   135: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   138: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   141: iload_3
    //   142: ifeq +14 -> 156
    //   145: aload_0
    //   146: aload_1
    //   147: iconst_2
    //   148: invokevirtual 273	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;I)Ljava/io/File;
    //   151: astore 9
    //   153: goto +11 -> 164
    //   156: aload_0
    //   157: aload_1
    //   158: iconst_0
    //   159: invokevirtual 273	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;I)Ljava/io/File;
    //   162: astore 9
    //   164: aload 9
    //   166: ifnonnull +28 -> 194
    //   169: aload_1
    //   170: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   173: sipush 205
    //   176: ldc_w 275
    //   179: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   182: aload_1
    //   183: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   186: sipush 65015
    //   189: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   192: iconst_0
    //   193: ireturn
    //   194: iconst_1
    //   195: istore 6
    //   197: iconst_1
    //   198: istore 5
    //   200: aload 9
    //   202: invokestatic 277	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)Z
    //   205: pop
    //   206: iload_3
    //   207: ifeq +9 -> 216
    //   210: aload 9
    //   212: iconst_1
    //   213: invokestatic 280	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;Z)V
    //   216: aload_2
    //   217: aload 9
    //   219: invokestatic 283	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;Ljava/io/File;)Z
    //   222: istore 8
    //   224: iload 8
    //   226: istore 7
    //   228: iload 8
    //   230: ifeq +12 -> 242
    //   233: aload_0
    //   234: aload 9
    //   236: aload_1
    //   237: invokespecial 286	com/tencent/smtt/downloader/e:a	(Ljava/io/File;Landroid/content/Context;)Z
    //   240: istore 7
    //   242: iload_3
    //   243: ifeq +84 -> 327
    //   246: aload 9
    //   248: invokevirtual 290	java/io/File:list	()[Ljava/lang/String;
    //   251: astore_2
    //   252: iconst_0
    //   253: istore 4
    //   255: iload 4
    //   257: aload_2
    //   258: arraylength
    //   259: if_icmpge +47 -> 306
    //   262: new 125	java/io/File
    //   265: dup
    //   266: aload 9
    //   268: aload_2
    //   269: iload 4
    //   271: aaload
    //   272: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   275: astore 10
    //   277: aload 10
    //   279: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   282: ldc_w 295
    //   285: invokevirtual 299	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   288: ifeq +9 -> 297
    //   291: aload 10
    //   293: invokevirtual 302	java/io/File:delete	()Z
    //   296: pop
    //   297: iload 4
    //   299: iconst_1
    //   300: iadd
    //   301: istore 4
    //   303: goto -48 -> 255
    //   306: new 125	java/io/File
    //   309: dup
    //   310: aload_1
    //   311: invokestatic 304	com/tencent/smtt/downloader/e:d	(Landroid/content/Context;)Ljava/io/File;
    //   314: ldc_w 306
    //   317: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   320: invokevirtual 302	java/io/File:delete	()Z
    //   323: pop
    //   324: goto +3 -> 327
    //   327: iload 7
    //   329: ifne +56 -> 385
    //   332: aload 9
    //   334: invokestatic 210	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)V
    //   337: aload_1
    //   338: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   341: sipush 65014
    //   344: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   347: new 134	java/lang/StringBuilder
    //   350: dup
    //   351: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   354: astore_2
    //   355: aload_2
    //   356: ldc_w 308
    //   359: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: pop
    //   363: aload_2
    //   364: aload 9
    //   366: invokevirtual 129	java/io/File:exists	()Z
    //   369: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   372: pop
    //   373: ldc 116
    //   375: aload_2
    //   376: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   379: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   382: goto +31 -> 413
    //   385: aload_0
    //   386: aload_1
    //   387: iconst_1
    //   388: invokespecial 313	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;Z)V
    //   391: iload_3
    //   392: ifeq +21 -> 413
    //   395: aload_0
    //   396: aload_1
    //   397: invokevirtual 314	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)Ljava/io/File;
    //   400: astore_2
    //   401: aload_2
    //   402: iconst_1
    //   403: invokestatic 280	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;Z)V
    //   406: aload 9
    //   408: aload_2
    //   409: invokevirtual 317	java/io/File:renameTo	(Ljava/io/File;)Z
    //   412: pop
    //   413: ldc 116
    //   415: ldc_w 319
    //   418: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   421: iload 7
    //   423: ireturn
    //   424: astore_1
    //   425: goto +293 -> 718
    //   428: astore_2
    //   429: aload_1
    //   430: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   433: sipush 65013
    //   436: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   439: aload_1
    //   440: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   443: sipush 207
    //   446: aload_2
    //   447: invokevirtual 322	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/Throwable;)V
    //   450: aload 9
    //   452: ifnull +20 -> 472
    //   455: aload 9
    //   457: invokevirtual 129	java/io/File:exists	()Z
    //   460: istore_3
    //   461: iload_3
    //   462: ifeq +10 -> 472
    //   465: iload 5
    //   467: istore 4
    //   469: goto +6 -> 475
    //   472: iconst_0
    //   473: istore 4
    //   475: iload 4
    //   477: ifeq +86 -> 563
    //   480: aload 9
    //   482: ifnull +81 -> 563
    //   485: aload 9
    //   487: invokestatic 210	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)V
    //   490: new 134	java/lang/StringBuilder
    //   493: dup
    //   494: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   497: astore_1
    //   498: aload_1
    //   499: ldc_w 324
    //   502: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   505: pop
    //   506: aload_1
    //   507: aload 9
    //   509: invokevirtual 129	java/io/File:exists	()Z
    //   512: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   515: pop
    //   516: ldc 116
    //   518: aload_1
    //   519: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   522: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   525: goto +38 -> 563
    //   528: astore_1
    //   529: new 134	java/lang/StringBuilder
    //   532: dup
    //   533: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   536: astore_2
    //   537: aload_2
    //   538: ldc_w 326
    //   541: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   544: pop
    //   545: aload_2
    //   546: aload_1
    //   547: invokestatic 268	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   550: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   553: pop
    //   554: ldc 116
    //   556: aload_2
    //   557: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   560: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   563: ldc 116
    //   565: ldc_w 319
    //   568: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   571: iconst_0
    //   572: ireturn
    //   573: astore_2
    //   574: aload_1
    //   575: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   578: sipush 65013
    //   581: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   584: aload_1
    //   585: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   588: sipush 206
    //   591: aload_2
    //   592: invokevirtual 322	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/Throwable;)V
    //   595: aload 9
    //   597: ifnull +20 -> 617
    //   600: aload 9
    //   602: invokevirtual 129	java/io/File:exists	()Z
    //   605: istore_3
    //   606: iload_3
    //   607: ifeq +10 -> 617
    //   610: iload 6
    //   612: istore 4
    //   614: goto +6 -> 620
    //   617: iconst_0
    //   618: istore 4
    //   620: iload 4
    //   622: ifeq +86 -> 708
    //   625: aload 9
    //   627: ifnull +81 -> 708
    //   630: aload 9
    //   632: invokestatic 210	com/tencent/smtt/downloader/utils/FileUtil:a	(Ljava/io/File;)V
    //   635: new 134	java/lang/StringBuilder
    //   638: dup
    //   639: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   642: astore_1
    //   643: aload_1
    //   644: ldc_w 324
    //   647: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   650: pop
    //   651: aload_1
    //   652: aload 9
    //   654: invokevirtual 129	java/io/File:exists	()Z
    //   657: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   660: pop
    //   661: ldc 116
    //   663: aload_1
    //   664: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   667: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   670: goto +38 -> 708
    //   673: astore_1
    //   674: new 134	java/lang/StringBuilder
    //   677: dup
    //   678: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   681: astore_2
    //   682: aload_2
    //   683: ldc_w 326
    //   686: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   689: pop
    //   690: aload_2
    //   691: aload_1
    //   692: invokestatic 268	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   695: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   698: pop
    //   699: ldc 116
    //   701: aload_2
    //   702: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   705: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   708: ldc 116
    //   710: ldc_w 319
    //   713: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   716: iconst_0
    //   717: ireturn
    //   718: ldc 116
    //   720: ldc_w 319
    //   723: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   726: aload_1
    //   727: athrow
    //   728: astore_2
    //   729: goto -402 -> 327
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	732	0	this	e
    //   0	732	1	paramContext	Context
    //   0	732	2	paramFile	File
    //   0	732	3	paramBoolean	boolean
    //   253	368	4	i	int
    //   198	268	5	j	int
    //   195	416	6	k	int
    //   226	196	7	bool1	boolean
    //   222	7	8	bool2	boolean
    //   42	51	9	localFile1	File
    //   100	23	9	localThrowable	Throwable
    //   151	502	9	localFile2	File
    //   109	183	10	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   38	44	100	java/lang/Throwable
    //   48	61	100	java/lang/Throwable
    //   64	77	100	java/lang/Throwable
    //   77	97	100	java/lang/Throwable
    //   200	206	424	finally
    //   210	216	424	finally
    //   216	224	424	finally
    //   233	242	424	finally
    //   246	252	424	finally
    //   255	297	424	finally
    //   306	324	424	finally
    //   332	382	424	finally
    //   385	391	424	finally
    //   395	413	424	finally
    //   429	450	424	finally
    //   455	461	424	finally
    //   574	595	424	finally
    //   600	606	424	finally
    //   200	206	428	java/lang/Exception
    //   210	216	428	java/lang/Exception
    //   216	224	428	java/lang/Exception
    //   233	242	428	java/lang/Exception
    //   246	252	428	java/lang/Exception
    //   255	297	428	java/lang/Exception
    //   332	382	428	java/lang/Exception
    //   385	391	428	java/lang/Exception
    //   395	413	428	java/lang/Exception
    //   485	525	528	java/lang/Throwable
    //   200	206	573	java/io/IOException
    //   210	216	573	java/io/IOException
    //   216	224	573	java/io/IOException
    //   233	242	573	java/io/IOException
    //   246	252	573	java/io/IOException
    //   255	297	573	java/io/IOException
    //   306	324	573	java/io/IOException
    //   332	382	573	java/io/IOException
    //   385	391	573	java/io/IOException
    //   395	413	573	java/io/IOException
    //   630	670	673	java/lang/Throwable
    //   306	324	728	java/lang/Exception
  }
  
  /* Error */
  private boolean a(File paramFile, Context paramContext)
  {
    // Byte code:
    //   0: new 134	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: ldc_w 328
    //   14: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload 6
    //   20: aload_1
    //   21: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload 6
    //   27: ldc_w 330
    //   30: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: aload 6
    //   36: aload_2
    //   37: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: ldc 116
    //   43: aload 6
    //   45: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   48: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   51: aconst_null
    //   52: astore 7
    //   54: aconst_null
    //   55: astore 9
    //   57: aconst_null
    //   58: astore 6
    //   60: aload 7
    //   62: astore_2
    //   63: new 125	java/io/File
    //   66: dup
    //   67: aload_1
    //   68: ldc_w 332
    //   71: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   74: astore 10
    //   76: aload 7
    //   78: astore_2
    //   79: new 334	java/util/Properties
    //   82: dup
    //   83: invokespecial 335	java/util/Properties:<init>	()V
    //   86: astore 8
    //   88: aload 7
    //   90: astore_2
    //   91: aload 10
    //   93: invokevirtual 129	java/io/File:exists	()Z
    //   96: ifeq +62 -> 158
    //   99: aload 7
    //   101: astore_2
    //   102: new 337	java/io/BufferedInputStream
    //   105: dup
    //   106: new 339	java/io/FileInputStream
    //   109: dup
    //   110: aload 10
    //   112: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   115: invokespecial 344	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   118: astore 6
    //   120: aload 8
    //   122: aload 6
    //   124: invokevirtual 347	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   127: aload 6
    //   129: astore_2
    //   130: iconst_1
    //   131: istore 4
    //   133: goto +31 -> 164
    //   136: astore_1
    //   137: aload 6
    //   139: astore_2
    //   140: goto +524 -> 664
    //   143: astore 7
    //   145: aload 6
    //   147: astore_2
    //   148: aload 8
    //   150: astore 6
    //   152: aload_2
    //   153: astore 8
    //   155: goto +78 -> 233
    //   158: iconst_0
    //   159: istore 4
    //   161: aload 6
    //   163: astore_2
    //   164: iload 4
    //   166: istore 5
    //   168: aload 8
    //   170: astore 6
    //   172: aload_2
    //   173: ifnull +89 -> 262
    //   176: aload_2
    //   177: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   180: iload 4
    //   182: istore 5
    //   184: aload 8
    //   186: astore 6
    //   188: goto +74 -> 262
    //   191: astore_2
    //   192: aload_2
    //   193: invokevirtual 353	java/io/IOException:printStackTrace	()V
    //   196: iload 4
    //   198: istore 5
    //   200: aload 8
    //   202: astore 6
    //   204: goto +58 -> 262
    //   207: astore 7
    //   209: aload 8
    //   211: astore 6
    //   213: aload 9
    //   215: astore 8
    //   217: goto +16 -> 233
    //   220: astore_1
    //   221: goto +443 -> 664
    //   224: astore 7
    //   226: aconst_null
    //   227: astore 6
    //   229: aload 9
    //   231: astore 8
    //   233: aload 8
    //   235: astore_2
    //   236: aload 7
    //   238: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   241: aload 8
    //   243: ifnull +16 -> 259
    //   246: aload 8
    //   248: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   251: goto +8 -> 259
    //   254: astore_2
    //   255: aload_2
    //   256: invokevirtual 353	java/io/IOException:printStackTrace	()V
    //   259: iconst_1
    //   260: istore 5
    //   262: new 134	java/lang/StringBuilder
    //   265: dup
    //   266: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   269: astore_2
    //   270: aload_2
    //   271: ldc_w 356
    //   274: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: pop
    //   278: aload_2
    //   279: iload 5
    //   281: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   284: pop
    //   285: ldc 116
    //   287: aload_2
    //   288: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   294: iload 5
    //   296: ifeq +303 -> 599
    //   299: aload_1
    //   300: invokevirtual 159	java/io/File:listFiles	()[Ljava/io/File;
    //   303: astore_2
    //   304: iconst_0
    //   305: istore_3
    //   306: iload_3
    //   307: aload_2
    //   308: arraylength
    //   309: if_icmpge +284 -> 593
    //   312: aload_2
    //   313: iload_3
    //   314: aaload
    //   315: astore_1
    //   316: ldc_w 332
    //   319: aload_1
    //   320: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   323: invokevirtual 360	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   326: ifne +213 -> 539
    //   329: aload_1
    //   330: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   333: ldc_w 295
    //   336: invokevirtual 299	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   339: ifne +200 -> 539
    //   342: ldc_w 362
    //   345: aload_1
    //   346: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   349: invokevirtual 360	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   352: ifne +187 -> 539
    //   355: aload_1
    //   356: invokevirtual 132	java/io/File:isDirectory	()Z
    //   359: ifne +180 -> 539
    //   362: aload_1
    //   363: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   366: ldc_w 364
    //   369: invokevirtual 299	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   372: ifeq +6 -> 378
    //   375: goto +164 -> 539
    //   378: aload_1
    //   379: invokestatic 369	com/tencent/smtt/downloader/utils/ApkUtil:a	(Ljava/io/File;)Ljava/lang/String;
    //   382: astore 7
    //   384: aload 6
    //   386: aload_1
    //   387: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   390: ldc_w 371
    //   393: invokevirtual 375	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   396: astore 8
    //   398: aload 8
    //   400: ldc_w 371
    //   403: invokevirtual 360	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   406: ifne +63 -> 469
    //   409: aload 8
    //   411: aload 7
    //   413: invokevirtual 360	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   416: ifeq +53 -> 469
    //   419: new 134	java/lang/StringBuilder
    //   422: dup
    //   423: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   426: astore 7
    //   428: aload 7
    //   430: ldc_w 377
    //   433: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   436: pop
    //   437: aload 7
    //   439: aload_1
    //   440: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   443: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   446: pop
    //   447: aload 7
    //   449: ldc_w 379
    //   452: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   455: pop
    //   456: ldc 116
    //   458: aload 7
    //   460: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   463: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   466: goto +120 -> 586
    //   469: new 134	java/lang/StringBuilder
    //   472: dup
    //   473: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   476: astore_2
    //   477: aload_2
    //   478: ldc_w 381
    //   481: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: pop
    //   485: aload_2
    //   486: aload_1
    //   487: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   490: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   493: pop
    //   494: aload_2
    //   495: ldc_w 383
    //   498: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: pop
    //   502: aload_2
    //   503: aload 8
    //   505: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   508: pop
    //   509: aload_2
    //   510: ldc_w 385
    //   513: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   516: pop
    //   517: aload_2
    //   518: aload 7
    //   520: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   523: pop
    //   524: ldc 116
    //   526: aload_2
    //   527: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   530: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   533: iconst_0
    //   534: istore 4
    //   536: goto +66 -> 602
    //   539: new 134	java/lang/StringBuilder
    //   542: dup
    //   543: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   546: astore 7
    //   548: aload 7
    //   550: ldc_w 387
    //   553: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   556: pop
    //   557: aload 7
    //   559: aload_1
    //   560: invokevirtual 293	java/io/File:getName	()Ljava/lang/String;
    //   563: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   566: pop
    //   567: aload 7
    //   569: ldc_w 389
    //   572: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   575: pop
    //   576: ldc 116
    //   578: aload 7
    //   580: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   583: invokestatic 391	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   586: iload_3
    //   587: iconst_1
    //   588: iadd
    //   589: istore_3
    //   590: goto -284 -> 306
    //   593: iconst_1
    //   594: istore 4
    //   596: goto +6 -> 602
    //   599: iconst_1
    //   600: istore 4
    //   602: new 134	java/lang/StringBuilder
    //   605: dup
    //   606: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   609: astore_1
    //   610: aload_1
    //   611: ldc_w 393
    //   614: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   617: pop
    //   618: aload_1
    //   619: iload 4
    //   621: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   624: pop
    //   625: ldc 116
    //   627: aload_1
    //   628: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   631: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   634: iload 5
    //   636: ifeq +18 -> 654
    //   639: iload 4
    //   641: ifne +13 -> 654
    //   644: ldc 116
    //   646: ldc_w 395
    //   649: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   652: iconst_0
    //   653: ireturn
    //   654: ldc 116
    //   656: ldc_w 397
    //   659: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   662: iconst_1
    //   663: ireturn
    //   664: aload_2
    //   665: ifnull +15 -> 680
    //   668: aload_2
    //   669: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   672: goto +8 -> 680
    //   675: astore_2
    //   676: aload_2
    //   677: invokevirtual 353	java/io/IOException:printStackTrace	()V
    //   680: aload_1
    //   681: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	682	0	this	e
    //   0	682	1	paramFile	File
    //   0	682	2	paramContext	Context
    //   305	285	3	i	int
    //   131	509	4	bool1	boolean
    //   166	469	5	bool2	boolean
    //   7	378	6	localObject1	Object
    //   52	48	7	localObject2	Object
    //   143	1	7	localException1	Exception
    //   207	1	7	localException2	Exception
    //   224	13	7	localException3	Exception
    //   382	197	7	localObject3	Object
    //   86	418	8	localObject4	Object
    //   55	175	9	localObject5	Object
    //   74	37	10	localFile	File
    // Exception table:
    //   from	to	target	type
    //   120	127	136	finally
    //   120	127	143	java/lang/Exception
    //   176	180	191	java/io/IOException
    //   91	99	207	java/lang/Exception
    //   102	120	207	java/lang/Exception
    //   63	76	220	finally
    //   79	88	220	finally
    //   91	99	220	finally
    //   102	120	220	finally
    //   236	241	220	finally
    //   63	76	224	java/lang/Exception
    //   79	88	224	java/lang/Exception
    //   246	251	254	java/io/IOException
    //   668	672	675	java/io/IOException
  }
  
  static void b(Context paramContext)
  {
    h.a("TbsInstaller", "shareTbsCore #1");
    try
    {
      localObject = new TbsLinuxToolsJni(paramContext);
      a(paramContext, (TbsLinuxToolsJni)localObject, a().b(paramContext));
      paramContext = a().c(paramContext);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("shareTbsCore tbsShareDir is ");
      localStringBuilder.append(paramContext.getAbsolutePath());
      h.a("TbsInstaller", localStringBuilder.toString());
      ((TbsLinuxToolsJni)localObject).a(paramContext.getAbsolutePath(), "755");
      return;
    }
    catch (Throwable paramContext)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("shareTbsCore tbsShareDir error is ");
      ((StringBuilder)localObject).append(paramContext.getMessage());
      ((StringBuilder)localObject).append(" ## ");
      ((StringBuilder)localObject).append(paramContext.getCause());
      h.a("TbsInstaller", ((StringBuilder)localObject).toString());
      paramContext.printStackTrace();
    }
  }
  
  /* Error */
  @android.annotation.TargetApi(11)
  private void b(Context paramContext, String paramString, int paramInt)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4: sipush 65035
    //   7: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   10: aload_0
    //   11: aload_1
    //   12: invokevirtual 421	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)Z
    //   15: istore 10
    //   17: iconst_1
    //   18: istore 7
    //   20: iload 10
    //   22: ifeq +23 -> 45
    //   25: ldc 116
    //   27: ldc_w 423
    //   30: iconst_1
    //   31: invokestatic 425	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   34: aload_1
    //   35: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   38: sipush 65034
    //   41: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   44: return
    //   45: new 134	java/lang/StringBuilder
    //   48: dup
    //   49: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   52: astore 15
    //   54: aload 15
    //   56: ldc_w 427
    //   59: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload 15
    //   65: aload_2
    //   66: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: pop
    //   70: ldc 116
    //   72: aload 15
    //   74: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   77: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   80: new 134	java/lang/StringBuilder
    //   83: dup
    //   84: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   87: astore 15
    //   89: aload 15
    //   91: ldc_w 429
    //   94: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: pop
    //   98: aload 15
    //   100: iload_3
    //   101: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   104: pop
    //   105: ldc 116
    //   107: aload 15
    //   109: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   112: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   115: new 134	java/lang/StringBuilder
    //   118: dup
    //   119: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   122: astore 15
    //   124: aload 15
    //   126: ldc_w 434
    //   129: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload 15
    //   135: aload_1
    //   136: invokevirtual 440	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   139: getfield 446	android/content/pm/ApplicationInfo:processName	Ljava/lang/String;
    //   142: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: pop
    //   146: ldc 116
    //   148: aload 15
    //   150: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   156: new 134	java/lang/StringBuilder
    //   159: dup
    //   160: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   163: astore 15
    //   165: aload 15
    //   167: ldc_w 448
    //   170: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: pop
    //   174: aload 15
    //   176: invokestatic 454	android/os/Process:myPid	()I
    //   179: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: ldc 116
    //   185: aload 15
    //   187: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   190: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   193: new 134	java/lang/StringBuilder
    //   196: dup
    //   197: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   200: astore 15
    //   202: aload 15
    //   204: ldc_w 456
    //   207: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   210: pop
    //   211: aload 15
    //   213: invokestatic 462	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   216: invokevirtual 463	java/lang/Thread:getName	()Ljava/lang/String;
    //   219: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: ldc 116
    //   225: aload 15
    //   227: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   230: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   233: getstatic 468	android/os/Build$VERSION:SDK_INT	I
    //   236: istore 4
    //   238: iconst_0
    //   239: istore 8
    //   241: iload 4
    //   243: bipush 11
    //   245: if_icmplt +16 -> 261
    //   248: aload_1
    //   249: ldc_w 470
    //   252: iconst_4
    //   253: invokevirtual 474	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   256: astore 15
    //   258: goto +13 -> 271
    //   261: aload_1
    //   262: ldc_w 470
    //   265: iconst_0
    //   266: invokevirtual 474	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   269: astore 15
    //   271: aload 15
    //   273: ldc_w 476
    //   276: iconst_m1
    //   277: invokeinterface 482 3 0
    //   282: iload_3
    //   283: if_icmpne +53 -> 336
    //   286: new 134	java/lang/StringBuilder
    //   289: dup
    //   290: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   293: astore_2
    //   294: aload_2
    //   295: ldc_w 484
    //   298: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: pop
    //   302: aload_2
    //   303: iload_3
    //   304: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload_2
    //   309: ldc_w 486
    //   312: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   315: pop
    //   316: ldc 116
    //   318: aload_2
    //   319: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   322: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   325: aload_1
    //   326: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   329: sipush 65033
    //   332: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   335: return
    //   336: aload_1
    //   337: invokestatic 487	com/tencent/smtt/downloader/utils/FileUtil:a	(Landroid/content/Context;)Z
    //   340: ifne +82 -> 422
    //   343: invokestatic 492	com/tencent/smtt/downloader/utils/j:a	()J
    //   346: lstore 11
    //   348: aload_1
    //   349: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   352: invokevirtual 494	com/tencent/smtt/downloader/c:c	()J
    //   355: lstore 13
    //   357: aload_1
    //   358: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   361: sipush 65032
    //   364: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   367: aload_1
    //   368: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   371: astore_1
    //   372: new 134	java/lang/StringBuilder
    //   375: dup
    //   376: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   379: astore_2
    //   380: aload_2
    //   381: ldc_w 496
    //   384: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   387: pop
    //   388: aload_2
    //   389: lload 11
    //   391: invokevirtual 499	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   394: pop
    //   395: aload_2
    //   396: ldc_w 501
    //   399: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: pop
    //   403: aload_2
    //   404: lload 13
    //   406: invokevirtual 499	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   409: pop
    //   410: aload_1
    //   411: sipush 210
    //   414: aload_2
    //   415: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   418: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   421: return
    //   422: aload_0
    //   423: aload_1
    //   424: invokevirtual 503	com/tencent/smtt/downloader/e:c	(Landroid/content/Context;)Z
    //   427: ifne +14 -> 441
    //   430: aload_1
    //   431: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   434: sipush 65031
    //   437: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   440: return
    //   441: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   444: invokeinterface 508 1 0
    //   449: istore 10
    //   451: new 134	java/lang/StringBuilder
    //   454: dup
    //   455: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   458: astore 15
    //   460: aload 15
    //   462: ldc_w 510
    //   465: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   468: pop
    //   469: aload 15
    //   471: iload 10
    //   473: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   476: pop
    //   477: ldc 116
    //   479: aload 15
    //   481: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   484: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   487: iload 10
    //   489: ifeq +1563 -> 2052
    //   492: aload_1
    //   493: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   496: sipush 65029
    //   499: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   502: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   505: invokevirtual 513	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   508: aload_1
    //   509: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   512: ldc_w 520
    //   515: invokevirtual 522	com/tencent/smtt/downloader/b:b	(Ljava/lang/String;)I
    //   518: istore 4
    //   520: aload_1
    //   521: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   524: invokevirtual 524	com/tencent/smtt/downloader/b:a	()I
    //   527: istore 5
    //   529: new 134	java/lang/StringBuilder
    //   532: dup
    //   533: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   536: astore 15
    //   538: aload 15
    //   540: ldc_w 526
    //   543: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   546: pop
    //   547: aload 15
    //   549: iload 4
    //   551: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   554: pop
    //   555: ldc 116
    //   557: aload 15
    //   559: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   562: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   565: new 134	java/lang/StringBuilder
    //   568: dup
    //   569: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   572: astore 15
    //   574: aload 15
    //   576: ldc_w 528
    //   579: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   582: pop
    //   583: aload 15
    //   585: iload 5
    //   587: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   590: pop
    //   591: ldc 116
    //   593: aload 15
    //   595: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   598: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   601: new 134	java/lang/StringBuilder
    //   604: dup
    //   605: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   608: astore 15
    //   610: aload 15
    //   612: ldc_w 530
    //   615: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   618: pop
    //   619: aload 15
    //   621: iload_3
    //   622: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   625: pop
    //   626: ldc 116
    //   628: aload 15
    //   630: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   633: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   636: iload 5
    //   638: ifle +1429 -> 2067
    //   641: iload_3
    //   642: iload 5
    //   644: if_icmpgt +6 -> 650
    //   647: goto +1420 -> 2067
    //   650: aload_0
    //   651: aload_1
    //   652: invokevirtual 532	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)V
    //   655: aload_1
    //   656: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   659: invokevirtual 534	com/tencent/smtt/downloader/b:b	()I
    //   662: istore 5
    //   664: aload_0
    //   665: aload_1
    //   666: invokevirtual 537	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)I
    //   669: istore 4
    //   671: new 134	java/lang/StringBuilder
    //   674: dup
    //   675: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   678: astore 15
    //   680: aload 15
    //   682: ldc_w 539
    //   685: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   688: pop
    //   689: aload 15
    //   691: iload 5
    //   693: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   696: pop
    //   697: ldc 116
    //   699: aload 15
    //   701: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   704: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   707: new 134	java/lang/StringBuilder
    //   710: dup
    //   711: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   714: astore 15
    //   716: aload 15
    //   718: ldc_w 541
    //   721: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   724: pop
    //   725: aload 15
    //   727: iload 4
    //   729: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   732: pop
    //   733: ldc 116
    //   735: aload 15
    //   737: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   740: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   743: iload 5
    //   745: iflt +1336 -> 2081
    //   748: iload 5
    //   750: iconst_2
    //   751: if_icmpge +1330 -> 2081
    //   754: ldc 116
    //   756: ldc_w 543
    //   759: iconst_1
    //   760: invokestatic 425	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   763: iconst_1
    //   764: istore 4
    //   766: goto +26 -> 792
    //   769: aload_0
    //   770: aload_1
    //   771: invokevirtual 532	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)V
    //   774: ldc 116
    //   776: ldc_w 545
    //   779: iconst_1
    //   780: invokestatic 425	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   783: iconst_m1
    //   784: istore 5
    //   786: iconst_0
    //   787: istore 4
    //   789: goto +3 -> 792
    //   792: aload_1
    //   793: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   796: sipush 65028
    //   799: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   802: new 134	java/lang/StringBuilder
    //   805: dup
    //   806: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   809: astore 15
    //   811: aload 15
    //   813: ldc_w 547
    //   816: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   819: pop
    //   820: aload 15
    //   822: iload 5
    //   824: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   827: pop
    //   828: ldc 116
    //   830: aload 15
    //   832: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   835: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   838: iload 5
    //   840: iconst_1
    //   841: if_icmpge +1273 -> 2114
    //   844: ldc 116
    //   846: ldc_w 549
    //   849: iconst_1
    //   850: invokestatic 425	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   853: aload_1
    //   854: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   857: sipush 65027
    //   860: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   863: iload 4
    //   865: ifeq +94 -> 959
    //   868: aload_1
    //   869: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   872: ldc_w 551
    //   875: invokevirtual 522	com/tencent/smtt/downloader/b:b	(Ljava/lang/String;)I
    //   878: istore 6
    //   880: iload 6
    //   882: bipush 10
    //   884: if_icmple +64 -> 948
    //   887: aload_1
    //   888: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   891: sipush 201
    //   894: ldc_w 553
    //   897: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   900: aload_0
    //   901: aload_1
    //   902: invokespecial 555	com/tencent/smtt/downloader/e:c	(Landroid/content/Context;)V
    //   905: aload_1
    //   906: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   909: sipush 65026
    //   912: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   915: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   918: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   921: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   924: invokeinterface 559 1 0
    //   929: goto +8 -> 937
    //   932: astore_1
    //   933: aload_1
    //   934: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   937: aload_0
    //   938: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   941: return
    //   942: astore_1
    //   943: aload_1
    //   944: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   947: return
    //   948: aload_1
    //   949: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   952: iload 6
    //   954: iconst_1
    //   955: iadd
    //   956: invokevirtual 562	com/tencent/smtt/downloader/b:b	(I)V
    //   959: aload_2
    //   960: astore 15
    //   962: aload_2
    //   963: ifnonnull +77 -> 1040
    //   966: aload_1
    //   967: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   970: ldc_w 564
    //   973: invokevirtual 567	com/tencent/smtt/downloader/b:a	(Ljava/lang/String;)Ljava/lang/String;
    //   976: astore_2
    //   977: aload_2
    //   978: astore 15
    //   980: aload_2
    //   981: ifnonnull +59 -> 1040
    //   984: aload_1
    //   985: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   988: sipush 202
    //   991: ldc_w 569
    //   994: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   997: aload_1
    //   998: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1001: sipush 65025
    //   1004: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1007: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1010: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1013: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1016: invokeinterface 559 1 0
    //   1021: goto +8 -> 1029
    //   1024: astore_1
    //   1025: aload_1
    //   1026: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1029: aload_0
    //   1030: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1033: return
    //   1034: astore_1
    //   1035: aload_1
    //   1036: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1039: return
    //   1040: new 134	java/lang/StringBuilder
    //   1043: dup
    //   1044: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   1047: astore_2
    //   1048: aload_2
    //   1049: ldc_w 571
    //   1052: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1055: pop
    //   1056: aload_2
    //   1057: aload 15
    //   1059: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1062: pop
    //   1063: ldc 116
    //   1065: aload_2
    //   1066: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1069: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   1072: aload_0
    //   1073: aload_1
    //   1074: aload 15
    //   1076: invokevirtual 574	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;Ljava/lang/String;)I
    //   1079: istore 6
    //   1081: iload 6
    //   1083: ifne +59 -> 1142
    //   1086: aload_1
    //   1087: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1090: sipush 65024
    //   1093: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1096: aload_1
    //   1097: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1100: sipush 203
    //   1103: ldc_w 576
    //   1106: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   1109: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1112: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1115: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1118: invokeinterface 559 1 0
    //   1123: goto +8 -> 1131
    //   1126: astore_1
    //   1127: aload_1
    //   1128: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1131: aload_0
    //   1132: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1135: return
    //   1136: astore_1
    //   1137: aload_1
    //   1138: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1141: return
    //   1142: aload_1
    //   1143: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1146: ldc_w 564
    //   1149: aload 15
    //   1151: invokevirtual 577	com/tencent/smtt/downloader/b:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   1154: aload_1
    //   1155: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1158: iload 6
    //   1160: iconst_0
    //   1161: invokevirtual 580	com/tencent/smtt/downloader/b:b	(II)V
    //   1164: aload_1
    //   1165: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1168: sipush 64988
    //   1171: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1174: aload_1
    //   1175: invokestatic 260	com/tencent/smtt/downloader/TbsDownloader:a	(Landroid/content/Context;)Z
    //   1178: ifeq +70 -> 1248
    //   1181: aload_0
    //   1182: aload_1
    //   1183: new 125	java/io/File
    //   1186: dup
    //   1187: aload 15
    //   1189: invokespecial 583	java/io/File:<init>	(Ljava/lang/String;)V
    //   1192: iconst_1
    //   1193: invokespecial 232	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;Ljava/io/File;Z)Z
    //   1196: ifne +115 -> 1311
    //   1199: aload_1
    //   1200: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1203: sipush 207
    //   1206: ldc_w 585
    //   1209: getstatic 591	com/tencent/smtt/downloader/utils/i$a:e	Lcom/tencent/smtt/downloader/utils/i$a;
    //   1212: invokevirtual 594	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;Lcom/tencent/smtt/downloader/utils/i$a;)V
    //   1215: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1218: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1221: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1224: invokeinterface 559 1 0
    //   1229: goto +8 -> 1237
    //   1232: astore_1
    //   1233: aload_1
    //   1234: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1237: aload_0
    //   1238: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1241: return
    //   1242: astore_1
    //   1243: aload_1
    //   1244: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1247: return
    //   1248: aload_0
    //   1249: aload_1
    //   1250: new 125	java/io/File
    //   1253: dup
    //   1254: aload 15
    //   1256: invokespecial 583	java/io/File:<init>	(Ljava/lang/String;)V
    //   1259: invokespecial 596	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;Ljava/io/File;)Z
    //   1262: ifne +49 -> 1311
    //   1265: aload_1
    //   1266: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1269: sipush 207
    //   1272: ldc_w 585
    //   1275: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   1278: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1281: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1284: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1287: invokeinterface 559 1 0
    //   1292: goto +8 -> 1300
    //   1295: astore_1
    //   1296: aload_1
    //   1297: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1300: aload_0
    //   1301: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1304: return
    //   1305: astore_1
    //   1306: aload_1
    //   1307: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1310: return
    //   1311: iload 4
    //   1313: ifeq +144 -> 1457
    //   1316: aload_1
    //   1317: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1320: ldc_w 598
    //   1323: invokevirtual 600	com/tencent/smtt/downloader/b:a	(Ljava/lang/String;)I
    //   1326: istore 9
    //   1328: iload 9
    //   1330: iconst_5
    //   1331: if_icmple +115 -> 1446
    //   1334: aload_1
    //   1335: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1338: sipush 223
    //   1341: ldc_w 602
    //   1344: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   1347: aload_1
    //   1348: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1351: sipush 64983
    //   1354: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1357: aload_0
    //   1358: aload_1
    //   1359: invokespecial 555	com/tencent/smtt/downloader/e:c	(Landroid/content/Context;)V
    //   1362: aload_1
    //   1363: invokestatic 605	com/tencent/smtt/downloader/TbsApkDownloader:a	(Landroid/content/Context;)V
    //   1366: aload_1
    //   1367: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1370: getfield 608	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   1373: ldc_w 610
    //   1376: iconst_1
    //   1377: invokestatic 615	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   1380: invokeinterface 621 3 0
    //   1385: pop
    //   1386: aload_1
    //   1387: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1390: getfield 608	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   1393: ldc_w 623
    //   1396: iconst_1
    //   1397: invokestatic 615	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   1400: invokeinterface 621 3 0
    //   1405: pop
    //   1406: aload_1
    //   1407: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1410: invokevirtual 624	com/tencent/smtt/downloader/c:a	()V
    //   1413: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1416: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1419: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1422: invokeinterface 559 1 0
    //   1427: goto +8 -> 1435
    //   1430: astore_1
    //   1431: aload_1
    //   1432: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1435: aload_0
    //   1436: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1439: return
    //   1440: astore_1
    //   1441: aload_1
    //   1442: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1445: return
    //   1446: aload_1
    //   1447: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1450: iload 9
    //   1452: iconst_1
    //   1453: iadd
    //   1454: invokevirtual 626	com/tencent/smtt/downloader/b:d	(I)V
    //   1457: ldc 116
    //   1459: ldc_w 628
    //   1462: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   1465: invokestatic 631	com/tencent/smtt/downloader/c:a	()Lcom/tencent/smtt/downloader/c;
    //   1468: getfield 634	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   1471: ldc_w 636
    //   1474: iconst_0
    //   1475: invokeinterface 482 3 0
    //   1480: pop
    //   1481: aload_0
    //   1482: aload_1
    //   1483: invokevirtual 537	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)I
    //   1486: pop
    //   1487: aload_1
    //   1488: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1491: iload 6
    //   1493: iconst_1
    //   1494: invokevirtual 580	com/tencent/smtt/downloader/b:b	(II)V
    //   1497: goto +3 -> 1500
    //   1500: iload 5
    //   1502: iconst_2
    //   1503: if_icmpge +413 -> 1916
    //   1506: iload 4
    //   1508: ifeq +94 -> 1602
    //   1511: aload_1
    //   1512: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1515: ldc_w 638
    //   1518: invokevirtual 522	com/tencent/smtt/downloader/b:b	(Ljava/lang/String;)I
    //   1521: istore 4
    //   1523: iload 4
    //   1525: bipush 10
    //   1527: if_icmple +64 -> 1591
    //   1530: aload_1
    //   1531: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1534: sipush 208
    //   1537: ldc_w 640
    //   1540: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   1543: aload_1
    //   1544: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1547: sipush 65022
    //   1550: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1553: aload_0
    //   1554: aload_1
    //   1555: invokespecial 555	com/tencent/smtt/downloader/e:c	(Landroid/content/Context;)V
    //   1558: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1561: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1564: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1567: invokeinterface 559 1 0
    //   1572: goto +8 -> 1580
    //   1575: astore_1
    //   1576: aload_1
    //   1577: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1580: aload_0
    //   1581: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1584: return
    //   1585: astore_1
    //   1586: aload_1
    //   1587: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1590: return
    //   1591: aload_1
    //   1592: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1595: iload 4
    //   1597: iconst_1
    //   1598: iadd
    //   1599: invokevirtual 642	com/tencent/smtt/downloader/b:a	(I)V
    //   1602: aload_1
    //   1603: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1606: sipush 64987
    //   1609: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1612: aload_0
    //   1613: aload_1
    //   1614: iconst_0
    //   1615: invokespecial 645	com/tencent/smtt/downloader/e:b	(Landroid/content/Context;I)Z
    //   1618: ifne +46 -> 1664
    //   1621: aload_1
    //   1622: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1625: sipush 65021
    //   1628: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1631: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1634: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1637: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1640: invokeinterface 559 1 0
    //   1645: goto +8 -> 1653
    //   1648: astore_1
    //   1649: aload_1
    //   1650: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1653: aload_0
    //   1654: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1657: return
    //   1658: astore_1
    //   1659: aload_1
    //   1660: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1663: return
    //   1664: aload_1
    //   1665: invokestatic 518	com/tencent/smtt/downloader/b:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/b;
    //   1668: iload 6
    //   1670: iconst_2
    //   1671: invokevirtual 580	com/tencent/smtt/downloader/b:b	(II)V
    //   1674: ldc 116
    //   1676: ldc_w 647
    //   1679: iconst_1
    //   1680: invokestatic 425	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   1683: new 134	java/lang/StringBuilder
    //   1686: dup
    //   1687: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   1690: astore_2
    //   1691: aload_2
    //   1692: ldc_w 649
    //   1695: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1698: pop
    //   1699: aload_2
    //   1700: iload_3
    //   1701: invokevirtual 432	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1704: pop
    //   1705: ldc 116
    //   1707: aload_2
    //   1708: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1711: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   1714: aload_1
    //   1715: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1718: sipush 65020
    //   1721: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1724: getstatic 468	android/os/Build$VERSION:SDK_INT	I
    //   1727: bipush 11
    //   1729: if_icmplt +15 -> 1744
    //   1732: aload_1
    //   1733: ldc_w 470
    //   1736: iconst_4
    //   1737: invokevirtual 474	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   1740: astore_2
    //   1741: goto +12 -> 1753
    //   1744: aload_1
    //   1745: ldc_w 470
    //   1748: iconst_0
    //   1749: invokevirtual 474	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   1752: astore_2
    //   1753: aload_2
    //   1754: invokeinterface 653 1 0
    //   1759: astore_2
    //   1760: aload_2
    //   1761: ldc_w 655
    //   1764: iconst_0
    //   1765: invokeinterface 661 3 0
    //   1770: pop
    //   1771: aload_2
    //   1772: ldc_w 663
    //   1775: iconst_0
    //   1776: invokeinterface 661 3 0
    //   1781: pop
    //   1782: aload_2
    //   1783: ldc_w 665
    //   1786: iload_3
    //   1787: invokeinterface 661 3 0
    //   1792: pop
    //   1793: aload_2
    //   1794: invokeinterface 668 1 0
    //   1799: pop
    //   1800: aload_1
    //   1801: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1804: sipush 65019
    //   1807: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1810: goto +52 -> 1862
    //   1813: astore_2
    //   1814: new 134	java/lang/StringBuilder
    //   1817: dup
    //   1818: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   1821: astore 15
    //   1823: aload 15
    //   1825: ldc_w 670
    //   1828: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1831: pop
    //   1832: aload 15
    //   1834: aload_2
    //   1835: invokestatic 268	android/util/Log:getStackTraceString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   1838: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1841: pop
    //   1842: ldc 116
    //   1844: aload 15
    //   1846: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1849: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   1852: aload_1
    //   1853: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1856: sipush 65018
    //   1859: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   1862: aload_0
    //   1863: aload_1
    //   1864: invokespecial 672	com/tencent/smtt/downloader/e:d	(Landroid/content/Context;)V
    //   1867: aload_0
    //   1868: getfield 97	com/tencent/smtt/downloader/e:c	Z
    //   1871: ifeq +24 -> 1895
    //   1874: aload_1
    //   1875: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1878: aload_0
    //   1879: aload_1
    //   1880: invokespecial 674	com/tencent/smtt/downloader/e:d	(Landroid/content/Context;)I
    //   1883: ldc_w 676
    //   1886: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   1889: iload 7
    //   1891: istore_3
    //   1892: goto +55 -> 1947
    //   1895: aload_1
    //   1896: invokestatic 187	com/tencent/smtt/downloader/utils/i:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/utils/i;
    //   1899: aload_0
    //   1900: aload_1
    //   1901: invokespecial 674	com/tencent/smtt/downloader/e:d	(Landroid/content/Context;)I
    //   1904: ldc_w 678
    //   1907: invokevirtual 192	com/tencent/smtt/downloader/utils/i:a	(ILjava/lang/String;)V
    //   1910: iload 7
    //   1912: istore_3
    //   1913: goto +34 -> 1947
    //   1916: iload 5
    //   1918: iconst_2
    //   1919: if_icmpne +26 -> 1945
    //   1922: getstatic 681	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   1925: sipush 200
    //   1928: invokeinterface 686 2 0
    //   1933: iload 7
    //   1935: istore_3
    //   1936: goto +11 -> 1947
    //   1939: astore_1
    //   1940: iconst_1
    //   1941: istore_3
    //   1942: goto +59 -> 2001
    //   1945: iconst_0
    //   1946: istore_3
    //   1947: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   1950: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   1953: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   1956: invokeinterface 559 1 0
    //   1961: goto +8 -> 1969
    //   1964: astore_1
    //   1965: aload_1
    //   1966: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1969: aload_0
    //   1970: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   1973: goto +8 -> 1981
    //   1976: astore_1
    //   1977: aload_1
    //   1978: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   1981: iload_3
    //   1982: ifeq +84 -> 2066
    //   1985: getstatic 681	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   1988: sipush 232
    //   1991: invokeinterface 686 2 0
    //   1996: return
    //   1997: astore_1
    //   1998: iload 8
    //   2000: istore_3
    //   2001: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   2004: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   2007: getstatic 37	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksLock	Ljava/util/concurrent/locks/Lock;
    //   2010: invokeinterface 559 1 0
    //   2015: goto +8 -> 2023
    //   2018: astore_2
    //   2019: aload_2
    //   2020: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   2023: aload_0
    //   2024: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   2027: goto +8 -> 2035
    //   2030: astore_2
    //   2031: aload_2
    //   2032: invokevirtual 354	java/lang/Exception:printStackTrace	()V
    //   2035: iload_3
    //   2036: ifeq +14 -> 2050
    //   2039: getstatic 681	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   2042: sipush 232
    //   2045: invokeinterface 686 2 0
    //   2050: aload_1
    //   2051: athrow
    //   2052: aload_1
    //   2053: invokestatic 248	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2056: sipush 65017
    //   2059: invokevirtual 251	com/tencent/smtt/downloader/c:b	(I)V
    //   2062: aload_0
    //   2063: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   2066: return
    //   2067: iload 4
    //   2069: ifle -1414 -> 655
    //   2072: iload_3
    //   2073: iload 4
    //   2075: if_icmple -1420 -> 655
    //   2078: goto -1428 -> 650
    //   2081: iload 5
    //   2083: iconst_3
    //   2084: if_icmpne +24 -> 2108
    //   2087: iload 4
    //   2089: iflt +19 -> 2108
    //   2092: iload_3
    //   2093: iload 4
    //   2095: if_icmpgt -1326 -> 769
    //   2098: iload_3
    //   2099: ldc_w 687
    //   2102: if_icmpne +6 -> 2108
    //   2105: goto -1336 -> 769
    //   2108: iconst_0
    //   2109: istore 4
    //   2111: goto -1319 -> 792
    //   2114: iconst_0
    //   2115: istore 6
    //   2117: goto -617 -> 1500
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2120	0	this	e
    //   0	2120	1	paramContext	Context
    //   0	2120	2	paramString	String
    //   0	2120	3	paramInt	int
    //   236	1874	4	i	int
    //   527	1558	5	j	int
    //   878	1238	6	k	int
    //   18	1916	7	m	int
    //   239	1760	8	n	int
    //   1326	128	9	i1	int
    //   15	473	10	bool	boolean
    //   346	44	11	l1	long
    //   355	50	13	l2	long
    //   52	1793	15	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   915	929	932	java/lang/Exception
    //   937	941	942	java/lang/Exception
    //   1007	1021	1024	java/lang/Exception
    //   1029	1033	1034	java/lang/Exception
    //   1109	1123	1126	java/lang/Exception
    //   1131	1135	1136	java/lang/Exception
    //   1215	1229	1232	java/lang/Exception
    //   1237	1241	1242	java/lang/Exception
    //   1278	1292	1295	java/lang/Exception
    //   1300	1304	1305	java/lang/Exception
    //   1413	1427	1430	java/lang/Exception
    //   1435	1439	1440	java/lang/Exception
    //   1558	1572	1575	java/lang/Exception
    //   1580	1584	1585	java/lang/Exception
    //   1631	1645	1648	java/lang/Exception
    //   1653	1657	1658	java/lang/Exception
    //   1753	1810	1813	java/lang/Throwable
    //   1922	1933	1939	finally
    //   1947	1961	1964	java/lang/Exception
    //   1969	1973	1976	java/lang/Exception
    //   508	636	1997	finally
    //   650	655	1997	finally
    //   655	743	1997	finally
    //   754	763	1997	finally
    //   769	783	1997	finally
    //   792	838	1997	finally
    //   844	863	1997	finally
    //   868	880	1997	finally
    //   887	915	1997	finally
    //   948	959	1997	finally
    //   966	977	1997	finally
    //   984	1007	1997	finally
    //   1040	1081	1997	finally
    //   1086	1109	1997	finally
    //   1142	1215	1997	finally
    //   1248	1278	1997	finally
    //   1316	1328	1997	finally
    //   1334	1413	1997	finally
    //   1446	1457	1997	finally
    //   1457	1497	1997	finally
    //   1511	1523	1997	finally
    //   1530	1558	1997	finally
    //   1591	1602	1997	finally
    //   1602	1631	1997	finally
    //   1664	1741	1997	finally
    //   1744	1753	1997	finally
    //   1753	1810	1997	finally
    //   1814	1862	1997	finally
    //   1862	1889	1997	finally
    //   1895	1910	1997	finally
    //   2001	2015	2018	java/lang/Exception
    //   2023	2027	2030	java/lang/Exception
  }
  
  private boolean b(Context paramContext, int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsInstaller-doTbsDexOpt start - dirMode: ");
    ((StringBuilder)localObject).append(paramInt);
    h.a("TbsInstaller", ((StringBuilder)localObject).toString());
    int k = 0;
    switch (paramInt)
    {
    default: 
      break;
    }
    for (;;)
    {
      try
      {
        localObject = b(paramContext);
        continue;
        localObject = a(paramContext, 1);
        continue;
        if (TbsDownloader.a(paramContext)) {
          return true;
        }
        localObject = a(paramContext, 0);
        try
        {
          String str = System.getProperty("java.vm.version");
          if (str != null)
          {
            bool = str.startsWith("2");
            if (bool)
            {
              paramInt = 1;
              continue;
            }
          }
          paramInt = 0;
        }
        catch (Throwable localThrowable)
        {
          i.a(paramContext).a(226, localThrowable);
          paramInt = 0;
        }
        if (Build.VERSION.SDK_INT != 23) {
          break label343;
        }
        i = 1;
        boolean bool = c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getBoolean("tbs_stop_preoat", false);
        int j = k;
        if (paramInt != 0)
        {
          j = k;
          if (i != 0)
          {
            j = k;
            if (!bool) {
              j = 1;
            }
          }
        }
        if ((j != 0) && (c(paramContext, (File)localObject)))
        {
          h.a("TbsInstaller", "doTbsDexOpt -- doDexoatForArtVm");
          return true;
        }
        if (paramInt != 0)
        {
          h.a("TbsInstaller", "doTbsDexOpt -- is ART mode, skip!");
          continue;
        }
        h.a("TbsInstaller", "doTbsDexOpt -- doDexoptForDavlikVM");
        return b(paramContext, (File)localObject);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        i.a(paramContext).a(209, localException.toString());
        h.a("TbsInstaller", "TbsInstaller-doTbsDexOpt done");
        return true;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doDexoptOrDexoat mode error: ");
      ((StringBuilder)localObject).append(paramInt);
      h.b("TbsInstaller", ((StringBuilder)localObject).toString());
      return false;
      label343:
      int i = 0;
    }
  }
  
  private boolean b(Context paramContext, File paramFile)
  {
    h.a("TbsInstaller", "TbsInstaller-doTbsDexOpt done");
    return false;
  }
  
  private void c(Context paramContext)
  {
    h.a("TbsInstaller", "TbsInstaller--clearNewTbsCore");
    File localFile = a(paramContext, 0);
    if (localFile != null) {
      FileUtil.a(localFile, false);
    }
    b.a(paramContext).b(0, 5);
    b.a(paramContext).c(-1);
  }
  
  private boolean c(Context paramContext, File paramFile)
  {
    return false;
  }
  
  private int d(Context paramContext)
  {
    int j = b.a(paramContext).c();
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    boolean bool = TbsDownloader.a(paramContext);
    if (i != 0)
    {
      if (bool) {
        return 234;
      }
      return 221;
    }
    if (bool) {
      return 233;
    }
    return 200;
  }
  
  static File d(Context paramContext)
  {
    paramContext = new File(a.a(paramContext), "core_private");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext;
  }
  
  private void d(Context paramContext)
  {
    h.a("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip");
    try
    {
      e(paramContext);
      f(paramContext);
      h.a("TbsInstaller", "after renameTbsCoreShareDir");
      h.a("TbsInstaller", "prepare to shareTbsCore");
      b(paramContext);
      b.a(paramContext).a(0);
      b.a(paramContext).b(0);
      b.a(paramContext).d(0);
      b.a(paramContext).a("incrupdate_retry_num", 0);
      b.a(paramContext).b(0, 3);
      b.a(paramContext).a("");
      b.a(paramContext).a("tpatch_num", 0);
      b.a(paramContext).c(-1);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      paramContext = i.a(paramContext);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("exception when renameing from unzip:");
      localStringBuilder.append(localThrowable.toString());
      paramContext.a(219, localStringBuilder.toString());
      h.b("TbsInstaller", "TbsInstaller--generateNewTbsCoreFromUnzip Exception", true);
    }
  }
  
  private void e(Context paramContext)
  {
    h.a("TbsInstaller", "TbsInstaller--deleteOldCore");
    FileUtil.a(b(paramContext), false);
  }
  
  private void f(Context paramContext)
  {
    h.a("TbsInstaller", "TbsInstaller--renameShareDir");
    Object localObject = a(paramContext, 0);
    File localFile = b(paramContext);
    if ((localObject != null) && (localFile != null))
    {
      boolean bool = ((File)localObject).renameTo(localFile);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("renameTbsCoreShareDir rename success=");
      ((StringBuilder)localObject).append(bool);
      h.a("TbsInstaller", ((StringBuilder)localObject).toString());
      if ((paramContext != null) && ("com.tencent.mm".equals(paramContext.getApplicationContext().getApplicationInfo().packageName))) {
        if (bool) {
          i.a(paramContext).a(230, " ");
        } else {
          i.a(paramContext).a(231, " ");
        }
      }
      a(paramContext, false);
      return;
    }
    paramContext = new StringBuilder();
    paramContext.append("renameTbsCoreShareDir return,tmpTbsCoreUnzipDir=");
    paramContext.append(localObject);
    paramContext.append("tbsSharePath=");
    paramContext.append(localFile);
    h.a("TbsInstaller", paramContext.toString());
  }
  
  /* Error */
  int a(Context paramContext)
  {
    // Byte code:
    //   0: ldc 116
    //   2: ldc_w 783
    //   5: invokestatic 391	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: aconst_null
    //   9: astore 5
    //   11: aconst_null
    //   12: astore 4
    //   14: aload 4
    //   16: astore_3
    //   17: new 125	java/io/File
    //   20: dup
    //   21: aload_0
    //   22: aload_1
    //   23: invokevirtual 406	com/tencent/smtt/downloader/e:b	(Landroid/content/Context;)Ljava/io/File;
    //   26: ldc_w 362
    //   29: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   32: astore_1
    //   33: aload 4
    //   35: astore_3
    //   36: aload_1
    //   37: invokevirtual 129	java/io/File:exists	()Z
    //   40: ifne +5 -> 45
    //   43: iconst_0
    //   44: ireturn
    //   45: aload 4
    //   47: astore_3
    //   48: new 334	java/util/Properties
    //   51: dup
    //   52: invokespecial 335	java/util/Properties:<init>	()V
    //   55: astore 6
    //   57: aload 4
    //   59: astore_3
    //   60: new 337	java/io/BufferedInputStream
    //   63: dup
    //   64: new 339	java/io/FileInputStream
    //   67: dup
    //   68: aload_1
    //   69: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   72: invokespecial 344	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   75: astore_1
    //   76: aload 6
    //   78: aload_1
    //   79: invokevirtual 347	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   82: aload_1
    //   83: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   86: aload 6
    //   88: ldc_w 785
    //   91: invokevirtual 786	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   94: astore_3
    //   95: aload_3
    //   96: ifnonnull +46 -> 142
    //   99: aload_1
    //   100: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   103: iconst_0
    //   104: ireturn
    //   105: astore_1
    //   106: new 134	java/lang/StringBuilder
    //   109: dup
    //   110: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   113: astore_3
    //   114: aload_3
    //   115: ldc_w 788
    //   118: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload_3
    //   123: aload_1
    //   124: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   127: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: ldc 116
    //   133: aload_3
    //   134: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   137: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   140: iconst_0
    //   141: ireturn
    //   142: aload_3
    //   143: invokestatic 794	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   146: istore_2
    //   147: getstatic 87	com/tencent/smtt/downloader/e:jdField_b_of_type_Int	I
    //   150: ifne +7 -> 157
    //   153: iload_2
    //   154: putstatic 87	com/tencent/smtt/downloader/e:jdField_b_of_type_Int	I
    //   157: aload_1
    //   158: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   161: iload_2
    //   162: ireturn
    //   163: astore_1
    //   164: new 134	java/lang/StringBuilder
    //   167: dup
    //   168: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   171: astore_3
    //   172: aload_3
    //   173: ldc_w 788
    //   176: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: pop
    //   180: aload_3
    //   181: aload_1
    //   182: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   185: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: ldc 116
    //   191: aload_3
    //   192: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   195: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   198: iload_2
    //   199: ireturn
    //   200: astore 4
    //   202: aload_1
    //   203: astore_3
    //   204: aload 4
    //   206: astore_1
    //   207: goto +111 -> 318
    //   210: astore 4
    //   212: goto +12 -> 224
    //   215: astore_1
    //   216: goto +102 -> 318
    //   219: astore 4
    //   221: aload 5
    //   223: astore_1
    //   224: aload_1
    //   225: astore_3
    //   226: new 134	java/lang/StringBuilder
    //   229: dup
    //   230: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   233: astore 5
    //   235: aload_1
    //   236: astore_3
    //   237: aload 5
    //   239: ldc_w 796
    //   242: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: pop
    //   246: aload_1
    //   247: astore_3
    //   248: aload 5
    //   250: aload 4
    //   252: invokevirtual 720	java/lang/Exception:toString	()Ljava/lang/String;
    //   255: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: pop
    //   259: aload_1
    //   260: astore_3
    //   261: ldc 116
    //   263: aload 5
    //   265: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   268: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   271: aload_1
    //   272: ifnull +44 -> 316
    //   275: aload_1
    //   276: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   279: iconst_0
    //   280: ireturn
    //   281: astore_1
    //   282: new 134	java/lang/StringBuilder
    //   285: dup
    //   286: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   289: astore_3
    //   290: aload_3
    //   291: ldc_w 788
    //   294: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: pop
    //   298: aload_3
    //   299: aload_1
    //   300: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   303: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: pop
    //   307: ldc 116
    //   309: aload_3
    //   310: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   313: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   316: iconst_0
    //   317: ireturn
    //   318: aload_3
    //   319: ifnull +49 -> 368
    //   322: aload_3
    //   323: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   326: goto +42 -> 368
    //   329: astore_3
    //   330: new 134	java/lang/StringBuilder
    //   333: dup
    //   334: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   337: astore 4
    //   339: aload 4
    //   341: ldc_w 788
    //   344: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   347: pop
    //   348: aload 4
    //   350: aload_3
    //   351: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   354: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   357: pop
    //   358: ldc 116
    //   360: aload 4
    //   362: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   365: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   368: aload_1
    //   369: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	370	0	this	e
    //   0	370	1	paramContext	Context
    //   146	53	2	i	int
    //   16	307	3	localObject1	Object
    //   329	22	3	localIOException	java.io.IOException
    //   12	46	4	localObject2	Object
    //   200	5	4	localObject3	Object
    //   210	1	4	localException1	Exception
    //   219	32	4	localException2	Exception
    //   337	24	4	localStringBuilder1	StringBuilder
    //   9	255	5	localStringBuilder2	StringBuilder
    //   55	32	6	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   99	103	105	java/io/IOException
    //   157	161	163	java/io/IOException
    //   76	95	200	finally
    //   142	157	200	finally
    //   76	95	210	java/lang/Exception
    //   142	157	210	java/lang/Exception
    //   17	33	215	finally
    //   36	43	215	finally
    //   48	57	215	finally
    //   60	76	215	finally
    //   226	235	215	finally
    //   237	246	215	finally
    //   248	259	215	finally
    //   261	271	215	finally
    //   17	33	219	java/lang/Exception
    //   36	43	219	java/lang/Exception
    //   48	57	219	java/lang/Exception
    //   60	76	219	java/lang/Exception
    //   275	279	281	java/io/IOException
    //   322	326	329	java/io/IOException
  }
  
  int a(Context paramContext, int paramInt)
  {
    return a(a(paramContext, paramInt));
  }
  
  int a(Context paramContext, String paramString)
  {
    paramContext = paramContext.getPackageManager().getPackageArchiveInfo(paramString, 0);
    if (paramContext != null) {
      return paramContext.versionCode;
    }
    return 0;
  }
  
  /* Error */
  int a(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_3
    //   5: new 134	java/lang/StringBuilder
    //   8: dup
    //   9: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   12: astore 5
    //   14: aload 5
    //   16: ldc_w 817
    //   19: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: pop
    //   23: aload 5
    //   25: aload_1
    //   26: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   29: pop
    //   30: ldc 116
    //   32: aload 5
    //   34: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   37: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   40: new 125	java/io/File
    //   43: dup
    //   44: aload_1
    //   45: ldc_w 362
    //   48: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   51: astore_1
    //   52: aload_1
    //   53: invokevirtual 129	java/io/File:exists	()Z
    //   56: ifne +5 -> 61
    //   59: iconst_0
    //   60: ireturn
    //   61: new 334	java/util/Properties
    //   64: dup
    //   65: invokespecial 335	java/util/Properties:<init>	()V
    //   68: astore 5
    //   70: new 337	java/io/BufferedInputStream
    //   73: dup
    //   74: new 339	java/io/FileInputStream
    //   77: dup
    //   78: aload_1
    //   79: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   82: invokespecial 344	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   85: astore_1
    //   86: aload 5
    //   88: aload_1
    //   89: invokevirtual 347	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   92: aload_1
    //   93: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   96: aload 5
    //   98: ldc_w 785
    //   101: invokevirtual 786	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   104: astore_3
    //   105: aload_3
    //   106: ifnonnull +9 -> 115
    //   109: aload_1
    //   110: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   113: iconst_0
    //   114: ireturn
    //   115: aload_3
    //   116: invokestatic 794	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   119: istore_2
    //   120: aload_1
    //   121: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   124: iload_2
    //   125: ireturn
    //   126: astore 4
    //   128: aload_1
    //   129: astore_3
    //   130: aload 4
    //   132: astore_1
    //   133: goto +7 -> 140
    //   136: goto +14 -> 150
    //   139: astore_1
    //   140: aload_3
    //   141: ifnull +7 -> 148
    //   144: aload_3
    //   145: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   148: aload_1
    //   149: athrow
    //   150: aload_1
    //   151: ifnull +7 -> 158
    //   154: aload_1
    //   155: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   158: iconst_0
    //   159: ireturn
    //   160: astore_1
    //   161: aload 4
    //   163: astore_1
    //   164: goto -14 -> 150
    //   167: astore_3
    //   168: goto -32 -> 136
    //   171: astore_1
    //   172: iconst_0
    //   173: ireturn
    //   174: astore_1
    //   175: iload_2
    //   176: ireturn
    //   177: astore_3
    //   178: goto -30 -> 148
    //   181: astore_1
    //   182: iconst_0
    //   183: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	this	e
    //   0	184	1	paramFile	File
    //   119	57	2	i	int
    //   4	141	3	localObject1	Object
    //   167	1	3	localException	Exception
    //   177	1	3	localIOException	java.io.IOException
    //   1	1	4	localObject2	Object
    //   126	36	4	localObject3	Object
    //   12	85	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   86	105	126	finally
    //   115	120	126	finally
    //   5	59	139	finally
    //   61	86	139	finally
    //   5	59	160	java/lang/Exception
    //   61	86	160	java/lang/Exception
    //   86	105	167	java/lang/Exception
    //   115	120	167	java/lang/Exception
    //   109	113	171	java/io/IOException
    //   120	124	174	java/io/IOException
    //   144	148	177	java/io/IOException
    //   154	158	181	java/io/IOException
  }
  
  File a(Context paramContext)
  {
    paramContext = new File(a.a(paramContext), "core_share_decouple");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext;
  }
  
  File a(Context paramContext, int paramInt)
  {
    return a(paramContext, paramInt, true);
  }
  
  File a(Context paramContext, int paramInt, boolean paramBoolean)
  {
    File localFile = a.a(paramContext);
    paramContext = "";
    switch (paramInt)
    {
    default: 
      break;
    case 6: 
      paramContext = "tpatch_decouple_tmp";
      break;
    case 5: 
      paramContext = "tpatch_tmp";
      break;
    case 4: 
      paramContext = "core_share_backup_tmp";
      break;
    case 3: 
      paramContext = "core_share_backup";
      break;
    case 2: 
      paramContext = "core_unzip_tmp_decouple";
      break;
    case 1: 
      paramContext = "core_copy_tmp";
      break;
    case 0: 
      paramContext = "core_unzip_tmp";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("type=");
    localStringBuilder.append(paramInt);
    localStringBuilder.append("needMakeDir=");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("folder=");
    localStringBuilder.append(paramContext);
    h.a("TbsInstaller", localStringBuilder.toString());
    paramContext = new File(localFile, paramContext);
    if (!paramContext.isDirectory()) {
      if (paramBoolean)
      {
        if (!paramContext.mkdir())
        {
          h.a("TbsInstaller", "getCoreDir,mkdir false");
          return null;
        }
      }
      else
      {
        h.a("TbsInstaller", "getCoreDir,no need mkdir");
        return null;
      }
    }
    return paramContext;
  }
  
  File a(Context paramContext1, Context paramContext2)
  {
    paramContext2 = new File(a.a(paramContext2), "core_share");
    if ((!paramContext2.isDirectory()) && (paramContext1 == null) && (!paramContext2.mkdir()))
    {
      h.a("TbsInstaller", "getTbsCoreShareDir,mkdir false");
      return null;
    }
    return paramContext2;
  }
  
  void a()
  {
    try
    {
      if (this.jdField_a_of_type_Int <= 0)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("releaseTbsInstallingFileLock currentTbsFileLockStackCount=");
        localStringBuilder.append(this.jdField_a_of_type_Int);
        localStringBuilder.append("call stack:");
        localStringBuilder.append(Log.getStackTraceString(new Throwable()));
        h.a("TbsInstaller", localStringBuilder.toString());
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("releaseTbsInstallingFileLock,current Thread Id=");
      localStringBuilder.append(Thread.currentThread().getId());
      localStringBuilder.append("currentTbsFileLockStackCount=");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      h.d("TbsInstaller", localStringBuilder.toString());
      if (this.jdField_a_of_type_Int > 1)
      {
        h.a("TbsInstaller", "releaseTbsInstallingFileLock with skip");
        this.jdField_a_of_type_Int -= 1;
        return;
      }
      if (this.jdField_a_of_type_Int == 1)
      {
        h.a("TbsInstaller", "releaseTbsInstallingFileLock without skip");
        FileUtil.a(this.jdField_a_of_type_JavaNioChannelsFileLock, this.jdField_a_of_type_JavaIoFileOutputStream);
        this.jdField_a_of_type_Int = 0;
      }
      return;
    }
    finally {}
  }
  
  void a(Context paramContext)
  {
    h.a("TbsInstaller", "TbsInstaller--cleanStatusAndTmpDir");
    b.a(paramContext).a(0);
    b.a(paramContext).b(0);
    b.a(paramContext).d(0);
    b.a(paramContext).a("incrupdate_retry_num", 0);
    if (!TbsDownloader.a(paramContext))
    {
      b.a(paramContext).b(0, -1);
      b.a(paramContext).a("");
      b.a(paramContext).a("copy_retry_num", 0);
      b.a(paramContext).c(-1);
      b.a(paramContext).a(0, -1);
      FileUtil.a(a(paramContext, 0), true);
      FileUtil.a(a(paramContext, 1), true);
    }
  }
  
  void a(Context paramContext, String paramString, int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsInstaller-installTbsCore tbsApkPath=");
    ((StringBuilder)localObject).append(paramString);
    h.a("TbsInstaller", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsInstaller-installTbsCore tbsCoreTargetVer=");
    ((StringBuilder)localObject).append(paramInt);
    h.a("TbsInstaller", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsInstaller-continueInstallTbsCore currentProcessName=");
    ((StringBuilder)localObject).append(paramContext.getApplicationInfo().processName);
    h.a("TbsInstaller", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsInstaller-installTbsCore currentProcessId=");
    ((StringBuilder)localObject).append(Process.myPid());
    h.a("TbsInstaller", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsInstaller-installTbsCore currentThreadName=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    h.a("TbsInstaller", ((StringBuilder)localObject).toString());
    localObject = new Message();
    ((Message)localObject).what = 1;
    ((Message)localObject).obj = new Object[] { paramContext, paramString, Integer.valueOf(paramInt) };
    jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject);
  }
  
  /* Error */
  boolean a(Context paramContext)
  {
    // Byte code:
    //   0: new 125	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: aload_1
    //   6: invokevirtual 406	com/tencent/smtt/downloader/e:b	(Landroid/content/Context;)Ljava/io/File;
    //   9: ldc_w 362
    //   12: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   15: astore 7
    //   17: aload 7
    //   19: invokevirtual 129	java/io/File:exists	()Z
    //   22: istore_2
    //   23: iconst_0
    //   24: istore_3
    //   25: iload_2
    //   26: ifne +5 -> 31
    //   29: iconst_0
    //   30: ireturn
    //   31: new 334	java/util/Properties
    //   34: dup
    //   35: invokespecial 335	java/util/Properties:<init>	()V
    //   38: astore 8
    //   40: aconst_null
    //   41: astore 6
    //   43: aconst_null
    //   44: astore 5
    //   46: new 337	java/io/BufferedInputStream
    //   49: dup
    //   50: new 339	java/io/FileInputStream
    //   53: dup
    //   54: aload 7
    //   56: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   59: invokespecial 344	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   62: astore_1
    //   63: aload 8
    //   65: aload_1
    //   66: invokevirtual 347	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   69: aload 8
    //   71: ldc_w 905
    //   74: ldc_w 907
    //   77: invokevirtual 375	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   80: invokestatic 910	java/lang/Boolean:valueOf	(Ljava/lang/String;)Ljava/lang/Boolean;
    //   83: invokevirtual 913	java/lang/Boolean:booleanValue	()Z
    //   86: istore 4
    //   88: iload_3
    //   89: istore_2
    //   90: iload 4
    //   92: ifeq +23 -> 115
    //   95: iload_3
    //   96: istore_2
    //   97: invokestatic 916	java/lang/System:currentTimeMillis	()J
    //   100: aload 7
    //   102: invokevirtual 919	java/io/File:lastModified	()J
    //   105: lsub
    //   106: ldc2_w 920
    //   109: lcmp
    //   110: ifle +5 -> 115
    //   113: iconst_1
    //   114: istore_2
    //   115: new 134	java/lang/StringBuilder
    //   118: dup
    //   119: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   122: astore 5
    //   124: aload 5
    //   126: ldc_w 923
    //   129: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload 5
    //   135: iload 4
    //   137: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload 5
    //   143: ldc_w 925
    //   146: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: pop
    //   150: aload 5
    //   152: iload_2
    //   153: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   156: pop
    //   157: ldc 116
    //   159: aload 5
    //   161: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   164: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   167: iload 4
    //   169: iload_2
    //   170: iconst_1
    //   171: ixor
    //   172: iand
    //   173: istore_2
    //   174: iload_2
    //   175: istore_3
    //   176: aload_1
    //   177: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   180: iload_2
    //   181: ireturn
    //   182: astore_1
    //   183: aload_1
    //   184: invokevirtual 353	java/io/IOException:printStackTrace	()V
    //   187: iload_3
    //   188: ireturn
    //   189: astore 5
    //   191: aload_1
    //   192: astore 6
    //   194: aload 5
    //   196: astore_1
    //   197: iload 4
    //   199: istore_2
    //   200: goto +34 -> 234
    //   203: astore 5
    //   205: goto +51 -> 256
    //   208: astore 5
    //   210: aload_1
    //   211: astore 6
    //   213: aload 5
    //   215: astore_1
    //   216: goto +16 -> 232
    //   219: astore 6
    //   221: aload 5
    //   223: astore_1
    //   224: aload 6
    //   226: astore 5
    //   228: goto +28 -> 256
    //   231: astore_1
    //   232: iconst_0
    //   233: istore_2
    //   234: aload 6
    //   236: astore 5
    //   238: aload_1
    //   239: invokevirtual 417	java/lang/Throwable:printStackTrace	()V
    //   242: aload 6
    //   244: ifnull +10 -> 254
    //   247: iload_2
    //   248: istore_3
    //   249: aload 6
    //   251: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   254: iload_2
    //   255: ireturn
    //   256: aload_1
    //   257: ifnull +15 -> 272
    //   260: aload_1
    //   261: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   264: goto +8 -> 272
    //   267: astore_1
    //   268: aload_1
    //   269: invokevirtual 353	java/io/IOException:printStackTrace	()V
    //   272: aload 5
    //   274: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	275	0	this	e
    //   0	275	1	paramContext	Context
    //   22	233	2	bool1	boolean
    //   24	225	3	bool2	boolean
    //   86	112	4	bool3	boolean
    //   44	116	5	localStringBuilder	StringBuilder
    //   189	6	5	localThrowable1	Throwable
    //   203	1	5	localObject1	Object
    //   208	14	5	localThrowable2	Throwable
    //   226	47	5	localObject2	Object
    //   41	171	6	localContext	Context
    //   219	31	6	localObject3	Object
    //   15	86	7	localFile	File
    //   38	32	8	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   176	180	182	java/io/IOException
    //   249	254	182	java/io/IOException
    //   97	113	189	java/lang/Throwable
    //   115	167	189	java/lang/Throwable
    //   63	88	203	finally
    //   97	113	203	finally
    //   115	167	203	finally
    //   63	88	208	java/lang/Throwable
    //   46	63	219	finally
    //   238	242	219	finally
    //   46	63	231	java/lang/Throwable
    //   260	264	267	java/io/IOException
  }
  
  boolean a(Context paramContext, int paramInt)
  {
    return false;
  }
  
  /* Error */
  int b(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 503	com/tencent/smtt/downloader/e:c	(Landroid/content/Context;)Z
    //   5: ifne +5 -> 10
    //   8: iconst_m1
    //   9: ireturn
    //   10: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   13: invokevirtual 926	java/util/concurrent/locks/ReentrantLock:tryLock	()Z
    //   16: istore_3
    //   17: new 134	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   24: astore 4
    //   26: aload 4
    //   28: ldc_w 928
    //   31: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: pop
    //   35: aload 4
    //   37: iload_3
    //   38: invokevirtual 311	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: ldc 116
    //   44: aload 4
    //   46: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   52: iload_3
    //   53: ifeq +701 -> 754
    //   56: aconst_null
    //   57: astore 6
    //   59: aconst_null
    //   60: astore 5
    //   62: aload 5
    //   64: astore 4
    //   66: new 125	java/io/File
    //   69: dup
    //   70: aload_0
    //   71: aload_1
    //   72: invokevirtual 406	com/tencent/smtt/downloader/e:b	(Landroid/content/Context;)Ljava/io/File;
    //   75: ldc_w 362
    //   78: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   81: astore_1
    //   82: aload 5
    //   84: astore 4
    //   86: aload_1
    //   87: invokevirtual 129	java/io/File:exists	()Z
    //   90: istore_3
    //   91: iload_3
    //   92: ifne +63 -> 155
    //   95: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   98: invokevirtual 931	java/util/concurrent/locks/ReentrantLock:isHeldByCurrentThread	()Z
    //   101: ifeq +48 -> 149
    //   104: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   107: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   110: goto +39 -> 149
    //   113: astore_1
    //   114: new 134	java/lang/StringBuilder
    //   117: dup
    //   118: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   121: astore 4
    //   123: aload 4
    //   125: ldc_w 933
    //   128: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: pop
    //   132: aload 4
    //   134: aload_1
    //   135: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: ldc 116
    //   141: aload 4
    //   143: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   146: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   149: aload_0
    //   150: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   153: iconst_0
    //   154: ireturn
    //   155: aload 5
    //   157: astore 4
    //   159: new 334	java/util/Properties
    //   162: dup
    //   163: invokespecial 335	java/util/Properties:<init>	()V
    //   166: astore 7
    //   168: aload 5
    //   170: astore 4
    //   172: new 337	java/io/BufferedInputStream
    //   175: dup
    //   176: new 339	java/io/FileInputStream
    //   179: dup
    //   180: aload_1
    //   181: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   184: invokespecial 344	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   187: astore_1
    //   188: aload 7
    //   190: aload_1
    //   191: invokevirtual 347	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   194: aload_1
    //   195: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   198: aload 7
    //   200: ldc_w 785
    //   203: invokevirtual 786	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   206: astore 4
    //   208: aload 4
    //   210: ifnonnull +109 -> 319
    //   213: aload_1
    //   214: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   217: goto +42 -> 259
    //   220: astore_1
    //   221: new 134	java/lang/StringBuilder
    //   224: dup
    //   225: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   228: astore 4
    //   230: aload 4
    //   232: ldc_w 935
    //   235: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload 4
    //   241: aload_1
    //   242: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   245: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: pop
    //   249: ldc 116
    //   251: aload 4
    //   253: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   256: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   259: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   262: invokevirtual 931	java/util/concurrent/locks/ReentrantLock:isHeldByCurrentThread	()Z
    //   265: ifeq +48 -> 313
    //   268: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   271: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   274: goto +39 -> 313
    //   277: astore_1
    //   278: new 134	java/lang/StringBuilder
    //   281: dup
    //   282: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   285: astore 4
    //   287: aload 4
    //   289: ldc_w 933
    //   292: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: pop
    //   296: aload 4
    //   298: aload_1
    //   299: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   302: pop
    //   303: ldc 116
    //   305: aload 4
    //   307: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   310: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   313: aload_0
    //   314: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   317: iconst_0
    //   318: ireturn
    //   319: getstatic 42	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaLangThreadLocal	Ljava/lang/ThreadLocal;
    //   322: aload 4
    //   324: invokestatic 794	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   327: invokestatic 893	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   330: invokevirtual 941	java/lang/ThreadLocal:set	(Ljava/lang/Object;)V
    //   333: getstatic 42	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaLangThreadLocal	Ljava/lang/ThreadLocal;
    //   336: invokevirtual 945	java/lang/ThreadLocal:get	()Ljava/lang/Object;
    //   339: checkcast 791	java/lang/Integer
    //   342: invokevirtual 948	java/lang/Integer:intValue	()I
    //   345: istore_2
    //   346: aload_1
    //   347: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   350: goto +42 -> 392
    //   353: astore_1
    //   354: new 134	java/lang/StringBuilder
    //   357: dup
    //   358: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   361: astore 4
    //   363: aload 4
    //   365: ldc_w 935
    //   368: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: pop
    //   372: aload 4
    //   374: aload_1
    //   375: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   378: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   381: pop
    //   382: ldc 116
    //   384: aload 4
    //   386: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   389: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   392: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   395: invokevirtual 931	java/util/concurrent/locks/ReentrantLock:isHeldByCurrentThread	()Z
    //   398: ifeq +48 -> 446
    //   401: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   404: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   407: goto +39 -> 446
    //   410: astore_1
    //   411: new 134	java/lang/StringBuilder
    //   414: dup
    //   415: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   418: astore 4
    //   420: aload 4
    //   422: ldc_w 933
    //   425: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   428: pop
    //   429: aload 4
    //   431: aload_1
    //   432: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   435: pop
    //   436: ldc 116
    //   438: aload 4
    //   440: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   443: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   446: aload_0
    //   447: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   450: iload_2
    //   451: ireturn
    //   452: astore 5
    //   454: aload_1
    //   455: astore 4
    //   457: aload 5
    //   459: astore_1
    //   460: goto +178 -> 638
    //   463: astore 5
    //   465: goto +12 -> 477
    //   468: astore_1
    //   469: goto +169 -> 638
    //   472: astore 5
    //   474: aload 6
    //   476: astore_1
    //   477: aload_1
    //   478: astore 4
    //   480: new 134	java/lang/StringBuilder
    //   483: dup
    //   484: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   487: astore 6
    //   489: aload_1
    //   490: astore 4
    //   492: aload 6
    //   494: ldc_w 950
    //   497: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   500: pop
    //   501: aload_1
    //   502: astore 4
    //   504: aload 6
    //   506: aload 5
    //   508: invokevirtual 720	java/lang/Exception:toString	()Ljava/lang/String;
    //   511: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   514: pop
    //   515: aload_1
    //   516: astore 4
    //   518: ldc 116
    //   520: aload 6
    //   522: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   525: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   528: aload_1
    //   529: ifnull +49 -> 578
    //   532: aload_1
    //   533: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   536: goto +42 -> 578
    //   539: astore_1
    //   540: new 134	java/lang/StringBuilder
    //   543: dup
    //   544: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   547: astore 4
    //   549: aload 4
    //   551: ldc_w 935
    //   554: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   557: pop
    //   558: aload 4
    //   560: aload_1
    //   561: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   564: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   567: pop
    //   568: ldc 116
    //   570: aload 4
    //   572: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   575: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   578: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   581: invokevirtual 931	java/util/concurrent/locks/ReentrantLock:isHeldByCurrentThread	()Z
    //   584: ifeq +48 -> 632
    //   587: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   590: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   593: goto +39 -> 632
    //   596: astore_1
    //   597: new 134	java/lang/StringBuilder
    //   600: dup
    //   601: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   604: astore 4
    //   606: aload 4
    //   608: ldc_w 933
    //   611: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   614: pop
    //   615: aload 4
    //   617: aload_1
    //   618: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   621: pop
    //   622: ldc 116
    //   624: aload 4
    //   626: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   629: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   632: aload_0
    //   633: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   636: iconst_0
    //   637: ireturn
    //   638: aload 4
    //   640: ifnull +52 -> 692
    //   643: aload 4
    //   645: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   648: goto +44 -> 692
    //   651: astore 4
    //   653: new 134	java/lang/StringBuilder
    //   656: dup
    //   657: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   660: astore 5
    //   662: aload 5
    //   664: ldc_w 935
    //   667: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   670: pop
    //   671: aload 5
    //   673: aload 4
    //   675: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   678: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   681: pop
    //   682: ldc 116
    //   684: aload 5
    //   686: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   689: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   692: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   695: invokevirtual 931	java/util/concurrent/locks/ReentrantLock:isHeldByCurrentThread	()Z
    //   698: ifeq +50 -> 748
    //   701: getstatic 35	com/tencent/smtt/downloader/e:jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock	Ljava/util/concurrent/locks/ReentrantLock;
    //   704: invokevirtual 558	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   707: goto +41 -> 748
    //   710: astore 4
    //   712: new 134	java/lang/StringBuilder
    //   715: dup
    //   716: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   719: astore 5
    //   721: aload 5
    //   723: ldc_w 933
    //   726: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   729: pop
    //   730: aload 5
    //   732: aload 4
    //   734: invokevirtual 224	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   737: pop
    //   738: ldc 116
    //   740: aload 5
    //   742: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   745: invokestatic 270	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   748: aload_0
    //   749: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   752: aload_1
    //   753: athrow
    //   754: aload_0
    //   755: invokevirtual 561	com/tencent/smtt/downloader/e:a	()V
    //   758: iconst_0
    //   759: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	760	0	this	e
    //   0	760	1	paramContext	Context
    //   345	106	2	i	int
    //   16	76	3	bool	boolean
    //   24	620	4	localObject1	Object
    //   651	23	4	localIOException	java.io.IOException
    //   710	23	4	localThrowable	Throwable
    //   60	109	5	localObject2	Object
    //   452	6	5	localObject3	Object
    //   463	1	5	localException1	Exception
    //   472	35	5	localException2	Exception
    //   660	81	5	localStringBuilder1	StringBuilder
    //   57	464	6	localStringBuilder2	StringBuilder
    //   166	33	7	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   95	110	113	java/lang/Throwable
    //   213	217	220	java/io/IOException
    //   259	274	277	java/lang/Throwable
    //   346	350	353	java/io/IOException
    //   392	407	410	java/lang/Throwable
    //   188	208	452	finally
    //   319	346	452	finally
    //   188	208	463	java/lang/Exception
    //   319	346	463	java/lang/Exception
    //   66	82	468	finally
    //   86	91	468	finally
    //   159	168	468	finally
    //   172	188	468	finally
    //   480	489	468	finally
    //   492	501	468	finally
    //   504	515	468	finally
    //   518	528	468	finally
    //   66	82	472	java/lang/Exception
    //   86	91	472	java/lang/Exception
    //   159	168	472	java/lang/Exception
    //   172	188	472	java/lang/Exception
    //   532	536	539	java/io/IOException
    //   578	593	596	java/lang/Throwable
    //   643	648	651	java/io/IOException
    //   692	707	710	java/lang/Throwable
  }
  
  File b(Context paramContext)
  {
    return a(null, paramContext);
  }
  
  boolean b(Context paramContext)
  {
    return new File(b(paramContext), "tbs.conf").exists();
  }
  
  /* Error */
  int c(Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: aload 4
    //   8: astore_3
    //   9: new 125	java/io/File
    //   12: dup
    //   13: aload_0
    //   14: aload_1
    //   15: iconst_3
    //   16: iconst_0
    //   17: invokevirtual 820	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;IZ)Ljava/io/File;
    //   20: ldc_w 362
    //   23: invokespecial 202	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   26: astore_1
    //   27: aload 4
    //   29: astore_3
    //   30: aload_1
    //   31: invokevirtual 129	java/io/File:exists	()Z
    //   34: ifne +5 -> 39
    //   37: iconst_0
    //   38: ireturn
    //   39: aload 4
    //   41: astore_3
    //   42: new 334	java/util/Properties
    //   45: dup
    //   46: invokespecial 335	java/util/Properties:<init>	()V
    //   49: astore 6
    //   51: aload 4
    //   53: astore_3
    //   54: new 337	java/io/BufferedInputStream
    //   57: dup
    //   58: new 339	java/io/FileInputStream
    //   61: dup
    //   62: aload_1
    //   63: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   66: invokespecial 344	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   69: astore_1
    //   70: aload 6
    //   72: aload_1
    //   73: invokevirtual 347	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   76: aload_1
    //   77: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   80: aload 6
    //   82: ldc_w 785
    //   85: invokevirtual 786	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   88: astore_3
    //   89: aload_3
    //   90: ifnonnull +46 -> 136
    //   93: aload_1
    //   94: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   97: iconst_0
    //   98: ireturn
    //   99: astore_1
    //   100: new 134	java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   107: astore_3
    //   108: aload_3
    //   109: ldc_w 954
    //   112: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: pop
    //   116: aload_3
    //   117: aload_1
    //   118: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   121: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: ldc 116
    //   127: aload_3
    //   128: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   131: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   134: iconst_0
    //   135: ireturn
    //   136: aload_3
    //   137: invokestatic 794	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   140: istore_2
    //   141: aload_1
    //   142: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   145: iload_2
    //   146: ireturn
    //   147: astore_1
    //   148: new 134	java/lang/StringBuilder
    //   151: dup
    //   152: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   155: astore_3
    //   156: aload_3
    //   157: ldc_w 954
    //   160: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: aload_3
    //   165: aload_1
    //   166: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   169: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: pop
    //   173: ldc 116
    //   175: aload_3
    //   176: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   179: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   182: iload_2
    //   183: ireturn
    //   184: astore 4
    //   186: aload_1
    //   187: astore_3
    //   188: aload 4
    //   190: astore_1
    //   191: goto +111 -> 302
    //   194: astore 4
    //   196: goto +12 -> 208
    //   199: astore_1
    //   200: goto +102 -> 302
    //   203: astore 4
    //   205: aload 5
    //   207: astore_1
    //   208: aload_1
    //   209: astore_3
    //   210: new 134	java/lang/StringBuilder
    //   213: dup
    //   214: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   217: astore 5
    //   219: aload_1
    //   220: astore_3
    //   221: aload 5
    //   223: ldc_w 956
    //   226: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   229: pop
    //   230: aload_1
    //   231: astore_3
    //   232: aload 5
    //   234: aload 4
    //   236: invokevirtual 720	java/lang/Exception:toString	()Ljava/lang/String;
    //   239: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   242: pop
    //   243: aload_1
    //   244: astore_3
    //   245: ldc 116
    //   247: aload 5
    //   249: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   252: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   255: aload_1
    //   256: ifnull +44 -> 300
    //   259: aload_1
    //   260: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   263: iconst_0
    //   264: ireturn
    //   265: astore_1
    //   266: new 134	java/lang/StringBuilder
    //   269: dup
    //   270: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   273: astore_3
    //   274: aload_3
    //   275: ldc_w 954
    //   278: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: pop
    //   282: aload_3
    //   283: aload_1
    //   284: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   287: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   290: pop
    //   291: ldc 116
    //   293: aload_3
    //   294: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   300: iconst_0
    //   301: ireturn
    //   302: aload_3
    //   303: ifnull +49 -> 352
    //   306: aload_3
    //   307: invokevirtual 350	java/io/BufferedInputStream:close	()V
    //   310: goto +42 -> 352
    //   313: astore_3
    //   314: new 134	java/lang/StringBuilder
    //   317: dup
    //   318: invokespecial 135	java/lang/StringBuilder:<init>	()V
    //   321: astore 4
    //   323: aload 4
    //   325: ldc_w 954
    //   328: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: pop
    //   332: aload 4
    //   334: aload_3
    //   335: invokevirtual 789	java/io/IOException:toString	()Ljava/lang/String;
    //   338: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   341: pop
    //   342: ldc 116
    //   344: aload 4
    //   346: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   349: invokestatic 123	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   352: aload_1
    //   353: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	354	0	this	e
    //   0	354	1	paramContext	Context
    //   140	43	2	i	int
    //   8	299	3	localObject1	Object
    //   313	22	3	localIOException	java.io.IOException
    //   4	48	4	localObject2	Object
    //   184	5	4	localObject3	Object
    //   194	1	4	localException1	Exception
    //   203	32	4	localException2	Exception
    //   321	24	4	localStringBuilder1	StringBuilder
    //   1	247	5	localStringBuilder2	StringBuilder
    //   49	32	6	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   93	97	99	java/io/IOException
    //   141	145	147	java/io/IOException
    //   70	89	184	finally
    //   136	141	184	finally
    //   70	89	194	java/lang/Exception
    //   136	141	194	java/lang/Exception
    //   9	27	199	finally
    //   30	37	199	finally
    //   42	51	199	finally
    //   54	70	199	finally
    //   210	219	199	finally
    //   221	230	199	finally
    //   232	243	199	finally
    //   245	255	199	finally
    //   9	27	203	java/lang/Exception
    //   30	37	203	java/lang/Exception
    //   42	51	203	java/lang/Exception
    //   54	70	203	java/lang/Exception
    //   259	263	265	java/io/IOException
    //   306	310	313	java/io/IOException
  }
  
  File c(Context paramContext)
  {
    paramContext = new File(a.a(paramContext), "share");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext;
  }
  
  boolean c(Context paramContext)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getTbsInstallingFileLock,current Thread Id=");
      localStringBuilder.append(Thread.currentThread().getId());
      localStringBuilder.append(",");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      h.d("TbsInstaller", localStringBuilder.toString());
      if (this.jdField_a_of_type_Int > 0)
      {
        h.a("TbsInstaller", "getTbsInstallingFileLock success,is cached= true");
        this.jdField_a_of_type_Int += 1;
        return true;
      }
      this.jdField_a_of_type_JavaIoFileOutputStream = FileUtil.a(paramContext, true, "tbslock.txt");
      if (this.jdField_a_of_type_JavaIoFileOutputStream != null)
      {
        this.jdField_a_of_type_JavaNioChannelsFileLock = FileUtil.a(paramContext, this.jdField_a_of_type_JavaIoFileOutputStream);
        if (this.jdField_a_of_type_JavaNioChannelsFileLock == null)
        {
          h.a("TbsInstaller", "getTbsInstallingFileLock tbsFileLockFileLock == null");
          return false;
        }
        h.a("TbsInstaller", "getTbsInstallingFileLock success,is cached= false");
        this.jdField_a_of_type_Int += 1;
        return true;
      }
      h.a("TbsInstaller", "getTbsInstallingFileLock get install fos failed");
      return false;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */