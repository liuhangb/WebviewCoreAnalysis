package org.chromium.content.browser.selection;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressLint({"UseSparseArrays"})
public class MenuDescriptor
{
  private Map<Integer, ItemDescriptor> jdField_a_of_type_JavaUtilMap = new HashMap();
  private Set<Integer> jdField_a_of_type_JavaUtilSet = new HashSet();
  
  public void addItem(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence, Drawable paramDrawable)
  {
    this.jdField_a_of_type_JavaUtilMap.put(Integer.valueOf(paramInt2), new ItemDescriptor(paramInt1, paramInt2, paramInt3, paramCharSequence, paramDrawable));
  }
  
  public void apply(Menu paramMenu)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilSet.iterator();
    while (localIterator.hasNext()) {
      paramMenu.removeItem(((Integer)localIterator.next()).intValue());
    }
    localIterator = this.jdField_a_of_type_JavaUtilMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      ItemDescriptor localItemDescriptor = (ItemDescriptor)((Map.Entry)localIterator.next()).getValue();
      MenuItem localMenuItem = paramMenu.add(localItemDescriptor.mGroupId, localItemDescriptor.mItemId, localItemDescriptor.mOrder, localItemDescriptor.mTitle);
      if (localItemDescriptor.mIcon != null) {
        localMenuItem.setIcon(localItemDescriptor.mIcon);
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject != null)
    {
      if (!(paramObject instanceof MenuDescriptor)) {
        return false;
      }
      paramObject = (MenuDescriptor)paramObject;
      boolean bool1 = bool2;
      if (this.jdField_a_of_type_JavaUtilSet.equals(((MenuDescriptor)paramObject).jdField_a_of_type_JavaUtilSet))
      {
        bool1 = bool2;
        if (this.jdField_a_of_type_JavaUtilMap.equals(((MenuDescriptor)paramObject).jdField_a_of_type_JavaUtilMap)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public int hashCode()
  {
    return 1;
  }
  
  public void removeItem(int paramInt)
  {
    this.jdField_a_of_type_JavaUtilSet.add(Integer.valueOf(paramInt));
  }
  
  private static class ItemDescriptor
  {
    public int mGroupId;
    public Drawable mIcon;
    public int mItemId;
    public int mOrder;
    public CharSequence mTitle;
    
    public ItemDescriptor(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence, Drawable paramDrawable)
    {
      this.mItemId = paramInt2;
      this.mGroupId = paramInt1;
      this.mOrder = paramInt3;
      this.mTitle = paramCharSequence;
      this.mIcon = paramDrawable;
    }
    
    private static boolean a(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
    {
      if ((paramCharSequence1 == null) && (paramCharSequence2 == null)) {
        return true;
      }
      if ((paramCharSequence1 != null) && (paramCharSequence2 != null)) {
        return paramCharSequence1.toString().contentEquals(paramCharSequence2);
      }
      return false;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = false;
      if (paramObject != null)
      {
        if (!(paramObject instanceof ItemDescriptor)) {
          return false;
        }
        paramObject = (ItemDescriptor)paramObject;
        boolean bool1 = bool2;
        if (this.mItemId == ((ItemDescriptor)paramObject).mItemId)
        {
          bool1 = bool2;
          if (this.mGroupId == ((ItemDescriptor)paramObject).mGroupId)
          {
            bool1 = bool2;
            if (this.mOrder == ((ItemDescriptor)paramObject).mOrder)
            {
              bool1 = bool2;
              if (a(this.mTitle, ((ItemDescriptor)paramObject).mTitle)) {
                bool1 = true;
              }
            }
          }
        }
        return bool1;
      }
      return false;
    }
    
    public int hashCode()
    {
      return this.mItemId;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\MenuDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */