package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.HashMap;
import java.util.Map;

public final class TBSJSApiWhitelistRsp
  extends JceStruct
{
  static int cache_eRetCode;
  static Map<String, TBSJSApiApiNames> cache_mHostApiNames;
  public int eRetCode = 0;
  public Map<String, TBSJSApiApiNames> mHostApiNames = null;
  public String sAuth = "";
  
  public TBSJSApiWhitelistRsp() {}
  
  public TBSJSApiWhitelistRsp(int paramInt, Map<String, TBSJSApiApiNames> paramMap, String paramString)
  {
    this.eRetCode = paramInt;
    this.mHostApiNames = paramMap;
    this.sAuth = paramString;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.eRetCode = paramJceInputStream.read(this.eRetCode, 0, true);
    if (cache_mHostApiNames == null)
    {
      cache_mHostApiNames = new HashMap();
      TBSJSApiApiNames localTBSJSApiApiNames = new TBSJSApiApiNames();
      cache_mHostApiNames.put("", localTBSJSApiApiNames);
    }
    this.mHostApiNames = ((Map)paramJceInputStream.read(cache_mHostApiNames, 1, false));
    this.sAuth = paramJceInputStream.readString(2, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.eRetCode, 0);
    Object localObject = this.mHostApiNames;
    if (localObject != null) {
      paramJceOutputStream.write((Map)localObject, 1);
    }
    localObject = this.sAuth;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TBSJSApiWhitelistRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */