package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.base.ReaderSelectView;
import com.tencent.tbs.common.resources.TBSResources;

public class MttSelectView
  extends ReaderSelectView
  implements View.OnClickListener
{
  QBSelectView a = null;
  
  public MttSelectView(Context paramContext)
  {
    super(paramContext);
  }
  
  int a()
  {
    if (this.mSupportEdit) {
      return 997;
    }
    return 998;
  }
  
  String a()
  {
    String str = TBSResources.getString("reader_search");
    if (this.mSupportEdit) {
      str = TBSResources.getString("reader_edit");
    }
    return str;
  }
  
  public int create()
  {
    LogUtils.d("MttSelectView", "create ...");
    this.a = new QBSelectView(this.mContext);
    this.a.a(TBSResources.getString("reader_copy"), a(), a());
    this.a.setClickLintener(this);
    return 0;
  }
  
  public void destroy()
  {
    removeSelectView();
    this.a = null;
  }
  
  public void notifySkinChanged()
  {
    QBSelectView localQBSelectView = this.a;
  }
  
  public void onClick(View paramView)
  {
    if (this.a != null)
    {
      if (paramView.getId() == 999)
      {
        postEvent(3008, this.a.getSelectText(), null);
        return;
      }
      if (paramView.getId() == 998)
      {
        postEvent(3012, this.a.getSelectText(), null);
        return;
      }
      if (paramView.getId() == 997) {
        postEvent(3015, null, null);
      }
    }
  }
  
  public void removeSelectView()
  {
    QBSelectView localQBSelectView = this.a;
    if (localQBSelectView != null) {
      localQBSelectView.hideSelectView(this.mParentLayout);
    }
  }
  
  public void setSelectViewHidden(boolean paramBoolean)
  {
    QBSelectView localQBSelectView = this.a;
    if (localQBSelectView != null)
    {
      if (paramBoolean)
      {
        localQBSelectView.setVisibility(8);
        return;
      }
      localQBSelectView.setVisibility(0);
    }
  }
  
  public void showSelectView(Rect paramRect, String paramString)
  {
    if (this.a != null)
    {
      LogUtils.d("MttSelectView", "showSelectView ...");
      this.a.showSelectView(this.mParentLayout, paramRect, paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttSelectView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */