package com.tencent.tbs.common.push;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IPushResponseCallBack
  extends IInterface
{
  public abstract void onReceiveData(PushResponse paramPushResponse)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IPushResponseCallBack
  {
    private static final String DESCRIPTOR = "com.tencent.tbs.common.push.IPushResponseCallBack";
    static final int TRANSACTION_onReceiveData = 1;
    
    public Stub()
    {
      attachInterface(this, "com.tencent.tbs.common.push.IPushResponseCallBack");
    }
    
    public static IPushResponseCallBack asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.tencent.tbs.common.push.IPushResponseCallBack");
      if ((localIInterface != null) && ((localIInterface instanceof IPushResponseCallBack))) {
        return (IPushResponseCallBack)localIInterface;
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
      if (paramInt1 != 1)
      {
        if (paramInt1 != 1598968902) {
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        }
        paramParcel2.writeString("com.tencent.tbs.common.push.IPushResponseCallBack");
        return true;
      }
      paramParcel1.enforceInterface("com.tencent.tbs.common.push.IPushResponseCallBack");
      if (paramParcel1.readInt() != 0) {
        paramParcel1 = (PushResponse)PushResponse.CREATOR.createFromParcel(paramParcel1);
      } else {
        paramParcel1 = null;
      }
      onReceiveData(paramParcel1);
      paramParcel2.writeNoException();
      if (paramParcel1 != null)
      {
        paramParcel2.writeInt(1);
        paramParcel1.writeToParcel(paramParcel2, 1);
        return true;
      }
      paramParcel2.writeInt(0);
      return true;
    }
    
    private static class Proxy
      implements IPushResponseCallBack
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor()
      {
        return "com.tencent.tbs.common.push.IPushResponseCallBack";
      }
      
      public void onReceiveData(PushResponse paramPushResponse)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.tencent.tbs.common.push.IPushResponseCallBack");
          if (paramPushResponse != null)
          {
            localParcel1.writeInt(1);
            paramPushResponse.writeToParcel(localParcel1, 0);
          }
          else
          {
            localParcel1.writeInt(0);
          }
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0) {
            paramPushResponse.readFromParcel(localParcel2);
          }
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\push\IPushResponseCallBack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */