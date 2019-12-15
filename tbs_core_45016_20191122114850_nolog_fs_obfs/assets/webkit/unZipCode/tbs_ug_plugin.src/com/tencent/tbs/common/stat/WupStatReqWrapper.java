package com.tencent.tbs.common.stat;

import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.tbs.common.wup.MultiWUPRequest;
import com.tencent.tbs.common.wup.WUPRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WupStatReqWrapper
{
  private boolean mIsDataChanged = false;
  private byte[] mLock = new byte[0];
  public List<WUPRequest> mPendingRequests = null;
  
  public WupStatReqWrapper()
  {
    synchronized (this.mLock)
    {
      this.mPendingRequests = new ArrayList();
      LogUtils.d("WUPStatManagerUI", "WupStatReqWrapper: init WupStatReqWrapper");
      return;
    }
  }
  
  public boolean addData(WUPRequest paramWUPRequest)
  {
    if (paramWUPRequest == null) {
      return false;
    }
    synchronized (this.mLock)
    {
      if (this.mPendingRequests == null) {
        this.mPendingRequests = new ArrayList();
      }
      if (!this.mPendingRequests.contains(paramWUPRequest))
      {
        this.mPendingRequests.add(paramWUPRequest);
        return true;
      }
      return false;
    }
  }
  
  public boolean addData(List<WUPRequest> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty())) {
      synchronized (this.mLock)
      {
        if (this.mPendingRequests == null) {
          this.mPendingRequests = new ArrayList();
        }
        boolean bool = this.mPendingRequests.addAll(paramList);
        return bool;
      }
    }
    return false;
  }
  
  public MultiWUPRequest getMultiWupRequest(IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    MultiWUPRequest localMultiWUPRequest = new MultiWUPRequest();
    synchronized (this.mLock)
    {
      if (this.mPendingRequests != null)
      {
        Iterator localIterator = this.mPendingRequests.iterator();
        while (localIterator.hasNext())
        {
          WUPRequest localWUPRequest = (WUPRequest)localIterator.next();
          if (localWUPRequest != null)
          {
            localWUPRequest.setRequestCallBack(paramIWUPRequestCallBack);
            localMultiWUPRequest.addWUPRequest(localWUPRequest);
          }
        }
      }
      return localMultiWUPRequest;
    }
  }
  
  public int getSize()
  {
    synchronized (this.mLock)
    {
      if (this.mPendingRequests == null) {
        return 0;
      }
      int i = this.mPendingRequests.size();
      return i;
    }
  }
  
  public boolean hasDataChanged()
  {
    synchronized (this.mLock)
    {
      boolean bool = this.mIsDataChanged;
      return bool;
    }
  }
  
  public WupStatReqWrapper makeCopy()
  {
    synchronized (this.mLock)
    {
      WupStatReqWrapper localWupStatReqWrapper = new WupStatReqWrapper();
      localWupStatReqWrapper.addData(this.mPendingRequests);
      return localWupStatReqWrapper;
    }
  }
  
  public boolean removeData(WUPRequest paramWUPRequest)
  {
    if (paramWUPRequest == null) {
      return false;
    }
    synchronized (this.mLock)
    {
      if (this.mPendingRequests == null)
      {
        LogUtils.d("WUPStatManagerUI", "mPendingRequests is NULL, return");
        return false;
      }
      if (this.mPendingRequests.contains(paramWUPRequest))
      {
        this.mPendingRequests.remove(paramWUPRequest);
        return true;
      }
      return false;
    }
  }
  
  public boolean removeDataById(int paramInt)
  {
    boolean bool2 = false;
    if (paramInt < 0) {
      return false;
    }
    synchronized (this.mLock)
    {
      if (this.mPendingRequests == null) {
        return false;
      }
      Iterator localIterator = this.mPendingRequests.iterator();
      Object localObject2;
      do
      {
        for (;;)
        {
          bool1 = bool2;
          if (!localIterator.hasNext()) {
            break label115;
          }
          localObject2 = (WUPRequest)localIterator.next();
          if (localObject2 != null) {
            break;
          }
          localIterator.remove();
        }
        localObject2 = ((WUPRequest)localObject2).getBindObject();
      } while ((!(localObject2 instanceof Integer)) || (((Integer)localObject2).intValue() != paramInt));
      boolean bool1 = true;
      localIterator.remove();
      label115:
      return bool1;
    }
  }
  
  public void setDataChanged(boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      this.mIsDataChanged = paramBoolean;
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\WupStatReqWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */