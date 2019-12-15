package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class TBSJSApiApiNames
  extends JceStruct
{
  static ArrayList<String> cache_vecApiNames;
  public ArrayList<String> vecApiNames = null;
  
  public TBSJSApiApiNames() {}
  
  public TBSJSApiApiNames(ArrayList<String> paramArrayList)
  {
    this.vecApiNames = paramArrayList;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_vecApiNames == null)
    {
      cache_vecApiNames = new ArrayList();
      cache_vecApiNames.add("");
    }
    this.vecApiNames = ((ArrayList)paramJceInputStream.read(cache_vecApiNames, 0, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    ArrayList localArrayList = this.vecApiNames;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 0);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TBSJSApiApiNames.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */