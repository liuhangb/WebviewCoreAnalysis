package org.chromium.content.browser;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.util.ArrayList;

public class ViewPositionObserver
  implements PositionObserver
{
  private View jdField_a_of_type_AndroidViewView;
  private ViewTreeObserver.OnPreDrawListener jdField_a_of_type_AndroidViewViewTreeObserver$OnPreDrawListener;
  private final ArrayList<PositionObserver.Listener> jdField_a_of_type_JavaUtilArrayList;
  private final int[] jdField_a_of_type_ArrayOfInt = new int[2];
  
  public ViewPositionObserver(View paramView)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    b();
    this.jdField_a_of_type_AndroidViewViewTreeObserver$OnPreDrawListener = new ViewTreeObserver.OnPreDrawListener()
    {
      public boolean onPreDraw()
      {
        ViewPositionObserver.a(ViewPositionObserver.this);
        return true;
      }
    };
  }
  
  private void a()
  {
    int i = 0;
    while (i < this.jdField_a_of_type_JavaUtilArrayList.size())
    {
      PositionObserver.Listener localListener = (PositionObserver.Listener)this.jdField_a_of_type_JavaUtilArrayList.get(i);
      int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
      localListener.onPositionChanged(arrayOfInt[0], arrayOfInt[1]);
      i += 1;
    }
  }
  
  private void b()
  {
    int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
    int i = arrayOfInt[0];
    int j = arrayOfInt[1];
    this.jdField_a_of_type_AndroidViewView.getLocationInWindow(arrayOfInt);
    arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
    if ((arrayOfInt[0] != i) || (arrayOfInt[1] != j)) {
      a();
    }
  }
  
  public void addListener(PositionObserver.Listener paramListener)
  {
    if (this.jdField_a_of_type_JavaUtilArrayList.contains(paramListener)) {
      return;
    }
    if (this.jdField_a_of_type_JavaUtilArrayList.isEmpty())
    {
      this.jdField_a_of_type_AndroidViewView.getViewTreeObserver().addOnPreDrawListener(this.jdField_a_of_type_AndroidViewViewTreeObserver$OnPreDrawListener);
      b();
    }
    this.jdField_a_of_type_JavaUtilArrayList.add(paramListener);
  }
  
  public void clearListener()
  {
    this.jdField_a_of_type_JavaUtilArrayList.clear();
  }
  
  public int getPositionX()
  {
    b();
    return this.jdField_a_of_type_ArrayOfInt[0];
  }
  
  public int getPositionY()
  {
    b();
    return this.jdField_a_of_type_ArrayOfInt[1];
  }
  
  public void removeListener(PositionObserver.Listener paramListener)
  {
    if (!this.jdField_a_of_type_JavaUtilArrayList.contains(paramListener)) {
      return;
    }
    this.jdField_a_of_type_JavaUtilArrayList.remove(paramListener);
    if (this.jdField_a_of_type_JavaUtilArrayList.isEmpty()) {
      this.jdField_a_of_type_AndroidViewView.getViewTreeObserver().removeOnPreDrawListener(this.jdField_a_of_type_AndroidViewViewTreeObserver$OnPreDrawListener);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ViewPositionObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */