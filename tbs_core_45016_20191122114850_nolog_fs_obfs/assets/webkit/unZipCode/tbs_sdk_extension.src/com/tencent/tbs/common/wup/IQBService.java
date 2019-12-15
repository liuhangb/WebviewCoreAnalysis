package com.tencent.tbs.common.wup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.tencent.tbs.common.push.IPushResponseCallBack;
import com.tencent.tbs.common.push.IPushResponseCallBack.Stub;
import java.util.Map;

public abstract interface IQBService
  extends IInterface
{
  public abstract void addUserAction(int paramInt)
    throws RemoteException;
  
  public abstract void deRegisterPushCallBack(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract void recordDebugAction(String paramString)
    throws RemoteException;
  
  public abstract void registerPushCallBack(int paramInt, String paramString, IPushResponseCallBack paramIPushResponseCallBack)
    throws RemoteException;
  
  public abstract void statDebugEvent(String paramString, Map paramMap)
    throws RemoteException;
  
  public abstract void uploadToBeacon(String paramString, Map paramMap)
    throws RemoteException;
  
  public abstract void userBehaviorStatistics(String paramString)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IQBService
  {
    private static final String DESCRIPTOR = "com.tencent.tbs.common.wup.IQBService";
    static final int TRANSACTION_addUserAction = 4;
    static final int TRANSACTION_deRegisterPushCallBack = 7;
    static final int TRANSACTION_recordDebugAction = 1;
    static final int TRANSACTION_registerPushCallBack = 6;
    static final int TRANSACTION_statDebugEvent = 5;
    static final int TRANSACTION_uploadToBeacon = 3;
    static final int TRANSACTION_userBehaviorStatistics = 2;
    
    public Stub()
    {
      attachInterface(this, "com.tencent.tbs.common.wup.IQBService");
    }
    
    public static IQBService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.tbs.common.wup.IQBService");
      if ((localIInterface != null) && ((localIInterface instanceof IQBService))) {
        return (IQBService)localIInterface;
      }
      return new Proxy(paramIBinder);
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
        switch (paramInt1)
        {
        default: 
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        case 7: 
          paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
          deRegisterPushCallBack(paramParcel1.readInt(), paramParcel1.readString());
          paramParcel2.writeNoException();
          return true;
        case 6: 
          paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
          registerPushCallBack(paramParcel1.readInt(), paramParcel1.readString(), IPushResponseCallBack.Stub.asInterface(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
        case 5: 
          paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
          statDebugEvent(paramParcel1.readString(), paramParcel1.readHashMap(getClass().getClassLoader()));
          paramParcel2.writeNoException();
          return true;
        case 4: 
          paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
          addUserAction(paramParcel1.readInt());
          paramParcel2.writeNoException();
          return true;
        case 3: 
          paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
          uploadToBeacon(paramParcel1.readString(), paramParcel1.readHashMap(getClass().getClassLoader()));
          paramParcel2.writeNoException();
          return true;
        case 2: 
          paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
          userBehaviorStatistics(paramParcel1.readString());
          paramParcel2.writeNoException();
          return true;
        }
        paramParcel1.enforceInterface("com.tencent.tbs.common.wup.IQBService");
        recordDebugAction(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel2.writeString("com.tencent.tbs.common.wup.IQBService");
      return true;
    }
    
    private static class Proxy
      implements IQBService
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public void addUserAction(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public void deRegisterPushCallBack(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
          localParcel1.writeInt(paramInt);
          localParcel1.writeString(paramString);
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "com.tencent.tbs.common.wup.IQBService";
      }
      
      public void recordDebugAction(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void registerPushCallBack(int paramInt, String paramString, IPushResponseCallBack paramIPushResponseCallBack)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
            localParcel1.writeInt(paramInt);
            localParcel1.writeString(paramString);
            if (paramIPushResponseCallBack != null)
            {
              paramString = paramIPushResponseCallBack.asBinder();
              localParcel1.writeStrongBinder(paramString);
              this.mRemote.transact(6, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
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
      
      public void statDebugEvent(String paramString, Map paramMap)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
          localParcel1.writeString(paramString);
          localParcel1.writeMap(paramMap);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void uploadToBeacon(String paramString, Map paramMap)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
          localParcel1.writeString(paramString);
          localParcel1.writeMap(paramMap);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public void userBehaviorStatistics(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.wup.IQBService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wup\IQBService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */