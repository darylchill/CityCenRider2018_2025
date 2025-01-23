package darylgorra.citycenrider2018;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import darylgorra.citycenrider2018.Classes.MySettings;
import darylgorra.citycenrider2018.Classes.databaseHelper;


public class MainLoginActivity extends AppCompatActivity {

    private TextView txtView_BarangayCode,txtView_InterviewerCode;
    private AutoCompleteTextView txtboxLogin_InterviewerName;
    private EditText txtboxLoginBarangay,  txtboxLogin_FieldSupervisor;
    private RadioButton radioButton_ListInterviewer;
    private MySettings mySettings = new MySettings();
    private databaseHelper DBConn = new databaseHelper(MainLoginActivity.this);
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        getSupportActionBar().setTitle("CityCenRider - Login");

        initViewControls();


        Tasks.executeInBackground(MainLoginActivity.this, new BackgroundWork<Object>() {
            @Override
            public Object doInBackground() throws Exception {

                if (!mySettings.getLogin_LoadDatabase(MainLoginActivity.this))
                {
                    DBConn.dropThisTables();
                    DBConn.insertInterviewerProfile();
                    DBConn.insert_Supervisor_Profile();
                    mySettings.setLogin_DatabaseCheck(MainLoginActivity.this,true);
                }
                else
                {
                    //NOTHING
                }


                return null;
            }
        }, new Completion<Object>() {
            @Override
            public void onSuccess(Context context, Object result) {
                Toast.makeText(MainLoginActivity.this,"Loading Database Complete",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Context context, Exception e) {
                Toast.makeText(MainLoginActivity.this,"Loading Database Failed",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initViewControls()
    {
        txtboxLoginBarangay = findViewById(R.id.txtBoxSettings_Barangay);
        txtboxLoginBarangay.setInputType(InputType.TYPE_NULL);
        txtView_BarangayCode = findViewById(R.id.txtViewLogin_BarangayCode);
        txtView_InterviewerCode = findViewById(R.id.txtViewLogin_InterviewerCode);

        txtboxLoginBarangay.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getAction() == motionEvent.ACTION_UP)
            {
                callCustomDialogBarangayCode();
                return true;
            }
            return false;
        });

        txtboxLogin_InterviewerName = (AutoCompleteTextView) findViewById(R.id.txtBoxSettings_InterviewerName);
        //txtboxLogin_InterviewerName.setEnabled(false);

        txtboxLogin_FieldSupervisor = (EditText) findViewById(R.id.txtBoxSettings_SupervisorName);
        txtboxLogin_FieldSupervisor.setInputType(InputType.TYPE_NULL);
        txtboxLogin_FieldSupervisor.setEnabled(false);


        txtboxLoginBarangay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (txtboxLoginBarangay.getText().toString().trim().equals(""))
                {
                  //  txtboxLogin_InterviewerName.setEnabled(false);
                }
                else
                {
                  //  txtboxLogin_InterviewerName.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });

        /**
        txtboxLogin_InterviewerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (txtboxLogin_InterviewerName.getText().toString().trim().equals(""))
                {
                    txtboxLogin_FieldSupervisor.setEnabled(false);
                }
                else
                {
                    txtboxLogin_FieldSupervisor.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });
**/

        Button btnNext = (Button) findViewById(R.id.btnNextLogin);

        /*
        txtboxLogin_InterviewerName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_UP)
                {
                  // callCustomDialogFieldInterviewersCode();

                    return true;
                }
                return false;
            }
        });
        */





        btnNext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_UP)
                {
                    initLogin();
                    return true;
                }
                return false;
            }
        });
    }


    private void load_to_Login_Interviewer_Adapter()
    {
        try
        {
            final String[] InterviewerNames = DBConn.getAll_Interviewer_Barangay(txtView_BarangayCode.getText().toString());

            final ArrayAdapter<String> adapterInterviewer;
            adapterInterviewer = new ArrayAdapter<String>(MainLoginActivity.this, android.R.layout.simple_list_item_1,InterviewerNames);
            txtboxLogin_InterviewerName.setAdapter(adapterInterviewer);
            txtboxLogin_InterviewerName.setThreshold(1);

            txtboxLogin_InterviewerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    txtboxLogin_InterviewerName.setText((String.valueOf(adapterInterviewer.getItem(i))));
                    txtView_InterviewerCode.setText(DBConn.getInterviewer_ID(txtboxLogin_InterviewerName.getText().toString()));
                    //GET THE ASSIGN PASS FOR THE FI
                    final DatabaseReference mref = database.getReference("Interviewer_Profile").child(DBConn.getInterviewer_ID(txtboxLogin_InterviewerName.getText().toString()));
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mySettings.setCurrent_FS_Pass(MainLoginActivity.this,String.valueOf(dataSnapshot.getValue()));
                            Toast.makeText(MainLoginActivity.this,"New Supervisor Password saved!",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    final DatabaseReference mref1 = database.getReference("ATT_Pass");

                    mref1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mySettings.setMasterPass(MainLoginActivity.this,String.valueOf(dataSnapshot.getValue()));
                            Toast.makeText(MainLoginActivity.this,"New ATT Password saved",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    txtboxLogin_FieldSupervisor.setText(DBConn.getInterviewer_SupervisorName(String.valueOf( txtView_InterviewerCode.getText())));
                }
            });
        }
        catch ( Exception ex )
        {
            Toast.makeText(MainLoginActivity.this,"No Data!",Toast.LENGTH_SHORT).show();
        }

        }



    private void callCustomDialogFieldInterviewersCode()
    {
        Thread callDialog_InterviewerCode = new Thread(new Runnable() {
            @Override
            public void run () {
                MainLoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run () {
                       /* try
                        {*/
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainLoginActivity.this);
                            final View alertView = getLayoutInflater().inflate(R.layout.dialog_list_of_interviewer, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            final RadioGroup rdGroup_dialog_Interviewers= (RadioGroup) alertView.findViewById(R.id.rdGroup_dialog_ListofInterviewers);

                            //STRING QUERY HERE
                            String selectQuery =
                                    String.format("SELECT DISTINCT "+DBConn.getTableName_InterviewerProfile()+".ID,"
                                            +DBConn.getTableName_InterviewerProfile()+".Name FROM "
                                            +DBConn.getTableName_InterviewerProfile() +" WHERE "
                                            +DBConn.getTableName_InterviewerProfile()
                                            +".BarangayCode=%s","'"+String.valueOf(txtView_BarangayCode.getText())+"'");
                            SQLiteDatabase db = DBConn.getReadableDatabase();
                            Cursor cursor = db.rawQuery(selectQuery, null);

                            while (cursor.moveToNext() ) {

                                for (int i = 0; i < 1; i++ )
                                {
                                    // create a new RadioButtons
                                    radioButton_ListInterviewer = new RadioButton(MainLoginActivity.this);
                                    radioButton_ListInterviewer.setId(View.generateViewId());
                                    radioButton_ListInterviewer.setGravity(Gravity.CENTER);
                                    radioButton_ListInterviewer.setTextSize(18);

                                    radioButton_ListInterviewer.setPadding(10, 10, 10, 10);
                                    radioButton_ListInterviewer.setHint(cursor.getString(0));
                                    radioButton_ListInterviewer.setText(cursor.getString(1));

                                    rdGroup_dialog_Interviewers.addView(radioButton_ListInterviewer);
                                }

                            }

                            cursor.close();

                            rdGroup_dialog_Interviewers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                                    RadioButton radioButton;
                                    radioButton = (RadioButton) alertView.findViewById(i);
                                    txtboxLogin_InterviewerName.setText((String.valueOf(radioButton.getText())));
                                    txtboxLogin_FieldSupervisor.setText(DBConn.getInterviewer_SupervisorName(String.valueOf(radioButton.getHint())));
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        /*
                        catch ( Exception e )
                        {
                           Toast.makeText(MainLoginActivity.this,"Error Message:" + e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }*/
                });
            }
        });
        callDialog_InterviewerCode.start();
    }

    private void callCustomDialogBarangayCode()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainLoginActivity.this);
        final View alertView = getLayoutInflater().inflate(R.layout.dialog_barangay_choices, null);
        mBuilder.setView(alertView);
        final AlertDialog dialog = mBuilder.create();

        final RadioGroup rdGroup_dialog_Barangays = (RadioGroup) alertView.findViewById(R.id.rdGroup_dialog_Barangays);

        rdGroup_dialog_Barangays.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) alertView.findViewById(rdGroup_dialog_Barangays.getCheckedRadioButtonId());
                txtboxLoginBarangay.setText(String.valueOf(radioButton.getText()));
                txtView_BarangayCode.setText(String.valueOf(radioButton.getHint()));
                dialog.dismiss();
                txtboxLogin_InterviewerName.setText("");
                txtboxLogin_FieldSupervisor.setText("");
                load_to_Login_Interviewer_Adapter();
            }
        });

        dialog.show();
    }

    private void initLogin()
    {
        if (txtboxLoginBarangay.length()==0 || txtboxLogin_InterviewerName.length()==0 )
        {
            Toast.makeText(MainLoginActivity.this,"Please provide the information above",Toast.LENGTH_SHORT).show();
        }
        else if (txtboxLoginBarangay.length()>0 && txtboxLogin_InterviewerName.length()>0) {
            mySettings.setLoginCheck(MainLoginActivity.this, true);
            mySettings.setBarangayCode(MainLoginActivity.this, txtView_BarangayCode.getText().toString());
            mySettings.setInterviewer(MainLoginActivity.this, txtboxLogin_InterviewerName.getText().toString());
          //  mySettings.setSupervisorID(MainLoginActivity.this,DBConn.getInterviewer_SupervisorID(txtboxLogin_FieldSupervisor.getText().toString()));

            setUpThread_DatabaseHere();

            Intent mainActivity = new Intent(MainLoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }

    private void setUpThread_DatabaseHere()
    {
        Tasks.executeInBackground(MainLoginActivity.this, new BackgroundWork<Object>() {
            @Override
            public Object doInBackground() throws Exception {
                DBConn.insertBarangayCodes();
                DBConn.insertSchoolCodes();
                DBConn.insertCooperationCodes();
                DBConn.insertTransactionHere(mySettings.getCurrentDate(),mySettings.getCurrentTime(),
                        mySettings.getInterviewer(MainLoginActivity.this)+" " +
                                "activated CityCenRider and deployed at " +
                                mySettings.getBarangayCode(MainLoginActivity.this));
                return null;
            }
        }, new Completion<Object>() {
            @Override
            public void onSuccess(Context context, Object result) {
                Toast.makeText(MainLoginActivity.this,"Loading Database Complete",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Context context, Exception e) {
                Toast.makeText(MainLoginActivity.this,"Loading Database Failed",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onBackPressed()
    {
        if ( mySettings.getLoginCheck(MainLoginActivity.this) == false ) {
            AlertDialog.Builder  builderDialog ;
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                builderDialog = new AlertDialog.Builder(MainLoginActivity.this,android.R.style.Theme_Material_Dialog_Alert);
            }
            else
            {
                builderDialog = new AlertDialog.Builder(MainLoginActivity.this);
            }
            builderDialog.setTitle("Exiting CityCen2018 Rider")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                           finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            //Nothing
                        }
                    })
                    .show();
        }
        else {
            super.onBackPressed();
        }
    }

    }




