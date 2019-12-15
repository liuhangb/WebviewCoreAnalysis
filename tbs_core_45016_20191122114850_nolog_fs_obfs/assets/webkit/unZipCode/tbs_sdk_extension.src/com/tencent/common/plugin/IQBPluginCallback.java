package com.tencent.common.plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IQBPluginCallback
  extends IInterface
{
  public abstract void onGetPluginListFailed(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void onGetPluginListSucc(String paramString, int paramInt)
    throws RemoteException;
  
  public abstract void onPluginDownloadCreated(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onPluginDownloadFailed(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
  
  public abstract void onPluginDownloadProgress(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;
  
  public abstract void onPluginDownloadStarted(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;
  
  public abstract void onPluginDownloadSuccessed(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    throws RemoteException;
  
  public abstract void onPluginInstallFailed(String paramString, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onPluginInstallSuccessed(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void onPluginSystemInit(boolean paramBoolean, int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IQBPluginCallback
  {
    public Stub()
    {
      attachInterface(this, "com.tencent.common.plugin.IQBPluginCallback");
    }
    
    public static IQBPluginCallback asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.common.plugin.IQBPluginCallback");
      if ((localIInterface != null) && ((localIInterface instanceof IQBPluginCallback))) {
        return (IQBPluginCallback)localIInterface;
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
      if (paramInt1 != 1598968902)
      {
        boolean bool = false;
        String str1;
        Object localObject;
        switch (paramInt1)
        {
        default: 
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        case 10: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          if (paramParcel1.readInt() != 0) {
            bool = true;
          }
          onPluginSystemInit(bool, paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 9: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          onPluginInstallFailed(paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 8: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          str1 = paramParcel1.readString();
          if (paramParcel1.readInt() != 0) {
            localObject = (QBPluginItemInfo)QBPluginItemInfo.CREATOR.createFromParcel(paramParcel1);
          } else {
            localObject = null;
          }
          onPluginInstallSuccessed(str1, (QBPluginItemInfo)localObject, paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 7: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          onPluginDownloadFailed(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 6: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          localObject = paramParcel1.readString();
          str1 = paramParcel1.readString();
          String str2 = paramParcel1.readString();
          String str3 = paramParcel1.readString();
          paramInt1 = paramParcel1.readInt();
          paramInt2 = paramParcel1.readInt();
          int i = paramParcel1.readInt();
          int j = paramParcel1.readInt();
          if (paramParcel1.readInt() != 0) {
            bool = true;
          } else {
            bool = false;
          }
          onPluginDownloadSuccessed((String)localObject, str1, str2, str3, paramInt1, paramInt2, i, j, bool);
          paramParcel2.writeNoException();
          return true;
        case 5: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          onPluginDownloadProgress(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 4: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          onPluginDownloadStarted(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 3: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          onPluginDownloadCreated(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readInt(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 2: 
          paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
          onGetPluginListFailed(paramParcel1.readString(), paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        }
        paramParcel1.enforceInterface("com.tencent.common.plugin.IQBPluginCallback");
        onGetPluginListSucc(paramParcel1.readString(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel2.writeString("com.tencent.common.plugin.IQBPluginCallback");
      return true;
    }
    
    private static class a
      implements IQBPluginCallback
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
      
      public void onGetPluginListFailed(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          this.a.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onGetPluginListSucc(String paramString, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          this.a.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginDownloadCreated(String paramString1, String paramString2, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginDownloadFailed(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          this.a.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginDownloadProgress(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeInt(paramInt4);
          this.a.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginDownloadStarted(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeInt(paramInt4);
          this.a.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginDownloadSuccessed(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
            localParcel1.writeString(paramString1);
            localParcel1.writeString(paramString2);
            localParcel1.writeString(paramString3);
            localParcel1.writeString(paramString4);
            localParcel1.writeInt(paramInt1);
            localParcel1.writeInt(paramInt2);
            localParcel1.writeInt(paramInt3);
            localParcel1.writeInt(paramInt4);
            if (paramBoolean)
            {
              paramInt1 = 1;
              localParcel1.writeInt(paramInt1);
              this.a.transact(6, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramInt1 = 0;
        }
      }
      
      public void onPluginInstallFailed(String paramString, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginInstallSuccessed(String paramString, QBPluginItemInfo paramQBPluginItemInfo, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
          localParcel1.writeString(paramString);
          if (paramQBPluginItemInfo != null)
          {
            localParcel1.writeInt(1);
            paramQBPluginItemInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          this.a.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void onPluginSystemInit(boolean paramBoolean, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.plugin.IQBPluginCallback");
            if (paramBoolean)
            {
              i = 1;
              localParcel1.writeInt(i);
              localParcel1.writeInt(paramInt);
              this.a.transact(10, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          int i = 0;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\IQBPluginCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */