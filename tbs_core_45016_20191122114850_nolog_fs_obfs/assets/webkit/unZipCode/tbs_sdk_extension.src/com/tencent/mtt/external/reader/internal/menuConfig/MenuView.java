package com.tencent.mtt.external.reader.internal.menuConfig;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.tencent.mtt.external.reader.base.ReaderConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class MenuView
  extends FrameLayout
{
  private static String jdField_a_of_type_JavaLangString = "www_MenuView";
  Context jdField_a_of_type_AndroidContentContext = null;
  View.OnClickListener jdField_a_of_type_AndroidViewView$OnClickListener = null;
  LinearLayout jdField_a_of_type_AndroidWidgetLinearLayout = null;
  private ReaderConfig jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig = null;
  private MenuConfig jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig = null;
  private MenuType jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType = null;
  boolean jdField_a_of_type_Boolean = false;
  private MenuType jdField_b_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType = null;
  private String jdField_b_of_type_JavaLangString = null;
  boolean jdField_b_of_type_Boolean = true;
  
  public MenuView(Context paramContext, MenuConfig paramMenuConfig, ReaderConfig paramReaderConfig, String paramString, View.OnClickListener paramOnClickListener)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_b_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_AndroidViewView$OnClickListener = paramOnClickListener;
    a(paramMenuConfig, paramReaderConfig);
  }
  
  private int a(MenuConfig paramMenuConfig, ReaderConfig paramReaderConfig)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig = paramMenuConfig;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig = paramReaderConfig;
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig == null) {
      return -1;
    }
    this.jdField_a_of_type_AndroidWidgetLinearLayout = a();
    if (this.jdField_a_of_type_AndroidWidgetLinearLayout == null) {
      return -1;
    }
    paramMenuConfig = new RelativeLayout(this.jdField_a_of_type_AndroidContentContext);
    addView(paramMenuConfig, new FrameLayout.LayoutParams(-1, -1));
    paramReaderConfig = new RelativeLayout.LayoutParams(-2, -2);
    HashMap localHashMap = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getMargin();
    if (localHashMap != null)
    {
      if (localHashMap.isEmpty()) {
        return -1;
      }
      paramReaderConfig.addRule(11);
      paramReaderConfig.addRule(12);
      paramReaderConfig.leftMargin = ((Integer)localHashMap.get(MenuConstants.MENU_POS_LEFT)).intValue();
      paramReaderConfig.topMargin = ((Integer)localHashMap.get(MenuConstants.MENU_POS_TOP)).intValue();
      paramReaderConfig.rightMargin = ((Integer)localHashMap.get(MenuConstants.MENU_POS_RIGHT)).intValue();
      paramReaderConfig.bottomMargin = ((Integer)localHashMap.get(MenuConstants.MENU_POS_BOTTOM)).intValue();
      paramMenuConfig.addView(this.jdField_a_of_type_AndroidWidgetLinearLayout, paramReaderConfig);
      paramMenuConfig = jdField_a_of_type_JavaLangString;
      return 0;
    }
    return -1;
  }
  
  private LinearLayout a()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getMenuType(this.jdField_b_of_type_JavaLangString);
    this.jdField_b_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getMenuType(MenuConstants.TYPE_DEFAULT);
    if ((this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType == null) && (this.jdField_b_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType == null)) {
      return null;
    }
    Object localObject1 = new ArrayList();
    Object localObject2 = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType;
    if ((localObject2 != null) && (((MenuType)localObject2).getShouldShow())) {
      ((ArrayList)localObject1).add(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType);
    }
    localObject2 = this.jdField_b_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType;
    if ((localObject2 != null) && (((MenuType)localObject2).getShouldShow())) {
      ((ArrayList)localObject1).add(this.jdField_b_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType);
    }
    localObject2 = a((ArrayList)localObject1);
    localObject1 = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
    int i = 0;
    ((LinearLayout)localObject1).setOrientation(0);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    if ((localObject2 != null) && (!((ArrayList)localObject2).isEmpty()))
    {
      int j = ((ArrayList)localObject2).size();
      while (i < j)
      {
        Object localObject3 = (MenuButton)((ArrayList)localObject2).get(i);
        if (j == 1) {
          ((MenuButton)localObject3).setDrawable(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getDrawable(MenuConstants.MENU_BACKGROUND_SINGLE));
        } else if (j >= 2) {
          if (i == 0) {
            ((MenuButton)localObject3).setDrawable(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getDrawable(MenuConstants.MENU_BACKGROUND_LEFT));
          } else if (i == j - 1) {
            ((MenuButton)localObject3).setDrawable(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getDrawable(MenuConstants.MENU_BACKGROUND_RIGHT));
          } else {
            ((MenuButton)localObject3).setDrawable(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig.getDrawable(MenuConstants.MENU_BACKGROUND_MIDDLE));
          }
        }
        localObject3 = new MenuButtonView(this.jdField_a_of_type_AndroidContentContext, (MenuButton)localObject3);
        ((MenuButtonView)localObject3).setClickListener(this.jdField_a_of_type_AndroidViewView$OnClickListener);
        ((LinearLayout)localObject1).addView((View)localObject3, localLayoutParams);
        i += 1;
      }
    }
    localObject2 = jdField_a_of_type_JavaLangString;
    return (LinearLayout)localObject1;
  }
  
  private ArrayList<MenuButton> a(ArrayList<MenuType> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
    {
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        Object localObject = (MenuType)paramArrayList.next();
        if (localObject != null)
        {
          localObject = ((MenuType)localObject).getMenuButtons(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig);
          if ((localObject != null) && (!((TreeMap)localObject).isEmpty()))
          {
            localObject = ((TreeMap)localObject).entrySet().iterator();
            while (((Iterator)localObject).hasNext()) {
              localArrayList.add((MenuButton)((Map.Entry)((Iterator)localObject).next()).getValue());
            }
          }
        }
      }
      return localArrayList;
    }
    return null;
  }
  
  public void destory()
  {
    removeAllViews();
    LinearLayout localLinearLayout = this.jdField_a_of_type_AndroidWidgetLinearLayout;
    if (localLinearLayout != null)
    {
      localLinearLayout.removeAllViews();
      this.jdField_a_of_type_AndroidWidgetLinearLayout = null;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuConfig = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType = null;
    this.jdField_b_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuType = null;
  }
  
  public boolean isAnimating()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public boolean isShow()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public void setVisiable(final boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_b_of_type_Boolean = paramBoolean1;
    if (!paramBoolean2)
    {
      if (paramBoolean1)
      {
        setVisibility(0);
        return;
      }
      setVisibility(4);
      return;
    }
    float f2 = 0.0F;
    float f1 = 1.0F;
    if (paramBoolean1)
    {
      getWidth();
    }
    else
    {
      getWidth();
      f2 = 1.0F;
      f1 = 0.0F;
    }
    this.jdField_a_of_type_Boolean = true;
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(f2, f1);
    localAlphaAnimation.setDuration(500L);
    localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        if (paramBoolean1) {
          MenuView.this.setVisibility(0);
        } else {
          MenuView.this.setVisibility(4);
        }
        MenuView.this.jdField_a_of_type_Boolean = false;
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    startAnimation(localAlphaAnimation);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */