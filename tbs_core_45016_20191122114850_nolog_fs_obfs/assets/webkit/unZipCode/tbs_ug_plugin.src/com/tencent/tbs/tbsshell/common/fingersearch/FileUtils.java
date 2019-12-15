package com.tencent.tbs.tbsshell.common.fingersearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.tencent.common.http.ContentType;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.tbs.common.ui.MttToaster;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils
{
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  private static final String DIR_CACHE = ".cache";
  public static final String DIR_DATA = "data";
  public static final String DIR_DYNAMIC_JAR_OUTPUT = "dynamic_jar_output";
  public static final String DIR_EXT_MAIN = "QQBrowser";
  private static final String DIR_SYS_PHOTO = "DCIM";
  public static final String DL_FILE_SUFFIX = ".qbdltmp";
  public static int ERR_BASE = 0;
  public static int ERR_SAVE_IMAGE_FAILED = 0;
  public static int ERR_SDCARD_NOT_AVAILABLE = 0;
  private static Pattern FILE_NAME_PATTERN = Pattern.compile("^(.*)\\((\\d+)\\)$", 2);
  private static Pattern FILE_NAME_VALID_PATTERN = Pattern.compile("[\\\\\\/\\:\\*\\?\\\"\\|\\<\\>]", 2);
  public static int SUCCESS = 0;
  private static final String TAG = "FileUtils";
  static final String TES_DATA_SHARE_FOLDER_NAME = "share";
  static final String TES_FOLDER_NAME = "tbs";
  static final String TES_PRIVATE_FOLDER_NAME = "core_private";
  private static final String TES_SDCARD_SHARE_DIR = ".tbs";
  static final String TES_SHARE_FOLDER_NAME = "core_share";
  static final String TES_SHARE_NAME = "share";
  static final String TMP_TES_SHARE_FOLDER_NAME = "core_share_tmp";
  private static HashMap<String, ContentType> mContentTypeMap;
  public static FileUtils sInstance = null;
  private int accCountIn = 0;
  private int accCountInHit = 0;
  private boolean bByteInUse = false;
  private byte[] mByteArray = new byte['က'];
  private int mByteBufferBufferNumber = 0;
  private final ByteBuffer[] mByteBufferPool;
  public int mByteBufferPoolAvailableSize;
  private int mMaxPoolSize = 0;
  
  static
  {
    ERR_BASE = 64536;
    int i = ERR_BASE;
    ERR_SDCARD_NOT_AVAILABLE = i - 1;
    ERR_SAVE_IMAGE_FAILED = i - 2;
    mContentTypeMap = new HashMap();
    ContentType localContentType = new ContentType("text", "html", "utf-8");
    mContentTypeMap.put("html", localContentType);
    mContentTypeMap.put("htm", localContentType);
    mContentTypeMap.put("txt", new ContentType("text", "plain", "utf-8"));
    mContentTypeMap.put("css", new ContentType("text", "css", "utf-8"));
    mContentTypeMap.put("js", new ContentType("text", "javascript", "utf-8"));
    mContentTypeMap.put("png", new ContentType("image", "png", "binary"));
    localContentType = new ContentType("image", "jpeg", "binary");
    mContentTypeMap.put("jpg", localContentType);
    mContentTypeMap.put("jpeg", localContentType);
    mContentTypeMap.put("gif", new ContentType("image", "gif", "binary"));
  }
  
  private FileUtils(int paramInt)
  {
    if (paramInt > 0)
    {
      this.mMaxPoolSize = paramInt;
      this.mByteBufferPool = new ByteBuffer[paramInt];
      return;
    }
    throw new IllegalArgumentException("The max pool size must be > 0");
  }
  
  private ByteBuffer acquireByteBuffer()
  {
    synchronized (this.mByteBufferPool)
    {
      if ((this.accCountIn != 0) && (this.accCountIn % 20 == 0))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("ByteBufferPool =");
        ((StringBuilder)localObject1).append(this.accCountIn);
        ((StringBuilder)localObject1).append(",accCountInHit=");
        ((StringBuilder)localObject1).append(this.accCountInHit);
        ((StringBuilder)localObject1).append(",ratio=");
        ((StringBuilder)localObject1).append(this.accCountInHit * 100 / this.accCountIn);
        ((StringBuilder)localObject1).append(",mByteBufferPoolAvailableSize=");
        ((StringBuilder)localObject1).append(this.mByteBufferPoolAvailableSize);
        LogUtils.d("RobinsliJcePool", ((StringBuilder)localObject1).toString());
      }
      this.accCountIn += 1;
      if (this.mByteBufferPoolAvailableSize > 0)
      {
        this.accCountInHit += 1;
        localObject1 = getABuffer();
        return (ByteBuffer)localObject1;
      }
      if (this.mByteBufferBufferNumber < this.mMaxPoolSize)
      {
        this.accCountInHit += 1;
        localObject1 = ByteBuffer.allocate(4096);
        this.mByteBufferPool[this.mByteBufferPoolAvailableSize] = localObject1;
        this.mByteBufferPoolAvailableSize += 1;
        this.mByteBufferBufferNumber += 1;
        localObject1 = getABuffer();
        return (ByteBuffer)localObject1;
      }
      Object localObject1 = ByteBuffer.allocate(4096);
      return (ByteBuffer)localObject1;
    }
  }
  
  public static boolean checkFileName(String paramString)
  {
    return FILE_NAME_VALID_PATTERN.matcher(paramString).find() ^ true;
  }
  
  public static void cleanDirectory(File paramFile)
    throws IOException, IllegalArgumentException
  {
    if (paramFile == null) {
      return;
    }
    if (paramFile.exists())
    {
      if (paramFile.isDirectory())
      {
        File localFile = null;
        try
        {
          File[] arrayOfFile = paramFile.listFiles();
        }
        catch (Error localError)
        {
          localError.printStackTrace();
          localStringBuilder = null;
        }
        if (localStringBuilder != null)
        {
          int j = localStringBuilder.length;
          int i = 0;
          paramFile = localFile;
          while (i < j)
          {
            localFile = localStringBuilder[i];
            try
            {
              delete(localFile);
            }
            catch (IOException paramFile) {}
            i += 1;
          }
          if (paramFile == null) {
            return;
          }
          throw paramFile;
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Failed to list contents of ");
        localStringBuilder.append(paramFile);
        throw new IOException(localStringBuilder.toString());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramFile);
      localStringBuilder.append(" is not a directory");
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramFile);
    localStringBuilder.append(" does not exist");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable == null) {
      return;
    }
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception paramCloseable)
    {
      paramCloseable.printStackTrace();
    }
  }
  
  public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException, OutOfMemoryError
  {
    long l = copyLarge(paramInputStream, paramOutputStream);
    if (l > 2147483647L) {
      return -1;
    }
    return (int)l;
  }
  
  /* Error */
  public static void copyAssetsFileTo(Context paramContext, String paramString, File paramFile)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +214 -> 215
    //   4: aload_1
    //   5: invokestatic 296	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   8: ifne +207 -> 215
    //   11: aload_2
    //   12: ifnonnull +4 -> 16
    //   15: return
    //   16: aconst_null
    //   17: astore 7
    //   19: aconst_null
    //   20: astore 5
    //   22: aconst_null
    //   23: astore 6
    //   25: aconst_null
    //   26: astore 8
    //   28: aload_0
    //   29: invokevirtual 302	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   32: invokevirtual 306	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   35: aload_1
    //   36: invokevirtual 312	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   39: astore 4
    //   41: aload 8
    //   43: astore_0
    //   44: aload 7
    //   46: astore_1
    //   47: aload_2
    //   48: iconst_0
    //   49: invokestatic 316	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:createOuputStream	(Ljava/io/File;Z)Ljava/io/FileOutputStream;
    //   52: astore_2
    //   53: aload_2
    //   54: ifnull +54 -> 108
    //   57: aload_2
    //   58: astore_0
    //   59: aload_2
    //   60: astore_1
    //   61: ldc_w 317
    //   64: newarray <illegal type>
    //   66: astore 5
    //   68: aload_2
    //   69: astore_0
    //   70: aload_2
    //   71: astore_1
    //   72: aload 4
    //   74: aload 5
    //   76: invokevirtual 323	java/io/InputStream:read	([B)I
    //   79: istore_3
    //   80: iload_3
    //   81: iconst_m1
    //   82: if_icmpeq +18 -> 100
    //   85: aload_2
    //   86: astore_0
    //   87: aload_2
    //   88: astore_1
    //   89: aload_2
    //   90: aload 5
    //   92: iconst_0
    //   93: iload_3
    //   94: invokevirtual 329	java/io/FileOutputStream:write	([BII)V
    //   97: goto -29 -> 68
    //   100: aload_2
    //   101: astore_0
    //   102: aload_2
    //   103: astore_1
    //   104: aload_2
    //   105: invokevirtual 332	java/io/FileOutputStream:flush	()V
    //   108: aload_2
    //   109: ifnull +15 -> 124
    //   112: aload_2
    //   113: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   116: goto +8 -> 124
    //   119: astore_0
    //   120: aload_0
    //   121: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   124: aload 4
    //   126: ifnull +14 -> 140
    //   129: aload 4
    //   131: invokevirtual 335	java/io/InputStream:close	()V
    //   134: return
    //   135: astore_0
    //   136: aload_0
    //   137: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   140: return
    //   141: astore_2
    //   142: aload 4
    //   144: astore_1
    //   145: goto +36 -> 181
    //   148: astore_2
    //   149: aload 4
    //   151: astore_0
    //   152: goto +18 -> 170
    //   155: astore_2
    //   156: aconst_null
    //   157: astore_1
    //   158: aload 6
    //   160: astore_0
    //   161: goto +20 -> 181
    //   164: astore_2
    //   165: aconst_null
    //   166: astore_1
    //   167: aload 5
    //   169: astore_0
    //   170: aload_2
    //   171: athrow
    //   172: astore_2
    //   173: aload_1
    //   174: astore 4
    //   176: aload_0
    //   177: astore_1
    //   178: aload 4
    //   180: astore_0
    //   181: aload_0
    //   182: ifnull +15 -> 197
    //   185: aload_0
    //   186: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   189: goto +8 -> 197
    //   192: astore_0
    //   193: aload_0
    //   194: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   197: aload_1
    //   198: ifnull +15 -> 213
    //   201: aload_1
    //   202: invokevirtual 335	java/io/InputStream:close	()V
    //   205: goto +8 -> 213
    //   208: astore_0
    //   209: aload_0
    //   210: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   213: aload_2
    //   214: athrow
    //   215: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	216	0	paramContext	Context
    //   0	216	1	paramString	String
    //   0	216	2	paramFile	File
    //   79	15	3	i	int
    //   39	140	4	localObject1	Object
    //   20	148	5	arrayOfByte	byte[]
    //   23	136	6	localObject2	Object
    //   17	28	7	localObject3	Object
    //   26	16	8	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   112	116	119	java/io/IOException
    //   129	134	135	java/io/IOException
    //   47	53	141	finally
    //   61	68	141	finally
    //   72	80	141	finally
    //   89	97	141	finally
    //   104	108	141	finally
    //   47	53	148	java/io/IOException
    //   61	68	148	java/io/IOException
    //   72	80	148	java/io/IOException
    //   89	97	148	java/io/IOException
    //   104	108	148	java/io/IOException
    //   28	41	155	finally
    //   28	41	164	java/io/IOException
    //   170	172	172	finally
    //   185	189	192	java/io/IOException
    //   201	205	208	java/io/IOException
  }
  
  /* Error */
  public static boolean copyFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aconst_null
    //   4: astore 9
    //   6: aconst_null
    //   7: astore 10
    //   9: aconst_null
    //   10: astore 8
    //   12: aconst_null
    //   13: astore 7
    //   15: aconst_null
    //   16: astore 11
    //   18: iconst_0
    //   19: istore 5
    //   21: iconst_0
    //   22: istore 6
    //   24: iconst_0
    //   25: istore_3
    //   26: new 241	java/io/File
    //   29: dup
    //   30: aload_0
    //   31: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   34: invokevirtual 244	java/io/File:exists	()Z
    //   37: ifeq +125 -> 162
    //   40: new 340	java/io/FileInputStream
    //   43: dup
    //   44: aload_0
    //   45: invokespecial 341	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   48: astore_0
    //   49: aload 11
    //   51: astore 7
    //   53: aload 9
    //   55: astore 8
    //   57: new 241	java/io/File
    //   60: dup
    //   61: aload_1
    //   62: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   65: iconst_0
    //   66: invokestatic 316	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:createOuputStream	(Ljava/io/File;Z)Ljava/io/FileOutputStream;
    //   69: astore_1
    //   70: aload_1
    //   71: astore 8
    //   73: aload_0
    //   74: astore 7
    //   76: aload_1
    //   77: ifnull +92 -> 169
    //   80: aload_1
    //   81: astore 7
    //   83: aload_1
    //   84: astore 8
    //   86: ldc_w 342
    //   89: newarray <illegal type>
    //   91: astore 9
    //   93: aload_1
    //   94: astore 7
    //   96: aload_1
    //   97: astore 8
    //   99: aload_0
    //   100: aload 9
    //   102: invokevirtual 323	java/io/InputStream:read	([B)I
    //   105: istore_2
    //   106: iload_2
    //   107: ifle +20 -> 127
    //   110: aload_1
    //   111: astore 7
    //   113: aload_1
    //   114: astore 8
    //   116: aload_1
    //   117: aload 9
    //   119: iconst_0
    //   120: iload_2
    //   121: invokevirtual 329	java/io/FileOutputStream:write	([BII)V
    //   124: goto -31 -> 93
    //   127: aload_1
    //   128: astore 7
    //   130: aload_1
    //   131: astore 8
    //   133: aload_1
    //   134: invokevirtual 332	java/io/FileOutputStream:flush	()V
    //   137: iconst_1
    //   138: istore_3
    //   139: aload_1
    //   140: astore 8
    //   142: aload_0
    //   143: astore 7
    //   145: goto +24 -> 169
    //   148: astore_1
    //   149: goto +131 -> 280
    //   152: astore 7
    //   154: aload_0
    //   155: astore_1
    //   156: aload 8
    //   158: astore_0
    //   159: goto +65 -> 224
    //   162: aconst_null
    //   163: astore 7
    //   165: aload 10
    //   167: astore 8
    //   169: aload 7
    //   171: ifnull +11 -> 182
    //   174: aload 7
    //   176: invokevirtual 335	java/io/InputStream:close	()V
    //   179: goto +3 -> 182
    //   182: iload_3
    //   183: istore 4
    //   185: aload 8
    //   187: ifnull +69 -> 256
    //   190: aload 8
    //   192: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   195: iload_3
    //   196: istore 4
    //   198: goto +58 -> 256
    //   201: aload_0
    //   202: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   205: iload_3
    //   206: istore 4
    //   208: goto +48 -> 256
    //   211: astore_1
    //   212: aconst_null
    //   213: astore_0
    //   214: goto +66 -> 280
    //   217: astore 7
    //   219: aconst_null
    //   220: astore_0
    //   221: aload 8
    //   223: astore_1
    //   224: aload 7
    //   226: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   229: aload_1
    //   230: ifnull +10 -> 240
    //   233: aload_1
    //   234: invokevirtual 335	java/io/InputStream:close	()V
    //   237: goto +3 -> 240
    //   240: iload 6
    //   242: istore 4
    //   244: aload_0
    //   245: ifnull +11 -> 256
    //   248: aload_0
    //   249: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   252: iload 6
    //   254: istore 4
    //   256: ldc 2
    //   258: monitorexit
    //   259: iload 4
    //   261: ireturn
    //   262: astore 9
    //   264: aload_1
    //   265: astore 7
    //   267: aload_0
    //   268: astore 8
    //   270: aload 9
    //   272: astore_1
    //   273: aload 7
    //   275: astore_0
    //   276: aload 8
    //   278: astore 7
    //   280: aload_0
    //   281: ifnull +10 -> 291
    //   284: aload_0
    //   285: invokevirtual 335	java/io/InputStream:close	()V
    //   288: goto +3 -> 291
    //   291: aload 7
    //   293: ifnull +15 -> 308
    //   296: aload 7
    //   298: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   301: goto +7 -> 308
    //   304: aload_0
    //   305: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   308: aload_1
    //   309: athrow
    //   310: ldc 2
    //   312: monitorexit
    //   313: aload_0
    //   314: athrow
    //   315: astore_0
    //   316: goto -115 -> 201
    //   319: astore_0
    //   320: goto -10 -> 310
    //   323: astore_0
    //   324: iload 5
    //   326: istore_3
    //   327: goto -126 -> 201
    //   330: astore_0
    //   331: goto -27 -> 304
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	334	0	paramString1	String
    //   0	334	1	paramString2	String
    //   105	16	2	i	int
    //   25	302	3	bool1	boolean
    //   183	77	4	bool2	boolean
    //   19	306	5	bool3	boolean
    //   22	231	6	bool4	boolean
    //   13	131	7	localObject1	Object
    //   152	1	7	localException1	Exception
    //   163	12	7	localObject2	Object
    //   217	8	7	localException2	Exception
    //   265	32	7	localObject3	Object
    //   10	267	8	localObject4	Object
    //   4	114	9	arrayOfByte	byte[]
    //   262	9	9	localObject5	Object
    //   7	159	10	localObject6	Object
    //   16	34	11	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   57	70	148	finally
    //   86	93	148	finally
    //   99	106	148	finally
    //   116	124	148	finally
    //   133	137	148	finally
    //   57	70	152	java/lang/Exception
    //   86	93	152	java/lang/Exception
    //   99	106	152	java/lang/Exception
    //   116	124	152	java/lang/Exception
    //   133	137	152	java/lang/Exception
    //   26	49	211	finally
    //   26	49	217	java/lang/Exception
    //   224	229	262	finally
    //   174	179	315	java/lang/Exception
    //   190	195	315	java/lang/Exception
    //   174	179	319	finally
    //   190	195	319	finally
    //   201	205	319	finally
    //   233	237	319	finally
    //   248	252	319	finally
    //   284	288	319	finally
    //   296	301	319	finally
    //   304	308	319	finally
    //   308	310	319	finally
    //   233	237	323	java/lang/Exception
    //   248	252	323	java/lang/Exception
    //   284	288	330	java/lang/Exception
    //   296	301	330	java/lang/Exception
  }
  
  /* Error */
  public static boolean copyFolder(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 241	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: invokevirtual 346	java/io/File:mkdirs	()Z
    //   11: pop
    //   12: new 241	java/io/File
    //   15: dup
    //   16: aload_0
    //   17: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   20: invokevirtual 350	java/io/File:list	()[Ljava/lang/String;
    //   23: astore 13
    //   25: aconst_null
    //   26: astore 8
    //   28: aload 8
    //   30: astore 7
    //   32: iconst_0
    //   33: istore_2
    //   34: iconst_1
    //   35: istore 4
    //   37: iload_2
    //   38: aload 13
    //   40: arraylength
    //   41: if_icmpge +668 -> 709
    //   44: aload_0
    //   45: getstatic 353	java/io/File:separator	Ljava/lang/String;
    //   48: invokevirtual 358	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   51: ifeq +46 -> 97
    //   54: new 184	java/lang/StringBuilder
    //   57: dup
    //   58: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   61: astore 9
    //   63: aload 9
    //   65: aload_0
    //   66: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: pop
    //   70: aload 9
    //   72: aload 13
    //   74: iload_2
    //   75: aaload
    //   76: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: pop
    //   80: new 241	java/io/File
    //   83: dup
    //   84: aload 9
    //   86: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   92: astore 12
    //   94: goto +52 -> 146
    //   97: new 184	java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   104: astore 9
    //   106: aload 9
    //   108: aload_0
    //   109: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload 9
    //   115: getstatic 353	java/io/File:separator	Ljava/lang/String;
    //   118: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload 9
    //   124: aload 13
    //   126: iload_2
    //   127: aaload
    //   128: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: pop
    //   132: new 241	java/io/File
    //   135: dup
    //   136: aload 9
    //   138: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   141: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   144: astore 12
    //   146: iload 4
    //   148: istore 5
    //   150: aload 8
    //   152: astore 9
    //   154: aload 7
    //   156: astore 10
    //   158: aload 12
    //   160: invokevirtual 361	java/io/File:isFile	()Z
    //   163: ifeq +422 -> 585
    //   166: aload 8
    //   168: astore 11
    //   170: aload 7
    //   172: astore 10
    //   174: new 184	java/lang/StringBuilder
    //   177: dup
    //   178: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   181: astore 9
    //   183: aload 8
    //   185: astore 11
    //   187: aload 7
    //   189: astore 10
    //   191: aload 9
    //   193: aload_1
    //   194: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: pop
    //   198: aload 8
    //   200: astore 11
    //   202: aload 7
    //   204: astore 10
    //   206: aload 9
    //   208: ldc_w 363
    //   211: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload 8
    //   217: astore 11
    //   219: aload 7
    //   221: astore 10
    //   223: aload 9
    //   225: aload 12
    //   227: invokevirtual 366	java/io/File:getName	()Ljava/lang/String;
    //   230: invokevirtual 367	java/lang/String:toString	()Ljava/lang/String;
    //   233: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload 8
    //   239: astore 11
    //   241: aload 7
    //   243: astore 10
    //   245: new 241	java/io/File
    //   248: dup
    //   249: aload 9
    //   251: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   254: invokespecial 338	java/io/File:<init>	(Ljava/lang/String;)V
    //   257: astore 14
    //   259: aload 8
    //   261: astore 11
    //   263: aload 7
    //   265: astore 10
    //   267: new 340	java/io/FileInputStream
    //   270: dup
    //   271: aload 12
    //   273: invokespecial 369	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   276: astore 9
    //   278: aload 7
    //   280: astore 8
    //   282: aload 14
    //   284: iconst_0
    //   285: invokestatic 316	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:createOuputStream	(Ljava/io/File;Z)Ljava/io/FileOutputStream;
    //   288: astore 10
    //   290: aload 10
    //   292: ifnull +75 -> 367
    //   295: aload 10
    //   297: astore 8
    //   299: aload 10
    //   301: astore 7
    //   303: sipush 5120
    //   306: newarray <illegal type>
    //   308: astore 11
    //   310: aload 10
    //   312: astore 8
    //   314: aload 10
    //   316: astore 7
    //   318: aload 9
    //   320: aload 11
    //   322: invokevirtual 370	java/io/FileInputStream:read	([B)I
    //   325: istore_3
    //   326: iload_3
    //   327: iconst_m1
    //   328: if_icmpeq +23 -> 351
    //   331: aload 10
    //   333: astore 8
    //   335: aload 10
    //   337: astore 7
    //   339: aload 10
    //   341: aload 11
    //   343: iconst_0
    //   344: iload_3
    //   345: invokevirtual 329	java/io/FileOutputStream:write	([BII)V
    //   348: goto -38 -> 310
    //   351: aload 10
    //   353: astore 8
    //   355: aload 10
    //   357: astore 7
    //   359: aload 10
    //   361: invokevirtual 332	java/io/FileOutputStream:flush	()V
    //   364: goto +6 -> 370
    //   367: iconst_0
    //   368: istore 4
    //   370: aload 10
    //   372: ifnull +18 -> 390
    //   375: aload 10
    //   377: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   380: goto +10 -> 390
    //   383: astore 7
    //   385: aload 7
    //   387: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   390: aload 9
    //   392: invokevirtual 371	java/io/FileInputStream:close	()V
    //   395: goto +10 -> 405
    //   398: astore 7
    //   400: aload 7
    //   402: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   405: iload 4
    //   407: istore 5
    //   409: goto +176 -> 585
    //   412: astore_0
    //   413: aload 9
    //   415: astore 11
    //   417: goto +130 -> 547
    //   420: astore 10
    //   422: aload 9
    //   424: astore 8
    //   426: aload 10
    //   428: astore 9
    //   430: goto +13 -> 443
    //   433: astore_0
    //   434: aload 10
    //   436: astore 8
    //   438: goto +109 -> 547
    //   441: astore 9
    //   443: iload 4
    //   445: istore 6
    //   447: iload 4
    //   449: ifeq +6 -> 455
    //   452: iconst_0
    //   453: istore 6
    //   455: aload 8
    //   457: astore 11
    //   459: aload 7
    //   461: astore 10
    //   463: aload 9
    //   465: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   468: aload 7
    //   470: ifnull +18 -> 488
    //   473: aload 7
    //   475: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   478: goto +10 -> 488
    //   481: astore 9
    //   483: aload 9
    //   485: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   488: iload 6
    //   490: istore 5
    //   492: aload 8
    //   494: astore 9
    //   496: aload 7
    //   498: astore 10
    //   500: aload 8
    //   502: ifnull +83 -> 585
    //   505: aload 8
    //   507: invokevirtual 371	java/io/FileInputStream:close	()V
    //   510: iload 6
    //   512: istore 5
    //   514: aload 8
    //   516: astore 9
    //   518: aload 7
    //   520: astore 10
    //   522: goto +63 -> 585
    //   525: astore 9
    //   527: aload 9
    //   529: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   532: iload 6
    //   534: istore 5
    //   536: aload 8
    //   538: astore 9
    //   540: aload 7
    //   542: astore 10
    //   544: goto +41 -> 585
    //   547: aload 8
    //   549: ifnull +16 -> 565
    //   552: aload 8
    //   554: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   557: goto +8 -> 565
    //   560: astore_1
    //   561: aload_1
    //   562: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   565: aload 11
    //   567: ifnull +16 -> 583
    //   570: aload 11
    //   572: invokevirtual 371	java/io/FileInputStream:close	()V
    //   575: goto +8 -> 583
    //   578: astore_1
    //   579: aload_1
    //   580: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   583: aload_0
    //   584: athrow
    //   585: iload 5
    //   587: istore 4
    //   589: aload 12
    //   591: invokevirtual 247	java/io/File:isDirectory	()Z
    //   594: ifeq +100 -> 694
    //   597: new 184	java/lang/StringBuilder
    //   600: dup
    //   601: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   604: astore 7
    //   606: aload 7
    //   608: aload_0
    //   609: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   612: pop
    //   613: aload 7
    //   615: ldc_w 363
    //   618: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   621: pop
    //   622: aload 7
    //   624: aload 13
    //   626: iload_2
    //   627: aaload
    //   628: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   631: pop
    //   632: aload 7
    //   634: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   637: astore 7
    //   639: new 184	java/lang/StringBuilder
    //   642: dup
    //   643: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   646: astore 8
    //   648: aload 8
    //   650: aload_1
    //   651: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   654: pop
    //   655: aload 8
    //   657: ldc_w 363
    //   660: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   663: pop
    //   664: aload 8
    //   666: aload 13
    //   668: iload_2
    //   669: aaload
    //   670: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   673: pop
    //   674: iload 5
    //   676: istore 4
    //   678: aload 7
    //   680: aload 8
    //   682: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   685: invokestatic 373	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:copyFolder	(Ljava/lang/String;Ljava/lang/String;)Z
    //   688: ifne +6 -> 694
    //   691: iconst_0
    //   692: istore 4
    //   694: iload_2
    //   695: iconst_1
    //   696: iadd
    //   697: istore_2
    //   698: aload 9
    //   700: astore 8
    //   702: aload 10
    //   704: astore 7
    //   706: goto -669 -> 37
    //   709: iload 4
    //   711: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	712	0	paramString1	String
    //   0	712	1	paramString2	String
    //   33	665	2	i	int
    //   325	20	3	j	int
    //   35	675	4	bool1	boolean
    //   148	527	5	bool2	boolean
    //   445	88	6	bool3	boolean
    //   30	328	7	localObject1	Object
    //   383	3	7	localIOException1	IOException
    //   398	143	7	localIOException2	IOException
    //   604	101	7	localObject2	Object
    //   26	675	8	localObject3	Object
    //   61	368	9	localObject4	Object
    //   441	23	9	localException1	Exception
    //   481	3	9	localIOException3	IOException
    //   494	23	9	localObject5	Object
    //   525	3	9	localIOException4	IOException
    //   538	161	9	localObject6	Object
    //   156	220	10	localObject7	Object
    //   420	15	10	localException2	Exception
    //   461	242	10	localIOException5	IOException
    //   168	403	11	localObject8	Object
    //   92	498	12	localFile1	File
    //   23	644	13	arrayOfString	String[]
    //   257	26	14	localFile2	File
    // Exception table:
    //   from	to	target	type
    //   375	380	383	java/io/IOException
    //   390	395	398	java/io/IOException
    //   282	290	412	finally
    //   303	310	412	finally
    //   318	326	412	finally
    //   339	348	412	finally
    //   359	364	412	finally
    //   282	290	420	java/lang/Exception
    //   303	310	420	java/lang/Exception
    //   318	326	420	java/lang/Exception
    //   339	348	420	java/lang/Exception
    //   359	364	420	java/lang/Exception
    //   174	183	433	finally
    //   191	198	433	finally
    //   206	215	433	finally
    //   223	237	433	finally
    //   245	259	433	finally
    //   267	278	433	finally
    //   463	468	433	finally
    //   174	183	441	java/lang/Exception
    //   191	198	441	java/lang/Exception
    //   206	215	441	java/lang/Exception
    //   223	237	441	java/lang/Exception
    //   245	259	441	java/lang/Exception
    //   267	278	441	java/lang/Exception
    //   473	478	481	java/io/IOException
    //   505	510	525	java/io/IOException
    //   552	557	560	java/io/IOException
    //   570	575	578	java/io/IOException
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException, OutOfMemoryError
  {
    if (paramInputStream == null) {
      return -1L;
    }
    byte[] arrayOfByte = new byte['က'];
    int i;
    for (long l = 0L;; l += i)
    {
      i = paramInputStream.read(arrayOfByte);
      if (-1 == i) {
        break;
      }
      paramOutputStream.write(arrayOfByte, 0, i);
    }
    return l;
  }
  
  public static File createDir(File paramFile, String paramString)
  {
    if ((paramFile != null) && (paramString != null) && (paramString.length() != 0))
    {
      paramFile = new File(paramFile, paramString);
      if (!paramFile.exists()) {
        paramFile.mkdirs();
      }
      return paramFile;
    }
    return null;
  }
  
  public static FileOutputStream createOuputStream(File paramFile, boolean paramBoolean)
  {
    try
    {
      paramFile = new FileOutputStream(paramFile, paramBoolean);
      try
      {
        if (paramFile.getChannel().tryLock() == null)
        {
          paramFile.close();
          return null;
        }
        return paramFile;
      }
      catch (Throwable localThrowable1) {}
      localThrowable2.printStackTrace();
    }
    catch (Throwable localThrowable2)
    {
      paramFile = null;
    }
    if (paramFile != null) {}
    try
    {
      paramFile.close();
      return null;
    }
    catch (IOException paramFile) {}
    return null;
  }
  
  public static void delete(File paramFile)
    throws IOException, IllegalArgumentException, FileNotFoundException
  {
    if (paramFile == null) {
      return;
    }
    if (paramFile.isDirectory()) {
      cleanDirectory(paramFile);
    }
    boolean bool = paramFile.exists();
    if (!paramFile.delete())
    {
      if (!bool)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("File does not exist: ");
        localStringBuilder.append(paramFile);
        throw new FileNotFoundException(localStringBuilder.toString());
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to delete file: ");
      localStringBuilder.append(paramFile);
      throw new IOException(localStringBuilder.toString());
    }
  }
  
  public static void deleteFileOnThread(File paramFile)
  {
    if (paramFile == null) {
      return;
    }
    Object localObject = paramFile.getName();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(System.currentTimeMillis());
    localStringBuilder.append((String)localObject);
    localObject = localStringBuilder.toString();
    localObject = new File(paramFile.getParentFile(), (String)localObject);
    if (paramFile.renameTo((File)localObject))
    {
      new Thread()
      {
        public void run()
        {
          if (this.val$newFile.isDirectory()) {
            try
            {
              FileUtils.delete(this.val$newFile);
              return;
            }
            catch (IOException localIOException)
            {
              localIOException.printStackTrace();
              return;
            }
          }
          this.val$newFile.delete();
        }
      }.start();
      return;
    }
    if (paramFile.isDirectory()) {
      try
      {
        delete(paramFile);
        return;
      }
      catch (IOException paramFile)
      {
        paramFile.printStackTrace();
        return;
      }
    }
    paramFile.delete();
  }
  
  public static boolean deleteQuietly(File paramFile)
  {
    if (paramFile == null) {
      return false;
    }
    if (!paramFile.exists()) {
      return true;
    }
    try
    {
      if (paramFile.isDirectory()) {
        cleanDirectory(paramFile);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    try
    {
      boolean bool = paramFile.delete();
      return bool;
    }
    catch (Exception paramFile) {}
    return false;
  }
  
  public static String fixIllegalPath(String paramString)
  {
    Object localObject = paramString;
    if (!checkFileName(paramString))
    {
      paramString = FILE_NAME_VALID_PATTERN.split(paramString);
      localObject = new StringBuilder();
      int j = paramString.length;
      int i = 0;
      while (i < j)
      {
        ((StringBuilder)localObject).append(paramString[i]);
        i += 1;
      }
      localObject = ((StringBuilder)localObject).toString();
    }
    return (String)localObject;
  }
  
  public static void forceMkdir(File paramFile)
    throws IOException
  {
    StringBuilder localStringBuilder;
    if (paramFile.exists())
    {
      if (paramFile.isDirectory()) {
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("File ");
      localStringBuilder.append(paramFile);
      localStringBuilder.append(" exists and is not a directory. Unable to create directory.");
      throw new IOException(localStringBuilder.toString());
    }
    if (!paramFile.mkdirs())
    {
      if (paramFile.isDirectory()) {
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to create directory ");
      localStringBuilder.append(paramFile);
      throw new IOException(localStringBuilder.toString());
    }
  }
  
  public static String generateFileOrigNameFromUrl(String paramString)
  {
    int i = paramString.lastIndexOf('/');
    int j = paramString.lastIndexOf('.');
    if ((i < j) && (i != -1)) {
      return paramString.substring(i + 1, j);
    }
    return null;
  }
  
  public static File generateImageFile(String paramString1, String paramString2, boolean paramBoolean)
  {
    return null;
  }
  
  private ByteBuffer getABuffer()
  {
    int i = this.mByteBufferPoolAvailableSize;
    int j = i - 1;
    ByteBuffer[] arrayOfByteBuffer = this.mByteBufferPool;
    ByteBuffer localByteBuffer = arrayOfByteBuffer[j];
    arrayOfByteBuffer[j] = null;
    this.mByteBufferPoolAvailableSize = (i - 1);
    return localByteBuffer;
  }
  
  public static File getAppCacheDir(Context paramContext)
  {
    if (hasSDcard()) {
      return createDir(getQQBrowserDir(), ".cache");
    }
    return getCacheDir(paramContext.getApplicationContext());
  }
  
  /* Error */
  public static String getAssetsRealName(String paramString, Context paramContext)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 302	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull +92 -> 98
    //   9: new 184	java/lang/StringBuilder
    //   12: dup
    //   13: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   16: astore_1
    //   17: aload_1
    //   18: ldc_w 480
    //   21: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload_1
    //   26: aload_0
    //   27: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: aload_1
    //   32: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   35: astore 4
    //   37: aconst_null
    //   38: astore_1
    //   39: aconst_null
    //   40: astore_3
    //   41: aload_2
    //   42: invokevirtual 306	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   45: aload 4
    //   47: invokevirtual 312	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   50: astore_2
    //   51: aload_2
    //   52: ifnull +14 -> 66
    //   55: aload_2
    //   56: astore_3
    //   57: aload_2
    //   58: astore_1
    //   59: aload_2
    //   60: invokevirtual 335	java/io/InputStream:close	()V
    //   63: aload 4
    //   65: areturn
    //   66: aload_2
    //   67: ifnull +29 -> 96
    //   70: aload_2
    //   71: astore_1
    //   72: aload_1
    //   73: invokevirtual 335	java/io/InputStream:close	()V
    //   76: aload_0
    //   77: areturn
    //   78: astore_0
    //   79: aload_3
    //   80: ifnull +7 -> 87
    //   83: aload_3
    //   84: invokevirtual 335	java/io/InputStream:close	()V
    //   87: aload_0
    //   88: athrow
    //   89: aload_1
    //   90: ifnull +6 -> 96
    //   93: goto -21 -> 72
    //   96: aload_0
    //   97: areturn
    //   98: aload_0
    //   99: areturn
    //   100: astore_2
    //   101: goto -12 -> 89
    //   104: astore_1
    //   105: aload_0
    //   106: areturn
    //   107: astore_1
    //   108: goto -21 -> 87
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	111	0	paramString	String
    //   0	111	1	paramContext	Context
    //   4	67	2	localObject1	Object
    //   100	1	2	localException	Exception
    //   40	44	3	localObject2	Object
    //   35	29	4	str	String
    // Exception table:
    //   from	to	target	type
    //   41	51	78	finally
    //   59	63	78	finally
    //   41	51	100	java/lang/Exception
    //   59	63	100	java/lang/Exception
    //   72	76	104	java/lang/Exception
    //   83	87	107	java/lang/Exception
  }
  
  /* Error */
  public static byte[] getBitmapBytesFromInputStream(InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +44 -> 45
    //   4: aload_0
    //   5: invokestatic 485	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:toByteArray	(Ljava/io/InputStream;)[B
    //   8: astore_1
    //   9: aload_0
    //   10: invokevirtual 335	java/io/InputStream:close	()V
    //   13: aload_1
    //   14: areturn
    //   15: astore_1
    //   16: goto +23 -> 39
    //   19: astore_1
    //   20: aload_1
    //   21: invokevirtual 486	java/lang/OutOfMemoryError:printStackTrace	()V
    //   24: aload_0
    //   25: invokevirtual 335	java/io/InputStream:close	()V
    //   28: goto +17 -> 45
    //   31: astore_1
    //   32: aload_1
    //   33: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   36: goto -12 -> 24
    //   39: aload_0
    //   40: invokevirtual 335	java/io/InputStream:close	()V
    //   43: aload_1
    //   44: athrow
    //   45: aconst_null
    //   46: areturn
    //   47: astore_0
    //   48: aload_1
    //   49: areturn
    //   50: astore_0
    //   51: goto -6 -> 45
    //   54: astore_0
    //   55: goto -12 -> 43
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	paramInputStream	InputStream
    //   8	6	1	arrayOfByte	byte[]
    //   15	1	1	localObject	Object
    //   19	2	1	localOutOfMemoryError	OutOfMemoryError
    //   31	18	1	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   4	9	15	finally
    //   20	24	15	finally
    //   32	36	15	finally
    //   4	9	19	java/lang/OutOfMemoryError
    //   4	9	31	java/lang/Exception
    //   9	13	47	java/io/IOException
    //   24	28	50	java/io/IOException
    //   39	43	54	java/io/IOException
  }
  
  public static File getCacheDir(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getApplicationContext().getCacheDir();
      return paramContext;
    }
    catch (IllegalArgumentException paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static ContentType getContentType(String paramString)
  {
    paramString = getFileExt(paramString);
    if (paramString != null) {
      paramString = (ContentType)mContentTypeMap.get(paramString.toLowerCase());
    } else {
      paramString = null;
    }
    Object localObject = paramString;
    if (paramString == null) {
      localObject = new ContentType("application", "octet-stream", "binary");
    }
    return (ContentType)localObject;
  }
  
  public static File getDataDir(Context paramContext)
  {
    return createDir(paramContext.getFilesDir(), "data");
  }
  
  public static long getDataFreeSpace(Context paramContext)
  {
    return getSdcardFreeSpace(getDataDir(paramContext).getAbsolutePath());
  }
  
  public static File getDatabaseBase(Context paramContext, String paramString)
  {
    if ((paramContext != null) && (!TextUtils.isEmpty(paramString))) {
      return new File(paramContext.getApplicationContext().getDatabasePath(paramString).getParent());
    }
    return null;
  }
  
  public static String getFileExt(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    int i = paramString.lastIndexOf('.');
    if ((i > -1) && (i < paramString.length() - 1))
    {
      String str = paramString.substring(i + 1);
      paramString = str;
      if (str.indexOf(File.separatorChar) > -1) {
        return null;
      }
    }
    else
    {
      paramString = null;
    }
    return paramString;
  }
  
  public static String getFileName(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    int i = paramString.lastIndexOf(File.separator);
    if (i < 0) {
      return paramString;
    }
    return paramString.substring(i + 1);
  }
  
  public static long getFileOrDirectorySize(File paramFile)
  {
    long l = 0L;
    if (paramFile == null) {
      return 0L;
    }
    if (!paramFile.isDirectory()) {
      return paramFile.length();
    }
    paramFile = paramFile.listFiles();
    if (paramFile == null) {
      return 0L;
    }
    int j = paramFile.length;
    int i = 0;
    while (i < j)
    {
      l += getFileOrDirectorySize(paramFile[i]);
      i += 1;
    }
    return l;
  }
  
  public static String getFileParentPath(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    return new File(paramString).getParent();
  }
  
  public static List<File> getFiles(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    paramString = new File(paramString).listFiles();
    if (paramString != null)
    {
      int j = paramString.length;
      int i = 0;
      while (i < j)
      {
        Object localObject = paramString[i];
        if (((File)localObject).isFile()) {
          localArrayList.add(localObject);
        } else {
          localArrayList.addAll(getFiles(((File)localObject).getPath()));
        }
        i += 1;
      }
    }
    return localArrayList;
  }
  
  public static File getFilesDir(Context paramContext)
  {
    return paramContext.getApplicationContext().getFilesDir();
  }
  
  /* Error */
  public static Bitmap getImage(File paramFile)
    throws OutOfMemoryError
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aconst_null
    //   6: astore 5
    //   8: aconst_null
    //   9: astore_2
    //   10: aconst_null
    //   11: astore 6
    //   13: aload_0
    //   14: ifnull +106 -> 120
    //   17: aload 6
    //   19: astore_1
    //   20: aload_0
    //   21: invokevirtual 244	java/io/File:exists	()Z
    //   24: ifeq +96 -> 120
    //   27: aload 6
    //   29: astore_1
    //   30: aload_0
    //   31: invokestatic 578	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   34: astore_0
    //   35: aload_0
    //   36: astore_1
    //   37: aload_0
    //   38: invokestatic 584	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   41: astore 4
    //   43: aload 4
    //   45: astore_1
    //   46: goto +79 -> 125
    //   49: astore_2
    //   50: aload_0
    //   51: astore_1
    //   52: aload_2
    //   53: astore_0
    //   54: goto +46 -> 100
    //   57: astore_0
    //   58: goto +44 -> 102
    //   61: aconst_null
    //   62: astore_0
    //   63: aload_0
    //   64: astore_1
    //   65: ldc 47
    //   67: ldc_w 586
    //   70: invokestatic 214	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   73: aload_0
    //   74: ifnull +65 -> 139
    //   77: aload_3
    //   78: astore_2
    //   79: aload_0
    //   80: invokevirtual 335	java/io/InputStream:close	()V
    //   83: aconst_null
    //   84: areturn
    //   85: astore_0
    //   86: aload_0
    //   87: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   90: aload_2
    //   91: areturn
    //   92: astore_0
    //   93: goto +9 -> 102
    //   96: astore_0
    //   97: aload 4
    //   99: astore_1
    //   100: aload_0
    //   101: athrow
    //   102: aload_1
    //   103: ifnull +15 -> 118
    //   106: aload_1
    //   107: invokevirtual 335	java/io/InputStream:close	()V
    //   110: goto +8 -> 118
    //   113: astore_1
    //   114: aload_1
    //   115: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   118: aload_0
    //   119: athrow
    //   120: aconst_null
    //   121: astore_0
    //   122: aload 5
    //   124: astore_1
    //   125: aload_1
    //   126: astore_2
    //   127: aload_0
    //   128: ifnull +11 -> 139
    //   131: aload_1
    //   132: astore_2
    //   133: aload_0
    //   134: invokevirtual 335	java/io/InputStream:close	()V
    //   137: aload_1
    //   138: astore_2
    //   139: aload_2
    //   140: areturn
    //   141: astore_0
    //   142: goto -81 -> 61
    //   145: astore_1
    //   146: goto -83 -> 63
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	paramFile	File
    //   19	88	1	localObject1	Object
    //   113	2	1	localIOException	IOException
    //   124	14	1	localObject2	Object
    //   145	1	1	localException	Exception
    //   9	1	2	localObject3	Object
    //   49	4	2	localOutOfMemoryError	OutOfMemoryError
    //   78	62	2	localObject4	Object
    //   1	77	3	localObject5	Object
    //   3	95	4	localBitmap	Bitmap
    //   6	117	5	localObject6	Object
    //   11	17	6	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   37	43	49	java/lang/OutOfMemoryError
    //   20	27	57	finally
    //   30	35	57	finally
    //   100	102	57	finally
    //   79	83	85	java/io/IOException
    //   133	137	85	java/io/IOException
    //   37	43	92	finally
    //   65	73	92	finally
    //   20	27	96	java/lang/OutOfMemoryError
    //   30	35	96	java/lang/OutOfMemoryError
    //   106	110	113	java/io/IOException
    //   20	27	141	java/lang/Exception
    //   30	35	141	java/lang/Exception
    //   37	43	145	java/lang/Exception
  }
  
  /* Error */
  public static Bitmap getImage(File paramFile, int paramInt1, int paramInt2)
    throws OutOfMemoryError
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 7
    //   8: aconst_null
    //   9: astore 6
    //   11: aload_0
    //   12: ifnull +272 -> 284
    //   15: aload_0
    //   16: invokevirtual 244	java/io/File:exists	()Z
    //   19: ifeq +265 -> 284
    //   22: aload_0
    //   23: invokestatic 578	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   26: astore_0
    //   27: new 589	java/io/BufferedInputStream
    //   30: dup
    //   31: aload_0
    //   32: invokespecial 592	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   35: astore 4
    //   37: iload_1
    //   38: ifle +71 -> 109
    //   41: iload_2
    //   42: ifgt +6 -> 48
    //   45: goto +64 -> 109
    //   48: aload 4
    //   50: astore_3
    //   51: aload_0
    //   52: astore 5
    //   54: new 594	android/graphics/BitmapFactory$Options
    //   57: dup
    //   58: invokespecial 595	android/graphics/BitmapFactory$Options:<init>	()V
    //   61: astore 8
    //   63: aload 4
    //   65: astore_3
    //   66: aload_0
    //   67: astore 5
    //   69: aload 8
    //   71: iload_1
    //   72: putfield 598	android/graphics/BitmapFactory$Options:outWidth	I
    //   75: aload 4
    //   77: astore_3
    //   78: aload_0
    //   79: astore 5
    //   81: aload 8
    //   83: iload_2
    //   84: putfield 601	android/graphics/BitmapFactory$Options:outHeight	I
    //   87: aload 4
    //   89: astore_3
    //   90: aload_0
    //   91: astore 5
    //   93: aload 4
    //   95: aconst_null
    //   96: aload 8
    //   98: invokestatic 604	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   101: astore 8
    //   103: aload 8
    //   105: astore_3
    //   106: goto +183 -> 289
    //   109: aload 4
    //   111: astore_3
    //   112: aload_0
    //   113: astore 5
    //   115: aload 4
    //   117: invokestatic 584	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   120: astore 8
    //   122: aload 8
    //   124: astore_3
    //   125: goto +164 -> 289
    //   128: astore 5
    //   130: aload 4
    //   132: astore_3
    //   133: aload 5
    //   135: astore 4
    //   137: goto +20 -> 157
    //   140: astore 4
    //   142: aconst_null
    //   143: astore_3
    //   144: goto +105 -> 249
    //   147: aconst_null
    //   148: astore 4
    //   150: goto +24 -> 174
    //   153: astore 4
    //   155: aconst_null
    //   156: astore_3
    //   157: goto +87 -> 244
    //   160: astore 4
    //   162: aconst_null
    //   163: astore_0
    //   164: aload_0
    //   165: astore_3
    //   166: goto +83 -> 249
    //   169: aconst_null
    //   170: astore_0
    //   171: aload_0
    //   172: astore 4
    //   174: aload 4
    //   176: astore_3
    //   177: aload_0
    //   178: astore 5
    //   180: ldc 47
    //   182: ldc_w 586
    //   185: invokestatic 214	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   188: aload_0
    //   189: ifnull +15 -> 204
    //   192: aload_0
    //   193: invokevirtual 335	java/io/InputStream:close	()V
    //   196: goto +8 -> 204
    //   199: astore_0
    //   200: aload_0
    //   201: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   204: aload 7
    //   206: astore_0
    //   207: aload 4
    //   209: ifnull +112 -> 321
    //   212: aload 6
    //   214: astore_0
    //   215: aload 4
    //   217: invokevirtual 605	java/io/BufferedInputStream:close	()V
    //   220: aconst_null
    //   221: areturn
    //   222: astore_3
    //   223: aload_3
    //   224: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   227: aload_0
    //   228: areturn
    //   229: astore 4
    //   231: aload 5
    //   233: astore_0
    //   234: goto +15 -> 249
    //   237: astore 4
    //   239: aconst_null
    //   240: astore_3
    //   241: aload 5
    //   243: astore_0
    //   244: aload 4
    //   246: athrow
    //   247: astore 4
    //   249: aload_0
    //   250: ifnull +15 -> 265
    //   253: aload_0
    //   254: invokevirtual 335	java/io/InputStream:close	()V
    //   257: goto +8 -> 265
    //   260: astore_0
    //   261: aload_0
    //   262: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   265: aload_3
    //   266: ifnull +15 -> 281
    //   269: aload_3
    //   270: invokevirtual 605	java/io/BufferedInputStream:close	()V
    //   273: goto +8 -> 281
    //   276: astore_0
    //   277: aload_0
    //   278: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   281: aload 4
    //   283: athrow
    //   284: aconst_null
    //   285: astore_0
    //   286: aload_0
    //   287: astore 4
    //   289: aload_0
    //   290: ifnull +15 -> 305
    //   293: aload_0
    //   294: invokevirtual 335	java/io/InputStream:close	()V
    //   297: goto +8 -> 305
    //   300: astore_0
    //   301: aload_0
    //   302: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   305: aload_3
    //   306: astore_0
    //   307: aload 4
    //   309: ifnull +12 -> 321
    //   312: aload_3
    //   313: astore_0
    //   314: aload 4
    //   316: invokevirtual 605	java/io/BufferedInputStream:close	()V
    //   319: aload_3
    //   320: astore_0
    //   321: aload_0
    //   322: areturn
    //   323: astore_0
    //   324: goto -155 -> 169
    //   327: astore_3
    //   328: goto -181 -> 147
    //   331: astore_3
    //   332: goto -158 -> 174
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	335	0	paramFile	File
    //   0	335	1	paramInt1	int
    //   0	335	2	paramInt2	int
    //   4	173	3	localObject1	Object
    //   222	2	3	localIOException	IOException
    //   240	80	3	localObject2	Object
    //   327	1	3	localException1	Exception
    //   331	1	3	localException2	Exception
    //   35	101	4	localObject3	Object
    //   140	1	4	localObject4	Object
    //   148	1	4	localObject5	Object
    //   153	1	4	localOutOfMemoryError1	OutOfMemoryError
    //   160	1	4	localObject6	Object
    //   172	44	4	localFile1	File
    //   229	1	4	localObject7	Object
    //   237	8	4	localOutOfMemoryError2	OutOfMemoryError
    //   247	35	4	localObject8	Object
    //   287	28	4	localFile2	File
    //   1	113	5	localFile3	File
    //   128	6	5	localOutOfMemoryError3	OutOfMemoryError
    //   178	64	5	localFile4	File
    //   9	204	6	localObject9	Object
    //   6	199	7	localObject10	Object
    //   61	62	8	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   54	63	128	java/lang/OutOfMemoryError
    //   69	75	128	java/lang/OutOfMemoryError
    //   81	87	128	java/lang/OutOfMemoryError
    //   93	103	128	java/lang/OutOfMemoryError
    //   115	122	128	java/lang/OutOfMemoryError
    //   27	37	140	finally
    //   27	37	153	java/lang/OutOfMemoryError
    //   15	27	160	finally
    //   192	196	199	java/io/IOException
    //   215	220	222	java/io/IOException
    //   314	319	222	java/io/IOException
    //   54	63	229	finally
    //   69	75	229	finally
    //   81	87	229	finally
    //   93	103	229	finally
    //   115	122	229	finally
    //   180	188	229	finally
    //   15	27	237	java/lang/OutOfMemoryError
    //   244	247	247	finally
    //   253	257	260	java/io/IOException
    //   269	273	276	java/io/IOException
    //   293	297	300	java/io/IOException
    //   15	27	323	java/lang/Exception
    //   27	37	327	java/lang/Exception
    //   54	63	331	java/lang/Exception
    //   69	75	331	java/lang/Exception
    //   81	87	331	java/lang/Exception
    //   93	103	331	java/lang/Exception
    //   115	122	331	java/lang/Exception
  }
  
  public static FileUtils getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new FileUtils(4);
      }
      FileUtils localFileUtils = sInstance;
      return localFileUtils;
    }
    finally {}
  }
  
  @SuppressLint({"NewApi"})
  public static String getNativeLibraryDir(Context paramContext)
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 9) {
      return paramContext.getApplicationInfo().nativeLibraryDir;
    }
    if (i >= 4)
    {
      paramContext = paramContext.getApplicationInfo().dataDir;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext);
      localStringBuilder.append("/lib");
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("/data/data/");
    localStringBuilder.append(paramContext.getPackageName());
    localStringBuilder.append("/lib");
    return localStringBuilder.toString();
  }
  
  public static File getQBSdcardGuidDir()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(getSDcardDir().getAbsolutePath());
    ((StringBuilder)localObject).append("/QQBrowser/.Application");
    localObject = new File(((StringBuilder)localObject).toString());
    if (!((File)localObject).exists()) {
      ((File)localObject).mkdirs();
    }
    return (File)localObject;
  }
  
  public static File getQQBrowserDir()
  {
    return createDir(getSDcardDir(), "QQBrowser");
  }
  
  public static File getSDcardDir()
  {
    return null;
  }
  
  public static long getSdcardFreeSpace()
  {
    return getSdcardFreeSpace(getSDcardDir().getAbsolutePath());
  }
  
  public static long getSdcardFreeSpace(String paramString)
  {
    try
    {
      StatFs localStatFs = new StatFs(paramString);
      localStatFs.restat(paramString);
      long l = localStatFs.getBlockSize();
      int i = localStatFs.getAvailableBlocks();
      return l * i;
    }
    catch (IllegalArgumentException paramString)
    {
      for (;;) {}
    }
    return 0L;
  }
  
  public static File getSystemPhotoDir()
  {
    return createDir(getSDcardDir(), "DCIM");
  }
  
  public static File getTbsDataShareDirWithoutChmod(Context paramContext)
  {
    return null;
  }
  
  public static File getTesCorePrivateDir(Context paramContext)
  {
    paramContext = new File(paramContext.getDir("tbs", 0), "core_private");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext;
  }
  
  public static File getTesCoreShareDir(Context paramContext)
  {
    paramContext = new File(paramContext.getDir("tbs", 0), "core_share");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext;
  }
  
  public static File getTesDataShareDir(Context paramContext)
  {
    return null;
  }
  
  public static File getTesSdcardShareDir()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(getSDcardDir().getAbsolutePath());
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append(".tbs");
    localObject = new File(((StringBuilder)localObject).toString());
    if (!((File)localObject).exists()) {
      ((File)localObject).mkdirs();
    }
    return (File)localObject;
  }
  
  public static File getTesShareDir(Context paramContext)
  {
    paramContext = new File(paramContext.getDir("tbs", 0), "share");
    if (paramContext.exists()) {
      return paramContext;
    }
    return null;
  }
  
  public static File getTmpTesCoreShareDir(Context paramContext)
  {
    paramContext = new File(paramContext.getDir("tbs", 0), "core_share_tmp");
    if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
      return null;
    }
    return paramContext;
  }
  
  public static boolean hasSDcard()
  {
    try
    {
      boolean bool = "mounted".equals(Environment.getExternalStorageState());
      return bool;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return false;
  }
  
  private static boolean isExternalFile(File paramFile)
  {
    return (paramFile != null) && (paramFile.getAbsolutePath().contains(getSDcardDir().getAbsolutePath()));
  }
  
  private boolean isInPool(ByteBuffer paramByteBuffer)
  {
    int i = 0;
    while (i < this.mByteBufferPoolAvailableSize)
    {
      if (this.mByteBufferPool[i] == paramByteBuffer) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static boolean isLocalFile(String paramString)
  {
    return (paramString != null) && ((paramString.startsWith("/")) || (paramString.startsWith("file:///")) || (paramString.startsWith("FILE:///")));
  }
  
  public static boolean isSameFileName(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1)) {
      if (TextUtils.isEmpty(paramString2)) {
        return false;
      }
    }
    for (;;)
    {
      try
      {
        int i = paramString1.lastIndexOf('.');
        if (i <= -1) {
          break label179;
        }
        localObject1 = paramString1.substring(0, i);
        localObject2 = paramString1.substring(i);
        paramString1 = (String)localObject1;
        localObject1 = localObject2;
        i = paramString2.lastIndexOf('.');
        if (i <= -1) {
          break label187;
        }
        localObject2 = paramString2.substring(0, i);
        String str = paramString2.substring(i);
        paramString2 = (String)localObject2;
        localObject2 = str;
        if ((((String)localObject1).equalsIgnoreCase((String)localObject2)) && (paramString1.startsWith(paramString2)))
        {
          paramString1 = paramString1.substring(paramString2.length());
          if ((paramString1 != null) && (paramString1.length() > 2) && (paramString1.startsWith("(")) && (paramString1.endsWith(")")))
          {
            boolean bool = StringUtils.isNumeric(paramString1.substring(1, paramString1.length() - 1));
            if (bool) {
              return true;
            }
          }
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
      }
      return false;
      return false;
      label179:
      Object localObject1 = "";
      continue;
      label187:
      Object localObject2 = "";
    }
  }
  
  public static FileInputStream openInputStream(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (paramFile.canRead()) {
          return new FileInputStream(paramFile);
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("File '");
        localStringBuilder.append(paramFile);
        localStringBuilder.append("' cannot be read");
        throw new IOException(localStringBuilder.toString());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("File '");
      localStringBuilder.append(paramFile);
      localStringBuilder.append("' exists but is a directory");
      throw new IOException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("File '");
    localStringBuilder.append(paramFile);
    localStringBuilder.append("' does not exist");
    throw new FileNotFoundException(localStringBuilder.toString());
  }
  
  public static FileInputStream openInputStream(String paramString)
    throws IOException
  {
    return openInputStream(new File(paramString));
  }
  
  public static FileOutputStream openOutputStream(File paramFile)
    throws IOException
  {
    Object localObject;
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (!paramFile.canWrite())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("File '");
          ((StringBuilder)localObject).append(paramFile);
          ((StringBuilder)localObject).append("' cannot be written to");
          throw new IOException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' exists but is a directory");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      localObject = paramFile.getParentFile();
      if ((localObject != null) && (!((File)localObject).exists()) && (!((File)localObject).mkdirs()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' could not be created");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    return createOuputStream(paramFile, false);
  }
  
  public static FileOutputStream openOutputStream(String paramString)
    throws IOException
  {
    return openOutputStream(new File(paramString));
  }
  
  public static FileOutputStream openOutputStreamAppend(File paramFile)
    throws IOException
  {
    Object localObject;
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (!paramFile.canWrite())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("File '");
          ((StringBuilder)localObject).append(paramFile);
          ((StringBuilder)localObject).append("' cannot be written to");
          throw new IOException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' exists but is a directory");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      localObject = paramFile.getParentFile();
      if ((localObject != null) && (!((File)localObject).exists()) && (!((File)localObject).mkdirs()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' could not be created");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    return createOuputStream(paramFile, true);
  }
  
  public static RandomAccessFile openRandomAccessFile(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (paramFile.canRead()) {
          return new RandomAccessFile(paramFile, "r");
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("File '");
        localStringBuilder.append(paramFile);
        localStringBuilder.append("' cannot be read");
        throw new IOException(localStringBuilder.toString());
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("File '");
      localStringBuilder.append(paramFile);
      localStringBuilder.append("' exists but is a directory");
      throw new IOException(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("File '");
    localStringBuilder.append(paramFile);
    localStringBuilder.append("' does not exist");
    throw new FileNotFoundException(localStringBuilder.toString());
  }
  
  /* Error */
  public static byte[] read(File paramFile)
    throws OutOfMemoryError
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_0
    //   7: invokestatic 578	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   10: astore_1
    //   11: aload_1
    //   12: astore_0
    //   13: aload_1
    //   14: invokestatic 748	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:toByteArrayOutputStream	(Ljava/io/InputStream;)Ljava/io/ByteArrayOutputStream;
    //   17: invokevirtual 753	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   20: astore_2
    //   21: aload_1
    //   22: ifnull +7 -> 29
    //   25: aload_1
    //   26: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   29: aload_2
    //   30: areturn
    //   31: astore_1
    //   32: goto +46 -> 78
    //   35: astore_2
    //   36: goto +16 -> 52
    //   39: astore_2
    //   40: goto +22 -> 62
    //   43: astore_1
    //   44: aconst_null
    //   45: astore_0
    //   46: goto +32 -> 78
    //   49: astore_2
    //   50: aconst_null
    //   51: astore_1
    //   52: aload_1
    //   53: astore_0
    //   54: aload_2
    //   55: invokevirtual 486	java/lang/OutOfMemoryError:printStackTrace	()V
    //   58: aload_1
    //   59: astore_0
    //   60: aload_2
    //   61: athrow
    //   62: aload_1
    //   63: astore_0
    //   64: aload_2
    //   65: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   68: aload_1
    //   69: ifnull +7 -> 76
    //   72: aload_1
    //   73: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   76: aconst_null
    //   77: areturn
    //   78: aload_0
    //   79: ifnull +7 -> 86
    //   82: aload_0
    //   83: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   86: aload_1
    //   87: athrow
    //   88: astore_2
    //   89: aconst_null
    //   90: astore_1
    //   91: goto -29 -> 62
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	paramFile	File
    //   10	16	1	localFileInputStream	FileInputStream
    //   31	1	1	localObject1	Object
    //   43	1	1	localObject2	Object
    //   51	40	1	localCloseable	Closeable
    //   20	10	2	arrayOfByte	byte[]
    //   35	1	2	localOutOfMemoryError1	OutOfMemoryError
    //   39	1	2	localException1	Exception
    //   49	16	2	localOutOfMemoryError2	OutOfMemoryError
    //   88	1	2	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   13	21	31	finally
    //   54	58	31	finally
    //   60	62	31	finally
    //   64	68	31	finally
    //   13	21	35	java/lang/OutOfMemoryError
    //   13	21	39	java/lang/Exception
    //   6	11	43	finally
    //   6	11	49	java/lang/OutOfMemoryError
    //   6	11	88	java/lang/Exception
  }
  
  public static byte[] read(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    if (paramInputStream.read(arrayOfByte, 0, paramInt) < paramInt) {
      return null;
    }
    return arrayOfByte;
  }
  
  public static byte[] read(InputStream paramInputStream, int paramInt1, int paramInt2)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt2];
    paramInputStream.read(arrayOfByte, paramInt1, paramInt2);
    return arrayOfByte;
  }
  
  public static byte[] read(RandomAccessFile paramRandomAccessFile, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramRandomAccessFile != null)
    {
      long l2 = paramInt1;
      if ((l2 < paramRandomAccessFile.length()) && (paramInt2 > 0))
      {
        long l1;
        if (paramInt1 + paramInt2 > paramRandomAccessFile.length()) {
          l1 = paramRandomAccessFile.length() - l2;
        } else {
          l1 = paramInt2;
        }
        paramRandomAccessFile.seek(l2);
        byte[] arrayOfByte = new byte[(int)l1];
        paramRandomAccessFile.readFully(arrayOfByte);
        return arrayOfByte;
      }
    }
    return null;
  }
  
  /* Error */
  public static byte[] read(String paramString, long paramLong, int paramInt)
    throws OutOfMemoryError
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: aconst_null
    //   4: astore 5
    //   6: aload 5
    //   8: astore 4
    //   10: iload_3
    //   11: newarray <illegal type>
    //   13: astore 6
    //   15: aload 5
    //   17: astore 4
    //   19: aload_0
    //   20: invokestatic 773	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:openInputStream	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   23: astore 5
    //   25: aload 5
    //   27: astore 4
    //   29: aload 5
    //   31: lload_1
    //   32: invokevirtual 777	java/io/InputStream:skip	(J)J
    //   35: pop2
    //   36: aload 5
    //   38: astore 4
    //   40: aload 5
    //   42: aload 6
    //   44: invokevirtual 323	java/io/InputStream:read	([B)I
    //   47: pop
    //   48: aload 5
    //   50: ifnull +8 -> 58
    //   53: aload 5
    //   55: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   58: aload 6
    //   60: areturn
    //   61: astore 6
    //   63: goto +13 -> 76
    //   66: astore_0
    //   67: goto +169 -> 236
    //   70: astore 6
    //   72: aload 7
    //   74: astore 5
    //   76: aload 5
    //   78: astore 4
    //   80: aload 6
    //   82: invokevirtual 486	java/lang/OutOfMemoryError:printStackTrace	()V
    //   85: aload 5
    //   87: astore 4
    //   89: new 184	java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   96: astore 7
    //   98: aload 5
    //   100: astore 4
    //   102: aload 7
    //   104: ldc_w 779
    //   107: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload 5
    //   113: astore 4
    //   115: aload 7
    //   117: aload_0
    //   118: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload 5
    //   124: astore 4
    //   126: aload 7
    //   128: ldc_w 781
    //   131: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload 5
    //   137: astore 4
    //   139: ldc 47
    //   141: aload 7
    //   143: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   146: invokestatic 214	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   149: aload 5
    //   151: astore 4
    //   153: aload 6
    //   155: athrow
    //   156: aconst_null
    //   157: astore 5
    //   159: aload 5
    //   161: astore 4
    //   163: new 184	java/lang/StringBuilder
    //   166: dup
    //   167: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   170: astore 6
    //   172: aload 5
    //   174: astore 4
    //   176: aload 6
    //   178: ldc_w 779
    //   181: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: pop
    //   185: aload 5
    //   187: astore 4
    //   189: aload 6
    //   191: aload_0
    //   192: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload 5
    //   198: astore 4
    //   200: aload 6
    //   202: ldc_w 783
    //   205: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: pop
    //   209: aload 5
    //   211: astore 4
    //   213: ldc 47
    //   215: aload 6
    //   217: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   220: invokestatic 214	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   223: aload 5
    //   225: ifnull +8 -> 233
    //   228: aload 5
    //   230: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   233: aconst_null
    //   234: areturn
    //   235: astore_0
    //   236: aload 4
    //   238: ifnull +8 -> 246
    //   241: aload 4
    //   243: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   246: aload_0
    //   247: athrow
    //   248: astore 4
    //   250: goto -94 -> 156
    //   253: astore 4
    //   255: goto -96 -> 159
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	258	0	paramString	String
    //   0	258	1	paramLong	long
    //   0	258	3	paramInt	int
    //   8	234	4	localObject1	Object
    //   248	1	4	localException1	Exception
    //   253	1	4	localException2	Exception
    //   4	225	5	localObject2	Object
    //   13	46	6	arrayOfByte	byte[]
    //   61	1	6	localOutOfMemoryError1	OutOfMemoryError
    //   70	84	6	localOutOfMemoryError2	OutOfMemoryError
    //   170	46	6	localStringBuilder1	StringBuilder
    //   1	141	7	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   29	36	61	java/lang/OutOfMemoryError
    //   40	48	61	java/lang/OutOfMemoryError
    //   10	15	66	finally
    //   19	25	66	finally
    //   80	85	66	finally
    //   89	98	66	finally
    //   102	111	66	finally
    //   115	122	66	finally
    //   126	135	66	finally
    //   139	149	66	finally
    //   153	156	66	finally
    //   10	15	70	java/lang/OutOfMemoryError
    //   19	25	70	java/lang/OutOfMemoryError
    //   29	36	235	finally
    //   40	48	235	finally
    //   163	172	235	finally
    //   176	185	235	finally
    //   189	196	235	finally
    //   200	209	235	finally
    //   213	223	235	finally
    //   10	15	248	java/lang/Exception
    //   19	25	248	java/lang/Exception
    //   29	36	253	java/lang/Exception
    //   40	48	253	java/lang/Exception
  }
  
  public static ByteBuffer readInputStreamToByteBuffer(InputStream paramInputStream, ByteBuffer paramByteBuffer, long paramLong, int paramInt)
    throws IOException, OutOfMemoryError
  {
    if (paramInputStream == null) {
      return paramByteBuffer;
    }
    byte[] arrayOfByte = getInstance().accqureByteArray();
    paramInputStream.skip(paramLong);
    int j;
    int i;
    if (paramInt == -1)
    {
      j = paramInt;
      i = 4096;
    }
    else
    {
      if (paramInt < 4096) {
        i = paramInt;
      } else {
        i = 4096;
      }
      j = paramInt;
    }
    ByteBuffer localByteBuffer;
    for (;;)
    {
      i = paramInputStream.read(arrayOfByte, 0, i);
      localByteBuffer = paramByteBuffer;
      if (-1 == i) {
        break;
      }
      paramByteBuffer = reserveBufferSpace(paramByteBuffer, i);
      if (paramByteBuffer == null)
      {
        localByteBuffer = paramByteBuffer;
        break;
      }
      paramByteBuffer.put(arrayOfByte, 0, i);
      j -= i;
      if (j == 0)
      {
        localByteBuffer = paramByteBuffer;
        break;
      }
      if (paramInt == -1) {
        i = 4096;
      } else if (j < 4096) {
        i = j;
      } else {
        i = 4096;
      }
    }
    getInstance().releaseByteArray(arrayOfByte);
    return localByteBuffer;
  }
  
  public static String renameFileIfExist(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return paramString2;
      }
      boolean bool = checkFileName(paramString2);
      int j = 0;
      Object localObject1 = paramString2;
      if (!bool)
      {
        paramString2 = FILE_NAME_VALID_PATTERN.split(paramString2);
        localObject1 = new StringBuilder();
        int k = paramString2.length;
        i = 0;
        while (i < k)
        {
          ((StringBuilder)localObject1).append(paramString2[i]);
          i += 1;
        }
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      if (!new File(paramString1, (String)localObject1).exists())
      {
        paramString2 = new StringBuilder();
        paramString2.append(".");
        paramString2.append((String)localObject1);
        paramString2.append(".qbdltmp");
        if (!new File(paramString1, paramString2.toString()).exists()) {
          return (String)localObject1;
        }
      }
      int i = ((String)localObject1).lastIndexOf('.');
      if (i > -1)
      {
        paramString2 = ((String)localObject1).substring(0, i);
        localObject2 = ((String)localObject1).substring(i);
        localObject1 = paramString2;
        paramString2 = (String)localObject2;
      }
      else
      {
        paramString2 = "";
      }
      Object localObject2 = FILE_NAME_PATTERN.matcher((CharSequence)localObject1);
      i = j;
      if (((Matcher)localObject2).find())
      {
        localObject1 = ((Matcher)localObject2).group(1);
        i = StringUtils.parseInt(((Matcher)localObject2).group(2), 0);
      }
      Object localObject3;
      do
      {
        File localFile;
        do
        {
          j = i + 1;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append("(");
          ((StringBuilder)localObject2).append(j);
          ((StringBuilder)localObject2).append(")");
          ((StringBuilder)localObject2).append(paramString2);
          localObject2 = ((StringBuilder)localObject2).toString();
          localFile = new File(paramString1, (String)localObject2);
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append((String)localObject2);
          ((StringBuilder)localObject3).append(".qbdltmp");
          localObject3 = new File(paramString1, ((StringBuilder)localObject3).toString());
          i = j;
        } while (localFile.exists());
        i = j;
      } while (((File)localObject3).exists());
      return (String)localObject2;
    }
    return paramString2;
  }
  
  /* Error */
  public static boolean renameTo(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: iconst_0
    //   4: istore 4
    //   6: iload 5
    //   8: istore_3
    //   9: aload_0
    //   10: ifnull +417 -> 427
    //   13: iload 5
    //   15: istore_3
    //   16: aload_1
    //   17: ifnull +410 -> 427
    //   20: invokestatic 421	java/lang/System:currentTimeMillis	()J
    //   23: lstore 6
    //   25: aload_0
    //   26: invokevirtual 529	java/io/File:getParent	()Ljava/lang/String;
    //   29: aload_1
    //   30: invokevirtual 529	java/io/File:getParent	()Ljava/lang/String;
    //   33: invokevirtual 700	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   36: ifeq +76 -> 112
    //   39: aload_0
    //   40: aload_1
    //   41: invokevirtual 432	java/io/File:renameTo	(Ljava/io/File;)Z
    //   44: ifeq +68 -> 112
    //   47: new 184	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   54: astore 8
    //   56: aload 8
    //   58: ldc_w 816
    //   61: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload 8
    //   67: aload_0
    //   68: invokevirtual 366	java/io/File:getName	()Ljava/lang/String;
    //   71: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload 8
    //   77: ldc_w 818
    //   80: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload 8
    //   86: invokestatic 421	java/lang/System:currentTimeMillis	()J
    //   89: lload 6
    //   91: lsub
    //   92: invokevirtual 424	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: ldc_w 820
    //   99: aload 8
    //   101: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   104: invokestatic 214	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   107: iconst_1
    //   108: istore_3
    //   109: goto +5 -> 114
    //   112: iconst_0
    //   113: istore_3
    //   114: iload_3
    //   115: istore 5
    //   117: iload_3
    //   118: ifne +298 -> 416
    //   121: aconst_null
    //   122: astore 11
    //   124: aconst_null
    //   125: astore 9
    //   127: aconst_null
    //   128: astore 8
    //   130: aconst_null
    //   131: astore 12
    //   133: new 340	java/io/FileInputStream
    //   136: dup
    //   137: aload_0
    //   138: invokespecial 369	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   141: astore 10
    //   143: aload 12
    //   145: astore 8
    //   147: aload 11
    //   149: astore 9
    //   151: aload_1
    //   152: iconst_0
    //   153: invokestatic 316	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:createOuputStream	(Ljava/io/File;Z)Ljava/io/FileOutputStream;
    //   156: astore_1
    //   157: iload_3
    //   158: istore 4
    //   160: aload_1
    //   161: ifnull +76 -> 237
    //   164: aload_1
    //   165: astore 8
    //   167: aload_1
    //   168: astore 9
    //   170: ldc_w 342
    //   173: newarray <illegal type>
    //   175: astore 11
    //   177: aload_1
    //   178: astore 8
    //   180: aload_1
    //   181: astore 9
    //   183: aload 10
    //   185: aload 11
    //   187: invokevirtual 323	java/io/InputStream:read	([B)I
    //   190: istore_2
    //   191: iload_2
    //   192: iconst_m1
    //   193: if_icmpeq +20 -> 213
    //   196: aload_1
    //   197: astore 8
    //   199: aload_1
    //   200: astore 9
    //   202: aload_1
    //   203: aload 11
    //   205: iconst_0
    //   206: iload_2
    //   207: invokevirtual 329	java/io/FileOutputStream:write	([BII)V
    //   210: goto -33 -> 177
    //   213: aload_1
    //   214: astore 8
    //   216: aload_1
    //   217: astore 9
    //   219: aload_1
    //   220: invokevirtual 332	java/io/FileOutputStream:flush	()V
    //   223: aload_1
    //   224: astore 8
    //   226: aload_1
    //   227: astore 9
    //   229: aload_0
    //   230: invokevirtual 409	java/io/File:delete	()Z
    //   233: pop
    //   234: iconst_1
    //   235: istore 4
    //   237: iload 4
    //   239: istore 5
    //   241: aload 10
    //   243: invokevirtual 335	java/io/InputStream:close	()V
    //   246: iload 4
    //   248: istore 5
    //   250: aload_1
    //   251: ifnull +165 -> 416
    //   254: iload 4
    //   256: istore 5
    //   258: aload_1
    //   259: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   262: iload 4
    //   264: istore 5
    //   266: goto +150 -> 416
    //   269: astore_0
    //   270: iload 4
    //   272: istore_3
    //   273: iload_3
    //   274: istore 5
    //   276: aload_0
    //   277: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   280: iload_3
    //   281: istore 5
    //   283: goto +133 -> 416
    //   286: astore_1
    //   287: aload 10
    //   289: astore_0
    //   290: goto +84 -> 374
    //   293: astore 8
    //   295: aload 9
    //   297: astore_0
    //   298: aload 10
    //   300: astore_1
    //   301: goto +16 -> 317
    //   304: astore_1
    //   305: aconst_null
    //   306: astore_0
    //   307: goto +67 -> 374
    //   310: astore 8
    //   312: aconst_null
    //   313: astore_0
    //   314: aload 9
    //   316: astore_1
    //   317: aload 8
    //   319: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   322: aload_1
    //   323: ifnull +13 -> 336
    //   326: iload_3
    //   327: istore 5
    //   329: aload_1
    //   330: invokevirtual 335	java/io/InputStream:close	()V
    //   333: goto +3 -> 336
    //   336: iload_3
    //   337: istore 5
    //   339: aload_0
    //   340: ifnull +76 -> 416
    //   343: iload_3
    //   344: istore 5
    //   346: aload_0
    //   347: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   350: iload_3
    //   351: istore 5
    //   353: goto +63 -> 416
    //   356: astore 10
    //   358: aload_1
    //   359: astore 8
    //   361: aload_0
    //   362: astore 9
    //   364: aload 10
    //   366: astore_1
    //   367: aload 8
    //   369: astore_0
    //   370: aload 9
    //   372: astore 8
    //   374: aload_0
    //   375: ifnull +13 -> 388
    //   378: iload_3
    //   379: istore 5
    //   381: aload_0
    //   382: invokevirtual 335	java/io/InputStream:close	()V
    //   385: goto +3 -> 388
    //   388: aload 8
    //   390: ifnull +21 -> 411
    //   393: iload_3
    //   394: istore 5
    //   396: aload 8
    //   398: invokevirtual 333	java/io/FileOutputStream:close	()V
    //   401: goto +10 -> 411
    //   404: iload_3
    //   405: istore 5
    //   407: aload_0
    //   408: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   411: iload_3
    //   412: istore 5
    //   414: aload_1
    //   415: athrow
    //   416: iload 5
    //   418: ireturn
    //   419: astore_0
    //   420: iload 4
    //   422: istore_3
    //   423: aload_0
    //   424: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   427: iload_3
    //   428: ireturn
    //   429: astore_0
    //   430: iload 5
    //   432: istore_3
    //   433: goto -10 -> 423
    //   436: astore_0
    //   437: goto -164 -> 273
    //   440: astore_0
    //   441: goto -37 -> 404
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	444	0	paramFile1	File
    //   0	444	1	paramFile2	File
    //   190	17	2	i	int
    //   8	425	3	bool1	boolean
    //   4	417	4	bool2	boolean
    //   1	430	5	bool3	boolean
    //   23	67	6	l	long
    //   54	171	8	localObject1	Object
    //   293	1	8	localException1	Exception
    //   310	8	8	localException2	Exception
    //   359	38	8	localObject2	Object
    //   125	246	9	localObject3	Object
    //   141	158	10	localFileInputStream	FileInputStream
    //   356	9	10	localObject4	Object
    //   122	82	11	arrayOfByte	byte[]
    //   131	13	12	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   241	246	269	java/io/IOException
    //   258	262	269	java/io/IOException
    //   151	157	286	finally
    //   170	177	286	finally
    //   183	191	286	finally
    //   202	210	286	finally
    //   219	223	286	finally
    //   229	234	286	finally
    //   151	157	293	java/lang/Exception
    //   170	177	293	java/lang/Exception
    //   183	191	293	java/lang/Exception
    //   202	210	293	java/lang/Exception
    //   219	223	293	java/lang/Exception
    //   229	234	293	java/lang/Exception
    //   133	143	304	finally
    //   133	143	310	java/lang/Exception
    //   317	322	356	finally
    //   25	107	419	java/lang/Exception
    //   241	246	429	java/lang/Exception
    //   258	262	429	java/lang/Exception
    //   276	280	429	java/lang/Exception
    //   329	333	429	java/lang/Exception
    //   346	350	429	java/lang/Exception
    //   381	385	429	java/lang/Exception
    //   396	401	429	java/lang/Exception
    //   407	411	429	java/lang/Exception
    //   414	416	429	java/lang/Exception
    //   329	333	436	java/io/IOException
    //   346	350	436	java/io/IOException
    //   381	385	440	java/io/IOException
    //   396	401	440	java/io/IOException
  }
  
  private static ByteBuffer reserveBufferSpace(ByteBuffer paramByteBuffer, int paramInt)
  {
    int i;
    if (paramByteBuffer.remaining() < paramInt)
    {
      i = paramByteBuffer.capacity();
      paramInt /= 2048;
    }
    try
    {
      ByteBuffer localByteBuffer = ByteBuffer.allocate(i + (paramInt + 1) * 2048);
      localByteBuffer.put(paramByteBuffer.array(), 0, paramByteBuffer.position());
      return localByteBuffer;
    }
    catch (Throwable paramByteBuffer)
    {
      for (;;) {}
    }
    return null;
    return paramByteBuffer;
  }
  
  /* Error */
  public static boolean save(File paramFile, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_0
    //   5: invokestatic 735	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   8: astore 4
    //   10: aload 4
    //   12: ifnull +31 -> 43
    //   15: aload 4
    //   17: astore_2
    //   18: aload 4
    //   20: astore_3
    //   21: aload 4
    //   23: aload_1
    //   24: iconst_0
    //   25: aload_1
    //   26: arraylength
    //   27: invokevirtual 378	java/io/OutputStream:write	([BII)V
    //   30: aload 4
    //   32: astore_2
    //   33: aload 4
    //   35: astore_3
    //   36: aload 4
    //   38: invokevirtual 835	java/io/OutputStream:close	()V
    //   41: iconst_1
    //   42: ireturn
    //   43: aload 4
    //   45: ifnull +15 -> 60
    //   48: aload 4
    //   50: invokevirtual 835	java/io/OutputStream:close	()V
    //   53: iconst_0
    //   54: ireturn
    //   55: astore_0
    //   56: aload_0
    //   57: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   60: iconst_0
    //   61: ireturn
    //   62: astore_0
    //   63: goto +87 -> 150
    //   66: astore_1
    //   67: aload_3
    //   68: astore_2
    //   69: new 184	java/lang/StringBuilder
    //   72: dup
    //   73: invokespecial 185	java/lang/StringBuilder:<init>	()V
    //   76: astore 4
    //   78: aload_3
    //   79: astore_2
    //   80: aload 4
    //   82: ldc_w 837
    //   85: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: pop
    //   89: aload_3
    //   90: astore_2
    //   91: aload 4
    //   93: aload_0
    //   94: invokevirtual 262	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   97: pop
    //   98: aload_3
    //   99: astore_2
    //   100: aload 4
    //   102: ldc_w 839
    //   105: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: pop
    //   109: aload_3
    //   110: astore_2
    //   111: aload 4
    //   113: aload_1
    //   114: invokevirtual 842	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   117: invokevirtual 191	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload_3
    //   122: astore_2
    //   123: ldc 47
    //   125: aload 4
    //   127: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: invokestatic 214	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: aload_3
    //   134: ifnull +14 -> 148
    //   137: aload_3
    //   138: invokevirtual 835	java/io/OutputStream:close	()V
    //   141: iconst_0
    //   142: ireturn
    //   143: astore_0
    //   144: aload_0
    //   145: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   148: iconst_0
    //   149: ireturn
    //   150: aload_2
    //   151: ifnull +15 -> 166
    //   154: aload_2
    //   155: invokevirtual 835	java/io/OutputStream:close	()V
    //   158: goto +8 -> 166
    //   161: astore_1
    //   162: aload_1
    //   163: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   166: aload_0
    //   167: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	168	0	paramFile	File
    //   0	168	1	paramArrayOfByte	byte[]
    //   3	152	2	localObject1	Object
    //   1	137	3	localObject2	Object
    //   8	118	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   48	53	55	java/io/IOException
    //   4	10	62	finally
    //   21	30	62	finally
    //   36	41	62	finally
    //   69	78	62	finally
    //   80	89	62	finally
    //   91	98	62	finally
    //   100	109	62	finally
    //   111	121	62	finally
    //   123	133	62	finally
    //   4	10	66	java/lang/Exception
    //   21	30	66	java/lang/Exception
    //   36	41	66	java/lang/Exception
    //   137	141	143	java/io/IOException
    //   154	158	161	java/io/IOException
  }
  
  public static int saveImage(File paramFile, Bitmap paramBitmap)
  {
    if ((isExternalFile(paramFile)) && (!hasSDcard())) {
      return ERR_SDCARD_NOT_AVAILABLE;
    }
    if (!saveImage(paramFile, paramBitmap, Bitmap.CompressFormat.PNG)) {
      return ERR_SAVE_IMAGE_FAILED;
    }
    return SUCCESS;
  }
  
  public static int saveImage(File paramFile, InputStream paramInputStream)
  {
    if ((paramFile != null) && (paramInputStream != null)) {
      return saveImage(paramFile, getBitmapBytesFromInputStream(paramInputStream));
    }
    return ERR_SAVE_IMAGE_FAILED;
  }
  
  public static int saveImage(File paramFile, byte[] paramArrayOfByte)
  {
    if ((paramFile != null) && (paramArrayOfByte != null))
    {
      if (!hasSDcard()) {
        return ERR_SDCARD_NOT_AVAILABLE;
      }
      if (paramArrayOfByte.length > getSdcardFreeSpace()) {
        return ERR_SAVE_IMAGE_FAILED;
      }
      if (save(paramFile, paramArrayOfByte)) {
        return SUCCESS;
      }
      return ERR_SAVE_IMAGE_FAILED;
    }
    return ERR_SAVE_IMAGE_FAILED;
  }
  
  /* Error */
  public static boolean saveImage(File paramFile, Bitmap paramBitmap, Bitmap.CompressFormat paramCompressFormat)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +198 -> 199
    //   4: aload_1
    //   5: ifnull +194 -> 199
    //   8: aload_1
    //   9: invokevirtual 870	android/graphics/Bitmap:isRecycled	()Z
    //   12: ifne +187 -> 199
    //   15: aload_0
    //   16: invokevirtual 244	java/io/File:exists	()Z
    //   19: ifeq +8 -> 27
    //   22: aload_0
    //   23: invokevirtual 409	java/io/File:delete	()Z
    //   26: pop
    //   27: new 750	java/io/ByteArrayOutputStream
    //   30: dup
    //   31: invokespecial 871	java/io/ByteArrayOutputStream:<init>	()V
    //   34: astore_3
    //   35: aload_1
    //   36: aload_2
    //   37: bipush 100
    //   39: aload_3
    //   40: invokevirtual 875	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   43: pop
    //   44: aload_3
    //   45: invokevirtual 753	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   48: astore 6
    //   50: aload_3
    //   51: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   54: aload_0
    //   55: invokestatic 846	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:isExternalFile	(Ljava/io/File;)Z
    //   58: ifeq +22 -> 80
    //   61: invokestatic 468	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:hasSDcard	()Z
    //   64: ifeq +16 -> 80
    //   67: aload 6
    //   69: arraylength
    //   70: i2l
    //   71: invokestatic 863	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:getSdcardFreeSpace	()J
    //   74: lcmp
    //   75: ifle +5 -> 80
    //   78: iconst_0
    //   79: ireturn
    //   80: aconst_null
    //   81: astore 4
    //   83: aconst_null
    //   84: astore_2
    //   85: aconst_null
    //   86: astore_3
    //   87: aconst_null
    //   88: astore 5
    //   90: aload 4
    //   92: astore_1
    //   93: aload_0
    //   94: iconst_0
    //   95: invokestatic 316	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:createOuputStream	(Ljava/io/File;Z)Ljava/io/FileOutputStream;
    //   98: astore 7
    //   100: aload 5
    //   102: astore_0
    //   103: aload 7
    //   105: ifnull +45 -> 150
    //   108: aload 4
    //   110: astore_1
    //   111: new 877	java/io/BufferedOutputStream
    //   114: dup
    //   115: aload 7
    //   117: invokespecial 880	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   120: astore_0
    //   121: aload_0
    //   122: aload 6
    //   124: invokevirtual 882	java/io/OutputStream:write	([B)V
    //   127: aload_0
    //   128: invokevirtual 883	java/io/OutputStream:flush	()V
    //   131: goto +19 -> 150
    //   134: astore_2
    //   135: aload_0
    //   136: astore_1
    //   137: aload_2
    //   138: astore_0
    //   139: goto +54 -> 193
    //   142: astore_2
    //   143: goto +23 -> 166
    //   146: astore_2
    //   147: goto +34 -> 181
    //   150: aload_0
    //   151: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   154: goto +45 -> 199
    //   157: astore_0
    //   158: goto +35 -> 193
    //   161: astore_1
    //   162: aload_2
    //   163: astore_0
    //   164: aload_1
    //   165: astore_2
    //   166: aload_0
    //   167: astore_1
    //   168: aload_2
    //   169: invokevirtual 486	java/lang/OutOfMemoryError:printStackTrace	()V
    //   172: aload_0
    //   173: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   176: iconst_0
    //   177: ireturn
    //   178: astore_2
    //   179: aload_3
    //   180: astore_0
    //   181: aload_0
    //   182: astore_1
    //   183: aload_2
    //   184: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   187: aload_0
    //   188: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   191: iconst_0
    //   192: ireturn
    //   193: aload_1
    //   194: invokestatic 755	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:closeQuietly	(Ljava/io/Closeable;)V
    //   197: aload_0
    //   198: athrow
    //   199: iconst_1
    //   200: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	201	0	paramFile	File
    //   0	201	1	paramBitmap	Bitmap
    //   0	201	2	paramCompressFormat	Bitmap.CompressFormat
    //   34	146	3	localByteArrayOutputStream	ByteArrayOutputStream
    //   81	28	4	localObject1	Object
    //   88	13	5	localObject2	Object
    //   48	75	6	arrayOfByte	byte[]
    //   98	18	7	localFileOutputStream	FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   121	131	134	finally
    //   121	131	142	java/lang/OutOfMemoryError
    //   121	131	146	java/lang/Exception
    //   93	100	157	finally
    //   111	121	157	finally
    //   168	172	157	finally
    //   183	187	157	finally
    //   93	100	161	java/lang/OutOfMemoryError
    //   111	121	161	java/lang/OutOfMemoryError
    //   93	100	178	java/lang/Exception
    //   111	121	178	java/lang/Exception
  }
  
  /* Error */
  public static boolean saveImageBMP(File paramFile, Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +248 -> 249
    //   4: aload_1
    //   5: ifnull +244 -> 249
    //   8: aload_1
    //   9: invokevirtual 870	android/graphics/Bitmap:isRecycled	()Z
    //   12: ifne +237 -> 249
    //   15: aload_0
    //   16: invokevirtual 244	java/io/File:exists	()Z
    //   19: ifeq +8 -> 27
    //   22: aload_0
    //   23: invokevirtual 409	java/io/File:delete	()Z
    //   26: pop
    //   27: aconst_null
    //   28: astore 5
    //   30: aconst_null
    //   31: astore 6
    //   33: aconst_null
    //   34: astore 7
    //   36: aload 7
    //   38: astore 4
    //   40: aload_1
    //   41: invokevirtual 888	android/graphics/Bitmap:getWidth	()I
    //   44: i2s
    //   45: istore_2
    //   46: aload 7
    //   48: astore 4
    //   50: aload_1
    //   51: invokevirtual 891	android/graphics/Bitmap:getHeight	()I
    //   54: i2s
    //   55: istore_3
    //   56: aload 7
    //   58: astore 4
    //   60: iload_2
    //   61: iload_3
    //   62: imul
    //   63: iconst_4
    //   64: imul
    //   65: invokestatic 221	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   68: astore 8
    //   70: aload 7
    //   72: astore 4
    //   74: aload_1
    //   75: aload 8
    //   77: invokevirtual 895	android/graphics/Bitmap:copyPixelsToBuffer	(Ljava/nio/Buffer;)V
    //   80: aload 7
    //   82: astore 4
    //   84: new 877	java/io/BufferedOutputStream
    //   87: dup
    //   88: aload_0
    //   89: invokestatic 735	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   92: invokespecial 880	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   95: astore_0
    //   96: aload_0
    //   97: iload_2
    //   98: invokevirtual 897	java/io/BufferedOutputStream:write	(I)V
    //   101: aload_0
    //   102: iload_2
    //   103: bipush 8
    //   105: ishr
    //   106: invokevirtual 897	java/io/BufferedOutputStream:write	(I)V
    //   109: aload_0
    //   110: iload_3
    //   111: invokevirtual 897	java/io/BufferedOutputStream:write	(I)V
    //   114: aload_0
    //   115: iload_3
    //   116: bipush 8
    //   118: ishr
    //   119: invokevirtual 897	java/io/BufferedOutputStream:write	(I)V
    //   122: aload_0
    //   123: aload 8
    //   125: invokevirtual 829	java/nio/ByteBuffer:array	()[B
    //   128: invokevirtual 898	java/io/BufferedOutputStream:write	([B)V
    //   131: aload_0
    //   132: invokevirtual 899	java/io/BufferedOutputStream:flush	()V
    //   135: aload_0
    //   136: invokevirtual 900	java/io/BufferedOutputStream:close	()V
    //   139: aload_0
    //   140: invokevirtual 900	java/io/BufferedOutputStream:close	()V
    //   143: iconst_1
    //   144: ireturn
    //   145: astore_0
    //   146: aload_0
    //   147: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   150: iconst_1
    //   151: ireturn
    //   152: astore_1
    //   153: aload_0
    //   154: astore 4
    //   156: aload_1
    //   157: astore_0
    //   158: goto +71 -> 229
    //   161: astore_1
    //   162: goto +15 -> 177
    //   165: astore_1
    //   166: goto +39 -> 205
    //   169: astore_0
    //   170: goto +59 -> 229
    //   173: astore_1
    //   174: aload 5
    //   176: astore_0
    //   177: aload_0
    //   178: astore 4
    //   180: aload_1
    //   181: invokevirtual 486	java/lang/OutOfMemoryError:printStackTrace	()V
    //   184: aload_0
    //   185: ifnull +14 -> 199
    //   188: aload_0
    //   189: invokevirtual 900	java/io/BufferedOutputStream:close	()V
    //   192: iconst_0
    //   193: ireturn
    //   194: astore_0
    //   195: aload_0
    //   196: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   199: iconst_0
    //   200: ireturn
    //   201: astore_1
    //   202: aload 6
    //   204: astore_0
    //   205: aload_0
    //   206: astore 4
    //   208: aload_1
    //   209: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   212: aload_0
    //   213: ifnull +14 -> 227
    //   216: aload_0
    //   217: invokevirtual 900	java/io/BufferedOutputStream:close	()V
    //   220: iconst_0
    //   221: ireturn
    //   222: astore_0
    //   223: aload_0
    //   224: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   227: iconst_0
    //   228: ireturn
    //   229: aload 4
    //   231: ifnull +16 -> 247
    //   234: aload 4
    //   236: invokevirtual 900	java/io/BufferedOutputStream:close	()V
    //   239: goto +8 -> 247
    //   242: astore_1
    //   243: aload_1
    //   244: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   247: aload_0
    //   248: athrow
    //   249: iconst_1
    //   250: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	251	0	paramFile	File
    //   0	251	1	paramBitmap	Bitmap
    //   45	61	2	i	int
    //   55	64	3	j	int
    //   38	197	4	localObject1	Object
    //   28	147	5	localObject2	Object
    //   31	172	6	localObject3	Object
    //   34	47	7	localObject4	Object
    //   68	56	8	localByteBuffer	ByteBuffer
    // Exception table:
    //   from	to	target	type
    //   139	143	145	java/io/IOException
    //   96	139	152	finally
    //   96	139	161	java/lang/OutOfMemoryError
    //   96	139	165	java/lang/Exception
    //   40	46	169	finally
    //   50	56	169	finally
    //   60	70	169	finally
    //   74	80	169	finally
    //   84	96	169	finally
    //   180	184	169	finally
    //   208	212	169	finally
    //   40	46	173	java/lang/OutOfMemoryError
    //   50	56	173	java/lang/OutOfMemoryError
    //   60	70	173	java/lang/OutOfMemoryError
    //   74	80	173	java/lang/OutOfMemoryError
    //   84	96	173	java/lang/OutOfMemoryError
    //   188	192	194	java/io/IOException
    //   40	46	201	java/lang/Exception
    //   50	56	201	java/lang/Exception
    //   60	70	201	java/lang/Exception
    //   74	80	201	java/lang/Exception
    //   84	96	201	java/lang/Exception
    //   216	220	222	java/io/IOException
    //   234	239	242	java/io/IOException
  }
  
  public static void saveLocalImage(Context paramContext, final String paramString1, final String paramString2, final boolean paramBoolean)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      new Thread("saveLocalImage")
      {
        public void run()
        {
          Object localObject = FileUtils.getFileName(paramString2);
          localObject = FileUtils.renameFileIfExist(paramString1, (String)localObject);
          localObject = new File(paramString1, (String)localObject);
          boolean bool = FileUtils.copyFile(paramString2, ((File)localObject).getPath());
          if (paramBoolean)
          {
            if (bool)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("图片保存在");
              ((StringBuilder)localObject).append(paramString1);
              MttToaster.show(((StringBuilder)localObject).toString(), 0);
              return;
            }
            MttToaster.show("图片保存失败", 0);
          }
        }
      }.start();
      return;
    }
    if (paramBoolean) {
      MttToaster.show("图片保存失败", 0);
    }
  }
  
  public static void shrinkDir(File paramFile, long paramLong1, long paramLong2)
  {
    if (paramFile != null)
    {
      if (!paramFile.isDirectory()) {
        return;
      }
      paramFile = paramFile.listFiles(new FileFileFilter());
      if (paramFile != null)
      {
        if (paramFile.length <= 0) {
          return;
        }
        try
        {
          Arrays.sort(paramFile, new FileComparator());
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder2 = new StringBuilder();
          localStringBuilder2.append("sort files ex: ");
          localStringBuilder2.append(localException.toString());
          LogUtils.d("FileUtils", localStringBuilder2.toString());
        }
        long l3 = System.currentTimeMillis();
        long l2 = 0L;
        long l1 = l2;
        int i;
        if (paramFile != null)
        {
          l1 = l2;
          if (paramFile.length > 0)
          {
            int j = paramFile.length;
            i = 0;
            for (;;)
            {
              l1 = l2;
              if (i >= j) {
                break;
              }
              localStringBuilder1 = paramFile[i];
              if (l3 - paramLong2 > localStringBuilder1.lastModified()) {
                localStringBuilder1.delete();
              } else {
                l2 += localStringBuilder1.length();
              }
              i += 1;
            }
          }
        }
        StringBuilder localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("files : ");
        localStringBuilder1.append(paramFile.length);
        LogUtils.d("FileUtils", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("totalFileSize : ");
        localStringBuilder1.append(l1);
        LogUtils.d("FileUtils", localStringBuilder1.toString());
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("DEFAULT_L2_MAX_SIZE : ");
        double d1 = paramLong1;
        Double.isNaN(d1);
        double d2 = 0.95D * d1;
        localStringBuilder1.append(d2);
        LogUtils.d("FileUtils", localStringBuilder1.toString());
        paramLong1 = l1;
        if (l1 >= d2)
        {
          Double.isNaN(d1);
          d1 *= 0.7D;
          paramLong2 = d1;
          localStringBuilder1 = new StringBuilder();
          localStringBuilder1.append("desiredSize : ");
          localStringBuilder1.append(d1);
          LogUtils.d("FileUtils", localStringBuilder1.toString());
          i = paramFile.length - 1;
          for (;;)
          {
            paramLong1 = l1;
            if (i < 0) {
              break;
            }
            paramLong1 = l1;
            if (l1 <= paramLong2) {
              break;
            }
            localStringBuilder1 = paramFile[i];
            paramLong1 = l1;
            if (localStringBuilder1 != null)
            {
              paramLong1 = localStringBuilder1.length();
              localStringBuilder1.delete();
              paramLong1 = l1 - paramLong1;
            }
            i -= 1;
            l1 = paramLong1;
          }
        }
        paramFile = new StringBuilder();
        paramFile.append("totalFileSize 2: ");
        paramFile.append(paramLong1);
        LogUtils.d("FileUtils", paramFile.toString());
        paramLong1 = System.currentTimeMillis();
        paramFile = new StringBuilder();
        paramFile.append("L2 Cache image was full, shrinked to 70% in ");
        paramFile.append(paramLong1 - l3);
        paramFile.append("ms.");
        LogUtils.d("FileUtils", paramFile.toString());
        return;
      }
      return;
    }
  }
  
  public static byte[] toByteArray(InputStream paramInputStream)
    throws IOException, OutOfMemoryError
  {
    return toByteArrayOutputStream(paramInputStream).toByteArray();
  }
  
  public static ByteArrayOutputStream toByteArrayOutputStream(InputStream paramInputStream)
    throws IOException, OutOfMemoryError
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream;
  }
  
  public static ByteBuffer toByteBuffer(InputStream paramInputStream)
    throws IOException, OutOfMemoryError
  {
    return readInputStreamToByteBuffer(paramInputStream, getInstance().acquireByteBuffer(), 0L, -1);
  }
  
  public static byte[] toBytes(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }
  
  public static String toString(InputStream paramInputStream, String paramString)
  {
    try
    {
      paramInputStream = new String(toBytes(paramInputStream), paramString);
      return paramInputStream;
    }
    catch (IOException paramInputStream)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static void write(String paramString, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramString != null) {
      paramOutputStream.write(paramString.getBytes());
    }
  }
  
  public static void write(String paramString1, OutputStream paramOutputStream, String paramString2)
    throws IOException
  {
    if (paramString1 != null)
    {
      if (paramString2 == null)
      {
        write(paramString1, paramOutputStream);
        return;
      }
      paramOutputStream.write(paramString1.getBytes(paramString2));
    }
  }
  
  public static void write(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramArrayOfByte != null) {
      paramOutputStream.write(paramArrayOfByte);
    }
  }
  
  /* Error */
  public static File writeInputStreamToFile(InputStream paramInputStream, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aload_0
    //   4: ifnull +183 -> 187
    //   7: aload_1
    //   8: ifnonnull +5 -> 13
    //   11: aconst_null
    //   12: areturn
    //   13: aload_1
    //   14: iconst_0
    //   15: invokestatic 316	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:createOuputStream	(Ljava/io/File;Z)Ljava/io/FileOutputStream;
    //   18: astore 4
    //   20: aload 4
    //   22: ifnull +65 -> 87
    //   25: aload 4
    //   27: astore_3
    //   28: sipush 4096
    //   31: newarray <illegal type>
    //   33: astore 6
    //   35: aload 4
    //   37: astore_3
    //   38: aload_0
    //   39: aload 6
    //   41: invokevirtual 323	java/io/InputStream:read	([B)I
    //   44: istore_2
    //   45: iload_2
    //   46: ifle +18 -> 64
    //   49: aload 4
    //   51: astore_3
    //   52: aload 4
    //   54: aload 6
    //   56: iconst_0
    //   57: iload_2
    //   58: invokevirtual 378	java/io/OutputStream:write	([BII)V
    //   61: goto -26 -> 35
    //   64: aload 4
    //   66: astore_3
    //   67: aload 4
    //   69: invokevirtual 835	java/io/OutputStream:close	()V
    //   72: aload 5
    //   74: astore 4
    //   76: goto +13 -> 89
    //   79: astore_0
    //   80: goto +39 -> 119
    //   83: astore_0
    //   84: goto +65 -> 149
    //   87: aconst_null
    //   88: astore_1
    //   89: aload 4
    //   91: ifnull +16 -> 107
    //   94: aload 4
    //   96: invokevirtual 835	java/io/OutputStream:close	()V
    //   99: goto +8 -> 107
    //   102: astore_0
    //   103: aload_0
    //   104: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   107: aload_1
    //   108: areturn
    //   109: astore_0
    //   110: aconst_null
    //   111: astore_3
    //   112: goto +57 -> 169
    //   115: astore_0
    //   116: aconst_null
    //   117: astore 4
    //   119: aload 4
    //   121: astore_3
    //   122: aload_0
    //   123: invokevirtual 278	java/lang/Exception:printStackTrace	()V
    //   126: aload 4
    //   128: ifnull +38 -> 166
    //   131: aload 4
    //   133: invokevirtual 835	java/io/OutputStream:close	()V
    //   136: aconst_null
    //   137: areturn
    //   138: astore_0
    //   139: aload_0
    //   140: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   143: aconst_null
    //   144: areturn
    //   145: astore_0
    //   146: aconst_null
    //   147: astore 4
    //   149: aload 4
    //   151: astore_3
    //   152: aload_0
    //   153: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   156: aload 4
    //   158: ifnull +8 -> 166
    //   161: aload 4
    //   163: invokevirtual 835	java/io/OutputStream:close	()V
    //   166: aconst_null
    //   167: areturn
    //   168: astore_0
    //   169: aload_3
    //   170: ifnull +15 -> 185
    //   173: aload_3
    //   174: invokevirtual 835	java/io/OutputStream:close	()V
    //   177: goto +8 -> 185
    //   180: astore_1
    //   181: aload_1
    //   182: invokevirtual 334	java/io/IOException:printStackTrace	()V
    //   185: aload_0
    //   186: athrow
    //   187: aconst_null
    //   188: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	189	0	paramInputStream	InputStream
    //   0	189	1	paramFile	File
    //   44	14	2	i	int
    //   27	147	3	localObject1	Object
    //   18	144	4	localObject2	Object
    //   1	72	5	localObject3	Object
    //   33	22	6	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   28	35	79	java/lang/Exception
    //   38	45	79	java/lang/Exception
    //   52	61	79	java/lang/Exception
    //   67	72	79	java/lang/Exception
    //   28	35	83	java/io/IOException
    //   38	45	83	java/io/IOException
    //   52	61	83	java/io/IOException
    //   67	72	83	java/io/IOException
    //   94	99	102	java/io/IOException
    //   13	20	109	finally
    //   13	20	115	java/lang/Exception
    //   131	136	138	java/io/IOException
    //   161	166	138	java/io/IOException
    //   13	20	145	java/io/IOException
    //   28	35	168	finally
    //   38	45	168	finally
    //   52	61	168	finally
    //   67	72	168	finally
    //   122	126	168	finally
    //   152	156	168	finally
    //   173	177	180	java/io/IOException
  }
  
  public byte[] accqureByteArray()
  {
    synchronized (this.mByteArray)
    {
      if (!this.bByteInUse)
      {
        this.bByteInUse = true;
        byte[] arrayOfByte2 = this.mByteArray;
        return arrayOfByte2;
      }
      return new byte['က'];
    }
  }
  
  public void releaseByteArray(byte[] paramArrayOfByte)
  {
    synchronized (this.mByteArray)
    {
      if ((this.bByteInUse == true) && (this.mByteArray == paramArrayOfByte)) {
        this.bByteInUse = false;
      }
      return;
    }
  }
  
  public boolean releaseByteBuffer(ByteBuffer paramByteBuffer)
  {
    synchronized (this.mByteBufferPool)
    {
      if (isInPool(paramByteBuffer)) {
        return true;
      }
      if (paramByteBuffer.position() > 16384) {
        return true;
      }
      if (this.mByteBufferPoolAvailableSize < this.mByteBufferPool.length)
      {
        paramByteBuffer.clear();
        this.mByteBufferPool[this.mByteBufferPoolAvailableSize] = paramByteBuffer;
        this.mByteBufferPoolAvailableSize += 1;
        return true;
      }
      return true;
    }
  }
  
  static class FileComparator
    implements Comparator<File>
  {
    public int compare(File paramFile1, File paramFile2)
    {
      long l1 = paramFile1.length();
      long l2 = paramFile2.length();
      if (l1 < l2) {
        return -1;
      }
      if (l1 > l2) {
        return 1;
      }
      return 0;
    }
  }
  
  static class FileFileFilter
    implements FileFilter
  {
    public boolean accept(File paramFile)
    {
      return paramFile.isFile();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */