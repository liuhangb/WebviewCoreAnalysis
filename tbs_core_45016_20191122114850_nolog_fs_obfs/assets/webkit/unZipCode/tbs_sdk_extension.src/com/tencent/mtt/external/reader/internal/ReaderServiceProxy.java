package com.tencent.mtt.external.reader.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.tencent.mtt.external.reader.export.IReaderSdkService;
import com.tencent.mtt.external.reader.export.IReaderSdkService.Stub;
import com.tencent.mtt.external.reader.export.ISdkCallback;
import com.tencent.mtt.external.reader.export.ISdkCallback.Stub;
import com.tencent.mtt.external.reader.utils.AppEngine;

public class ReaderServiceProxy
{
  public static final String ACTION_READER = "com.tencent.mtt.reader.ACTION_SDK_CLIENT";
  static ReaderServiceProxy jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy;
  public static final int mBaseID = 4096;
  public static final int sFolderID = 4097;
  private IReaderSdkService jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService = null;
  ISdkCallback jdField_a_of_type_ComTencentMttExternalReaderExportISdkCallback = null;
  ICallback jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback = null;
  private a jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$a = null;
  boolean jdField_a_of_type_Boolean = false;
  
  public static ReaderServiceProxy getInstance()
  {
    if (jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy == null) {
      jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy = new ReaderServiceProxy();
    }
    return jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy;
  }
  
  void a()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderExportISdkCallback == null) {
      this.jdField_a_of_type_ComTencentMttExternalReaderExportISdkCallback = new ISdkCallback.Stub()
      {
        public void onSdkCallback(int paramAnonymousInt, Bundle paramAnonymousBundle)
          throws RemoteException
        {
          if ((paramAnonymousInt == 4097) && (!ReaderServiceProxy.this.jdField_a_of_type_Boolean) && (ReaderServiceProxy.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback != null)) {
            ReaderServiceProxy.this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback.onCallback(0, paramAnonymousBundle);
          }
        }
      };
    }
    try
    {
      setParams(null);
      if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService != null)
      {
        this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService.setCallback(this.jdField_a_of_type_ComTencentMttExternalReaderExportISdkCallback);
        return;
      }
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }
  
  public void destroy(Context paramContext)
  {
    this.jdField_a_of_type_Boolean = true;
    try
    {
      if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService != null) {
        this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService.setCallback(null);
      }
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService = null;
    a locala = this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$a;
    if (locala != null) {
      paramContext.unbindService(locala);
    }
    new Intent("com.tencent.mtt.reader.ACTION_SDK_CLIENT");
    this.jdField_a_of_type_ComTencentMttExternalReaderExportISdkCallback = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$a = null;
  }
  
  public void setCallback(ICallback paramICallback)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$ICallback = paramICallback;
  }
  
  public void setParams(Bundle paramBundle)
  {
    try
    {
      if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService != null)
      {
        this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService.setParams(4096, paramBundle);
        return;
      }
    }
    catch (RemoteException paramBundle)
    {
      paramBundle.printStackTrace();
    }
  }
  
  public void startServerWhileNotRun(Context paramContext)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderSdkService == null) {
      startService(paramContext);
    }
  }
  
  public void startService(Context paramContext)
  {
    try
    {
      Intent localIntent = new Intent("com.tencent.mtt.reader.ACTION_SDK_CLIENT");
      localIntent.setPackage("com.tencent.mtt");
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$a = new a();
      if (Build.VERSION.SDK_INT >= 26) {
        AppEngine.getInstance().invokeMethod(paramContext.getClassLoader(), paramContext, "android.content.Context", "startForegroundService", new Class[] { Intent.class }, new Object[] { localIntent });
      } else {
        paramContext.startService(localIntent);
      }
      paramContext.bindService(localIntent, this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderServiceProxy$a, 1);
      this.jdField_a_of_type_Boolean = false;
      return;
    }
    catch (Exception paramContext)
    {
      Log.e("ReaderServiceProxy", paramContext.getMessage());
    }
  }
  
  public static abstract interface ICallback
  {
    public abstract void onCallback(int paramInt, Bundle paramBundle);
  }
  
  class a
    implements ServiceConnection
  {
    a() {}
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      ReaderServiceProxy.a(ReaderServiceProxy.this, IReaderSdkService.Stub.asInterface(paramIBinder));
      if (ReaderServiceProxy.a(ReaderServiceProxy.this) != null) {
        ReaderServiceProxy.this.a();
      }
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      ReaderServiceProxy.a(ReaderServiceProxy.this, null);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\ReaderServiceProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */