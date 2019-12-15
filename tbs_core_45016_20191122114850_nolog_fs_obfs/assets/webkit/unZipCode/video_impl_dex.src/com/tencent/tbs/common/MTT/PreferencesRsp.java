package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class PreferencesRsp
  extends JceStruct
{
  static ArrayList<PreferencesKeyValue> cache_vPreferencesKeyValue;
  public int iRspTime = 0;
  public ArrayList<PreferencesKeyValue> vPreferencesKeyValue = null;
  
  public PreferencesRsp() {}
  
  public PreferencesRsp(int paramInt, ArrayList<PreferencesKeyValue> paramArrayList)
  {
    this.iRspTime = paramInt;
    this.vPreferencesKeyValue = paramArrayList;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iRspTime = paramJceInputStream.read(this.iRspTime, 0, true);
    if (cache_vPreferencesKeyValue == null)
    {
      cache_vPreferencesKeyValue = new ArrayList();
      PreferencesKeyValue localPreferencesKeyValue = new PreferencesKeyValue();
      cache_vPreferencesKeyValue.add(localPreferencesKeyValue);
    }
    this.vPreferencesKeyValue = ((ArrayList)paramJceInputStream.read(cache_vPreferencesKeyValue, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRspTime, 0);
    ArrayList localArrayList = this.vPreferencesKeyValue;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PreferencesRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */