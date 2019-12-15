package com.tencent.smtt.webkit.webar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.ui.e.a;
import com.tencent.tbs.common.ar.WEBAR.IWebARCloudRecognitionCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebAREngineCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebARModelCallback;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager.CacheResult;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.net.GURLUtils;
import org.chromium.tencent.utils.TencentDeviceUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@JNINamespace("webar")
public class WebARCameraUtils
{
  private static int jdField_a_of_type_Int;
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static TextView jdField_a_of_type_AndroidWidgetTextView;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static String jdField_a_of_type_JavaLangString;
  private static WeakReference<TencentWebViewProxy> jdField_a_of_type_JavaLangRefWeakReference;
  private static HashMap<String, Boolean> jdField_a_of_type_JavaUtilHashMap;
  private static boolean jdField_a_of_type_Boolean = false;
  private static final String[] jdField_a_of_type_ArrayOfJavaLangString = { "qbact.html5.qq.com", "res.imtt.qq.com" };
  private static int jdField_b_of_type_Int;
  private static TextView jdField_b_of_type_AndroidWidgetTextView;
  private static String jdField_b_of_type_JavaLangString;
  private static WeakReference<IX5WebChromeClientExtension> jdField_b_of_type_JavaLangRefWeakReference;
  private static boolean jdField_b_of_type_Boolean = false;
  private static final String[] jdField_b_of_type_ArrayOfJavaLangString = { "qbact.html5.qq.com", "res.imtt.qq.com" };
  private static String jdField_c_of_type_JavaLangString;
  private static boolean jdField_c_of_type_Boolean = false;
  private static final String[] jdField_c_of_type_ArrayOfJavaLangString = { "qbact.html5.qq.com", "res.imtt.qq.com" };
  private static String jdField_d_of_type_JavaLangString;
  private static boolean jdField_d_of_type_Boolean;
  private static final String[] jdField_d_of_type_ArrayOfJavaLangString = { "qbact.html5.qq.com", "res.imtt.qq.com" };
  private static String jdField_e_of_type_JavaLangString;
  private static final String[] jdField_e_of_type_ArrayOfJavaLangString = { "OPPOA57" };
  private static String f;
  
  static
  {
    jdField_a_of_type_AndroidOsHandler = null;
    jdField_b_of_type_Int = 0;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sdk_level=");
    localStringBuilder.append(Build.VERSION.SDK_INT);
    f = localStringBuilder.toString();
    jdField_a_of_type_JavaUtilHashMap = new HashMap();
    jdField_d_of_type_Boolean = false;
    jdField_a_of_type_AndroidWidgetTextView = null;
    jdField_b_of_type_AndroidWidgetTextView = null;
  }
  
  private static Handler a()
  {
    if (jdField_a_of_type_AndroidOsHandler == null) {
      jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
    }
    return jdField_a_of_type_AndroidOsHandler;
  }
  
  public static void a(int paramInt)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        WebARCameraUtils.a(this.a, false);
      }
    });
  }
  
  public static void a(int paramInt1, int paramInt2)
  {
    nativeOnViewSizeChanged(paramInt1, paramInt2);
  }
  
  public static void a(int paramInt, final String paramString)
  {
    if (a())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          WebARCameraUtils.a(this.jdField_a_of_type_Int, paramString);
        }
      });
      return;
    }
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    if ((!paramString.toLowerCase().startsWith("http")) && (!paramString.toLowerCase().startsWith("https"))) {
      return;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if ((jdField_a_of_type_Int == paramInt) && (jdField_e_of_type_JavaLangString.equals(paramString))) {
        return;
      }
      jdField_a_of_type_Int = paramInt;
      jdField_e_of_type_JavaLangString = paramString;
      paramString = GURLUtils.getOrigin(jdField_e_of_type_JavaLangString);
      jdField_a_of_type_Boolean = d(paramString);
      jdField_b_of_type_Boolean = c(paramString);
      jdField_c_of_type_Boolean = f(paramString);
      return;
    }
  }
  
  private static void a(IWebARPermissionRequestCallback paramIWebARPermissionRequestCallback)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      final Object localObject2 = jdField_a_of_type_JavaLangRefWeakReference.get();
      boolean bool = false;
      if (localObject2 == null)
      {
        d(jdField_a_of_type_Int, false);
        return;
      }
      localObject2 = GURLUtils.getOrigin(jdField_e_of_type_JavaLangString);
      if (a(paramIWebARPermissionRequestCallback, (String)localObject2)) {
        return;
      }
      if (e((String)localObject2))
      {
        b(paramIWebARPermissionRequestCallback, (String)localObject2);
        return;
      }
      try
      {
        if (!((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getSettingsExtension().getDayOrNight()) {
          bool = true;
        }
        String str = SmttResource.getString("x5_video_capture_permission_prompt", "string");
        e.a locala = new e.a(((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext(), 2, bool);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append((String)localObject2);
        localStringBuilder.append(str);
        locala.a(localStringBuilder.toString()).b(SmttResource.getString("x5_accept", "string"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            WebARCameraUtils.a(this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARPermissionRequestCallback, localObject2);
          }
        }).a(SmttResource.getString("x5_prohibit", "string"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARPermissionRequestCallback.onPermissionRequestCanceled(localObject2);
          }
        }).a(new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            this.jdField_a_of_type_ComTencentSmttWebkitWebarIWebARPermissionRequestCallback.onPermissionRequestCanceled(localObject2);
          }
        }).a();
      }
      catch (Exception paramIWebARPermissionRequestCallback)
      {
        paramIWebARPermissionRequestCallback.printStackTrace();
      }
      return;
    }
  }
  
  public static void a(TencentWebViewProxy paramTencentWebViewProxy, IX5WebChromeClientExtension paramIX5WebChromeClientExtension)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramTencentWebViewProxy);
      jdField_b_of_type_JavaLangRefWeakReference = new WeakReference(paramIX5WebChromeClientExtension);
      return;
    }
  }
  
  private static boolean a()
  {
    return a().getLooper() != Looper.myLooper();
  }
  
  public static boolean a(int paramInt)
  {
    return nativeHasWebAR(paramInt);
  }
  
  private static boolean a(IWebARPermissionRequestCallback paramIWebARPermissionRequestCallback, String paramString)
  {
    if (com.tencent.smtt.webkit.j.a().b(paramString, 3))
    {
      if (com.tencent.smtt.webkit.j.a().a(paramString, 3))
      {
        b(paramIWebARPermissionRequestCallback, paramString);
        return true;
      }
      if (!jdField_d_of_type_Boolean)
      {
        jdField_d_of_type_Boolean = true;
        Toast.makeText(((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext(), SmttResource.getString("x5_ar_forbiden_retry", "string"), 1).show();
      }
      paramIWebARPermissionRequestCallback.onPermissionRequestCanceled(paramString);
      return true;
    }
    return false;
  }
  
  private static boolean a(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().isAutoSwitchARCoreWhiteList(paramString);
    }
    return false;
  }
  
  public static void b(int paramInt)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        WebARCameraUtils.a(this.a, true);
      }
    });
  }
  
  private static void b(IWebARPermissionRequestCallback paramIWebARPermissionRequestCallback, String paramString)
  {
    for (;;)
    {
      synchronized (jdField_a_of_type_JavaLangObject)
      {
        Object localObject2 = jdField_a_of_type_JavaLangRefWeakReference.get();
        if (localObject2 != null) {
          try
          {
            localObject2 = ((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext().getPackageManager();
            if (((PackageManager)localObject2).checkPermission("android.permission.CAMERA", ((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext().getPackageName()) != 0) {
              break label205;
            }
            i = 1;
            if (i == 0)
            {
              localObject2 = ((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext().getApplicationInfo().loadLabel((PackageManager)localObject2).toString();
              Context localContext = ((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext();
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append(SmttResource.getString("x5_ar_set_system", "string"));
              localStringBuilder.append((String)localObject2);
              localStringBuilder.append(SmttResource.getString("x5_ar_set_camera", "string"));
              Toast.makeText(localContext, localStringBuilder.toString(), 1).show();
              paramIWebARPermissionRequestCallback.onPermissionRequestCanceled(paramString);
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
        paramIWebARPermissionRequestCallback.onPermissionRequestGranted(paramString);
        return;
      }
      label205:
      int i = 0;
    }
  }
  
  private static boolean b(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().isAutoSwitchARCoreBlackList(paramString);
    }
    return false;
  }
  
  private static void c(int paramInt1, final int paramInt2, final int paramInt3, final String paramString)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        WebARCameraUtils.b(this.jdField_a_of_type_Int, paramInt2, paramInt3, paramString);
      }
    });
  }
  
  private static void c(int paramInt1, final int paramInt2, final String paramString1, final String paramString2, final String paramString3)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        WebARCameraUtils.b(this.jdField_a_of_type_Int, paramInt2, paramString1, paramString2, paramString3);
      }
    });
  }
  
  private static boolean c(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      paramString = com.tencent.smtt.util.j.c(paramString);
      Iterator localIterator = Arrays.asList(jdField_a_of_type_ArrayOfJavaLangString).iterator();
      while (localIterator.hasNext()) {
        if (paramString.endsWith((String)localIterator.next())) {
          return true;
        }
      }
      return SmttServiceProxy.getInstance().isWebARMarkerWhiteList(paramString);
    }
    return false;
  }
  
  @CalledByNative
  private static boolean checkArCoreApkIsAvailable()
  {
    if (Build.VERSION.SDK_INT < 24) {
      return false;
    }
    Context localContext = getContext();
    if (localContext == null) {
      return false;
    }
    try
    {
      Object localObject1 = localContext.getPackageManager().getPackageInfo("com.google.ar.core", 64);
      if (((PackageInfo)localObject1).versionCode < 180425094) {
        return false;
      }
      Signature localSignature = new Signature("308203c7308202afa003020102021500dc286b43b4ea12039958a00a6655eb84720e46c9300d06092a864886f70d01010b05003074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964301e170d3137303830343136353333375a170d3437303830343136353333375a3074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f696430820122300d06092a864886f70d01010105000382010f003082010a02820101008998646f47fc333db09644c303104ed183e904e351152aa66a603b77f63389d45d6fcffae3c94fadf1f28038e265d697fea347327f9081a7f0b9074d5b148db5bf357c611a77f87f844a15068818bdcd5b21d187e93fa2551676170eedce04a150c35ec0a791eef507fa9b406573c36f6f207764842e5677e35a281a422659e91e26eb4fecfb053b5c936d0976c37f8757adb57a37953da5844ea350695854d343a61ad341b63a1c425d22855af7ebfee018e1736cee98536be5b9947f288e2a26f99eb9f91b5de93fecc513019d2e90f12b38610d1f02eaa81deca4ce91c19cbce36d6c3025ce2432b3d178616beafaf437c08451bc469c6bc6f4517a714a5b0203010001a350304e300c0603551d13040530030101ff301d0603551d0e0416041419a864c0f2618c67c803a23da909bc70521f269b301f0603551d2304183016801419a864c0f2618c67c803a23da909bc70521f269b300d06092a864886f70d01010b050003820101005403fc56fdefc440376a0337815002b96a15bffc2fe42de6c58f52fae4d80652e3704455b885409eef81ffbb4c44dba104b6b8e24c9e2e0e7a04338ee73baa5b71bfb4488f8e04bef3d0eaf7d43aa42b03b278c33cc1f0dd3802571624baa161d851fab37db4bc92b9094b6885dff62b400ecd81f069d56a1be1db46d8198c50c9628cdb6e38686ef640fd386775f50376f957e24ea45ed1942968f20c82f189607fdb22f11cfdfd0760a77a60ceb3416cfb3f48f13f9f83f3834a01001750a7c78bc1fd81f0b53a7c41dcba9f5a0118259d083c32bb9ebb84d645d6f6b9c31923d8ab70e7f0a25940ecc9f4945144419f86e8c421d3b99774f4b8f3d09262e7");
      localObject1 = ((PackageInfo)localObject1).signatures;
      int j = localObject1.length;
      int i = 0;
      while (i < j)
      {
        Object localObject2 = localObject1[i];
        if (((Signature)localObject2).equals(localSignature)) {
          return true;
        }
        localSignature.toCharsString();
        ((Signature)localObject2).toCharsString();
        i += 1;
      }
      try
      {
        boolean bool = localContext.getPackageManager().getApplicationInfo(localContext.getPackageName(), 128).metaData.getBoolean("com.google.ar.core.disable_security_check", false);
        return bool;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException1)
      {
        localNameNotFoundException1.printStackTrace();
        return false;
      }
      return false;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException2) {}
  }
  
  private static void d(int paramInt, final boolean paramBoolean)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        WebARCameraUtils.b(this.jdField_a_of_type_Int, paramBoolean);
      }
    });
  }
  
  private static boolean d(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      paramString = com.tencent.smtt.util.j.c(paramString);
      Iterator localIterator = Arrays.asList(jdField_b_of_type_ArrayOfJavaLangString).iterator();
      while (localIterator.hasNext()) {
        if (paramString.endsWith((String)localIterator.next())) {
          return true;
        }
      }
      return SmttServiceProxy.getInstance().isWebARSlamWhiteList(paramString);
    }
    return false;
  }
  
  @CalledByNative
  private static void doRecognitionOnCloud(byte[] paramArrayOfByte)
  {
    SmttServiceProxy.getInstance().doRecogitionOnCloud(paramArrayOfByte);
  }
  
  private static boolean e(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      paramString = com.tencent.smtt.util.j.c(paramString);
      Iterator localIterator = Arrays.asList(jdField_c_of_type_ArrayOfJavaLangString).iterator();
      while (localIterator.hasNext()) {
        if (paramString.endsWith((String)localIterator.next())) {
          return true;
        }
      }
      return SmttServiceProxy.getInstance().isWebARCameraWhiteList(paramString);
    }
    return false;
  }
  
  private static boolean f(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      paramString = com.tencent.smtt.util.j.c(paramString);
      Iterator localIterator = Arrays.asList(jdField_d_of_type_ArrayOfJavaLangString).iterator();
      while (localIterator.hasNext()) {
        if (paramString.endsWith((String)localIterator.next())) {
          return true;
        }
      }
      return SmttServiceProxy.getInstance().isWebARTFLiteWhiteList(paramString);
    }
    return false;
  }
  
  private static boolean g(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().isAutoSwitchSlamVioWhiteList(paramString);
    }
    return false;
  }
  
  @CalledByNative
  private static String getAREngineDir(int paramInt)
  {
    if (paramInt == 1) {
      return jdField_a_of_type_JavaLangString;
    }
    if (paramInt == 2) {
      return jdField_b_of_type_JavaLangString;
    }
    if (paramInt == 4) {
      return jdField_c_of_type_JavaLangString;
    }
    if (paramInt == 5) {
      return jdField_d_of_type_JavaLangString;
    }
    return "";
  }
  
  @CalledByNative
  private static int getActivityOrientation(Context paramContext)
  {
    return ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getOrientation();
  }
  
  @CalledByNative
  private static byte[] getCachedFileData(String paramString)
  {
    try
    {
      paramString = TencentCacheManager.getCacheResult(paramString).getInputStream();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[100000];
      for (;;)
      {
        int i = paramString.read(arrayOfByte, 0, arrayOfByte.length);
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      paramString = localByteArrayOutputStream.toByteArray();
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  @CalledByNative
  private static Context getContext()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_JavaLangRefWeakReference.get() == null) {
        return null;
      }
      Context localContext = ((TencentWebViewProxy)jdField_a_of_type_JavaLangRefWeakReference.get()).getContext();
      return localContext;
    }
  }
  
  @CalledByNative
  private static int getCurrentPageId()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      int i = jdField_a_of_type_Int;
      return i;
    }
  }
  
  @CalledByNative
  private static String getDeviceMsg()
  {
    return f;
  }
  
  @CalledByNative
  private static int getSensorOrientation(int paramInt)
  {
    Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
    try
    {
      Camera.getCameraInfo(paramInt, localCameraInfo);
    }
    catch (RuntimeException localRuntimeException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Camera.getCameraInfo: ");
      localStringBuilder.append(localRuntimeException);
      Log.e("webarCameraUtils", localStringBuilder.toString());
    }
    return localCameraInfo.orientation;
  }
  
  @CalledByNative
  private static long getTimestamp()
  {
    try
    {
      long l = SystemClock.elapsedRealtimeNanos();
      return l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0L;
  }
  
  private static boolean h(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      return SmttServiceProxy.getInstance().isAutoSwitchSlamVioBlackList(paramString);
    }
    return false;
  }
  
  @CalledByNative
  private static boolean isAutoSwitchARCoreEnabled()
  {
    boolean bool2 = SmttServiceProxy.getInstance().isAutoSwitchARCoreEnabled();
    boolean bool1 = bool2;
    if (!bool2) {
      bool1 = SmttServiceProxy.getInstance().isAutoSwitchARCoreDeviceWhiteList(Build.MODEL);
    }
    String str = com.tencent.smtt.util.j.d(jdField_e_of_type_JavaLangString);
    if (bool1)
    {
      if (b(str)) {
        return false;
      }
    }
    else if (a(str)) {
      return true;
    }
    return bool1;
  }
  
  @CalledByNative
  private static boolean isAutoSwitchSlamVioEnabled()
  {
    if (isWebARSlamVIODisabled()) {
      return false;
    }
    boolean bool = SmttServiceProxy.getInstance().isAutoSwitchSlamVioEnabled();
    String str = com.tencent.smtt.util.j.d(jdField_e_of_type_JavaLangString);
    if (bool)
    {
      if (h(str)) {
        return false;
      }
    }
    else if (g(str)) {
      return true;
    }
    return bool;
  }
  
  @CalledByNative
  private static boolean isWebARMarkerDeviceAllowed()
  {
    if (Build.VERSION.SDK_INT < 19) {
      return false;
    }
    return SmttServiceProxy.getInstance().isWebARMarkerEnabledOnAllDevices();
  }
  
  @CalledByNative
  private static boolean isWebARMarkerDisabled()
  {
    return SmttServiceProxy.getInstance().isWebARMarkerDisabled();
  }
  
  @CalledByNative
  private static boolean isWebARMarkerWhiteList()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_e_of_type_JavaLangString != null)
      {
        boolean bool = jdField_b_of_type_Boolean;
        return bool;
      }
      return false;
    }
  }
  
  @CalledByNative
  private static boolean isWebARSlamDeviceAllowed()
  {
    if (Build.VERSION.SDK_INT < 19) {
      return false;
    }
    if (SmttServiceProxy.getInstance().isWebARSlamEnabledOnAllDevices()) {
      return true;
    }
    i = 4;
    int j = 1500000;
    float f1 = 1500.0F;
    Object localObject1 = SmttServiceProxy.getInstance().getWebARSlamHardwareConfig();
    int m;
    int k;
    if (localObject1 != null)
    {
      String[] arrayOfString = ((String)localObject1).split(",");
      m = 0;
      k = 4;
      j = 1500000;
      f1 = 1500.0F;
      while (m < arrayOfString.length)
      {
        try
        {
          localObject2 = arrayOfString[m].split("=");
          if (localObject2.length <= 1) {
            break label262;
          }
          localStringBuilder = localObject2[0];
          localObject2 = localObject2[1];
          i = localStringBuilder.hashCode();
          if (i != 843701756)
          {
            if (i != 948438902)
            {
              if ((i != 954755943) || (!localStringBuilder.equals("coreNum"))) {
                break label518;
              }
              i = 0;
              break label520;
            }
            if (!localStringBuilder.equals("memSize")) {
              break label518;
            }
            i = 2;
            break label520;
          }
          if (!localStringBuilder.equals("maxFreq")) {
            break label518;
          }
          i = 1;
        }
        catch (Exception localException2)
        {
          for (;;)
          {
            Object localObject2;
            StringBuilder localStringBuilder;
            float f2;
            label262:
            continue;
            i = -1;
            switch (i)
            {
            }
          }
        }
        f2 = Integer.parseInt((String)localObject2);
        f1 = f2;
        break label262;
        i = Integer.parseInt((String)localObject2);
        j = i;
        break label262;
        i = Integer.parseInt((String)localObject2);
        k = i;
        break label262;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("configString error: ");
        localStringBuilder.append((String)localObject1);
        Log.e("WebAR", localStringBuilder.toString());
        m += 1;
      }
      i = k;
    }
    try
    {
      k = TencentDeviceUtils.getNumberOfCPUCores();
      m = TencentDeviceUtils.getCPUMaxFreqKHz();
      if (m > jdField_b_of_type_Int) {
        jdField_b_of_type_Int = m;
      }
      f2 = TencentDeviceUtils.getTotalRAMMemory();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("sdk_level=");
      ((StringBuilder)localObject1).append(Build.VERSION.SDK_INT);
      ((StringBuilder)localObject1).append("; coreNum=");
      ((StringBuilder)localObject1).append(k);
      ((StringBuilder)localObject1).append("; cpuMaxFreq=");
      ((StringBuilder)localObject1).append(m);
      ((StringBuilder)localObject1).append("; memorySize=");
      ((StringBuilder)localObject1).append(f2);
      f = ((StringBuilder)localObject1).toString();
      if ((Build.VERSION.SDK_INT < 21) || (k < i) || (jdField_b_of_type_Int < j) || (f2 < f1))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("This device not support WebAR. sdk_level=");
        ((StringBuilder)localObject1).append(Build.VERSION.SDK_INT);
        ((StringBuilder)localObject1).append("; coreNum=");
        ((StringBuilder)localObject1).append(k);
        ((StringBuilder)localObject1).append("; cpuMaxFreq=");
        ((StringBuilder)localObject1).append(m);
        ((StringBuilder)localObject1).append("; memorySize=");
        ((StringBuilder)localObject1).append(f2);
        Log.e("chromium", ((StringBuilder)localObject1).toString());
        return false;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return true;
  }
  
  @CalledByNative
  private static boolean isWebARSlamDisabled()
  {
    return SmttServiceProxy.getInstance().isWebARSlamDisabled();
  }
  
  @CalledByNative
  private static boolean isWebARSlamVIODisabled()
  {
    if (SmttServiceProxy.getInstance().isWebARSlamVIODisabled()) {
      return true;
    }
    String str = Build.MODEL.replace(" ", "");
    Iterator localIterator = Arrays.asList(jdField_e_of_type_ArrayOfJavaLangString).iterator();
    while (localIterator.hasNext()) {
      if (str.equals((String)localIterator.next())) {
        return true;
      }
    }
    return false;
  }
  
  @CalledByNative
  private static boolean isWebARSlamWhiteList()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_e_of_type_JavaLangString != null)
      {
        boolean bool = jdField_a_of_type_Boolean;
        return bool;
      }
      return false;
    }
  }
  
  @CalledByNative
  private static boolean isWebARTFLiteWhiteList()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_e_of_type_JavaLangString != null)
      {
        boolean bool = jdField_c_of_type_Boolean;
        return bool;
      }
      return false;
    }
  }
  
  @CalledByNative
  public static void loadAREngine(int paramInt)
  {
    SmttServiceProxy.getInstance().loadWebAREngine(paramInt, new IWebAREngineCallback()
    {
      public void onDownloadProgress(int paramAnonymousInt)
      {
        WebARCameraUtils.a(WebARCameraUtils.a(), 2, paramAnonymousInt, "download in progress");
      }
      
      public void onDownloadStart(int paramAnonymousInt)
      {
        WebARCameraUtils.a(WebARCameraUtils.a(), 1, paramAnonymousInt, "start download");
      }
      
      public void onLoadFailed(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString)
      {
        WebARCameraUtils.a(WebARCameraUtils.a(), 4, paramAnonymousInt2, paramAnonymousString);
      }
      
      public void onLoadSuccess(int paramAnonymousInt, String paramAnonymousString)
      {
        if ((paramAnonymousInt != 1) && (paramAnonymousInt != 3))
        {
          if (paramAnonymousInt == 2)
          {
            WebARCameraUtils.b(paramAnonymousString);
          }
          else if (paramAnonymousInt == 4)
          {
            WebARCameraUtils.c(paramAnonymousString);
          }
          else if (paramAnonymousInt == 5)
          {
            WebARCameraUtils.d(paramAnonymousString);
            paramAnonymousString = new StringBuilder();
            paramAnonymousString.append(WebARCameraUtils.a());
            paramAnonymousString.append(File.separator);
            paramAnonymousString.append("libmtt_arcore.so");
            System.load(paramAnonymousString.toString());
          }
        }
        else {
          WebARCameraUtils.a(paramAnonymousString);
        }
        WebARCameraUtils.a(WebARCameraUtils.a(), 3, 0, "load success");
      }
    });
  }
  
  @CalledByNative
  private static void loadARModel(String paramString)
  {
    SmttServiceProxy.getInstance().loadWebARModel(paramString, new IWebARModelCallback()
    {
      public void onModelLoadFailed(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2)
      {
        WebARCameraUtils.a(WebARCameraUtils.a(), paramAnonymousInt, paramAnonymousString1, null, null);
      }
      
      public void onModelLoadSuccess(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
      {
        WebARCameraUtils.a(WebARCameraUtils.a(), 0, paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
      }
    });
  }
  
  private static native boolean nativeHasWebAR(int paramInt);
  
  private static native void nativeOnLoadAREngineResult(int paramInt1, int paramInt2, int paramInt3, String paramString);
  
  private static native void nativeOnLoadARModelResult(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3);
  
  private static native void nativeOnPauseOrResume(int paramInt, boolean paramBoolean);
  
  private static native void nativeOnPermissionRequestResult(int paramInt, boolean paramBoolean);
  
  private static native void nativeOnRequestCloudMarkerResult(int paramInt, boolean paramBoolean, String paramString1, String paramString2);
  
  private static native void nativeOnRequestCloudRecognitionResult(int paramInt, String paramString);
  
  private static native void nativeOnViewSizeChanged(int paramInt1, int paramInt2);
  
  @CalledByNative
  private static void removeShownTips()
  {
    if (!SmttServiceProxy.getInstance().getIsWebARDebugEnabled()) {
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        synchronized ()
        {
          if (WebARCameraUtils.a().get() == null) {
            return;
          }
          ((TencentWebViewProxy)WebARCameraUtils.a().get()).removeShownTips();
          return;
        }
      }
    });
  }
  
  @CalledByNative
  private static void requestCameraPermission()
  {
    a(new IWebARPermissionRequestCallback()
    {
      public void onPermissionRequestCanceled(String paramAnonymousString)
      {
        com.tencent.smtt.webkit.j.a().b(paramAnonymousString, 3);
        WebARCameraUtils.c(WebARCameraUtils.a(), false);
      }
      
      public void onPermissionRequestGranted(String paramAnonymousString)
      {
        com.tencent.smtt.webkit.j.a().a(paramAnonymousString, 3);
        WebARCameraUtils.c(WebARCameraUtils.a(), true);
      }
    });
  }
  
  @CalledByNative
  private static void requestCloudRecognition(String paramString)
  {
    SmttServiceProxy.getInstance().requestCloudRecognition(paramString, new IWebARCloudRecognitionCallback()
    {
      public void onCloudRecognitionEvent(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString)
      {
        Object localObject;
        if (paramAnonymousInt1 == 0) {
          try
          {
            JSONArray localJSONArray = new JSONObject(paramAnonymousString).getJSONArray("vMarker");
            localObject = null;
            if (localJSONArray.length() > 0) {
              localObject = localJSONArray.getJSONObject(0).optString("sUrl");
            }
            if (!TextUtils.isEmpty((CharSequence)localObject)) {
              WebARCameraUtils.a(WebARCameraUtils.a(), true, "recognition success", (String)localObject);
            }
            WebARCameraUtils.b(WebARCameraUtils.a(), paramAnonymousString);
            return;
          }
          catch (Throwable paramAnonymousString)
          {
            paramAnonymousString.printStackTrace();
            return;
          }
        }
        if (paramAnonymousInt1 == 1)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("requestCloudRecognition failed result: ");
          ((StringBuilder)localObject).append(paramAnonymousString);
          Log.e("webarCameraUtils", ((StringBuilder)localObject).toString());
          try
          {
            localObject = new JSONObject();
            ((JSONObject)localObject).put("sRetCode", paramAnonymousInt2);
            ((JSONObject)localObject).put("sMsg", paramAnonymousString);
            WebARCameraUtils.b(WebARCameraUtils.a(), ((JSONObject)localObject).toString());
            return;
          }
          catch (Exception paramAnonymousString)
          {
            paramAnonymousString.printStackTrace();
          }
        }
      }
    });
  }
  
  @CalledByNative
  private static void showRecognitionFps(int paramInt)
  {
    if (!SmttServiceProxy.getInstance().getIsWebARDebugEnabled()) {
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        synchronized ()
        {
          if (WebARCameraUtils.a().get() == null) {
            return;
          }
          ((TencentWebViewProxy)WebARCameraUtils.a().get()).showRecognitionFps(this.a);
          return;
        }
      }
    });
  }
  
  @CalledByNative
  private static void showToastForARCoreError(int paramInt) {}
  
  @CalledByNative
  private static void showWebAREngineTypeTips(String paramString)
  {
    if (!SmttServiceProxy.getInstance().getIsWebARDebugEnabled()) {
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        synchronized ()
        {
          if (WebARCameraUtils.a().get() == null) {
            return;
          }
          ((TencentWebViewProxy)WebARCameraUtils.a().get()).showWebAREngineTypeTips(this.a);
          return;
        }
      }
    });
  }
  
  @CalledByNative
  private static void stopCloudRecognition()
  {
    SmttServiceProxy.getInstance().stopCloudRecognition();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\webar\WebARCameraUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */