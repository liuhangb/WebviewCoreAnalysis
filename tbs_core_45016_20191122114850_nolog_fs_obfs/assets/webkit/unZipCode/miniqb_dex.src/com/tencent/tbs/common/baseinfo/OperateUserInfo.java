package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.HashMap;
import java.util.Map;

public final class OperateUserInfo
  extends JceStruct
{
  static int cache_browserThemeType;
  static Map<String, String> cache_extraUserInfo = new HashMap();
  public String androidId = "";
  public int browserThemeType = 0;
  public Map<String, String> extraUserInfo = null;
  public String guid = "";
  public String idfa = "";
  public String qua2 = "";
  public String remoteIp = "";
  
  static
  {
    cache_extraUserInfo.put("", "");
  }
  
  public OperateUserInfo() {}
  
  public OperateUserInfo(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, Map<String, String> paramMap)
  {
    this.guid = paramString1;
    this.qua2 = paramString2;
    this.remoteIp = paramString3;
    this.browserThemeType = paramInt;
    this.androidId = paramString4;
    this.idfa = paramString5;
    this.extraUserInfo = paramMap;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.guid = paramJceInputStream.readString(0, false);
    this.qua2 = paramJceInputStream.readString(1, false);
    this.remoteIp = paramJceInputStream.readString(2, false);
    this.browserThemeType = paramJceInputStream.read(this.browserThemeType, 3, false);
    this.androidId = paramJceInputStream.readString(4, false);
    this.idfa = paramJceInputStream.readString(5, false);
    this.extraUserInfo = ((Map)paramJceInputStream.read(cache_extraUserInfo, 6, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.guid;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.qua2;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.remoteIp;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    paramJceOutputStream.write(this.browserThemeType, 3);
    localObject = this.androidId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    localObject = this.idfa;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 5);
    }
    localObject = this.extraUserInfo;
    if (localObject != null) {
      paramJceOutputStream.write((Map)localObject, 6);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\OperateUserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */