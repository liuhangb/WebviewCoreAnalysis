package com.tencent.common.plugin;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class QBPluginItemInfo
  implements Parcelable
{
  public static final String CONTENT_IMG = "img";
  public static final String CONTENT_KEY = "plugin_content";
  public static final String CONTENT_TXT = "txt";
  public static final Parcelable.Creator<QBPluginItemInfo> CREATOR = new Parcelable.Creator()
  {
    public QBPluginItemInfo a(Parcel paramAnonymousParcel)
    {
      return new QBPluginItemInfo(paramAnonymousParcel);
    }
    
    public QBPluginItemInfo[] a(int paramAnonymousInt)
    {
      return new QBPluginItemInfo[paramAnonymousInt];
    }
  };
  public static final int LOCATION_MENU = 1;
  public static final int LOCATION_STORE = 0;
  public static final String PKGN_KEY = "plugin_package_name";
  public static final String URL_KEY = "plugin_url";
  public int isNeedUpdate = 0;
  public String mAntiHijackUrl = "";
  public String mDetailSumary = "";
  public String mDownloadDir = "";
  public String mDownloadFileName = "";
  public String mExt = "";
  public String mIconUrl = "";
  public int mInfoFrom = 0;
  public String mInstallDir = "";
  public String mInstallVersion = "";
  public int mIsInstall;
  public int mIsZipFileUpdate = 0;
  public int mLocation = 0;
  public String mMd5 = "";
  public int mOrder;
  public String mPackageName = "";
  public String mPackageSize = "";
  public String mPluginCompatiID = "";
  public int mPluginType;
  public String mSignature = "";
  public String mTitle = "";
  public String mUnzipDir = "";
  public int mUpdateType;
  public String mUrl = "";
  public String mVersion = "";
  public int mZipJarPluginType = 0;
  
  public QBPluginItemInfo() {}
  
  public QBPluginItemInfo(Parcel paramParcel)
  {
    this.mTitle = paramParcel.readString();
    this.mUrl = paramParcel.readString();
    this.mIconUrl = paramParcel.readString();
    this.mPackageName = paramParcel.readString();
    this.mPluginType = paramParcel.readInt();
    this.mVersion = paramParcel.readString();
    this.mPackageSize = paramParcel.readString();
    this.mIsInstall = paramParcel.readInt();
    this.mUpdateType = paramParcel.readInt();
    this.mOrder = paramParcel.readInt();
    this.mDetailSumary = paramParcel.readString();
    this.mExt = paramParcel.readString();
    this.mSignature = paramParcel.readString();
    this.mLocation = paramParcel.readInt();
    this.mDownloadDir = paramParcel.readString();
    this.mInstallDir = paramParcel.readString();
    this.mUnzipDir = paramParcel.readString();
    this.mIsZipFileUpdate = paramParcel.readInt();
    this.mPluginCompatiID = paramParcel.readString();
    this.mMd5 = paramParcel.readString();
    this.mZipJarPluginType = paramParcel.readInt();
    this.mDownloadFileName = paramParcel.readString();
    this.mInstallVersion = paramParcel.readString();
    this.mAntiHijackUrl = paramParcel.readString();
    this.mInfoFrom = paramParcel.readInt();
    this.isNeedUpdate = paramParcel.readInt();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mTitle);
    paramParcel.writeString(this.mUrl);
    paramParcel.writeString(this.mIconUrl);
    paramParcel.writeString(this.mPackageName);
    paramParcel.writeInt(this.mPluginType);
    paramParcel.writeString(this.mVersion);
    paramParcel.writeString(this.mPackageSize);
    paramParcel.writeInt(this.mIsInstall);
    paramParcel.writeInt(this.mUpdateType);
    paramParcel.writeInt(this.mOrder);
    paramParcel.writeString(this.mDetailSumary);
    paramParcel.writeString(this.mExt);
    paramParcel.writeString(this.mSignature);
    paramParcel.writeInt(this.mLocation);
    paramParcel.writeString(this.mDownloadDir);
    paramParcel.writeString(this.mInstallDir);
    paramParcel.writeString(this.mUnzipDir);
    paramParcel.writeInt(this.mIsZipFileUpdate);
    paramParcel.writeString(this.mPluginCompatiID);
    paramParcel.writeString(this.mMd5);
    paramParcel.writeInt(this.mZipJarPluginType);
    paramParcel.writeString(this.mDownloadFileName);
    paramParcel.writeString(this.mInstallVersion);
    paramParcel.writeString(this.mAntiHijackUrl);
    paramParcel.writeInt(this.mInfoFrom);
    paramParcel.writeInt(this.isNeedUpdate);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginItemInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */