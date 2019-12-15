package com.tencent.mtt.external.reader.internal.menuConfig;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.util.HashMap;

public class MenuButtonView
  extends LinearLayout
  implements MenuEventObj.MenuActionListener
{
  private static String jdField_a_of_type_JavaLangString = "www_MenuButtonView";
  private View jdField_a_of_type_AndroidViewView = null;
  private MenuButton jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton = null;
  private MenuEventObj jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj = null;
  
  public MenuButtonView(Context paramContext, MenuButton paramMenuButton)
  {
    super(paramContext);
    a(paramMenuButton);
  }
  
  private int a(MenuButton paramMenuButton)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton = paramMenuButton;
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton == null) {
      return -1;
    }
    this.jdField_a_of_type_AndroidViewView = a();
    if (this.jdField_a_of_type_AndroidViewView == null) {
      return -1;
    }
    paramMenuButton = new LinearLayout.LayoutParams(-2, -2);
    addView(this.jdField_a_of_type_AndroidViewView, paramMenuButton);
    paramMenuButton = jdField_a_of_type_JavaLangString;
    return 0;
  }
  
  private View a()
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton;
    if (localObject1 == null) {
      return null;
    }
    int i = ((MenuButton)localObject1).getIndex();
    int j = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getTextSize();
    int k = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getTextColor();
    String str = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getText();
    Object localObject2 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getDrawable();
    HashMap localHashMap = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getPadding();
    if ((i != MenuConstants.ERR) && (j != MenuConstants.ERR) && (k != MenuConstants.ERR) && (str != null) && (localObject2 != null) && (localHashMap != null))
    {
      if (localHashMap.isEmpty()) {
        return null;
      }
      localObject1 = new TextView(getContext());
      ((TextView)localObject1).setGravity(16);
      ((TextView)localObject1).setTextSize(0, j);
      ((TextView)localObject1).setTextColor(k);
      ((TextView)localObject1).setText(str);
      ((TextView)localObject1).setBackgroundDrawable((Drawable)localObject2);
      ((TextView)localObject1).setPadding(((Integer)localHashMap.get(MenuConstants.MENU_POS_LEFT)).intValue(), ((Integer)localHashMap.get(MenuConstants.MENU_POS_TOP)).intValue(), ((Integer)localHashMap.get(MenuConstants.MENU_POS_RIGHT)).intValue(), ((Integer)localHashMap.get(MenuConstants.MENU_POS_BOTTOM)).intValue());
      str = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getStatistics(MenuConstants.MENU_STATISTICS_SHOW_BTN);
      localObject2 = jdField_a_of_type_JavaLangString;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Do statistics info, show menu:");
      ((StringBuilder)localObject2).append(str);
      ((StringBuilder)localObject2).toString();
      if (!TextUtils.isEmpty(str)) {
        TBSStatManager.getInstance().userBehaviorStatistics(str);
      }
      str = jdField_a_of_type_JavaLangString;
      return (View)localObject1;
    }
    return null;
  }
  
  public void destory()
  {
    removeAllViews();
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton = null;
    this.jdField_a_of_type_AndroidViewView = null;
  }
  
  public MenuEventObj getEventObj()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getEventObj();
      MenuEventObj localMenuEventObj = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj;
      if (localMenuEventObj != null) {
        localMenuEventObj.setActionListener(this);
      }
    }
    return this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuEventObj;
  }
  
  public void onLaunchQB()
  {
    String str = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getStatistics(MenuConstants.MENU_STATISTICS_INTO_BROWSER);
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Do statistics info, launch QB:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(str)) {
      TBSStatManager.getInstance().userBehaviorStatistics(str);
    }
  }
  
  public void onMenuEventExecurte()
  {
    String str = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getStatistics(MenuConstants.MENU_STATISTICS_CLICK_BTN);
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Do statistics info, On click:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(str)) {
      TBSStatManager.getInstance().userBehaviorStatistics(str);
    }
  }
  
  public void onRequestInstallQB()
  {
    String str = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuButton.getStatistics(MenuConstants.MENU_STATISTICS_INTO_DIALOG);
    Object localObject = jdField_a_of_type_JavaLangString;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Do statistics info, show dialog:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    if (!TextUtils.isEmpty(str)) {
      TBSStatManager.getInstance().userBehaviorStatistics(str);
    }
  }
  
  public int setClickListener(View.OnClickListener paramOnClickListener)
  {
    View localView = this.jdField_a_of_type_AndroidViewView;
    if (localView == null) {
      return -1;
    }
    localView.setOnClickListener(paramOnClickListener);
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuButtonView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */