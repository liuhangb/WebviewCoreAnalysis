package com.tencent.mtt.base.utils;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.utils.QBSoLoader;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class FileListJNI
{
  public static boolean isLoadLibrary = true;
  
  static
  {
    try
    {
      String str = QBSoLoader.tinkerSoLoadLibraryPath("FileNDK");
      if (TextUtils.isEmpty(str))
      {
        System.loadLibrary("FileNDK");
        return;
      }
      System.load(str);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      isLoadLibrary = false;
    }
  }
  
  public static native ArrayList<String> fileList(String paramString);
  
  public static File[] fileList(String paramString, FileFilter paramFileFilter)
  {
    ArrayList localArrayList1 = securityFileList(paramString);
    ArrayList localArrayList2 = new ArrayList();
    int i = 0;
    while (i < localArrayList1.size())
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(File.separator);
      ((StringBuilder)localObject).append((String)localArrayList1.get(i));
      localObject = new File(((StringBuilder)localObject).toString());
      if ((paramFileFilter == null) || (paramFileFilter.accept((File)localObject))) {
        localArrayList2.add(localObject);
      }
      i += 1;
    }
    return (File[])localArrayList2.toArray(new File[localArrayList2.size()]);
  }
  
  public static File[] fileList(String paramString, FilenameFilter paramFilenameFilter)
  {
    File localFile = new File(paramString);
    ArrayList localArrayList1 = securityFileList(paramString);
    ArrayList localArrayList2 = new ArrayList();
    int i = 0;
    while (i < localArrayList1.size())
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(File.separator);
      ((StringBuilder)localObject).append((String)localArrayList1.get(i));
      localObject = new File(((StringBuilder)localObject).toString());
      if ((paramFilenameFilter == null) || (paramFilenameFilter.accept(localFile, (String)localArrayList1.get(i)))) {
        localArrayList2.add(localObject);
      }
      i += 1;
    }
    return (File[])localArrayList2.toArray(new File[localArrayList2.size()]);
  }
  
  public static ArrayList<String> securityFileList(String paramString)
  {
    return securityFileList(paramString, true);
  }
  
  public static ArrayList<String> securityFileList(String paramString, boolean paramBoolean)
  {
    if ((TextUtils.isEmpty(paramString)) || ((paramBoolean) && (!new File(paramString).exists())) || ((isLoadLibrary) && (Build.VERSION.SDK_INT != 19))) {}
    try
    {
      localArrayList = fileList(paramString);
      return localArrayList;
    }
    catch (Throwable localThrowable)
    {
      ArrayList localArrayList;
      for (;;) {}
    }
    return slowlyFileList(paramString);
    if (Build.VERSION.SDK_INT == 19)
    {
      localArrayList = new ArrayList();
      paramString = new File(paramString).list();
      if (paramString != null) {
        localArrayList.addAll(Arrays.asList(paramString));
      }
      return localArrayList;
    }
    return slowlyFileList(paramString);
    return new ArrayList();
  }
  
  /* Error */
  public static ArrayList<String> slowlyFileList(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 25	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifne +207 -> 211
    //   7: new 65	java/io/File
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 81	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: invokevirtual 112	java/io/File:exists	()Z
    //   18: ifeq +193 -> 211
    //   21: new 65	java/io/File
    //   24: dup
    //   25: aload_0
    //   26: invokespecial 81	java/io/File:<init>	(Ljava/lang/String;)V
    //   29: invokevirtual 143	java/io/File:isDirectory	()Z
    //   32: ifne +6 -> 38
    //   35: goto +176 -> 211
    //   38: aconst_null
    //   39: astore_3
    //   40: aconst_null
    //   41: astore 4
    //   43: aconst_null
    //   44: astore_1
    //   45: new 51	java/util/ArrayList
    //   48: dup
    //   49: invokespecial 52	java/util/ArrayList:<init>	()V
    //   52: astore 5
    //   54: new 145	java/io/BufferedReader
    //   57: dup
    //   58: new 147	java/io/InputStreamReader
    //   61: dup
    //   62: invokestatic 153	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   65: iconst_2
    //   66: anewarray 75	java/lang/String
    //   69: dup
    //   70: iconst_0
    //   71: ldc -101
    //   73: aastore
    //   74: dup
    //   75: iconst_1
    //   76: aload_0
    //   77: aastore
    //   78: invokevirtual 159	java/lang/Runtime:exec	([Ljava/lang/String;)Ljava/lang/Process;
    //   81: invokevirtual 165	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   84: invokespecial 168	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   87: invokespecial 171	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   90: astore_0
    //   91: aload_0
    //   92: invokevirtual 174	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   95: astore_1
    //   96: aload_1
    //   97: ifnull +20 -> 117
    //   100: aload_1
    //   101: invokestatic 25	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   104: ifne -13 -> 91
    //   107: aload 5
    //   109: aload_1
    //   110: invokevirtual 91	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   113: pop
    //   114: goto -23 -> 91
    //   117: aload_0
    //   118: invokevirtual 177	java/io/BufferedReader:close	()V
    //   121: aload 5
    //   123: areturn
    //   124: astore_2
    //   125: aload_0
    //   126: astore_1
    //   127: aload_2
    //   128: astore_0
    //   129: goto +64 -> 193
    //   132: astore_2
    //   133: goto +14 -> 147
    //   136: astore_2
    //   137: goto +31 -> 168
    //   140: astore_0
    //   141: goto +52 -> 193
    //   144: astore_2
    //   145: aload_3
    //   146: astore_0
    //   147: aload_0
    //   148: astore_1
    //   149: aload_2
    //   150: invokevirtual 37	java/lang/Throwable:printStackTrace	()V
    //   153: aload_0
    //   154: ifnull +36 -> 190
    //   157: aload_0
    //   158: invokevirtual 177	java/io/BufferedReader:close	()V
    //   161: aload 5
    //   163: areturn
    //   164: astore_2
    //   165: aload 4
    //   167: astore_0
    //   168: aload_0
    //   169: astore_1
    //   170: aload_2
    //   171: invokevirtual 178	java/io/IOException:printStackTrace	()V
    //   174: aload_0
    //   175: ifnull +15 -> 190
    //   178: aload_0
    //   179: invokevirtual 177	java/io/BufferedReader:close	()V
    //   182: aload 5
    //   184: areturn
    //   185: astore_0
    //   186: aload_0
    //   187: invokevirtual 178	java/io/IOException:printStackTrace	()V
    //   190: aload 5
    //   192: areturn
    //   193: aload_1
    //   194: ifnull +15 -> 209
    //   197: aload_1
    //   198: invokevirtual 177	java/io/BufferedReader:close	()V
    //   201: goto +8 -> 209
    //   204: astore_1
    //   205: aload_1
    //   206: invokevirtual 178	java/io/IOException:printStackTrace	()V
    //   209: aload_0
    //   210: athrow
    //   211: new 51	java/util/ArrayList
    //   214: dup
    //   215: invokespecial 52	java/util/ArrayList:<init>	()V
    //   218: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	paramString	String
    //   44	154	1	str	String
    //   204	2	1	localIOException1	java.io.IOException
    //   124	4	2	localObject1	Object
    //   132	1	2	localThrowable1	Throwable
    //   136	1	2	localIOException2	java.io.IOException
    //   144	6	2	localThrowable2	Throwable
    //   164	7	2	localIOException3	java.io.IOException
    //   39	107	3	localObject2	Object
    //   41	125	4	localObject3	Object
    //   52	139	5	localArrayList	ArrayList
    // Exception table:
    //   from	to	target	type
    //   91	96	124	finally
    //   100	114	124	finally
    //   91	96	132	java/lang/Throwable
    //   100	114	132	java/lang/Throwable
    //   91	96	136	java/io/IOException
    //   100	114	136	java/io/IOException
    //   54	91	140	finally
    //   149	153	140	finally
    //   170	174	140	finally
    //   54	91	144	java/lang/Throwable
    //   54	91	164	java/io/IOException
    //   117	121	185	java/io/IOException
    //   157	161	185	java/io/IOException
    //   178	182	185	java/io/IOException
    //   197	201	204	java/io/IOException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\FileListJNI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */