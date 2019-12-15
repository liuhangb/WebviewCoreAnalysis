package com.tencent.mtt.external.reader.base;

import android.widget.FrameLayout;

public abstract class ReaderBaseView
{
  final int jdField_a_of_type_Int = 1;
  IReaderEvent jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = null;
  final int b = 2;
  final int c = 1;
  final int d = 2;
  
  public abstract int create();
  
  public abstract void destroy();
  
  public abstract FrameLayout getFrameLayout();
  
  public final void postEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    IReaderEvent localIReaderEvent = this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent;
    if (localIReaderEvent != null) {
      localIReaderEvent.onUiEvent(paramInt, paramObject1, paramObject2);
    }
  }
  
  public final void setEvent(IReaderEvent paramIReaderEvent)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = paramIReaderEvent;
  }
  
  public void switchSkin() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderBaseView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */