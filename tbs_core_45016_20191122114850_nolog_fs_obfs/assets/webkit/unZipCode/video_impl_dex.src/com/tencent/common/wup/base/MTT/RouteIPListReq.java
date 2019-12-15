package com.tencent.common.wup.base.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class RouteIPListReq
  extends JceStruct
{
  static UserBase jdField_a_of_type_ComTencentCommonWupBaseMTTUserBase = new UserBase();
  static ArrayList<Integer> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  public int iSubType = 0;
  public String sExtraInfo = "";
  public String sMCCMNC = "";
  public String sTypeName = "";
  public UserBase stUB = null;
  public ArrayList<Integer> vIPType = null;
  
  static
  {
    jdField_a_of_type_JavaUtilArrayList.add(Integer.valueOf(0));
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.stUB = ((UserBase)paramJceInputStream.read(jdField_a_of_type_ComTencentCommonWupBaseMTTUserBase, 0, true));
    this.vIPType = ((ArrayList)paramJceInputStream.read(jdField_a_of_type_JavaUtilArrayList, 1, true));
    this.sTypeName = paramJceInputStream.readString(2, false);
    this.iSubType = paramJceInputStream.read(this.iSubType, 3, false);
    this.sExtraInfo = paramJceInputStream.readString(4, false);
    this.sMCCMNC = paramJceInputStream.readString(5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.stUB, 0);
    paramJceOutputStream.write(this.vIPType, 1);
    String str = this.sTypeName;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    paramJceOutputStream.write(this.iSubType, 3);
    str = this.sExtraInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
    str = this.sMCCMNC;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\MTT\RouteIPListReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */