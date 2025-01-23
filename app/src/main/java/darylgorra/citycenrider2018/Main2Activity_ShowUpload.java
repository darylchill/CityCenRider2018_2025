package darylgorra.citycenrider2018;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import darylgorra.citycenrider2018.Classes.MySettings;
import darylgorra.citycenrider2018.Classes.databaseHelper;


public class Main2Activity_ShowUpload extends AppCompatActivity implements View.OnClickListener {

    EditText selectDate;
    private int mYear, mMonth, mDay;
    private databaseHelper DBConn = new databaseHelper(Main2Activity_ShowUpload.this);
    private MySettings mySettings = new MySettings();
    ListView lstView_HCNLIST;
    ArrayList<String> lst = new ArrayList<String>();
    ListAdapter  listHCNAdapter = null;
    private FloatingActionButton fab_Upload;


    private static final String TAG = "CityCenRider_Upload";
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CREATOR = 2;

    private CoordinatorLayout coordinatorLayout;
    private GoogleSignInClient mGoogleSignInClient;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;
    private File mFileToSave;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public String ExcelFolderName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__show_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CityCenRider - Uploading");

       coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_Upload);
        fab_Upload = (FloatingActionButton) findViewById(R.id.fab_Upload_Data);
        lstView_HCNLIST= (ListView) findViewById(R.id.listView_HCN_List);

        lstView_HCNLIST.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentGoToShowDataAct = new Intent(Main2Activity_ShowUpload.this,Main2Activity_ShowData.class);
                intentGoToShowDataAct.putExtra("HCN",lstView_HCNLIST.getItemAtPosition(position).toString());
                intentGoToShowDataAct.putExtra("DateEntered",selectDate.getText().toString());
                startActivity(intentGoToShowDataAct);
            }
        });

        lstView_HCNLIST.setLongClickable(true);
        lstView_HCNLIST.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {

                final String myPass = mySettings.getMasterPass(Main2Activity_ShowUpload.this);


                if (myPass == null)
                {
                    Snackbar.make(coordinatorLayout, "Please connect to internet to get the ATT Password", Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(Main2Activity_ShowUpload.this,"Please connect to internet to get the ATT Password",Toast.LENGTH_LONG).show();
                }
                else
                    {
                        //CALL PASSWORD DIALOG
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activity_ShowUpload.this);
                        final View alertView = getLayoutInflater().inflate(R.layout.dialog_supervisor_password, null);
                        mBuilder.setView(alertView);
                        final AlertDialog dialog = mBuilder.create();

                        final Button btnSubmitUpdated = (Button) alertView.findViewById(R.id.btnSubmitUpdatedData);
                        btnSubmitUpdated.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if(event.getAction() == event.ACTION_UP)
                                {

                                    EditText editTextPassword = (EditText) alertView.findViewById(R.id.txtBoxSettings_SupervisorPass);

                                    //IF CORRECT PASSWORD
                                    if (editTextPassword.getText().toString().equals(myPass))
                                    {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(Main2Activity_ShowUpload.this);
                                        alert.setTitle("Alert!!");
                                        alert.setMessage("Are you sure to delete this HCN?");
                                        alert.setPositiveButton("YES", new DialogInterface.OnClickListener()
                                        {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                DBConn.remove_FINAL_HCN_SUBMIT(lstView_HCNLIST.getItemAtPosition(position).toString());
                                                loadDatabaseHCNToAdapter();
                                                dialog.dismiss();

                                            }
                                        });
                                        alert.setNegativeButton("NO", new DialogInterface.OnClickListener()
                                        {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {

                                                dialog.dismiss();
                                            }
                                        });

                                        alert.show();
                                    }

                                    else
                                    {
                                        Snackbar.make(coordinatorLayout, "Wrong Password", Snackbar.LENGTH_LONG).show();
                                        //Toast.makeText(Main2Activity_ShowUpload.this,"Wrong Password",Toast.LENGTH_LONG).show();
                                    }


                                }
                                return false;
                            }
                        });

                        dialog.show();
                    }

                return true;
            }
        });



        selectDate = (EditText) findViewById(R.id.txtBoxCalendarPicker);
        selectDate.setInputType(InputType.TYPE_NULL);
        selectDate.setText(DBConn.getDateToday());
        selectDate.setOnClickListener(this);


        loadDatabaseHCNToAdapter();



    //////// HERE FOR THE UPLOAD BUTTON//////////////////////
            fab_Upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //CHECK FOR THE INTERNET
                    if(checkIf_INTERNET_IS_ON() == false)
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity_ShowUpload.this);
                        alertDialogBuilder.setMessage("Please check your internet connection.");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent dialogIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(dialogIntent);
                                    }
                                }
                        );
                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();

                    }
                    else
                    {
                        //CHECK FOR THE DOWNLOADED SUPERVISOR PASSWORD
                            final String myPass = mySettings.getCurrent_FS_PASS(Main2Activity_ShowUpload.this);

                            if (myPass == null)
                            {
                                Snackbar.make(coordinatorLayout, "Please connect to internet to get your Supervisor Password", Snackbar.LENGTH_LONG).show();
                                //Toast.makeText(Main2Activity_ShowUpload.this,"Please connect to internet to get your Supervisor Password",Toast.LENGTH_LONG).show();

                            }
                            else
                            {


                                //CALL PASSWORD DIALOG
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activity_ShowUpload.this);
                                final View alertView = getLayoutInflater().inflate(R.layout.dialog_supervisor_password, null);
                                mBuilder.setView(alertView);
                                final AlertDialog dialog = mBuilder.create();

                                final Button btnSubmitUpdated = (Button) alertView.findViewById(R.id.btnSubmitUpdatedData);
                                btnSubmitUpdated.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if(event.getAction() == event.ACTION_UP)
                                        {
                                            EditText editTextPassword = (EditText) alertView.findViewById(R.id.txtBoxSettings_SupervisorPass);

                                            if (editTextPassword.getText().toString().equals(myPass))
                                            {

                                                ExcelFolderName = selectDate.getText().toString() + "_" + String.valueOf(DBConn.getBarangayName(mySettings.getBarangayCode(Main2Activity_ShowUpload.this))) + "_" +
                                                        mySettings.getInterviewer(Main2Activity_ShowUpload.this) + "_" + mySettings.getUploadNo(Main2Activity_ShowUpload.this);
                                                saveExcelFile(Main2Activity_ShowUpload.this,ExcelFolderName+"_file.xls");
                                                mySettings.setUploadNo(Main2Activity_ShowUpload.this, mySettings.getUploadNo(Main2Activity_ShowUpload.this) + 1);
                                                dialog.dismiss();
                                            }
                                            else
                                            {
                                                //Toast.makeText(Main2Activity_ShowUpload.this,"Wrong Password",Toast.LENGTH_LONG).show();
                                                Snackbar.make(coordinatorLayout, "Wrong Password", Snackbar.LENGTH_LONG).show();
                                            }

                                        }
                                        return false;
                                    }
                                });

                                dialog.show();

                            }

                    }

                }
            });

    }

    @Override
    public void onClick(View view) {
        if (view == selectDate)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth)
                        {
                            //HERE TO CHANGE THE FORMAT THE VALUE OF DATETIMEPICKER

                            String dayFormat = null;
                            String monthFormat = null;
                            //IF DAY IS LESSER THAN 10
                            if(dayOfMonth <=9 )
                            {
                                dayFormat =  "0" + String.valueOf(dayOfMonth) ;
                            }
                            else
                            {
                                dayFormat =   String.valueOf(dayOfMonth) ;
                            }

                            //IF MONTH IS LESSER THAN 10
                            if (monthOfYear  <=9)
                            {
                                monthFormat = "0" +String.valueOf(monthOfYear +1);
                            }
                            else
                            {
                                monthFormat = String.valueOf(monthOfYear +1);
                            }

                            //IF DAY AND MONTH IS LESSER THAN 10
                            if (dayOfMonth <=9 && monthOfYear  <=9 )
                            {
                                dayFormat =  "0" + String.valueOf(dayOfMonth) ;
                                monthFormat = "0" +String.valueOf(monthOfYear +1);
                            }

                            selectDate.setText( year +"-"+monthFormat + "-" +dayFormat);

                            loadDatabaseHCNToAdapter();


                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();
        }

    }

    private void loadDatabaseHCNToAdapter()
    {

        try
        {
            String[] HCN_DATA_LIST = null;

            //CHECK IF ARRAY IS NULL
            if (DBConn.getAllHHIDData_BY_DATE(selectDate.getText().toString()) == null)
            {
                HCN_DATA_LIST= new String[]{"DATA EMPTY"};
                lstView_HCNLIST.setEnabled(false);
            }
            else
            {
                HCN_DATA_LIST= DBConn.getAllHHIDData_BY_DATE(selectDate.getText().toString());
                lstView_HCNLIST.setEnabled(true);
            }


            lst = new ArrayList<String>(Arrays.asList(HCN_DATA_LIST));

            listHCNAdapter =  new ArrayAdapter<String>(Main2Activity_ShowUpload.this,R.layout.activity_custom_listview_hcn,R.id.txtView_listviewHCN,lst);

            lstView_HCNLIST.setAdapter(listHCNAdapter);
            ((ArrayAdapter) listHCNAdapter).notifyDataSetChanged();



        }
        catch (NullPointerException e)
        {

            Toast.makeText(Main2Activity_ShowUpload.this, "No data to fetch", Toast.LENGTH_LONG).show();
            lstView_HCNLIST.invalidateViews();
            lstView_HCNLIST.setEnabled(false);
        }

    }


    public boolean checkIf_INTERNET_IS_ON()
    {
        boolean checker;
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED)
        {
            checker=true;
            return checker;
        }
        else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED)
        {
            checker = false;
            return checker;
        }
        checker = false;
        return checker;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public boolean createRow_A_O_Question_Sheet1 (Workbook wb, Cell cell)
    {
        boolean success;

        try
        {
            int totalRow = 1;

            String selectQuery;
            selectQuery =String.format("SELECT tbl_BF_Tables.HCN,tbl_RespondentNames.RespondentName,Lastname,Firstname,Middlename,C1,C2,C2_a,C3,C4,C5," +
                    "D6,D7,E8,E9,E10,E10_a,E11,E12,E13,E13_a,E14," +
                    "F15,F15_a,F16,F17,F17_a,F17_b,F17_c,F17_d,F17_BCG,F17_Penta1,F17_Penta2,F17_Penta3," +
                    "F17_OPV1,F17_OPV2,F17_OPV3,F17_HEPAB1,F17_MEASLES,F17_f," +
                    "F18,F19,F20,F21,F21_a,F22,F22_a,F22_b,F23,F24," +
                    "G25,G26 ,H27," +
                    "H28Meat ,H28Seafoods INTEGER,H28Processed," +
                    "H28Fruits,H28Vegetables INTEGER,H29,H29_OTHERS ," +
                    " I30,I31,J32," +
                    "J32_Prawn ,J32_Hito,J32_Pangasius INTEGER,J32_Bangus ,J32_Tilapia ,J32_Others ," +
                    "K33 ,K33_Dogs,K33_Cats ,K33B ,K33C ," +
                    "L34 ,L34A_School,L34A_GovAgencies, L34A_NonGov,L34A_OTHERS," +
                    "L34B_CBDRRM ,L34B_FirstAid, L34B_BLS,L34B_Fire,L34B_Search ,L34B_OTHERS," +
                    "L35 ,L35_RADIO,L35_TV,L35_PUB,L35_SMS,L35_SIREN ,L35_INTERNET,L35_OTHER ," +
                    "M36_Roads ,M36_Drainage,M36_School ,M36_Health,M36_DayCare ,M36_WaterSystem," +
                    "M36_MultiPurpose ,M36_FloodControl,M36_Government ,M36_Bridges ,M36_StreetLights ," +
                    "M36_SolarDrier ,M36_CoveredCourt ,M36_Basketball ,M36_OTHERS ," +
                    "M37_Roads ,M37_Drainage ,M37_School ,M37_Health,M37_DayCare,M37_WaterSystem ," +
                    "M37_MultiPurpose,M37_FloodControl,M37_Government ,M37_Bridges ,M37_StreetLights ," +
                    "M37_SolarDrier ,M37_CoveredCourt ,M37_Basketball ,M37_OTHERS ," +
                    "N38 ,N38_A ,N38_B ,N38_C ,N38_D ,N38_E ,N38E_OTHER,N38_F ," +
                    "O39_Educ,O39_PO ,O39_Health,O39_Labor,O39_Economic ,O39_Transport ," +
                    "O39_House ,O39_Social ,O39_Infra ,O39_Environment,tbl_BF_Tables.BarangayCode " +
                    "FROM tbl_BF_Tables " +
                    "INNER JOIN tbl_GO_Tables ON tbl_BF_Tables.HCN=tbl_GO_Tables.HCN " +
                    "INNER JOIN tbl_RespondentNames ON tbl_GO_Tables.HCN = tbl_RespondentNames.HCN " +
                    "WHERE tbl_BF_Tables.DateEntered =%s","'"+selectDate.getText().toString()+"'");

            SQLiteDatabase db = DBConn.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount()==0)
            {
                Snackbar.make(coordinatorLayout,"Empty A-O Table",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(Main2Activity_ShowUpload.this,"Empty A-O Table",Toast.LENGTH_LONG).show();
                success = false;
                return success;
            }
            else
            {
                List<String> stringB_ONames = new ArrayList<String>();
                stringB_ONames.add("HCN");
                stringB_ONames.add("Respondent Name");
                stringB_ONames.add("Lastname");
                stringB_ONames.add("Firstname");
                stringB_ONames.add("Middlename");
                stringB_ONames.add("C1");
                stringB_ONames.add("C2");
                stringB_ONames.add("C2.a");
                stringB_ONames.add("C3");
                stringB_ONames.add("C4");
                stringB_ONames.add("C5");
                stringB_ONames.add("D6");
                stringB_ONames.add("D7");
                stringB_ONames.add("E8");
                stringB_ONames.add("E9");
                stringB_ONames.add("E10");
                stringB_ONames.add("E10_a");
                stringB_ONames.add("E11");
                stringB_ONames.add("E12");
                stringB_ONames.add("E13");
                stringB_ONames.add("E13_a");
                stringB_ONames.add("E14");
                stringB_ONames.add("F15");
                stringB_ONames.add("F15.a");
                stringB_ONames.add("F16");
                stringB_ONames.add("F17");
                stringB_ONames.add("F17.a");
                stringB_ONames.add("F17.b");
                stringB_ONames.add("F17.c");
                stringB_ONames.add("F17.d");
                stringB_ONames.add("F17.BCG");
                stringB_ONames.add("F17.PENTA1");
                stringB_ONames.add("F17.PENTA2");
                stringB_ONames.add("F17.PENTA3");
                stringB_ONames.add("F17.OPV1");
                stringB_ONames.add("F17.OPV2");
                stringB_ONames.add("F17.OPV3");
                stringB_ONames.add("F17.HEPAB1");
                stringB_ONames.add("F17.MEASLES");
                stringB_ONames.add("F17.f");
                stringB_ONames.add("F18");
                stringB_ONames.add("F19");
                stringB_ONames.add("F20");
                stringB_ONames.add("F21");
                stringB_ONames.add("F21.a");
                stringB_ONames.add("F22");
                stringB_ONames.add("F22.a");
                stringB_ONames.add("F22.b");
                stringB_ONames.add("F23");
                stringB_ONames.add("F24");

                //--------------------------------------------------------------------------------------
                stringB_ONames.add("G25");
                stringB_ONames.add("G26");
                stringB_ONames.add("H27");
                stringB_ONames.add("H28.Meat");
                stringB_ONames.add("H28.Seafoods");
                stringB_ONames.add("H28.Processed");
                stringB_ONames.add("H28.Fruits");
                stringB_ONames.add("H28.Vegetables");
                stringB_ONames.add("H29");
                stringB_ONames.add("H29 Others");
                stringB_ONames.add("I30");
                stringB_ONames.add("I31");
                stringB_ONames.add("J32");
                stringB_ONames.add("J32.Prawn");
                stringB_ONames.add("J32.Hito");
                stringB_ONames.add("J32.Pangasius");
                stringB_ONames.add("J32.Bangus");
                stringB_ONames.add("J32.Tilapia");
                stringB_ONames.add("J32.Others");
                stringB_ONames.add("K33");
                stringB_ONames.add("K33.Dogs");
                stringB_ONames.add("K33.Cats");
                stringB_ONames.add("K33.B");
                stringB_ONames.add("K33.C");
                stringB_ONames.add("L34");
                stringB_ONames.add("L34.A_School");
                stringB_ONames.add("L34.A_GovAgencies");
                stringB_ONames.add("L34.A_NonGov");
                stringB_ONames.add("L34.A OTHERS");
                stringB_ONames.add("L34.B_CBDRRM");
                stringB_ONames.add("L34.B_FirstAid");
                stringB_ONames.add("L34.B_BLS");
                stringB_ONames.add("L34.B_Fire");
                stringB_ONames.add("L34.B_Search");
                stringB_ONames.add("L34.B OTHERS");
                stringB_ONames.add("L35");
                stringB_ONames.add("L35 Radio");
                stringB_ONames.add("L35 TV");
                stringB_ONames.add("L35 Pub");
                stringB_ONames.add("L35 SMS");
                stringB_ONames.add("L35 Siren");
                stringB_ONames.add("L35 Internet");
                stringB_ONames.add("L35 OTHERS");
                stringB_ONames.add("M36 Roads");
                stringB_ONames.add("M36 Drainage");
                stringB_ONames.add("M36 School");
                stringB_ONames.add("M36 Health");
                stringB_ONames.add("M36 DayCare");
                stringB_ONames.add("M36 WaterSystem");
                stringB_ONames.add("M36 MultiPurpose");
                stringB_ONames.add("M36 Flood Control");
                stringB_ONames.add("M36 Government");
                stringB_ONames.add("M36 Bridges");
                stringB_ONames.add("M36 Street Lights");
                stringB_ONames.add("M36 Solar Drier");
                stringB_ONames.add("M36 Covered Court");
                stringB_ONames.add("M36 Basketball");
                stringB_ONames.add("M36 OTHERS");
                stringB_ONames.add("M37 Roads");
                stringB_ONames.add("M37 Drainage");
                stringB_ONames.add("M37 School");
                stringB_ONames.add("M37 Health");
                stringB_ONames.add("M37 DayCare");
                stringB_ONames.add("M37 WaterSystem");
                stringB_ONames.add("M37 MultiPurpose");
                stringB_ONames.add("M37 Flood Control");
                stringB_ONames.add("M37 Government");
                stringB_ONames.add("M37 Bridges");
                stringB_ONames.add("M37 Street Lights");
                stringB_ONames.add("M37 Solar Drier");
                stringB_ONames.add("M37 Covered Court");
                stringB_ONames.add("M37 Basketball");
                stringB_ONames.add("M37 OTHERS");
                stringB_ONames.add("N38");
                stringB_ONames.add("N38.A");
                stringB_ONames.add("N38.B");
                stringB_ONames.add("N38.C");
                stringB_ONames.add("N38.D");
                stringB_ONames.add("N38.E");
                stringB_ONames.add("N38.E OTHERS");
                stringB_ONames.add("N38.F");
                stringB_ONames.add("O39.Education");
                stringB_ONames.add("O39.Peace and Order");
                stringB_ONames.add("O39.Health Services");
                stringB_ONames.add("O39.Labor and Employment");
                stringB_ONames.add("O39.Economic Services/Development");
                stringB_ONames.add("O39.Transportation");
                stringB_ONames.add("O39.Housing and Community Development");
                stringB_ONames.add("O39.Social Welfare Services");
                stringB_ONames.add("O39.Infrastructure");
                stringB_ONames.add("O39.Environment");
                stringB_ONames.add("BarangayCode");

                //New Sheet
                Sheet sheetBF = null;
                sheetBF = wb.createSheet("A_O Questions");

                int cellMeasurement = (15*300);

                // Generate column headings
                Row rowBF;
                rowBF= sheetBF.createRow(0);

                //GENERATE COLUMNS
                for ( int i = 0; i < stringB_ONames.size(); i++ )
                {
                    cell = rowBF.createCell(i);
                    cell.setCellValue(String.valueOf(stringB_ONames.get(i)));
                    sheetBF.setColumnWidth(i, (cellMeasurement));
                }

                //LOAD THE DATAS HERE
                while ( cursor.moveToNext() )
                {
                    // Generate column headings
                    rowBF = sheetBF.createRow(totalRow);
                    for ( int i =0; i < stringB_ONames.size(); i++ )
                    {
                        cell = rowBF.createCell(i);
                        cell.setCellValue(cursor.getString(i));
                        sheetBF.setColumnWidth(i, (cellMeasurement));
                    }
                    totalRow = totalRow +1;
                }
                cursor.close();
                db.close();
            }

            success = true;
            return success;
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Table_A-O_Excel:"+ e.toString());
            //Toast.makeText(Main2Activity_ShowUpload.this,"HCN  not found in one of the tables!",Toast.LENGTH_LONG).show();
            Snackbar.make(coordinatorLayout,"HCN  not found in one of the tables!",Snackbar.LENGTH_LONG).show();
            success = false;
            return success;
        }

    }


    public boolean createRow_BFQuestion_Sheet2(Workbook wb,Cell cell)
    {
        boolean success;
        //LOAD THE DATAS HERE
        try
        {

            int totalRow = 1;

            String selectQuery;
            selectQuery =String.format("SELECT tbl_BF_Tables.HCN,Lastname,Firstname,Middlename,C1,C2,C2_a,C3,C4,C5," +
                    "D6,D7,E8,E9,E10,E10_a,E11,E12,E13,E13_a,E14," +
                    "F15,F15_a,F16,F17,F17_a,F17_b,F17_c,F17_d,F17_BCG,F17_Penta1,F17_Penta2,F17_Penta3," +
                    "F17_OPV1,F17_OPV2,F17_OPV3,F17_HEPAB1,F17_MEASLES,F17_f," +
                    "F18,F19,F20,F21,F21_a,F22,F22_a,F22_b,F23,F24 " +
                    "FROM " + DBConn.getTableName_BFTable()+" WHERE DateEntered=%s","'"+selectDate.getText().toString()+"'");

            SQLiteDatabase db = DBConn.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount()==0)
            {
                Snackbar.make(coordinatorLayout,"Empty B-F Table",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(Main2Activity_ShowUpload.this,"Empty B-F Table",Toast.LENGTH_LONG).show();
                success = false;
                return success;
            }
            else
            {
                List<String> stringBFNames = new ArrayList<String>();
                stringBFNames.add("HCN");
                stringBFNames.add("Lastname");
                stringBFNames.add("Firstname");
                stringBFNames.add("Middlename");
                stringBFNames.add("C1");
                stringBFNames.add("C2");
                stringBFNames.add("C2.a");
                stringBFNames.add("C3");
                stringBFNames.add("C4");
                stringBFNames.add("C5");
                stringBFNames.add("D6");
                stringBFNames.add("D7");
                stringBFNames.add("E8");
                stringBFNames.add("E9");
                stringBFNames.add("E10");
                stringBFNames.add("E10_a");
                stringBFNames.add("E11");
                stringBFNames.add("E12");
                stringBFNames.add("E13");
                stringBFNames.add("E13_a");
                stringBFNames.add("E14");
                stringBFNames.add("F15");
                stringBFNames.add("F15.a");
                stringBFNames.add("F16");
                stringBFNames.add("F17");
                stringBFNames.add("F17.a");
                stringBFNames.add("F17.b");
                stringBFNames.add("F17.c");
                stringBFNames.add("F17.d");
                stringBFNames.add("F17.BCG");
                stringBFNames.add("F17.PENTA1");
                stringBFNames.add("F17.PENTA2");
                stringBFNames.add("F17.PENTA3");
                stringBFNames.add("F17.OPV1");
                stringBFNames.add("F17.OPV2");
                stringBFNames.add("F17.OPV3");
                stringBFNames.add("F17.HEPAB1");
                stringBFNames.add("F17.MEASLES");
                stringBFNames.add("F17.f");
                stringBFNames.add("F18");
                stringBFNames.add("F19");
                stringBFNames.add("F20");
                stringBFNames.add("F21");
                stringBFNames.add("F21.a");
                stringBFNames.add("F22");
                stringBFNames.add("F22.a");
                stringBFNames.add("F22.b");
                stringBFNames.add("F23");
                stringBFNames.add("F24");

                //New Sheet
                Sheet sheetBF = null;
                sheetBF = wb.createSheet("BF_Questions");

                int cellMeasurement = (15*300);

                // Generate column headings
                Row rowBF;
                rowBF = sheetBF.createRow(0);
                //GENERATE COLUMNS
                for ( int i = 0; i < stringBFNames.size(); i++ )
                {

                    cell = rowBF.createCell(i);
                    cell.setCellValue(String.valueOf(stringBFNames.get(i)));
                    sheetBF.setColumnWidth(i, (cellMeasurement));
                }

                //FILL THE COLUMNS
                while ( cursor.moveToNext() )
                {
                    // Generate column headings
                    rowBF = sheetBF.createRow(totalRow);
                    for ( int i =0; i < stringBFNames.size(); i++ )
                    {
                        cell = rowBF.createCell(i);
                        cell.setCellValue(cursor.getString(i));
                        sheetBF.setColumnWidth(i, (cellMeasurement));
                    }
                    totalRow = totalRow +1;
                }
                cursor.close();
                db.close();

                success = true;
                return success;
            }


        }
        catch ( Exception e )
        {
            Log.i(TAG,"TableBFExcel:"+ e.toString());
            Snackbar.make(coordinatorLayout,"Error in creating BF-sheet",Snackbar.LENGTH_LONG).show();
            //Toast.makeText(Main2Activity_ShowUpload.this,"Error in creating BF-sheet",Toast.LENGTH_LONG).show();
            success = false;
            return success;
        }

    }

    public boolean createRow_GOQuestion_Sheet3(Workbook wb,Cell cell)
    {
        boolean success;
        //LOAD THE DATAS HERE
        try
        {

            int totalRow = 1;

            String selectQuery;
            selectQuery =
                    String.format("SELECT  HCN ,DateEntered ,G25,G26 ,H27," +
                            "H28Meat ,H28Seafoods INTEGER,H28Processed," +
                            "H28Fruits,H28Vegetables INTEGER,H29,H29_OTHERS ," +
                            "I30,I31,J32," +
                            "J32_Prawn ,J32_Hito,J32_Pangasius INTEGER,J32_Bangus ,J32_Tilapia ,J32_Others ," +
                            "K33 ,K33_Dogs,K33_Cats ,K33B ,K33C ," +
                            "L34 ,L34A_School,L34A_GovAgencies, L34A_NonGov,L34A_OTHERS," +
                            "L34B_CBDRRM ,L34B_FirstAid, L34B_BLS,L34B_Fire,L34B_Search ,L34B_OTHERS," +
                            "L35 ,L35_RADIO,L35_TV,L35_PUB,L35_SMS,L35_SIREN ,L35_INTERNET,L35_OTHER ," +
                            "M36_Roads ,M36_Drainage,M36_School ,M36_Health,M36_DayCare ,M36_WaterSystem," +
                            "M36_MultiPurpose ,M36_FloodControl,M36_Government ,M36_Bridges ,M36_StreetLights ," +
                            "M36_SolarDrier ,M36_CoveredCourt ,M36_Basketball ,M36_OTHERS ," +
                            "M37_Roads ,M37_Drainage ,M37_School ,M37_Health,M37_DayCare,M37_WaterSystem ," +
                            "M37_MultiPurpose,M37_FloodControl,M37_Government ,M37_Bridges ,M37_StreetLights ," +
                            "M37_SolarDrier ,M37_CoveredCourt ,M37_Basketball ,M37_OTHERS ," +
                            "N38 ,N38_A ,N38_B ,N38_C ,N38_D ,N38_E ,N38E_OTHER,N38_F ," +
                            "O39_Educ,O39_PO ,O39_Health,O39_Labor,O39_Economic ,O39_Transport ," +
                            "O39_House ,O39_Social ,O39_Infra ,O39_Environment ,BarangayCode,BarangayCode " +
                            " FROM " + DBConn.getTableName_GOTable()+" WHERE DateEntered=%s","'"+selectDate.getText().toString()+"'");

            SQLiteDatabase db = DBConn.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount()==0)
            {
                Snackbar.make(coordinatorLayout,"Empty G-O Table",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(Main2Activity_ShowUpload.this,"Empty G-O Table",Toast.LENGTH_LONG).show();
                success = false;
                return success;
            }
            else
            {
                List<String> stringGONames = new ArrayList<String>();
                stringGONames.add("HCN");
                stringGONames.add("Date Entered");
                stringGONames.add("G25");
                stringGONames.add("G26");
                stringGONames.add("H27");
                stringGONames.add("H28.Meat");
                stringGONames.add("H28.Seafoods");
                stringGONames.add("H28.Processed");
                stringGONames.add("H28.Fruits");
                stringGONames.add("H28.Vegetables");
                stringGONames.add("H29");
                stringGONames.add("H29 Others");
                stringGONames.add("I30");
                stringGONames.add("I31");
                stringGONames.add("J32");
                stringGONames.add("J32.Prawn");
                stringGONames.add("J32.Hito");
                stringGONames.add("J32.Pangasius");
                stringGONames.add("J32.Bangus");
                stringGONames.add("J32.Tilapia");
                stringGONames.add("J32.Others");
                stringGONames.add("K33");
                stringGONames.add("K33.Dogs");
                stringGONames.add("K33.Cats");
                stringGONames.add("K33.B");
                stringGONames.add("K33.C");
                stringGONames.add("L34");
                stringGONames.add("L34.A_School");
                stringGONames.add("L34.A_GovAgencies");
                stringGONames.add("L34.A_NonGov");
                stringGONames.add("L34.A OTHERS");
                stringGONames.add("L34.B_CBDRRM");
                stringGONames.add("L34.B_FirstAid");
                stringGONames.add("L34.B_BLS");
                stringGONames.add("L34.B_Fire");
                stringGONames.add("L34.B_Search");
                stringGONames.add("L34.B OTHERS");
                stringGONames.add("L35");
                stringGONames.add("L35 Radio");
                stringGONames.add("L35 TV");
                stringGONames.add("L35 Pub");
                stringGONames.add("L35 SMS");
                stringGONames.add("L35 Siren");
                stringGONames.add("L35 Internet");
                stringGONames.add("L35 OTHERS");
                stringGONames.add("M36 Roads");
                stringGONames.add("M36 Drainage");
                stringGONames.add("M36 School");
                stringGONames.add("M36 Health");
                stringGONames.add("M36 DayCare");
                stringGONames.add("M36 WaterSystem");
                stringGONames.add("M36 MultiPurpose");
                stringGONames.add("M36 Flood Control");
                stringGONames.add("M36 Government");
                stringGONames.add("M36 Bridges");
                stringGONames.add("M36 Street Lights");
                stringGONames.add("M36 Solar Drier");
                stringGONames.add("M36 Covered Court");
                stringGONames.add("M36 Basketball");
                stringGONames.add("M36 OTHERS");
                stringGONames.add("M37 Roads");
                stringGONames.add("M37 Drainage");
                stringGONames.add("M37 School");
                stringGONames.add("M37 Health");
                stringGONames.add("M37 DayCare");
                stringGONames.add("M37 WaterSystem");
                stringGONames.add("M37 MultiPurpose");
                stringGONames.add("M37 Flood Control");
                stringGONames.add("M37 Government");
                stringGONames.add("M37 Bridges");
                stringGONames.add("M37 Street Lights");
                stringGONames.add("M37 Solar Drier");
                stringGONames.add("M37 Covered Court");
                stringGONames.add("M37 Basketball");
                stringGONames.add("M37 OTHERS");
                stringGONames.add("N38");
                stringGONames.add("N38.A");
                stringGONames.add("N38.B");
                stringGONames.add("N38.C");
                stringGONames.add("N38.D");
                stringGONames.add("N38.E");
                stringGONames.add("N38.E OTHERS");
                stringGONames.add("N38.F");
                stringGONames.add("O39.Education");
                stringGONames.add("O39.Peace and Order");
                stringGONames.add("O39.Health Services");
                stringGONames.add("O39.Labor and Employment");
                stringGONames.add("O39.Economic Services/Development");
                stringGONames.add("O39.Transportation");
                stringGONames.add("O39.Housing and Community Development");
                stringGONames.add("O39.Social Welfare Services");
                stringGONames.add("O39.Infrastructure");
                stringGONames.add("O39.Environment");

                //New Sheet
                Sheet sheetGO = null;
                sheetGO = wb.createSheet("GO_Questions");

                int cellMeasurement = (15*300);

                // Generate column headings
                Row rowGO;
                rowGO = sheetGO.createRow(0);
                //GENERATE COLUMNS
                for ( int i = 0; i < stringGONames.size(); i++ )
                {

                    cell = rowGO.createCell(i);
                    cell.setCellValue(String.valueOf(stringGONames.get(i)));
                    sheetGO.setColumnWidth(i, (cellMeasurement));
                }

                //FILL THE COLUMNS
                while ( cursor.moveToNext() )
                {
                    // Generate column headings
                    rowGO = sheetGO.createRow(totalRow);
                    for ( int i =0; i < stringGONames.size(); i++ )
                    {
                        cell = rowGO.createCell(i);
                        cell.setCellValue(cursor.getString(i));
                        sheetGO.setColumnWidth(i, (cellMeasurement));
                    }
                    totalRow = totalRow +1;
                }
                cursor.close();
                db.close();

                success = true;
                return success;
            }


        }
        catch ( Exception e )
        {
            Log.i(TAG,"TableGOExcel:"+ e.toString());
            Snackbar.make(coordinatorLayout,"Error in creating G-O sheet",Snackbar.LENGTH_LONG).show();
            //Toast.makeText(Main2Activity_ShowUpload.this,"Error in creating G-O sheet",Toast.LENGTH_LONG).show();
            success = false;
            return success;
        }

    }


    private boolean createSheet4_RespondentNames (Workbook wb, Cell cell)
    {
        boolean success;

        //try {
            //READ THE DATA HERE
            int totalRow = 1;

            String selectQuery;
            selectQuery = String.format("SELECT HCN, RespondentName " +
                    " FROM " + DBConn.getTableName_RespondentNames() + " WHERE DateEntered=%s", "'" + selectDate.getText().toString() + "'");

            SQLiteDatabase db = DBConn.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            //CONDITIONS FOLLOWS
            if ( cursor.getCount() == 0 ) {
                Snackbar.make(coordinatorLayout, "Empty Respondent Table", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(Main2Activity_ShowUpload.this,"Empty Respondent Table",Toast.LENGTH_LONG).show();
                success = false;
                return success;
            }
            else
            {
                //ArrayList for flexible handling
                List<String> stringRespondentNames = new ArrayList<String>();
                stringRespondentNames.add("HCN");
                stringRespondentNames.add("Respondent Name");

                //New Sheet
                Sheet sheetRespondent = null;
                sheetRespondent = wb.createSheet("Respondent Names");

                int cellMeasurement = (15 * 300);

                // Generate column headings
                Row rowRespondent = sheetRespondent.createRow(0);
                //GENERATE COLUMNS
                for ( int i = 0; i < stringRespondentNames.size(); i++ ) {

                    cell = rowRespondent.createCell(i);
                    cell.setCellValue(String.valueOf(stringRespondentNames.get(i)));
                    sheetRespondent.setColumnWidth(i, (cellMeasurement));
                }


                //LOAD THE DATAS HERE
                while ( cursor.moveToNext() ) {
                    // Generate column headings
                    rowRespondent = sheetRespondent.createRow(totalRow);
                    for ( int i = 0; i < 2; i++ ) {
                        cell = rowRespondent.createCell(i);
                        cell.setCellValue(cursor.getString(i));
                        sheetRespondent.setColumnWidth(i, (cellMeasurement));
                    }
                    totalRow = totalRow + 1;
                }
                cursor.close();
                db.close();

                success = true;
                return success;
            }

        }
        /**
        catch ( Exception e )
        {
            Log.i(TAG, "RespondentExcel:" + e.toString());
            Snackbar.make(coordinatorLayout,"Error in creatiing Respondent sheet",Snackbar.LENGTH_LONG).show();
            success = false;
            return success;
        }

         }
         **/




    private boolean createSheet5_TransactionLog (Workbook wb, Cell cell)
    {
        boolean success;

        //try
        //{
            int totalRow = 1;

            String selectQuery;
            selectQuery = String.format("SELECT Date,Time,TransactionLog " +
                    " FROM " + DBConn.getTableName_TransactionLog()+" WHERE Date=%s","'"+selectDate.getText().toString()+"'");

            SQLiteDatabase db = DBConn.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount()==0)
            {
                Snackbar.make(coordinatorLayout, "Empty Transaction Log", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(Main2Activity_ShowUpload.this,"Empty Transaction Log",Toast.LENGTH_LONG).show();
                success = false;
                return success;
            }
            else
            {
                List<String> stringTransactionNames = new ArrayList<String>();
                stringTransactionNames.add("Date");
                stringTransactionNames.add("Time");
                stringTransactionNames.add("Transaction Log");

                //New Sheet
                Sheet sheetTransaction = null;
                sheetTransaction = wb.createSheet("Transaction Log");

                int cellMeasurement = (15 * 300);

                // Generate column headings
                Row rowTransaction = sheetTransaction.createRow(0);
                //GENERATE COLUMNS
                for ( int i = 0; i < stringTransactionNames.size(); i++ ) {

                    cell = rowTransaction.createCell(i);
                    cell.setCellValue(String.valueOf(stringTransactionNames.get(i)));
                    sheetTransaction.setColumnWidth(i, (cellMeasurement));
                }


                //LOAD THE DATAS HERE
                while ( cursor.moveToNext() )
                {
                    // Generate column headings
                    rowTransaction = sheetTransaction.createRow(totalRow);
                    for ( int i =0; i < 3; i++ )
                    {
                        cell = rowTransaction.createCell(i);
                        cell.setCellValue(cursor.getString(i));
                        sheetTransaction.setColumnWidth(i, (cellMeasurement));
                    }
                    totalRow = totalRow +1;
                }
                cursor.close();
                db.close();
                success = true;
                return success;
            }

        }
        /*
        catch ( Exception e )
        {
            Log.i(TAG,"TransactionExcel:"+ e.toString());
            Snackbar.make(coordinatorLayout,"Error in creating Transaction sheet",Snackbar.LENGTH_LONG).show();
            success = false;
            return success;
        }

    }*/

    private  boolean saveExcelFile(final Context context, final String fileName) {


        final DatabaseReference mref = database.getReference("CityCenRiderVersion");

        final boolean[] success = {false};

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        //GET THE LATEST CITYCENRIDER VERSION
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                mySettings.setRiderVersion_Available(Main2Activity_ShowUpload.this,String.valueOf(dataSnapshot.getValue()));
                if ( BuildConfig.VERSION_NAME.equals(mySettings.getCurrentRIDER_Version_Avail(Main2Activity_ShowUpload.this)) == true )
                {

                    //New Workbook
                    Workbook workbook = new HSSFWorkbook();

                    Cell cell = null;

                    if (DBConn.check_Entry_BY_DATE(selectDate.getText().toString()) == true)
                    {
                        //CREATE EXCEL WORKSHEET HERE
                        if ( createRow_BFQuestion_Sheet2(workbook,cell)==true && createRow_GOQuestion_Sheet3(workbook,cell)==true)
                        {
                            createRow_A_O_Question_Sheet1(workbook,cell);
                            createSheet4_RespondentNames(workbook,cell);
                            createSheet5_TransactionLog(workbook,cell);

                            // Create a path where we will place our List of objects on external storage
                            File file = new File(context.getExternalFilesDir(null), fileName);
                            FileOutputStream os = null;
                            try {
                                os = new FileOutputStream(file);
                                workbook.write(os);
                                Log.w(TAG, "Writing file" + file);
                                success[ 0 ] = true;

                                // UPLOAD IT NOW
                                signIn();

                            } catch (IOException e) {
                                Log.w(TAG, "Error writing " + file, e);
                            } catch (Exception e) {
                                Log.w(TAG, "Failed to save file", e);
                            } finally {
                                try {
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                    else
                    {
                        Snackbar.make(coordinatorLayout,"No data for this date",Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(Main2Activity_ShowUpload.this,"No data for this date",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Snackbar.make(coordinatorLayout,"CityCen2018 Rider V."+  mySettings.getCurrentRIDER_Version_Avail(Main2Activity_ShowUpload.this)+" is available, Please update your  CityCenRider App",Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(Main2Activity_ShowUpload.this,"CityCen2018 Rider V."+  mySettings.getCurrentRIDER_Version_Avail(Main2Activity_ShowUpload.this)+" is available, Please update your  CityCenRider App",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {

            }
        });



        return success[ 0 ];
    }


    // THE FOLLOWING METHODS ARE USE FOR UPLOADING TO GOOGLE DRIVE
    /** Start sign in activity. */
    private void signIn()
    {
        Log.i(TAG, "Start sign in");
        mGoogleSignInClient = buildGoogleSignInClient();
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }
    /** Build a Google SignIn client. */
    private GoogleSignInClient buildGoogleSignInClient()
    {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }
    /** Create a new file and save it to Drive. */
    private void saveFileToDrive()
    {
        // Start by creating a new contents, and setting a callback.
        Log.i(TAG, "Creating new contents.");
        mDriveResourceClient
                .createContents()
                .continueWithTask(
                        new Continuation<DriveContents, Task<Void>>() {
                            @Override
                            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                                return createFileIntentSender(task.getResult(), ExcelFolderName+"_file.xls" );

                            }
                        })


                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Failed to create new excel contents.", e);
                            }
                        });

    }

    /** Creates an {@link IntentSender} to start a dialog activity with configured */
    private Task<Void> createFileIntentSender(DriveContents driveContents,String fileName)
            throws IOException
    {
        Log.i(TAG, "New contents created.");
        // Get an output stream for the contents.
        OutputStream outputStream = driveContents.getOutputStream();

        //LOCATION OF EXCEL FILE HERE
        mFileToSave =  new File(Main2Activity_ShowUpload.this.getExternalFilesDir(null), fileName);//>>>>>> WHAT FILE ?

        FileInputStream fileInputStream = new FileInputStream(mFileToSave);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Create the initial metadata - MIME type and title.
        // FOR THIS USE - application/vnd.ms-excel -
        // Note that the user will be able to change the title later.
        MetadataChangeSet metadataChangeSet =
                new MetadataChangeSet.Builder()
                        .setMimeType("application/vnd.ms-excel")
                        .setTitle(fileName)
                        .build();
        // Set up options to configure and display the create file activity.
        CreateFileActivityOptions createFileActivityOptions =
                new CreateFileActivityOptions.Builder()
                        .setInitialMetadata(metadataChangeSet)
                        .setInitialDriveContents(driveContents)
                        .build();

        Snackbar.make(coordinatorLayout,"Success",Snackbar.LENGTH_LONG).show();
        //Toast.makeText(Main2Activity_ShowUpload.this,"Success",Toast.LENGTH_LONG).show();

        return mDriveClient
                .newCreateFileActivityIntentSender(createFileActivityOptions)
                .continueWith(
                        new Continuation<IntentSender, Void>() {
                            @Override
                            public Void then(@NonNull Task<IntentSender> task) throws Exception {
                                startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0);
                                return null;
                            }
                        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                Log.i(TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Signed in successfully.");

                    // Use the last signed in account here since it already have a Drive scope.
                    mDriveClient = Drive.getDriveClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    Snackbar.make(coordinatorLayout,"Sign-in successful",Snackbar.LENGTH_LONG).show();
                    // Toast.makeText(Main2Activity_ShowUpload.this,"Sign-in successful",Toast.LENGTH_LONG).show();
                    // Build a drive resource client.
                    mDriveResourceClient =
                            Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));
                    mFileToSave = (File) data.getExtras().get("data");
                    saveFileToDrive();

                }
                else
                {
                    Snackbar.make(coordinatorLayout,"Connecting to server failed! Data saved on this device",Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(Main2Activity_ShowUpload.this,"Connecting to server failed! Data saved on this device",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        onBackPressed();
        return true;
    }

}
