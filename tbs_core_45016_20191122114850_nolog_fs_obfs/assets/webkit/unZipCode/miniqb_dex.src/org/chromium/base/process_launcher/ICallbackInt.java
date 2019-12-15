package org.chromium.base.process_launcher;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ICallbackInt
  extends IInterface
{
  public abstract void call(int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements ICallbackInt
  {
    public Stub()
    {
      attachInterface(this, "org.chromium.base.process_launcher.ICallbackInt");
    }
    
    public static ICallbackInt asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("org.chromium.base.process_launcher.ICallbackInt");
      if ((localIInterface != null) && ((localIInterface instanceof ICallbackInt))) {
        return (ICallbackInt)localIInterface;
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
        paramParcel2.writeString("org.chromium.base.process_launcher.ICallbackInt");
        return true;
      }
      paramParcel1.enforceInterface("org.chromium.base.process_launcher.ICallbackInt");
      call(paramParcel1.readInt());
      return true;
    }
    
    private static class Proxy
      implements ICallbackInt
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
      
      public void call(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("org.chromium.base.process_launcher.ICallbackInt");
          localParcel.writeInt(paramInt);
          this.a.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public String getInterfaceDescriptor()
      {
        return "org.chromium.base.process_launcher.ICallbackInt";
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\ICallbackInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */