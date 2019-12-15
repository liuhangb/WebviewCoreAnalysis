package org.chromium.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.chromium.base.annotations.CalledByNative;

public class UnguessableToken
  implements Parcelable
{
  public static final Parcelable.Creator<UnguessableToken> CREATOR = new Parcelable.Creator()
  {
    public UnguessableToken createFromParcel(Parcel paramAnonymousParcel)
    {
      long l1 = paramAnonymousParcel.readLong();
      long l2 = paramAnonymousParcel.readLong();
      if ((l1 != 0L) && (l2 != 0L)) {
        return new UnguessableToken(l1, l2, null);
      }
      return null;
    }
    
    public UnguessableToken[] newArray(int paramAnonymousInt)
    {
      return new UnguessableToken[paramAnonymousInt];
    }
  };
  private final long a;
  private final long b;
  
  private UnguessableToken(long paramLong1, long paramLong2)
  {
    this.a = paramLong1;
    this.b = paramLong2;
  }
  
  @CalledByNative
  private static UnguessableToken create(long paramLong1, long paramLong2)
  {
    return new UnguessableToken(paramLong1, paramLong2);
  }
  
  @CalledByNative
  private UnguessableToken parcelAndUnparcelForTesting()
  {
    Parcel localParcel = Parcel.obtain();
    writeToParcel(localParcel, 0);
    localParcel.setDataPosition(0);
    UnguessableToken localUnguessableToken = (UnguessableToken)CREATOR.createFromParcel(localParcel);
    localParcel.recycle();
    return localUnguessableToken;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  @CalledByNative
  public long getHighForSerialization()
  {
    return this.a;
  }
  
  @CalledByNative
  public long getLowForSerialization()
  {
    return this.b;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.a);
    paramParcel.writeLong(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\UnguessableToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */