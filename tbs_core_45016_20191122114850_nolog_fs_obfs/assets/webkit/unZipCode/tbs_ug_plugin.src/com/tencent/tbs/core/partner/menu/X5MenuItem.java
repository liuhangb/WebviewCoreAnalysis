package com.tencent.tbs.core.partner.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public class X5MenuItem
  implements MenuItem
{
  private int itemId;
  private String mOnClick;
  private String mTitle;
  
  public boolean collapseActionView()
  {
    return false;
  }
  
  public boolean expandActionView()
  {
    return false;
  }
  
  public ActionProvider getActionProvider()
  {
    return null;
  }
  
  public View getActionView()
  {
    return null;
  }
  
  public char getAlphabeticShortcut()
  {
    return '\000';
  }
  
  public int getGroupId()
  {
    return 0;
  }
  
  public Drawable getIcon()
  {
    return null;
  }
  
  public Intent getIntent()
  {
    return null;
  }
  
  public int getItemId()
  {
    return this.itemId;
  }
  
  public ContextMenu.ContextMenuInfo getMenuInfo()
  {
    return null;
  }
  
  public char getNumericShortcut()
  {
    return '\000';
  }
  
  public String getOnClick()
  {
    return this.mOnClick;
  }
  
  public int getOrder()
  {
    return 0;
  }
  
  public SubMenu getSubMenu()
  {
    return null;
  }
  
  public String getTitle()
  {
    return this.mTitle;
  }
  
  public CharSequence getTitleCondensed()
  {
    return null;
  }
  
  public boolean hasSubMenu()
  {
    return false;
  }
  
  public boolean isActionViewExpanded()
  {
    return false;
  }
  
  public boolean isCheckable()
  {
    return false;
  }
  
  public boolean isChecked()
  {
    return false;
  }
  
  public boolean isEnabled()
  {
    return false;
  }
  
  public boolean isVisible()
  {
    return false;
  }
  
  public MenuItem setActionProvider(ActionProvider paramActionProvider)
  {
    return null;
  }
  
  public MenuItem setActionView(int paramInt)
  {
    return null;
  }
  
  public MenuItem setActionView(View paramView)
  {
    return null;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar)
  {
    return null;
  }
  
  public MenuItem setCheckable(boolean paramBoolean)
  {
    return null;
  }
  
  public MenuItem setChecked(boolean paramBoolean)
  {
    return null;
  }
  
  public MenuItem setEnabled(boolean paramBoolean)
  {
    return null;
  }
  
  public MenuItem setIcon(int paramInt)
  {
    return null;
  }
  
  public MenuItem setIcon(Drawable paramDrawable)
  {
    return null;
  }
  
  public MenuItem setIntent(Intent paramIntent)
  {
    return null;
  }
  
  public void setItemID(int paramInt)
  {
    this.itemId = paramInt;
  }
  
  public MenuItem setNumericShortcut(char paramChar)
  {
    return null;
  }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener)
  {
    return null;
  }
  
  public void setOnClick(String paramString)
  {
    this.mOnClick = paramString;
  }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    return null;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2)
  {
    return null;
  }
  
  public void setShowAsAction(int paramInt) {}
  
  public MenuItem setShowAsActionFlags(int paramInt)
  {
    return null;
  }
  
  public MenuItem setTitle(int paramInt)
  {
    return null;
  }
  
  public MenuItem setTitle(CharSequence paramCharSequence)
  {
    return null;
  }
  
  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence)
  {
    return null;
  }
  
  public MenuItem setVisible(boolean paramBoolean)
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5MenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */