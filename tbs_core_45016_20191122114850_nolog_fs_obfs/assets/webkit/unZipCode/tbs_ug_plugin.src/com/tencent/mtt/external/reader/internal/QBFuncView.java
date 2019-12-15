package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.tbs.common.resources.TBSResources;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class QBFuncView
  extends LinearLayout
{
  private Map<Integer, View> a = null;
  
  public QBFuncView(Context paramContext)
  {
    super(paramContext);
  }
  
  private StateListDrawable a(Context paramContext, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    paramContext = new StateListDrawable();
    paramContext.addState(new int[] { 16842910, 16842908 }, paramDrawable3);
    paramContext.addState(new int[] { 16842919, 16842910 }, paramDrawable2);
    paramContext.addState(new int[] { 16842908 }, paramDrawable3);
    paramContext.addState(new int[] { 16842919 }, paramDrawable2);
    paramContext.addState(new int[] { 16842910 }, paramDrawable1);
    paramContext.addState(new int[0], paramDrawable1);
    return paramContext;
  }
  
  private View a(int paramInt, String paramString)
  {
    TextView localTextView = new TextView(getContext());
    localTextView.setId(paramInt);
    localTextView.setGravity(16);
    localTextView.setTextSize(0, TBSResources.getDimensionPixelSize("reader_fontsize_t3"));
    localTextView.setTextColor(TBSResources.getColor("reader_select_text_color"));
    localTextView.setText(paramString);
    if (paramInt == 1000) {
      paramString = a(getContext(), TBSResources.getDrawable("reader_menu_btn_outline_normal"), TBSResources.getDrawable("reader_menu_btn_outline_pressed"), TBSResources.getDrawable("reader_menu_btn_outline_normal"));
    } else if (paramInt == 1001) {
      paramString = a(getContext(), TBSResources.getDrawable("reader_menu_btn_view_normal"), TBSResources.getDrawable("reader_menu_btn_view_pressed"), TBSResources.getDrawable("reader_menu_btn_view_normal"));
    } else if (paramInt == 1002) {
      paramString = a(getContext(), TBSResources.getDrawable("reader_menu_btn_play_normal"), TBSResources.getDrawable("reader_menu_btn_play_pressed"), TBSResources.getDrawable("reader_menu_btn_play_normal"));
    } else if (paramInt == 1003) {
      paramString = a(getContext(), TBSResources.getDrawable("reader_menu_btn_search_normal"), TBSResources.getDrawable("reader_menu_btn_search_pressed"), TBSResources.getDrawable("reader_menu_btn_search_normal"));
    } else {
      paramString = null;
    }
    if (paramString != null) {
      localTextView.setBackgroundDrawable(paramString);
    }
    localTextView.setPadding(TBSResources.getDimensionPixelSize("reader_menu_btn_padding_left"), 0, TBSResources.getDimensionPixelSize("reader_menu_btn_padding_right"), 0);
    return localTextView;
  }
  
  public void destory()
  {
    removeAllViews();
    this.a = null;
  }
  
  public void setClickLintener(View.OnClickListener paramOnClickListener)
  {
    Object localObject = this.a;
    if (localObject == null) {
      return;
    }
    localObject = ((Map)localObject).values().iterator();
    while (((Iterator)localObject).hasNext()) {
      ((View)((Iterator)localObject).next()).setOnClickListener(paramOnClickListener);
    }
  }
  
  public int setMenu(Map<Integer, String> paramMap)
  {
    if (paramMap.isEmpty()) {
      return -1;
    }
    Object localObject = this.a;
    if ((localObject != null) && (!((Map)localObject).isEmpty()))
    {
      removeAllViews();
      this.a.clear();
    }
    if (this.a == null) {
      this.a = new HashMap();
    }
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      localObject = (Map.Entry)paramMap.next();
      int i = ((Integer)((Map.Entry)localObject).getKey()).intValue();
      localObject = a(i, (String)((Map.Entry)localObject).getValue());
      addView((View)localObject, new LinearLayout.LayoutParams(-1, -1));
      this.a.put(Integer.valueOf(i), localObject);
    }
    return 0;
  }
  
  public int updateMenu(int paramInt, String paramString)
  {
    if (!this.a.containsKey(Integer.valueOf(paramInt))) {
      return -1;
    }
    ((TextView)this.a.get(Integer.valueOf(paramInt))).setText(paramString);
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\QBFuncView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */