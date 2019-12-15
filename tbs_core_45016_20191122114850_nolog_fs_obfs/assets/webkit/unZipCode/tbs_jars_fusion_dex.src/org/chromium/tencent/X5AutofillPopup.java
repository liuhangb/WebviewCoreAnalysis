package org.chromium.tencent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.chromium.components.autofill.AutofillDelegate;
import org.chromium.components.autofill.AutofillSuggestion;

public class X5AutofillPopup
  extends X5DropdownPopupWindow
  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, PopupWindow.OnDismissListener
{
  private static final int ITEM_ID_SEPARATOR_ENTRY = -3;
  private final AutofillDelegate mAutofillDelegate;
  private final Context mContext;
  private boolean mInNightMode;
  private List<AutofillSuggestion> mSuggestions;
  
  public X5AutofillPopup(Context paramContext, View paramView, AutofillDelegate paramAutofillDelegate)
  {
    this(paramContext, paramView, paramAutofillDelegate, false);
  }
  
  public X5AutofillPopup(Context paramContext, View paramView, AutofillDelegate paramAutofillDelegate, boolean paramBoolean)
  {
    super(paramContext, paramView, getStyleId(paramBoolean));
    this.mContext = paramContext;
    this.mAutofillDelegate = paramAutofillDelegate;
    this.mInNightMode = paramBoolean;
    setOnItemClickListener(this);
    setOnDismissListener(this);
    setContentDescriptionForAccessibility(this.mContext.getString(SharedResource.loadIdentifierResource("x5_autofill_popup_content_description", "string")));
  }
  
  private static int getStyleId(boolean paramBoolean)
  {
    if (paramBoolean) {
      return SharedResource.loadIdentifierResource("x5_DropdownPopupWindow_night", "style");
    }
    return SharedResource.loadIdentifierResource("x5_DropdownPopupWindow", "style");
  }
  
  public void dismiss()
  {
    try
    {
      super.dismiss();
      return;
    }
    catch (Exception localException) {}
  }
  
  @SuppressLint({"InlinedApi"})
  public void filterAndShow(AutofillSuggestion[] paramArrayOfAutofillSuggestion, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mSuggestions = new ArrayList(Arrays.asList(paramArrayOfAutofillSuggestion));
    ArrayList localArrayList = new ArrayList();
    HashSet localHashSet = new HashSet();
    int i = 0;
    while (i < paramArrayOfAutofillSuggestion.length)
    {
      if (paramArrayOfAutofillSuggestion[i].a() == -3) {
        localHashSet.add(Integer.valueOf(localArrayList.size()));
      } else {
        localArrayList.add(paramArrayOfAutofillSuggestion[i]);
      }
      i += 1;
    }
    Context localContext = this.mContext;
    if (paramInt1 == 0) {
      paramArrayOfAutofillSuggestion = null;
    } else {
      paramArrayOfAutofillSuggestion = Integer.valueOf(paramInt1);
    }
    Integer localInteger1;
    if (paramInt2 == 0) {
      localInteger1 = null;
    } else {
      localInteger1 = Integer.valueOf(paramInt2);
    }
    Integer localInteger2;
    if (paramInt3 == 0) {
      localInteger2 = null;
    } else {
      localInteger2 = Integer.valueOf(paramInt3);
    }
    Integer localInteger3;
    if (paramInt4 == 0) {
      localInteger3 = null;
    } else {
      localInteger3 = Integer.valueOf(paramInt4);
    }
    setAdapter(new X5DropdownAdapter(localContext, localArrayList, localHashSet, paramArrayOfAutofillSuggestion, localInteger1, localInteger2, localInteger3, this.mInNightMode));
    setRtl(paramBoolean);
    show();
    getListView().setOnItemLongClickListener(this);
  }
  
  public boolean isNightMode()
  {
    return this.mInNightMode;
  }
  
  public void onDismiss()
  {
    this.mAutofillDelegate.dismissed();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = (X5DropdownAdapter)paramAdapterView.getAdapter();
    paramInt = this.mSuggestions.indexOf(paramAdapterView.getItem(paramInt));
    this.mAutofillDelegate.suggestionSelected(paramInt);
  }
  
  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = (AutofillSuggestion)((X5DropdownAdapter)paramAdapterView.getAdapter()).getItem(paramInt);
    if (!paramAdapterView.a()) {
      return false;
    }
    paramInt = this.mSuggestions.indexOf(paramAdapterView);
    this.mAutofillDelegate.deleteSuggestion(paramInt);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\X5AutofillPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */