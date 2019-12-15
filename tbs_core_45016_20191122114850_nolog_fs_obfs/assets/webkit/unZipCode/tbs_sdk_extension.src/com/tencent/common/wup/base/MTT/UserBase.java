package com.tencent.common.wup.base.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class UserBase
  extends JceStruct
{
  static ArrayList<Long> jdField_a_of_type_JavaUtilArrayList;
  static byte[] jdField_a_of_type_ArrayOfByte;
  static byte[] b;
  static byte[] c;
  public boolean bSave = true;
  public int iLanguageType = 200;
  public short iMCC = 0;
  public short iMNC = 0;
  public int iServerVer = 1;
  public String sAPN = "";
  public String sAdId = "";
  public String sCellNumber = "";
  public String sCellid = "";
  public String sCellphone = "";
  public String sChannel = "";
  public String sFirstChannel = "";
  public byte[] sGUID = new byte[16];
  public String sIMEI = "";
  public String sLAC = "";
  public String sLC = "";
  public byte[] sMac = new byte[1];
  public String sQUA = "";
  public String sUA = "";
  public String sUin = "";
  public String sVenderId = "";
  public byte[] vLBSKeyData = new byte[1];
  public ArrayList<Long> vWifiMacs = new ArrayList();
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sIMEI = paramJceInputStream.readString(0, true);
    if (jdField_a_of_type_ArrayOfByte == null)
    {
      jdField_a_of_type_ArrayOfByte = (byte[])new byte[1];
      ((byte[])jdField_a_of_type_ArrayOfByte)[0] = 0;
    }
    this.sGUID = ((byte[])paramJceInputStream.read(jdField_a_of_type_ArrayOfByte, 1, true));
    this.sQUA = paramJceInputStream.readString(2, true);
    this.sLC = paramJceInputStream.readString(3, true);
    this.sCellphone = paramJceInputStream.readString(4, true);
    this.sUin = paramJceInputStream.readString(5, true);
    this.sCellid = paramJceInputStream.readString(6, false);
    this.iServerVer = paramJceInputStream.read(this.iServerVer, 7, false);
    this.bSave = paramJceInputStream.read(this.bSave, 8, false);
    this.sChannel = paramJceInputStream.readString(9, false);
    this.sLAC = paramJceInputStream.readString(10, false);
    this.sUA = paramJceInputStream.readString(11, false);
    this.iLanguageType = paramJceInputStream.read(this.iLanguageType, 12, false);
    this.iMCC = paramJceInputStream.read(this.iMCC, 13, false);
    this.iMNC = paramJceInputStream.read(this.iMNC, 14, false);
    this.sAPN = paramJceInputStream.readString(15, false);
    this.sCellNumber = paramJceInputStream.readString(16, false);
    if (b == null)
    {
      b = (byte[])new byte[1];
      ((byte[])b)[0] = 0;
    }
    this.sMac = ((byte[])paramJceInputStream.read(b, 17, false));
    if (jdField_a_of_type_JavaUtilArrayList == null)
    {
      jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      jdField_a_of_type_JavaUtilArrayList.add(Long.valueOf(0L));
    }
    this.vWifiMacs = ((ArrayList)paramJceInputStream.read(jdField_a_of_type_JavaUtilArrayList, 19, false));
    if (c == null)
    {
      c = (byte[])new byte[1];
      ((byte[])c)[0] = 0;
    }
    this.vLBSKeyData = ((byte[])paramJceInputStream.read(c, 20, false));
    this.sVenderId = paramJceInputStream.readString(21, false);
    this.sAdId = paramJceInputStream.readString(22, false);
    this.sFirstChannel = paramJceInputStream.readString(23, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sIMEI, 0);
    Object localObject = this.sGUID;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 1);
    }
    paramJceOutputStream.write(this.sQUA, 2);
    paramJceOutputStream.write(this.sLC, 3);
    paramJceOutputStream.write(this.sCellphone, 4);
    paramJceOutputStream.write(this.sUin, 5);
    localObject = this.sCellid;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 6);
    }
    paramJceOutputStream.write(this.iServerVer, 7);
    paramJceOutputStream.write(this.bSave, 8);
    localObject = this.sChannel;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 9);
    }
    localObject = this.sLAC;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 10);
    }
    localObject = this.sUA;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 11);
    }
    paramJceOutputStream.write(this.iLanguageType, 12);
    paramJceOutputStream.write(this.iMCC, 13);
    paramJceOutputStream.write(this.iMNC, 14);
    localObject = this.sAPN;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 15);
    }
    localObject = this.sCellNumber;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 16);
    }
    localObject = this.sMac;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 17);
    }
    localObject = this.vWifiMacs;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 19);
    }
    localObject = this.vLBSKeyData;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 20);
    }
    localObject = this.sVenderId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 21);
    }
    localObject = this.sAdId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 22);
    }
    localObject = this.sFirstChannel;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 23);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\MTT\UserBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */