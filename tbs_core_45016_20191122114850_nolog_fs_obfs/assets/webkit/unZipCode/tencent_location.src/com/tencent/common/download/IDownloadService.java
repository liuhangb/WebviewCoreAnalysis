package com.tencent.common.download;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IDownloadService
  extends IInterface
{
  public abstract void addTaskObserver(DownloadTaskObserver paramDownloadTaskObserver)
    throws RemoteException;
  
  public abstract void excuteFlowCtrlTasks(byte paramByte)
    throws RemoteException;
  
  public abstract String getDownloadTaskFileName(String paramString)
    throws RemoteException;
  
  public abstract String getDownloadTaskPath(String paramString)
    throws RemoteException;
  
  public abstract DownloadTaskInfo getTaskFromDatabaseById(int paramInt)
    throws RemoteException;
  
  public abstract DownloadTaskInfo getTaskFromDatabaseByUrl(String paramString)
    throws RemoteException;
  
  public abstract boolean isCompletedTask(String paramString)
    throws RemoteException;
  
  public abstract boolean isOnGoing(String paramString)
    throws RemoteException;
  
  public abstract void removeTaskObserver(DownloadTaskObserver paramDownloadTaskObserver)
    throws RemoteException;
  
  public abstract boolean startDownloadTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, long paramLong, boolean paramBoolean)
    throws RemoteException;
  
  public abstract boolean startDownloadTaskWithExtFlag(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, long paramLong1, boolean paramBoolean, long paramLong2)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IDownloadService
  {
    public Stub()
    {
      attachInterface(this, "com.tencent.common.download.IDownloadService");
    }
    
    public static IDownloadService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.common.download.IDownloadService");
      if ((localIInterface != null) && ((localIInterface instanceof IDownloadService))) {
        return (IDownloadService)localIInterface;
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
      implements IDownloadService
    {
      private IBinder a;
      
      a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      public void addTaskObserver(DownloadTaskObserver paramDownloadTaskObserver)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
            if (paramDownloadTaskObserver != null)
            {
              paramDownloadTaskObserver = paramDownloadTaskObserver.asBinder();
              localParcel1.writeStrongBinder(paramDownloadTaskObserver);
              this.a.transact(3, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramDownloadTaskObserver = null;
        }
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
      
      public void excuteFlowCtrlTasks(byte paramByte)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeByte(paramByte);
          this.a.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public String getDownloadTaskFileName(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeString(paramString);
          this.a.transact(8, localParcel1, localParcel2, 0);
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
      
      public String getDownloadTaskPath(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeString(paramString);
          this.a.transact(7, localParcel1, localParcel2, 0);
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
      
      public DownloadTaskInfo getTaskFromDatabaseById(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeInt(paramInt);
          this.a.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          DownloadTaskInfo localDownloadTaskInfo;
          if (localParcel2.readInt() != 0) {
            localDownloadTaskInfo = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(localParcel2);
          } else {
            localDownloadTaskInfo = null;
          }
          return localDownloadTaskInfo;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public DownloadTaskInfo getTaskFromDatabaseByUrl(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeString(paramString);
          this.a.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0) {
            paramString = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(localParcel2);
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
      
      public boolean isCompletedTask(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeString(paramString);
          paramString = this.a;
          boolean bool = false;
          paramString.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
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
      
      public boolean isOnGoing(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
          localParcel1.writeString(paramString);
          paramString = this.a;
          boolean bool = false;
          paramString.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0) {
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
      
      public void removeTaskObserver(DownloadTaskObserver paramDownloadTaskObserver)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
            if (paramDownloadTaskObserver != null)
            {
              paramDownloadTaskObserver = paramDownloadTaskObserver.asBinder();
              localParcel1.writeStrongBinder(paramDownloadTaskObserver);
              this.a.transact(4, localParcel1, localParcel2, 0);
              localParcel2.readException();
              return;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          paramDownloadTaskObserver = null;
        }
      }
      
      public boolean startDownloadTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, long paramLong, boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
            localParcel1.writeString(paramString1);
            localParcel1.writeString(paramString2);
            localParcel1.writeString(paramString3);
            localParcel1.writeString(paramString4);
            localParcel1.writeString(paramString5);
            localParcel1.writeInt(paramInt);
            localParcel1.writeLong(paramLong);
            boolean bool = true;
            if (paramBoolean)
            {
              paramInt = 1;
              localParcel1.writeInt(paramInt);
              this.a.transact(5, localParcel1, localParcel2, 0);
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
          paramInt = 0;
        }
      }
      
      public boolean startDownloadTaskWithExtFlag(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, long paramLong1, boolean paramBoolean, long paramLong2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.tencent.common.download.IDownloadService");
            localParcel1.writeString(paramString1);
            localParcel1.writeString(paramString2);
            localParcel1.writeString(paramString3);
            localParcel1.writeString(paramString4);
            localParcel1.writeString(paramString5);
            localParcel1.writeInt(paramInt);
            localParcel1.writeLong(paramLong1);
            boolean bool = true;
            if (paramBoolean)
            {
              paramInt = 1;
              localParcel1.writeInt(paramInt);
              localParcel1.writeLong(paramLong2);
              this.a.transact(6, localParcel1, localParcel2, 0);
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
          paramInt = 0;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\download\IDownloadService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */