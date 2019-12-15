package com.tencent.common.wup;

import android.text.TextUtils;
import com.taf.UniPacket;
import com.taf.UniPacketEx;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.http.MttResponse;
import com.tencent.common.manifest.EventEmiter;
import com.tencent.common.manifest.EventMessage;
import com.tencent.common.serverconfig.WupServerConfigsWrapper;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.common.wup.base.WupAccessController;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.tbs.common.internal.service.StatServerHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WUPTaskObserver
  implements TaskObserver
{
  private WUPTaskClient jdField_a_of_type_ComTencentCommonWupWUPTaskClient = null;
  Object jdField_a_of_type_JavaLangObject = new byte[0];
  ArrayList<MultiWUPRequestBase> jdField_a_of_type_JavaUtilArrayList = null;
  boolean jdField_a_of_type_Boolean = false;
  ArrayList<Task> b = null;
  
  public WUPTaskObserver(WUPTaskClient paramWUPTaskClient)
  {
    this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient = paramWUPTaskClient;
  }
  
  private WUPRequestBase a(List<WUPRequestBase> paramList, WUPResponseBase paramWUPResponseBase)
  {
    if (paramList != null)
    {
      if (paramList.isEmpty()) {
        return null;
      }
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        WUPRequestBase localWUPRequestBase = (WUPRequestBase)paramList.next();
        if ((StringUtils.isStringEqual(localWUPRequestBase.getFuncName(), paramWUPResponseBase.getFuncName())) && (StringUtils.isStringEqual(localWUPRequestBase.getServerName(), paramWUPResponseBase.getServantName())) && (localWUPRequestBase.getRequstID() == paramWUPResponseBase.getRequestId())) {
          return localWUPRequestBase;
        }
      }
      return null;
    }
    return null;
  }
  
  private WUPResponseBase a(WUPTask paramWUPTask, WUPRequestBase paramWUPRequestBase)
  {
    byte[] arrayOfByte = paramWUPTask.getResponseData();
    if (arrayOfByte != null)
    {
      if (arrayOfByte.length <= 4) {
        return null;
      }
      long l = System.currentTimeMillis();
      Object localObject2 = new UniPacket();
      ((UniPacket)localObject2).setProtocolClassNamePrefs(paramWUPRequestBase.getClassNamePrefs());
      try
      {
        ((UniPacket)localObject2).setEncodeName(paramWUPTask.getEncodeName());
        ((UniPacket)localObject2).decode(arrayOfByte);
      }
      catch (Throwable localThrowable)
      {
        arrayOfByte = a(paramWUPTask, (UniPacket)localObject2);
        if (arrayOfByte == null)
        {
          paramWUPTask.mFailedReason = localThrowable;
          paramWUPRequestBase.setFailedReason(localThrowable);
          localThrowable.printStackTrace();
          return null;
        }
      }
      ClassLoader localClassLoader = paramWUPRequestBase.getClassLoader();
      if (localClassLoader == null) {}
      try
      {
        Object localObject1 = (Integer)((UniPacket)localObject2).get("");
        paramWUPRequestBase = (WUPRequestBase)localObject1;
        break label143;
        localObject1 = (Integer)((UniPacket)localObject2).get("", false, localClassLoader);
        paramWUPRequestBase = (WUPRequestBase)localObject1;
        label143:
        if (localClassLoader != null) {
          localObject1 = new WUPResponseBase((UniPacket)localObject2, localClassLoader);
        } else {
          localObject1 = new WUPResponseBase((UniPacket)localObject2);
        }
        ((WUPResponseBase)localObject1).setDecodeStartTime(l);
        ((WUPResponseBase)localObject1).setRespFromTestServer(paramWUPTask.isRespFromTestServer());
        localObject2 = new UniPacketEx();
        try
        {
          ((UniPacketEx)localObject2).setEncodeName("utf8");
          ((UniPacketEx)localObject2).decode(arrayOfByte);
          if (localClassLoader == null) {
            paramWUPTask = (Integer)((UniPacketEx)localObject2).get("");
          } else {
            paramWUPTask = (Integer)((UniPacketEx)localObject2).get("", false, localClassLoader);
          }
          if ((paramWUPTask != null) && (paramWUPTask.intValue() == 0)) {
            ((WUPResponseBase)localObject1).setContextFeature(((UniPacketEx)localObject2).getContext("CMD_FEATURE"));
          }
        }
        catch (Throwable paramWUPTask)
        {
          paramWUPTask.printStackTrace();
        }
        ((WUPResponseBase)localObject1).setReturnCode(paramWUPRequestBase);
        ((WUPResponseBase)localObject1).setDecodeEndTime(System.currentTimeMillis());
        return (WUPResponseBase)localObject1;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        paramWUPRequestBase.setFailedReason(localException);
        paramWUPTask.mFailedReason = localException;
        return null;
      }
    }
    return null;
  }
  
  private ArrayList<WUPResponseBase> a(WUPTask paramWUPTask, int paramInt, MultiWUPRequestBase paramMultiWUPRequestBase)
  {
    Object localObject1 = paramWUPTask.getResponseData();
    if ((localObject1 != null) && (localObject1.length >= 4))
    {
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = paramMultiWUPRequestBase.getRequests();
      int i = 0;
      Object localObject4;
      for (;;)
      {
        if (i >= paramInt) {
          break label468;
        }
        long l = System.currentTimeMillis();
        Object localObject3 = new UniPacket();
        try
        {
          ((UniPacket)localObject3).setEncodeName(paramWUPTask.getEncodeName());
          ((UniPacket)localObject3).decode((byte[])localObject1);
        }
        catch (Throwable localThrowable)
        {
          if (i != 0) {
            break;
          }
        }
        Object localObject2 = a(paramWUPTask, (UniPacket)localObject3);
        localObject1 = localObject2;
        if (localObject2 != null)
        {
          int j = ((UniPacket)localObject3).getPacketSize();
          if (j < 0) {
            return localArrayList1;
          }
          try
          {
            int k = localObject1.length - j;
            if (k >= 0)
            {
              localObject2 = new byte[k];
              System.arraycopy(localObject1, j, localObject2, 0, localObject2.length);
              localObject1 = localObject2;
            }
            localObject4 = paramMultiWUPRequestBase.getClassLoader();
            if (localObject4 == null) {}
            try
            {
              localObject2 = (Integer)((UniPacket)localObject3).get("");
              break label201;
              localObject2 = (Integer)((UniPacket)localObject3).get("", false, (ClassLoader)localObject4);
              label201:
              localObject3 = new WUPResponseBase((UniPacket)localObject3, (ClassLoader)localObject4);
              ((WUPResponseBase)localObject3).setReturnCode((Integer)localObject2);
              ((WUPResponseBase)localObject3).setDecodeStartTime(l);
              ((WUPResponseBase)localObject3).setDecodeEndTime(System.currentTimeMillis());
              ((WUPResponseBase)localObject3).setRespFromTestServer(paramWUPTask.isRespFromTestServer());
              if (((WUPResponseBase)localObject3).getErrorCode() == -9)
              {
                this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().disableWupServant((WUPResponseBase)localObject3, ((WUPResponseBase)localObject3).getRetryTime());
                localObject2 = a(localArrayList2, (WUPResponseBase)localObject3);
                if (localObject2 != null) {
                  ((WUPRequestBase)localObject2).setErrorCode(63525);
                }
              }
              else
              {
                localArrayList1.add(localObject3);
                if (localArrayList2 != null)
                {
                  localObject2 = localArrayList2.iterator();
                  while (((Iterator)localObject2).hasNext())
                  {
                    localObject4 = (WUPRequestBase)((Iterator)localObject2).next();
                    if ((StringUtils.isStringEqual(((WUPRequestBase)localObject4).getFuncName(), ((WUPResponseBase)localObject3).getFuncName())) && (StringUtils.isStringEqual(((WUPRequestBase)localObject4).getServerName(), ((WUPResponseBase)localObject3).getServantName())) && (((WUPRequestBase)localObject4).getRequstID() == ((WUPResponseBase)localObject3).getRequestId())) {
                      ((WUPResponseBase)localObject3).setProtocolClassNamePrefs(((WUPRequestBase)localObject4).getClassNamePrefs());
                    }
                  }
                }
                if (localObject1 == null) {
                  break label468;
                }
                if (localObject1.length < 4) {
                  return localArrayList1;
                }
              }
              i += 1;
            }
            catch (Exception localException)
            {
              paramMultiWUPRequestBase.setFailedReason(localException);
              paramWUPTask.mFailedReason = localException;
              localException.printStackTrace();
              return null;
            }
            paramMultiWUPRequestBase.setFailedReason((Throwable)localObject4);
          }
          catch (OutOfMemoryError paramWUPTask)
          {
            paramMultiWUPRequestBase.setFailedReason(paramWUPTask);
            return localArrayList1;
          }
        }
      }
      paramWUPTask.mFailedReason = ((Throwable)localObject4);
      ((Throwable)localObject4).printStackTrace();
      return null;
      label468:
      return localArrayList1;
    }
    return null;
  }
  
  private void a(WUPRequestBase paramWUPRequestBase, WUPTask paramWUPTask)
  {
    paramWUPRequestBase.clearPath();
    paramWUPRequestBase.setErrorCode(0);
    paramWUPRequestBase.addNetTime(paramWUPTask.mNetTimeList);
    paramWUPRequestBase.addThreadWaitTime(Long.valueOf(paramWUPTask.mThreadWaitTime));
    paramWUPRequestBase.setFailedReason(null);
    if ((paramWUPRequestBase instanceof MultiWUPRequestBase))
    {
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.send((MultiWUPRequestBase)paramWUPRequestBase);
      return;
    }
    this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.send(paramWUPRequestBase);
  }
  
  private void a(WUPTask paramWUPTask)
  {
    if (paramWUPTask == null) {
      return;
    }
    String str2 = paramWUPTask.getDnsType();
    String str1 = paramWUPTask.getTaskUrl();
    int i = paramWUPTask.mErrorCode;
    if ((i != 63528) && (i != 63532) && (i != 63535) && (i != 63529))
    {
      FLogger.d("WUPTaskProxy", "startWupIPListCheckIfNeeded: ERR CODE Not Network Exception, ignore");
      return;
    }
    if (paramWUPTask.hasSetServer())
    {
      paramWUPTask = new StringBuilder();
      paramWUPTask.append("startWupIPListCheckIfNeeded: current using domain ");
      paramWUPTask.append(str1);
      paramWUPTask.append(", ignore");
      FLogger.d("WUPTaskProxy", paramWUPTask.toString());
      return;
    }
    if (Apn.isNetworkAvailable())
    {
      FLogger.d("WUPTaskProxy", "startWupIPListCheckIfNeeded: do start Check");
      if (!TextUtils.isEmpty(str2))
      {
        FLogger.d("WUPTaskProxy", "startWupIPListCheckIfNeeded: do start Check Domain Address");
        WupServerConfigsWrapper.startCheckWupDomain();
        return;
      }
      FLogger.d("WUPTaskProxy", "startWupIPListCheckIfNeeded: do start Check IP Address");
      WupServerConfigsWrapper.startCheckWupIPAddr(str1);
    }
  }
  
  private void a(Task paramTask)
  {
    Object localObject3 = (WUPTask)paramTask;
    MultiWUPRequestBase localMultiWUPRequestBase = (MultiWUPRequestBase)((WUPTask)localObject3).getBindObject();
    if (localMultiWUPRequestBase == null) {
      return;
    }
    Object localObject2 = ((WUPTask)localObject3).getTaskUrl();
    Object localObject1 = localObject2;
    if (localObject2 != null)
    {
      localObject1 = localObject2;
      if (((String)localObject2).startsWith("http")) {
        localObject1 = ((String)localObject2).substring(7, ((String)localObject2).length());
      }
    }
    localMultiWUPRequestBase.setCurrentIP((String)localObject1);
    localMultiWUPRequestBase.addNetTime(paramTask.mNetTimeList);
    localMultiWUPRequestBase.addThreadWaitTime(Long.valueOf(paramTask.mThreadWaitTime));
    localMultiWUPRequestBase.setWupResolvedAddrInfo(((WUPTask)localObject3).getDnsIP(), ((WUPTask)localObject3).getDnsType());
    localMultiWUPRequestBase.setExecuteRes((byte)0);
    localMultiWUPRequestBase.addPath("mrf");
    if (paramTask.mErrorCode != 0) {
      localMultiWUPRequestBase.setErrorCode(paramTask.mErrorCode);
    }
    localMultiWUPRequestBase.setFailedReason(paramTask.getFailReason());
    this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().statFlow(localMultiWUPRequestBase, (WUPTask)localObject3);
    localObject1 = localMultiWUPRequestBase.getRequests();
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      localMultiWUPRequestBase.addPath("mrfnr");
      localObject1 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (WUPRequestBase)((Iterator)localObject1).next();
        if (localObject2 != null)
        {
          ((WUPRequestBase)localObject2).setNetworkStatus(paramTask.getNetworkStatus());
          ((WUPRequestBase)localObject2).setFailedReason(paramTask.getFailReason());
          ((WUPRequestBase)localObject2).copyWupQuality(localMultiWUPRequestBase);
          ((WUPRequestBase)localObject2).addPath("mfail");
          localObject3 = ((WUPRequestBase)localObject2).getRequestCallBack();
          if (localObject3 != null) {
            ((IWUPRequestCallBack)localObject3).onWUPTaskFail((WUPRequestBase)localObject2);
          }
        }
      }
      localMultiWUPRequestBase.addPath("mfail");
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction(localMultiWUPRequestBase);
      return;
    }
    localMultiWUPRequestBase.addPath("mrfre");
    this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction(localMultiWUPRequestBase);
  }
  
  private boolean a(WUPResponseBase paramWUPResponseBase, boolean paramBoolean)
  {
    if (paramWUPResponseBase == null) {
      return false;
    }
    String str = paramWUPResponseBase.getFuncName();
    paramWUPResponseBase = paramWUPResponseBase.getServantName();
    if ((StringUtils.isStringEqual("getGuid", str)) && (StringUtils.isStringEqual("CMD_GUID", paramWUPResponseBase))) {
      return true;
    }
    if ((StringUtils.isStringEqual("getHotWordList", str)) && (StringUtils.isStringEqual("hotword", paramWUPResponseBase))) {
      return true;
    }
    if ((StringUtils.isStringEqual("getPluginList", str)) && (StringUtils.isStringEqual("uniplugin", paramWUPResponseBase))) {
      return true;
    }
    return (StringUtils.isStringEqual("getInterestLists", str)) && (StringUtils.isStringEqual("coolRead", paramWUPResponseBase));
  }
  
  private byte[] a(WUPTask paramWUPTask, UniPacket paramUniPacket)
  {
    if ((paramWUPTask != null) && (paramUniPacket != null))
    {
      Object localObject2 = paramWUPTask.getMttResponse();
      if (localObject2 == null)
      {
        FLogger.d("WUPTaskProxy", "tryDecodeWhenHeadersLoss: mttResponse is null, ignore");
        return null;
      }
      Object localObject1 = ((MttResponse)localObject2).getServer();
      String str = ((MttResponse)localObject2).getQSZip();
      localObject2 = ((MttResponse)localObject2).getQEncrypt();
      int i;
      if (("QBServer".equals(localObject1)) && (TextUtils.isEmpty(str)) && (TextUtils.isEmpty((CharSequence)localObject2)))
      {
        localObject1 = paramWUPTask.a(true, true);
        if (localObject1 != null) {
          try
          {
            paramUniPacket.decode((byte[])localObject1);
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
            FLogger.d("WUPTaskProxy", "tryDecodeWhenHeadersLoss: try decode with encrypt and gzip fail");
            i = 0;
            break label126;
          }
        }
        i = 1;
        label126:
        if (i == 0)
        {
          paramWUPTask = paramWUPTask.a(true, false);
          try
          {
            paramUniPacket.decode(paramWUPTask);
            i = 1;
          }
          catch (Throwable paramUniPacket)
          {
            paramUniPacket.printStackTrace();
            FLogger.d("WUPTaskProxy", "tryDecodeWhenHeadersLoss: try decode with encrypt and non-gzip fail");
          }
        }
        else
        {
          paramWUPTask = (WUPTask)localObject1;
        }
      }
      else
      {
        paramWUPTask = null;
        i = 0;
      }
      if (i != 0) {
        return paramWUPTask;
      }
      return null;
    }
    FLogger.d("WUPTaskProxy", "tryDecodeWhenHeadersLoss: param is null, ignore");
    return null;
  }
  
  public void filterSplashCandoRequest(MultiWUPRequestBase paramMultiWUPRequestBase)
  {
    if (paramMultiWUPRequestBase == null) {
      return;
    }
    Object localObject1 = paramMultiWUPRequestBase.getResponses();
    if (localObject1 != null)
    {
      if (((ArrayList)localObject1).size() <= 0) {
        return;
      }
      paramMultiWUPRequestBase = paramMultiWUPRequestBase.getRequests();
      if (paramMultiWUPRequestBase != null)
      {
        if (paramMultiWUPRequestBase.size() <= 0) {
          return;
        }
        localObject1 = ((ArrayList)localObject1).iterator();
        for (;;)
        {
          if (!((Iterator)localObject1).hasNext()) {
            break label317;
          }
          WUPResponseBase localWUPResponseBase = (WUPResponseBase)((Iterator)localObject1).next();
          if (a(localWUPResponseBase, true))
          {
            Object localObject2 = paramMultiWUPRequestBase.iterator();
            if (((Iterator)localObject2).hasNext())
            {
              WUPRequestBase localWUPRequestBase = (WUPRequestBase)((Iterator)localObject2).next();
              if ((!StringUtils.isStringEqual(localWUPRequestBase.getFuncName(), localWUPResponseBase.getFuncName())) || (!StringUtils.isStringEqual(localWUPRequestBase.getServerName(), localWUPResponseBase.getServantName())) || (localWUPRequestBase.getRequstID() != localWUPResponseBase.getRequestId())) {
                break;
              }
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("filterSplashCandoRequest:  request (id=");
              ((StringBuilder)localObject2).append(localWUPRequestBase.getRequstID());
              ((StringBuilder)localObject2).append("): ");
              ((StringBuilder)localObject2).append(localWUPRequestBase.getServerName());
              ((StringBuilder)localObject2).append("/");
              ((StringBuilder)localObject2).append(localWUPRequestBase.getFuncName());
              ((StringBuilder)localObject2).append(", response (id=");
              ((StringBuilder)localObject2).append(localWUPResponseBase.getRequestId());
              ((StringBuilder)localObject2).append("): ");
              ((StringBuilder)localObject2).append(localWUPResponseBase.getServantName());
              ((StringBuilder)localObject2).append("/");
              ((StringBuilder)localObject2).append(localWUPResponseBase.getFuncName());
              FLogger.d("WUPTaskProxy", ((StringBuilder)localObject2).toString());
              localObject2 = localWUPRequestBase.getRequestCallBack();
              if (localObject2 != null)
              {
                localWUPResponseBase.setHandleStartTime(System.currentTimeMillis());
                ((IWUPRequestCallBack)localObject2).onWUPTaskSuccess(localWUPRequestBase, localWUPResponseBase);
              }
              localWUPRequestBase.setHasReplied(true);
            }
          }
        }
        label317:
        return;
      }
      return;
    }
  }
  
  public void notifyPendingTask()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_Boolean = true;
      if ((this.jdField_a_of_type_JavaUtilArrayList == null) && (this.b == null)) {
        return;
      }
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          Iterator localIterator;
          Object localObject;
          if (WUPTaskObserver.this.a != null)
          {
            localIterator = WUPTaskObserver.this.a.iterator();
            while (localIterator.hasNext())
            {
              localObject = (MultiWUPRequestBase)localIterator.next();
              WUPTaskObserver.this.onMultiTaskSucc((MultiWUPRequestBase)localObject);
            }
            WUPTaskObserver.this.a = null;
          }
          if (WUPTaskObserver.this.b != null)
          {
            localIterator = WUPTaskObserver.this.b.iterator();
            while (localIterator.hasNext())
            {
              localObject = (Task)localIterator.next();
              WUPTaskObserver.a(WUPTaskObserver.this, (Task)localObject);
            }
            WUPTaskObserver.this.b = null;
          }
        }
      });
      return;
    }
  }
  
  public void onMultiTaskSucc(MultiWUPRequestBase paramMultiWUPRequestBase)
  {
    if (paramMultiWUPRequestBase == null) {
      return;
    }
    Object localObject1 = paramMultiWUPRequestBase.getRequests();
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      Object localObject2 = paramMultiWUPRequestBase.getResponses();
      if (localObject2 != null)
      {
        if (((ArrayList)localObject2).size() <= 0) {
          return;
        }
        ((ArrayList)localObject2).iterator();
        paramMultiWUPRequestBase.addPath("mrnr");
        localObject2 = ((ArrayList)localObject2).iterator();
        Object localObject3;
        for (;;)
        {
          if (!((Iterator)localObject2).hasNext()) {
            break label213;
          }
          localObject3 = (WUPResponseBase)((Iterator)localObject2).next();
          Object localObject4 = ((ArrayList)localObject1).iterator();
          if (((Iterator)localObject4).hasNext())
          {
            WUPRequestBase localWUPRequestBase = (WUPRequestBase)((Iterator)localObject4).next();
            if ((localWUPRequestBase.getHasReplied()) || (!StringUtils.isStringEqual(localWUPRequestBase.getFuncName(), ((WUPResponseBase)localObject3).getFuncName())) || (!StringUtils.isStringEqual(localWUPRequestBase.getServerName(), ((WUPResponseBase)localObject3).getServantName())) || (localWUPRequestBase.getRequstID() != ((WUPResponseBase)localObject3).getRequestId())) {
              break;
            }
            localObject4 = localWUPRequestBase.getRequestCallBack();
            if (localObject4 != null)
            {
              ((WUPResponseBase)localObject3).setHandleStartTime(System.currentTimeMillis());
              ((IWUPRequestCallBack)localObject4).onWUPTaskSuccess(localWUPRequestBase, (WUPResponseBase)localObject3);
            }
            localWUPRequestBase.setHasReplied(true);
            localWUPRequestBase.copyWupQuality(paramMultiWUPRequestBase);
            localWUPRequestBase.addPath("msucc");
          }
        }
        label213:
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (WUPRequestBase)((Iterator)localObject1).next();
          if ((localObject2 != null) && (!((WUPRequestBase)localObject2).getHasReplied()))
          {
            paramMultiWUPRequestBase.addPath("mrne");
            localObject3 = ((WUPRequestBase)localObject2).getRequestCallBack();
            if (localObject3 != null) {
              ((IWUPRequestCallBack)localObject3).onWUPTaskFail((WUPRequestBase)localObject2);
            }
            ((WUPRequestBase)localObject2).copyWupQuality(paramMultiWUPRequestBase);
            ((WUPRequestBase)localObject2).setExecuteRes((byte)0);
          }
        }
        paramMultiWUPRequestBase.addPath("msucc");
        this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction(paramMultiWUPRequestBase);
        return;
      }
      return;
    }
    paramMultiWUPRequestBase.addPath("mrre");
  }
  
  public void onTaskCompleted(Task arg1)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onTaskCompleted: ");
    ((StringBuilder)localObject1).append(???);
    FLogger.d("WUPTaskProxy", ((StringBuilder)localObject1).toString());
    WUPTask localWUPTask = (WUPTask)???;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("wup task SUCC, curr ip = ");
    ((StringBuilder)localObject1).append(localWUPTask.getTaskUrl());
    ((StringBuilder)localObject1).append(", before dns = ");
    ((StringBuilder)localObject1).append(localWUPTask.getTaskUrlBeforeDns());
    FLogger.d("wup-ip-list", ((StringBuilder)localObject1).toString());
    if (UrlUtils.isIpv6Url(???.getTaskUrl())) {
      StatServerHolder.userBehaviorStatistics("AWNWF53_IPV6_WUP_1");
    }
    if (localWUPTask.getPacketSize() == 1)
    {
      localObject4 = (WUPRequestBase)localWUPTask.getBindObject();
      if (localObject4 == null) {
        return;
      }
      ((WUPRequestBase)localObject4).setEndTime(System.currentTimeMillis());
      localObject3 = localWUPTask.getTaskUrl();
      localObject1 = localObject3;
      if (((String)localObject3).startsWith("http")) {
        localObject1 = ((String)localObject3).substring(7, ((String)localObject3).length());
      }
      ((WUPRequestBase)localObject4).setCurrentIP((String)localObject1);
      ((WUPRequestBase)localObject4).addTaskPaths(localWUPTask.getStatPath());
      ((WUPRequestBase)localObject4).setExecuteRes((byte)1);
      ((WUPRequestBase)localObject4).addPath("srs");
      ((WUPRequestBase)localObject4).addNetTime(localWUPTask.mNetTimeList);
      ((WUPRequestBase)localObject4).addThreadWaitTime(Long.valueOf(localWUPTask.mThreadWaitTime));
      ((WUPRequestBase)localObject4).setWupResolvedAddrInfo(localWUPTask.getDnsIP(), localWUPTask.getDnsType());
      localObject1 = a(localWUPTask, (WUPRequestBase)localObject4);
      if (localObject1 == null)
      {
        ((WUPRequestBase)localObject4).addPath("sde");
        ((WUPRequestBase)localObject4).setErrorCode(62535);
        onTaskFailed(???);
        return;
      }
      if (((WUPResponseBase)localObject1).getErrorCode() != 0)
      {
        int j = ((WUPResponseBase)localObject1).getRetryTime();
        int i = 3600;
        if (j <= 3600) {
          i = ((WUPResponseBase)localObject1).getRetryTime();
        }
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("ErrorCode: ");
        ((StringBuilder)localObject3).append(((WUPResponseBase)localObject1).getErrorCode());
        ((StringBuilder)localObject3).append(", RetryTime: ");
        ((StringBuilder)localObject3).append(((WUPResponseBase)localObject1).getRetryTime());
        ((StringBuilder)localObject3).append(", RetryFlag: ");
        ((StringBuilder)localObject3).append(((WUPResponseBase)localObject1).getRetryFlag());
        FLogger.d("WUPTaskProxy", ((StringBuilder)localObject3).toString());
        j = ((WUPResponseBase)localObject1).getErrorCode();
        boolean bool = false;
        if (j == -9)
        {
          this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().disableWupServant((WUPResponseBase)localObject1, i);
        }
        else if (((WUPResponseBase)localObject1).getRetryFlag() != 0)
        {
          FLogger.d("WUPTaskProxy", "can retry");
          bool = true;
        }
        ???.mNeedRetryNow = bool;
        localWUPTask.mErrorCode = 63525;
        onTaskFailed(???);
        return;
      }
      ((WUPResponseBase)localObject1).setOrglResponseData(localWUPTask.getResponseData());
      ((WUPResponseBase)localObject1).setEncodeName(localWUPTask.getEncodeName());
      ??? = ((WUPRequestBase)localObject4).getRequestCallBack();
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().statFlow((WUPRequestBase)localObject4, localWUPTask);
      if (??? == null)
      {
        ((WUPRequestBase)localObject4).addPath("scn");
        this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction((WUPRequestBase)localObject4);
        return;
      }
      ((WUPResponseBase)localObject1).setHandleStartTime(System.currentTimeMillis());
      ???.onWUPTaskSuccess((WUPRequestBase)localObject4, (WUPResponseBase)localObject1);
      ((WUPRequestBase)localObject4).addPath("ssucc");
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction((WUPRequestBase)localObject4);
      return;
    }
    Object localObject4 = (MultiWUPRequestBase)localWUPTask.getBindObject();
    if (localObject4 == null) {
      return;
    }
    ((MultiWUPRequestBase)localObject4).setEndTime(System.currentTimeMillis());
    ((MultiWUPRequestBase)localObject4).addTaskPaths(localWUPTask.getStatPath());
    ((MultiWUPRequestBase)localObject4).addPath("mrs");
    Object localObject3 = localWUPTask.getTaskUrl();
    localObject1 = localObject3;
    if (((String)localObject3).startsWith("http")) {
      localObject1 = ((String)localObject3).substring(7, ((String)localObject3).length());
    }
    ((MultiWUPRequestBase)localObject4).setCurrentIP((String)localObject1);
    ((MultiWUPRequestBase)localObject4).setWupResolvedAddrInfo(localWUPTask.getDnsIP(), localWUPTask.getDnsType());
    this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().statFlow((WUPRequestBase)localObject4, localWUPTask);
    localObject1 = a(localWUPTask, localWUPTask.getPacketSize(), (MultiWUPRequestBase)localObject4);
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      ((MultiWUPRequestBase)localObject4).setResponses((ArrayList)localObject1);
      ((MultiWUPRequestBase)localObject4).addNetTime(???.mNetTimeList);
      ((MultiWUPRequestBase)localObject4).addThreadWaitTime(Long.valueOf(???.mThreadWaitTime));
      ((MultiWUPRequestBase)localObject4).setExecuteRes((byte)1);
      if (this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().shouldPendWUPResponses()) {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          if (!this.jdField_a_of_type_Boolean)
          {
            filterSplashCandoRequest((MultiWUPRequestBase)localObject4);
            if (this.jdField_a_of_type_JavaUtilArrayList == null) {
              this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
            }
            ((MultiWUPRequestBase)localObject4).addPath("mrp");
            this.jdField_a_of_type_JavaUtilArrayList.add(localObject4);
            return;
          }
        }
      }
      onMultiTaskSucc((MultiWUPRequestBase)localObject4);
      return;
    }
    ((MultiWUPRequestBase)localObject4).addPath("mde");
    ((MultiWUPRequestBase)localObject4).setErrorCode(62535);
    ???.mErrorCode = 62535;
    a(???);
  }
  
  public void onTaskCreated(Task paramTask) {}
  
  public void onTaskExtEvent(Task paramTask) {}
  
  public void onTaskFailed(Task paramTask)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onTaskFailed: ");
    ((StringBuilder)???).append(paramTask);
    FLogger.d("WUPTaskProxy", ((StringBuilder)???).toString());
    WUPTask localWUPTask = (WUPTask)paramTask;
    WUPRequestBase localWUPRequestBase = (WUPRequestBase)localWUPTask.getBindObject();
    if (localWUPRequestBase == null) {
      return;
    }
    if (UrlUtils.isIpv6Url(paramTask.getTaskUrl()))
    {
      ??? = new HashMap();
      ((HashMap)???).put("type", "ipv6_wup_fail");
      ((HashMap)???).put("k1", paramTask.getTaskUrl());
      EventEmiter.getDefault().emit(new EventMessage("com.tencent.mtt.operation.res.OperationResManager.STAT_WITH_BEACON", ???));
      StatServerHolder.userBehaviorStatistics("AWNWF53_IPV6_WUP_0");
    }
    if (paramTask.mErrorCode == 63524)
    {
      if (localWUPRequestBase.getTriedTimes() < 3)
      {
        paramTask = new StringBuilder();
        paramTask.append("request failed due to token, retry now, retry times=");
        paramTask.append(localWUPRequestBase.getTriedTimes());
        FLogger.d("WUPTask", paramTask.toString());
        localWUPRequestBase.addTridTimes();
        a(localWUPRequestBase, localWUPTask);
        return;
      }
      ??? = new StringBuilder();
      ((StringBuilder)???).append("request failed due to token, but we have tried ");
      ((StringBuilder)???).append(localWUPRequestBase.getTriedTimes());
      ((StringBuilder)???).append(", times, bye bye!");
      FLogger.d("WUPTask", ((StringBuilder)???).toString());
      paramTask.mNeedRetryNow = false;
    }
    localWUPRequestBase.setEndTime(System.currentTimeMillis());
    Object localObject2 = localWUPTask.getTaskUrl();
    ??? = localObject2;
    if (localObject2 != null)
    {
      ??? = localObject2;
      if (((String)localObject2).startsWith("http")) {
        ??? = ((String)localObject2).substring(7);
      }
    }
    localWUPRequestBase.setCurrentIP((String)???);
    ??? = localWUPTask.getTaskUrlBeforeDns();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("onTaskFailed, retry ");
    ((StringBuilder)localObject2).append(localWUPRequestBase.getTriedIPSize());
    ((StringBuilder)localObject2).append(" times, current ip=");
    ((StringBuilder)localObject2).append((String)???);
    ((StringBuilder)localObject2).append(", request=");
    ((StringBuilder)localObject2).append(localWUPRequestBase.getServerName());
    FLogger.d("debugWUP", ((StringBuilder)localObject2).toString());
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("wup task FAIL, curr ip = ");
    ((StringBuilder)localObject2).append(localWUPTask.getTaskUrl());
    ((StringBuilder)localObject2).append(", before dns = ");
    ((StringBuilder)localObject2).append(localWUPTask.getTaskUrlBeforeDns());
    FLogger.d("wup-ip-list", ((StringBuilder)localObject2).toString());
    a(localWUPTask);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("WUPIPList onTaskFailed url=");
    ((StringBuilder)localObject2).append((String)???);
    FLogger.d("debugWUP", ((StringBuilder)localObject2).toString());
    if ((localWUPRequestBase.getRequestPolicy() == RequestPolicy.MAX_RETRY_POLICY) && (paramTask.mNeedRetryNow) && (localWUPTask.getResponseData() == null) && (!localWUPTask.hasSetServer()) && (Apn.isNetworkAvailable()) && (localWUPRequestBase.getTriedIPSize() < 1))
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onTaskFailed url=");
      ((StringBuilder)localObject2).append((String)???);
      FLogger.d("debugWUP", ((StringBuilder)localObject2).toString());
      localObject2 = WupServerConfigsWrapper.getWupProxyAddress(this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a());
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onTaskFailed serverNow=");
      localStringBuilder.append((String)localObject2);
      FLogger.d("debugWUP", localStringBuilder.toString());
      int i;
      if (!StringUtils.isStringEqualsIgnoreCase((String)localObject2, (String)???))
      {
        FLogger.d("debugWUP", "onTaskFailed serverNow!=url retry");
        i = 1;
      }
      else
      {
        i = WupServerConfigsWrapper.getWupAddressIndex(this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(localWUPRequestBase.getServerName());
        ((StringBuilder)localObject2).append(localWUPRequestBase.getFuncName());
        localObject2 = WupServerConfigsWrapper.getNextWupProxyAddress(((StringBuilder)localObject2).toString(), this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a());
        if (i != WupServerConfigsWrapper.getWupAddressIndex(this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a())) {
          WupServerConfigsWrapper.setWupAddressReason("previous_request_failed");
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onTaskFailed serverNext=");
        localStringBuilder.append((String)localObject2);
        FLogger.d("debugWUP", localStringBuilder.toString());
        if (!StringUtils.isStringEqualsIgnoreCase((String)localObject2, (String)???))
        {
          FLogger.d("debugWUP", "onTaskFailed serverNext!=url retry");
          i = 1;
        }
        else
        {
          FLogger.d("debugWUP", "onTaskFailed has no server");
          i = 0;
        }
      }
      if (i != 0)
      {
        localWUPRequestBase.addTriedIP((String)???);
        a(localWUPRequestBase, localWUPTask);
        return;
      }
    }
    if (localWUPTask.mErrorCode != 0) {
      localWUPRequestBase.setErrorCode(localWUPTask.mErrorCode);
    }
    localWUPRequestBase.addTaskPaths(localWUPTask.getStatPath());
    if (localWUPTask.getPacketSize() == 1)
    {
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().statFlow(localWUPRequestBase, localWUPTask);
      localWUPRequestBase.setNetworkStatus(paramTask.getNetworkStatus());
      localWUPRequestBase.setFailedReason(paramTask.mFailedReason);
      localWUPRequestBase.addNetTime(localWUPTask.mNetTimeList);
      localWUPRequestBase.addThreadWaitTime(Long.valueOf(localWUPTask.mThreadWaitTime));
      localWUPRequestBase.setWupResolvedAddrInfo(localWUPTask.getDnsIP(), localWUPTask.getDnsType());
      paramTask = localWUPRequestBase.getRequestCallBack();
      if (paramTask == null)
      {
        localWUPRequestBase.addPath("scn");
        this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction(localWUPRequestBase);
        return;
      }
      localWUPRequestBase.addPath("sfail");
      localWUPRequestBase.setExecuteRes((byte)0);
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().sendStatAction(localWUPRequestBase);
      paramTask.onWUPTaskFail(localWUPRequestBase);
      return;
    }
    if (this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().shouldPendWUPResponses()) {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (!this.jdField_a_of_type_Boolean)
        {
          if (this.b == null) {
            this.b = new ArrayList();
          }
          this.b.add(paramTask);
          return;
        }
      }
    }
    a(paramTask);
  }
  
  public void onTaskProgress(Task paramTask) {}
  
  public void onTaskStarted(Task paramTask) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPTaskObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */