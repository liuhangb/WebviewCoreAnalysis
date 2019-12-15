package org.chromium.base.process_launcher;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.chromium.base.annotations.MainDex;
import org.chromium.base.annotations.UsedByReflection;

@MainDex
@UsedByReflection("child_process_launcher_helper_android.cc")
public final class FileDescriptorInfo
  implements Parcelable
{
  public static final Parcelable.Creator<FileDescriptorInfo> CREATOR = new Parcelable.Creator()
  {
    public FileDescriptorInfo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FileDescriptorInfo(paramAnonymousParcel);
    }
    
    public FileDescriptorInfo[] newArray(int paramAnonymousInt)
    {
      return new FileDescriptorInfo[paramAnonymousInt];
    }
  };
  public final ParcelFileDescriptor fd;
  public final int id;
  public final long offset;
  public final long size;
  
  public FileDescriptorInfo(int paramInt, ParcelFileDescriptor paramParcelFileDescriptor, long paramLong1, long paramLong2)
  {
    this.id = paramInt;
    this.fd = paramParcelFileDescriptor;
    this.offset = paramLong1;
    this.size = paramLong2;
  }
  
  FileDescriptorInfo(Parcel paramParcel)
  {
    this.id = paramParcel.readInt();
    this.fd = ((ParcelFileDescriptor)paramParcel.readParcelable(ParcelFileDescriptor.class.getClassLoader()));
    this.offset = paramParcel.readLong();
    this.size = paramParcel.readLong();
  }
  
  public int describeContents()
  {
    return 1;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.id);
    paramParcel.writeParcelable(this.fd, 1);
    paramParcel.writeLong(this.offset);
    paramParcel.writeLong(this.size);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\FileDescriptorInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */