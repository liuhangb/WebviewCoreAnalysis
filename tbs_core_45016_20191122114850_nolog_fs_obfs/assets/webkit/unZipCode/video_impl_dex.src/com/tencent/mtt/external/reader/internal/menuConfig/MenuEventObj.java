package com.tencent.mtt.external.reader.internal.menuConfig;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.MttLoader.BrowserInfo;
import com.tencent.mtt.external.reader.PDFOutlineData;
import com.tencent.mtt.external.reader.base.ReaderController;
import com.tencent.mtt.external.reader.base.ReaderDefaultView;
import com.tencent.mtt.external.reader.base.ReaderPdfView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class MenuEventObj
{
  public static final String INTENT_ACTION_SDK_DOCUMENT = "com.tencent.QQBrowser.action.sdk.document";
  public static final String INTENT_ACTION_VIEW = "com.tencent.QQBrowser.action.VIEW";
  public static final String KEY_BUNDLE_FEATURE_ID = "REQ_FEATURE_ID";
  public static final String KEY_BUNDLE_OUTLINE_DATA = "outlinedata";
  public static final int KEY_DEFAULT_FEATURE_ID_DOC_SEARCH = 4014;
  public static final int KEY_DEFAULT_FEATURE_ID_PDF_OUTLINE = 4011;
  public static final String KEY_INTENT_BUNDLE_EXTRA_EXTRALS = "key_reader_sdk_extrals";
  public static final String KEY_INTENT_INT_EXTRA_SDK_ID = "key_reader_sdk_id";
  public static final String KEY_INTENT_INT_EXTRA_SDK_TYPE = "key_reader_sdk_type";
  public static final String KEY_INTENT_STRING_EXTRA_CALL_POS = "PosID";
  public static final String KEY_INTENT_STRING_EXTRA_CHANNEL_ID = "ChannelID";
  public static final String KEY_INTENT_STRING_EXTRA_FORMAT = "key_reader_sdk_format";
  public static final String KEY_INTENT_STRING_EXTRA_SDK_PATH = "key_reader_sdk_path";
  public static final String KEY_INTENT_STRING_EXTRA_SDK_URL = "key_reader_sdk_url";
  public static final String VALUE_PACKAGE_NAME = "com.tencent.mtt";
  public final int MENU_ACTION_TYPE_CALLBACK_EVENT = 2;
  public final int MENU_ACTION_TYPE_START_INTENT = 1;
  public final int MENU_ACTION_TYPE_UNDEFINED = 0;
  public final int VALUE_INTENT_PARAMS_CALL_POS = 4;
  public final int VALUE_INTENT_PARAMS_SDK_ID = 3;
  public final int VALUE_READER_SDK_TYPE_LOCAL = 0;
  public final int VALUE_READER_SDK_TYPE_ONLINE = 1;
  public final int VALUE_REQ_MIN_QB_VER = 601660;
  private int jdField_a_of_type_Int = 0;
  private Uri jdField_a_of_type_AndroidNetUri = null;
  private Bundle jdField_a_of_type_AndroidOsBundle = null;
  private CommandCallback jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback = null;
  private MenuActionListener jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$MenuActionListener = null;
  private final String jdField_a_of_type_JavaLangString = "MttMenuEventObj";
  private HashMap<String, Object> jdField_a_of_type_JavaUtilHashMap = null;
  private boolean jdField_a_of_type_Boolean = true;
  private int jdField_b_of_type_Int = 601660;
  private String jdField_b_of_type_JavaLangString = "";
  private final int c = 0;
  private final int d = 1;
  private int e = 0;
  
  private Intent a(Context paramContext)
  {
    try
    {
      Iterator localIterator;
      if ((this.jdField_b_of_type_JavaLangString != null) && (!this.jdField_b_of_type_JavaLangString.isEmpty())) {
        if ((this.jdField_a_of_type_JavaUtilHashMap != null) && (!this.jdField_a_of_type_JavaUtilHashMap.isEmpty()))
        {
          paramContext = new Intent(this.jdField_b_of_type_JavaLangString, this.jdField_a_of_type_AndroidNetUri);
          paramContext.setPackage("com.tencent.mtt");
          localIterator = this.jdField_a_of_type_JavaUtilHashMap.entrySet().iterator();
        }
      }
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        Object localObject = localEntry.getValue();
        if ((localObject instanceof String))
        {
          if ("key_reader_sdk_format".equalsIgnoreCase((String)localEntry.getKey()))
          {
            Uri localUri = this.jdField_a_of_type_AndroidNetUri;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("mtt/");
            localStringBuilder.append((String)localObject);
            paramContext.setDataAndType(localUri, localStringBuilder.toString());
          }
          paramContext.putExtra((String)localEntry.getKey(), (String)localObject);
        }
        else if ((localObject instanceof Integer))
        {
          paramContext.putExtra((String)localEntry.getKey(), (Integer)localObject);
        }
        else if ((localObject instanceof Bundle))
        {
          paramContext.putExtra((String)localEntry.getKey(), (Bundle)localObject);
        }
        else
        {
          LogUtils.d("MttMenuEventObj", "Unexpect args type");
          continue;
          LogUtils.d("MttMenuEventObj", "Invalid intent extra data!");
          return null;
          LogUtils.d("MttMenuEventObj", "Invalid intent URI!");
          return null;
        }
      }
    }
    catch (Throwable paramContext)
    {
      LogUtils.d("MttMenuEventObj", paramContext.getMessage());
      return null;
    }
    return paramContext;
  }
  
  private void a(Context paramContext, Intent paramIntent) {}
  
  private void a(Context paramContext, ReaderController paramReaderController)
  {
    if (this.jdField_a_of_type_AndroidNetUri == null)
    {
      String str2 = paramReaderController.getFilePath();
      this.jdField_a_of_type_AndroidNetUri = Uri.fromFile(new File(str2));
      String str1;
      if (((Integer)this.jdField_a_of_type_JavaUtilHashMap.get("key_reader_sdk_type")).intValue() == 0) {
        str1 = "key_reader_sdk_path";
      } else {
        str1 = "key_reader_sdk_url";
      }
      setIntentExtraKV(str1, str2);
      setIntentExtraKV("key_reader_sdk_format", paramReaderController.getFileExt());
    }
    setIntentExtraKV("ChannelID", paramContext.getApplicationInfo().processName);
    paramContext = MttLoader.getBrowserInfo(paramContext);
    if ((paramContext != null) && (paramContext.ver > 601660) && (paramContext.ver <= 611710) && (this.jdField_a_of_type_JavaUtilHashMap.containsKey("key_reader_sdk_extrals")))
    {
      paramContext = (Bundle)this.jdField_a_of_type_JavaUtilHashMap.get("key_reader_sdk_extrals");
      if ((paramContext != null) && (paramContext.containsKey("REQ_FEATURE_ID")) && (paramContext.getInt("REQ_FEATURE_ID") == 4016))
      {
        paramContext.putInt("REQ_FEATURE_ID", 4014);
        LogUtils.d("MttMenuEventObj", "Warning, fix QB issue, change feature ID 4016 => 4014");
      }
    }
  }
  
  private boolean a(Context paramContext)
  {
    try
    {
      if (!this.jdField_a_of_type_Boolean) {
        return true;
      }
      if (this.jdField_b_of_type_Int <= 0) {
        return true;
      }
      paramContext = MttLoader.getBrowserInfo(paramContext);
      if ((paramContext != null) && (paramContext.ver > 0))
      {
        if (paramContext.ver >= this.jdField_b_of_type_Int) {
          return true;
        }
        if ((this.jdField_a_of_type_AndroidOsBundle != null) && (this.jdField_a_of_type_AndroidOsBundle.containsKey("upgrade_tip"))) {
          this.jdField_a_of_type_AndroidOsBundle.putString("tip", this.jdField_a_of_type_AndroidOsBundle.getString("upgrade_tip"));
        }
      }
    }
    catch (Exception paramContext)
    {
      LogUtils.d("MttMenuEventObj", paramContext.getMessage());
    }
    return false;
  }
  
  private void b()
  {
    if (this.jdField_a_of_type_JavaUtilHashMap == null)
    {
      this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
      this.jdField_a_of_type_JavaUtilHashMap.put("PosID", String.valueOf(4));
      this.jdField_a_of_type_JavaUtilHashMap.put("key_reader_sdk_id", Integer.valueOf(3));
      this.jdField_a_of_type_JavaUtilHashMap.put("key_reader_sdk_type", Integer.valueOf(0));
    }
  }
  
  void a()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback != null) {
      return;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback = new CommandCallback()
    {
      public void onCommandDone(Context paramAnonymousContext, ReaderDefaultView paramAnonymousReaderDefaultView, Message paramAnonymousMessage)
      {
        if (205 == paramAnonymousMessage.what)
        {
          if (MenuEventObj.a(MenuEventObj.this).containsKey("key_reader_sdk_extrals"))
          {
            Bundle localBundle = (Bundle)MenuEventObj.a(MenuEventObj.this).get("key_reader_sdk_extrals");
            if (localBundle != null) {
              localBundle.putParcelableArrayList("outlinedata", (ArrayList)paramAnonymousMessage.obj);
            }
          }
          else
          {
            LogUtils.d("MttMenuEventObj", "Invalid init menu params, expect extra bundle key:MenuEventObj.KEY_INTENT_BUNDLE_EXTRA_EXTRALS");
          }
          MenuEventObj.this.executeAction(paramAnonymousContext, paramAnonymousReaderDefaultView);
        }
      }
    };
  }
  
  public void executeAction(Context paramContext, ReaderDefaultView paramReaderDefaultView)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$MenuActionListener.onMenuEventExecurte();
    if (this.jdField_a_of_type_Int == 0)
    {
      paramContext = new StringBuilder();
      paramContext.append("Unexpect Action type:");
      paramContext.append(this.jdField_a_of_type_Int);
      LogUtils.d("MttMenuEventObj", paramContext.toString());
      return;
    }
    Object localObject1 = paramReaderDefaultView.mReaderController;
    if (localObject1 == null)
    {
      LogUtils.d("MttMenuEventObj", "Unexpect reader controller!");
      return;
    }
    if (!a(paramContext))
    {
      LogUtils.d("MttMenuEventObj", "QQBrowser version is not supported, callback to install QB!");
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$MenuActionListener.onRequestInstallQB();
      if (this.e == 0)
      {
        ((ReaderController)localObject1).doCallBackEvent(5011, this.jdField_a_of_type_AndroidOsBundle, null);
        return;
      }
      a(paramContext, (ReaderController)localObject1);
      a(paramContext, a(paramContext));
      return;
    }
    if (this.jdField_a_of_type_JavaUtilHashMap.containsKey("key_reader_sdk_extrals"))
    {
      Object localObject2 = (Bundle)this.jdField_a_of_type_JavaUtilHashMap.get("key_reader_sdk_extrals");
      if ((localObject2 != null) && (((Bundle)localObject2).containsKey("REQ_FEATURE_ID")) && (((Bundle)localObject2).getInt("REQ_FEATURE_ID") == 4011))
      {
        if ((paramReaderDefaultView instanceof ReaderPdfView)) {
          ((ReaderPdfView)paramReaderDefaultView).startService();
        }
        if (!((Bundle)localObject2).containsKey("outlinedata"))
        {
          a();
          paramReaderDefaultView.setMenuCommandCallback(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$CommandCallback);
          paramReaderDefaultView.doCommand(308, null, null);
          return;
        }
        int j = paramReaderDefaultView.getCurPageID();
        paramReaderDefaultView = new StringBuilder();
        paramReaderDefaultView.append("curPageId:");
        paramReaderDefaultView.append(j);
        LogUtils.d("MttMenuEventObj", paramReaderDefaultView.toString());
        paramReaderDefaultView = ((Bundle)localObject2).getParcelableArrayList("outlinedata");
        if (paramReaderDefaultView != null)
        {
          int i = -1;
          localObject2 = paramReaderDefaultView.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            Object localObject3 = (Parcelable)((Iterator)localObject2).next();
            PDFOutlineData localPDFOutlineData = (PDFOutlineData)localObject3;
            localPDFOutlineData.setCurrOutline(false);
            if (localPDFOutlineData.getPage() <= j)
            {
              i = paramReaderDefaultView.indexOf(localObject3);
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("curOutlineIdx:");
              ((StringBuilder)localObject3).append(i);
              LogUtils.d("MttMenuEventObj", ((StringBuilder)localObject3).toString());
            }
          }
          if ((i >= 0) && (i < paramReaderDefaultView.size())) {
            ((PDFOutlineData)paramReaderDefaultView.get(i)).setCurrOutline(true);
          }
        }
      }
    }
    for (;;)
    {
      try
      {
        switch (this.jdField_a_of_type_Int)
        {
        case 1: 
          a(paramContext, (ReaderController)localObject1);
          paramReaderDefaultView = a(paramContext);
          if (paramReaderDefaultView != null)
          {
            if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$MenuActionListener != null) {
              this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$MenuActionListener.onLaunchQB();
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("MENU_ACTION_TYPE_START_INTENT:");
            ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaUtilHashMap.toString());
            LogUtils.d("MttMenuEventObj", ((StringBuilder)localObject1).toString());
            paramContext.startActivity(paramReaderDefaultView);
            return;
          }
          LogUtils.d("MttMenuEventObj", "Invalid intent!");
          return;
        }
      }
      catch (Throwable paramContext)
      {
        LogUtils.d("MttMenuEventObj", paramContext.getMessage());
        return;
      }
      paramContext = new StringBuilder();
      paramContext.append("Invalid action type:");
      paramContext.append(this.jdField_a_of_type_Int);
      LogUtils.d("MttMenuEventObj", paramContext.toString());
      return;
    }
  }
  
  public void setActionListener(MenuActionListener paramMenuActionListener)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj$MenuActionListener = paramMenuActionListener;
  }
  
  public void setCheckQBVer(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public void setIntentAction(String paramString, Uri paramUri)
  {
    this.jdField_a_of_type_Int = 1;
    this.jdField_b_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_AndroidNetUri = paramUri;
  }
  
  public void setIntentExtraKV(String paramString, Object paramObject)
  {
    b();
    this.jdField_a_of_type_JavaUtilHashMap.put(paramString, paramObject);
  }
  
  public void setRequiredQB(int paramInt, Bundle paramBundle)
  {
    this.jdField_b_of_type_Int = paramInt;
    this.jdField_a_of_type_AndroidOsBundle = paramBundle;
  }
  
  public static abstract interface CommandCallback
  {
    public abstract void onCommandDone(Context paramContext, ReaderDefaultView paramReaderDefaultView, Message paramMessage);
  }
  
  public static abstract interface MenuActionListener
  {
    public abstract void onLaunchQB();
    
    public abstract void onMenuEventExecurte();
    
    public abstract void onRequestInstallQB();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuEventObj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */