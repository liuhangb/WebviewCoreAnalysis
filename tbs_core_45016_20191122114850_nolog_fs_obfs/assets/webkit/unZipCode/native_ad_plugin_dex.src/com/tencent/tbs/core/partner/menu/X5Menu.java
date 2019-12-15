package com.tencent.tbs.core.partner.menu;

import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class X5Menu
  implements Menu
{
  private Boolean mBMenuCleared = Boolean.valueOf(false);
  
  public MenuItem add(int paramInt)
  {
    return null;
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return null;
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return null;
  }
  
  public MenuItem add(CharSequence paramCharSequence)
  {
    return null;
  }
  
  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
  {
    return 0;
  }
  
  public SubMenu addSubMenu(int paramInt)
  {
    return null;
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return null;
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return null;
  }
  
  public SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    return null;
  }
  
  public void clear()
  {
    this.mBMenuCleared = Boolean.valueOf(true);
  }
  
  public void close() {}
  
  public MenuItem findItem(int paramInt)
  {
    return null;
  }
  
  public MenuItem getItem(int paramInt)
  {
    return null;
  }
  
  public boolean getMenuCleared()
  {
    return this.mBMenuCleared.booleanValue();
  }
  
  public boolean hasVisibleItems()
  {
    return false;
  }
  
  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public boolean performIdentifierAction(int paramInt1, int paramInt2)
  {
    return false;
  }
  
  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    return false;
  }
  
  public void removeGroup(int paramInt) {}
  
  public void removeItem(int paramInt) {}
  
  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void setGroupEnabled(int paramInt, boolean paramBoolean) {}
  
  public void setGroupVisible(int paramInt, boolean paramBoolean) {}
  
  public void setQwertyMode(boolean paramBoolean) {}
  
  public int size()
  {
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5Menu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */