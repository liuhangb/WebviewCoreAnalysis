package com.tencent.common.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.CpuInfoUtils;
import com.tencent.common.utils.IntentUtilsF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class QBPluginProxy
  extends IQBPluginCallback.Stub
{
  public static final String ACTION_PLUGIN_SERV = "com.tencent.mtt.ACTION_PLUGIN_SERV";
  public static final int LOCAL_PLUGIN_FOUND = 1;
  public static final int LOCAL_PLUGIN_NEEDUNZIP = 2;
  public static final int LOCAL_PLUGIN_NONE = 0;
  public static final String MTT_MAIN_PROCESS_NAME = "com.tencent.mtt";
  public static final int PLUGIN_ERR_USERSTOP_DOWNLOADTASK = -10000;
  private int jdField_a_of_type_Int = 0;
  Context jdField_a_of_type_AndroidContentContext = null;
  ServiceConnection jdField_a_of_type_AndroidContentServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, final IBinder paramAnonymousIBinder)
    {
      BrowserExecutorSupplier.postForTimeoutTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          QBPluginProxy.this.a = IQBPluginService.Stub.asInterface(paramAnonymousIBinder);
          try
          {
            QBPluginProxy.a(QBPluginProxy.this, QBPluginProxy.a(QBPluginProxy.this));
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }
      });
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onServiceDisconnected=");
      localStringBuilder.append(paramAnonymousComponentName);
      FLogger.i("QBPluginProxy", localStringBuilder.toString());
      paramAnonymousComponentName = QBPluginProxy.this;
      paramAnonymousComponentName.jdField_a_of_type_ComTencentCommonPluginIQBPluginService = null;
      paramAnonymousComponentName.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy$a.onPluignServiceDisconnected();
      QBPluginProxy.this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
      paramAnonymousComponentName = QBPluginProxy.this;
      paramAnonymousComponentName.b = false;
      paramAnonymousComponentName.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
      if (!QBPluginProxy.this.c)
      {
        QBPluginProxy.this.c = true;
        PluginStatBehavior.addLogPath("PluginServiceBind", 5, 217);
        PluginStatBehavior.setFinCode("PluginServiceBind", 5, 217);
      }
    }
  };
  IQBPluginService jdField_a_of_type_ComTencentCommonPluginIQBPluginService = null;
  a jdField_a_of_type_ComTencentCommonPluginQBPluginProxy$a = null;
  HashMap<String, ArrayList<IPluginDownInstallCallback>> jdField_a_of_type_JavaUtilHashMap = null;
  ReentrantLock jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock = new ReentrantLock();
  boolean jdField_a_of_type_Boolean = true;
  boolean b = false;
  boolean c = false;
  
  QBPluginProxy(Context paramContext, a parama)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    PluginStatBehavior.init(this.jdField_a_of_type_AndroidContentContext);
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy$a = parama;
    this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
  }
  
  private ArrayList<IPluginDownInstallCallback> a(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (TextUtils.isEmpty(paramString)) {
      return localArrayList;
    }
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      if (this.jdField_a_of_type_JavaUtilHashMap.get(paramString) != null)
      {
        int i = 0;
        while (i < ((ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).size())
        {
          localArrayList.add(((ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).get(i));
          i += 1;
        }
      }
      return localArrayList;
    }
  }
  
  private void a(int paramInt)
    throws RemoteException
  {
    try
    {
      if (this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService != null)
      {
        this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService.setPluginCallback(this, paramInt);
        this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService.requestPluginSystemInit(this, paramInt);
      }
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  private void b(int paramInt)
  {
    if (this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService != null) {
      try
      {
        a(paramInt);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
        return;
      }
    }
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    this.jdField_a_of_type_Int = paramInt;
    Object localObject = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (localObject == null)
    {
      bool1 = bool2;
      if (!this.b)
      {
        PluginStatBehavior.setOpType("PluginServiceBind", 5);
        try
        {
          localObject = new Intent("com.tencent.mtt.ACTION_PLUGIN_SERV");
          ((Intent)localObject).putExtra("time", SystemClock.elapsedRealtime());
          IntentUtilsF.fillPackageName((Intent)localObject);
          this.jdField_a_of_type_AndroidContentContext.startService((Intent)localObject);
          bool1 = this.jdField_a_of_type_AndroidContentContext.bindService((Intent)localObject, this.jdField_a_of_type_AndroidContentServiceConnection, 0);
        }
        catch (Exception localException)
        {
          PluginStatBehavior.addLogPath("PluginServiceBind", 5, 213);
          PluginStatBehavior.addLogPath("PluginServiceBind", 5, localException.getMessage());
          localException.printStackTrace();
          this.jdField_a_of_type_Int = 0;
          bool1 = false;
        }
        FLogger.i("QBPluginProxy", "bindService=com.tencent.mtt.ACTION_PLUGIN_SERV");
        if (!bool1)
        {
          FLogger.i("QBPluginProxy", "Service bond fail");
          this.b = false;
          PluginStatBehavior.setFinCode("PluginServiceBind", 5, 214);
        }
        else
        {
          this.b = true;
          PluginStatBehavior.addLogPath("PluginServiceBind", 5, 215);
        }
      }
    }
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
    if (!bool1) {
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy$a.onPluignServiceDisconnected();
    }
  }
  
  public static boolean checkCPUTypeLimited(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("checkCPUTypeLimited  extInfo = ");
    localStringBuilder.append(paramString1);
    FLogger.d("QBPluginProxy", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("BLimit");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("=");
    paramString2 = localStringBuilder.toString();
    if (paramString1.contains(paramString2))
    {
      int i = CpuInfoUtils.getCPUType();
      if (i == 18)
      {
        FLogger.d("QBPluginProxy", "checkCPUTypeLimited 64bite");
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString2);
        localStringBuilder.append("64");
        paramString2 = localStringBuilder.toString();
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("checkCPUTypeLimited 32bite__");
        localStringBuilder.append(i);
        FLogger.d("QBPluginProxy", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString2);
        localStringBuilder.append("32");
        paramString2 = localStringBuilder.toString();
      }
      if (paramString1.contains(paramString2)) {
        return true;
      }
    }
    return false;
  }
  
  public static String getCpuType()
  {
    FLogger.d("QBPluginProxy", "getCpuType");
    switch (CpuInfoUtils.getCPUType())
    {
    default: 
      FLogger.d("QBPluginProxy", "getCpuType other cpu!!!");
      return "";
    case 33: 
      FLogger.d("QBPluginProxy", "getCpuType CPU_ARM_V6 below");
      return "V5";
    case 18: 
      FLogger.d("QBPluginProxy", "getCpuType ANDROID_CPU_ARMv8 64bit");
      return "V8";
    case 17: 
      FLogger.d("QBPluginProxy", "getCpuType CPU_ARM_NENO");
      return "NENO";
    case 9: 
      FLogger.d("QBPluginProxy", "getCpuType CPU_ARM_VFP_V3");
      return "V7VFP";
    case 6: 
      FLogger.d("QBPluginProxy", "getCpuType ANDROID_CPU_X86_FEATURE_SSSE3");
      return "X86";
    case 5: 
      FLogger.d("QBPluginProxy", "getCpuType CPU_ARM_V7");
      return "V7";
    }
    FLogger.d("QBPluginProxy", "getCpuType CPU_ARM_V6");
    return "V6";
  }
  
  void a(Context paramContext, int paramInt)
  {
    paramContext = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (paramContext != null)
    {
      try
      {
        paramContext.requestPluginSystemInit(this, paramInt);
      }
      catch (RemoteException paramContext)
      {
        paramContext.printStackTrace();
      }
      if (this.jdField_a_of_type_Boolean)
      {
        b(paramInt);
        this.jdField_a_of_type_Boolean = false;
      }
      return;
    }
    b(paramInt);
  }
  
  public void addPluginListener(String paramString, IPluginDownInstallCallback paramIPluginDownInstallCallback)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      if (this.jdField_a_of_type_JavaUtilHashMap.get(paramString) == null) {
        this.jdField_a_of_type_JavaUtilHashMap.put(paramString, new ArrayList());
      }
      if (!((ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).contains(paramIPluginDownInstallCallback)) {
        ((ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).add(paramIPluginDownInstallCallback);
      }
      ??? = new StringBuilder();
      ((StringBuilder)???).append("addPluginListener::");
      ((StringBuilder)???).append(paramString);
      ((StringBuilder)???).append(",");
      ((StringBuilder)???).append(paramIPluginDownInstallCallback);
      ((StringBuilder)???).append(",totalLitener=");
      ((StringBuilder)???).append(((ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString)).size());
      FLogger.d("QBPluginProxy", ((StringBuilder)???).toString());
      return;
    }
  }
  
  public int checkLocalPlugin(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.checkLocalPlugin(paramString, paramInt1, paramInt2);
    }
    throw new RemoteException();
  }
  
  public boolean checkNeedUpdate(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.checkNeedUpdate(paramString, paramInt1, paramInt2, paramQBPluginItemInfo);
    }
    throw new RemoteException();
  }
  
  public ArrayList<QBPluginItemInfo> getAllPluginList(int paramInt)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return (ArrayList)localIQBPluginService.getAllPluginList(paramInt);
    }
    throw new RemoteException();
  }
  
  public QBPluginItemInfo getPluginInfo(String paramString, int paramInt)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.getPluginInfo(paramString, paramInt);
    }
    throw new RemoteException();
  }
  
  public boolean getPluginInfoAsync(String paramString, IGetPluginInfoCallback.Stub paramStub, int paramInt)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.getPluginInfoAsync(paramString, paramStub, paramInt);
    }
    throw new RemoteException();
  }
  
  public ArrayList<QBPluginItemInfo> getPluginListByPos(int paramInt1, int paramInt2)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return (ArrayList)localIQBPluginService.getPluginListByPos(paramInt1, paramInt2);
    }
    throw new RemoteException();
  }
  
  public ArrayList<QBPluginItemInfo> getPluginListByType(int paramInt1, int paramInt2)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return (ArrayList)localIQBPluginService.getPluginListByType(paramInt1, paramInt2);
    }
    throw new RemoteException();
  }
  
  public String getPluginPath(String paramString, int paramInt)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.getPluginPath(paramString, paramInt);
    }
    throw new RemoteException();
  }
  
  public void installPlugin(String paramString, int paramInt1, int paramInt2, IInstallPluginCallback paramIInstallPluginCallback, QBPluginItemInfo paramQBPluginItemInfo, boolean paramBoolean)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null)
    {
      localIQBPluginService.installPlugin(paramString, paramInt1, paramInt2, paramIInstallPluginCallback, paramQBPluginItemInfo, paramBoolean);
      return;
    }
    throw new RemoteException();
  }
  
  public boolean isNewVersionFileDownloaded(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.isNewVersionFileDownloaded(paramString, paramInt, paramQBPluginItemInfo);
    }
    throw new RemoteException();
  }
  
  public boolean needForceUpdate(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.needForceUpdate(paramString, paramInt, paramQBPluginItemInfo);
    }
    throw new RemoteException();
  }
  
  public void onGetPluginListFailed(String arg1, int paramInt)
    throws RemoteException
  {
    boolean bool = TextUtils.isEmpty(???);
    int i = 0;
    if (bool)
    {
      Object localObject1 = new HashMap();
      synchronized (this.jdField_a_of_type_JavaUtilHashMap)
      {
        Object localObject3 = this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          Map.Entry localEntry = (Map.Entry)((Iterator)localObject3).next();
          ((HashMap)localObject1).put(localEntry.getKey(), new ArrayList());
          i = 0;
          while (i < ((ArrayList)localEntry.getValue()).size())
          {
            ((ArrayList)((HashMap)localObject1).get(localEntry.getKey())).add(((ArrayList)localEntry.getValue()).get(i));
            i += 1;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("doAfterServConnected callback=");
          localStringBuilder.append(localEntry.getValue());
          FLogger.d("QBPluginProxy", localStringBuilder.toString());
        }
        ??? = ((HashMap)localObject1).entrySet().iterator();
        while (???.hasNext())
        {
          localObject1 = (Map.Entry)???.next();
          i = 0;
          while (i < ((ArrayList)((Map.Entry)localObject1).getValue()).size())
          {
            ((IPluginDownInstallCallback)((ArrayList)((Map.Entry)localObject1).getValue()).get(i)).onGetPluginListFailed(paramInt);
            i += 1;
          }
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("doAfterServConnected callback=");
          ((StringBuilder)localObject3).append(((Map.Entry)localObject1).getValue());
          FLogger.d("QBPluginProxy", ((StringBuilder)localObject3).toString());
        }
        return;
      }
    }
    ??? = a(???);
    while (i < ???.size())
    {
      ((IPluginDownInstallCallback)???.get(i)).onGetPluginListFailed(paramInt);
      i += 1;
    }
  }
  
  public void onGetPluginListSucc(String arg1, int paramInt)
    throws RemoteException
  {
    boolean bool = TextUtils.isEmpty(???);
    int i = 0;
    if (bool)
    {
      Object localObject1 = new HashMap();
      synchronized (this.jdField_a_of_type_JavaUtilHashMap)
      {
        Object localObject3 = this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          Map.Entry localEntry = (Map.Entry)((Iterator)localObject3).next();
          ((HashMap)localObject1).put(localEntry.getKey(), new ArrayList());
          i = 0;
          while (i < ((ArrayList)localEntry.getValue()).size())
          {
            ((ArrayList)((HashMap)localObject1).get(localEntry.getKey())).add(((ArrayList)localEntry.getValue()).get(i));
            i += 1;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("doAfterServConnected callback=");
          localStringBuilder.append(localEntry.getValue());
          FLogger.d("QBPluginProxy", localStringBuilder.toString());
        }
        ??? = ((HashMap)localObject1).entrySet().iterator();
        while (???.hasNext())
        {
          localObject1 = (Map.Entry)???.next();
          i = 0;
          while (i < ((ArrayList)((Map.Entry)localObject1).getValue()).size())
          {
            ((IPluginDownInstallCallback)((ArrayList)((Map.Entry)localObject1).getValue()).get(i)).onGetPluginListSucc(paramInt);
            i += 1;
          }
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("doAfterServConnected callback=");
          ((StringBuilder)localObject3).append(((Map.Entry)localObject1).getValue());
          FLogger.d("QBPluginProxy", ((StringBuilder)localObject3).toString());
        }
        return;
      }
    }
    ??? = a(???);
    while (i < ???.size())
    {
      ((IPluginDownInstallCallback)???.get(i)).onGetPluginListFailed(paramInt);
      i += 1;
    }
  }
  
  public void onPluginDownloadCreated(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString1);
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginDownloadCreated(paramString1, paramString2, paramInt1, paramInt2);
      i += 1;
    }
  }
  
  public void onPluginDownloadFailed(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString1);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("pkglstnr1(");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append("_");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append("_");
    localStringBuilder.append(localArrayList.size());
    localStringBuilder.append(")");
    PluginStatBehavior.addLogPath(paramString1, 4, localStringBuilder.toString());
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginDownloadFailed(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
      i += 1;
    }
  }
  
  public void onPluginDownloadProgress(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString1);
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginDownloadProgress(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
      i += 1;
    }
  }
  
  public void onPluginDownloadStarted(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString1);
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginDownloadStarted(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
      i += 1;
    }
  }
  
  public void onPluginDownloadSuccessed(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString1);
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginDownloadSuccessed(paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
      i += 1;
    }
  }
  
  public void onPluginInstallFailed(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString);
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginInstallFailed(paramString, paramInt1, paramInt2);
      i += 1;
    }
  }
  
  public void onPluginInstallSuccessed(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2)
    throws RemoteException
  {
    ArrayList localArrayList = a(paramString);
    int i = 0;
    while (i < localArrayList.size())
    {
      ((IPluginDownInstallCallback)localArrayList.get(i)).onPluginInstallSuccessed(paramString, paramQBPluginItemInfo, paramInt1, paramInt2);
      i += 1;
    }
  }
  
  public void onPluginSystemInit(boolean paramBoolean, int paramInt)
    throws RemoteException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onPluginSystemInit ,name: ");
    localStringBuilder.append(this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService);
    localStringBuilder.append(":initQbtbsResult=");
    localStringBuilder.append(paramBoolean);
    FLogger.i("QBPluginProxy", localStringBuilder.toString());
    if (!paramBoolean)
    {
      this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService = null;
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy$a.onPluignServiceDisconnected();
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
      this.b = false;
      this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
      if (!this.c) {
        this.c = true;
      }
      return;
    }
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy$a.onBindPluginSuccess(this);
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.lock();
    this.b = false;
    this.jdField_a_of_type_JavaUtilConcurrentLocksReentrantLock.unlock();
    if (!this.c) {
      this.c = true;
    }
  }
  
  public boolean refreshPluginListForced(String paramString, int paramInt, IGetPluginInfoCallback.Stub paramStub)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.refreshPluginListForced(paramString, paramInt, paramStub);
    }
    throw new RemoteException();
  }
  
  public int refreshPluignListIfNeeded(String paramString, int paramInt)
    throws RemoteException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("refreshPluignListIfNeeded:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(",mPluginService=");
    localStringBuilder.append(this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService);
    FLogger.i("QBPluginProxy", localStringBuilder.toString());
    paramString = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (paramString != null)
    {
      boolean bool = paramString.refreshPluignListIfNeeded(paramInt);
      paramInt = 1;
      if (bool == true) {
        paramInt = 0;
      }
      return paramInt;
    }
    throw new RemoteException();
  }
  
  public boolean removePluginListener(String paramString, IPluginDownInstallCallback paramIPluginDownInstallCallback)
  {
    synchronized (this.jdField_a_of_type_JavaUtilHashMap)
    {
      ArrayList localArrayList = (ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
      if (localArrayList == null) {
        return false;
      }
      if (localArrayList.contains(paramIPluginDownInstallCallback))
      {
        localArrayList.remove(paramIPluginDownInstallCallback);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("removePluginListener::");
        localStringBuilder.append(paramString);
        localStringBuilder.append(",");
        localStringBuilder.append(paramIPluginDownInstallCallback);
        FLogger.d("QBPluginProxy", localStringBuilder.toString());
      }
      if (localArrayList.size() == 0) {
        this.jdField_a_of_type_JavaUtilHashMap.remove(paramString);
      }
      return true;
    }
  }
  
  public void setLocalPluginServiceImpl(IQBPluginService paramIQBPluginService)
  {
    if ((this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService == null) && (paramIQBPluginService != null)) {
      this.jdField_a_of_type_Boolean = true;
    }
    if (this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService == null) {
      this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService = paramIQBPluginService;
    }
    if (this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService != paramIQBPluginService)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setLocalPluginServiceImpl(mPluginService=");
      localStringBuilder.append(this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService);
      localStringBuilder.append(",pluginService=");
      localStringBuilder.append(paramIQBPluginService);
      localStringBuilder.append("");
      FLogger.i("QBPluginProxy", localStringBuilder.toString());
    }
  }
  
  public void setPluginJarZipType(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("needForceUpdate,this:");
    ((StringBuilder)localObject).append(this);
    ((StringBuilder)localObject).append(",mPluginService=");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService);
    FLogger.i("QBPluginProxy", ((StringBuilder)localObject).toString());
    localObject = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localObject != null)
    {
      ((IQBPluginService)localObject).setPluginJarZipType(paramString, paramInt1, paramInt2);
      return;
    }
    throw new RemoteException();
  }
  
  public int startDownloadPlugin(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null) {
      return localIQBPluginService.downloadPlugin(paramString, paramInt1, paramInt2, paramQBPluginItemInfo);
    }
    throw new RemoteException();
  }
  
  public int stopDownloadPlugin(String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null)
    {
      QBPluginProxy localQBPluginProxy;
      if (paramBoolean2) {
        localQBPluginProxy = this;
      } else {
        localQBPluginProxy = null;
      }
      return localIQBPluginService.stopDownloadPlugin(paramString, paramBoolean1, localQBPluginProxy, paramInt) ^ true;
    }
    throw new RemoteException();
  }
  
  public void updatePluginNeesUpdateFlag(String paramString, int paramInt1, int paramInt2)
    throws RemoteException
  {
    IQBPluginService localIQBPluginService = this.jdField_a_of_type_ComTencentCommonPluginIQBPluginService;
    if (localIQBPluginService != null)
    {
      localIQBPluginService.updatePluginNeesUpdateFlag(paramString, paramInt1, paramInt2);
      return;
    }
    throw new RemoteException();
  }
  
  static abstract interface a
  {
    public abstract void onBindPluginSuccess(QBPluginProxy paramQBPluginProxy);
    
    public abstract void onPluignServiceDisconnected();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */