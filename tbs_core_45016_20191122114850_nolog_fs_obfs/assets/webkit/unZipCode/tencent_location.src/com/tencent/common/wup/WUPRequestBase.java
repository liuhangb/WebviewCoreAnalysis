package com.tencent.common.wup;

import android.text.TextUtils;
import com.taf.UniPacket;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.manifest.EventEmiter;
import com.tencent.common.manifest.EventMessage;
import com.tencent.mtt.base.task.Task.Priority;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WUPRequestBase
{
  public static final byte NETWORK_TEST_FLAG_FAIL = 0;
  public static final byte NETWORK_TEST_FLAG_OK = 1;
  public static final byte NETWORK_TEST_FLAG_UNKOWN = 2;
  public static final byte WUP_REQUEST_EXECUTE_RES_FAIL = 0;
  public static final byte WUP_REQUEST_EXECUTE_RES_OK = 1;
  public static final byte WUP_REQUEST_EXECUTE_RES_UNKOWN = 2;
  protected String mAdditionHeader = "";
  protected Object mBindObject = null;
  protected ClassLoader mClassLoader = null;
  protected String mClassNamePrefs = "";
  protected String mEncodeName = "UTF-8";
  private long mEndTime = -1L;
  protected Throwable mFailedReason = null;
  protected String mFuncName = "";
  protected boolean mHasReplied = false;
  protected boolean mIsBackgroudTask = false;
  protected boolean mIsEmergency = false;
  protected boolean mIsFromService = false;
  private boolean mIsInMultiPackage = false;
  protected boolean mNeedCloseConnection = true;
  protected boolean mNeedEncrypt = true;
  protected boolean mNeedStatFlow = false;
  protected INetworkDetectCallback mNetworkDetectCallback = null;
  protected int mNetworkStatus = -1;
  protected int mPacketSize = 1;
  protected byte[] mPostData = null;
  protected Task.Priority mPriority = Task.Priority.NORMAL;
  protected a mQuality = new a();
  protected IWUPRequestCallBack mRequestCallBack = null;
  protected int mRequestID = Integer.MIN_VALUE;
  protected String mRequestName = "";
  protected ArrayList<String> mRequestParamNames = null;
  protected ArrayList<Object> mRequestParams = null;
  protected RequestPolicy mRequestPolicy = RequestPolicy.MAX_RETRY_POLICY;
  private long mSendTime = -1L;
  protected String mServerName = "";
  private long mStartTime = System.currentTimeMillis();
  public String mStatAddressReason = null;
  public int mStatWupListIndex = -3;
  protected int mTriedTimes = 0;
  protected byte mType = Byte.MIN_VALUE;
  protected String mUrl = "";
  
  public WUPRequestBase() {}
  
  public WUPRequestBase(String paramString1, String paramString2)
  {
    this.mServerName = paramString1;
    this.mFuncName = paramString2;
  }
  
  public WUPRequestBase(String paramString1, String paramString2, IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    this.mServerName = paramString1;
    this.mFuncName = paramString2;
    this.mRequestCallBack = paramIWUPRequestCallBack;
  }
  
  private void checkClassName(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    try
    {
      paramObject = paramObject.getClass().getPackage().getName();
    }
    catch (Exception paramObject)
    {
      ((Exception)paramObject).printStackTrace();
      paramObject = "";
    }
    if (TextUtils.isEmpty((CharSequence)paramObject)) {
      return;
    }
    if ((!((String)paramObject).equalsIgnoreCase("MTT")) && (!((String)paramObject).equalsIgnoreCase("TIRI")))
    {
      if (((String)paramObject).equalsIgnoreCase("circle")) {
        return;
      }
      if (TextUtils.isEmpty(this.mClassNamePrefs))
      {
        this.mClassNamePrefs = ((String)paramObject);
        return;
      }
      if (this.mClassNamePrefs.equalsIgnoreCase((String)paramObject)) {
        return;
      }
      throw new RuntimeException("protocol exeption: all wup params must be in same package");
    }
  }
  
  public void addNetTime(ArrayList<Long> paramArrayList)
  {
    if (paramArrayList == null) {
      return;
    }
    this.mQuality.jdField_c_of_type_JavaUtilArrayList.addAll(paramArrayList);
  }
  
  public void addPath(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    try
    {
      StringBuilder localStringBuilder = this.mQuality.jdField_a_of_type_JavaLangStringBuilder;
      localStringBuilder.append(paramString);
      localStringBuilder.append("/");
      return;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void addTaskPaths(ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      try
      {
        paramArrayList = paramArrayList.iterator();
        while (paramArrayList.hasNext())
        {
          String str = (String)paramArrayList.next();
          if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("wtrc_"))
            {
              this.mQuality.jdField_d_of_type_JavaLangString = str.substring(5);
            }
            else
            {
              StringBuilder localStringBuilder = this.mQuality.jdField_a_of_type_JavaLangStringBuilder;
              localStringBuilder.append(str);
              localStringBuilder.append("/");
            }
          }
        }
        return;
      }
      catch (Exception paramArrayList)
      {
        paramArrayList.printStackTrace();
      }
    }
  }
  
  public void addThreadWaitTime(Long paramLong)
  {
    if (paramLong == null) {
      return;
    }
    this.mQuality.jdField_b_of_type_JavaUtilArrayList.add(paramLong);
  }
  
  public void addTridTimes()
  {
    this.mTriedTimes += 1;
  }
  
  public void addTriedIP(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.mQuality.jdField_a_of_type_JavaUtilArrayList.add(paramString);
  }
  
  protected void checkClassLoader(Object paramObject) {}
  
  public void clearPath()
  {
    a locala = this.mQuality;
    locala.jdField_a_of_type_JavaLangStringBuilder = null;
    locala.jdField_a_of_type_JavaLangStringBuilder = new StringBuilder();
  }
  
  public void copyWupQuality(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase != null)
    {
      paramWUPRequestBase = paramWUPRequestBase.mQuality;
      if (paramWUPRequestBase == null) {
        return;
      }
      this.mQuality = paramWUPRequestBase.a();
      return;
    }
  }
  
  public String getAddtionHeader()
  {
    return this.mAdditionHeader;
  }
  
  public Object getBindObject()
  {
    return this.mBindObject;
  }
  
  public ClassLoader getClassLoader()
  {
    return this.mClassLoader;
  }
  
  public String getClassNamePrefs()
  {
    return this.mClassNamePrefs;
  }
  
  public String getCurrentIP()
  {
    a locala = this.mQuality;
    if (locala == null) {
      return "";
    }
    return locala.jdField_a_of_type_JavaLangString;
  }
  
  public String getEncodeName()
  {
    return this.mEncodeName;
  }
  
  public int getErrorCode()
  {
    return this.mQuality.jdField_a_of_type_Int;
  }
  
  public String getErrorStackInfo()
  {
    Object localObject1 = this.mFailedReason;
    if (localObject1 == null) {
      return "";
    }
    try
    {
      localObject1 = ((Throwable)localObject1).getStackTrace();
    }
    catch (Throwable localThrowable)
    {
      StringBuilder localStringBuilder;
      int i;
      int k;
      int j;
      for (;;) {}
    }
    localObject1 = null;
    if (localObject1 == null) {
      return "";
    }
    localStringBuilder = new StringBuilder();
    i = 0;
    k = 0;
    j = 0;
    while (i < localObject1.length)
    {
      Object localObject2 = localObject1[i];
      if (localObject2 != null) {
        if (((StackTraceElement)localObject2).toString().contains("com.tencent"))
        {
          localStringBuilder.append(((StackTraceElement)localObject2).toString());
          localStringBuilder.append("\\");
          int m = j + 1;
          j = m;
          if (m >= 3) {
            break;
          }
        }
        else if (k < 2)
        {
          localStringBuilder.append(((StackTraceElement)localObject2).toString());
          localStringBuilder.append("\\|");
          k += 1;
        }
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public String getFailedReason()
  {
    Throwable localThrowable = this.mFailedReason;
    if (localThrowable != null) {
      return localThrowable.toString();
    }
    return "";
  }
  
  public String getFuncName()
  {
    return this.mFuncName;
  }
  
  public boolean getHasReplied()
  {
    return this.mHasReplied;
  }
  
  public boolean getIsBackGroudTask()
  {
    return this.mIsBackgroudTask;
  }
  
  public boolean getIsFromService()
  {
    return this.mIsFromService;
  }
  
  public boolean getNeedCloseConnection()
  {
    return this.mNeedCloseConnection;
  }
  
  public boolean getNeedEncrypt()
  {
    return this.mNeedEncrypt;
  }
  
  public boolean getNeedStatFlow()
  {
    return this.mNeedStatFlow;
  }
  
  public INetworkDetectCallback getNetworkDetectCallback()
  {
    return this.mNetworkDetectCallback;
  }
  
  public int getNetworkStatus()
  {
    return this.mNetworkStatus;
  }
  
  public int getPackageSize()
  {
    return this.mPacketSize;
  }
  
  public byte[] getPostData()
  {
    return this.mPostData;
  }
  
  public byte[] getPostDataFromWUPRequest(int paramInt)
  {
    FLogger.d("TaskCenter : ", "getting post data");
    try
    {
      Object localObject = new UniPacket();
      ((UniPacket)localObject).setEncodeName(this.mEncodeName);
      ((UniPacket)localObject).setProtocolClassNamePrefs(this.mClassNamePrefs);
      if (getRequstID() != Integer.MIN_VALUE)
      {
        ((UniPacket)localObject).setRequestId(this.mRequestID);
      }
      else
      {
        ((UniPacket)localObject).setRequestId(paramInt);
        this.mRequestID = ((UniPacket)localObject).getRequestId();
      }
      ((UniPacket)localObject).setFuncName(this.mFuncName);
      ((UniPacket)localObject).setServantName(this.mServerName);
      if ((this.mRequestParamNames != null) && (this.mRequestParams != null))
      {
        Iterator localIterator1 = this.mRequestParamNames.iterator();
        Iterator localIterator2 = this.mRequestParams.iterator();
        while ((localIterator1.hasNext()) && (localIterator2.hasNext())) {
          ((UniPacket)localObject).put((String)localIterator1.next(), localIterator2.next());
        }
      }
      localObject = ((UniPacket)localObject).encode();
      return (byte[])localObject;
    }
    catch (Exception localException)
    {
      setFailedReason(localException);
      localException.printStackTrace();
      EventMessage localEventMessage = new EventMessage("com.tencent.mtt.external.rqd.RQDManager.handleCatchException", new Object[] { Thread.currentThread(), localException, "getPostDataFromWUPRequest" });
      EventEmiter.getDefault().emit(localEventMessage);
    }
    return null;
  }
  
  public Task.Priority getPriority()
  {
    return this.mPriority;
  }
  
  public IWUPRequestCallBack getRequestCallBack()
  {
    return this.mRequestCallBack;
  }
  
  public String getRequestName()
  {
    return this.mRequestName;
  }
  
  public ArrayList<String> getRequestParamNames()
  {
    return this.mRequestParamNames;
  }
  
  public ArrayList<Object> getRequestParams()
  {
    return this.mRequestParams;
  }
  
  public RequestPolicy getRequestPolicy()
  {
    return this.mRequestPolicy;
  }
  
  public int getRequstID()
  {
    return this.mRequestID;
  }
  
  public long getSendTime()
  {
    long l = this.mSendTime;
    if (l <= 0L) {
      return -1L;
    }
    return l - this.mStartTime;
  }
  
  public String getServerName()
  {
    return this.mServerName;
  }
  
  public int getTriedIPSize()
  {
    return this.mQuality.jdField_a_of_type_JavaUtilArrayList.size();
  }
  
  public int getTriedTimes()
  {
    return this.mTriedTimes;
  }
  
  public byte getType()
  {
    return this.mType;
  }
  
  public String getUrl()
  {
    return this.mUrl;
  }
  
  public long getUsedTime()
  {
    long l1 = this.mSendTime;
    if (l1 > 0L)
    {
      long l2 = this.mEndTime;
      if ((l2 > 0L) && (l2 >= l1)) {
        return l2 - l1;
      }
    }
    return -1L;
  }
  
  public int getWupExecResult()
  {
    return this.mQuality.jdField_a_of_type_Byte;
  }
  
  public boolean isEmergencyTask()
  {
    return this.mIsEmergency;
  }
  
  public boolean isInMultiPackage()
  {
    return this.mIsInMultiPackage;
  }
  
  public void put(String paramString, Object paramObject)
  {
    putRequestParam(paramString, paramObject);
  }
  
  public void putRequestParam(String paramString, Object paramObject)
  {
    if (this.mRequestParamNames == null) {
      this.mRequestParamNames = new ArrayList();
    }
    if (this.mRequestParams == null) {
      this.mRequestParams = new ArrayList();
    }
    if ((paramString != null) && (!this.mRequestParamNames.contains(paramString)) && (paramObject != null))
    {
      checkClassName(paramObject);
      checkClassLoader(paramObject);
      this.mRequestParamNames.add(paramString);
      this.mRequestParams.add(paramObject);
    }
  }
  
  public void setAddtionHeader(String paramString)
  {
    this.mAdditionHeader = paramString;
  }
  
  public void setBindObject(Object paramObject)
  {
    this.mBindObject = paramObject;
  }
  
  public void setClassLoader(ClassLoader paramClassLoader)
  {
    this.mClassLoader = paramClassLoader;
  }
  
  public void setClassNamePrefs(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      this.mClassNamePrefs = paramString;
    }
  }
  
  public void setCurrentIP(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.mQuality.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public void setEmergencyTask(boolean paramBoolean)
  {
    this.mIsEmergency = paramBoolean;
  }
  
  public void setEncodeName(String paramString)
  {
    this.mEncodeName = paramString;
  }
  
  public void setEndTime(long paramLong)
  {
    this.mEndTime = paramLong;
    this.mQuality.f = paramLong;
  }
  
  public void setErrorCode(int paramInt)
  {
    this.mQuality.jdField_a_of_type_Int = paramInt;
  }
  
  public void setExecuteRes(byte paramByte)
  {
    this.mQuality.jdField_a_of_type_Byte = paramByte;
  }
  
  public void setFailedReason(Throwable paramThrowable)
  {
    this.mFailedReason = paramThrowable;
  }
  
  public void setFlowInfo(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    a locala = this.mQuality;
    locala.jdField_a_of_type_Long = paramLong1;
    locala.jdField_b_of_type_Long = paramLong2;
    locala.jdField_c_of_type_Long = paramLong3;
    locala.jdField_d_of_type_Long = paramLong4;
  }
  
  public void setFuncName(String paramString)
  {
    this.mFuncName = paramString;
  }
  
  public void setHasReplied(boolean paramBoolean)
  {
    this.mHasReplied = paramBoolean;
  }
  
  public void setInMultiPackage()
  {
    this.mIsInMultiPackage = true;
  }
  
  public void setIsBackgroudTask(boolean paramBoolean)
  {
    this.mIsBackgroudTask = paramBoolean;
  }
  
  public void setIsFromService(boolean paramBoolean)
  {
    this.mIsFromService = paramBoolean;
  }
  
  public void setNeedCloseConnection(boolean paramBoolean)
  {
    this.mNeedCloseConnection = paramBoolean;
    this.mQuality.jdField_a_of_type_Boolean = (paramBoolean ^ true);
  }
  
  public void setNeedEncrypt(boolean paramBoolean)
  {
    this.mNeedEncrypt = paramBoolean;
  }
  
  public void setNeedStatFlow(boolean paramBoolean)
  {
    this.mNeedStatFlow = paramBoolean;
  }
  
  public void setNetworkDetectCallback(INetworkDetectCallback paramINetworkDetectCallback)
  {
    this.mNetworkDetectCallback = paramINetworkDetectCallback;
  }
  
  public void setNetworkStatus(int paramInt)
  {
    this.mNetworkStatus = paramInt;
  }
  
  public void setPackageSize(int paramInt)
  {
    this.mPacketSize = paramInt;
  }
  
  public void setPostData(byte[] paramArrayOfByte)
  {
    this.mPostData = paramArrayOfByte;
  }
  
  public void setPriority(Task.Priority paramPriority)
  {
    this.mPriority = paramPriority;
  }
  
  public void setRequestCallBack(IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    this.mRequestCallBack = paramIWUPRequestCallBack;
  }
  
  public void setRequestName(String paramString)
  {
    this.mRequestName = paramString;
  }
  
  public void setRequestPolicy(RequestPolicy paramRequestPolicy)
  {
    this.mRequestPolicy = paramRequestPolicy;
  }
  
  public void setRequstID(int paramInt)
  {
    this.mRequestID = paramInt;
  }
  
  public void setSendTime(long paramLong)
  {
    this.mSendTime = paramLong;
    this.mQuality.e = paramLong;
  }
  
  public void setServerName(String paramString)
  {
    this.mServerName = paramString;
  }
  
  public void setType(byte paramByte)
  {
    this.mType = paramByte;
  }
  
  public void setUrl(String paramString)
  {
    this.mUrl = paramString;
  }
  
  public void setWupResolvedAddrInfo(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return;
      }
      a locala = this.mQuality;
      locala.jdField_b_of_type_JavaLangString = paramString1;
      locala.jdField_c_of_type_JavaLangString = paramString2;
      return;
    }
  }
  
  public HashMap<String, String> toBeaconStatMap(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    Object localObject = new HashMap();
    try
    {
      paramString1 = this.mQuality.a(paramString1, paramString2, paramString3, paramInt);
      localObject = paramString1;
      paramString2 = new StringBuilder();
      localObject = paramString1;
      paramString2.append(getRequstID());
      localObject = paramString1;
      paramString2.append("");
      localObject = paramString1;
      paramString1.put("requestId", paramString2.toString());
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
    return (HashMap<String, String>)localObject;
  }
  
  class a
  {
    public byte a;
    public int a;
    public long a;
    public String a;
    public StringBuilder a;
    public ArrayList<String> a;
    public boolean a;
    public int b;
    public long b;
    public String b;
    public ArrayList<Long> b;
    public long c;
    public String c;
    public ArrayList<Long> c;
    public long d;
    public String d;
    public long e = -1L;
    public long f = -1L;
    
    a()
    {
      this.jdField_a_of_type_JavaLangString = "";
      this.jdField_b_of_type_JavaLangString = "";
      this.jdField_c_of_type_JavaLangString = "";
      this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      this.jdField_a_of_type_JavaLangStringBuilder = new StringBuilder();
      this.jdField_a_of_type_Byte = 2;
      this.jdField_a_of_type_Long = 0L;
      this.jdField_b_of_type_Long = 0L;
      this.jdField_c_of_type_Long = 0L;
      this.jdField_d_of_type_Long = 0L;
      this.jdField_d_of_type_JavaLangString = "0";
      this.jdField_a_of_type_Int = 0;
      this.jdField_b_of_type_Int = 2;
      this.jdField_b_of_type_JavaUtilArrayList = new ArrayList();
      this.jdField_c_of_type_JavaUtilArrayList = new ArrayList();
      this.jdField_a_of_type_Boolean = false;
    }
    
    public a a()
    {
      a locala = new a(this.jdField_a_of_type_ComTencentCommonWupWUPRequestBase);
      locala.jdField_a_of_type_JavaLangString = this.jdField_a_of_type_JavaLangString;
      locala.jdField_b_of_type_JavaLangString = this.jdField_b_of_type_JavaLangString;
      locala.jdField_c_of_type_JavaLangString = this.jdField_c_of_type_JavaLangString;
      locala.jdField_a_of_type_JavaUtilArrayList.addAll(this.jdField_a_of_type_JavaUtilArrayList);
      locala.jdField_a_of_type_JavaLangStringBuilder = new StringBuilder();
      locala.jdField_a_of_type_JavaLangStringBuilder.append(this.jdField_a_of_type_JavaLangStringBuilder.toString());
      locala.jdField_a_of_type_Byte = this.jdField_a_of_type_Byte;
      locala.jdField_a_of_type_Long = this.jdField_a_of_type_Long;
      locala.jdField_b_of_type_Long = this.jdField_b_of_type_Long;
      locala.jdField_c_of_type_Long = this.jdField_c_of_type_Long;
      locala.jdField_d_of_type_Long = this.jdField_d_of_type_Long;
      locala.jdField_d_of_type_JavaLangString = this.jdField_d_of_type_JavaLangString;
      locala.jdField_a_of_type_Int = this.jdField_a_of_type_Int;
      locala.jdField_b_of_type_JavaUtilArrayList.addAll(this.jdField_b_of_type_JavaUtilArrayList);
      locala.jdField_c_of_type_JavaUtilArrayList.addAll(this.jdField_c_of_type_JavaUtilArrayList);
      locala.f = this.f;
      locala.e = this.e;
      return locala;
    }
    
    public HashMap<String, String> a(String arg1, String paramString2, String paramString3, int paramInt)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("qua2", ???);
      localHashMap.put("apn", Apn.getApnName(Apn.getApnTypeS()));
      ??? = new StringBuilder();
      ???.append(Apn.getApnType());
      ???.append("");
      localHashMap.put("network_type", ???.toString());
      ??? = new StringBuilder();
      ???.append(paramInt);
      ???.append("");
      localHashMap.put("network_test_flag", ???.toString());
      localHashMap.put("retry_times", this.jdField_d_of_type_JavaLangString);
      localHashMap.put("wup_ip", this.jdField_a_of_type_JavaLangString);
      localHashMap.put("wup_iplist", this.jdField_a_of_type_JavaUtilArrayList.toString());
      localHashMap.put("wup_rsvl_addr", this.jdField_b_of_type_JavaLangString);
      localHashMap.put("wup_rsvl_type", this.jdField_c_of_type_JavaLangString);
      ??? = new StringBuilder();
      ???.append(paramString2);
      ???.append(paramString3);
      if (TextUtils.isEmpty(???.toString()))
      {
        localHashMap.put("request_type", this.jdField_a_of_type_ComTencentCommonWupWUPRequestBase.getRequestName());
      }
      else
      {
        ??? = new StringBuilder();
        ???.append(paramString2);
        ???.append("/");
        ???.append(paramString3);
        localHashMap.put("request_type", ???.toString());
      }
      synchronized (this.jdField_a_of_type_JavaLangStringBuilder)
      {
        paramString2 = this.jdField_a_of_type_JavaLangStringBuilder.toString();
        if ((this.jdField_a_of_type_JavaLangStringBuilder.length() > 0) && (paramString2.endsWith("/"))) {
          this.jdField_a_of_type_JavaLangStringBuilder.deleteCharAt(this.jdField_a_of_type_JavaLangStringBuilder.length() - 1);
        }
        localHashMap.put("request_status", this.jdField_a_of_type_JavaLangStringBuilder.toString());
        ??? = new StringBuilder();
        ???.append(this.jdField_a_of_type_Byte);
        ???.append("");
        localHashMap.put("request_result", ???.toString());
        localHashMap.put("error_detail", this.jdField_a_of_type_ComTencentCommonWupWUPRequestBase.getFailedReason());
        localHashMap.put("error_stack", this.jdField_a_of_type_ComTencentCommonWupWUPRequestBase.getErrorStackInfo());
        ??? = new StringBuilder();
        ???.append(this.jdField_a_of_type_Long);
        ???.append("");
        localHashMap.put("wup_send_size_on", ???.toString());
        ??? = new StringBuilder();
        ???.append(this.jdField_b_of_type_Long);
        ???.append("");
        localHashMap.put("wup_receive_size_on", ???.toString());
        ??? = new StringBuilder();
        ???.append(this.jdField_c_of_type_Long);
        ???.append("");
        localHashMap.put("wup_send_size_off", ???.toString());
        ??? = new StringBuilder();
        ???.append(this.jdField_d_of_type_Long);
        ???.append("");
        localHashMap.put("wup_receive_size_off", ???.toString());
        ??? = new StringBuilder();
        ???.append(this.jdField_a_of_type_Boolean);
        ???.append("");
        localHashMap.put("wup_keep_alive", ???.toString());
        long l3 = -1L;
        long l4 = this.f;
        long l1 = 0L;
        long l2 = l3;
        if (l4 > 0L)
        {
          long l5 = this.e;
          l2 = l3;
          if (l5 > 0L)
          {
            l2 = l3;
            if (l4 > l5) {
              l2 = l4 - l5;
            }
          }
        }
        l3 = l1;
        if (l2 > 0L)
        {
          ??? = this.jdField_c_of_type_JavaUtilArrayList;
          l3 = l1;
          if (??? != null)
          {
            l3 = l1;
            if (!???.isEmpty())
            {
              paramInt = 0;
              for (;;)
              {
                l3 = l1;
                if (paramInt >= this.jdField_c_of_type_JavaUtilArrayList.size()) {
                  break;
                }
                l1 += ((Long)this.jdField_c_of_type_JavaUtilArrayList.get(paramInt)).longValue();
                paramInt += 1;
              }
            }
          }
        }
        ??? = new StringBuilder();
        ???.append(l2);
        ???.append("");
        localHashMap.put("request_time", ???.toString());
        ??? = new StringBuilder();
        ???.append(l2 - l3);
        ???.append("");
        localHashMap.put("processself_time", ???.toString());
        localHashMap.put("thread_time", this.jdField_b_of_type_JavaUtilArrayList.toString());
        ??? = this.jdField_c_of_type_JavaUtilArrayList;
        if (??? != null) {
          localHashMap.put("net_time", ???.toString());
        }
        ??? = new StringBuilder();
        ???.append(this.jdField_a_of_type_Int);
        ???.append("");
        localHashMap.put("error_code", ???.toString());
        return localHashMap;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPRequestBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */