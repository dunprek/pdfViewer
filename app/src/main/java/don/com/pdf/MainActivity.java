package don.com.pdf;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.asset.CopyAsset;
import es.voghdev.pdfviewpager.library.asset.CopyAssetThreadImpl;

public class MainActivity extends AppCompatActivity {

    PDFViewPager pdfViewPager;

    private String TAG = MainActivity.class.getSimpleName();

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.


    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
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


    private boolean checkPermission() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CopyAsset copyAsset = new CopyAssetThreadImpl(MainActivity.this, new Handler());
                    copyAsset.copy(getPDFPathonSDCard(), new File(getCacheDir(), "Proposal.pdf").getAbsolutePath());

                } else {
                    finish();
                }
                return;
            }
        }
    }


}
