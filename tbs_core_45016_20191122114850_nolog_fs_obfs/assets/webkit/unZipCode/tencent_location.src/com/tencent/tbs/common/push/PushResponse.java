package com.tencent.tbs.common.push;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PushResponse
  implements Parcelable
{
  public static final Parcelable.Creator<PushResponse> CREATOR = new Parcelable.Creator()
  {
    public PushResponse createFromParcel(Parcel paramAnonymousParcel)
    {
      return new PushResponse(paramAnonymousParcel);
    }
    
    public PushResponse[] newArray(int paramAnonymousInt)
    {
      return new PushResponse[paramAnonymousInt];
    }
  };
  public byte[] data;
  
  public PushResponse(Parcel paramParcel)
  {
    readFromParcel(paramParcel);
  }
  
  public PushResponse(byte[] paramArrayOfByte)
  {
    this.data = paramArrayOfByte;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void readFromParcel(Parcel paramParcel) {}
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeByteArray(this.data);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\push\PushResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */