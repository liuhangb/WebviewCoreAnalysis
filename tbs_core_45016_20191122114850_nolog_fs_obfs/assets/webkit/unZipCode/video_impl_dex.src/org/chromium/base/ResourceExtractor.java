package org.chromium.base;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ResourceExtractor
{
  private static ResourceExtractor jdField_a_of_type_OrgChromiumBaseResourceExtractor;
  private ExtractTask jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask;
  private final String[] jdField_a_of_type_ArrayOfJavaLangString = a();
  
  private File a()
  {
    return new File(PathUtils.getDataDirectory());
  }
  
  private static void a(File paramFile)
  {
    if (paramFile.exists()) {
      paramFile.delete();
    }
  }
  
  private void a(String[] paramArrayOfString)
  {
    a(new File(a(), "icudtl.dat"));
    a(new File(a(), "natives_blob.bin"));
    a(new File(a(), "snapshot_blob.bin"));
    if (paramArrayOfString != null)
    {
      int j = paramArrayOfString.length;
      int i = 0;
      while (i < j)
      {
        String str = paramArrayOfString[i];
        a(new File(b(), str));
        i += 1;
      }
    }
  }
  
  private static boolean a()
  {
    return get().jdField_a_of_type_ArrayOfJavaLangString.length == 0;
  }
  
  private static String[] a()
  {
    String str1 = LocaleUtils.getUpdatedLanguageForChromium(Locale.getDefault().getLanguage());
    ArrayList localArrayList = new ArrayList(6);
    String[] arrayOfString = BuildConfig.COMPRESSED_LOCALES;
    int j = arrayOfString.length;
    int i = 0;
    while (i < j)
    {
      String str2 = arrayOfString[i];
      if (str2.startsWith(str1))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(str2);
        localStringBuilder.append(".pak");
        localArrayList.add(localStringBuilder.toString());
      }
      i += 1;
    }
    if ((localArrayList.isEmpty()) && (BuildConfig.COMPRESSED_LOCALES.length > 0))
    {
      if ((!jdField_a_of_type_Boolean) && (!Arrays.asList(BuildConfig.COMPRESSED_LOCALES).contains("en-US"))) {
        throw new AssertionError();
      }
      localArrayList.add("en-US.pak");
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  private File b()
  {
    return new File(a(), "paks");
  }
  
  public static ResourceExtractor get()
  {
    if (jdField_a_of_type_OrgChromiumBaseResourceExtractor == null) {
      jdField_a_of_type_OrgChromiumBaseResourceExtractor = new ResourceExtractor();
    }
    return jdField_a_of_type_OrgChromiumBaseResourceExtractor;
  }
  
  public void addCompletionCallback(Runnable paramRunnable)
  {
    ThreadUtils.assertOnUiThread();
    Handler localHandler = new Handler(Looper.getMainLooper());
    if (a())
    {
      localHandler.post(paramRunnable);
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask.isCancelled())) {
      throw new AssertionError();
    }
    if (this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask.getStatus() == AsyncTask.Status.FINISHED)
    {
      localHandler.post(paramRunnable);
      return;
    }
    ExtractTask.a(this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask).add(paramRunnable);
  }
  
  public void startExtractingResources()
  {
    if (this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask != null) {
      return;
    }
    if (a()) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask = new ExtractTask(null);
    this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
  }
  
  public void waitForCompletion()
  {
    if (this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask != null) {
      if (a()) {
        return;
      }
    }
    try
    {
      this.jdField_a_of_type_OrgChromiumBaseResourceExtractor$ExtractTask.get();
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if (jdField_a_of_type_Boolean) {
      return;
    }
    throw new AssertionError();
  }
  
  private class ExtractTask
    extends AsyncTask<Void, Void, Void>
  {
    private final List<Runnable> jdField_a_of_type_JavaUtilList = new ArrayList();
    
    private ExtractTask() {}
    
    /* Error */
    private void a()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 16	org/chromium/base/ResourceExtractor$ExtractTask:jdField_a_of_type_OrgChromiumBaseResourceExtractor	Lorg/chromium/base/ResourceExtractor;
      //   4: invokestatic 34	org/chromium/base/ResourceExtractor:a	(Lorg/chromium/base/ResourceExtractor;)Ljava/io/File;
      //   7: astore 5
      //   9: aload 5
      //   11: invokevirtual 40	java/io/File:exists	()Z
      //   14: ifne +22 -> 36
      //   17: aload 5
      //   19: invokevirtual 43	java/io/File:mkdirs	()Z
      //   22: ifeq +6 -> 28
      //   25: goto +11 -> 36
      //   28: new 45	java/lang/RuntimeException
      //   31: dup
      //   32: invokespecial 46	java/lang/RuntimeException:<init>	()V
      //   35: athrow
      //   36: invokestatic 52	org/chromium/base/BuildInfo:getExtractedFileSuffix	()Ljava/lang/String;
      //   39: astore 6
      //   41: aload 5
      //   43: invokevirtual 56	java/io/File:list	()[Ljava/lang/String;
      //   46: astore 7
      //   48: iconst_0
      //   49: istore_3
      //   50: aload 7
      //   52: ifnull +8 -> 60
      //   55: iconst_1
      //   56: istore_1
      //   57: goto +5 -> 62
      //   60: iconst_0
      //   61: istore_1
      //   62: iload_1
      //   63: istore_2
      //   64: iload_1
      //   65: ifeq +87 -> 152
      //   68: aload 7
      //   70: invokestatic 62	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
      //   73: astore 8
      //   75: aload_0
      //   76: getfield 16	org/chromium/base/ResourceExtractor$ExtractTask:jdField_a_of_type_OrgChromiumBaseResourceExtractor	Lorg/chromium/base/ResourceExtractor;
      //   79: invokestatic 65	org/chromium/base/ResourceExtractor:a	(Lorg/chromium/base/ResourceExtractor;)[Ljava/lang/String;
      //   82: astore 9
      //   84: aload 9
      //   86: arraylength
      //   87: istore 4
      //   89: iconst_0
      //   90: istore_2
      //   91: iload_2
      //   92: iload 4
      //   94: if_icmpge +56 -> 150
      //   97: aload 9
      //   99: iload_2
      //   100: aaload
      //   101: astore 10
      //   103: new 67	java/lang/StringBuilder
      //   106: dup
      //   107: invokespecial 68	java/lang/StringBuilder:<init>	()V
      //   110: astore 11
      //   112: aload 11
      //   114: aload 10
      //   116: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   119: pop
      //   120: aload 11
      //   122: aload 6
      //   124: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   127: pop
      //   128: iload_1
      //   129: aload 8
      //   131: aload 11
      //   133: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   136: invokeinterface 81 2 0
      //   141: iand
      //   142: istore_1
      //   143: iload_2
      //   144: iconst_1
      //   145: iadd
      //   146: istore_2
      //   147: goto -56 -> 91
      //   150: iload_1
      //   151: istore_2
      //   152: iload_2
      //   153: ifeq +4 -> 157
      //   156: return
      //   157: aload_0
      //   158: getfield 16	org/chromium/base/ResourceExtractor$ExtractTask:jdField_a_of_type_OrgChromiumBaseResourceExtractor	Lorg/chromium/base/ResourceExtractor;
      //   161: aload 7
      //   163: invokestatic 84	org/chromium/base/ResourceExtractor:a	(Lorg/chromium/base/ResourceExtractor;[Ljava/lang/String;)V
      //   166: invokestatic 90	org/chromium/base/ContextUtils:getApplicationAssets	()Landroid/content/res/AssetManager;
      //   169: astore 7
      //   171: sipush 16384
      //   174: newarray <illegal type>
      //   176: astore 8
      //   178: aload_0
      //   179: getfield 16	org/chromium/base/ResourceExtractor$ExtractTask:jdField_a_of_type_OrgChromiumBaseResourceExtractor	Lorg/chromium/base/ResourceExtractor;
      //   182: invokestatic 65	org/chromium/base/ResourceExtractor:a	(Lorg/chromium/base/ResourceExtractor;)[Ljava/lang/String;
      //   185: astore 9
      //   187: aload 9
      //   189: arraylength
      //   190: istore_2
      //   191: iload_3
      //   192: istore_1
      //   193: iload_1
      //   194: iload_2
      //   195: if_icmpge +107 -> 302
      //   198: aload 9
      //   200: iload_1
      //   201: aaload
      //   202: astore 10
      //   204: new 67	java/lang/StringBuilder
      //   207: dup
      //   208: invokespecial 68	java/lang/StringBuilder:<init>	()V
      //   211: astore 11
      //   213: aload 11
      //   215: aload 10
      //   217: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   220: pop
      //   221: aload 11
      //   223: aload 6
      //   225: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   228: pop
      //   229: new 36	java/io/File
      //   232: dup
      //   233: aload 5
      //   235: aload 11
      //   237: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   240: invokespecial 93	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   243: astore 11
      //   245: ldc 95
      //   247: invokestatic 101	org/chromium/base/TraceEvent:begin	(Ljava/lang/String;)V
      //   250: aload_0
      //   251: aload 7
      //   253: aload 10
      //   255: invokevirtual 107	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
      //   258: aload 11
      //   260: aload 8
      //   262: invokespecial 110	org/chromium/base/ResourceExtractor$ExtractTask:a	(Ljava/io/InputStream;Ljava/io/File;[B)V
      //   265: ldc 95
      //   267: invokestatic 113	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
      //   270: iload_1
      //   271: iconst_1
      //   272: iadd
      //   273: istore_1
      //   274: goto -81 -> 193
      //   277: astore 5
      //   279: goto +15 -> 294
      //   282: astore 5
      //   284: new 45	java/lang/RuntimeException
      //   287: dup
      //   288: aload 5
      //   290: invokespecial 116	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
      //   293: athrow
      //   294: ldc 95
      //   296: invokestatic 113	org/chromium/base/TraceEvent:end	(Ljava/lang/String;)V
      //   299: aload 5
      //   301: athrow
      //   302: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	303	0	this	ExtractTask
      //   56	217	1	i	int
      //   273	1	1	j	int
      //   63	83	2	k	int
      //   146	50	2	m	int
      //   49	143	3	n	int
      //   87	8	4	i1	int
      //   7	227	5	localFile	File
      //   277	1	5	localObject1	Object
      //   282	18	5	localIOException	java.io.IOException
      //   39	185	6	str1	String
      //   46	206	7	localObject2	Object
      //   73	188	8	localObject3	Object
      //   82	117	9	arrayOfString	String[]
      //   101	153	10	str2	String
      //   110	149	11	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   250	265	277	finally
      //   284	294	277	finally
      //   250	265	282	java/io/IOException
    }
    
    /* Error */
    private void a(java.io.InputStream paramInputStream, File paramFile, byte[] paramArrayOfByte)
      throws java.io.IOException
    {
      // Byte code:
      //   0: new 67	java/lang/StringBuilder
      //   3: dup
      //   4: invokespecial 68	java/lang/StringBuilder:<init>	()V
      //   7: astore 5
      //   9: aload 5
      //   11: aload_2
      //   12: invokevirtual 119	java/io/File:getPath	()Ljava/lang/String;
      //   15: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   18: pop
      //   19: aload 5
      //   21: ldc 121
      //   23: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   26: pop
      //   27: new 36	java/io/File
      //   30: dup
      //   31: aload 5
      //   33: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   36: invokespecial 123	java/io/File:<init>	(Ljava/lang/String;)V
      //   39: astore 7
      //   41: aconst_null
      //   42: astore 5
      //   44: new 125	java/io/FileOutputStream
      //   47: dup
      //   48: aload 7
      //   50: invokespecial 128	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   53: astore 6
      //   55: aload_1
      //   56: aload_3
      //   57: iconst_0
      //   58: sipush 16384
      //   61: invokevirtual 134	java/io/InputStream:read	([BII)I
      //   64: istore 4
      //   66: iload 4
      //   68: iconst_m1
      //   69: if_icmpeq +15 -> 84
      //   72: aload 6
      //   74: aload_3
      //   75: iconst_0
      //   76: iload 4
      //   78: invokevirtual 140	java/io/OutputStream:write	([BII)V
      //   81: goto -26 -> 55
      //   84: aload 6
      //   86: invokestatic 146	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   89: aload_1
      //   90: invokestatic 146	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   93: aload 7
      //   95: aload_2
      //   96: invokevirtual 150	java/io/File:renameTo	(Ljava/io/File;)Z
      //   99: ifeq +4 -> 103
      //   102: return
      //   103: new 31	java/io/IOException
      //   106: dup
      //   107: invokespecial 151	java/io/IOException:<init>	()V
      //   110: athrow
      //   111: astore_3
      //   112: aload 6
      //   114: astore_2
      //   115: goto +7 -> 122
      //   118: astore_3
      //   119: aload 5
      //   121: astore_2
      //   122: aload_2
      //   123: invokestatic 146	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   126: aload_1
      //   127: invokestatic 146	org/chromium/base/StreamUtil:closeQuietly	(Ljava/io/Closeable;)V
      //   130: aload_3
      //   131: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	132	0	this	ExtractTask
      //   0	132	1	paramInputStream	java.io.InputStream
      //   0	132	2	paramFile	File
      //   0	132	3	paramArrayOfByte	byte[]
      //   64	13	4	i	int
      //   7	113	5	localStringBuilder	StringBuilder
      //   53	60	6	localFileOutputStream	java.io.FileOutputStream
      //   39	55	7	localFile	File
      // Exception table:
      //   from	to	target	type
      //   55	66	111	finally
      //   72	81	111	finally
      //   44	55	118	finally
    }
    
    private void b()
    {
      int i = 0;
      while (i < this.jdField_a_of_type_JavaUtilList.size())
      {
        ((Runnable)this.jdField_a_of_type_JavaUtilList.get(i)).run();
        i += 1;
      }
      this.jdField_a_of_type_JavaUtilList.clear();
    }
    
    protected Void a(Void... paramVarArgs)
    {
      TraceEvent.begin("ResourceExtractor.ExtractTask.doInBackground");
      try
      {
        a();
        return null;
      }
      finally
      {
        TraceEvent.end("ResourceExtractor.ExtractTask.doInBackground");
      }
    }
    
    protected void a(Void paramVoid)
    {
      TraceEvent.begin("ResourceExtractor.ExtractTask.onPostExecute");
      try
      {
        b();
        return;
      }
      finally
      {
        TraceEvent.end("ResourceExtractor.ExtractTask.onPostExecute");
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ResourceExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */