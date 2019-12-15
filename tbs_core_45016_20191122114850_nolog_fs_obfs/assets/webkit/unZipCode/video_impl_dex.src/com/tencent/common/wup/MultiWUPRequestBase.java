package com.tencent.common.wup;

import java.util.ArrayList;
import java.util.Iterator;

public class MultiWUPRequestBase
  extends WUPRequestBase
{
  private ArrayList<WUPRequestBase> mRequests = null;
  private ArrayList<WUPResponseBase> mResponses = null;
  
  public boolean addWUPRequest(WUPRequestBase paramWUPRequestBase)
  {
    if (this.mRequests == null) {
      this.mRequests = new ArrayList();
    }
    if (paramWUPRequestBase != null) {
      try
      {
        boolean bool = this.mRequests.contains(paramWUPRequestBase);
        if (!bool) {
          i = 1;
        }
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        localArrayIndexOutOfBoundsException.printStackTrace();
        i = 0;
      }
    }
    int i = 0;
    if (i != 0)
    {
      this.mRequests.add(paramWUPRequestBase);
      checkClassLoader(paramWUPRequestBase);
      return true;
    }
    return false;
  }
  
  public ArrayList<WUPRequestBase> getRequests()
  {
    return this.mRequests;
  }
  
  public ArrayList<WUPResponseBase> getResponses()
  {
    return this.mResponses;
  }
  
  public void setRequestName(String paramString)
  {
    super.setRequestName(paramString);
    Object localObject = this.mRequests;
    if (localObject == null) {
      return;
    }
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      ((WUPRequestBase)((Iterator)localObject).next()).setRequestName(paramString);
    }
  }
  
  public void setResponses(ArrayList<WUPResponseBase> paramArrayList)
  {
    if (paramArrayList == null) {
      return;
    }
    this.mResponses = paramArrayList;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\MultiWUPRequestBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */