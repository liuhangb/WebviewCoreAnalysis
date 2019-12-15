package com.tencent.tbs.tbsshell.common.fingersearch;

import MTT.FingerQueryModifyReq;
import MTT.FingerSearchReq;
import MTT.FingerSearchRsp;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.taf.ObjectCreateException;
import com.taf.UniPacket;
import com.tencent.common.utils.LogUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import com.tencent.tbs.tbsshell.common.fingersearch.facade.IFingerSearchService;
import com.tencent.tbs.tbsshell.common.fingersearch.facade.IFingerSearchStatusListener;
import com.tencent.tbs.tbsshell.common.fingersearch.network.WUPConnectionListener;
import com.tencent.tbs.tbsshell.common.fingersearch.network.WUPStreamConnection;
import java.util.HashMap;
import java.util.Map;

public class FingerSearch
  implements IFingerSearchService, IFingerSearchStatusListener, WUPConnectionListener
{
  private static final String APP_DEMO = "com.tencent.tbs";
  private static final String APP_QB = "com.tencent.mtt";
  private static final String APP_QQ = "com.tencent.mobileqq";
  private static final String APP_QZONE = "com.qzone";
  private static final String APP_TMS = "com.android.browser";
  private static final String APP_WX = "com.tencent.mm";
  private static final int BEACON_STATISTICS_ADJUSTMENT_LENGTH_BETWEEN_5_10 = 63;
  private static final int BEACON_STATISTICS_ADJUSTMENT_LENGTH_GREATER_10 = 62;
  private static final int BEACON_STATISTICS_ADJUSTMENT_LENGTH_LESS_5 = 61;
  private static final int BEACON_STATISTICS_FAIL_ADJUSTMENT_TEXT_EXCEPTION = -6;
  private static final int BEACON_STATISTICS_FAIL_EXIT_X5_LONG_CLICK_POP_MENU = -8;
  private static final int BEACON_STATISTICS_FAIL_PACKAGE_NAME_EXCEPTION = -7;
  private static final int BEACON_STATISTICS_FAIL_REQ_FAILURE = -1;
  private static final int BEACON_STATISTICS_FAIL_RSP_DATA_ERROR = -2;
  private static final int BEACON_STATISTICS_FAIL_RSP_DETAILS = -3;
  private static final int BEACON_STATISTICS_FAIL_RSP_DETAILS_EMPTY = -31;
  private static final int BEACON_STATISTICS_FAIL_RSP_DETAILS_EXCEPTION = -32;
  private static final int BEACON_STATISTICS_FAIL_RSP_DETAILS_MISMATCHING = -33;
  private static final int BEACON_STATISTICS_FAIL_RSP_DETAILS_RESULT_NULL = -4;
  private static final int BEACON_STATISTICS_FAIL_RSP_DETAILS_SESSIONID_ZERO = -5;
  private static final int BEACON_STATISTICS_FAIL_USER_EXIT_WEBVIEW = -9;
  private static final int BEACON_STATISTICS_FAIL_WUP_ALL_SERVERS_FAILURE = -11;
  private static final int BEACON_STATISTICS_FAIL_WUP_CONNECT_EXCEPTION = -13;
  private static final int BEACON_STATISTICS_FAIL_WUP_PROBLEM = -1;
  private static final int BEACON_STATISTICS_FAIL_WUP_SINGLE_SERVER_TIMEOUT = -12;
  private static final int BEACON_STATISTICS_REPORT_DO_FINGER_SEARCH = 72;
  private static final int BEACON_STATISTICS_REPORT_DO_LONG_CLICK = 71;
  private static final int BEACON_STATISTICS_REPORT_LONG_CLICK_TO_POP_MENU = 7;
  private static final int BEACON_STATISTICS_REQ = 0;
  private static final int BEACON_STATISTICS_REQ_SUCCESS = 1;
  private static final int BEACON_STATISTICS_RSP_RESULT = 2;
  private static final int BEACON_STATISTICS_SHOW_LONG_CLICK_POP_MENU = 73;
  private static final int BEACON_STATISTICS_USER_ADJUSTMENT_RESULT = 6;
  private static final int BEACON_STATISTICS_USER_CANCEL = 9;
  private static final int BEACON_STATISTICS_USER_COPY = 51;
  private static final int BEACON_STATISTICS_USER_DIRECT_CONSUMPTION = 5;
  private static final int BEACON_STATISTICS_USER_FAVORITES = 55;
  private static final int BEACON_STATISTICS_USER_MORE_ITEMS = 53;
  private static final int BEACON_STATISTICS_USER_OTHER_MODE = 4;
  private static final int BEACON_STATISTICS_USER_SEARCH = 52;
  private static final int BEACON_STATISTICS_USER_SELECTION_COPYING_MODE = 3;
  private static final int BEACON_STATISTICS_USER_SUCCESS = 8;
  private static final int BEACON_STATISTICS_USER_TRANSLATION = 54;
  private static final int BEACON_STATISTICS_WHOLE_ADJUSTMENT_CANCEL = 92;
  private static final int BEACON_STATISTICS_WHOLE_ADJUSTMENT_CONSUMPTION = 82;
  private static final int BEACON_STATISTICS_WHOLE_DIRECT_CANCEL = 91;
  private static final int BEACON_STATISTICS_WHOLE_DIRECT_CONSUMPTION = 81;
  private static final int BEACON_STATISTICS_WX_MODE_REQ = 41;
  private static final int BEACON_STATISTICS_WX_MODE_RSP_SUCCESS = 42;
  private static final String BEACON_UPLOAD_KEY = "TBS_FINGERSEARCH_SERVICE_STATE_M57";
  private static final String TAG = "FingerSearch";
  private static final String WX_BEACON_STATISTICS_REQUEST_TIME_CONSUMING = "time_consuming";
  private static final String WX_BEACON_UPLOAD_KEY = "WX_TBS_FINGERSEARCH_SERVICE_STATE";
  private boolean[] exceptionStatusSet = new boolean[10];
  private WUPStreamConnection mConnection = null;
  private boolean mIsX5ModeForWX = false;
  private String mLastRspString;
  private int mLastSessionId = 0;
  private int mWXIndex = 0;
  private int mWXIsMainOrSub = 0;
  private String mWXRspString;
  private IX5WebView mWebView = null;
  private IX5WebViewClientExtension mWebViewClientExtension = null;
  private boolean[] operationStatusSet = new boolean[10];
  
  private void computeStatus()
  {
    boolean[] arrayOfBoolean = this.operationStatusSet;
    if ((arrayOfBoolean[0] != 0) && (arrayOfBoolean[1] != 0) && (arrayOfBoolean[2] != 0) && (arrayOfBoolean[3] != 0))
    {
      if (arrayOfBoolean[9] != 0)
      {
        if (arrayOfBoolean[6] == 0)
        {
          LogUtils.d("upLoadToBeacon", "[91] BEACON_STATISTICS_WHOLE_DIRECT_CANCEL");
          upLoadToBeacon(91, "This is direct cancel.");
          resetStatusSets();
          return;
        }
        LogUtils.d("upLoadToBeacon", "[92] BEACON_STATISTICS_WHOLE_ADJUSTMENT_CANCEL");
        upLoadToBeacon(92, "This is adjustment cancel.");
        resetStatusSets();
        return;
      }
      if ((arrayOfBoolean[5] != 0) && (arrayOfBoolean[8] != 0))
      {
        LogUtils.d("upLoadToBeacon", "[81] BEACON_STATISTICS_WHOLE_DIRECT_CONSUMPTION");
        upLoadToBeacon(81, "This is direct consumption.");
        resetStatusSets();
      }
      if (this.operationStatusSet[6] != 0)
      {
        LogUtils.d("upLoadToBeacon", "[82] BEACON_STATISTICS_WHOLE_ADJUSTMENT_CONSUMPTION");
        upLoadToBeacon(82, "This is adjustment consumption.");
        resetStatusSets();
      }
    }
  }
  
  private boolean fingerSearchForWX(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4)
  {
    paramString3 = new Bundle();
    if (paramString1 == null) {
      return false;
    }
    if (!putSelectInfoIntoBundleForWX(paramString1, paramInt1, true, paramString3)) {
      return false;
    }
    if (paramString2 != null) {
      putSelectInfoIntoBundleForWX(paramString2, paramInt2, false, paramString3);
    }
    paramString3.putInt("TagNewLineBefore", paramArrayOfInt[0]);
    paramString3.putInt("TagNewLineAfter", paramArrayOfInt[1]);
    paramString3.putInt("TagNewLineBeforeSub", paramArrayOfInt[2]);
    paramString3.putInt("TagNewLineAfterSub", paramArrayOfInt[3]);
    paramString3.putInt("Scene", 4);
    if (this.mWebViewClientExtension != null)
    {
      LogUtils.d("WX", "--> smartPickWord. REQ Bundle: ");
      paramString1 = new StringBuilder();
      paramString1.append("[0] MainIndex: ");
      paramString1.append(paramString3.getInt("MainIndex"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[1] PickedWord: ");
      paramString1.append(paramString3.getString("PickedWord"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[2] PrefixText: ");
      paramString1.append(paramString3.getString("PrefixText"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[3] SuffixText: ");
      paramString1.append(paramString3.getString("SuffixText"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[4] SubIndex: ");
      paramString1.append(paramString3.getInt("SubIndex"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[5] SubPickedWord: ");
      paramString1.append(paramString3.getString("SubPickedWord"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[6] SubPrefixText: ");
      paramString1.append(paramString3.getString("SubPrefixText"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[7] SubSuffixText: ");
      paramString1.append(paramString3.getString("SubSuffixText"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[8] TagNewLineBefore: ");
      paramString1.append(paramString3.getInt("TagNewLineBefore"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[9] TagNewLineAfter: ");
      paramString1.append(paramString3.getInt("TagNewLineAfter"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[10] TagNewLineBeforeSub: ");
      paramString1.append(paramString3.getInt("TagNewLineBeforeSub"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[11] TagNewLineAfterSub: ");
      paramString1.append(paramString3.getInt("TagNewLineAfterSub"));
      LogUtils.d("WX", paramString1.toString());
      paramString1 = new StringBuilder();
      paramString1.append("[12] Scene = ");
      paramString1.append(paramString3.getInt("Scene"));
      LogUtils.d("WX", paramString1.toString());
      long l = System.currentTimeMillis();
      paramString1 = this.mWebViewClientExtension.onMiscCallBack("smartPickWord", paramString3);
      upLoadToBeaconForWXTimeConsuming(System.currentTimeMillis() - l);
      if (paramString1 == null)
      {
        LogUtils.d("WX", "retObj is null.");
        return false;
      }
      if (!(paramString1 instanceof Bundle)) {}
    }
    try
    {
      paramString1 = (Bundle)paramString1;
      LogUtils.d("WX", "RSP Bundle: ");
      paramString2 = new StringBuilder();
      paramString2.append("[1] PickedWord: ");
      paramString2.append(paramString1.getString("PickedWord"));
      LogUtils.d("WX", paramString2.toString());
      paramString2 = new StringBuilder();
      paramString2.append("[2] PrefixText: ");
      paramString2.append(paramString1.getString("PrefixText"));
      paramString2.append(" PrefixOffset = ");
      paramString2.append(paramString1.getInt("PrefixOffset"));
      LogUtils.d("WX", paramString2.toString());
      paramString2 = new StringBuilder();
      paramString2.append("[3] SuffixText: ");
      paramString2.append(paramString1.getString("SuffixText"));
      paramString2.append(" SuffixOffset: ");
      paramString2.append(paramString1.getInt("SuffixOffset"));
      LogUtils.d("WX", paramString2.toString());
      paramString2 = paramString1.getString("PickedWord");
      if (!TextUtils.isEmpty(paramString2))
      {
        this.mWXRspString = paramString2;
        this.mWXIndex = paramString1.getString("PrefixText").length();
        this.mWXIsMainOrSub = paramString1.getInt("IsMainOrSub", 0);
        paramString1 = new StringBuilder();
        paramString1.append("mWXRspString: ");
        paramString1.append(this.mWXRspString);
        paramString1.append(" mWXIndex: ");
        paramString1.append(this.mWXIndex);
        paramString1.append(" mWXIsMainOrSub:");
        paramString1.append(this.mWXIsMainOrSub);
        LogUtils.d("WX", paramString1.toString());
        return true;
        LogUtils.d("WX", "retObj isn't Bundle.");
        return false;
        LogUtils.d("WX", "IX5WebViewClientExtension is null.");
      }
      return false;
    }
    catch (Exception paramString1) {}
    return false;
  }
  
  private void initSmartBoxManager()
  {
    if (this.mConnection == null)
    {
      this.mConnection = new WUPStreamConnection();
      this.mConnection.setWupConnectionListener(this);
      this.mConnection.setIsNeedPenddingAll(false);
      this.mConnection.reset(0);
    }
  }
  
  private void printStatusSets()
  {
    LogUtils.d("print", " ");
    LogUtils.d("upLoadToBeacon", ">>>> computeStatus");
    LogUtils.d("upLoadToBeacon", " ");
    LogUtils.d("print", "operationStatusSet:");
    LogUtils.d("print", "Index:    0   |   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9");
    LogUtils.d("print", "----------------------------------------------");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Status: ");
    localStringBuilder.append(this.operationStatusSet[0]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[1]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[2]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[3]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[4]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[5]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[6]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[7]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[8]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.operationStatusSet[9]);
    LogUtils.d("print", localStringBuilder.toString());
    LogUtils.d("print", "++++++++++++++++++++++++++++++++++++++++++++++");
    LogUtils.d("print", "exceptionStatusSet:");
    LogUtils.d("print", "Index:    0   |   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9");
    LogUtils.d("print", "----------------------------------------------");
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Status: ");
    localStringBuilder.append(this.exceptionStatusSet[0]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[1]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[2]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[3]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[4]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[5]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[6]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[7]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[8]);
    localStringBuilder.append(" | ");
    localStringBuilder.append(this.exceptionStatusSet[9]);
    LogUtils.d("print", localStringBuilder.toString());
    LogUtils.d("print", " ");
    LogUtils.d("upLoadToBeacon", "<<<< computeStatus");
    LogUtils.d("upLoadToBeacon", " ");
  }
  
  private boolean putSelectInfoIntoBundleForWX(String paramString, int paramInt, boolean paramBoolean, Bundle paramBundle)
  {
    int j = paramString.length();
    str2 = null;
    if ((j == 1) || (j == 2) || ((j <= 2) || (paramInt == 0))) {}
    for (;;)
    {
      try
      {
        str1 = paramString.substring(0, 2);
      }
      catch (Exception paramString)
      {
        String str1;
        int i;
        continue;
      }
      try
      {
        paramString = paramString.substring(2);
      }
      catch (Exception paramString) {}
    }
    paramString = null;
    break label145;
    str1 = null;
    paramString = str1;
    break label145;
    i = paramInt + 1;
    if (i == j)
    {
      i = paramInt - 1;
      str1 = paramString.substring(i);
    }
    try
    {
      str2 = paramString.substring(0, i);
      paramString = null;
    }
    catch (Exception paramString)
    {
      label145:
      for (;;) {}
    }
    paramString = null;
    break label145;
    j = paramInt - 1;
    str1 = paramString.substring(j, i);
    for (;;)
    {
      try
      {
        str2 = paramString.substring(0, j);
      }
      catch (Exception paramString)
      {
        StringBuilder localStringBuilder;
        continue;
      }
      try
      {
        paramString = paramString.substring(i);
      }
      catch (Exception paramString)
      {
        paramString = str2;
      }
    }
    paramString = null;
    str2 = paramString;
    paramString = null;
    break label164;
    return false;
    localStringBuilder = null;
    str1 = paramString;
    paramString = localStringBuilder;
    label164:
    if (paramBoolean)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("wxPickedWord: ");
      localStringBuilder.append(str1);
      LogUtils.d("WX", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("wxPrefixText: ");
      localStringBuilder.append(str2);
      LogUtils.d("WX", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("wxSuffixText: ");
      localStringBuilder.append(paramString);
      LogUtils.d("WX", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("MainIndex: ");
      localStringBuilder.append(paramInt);
      LogUtils.d("WX", localStringBuilder.toString());
      paramBundle.putString("PickedWord", str1);
      paramBundle.putString("PrefixText", str2);
      paramBundle.putString("SuffixText", paramString);
      paramBundle.putInt("MainIndex", paramInt);
      return true;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("wxSubPickedWord: ");
    localStringBuilder.append(str1);
    LogUtils.d("WX", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("wxSubPrefixText: ");
    localStringBuilder.append(str2);
    LogUtils.d("WX", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("wxSubSuffixText: ");
    localStringBuilder.append(paramString);
    LogUtils.d("WX", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("SubIndex: ");
    localStringBuilder.append(paramInt);
    LogUtils.d("WX", localStringBuilder.toString());
    paramBundle.putString("SubPickedWord", str1);
    paramBundle.putString("SubPrefixText", str2);
    paramBundle.putString("SubSuffixText", paramString);
    paramBundle.putInt("SubIndex", paramInt);
    return true;
  }
  
  private void resetStatusSets()
  {
    LogUtils.d("upLoadToBeacon", "resetStatusSets");
    int i = 0;
    while (i < 10)
    {
      this.operationStatusSet[i] = false;
      this.exceptionStatusSet[i] = false;
      i += 1;
    }
  }
  
  private void setStatusSet(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.operationStatusSet[paramInt] = true;
      return;
    }
    paramInt = -paramInt;
    this.exceptionStatusSet[paramInt] = true;
  }
  
  private void upLoadToBeacon(int paramInt, String paramString)
  {
    if ((paramInt == 8) || (paramInt == 9) || (paramInt == 6)) {
      computeStatus();
    }
    if (TbsBaseModuleShell.getCallerContext() == null)
    {
      upLoadToBeacon(-7, "The package name is exception.");
      return;
    }
    if ((TbsBaseModuleShell.getCallerContext().getPackageName().equals("com.tencent.mm")) || (TbsBaseModuleShell.getCallerContext().getPackageName().equals("com.tencent.mtt")))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("[stateCode] = ");
      ((StringBuilder)localObject).append(paramInt);
      ((StringBuilder)localObject).append(" [extraInfo] = ");
      ((StringBuilder)localObject).append(paramString);
      LogUtils.d("upLoadToBeacon", ((StringBuilder)localObject).toString());
      HashMap localHashMap = new HashMap();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("");
      ((StringBuilder)localObject).append(paramInt);
      localHashMap.put("stateCode", ((StringBuilder)localObject).toString());
      localObject = paramString;
      if (TextUtils.isEmpty(paramString)) {
        localObject = "";
      }
      localHashMap.put("extraInfo", localObject);
      TBSShellFactory.getSmttServiceCommon().upLoadToBeacon("TBS_FINGERSEARCH_SERVICE_STATE_M57", localHashMap);
    }
  }
  
  private void upLoadToBeaconForWXTimeConsuming(long paramLong)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("upLoadToBeaconForWXTimeConsuming: ");
    ((StringBuilder)localObject).append(paramLong);
    LogUtils.d("WX", ((StringBuilder)localObject).toString());
    if (TbsBaseModuleShell.getCallerContext().getPackageName().equals("com.tencent.mm"))
    {
      localObject = new HashMap();
      ((HashMap)localObject).put("time_consuming", String.valueOf(paramLong));
      TBSShellFactory.getSmttServiceCommon().upLoadToBeacon("WX_TBS_FINGERSEARCH_SERVICE_STATE", (Map)localObject);
    }
  }
  
  public void build(IX5WebView paramIX5WebView)
  {
    if (paramIX5WebView == null) {
      return;
    }
    this.mWebView = paramIX5WebView;
  }
  
  public void cancelFingerSearch(String paramString, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("cancelFingerSearch ServerRepText:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" isDestroyWebview = ");
    localStringBuilder.append(paramBoolean);
    LogUtils.d("selection", localStringBuilder.toString());
    if (paramBoolean)
    {
      LogUtils.d("upLoadToBeacon", "[-9] BEACON_STATISTICS_FAIL_USER_EXIT_WEBVIEW");
      setStatusSet(-9, false);
      upLoadToBeacon(-9, "Exit Webview");
      return;
    }
    if ((!TextUtils.isEmpty(this.mLastRspString)) && (this.mLastRspString.equals(paramString)))
    {
      LogUtils.d("upLoadToBeacon", "[9] BEACON_STATISTICS_USER_CANCEL");
      setStatusSet(9, true);
      upLoadToBeacon(9, paramString);
      return;
    }
    LogUtils.d("upLoadToBeacon", "[9] BEACON_STATISTICS_USER_CANCEL");
    setStatusSet(6, true);
    setStatusSet(9, true);
    upLoadToBeacon(9, paramString);
  }
  
  public void chooseFavorites(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("chooseFavorites Text: ");
    localStringBuilder.append(paramString);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[55] BEACON_STATISTICS_USER_FAVORITES");
    upLoadToBeacon(55, paramString);
    setStatusSet(5, true);
    fingerSearchSuccess();
  }
  
  public void chooseTranslation(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("chooseTranslation Text: ");
    localStringBuilder.append(paramString);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[54] BEACON_STATISTICS_USER_TRANSLATION");
    setStatusSet(5, true);
    upLoadToBeacon(54, paramString);
    fingerSearchSuccess();
  }
  
  public void enterFingerSearchMode()
  {
    LogUtils.d("selection", "enterFingerSearchMode");
    LogUtils.d("upLoadToBeacon", "[3] BEACON_STATISTICS_USER_SELECTION_COPYING_MODE");
    setStatusSet(3, true);
    upLoadToBeacon(3, "");
  }
  
  public void enterOtherMode()
  {
    LogUtils.d("selection", "enterOtherMode");
    LogUtils.d("upLoadToBeacon", "[4] BEACON_STATISTICS_USER_OTHER_MODE");
    setStatusSet(4, true);
    upLoadToBeacon(4, "This long-click is possible misoperation.");
  }
  
  public void executeCopyItem(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("executeCopyItem Text: ");
    localStringBuilder.append(paramString);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[51] BEACON_STATISTICS_USER_COPY");
    setStatusSet(5, true);
    upLoadToBeacon(51, paramString);
  }
  
  public void executeDiectConsumptionItems(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("executeDiectConsumptionItems Text: ");
    localStringBuilder.append(paramString);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[5] BEACON_STATISTICS_USER_DIRECT_CONSUMPTION");
    setStatusSet(5, true);
    upLoadToBeacon(5, paramString);
    fingerSearchSuccess();
  }
  
  public void executeMoreItem(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("executeMoreItem Text: ");
    localStringBuilder.append(paramString);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[53] BEACON_STATISTICS_USER_MORE_ITEMS");
    setStatusSet(5, true);
    upLoadToBeacon(53, paramString);
  }
  
  public void executeSearchItem(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("executeSearchItem Text: ");
    localStringBuilder.append(paramString);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[52] BEACON_STATISTICS_USER_SEARCH");
    setStatusSet(5, true);
    upLoadToBeacon(52, paramString);
  }
  
  public void exitX5LongClickPopMenu()
  {
    LogUtils.d("selection", "exitX5LongClickPopMenu");
    LogUtils.d("upLoadToBeacon", "[-8] BEACON_STATISTICS_FAIL_EXIT_X5_LONG_CLICK_POP_MENU");
    upLoadToBeacon(-8, "Exit X5 long click pop menu.");
    setStatusSet(-8, false);
  }
  
  public void fingerSearchRequest(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4)
  {
    LogUtils.d("selection", "------------finger search request stat start:-------------");
    LogUtils.d("selection", "main line:");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("text: ");
    ((StringBuilder)localObject).append(paramString1);
    LogUtils.d("selection", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("index:");
    ((StringBuilder)localObject).append(paramInt1);
    LogUtils.d("selection", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("breakinfo:");
    ((StringBuilder)localObject).append(paramArrayOfInt[0]);
    ((StringBuilder)localObject).append(",");
    ((StringBuilder)localObject).append(paramArrayOfInt[1]);
    LogUtils.d("selection", ((StringBuilder)localObject).toString());
    LogUtils.d("selection", "----------------------------------");
    LogUtils.d("selection", "sub line:");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("text: ");
    ((StringBuilder)localObject).append(paramString2);
    LogUtils.d("selection", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("index:");
    ((StringBuilder)localObject).append(paramInt2);
    LogUtils.d("selection", ((StringBuilder)localObject).toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("breakinfo:");
    ((StringBuilder)localObject).append(paramArrayOfInt[2]);
    ((StringBuilder)localObject).append(",");
    ((StringBuilder)localObject).append(paramArrayOfInt[3]);
    LogUtils.d("selection", ((StringBuilder)localObject).toString());
    LogUtils.d("selection", "------------finger search request stat end:-------------");
    initSmartBoxManager();
    if (TbsBaseModuleShell.getCallerContext().getPackageName().equals("com.tencent.mm"))
    {
      this.mWebViewClientExtension = this.mWebView.getWebViewClientExtension();
      localObject = this.mWebViewClientExtension;
      if (localObject != null)
      {
        localObject = ((IX5WebViewClientExtension)localObject).onMiscCallBack("supportSmartPickWord", new Bundle());
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("supportSmartPickWord: ");
        localStringBuilder.append(localObject);
        LogUtils.d("WX", localStringBuilder.toString());
        LogUtils.d("upLoadToBeacon", "[41] BEACON_STATISTICS_WX_MODE_REQ");
        upLoadToBeacon(41, "WX_MODE_REQ");
        if ((localObject != null) && ((localObject instanceof Boolean)) && (((Boolean)localObject).booleanValue()))
        {
          this.mWXRspString = "";
          this.mWXIndex = 0;
          this.mIsX5ModeForWX = fingerSearchForWX(paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, paramString3, paramString4);
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("mIsX5ModeForWX: ");
          ((StringBuilder)localObject).append(this.mIsX5ModeForWX);
          LogUtils.d("WX", ((StringBuilder)localObject).toString());
          if (this.mIsX5ModeForWX)
          {
            paramString1 = new StringBuilder();
            paramString1.append("mWXRspString: ");
            paramString1.append(this.mWXRspString);
            paramString1.append(" mWXIndex: ");
            paramString1.append(this.mWXIndex);
            LogUtils.d("WX", paramString1.toString());
            this.mWebView.onFingerSearchResult(this.mWXRspString, this.mWXIndex, this.mWXIsMainOrSub);
            this.mLastRspString = this.mWXRspString;
            LogUtils.d("upLoadToBeacon", "[42] BEACON_STATISTICS_WX_MODE_RSP_SUCCESS");
            paramString1 = new StringBuilder();
            paramString1.append("WX_MODE_RSP: ");
            paramString1.append(this.mWXRspString);
            upLoadToBeacon(42, paramString1.toString());
            return;
          }
        }
      }
    }
    resetStatusSets();
    search(paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, paramString3, paramString4);
  }
  
  public void fingerSearchSuccess()
  {
    LogUtils.d("selection", "fingerSearchSuccess");
    LogUtils.d("upLoadToBeacon", "[8] BEACON_STATISTICS_USER_SUCCESS");
    setStatusSet(8, true);
    upLoadToBeacon(8, "The result is right.");
  }
  
  public void onAllServersFailure(Throwable paramThrowable)
  {
    LogUtils.d("selection", "onAllServersFailure");
    LogUtils.d("upLoadToBeacon", "[-11] BEACON_STATISTICS_FAIL_WUP_ALL_SERVERS_FAILURE");
    setStatusSet(-1, false);
    upLoadToBeacon(-11, "Connect all WUP servers failed.");
  }
  
  public void onConnectException(Throwable paramThrowable, int paramInt)
  {
    paramThrowable = new StringBuilder();
    paramThrowable.append("onConnectException serverIndex: ");
    paramThrowable.append(paramInt);
    LogUtils.d("selection", paramThrowable.toString());
    LogUtils.d("upLoadToBeacon", "[-13] BEACON_STATISTICS_FAIL_WUP_CONNECT_EXCEPTION ");
    setStatusSet(-1, false);
    upLoadToBeacon(-13, "Connect exception.");
  }
  
  public void onFingerSearchFailed(UniPacket paramUniPacket)
  {
    LogUtils.d("selection", "onFingerSearchFailed");
    this.mWebView.onFingerSearchResult("", -1, 0);
  }
  
  public void onFingerSearchSuccess(UniPacket paramUniPacket)
  {
    try
    {
      paramUniPacket = (FingerSearchRsp)paramUniPacket.get("rsp", false, getClass().getClassLoader());
    }
    catch (ObjectCreateException paramUniPacket)
    {
      LogUtils.d("selection", "onFingerSearchSuccess exception.");
      LogUtils.d("upLoadToBeacon", "[-32] BEACON_STATISTICS_FAIL_RSP_DETAILS_EXCEPTION");
      setStatusSet(-3, false);
      upLoadToBeacon(-32, paramUniPacket.getMessage());
      paramUniPacket.printStackTrace();
      paramUniPacket = null;
    }
    if (paramUniPacket != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("rsp: ");
      localStringBuilder.append(paramUniPacket.sWord);
      localStringBuilder.append(" iSessionID: ");
      localStringBuilder.append(paramUniPacket.iSessionID);
      LogUtils.d("selection", localStringBuilder.toString());
      this.mLastRspString = paramUniPacket.sWord;
      this.mLastSessionId = paramUniPacket.iSessionID;
      if ((TextUtils.isEmpty(this.mLastRspString)) && (this.mLastSessionId == 0))
      {
        if (TextUtils.isEmpty(this.mLastRspString))
        {
          LogUtils.d("upLoadToBeacon", "[-4] BEACON_STATISTICS_FAIL_RSP_DETAILS_RESULT_NULL ");
          setStatusSet(-4, false);
          upLoadToBeacon(-4, "The result is null.");
        }
        if (this.mLastSessionId == 0)
        {
          LogUtils.d("upLoadToBeacon", "[-5] BEACON_STATISTICS_FAIL_RSP_DETAILS_SESSIONID_ZERO");
          setStatusSet(-5, false);
          upLoadToBeacon(-5, "The sessionID is 0.");
        }
        this.mWebView.onFingerSearchResult("", -1, 0);
        return;
      }
      if (((!TextUtils.isEmpty(this.mLastRspString)) && (this.mLastSessionId == 0)) || ((TextUtils.isEmpty(this.mLastRspString)) && (this.mLastSessionId != 0)))
      {
        LogUtils.d("upLoadToBeacon", "[-33] BEACON_STATISTICS_FAIL_RSP_DETAILS_MISMATCHING");
        setStatusSet(-3, false);
        upLoadToBeacon(-33, "The RSP is mismatching.");
        this.mWebView.onFingerSearchResult("", -1, 0);
        return;
      }
      int i = paramUniPacket.iIndex;
      LogUtils.d("upLoadToBeacon", "[2] BEACON_STATISTICS_RSP_RESULT");
      setStatusSet(2, true);
      upLoadToBeacon(2, this.mLastRspString);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("mLastRspString: ");
      localStringBuilder.append(this.mLastRspString);
      localStringBuilder.append(" index: ");
      localStringBuilder.append(i);
      localStringBuilder.append(" iIsMainOrSub: ");
      localStringBuilder.append(paramUniPacket.iIsMainOrSub);
      LogUtils.d("selection", localStringBuilder.toString());
      this.mWebView.onFingerSearchResult(this.mLastRspString, i, paramUniPacket.iIsMainOrSub);
      return;
    }
    LogUtils.d("selection", "onFingerSearchSuccess rsp is null");
    LogUtils.d("upLoadToBeacon", "[-31] BEACON_STATISTICS_FAIL_RSP_DETAILS_EMPTY");
    setStatusSet(-3, false);
    upLoadToBeacon(-31, "Response is empty");
  }
  
  public void onReceiveData(UniPacket paramUniPacket)
  {
    if (paramUniPacket == null) {
      return;
    }
    onFingerSearchSuccess(paramUniPacket);
  }
  
  public void onReceiveDataError()
  {
    LogUtils.d("selection", "onReceiveDataError");
    LogUtils.d("upLoadToBeacon", "[-2] BEACON_STATISTICS_FAIL_RSP_DATA_ERROR");
    setStatusSet(-2, false);
    upLoadToBeacon(-2, "Receive data error.");
  }
  
  public void onSendFailure(boolean paramBoolean1, boolean paramBoolean2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSendFailure isPending = ");
    localStringBuilder.append(paramBoolean1);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[-1] BEACON_STATISTICS_FAIL_REQ_FAILURE");
    if (paramBoolean2)
    {
      setStatusSet(0, false);
      if (paramBoolean1)
      {
        upLoadToBeacon(-1, "REQ failure and pending send.");
        return;
      }
      upLoadToBeacon(-1, "REQ failure and immediate send.");
    }
  }
  
  public void onSendSuccess(boolean paramBoolean1, boolean paramBoolean2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSendSuccess isPending = ");
    localStringBuilder.append(paramBoolean1);
    LogUtils.d("selection", localStringBuilder.toString());
    LogUtils.d("upLoadToBeacon", "[1] BEACON_STATISTICS_REQ_SUCCESS");
    if (paramBoolean2)
    {
      setStatusSet(1, true);
      if (paramBoolean1)
      {
        upLoadToBeacon(1, "REQ success and pending send.");
        return;
      }
      upLoadToBeacon(1, "REQ success and immediate send.");
    }
  }
  
  public void onSingleServerTimeout(Throwable paramThrowable, int paramInt)
  {
    LogUtils.d("selection", "onSingleServerTimeout");
    LogUtils.d("upLoadToBeacon", "[-12] BEACON_STATISTICS_FAIL_WUP_SINGLE_SERVER_TIMEOUT");
    setStatusSet(-1, false);
    paramThrowable = new StringBuilder();
    paramThrowable.append("A WUP Server timeout and server return: ");
    paramThrowable.append(paramInt);
    upLoadToBeacon(-12, paramThrowable.toString());
  }
  
  public void reconnect()
  {
    WUPStreamConnection localWUPStreamConnection = this.mConnection;
    if (localWUPStreamConnection != null)
    {
      localWUPStreamConnection.setWupConnectionListener(this);
      this.mConnection.reset(0);
    }
  }
  
  public void recyle()
  {
    WUPStreamConnection localWUPStreamConnection = this.mConnection;
    if (localWUPStreamConnection != null) {
      localWUPStreamConnection.disConnectServer();
    }
  }
  
  public void reportDoFingerSearch()
  {
    LogUtils.d("selection", "reportDoFingerSearch");
    LogUtils.d("upLoadToBeacon", "[72] BEACON_STATISTICS_REPORT_DO_FINGER_SEARCH");
    upLoadToBeacon(72, "Do finger search.");
  }
  
  public void reportDoLongClick()
  {
    LogUtils.d("selection", "reportDoLongClick");
    LogUtils.d("upLoadToBeacon", "[71] BEACON_STATISTICS_REPORT_DO_LONG_CLICK");
    upLoadToBeacon(71, "Do long click.");
  }
  
  public void reportShowLongClickPopupMenu()
  {
    LogUtils.d("selection", "reportShowLongClickPopupMenu");
    LogUtils.d("upLoadToBeacon", "[73] BEACON_STATISTICS_SHOW_LONG_CLICK_POP_MENU");
    upLoadToBeacon(73, "Show long click pop menu.");
  }
  
  public void reportUserFinallySelectText(String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("reportUserFinallySelectText mConnection ");
    ((StringBuilder)localObject1).append(this.mConnection);
    ((StringBuilder)localObject1).append(" mLastSessionId: ");
    ((StringBuilder)localObject1).append(this.mLastSessionId);
    ((StringBuilder)localObject1).append(" selText: ");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append(" mLastRspString: ");
    ((StringBuilder)localObject1).append(this.mLastRspString);
    LogUtils.d("selection", ((StringBuilder)localObject1).toString());
    if (this.mConnection != null)
    {
      Object localObject2 = new FingerQueryModifyReq();
      ((FingerQueryModifyReq)localObject2).vGuid = GUIDFactory.getInstance().getByteGuid();
      ((FingerQueryModifyReq)localObject2).sParseWords = this.mLastRspString;
      ((FingerQueryModifyReq)localObject2).sQuery = paramString;
      ((FingerQueryModifyReq)localObject2).iSessionID = this.mLastSessionId;
      localObject1 = new UniPacket();
      ((UniPacket)localObject1).setServantName("hotword");
      ((UniPacket)localObject1).setFuncName("reportFingerQueryModify");
      ((UniPacket)localObject1).setEncodeName("utf-8");
      ((UniPacket)localObject1).put("req", localObject2);
      if (!TextUtils.isEmpty(paramString))
      {
        LogUtils.d("upLoadToBeacon", "[6] BEACON_STATISTICS_USER_ADJUSTMENT_RESULT");
        setStatusSet(6, true);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Before: ");
        ((StringBuilder)localObject2).append(this.mLastRspString);
        ((StringBuilder)localObject2).append(" After: ");
        ((StringBuilder)localObject2).append(paramString);
        upLoadToBeacon(6, ((StringBuilder)localObject2).toString());
        this.mConnection.sendData((UniPacket)localObject1, false);
        if (paramString.length() < 5)
        {
          LogUtils.d("upLoadToBeacon", "[61] BEACON_STATISTICS_ADJUSTMENT_LENGTH_LESS_5");
          setStatusSet(6, true);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("LESS 5: Before: ");
          ((StringBuilder)localObject1).append(this.mLastRspString);
          ((StringBuilder)localObject1).append(" After: ");
          ((StringBuilder)localObject1).append(paramString);
          upLoadToBeacon(61, ((StringBuilder)localObject1).toString());
          return;
        }
        if (paramString.length() > 10)
        {
          LogUtils.d("upLoadToBeacon", "[62] BEACON_STATISTICS_ADJUSTMENT_LENGTH_GREATER_10");
          setStatusSet(6, true);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("GREATER 10: Before: ");
          ((StringBuilder)localObject1).append(this.mLastRspString);
          ((StringBuilder)localObject1).append(" After: ");
          ((StringBuilder)localObject1).append(paramString);
          upLoadToBeacon(62, ((StringBuilder)localObject1).toString());
          return;
        }
        LogUtils.d("upLoadToBeacon", "[63] BEACON_STATISTICS_ADJUSTMENT_LENGTH_BETWEEN_5_10");
        setStatusSet(6, true);
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("BETWEEN 5 and 10 Before: ");
        ((StringBuilder)localObject1).append(this.mLastRspString);
        ((StringBuilder)localObject1).append(" After: ");
        ((StringBuilder)localObject1).append(paramString);
        upLoadToBeacon(63, ((StringBuilder)localObject1).toString());
        return;
      }
      LogUtils.d("upLoadToBeacon", "[-6] BEACON_STATISTICS_FAIL_ADJUSTMENT_TEXT_EXCEPTION");
      setStatusSet(-6, false);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("BETWEEN 5 and 10 Before: ");
      ((StringBuilder)localObject1).append(this.mLastRspString);
      ((StringBuilder)localObject1).append(" After: ");
      ((StringBuilder)localObject1).append(paramString);
      upLoadToBeacon(-6, ((StringBuilder)localObject1).toString());
    }
  }
  
  public void search(String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4)
  {
    try
    {
      if (this.mConnection == null) {
        break label243;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("FingerSearchReq:");
      ((StringBuilder)localObject).append(paramString1);
      ((StringBuilder)localObject).append(" ");
      ((StringBuilder)localObject).append(paramInt1);
      LogUtils.d("selection", ((StringBuilder)localObject).toString());
      this.mLastSessionId = 0;
      this.mLastRspString = "";
      localObject = new FingerSearchReq();
      ((FingerSearchReq)localObject).vGuid = GUIDFactory.getInstance().getByteGuid();
      ((FingerSearchReq)localObject).sText = paramString1;
      ((FingerSearchReq)localObject).iTouchIndex = paramInt1;
      ((FingerSearchReq)localObject).sTextSub = paramString2;
      ((FingerSearchReq)localObject).iIndexSub = paramInt2;
      ((FingerSearchReq)localObject).iTagNewLineBefore = paramArrayOfInt[0];
      ((FingerSearchReq)localObject).iTagNewLineAfter = paramArrayOfInt[1];
      ((FingerSearchReq)localObject).iTagNewLineBeforeSub = paramArrayOfInt[2];
      ((FingerSearchReq)localObject).iTagNewLineAfterSub = paramArrayOfInt[3];
      ((FingerSearchReq)localObject).sUrl = paramString3;
      ((FingerSearchReq)localObject).sTitle = paramString4;
      paramString2 = new UniPacket();
      paramString2.setServantName("hotword");
      paramString2.setFuncName("getFingerSearchWord");
      paramString2.setEncodeName("utf-8");
      paramString2.put("req", localObject);
      LogUtils.d("upLoadToBeacon", "[0] BEACON_STATISTICS_REQ");
      setStatusSet(0, true);
      upLoadToBeacon(0, paramString1);
      this.mConnection.sendData(paramString2, true);
      return;
    }
    catch (Exception paramString1)
    {
      label243:
      for (;;) {}
    }
    LogUtils.d("selection", "search exception.");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\FingerSearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */