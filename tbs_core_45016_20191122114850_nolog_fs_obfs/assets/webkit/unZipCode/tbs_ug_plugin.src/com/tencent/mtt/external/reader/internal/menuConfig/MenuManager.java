package com.tencent.mtt.external.reader.internal.menuConfig;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.mtt.external.reader.base.IReaderEvent;
import com.tencent.mtt.external.reader.base.ReaderConfig;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class MenuManager
  implements View.OnClickListener
{
  private static String jdField_a_of_type_JavaLangString = "www_MenuManager";
  private Context jdField_a_of_type_AndroidContentContext = null;
  private FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  private IReaderEvent jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = null;
  ReaderConfig jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig = null;
  private MenuButton jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton = null;
  MenuConfig.ConfigCallback jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$ConfigCallback = null;
  private MenuConfig jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig = null;
  private MenuView jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView = null;
  private boolean jdField_a_of_type_Boolean = true;
  private String b = null;
  private String c = "";
  
  public MenuManager(Context paramContext, FrameLayout paramFrameLayout, String paramString1, String paramString2)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidWidgetFrameLayout = paramFrameLayout;
    this.b = paramString1.toLowerCase();
    this.c = paramString2;
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
      paramView = jdField_a_of_type_JavaLangString;
      return;
    }
  }
  
  void a()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView = new MenuView(this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig, this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig, this.b, this);
    Object localObject1 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView;
    if (localObject1 == null) {
      return;
    }
    a((View)localObject1);
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton == null)
    {
      localObject1 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig;
      if (localObject1 != null)
      {
        Object localObject2 = null;
        Object localObject3 = ((MenuConfig)localObject1).getMenuType(MenuConstants.TYPE_SNAPSHOT);
        localObject1 = localObject2;
        if (localObject3 != null)
        {
          localObject3 = ((MenuType)localObject3).getMenuButtons(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig);
          localObject1 = localObject2;
          if (localObject3 != null)
          {
            localObject1 = localObject2;
            if (!((TreeMap)localObject3).isEmpty())
            {
              localObject3 = ((TreeMap)localObject3).entrySet().iterator();
              localObject1 = localObject2;
              if (((Iterator)localObject3).hasNext()) {
                localObject1 = (MenuButton)((Map.Entry)((Iterator)localObject3).next()).getValue();
              }
            }
          }
        }
        if ((localObject1 != null) && (((MenuButton)localObject1).getShouldShow(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.installed_qb_version))) {
          this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton = ((MenuButton)localObject1);
        }
      }
    }
  }
  
  public void destory()
  {
    removeAll();
    this.jdField_a_of_type_AndroidWidgetFrameLayout = null;
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public void focus()
  {
    MenuView localMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView;
    if (localMenuView == null) {
      return;
    }
    localMenuView.bringToFront();
  }
  
  public MenuEventObj getSnapshotEventObj()
  {
    MenuButton localMenuButton = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton;
    if (localMenuButton != null) {
      return localMenuButton.getEventObj();
    }
    return null;
  }
  
  public String getSnapshotStatistics(String paramString)
  {
    MenuButton localMenuButton = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton;
    if (localMenuButton != null) {
      return localMenuButton.getStatistics(paramString);
    }
    return null;
  }
  
  public int initMenu(ReaderConfig paramReaderConfig)
  {
    removeAll();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig = new MenuConfig();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig = paramReaderConfig;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$ConfigCallback = new MenuConfig.ConfigCallback()
    {
      public void onCallback(int paramAnonymousInt)
      {
        if (paramAnonymousInt == 0) {
          MenuManager.this.a();
        }
      }
    };
    if (!this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.initConfig(this.c, this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig$ConfigCallback)) {
      return -1;
    }
    paramReaderConfig = jdField_a_of_type_JavaLangString;
    return 0;
  }
  
  public boolean isAnimating()
  {
    MenuView localMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView;
    if (localMenuView == null) {
      return false;
    }
    return localMenuView.isAnimating();
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
    if ((paramView instanceof TextView))
    {
      paramView = ((MenuButtonView)paramView.getParent()).getEventObj();
      if (paramView == null) {
        return;
      }
      postEvent(10005, paramView, null);
    }
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
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig;
    if (localObject != null) {
      ((MenuConfig)localObject).destory();
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView;
    if (localObject != null)
    {
      localObject = ((MenuView)localObject).getParent();
      FrameLayout localFrameLayout = this.jdField_a_of_type_AndroidWidgetFrameLayout;
      if (localObject == localFrameLayout) {
        localFrameLayout.removeView(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView);
      }
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView.destory();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView = null;
    }
  }
  
  public void setEventHandler(IReaderEvent paramIReaderEvent)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = paramIReaderEvent;
  }
  
  public void setMenuVisiable(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_a_of_type_Boolean = paramBoolean1;
    MenuView localMenuView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuView;
    if (localMenuView == null) {
      return;
    }
    localMenuView.setVisiable(paramBoolean1, paramBoolean2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */