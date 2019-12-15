package com.tencent.common.wup.base;

import com.tencent.basesupport.FLogger;
import com.tencent.common.wup.interfaces.IWUPClientProxy;

public class WupOaepEncryptController
{
  private volatile byte jdField_a_of_type_Byte = 0;
  private IWUPClientProxy jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy = null;
  
  public WupOaepEncryptController(IWUPClientProxy paramIWUPClientProxy)
  {
    this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy = paramIWUPClientProxy;
  }
  
  private void a(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setWupEncryptType: encType = ");
    localStringBuilder.append(paramInt);
    FLogger.d("WupOaepEncryptController", localStringBuilder.toString());
    this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.setIntConfiguration("key_wup_rsa_aes_encrypt_type", paramInt);
    this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.setLongConfiguration("key_last_modify_wup_encrypt_time", System.currentTimeMillis());
  }
  
  public void disableOAPEPadding()
  {
    try
    {
      if (this.jdField_a_of_type_Byte != 2)
      {
        FLogger.d("WupOaepEncryptController", "disableOAPEPadding: current is no padding, ignore");
        return;
      }
      this.jdField_a_of_type_Byte = 1;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("disableOAPEPadding: encType = ");
      localStringBuilder.append(this.jdField_a_of_type_Byte);
      FLogger.d("WupOaepEncryptController", localStringBuilder.toString());
      a(this.jdField_a_of_type_Byte);
      return;
    }
    finally {}
  }
  
  public byte getWupEncryptType()
  {
    for (;;)
    {
      try
      {
        byte b1;
        if (this.jdField_a_of_type_Byte != 0)
        {
          b1 = this.jdField_a_of_type_Byte;
          return b1;
        }
        byte b2 = (byte)this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.getIntConfiguration("key_wup_rsa_aes_encrypt_type", 2);
        long l = this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.getLongConfiguration("key_last_modify_wup_encrypt_time", 0L);
        if (System.currentTimeMillis() - l > 86400000L)
        {
          i = 1;
          if ((b2 != 1) && (b2 != 2))
          {
            FLogger.d("WupOaepEncryptController", "getWupEncryptType: error occured, use default no padding");
            a(1);
            b1 = 1;
          }
          else
          {
            b1 = b2;
            if (b2 == 1)
            {
              b1 = b2;
              if (i != 0)
              {
                a(2);
                FLogger.d("WupOaepEncryptController", "getWupEncryptType: current is no padding, but last modify more than 24 hour");
                b1 = 2;
              }
            }
          }
          this.jdField_a_of_type_Byte = b1;
          return b1;
        }
      }
      finally {}
      int i = 0;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\WupOaepEncryptController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */