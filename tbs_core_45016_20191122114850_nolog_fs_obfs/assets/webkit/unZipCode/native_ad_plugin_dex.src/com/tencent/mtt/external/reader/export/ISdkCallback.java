package com.tencent.mtt.external.reader.export;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface ISdkCallback
  extends IInterface
{
  public abstract void onSdkCallback(int paramInt, Bundle paramBundle)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements ISdkCallback
  {
    public Stub()
    {
      attachInterface(this, "com.tencent.mtt.external.reader.export.ISdkCallback");
    }
    
    public static ISdkCallback asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.mtt.external.reader.export.ISdkCallback");
      if ((localIInterface != null) && ((localIInterface instanceof ISdkCallback))) {
        return (ISdkCallback)localIInterface;
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
      if (paramInt1 != 1)
      {
        if (paramInt1 != 1598968902) {
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        }
        paramParcel2.writeString("com.tencent.mtt.external.reader.export.ISdkCallback");
        return true;
      }
      paramParcel1.enforceInterface("com.tencent.mtt.external.reader.export.ISdkCallback");
      paramInt1 = paramParcel1.readInt();
      if (paramParcel1.readInt() != 0) {
        paramParcel1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
      } else {
        paramParcel1 = null;
      }
      onSdkCallback(paramInt1, paramParcel1);
      paramParcel2.writeNoException();
      return true;
    }
    
    private static class a
      implements ISdkCallback
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
      
      public void onSdkCallback(int paramInt, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.mtt.external.reader.export.ISdkCallback");
          localParcel1.writeInt(paramInt);
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
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
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\export\ISdkCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */