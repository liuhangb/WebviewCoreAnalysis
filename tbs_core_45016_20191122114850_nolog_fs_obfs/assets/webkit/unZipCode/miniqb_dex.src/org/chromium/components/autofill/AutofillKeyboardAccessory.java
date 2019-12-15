package org.chromium.components.autofill;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import org.chromium.ui.UiUtils;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.base.WindowAndroid.KeyboardVisibilityListener;

public class AutofillKeyboardAccessory
  extends LinearLayout
  implements View.OnClickListener, View.OnLongClickListener, WindowAndroid.KeyboardVisibilityListener
{
  private final int jdField_a_of_type_Int;
  private Animator jdField_a_of_type_AndroidAnimationAnimator;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private final AutofillDelegate jdField_a_of_type_OrgChromiumComponentsAutofillAutofillDelegate;
  private final WindowAndroid jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
  private int jdField_b_of_type_Int;
  private boolean jdField_b_of_type_Boolean;
  
  private void a(final View paramView)
  {
    if ((!jdField_a_of_type_Boolean) && (getChildCount() <= 1)) {
      throw new AssertionError();
    }
    final Object localObject = getChildAt(0);
    if ((localObject instanceof ImageView))
    {
      if (((View)localObject).getContentDescription() != null) {
        return;
      }
      if ((!jdField_a_of_type_Boolean) && (!(getChildAt(0) instanceof ImageView))) {
        throw new AssertionError();
      }
      int i = this.jdField_b_of_type_Int + 1;
      while (i < getChildCount())
      {
        if ((!jdField_a_of_type_Boolean) && (!(getChildAt(i) instanceof ImageView))) {
          throw new AssertionError();
        }
        i += 1;
      }
      i = ((View)localObject).getWidth();
      final ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, new float[] { -i });
      localObjectAnimator.setDuration(this.jdField_a_of_type_Int);
      localObjectAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          localObjectAnimator.removeListener(this);
          if (paramView.getVisibility() != 0) {
            return;
          }
          AutofillKeyboardAccessory.this.removeView(localObject);
          AutofillKeyboardAccessory.c(AutofillKeyboardAccessory.this);
          AutofillKeyboardAccessory.this.setTranslationX(0.0F);
        }
      });
      localObject = new AnimatorSet();
      ArrayList localArrayList = new ArrayList();
      i = this.jdField_b_of_type_Int + 1;
      while (i < getChildCount())
      {
        localArrayList.add(ObjectAnimator.ofFloat(getChildAt(i), View.ALPHA, new float[] { 1.0F }));
        i += 1;
      }
      ((AnimatorSet)localObject).setDuration(250L);
      ((AnimatorSet)localObject).playTogether(localArrayList);
      this.jdField_a_of_type_AndroidAnimationAnimator = new AnimatorSet();
      ((AnimatorSet)this.jdField_a_of_type_AndroidAnimationAnimator).playSequentially(new Animator[] { localObjectAnimator, localObject });
      this.jdField_a_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          if (paramView.getVisibility() == 0) {
            AutofillKeyboardAccessory.a(AutofillKeyboardAccessory.this).start();
          }
        }
      };
      paramView.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 1000L);
      return;
    }
  }
  
  public void a()
  {
    ViewGroup localViewGroup = this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid.getKeyboardAccessoryView();
    localViewGroup.removeView(this);
    localViewGroup.setVisibility(8);
    this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid.removeKeyboardVisibilityListener(this);
    ((View)localViewGroup.getParent()).requestLayout();
    this.jdField_b_of_type_Boolean = true;
  }
  
  public void keyboardVisibilityChanged(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      a();
      this.jdField_a_of_type_OrgChromiumComponentsAutofillAutofillDelegate.dismissed();
    }
  }
  
  public void onClick(View paramView)
  {
    UiUtils.a(this);
    this.jdField_a_of_type_OrgChromiumComponentsAutofillAutofillDelegate.suggestionSelected(((Integer)paramView.getTag()).intValue());
  }
  
  public boolean onLongClick(View paramView)
  {
    this.jdField_a_of_type_OrgChromiumComponentsAutofillAutofillDelegate.deleteSuggestion(((Integer)paramView.getTag()).intValue());
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\autofill\AutofillKeyboardAccessory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */