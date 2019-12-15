package com.tencent.common.download;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DownloadTaskInfo
  implements Parcelable
{
  public static final Parcelable.Creator<DownloadTaskInfo> CREATOR = new Parcelable.Creator()
  {
    public DownloadTaskInfo a(Parcel paramAnonymousParcel)
    {
      return new DownloadTaskInfo(paramAnonymousParcel);
    }
    
    public DownloadTaskInfo[] a(int paramAnonymousInt)
    {
      return new DownloadTaskInfo[paramAnonymousInt];
    }
  };
  public long mDownloadSize;
  public String mDownloadUrl;
  public int mErrorCode;
  public String mFileName;
  public String mFilePath;
  public long mFileSize;
  public int mFlag;
  public int mProgress;
  public byte mStatus;
  public int mTaskId;
  
  public DownloadTaskInfo() {}
  
  public DownloadTaskInfo(Parcel paramParcel)
  {
    readFromParcel(paramParcel);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean hasFlag(int paramInt)
  {
    return (paramInt & this.mFlag) != 0;
  }
  
  public void readFromParcel(Parcel paramParcel)
  {
    this.mTaskId = paramParcel.readInt();
    this.mDownloadUrl = paramParcel.readString();
    this.mFileName = paramParcel.readString();
    this.mFilePath = paramParcel.readString();
    this.mFileSize = paramParcel.readLong();
    this.mDownloadSize = paramParcel.readLong();
    this.mStatus = paramParcel.readByte();
    this.mErrorCode = paramParcel.readInt();
    this.mFlag = paramParcel.readInt();
    this.mProgress = paramParcel.readInt();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mTaskId);
    paramParcel.writeString(this.mDownloadUrl);
    paramParcel.writeString(this.mFileName);
    paramParcel.writeString(this.mFilePath);
    paramParcel.writeLong(this.mFileSize);
    paramParcel.writeLong(this.mDownloadSize);
    paramParcel.writeByte(this.mStatus);
    paramParcel.writeInt(this.mErrorCode);
    paramParcel.writeInt(this.mFlag);
    paramParcel.writeInt(this.mProgress);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\download\DownloadTaskInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */