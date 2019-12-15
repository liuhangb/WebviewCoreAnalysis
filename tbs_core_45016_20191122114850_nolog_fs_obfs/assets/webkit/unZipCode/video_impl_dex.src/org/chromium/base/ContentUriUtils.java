package org.chromium.base;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import java.io.File;
import java.io.IOException;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public abstract class ContentUriUtils
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static FileProviderUtil jdField_a_of_type_OrgChromiumBaseContentUriUtils$FileProviderUtil;
  
  /* Error */
  private static AssetFileDescriptor a(String paramString)
  {
    // Byte code:
    //   0: invokestatic 33	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   3: invokevirtual 39	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   6: astore_3
    //   7: aload_0
    //   8: invokestatic 45	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   11: astore_0
    //   12: aload_0
    //   13: invokestatic 48	org/chromium/base/ContentUriUtils:a	(Landroid/net/Uri;)Z
    //   16: ifeq +65 -> 81
    //   19: aload_3
    //   20: aload_0
    //   21: ldc 50
    //   23: invokevirtual 56	android/content/ContentResolver:getStreamTypes	(Landroid/net/Uri;Ljava/lang/String;)[Ljava/lang/String;
    //   26: astore 4
    //   28: aload 4
    //   30: ifnull +78 -> 108
    //   33: aload 4
    //   35: arraylength
    //   36: ifle +72 -> 108
    //   39: aload_3
    //   40: aload_0
    //   41: aload 4
    //   43: iconst_0
    //   44: aaload
    //   45: aconst_null
    //   46: invokevirtual 60	android/content/ContentResolver:openTypedAssetFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/Bundle;)Landroid/content/res/AssetFileDescriptor;
    //   49: astore_0
    //   50: aload_0
    //   51: ifnull +66 -> 117
    //   54: aload_0
    //   55: invokevirtual 66	android/content/res/AssetFileDescriptor:getStartOffset	()J
    //   58: lstore_1
    //   59: lload_1
    //   60: lconst_0
    //   61: lcmp
    //   62: ifne +5 -> 67
    //   65: aload_0
    //   66: areturn
    //   67: aload_0
    //   68: invokevirtual 69	android/content/res/AssetFileDescriptor:close	()V
    //   71: new 23	java/lang/SecurityException
    //   74: dup
    //   75: ldc 71
    //   77: invokespecial 74	java/lang/SecurityException:<init>	(Ljava/lang/String;)V
    //   80: athrow
    //   81: aload_3
    //   82: aload_0
    //   83: ldc 76
    //   85: invokevirtual 80	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;)Landroid/os/ParcelFileDescriptor;
    //   88: astore_0
    //   89: aload_0
    //   90: ifnull +18 -> 108
    //   93: new 62	android/content/res/AssetFileDescriptor
    //   96: dup
    //   97: aload_0
    //   98: lconst_0
    //   99: ldc2_w 81
    //   102: invokespecial 85	android/content/res/AssetFileDescriptor:<init>	(Landroid/os/ParcelFileDescriptor;JJ)V
    //   105: astore_0
    //   106: aload_0
    //   107: areturn
    //   108: aconst_null
    //   109: areturn
    //   110: astore_0
    //   111: aconst_null
    //   112: areturn
    //   113: astore_0
    //   114: goto -43 -> 71
    //   117: aload_0
    //   118: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	paramString	String
    //   58	2	1	l	long
    //   6	76	3	localContentResolver	ContentResolver
    //   26	16	4	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   12	28	110	java/io/FileNotFoundException
    //   12	28	110	java/lang/SecurityException
    //   12	28	110	java/lang/Exception
    //   33	50	110	java/io/FileNotFoundException
    //   33	50	110	java/lang/SecurityException
    //   33	50	110	java/lang/Exception
    //   54	59	110	java/io/FileNotFoundException
    //   54	59	110	java/lang/SecurityException
    //   54	59	110	java/lang/Exception
    //   67	71	110	java/io/FileNotFoundException
    //   67	71	110	java/lang/SecurityException
    //   67	71	110	java/lang/Exception
    //   71	81	110	java/io/FileNotFoundException
    //   71	81	110	java/lang/SecurityException
    //   71	81	110	java/lang/Exception
    //   81	89	110	java/io/FileNotFoundException
    //   81	89	110	java/lang/SecurityException
    //   81	89	110	java/lang/Exception
    //   93	106	110	java/io/FileNotFoundException
    //   93	106	110	java/lang/SecurityException
    //   93	106	110	java/lang/Exception
    //   67	71	113	java/io/IOException
  }
  
  private static boolean a(Cursor paramCursor)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 24) {
      return false;
    }
    i = paramCursor.getColumnIndex("flags");
    boolean bool1 = bool2;
    if (i > -1)
    {
      bool1 = bool2;
      if ((paramCursor.getLong(i) & 0x200) != 0L) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  /* Error */
  private static boolean a(Uri paramUri)
  {
    // Byte code:
    //   0: getstatic 92	android/os/Build$VERSION:SDK_INT	I
    //   3: bipush 19
    //   5: if_icmpge +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: aload_0
    //   11: ifnonnull +5 -> 16
    //   14: iconst_0
    //   15: ireturn
    //   16: invokestatic 33	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   19: aload_0
    //   20: invokestatic 116	android/provider/DocumentsContract:isDocumentUri	(Landroid/content/Context;Landroid/net/Uri;)Z
    //   23: ifne +5 -> 28
    //   26: iconst_0
    //   27: ireturn
    //   28: invokestatic 33	org/chromium/base/ContextUtils:getApplicationContext	()Landroid/content/Context;
    //   31: invokevirtual 39	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   34: astore_2
    //   35: aload_2
    //   36: aload_0
    //   37: aconst_null
    //   38: aconst_null
    //   39: aconst_null
    //   40: aconst_null
    //   41: invokevirtual 120	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   44: astore_3
    //   45: aconst_null
    //   46: astore_2
    //   47: aload_3
    //   48: ifnull +86 -> 134
    //   51: aload_2
    //   52: astore_0
    //   53: aload_3
    //   54: invokeinterface 124 1 0
    //   59: iconst_1
    //   60: if_icmplt +74 -> 134
    //   63: aload_2
    //   64: astore_0
    //   65: aload_3
    //   66: invokeinterface 128 1 0
    //   71: pop
    //   72: aload_2
    //   73: astore_0
    //   74: aload_3
    //   75: invokestatic 130	org/chromium/base/ContentUriUtils:a	(Landroid/database/Cursor;)Z
    //   78: istore_1
    //   79: aload_3
    //   80: ifnull +9 -> 89
    //   83: aload_3
    //   84: invokeinterface 131 1 0
    //   89: iload_1
    //   90: ireturn
    //   91: astore_2
    //   92: goto +8 -> 100
    //   95: astore_2
    //   96: aload_2
    //   97: astore_0
    //   98: aload_2
    //   99: athrow
    //   100: aload_3
    //   101: ifnull +31 -> 132
    //   104: aload_0
    //   105: ifnull +21 -> 126
    //   108: aload_3
    //   109: invokeinterface 131 1 0
    //   114: goto +18 -> 132
    //   117: astore_3
    //   118: aload_0
    //   119: aload_3
    //   120: invokevirtual 135	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   123: goto +9 -> 132
    //   126: aload_3
    //   127: invokeinterface 131 1 0
    //   132: aload_2
    //   133: athrow
    //   134: aload_3
    //   135: ifnull +9 -> 144
    //   138: aload_3
    //   139: invokeinterface 131 1 0
    //   144: iconst_0
    //   145: ireturn
    //   146: astore_0
    //   147: iconst_0
    //   148: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	paramUri	Uri
    //   78	12	1	bool	boolean
    //   34	39	2	localContentResolver	ContentResolver
    //   91	1	2	localObject	Object
    //   95	38	2	localThrowable1	Throwable
    //   44	65	3	localCursor	Cursor
    //   117	22	3	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   53	63	91	finally
    //   65	72	91	finally
    //   74	79	91	finally
    //   98	100	91	finally
    //   53	63	95	java/lang/Throwable
    //   65	72	95	java/lang/Throwable
    //   74	79	95	java/lang/Throwable
    //   108	114	117	java/lang/Throwable
    //   35	45	146	java/lang/NullPointerException
    //   83	89	146	java/lang/NullPointerException
    //   108	114	146	java/lang/NullPointerException
    //   118	123	146	java/lang/NullPointerException
    //   126	132	146	java/lang/NullPointerException
    //   132	134	146	java/lang/NullPointerException
    //   138	144	146	java/lang/NullPointerException
  }
  
  @CalledByNative
  public static boolean contentUriExists(String paramString)
  {
    try
    {
      paramString = a(paramString);
      boolean bool;
      if (paramString != null) {
        bool = true;
      } else {
        bool = false;
      }
      if (paramString != null) {}
      return bool;
    }
    finally
    {
      try
      {
        paramString.close();
        return bool;
      }
      catch (IOException paramString) {}
      paramString = finally;
    }
  }
  
  public static Uri getContentUriFromFile(File paramFile)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_OrgChromiumBaseContentUriUtils$FileProviderUtil != null)
      {
        paramFile = jdField_a_of_type_OrgChromiumBaseContentUriUtils$FileProviderUtil.getContentUriFromFile(paramFile);
        return paramFile;
      }
      return null;
    }
  }
  
  /* Error */
  public static String getDisplayName(Uri paramUri, Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +6 -> 7
    //   4: ldc -105
    //   6: areturn
    //   7: aload_1
    //   8: invokevirtual 39	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   11: astore 7
    //   13: aload 7
    //   15: aload_0
    //   16: aconst_null
    //   17: aconst_null
    //   18: aconst_null
    //   19: aconst_null
    //   20: invokevirtual 120	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   23: astore 6
    //   25: aconst_null
    //   26: astore 4
    //   28: aload 6
    //   30: ifnull +245 -> 275
    //   33: aload 4
    //   35: astore_1
    //   36: aload 6
    //   38: invokeinterface 124 1 0
    //   43: iconst_1
    //   44: if_icmplt +231 -> 275
    //   47: aload 4
    //   49: astore_1
    //   50: aload 6
    //   52: invokeinterface 128 1 0
    //   57: pop
    //   58: aload 4
    //   60: astore_1
    //   61: aload 6
    //   63: aload_2
    //   64: invokeinterface 100 2 0
    //   69: istore_3
    //   70: iload_3
    //   71: iconst_m1
    //   72: if_icmpne +18 -> 90
    //   75: aload 6
    //   77: ifnull +10 -> 87
    //   80: aload 6
    //   82: invokeinterface 131 1 0
    //   87: ldc -105
    //   89: areturn
    //   90: aload 4
    //   92: astore_1
    //   93: aload 6
    //   95: iload_3
    //   96: invokeinterface 155 2 0
    //   101: astore 5
    //   103: aload 5
    //   105: astore_2
    //   106: aload 4
    //   108: astore_1
    //   109: aload 6
    //   111: invokestatic 130	org/chromium/base/ContentUriUtils:a	(Landroid/database/Cursor;)Z
    //   114: ifeq +101 -> 215
    //   117: aload 4
    //   119: astore_1
    //   120: aload 7
    //   122: aload_0
    //   123: ldc 50
    //   125: invokevirtual 56	android/content/ContentResolver:getStreamTypes	(Landroid/net/Uri;Ljava/lang/String;)[Ljava/lang/String;
    //   128: astore_0
    //   129: aload 5
    //   131: astore_2
    //   132: aload_0
    //   133: ifnull +82 -> 215
    //   136: aload 5
    //   138: astore_2
    //   139: aload 4
    //   141: astore_1
    //   142: aload_0
    //   143: arraylength
    //   144: ifle +71 -> 215
    //   147: aload 4
    //   149: astore_1
    //   150: invokestatic 161	com/tencent/tbs/core/webkit/MimeTypeMap:getSingleton	()Lcom/tencent/tbs/core/webkit/MimeTypeMap;
    //   153: aload_0
    //   154: iconst_0
    //   155: aaload
    //   156: invokevirtual 165	com/tencent/tbs/core/webkit/MimeTypeMap:getExtensionFromMimeType	(Ljava/lang/String;)Ljava/lang/String;
    //   159: astore_0
    //   160: aload 5
    //   162: astore_2
    //   163: aload_0
    //   164: ifnull +51 -> 215
    //   167: aload 4
    //   169: astore_1
    //   170: new 167	java/lang/StringBuilder
    //   173: dup
    //   174: invokespecial 168	java/lang/StringBuilder:<init>	()V
    //   177: astore_2
    //   178: aload 4
    //   180: astore_1
    //   181: aload_2
    //   182: aload 5
    //   184: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: aload 4
    //   190: astore_1
    //   191: aload_2
    //   192: ldc -82
    //   194: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: aload 4
    //   200: astore_1
    //   201: aload_2
    //   202: aload_0
    //   203: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: pop
    //   207: aload 4
    //   209: astore_1
    //   210: aload_2
    //   211: invokevirtual 178	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: astore_2
    //   215: aload 6
    //   217: ifnull +10 -> 227
    //   220: aload 6
    //   222: invokeinterface 131 1 0
    //   227: aload_2
    //   228: areturn
    //   229: astore_0
    //   230: goto +8 -> 238
    //   233: astore_0
    //   234: aload_0
    //   235: astore_1
    //   236: aload_0
    //   237: athrow
    //   238: aload 6
    //   240: ifnull +33 -> 273
    //   243: aload_1
    //   244: ifnull +22 -> 266
    //   247: aload 6
    //   249: invokeinterface 131 1 0
    //   254: goto +19 -> 273
    //   257: astore_2
    //   258: aload_1
    //   259: aload_2
    //   260: invokevirtual 135	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   263: goto +10 -> 273
    //   266: aload 6
    //   268: invokeinterface 131 1 0
    //   273: aload_0
    //   274: athrow
    //   275: aload 6
    //   277: ifnull +10 -> 287
    //   280: aload 6
    //   282: invokeinterface 131 1 0
    //   287: ldc -105
    //   289: areturn
    //   290: ldc -105
    //   292: areturn
    //   293: astore_0
    //   294: goto -4 -> 290
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	297	0	paramUri	Uri
    //   0	297	1	paramContext	Context
    //   0	297	2	paramString	String
    //   69	27	3	i	int
    //   26	182	4	localObject	Object
    //   101	82	5	str	String
    //   23	258	6	localCursor	Cursor
    //   11	110	7	localContentResolver	ContentResolver
    // Exception table:
    //   from	to	target	type
    //   36	47	229	finally
    //   50	58	229	finally
    //   61	70	229	finally
    //   93	103	229	finally
    //   109	117	229	finally
    //   120	129	229	finally
    //   142	147	229	finally
    //   150	160	229	finally
    //   170	178	229	finally
    //   181	188	229	finally
    //   191	198	229	finally
    //   201	207	229	finally
    //   210	215	229	finally
    //   236	238	229	finally
    //   36	47	233	java/lang/Throwable
    //   50	58	233	java/lang/Throwable
    //   61	70	233	java/lang/Throwable
    //   93	103	233	java/lang/Throwable
    //   109	117	233	java/lang/Throwable
    //   120	129	233	java/lang/Throwable
    //   142	147	233	java/lang/Throwable
    //   150	160	233	java/lang/Throwable
    //   170	178	233	java/lang/Throwable
    //   181	188	233	java/lang/Throwable
    //   191	198	233	java/lang/Throwable
    //   201	207	233	java/lang/Throwable
    //   210	215	233	java/lang/Throwable
    //   247	254	257	java/lang/Throwable
    //   13	25	293	java/lang/NullPointerException
    //   80	87	293	java/lang/NullPointerException
    //   220	227	293	java/lang/NullPointerException
    //   247	254	293	java/lang/NullPointerException
    //   258	263	293	java/lang/NullPointerException
    //   266	273	293	java/lang/NullPointerException
    //   273	275	293	java/lang/NullPointerException
    //   280	287	293	java/lang/NullPointerException
  }
  
  @CalledByNative
  public static String getMimeType(String paramString)
  {
    ContentResolver localContentResolver = ContextUtils.getApplicationContext().getContentResolver();
    paramString = Uri.parse(paramString);
    if (a(paramString))
    {
      paramString = localContentResolver.getStreamTypes(paramString, "*/*");
      if ((paramString != null) && (paramString.length > 0)) {
        return paramString[0];
      }
      return null;
    }
    return localContentResolver.getType(paramString);
  }
  
  @CalledByNative
  public static int openContentUriForRead(String paramString)
  {
    paramString = a(paramString);
    if (paramString != null) {
      return X5ApiCompatibilityUtils.detachFd(paramString.getParcelFileDescriptor());
    }
    return -1;
  }
  
  public static void setFileProviderUtil(FileProviderUtil paramFileProviderUtil)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      jdField_a_of_type_OrgChromiumBaseContentUriUtils$FileProviderUtil = paramFileProviderUtil;
      return;
    }
  }
  
  public static abstract interface FileProviderUtil
  {
    public abstract Uri getContentUriFromFile(File paramFile);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ContentUriUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */