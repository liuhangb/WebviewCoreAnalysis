package org.chromium.content.browser;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.chromium.base.Callback;
import org.chromium.base.CollectionUtil;
import org.chromium.base.ContextUtils;
import org.chromium.base.CpuFeatures;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.library_loader.Linker;
import org.chromium.base.process_launcher.ChildConnectionAllocator;
import org.chromium.base.process_launcher.ChildConnectionAllocator.ConnectionFactory;
import org.chromium.base.process_launcher.ChildConnectionAllocator.Listener;
import org.chromium.base.process_launcher.ChildProcessConnection;
import org.chromium.base.process_launcher.ChildProcessConnection.ServiceCallback;
import org.chromium.base.process_launcher.ChildProcessLauncher;
import org.chromium.base.process_launcher.ChildProcessLauncher.Delegate;
import org.chromium.base.process_launcher.FileDescriptorInfo;
import org.chromium.content.app.ChromiumLinkerParams;
import org.chromium.content.app.SandboxedProcessService;
import org.chromium.content.common.ContentSwitches;

@JNINamespace("content::internal")
public class ChildProcessLauncherHelper
{
  private static int jdField_a_of_type_Int = -1;
  private static String jdField_a_of_type_JavaLangString;
  private static final Map<Integer, ChildProcessLauncherHelper> jdField_a_of_type_JavaUtilMap;
  private static ChildConnectionAllocator.ConnectionFactory jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator$ConnectionFactory;
  private static ChildConnectionAllocator jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
  private static BindingManager jdField_a_of_type_OrgChromiumContentBrowserBindingManager;
  private static SpareChildConnection jdField_a_of_type_OrgChromiumContentBrowserSpareChildConnection;
  private static long jdField_b_of_type_Long;
  private static ChildConnectionAllocator jdField_b_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
  private static boolean jdField_b_of_type_Boolean = true;
  private static boolean e;
  private long jdField_a_of_type_Long;
  private final ChildProcessLauncher.Delegate jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate = new ChildProcessLauncher.Delegate()
  {
    public ChildProcessConnection getBoundConnection(ChildConnectionAllocator paramAnonymousChildConnectionAllocator, ChildProcessConnection.ServiceCallback paramAnonymousServiceCallback)
    {
      if (ChildProcessLauncherHelper.a() == null) {
        return null;
      }
      return ChildProcessLauncherHelper.a().getConnection(paramAnonymousChildConnectionAllocator, paramAnonymousServiceCallback);
    }
    
    public void onBeforeConnectionAllocated(Bundle paramAnonymousBundle)
    {
      ChildProcessLauncherHelper.a(paramAnonymousBundle);
    }
    
    public void onBeforeConnectionSetup(Bundle paramAnonymousBundle)
    {
      paramAnonymousBundle.putInt("com.google.android.apps.chrome.extra.cpu_count", CpuFeatures.getCount());
      paramAnonymousBundle.putLong("com.google.android.apps.chrome.extra.cpu_features", CpuFeatures.getMask());
      if (Linker.isUsed()) {
        paramAnonymousBundle.putBundle("org.chromium.base.android.linker.shared_relros", Linker.getInstance().getSharedRelros());
      }
    }
    
    public void onConnectionEstablished(ChildProcessConnection paramAnonymousChildProcessConnection)
    {
      int i = paramAnonymousChildProcessConnection.getPid();
      if ((!jdField_a_of_type_Boolean) && (i <= 0)) {
        throw new AssertionError();
      }
      ChildProcessLauncherHelper.a().put(Integer.valueOf(i), ChildProcessLauncherHelper.this);
      if (ChildProcessLauncherHelper.a(ChildProcessLauncherHelper.this)) {
        ChildProcessLauncherHelper.getBindingManager().addNewConnection(i, paramAnonymousChildProcessConnection);
      }
      if (ChildProcessLauncherHelper.a(ChildProcessLauncherHelper.this) != 0L) {
        ChildProcessLauncherHelper.a(ChildProcessLauncherHelper.a(ChildProcessLauncherHelper.this), paramAnonymousChildProcessConnection.getPid());
      }
      ChildProcessLauncherHelper.a(ChildProcessLauncherHelper.this, 0L);
    }
    
    public void onConnectionLost(ChildProcessConnection paramAnonymousChildProcessConnection)
    {
      if ((!jdField_a_of_type_Boolean) && (paramAnonymousChildProcessConnection.getPid() <= 0)) {
        throw new AssertionError();
      }
      ChildProcessLauncherHelper.a().remove(Integer.valueOf(paramAnonymousChildProcessConnection.getPid()));
      if (ChildProcessLauncherHelper.a(ChildProcessLauncherHelper.this)) {
        ChildProcessLauncherHelper.getBindingManager().removeConnection(paramAnonymousChildProcessConnection.getPid());
      }
    }
  };
  private final ChildProcessLauncher jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher;
  private int jdField_b_of_type_Int = 0;
  private final String jdField_b_of_type_JavaLangString;
  private final boolean c;
  private final boolean d;
  
  static
  {
    jdField_a_of_type_JavaUtilMap = new HashMap();
  }
  
  private ChildProcessLauncherHelper(long paramLong, String[] paramArrayOfString, FileDescriptorInfo[] paramArrayOfFileDescriptorInfo, boolean paramBoolean, IBinder paramIBinder)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_Long = paramLong;
    this.c = paramBoolean;
    this.d = paramBoolean;
    ChildConnectionAllocator localChildConnectionAllocator = a(ContextUtils.getApplicationContext(), paramBoolean);
    Handler localHandler = LauncherThread.getHandler();
    ChildProcessLauncher.Delegate localDelegate = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher$Delegate;
    if (paramIBinder == null) {
      paramIBinder = null;
    } else {
      paramIBinder = Arrays.asList(new IBinder[] { paramIBinder });
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher = new ChildProcessLauncher(localHandler, localDelegate, paramArrayOfString, paramArrayOfFileDescriptorInfo, localChildConnectionAllocator, paramIBinder);
    this.jdField_b_of_type_JavaLangString = ContentSwitches.a(paramArrayOfString, "type");
  }
  
  @VisibleForTesting
  static ChildConnectionAllocator a(Context paramContext, boolean paramBoolean)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    Object localObject = ChildProcessCreationParams.getPackageNameForService();
    boolean bool2 = ChildProcessCreationParams.getBindToCallerCheck();
    boolean bool1;
    if ((paramBoolean) && (ChildProcessCreationParams.getIsSandboxedServiceExternal())) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (!paramBoolean)
    {
      if (jdField_b_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator == null) {
        jdField_b_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator = ChildConnectionAllocator.create(paramContext, LauncherThread.getHandler(), (String)localObject, "org.chromium.content.app.PrivilegedProcessService", "org.chromium.content.browser.NUM_PRIVILEGED_SERVICES", bool2, bool1, true);
      }
      return jdField_b_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
    }
    if (jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator == null)
    {
      if (jdField_a_of_type_Int != -1)
      {
        if (!TextUtils.isEmpty(jdField_a_of_type_JavaLangString)) {
          paramContext = jdField_a_of_type_JavaLangString;
        } else {
          paramContext = SandboxedProcessService.class.getName();
        }
        paramContext = ChildConnectionAllocator.createForTest((String)localObject, paramContext, jdField_a_of_type_Int, bool2, bool1, false);
      }
      else
      {
        paramContext = ChildConnectionAllocator.create(paramContext, LauncherThread.getHandler(), (String)localObject, "org.chromium.content.app.SandboxedProcessService", "org.chromium.content.browser.NUM_SANDBOXED_SERVICES", bool2, bool1, false);
      }
      localObject = jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator$ConnectionFactory;
      if (localObject != null) {
        paramContext.setConnectionFactoryForTesting((ChildConnectionAllocator.ConnectionFactory)localObject);
      }
      jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator = paramContext;
      paramContext.addListener(new ChildConnectionAllocator.Listener()
      {
        public void onConnectionAllocated(ChildConnectionAllocator paramAnonymousChildConnectionAllocator, ChildProcessConnection paramAnonymousChildProcessConnection)
        {
          if ((!jdField_a_of_type_Boolean) && (paramAnonymousChildProcessConnection == null)) {
            throw new AssertionError();
          }
          if ((!jdField_a_of_type_Boolean) && (paramAnonymousChildConnectionAllocator != this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator)) {
            throw new AssertionError();
          }
          if (!paramAnonymousChildConnectionAllocator.isFreeConnectionAvailable()) {
            ChildProcessLauncherHelper.getBindingManager().releaseAllModerateBindings();
          }
        }
      });
    }
    return jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
  }
  
  private static ChromiumLinkerParams a()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    a();
    if ((!jdField_a_of_type_Boolean) && (!e)) {
      throw new AssertionError();
    }
    if (jdField_b_of_type_Long == 0L) {
      return null;
    }
    if (Linker.areTestsEnabled())
    {
      Linker localLinker = Linker.getInstance();
      return new ChromiumLinkerParams(jdField_b_of_type_Long, true, localLinker.getTestRunnerClassNameForTesting());
    }
    return new ChromiumLinkerParams(jdField_b_of_type_Long, true);
  }
  
  private static void a()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    if (e) {
      return;
    }
    if (Linker.isUsed())
    {
      jdField_b_of_type_Long = Linker.getInstance().getBaseLoadAddress();
      long l = jdField_b_of_type_Long;
    }
    e = true;
  }
  
  private static Bundle b(Bundle paramBundle)
  {
    Object localObject = ChildProcessCreationParams.get();
    if (localObject != null) {
      ((ChildProcessCreationParams)localObject).addIntentExtras(paramBundle);
    }
    paramBundle.putBoolean("org.chromium.base.process_launcher.extra.bind_to_caller", ChildProcessCreationParams.getBindToCallerCheck());
    localObject = a();
    if (localObject != null) {
      ((ChromiumLinkerParams)localObject).a(paramBundle);
    }
    return paramBundle;
  }
  
  private static void b(Context paramContext)
  {
    Object localObject = jdField_a_of_type_OrgChromiumContentBrowserSpareChildConnection;
    if ((localObject != null) && (!((SpareChildConnection)localObject).isEmpty())) {
      return;
    }
    localObject = b(new Bundle());
    jdField_a_of_type_OrgChromiumContentBrowserSpareChildConnection = new SpareChildConnection(paramContext, a(paramContext, true), (Bundle)localObject);
  }
  
  @VisibleForTesting
  public static boolean crashProcessForTesting(int paramInt)
  {
    if (jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(paramInt)) == null) {
      return false;
    }
    ChildProcessConnection localChildProcessConnection = ((ChildProcessLauncherHelper)jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(paramInt))).jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getConnection();
    if (localChildProcessConnection == null) {
      return false;
    }
    try
    {
      localChildProcessConnection.crashServiceForTesting();
      return true;
    }
    catch (RemoteException localRemoteException) {}
    return false;
  }
  
  @VisibleForTesting
  @CalledByNative
  public static ChildProcessLauncherHelper createAndStart(long paramLong, String[] paramArrayOfString, FileDescriptorInfo[] paramArrayOfFileDescriptorInfo)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    Object localObject = ContentSwitches.a(paramArrayOfString, "type");
    if (!"renderer".equals(localObject))
    {
      if ("gpu-process".equals(localObject))
      {
        bool = false;
        break label112;
      }
      if ((!jdField_a_of_type_Boolean) && (!"utility".equals(localObject))) {
        throw new AssertionError();
      }
      if ("network".equals(ContentSwitches.a(paramArrayOfString, "service-sandbox-type")))
      {
        bool = false;
        break label112;
      }
    }
    boolean bool = true;
    label112:
    if ("gpu-process".equals(localObject)) {
      localObject = new GpuProcessCallback();
    } else {
      localObject = null;
    }
    paramArrayOfString = new ChildProcessLauncherHelper(paramLong, paramArrayOfString, paramArrayOfFileDescriptorInfo, bool, (IBinder)localObject);
    paramArrayOfString.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.start(true, true);
    return paramArrayOfString;
  }
  
  @VisibleForTesting
  public static ChildProcessLauncherHelper createAndStartForTesting(String[] paramArrayOfString, FileDescriptorInfo[] paramArrayOfFileDescriptorInfo, boolean paramBoolean1, IBinder paramIBinder, boolean paramBoolean2)
  {
    paramArrayOfString = new ChildProcessLauncherHelper(0L, paramArrayOfString, paramArrayOfFileDescriptorInfo, paramBoolean1, paramIBinder);
    paramArrayOfString.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.start(paramBoolean2, true);
    return paramArrayOfString;
  }
  
  @VisibleForTesting
  public static Map<Integer, ChildProcessLauncherHelper> getAllProcessesForTesting()
  {
    return jdField_a_of_type_JavaUtilMap;
  }
  
  public static BindingManager getBindingManager()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    if (jdField_a_of_type_OrgChromiumContentBrowserBindingManager == null) {
      jdField_a_of_type_OrgChromiumContentBrowserBindingManager = BindingManagerImpl.createBindingManager();
    }
    return jdField_a_of_type_OrgChromiumContentBrowserBindingManager;
  }
  
  public static ChildProcessLauncherHelper getByPid(int paramInt)
  {
    return (ChildProcessLauncherHelper)jdField_a_of_type_JavaUtilMap.get(Integer.valueOf(paramInt));
  }
  
  @VisibleForTesting
  public static int getConnectedSandboxedServicesCountForTesting()
  {
    ChildConnectionAllocator localChildConnectionAllocator = jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator;
    if (localChildConnectionAllocator == null) {
      return 0;
    }
    return localChildConnectionAllocator.allocatedConnectionsCountForTesting();
  }
  
  @CalledByNative
  private static int getNumberOfRendererSlots()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    int i = jdField_a_of_type_Int;
    if (i != -1) {
      return i;
    }
    Context localContext = ContextUtils.getApplicationContext();
    String str = ChildProcessCreationParams.getPackageNameForService();
    try
    {
      i = ChildConnectionAllocator.getNumberOfServices(localContext, str, "org.chromium.content.browser.NUM_SANDBOXED_SERVICES");
      return i;
    }
    catch (RuntimeException localRuntimeException)
    {
      for (;;) {}
    }
    return 65535;
  }
  
  public static void getProcessIdsByType(final Callback<Map<String, List<Integer>>> paramCallback)
  {
    LauncherThread.post(new Runnable()
    {
      public void run()
      {
        final HashMap localHashMap = new HashMap();
        CollectionUtil.forEach(ChildProcessLauncherHelper.a(), new Callback()
        {
          public void onResult(Map.Entry<Integer, ChildProcessLauncherHelper> paramAnonymous2Entry)
          {
            String str = ((ChildProcessLauncherHelper)paramAnonymous2Entry.getValue()).getProcessType();
            List localList = (List)localHashMap.get(str);
            Object localObject = localList;
            if (localList == null)
            {
              localObject = new ArrayList();
              localHashMap.put(str, localObject);
            }
            ((List)localObject).add(paramAnonymous2Entry.getKey());
          }
        });
        this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
        {
          public void run()
          {
            ChildProcessLauncherHelper.7.this.a.onResult(localHashMap);
          }
        });
      }
    });
  }
  
  @VisibleForTesting
  public static ChildProcessConnection getWarmUpConnectionForTesting()
  {
    SpareChildConnection localSpareChildConnection = jdField_a_of_type_OrgChromiumContentBrowserSpareChildConnection;
    if (localSpareChildConnection == null) {
      return null;
    }
    return localSpareChildConnection.getConnection();
  }
  
  @CalledByNative
  private boolean isOomProtected()
  {
    ChildProcessConnection localChildProcessConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getConnection();
    boolean bool2 = false;
    if (localChildProcessConnection == null) {
      return false;
    }
    boolean bool1 = bool2;
    if (jdField_b_of_type_Boolean)
    {
      bool1 = bool2;
      if (!localChildProcessConnection.isWaivedBoundOnlyOrWasWhenDied()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  @CalledByNative
  private static FileDescriptorInfo makeFdInfo(int paramInt1, int paramInt2, boolean paramBoolean, long paramLong1, long paramLong2)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ParcelFileDescriptor localParcelFileDescriptor;
    if (paramBoolean) {
      localParcelFileDescriptor = ParcelFileDescriptor.adoptFd(paramInt2);
    }
    try
    {
      localParcelFileDescriptor = ParcelFileDescriptor.fromFd(paramInt2);
      return new FileDescriptorInfo(paramInt1, localParcelFileDescriptor, paramLong1, paramLong2);
    }
    catch (IOException localIOException)
    {
      Log.e("ChildProcLH", "Invalid FD provided for process connection, aborting connection.", new Object[] { localIOException });
    }
    return null;
  }
  
  private static native void nativeOnChildProcessStarted(long paramLong, int paramInt);
  
  public static void onBroughtToForeground()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    jdField_b_of_type_Boolean = true;
    LauncherThread.post(new Runnable()
    {
      public void run()
      {
        ChildProcessLauncherHelper.getBindingManager().onBroughtToForeground();
      }
    });
  }
  
  public static void onSentToBackground()
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    jdField_b_of_type_Boolean = false;
    LauncherThread.post(new Runnable()
    {
      public void run()
      {
        ChildProcessLauncherHelper.getBindingManager().onSentToBackground();
      }
    });
  }
  
  @VisibleForTesting
  public static void setBindingManagerForTesting(BindingManager paramBindingManager)
  {
    jdField_a_of_type_OrgChromiumContentBrowserBindingManager = paramBindingManager;
  }
  
  @CalledByNative
  private void setPriority(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getPid() != paramInt1)) {
      throw new AssertionError();
    }
    ChildProcessConnection localChildProcessConnection = this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getConnection();
    if (this.jdField_b_of_type_Int != paramInt2) {
      switch (paramInt2)
      {
      default: 
        if (!jdField_a_of_type_Boolean) {
          break;
        }
      case 3: 
        if ((goto 150) && (!jdField_a_of_type_Boolean)) {
          throw new AssertionError();
        }
        break;
      case 2: 
        localChildProcessConnection.addStrongBinding();
        break;
      case 1: 
        localChildProcessConnection.addModerateBinding();
        break label150;
        throw new AssertionError();
      }
    }
    label150:
    if (ChildProcessCreationParams.getIgnoreVisibilityForImportance())
    {
      paramBoolean1 = false;
      paramBoolean2 = false;
    }
    getBindingManager().setPriority(paramInt1, paramBoolean1, paramBoolean2);
    paramInt1 = this.jdField_b_of_type_Int;
    if (paramInt1 != paramInt2) {
      switch (paramInt1)
      {
      default: 
        if (!jdField_a_of_type_Boolean) {
          break;
        }
      case 3: 
        if ((goto 262) && (!jdField_a_of_type_Boolean)) {
          throw new AssertionError();
        }
        break;
      case 2: 
        localChildProcessConnection.removeStrongBinding();
        break;
      case 1: 
        localChildProcessConnection.removeModerateBinding();
        break label262;
        throw new AssertionError();
      }
    }
    label262:
    this.jdField_b_of_type_Int = paramInt2;
  }
  
  @VisibleForTesting
  public static void setSandboxServicesSettingsForTesting(ChildConnectionAllocator.ConnectionFactory paramConnectionFactory, int paramInt, String paramString)
  {
    jdField_a_of_type_OrgChromiumBaseProcess_launcherChildConnectionAllocator$ConnectionFactory = paramConnectionFactory;
    jdField_a_of_type_Int = paramInt;
    jdField_a_of_type_JavaLangString = paramString;
  }
  
  public static void startModerateBindingManagement(Context paramContext)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    LauncherThread.post(new Runnable()
    {
      public void run()
      {
        ChildConnectionAllocator localChildConnectionAllocator = ChildProcessLauncherHelper.a(this.a, true);
        ChildProcessLauncherHelper.getBindingManager().startModerateBindingManagement(this.a, localChildConnectionAllocator.getNumberOfServices());
      }
    });
  }
  
  @CalledByNative
  static void stop(int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    ChildProcessLauncherHelper localChildProcessLauncherHelper = getByPid(paramInt);
    if (localChildProcessLauncherHelper != null) {
      localChildProcessLauncherHelper.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.stop();
    }
  }
  
  public static void warmUp(Context paramContext)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError();
    }
    LauncherThread.post(new Runnable()
    {
      public void run()
      {
        ChildProcessLauncherHelper.a(this.a);
      }
    });
  }
  
  @VisibleForTesting
  public ChildConnectionAllocator getChildConnectionAllocatorForTesting()
  {
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getConnectionAllocator();
  }
  
  @VisibleForTesting
  public ChildProcessConnection getChildProcessConnection()
  {
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getConnection();
  }
  
  public int getPid()
  {
    if ((!jdField_a_of_type_Boolean) && (!LauncherThread.runningOnLauncherThread())) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessLauncher.getPid();
  }
  
  public String getProcessType()
  {
    if (TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString)) {
      return "";
    }
    return this.jdField_b_of_type_JavaLangString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ChildProcessLauncherHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */