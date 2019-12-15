package org.chromium;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IsReadyToPayService
  extends IInterface
{
  public abstract void isReadyToPay(IsReadyToPayServiceCallback paramIsReadyToPayServiceCallback)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IsReadyToPayService
  {
    public Stub()
    {
      attachInterface(this, "org.chromium.IsReadyToPayService");
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      if (paramInt1 != 1)
      {
        if (paramInt1 != 1598968902) {
          return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
        }
        paramParcel2.writeString("org.chromium.IsReadyToPayService");
        return true;
      }
      paramParcel1.enforceInterface("org.chromium.IsReadyToPayService");
      isReadyToPay(IsReadyToPayServiceCallback.Stub.a(paramParcel1.readStrongBinder()));
      return true;
    }
    
    private static class Proxy
      implements IsReadyToPayService
    {
      private IBinder a;
      
      public IBinder asBinder()
      {
        return this.a;
      }
      
      public void isReadyToPay(IsReadyToPayServiceCallback paramIsReadyToPayServiceCallback)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        for (;;)
        {
          try
          {
            localParcel.writeInterfaceToken("org.chromium.IsReadyToPayService");
            if (paramIsReadyToPayServiceCallback != null)
            {
              paramIsReadyToPayServiceCallback = paramIsReadyToPayServiceCallback.asBinder();
              localParcel.writeStrongBinder(paramIsReadyToPayServiceCallback);
              this.a.transact(1, localParcel, null, 1);
              return;
            }
          }
          finally
          {
            localParcel.recycle();
          }
          paramIsReadyToPayServiceCallback = null;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\IsReadyToPayService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */