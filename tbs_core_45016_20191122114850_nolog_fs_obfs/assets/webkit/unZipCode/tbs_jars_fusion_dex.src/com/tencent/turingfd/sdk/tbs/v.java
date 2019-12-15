package com.tencent.turingfd.sdk.tbs;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class v
  implements q
{
  public IBinder a;
  
  public v(IBinder paramIBinder)
  {
    this.a = paramIBinder;
  }
  
  public IBinder a(String paramString)
    throws RemoteException
  {
    Parcel localParcel1 = Parcel.obtain();
    Parcel localParcel2 = Parcel.obtain();
    localParcel1.writeInterfaceToken("android.os.IServiceManager");
    localParcel1.writeString(paramString);
    this.a.transact(1, localParcel1, localParcel2, 0);
    paramString = localParcel2.readStrongBinder();
    localParcel2.recycle();
    localParcel1.recycle();
    return paramString;
  }
  
  public IBinder asBinder()
  {
    return this.a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\v.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */