package org.chromium;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IsReadyToPayServiceCallback
  extends IInterface
{
  public abstract void handleIsReadyToPay(boolean paramBoolean)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IsReadyToPayServiceCallback
  {
    public Stub()
    {
      attachInterface(this, "org.chromium.IsReadyToPayServiceCallback");
    }
    
    public static IsReadyToPayServiceCallback a(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("org.chromium.IsReadyToPayServiceCallback");
      if ((localIInterface != null) && ((localIInterface instanceof IsReadyToPayServiceCallback))) {
        return (IsReadyToPayServiceCallback)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      if (paramInt1 != 1)
      {
        if (paramInt1 != 1598968902) {
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        }
        paramParcel2.writeString("org.chromium.IsReadyToPayServiceCallback");
        return true;
      }
      paramParcel1.enforceInterface("org.chromium.IsReadyToPayServiceCallback");
      boolean bool;
      if (paramParcel1.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      handleIsReadyToPay(bool);
      return true;
    }
    
    private static class Proxy
      implements IsReadyToPayServiceCallback
    {
      private IBinder a;
      
      Proxy(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
      
      public void handleIsReadyToPay(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("org.chromium.IsReadyToPayServiceCallback");
            if (paramBoolean)
            {
              i = 1;
              localParcel.writeInt(i);
              this.a.transact(1, localParcel, null, 1);
              return;
            }
          }
          finally
          {
            localParcel.recycle();
          }
          int i = 0;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\IsReadyToPayServiceCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */