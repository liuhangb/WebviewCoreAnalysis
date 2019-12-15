package com.tencent.smtt.processutil.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.io.IOException;

public final class Status
  extends ProcFile
{
  public static final Parcelable.Creator<Status> CREATOR = new Parcelable.Creator()
  {
    public Status a(Parcel paramAnonymousParcel)
    {
      return new Status(paramAnonymousParcel, null);
    }
    
    public Status[] a(int paramAnonymousInt)
    {
      return new Status[paramAnonymousInt];
    }
  };
  
  private Status(Parcel paramParcel)
  {
    super(paramParcel);
  }
  
  private Status(String paramString)
    throws IOException
  {
    super(paramString);
  }
  
  public static Status a(int paramInt)
    throws IOException
  {
    return new Status(String.format("/proc/%d/status", new Object[] { Integer.valueOf(paramInt) }));
  }
  
  public int a()
  {
    try
    {
      int i = Integer.parseInt(b("Uid").split("\\s+")[0]);
      return i;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return -1;
  }
  
  public String b(String paramString)
  {
    Object localObject2 = this.a.split("\n");
    int j = localObject2.length;
    int i = 0;
    while (i < j)
    {
      Object localObject1 = localObject2[i];
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(":");
      if (((String)localObject1).startsWith(localStringBuilder.toString()))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).append(":");
        return localObject1.split(localObject2.toString())[1].trim();
      }
      i += 1;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\models\Status.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */