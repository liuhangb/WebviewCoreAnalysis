package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class PreferencesKeyValue
  extends JceStruct
{
  public String sKey = "";
  public String sValue = "";
  
  public PreferencesKeyValue() {}
  
  public PreferencesKeyValue(String paramString1, String paramString2)
  {
    this.sKey = paramString1;
    this.sValue = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sKey = paramJceInputStream.readString(0, false);
    this.sValue = paramJceInputStream.readString(1, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sKey;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sValue;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PreferencesKeyValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */