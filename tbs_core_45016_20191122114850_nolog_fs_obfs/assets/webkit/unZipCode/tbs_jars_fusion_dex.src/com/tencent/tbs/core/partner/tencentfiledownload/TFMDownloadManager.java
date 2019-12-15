package com.tencent.tbs.core.partner.tencentfiledownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.MainThreadExecutor;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.menu.X5SnapshotMenu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

public class TFMDownloadManager
  implements TFMDownloadListener
{
  private static final String DOWNLOAD_LOCK_FILENAME = "tftql.data";
  public static final String DOWNLOAD_URL = "http://appchannel.html5.qq.com/directdown?app=file&channel=11148";
  private static final int MSG_SHOW_TOAST = 1000;
  private static final int MSG_START_DOWNLOAD = 100;
  public static final String TAG = "TencentFileDownload";
  private static final String TFDOWNLOADER_THREAD_NAME = "tfdownloader_thread";
  private static final String TF_SIG = "3082019b30820104a00302010202044e3a4bd0300d06092a864886f70d010105050030123110300e060355040a130754656e63656e74301e170d3131303830343037333534345a170d3431303732373037333534345a30123110300e060355040a130754656e63656e7430819f300d06092a864886f70d010101050003818d00308189028181008beb584b8807431eca10a75996fcd95344198c145149c2c66004d208a03a2f733424fb5e6585b3a3d4edb7ab93866a2c712ffae1b07f4a7e3646ded857dbfaf27be359ae460a9dc35d6e99396a6270b2d4d94559331f5ed919917e67a1f1cd19023770db06b214ffc8702024c854751c8158a3e83aa4030365f9a88f6c907adf0203010001300d06092a864886f70d0101050500038181000148b50768d9c7bdbbb320f2e66cd0eb74efc984b9af413d3508898662fd5f00751bd23224f63ba1d70156139a391010e3c07fbcb80c4a6bd1c62f2319356fae3b25a5dec5e8823cccdc123dfcc6967b01225db046171938554f45f769a01afc8c8c5922f45cdae23fd0e9d8a4551ec3d9de3462563b72c35a392d6cacbe1033";
  private static TFMDownloadManager mInstance;
  private Context mAppContext = null;
  private String mDownloadFileName = "tmptf.apk";
  private Handler mDownloadHandler = null;
  boolean mDownloadSuccess = false;
  private File mFilePath = null;
  Bundle mLastBundle = null;
  private Set<TFMDownloadListener> mTFDownloadListeners = new HashSet();
  private TFInstallBroadcastReceiver mTFInstallBroadcastReceiver = null;
  private HashMap<Integer, TFMInstallListener> mTFInstallListeners = new HashMap();
  private TFMDownloader mTencentFileDownloader;
  private Handler mUIHandler = null;
  
  private TFMDownloadManager()
  {
    init();
  }
  
  private String certToCharsString(Certificate paramCertificate)
    throws CertificateEncodingException
  {
    paramCertificate = paramCertificate.getEncoded();
    int k = paramCertificate.length;
    char[] arrayOfChar = new char[k * 2];
    int i = 0;
    while (i < k)
    {
      int n = paramCertificate[i];
      int j = n >> 4 & 0xF;
      int m = i * 2;
      if (j >= 10) {
        j = j + 97 - 10;
      } else {
        j += 48;
      }
      arrayOfChar[m] = ((char)j);
      j = n & 0xF;
      if (j >= 10) {
        j = j + 97 - 10;
      } else {
        j += 48;
      }
      arrayOfChar[(m + 1)] = ((char)j);
      i += 1;
    }
    return new String(arrayOfChar);
  }
  
  private void deleteApkFile()
  {
    Log.e("TencentFileDownload", "deleteApkFile -- ");
    try
    {
      File localFile = new File(this.mFilePath, this.mDownloadFileName);
      if (localFile.exists()) {
        localFile.delete();
      }
      this.mDownloadSuccess = false;
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
  }
  
  private void doInitifNeeded(Context paramContext)
  {
    if (this.mAppContext == null) {
      this.mAppContext = paramContext.getApplicationContext();
    }
    if (this.mTencentFileDownloader == null) {
      this.mTencentFileDownloader = new TFMDownloader("http://appchannel.html5.qq.com/directdown?app=file&channel=11148", this.mFilePath, this.mDownloadFileName);
    }
    Object localObject;
    if (this.mDownloadHandler == null)
    {
      localObject = new HandlerThread("tfdownloader_thread");
      ((HandlerThread)localObject).start();
      this.mDownloadHandler = new Handler(((HandlerThread)localObject).getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (TFMDownloadManager.this.mFilePath == null)
          {
            Log.e("TencentFileDownload", "handleMessage() mFilePath null return");
            return;
          }
          Object localObject = TFMDownloadManager.this;
          localObject = ((TFMDownloadManager)localObject).getLockFileOutputStream(((TFMDownloadManager)localObject).mFilePath);
          if (localObject == null)
          {
            Log.e("TencentFileDownload", "get lockFos == null");
            return;
          }
          FileLock localFileLock = TFMDownloadManager.this.tryFileLock((FileOutputStream)localObject);
          if (paramAnonymousMessage.what != 100)
          {
            if (localFileLock != null) {
              TFMDownloadManager.this.freeFileLock(localFileLock, (FileOutputStream)localObject);
            }
          }
          else
          {
            if (paramAnonymousMessage.obj != null)
            {
              paramAnonymousMessage = (Bundle)paramAnonymousMessage.obj;
              TFMDownloadManager.this.mTencentFileDownloader.setExtraParams(TFMDownloadManager.this, paramAnonymousMessage);
            }
            if (localFileLock == null)
            {
              TFMDownloadManager.this.mTencentFileDownloader.startVirtualDownload();
            }
            else
            {
              TFMDownloadManager.this.mTencentFileDownloader.startDownload();
              TFMDownloadManager.this.freeFileLock(localFileLock, (FileOutputStream)localObject);
            }
            SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC009");
          }
        }
      };
    }
    if (this.mTFInstallBroadcastReceiver == null)
    {
      localObject = new IntentFilter();
      ((IntentFilter)localObject).addAction("android.intent.action.PACKAGE_ADDED");
      ((IntentFilter)localObject).addAction("android.intent.action.PACKAGE_INSTALL");
      ((IntentFilter)localObject).addAction("android.intent.action.PACKAGE_REMOVED");
      ((IntentFilter)localObject).addDataScheme("package");
      this.mTFInstallBroadcastReceiver = new TFInstallBroadcastReceiver(null);
      try
      {
        X5ApiCompatibilityUtils.x5RegisterReceiver(paramContext.getApplicationContext(), this.mTFInstallBroadcastReceiver, (IntentFilter)localObject);
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        this.mTFInstallBroadcastReceiver = null;
        paramContext = new StringBuilder();
        paramContext.append("registerTFInstallBroadcastReceiverIfNeeded mTFInstallBroadcastReceiver=");
        paramContext.append(this.mTFInstallBroadcastReceiver);
        Log.e("TencentFileDownload", paramContext.toString());
      }
    }
    if (this.mUIHandler == null) {
      this.mUIHandler = new Handler(Looper.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what == 1000)
          {
            paramAnonymousMessage = (String)paramAnonymousMessage.obj;
            Toast.makeText(TFMDownloadManager.this.mAppContext, paramAnonymousMessage, 0).show();
          }
        }
      };
    }
    if (this.mLastBundle == null) {
      this.mLastBundle = new Bundle();
    }
    this.mDownloadSuccess = false;
  }
  
  private void freeFileLock(FileLock paramFileLock, FileOutputStream paramFileOutputStream)
  {
    if (paramFileLock != null) {
      try
      {
        paramFileLock.release();
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
  
  private String getBundleMsg(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return "null";
    }
    return paramBundle.toString();
  }
  
  public static TFMDownloadManager getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new TFMDownloadManager();
      }
      TFMDownloadManager localTFMDownloadManager = mInstance;
      return localTFMDownloadManager;
    }
    finally {}
  }
  
  private File getLockFile(File paramFile)
  {
    if (paramFile == null) {
      return null;
    }
    if (!paramFile.exists()) {
      try
      {
        paramFile.mkdir();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    if ((paramFile.exists()) && (paramFile.isDirectory()))
    {
      paramFile = new File(paramFile, "tftql.data");
      if (paramFile.exists()) {
        return paramFile;
      }
      try
      {
        paramFile.createNewFile();
        return paramFile;
      }
      catch (IOException paramFile)
      {
        paramFile.printStackTrace();
      }
    }
    return null;
  }
  
  private FileOutputStream getLockFileOutputStream(File paramFile)
  {
    paramFile = getLockFile(paramFile);
    if (paramFile != null) {
      try
      {
        paramFile = new FileOutputStream(paramFile);
        return paramFile;
      }
      catch (FileNotFoundException paramFile)
      {
        paramFile.printStackTrace();
      }
    }
    return null;
  }
  
  public static File getPublicDownloadDir()
  {
    Object localObject = null;
    try
    {
      File localFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
      if (localFile != null)
      {
        localObject = localFile;
        if (!localFile.exists())
        {
          localObject = localFile;
          localFile.mkdir();
        }
      }
      return localFile;
    }
    catch (Throwable localThrowable) {}
    return (File)localObject;
  }
  
  private void init()
  {
    this.mFilePath = getPublicDownloadDir();
  }
  
  private void startInstall(final Context paramContext, File paramFile)
  {
    final Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addFlags(268435456);
    localIntent.addFlags(1073741824);
    localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
    Object localObject1 = new HashMap();
    ((Map)localObject1).put("com.tencent.mobileqq", "com.tencent.mobileqq.fileprovider");
    ((Map)localObject1).put("com.tencent.mm", "com.tencent.mm.external.fileprovider");
    if ((Build.VERSION.SDK_INT >= 24) && (((Map)localObject1).containsKey(paramContext.getPackageName()))) {
      localObject1 = (String)((Map)localObject1).get(paramContext.getPackageName());
    }
    try
    {
      localIntent.setFlags(1);
      Object localObject2 = Class.forName("android.support.v4.content.FileProvider");
      if (localObject2 != null)
      {
        localObject2 = ((Class)localObject2).getDeclaredMethod("getUriForFile", new Class[] { Context.class, String.class, File.class });
        if (localObject2 != null)
        {
          ((Method)localObject2).setAccessible(true);
          paramFile = ((Method)localObject2).invoke(null, new Object[] { paramContext, localObject1, paramFile });
          if ((paramFile instanceof Uri)) {
            localIntent.setDataAndType((Uri)paramFile, "application/vnd.android.package-archive");
          }
        }
      }
    }
    catch (Exception paramFile)
    {
      for (;;) {}
    }
    BrowserExecutorSupplier.getInstance().getMainThreadExecutor().execute(new Runnable()
    {
      public void run()
      {
        try
        {
          paramContext.startActivity(localIntent);
          return;
        }
        catch (Exception localException) {}
      }
    });
  }
  
  private FileLock tryFileLock(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return null;
    }
    try
    {
      paramFileOutputStream = paramFileOutputStream.getChannel().tryLock();
      boolean bool = paramFileOutputStream.isValid();
      if (bool) {
        return paramFileOutputStream;
      }
    }
    catch (Exception paramFileOutputStream)
    {
      paramFileOutputStream.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  private boolean verifySignature(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 7
    //   9: aconst_null
    //   10: astore 6
    //   12: new 420	java/util/jar/JarFile
    //   15: dup
    //   16: aload_1
    //   17: invokespecial 421	java/util/jar/JarFile:<init>	(Ljava/io/File;)V
    //   20: astore_1
    //   21: aload 6
    //   23: astore 4
    //   25: aload 7
    //   27: astore 5
    //   29: aload_1
    //   30: ldc_w 423
    //   33: invokevirtual 427	java/util/jar/JarFile:getJarEntry	(Ljava/lang/String;)Ljava/util/jar/JarEntry;
    //   36: astore 8
    //   38: aload 8
    //   40: ifnonnull +9 -> 49
    //   43: aload_1
    //   44: invokevirtual 428	java/util/jar/JarFile:close	()V
    //   47: iconst_0
    //   48: ireturn
    //   49: aload 6
    //   51: astore 4
    //   53: aload 7
    //   55: astore 5
    //   57: sipush 8192
    //   60: newarray <illegal type>
    //   62: astore 9
    //   64: aload 6
    //   66: astore 4
    //   68: aload 7
    //   70: astore 5
    //   72: aload_1
    //   73: aload 8
    //   75: invokevirtual 432	java/util/jar/JarFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
    //   78: astore 6
    //   80: aload 6
    //   82: astore 4
    //   84: aload 6
    //   86: astore 5
    //   88: aload 6
    //   90: aload 9
    //   92: iconst_0
    //   93: aload 9
    //   95: arraylength
    //   96: invokevirtual 438	java/io/InputStream:read	([BII)I
    //   99: iconst_m1
    //   100: if_icmpeq +6 -> 106
    //   103: goto -23 -> 80
    //   106: aload 6
    //   108: astore 4
    //   110: aload 6
    //   112: astore 5
    //   114: aload 6
    //   116: invokevirtual 439	java/io/InputStream:close	()V
    //   119: aload 6
    //   121: astore 4
    //   123: aload 6
    //   125: astore 5
    //   127: aload 8
    //   129: invokevirtual 445	java/util/jar/JarEntry:getCertificates	()[Ljava/security/cert/Certificate;
    //   132: astore 7
    //   134: aload 6
    //   136: astore 4
    //   138: aload 6
    //   140: astore 5
    //   142: aload 7
    //   144: arraylength
    //   145: istore_2
    //   146: iload_2
    //   147: iconst_1
    //   148: if_icmpge +19 -> 167
    //   151: aload 6
    //   153: ifnull +8 -> 161
    //   156: aload 6
    //   158: invokevirtual 439	java/io/InputStream:close	()V
    //   161: aload_1
    //   162: invokevirtual 428	java/util/jar/JarFile:close	()V
    //   165: iconst_0
    //   166: ireturn
    //   167: aload 6
    //   169: astore 4
    //   171: aload 6
    //   173: astore 5
    //   175: aload_0
    //   176: aload 7
    //   178: iconst_0
    //   179: aaload
    //   180: invokespecial 447	com/tencent/tbs/core/partner/tencentfiledownload/TFMDownloadManager:certToCharsString	(Ljava/security/cert/Certificate;)Ljava/lang/String;
    //   183: astore 7
    //   185: aload 7
    //   187: ifnull +39 -> 226
    //   190: aload 6
    //   192: astore 4
    //   194: aload 6
    //   196: astore 5
    //   198: aload 7
    //   200: ldc 36
    //   202: invokevirtual 450	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   205: istore_3
    //   206: iload_3
    //   207: ifeq +19 -> 226
    //   210: aload 6
    //   212: ifnull +8 -> 220
    //   215: aload 6
    //   217: invokevirtual 439	java/io/InputStream:close	()V
    //   220: aload_1
    //   221: invokevirtual 428	java/util/jar/JarFile:close	()V
    //   224: iconst_1
    //   225: ireturn
    //   226: aload_1
    //   227: astore 4
    //   229: aload 6
    //   231: ifnull +11 -> 242
    //   234: aload 6
    //   236: invokevirtual 439	java/io/InputStream:close	()V
    //   239: aload_1
    //   240: astore 4
    //   242: aload 4
    //   244: invokevirtual 428	java/util/jar/JarFile:close	()V
    //   247: iconst_0
    //   248: ireturn
    //   249: astore 6
    //   251: aload 4
    //   253: astore 5
    //   255: aload_1
    //   256: astore 4
    //   258: aload 6
    //   260: astore_1
    //   261: goto +7 -> 268
    //   264: astore_1
    //   265: aconst_null
    //   266: astore 4
    //   268: aload 5
    //   270: ifnull +8 -> 278
    //   273: aload 5
    //   275: invokevirtual 439	java/io/InputStream:close	()V
    //   278: aload 4
    //   280: ifnull +8 -> 288
    //   283: aload 4
    //   285: invokevirtual 428	java/util/jar/JarFile:close	()V
    //   288: aload_1
    //   289: athrow
    //   290: aconst_null
    //   291: astore_1
    //   292: aload 4
    //   294: astore 5
    //   296: aload 5
    //   298: ifnull +8 -> 306
    //   301: aload 5
    //   303: invokevirtual 439	java/io/InputStream:close	()V
    //   306: aload_1
    //   307: ifnull +9 -> 316
    //   310: aload_1
    //   311: astore 4
    //   313: goto -71 -> 242
    //   316: iconst_0
    //   317: ireturn
    //   318: astore_1
    //   319: goto -29 -> 290
    //   322: astore 4
    //   324: goto -28 -> 296
    //   327: astore_1
    //   328: iconst_0
    //   329: ireturn
    //   330: astore 4
    //   332: goto -171 -> 161
    //   335: astore_1
    //   336: iconst_0
    //   337: ireturn
    //   338: astore 4
    //   340: goto -120 -> 220
    //   343: astore_1
    //   344: iconst_1
    //   345: ireturn
    //   346: astore 4
    //   348: aload_1
    //   349: astore 4
    //   351: goto -109 -> 242
    //   354: astore_1
    //   355: iconst_0
    //   356: ireturn
    //   357: astore 5
    //   359: goto -81 -> 278
    //   362: astore 4
    //   364: goto -76 -> 288
    //   367: astore 4
    //   369: goto -63 -> 306
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	372	0	this	TFMDownloadManager
    //   0	372	1	paramFile	File
    //   145	4	2	i	int
    //   205	2	3	bool	boolean
    //   4	308	4	localObject1	Object
    //   322	1	4	localThrowable	Throwable
    //   330	1	4	localIOException1	IOException
    //   338	1	4	localIOException2	IOException
    //   346	1	4	localIOException3	IOException
    //   349	1	4	localFile	File
    //   362	1	4	localIOException4	IOException
    //   367	1	4	localIOException5	IOException
    //   1	301	5	localObject2	Object
    //   357	1	5	localIOException6	IOException
    //   10	225	6	localInputStream	java.io.InputStream
    //   249	10	6	localObject3	Object
    //   7	192	7	localObject4	Object
    //   36	92	8	localJarEntry	java.util.jar.JarEntry
    //   62	32	9	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   29	38	249	finally
    //   57	64	249	finally
    //   72	80	249	finally
    //   88	103	249	finally
    //   114	119	249	finally
    //   127	134	249	finally
    //   142	146	249	finally
    //   175	185	249	finally
    //   198	206	249	finally
    //   12	21	264	finally
    //   12	21	318	java/lang/Throwable
    //   29	38	322	java/lang/Throwable
    //   57	64	322	java/lang/Throwable
    //   72	80	322	java/lang/Throwable
    //   88	103	322	java/lang/Throwable
    //   114	119	322	java/lang/Throwable
    //   127	134	322	java/lang/Throwable
    //   142	146	322	java/lang/Throwable
    //   175	185	322	java/lang/Throwable
    //   198	206	322	java/lang/Throwable
    //   43	47	327	java/io/IOException
    //   156	161	330	java/io/IOException
    //   161	165	335	java/io/IOException
    //   215	220	338	java/io/IOException
    //   220	224	343	java/io/IOException
    //   234	239	346	java/io/IOException
    //   242	247	354	java/io/IOException
    //   273	278	357	java/io/IOException
    //   283	288	362	java/io/IOException
    //   301	306	367	java/io/IOException
  }
  
  public void cancelDownload()
  {
    try
    {
      if (this.mTencentFileDownloader != null) {
        this.mTencentFileDownloader.stopDownload();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getTFDownloadProgress()
  {
    try
    {
      if ((this.mTencentFileDownloader != null) && (this.mTencentFileDownloader.isDownloading()))
      {
        int i = this.mTencentFileDownloader.currentDownloadProgress();
        return i;
      }
      return 0;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected boolean installDownloadFile(Context paramContext)
  {
    File localFile;
    label116:
    try
    {
      doInitifNeeded(paramContext);
      localFile = new File(this.mFilePath, this.mDownloadFileName);
      if (localFile.exists()) {
        if (!verifySignature(localFile))
        {
          Log.e("TencentFileDownload", "tf file signature verify error!!!");
          if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(paramContext, SmttResource.getString("x5_pop_menu_snapshot_stop_scrolling", "string"), 0).show();
          }
          deleteApkFile();
        }
      }
    }
    finally {}
    try
    {
      startInstall(paramContext, localFile);
      return true;
    }
    catch (Exception paramContext)
    {
      break label116;
    }
    if (Looper.myLooper() == Looper.getMainLooper()) {
      Toast.makeText(paramContext, SmttResource.getString("x5_pop_menu_snapshot_tf_notexists", "string"), 0).show();
    }
    return false;
  }
  
  public boolean isTFDownloaded()
  {
    for (;;)
    {
      try
      {
        File localFile = this.mFilePath;
        boolean bool1 = true;
        if ((localFile != null) && (new File(this.mFilePath, this.mDownloadFileName).exists()))
        {
          i = 1;
          if (i != 0)
          {
            boolean bool2 = this.mDownloadSuccess;
            if (bool2) {}
          }
          else
          {
            bool1 = false;
          }
          return bool1;
        }
      }
      finally {}
      int i = 0;
    }
  }
  
  public boolean isTFDownloading()
  {
    try
    {
      if (this.mTencentFileDownloader != null)
      {
        boolean bool = this.mTencentFileDownloader.isDownloading();
        if (bool) {
          return true;
        }
      }
      return false;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadFailed(Bundle paramBundle)
  {
    int i = paramBundle.getInt(TFMDownloader.mErrorType);
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadFailed, errorcode=");
    ((StringBuilder)???).append(i);
    Log.e("TencentFileDownload", ((StringBuilder)???).toString());
    for (;;)
    {
      synchronized (this.mTFDownloadListeners)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        TFMDownloadListener localTFMDownloadListener;
        if (localIterator.hasNext())
        {
          localTFMDownloadListener = (TFMDownloadListener)localIterator.next();
          if (localTFMDownloadListener == null) {}
        }
        else
        {
          try
          {
            localTFMDownloadListener.onDownloadFailed(paramBundle);
          }
          catch (Throwable localThrowable) {}
          return;
        }
      }
    }
  }
  
  public void onDownloadPause(int paramInt)
  {
    for (;;)
    {
      TFMDownloadListener localTFMDownloadListener;
      synchronized (this.mTFDownloadListeners)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        if (localIterator.hasNext())
        {
          localTFMDownloadListener = (TFMDownloadListener)localIterator.next();
          if (localTFMDownloadListener == null) {
            continue;
          }
        }
      }
      try
      {
        localTFMDownloadListener.onDownloadPause(paramInt);
      }
      catch (Throwable localThrowable) {}
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadProgress(int paramInt)
  {
    for (;;)
    {
      TFMDownloadListener localTFMDownloadListener;
      synchronized (this.mTFDownloadListeners)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        if (localIterator.hasNext())
        {
          localTFMDownloadListener = (TFMDownloadListener)localIterator.next();
          if (localTFMDownloadListener == null) {
            continue;
          }
        }
      }
      try
      {
        localTFMDownloadListener.onDownloadProgress(paramInt);
      }
      catch (Throwable localThrowable) {}
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadResume(int paramInt)
  {
    for (;;)
    {
      TFMDownloadListener localTFMDownloadListener;
      synchronized (this.mTFDownloadListeners)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        if (localIterator.hasNext())
        {
          localTFMDownloadListener = (TFMDownloadListener)localIterator.next();
          if (localTFMDownloadListener == null) {
            continue;
          }
        }
      }
      try
      {
        localTFMDownloadListener.onDownloadResume(paramInt);
      }
      catch (Throwable localThrowable) {}
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadStart()
  {
    for (;;)
    {
      TFMDownloadListener localTFMDownloadListener;
      synchronized (this.mTFDownloadListeners)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        if (localIterator.hasNext())
        {
          localTFMDownloadListener = (TFMDownloadListener)localIterator.next();
          if (localTFMDownloadListener == null) {
            continue;
          }
        }
      }
      try
      {
        localTFMDownloadListener.onDownloadStart();
      }
      catch (Throwable localThrowable) {}
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadSucess(String paramString, Bundle paramBundle)
  {
    for (;;)
    {
      synchronized (this.mTFDownloadListeners)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        TFMDownloadListener localTFMDownloadListener;
        if (localIterator.hasNext())
        {
          localTFMDownloadListener = (TFMDownloadListener)localIterator.next();
          if (localTFMDownloadListener == null) {}
        }
        else
        {
          try
          {
            localTFMDownloadListener.onDownloadSucess(paramString, paramBundle);
          }
          catch (Throwable localThrowable) {}
          this.mDownloadSuccess = true;
          installDownloadFile(this.mAppContext);
          return;
        }
      }
    }
  }
  
  public void registerTFDownloadListener(TFMDownloadListener paramTFMDownloadListener)
  {
    Set localSet = this.mTFDownloadListeners;
    if (paramTFMDownloadListener != null) {}
    try
    {
      if (this.mTFDownloadListeners != null)
      {
        Iterator localIterator = this.mTFDownloadListeners.iterator();
        while (localIterator.hasNext()) {
          if ((TFMDownloadListener)localIterator.next() == paramTFMDownloadListener) {
            return;
          }
        }
        this.mTFDownloadListeners.add(paramTFMDownloadListener);
      }
      return;
    }
    finally {}
  }
  
  public void registerTFInstallListener(TFMInstallListener paramTFMInstallListener)
  {
    HashMap localHashMap = this.mTFInstallListeners;
    if (paramTFMInstallListener != null) {}
    try
    {
      if (this.mTFInstallListeners != null) {
        this.mTFInstallListeners.put(Integer.valueOf(paramTFMInstallListener.hashCode()), paramTFMInstallListener);
      }
      return;
    }
    finally {}
  }
  
  public int startDownload(Context paramContext, TFMDownloadListener paramTFMDownloadListener, TFMInstallListener paramTFMInstallListener)
  {
    try
    {
      if (!FileUtils.hasSDcard())
      {
        paramTFMDownloadListener.onDownloadFailed(null);
        return -2;
      }
      doInitifNeeded(paramContext);
      if (this.mFilePath == null)
      {
        paramTFMDownloadListener.onDownloadFailed(null);
        return -2;
      }
      if (paramTFMDownloadListener != null) {
        registerTFDownloadListener(paramTFMDownloadListener);
      }
      if (paramTFMInstallListener != null) {
        registerTFInstallListener(paramTFMInstallListener);
      }
      boolean bool = this.mTencentFileDownloader.isDownloading();
      if (bool) {
        return -3;
      }
      this.mDownloadHandler.removeMessages(100);
      Message.obtain(this.mDownloadHandler, 100, this.mLastBundle).sendToTarget();
      return 1;
    }
    finally {}
  }
  
  public void unregisterTFDownloadListener(TFMDownloadListener paramTFMDownloadListener)
  {
    Set localSet = this.mTFDownloadListeners;
    if (paramTFMDownloadListener != null) {}
    try
    {
      if ((this.mTFDownloadListeners != null) && (this.mTFDownloadListeners.contains(paramTFMDownloadListener))) {
        this.mTFDownloadListeners.remove(paramTFMDownloadListener);
      }
      return;
    }
    finally {}
  }
  
  public void unregisterTFInstallBroadcastReceiver(Context paramContext)
  {
    TFInstallBroadcastReceiver localTFInstallBroadcastReceiver = this.mTFInstallBroadcastReceiver;
    if (localTFInstallBroadcastReceiver != null) {}
    try
    {
      X5ApiCompatibilityUtils.x5UnRegisterReceiver(paramContext, localTFInstallBroadcastReceiver);
      this.mTFInstallBroadcastReceiver = null;
      return;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
  }
  
  public void unregisterTFInstallListener(TFMInstallListener paramTFMInstallListener)
  {
    HashMap localHashMap = this.mTFInstallListeners;
    if (paramTFMInstallListener != null) {}
    try
    {
      if (this.mTFInstallListeners.containsKey(Integer.valueOf(paramTFMInstallListener.hashCode()))) {
        this.mTFInstallListeners.remove(Integer.valueOf(paramTFMInstallListener.hashCode()));
      }
      return;
    }
    finally {}
  }
  
  private class TFInstallBroadcastReceiver
    extends BroadcastReceiver
  {
    private TFInstallBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent != null) {
        if ("android.intent.action.PACKAGE_ADDED".equals(paramIntent.getAction()))
        {
          if ((!"package:com.tencent.FileManager".equals(paramIntent.getDataString())) || (!X5SnapshotMenu.isFileManagerInstalled(TFMDownloadManager.this.mAppContext))) {
            break label205;
          }
          paramContext = (HashMap)TFMDownloadManager.this.mTFInstallListeners.clone();
          paramIntent = paramContext.keySet().iterator();
        }
      }
      for (;;)
      {
        if (!paramIntent.hasNext()) {
          break label205;
        }
        TFMInstallListener localTFMInstallListener = (TFMInstallListener)paramContext.get(Integer.valueOf(((Integer)paramIntent.next()).intValue()));
        label205:
        try
        {
          localTFMInstallListener.onInstallFinished();
        }
        catch (Throwable localThrowable1) {}
      }
      if (("android.intent.action.PACKAGE_REMOVED".equals(paramIntent.getAction())) && ("package:com.tencent.FileManager".equals(paramIntent.getDataString())))
      {
        Log.e("TencentFileDownload", "TFInstallBroadcastReceiver.onReceive tencent file has uninstalled");
        paramContext = (HashMap)TFMDownloadManager.this.mTFInstallListeners.clone();
        paramIntent = paramContext.keySet().iterator();
      }
      for (;;)
      {
        if (paramIntent.hasNext()) {
          localTFMInstallListener = (TFMInstallListener)paramContext.get(Integer.valueOf(((Integer)paramIntent.next()).intValue()));
        }
        try
        {
          localTFMInstallListener.onUninstallFinished();
        }
        catch (Throwable localThrowable2) {}
        return;
        break;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\tencentfiledownload\TFMDownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */