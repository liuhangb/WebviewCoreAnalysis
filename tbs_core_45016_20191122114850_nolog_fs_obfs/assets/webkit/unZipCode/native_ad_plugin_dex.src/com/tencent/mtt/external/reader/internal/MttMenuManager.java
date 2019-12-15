package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.IReaderEvent;
import com.tencent.mtt.external.reader.base.ReaderMenuManager;
import com.tencent.mtt.external.reader.base.ReaderMenuView;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuEventObj;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.resources.TBSResources;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MttMenuManager
  extends ReaderMenuManager
  implements View.OnClickListener
{
  private Context jdField_a_of_type_AndroidContentContext = null;
  private FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  private IReaderEvent jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = null;
  private ReaderMenuView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView = null;
  private HashMap<Integer, MenuEventObj> jdField_a_of_type_JavaUtilHashMap = null;
  private boolean jdField_a_of_type_Boolean = true;
  
  public MttMenuManager(Context paramContext, FrameLayout paramFrameLayout)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidWidgetFrameLayout = paramFrameLayout;
    initMenuEvents();
  }
  
  private Map<Integer, String> a(ArrayList<Integer> paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      int i = ((Integer)paramArrayList.next()).intValue();
      switch (i)
      {
      case 1004: 
      default: 
        break;
      case 1003: 
        localHashMap.put(Integer.valueOf(i), TBSResources.getString("reader_search2"));
        break;
      case 1002: 
        localHashMap.put(Integer.valueOf(i), TBSResources.getString("reader_model_project"));
        break;
      case 1001: 
        localHashMap.put(Integer.valueOf(i), TBSResources.getString("reader_model_reading"));
        break;
      case 1000: 
        localHashMap.put(Integer.valueOf(i), TBSResources.getString("reader_outline"));
      }
    }
    return localHashMap;
  }
  
  private void a()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView = new ReaderMenuView(this.jdField_a_of_type_AndroidContentContext);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView.setMenuClickListener(this);
      a(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView);
    }
  }
  
  private void a(View paramView)
  {
    if (this.jdField_a_of_type_AndroidWidgetFrameLayout != null)
    {
      if (paramView == null) {
        return;
      }
      ViewParent localViewParent = paramView.getParent();
      FrameLayout localFrameLayout = this.jdField_a_of_type_AndroidWidgetFrameLayout;
      if (localViewParent == localFrameLayout) {
        return;
      }
      localFrameLayout.addView(paramView, new FrameLayout.LayoutParams(-1, -1));
      return;
    }
  }
  
  public void destory()
  {
    removeAll();
    this.jdField_a_of_type_AndroidWidgetFrameLayout = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = null;
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public void focus()
  {
    ReaderMenuView localReaderMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView;
    if (localReaderMenuView == null) {
      return;
    }
    localReaderMenuView.bringToFront();
  }
  
  public int initMenu(int paramInt)
  {
    removeAll();
    if ((paramInt > 0) && (Configuration.getInstance(this.jdField_a_of_type_AndroidContentContext).isFileReaderMenuEnabled(paramInt)))
    {
      a();
      return setMenu(paramInt);
    }
    return -1;
  }
  
  public void initMenuEvents()
  {
    if (this.jdField_a_of_type_JavaUtilHashMap == null) {
      this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
    }
    MenuEventObj localMenuEventObj = new MenuEventObj();
    localMenuEventObj.setIntentAction("com.tencent.QQBrowser.action.sdk.document", null);
    Bundle localBundle = new Bundle();
    localBundle.putInt("REQ_FEATURE_ID", 4013);
    localMenuEventObj.setIntentExtraKV("key_reader_sdk_extrals", localBundle);
    this.jdField_a_of_type_JavaUtilHashMap.put(Integer.valueOf(1001), localMenuEventObj);
    localMenuEventObj = new MenuEventObj();
    localMenuEventObj.setIntentAction("com.tencent.QQBrowser.action.sdk.document", null);
    localBundle = new Bundle();
    localBundle.putInt("REQ_FEATURE_ID", 4012);
    localMenuEventObj.setIntentExtraKV("key_reader_sdk_extrals", localBundle);
    this.jdField_a_of_type_JavaUtilHashMap.put(Integer.valueOf(1002), localMenuEventObj);
    localMenuEventObj = new MenuEventObj();
    localMenuEventObj.setIntentAction("com.tencent.QQBrowser.action.sdk.document", null);
    localBundle = new Bundle();
    localBundle.putInt("REQ_FEATURE_ID", 4014);
    localMenuEventObj.setIntentExtraKV("key_reader_sdk_extrals", localBundle);
    this.jdField_a_of_type_JavaUtilHashMap.put(Integer.valueOf(1003), localMenuEventObj);
    localMenuEventObj = new MenuEventObj();
    localMenuEventObj.setIntentAction("com.tencent.QQBrowser.action.sdk.document", null);
    localBundle = new Bundle();
    localBundle.putInt("REQ_FEATURE_ID", 4011);
    localMenuEventObj.setIntentExtraKV("key_reader_sdk_extrals", localBundle);
    this.jdField_a_of_type_JavaUtilHashMap.put(Integer.valueOf(1000), localMenuEventObj);
    localMenuEventObj = new MenuEventObj();
    localMenuEventObj.setIntentAction("com.tencent.QQBrowser.action.VIEW", Uri.parse("qb://filesystem?fromwhere=18"));
    localMenuEventObj.setIntentExtraKV("fromwhere", Integer.valueOf(9));
    localMenuEventObj.setIntentExtraKV("appid", Integer.valueOf(60480));
    localBundle = new Bundle();
    localBundle.putInt("REQ_FEATURE_ID", 4015);
    localMenuEventObj.setIntentExtraKV("key_reader_sdk_extrals", localBundle);
    this.jdField_a_of_type_JavaUtilHashMap.put(Integer.valueOf(1004), localMenuEventObj);
  }
  
  public boolean isAnimating()
  {
    ReaderMenuView localReaderMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView;
    if (localReaderMenuView == null) {
      return false;
    }
    return localReaderMenuView.isAnimating();
  }
  
  public boolean isShow()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void onClick(View paramView)
  {
    if (paramView == null) {
      return;
    }
    postEvent(10005, this.jdField_a_of_type_JavaUtilHashMap.get(Integer.valueOf(paramView.getId())), null);
  }
  
  public int postEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    IReaderEvent localIReaderEvent = this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent;
    int i = -1;
    if (localIReaderEvent == null) {
      return -1;
    }
    if (localIReaderEvent.onUiEvent(paramInt, paramObject1, paramObject2)) {
      i = 0;
    }
    return i;
  }
  
  public void removeAll()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView;
    if (localObject != null)
    {
      localObject = ((ReaderMenuView)localObject).getParent();
      FrameLayout localFrameLayout = this.jdField_a_of_type_AndroidWidgetFrameLayout;
      if (localObject == localFrameLayout) {
        localFrameLayout.removeView(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView);
      }
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView.destory();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView = null;
    }
  }
  
  public final void setEventHandler(IReaderEvent paramIReaderEvent)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = paramIReaderEvent;
  }
  
  public int setMenu(int paramInt)
  {
    if ((paramInt > 0) && (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView != null))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("setMenu:");
      ((StringBuilder)localObject).append(paramInt);
      LogUtils.d("MttMenuManager", ((StringBuilder)localObject).toString());
      localObject = a(new ArrayList(Arrays.asList(new Integer[] { Integer.valueOf(paramInt) })));
      return this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView.createMenus((Map)localObject);
    }
    LogUtils.d("MttMenuManager", "invalid menu id or not init menu view ...");
    return -1;
  }
  
  public void setMenuVisiable(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_a_of_type_Boolean = paramBoolean1;
    ReaderMenuView localReaderMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView;
    if (localReaderMenuView == null) {
      return;
    }
    localReaderMenuView.setVisiable(paramBoolean1, paramBoolean2);
  }
  
  public void updateMenuText(int paramInt, String paramString)
  {
    ReaderMenuView localReaderMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView;
    if (localReaderMenuView == null) {
      return;
    }
    localReaderMenuView.updateMenu(paramInt, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttMenuManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */