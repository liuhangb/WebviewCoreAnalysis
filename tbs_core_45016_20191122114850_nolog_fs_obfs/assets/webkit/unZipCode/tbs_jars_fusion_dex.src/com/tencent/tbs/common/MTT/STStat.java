package com.tencent.tbs.common.MTT;

import android.text.TextUtils;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class STStat
  extends JceStruct
{
  static ArrayList<STPV> cache_stInnerPv;
  static ArrayList<STPV> cache_stOuterPv;
  static STTime cache_stTime;
  static ArrayList<STTotal> cache_stTotal;
  static UserBase cache_stUB;
  static ArrayList<ETPV> cache_vEntryPv;
  static ArrayList<FKINFO> cache_vFastKey;
  static ArrayList<PerformanceInfo> cache_vPerformanceInfo;
  static ArrayList<PerformanceStat> cache_vPerformanceStat;
  static ArrayList<STCommonAppInfo> cache_vSTCommonAppInfo;
  static ArrayList<ThirdUse> cache_vThirdUse;
  static ArrayList<Integer> cache_vUseTime;
  public int iPacketNum = 0;
  public byte iRetryCount = 0;
  public int iUseTime = 0;
  public String sAdrID = "";
  public String sMiniQbVersion = "";
  public String sPacketTime = "";
  public String sProtocol = "";
  public String sQIMEI = "";
  public String sSource = "";
  public ArrayList<STPV> stInnerPv = null;
  public ArrayList<STPV> stOuterPv = null;
  public STTime stTime = null;
  public ArrayList<STTotal> stTotal = null;
  public UserBase stUB = null;
  public ArrayList<ETPV> vEntryPv = null;
  public ArrayList<FKINFO> vFastKey = null;
  public ArrayList<PerformanceInfo> vPerformanceInfo = null;
  public ArrayList<PerformanceStat> vPerformanceStat = null;
  public ArrayList<STCommonAppInfo> vSTCommonAppInfo = null;
  public ArrayList<ThirdUse> vThirdUse = null;
  public ArrayList<Integer> vUseTime = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_stUB == null) {
      cache_stUB = new UserBase();
    }
    this.stUB = ((UserBase)paramJceInputStream.read(cache_stUB, 0, true));
    if (cache_stTime == null) {
      cache_stTime = new STTime();
    }
    this.stTime = ((STTime)paramJceInputStream.read(cache_stTime, 1, true));
    Object localObject;
    if (cache_stTotal == null)
    {
      cache_stTotal = new ArrayList();
      localObject = new STTotal();
      cache_stTotal.add(localObject);
    }
    this.stTotal = ((ArrayList)paramJceInputStream.read(cache_stTotal, 2, true));
    if (cache_stOuterPv == null)
    {
      cache_stOuterPv = new ArrayList();
      localObject = new STPV();
      cache_stOuterPv.add(localObject);
    }
    this.stOuterPv = ((ArrayList)paramJceInputStream.read(cache_stOuterPv, 3, true));
    if (cache_stInnerPv == null)
    {
      cache_stInnerPv = new ArrayList();
      localObject = new STPV();
      cache_stInnerPv.add(localObject);
    }
    this.stInnerPv = ((ArrayList)paramJceInputStream.read(cache_stInnerPv, 4, true));
    this.iUseTime = paramJceInputStream.read(this.iUseTime, 5, true);
    if (cache_vEntryPv == null)
    {
      cache_vEntryPv = new ArrayList();
      localObject = new ETPV();
      cache_vEntryPv.add(localObject);
    }
    this.vEntryPv = ((ArrayList)paramJceInputStream.read(cache_vEntryPv, 6, false));
    if (cache_vFastKey == null)
    {
      cache_vFastKey = new ArrayList();
      localObject = new FKINFO();
      cache_vFastKey.add(localObject);
    }
    this.vFastKey = ((ArrayList)paramJceInputStream.read(cache_vFastKey, 7, false));
    this.sProtocol = paramJceInputStream.readString(8, false);
    if (cache_vThirdUse == null)
    {
      cache_vThirdUse = new ArrayList();
      localObject = new ThirdUse();
      cache_vThirdUse.add(localObject);
    }
    this.vThirdUse = ((ArrayList)paramJceInputStream.read(cache_vThirdUse, 9, false));
    if (cache_vPerformanceInfo == null)
    {
      cache_vPerformanceInfo = new ArrayList();
      localObject = new PerformanceInfo();
      cache_vPerformanceInfo.add(localObject);
    }
    this.vPerformanceInfo = ((ArrayList)paramJceInputStream.read(cache_vPerformanceInfo, 10, false));
    if (cache_vUseTime == null)
    {
      cache_vUseTime = new ArrayList();
      cache_vUseTime.add(Integer.valueOf(0));
    }
    this.vUseTime = ((ArrayList)paramJceInputStream.read(cache_vUseTime, 11, false));
    if (cache_vPerformanceStat == null)
    {
      cache_vPerformanceStat = new ArrayList();
      localObject = new PerformanceStat();
      cache_vPerformanceStat.add(localObject);
    }
    this.vPerformanceStat = ((ArrayList)paramJceInputStream.read(cache_vPerformanceStat, 12, false));
    this.iPacketNum = paramJceInputStream.read(this.iPacketNum, 13, false);
    this.iRetryCount = paramJceInputStream.read(this.iRetryCount, 14, false);
    this.sPacketTime = paramJceInputStream.readString(15, false);
    if (cache_vSTCommonAppInfo == null)
    {
      cache_vSTCommonAppInfo = new ArrayList();
      localObject = new STCommonAppInfo();
      cache_vSTCommonAppInfo.add(localObject);
    }
    this.vSTCommonAppInfo = ((ArrayList)paramJceInputStream.read(cache_vSTCommonAppInfo, 16, false));
    this.sSource = paramJceInputStream.readString(17, false);
    this.sQIMEI = paramJceInputStream.readString(18, false);
    this.sAdrID = paramJceInputStream.readString(19, false);
    this.sMiniQbVersion = paramJceInputStream.readString(20, false);
  }
  
  public void setForSimpleQB(boolean paramBoolean, String paramString)
  {
    if (paramBoolean) {
      this.sSource = "miniqb";
    } else {
      this.sSource = "";
    }
    if ((paramBoolean) && (!TextUtils.isEmpty(paramString))) {
      this.sMiniQbVersion = paramString;
    }
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.stUB, 0);
    paramJceOutputStream.write(this.stTime, 1);
    paramJceOutputStream.write(this.stTotal, 2);
    paramJceOutputStream.write(this.stOuterPv, 3);
    paramJceOutputStream.write(this.stInnerPv, 4);
    paramJceOutputStream.write(this.iUseTime, 5);
    Object localObject = this.vEntryPv;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 6);
    }
    localObject = this.vFastKey;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 7);
    }
    localObject = this.sProtocol;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 8);
    }
    localObject = this.vThirdUse;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 9);
    }
    localObject = this.vPerformanceInfo;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 10);
    }
    localObject = this.vUseTime;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 11);
    }
    localObject = this.vPerformanceStat;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 12);
    }
    paramJceOutputStream.write(this.iPacketNum, 13);
    paramJceOutputStream.write(this.iRetryCount, 14);
    localObject = this.sPacketTime;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 15);
    }
    localObject = this.vSTCommonAppInfo;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 16);
    }
    localObject = this.sSource;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 17);
    }
    localObject = this.sQIMEI;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 18);
    }
    localObject = this.sAdrID;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 19);
    }
    localObject = this.sMiniQbVersion;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 20);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\STStat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */