package com.tencent.common.wup.extension;

import com.tencent.basesupport.FLogger;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class a
{
  public List<WUPStatRequest> a;
  private boolean jdField_a_of_type_Boolean = false;
  private byte[] jdField_a_of_type_ArrayOfByte = new byte[0];
  
  public a()
  {
    this.jdField_a_of_type_JavaUtilList = null;
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      this.jdField_a_of_type_JavaUtilList = new ArrayList();
      FLogger.d("WUPStatClient", "WupStatReqWrapper: init WupStatReqWrapper");
      return;
    }
  }
  
  public int a()
  {
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      if (this.jdField_a_of_type_JavaUtilList == null) {
        return 0;
      }
      int i = this.jdField_a_of_type_JavaUtilList.size();
      return i;
    }
  }
  
  public a a()
  {
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      a locala = new a();
      locala.a(this.jdField_a_of_type_JavaUtilList);
      return locala;
    }
  }
  
  public ArrayList<WUPRequestBase> a(IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    ArrayList localArrayList = new ArrayList();
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      if (this.jdField_a_of_type_JavaUtilList != null)
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext())
        {
          WUPRequestBase localWUPRequestBase = (WUPRequestBase)localIterator.next();
          if (localWUPRequestBase != null)
          {
            localWUPRequestBase.setRequestCallBack(paramIWUPRequestCallBack);
            localArrayList.add(localWUPRequestBase);
          }
        }
      }
      return localArrayList;
    }
  }
  
  public boolean a(int paramInt)
  {
    boolean bool2 = false;
    if (paramInt < 0) {
      return false;
    }
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      if (this.jdField_a_of_type_JavaUtilList == null) {
        return false;
      }
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      Object localObject2;
      do
      {
        for (;;)
        {
          bool1 = bool2;
          if (!localIterator.hasNext()) {
            break label115;
          }
          localObject2 = (WUPStatRequest)localIterator.next();
          if (localObject2 != null) {
            break;
          }
          localIterator.remove();
        }
        localObject2 = ((WUPStatRequest)localObject2).getBindObject();
      } while ((!(localObject2 instanceof Integer)) || (((Integer)localObject2).intValue() != paramInt));
      boolean bool1 = true;
      localIterator.remove();
      label115:
      return bool1;
    }
  }
  
  public boolean a(WUPStatRequest paramWUPStatRequest)
  {
    if (paramWUPStatRequest == null) {
      return false;
    }
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      if (this.jdField_a_of_type_JavaUtilList == null) {
        this.jdField_a_of_type_JavaUtilList = new ArrayList();
      }
      if (!this.jdField_a_of_type_JavaUtilList.contains(paramWUPStatRequest))
      {
        this.jdField_a_of_type_JavaUtilList.add(paramWUPStatRequest);
        return true;
      }
      return false;
    }
  }
  
  public boolean a(List<WUPStatRequest> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty())) {
      synchronized (this.jdField_a_of_type_ArrayOfByte)
      {
        if (this.jdField_a_of_type_JavaUtilList == null) {
          this.jdField_a_of_type_JavaUtilList = new ArrayList();
        }
        boolean bool = this.jdField_a_of_type_JavaUtilList.addAll(paramList);
        return bool;
      }
    }
    return false;
  }
  
  public boolean b(WUPStatRequest paramWUPStatRequest)
  {
    if (paramWUPStatRequest == null) {
      return false;
    }
    synchronized (this.jdField_a_of_type_ArrayOfByte)
    {
      if (this.jdField_a_of_type_JavaUtilList == null)
      {
        FLogger.d("WUPStatClient", "mPendingRequests is NULL, return");
        return false;
      }
      if (this.jdField_a_of_type_JavaUtilList.contains(paramWUPStatRequest))
      {
        this.jdField_a_of_type_JavaUtilList.remove(paramWUPStatRequest);
        return true;
      }
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\extension\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */