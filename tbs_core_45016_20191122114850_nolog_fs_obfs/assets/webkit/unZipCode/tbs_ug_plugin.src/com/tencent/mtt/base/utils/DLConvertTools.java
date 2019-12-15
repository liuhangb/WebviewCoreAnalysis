package com.tencent.mtt.base.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.mtt.browser.download.engine.DownloadTask;

public class DLConvertTools
{
  private static int A = -1;
  private static int B = -1;
  private static int C = -1;
  private static int D = -1;
  private static int E = -1;
  private static int F = -1;
  private static int G = -1;
  private static int H = -1;
  private static int I = -1;
  private static int J = -1;
  private static int K = -1;
  private static int L = -1;
  private static int M = -1;
  private static int N = -1;
  private static int O = -1;
  private static int P = -1;
  private static int Q = -1;
  private static int R = -1;
  private static int S = -1;
  private static int T = -1;
  private static int U = -1;
  private static int V = -1;
  private static int W = -1;
  private static int jdField_a_of_type_Int = -1;
  private static volatile boolean jdField_a_of_type_Boolean = false;
  private static int b = -1;
  private static int c = -1;
  private static int d = -1;
  private static int e = -1;
  private static int f = -1;
  private static int g = -1;
  private static int h = -1;
  private static int i = -1;
  private static int j = -1;
  private static int k = -1;
  private static int l = -1;
  private static int m = -1;
  private static int n = -1;
  private static int o = -1;
  private static int p = -1;
  private static int q = -1;
  private static int r = -1;
  private static int s = -1;
  public static ConvertTaskInterface sConvertTaskInterface;
  private static int t = -1;
  private static int u = -1;
  private static int v = -1;
  private static int w = -1;
  private static int x = -1;
  private static int y = -1;
  private static int z = -1;
  
  private static void a(Cursor paramCursor)
  {
    if (jdField_a_of_type_Boolean) {
      return;
    }
    jdField_a_of_type_Int = paramCursor.getColumnIndex("id");
    b = paramCursor.getColumnIndex("status");
    c = paramCursor.getColumnIndex("url");
    d = paramCursor.getColumnIndex("filename");
    e = paramCursor.getColumnIndex("filefolderpath");
    f = paramCursor.getColumnIndex("downloadsize");
    g = paramCursor.getColumnIndex("downloadedsize");
    h = paramCursor.getColumnIndex("totalsize");
    i = paramCursor.getColumnIndex("supportresume");
    j = paramCursor.getColumnIndex("referer");
    k = paramCursor.getColumnIndex("flag");
    l = paramCursor.getColumnIndex("costtime");
    m = paramCursor.getColumnIndex("createdate");
    n = paramCursor.getColumnIndex("donedate");
    o = paramCursor.getColumnIndex("etag");
    p = paramCursor.getColumnIndex("threadnum");
    q = paramCursor.getColumnIndex("annotation");
    r = paramCursor.getColumnIndex("annotationext");
    s = paramCursor.getColumnIndex("extend_1");
    t = paramCursor.getColumnIndex("extend_2");
    u = paramCursor.getColumnIndex("extend_3");
    v = paramCursor.getColumnIndex("extend_4");
    w = paramCursor.getColumnIndex("extend_5");
    x = paramCursor.getColumnIndex("extend_6");
    y = paramCursor.getColumnIndex("extend_9");
    z = paramCursor.getColumnIndex("versionname");
    A = paramCursor.getColumnIndex("startpos1");
    B = paramCursor.getColumnIndex("endpos1");
    C = paramCursor.getColumnIndex("writepos1");
    D = paramCursor.getColumnIndex("startpos2");
    E = paramCursor.getColumnIndex("endpos2");
    F = paramCursor.getColumnIndex("writepos2");
    G = paramCursor.getColumnIndex("startpos3");
    H = paramCursor.getColumnIndex("endpos3");
    I = paramCursor.getColumnIndex("writepos3");
    J = paramCursor.getColumnIndex("ext_flag");
    K = paramCursor.getColumnIndex("extend_7");
    L = paramCursor.getColumnIndex("second_extend_1");
    M = paramCursor.getColumnIndex("second_extend_2");
    N = paramCursor.getColumnIndex("second_extend_3");
    O = paramCursor.getColumnIndex("second_extend_4");
    P = paramCursor.getColumnIndex("second_extend_5");
    Q = paramCursor.getColumnIndex("second_extend_6");
    R = paramCursor.getColumnIndex("second_extend_7");
    S = paramCursor.getColumnIndex("second_extend_8");
    T = paramCursor.getColumnIndex("second_extend_9");
    U = paramCursor.getColumnIndex("second_extend_10");
    V = paramCursor.getColumnIndex("download_operations");
    W = paramCursor.getColumnIndex("extend_8");
    jdField_a_of_type_Boolean = true;
  }
  
  public static ContentValues cursor2ContentValues(Cursor paramCursor)
  {
    if (paramCursor == null) {
      return null;
    }
    a(paramCursor);
    ContentValues localContentValues = new ContentValues();
    int i1 = jdField_a_of_type_Int;
    if ((i1 != -1) && (paramCursor.getInt(i1) != -1)) {
      localContentValues.put("id", Integer.valueOf(paramCursor.getInt(jdField_a_of_type_Int)));
    }
    i1 = y;
    String str;
    if (i1 != -1)
    {
      str = paramCursor.getString(i1);
      if (!TextUtils.isEmpty(str)) {
        localContentValues.put("extend_9", str);
      }
    }
    i1 = l;
    if (i1 != -1) {
      localContentValues.put("costtime", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = c;
    if (i1 != -1) {
      localContentValues.put("url", paramCursor.getString(i1));
    }
    i1 = d;
    if (i1 != -1) {
      localContentValues.put("filename", paramCursor.getString(i1));
    }
    i1 = e;
    if (i1 != -1) {
      localContentValues.put("filefolderpath", paramCursor.getString(i1));
    }
    i1 = h;
    if (i1 != -1) {
      localContentValues.put("totalsize", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = f;
    if (i1 != -1) {
      localContentValues.put("downloadsize", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = g;
    if (i1 != -1) {
      localContentValues.put("downloadedsize", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = b;
    if (i1 != -1) {
      localContentValues.put("status", Byte.valueOf((byte)paramCursor.getInt(i1)));
    }
    i1 = i;
    if (i1 != -1) {
      localContentValues.put("supportresume", Integer.valueOf(paramCursor.getInt(i1)));
    }
    i1 = m;
    if (i1 != -1) {
      localContentValues.put("createdate", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = n;
    if (i1 != -1) {
      localContentValues.put("donedate", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = j;
    if (i1 != -1) {
      localContentValues.put("referer", paramCursor.getString(i1));
    }
    i1 = k;
    if (i1 != -1) {
      localContentValues.put("flag", Integer.valueOf(paramCursor.getInt(i1)));
    }
    i1 = o;
    if (i1 != -1)
    {
      str = paramCursor.getString(i1);
      if (!TextUtils.isEmpty(str)) {
        localContentValues.put("etag", str);
      }
    }
    i1 = p;
    if (i1 != -1) {
      localContentValues.put("threadnum", Integer.valueOf(paramCursor.getInt(i1)));
    }
    i1 = q;
    if (i1 != -1) {
      localContentValues.put("annotation", paramCursor.getString(i1));
    }
    i1 = r;
    if (i1 != -1) {
      localContentValues.put("annotationext", paramCursor.getString(i1));
    }
    i1 = s;
    if (i1 != -1) {
      localContentValues.put("extend_1", paramCursor.getString(i1));
    }
    i1 = v;
    if (i1 != -1) {
      localContentValues.put("extend_4", paramCursor.getString(i1));
    }
    i1 = t;
    if (i1 != -1) {
      localContentValues.put("extend_2", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = u;
    if (i1 != -1)
    {
      str = paramCursor.getString(i1);
      if (!TextUtils.isEmpty(str)) {
        localContentValues.put("extend_3", str);
      }
    }
    i1 = w;
    if (i1 != -1) {
      localContentValues.put("extend_5", Integer.valueOf(paramCursor.getInt(i1)));
    }
    i1 = x;
    if (i1 != -1) {
      localContentValues.put("extend_6", Integer.valueOf(paramCursor.getInt(i1)));
    }
    i1 = K;
    if (i1 != -1)
    {
      str = paramCursor.getString(i1);
      if (!TextUtils.isEmpty(str)) {
        localContentValues.put("extend_7", str);
      }
    }
    i1 = z;
    if (i1 != -1)
    {
      str = paramCursor.getString(i1);
      if (!TextUtils.isEmpty(str)) {
        localContentValues.put("versionname", str);
      }
    }
    i1 = A;
    if (i1 != -1) {
      localContentValues.put("startpos1", paramCursor.getString(i1));
    }
    i1 = B;
    if (i1 != -1) {
      localContentValues.put("endpos1", paramCursor.getString(i1));
    }
    i1 = C;
    if (i1 != -1) {
      localContentValues.put("writepos1", paramCursor.getString(i1));
    }
    i1 = D;
    if (i1 != -1) {
      localContentValues.put("startpos2", paramCursor.getString(i1));
    }
    i1 = E;
    if (i1 != -1) {
      localContentValues.put("endpos2", paramCursor.getString(i1));
    }
    i1 = F;
    if (i1 != -1) {
      localContentValues.put("writepos2", paramCursor.getString(i1));
    }
    i1 = G;
    if (i1 != -1) {
      localContentValues.put("startpos3", paramCursor.getString(i1));
    }
    i1 = H;
    if (i1 != -1) {
      localContentValues.put("endpos3", paramCursor.getString(i1));
    }
    i1 = I;
    if (i1 != -1) {
      localContentValues.put("writepos3", paramCursor.getString(i1));
    }
    if (paramCursor.getColumnIndex("ext_flag") != -1) {
      localContentValues.put("ext_flag", Long.valueOf(paramCursor.getLong(paramCursor.getColumnIndex("ext_flag"))));
    }
    i1 = L;
    if (i1 != -1) {
      localContentValues.put("second_extend_1", Integer.valueOf(paramCursor.getInt(i1)));
    }
    i1 = M;
    if (i1 != -1) {
      localContentValues.put("second_extend_2", Long.valueOf(paramCursor.getLong(i1)));
    }
    i1 = N;
    if (i1 != -1) {
      localContentValues.put("second_extend_3", paramCursor.getString(i1));
    }
    i1 = O;
    if (i1 != -1) {
      localContentValues.put("second_extend_4", paramCursor.getString(i1));
    }
    i1 = Q;
    if (i1 != -1) {
      localContentValues.put("second_extend_6", paramCursor.getString(i1));
    }
    i1 = P;
    if (i1 != -1) {
      localContentValues.put("second_extend_5", paramCursor.getString(i1));
    }
    i1 = R;
    if (i1 != -1) {
      localContentValues.put("second_extend_7", paramCursor.getString(i1));
    }
    i1 = S;
    if (i1 != -1) {
      localContentValues.put("second_extend_8", paramCursor.getString(i1));
    }
    i1 = T;
    if (i1 != -1) {
      localContentValues.put("second_extend_9", paramCursor.getString(i1));
    }
    i1 = U;
    if (i1 != -1) {
      localContentValues.put("second_extend_10", paramCursor.getString(i1));
    }
    return localContentValues;
  }
  
  public static DownloadTask cursor2DownloadTask(Context paramContext, Cursor paramCursor)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a20 = a2\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\r\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\r\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\nCaused by: java.lang.NullPointerException\r\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:552)\r\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:1)\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:166)\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:1)\r\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:331)\r\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:387)\r\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:90)\r\n\t... 17 more\r\n");
  }
  
  public static ContentValues downloadTast2ContentValues(DownloadTask paramDownloadTask)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static int getTastIdFromURI(Uri paramUri)
  {
    return (int)ContentUris.parseId(paramUri);
  }
  
  public static abstract interface ConvertTaskInterface
  {
    public abstract DownloadTask getVideoTask(DownloadTask paramDownloadTask);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLConvertTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */