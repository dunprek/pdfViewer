package don.com.pdf;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.asset.CopyAsset;
import es.voghdev.pdfviewpager.library.asset.CopyAssetThreadImpl;

public class MainActivity extends AppCompatActivity {

    PDFViewPager pdfViewPager;

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CopyAsset copyAsset = new CopyAssetThreadImpl(MainActivity.this, new Handler());
        copyAsset.copy(getPDFPathonSDCard(), new File(getCacheDir(), "Proposal.pdf").getAbsolutePath());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((PDFPagerAdapter) pdfViewPager.getAdapter()).close();
    }

    protected String getPDFPathonSDCard() {
        File file = new File(getExternalFilesDir("pdf"), "Proposal.pdf");

        Log.d(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }







}
