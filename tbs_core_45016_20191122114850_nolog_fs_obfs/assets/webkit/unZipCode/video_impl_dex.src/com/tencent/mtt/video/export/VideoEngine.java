package com.tencent.mtt.video.export;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.tencent.common.plugin.ZipPluginCheck;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class VideoEngine
{
  public static final int DEFAULT_VERSION = 43000;
  public static final String VIDEO_DEX_NAME = "video_impl_dex.jar";
  public static final String VIDEO_KEY_CUR_VERSION = "video_cur_version";
  public static final String VIDEO_KEY_PLUGIN_INSTALLED_DIR = "video_plugin_installed_dir";
  public static final String VIDEO_PLUGIN_NAME = "com.tencent.mtt.tbs.video.main";
  public static final String VIDEO_PLUGIN_RUN_DIR = "plugin_run_dir";
  public static final String VIDEO_RES_APK_NAME = "videores.apk";
  private static VideoEngine jdField_a_of_type_ComTencentMttVideoExportVideoEngine;
  public static DexClassLoader mInternalDexLoader;
  public static String sResApkPath;
  public static String sTbsDexOutPutDir;
  public static String sTbsInstallDir;
  public static boolean sTbsThirdcallMode = false;
  public static boolean sTesThirdcallMode;
  private IVideoDexLoaderProvider jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider = null;
  private IVideoManager jdField_a_of_type_ComTencentMttVideoExportIVideoManager = null;
  private VideoHost jdField_a_of_type_ComTencentMttVideoExportVideoHost = null;
  Object jdField_a_of_type_JavaLangObject = new Object();
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  public Context mHostContext;
  
  public static VideoEngine getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttVideoExportVideoEngine == null) {
        jdField_a_of_type_ComTencentMttVideoExportVideoEngine = new VideoEngine();
      }
      VideoEngine localVideoEngine = jdField_a_of_type_ComTencentMttVideoExportVideoEngine;
      return localVideoEngine;
    }
    finally {}
  }
  
  public static HashMap<String, String> getReportMapErrorDes(Throwable paramThrowable)
  {
    HashMap localHashMap;
    try
    {
      localHashMap = new HashMap();
      localHashMap.put("line_0", paramThrowable.toString());
      paramThrowable = paramThrowable.getStackTrace();
      k = paramThrowable.length;
      i = 0;
      j = 1;
    }
    catch (Throwable paramThrowable)
    {
      int j;
      do
      {
        int k;
        int i;
        do
        {
          StringBuilder localStringBuilder;
          for (;;) {}
        } while (i >= k);
        Object localObject = paramThrowable[i];
      } while (j <= 50);
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("line_");
    localStringBuilder.append(j);
    localHashMap.put(localStringBuilder.toString(), ((StackTraceElement)localObject).toString());
    j += 1;
    i += 1;
    break label96;
    return localHashMap;
    return null;
    label96:
    return localHashMap;
  }
  
  public static Object invokeStaticMethod(DexClassLoader paramDexClassLoader, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
    throws NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    paramDexClassLoader = paramDexClassLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
    paramDexClassLoader.setAccessible(true);
    return paramDexClassLoader.invoke(null, paramVarArgs);
  }
  
  void a(IVideoManager paramIVideoManager, DexClassLoader paramDexClassLoader)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      mInternalDexLoader = paramDexClassLoader;
      this.jdField_a_of_type_Boolean = false;
      this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager = paramIVideoManager;
      this.jdField_a_of_type_JavaLangObject.notifyAll();
      return;
    }
  }
  
  public IVideoManager getVideoManager()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager == null) {
        if (this.jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider != null)
        {
          if (!this.jdField_a_of_type_Boolean)
          {
            this.jdField_a_of_type_Boolean = true;
            new DexLoadThread(this, this.jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider).start();
          }
          try
          {
            this.jdField_a_of_type_JavaLangObject.wait(4500L);
            if (!this.jdField_a_of_type_Boolean) {
              break label136;
            }
            HashMap localHashMap = new HashMap();
            localHashMap.put("line_0", "DexLoadThread is timeout 4.5s");
            X5CoreBeaconUploader.getInstance(this.mHostContext).upLoadToBeacon("H5VideoLoadCrash", localHashMap);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
        }
        else
        {
          localObject2 = new HashMap();
          ((HashMap)localObject2).put("line_0", "mDexProvider is null");
          X5CoreBeaconUploader.getInstance(this.mHostContext).upLoadToBeacon("H5VideoLoadCrash", (Map)localObject2);
        }
      }
      label136:
      if ((!this.b) && (this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager != null))
      {
        this.b = true;
        this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager.setContext(this.mHostContext);
        this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager.setVideoHost(this.jdField_a_of_type_ComTencentMttVideoExportVideoHost);
      }
      Object localObject2 = this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager;
      return (IVideoManager)localObject2;
    }
  }
  
  public void init(Context paramContext, IVideoDexLoaderProvider paramIVideoDexLoaderProvider)
  {
    this.mHostContext = paramContext.getApplicationContext();
    this.jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider = paramIVideoDexLoaderProvider;
  }
  
  public void setVideoHost(VideoHost paramVideoHost)
  {
    this.jdField_a_of_type_ComTencentMttVideoExportVideoHost = paramVideoHost;
    paramVideoHost = this.jdField_a_of_type_ComTencentMttVideoExportIVideoManager;
    if (paramVideoHost != null) {
      paramVideoHost.setVideoHost(this.jdField_a_of_type_ComTencentMttVideoExportVideoHost);
    }
  }
  
  private static class DexLoadThread
    extends Thread
  {
    private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences;
    IVideoDexLoaderProvider jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider = null;
    private VideoEngine jdField_a_of_type_ComTencentMttVideoExportVideoEngine;
    private String jdField_a_of_type_JavaLangString;
    private String b;
    
    public DexLoadThread(VideoEngine paramVideoEngine, IVideoDexLoaderProvider paramIVideoDexLoaderProvider)
    {
      this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine = paramVideoEngine;
      this.jdField_a_of_type_AndroidContentSharedPreferences = this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine.mHostContext.getSharedPreferences("qb_video_settings", 0);
      this.jdField_a_of_type_JavaLangString = this.jdField_a_of_type_AndroidContentSharedPreferences.getString("video_plugin_installed_dir", null);
      paramVideoEngine = this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine.mHostContext.getDir("tbs", 0).getAbsolutePath();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramVideoEngine);
      localStringBuilder.append("/share/video/");
      localStringBuilder.append("plugin_run_dir");
      this.b = localStringBuilder.toString();
      this.jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider = paramIVideoDexLoaderProvider;
    }
    
    public void run()
    {
      LogUtils.d("DexLoadThread", "slecet_video_dex_path begin");
      Object localObject1 = VideoEngine.sTbsInstallDir;
      localObject1 = VideoEngine.sTbsDexOutPutDir;
      int i;
      if (this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("VIDEO_MAIN_DEX_PLUGIN_DISABLED", 0) == 1) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      int k;
      if (i == 0)
      {
        if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
          i = StringUtils.parseInt(ZipPluginCheck.getPluginVerCode(new File(this.jdField_a_of_type_JavaLangString), "com.tencent.mtt.tbs.video.main"), 0);
        } else {
          i = 0;
        }
        if (!TextUtils.isEmpty(this.b))
        {
          localObject1 = new File(this.b);
          j = StringUtils.parseInt(ZipPluginCheck.getPluginVerCode((File)localObject1, "com.tencent.mtt.tbs.video.main"), 0);
          k = i;
          i = j;
        }
        else
        {
          localObject1 = null;
          j = 0;
          k = i;
          i = j;
        }
      }
      else
      {
        localObject1 = null;
        k = 0;
        i = 0;
      }
      int m = Math.max(k, i);
      int n = 43000;
      if (43000 >= m) {
        j = 1;
      } else if (43 != m / 1000) {
        j = 1;
      } else {
        j = 0;
      }
      Object localObject2;
      if (j != 0)
      {
        localObject1 = VideoEngine.sTbsInstallDir;
        localObject2 = VideoEngine.sTbsDexOutPutDir;
        localObject3 = null;
        i = n;
      }
      else
      {
        if (k > i)
        {
          FileUtils.deleteQuietly((File)localObject1);
          FileUtils.copyFolder(this.jdField_a_of_type_JavaLangString, this.b);
          localObject2 = new FilenameFilter()
          {
            public boolean accept(File paramAnonymousFile, String paramAnonymousString)
            {
              return (paramAnonymousString.endsWith(".so")) || (paramAnonymousString.endsWith(".jar")) || (paramAnonymousString.endsWith(".zip"));
            }
          };
          localObject3 = new File(this.b);
          ZipPluginCheck.genCheckList((File)localObject3, k, "com.tencent.mtt.tbs.video.main", ((File)localObject3).listFiles((FilenameFilter)localObject2));
        }
        if (ZipPluginCheck.checkLocalPluginNotModified((File)localObject1, "com.tencent.mtt.tbs.video.main") == 0)
        {
          localObject1 = this.b;
          localObject2 = localObject1;
          localObject3 = localObject2;
          i = m;
        }
        else
        {
          localObject1 = VideoEngine.sTbsInstallDir;
          localObject2 = VideoEngine.sTbsDexOutPutDir;
          localObject3 = null;
          i = n;
        }
      }
      this.jdField_a_of_type_AndroidContentSharedPreferences.edit().putInt("video_cur_version", i).commit();
      LogUtils.d("DexLoadThread", "slecet_video_dex_path end");
      Object localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append((String)localObject1);
      ((StringBuilder)localObject4).append("/");
      ((StringBuilder)localObject4).append("videores.apk");
      VideoEngine.sResApkPath = ((StringBuilder)localObject4).toString();
      localObject4 = this.jdField_a_of_type_ComTencentMttVideoExportIVideoDexLoaderProvider;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject1);
      localStringBuilder.append("/");
      localStringBuilder.append("video_impl_dex.jar");
      Object localObject3 = ((IVideoDexLoaderProvider)localObject4).getDexClassLoader(new String[] { localStringBuilder.toString() }, (String)localObject2, (String)localObject3);
      if (localObject3 != null)
      {
        try
        {
          localObject2 = (IVideoManager)VideoEngine.invokeStaticMethod((DexClassLoader)localObject3, "com.tencent.mtt.video.internal.engine.VideoManager", "getInstance", null, new Object[0]);
          localObject1 = localObject2;
        }
        catch (Throwable localThrowable)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append("/");
          ((StringBuilder)localObject2).append("video_impl_dex.jar");
          if (!new File(((StringBuilder)localObject2).toString()).exists())
          {
            localObject2 = new HashMap();
            ((HashMap)localObject2).put("line_0", "dex file not found");
            localStringBuilder = new StringBuilder();
            localStringBuilder.append((String)localObject1);
            localStringBuilder.append("/");
            localStringBuilder.append("video_impl_dex.jar");
            ((HashMap)localObject2).put("line_1", localStringBuilder.toString());
            localObject1 = localObject2;
          }
          else
          {
            localObject1 = VideoEngine.getReportMapErrorDes(localThrowable);
          }
          if (localObject1 != null) {
            X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine.mHostContext).upLoadToBeacon("H5VideoLoadCrash", (Map)localObject1);
          }
          localThrowable.printStackTrace();
          localObject1 = null;
        }
        this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine.a((IVideoManager)localObject1, (DexClassLoader)localObject3);
        return;
      }
      this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine.a(null, null);
      localObject1 = new HashMap();
      ((HashMap)localObject1).put("line_0", "classLoader is null");
      X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_ComTencentMttVideoExportVideoEngine.mHostContext).upLoadToBeacon("H5VideoLoadCrash", (Map)localObject1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\VideoEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */