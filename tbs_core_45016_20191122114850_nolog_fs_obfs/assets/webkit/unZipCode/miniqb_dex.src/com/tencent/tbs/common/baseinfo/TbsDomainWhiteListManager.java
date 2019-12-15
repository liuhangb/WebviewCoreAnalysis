package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.content.res.AssetManager;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.DomainWhiteListReq;
import com.tencent.tbs.common.MTT.DomainWhiteListRsp;
import com.tencent.tbs.common.MTT.TBSJSApiApiNames;
import com.tencent.tbs.common.MTT.TBSJSApiWhitelistReq;
import com.tencent.tbs.common.MTT.TBSJSApiWhitelistRsp;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.TbsFileUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TbsDomainWhiteListManager
  implements IWUPRequestCallBack
{
  private static final String TAG = "TbsDomainWhiteListManager";
  private static TbsDomainWhiteListManager mInstance;
  private IRequestDomainCallBack mCommDomainCallBack = null;
  private IDomainListCallBack mDomainListCallBack = null;
  private HashMap<Byte, Boolean> mIsGotDomains = new HashMap();
  private boolean mIsGuidValidateWhenRequestDomain = false;
  private IRequestDomainCallBack mJsApiCallBack = null;
  
  public static void deleteApiDomainFile()
  {
    Object localObject = TbsFileUtils.getJsApiWhiteListDataFileName();
    localObject = new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), (String)localObject);
    if (((File)localObject).exists()) {
      ((File)localObject).delete();
    }
  }
  
  public static ArrayList<String> getDefalutLbsDontAlertWhiteList()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("map.qq.com");
    return localArrayList;
  }
  
  public static String getDefaultJsApiDomainPath()
  {
    return "tbs_jsApi";
  }
  
  public static InputStream getDefaultJsApiDomainStream(String paramString)
    throws IOException
  {
    AssetManager localAssetManager = TbsBaseModuleShell.getContext().getAssets();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getDefaultJsApiDomainPath());
    localStringBuilder.append("/");
    localStringBuilder.append(paramString);
    return localAssetManager.open(localStringBuilder.toString());
  }
  
  private DomainWhiteListReq getDomainReq(boolean paramBoolean, byte paramByte)
  {
    DomainWhiteListReq localDomainWhiteListReq = new DomainWhiteListReq();
    if (paramBoolean)
    {
      localDomainWhiteListReq.iDomainTime = 0;
    }
    else if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
    {
      localDomainWhiteListReq.iDomainTime = PublicSettingManager.getInstance().getDomainTime(paramByte);
    }
    else if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO)
    {
      localDomainWhiteListReq.iDomainTime = TbsUserInfo.getInstance().getDomainTime();
    }
    else
    {
      localDomainWhiteListReq.iDomainTime = TbsUserInfo.getInstance().getDomainTime();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("dwlDomainTime = , req.iDomainTime = ");
      localStringBuilder.append(localDomainWhiteListReq.iDomainTime);
      LogUtils.d("TbsDomainWhiteListManager", localStringBuilder.toString());
    }
    localDomainWhiteListReq.sGUID = GUIDFactory.getInstance().getStrGuid();
    localDomainWhiteListReq.sQUA = TbsInfoUtils.getQUA();
    if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS)
    {
      localDomainWhiteListReq.sQua2ExInfo = "SR=TBS";
    }
    else if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("SR=TBSVIDEO&SRVER=");
      localStringBuilder.append(PublicSettingManager.getInstance().getTbsVideoVersion());
      localDomainWhiteListReq.sQua2ExInfo = localStringBuilder.toString();
    }
    else if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("SR=MINIQB&SRVER=");
      localStringBuilder.append(TbsBaseModuleShell.getMiniQBVersion());
      localDomainWhiteListReq.sQua2ExInfo = localStringBuilder.toString();
    }
    this.mIsGuidValidateWhenRequestDomain = GUIDFactory.getInstance().isGuidValidate();
    localDomainWhiteListReq.iGrayAreaType = TbsWupManager.getAreaTypeFromDisk();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("req.iGrayAreaType =");
    localStringBuilder.append(localDomainWhiteListReq.iGrayAreaType);
    LogUtils.d("TbsDomainWhiteListManager", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("req.iDomainTime =");
    localStringBuilder.append(localDomainWhiteListReq.iDomainTime);
    LogUtils.d("TbsDomainWhiteListManager", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("req.sGUID ");
    localStringBuilder.append(localDomainWhiteListReq.sGUID);
    LogUtils.d("TbsDomainWhiteListManager", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("req.sQUA ");
    localStringBuilder.append(localDomainWhiteListReq.sQUA);
    LogUtils.d("TbsDomainWhiteListManager", localStringBuilder.toString());
    return localDomainWhiteListReq;
  }
  
  public static TbsDomainWhiteListManager getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new TbsDomainWhiteListManager();
      }
      TbsDomainWhiteListManager localTbsDomainWhiteListManager = mInstance;
      return localTbsDomainWhiteListManager;
    }
    finally {}
  }
  
  public static boolean isJsApiOverValidTimeOrRange()
  {
    Object localObject = TbsFileUtils.FILE_JSAPI_WHITELIST_CONF;
    localObject = new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), (String)localObject);
    if (!((File)localObject).exists()) {
      return true;
    }
    long l = ((File)localObject).lastModified();
    if (System.currentTimeMillis() - l < 86400000L) {
      return ((File)localObject).length() > 30720L;
    }
    return true;
  }
  
  public static Map<String, TBSJSApiApiNames> loadJsApiWhiteList()
  {
    Object localObject = TbsFileUtils.FILE_JSAPI_WHITELIST_CONF;
    localObject = new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), (String)localObject);
    if (!((File)localObject).exists()) {
      return null;
    }
    try
    {
      localObject = FileUtils.read((File)localObject);
      if (localObject != null)
      {
        localObject = new JceInputStream((ByteBuffer)localObject);
        ((JceInputStream)localObject).setServerEncoding("UTF-8");
        TBSJSApiWhitelistRsp localTBSJSApiWhitelistRsp = new TBSJSApiWhitelistRsp();
        localTBSJSApiWhitelistRsp.readFrom((JceInputStream)localObject);
        TbsUserInfo.mJsApiSAuth = localTBSJSApiWhitelistRsp.sAuth;
        localObject = localTBSJSApiWhitelistRsp.mHostApiNames;
        return (Map<String, TBSJSApiApiNames>)localObject;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  private static byte[] mergeJsApiDomainList(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte1.length > 0) && (paramArrayOfByte2 != null) && (paramArrayOfByte2.length > 0))
    {
      Object localObject = new JceInputStream(paramArrayOfByte1);
      ((JceInputStream)localObject).setServerEncoding("UTF-8");
      paramArrayOfByte1 = new TBSJSApiWhitelistRsp();
      paramArrayOfByte1.readFrom((JceInputStream)localObject);
      Map localMap = paramArrayOfByte1.mHostApiNames;
      paramArrayOfByte2 = new JceInputStream(paramArrayOfByte2);
      ((JceInputStream)localObject).setServerEncoding("UTF-8");
      localObject = new TBSJSApiWhitelistRsp();
      ((TBSJSApiWhitelistRsp)localObject).readFrom(paramArrayOfByte2);
      paramArrayOfByte2 = ((TBSJSApiWhitelistRsp)localObject).mHostApiNames;
      if ((localMap != null) && (localMap.size() > 0) && (paramArrayOfByte2 != null) && (paramArrayOfByte2.size() > 0)) {
        localMap.putAll(paramArrayOfByte2);
      }
      paramArrayOfByte2 = new JceOutputStream();
      paramArrayOfByte2.setServerEncoding("UTF-8");
      paramArrayOfByte1.writeTo(paramArrayOfByte2);
      return paramArrayOfByte2.toByteArray();
    }
    return null;
  }
  
  public static void onJsApiWhiteList(WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPResponseBase != null)
    {
      paramWUPResponseBase = (TBSJSApiWhitelistRsp)paramWUPResponseBase.get("rsp");
      if (paramWUPResponseBase != null)
      {
        int i = paramWUPResponseBase.eRetCode;
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onJsApiWhiteList rspRet: ");
        ((StringBuilder)localObject).append(i);
        LogUtils.d("TbsDomainWhiteListManager", ((StringBuilder)localObject).toString());
        if (i != 0) {
          return;
        }
        localObject = paramWUPResponseBase.mHostApiNames;
        if ((localObject != null) && (((Map)localObject).size() > 0))
        {
          if (TbsUserInfo.getInstance().getJSApiDomainList() != null) {
            TbsUserInfo.getInstance().getJSApiDomainList().clear();
          }
          LogUtils.d("TbsDomainWhiteListManager", "configList clear");
          TbsUserInfo.setJSApiDomainList(paramWUPResponseBase.mHostApiNames);
        }
      }
      else
      {
        LogUtils.d("TbsDomainWhiteListManager", "packet.get(rsp) failed");
      }
    }
  }
  
  public static void saveDomains(ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    if (paramArrayList2 == null) {
      return;
    }
    paramArrayList2.clear();
    if (paramArrayList1 != null)
    {
      if (paramArrayList1.size() == 0) {
        return;
      }
      paramArrayList2.addAll(paramArrayList1);
      return;
    }
  }
  
  private void saveExtendFunctionOption(ArrayList<String> paramArrayList)
  {
    PublicSettingManager localPublicSettingManager = PublicSettingManager.getInstance();
    localPublicSettingManager.setQvodSupportType(1);
    ArrayList localArrayList1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(901);
    localArrayList1.clear();
    int i = 0;
    while (i < paramArrayList.size())
    {
      Object localObject = (String)paramArrayList.get(i);
      if (localObject != null)
      {
        localObject = ((String)localObject).split("\\|");
        if (localObject.length > 0) {
          if (localObject[0].equals("RuleAddr"))
          {
            if (localObject.length <= 3) {
              break label282;
            }
            ArrayList localArrayList2 = new ArrayList();
            localArrayList2.add(localObject[1]);
            TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainList(62, localArrayList2);
            if (localObject[2] == null) {}
          }
        }
      }
      for (;;)
      {
        try
        {
          j = Integer.parseInt(localObject[2]);
          if (localPublicSettingManager.getExtendRuleVersion() < j) {
            localPublicSettingManager.setExtendRuleUpdated(true);
          }
          localPublicSettingManager.setExtendRuleVersion(j);
          if (localObject[3] != null)
          {
            localPublicSettingManager.setExtendRuleMD5(localObject[3]);
            continue;
            if (localObject[0].equals("Switch"))
            {
              j = 1;
              if (j >= localObject.length) {
                continue;
              }
            }
          }
        }
        catch (NumberFormatException paramArrayList)
        {
          int j;
          int k;
          label282:
          continue;
        }
        try
        {
          k = Integer.parseInt(localObject[j]);
          switch (j)
          {
          default: 
            break;
          case 2: 
            localPublicSettingManager.setBdhdSupportType(k);
            break;
          case 1: 
            localPublicSettingManager.setQvodSupportType(k);
          }
          j += 1;
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      if (localObject[0].equals("F2V")) {
        localArrayList1.add(localObject[1]);
      }
      i += 1;
    }
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainList(901, localArrayList1);
  }
  
  public static boolean saveJsApiRspData(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    paramObject = (TBSJSApiWhitelistRsp)paramObject;
    try
    {
      Object localObject = new JceOutputStream();
      ((JceOutputStream)localObject).setServerEncoding("UTF-8");
      ((JceStruct)paramObject).writeTo((JceOutputStream)localObject);
      paramObject = TbsFileUtils.getJsApiWhiteListDataFileName();
      paramObject = new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), (String)paramObject);
      byte[] arrayOfByte = ((JceOutputStream)localObject).toByteArray();
      if ((arrayOfByte != null) && (arrayOfByte.length > 0))
      {
        if (((File)paramObject).exists())
        {
          localObject = FileUtils.read((File)paramObject);
          if ((localObject != null) && (((ByteBuffer)localObject).array().length > 0))
          {
            arrayOfByte = mergeJsApiDomainList(arrayOfByte, ((ByteBuffer)localObject).array());
            if ((arrayOfByte != null) && (arrayOfByte.length > 0)) {
              FileUtils.save((File)paramObject, arrayOfByte);
            } else {
              FileUtils.save((File)paramObject, ((ByteBuffer)localObject).array());
            }
          }
          else
          {
            FileUtils.save((File)paramObject, arrayOfByte);
          }
          FileUtils.getInstance().releaseByteBuffer((ByteBuffer)localObject);
        }
        else
        {
          FileUtils.save((File)paramObject, arrayOfByte);
        }
        return true;
      }
    }
    catch (Exception paramObject)
    {
      ((Exception)paramObject).printStackTrace();
    }
    return false;
  }
  
  public WUPRequest getDomainWupRequest(boolean paramBoolean, byte paramByte)
  {
    Object localObject = (Boolean)this.mIsGotDomains.get(Byte.valueOf(paramByte));
    if ((!paramBoolean) && (localObject != null) && (((Boolean)localObject).booleanValue())) {
      return null;
    }
    this.mIsGotDomains.put(Byte.valueOf(paramByte), Boolean.valueOf(true));
    localObject = new WUPRequest("CMD_DOMAIN_WHITE_LIST", "getDomain");
    ((WUPRequest)localObject).put("req", getDomainReq(paramBoolean, paramByte));
    ((WUPRequest)localObject).setBindObject(Byte.valueOf(paramByte));
    ((WUPRequest)localObject).setRequestCallBack(this);
    return (WUPRequest)localObject;
  }
  
  public WUPRequest getJsApiDomainRequest(Object paramObject, String paramString1, String paramString2)
  {
    paramString2 = new TBSJSApiWhitelistReq();
    paramString2.sAuth = TbsUserInfo.mJsApiSAuth;
    paramString2.sGuid = GUIDFactory.getInstance().getStrGuid();
    paramString2.sQua = TbsInfoUtils.getQUA2();
    paramString2.sHost = paramString1;
    paramString1 = new WUPRequest("tbsjsapi", "getWhitelistApi");
    paramString1.put("req", paramString2);
    paramString1.setRequestCallBack(this);
    paramString1.setType((byte)67);
    paramString1.setBindObject(paramObject);
    return paramString1;
  }
  
  public void onCmdGetDomainWhiteList(IRequestDomainCallBack paramIRequestDomainCallBack)
  {
    this.mCommDomainCallBack = paramIRequestDomainCallBack;
    WUPTaskProxy.send(getDomainWupRequest(false, TbsBaseModuleShell.REQ_SRC_TBS));
  }
  
  public void onCmdGetJsApiWhiteList(IRequestDomainCallBack paramIRequestDomainCallBack)
  {
    this.mJsApiCallBack = paramIRequestDomainCallBack;
    WUPTaskProxy.send(getJsApiDomainRequest(null, null, null));
  }
  
  public void onDomainWhiteList(WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPResponseBase != null)
    {
      Object localObject1 = (DomainWhiteListRsp)paramWUPResponseBase.get("rsp");
      if (localObject1 != null)
      {
        int i = ((DomainWhiteListRsp)localObject1).iDomainTime;
        Map localMap = ((DomainWhiteListRsp)localObject1).mTypeDomain;
        localObject1 = null;
        Object localObject2 = null;
        boolean bool = paramWUPResponseBase.isRespFromTestEnvServer();
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("domain list from Test Server? ");
        paramWUPResponseBase.append(bool);
        LogUtils.d("TbsDomainWhiteListManager", paramWUPResponseBase.toString());
        TbsWupManager.getInstance().setDomainListFromTest(bool);
        if (localMap != null)
        {
          if (!TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).hasDomainListData())
          {
            LogUtils.d("TbsDomainWhiteListManager", "WARRNING domain not init,to be init domain");
            TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).loadJsonObject());
          }
          Iterator localIterator = localMap.entrySet().iterator();
          paramWUPResponseBase = (WUPResponseBase)localObject2;
          for (;;)
          {
            localObject1 = paramWUPResponseBase;
            if (!localIterator.hasNext()) {
              break;
            }
            localObject1 = (Map.Entry)localIterator.next();
            localObject2 = (Integer)((Map.Entry)localObject1).getKey();
            ArrayList localArrayList = (ArrayList)((Map.Entry)localObject1).getValue();
            if ((localArrayList != null) && (localArrayList.size() != 0))
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("onDomainWhiteList type=");
              ((StringBuilder)localObject1).append(localObject2);
              ((StringBuilder)localObject1).append(", list=");
              ((StringBuilder)localObject1).append(localArrayList.toString());
              LogUtils.d("TbsDomainWhiteListManager", ((StringBuilder)localObject1).toString());
              int j = ((Integer)localObject2).intValue();
              if (j != 52)
              {
                if (j != 55) {
                  if (j != 59) {
                    if (j == 62) {}
                  }
                }
                switch (j)
                {
                default: 
                  TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainList(((Integer)localObject2).intValue(), localArrayList);
                  continue;
                  saveExtendFunctionOption(localArrayList);
                  continue;
                  localObject1 = (String)localArrayList.get(0);
                  localObject2 = this.mDomainListCallBack;
                  if (localObject2 == null) {
                    continue;
                  }
                  ((IDomainListCallBack)localObject2).setCallWeChatPattern((String)localObject1);
                  continue;
                  localObject1 = (String)localArrayList.get(0);
                  localObject2 = this.mDomainListCallBack;
                  if (localObject2 == null) {
                    continue;
                  }
                  ((IDomainListCallBack)localObject2).setWeChatAppid((String)localObject1);
                  break;
                }
              }
              else
              {
                localObject1 = paramWUPResponseBase;
                if (paramWUPResponseBase == null) {
                  localObject1 = new HashMap();
                }
                paramWUPResponseBase = (WUPResponseBase)localObject1;
                if (localArrayList != null)
                {
                  paramWUPResponseBase = (WUPResponseBase)localObject1;
                  if (localArrayList.size() > 0)
                  {
                    ((Map)localObject1).put(localObject2, localArrayList);
                    paramWUPResponseBase = (WUPResponseBase)localObject1;
                  }
                }
              }
            }
          }
        }
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("onDomainWhiteList mIsGuidValidateWhenRequestDomain:");
        paramWUPResponseBase.append(this.mIsGuidValidateWhenRequestDomain);
        LogUtils.d("TbsDomainWhiteListManager", paramWUPResponseBase.toString());
        if (this.mIsGuidValidateWhenRequestDomain)
        {
          TbsUserInfo.getInstance().setDomainTime(i);
          TbsUserInfoDataProvider.getInstance().addUserInfoData(1013, Integer.valueOf(i));
          TbsUserInfoDataProvider.getInstance().saveUserInfoData();
        }
        if ((localMap != null) && (localMap.entrySet().size() > 0))
        {
          paramWUPResponseBase = new ArrayList();
          paramWUPResponseBase.add(String.valueOf(i));
          TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).addDomainList(1001, paramWUPResponseBase);
          TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).saveDomainList();
        }
        paramWUPResponseBase = TbsBaseModuleShell.getCoreService();
        if (paramWUPResponseBase != null) {
          paramWUPResponseBase.setSafeDomainList((Map)localObject1);
        }
      }
      else
      {
        LogUtils.d("TbsDomainWhiteListManager", "packet.get(rsp) failed");
      }
    }
  }
  
  public void onMiniQBDomainWhiteList(WUPResponseBase paramWUPResponseBase, byte paramByte)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("handle domain list rsp begin, reqType=");
    ((StringBuilder)localObject1).append(paramByte);
    LogUtils.d("TbsDomainWhiteListManager", ((StringBuilder)localObject1).toString());
    if (paramWUPResponseBase == null) {
      return;
    }
    paramWUPResponseBase = paramWUPResponseBase.get("rsp");
    boolean bool = paramWUPResponseBase instanceof DomainWhiteListRsp;
    localObject1 = null;
    if (bool) {
      paramWUPResponseBase = (DomainWhiteListRsp)paramWUPResponseBase;
    } else {
      paramWUPResponseBase = null;
    }
    if (paramWUPResponseBase == null) {
      return;
    }
    Object localObject2 = paramWUPResponseBase.mTypeDomain;
    if (localObject2 == null) {
      return;
    }
    int i = paramWUPResponseBase.iDomainTime;
    localObject2 = ((Map)localObject2).entrySet().iterator();
    paramWUPResponseBase = (WUPResponseBase)localObject1;
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Map.Entry)((Iterator)localObject2).next();
      Integer localInteger = (Integer)((Map.Entry)localObject1).getKey();
      ArrayList localArrayList = (ArrayList)((Map.Entry)localObject1).getValue();
      int j = localInteger.intValue();
      if (j != 52)
      {
        switch (j)
        {
        default: 
          TbsDomainListDataProvider.getInstance(paramByte).addDomainList(localInteger.intValue(), localArrayList);
          break;
        }
      }
      else
      {
        localObject1 = paramWUPResponseBase;
        if (paramWUPResponseBase == null) {
          localObject1 = new HashMap();
        }
        paramWUPResponseBase = (WUPResponseBase)localObject1;
        if (localArrayList != null)
        {
          paramWUPResponseBase = (WUPResponseBase)localObject1;
          if (localArrayList.size() > 0)
          {
            ((Map)localObject1).put(localInteger, localArrayList);
            paramWUPResponseBase = (WUPResponseBase)localObject1;
          }
        }
      }
    }
    if (this.mIsGuidValidateWhenRequestDomain) {
      PublicSettingManager.getInstance().setDomainTime(paramByte, i);
    }
    TbsDomainListDataProvider.getInstance(paramByte).saveDomainList();
    LogUtils.d("TbsDomainWhiteListManager", "handle domain list rsp end");
  }
  
  public void onVideoDomainWhiteList(WUPResponseBase paramWUPResponseBase)
  {
    LogUtils.d("TbsDomainWhiteListManager", "onVideoDomainWhiteList: handle domain list rsp begin, reqType=");
    if (paramWUPResponseBase == null) {
      return;
    }
    Object localObject1 = null;
    DomainListDataProvider localDomainListDataProvider = DomainListDataProvider.getVideoInstance();
    Object localObject2 = paramWUPResponseBase.get("rsp");
    paramWUPResponseBase = (WUPResponseBase)localObject1;
    if ((localObject2 instanceof DomainWhiteListRsp)) {
      paramWUPResponseBase = (DomainWhiteListRsp)localObject2;
    }
    if (paramWUPResponseBase == null) {
      return;
    }
    localObject1 = paramWUPResponseBase.mTypeDomain;
    if (localObject1 == null) {
      return;
    }
    int i = paramWUPResponseBase.iDomainTime;
    paramWUPResponseBase = ((Map)localObject1).entrySet().iterator();
    while (paramWUPResponseBase.hasNext())
    {
      localObject2 = (Map.Entry)paramWUPResponseBase.next();
      localObject1 = (Integer)((Map.Entry)localObject2).getKey();
      localObject2 = (ArrayList)((Map.Entry)localObject2).getValue();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onVideoDomainWhiteList: type=");
      localStringBuilder.append(localObject1);
      localStringBuilder.append(", domains=");
      localStringBuilder.append(localObject2);
      LogUtils.d("TbsDomainWhiteListManager", localStringBuilder.toString());
      TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS_VIDEO).addDomainList(((Integer)localObject1).intValue(), (ArrayList)localObject2);
    }
    if (this.mIsGuidValidateWhenRequestDomain)
    {
      TbsUserInfo.getInstance().setDomainTime(i);
      TbsUserInfoDataProvider.getInstance().addUserInfoData(1013, Integer.valueOf(i));
      TbsUserInfoDataProvider.getInstance().saveUserInfoData();
    }
    localDomainListDataProvider.createDomainMatherCache();
    TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS_VIDEO).saveDomainList();
    LogUtils.d("TbsDomainWhiteListManager", "onVideoDomainWhiteList handle domain list rsp end");
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    LogUtils.d("TbsDomainWhiteListManager", "onDomainWhiteListFailed...");
    if ((paramWUPRequestBase != null) && (paramWUPRequestBase.getType() == 67))
    {
      paramWUPRequestBase = this.mJsApiCallBack;
      if (paramWUPRequestBase != null) {
        paramWUPRequestBase.onCmdGetDomain(null, false);
      }
    }
    else
    {
      IRequestDomainCallBack localIRequestDomainCallBack = this.mCommDomainCallBack;
      if ((localIRequestDomainCallBack != null) && (paramWUPRequestBase != null)) {
        localIRequestDomainCallBack.onCmdGetDomain(null, false);
      }
    }
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    LogUtils.d("WUPRequestCallBack", "Domain onWUPTaskSuccess");
    Object localObject1;
    if ((paramWUPRequestBase != null) && (paramWUPRequestBase.getType() == 67))
    {
      onJsApiWhiteList(paramWUPResponseBase);
      localObject1 = this.mJsApiCallBack;
      if ((localObject1 != null) && (paramWUPRequestBase != null) && (paramWUPResponseBase != null)) {
        ((IRequestDomainCallBack)localObject1).onCmdGetDomain(paramWUPResponseBase.getContextFeature(), true);
      }
    }
    else if (paramWUPRequestBase != null)
    {
      Object localObject3 = paramWUPRequestBase.getBindObject();
      Object localObject2 = null;
      localObject1 = localObject2;
      if (localObject3 != null)
      {
        localObject1 = localObject2;
        if ((localObject3 instanceof Byte)) {
          localObject1 = (Byte)localObject3;
        }
      }
      if ((localObject1 != null) && (((Byte)localObject1).byteValue() == TbsBaseModuleShell.REQ_SRC_MINI_QB))
      {
        onMiniQBDomainWhiteList(paramWUPResponseBase, TbsBaseModuleShell.REQ_SRC_MINI_QB);
        return;
      }
      if ((localObject1 != null) && (((Byte)localObject1).byteValue() == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO))
      {
        onVideoDomainWhiteList(paramWUPResponseBase);
        return;
      }
      onDomainWhiteList(paramWUPResponseBase);
      localObject1 = this.mCommDomainCallBack;
      if ((localObject1 != null) && (paramWUPRequestBase != null) && (paramWUPResponseBase != null)) {
        ((IRequestDomainCallBack)localObject1).onCmdGetDomain(paramWUPResponseBase.getContextFeature(), true);
      }
    }
  }
  
  public void setCallBack(IDomainListCallBack paramIDomainListCallBack)
  {
    this.mDomainListCallBack = paramIDomainListCallBack;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsDomainWhiteListManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */