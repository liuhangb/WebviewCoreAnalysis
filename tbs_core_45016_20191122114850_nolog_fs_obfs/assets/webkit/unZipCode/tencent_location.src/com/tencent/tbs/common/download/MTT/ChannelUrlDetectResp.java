package com.tencent.tbs.common.download.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class ChannelUrlDetectResp
  extends JceStruct
{
  static ArrayList<String> cache_vBackupUrl;
  public long apkFileSize = 0L;
  public String apkIconUrl = "";
  public int apkSwitch = 0;
  public int apkType = 0;
  public String appName = "";
  public String appVersionName = "";
  public String backupUrl = "";
  public String channelUrl = "";
  public int hasDetail = 0;
  public String marketPkgName = "";
  public String packageName = "";
  public int retCode = 0;
  public ArrayList<String> vBackupUrl = null;
  
  public ChannelUrlDetectResp() {}
  
  public ChannelUrlDetectResp(int paramInt1, String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, int paramInt2, int paramInt3, ArrayList<String> paramArrayList, String paramString5, String paramString6, String paramString7, int paramInt4)
  {
    this.retCode = paramInt1;
    this.channelUrl = paramString1;
    this.backupUrl = paramString2;
    this.apkFileSize = paramLong;
    this.packageName = paramString3;
    this.marketPkgName = paramString4;
    this.apkType = paramInt2;
    this.apkSwitch = paramInt3;
    this.vBackupUrl = paramArrayList;
    this.apkIconUrl = paramString5;
    this.appName = paramString6;
    this.appVersionName = paramString7;
    this.hasDetail = paramInt4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.retCode = paramJceInputStream.read(this.retCode, 0, false);
    this.channelUrl = paramJceInputStream.readString(1, false);
    this.backupUrl = paramJceInputStream.readString(2, false);
    this.apkFileSize = paramJceInputStream.read(this.apkFileSize, 3, false);
    this.packageName = paramJceInputStream.readString(4, false);
    this.marketPkgName = paramJceInputStream.readString(5, false);
    this.apkType = paramJceInputStream.read(this.apkType, 6, false);
    this.apkSwitch = paramJceInputStream.read(this.apkSwitch, 7, false);
    if (cache_vBackupUrl == null)
    {
      cache_vBackupUrl = new ArrayList();
      cache_vBackupUrl.add("");
    }
    this.vBackupUrl = ((ArrayList)paramJceInputStream.read(cache_vBackupUrl, 8, false));
    this.apkIconUrl = paramJceInputStream.readString(9, false);
    this.appName = paramJceInputStream.readString(10, false);
    this.appVersionName = paramJceInputStream.readString(11, false);
    this.hasDetail = paramJceInputStream.read(this.hasDetail, 12, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.retCode, 0);
    Object localObject = this.channelUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.backupUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    paramJceOutputStream.write(this.apkFileSize, 3);
    localObject = this.packageName;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    localObject = this.marketPkgName;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 5);
    }
    paramJceOutputStream.write(this.apkType, 6);
    paramJceOutputStream.write(this.apkSwitch, 7);
    localObject = this.vBackupUrl;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 8);
    }
    localObject = this.apkIconUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 9);
    }
    localObject = this.appName;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 10);
    }
    localObject = this.appVersionName;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 11);
    }
    paramJceOutputStream.write(this.hasDetail, 12);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\MTT\ChannelUrlDetectResp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */