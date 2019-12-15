package com.tencent.common.wup;

import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.wup.base.WupAccessController;
import com.tencent.common.wup.base.WupConnectionPool;
import com.tencent.common.wup.base.WupOaepEncryptController;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.mtt.base.net.frame.RequestManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class WUPTaskClient
{
  public static final boolean USE_REQUEST_QUEUE = true;
  private WUPTaskObserver jdField_a_of_type_ComTencentCommonWupWUPTaskObserver = null;
  private WupAccessController jdField_a_of_type_ComTencentCommonWupBaseWupAccessController = null;
  private WupConnectionPool jdField_a_of_type_ComTencentCommonWupBaseWupConnectionPool = null;
  private WupOaepEncryptController jdField_a_of_type_ComTencentCommonWupBaseWupOaepEncryptController = null;
  private IWUPClientProxy jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy = null;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private AtomicInteger jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger = new AtomicInteger(0);
  
  public WUPTaskClient(IWUPClientProxy paramIWUPClientProxy)
  {
    if (paramIWUPClientProxy != null)
    {
      this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy = paramIWUPClientProxy;
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskObserver = new WUPTaskObserver(this);
      this.jdField_a_of_type_ComTencentCommonWupBaseWupAccessController = new WupAccessController(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy);
      this.jdField_a_of_type_ComTencentCommonWupBaseWupOaepEncryptController = new WupOaepEncryptController(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy);
      return;
    }
    throw new RuntimeException("client proxy must not be null");
  }
  
  private int a()
  {
    return this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicInteger.getAndAdd(1);
  }
  
  private static void a(ArrayList<WUPRequestBase> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        WUPRequestBase localWUPRequestBase = (WUPRequestBase)paramArrayList.next();
        try
        {
          localWUPRequestBase.setFailedReason(new Throwable("servant not available"));
          localWUPRequestBase.getRequestCallBack().onWUPTaskFail(localWUPRequestBase);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      return;
    }
  }
  
  private void a(byte[] paramArrayOfByte, WUPRequestBase paramWUPRequestBase, int paramInt)
  {
    paramArrayOfByte = new WUPTask(paramArrayOfByte, paramWUPRequestBase.getAddtionHeader(), paramWUPRequestBase.getNeedEncrypt(), paramWUPRequestBase.getIsFromService(), this);
    paramArrayOfByte.addObserver(this.jdField_a_of_type_ComTencentCommonWupWUPTaskObserver);
    paramArrayOfByte.setTaskType(paramWUPRequestBase.getType());
    paramArrayOfByte.setBindObject(paramWUPRequestBase);
    paramArrayOfByte.setUrl(paramWUPRequestBase.getUrl());
    paramArrayOfByte.setKeepAliveEnable(paramWUPRequestBase.getNeedCloseConnection() ^ true);
    paramArrayOfByte.setPacketSize(paramInt);
    paramArrayOfByte.setIsBackgroudTask(paramWUPRequestBase.getIsBackGroudTask());
    paramArrayOfByte.setEncodeName(paramWUPRequestBase.getEncodeName());
    paramArrayOfByte.setRequestPolicy(paramWUPRequestBase.getRequestPolicy());
    paramArrayOfByte.mNeedStatFlow = paramWUPRequestBase.getNeedStatFlow();
    paramArrayOfByte.mRequestName = paramWUPRequestBase.getRequestName();
    paramArrayOfByte.mPriority = paramWUPRequestBase.getPriority();
    paramArrayOfByte.mTag = paramWUPRequestBase;
    paramArrayOfByte.mThreadWaitTime = System.currentTimeMillis();
    paramWUPRequestBase.addPath("art");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doSendTask: ");
    localStringBuilder.append(paramArrayOfByte);
    FLogger.d("WUPTaskProxy", localStringBuilder.toString());
    if (paramWUPRequestBase.isEmergencyTask()) {
      RequestManager.executeEmergency(paramArrayOfByte);
    } else if (paramWUPRequestBase.getIsBackGroudTask()) {
      RequestManager.executeBackground(paramArrayOfByte);
    } else {
      RequestManager.execute(paramArrayOfByte);
    }
    FLogger.d("debugWUP", "do send new task");
    paramWUPRequestBase = new StringBuilder();
    paramWUPRequestBase.append("Task id:");
    paramWUPRequestBase.append(paramArrayOfByte.mTaskId);
    FLogger.d("WUPTaskProxy", paramWUPRequestBase.toString());
  }
  
  private boolean a(WUPRequestBase paramWUPRequestBase)
  {
    ArrayList localArrayList = new ArrayList();
    boolean bool2 = paramWUPRequestBase instanceof MultiWUPRequestBase;
    boolean bool1 = false;
    if (bool2)
    {
      paramWUPRequestBase = ((MultiWUPRequestBase)paramWUPRequestBase).getRequests();
      if (paramWUPRequestBase != null)
      {
        if (paramWUPRequestBase.size() <= 0) {
          return false;
        }
        localArrayList.addAll(paramWUPRequestBase);
        int i = paramWUPRequestBase.size() - 1;
        while (i >= 0)
        {
          WUPRequestBase localWUPRequestBase = (WUPRequestBase)paramWUPRequestBase.get(i);
          if (this.jdField_a_of_type_ComTencentCommonWupBaseWupAccessController.isWupServantAvalaible(localWUPRequestBase)) {
            localArrayList.remove(localWUPRequestBase);
          } else {
            paramWUPRequestBase.remove(localWUPRequestBase);
          }
          i -= 1;
        }
        if (paramWUPRequestBase.size() > 0) {
          bool1 = true;
        }
      }
      else
      {
        return false;
      }
    }
    else if (this.jdField_a_of_type_ComTencentCommonWupBaseWupAccessController.isWupServantAvalaible(paramWUPRequestBase))
    {
      bool1 = true;
    }
    else
    {
      localArrayList.add(paramWUPRequestBase);
    }
    a(localArrayList);
    return bool1;
  }
  
  WupAccessController a()
  {
    return this.jdField_a_of_type_ComTencentCommonWupBaseWupAccessController;
  }
  
  WupConnectionPool a()
  {
    if (this.jdField_a_of_type_ComTencentCommonWupBaseWupConnectionPool == null) {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (this.jdField_a_of_type_ComTencentCommonWupBaseWupConnectionPool == null) {
          this.jdField_a_of_type_ComTencentCommonWupBaseWupConnectionPool = new WupConnectionPool();
        }
      }
    }
    return this.jdField_a_of_type_ComTencentCommonWupBaseWupConnectionPool;
  }
  
  WupOaepEncryptController a()
  {
    return this.jdField_a_of_type_ComTencentCommonWupBaseWupOaepEncryptController;
  }
  
  IWUPClientProxy a()
  {
    return this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy;
  }
  
  void a(WUPRequestBase paramWUPRequestBase)
  {
    IWUPClientProxy localIWUPClientProxy = this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy;
    if (localIWUPClientProxy != null) {
      localIWUPClientProxy.sendStatAction(paramWUPRequestBase);
    }
  }
  
  public void notifyPendingTasks()
  {
    WUPTaskObserver localWUPTaskObserver = this.jdField_a_of_type_ComTencentCommonWupWUPTaskObserver;
    if (localWUPTaskObserver != null) {
      localWUPTaskObserver.notifyPendingTask();
    }
  }
  
  public boolean send(MultiWUPRequestBase paramMultiWUPRequestBase)
  {
    if (paramMultiWUPRequestBase == null) {
      return false;
    }
    if (!a(paramMultiWUPRequestBase)) {
      return false;
    }
    paramMultiWUPRequestBase.addPath("gmr");
    paramMultiWUPRequestBase.setSendTime(System.currentTimeMillis());
    Object localObject = paramMultiWUPRequestBase.getRequests();
    if (localObject == null)
    {
      paramMultiWUPRequestBase.addPath("mre");
      paramMultiWUPRequestBase.setErrorCode(64535);
      a(paramMultiWUPRequestBase);
      return false;
    }
    if (((ArrayList)localObject).size() == 1)
    {
      paramMultiWUPRequestBase.addPath("mrs");
      return send((WUPRequestBase)((ArrayList)localObject).get(0));
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = ((ArrayList)localObject).iterator();
    while (localIterator.hasNext())
    {
      WUPRequestBase localWUPRequestBase = (WUPRequestBase)localIterator.next();
      localWUPRequestBase.setSendTime(System.currentTimeMillis());
      localWUPRequestBase.setInMultiPackage();
      byte[] arrayOfByte2 = localWUPRequestBase.getPostData();
      localObject = arrayOfByte2;
      if (arrayOfByte2 == null) {
        localObject = localWUPRequestBase.getPostDataFromWUPRequest(a());
      }
      if (localObject != null) {
        localArrayList.add(localObject);
      }
    }
    if (localArrayList.size() > 0)
    {
      byte[] arrayOfByte1;
      try
      {
        localObject = ByteUtils.mergeListByteData(localArrayList);
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        paramMultiWUPRequestBase.setFailedReason(localOutOfMemoryError);
        arrayOfByte1 = null;
      }
      if (arrayOfByte1 == null)
      {
        paramMultiWUPRequestBase.addPath("mme");
        paramMultiWUPRequestBase.setErrorCode(64533);
        a(paramMultiWUPRequestBase);
        return false;
      }
      paramMultiWUPRequestBase.addPath("mpo");
      a(arrayOfByte1, paramMultiWUPRequestBase, localArrayList.size());
      return true;
    }
    paramMultiWUPRequestBase.addPath("mee");
    paramMultiWUPRequestBase.setErrorCode(64534);
    a(paramMultiWUPRequestBase);
    return false;
  }
  
  public boolean send(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase == null) {
      return false;
    }
    if (!a(paramWUPRequestBase)) {
      return false;
    }
    paramWUPRequestBase.setSendTime(System.currentTimeMillis());
    paramWUPRequestBase.addPath("gsr");
    byte[] arrayOfByte;
    if (paramWUPRequestBase.getPostData() != null) {
      arrayOfByte = paramWUPRequestBase.getPostData();
    } else {
      arrayOfByte = paramWUPRequestBase.getPostDataFromWUPRequest(a());
    }
    if (arrayOfByte == null)
    {
      paramWUPRequestBase.addPath("see");
      paramWUPRequestBase.setErrorCode(64534);
      a(paramWUPRequestBase);
      return false;
    }
    paramWUPRequestBase.addPath("spo");
    a(arrayOfByte, paramWUPRequestBase, 1);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPTaskClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */