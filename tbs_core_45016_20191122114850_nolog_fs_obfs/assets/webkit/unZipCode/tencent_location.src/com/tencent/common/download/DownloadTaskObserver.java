package com.tencent.common.download;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface DownloadTaskObserver
  extends IInterface
{
  public abstract void onTaskCompleted(DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;
  
  public abstract void onTaskCreated(DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;
  
  public abstract void onTaskExtEvent(DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;
  
  public abstract void onTaskFailed(DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;
  
  public abstract void onTaskProgress(DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;
  
  public abstract void onTaskStarted(DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements DownloadTaskObserver
  {
    public Stub()
    {
      attachInterface(this, "com.tencent.common.download.DownloadTaskObserver");
    }
    
    public static DownloadTaskObserver asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.common.download.DownloadTaskObserver");
      if ((localIInterface != null) && ((localIInterface instanceof DownloadTaskObserver))) {
        return (DownloadTaskObserver)localIInterface;
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
        Object localObject2 = null;
        Object localObject3 = null;
        Object localObject4 = null;
        Object localObject5 = null;
        Object localObject6 = null;
        Object localObject1 = null;
        switch (paramInt1)
        {
        default: 
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        case 6: 
          paramParcel1.enforceInterface("com.tencent.common.download.DownloadTaskObserver");
          if (paramParcel1.readInt() != 0) {
            localObject1 = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1);
          }
          onTaskExtEvent((DownloadTaskInfo)localObject1);
          paramParcel2.writeNoException();
          return true;
        case 5: 
          paramParcel1.enforceInterface("com.tencent.common.download.DownloadTaskObserver");
          localObject1 = localObject2;
          if (paramParcel1.readInt() != 0) {
            localObject1 = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1);
          }
          onTaskFailed((DownloadTaskInfo)localObject1);
          paramParcel2.writeNoException();
          return true;
        case 4: 
          paramParcel1.enforceInterface("com.tencent.common.download.DownloadTaskObserver");
          localObject1 = localObject3;
          if (paramParcel1.readInt() != 0) {
            localObject1 = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1);
          }
          onTaskCompleted((DownloadTaskInfo)localObject1);
          paramParcel2.writeNoException();
          return true;
        case 3: 
          paramParcel1.enforceInterface("com.tencent.common.download.DownloadTaskObserver");
          localObject1 = localObject4;
          if (paramParcel1.readInt() != 0) {
            localObject1 = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1);
          }
          onTaskProgress((DownloadTaskInfo)localObject1);
          paramParcel2.writeNoException();
          return true;
        case 2: 
          paramParcel1.enforceInterface("com.tencent.common.download.DownloadTaskObserver");
          localObject1 = localObject5;
          if (paramParcel1.readInt() != 0) {
            localObject1 = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1);
          }
          onTaskStarted((DownloadTaskInfo)localObject1);
          paramParcel2.writeNoException();
          return true;
        }
        paramParcel1.enforceInterface("com.tencent.common.download.DownloadTaskObserver");
        localObject1 = localObject6;
        if (paramParcel1.readInt() != 0) {
          localObject1 = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1);
        }
        onTaskCreated((DownloadTaskInfo)localObject1);
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel2.writeString("com.tencent.common.download.DownloadTaskObserver");
      return true;
    }
    
    private static class a
      implements DownloadTaskObserver
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
      
      public void onTaskCompleted(DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.DownloadTaskObserver");
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
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
      
      public void onTaskCreated(DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.DownloadTaskObserver");
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
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
      
      public void onTaskExtEvent(DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.DownloadTaskObserver");
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
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
      
      public void onTaskFailed(DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.DownloadTaskObserver");
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
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
      
      public void onTaskProgress(DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.DownloadTaskObserver");
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
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
      
      public void onTaskStarted(DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.DownloadTaskObserver");
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
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
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\download\DownloadTaskObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */