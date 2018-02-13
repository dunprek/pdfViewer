package don.com.pdf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String TAG = MainActivity.class.getSimpleName();

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.


    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermission()){

            PdfReader reader = null;
            try {
                reader = new PdfReader( getPDFPathonSDCard());
            } catch (IOException e) {
                e.printStackTrace();
            }

            int aa = reader.getNumberOfPages();

            Log.d(TAG+"TOTALPAGE",String.valueOf(aa));




        /*    try {
                pdfDocument = PDDocument.load(new File(getPDFPathonSDCard()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, String.valueOf(pdfDocument.getNumberOfPages()));*/
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected String getPDFPathonSDCard() {

         file = new File(getExternalFilesDir("pdf"), "tester.pdf");
        Log.d(TAG + "PDF", file.getAbsolutePath());
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
                    getPDFPathonSDCard();
                } else {
                    finish();
                }
                return;
            }
        }
    }


}
