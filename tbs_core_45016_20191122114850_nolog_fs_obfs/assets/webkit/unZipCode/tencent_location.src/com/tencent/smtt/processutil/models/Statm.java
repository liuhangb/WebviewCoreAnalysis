package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class Statm
  extends ProcFile
{
  public static final Parcelable.Creator<Statm> CREATOR = new Parcelable.Creator()
  {
    public Statm a(Parcel paramAnonymousParcel)
    {
      return new Statm(paramAnonymousParcel, null);
    }
    
    public Statm[] a(int paramAnonymousInt)
    {
      return new Statm[paramAnonymousInt];
    }
  };
  public final String[] a;
  
  private Statm(Parcel paramParcel)
  {
    super(paramParcel);
    this.a = paramParcel.createStringArray();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeStringArray(this.a);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\Statm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */