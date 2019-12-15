package org.chromium.content_public.browser;

import java.util.ArrayList;

public class NavigationHistory
{
  private int jdField_a_of_type_Int;
  private final ArrayList<NavigationEntry> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  
  public void addEntry(NavigationEntry paramNavigationEntry)
  {
    this.jdField_a_of_type_JavaUtilArrayList.add(paramNavigationEntry);
  }
  
  public int getCurrentEntryIndex()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public NavigationEntry getEntryAtIndex(int paramInt)
  {
    return (NavigationEntry)this.jdField_a_of_type_JavaUtilArrayList.get(paramInt);
  }
  
  public int getEntryCount()
  {
    return this.jdField_a_of_type_JavaUtilArrayList.size();
  }
  
  public void setCurrentEntryIndex(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\NavigationHistory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */