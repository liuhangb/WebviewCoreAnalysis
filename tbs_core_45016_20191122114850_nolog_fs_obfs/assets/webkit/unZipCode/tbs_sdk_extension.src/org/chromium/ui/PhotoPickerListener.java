package org.chromium.ui;

public abstract interface PhotoPickerListener
{
  public abstract void onPickerUserAction(Action paramAction, String[] paramArrayOfString);
  
  public static enum Action
  {
    static
    {
      jdField_a_of_type_OrgChromiumUiPhotoPickerListener$Action = new Action("CANCEL", 0);
      b = new Action("PHOTOS_SELECTED", 1);
      c = new Action("LAUNCH_CAMERA", 2);
      d = new Action("LAUNCH_GALLERY", 3);
      jdField_a_of_type_ArrayOfOrgChromiumUiPhotoPickerListener$Action = new Action[] { jdField_a_of_type_OrgChromiumUiPhotoPickerListener$Action, b, c, d };
    }
    
    private Action() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\PhotoPickerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */