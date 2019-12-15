package org.chromium.android_webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo.Builder;
import java.util.ArrayList;

@SuppressLint({"NewApi"})
public class AwPrintDocumentAdapter
  extends PrintDocumentAdapter
{
  private PrintAttributes jdField_a_of_type_AndroidPrintPrintAttributes;
  private String jdField_a_of_type_JavaLangString;
  private AwPdfExporter jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter;
  
  public AwPrintDocumentAdapter(AwPdfExporter paramAwPdfExporter)
  {
    this(paramAwPdfExporter, "default");
  }
  
  public AwPrintDocumentAdapter(AwPdfExporter paramAwPdfExporter, String paramString)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter = paramAwPdfExporter;
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  private static int[] a(PageRange[] paramArrayOfPageRange)
  {
    int i = paramArrayOfPageRange.length;
    int k = 0;
    if ((i == 1) && (PageRange.ALL_PAGES.equals(paramArrayOfPageRange[0]))) {
      return new int[0];
    }
    ArrayList localArrayList = new ArrayList();
    int m = paramArrayOfPageRange.length;
    i = 0;
    while (i < m)
    {
      PageRange localPageRange = paramArrayOfPageRange[i];
      int j = localPageRange.getStart();
      while (j <= localPageRange.getEnd())
      {
        localArrayList.add(Integer.valueOf(j));
        j += 1;
      }
      i += 1;
    }
    paramArrayOfPageRange = new int[localArrayList.size()];
    i = k;
    while (i < localArrayList.size())
    {
      paramArrayOfPageRange[i] = ((Integer)localArrayList.get(i)).intValue();
      i += 1;
    }
    return paramArrayOfPageRange;
  }
  
  private static PageRange[] b(PageRange[] paramArrayOfPageRange, int paramInt)
  {
    if ((paramArrayOfPageRange.length == 1) && (PageRange.ALL_PAGES.equals(paramArrayOfPageRange[0]))) {
      return new PageRange[] { new PageRange(0, paramInt - 1) };
    }
    return paramArrayOfPageRange;
  }
  
  public void onLayout(PrintAttributes paramPrintAttributes1, PrintAttributes paramPrintAttributes2, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.LayoutResultCallback paramLayoutResultCallback, Bundle paramBundle)
  {
    this.jdField_a_of_type_AndroidPrintPrintAttributes = paramPrintAttributes2;
    paramLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.jdField_a_of_type_JavaLangString).build(), true);
  }
  
  public void onWrite(final PageRange[] paramArrayOfPageRange, ParcelFileDescriptor paramParcelFileDescriptor, CancellationSignal paramCancellationSignal, final PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
  {
    if ((paramArrayOfPageRange != null) && (paramArrayOfPageRange.length != 0))
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwPdfExporter.exportToPdf(paramParcelFileDescriptor, this.jdField_a_of_type_AndroidPrintPrintAttributes, a(paramArrayOfPageRange), new AwPdfExporter.AwPdfExporterCallback()
      {
        public void pdfWritingDone(int paramAnonymousInt)
        {
          if (paramAnonymousInt > 0)
          {
            paramWriteResultCallback.onWriteFinished(AwPrintDocumentAdapter.a(paramArrayOfPageRange, paramAnonymousInt));
            return;
          }
          paramWriteResultCallback.onWriteFailed(null);
        }
      }, paramCancellationSignal);
      return;
    }
    paramWriteResultCallback.onWriteFailed(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwPrintDocumentAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */