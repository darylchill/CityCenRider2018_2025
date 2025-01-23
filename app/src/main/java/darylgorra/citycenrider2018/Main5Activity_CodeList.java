package darylgorra.citycenrider2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.barteksc.pdfviewer.PDFView;

public class Main5Activity_CodeList extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5_code_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CityCenRider - Code Listl");

        pdfView=findViewById(R.id.pdfView_codelist);
        pdfView.fromAsset("codelist.pdf").load();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        onBackPressed();
        return true;
    }
}
