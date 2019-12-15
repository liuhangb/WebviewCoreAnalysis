package com.tencent.common.serverconfig;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.connectivitydetect.ConnectivityDetector;
import com.tencent.common.dns.IDnsResolver;
import com.tencent.common.manifest.annotation.CreateMethod;
import com.tencent.common.manifest.annotation.ServiceImpl;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.StringUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.network.QBUrl;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ServiceImpl(createMethod=CreateMethod.GET, service=IDnsResolver.class)
public class DnsManager
  implements IDnsResolver
{
  private Context jdField_a_of_type_AndroidContentContext = ContextHolder.getAppContext();
  Object jdField_a_of_type_JavaLangObject = new Object();
  HashMap<String, Long> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private Random jdField_a_of_type_JavaUtilRandom = new Random();
  private Pattern jdField_a_of_type_JavaUtilRegexPattern = null;
  private Object jdField_b_of_type_JavaLangObject = new Object();
  private HashMap<String, HashSet<DnsData>> jdField_b_of_type_JavaUtilHashMap = null;
  private HashMap<String, ReentrantLock> c = new HashMap();
  
  private DnsManager()
  {
    try
    {
      Security.setProperty("networkaddress.cache.ttl", "0");
      this.jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile("(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])", 2);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private DnsData a(String paramString, boolean paramBoolean)
  {
    String str1 = a();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(str1);
    ((StringBuilder)localObject1).append(paramString);
    String str2 = ((StringBuilder)localObject1).toString();
    for (;;)
    {
      StringBuilder localStringBuilder1;
      synchronized (this.jdField_b_of_type_JavaLangObject)
      {
        localObject1 = this.jdField_b_of_type_JavaUtilHashMap;
        localStringBuilder1 = null;
        if (localObject1 == null)
        {
          FLogger.d("DnsManager", "getIPAdressFromCache : mIPAdressCache = null, return");
          return null;
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("getIPAdressFromCache BEGINS check dns: cacheKey = ");
        ((StringBuilder)localObject1).append(str2);
        FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
        Object localObject3 = (HashSet)this.jdField_b_of_type_JavaUtilHashMap.get(str2);
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("current cacheKey=");
        ((StringBuilder)localObject1).append(str2);
        ((StringBuilder)localObject1).append(": ");
        localObject1 = ((StringBuilder)localObject1).toString();
        if (localObject3 != null)
        {
          Object localObject4 = ((HashSet)localObject3).iterator();
          if (((Iterator)localObject4).hasNext())
          {
            DnsData localDnsData = (DnsData)((Iterator)localObject4).next();
            if (!a(localDnsData))
            {
              localStringBuilder2 = new StringBuilder();
              localStringBuilder2.append("getIPAdressFromCache, ");
              localStringBuilder2.append(localDnsData);
              localStringBuilder2.append(", is out of date, remove it");
              FLogger.d("DnsManager", localStringBuilder2.toString());
              ((Iterator)localObject4).remove();
              continue;
            }
            StringBuilder localStringBuilder2 = new StringBuilder();
            localStringBuilder2.append((String)localObject1);
            localStringBuilder2.append(localDnsData.mIP);
            localStringBuilder2.append(": ");
            localStringBuilder2.append(localDnsData.mFailTimes);
            localStringBuilder2.append("; ");
            localObject1 = localStringBuilder2.toString();
            continue;
          }
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("getIPAdressFromCache, ");
          ((StringBuilder)localObject4).append((String)localObject1);
          FLogger.d("DnsManager", ((StringBuilder)localObject4).toString());
          localObject3 = ((HashSet)localObject3).toArray();
          int i;
          if (localObject3 == null) {
            i = 0;
          } else {
            i = localObject3.length;
          }
          localObject1 = localStringBuilder1;
          j = i;
          if (i > 0)
          {
            localObject1 = a((Object[])localObject3);
            localStringBuilder1 = new StringBuilder();
            localStringBuilder1.append("getIPAdressFromCache, OK  key=");
            localStringBuilder1.append(str2);
            localStringBuilder1.append(", value=");
            localStringBuilder1.append(localObject1);
            FLogger.d("DnsManager", localStringBuilder1.toString());
            j = i;
          }
          if ((paramBoolean) && (j < 2)) {
            a(paramString, str1, true);
          }
          return (DnsData)localObject1;
        }
      }
      int j = -1;
      localObject1 = localStringBuilder1;
    }
  }
  
  private DnsData a(Object[] paramArrayOfObject)
  {
    if ((paramArrayOfObject != null) && (paramArrayOfObject.length != 0))
    {
      int j = 0;
      Object localObject1 = (DnsData)paramArrayOfObject[0];
      int i = 0;
      while (i < paramArrayOfObject.length)
      {
        localObject2 = localObject1;
        if (((DnsData)paramArrayOfObject[i]).mFailTimes < ((DnsData)localObject1).mFailTimes) {
          localObject2 = (DnsData)paramArrayOfObject[i];
        }
        i += 1;
        localObject1 = localObject2;
      }
      Object localObject2 = new ArrayList();
      i = j;
      while (i < paramArrayOfObject.length)
      {
        if (((DnsData)paramArrayOfObject[i]).mFailTimes == ((DnsData)localObject1).mFailTimes) {
          ((List)localObject2).add((DnsData)paramArrayOfObject[i]);
        }
        i += 1;
      }
      i = ((List)localObject2).size();
      if (i <= 1) {
        return (DnsData)localObject1;
      }
      return (DnsData)((List)localObject2).get(this.jdField_a_of_type_JavaUtilRandom.nextInt(i));
    }
    return null;
  }
  
  private ReentrantLock a(String paramString)
  {
    ReentrantLock localReentrantLock2 = (ReentrantLock)this.c.get(paramString);
    ReentrantLock localReentrantLock1 = localReentrantLock2;
    if (localReentrantLock2 == null)
    {
      localReentrantLock1 = new ReentrantLock();
      this.c.put(paramString, localReentrantLock1);
    }
    return localReentrantLock1;
  }
  
  private void a(final String paramString1, String paramString2, final boolean paramBoolean)
  {
    if (!ConnectivityDetector.detectWithTCPPing())
    {
      FLogger.d("DnsManager", "tryGetMoreDnsData begins, but current net is not available, ignore");
      return;
    }
    ??? = new StringBuilder();
    ((StringBuilder)???).append(paramString1);
    ((StringBuilder)???).append(paramString2);
    Object localObject2 = ((StringBuilder)???).toString();
    if (paramBoolean) {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        Long localLong = (Long)this.jdField_a_of_type_JavaUtilHashMap.get(localObject2);
        if (localLong != null)
        {
          long l = localLong.longValue();
          if (System.currentTimeMillis() - l < 900000L)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("tryGetMoreDnsData, domain=");
            ((StringBuilder)localObject2).append(paramString1);
            ((StringBuilder)localObject2).append(", netInfo=");
            ((StringBuilder)localObject2).append(paramString2);
            ((StringBuilder)localObject2).append(", req too frequent, ignore");
            FLogger.d("DnsManager", ((StringBuilder)localObject2).toString());
          }
        }
        else
        {
          this.jdField_a_of_type_JavaUtilHashMap.put(localObject2, Long.valueOf(System.currentTimeMillis()));
        }
      }
    }
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("tryGetMoreDnsData, needTencentDns=");
        ((StringBuilder)localObject).append(paramBoolean);
        FLogger.d("DnsManager", ((StringBuilder)localObject).toString());
        StringBuilder localStringBuilder;
        if (paramBoolean)
        {
          localObject = DnsManager.this.a(paramString1);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("tryGetMoreDnsData, from tencent dns ok, value=");
          localStringBuilder.append(((ArrayList)localObject).toString());
          FLogger.d("DnsManager", localStringBuilder.toString());
          DnsManager.a(DnsManager.this, paramString1, (ArrayList)localObject);
        }
        localObject = DnsManager.this.a(paramString1);
        if (DnsManager.this.a((DnsManager.DnsData)localObject))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("tryGetMoreDnsData, from sys dns ok, value=");
          localStringBuilder.append(localObject);
          FLogger.d("DnsManager", localStringBuilder.toString());
          DnsManager.this.a(paramString1, (DnsManager.DnsData)localObject);
        }
      }
    });
  }
  
  private void a(String paramString, ArrayList<DnsData> paramArrayList)
  {
    if ((paramArrayList != null) && (!paramArrayList.isEmpty()) && (!TextUtils.isEmpty(paramString)))
    {
      ??? = "";
      int j = 0;
      int i = 0;
      Object localObject1;
      for (;;)
      {
        localObject1 = ???;
        if (i >= paramArrayList.size()) {
          break;
        }
        localObject1 = (DnsData)paramArrayList.get(i);
        if ((localObject1 != null) && (!TextUtils.isEmpty(((DnsData)localObject1).mNetworkInfo)))
        {
          localObject1 = ((DnsData)localObject1).mNetworkInfo;
          break;
        }
        i += 1;
      }
      if (TextUtils.isEmpty((CharSequence)localObject1))
      {
        FLogger.d("DnsManager", "updateIPAdressCache: no available network info, ignore");
        return;
      }
      ??? = new StringBuilder();
      ((StringBuilder)???).append((String)localObject1);
      ((StringBuilder)???).append(paramString);
      String str = ((StringBuilder)???).toString();
      paramString = new StringBuilder();
      paramString.append("updateIPAdressCache: cacheKey = ");
      paramString.append(str);
      FLogger.d("DnsManager", paramString.toString());
      synchronized (this.jdField_b_of_type_JavaLangObject)
      {
        if (this.jdField_b_of_type_JavaUtilHashMap == null) {
          this.jdField_b_of_type_JavaUtilHashMap = new HashMap();
        }
        localObject1 = (HashSet)this.jdField_b_of_type_JavaUtilHashMap.get(str);
        i = j;
        paramString = (String)localObject1;
        if (localObject1 == null)
        {
          paramString = new HashSet();
          this.jdField_b_of_type_JavaUtilHashMap.put(str, paramString);
          i = j;
        }
        try
        {
          while (i < paramArrayList.size())
          {
            localObject1 = (DnsData)paramArrayList.get(i);
            if (a((DnsData)localObject1))
            {
              localObject1 = ((DnsData)localObject1).clone();
              paramString.add(localObject1);
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("updateIPAdressCache, key=");
              localStringBuilder.append(str);
              localStringBuilder.append(", data=");
              localStringBuilder.append(localObject1);
              localStringBuilder.append(", curr ip size=");
              localStringBuilder.append(paramString.size());
              FLogger.d("DnsManager", localStringBuilder.toString());
            }
            i += 1;
          }
          paramString = finally;
        }
        catch (Exception paramString)
        {
          paramString.printStackTrace();
          return;
        }
      }
    }
    FLogger.d("DnsManager", "updateIPAdressCache: data is null or empty, ignore");
  }
  
  private boolean a(String paramString)
  {
    return (e(paramString)) && (!b(paramString));
  }
  
  private DnsData b(String paramString)
  {
    ReentrantLock localReentrantLock = a(paramString);
    boolean bool = localReentrantLock.tryLock();
    Object localObject1;
    if (!bool)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("syncGetIPAddessFromTencentDNS, lock for ");
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append(" is already Locked, wait");
      FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
      localReentrantLock.lock();
    }
    for (;;)
    {
      try
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("syncGetIPAddessFromTencentDNS, lock for ");
        ((StringBuilder)localObject1).append(paramString);
        ((StringBuilder)localObject1).append(" acuqired, begin get ips");
        FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
        if (!bool)
        {
          Object localObject2 = a(paramString, false);
          localObject1 = localObject2;
          if (!a((DnsData)localObject2))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("syncGetIPAddessFromTencentDNS, from cache faile, domain=");
            ((StringBuilder)localObject1).append(paramString);
            FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
            localObject1 = null;
          }
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            FLogger.d("DnsManager", "syncGetIPAddessFromTencentDNS, dnsData cache not valid, begin real get");
            ArrayList localArrayList = a(paramString);
            localObject2 = localObject1;
            if (localArrayList.size() > 0)
            {
              localObject2 = (DnsData)localArrayList.get(this.jdField_a_of_type_JavaUtilRandom.nextInt(localArrayList.size()));
              if (a((DnsData)localObject2))
              {
                localObject1 = new StringBuilder();
                ((StringBuilder)localObject1).append("syncGetIPAddessFromTencentDNS, begin update current cache, value=");
                ((StringBuilder)localObject1).append(localObject2);
                FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
                a(paramString, localArrayList);
              }
              else
              {
                localObject2 = null;
              }
            }
          }
          return (DnsData)localObject2;
        }
      }
      finally
      {
        localReentrantLock.unlock();
      }
      localObject1 = null;
    }
  }
  
  private boolean b(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return true;
    }
    String[] arrayOfString = paramString.split("\\.");
    if (arrayOfString != null)
    {
      if (arrayOfString.length != 4) {
        return true;
      }
      paramString = arrayOfString[0];
      int i = 1;
      while (i < arrayOfString.length)
      {
        if (!StringUtils.isStringEqual(paramString, arrayOfString[i])) {
          return false;
        }
        paramString = arrayOfString[i];
        i += 1;
      }
      return true;
    }
    return true;
  }
  
  private boolean c(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = paramString.split("\\.");
    if (paramString != null)
    {
      if (paramString.length != 4) {
        return false;
      }
      int[] arrayOfInt = new int[4];
      int[] tmp34_33 = arrayOfInt;
      tmp34_33[0] = 0;
      int[] tmp38_34 = tmp34_33;
      tmp38_34[1] = 0;
      int[] tmp42_38 = tmp38_34;
      tmp42_38[2] = 0;
      int[] tmp46_42 = tmp42_38;
      tmp46_42[3] = 0;
      tmp46_42;
      int i = 0;
      try
      {
        while (i < arrayOfInt.length)
        {
          arrayOfInt[i] = Integer.valueOf(paramString[i]).intValue();
          i += 1;
        }
        if (arrayOfInt[0] == 10) {
          return true;
        }
        if ((arrayOfInt[0] == 172) && (16 <= arrayOfInt[1]) && (arrayOfInt[1] <= 31)) {
          return true;
        }
        return (arrayOfInt[0] == 192) && (arrayOfInt[1] == 168);
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return false;
      }
    }
    return false;
  }
  
  private boolean d(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return paramString.contains("127.0.0.1");
  }
  
  private boolean e(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      boolean bool = this.jdField_a_of_type_JavaUtilRegexPattern.matcher(paramString).matches();
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public static DnsManager getInstance()
  {
    return a.a;
  }
  
  DnsData a(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      int i;
      try
      {
        str = a();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("getIPAdressFromSystemDNS, domain=");
        ((StringBuilder)localObject1).append(paramString);
        FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
        localObject1 = InetAddress.getAllByName(paramString);
        if (localObject1 == null) {
          return null;
        }
        if (StringUtils.isStringEqual(str, a())) {
          break label426;
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("network has changed, before = ");
        ((StringBuilder)localObject1).append(str);
        ((StringBuilder)localObject1).append(", ignore these ips");
        FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
        return null;
      }
      catch (Throwable localThrowable)
      {
        String str;
        Object localObject1;
        DnsData localDnsData;
        Object localObject2;
        localThrowable.printStackTrace();
        if (localArrayList.isEmpty()) {
          continue;
        }
        i = this.jdField_a_of_type_JavaUtilRandom.nextInt(localArrayList.size());
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getIPAdressFromSystemDNS, finally return ip = ");
        localStringBuilder.append(((DnsData)localArrayList.get(i)).mIP);
        localStringBuilder.append(", domain=");
        localStringBuilder.append(paramString);
        FLogger.d("DnsManager", localStringBuilder.toString());
        return (DnsData)localArrayList.get(i);
        return null;
      }
      if (i < localObject1.length)
      {
        localDnsData = localObject1[i];
        if (localDnsData == null) {
          return null;
        }
        if ((localDnsData instanceof Inet6Address))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("updateIPAdressCache, is ipv6, value=");
          ((StringBuilder)localObject2).append(localDnsData.getHostAddress());
          FLogger.d("DnsManager", ((StringBuilder)localObject2).toString());
        }
        else
        {
          localObject2 = localDnsData.getHostAddress();
          if (a((String)localObject2))
          {
            localDnsData = new DnsData();
            localDnsData.jdField_a_of_type_JavaLangString = paramString;
            localDnsData.mIP = ((String)localObject2);
            localDnsData.jdField_a_of_type_Long = System.currentTimeMillis();
            localDnsData.mType = "SYS";
            localDnsData.b = 1200000L;
            localDnsData.mNetworkInfo = new String(str);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("updateIPAdressCache, is ipv4, value=");
            ((StringBuilder)localObject2).append(localDnsData);
            FLogger.d("DnsManager", ((StringBuilder)localObject2).toString());
            localArrayList.add(localDnsData);
          }
        }
        i += 1;
      }
      else
      {
        label426:
        i = 0;
      }
    }
  }
  
  String a()
  {
    int i = IPListUtils.getConnectType(this.jdField_a_of_type_AndroidContentContext);
    String str;
    if (i == 1) {
      str = IPListUtils.getWifiBSSID(this.jdField_a_of_type_AndroidContentContext);
    } else {
      str = IPListUtils.getConnectExtraInfo(this.jdField_a_of_type_AndroidContentContext);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(IPListUtils.getConnectTypeName(this.jdField_a_of_type_AndroidContentContext));
    localStringBuilder.append(i);
    localStringBuilder.append(str);
    localStringBuilder.append(IPListUtils.getMCC(this.jdField_a_of_type_AndroidContentContext));
    localStringBuilder.append(IPListUtils.getMNC(this.jdField_a_of_type_AndroidContentContext));
    return localStringBuilder.toString();
  }
  
  ArrayList<DnsData> a(String paramString)
  {
    localArrayList = new ArrayList();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("getIPAdressFromTencentDnsServer, domain=");
    ((StringBuilder)localObject1).append(paramString);
    FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
    try
    {
      localObject1 = a();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("http://119.29.29.29/d?dn=");
      ((StringBuilder)localObject2).append(paramString);
      localObject2 = new QBUrl(((StringBuilder)localObject2).toString());
      int i = 0;
      localObject2 = (HttpURLConnection)((QBUrl)localObject2).setQueenProxyEnable(false).setTag("dns").openConnection();
      ((HttpURLConnection)localObject2).setRequestProperty("User-Agent", "Tencent QQBrowser X5");
      ((HttpURLConnection)localObject2).setRequestProperty("Cache-Control", "no-cache");
      ((HttpURLConnection)localObject2).setConnectTimeout(5000);
      ((HttpURLConnection)localObject2).setReadTimeout(5000);
      ((HttpURLConnection)localObject2).setInstanceFollowRedirects(false);
      ((HttpURLConnection)localObject2).setUseCaches(false);
      ((HttpURLConnection)localObject2).connect();
      Object localObject3 = ((HttpURLConnection)localObject2).getInputStream();
      Object localObject4 = FileUtilsF.toByteArray((InputStream)localObject3);
      String str = new String(((ByteBuffer)localObject4).array(), 0, ((ByteBuffer)localObject4).position(), "UTF-8");
      FileUtilsF.getInstance().releaseByteBuffer((ByteBuffer)localObject4);
      ((InputStream)localObject3).close();
      ((HttpURLConnection)localObject2).disconnect();
      if (!StringUtils.isStringEqual((String)localObject1, a()))
      {
        paramString = new StringBuilder();
        paramString.append("network has changed, before = ");
        paramString.append((String)localObject1);
        paramString.append(", ignore these ips");
        FLogger.d("DnsManager", paramString.toString());
        return localArrayList;
      }
      localObject2 = str.split(";");
      while (i < localObject2.length)
      {
        if (a(localObject2[i]))
        {
          localObject3 = new DnsData();
          ((DnsData)localObject3).jdField_a_of_type_JavaLangString = paramString;
          ((DnsData)localObject3).mIP = localObject2[i];
          ((DnsData)localObject3).jdField_a_of_type_Long = System.currentTimeMillis();
          ((DnsData)localObject3).mType = "TENCENT";
          ((DnsData)localObject3).mNetworkInfo = new String((String)localObject1);
          localArrayList.add(localObject3);
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("getIPAdressFromTencentDnsServer, ok, domain=");
          ((StringBuilder)localObject4).append(paramString);
          ((StringBuilder)localObject4).append(", value=");
          ((StringBuilder)localObject4).append(localObject3);
          FLogger.d("DnsManager", ((StringBuilder)localObject4).toString());
        }
        i += 1;
      }
      return localArrayList;
    }
    catch (Throwable paramString)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("getIPAdressFromTencentDnsServer, Failed error = ");
      ((StringBuilder)localObject1).append(paramString.getMessage());
      FLogger.d("DnsManager", ((StringBuilder)localObject1).toString());
      paramString.printStackTrace();
    }
  }
  
  void a(String paramString, DnsData paramDnsData)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramDnsData);
    a(paramString, localArrayList);
  }
  
  boolean a(DnsData paramDnsData)
  {
    if (paramDnsData == null) {
      return false;
    }
    if (paramDnsData.a()) {
      return false;
    }
    if ((a(paramDnsData.mIP)) && (!c(paramDnsData.mIP))) {
      return !d(paramDnsData.mIP);
    }
    return false;
  }
  
  public DnsData getIPAddressSync(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getIPAdressSync, from cache, domain=");
    ((StringBuilder)localObject).append(paramString);
    FLogger.d("DnsManager", ((StringBuilder)localObject).toString());
    localObject = a(paramString, true);
    if (localObject != null)
    {
      paramString = new StringBuilder();
      paramString.append("getIPAdressSync, from cache SUCC, value=");
      paramString.append(localObject);
      FLogger.d("DnsManager", paramString.toString());
      return (DnsData)localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getIPAdressSync, from cache fail, now get from tencent dns, domain=");
    ((StringBuilder)localObject).append(paramString);
    FLogger.d("DnsManager", ((StringBuilder)localObject).toString());
    localObject = b(paramString);
    StringBuilder localStringBuilder;
    if (localObject != null)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("getIPAdressSync, from tencent dns SUCC, value=");
      localStringBuilder.append(localObject);
      FLogger.d("DnsManager", localStringBuilder.toString());
      a(paramString, a(), false);
      return (DnsData)localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getIPAdressSync, from tencent dns fail, now get from sys dns, domain=");
    ((StringBuilder)localObject).append(paramString);
    FLogger.d("DnsManager", ((StringBuilder)localObject).toString());
    localObject = a(paramString);
    if (a((DnsData)localObject))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("getIPAdressSync, from sys dns SUCC, value=");
      localStringBuilder.append(localObject);
      FLogger.d("DnsManager", localStringBuilder.toString());
      a(paramString, (DnsData)localObject);
      return (DnsData)localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getIPAdressSync, from sys dns fail, all fail, domain=");
    ((StringBuilder)localObject).append(paramString);
    FLogger.d("DnsManager", ((StringBuilder)localObject).toString());
    return null;
  }
  
  public String resolveDomain(String paramString)
  {
    paramString = getIPAddressSync(paramString);
    if (paramString != null) {
      return paramString.mIP;
    }
    return null;
  }
  
  public class DnsData
  {
    long jdField_a_of_type_Long;
    String jdField_a_of_type_JavaLangString;
    long b = 1200000L;
    public int mFailTimes = 0;
    public String mIP;
    public String mNetworkInfo = "";
    public int mPort = -1;
    public String mType = "NULL";
    
    public DnsData() {}
    
    boolean a()
    {
      return System.currentTimeMillis() >= this.jdField_a_of_type_Long + this.b;
    }
    
    public DnsData clone()
    {
      DnsData localDnsData = new DnsData(DnsManager.this);
      localDnsData.jdField_a_of_type_JavaLangString = this.jdField_a_of_type_JavaLangString;
      localDnsData.mIP = this.mIP;
      localDnsData.jdField_a_of_type_Long = this.jdField_a_of_type_Long;
      localDnsData.b = this.b;
      localDnsData.mType = this.mType;
      localDnsData.mNetworkInfo = this.mNetworkInfo;
      return localDnsData;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof DnsData;
      boolean bool2 = false;
      if (bool1)
      {
        paramObject = (DnsData)paramObject;
        bool1 = bool2;
        if (StringUtils.isStringEqual(this.mIP, ((DnsData)paramObject).mIP))
        {
          bool1 = bool2;
          if (StringUtils.isStringEqual(this.jdField_a_of_type_JavaLangString, ((DnsData)paramObject).jdField_a_of_type_JavaLangString)) {
            bool1 = true;
          }
        }
        return bool1;
      }
      return false;
    }
    
    public int hashCode()
    {
      boolean bool = TextUtils.isEmpty(this.mIP);
      int j = 0;
      int i;
      if (bool) {
        i = 0;
      } else {
        i = this.mIP.hashCode();
      }
      if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) {
        j = this.jdField_a_of_type_JavaLangString.hashCode();
      }
      return i ^ j;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[domain=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", ip=");
      localStringBuilder.append(this.mIP);
      localStringBuilder.append(", TTL=");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", expired=");
      localStringBuilder.append(a());
      localStringBuilder.append(", type=");
      localStringBuilder.append(this.mType);
      localStringBuilder.append(", netInfo=");
      localStringBuilder.append(this.mNetworkInfo);
      localStringBuilder.append(", failTimes=");
      localStringBuilder.append(this.mFailTimes);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  private static class a
  {
    static final DnsManager a = new DnsManager(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\DnsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */