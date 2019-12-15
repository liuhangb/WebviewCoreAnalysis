package com.tencent.mtt.external.reader;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.export.IReaderEventProxy;
import com.tencent.mtt.external.reader.export.IReaderProxy;
import com.tencent.mtt.external.reader.utils.ClipboardMangerAdapter;
import com.tencent.tbs.common.resources.TBSResources;

public class ReaderCallbackAction
{
  public static final int COPY_SELECT_TEXT = 5003;
  public static final int GET_BAR_ANIMATING = 5000;
  public static final int GET_BAR_ISSHOWING = 5024;
  public static final int HIDDEN_BAR = 5001;
  public static final int INSTALL_QB = 5011;
  public static final String IS_BAR_ANIMATING = "is_bar_animating";
  public static final String IS_BAR_SHOWING = "is_bar_show";
  public static final int NOTIFY_Common_Info = 5049;
  public static final int NOTIFY_ENTRY_EDIT_MODE = 5070;
  public static final int NOTIFY_QUERY_ENCRYPT_SUPPORT = 5060;
  public static final int NOTIFY_REQUEST_ENCRYPT_INPUT_PWD = 5061;
  public static final int NOTIFY_RESPONSE_ENCRYPT_INPUT_PWD = 5062;
  public static final int NOTIFY_RESPONSE_ENCRYPT_PASSWORD_VERIFY = 5063;
  public static final int NOTIFY_SHOW_TOOLBAR = 5050;
  public static final int READER_PAGE_TOAST = 5048;
  public static final int READER_PDF_LIST = 5008;
  public static final int READER_PLUGIN_ACTIVITY_PAUSE = 5032;
  public static final int READER_PLUGIN_CANLOAD = 5013;
  public static final int READER_PLUGIN_COMMAND_FIXSCREEN = 5015;
  public static final int READER_PLUGIN_COMMAND_PDF_LIST = 5036;
  public static final int READER_PLUGIN_COMMAND_PPT_PLAYER = 5035;
  public static final int READER_PLUGIN_COMMAND_ROTATESCREEN = 5018;
  public static final int READER_PLUGIN_COMMAND_TEXT_FIND = 5038;
  public static final int READER_PLUGIN_COMMAND_TEXT_FIND_CLEAR = 5041;
  public static final int READER_PLUGIN_COMMAND_TEXT_FIND_NEXT = 5039;
  public static final int READER_PLUGIN_COMMAND_TEXT_FIND_PREV = 5040;
  public static final int READER_PLUGIN_COMMAND_TEXT_FIT_SCREEN = 6003;
  public static final int READER_PLUGIN_DOCX_FINDRESULT = 6000;
  public static final int READER_PLUGIN_DOWNLOADING = 5014;
  public static final int READER_PLUGIN_PDF_OUTLINE_EXIST = 5037;
  public static final int READER_PLUGIN_QUERY_TEXT_FIND = 6001;
  public static final int READER_PLUGIN_QUERY_TEXT_FIT_SCREEN = 6002;
  public static final int READER_PLUGIN_RES_FIXSCREEN_NORMAL = 5016;
  public static final int READER_PLUGIN_RES_FIXSCREEN_PRESS = 5017;
  public static final int READER_PLUGIN_RES_ROTATESCREEN_NORMAL = 5019;
  public static final int READER_PLUGIN_RES_ROTATESCREEN_PRESS = 5020;
  public static final int READER_PLUGIN_SO_ERR = 5025;
  public static final int READER_PLUGIN_SO_INTO_START = 5027;
  public static final int READER_PLUGIN_SO_PROGRESS = 5028;
  public static final int READER_PLUGIN_SO_VERSION = 5030;
  public static final int READER_PLUGIN_STATUS = 5012;
  public static final int READER_PLUGIN_TEXT_FIND_RESULT = 5042;
  public static final int READER_PPT_PLAY_MODEL = 5009;
  public static final int READER_SEARCH_IN_DOCUMENT = 5026;
  public static final int READER_TOAST = 5005;
  public static final int READER_TXT_READING_MODEL = 5010;
  public static final int REQUEST_DOWNLOAD_QB = 5007;
  public static final int SEARCH_SELECT_TEXT = 5004;
  public static final int SHOW_BAR = 5002;
  public static final int SHOW_DIALOG = 5006;
  public static final int WEBVIEW_GET_TITLE_HEIGHT = 5045;
  public static final int WEBVIEW_GET_VISBLE_TITLE_HEIGHT = 5047;
  public static final int WEBVIEW_ON_VISBLE_TITLE_HEIGHT_CHANGED = 5046;
  Context jdField_a_of_type_AndroidContentContext;
  IReaderEventProxy jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy;
  IReaderProxy jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy;
  
  public ReaderCallbackAction(Context paramContext, IReaderProxy paramIReaderProxy)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy = paramIReaderProxy;
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy = this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.getReaderEventProxy();
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy = null;
    this.jdField_a_of_type_AndroidContentContext = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy = null;
  }
  
  public void doCallBackEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doCallBackEvent,actionType=");
    ((StringBuilder)localObject).append(paramInt);
    LogUtils.d("MttFileReaderWrapper", ((StringBuilder)localObject).toString());
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy;
    if (localObject != null) {
      switch (paramInt)
      {
      default: 
        switch (paramInt)
        {
        default: 
          switch (paramInt)
          {
          default: 
            switch (paramInt)
            {
            default: 
              switch (paramInt)
              {
              default: 
                return;
              }
              break;
            }
            break;
          }
          break;
        }
        break;
      }
    }
    try
    {
      ((IReaderEventProxy)localObject).doCallBackEvent(paramInt, paramObject1, paramObject2);
      if ((paramObject1 != null) && ((paramObject1 instanceof String)))
      {
        paramObject1 = (String)paramObject1;
        if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy != null)
        {
          this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.doSearch(this.jdField_a_of_type_AndroidContentContext, (String)paramObject1);
          return;
          ((IReaderEventProxy)localObject).doCallBackEvent(paramInt, paramObject1, paramObject2);
          if ((paramObject1 != null) && ((paramObject1 instanceof String)))
          {
            paramObject1 = (String)paramObject1;
            ClipboardMangerAdapter.setText(this.jdField_a_of_type_AndroidContentContext, (String)paramObject1);
            if (!TextUtils.isEmpty((CharSequence)paramObject1)) {
              Toast.makeText(this.jdField_a_of_type_AndroidContentContext, TBSResources.getString("x5_copy_to_paste"), 0).show();
            }
          }
          else
          {
            return;
            this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy.doCallBackEvent(paramInt, paramObject1, paramObject2);
          }
        }
        return;
      }
    }
    catch (Exception paramObject1) {}
  }
  
  public boolean isBarAnimating()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy != null)
    {
      Bundle localBundle = new Bundle();
      this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy.doCallBackEvent(5000, null, localBundle);
      return localBundle.getBoolean("is_bar_animating", true);
    }
    return false;
  }
  
  public int isBarShow()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy != null)
    {
      Bundle localBundle = new Bundle();
      this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderEventProxy.doCallBackEvent(5024, null, localBundle);
      return localBundle.getInt("is_bar_show", -1);
    }
    return -1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\ReaderCallbackAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */