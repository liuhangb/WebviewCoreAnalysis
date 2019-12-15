package com.tencent.common.serverconfig;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.serverconfig.netchecker.BaseWupSelfChecker.ISelfCheckCallback;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.wup.WUPProxyHolder;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.mtt.ContextHolder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class WupProxyDomainRouter
  implements BaseWupSelfChecker.ISelfCheckCallback
{
  static final WupProxyAddress jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress = new WupProxyAddress("wup.imtt.qq.com", 8080);
  static final WupProxyAddress b = new WupProxyAddress("iwup.mtt.qq.com", 80);
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private HashSet<a> jdField_a_of_type_JavaUtilHashSet;
  
  private void a()
  {
    if (this.jdField_a_of_type_JavaUtilHashSet != null) {
      return;
    }
    FLogger.d("WupProxyDomainRouter", "loadPrimaryDisabledList: BEGIN");
    HashSet localHashSet = new HashSet();
    label448:
    label455:
    for (;;)
    {
      int j;
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        Object localObject3 = WUPProxyHolder.getPublicWUPProxy();
        if (this.jdField_a_of_type_JavaUtilHashSet != null) {
          return;
        }
        this.jdField_a_of_type_JavaUtilHashSet = new HashSet();
        if (localObject3 == null)
        {
          FLogger.d("WupProxyDomainRouter", "loadPrimaryDisabledList: client proxy is null, empty return");
          return;
        }
        localObject3 = ((IWUPClientProxy)localObject3).getStringConfiguration("CONFIG_NAME_PRIMARY_DISABLED_LIST", "");
        Object localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("loadPrimaryDisabledList: disbaledStr = ");
        ((StringBuilder)localObject4).append((String)localObject3);
        FLogger.d("WupProxyDomainRouter", ((StringBuilder)localObject4).toString());
        if (TextUtils.isEmpty((CharSequence)localObject3))
        {
          FLogger.d("WupProxyDomainRouter", "loadPrimaryDisabledList: END, empty disable list");
          return;
        }
        localObject3 = ((String)localObject3).split("\\|");
        j = 0;
        int i = j;
        if (localObject3 != null)
        {
          i = j;
          if (localObject3.length > 0)
          {
            int k = localObject3.length;
            j = 0;
            i = 0;
            if (j >= k) {
              break label455;
            }
            Object localObject5 = localObject3[j];
            if (TextUtils.isEmpty((CharSequence)localObject5)) {
              break label448;
            }
            int m = ((String)localObject5).lastIndexOf("$");
            localObject4 = ((String)localObject5).substring(0, m);
            long l = StringUtils.parseLong(((String)localObject5).substring(m + 1), -1L);
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("loadPrimaryDisabledList: one: net = ");
            ((StringBuilder)localObject5).append((String)localObject4);
            ((StringBuilder)localObject5).append(", time = ");
            ((StringBuilder)localObject5).append(l);
            FLogger.d("WupProxyDomainRouter", ((StringBuilder)localObject5).toString());
            localObject5 = new a((String)localObject4, l);
            if (!((a)localObject5).a())
            {
              localObject5 = new StringBuilder();
              ((StringBuilder)localObject5).append("loadPrimaryDisabledList: disable info [");
              ((StringBuilder)localObject5).append((String)localObject4);
              ((StringBuilder)localObject5).append("] is invalid, remove from disble list");
              FLogger.d("WupProxyDomainRouter", ((StringBuilder)localObject5).toString());
              i = 1;
              break label448;
            }
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("loadPrimaryDisabledList: put [");
            localStringBuilder.append((String)localObject4);
            localStringBuilder.append("] into disabled list");
            FLogger.d("WupProxyDomainRouter", localStringBuilder.toString());
            this.jdField_a_of_type_JavaUtilHashSet.add(localObject5);
            break label448;
          }
        }
        localHashSet.addAll(this.jdField_a_of_type_JavaUtilHashSet);
        if (i != 0)
        {
          FLogger.d("WupProxyDomainRouter", "loadPrimaryDisabledList: begin synchronize");
          a(localHashSet);
        }
        return;
      }
      j += 1;
    }
  }
  
  private void a(HashSet<a> paramHashSet)
  {
    IWUPClientProxy localIWUPClientProxy = WUPProxyHolder.getPublicWUPProxy();
    if (localIWUPClientProxy == null)
    {
      FLogger.d("WupProxyDomainRouter", "synchronizePrimaryDisabledList: but client proxy is null ,return");
      return;
    }
    Object localObject = new StringBuilder();
    if ((paramHashSet != null) && (!paramHashSet.isEmpty()))
    {
      paramHashSet = paramHashSet.iterator();
      while (paramHashSet.hasNext())
      {
        a locala = (a)paramHashSet.next();
        if ((locala != null) && (locala.a()))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(locala.jdField_a_of_type_JavaLangString);
          localStringBuilder.append("$");
          localStringBuilder.append(locala.jdField_a_of_type_Long);
          localStringBuilder.append("|");
          ((StringBuilder)localObject).append(localStringBuilder.toString());
        }
        else
        {
          FLogger.d("WupProxyDomainRouter", "synchronizePrimaryDisabledList: found one invalid config, ignore");
        }
      }
    }
    localObject = ((StringBuilder)localObject).toString();
    paramHashSet = (HashSet<a>)localObject;
    if (!TextUtils.isEmpty((CharSequence)localObject))
    {
      paramHashSet = (HashSet<a>)localObject;
      if (((String)localObject).endsWith("|")) {
        paramHashSet = ((String)localObject).substring(0, ((String)localObject).length() - 1);
      }
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("synchronizePrimaryDisabledList: serailize config = ");
    ((StringBuilder)localObject).append(paramHashSet);
    FLogger.d("WupProxyDomainRouter", ((StringBuilder)localObject).toString());
    localIWUPClientProxy.setStringConfiguration("CONFIG_NAME_PRIMARY_DISABLED_LIST", paramHashSet);
  }
  
  public static ArrayList<String> getAllWupProxyDomains()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress.domain);
    localArrayList.add(b.domain);
    return localArrayList;
  }
  
  public static ArrayList<String> getAllWupProxyHostNames()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress.host);
    localArrayList.add(b.host);
    return localArrayList;
  }
  
  public static WupProxyDomainRouter getInstance()
  {
    return b.a();
  }
  
  public static boolean isWupProxyDomains(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return getAllWupProxyDomains().contains(paramString);
  }
  
  public static boolean isWupProxyHost(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return getAllWupProxyHostNames().contains(paramString);
  }
  
  public String getWupProxyDomain()
  {
    return getWupProxyDomain(IPListUtils.getWUPNetEnvironment(ContextHolder.getAppContext()));
  }
  
  public String getWupProxyDomain(String paramString)
  {
    a();
    String str;
    if (this.jdField_a_of_type_JavaUtilHashSet.contains(new a(paramString, -1L))) {
      str = b.domain;
    } else {
      str = jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress.domain;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getWupProxyDomain: net = ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", retun = ");
    localStringBuilder.append(str);
    FLogger.d("WupProxyDomainRouter", localStringBuilder.toString());
    return str;
  }
  
  public WupProxyAddress getWupProxyHostName()
  {
    return getWupProxyHostName(IPListUtils.getWUPNetEnvironment(ContextHolder.getAppContext()));
  }
  
  public WupProxyAddress getWupProxyHostName(String paramString)
  {
    a();
    WupProxyAddress localWupProxyAddress;
    if (this.jdField_a_of_type_JavaUtilHashSet.contains(new a(paramString, -1L))) {
      localWupProxyAddress = b;
    } else {
      localWupProxyAddress = jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getWupProxyHostName: net = ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", retun = ");
    localStringBuilder.append(localWupProxyAddress);
    FLogger.d("WupProxyDomainRouter", localStringBuilder.toString());
    return localWupProxyAddress;
  }
  
  public void onSelfCheckResult(String paramString, List<String> arg2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onSelfCheckResult: netInfo [");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("], availableIPs = ");
    ((StringBuilder)localObject).append(???);
    FLogger.d("WupProxyDomainRouter", ((StringBuilder)localObject).toString());
    if ((??? != null) && (!???.isEmpty()))
    {
      localObject = ???.iterator();
      while (((Iterator)localObject).hasNext()) {
        if (!isWupProxyDomains((String)((Iterator)localObject).next()))
        {
          FLogger.d("WupProxyDomainRouter", "onSelfCheckResult: detection address contains non-WUP address, return do nothing!");
          return;
        }
      }
      a();
      localObject = new HashSet();
      if (???.contains(jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress.domain)) {
        ???.contains(b.domain);
      }
      paramString = new a(paramString, System.currentTimeMillis());
      boolean bool = ???.contains(jdField_a_of_type_ComTencentCommonServerconfigWupProxyDomainRouter$WupProxyAddress.domain);
      int i = 1;
      if (!bool)
      {
        FLogger.d("WupProxyDomainRouter", "onSelfCheckResult: primary address is NOT reachable!!");
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_a_of_type_JavaUtilHashSet.remove(paramString);
          this.jdField_a_of_type_JavaUtilHashSet.add(paramString);
          ((HashSet)localObject).addAll(this.jdField_a_of_type_JavaUtilHashSet);
        }
      }
      FLogger.d("WupProxyDomainRouter", "onSelfCheckResult: primary address is reachable, try remove from disable if needed!!");
      if (this.jdField_a_of_type_JavaUtilHashSet.contains(paramString)) {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_a_of_type_JavaUtilHashSet.remove(paramString);
          ((HashSet)localObject).addAll(this.jdField_a_of_type_JavaUtilHashSet);
          FLogger.d("WupProxyDomainRouter", "onSelfCheckResult: primary address is banned before, remove from disable");
        }
      }
      i = 0;
      if (i != 0)
      {
        FLogger.d("WupProxyDomainRouter", "onSelfCheckResult: disable list changed, need synchronize");
        a((HashSet)localObject);
      }
      return;
    }
    FLogger.d("WupProxyDomainRouter", "onSelfCheckResult: detection results indicate that no address valid, just do nothing!");
  }
  
  public static class WupProxyAddress
  {
    public String domain;
    public String host;
    public int port;
    
    public WupProxyAddress(String paramString, int paramInt)
    {
      this.host = paramString;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://");
      localStringBuilder.append(paramString);
      this.domain = localStringBuilder.toString();
      this.port = paramInt;
      if (paramInt != 80)
      {
        paramString = new StringBuilder();
        paramString.append(this.domain);
        paramString.append(":");
        paramString.append(paramInt);
        this.domain = paramString.toString();
      }
    }
  }
  
  private static class a
  {
    public long a;
    public String a;
    
    public a(String paramString, long paramLong)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public boolean a()
    {
      return (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_Long > 0L) && (System.currentTimeMillis() - this.jdField_a_of_type_Long < 172800000L);
    }
    
    public boolean equals(Object paramObject)
    {
      if ((paramObject instanceof a))
      {
        paramObject = (a)paramObject;
        return StringUtils.isStringEqual(this.jdField_a_of_type_JavaLangString, ((a)paramObject).jdField_a_of_type_JavaLangString);
      }
      return false;
    }
    
    public int hashCode()
    {
      if (TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
        return 0;
      }
      return this.jdField_a_of_type_JavaLangString.hashCode();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("DomainDisableInfo: { net:");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", time: ");
      localStringBuilder.append(this.jdField_a_of_type_Long);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
  }
  
  private static final class b
  {
    private static final WupProxyDomainRouter a = new WupProxyDomainRouter(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\WupProxyDomainRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */