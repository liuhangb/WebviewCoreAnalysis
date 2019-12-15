package com.tencent.tbs.core.partner.menu;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class X5ActionMode
  extends ActionMode
{
  private Context mContext;
  private X5Menu mX5Menu = null;
  private X5MenuInflater mX5MenuInflater = null;
  
  public void finish() {}
  
  public View getCustomView()
  {
    return null;
  }
  
  public Menu getMenu()
  {
    if (this.mX5Menu == null) {
      this.mX5Menu = new X5Menu();
    }
    return this.mX5Menu;
  }
  
  public MenuInflater getMenuInflater()
  {
    if (this.mX5MenuInflater == null) {
      this.mX5MenuInflater = new X5MenuInflater(this.mContext);
    }
    return this.mX5MenuInflater;
  }
  
  public CharSequence getSubtitle()
  {
    return null;
  }
  
  public CharSequence getTitle()
  {
    return null;
  }
  
  public X5Menu getX5Menu()
  {
    if (this.mX5Menu == null) {
      this.mX5Menu = new X5Menu();
    }
    return this.mX5Menu;
  }
  
  public X5MenuInflater getX5MenuInflater()
  {
    if (this.mX5MenuInflater == null) {
      this.mX5MenuInflater = new X5MenuInflater(this.mContext);
    }
    return this.mX5MenuInflater;
  }
  
  public void invalidate() {}
  
  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void setCustomView(View paramView) {}
  
  public void setSubtitle(int paramInt) {}
  
  public void setSubtitle(CharSequence paramCharSequence) {}
  
  public void setTitle(int paramInt) {}
  
  public void setTitle(CharSequence paramCharSequence) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5ActionMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */