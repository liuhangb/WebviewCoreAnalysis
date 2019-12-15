package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.io.IOException;

public class AndroidProcess
  implements Parcelable
{
  public static final Parcelable.Creator<AndroidProcess> CREATOR = new Parcelable.Creator()
  {
    public AndroidProcess a(Parcel paramAnonymousParcel)
    {
      return new AndroidProcess(paramAnonymousParcel);
    }
    
    public AndroidProcess[] a(int paramAnonymousInt)
    {
      return new AndroidProcess[paramAnonymousInt];
    }
  };
  public final String a;
  public final int b;
  
  public AndroidProcess(int paramInt)
    throws IOException
  {
    this.b = paramInt;
    this.a = a(paramInt);
  }
  
  protected AndroidProcess(Parcel paramParcel)
  {
    this.a = paramParcel.readString();
    this.b = paramParcel.readInt();
  }
  
  static String a(int paramInt)
    throws IOException
  {
    try
    {
      str = ProcFile.a(String.format("/proc/%d/cmdline", new Object[] { Integer.valueOf(paramInt) })).trim();
    }
    catch (IOException localIOException)
    {
      String str;
      for (;;) {}
    }
    str = null;
    if (TextUtils.isEmpty(str)) {
      return Stat.a(paramInt).a();
    }
    return str;
  }
  
  public Cgroup a()
    throws IOException
  {
    return Cgroup.a(this.b);
  }
  
  public Stat a()
    throws IOException
  {
    return Stat.a(this.b);
  }
  
  public Status a()
    throws IOException
  {
    return Status.a(this.b);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.a);
    paramParcel.writeInt(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\AndroidProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */