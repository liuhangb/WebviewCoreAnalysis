package org.chromium.content.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.view.Surface;
import org.chromium.base.UnguessableToken;

public abstract interface IGpuProcessCallback
  extends IInterface
{
  public abstract void forwardSurfaceForSurfaceRequest(UnguessableToken paramUnguessableToken, Surface paramSurface)
    throws RemoteException;
  
  public abstract SurfaceWrapper getViewSurface(int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements IGpuProcessCallback
  {
    public Stub()
    {
      attachInterface(this, "org.chromium.content.common.IGpuProcessCallback");
    }
    
    public static IGpuProcessCallback asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("org.chromium.content.common.IGpuProcessCallback");
      if ((localIInterface != null) && ((localIInterface instanceof IGpuProcessCallback))) {
        return (IGpuProcessCallback)localIInterface;
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
        case 2: 
          paramParcel1.enforceInterface("org.chromium.content.common.IGpuProcessCallback");
          paramParcel1 = getViewSurface(paramParcel1.readInt());
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
        paramParcel1.enforceInterface("org.chromium.content.common.IGpuProcessCallback");
        paramInt1 = paramParcel1.readInt();
        Surface localSurface = null;
        if (paramInt1 != 0) {
          paramParcel2 = (UnguessableToken)UnguessableToken.CREATOR.createFromParcel(paramParcel1);
        } else {
          paramParcel2 = null;
        }
        if (paramParcel1.readInt() != 0) {
          localSurface = (Surface)Surface.CREATOR.createFromParcel(paramParcel1);
        }
        forwardSurfaceForSurfaceRequest(paramParcel2, localSurface);
        return true;
      }
      paramParcel2.writeString("org.chromium.content.common.IGpuProcessCallback");
      return true;
    }
    
    private static class Proxy
      implements IGpuProcessCallback
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
      
      public void forwardSurfaceForSurfaceRequest(UnguessableToken paramUnguessableToken, Surface paramSurface)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("org.chromium.content.common.IGpuProcessCallback");
          if (paramUnguessableToken != null)
          {
            localParcel.writeInt(1);
            paramUnguessableToken.writeToParcel(localParcel, 0);
          }
          else
          {
            localParcel.writeInt(0);
          }
          if (paramSurface != null)
          {
            localParcel.writeInt(1);
            paramSurface.writeToParcel(localParcel, 0);
          }
          else
          {
            localParcel.writeInt(0);
          }
          this.a.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public SurfaceWrapper getViewSurface(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("org.chromium.content.common.IGpuProcessCallback");
          localParcel1.writeInt(paramInt);
          this.a.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          SurfaceWrapper localSurfaceWrapper;
          if (localParcel2.readInt() != 0) {
            localSurfaceWrapper = (SurfaceWrapper)SurfaceWrapper.CREATOR.createFromParcel(localParcel2);
          } else {
            localSurfaceWrapper = null;
          }
          return localSurfaceWrapper;
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\common\IGpuProcessCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */