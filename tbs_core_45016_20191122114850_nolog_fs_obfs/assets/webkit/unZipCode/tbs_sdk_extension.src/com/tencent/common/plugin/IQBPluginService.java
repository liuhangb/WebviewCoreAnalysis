package com.tencent.common.plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract interface IQBPluginService
  extends IInterface
{
  public abstract int checkLocalPlugin(String paramString, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract boolean checkNeedUpdate(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException;
  
  public abstract int downloadPlugin(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException;
  
  public abstract List<QBPluginItemInfo> getAllPluginList(int paramInt)
    throws RemoteException;
  
  public abstract QBPluginItemInfo getPluginInfo(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract boolean getPluginInfoAsync(String paramString, IGetPluginInfoCallback paramIGetPluginInfoCallback, int paramInt)
    throws RemoteException;
  
  public abstract List<QBPluginItemInfo> getPluginListByPos(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract List<QBPluginItemInfo> getPluginListByType(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract String getPluginPath(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void installPlugin(String paramString, int paramInt1, int paramInt2, IInstallPluginCallback paramIInstallPluginCallback, QBPluginItemInfo paramQBPluginItemInfo, boolean paramBoolean)
    throws RemoteException;
  
  public abstract boolean isNewVersionFileDownloaded(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException;
  
  public abstract boolean needForceUpdate(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
    throws RemoteException;
  
  public abstract boolean refreshPluginListForced(String paramString, int paramInt, IGetPluginInfoCallback paramIGetPluginInfoCallback)
    throws RemoteException;
  
  public abstract boolean refreshPluignListIfNeeded(int paramInt)
    throws RemoteException;
  
  public abstract void requestPluginSystemInit(IQBPluginCallback paramIQBPluginCallback, int paramInt)
    throws RemoteException;
  
  public abstract void setPluginCallback(IQBPluginCallback paramIQBPluginCallback, int paramInt)
    throws RemoteException;
  
  public abstract void setPluginJarZipType(String paramString, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract boolean stopDownloadPlugin(String paramString, boolean paramBoolean, IQBPluginCallback paramIQBPluginCallback, int paramInt)
    throws RemoteException;
  
  public abstract void updatePluginNeesUpdateFlag(String paramString, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IQBPluginService
  {
    public Stub()
    {
      attachInterface(this, "com.tencent.common.plugin.IQBPluginService");
    }
    
    public static IQBPluginService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.common.plugin.IQBPluginService");
      if ((localIInterface != null) && ((localIInterface instanceof IQBPluginService))) {
        return (IQBPluginService)localIInterface;
      }
      return new a(paramIBinder);
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
    
    private static class a
      implements IQBPluginService
    {
      private IBinder a;
      
      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
      
      public int checkLocalPlugin(String paramString, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(13, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramInt1 = localParcel2.readInt();
          return paramInt1;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean checkNeedUpdate(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          boolean bool = true;
          if (paramQBPluginItemInfo != null)
          {
            localParcel1.writeInt(1);
            paramQBPluginItemInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.a.transact(14, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramInt1 = localParcel2.readInt();
          if (paramInt1 == 0) {
            bool = false;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int downloadPlugin(String paramString, int paramInt1, int paramInt2, QBPluginItemInfo paramQBPluginItemInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          if (paramQBPluginItemInfo != null)
          {
            localParcel1.writeInt(1);
            paramQBPluginItemInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.a.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramInt1 = localParcel2.readInt();
          return paramInt1;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public List<QBPluginItemInfo> getAllPluginList(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeInt(paramInt);
          this.a.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createTypedArrayList(QBPluginItemInfo.CREATOR);
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public QBPluginItemInfo getPluginInfo(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          this.a.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0) {
            paramString = (QBPluginItemInfo)QBPluginItemInfo.CREATOR.createFromParcel(localParcel2);
          } else {
            paramString = null;
          }
          return paramString;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean getPluginInfoAsync(String paramString, IGetPluginInfoCallback paramIGetPluginInfoCallback, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
            localParcel1.writeString(paramString);
            if (paramIGetPluginInfoCallback != null)
            {
              paramString = paramIGetPluginInfoCallback.asBinder();
              localParcel1.writeStrongBinder(paramString);
              localParcel1.writeInt(paramInt);
              paramString = this.a;
              boolean bool = false;
              paramString.transact(11, localParcel1, localParcel2, 0);
              localParcel2.readException();
              paramInt = localParcel2.readInt();
              if (paramInt != 0) {
                bool = true;
              }
              return bool;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramString = null;
        }
      }
      
      public List<QBPluginItemInfo> getPluginListByPos(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createTypedArrayList(QBPluginItemInfo.CREATOR);
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public List<QBPluginItemInfo> getPluginListByType(int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createTypedArrayList(QBPluginItemInfo.CREATOR);
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getPluginPath(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          this.a.transact(12, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramString = localParcel2.readString();
          return paramString;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void installPlugin(String paramString, int paramInt1, int paramInt2, IInstallPluginCallback paramIInstallPluginCallback, QBPluginItemInfo paramQBPluginItemInfo, boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
            localParcel1.writeString(paramString);
            localParcel1.writeInt(paramInt1);
            localParcel1.writeInt(paramInt2);
            if (paramIInstallPluginCallback == null) {
              break label141;
            }
            paramString = paramIInstallPluginCallback.asBinder();
            localParcel1.writeStrongBinder(paramString);
            paramInt1 = 1;
            if (paramQBPluginItemInfo != null)
            {
              localParcel1.writeInt(1);
              paramQBPluginItemInfo.writeToParcel(localParcel1, 0);
            }
            else
            {
              localParcel1.writeInt(0);
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          localParcel1.writeInt(paramInt1);
          this.a.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          localParcel2.recycle();
          localParcel1.recycle();
          return;
          label141:
          paramString = null;
          continue;
          if (!paramBoolean) {
            paramInt1 = 0;
          }
        }
      }
      
      public boolean isNewVersionFileDownloaded(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          boolean bool = true;
          if (paramQBPluginItemInfo != null)
          {
            localParcel1.writeInt(1);
            paramQBPluginItemInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.a.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramInt = localParcel2.readInt();
          if (paramInt == 0) {
            bool = false;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean needForceUpdate(String paramString, int paramInt, QBPluginItemInfo paramQBPluginItemInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          boolean bool = true;
          if (paramQBPluginItemInfo != null)
          {
            localParcel1.writeInt(1);
            paramQBPluginItemInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.a.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramInt = localParcel2.readInt();
          if (paramInt == 0) {
            bool = false;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean refreshPluginListForced(String paramString, int paramInt, IGetPluginInfoCallback paramIGetPluginInfoCallback)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
            localParcel1.writeString(paramString);
            localParcel1.writeInt(paramInt);
            if (paramIGetPluginInfoCallback != null)
            {
              paramString = paramIGetPluginInfoCallback.asBinder();
              localParcel1.writeStrongBinder(paramString);
              paramString = this.a;
              boolean bool = false;
              paramString.transact(16, localParcel1, localParcel2, 0);
              localParcel2.readException();
              paramInt = localParcel2.readInt();
              if (paramInt != 0) {
                bool = true;
              }
              return bool;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramString = null;
        }
      }
      
      public boolean refreshPluignListIfNeeded(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeInt(paramInt);
          IBinder localIBinder = this.a;
          boolean bool = false;
          localIBinder.transact(15, localParcel1, localParcel2, 0);
          localParcel2.readException();
          paramInt = localParcel2.readInt();
          if (paramInt != 0) {
            bool = true;
          }
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void requestPluginSystemInit(IQBPluginCallback paramIQBPluginCallback, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
            if (paramIQBPluginCallback != null)
            {
              paramIQBPluginCallback = paramIQBPluginCallback.asBinder();
              localParcel1.writeStrongBinder(paramIQBPluginCallback);
              localParcel1.writeInt(paramInt);
              this.a.transact(1, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramIQBPluginCallback = null;
        }
      }
      
      public void setPluginCallback(IQBPluginCallback paramIQBPluginCallback, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
            if (paramIQBPluginCallback != null)
            {
              paramIQBPluginCallback = paramIQBPluginCallback.asBinder();
              localParcel1.writeStrongBinder(paramIQBPluginCallback);
              localParcel1.writeInt(paramInt);
              this.a.transact(2, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramIQBPluginCallback = null;
        }
      }
      
      public void setPluginJarZipType(String paramString, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public boolean stopDownloadPlugin(String paramString, boolean paramBoolean, IQBPluginCallback paramIQBPluginCallback, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
            localParcel1.writeString(paramString);
            boolean bool = true;
            if (paramBoolean)
            {
              i = 1;
              localParcel1.writeInt(i);
              if (paramIQBPluginCallback == null) {
                break label142;
              }
              paramString = paramIQBPluginCallback.asBinder();
              localParcel1.writeStrongBinder(paramString);
              localParcel1.writeInt(paramInt);
              this.a.transact(4, localParcel1, localParcel2, 0);
              localParcel2.readException();
              paramInt = localParcel2.readInt();
              if (paramInt != 0) {
                paramBoolean = bool;
              } else {
                paramBoolean = false;
              }
              return paramBoolean;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          int i = 0;
          continue;
          label142:
          paramString = null;
        }
      }
      
      public void updatePluginNeesUpdateFlag(String paramString, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginService");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\IQBPluginService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */