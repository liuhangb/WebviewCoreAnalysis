package org.chromium.content.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.Surface;
import org.chromium.base.annotations.MainDex;

@MainDex
public class SurfaceWrapper
  implements Parcelable
{
  public static final Parcelable.Creator<SurfaceWrapper> CREATOR = new Parcelable.Creator()
  {
    public SurfaceWrapper a(Parcel paramAnonymousParcel)
    {
      return new SurfaceWrapper((Surface)Surface.CREATOR.createFromParcel(paramAnonymousParcel));
    }
    
    public SurfaceWrapper[] a(int paramAnonymousInt)
    {
      return new SurfaceWrapper[paramAnonymousInt];
    }
  };
  private final Surface a;
  
  public SurfaceWrapper(Surface paramSurface)
  {
    this.a = paramSurface;
  }
  
  public Surface a()
  {
    return this.a;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    this.a.writeToParcel(paramParcel, 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\common\SurfaceWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */