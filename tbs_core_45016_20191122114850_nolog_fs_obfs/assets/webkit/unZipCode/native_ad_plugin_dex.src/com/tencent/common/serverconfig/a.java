package com.tencent.common.serverconfig;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.serverconfig.netchecker.BaseWupSelfChecker;
import com.tencent.common.serverconfig.netchecker.BaseWupSelfChecker.ISelfCheckCallback;
import com.tencent.common.serverconfig.netchecker.WupDomainSelfChecker;
import com.tencent.common.serverconfig.netchecker.WupIPV4ListSelfChecker;
import com.tencent.common.serverconfig.netchecker.WupIPV6SelfChecker;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.wup.WUPProxyHolder;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.mtt.ContextHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class a
{
  private int jdField_a_of_type_Int = -1;
  private Context jdField_a_of_type_AndroidContentContext = ContextHolder.getAppContext();
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
  private ConnectivityChangeHandler jdField_a_of_type_ComTencentCommonServerconfigConnectivityChangeHandler = null;
  private String jdField_a_of_type_JavaLangString = "";
  private HashMap<String, ArrayList<String>> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private int jdField_b_of_type_Int = 1;
  private String jdField_b_of_type_JavaLangString = "";
  private String c = null;
  
  private void a(boolean paramBoolean, String paramString)
  {
    IWUPClientProxy localIWUPClientProxy = WUPProxyHolder.getPublicWUPProxy();
    if (localIWUPClientProxy != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_wup_server_ever_failed_");
      localStringBuilder.append(paramString);
      localIWUPClientProxy.setBooleanConfiguration(localStringBuilder.toString(), paramBoolean);
    }
  }
  
  private boolean a(boolean paramBoolean)
  {
    FLogger.d("WupServerConfigs", "WUPIPList startSelfCheckIPV6List");
    if (IPListDataManager.getInstance().getServerList(this.jdField_b_of_type_JavaLangString, true).size() > 0)
    {
      WupIPV6SelfChecker localWupIPV6SelfChecker = new WupIPV6SelfChecker(this.jdField_b_of_type_JavaLangString, this.jdField_a_of_type_AndroidContentContext);
      localWupIPV6SelfChecker.setCallback(new BaseWupSelfChecker.ISelfCheckCallback()
      {
        public void onSelfCheckResult(String paramAnonymousString, List<String> paramAnonymousList)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("WUPIPList startSelfCheckIPV6List onSelfCheckResult availableAddress=");
          localStringBuilder.append(paramAnonymousList.size());
          FLogger.d("WupServerConfigs", localStringBuilder.toString());
          if (paramAnonymousList.size() > 0)
          {
            a.a(a.this).put(paramAnonymousString, new ArrayList(paramAnonymousList));
            IPListDataManager.setWupServerEnable(paramAnonymousString, true);
            FLogger.d("wup-ip-list", "------------ipv6 is available----------");
            return;
          }
          a.a(a.this).remove(paramAnonymousString);
          FLogger.d("wup-ip-list", "------------ipv6 is not available----------");
        }
      });
      return localWupIPV6SelfChecker.startSelfCheck(paramBoolean);
    }
    return false;
  }
  
  private String c()
  {
    Object localObject1 = IPListUtils.removeScheme(this.jdField_a_of_type_JavaLangString);
    Object localObject2 = (ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(this.jdField_b_of_type_JavaLangString);
    if ((localObject2 != null) && (((ArrayList)localObject2).contains(localObject1)) && (((ArrayList)localObject2).size() > 1))
    {
      this.jdField_b_of_type_Int = 2;
      ((ArrayList)localObject2).remove(localObject1);
      localObject1 = IPListUtils.resolveValidIP((String)((ArrayList)localObject2).get(0));
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("WUPIPList getFromNextWupList ip=");
      ((StringBuilder)localObject2).append((String)localObject1);
      FLogger.d("WupServerConfigs", ((StringBuilder)localObject2).toString());
      return (String)localObject1;
    }
    if (localObject2 != null) {
      this.jdField_a_of_type_JavaUtilHashMap.remove(this.jdField_b_of_type_JavaLangString);
    } else {
      this.jdField_a_of_type_Int += 1;
    }
    if (this.jdField_a_of_type_Int < 0) {
      this.jdField_a_of_type_Int = 0;
    }
    this.jdField_b_of_type_Int = 1;
    localObject1 = IPListDataManager.getInstance().getServerList(this.jdField_b_of_type_JavaLangString);
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0) && (this.jdField_a_of_type_Int < ((ArrayList)localObject1).size())) {}
    try
    {
      localObject1 = (String)((ArrayList)localObject1).get(this.jdField_a_of_type_Int);
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("WUPIPList getFromNextWupList address=");
      ((StringBuilder)localObject2).append((String)localObject1);
      FLogger.d("WupServerConfigs", ((StringBuilder)localObject2).toString());
      localObject1 = IPListUtils.resolveValidIP((String)localObject1);
      return (String)localObject1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("WUPIPList getFromNextWupList empty mWupListIndex=");
    ((StringBuilder)localObject1).append(this.jdField_a_of_type_Int);
    FLogger.d("WupServerConfigs", ((StringBuilder)localObject1).toString());
    return "";
    return "";
  }
  
  public int a()
  {
    try
    {
      int i = this.jdField_a_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String a()
  {
    try
    {
      Object localObject1 = (ArrayList)this.jdField_a_of_type_JavaUtilHashMap.get(this.jdField_b_of_type_JavaLangString);
      if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
      {
        this.jdField_a_of_type_JavaLangString = IPListUtils.resolveValidIP((String)((ArrayList)localObject1).get(0));
        this.jdField_b_of_type_Int = 2;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("WUPIPList getWupProxyAddress mCurrentWupAddress=");
        ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaLangString);
        FLogger.d("WupServerConfigs", ((StringBuilder)localObject1).toString());
        localObject1 = this.jdField_a_of_type_JavaLangString;
        return (String)localObject1;
      }
      while (TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
        a("");
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("WUPIPList getWupProxyAddress mCurrentWupAddress=");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaLangString);
      FLogger.d("WupServerConfigs", ((StringBuilder)localObject1).toString());
      localObject1 = this.jdField_a_of_type_JavaLangString;
      return (String)localObject1;
    }
    finally {}
  }
  
  public String a(String paramString)
  {
    Object localObject = IPListUtils.getWUPNetEnvironment(this.jdField_a_of_type_AndroidContentContext);
    IPListDataManager.getInstance().getServerList((String)localObject);
    if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
      if (!WupProxyDomainRouter.isWupProxyDomains(this.jdField_a_of_type_JavaLangString))
      {
        paramString = new StringBuilder();
        paramString.append("getNextWupProxyAddress, current ip=");
        paramString.append(this.jdField_a_of_type_JavaLangString);
        paramString.append(", check wup server");
        FLogger.d("WupServerConfigs", paramString.toString());
        d("pre_request_failed_current_not_domain");
      }
      else if (!"proxyipgetIPListByRouter".equalsIgnoreCase(paramString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getNextWupProxyAddress, current request=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(", check wup server");
        FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
        d("pre_request_failed_current_not_iplist");
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getNextWupProxyAddress, current request=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(", do not check wup server");
        FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
      }
    }
    if (IPListDataManager.isWupserverValidate(this.jdField_b_of_type_JavaLangString))
    {
      paramString = c();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("get net wup server from currentNetEnvironment, ip=");
      ((StringBuilder)localObject).append(paramString);
      FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
      if (!TextUtils.isEmpty(paramString))
      {
        if (this.jdField_a_of_type_Int != 0) {
          a(true, this.jdField_b_of_type_JavaLangString);
        }
        this.jdField_a_of_type_JavaLangString = paramString;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getNextWupProxyAddress at wuplist  server=");
        ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
        FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
        return paramString;
      }
      FLogger.d("WupServerConfigs", "get net wup server from currentNetEnvironment, ip empty, set invalidate");
      IPListDataManager.setWupServerEnable(this.jdField_b_of_type_JavaLangString, false);
      a();
    }
    this.jdField_a_of_type_JavaLangString = WupProxyDomainRouter.getInstance().getWupProxyDomain(this.jdField_b_of_type_JavaLangString);
    this.jdField_b_of_type_Int = 0;
    FLogger.d("WupServerConfigs", "getNextWupProxyAddress SHIT, TRY DOMAIN");
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public void a()
  {
    FLogger.d("WupServerConfigs", "WUPIPList updateWupServerList");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(1));
    WUPTaskProxy.send(WupServerConfigsWrapper.a(localArrayList));
  }
  
  public void a(Intent paramIntent)
  {
    this.jdField_a_of_type_ComTencentCommonServerconfigConnectivityChangeHandler.onConnectivityIntent(paramIntent);
  }
  
  public void a(String paramString)
  {
    this.jdField_a_of_type_Int = -1;
    this.jdField_a_of_type_JavaLangString = "";
    c(paramString);
    FLogger.d("WupServerConfigs", "resetWupIPV4State");
  }
  
  public void a(String paramString, ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      FLogger.d("debugWUP", "saveWupProxyList,  save");
      IPListDataManager.getInstance().updateServerList(paramString, paramArrayList);
      IPListDataManager.setWupServerEnable(paramString, true);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("saveWupProxyList wup server .enable  netinfo=");
      localStringBuilder.append(paramString);
      localStringBuilder.append(", list=");
      localStringBuilder.append(paramArrayList);
      FLogger.d("WupServerConfigs", localStringBuilder.toString());
      a(false, paramString);
      b("receive_ip_list");
      a(false);
      return;
    }
  }
  
  protected boolean a()
  {
    IPListDataManager.getInstance().getServerList(this.jdField_b_of_type_JavaLangString);
    boolean bool = IPListDataManager.isWupserverValidate(this.jdField_b_of_type_JavaLangString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("current netinfo is ");
    localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
    localStringBuilder.append(", check if we have current IP? ");
    localStringBuilder.append(bool);
    FLogger.d("wup-ip-list", localStringBuilder.toString());
    return bool;
  }
  
  public String b()
  {
    return this.c;
  }
  
  public void b()
  {
    WupDomainSelfChecker localWupDomainSelfChecker = new WupDomainSelfChecker(this.jdField_b_of_type_JavaLangString, this.jdField_a_of_type_AndroidContentContext);
    localWupDomainSelfChecker.setCallback(WupProxyDomainRouter.getInstance());
    localWupDomainSelfChecker.startSelfCheck();
  }
  
  public void b(String paramString)
  {
    this.jdField_a_of_type_Int = -1;
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_a_of_type_JavaUtilHashMap.remove(this.jdField_b_of_type_JavaLangString);
    WupIPV6SelfChecker.resetCheckTime();
    c(paramString);
    FLogger.d("WupServerConfigs", "resetWupIPV4State");
  }
  
  protected boolean b()
  {
    Object localObject = WUPProxyHolder.getPublicWUPProxy();
    boolean bool = false;
    if (localObject != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("key_wup_server_ever_failed_");
      localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
      bool = ((IWUPClientProxy)localObject).getBooleanConfiguration(localStringBuilder.toString(), false);
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("current netinfo is ");
    ((StringBuilder)localObject).append(this.jdField_b_of_type_JavaLangString);
    ((StringBuilder)localObject).append(", check if these IPs have ever failed? ");
    ((StringBuilder)localObject).append(bool);
    FLogger.d("wup-ip-list", ((StringBuilder)localObject).toString());
    return bool;
  }
  
  public void c(String paramString)
  {
    this.c = paramString;
  }
  
  public boolean c()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("harryguo startSelfCheckIPV4List mWupListIndex=");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_Int);
    FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
    if (this.jdField_a_of_type_Int > 0)
    {
      localObject = new WupIPV4ListSelfChecker(this.jdField_b_of_type_JavaLangString, this.jdField_a_of_type_AndroidContentContext);
      ((BaseWupSelfChecker)localObject).setCallback(new BaseWupSelfChecker.ISelfCheckCallback()
      {
        public void onSelfCheckResult(String paramAnonymousString, List<String> paramAnonymousList)
        {
          if (paramAnonymousList == null) {
            return;
          }
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("WUPIPList startSelfCheckIPV4List onSelfCheckResult availableAddress=");
          ((StringBuilder)localObject1).append(paramAnonymousList.size());
          FLogger.d("WupServerConfigs", ((StringBuilder)localObject1).toString());
          localObject1 = IPListDataManager.getInstance();
          boolean bool2 = paramAnonymousList.isEmpty();
          boolean bool1 = true;
          if (bool2)
          {
            FLogger.d("WupIPListSelfChecker", "after check, availableIPs is empty, need request");
            IPListDataManager.setWupServerEnable(paramAnonymousString, false);
            a.a(a.this, true, paramAnonymousString);
            a.this.a("requst_failed_check");
            a.this.a();
            a.a(a.this, false);
          }
          else
          {
            Object localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("after check, availableIPs number = ");
            ((StringBuilder)localObject2).append(paramAnonymousList.size());
            FLogger.d("WupIPListSelfChecker", ((StringBuilder)localObject2).toString());
            IPListDataManager.setWupServerEnable(paramAnonymousString, true);
            localObject2 = ((IPListDataManager)localObject1).getServerList(paramAnonymousString);
            if ((localObject2 != null) && (!((List)localObject2).isEmpty()) && (paramAnonymousList.size() != ((List)localObject2).size()))
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("after check, currIps number = ");
              localStringBuilder.append(((List)localObject2).size());
              localStringBuilder.append(", need replace");
              FLogger.d("WupIPListSelfChecker", localStringBuilder.toString());
              ((IPListDataManager)localObject1).updateServerList(paramAnonymousString, new ArrayList(paramAnonymousList));
              ((IPListDataManager)localObject1).saveServerList();
              a.a(a.this, true, paramAnonymousString);
              a.this.a("requst_failed_check");
            }
            else
            {
              bool1 = false;
            }
          }
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("check complete, hasFoundBadIp= ");
          paramAnonymousString.append(bool1);
          FLogger.d("WupIPListSelfChecker", paramAnonymousString.toString());
        }
      });
      return ((BaseWupSelfChecker)localObject).startSelfCheck();
    }
    return false;
  }
  
  public void d(final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        a.this.e(paramString);
      }
    });
  }
  
  void e(String paramString)
  {
    String str = IPListUtils.getWUPNetEnvironment(this.jdField_a_of_type_AndroidContentContext);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkWupNetEnvironment: old netInfo=");
    ((StringBuilder)localObject).append(this.jdField_b_of_type_JavaLangString);
    ((StringBuilder)localObject).append(", lsit=");
    ((StringBuilder)localObject).append(IPListDataManager.getInstance().getServerList(this.jdField_b_of_type_JavaLangString));
    FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkWupNetEnvironment: old netInfo=");
    ((StringBuilder)localObject).append(this.jdField_b_of_type_JavaLangString);
    ((StringBuilder)localObject).append(", current netinfo=");
    ((StringBuilder)localObject).append(str);
    FLogger.d("WupServerConfigs", ((StringBuilder)localObject).toString());
    if (str.equalsIgnoreCase(this.jdField_b_of_type_JavaLangString))
    {
      if (!IPListDataManager.isWupserverValidate(str))
      {
        FLogger.d("WupServerConfigs", "checkWupNetEnvironment: old new netInfo equal, but current server invalidate, get it");
        a();
        return;
      }
      FLogger.d("WupServerConfigs", "checkWupNetEnvironment: old new netInfo equal, and can use");
      return;
    }
    FLogger.d("WupServerConfigs", "checkWupNetEnvironment: old new netInfo not equal");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("network changed, old netInfo=");
    ((StringBuilder)localObject).append(this.jdField_b_of_type_JavaLangString);
    ((StringBuilder)localObject).append(", current netinfo=");
    ((StringBuilder)localObject).append(str);
    FLogger.d("wup-ip-list", ((StringBuilder)localObject).toString());
    this.jdField_b_of_type_JavaLangString = str;
    localObject = IPListDataManager.getInstance().getServerList(str);
    if ((localObject != null) && (((ArrayList)localObject).size() > 0) && (IPListDataManager.isWupserverValidate(str)) && (!b()))
    {
      FLogger.d("WupServerConfigs", "WUPIPList checkWupNetEnvironment: we have new netinfo servers, use it and reset wup state");
      if (this.jdField_b_of_type_Int == 2)
      {
        b(paramString);
        return;
      }
      a(paramString);
      return;
    }
    FLogger.d("WupServerConfigs", "WUPIPList checkWupNetEnvironment: do not have new netinfo servers, try get one");
    IPListDataManager.setWupServerEnable(str, false);
    if (this.jdField_b_of_type_Int == 2) {
      b(paramString);
    } else {
      a(paramString);
    }
    a();
  }
  
  public void f(String paramString)
  {
    if (IPListUtils.isIPV4(paramString))
    {
      c();
      return;
    }
    a(true);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */