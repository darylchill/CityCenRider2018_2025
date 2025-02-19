package darylgorra.citycenrider2018;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.BuildConfig;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import java.util.ArrayList;
import java.util.List;

import darylgorra.citycenrider2018.Classes.MySettings;
import darylgorra.citycenrider2018.Classes.databaseHelper;
import darylgorra.citycenrider2018.Classes.systemPermissionList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int totalRow=2;
    int totalCell;
    boolean checkBFTablesInsertFINAL,checkBFTablesInsertTEMP,checkGOTablesInsertTEMP,checkGOTablesInsertFINAL;

    private static final String TAG = "CityCenRider_Main";
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionMenu fam;
    private FloatingActionButton fab_SaveContinue, fab_submit;
    private TableLayout tbllayout_Main;
    private TableRow tr;
    private AutoCompleteTextView txtboxHCN;

    //CHECKERS BLANK/DUPLICATES
    private final boolean check_GroupG25 = false;
    private final boolean  check_GroupH29 = false;
    private final boolean check_GroupI30 = false;
    private final boolean check_GroupI31 = false;
    private final boolean check_GroupJ32 = false;
    private final boolean check_GroupK33 = false;
    private final boolean check_GroupL34 = false;
    private final boolean check_GroupL35 = false ;
    private boolean checkO39_Duplicates = false;


    NestedScrollView nestedScrollView;
    private EditText txtboxAnchorQuestion,txtViewTBL, txtRespondentName,txtboxSettings_BarangayCode,txtboxSettings_Name,
            txtBoxG26, txtBoxH27, txtBoxH28Meat, txtBoxH28Seafoods, txtBoxH28Processed, txtBoxH28Fruits, txtBoxH28Vege, txtBoxH29_Others,
            txtboxJ32Prawn, txtboxJ32Hito, txtboxJ32Pangasius, txtboxJ32Bangus, txtboxJ32Tilapia, txtboxJ32Others,
            txtboxK33Dog, txtboxK33Cats, txtboxL34Other, txtboxL34BOthers, txtBoxL35AOthers, txtboxM36Others, txtboxM37Others, txtBoxN38EOthers,
            txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic, txtboxO39Transport, txtboxO39House, txtboxO39Social,
            txtboxO39Infrastructure, txtboxO39Environment;

    private RadioGroup radioGroup_G, radioGroup_H29, radioGroup_I30, radioGroup_I31, radioGroup_J32, radioGroup_K33, radioGroup_K33_B, radioGroup_K33_C, radioGroup_L34,radioGroup_L34A,RadioGroup_L34B,
            radioGroup_L35, radioGroup_N38, radioGroup_N38A, radioGroup_N38B, radioGroup_N38C, radioGroup_N38D, radioGroup_N38E, radioGroup_N38F;
    private TextView txtNavHeaderVersion,txtNavHeader,txtNavHeader2,txtViewSettings_BarangayCode;
    private CheckBox chkboxL34A_School, chkboxL34A_GovAgencies, chkboxL34A_NonGov, chkboxL34B_CBDRRM, chkboxL34B_FirstAid, chkboxL34B_BLS, chkboxL34B_Fire, chkboxL34B_Search,
            chkboxL35Radio, chkboxL35TV, chkboxL35Pub, chkboxL35SMS, chkboxL35Siren, chkboxL35Internet,
            chkboxM36Roads, chkboxM36Drainage, chkboxM36School, chkboxM36Health, chkboxM36DayCare, chkboxM36Flood,
            chkboxM36Water, chkboxM36Multi, chkboxM36Govern, chkboxM36Bridges, chkboxM36StreetLights, chkboxM36SolarDrier, chkboxM36Court, chkboxM36Basketball,
            chkboxM37Roads, chkboxM37Drainage, chkboxM37School, chkboxM37Health, chkboxM37DayCare, chkboxM37Water, chkboxM37Multi,
            chkboxM37Flood, chkboxM37Govern, chkboxM37Bridges, chkboxM37StreetLights, chkboxM37SolarDrier, chkboxM37Court, chkboxM37Basketball;
    private Button btnAddRow,btnRemoveRow;

    List<EditText> editTextsLists = new ArrayList<EditText>();
    List<Integer> RadioButtonsGOList = new ArrayList<Integer>();
    List<String> EditTextGOList = new ArrayList<String>();
    List<String> CheckboxesGOList = new ArrayList<String>();
    //List<Integer> indexes = new ArrayList<Integer>();

    ArrayAdapter<String> adapter =null;

    Integer[] indexes;
    Integer[] integersRadioButtons ;
    String[] stringsEditText ;
    String[] stringsCheckBoxes;

    int counter = 0;
    private static Integer counterM37_checkboxes=1;
   // SQLiteImporterExporter sqLiteImporterExporter;
    databaseHelper DBConn = new databaseHelper(MainActivity.this);

    private final MySettings mySettings = new MySettings();
    private final String tag = "CityCenRider";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtNavHeaderVersion = headerView.findViewById(R.id.NavHeader_Text0);
        txtNavHeader = headerView.findViewById(R.id.NavHeader_Text);
        txtNavHeader2 = headerView.findViewById(R.id.NavHeader_Text2);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

      // sqLiteImporterExporter = new SQLiteImporterExporter(MainActivity.this, DBConn.getDataBaseName());

        //CHECK HERE IF LOGIN IS COMPLETED
        if (!mySettings.getLoginCheck(MainActivity.this)) {
            Intent mainLoginActivity = new Intent(MainActivity.this, MainLoginActivity.class);
            startActivity(mainLoginActivity);
            finish();
        } else {

            DBConn.dropThisTables();

            requestSystemPermissions();
            initViewControls();
            validations();
            loadDatabaseHHIDToAdapter();


            txtNavHeaderVersion.setText("v."+ BuildConfig.VERSION_NAME);
            txtNavHeader.setText(String.format("Interviewer: %s", mySettings.getInterviewer(MainActivity.this)));
            txtNavHeader2.setText(String.format("Deployed at Brgy. %s", DBConn.getBarangayName(mySettings.getBarangayCode(MainActivity.this))));
            //-----------------------setOnClickListenersHERE!----------------------;
            btnAddRow.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    addCustomTableLayoutRows();
                    return true;
                }

                return false;
            });

            btnRemoveRow.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    removeLastTableRow();
                    return true;
                }
                return false;
            });


            //SAVE BUTTON
           fab_SaveContinue.setOnClickListener(view -> saveToTempDatabase());

            //SUBMIT BUTTON
           fab_submit.setOnClickListener(v -> saveToFinalDatabase());
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mySettings.getLoginCheck(MainActivity.this)) {
                AlertDialog.Builder  builderDialog ;
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    builderDialog = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Material_Dialog_Alert);
                }
                else
                {
                    builderDialog = new AlertDialog.Builder(MainActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

        if (id == R.id.action_sync)
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
           final DatabaseReference mref = database.getReference("ATT_Pass");

           mref.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange (@NonNull DataSnapshot dataSnapshot)
               {

                   mySettings.setMasterPass(MainActivity.this,String.valueOf(dataSnapshot.getValue()));
                   Snackbar.make(coordinatorLayout,"New ATT Password saved",Snackbar.LENGTH_SHORT).show();

               }

               @Override
               public void onCancelled (@NonNull DatabaseError databaseError) {

               }
           });

            final DatabaseReference mref1 = database.getReference("Interviewer_Profile").
                    child(DBConn.getInterviewer_ID(mySettings.getInterviewer(MainActivity.this)));

            mref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mySettings.setCurrent_FS_Pass(MainActivity.this,String.valueOf(dataSnapshot.getValue()));
                    Snackbar.make(coordinatorLayout,"New Supervisor Password saved",Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            callDialog_settings();
        }

        if (id == R.id.action_settings_EXPORT)
        {

            AlertDialog.Builder  builderDialog ;
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                builderDialog = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Material_Dialog_Alert);
            }
            else
            {
                builderDialog = new AlertDialog.Builder(MainActivity.this);
            }
            builderDialog.setTitle("Exporting database")
                    .setMessage("Are you sure you want to export?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            try {
                               // sqLiteImporterExporter.exportDataBase(path);
                                Snackbar.make(coordinatorLayout,"Export Complete",Snackbar.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Snackbar.make(coordinatorLayout,"Exporting Failed",Snackbar.LENGTH_LONG).show();
                            }
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

        if (id == R.id.action_settings_IMPORT)
        {
            AlertDialog.Builder  builderDialog ;
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                builderDialog = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Material_Dialog_Alert);
            }
            else
            {
                builderDialog = new AlertDialog.Builder(MainActivity.this);
            }
            builderDialog.setTitle("Importing database")
                    .setMessage("Are you sure you want to import?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                        try {
                            //sqLiteImporterExporter.importDataBase(path);
                            Snackbar.make(coordinatorLayout,"Importing Complete,PLEASE RESTART THE RIDER",Snackbar.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Snackbar.make(coordinatorLayout,"Importing Failed",Snackbar.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {
                        //Nothing
                    })
                    .show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    //NAVIGATION MENU HERE!!!
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_UpdateSubmitted ) {

            boolean checkDatabaseifEmpty = DBConn.check_finalDatabaseifEmpty();

            //CHECK IF DATABASE IS EMPTY
            if (!checkDatabaseifEmpty)
            {

                Intent intentGoToShowDataAct = new Intent(MainActivity.this,Main2Activity_ShowUpload.class);
                startActivity(intentGoToShowDataAct);
            }
            else
            {
                Snackbar.make(coordinatorLayout,"No data to search",Snackbar.LENGTH_LONG).show();
            }

        }

        else if (id == R.id.nav_changeBarangayCode )
        {
            callDialog_settings();
        }
        else if (id == R.id.nav_manual)
        {
            Intent intentGoTo_ManualAct = new Intent(MainActivity.this,Main4Activity_Manual.class);
            startActivity(intentGoTo_ManualAct);
        }

        else if (id == R.id.nav_codelist)
        {
            Intent intentGoTo_CodeListAct = new Intent(MainActivity.this,Main5Activity_CodeList.class);
            startActivity(intentGoTo_CodeListAct);
        }

        else if (id == R.id.nav_about)
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View alertView = getLayoutInflater().inflate(R.layout.dialog_about, null);
            mBuilder.setView(alertView);
            AlertDialog dialog = mBuilder.create();

            ////SET THE HIDDEN DEVELOPER SETTINGS
            TextView imgDeveloper = alertView.findViewById(R.id.txtMaster_Settings);
            imgDeveloper.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                   // USER NEEDS TO TAP 7 TIMES TO INFLATE DIALOG

                    // IF TAPPING IS LESS THAN 7 TIMES
                    if (counter <7)
                    {
                        counter ++;
                    }
                    else
                    {
                        //IF TAPPING REACH 7 times
                        counter = 0;
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        //CALL PASSWORD DIALOG
                        android.support.v7.app.AlertDialog.Builder mBuilder12 = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                        final View alertView12 = getLayoutInflater().inflate(R.layout.dialog_supervisor_password, null);
                        mBuilder12.setView(alertView12);
                        final android.support.v7.app.AlertDialog dialog12 = mBuilder12.create();

                        final Button btnSubmitUpdated = alertView12.findViewById(R.id.btnSubmitUpdatedData);
                        btnSubmitUpdated.setOnTouchListener((v13, event1) -> {
                            if(event1.getAction() == MotionEvent.ACTION_UP)
                            {
                                String myPass =getResources().getString(R.string.MasterPass);
                                EditText editTextPassword = alertView12.findViewById(R.id.txtBoxSettings_SupervisorPass);
                                if (editTextPassword.getText().toString().equals(myPass))
                                {
                                    dialog12.dismiss();
                                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    //INFLATE THE DEVELOPER DIALOG

                                    AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(MainActivity.this);
                                    View alertView1 = getLayoutInflater().inflate(R.layout.dialog_developers_master_settings, null);
                                    mBuilder1.setView(alertView1);


                                    // INIT VIEWS HERE
                                    final CoordinatorLayout masterSettings_CoordinatorLayout = alertView1.findViewById(R.id.master_Setting_CoordinatorLayout);
                                    EditText master_txtbox_DELETE_SUBMITTED_HCN = alertView1.findViewById(R.id.MasterSettings_txtHCN_DELETE);
                                    final EditText master_txtbox_CHANGE_SUPERVISOR = alertView1.findViewById(R.id.MasterSettings_txt_CHANGE_SUPERVISOR);
                                    master_txtbox_DELETE_SUBMITTED_HCN.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    master_txtbox_CHANGE_SUPERVISOR.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);

                                    final Button master_btn_DELETE_SUBMITTED_HCN = alertView1.findViewById(R.id.MasterSettings_btn_HCN_DELETE);
                                    Button master_btn_CHANGE_SUPERVISOR = alertView1.findViewById(R.id.MasterSettings_btn_NEW_SUPERVISOR);

                                    master_btn_DELETE_SUBMITTED_HCN.setOnClickListener(v12 -> {
                                        final String[] HCN_TEMP_DELETE = DBConn.getAll_TEMP_HCN_ID(txtboxHCN.getText().toString());
                                        final String[] HCN_FINAL_DELETE = DBConn.getAll_FINAL_HCN_ID(txtboxHCN.getText().toString());


                                            DBConn.remove_ANNOYING_HCN(master_btn_DELETE_SUBMITTED_HCN.getText().toString(),HCN_TEMP_DELETE,HCN_FINAL_DELETE);
                                            Snackbar.make(masterSettings_CoordinatorLayout,"DELETING HCN ENTRY SUCCESSFUL",Snackbar.LENGTH_LONG).show();
                                            loadDatabaseHHIDToAdapter();

                                    });

                                    master_btn_CHANGE_SUPERVISOR.setOnClickListener(v1 -> {
                                        String INTERVIEWERID = DBConn.getInterviewer_ID(mySettings.getInterviewer(MainActivity.this));
                                       mySettings.setSupervisorID(MainActivity.this,DBConn.getInterviewer_SupervisorID_BY_InterviewerName(mySettings.getInterviewer(MainActivity.this)));
                                        boolean check = DBConn.update_INTERVIEWER_NEW_SUPERVISOR(INTERVIEWERID
                                                ,master_txtbox_CHANGE_SUPERVISOR.getText().toString());
                                        if (check)
                                        {
                                            Snackbar.make(masterSettings_CoordinatorLayout,"UPDATING NEW SUPERVISOR SUCCESSFUL",Snackbar.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Snackbar.make(masterSettings_CoordinatorLayout,"UPDATING NEW SUPERVISOR FAILED",Snackbar.LENGTH_LONG).show();
                                        }
                                    });

                                    final AlertDialog dialog1 = mBuilder1.create();
                                    dialog1.show();

                                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                                else
                                {
                                    Snackbar.make(coordinatorLayout, "Wrong Password", Snackbar.LENGTH_LONG).show();
                                }

                            }
                            return false;
                        });

                        dialog12.show();
                    }

                }
                return false;
            });

            dialog.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //CUSTOM METHODS

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViewControls()
    {
        coordinatorLayout = findViewById(R.id.coordinatorLayout_Body);

        fam = findViewById(R.id.fabMenu);
        fab_SaveContinue= findViewById(R.id.fab_save);
        fab_submit = findViewById(R.id.fab_Submit);

        nestedScrollView = this.findViewById(R.id.root_ScrollView_Body);

        tbllayout_Main = findViewById(R.id.tblACQuestion_layout);

        btnAddRow = findViewById(R.id.btnAddRow);
        btnRemoveRow = findViewById(R.id.btnRemoveRow);

        txtboxHCN = findViewById(R.id.Auto_txtboxHCN);
        //txtboxHCN.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.abc_popup_background_mtrl_mult));
        txtboxHCN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterM37_checkboxes = 1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtRespondentName = findViewById(R.id.txtBox_RespondentName);

        radioGroup_G = findViewById(R.id.rdGroupG);
        radioGroup_H29 = findViewById(R.id.rdG_H29);
        radioGroup_I30 = findViewById(R.id.rdG_L30);
        radioGroup_I31 = findViewById(R.id.rdG_L31);
        radioGroup_J32 = findViewById(R.id.rdG_J32);
        radioGroup_K33 = findViewById(R.id.rdG_K33);
        radioGroup_K33_B = findViewById(R.id.rdG_K33B);
        radioGroup_K33_C = findViewById(R.id.rdG_K33_C);
        radioGroup_L34 = findViewById(R.id.rdGroup_L34);
        radioGroup_L35 = findViewById(R.id.rdG_L35);
        radioGroup_N38 = findViewById(R.id.rdG_N38);
        radioGroup_N38A = findViewById(R.id.rdG_N38_A);
        radioGroup_N38B = findViewById(R.id.rdG_N38_B);
        radioGroup_N38C = findViewById(R.id.rdG_N38_C);
        radioGroup_N38D = findViewById(R.id.rdG_N38_D);
        radioGroup_N38E = findViewById(R.id.rdG_N38_E);
        radioGroup_N38F = findViewById(R.id.rdG_N38_F);

        chkboxL34A_School = findViewById(R.id.chkL34A_School);
        chkboxL34A_GovAgencies = findViewById(R.id.chkL34A_GovAgencies);
        chkboxL34A_NonGov = findViewById(R.id.chkL34A_NonGov);


        chkboxL34B_CBDRRM = findViewById(R.id.chkboxL34_BRCRP);
        chkboxL34B_FirstAid = findViewById(R.id.chkboxL34B_FirstAid);
        chkboxL34B_BLS = findViewById(R.id.chkboxL34B_BLS);
        chkboxL34B_Fire = findViewById(R.id.chkboxL34B_FireFight);
        chkboxL34B_Search = findViewById(R.id.chkboxL34B_SearchRescue);


        chkboxL35Radio = findViewById(R.id.chkL35A_Radio);
        chkboxL35TV = findViewById(R.id.chkL35A_TV);
        chkboxL35Pub = findViewById(R.id.chkL35A_Publication);
        chkboxL35SMS = findViewById(R.id.chkL35A_SMS);
        chkboxL35Siren = findViewById(R.id.chkL35A_Siren);
        chkboxL35Internet = findViewById(R.id.chkL35A_Internet);

        chkboxM36Roads = findViewById(R.id.chkboxM36_Roads);
        chkboxM36Drainage = findViewById(R.id.chkboxM36_Drainage);
        chkboxM36School = findViewById(R.id.chkboxM36_School);
        chkboxM36Health = findViewById(R.id.chkboxM36_Health);
        chkboxM36DayCare = findViewById(R.id.chkboxM36_DayCare);
        chkboxM36Water = findViewById(R.id.chkboxM36_Water);
        chkboxM36Multi = findViewById(R.id.chkboxM36_Multi);
        chkboxM36Flood = findViewById(R.id.chkboxM36_Flood);
        chkboxM36Govern = findViewById(R.id.chkboxM36_Government);
        chkboxM36Bridges = findViewById(R.id.chkboxM36_Bridges);
        chkboxM36StreetLights = findViewById(R.id.chkboxM36_Roads);
        chkboxM36SolarDrier = findViewById(R.id.chkboxM36_Solar);
        chkboxM36Court = findViewById(R.id.chkboxM36_Covered);
        chkboxM36Basketball = findViewById(R.id.chkboxM36_Basketball);

        chkboxM37Roads = findViewById(R.id.chkboxM37_Roads);
        chkboxM37Drainage = findViewById(R.id.chkboxM37_Drainage);
        chkboxM37School = findViewById(R.id.chkboxM37_School);
        chkboxM37Health = findViewById(R.id.chkboxM37_Health);
        chkboxM37DayCare = findViewById(R.id.chkboxM37_DayCare);
        chkboxM37Water = findViewById(R.id.chkboxM37_Water);
        chkboxM37Multi = findViewById(R.id.chkboxM37_Multi);
        chkboxM37Flood = findViewById(R.id.chkboxM37_Flood);
        chkboxM37Govern = findViewById(R.id.chkboxM37_Government);
        chkboxM37Bridges = findViewById(R.id.chkboxM37_Bridges);
        chkboxM37StreetLights = findViewById(R.id.chkboxM37_StreetLights);
        chkboxM37SolarDrier = findViewById(R.id.chkboxM37_Solar);
        chkboxM37Court = findViewById(R.id.chkboxM37_Covered);
        chkboxM37Basketball = findViewById(R.id.chkboxM37_Basketball);


        txtBoxG26 = findViewById(R.id.txtBoxG26Year);
        txtBoxH27 = findViewById(R.id.txtBoxH27);
        txtBoxH28Meat = findViewById(R.id.txtBoxH28_Meat);
        txtBoxH28Seafoods = findViewById(R.id.txtBoxH28_Seafoods);
        txtBoxH28Processed = findViewById(R.id.txtBoxH28_Processed);
        txtBoxH28Fruits = findViewById(R.id.txtBoxH28_Fruits);
        txtBoxH28Vege = findViewById(R.id.txtBoxH28_Vege);
        txtBoxH29_Others = findViewById(R.id.txtboxH29_Others);
        txtboxJ32Prawn = findViewById(R.id.txtboxJ32_Prawn);
        txtboxJ32Hito = findViewById(R.id.txtboxJ32_Hito);
        txtboxJ32Pangasius = findViewById(R.id.txtboxJ32_Pangasius);
        txtboxJ32Bangus = findViewById(R.id.txtboxJ32_Bangus);
        txtboxJ32Tilapia = findViewById(R.id.txtboxJ32_Tilapia);
        txtboxJ32Others = findViewById(R.id.txtboxJ32_Others);

        txtboxK33Dog = findViewById(R.id.txtboxK33A_Dog);
        txtboxK33Cats = findViewById(R.id.txtboxK33A_Cat);
        txtboxL34Other = findViewById(R.id.txtboxL34A_Other);
        txtboxL34BOthers = findViewById(R.id.txtboxL34B_Others);
        txtBoxL35AOthers = findViewById(R.id.txtboxL35A_Others);
        txtboxM36Others = findViewById(R.id.txtboxM36A_Others);
        txtboxM37Others = findViewById(R.id.txtboxM37_Others);
        txtBoxN38EOthers = findViewById(R.id.txtboxN38E_Others);
        txtboxO39Education = findViewById(R.id.txtboxO39_Education);
        txtboxO39Peace = findViewById(R.id.txtboxO39_Peace);
        txtboxO39Health = findViewById(R.id.txtboxO39_Health);
        txtboxO39Labor = findViewById(R.id.txtboxO39_Labor);
        txtboxO39Economic = findViewById(R.id.txtboxO39_Economic);
        txtboxO39Transport = findViewById(R.id.txtboxO39_Transport);
        txtboxO39House = findViewById(R.id.txtboxO39_House);
        txtboxO39Social = findViewById(R.id.txtboxO39_Social);
        txtboxO39Infrastructure = findViewById(R.id.txtboxO39_Infrastructure);
        txtboxO39Environment = findViewById(R.id.txtboxO39_Environment);

    }

    private void requestSystemPermissions()
    {
        //ENTER MULTIPLE PERMISSIONS IN ONE GO
        String[] permissions = {systemPermissionList.REQUEST_WRITE_EXTERNAL_STORAGE,
                systemPermissionList.REQUEST_READ_EXTERNAL_STORAGE,
                systemPermissionList.REQUEST_GET_ACCOUNTS,
                systemPermissionList.REQUEST_INTERNET};
        if (!systemPermissionList.hasPermissions(MainActivity.this, permissions)) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, systemPermissionList.Permissions_All_Code);
        }

    }

    private String getValueFromSelectedTableRow_SelectedCell(View view, int index)
    {
        String valueFromSpecificTableRow;

        tr = (TableRow) view.getParent();
        EditText firstTextView = (EditText) tr.getChildAt(index);

        valueFromSpecificTableRow = String.valueOf(firstTextView.getText());
        return valueFromSpecificTableRow;

    }

    private void setDefaultValue_AnchorQuestions(View view,Integer[] indexes,String defaultValue)
    {
        try
        {
            //HERE IS TO SET VALUES FOR ANCHORED QUESTION
            //AGE 0 --> SET ANCHORED QUESTIONS TO 97(NOT Applicable)
            tr = (TableRow) view.getParent();

            for (int i=0;i<indexes.length;i++)
            {
                txtboxAnchorQuestion = (EditText) tr.getChildAt(indexes[i]);
                txtboxAnchorQuestion.setText(defaultValue);
            }
        }
        catch (Exception e)
        {
            Log.i(TAG,e.toString());
        }


    }


    private void initInflateCustomDialogsHere(final View viewEditTextHere, final int layoutCustom, final int rdGroupCustom )
    {

        //WE NEED TO USE A THREAD FOR FASTER EXECUTION
        //USE THE "runOnUIThread" WHEN IF YOU WANT TO UPDATE THE UI
        final Thread thread_inflateCustomDialog = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //INFLATE CUSTOM DIALOG HERE
                        try
                        {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            final View alertView = getLayoutInflater().inflate(layoutCustom, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            final EditText editText = viewEditTextHere.findViewById(viewEditTextHere.getId());

                            editText.setText(editText.getText().toString());
                            final RadioGroup rdGroup_dialogCustom = alertView.findViewById(rdGroupCustom);

                            rdGroup_dialogCustom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton radioButton = alertView.findViewById(rdGroup_dialogCustom.getCheckedRadioButtonId());
                                    editText.setText(String.valueOf(radioButton.getHint()));
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        catch ( Exception e )
                        {
                            Log.i(tag ,"ErrorMsg:"+ e);
                        }

                    }
                });
            }
        });
        thread_inflateCustomDialog.start();

    }


    private void initInflateCustomDialogs_EditText(final View view_Here, final int layoutCustom, final int EdittextID, final int TextViewID,
                                                   final int keyboardCode,final String title_TxtView )
    {

        //WE NEED TO USE A THREAD FOR FASTER EXECUTION
        //USE THE "runOnUIThread" WHEN IF YOU WANT TO UPDATE THE UI
        final Thread thread_inflateCustomDialog = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //INFLATE CUSTOM DIALOG HERE
                        try
                        {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            final View alertView = getLayoutInflater().inflate(layoutCustom, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            TextView title = alertView.findViewById(TextViewID);
                            final EditText editText = view_Here.findViewById(view_Here.getId());

                            title.setText(title_TxtView);
                            editText.setText(editText.getText().toString());
                            final EditText custom_Edittext = alertView.findViewById(EdittextID);

                            custom_Edittext.setInputType(keyboardCode);
                            custom_Edittext.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged (CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged (CharSequence s, int start, int before, int count) {
                                    if (keyboardCode == InputType.TYPE_CLASS_NUMBER)
                                    {
                                        if ( custom_Edittext.length() <= 3 )
                                            editText.setText(String.valueOf(custom_Edittext.getText()));

                                        else {
                                            custom_Edittext.setInputType(InputType.TYPE_NULL);
                                            Snackbar.make(coordinatorLayout, "Max Limit", Snackbar.LENGTH_LONG).show();
                                        }
                                    }
                                    else
                                    {
                                        editText.setText(String.valueOf(custom_Edittext.getText()));
                                    }
                                }

                                @Override
                                public void afterTextChanged (Editable s) {

                                }
                            });



                            dialog.show();
                        }
                        catch ( Exception e )
                        {
                            Log.i(tag ,"ErrorMsg:"+ e);
                        }

                    }
                });
            }
        });
        thread_inflateCustomDialog.start();

    }

    private void initInflateCustomDialogs_RadioGroupYESNO(final View view_Here, final int layoutCustom, final int rdGroupCustom, final int TextViewID ,final String title_TxtView )
    {

        //WE NEED TO USE A THREAD FOR FASTER EXECUTION
        //USE THE "runOnUIThread" WHEN IF YOU WANT TO UPDATE THE UI
        final Thread thread_inflateCustomDialog = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //INFLATE CUSTOM DIALOG HERE
                        try
                        {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            final View alertView = getLayoutInflater().inflate(layoutCustom, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            final EditText editText = view_Here.findViewById(view_Here.getId());

                            TextView title = alertView.findViewById(TextViewID);
                            final RadioGroup rdGroup_dialogCustom = alertView.findViewById(rdGroupCustom);

                            title.setText(title_TxtView);
                            editText.setText(editText.getText().toString());

                            rdGroup_dialogCustom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton radioButton = alertView.findViewById(rdGroup_dialogCustom.getCheckedRadioButtonId());
                                    editText.setText(String.valueOf(radioButton.getHint()));
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        catch ( Exception e )
                        {
                            Log.i(tag ,"ErrorMsg:"+ e);
                        }

                    }
                });
            }
        });
        thread_inflateCustomDialog.start();

    }

    private void initInflateCustomDialog_OthersHere(final View viewEditTextHere, final int layoutCustom, final int rdGroupCustom, final int EditTextOther)
    {
        //WE NEED TO USE A THREAD FOR FASTER EXECUTION
        Thread thread_inflateCustomThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        try
                        {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            final View alertView = getLayoutInflater().inflate(layoutCustom, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            final RadioGroup rdGroup_dialogCustom = alertView.findViewById(rdGroupCustom);
                            final EditText editTextDialog = alertView.findViewById(EditTextOther);
                            final EditText editTextOther = viewEditTextHere.findViewById(viewEditTextHere.getId());
                            editTextOther.setText(editTextOther.getText().toString());

                            rdGroup_dialogCustom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton radioButton = alertView.findViewById(rdGroup_dialogCustom.getCheckedRadioButtonId());
                                    editTextOther.setText(String.valueOf(radioButton.getHint()));
                                    dialog.dismiss();
                                }
                            });

                            editTextOther.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                            editTextDialog.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                                { }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count)
                                {
                                    if (rdGroup_dialogCustom.getCheckedRadioButtonId()== -1)
                                    {
                                        editTextOther.setText(editTextDialog.getText().toString());
                                    }
                                    else
                                    {
                                        rdGroup_dialogCustom.clearCheck();
                                        editTextOther.setText(editTextDialog.getText().toString());
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s)
                                {

                                }
                            });

                            dialog.show();
                        }
                        catch ( Exception e )
                        {
                            Log.i(tag,"ErrorMsg:"+ e);
                        }
                    }
                });
            }
        });
        thread_inflateCustomThread2.start();

    }

    private void loadDatabaseSchoolCodesToAdapter(final AutoCompleteTextView autoCompleteTextView, final EditText editText)
    {
        final String[] SchoolCodesData = DBConn.getAllSchoolNames();
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,SchoolCodesData);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                editText.setText(String.valueOf(i));
            }
        });
    }

    private void loadDatabaseCooperationCodesToAdapter(final AutoCompleteTextView autoCompleteTextView, final EditText editText)
    {
        final String[] CooperationCodesData = DBConn.getAllCooperationNames();
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, CooperationCodesData);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(String.valueOf(i));

            }
        });
    }

    private void loadDatabaseHHIDToAdapter()
    {
        try
        {
            String[] HCN = DBConn.getAll_TEMP_HHIDData();

            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,HCN);
            txtboxHCN.setThreshold(1);
            txtboxHCN.setAdapter(adapter);

            txtboxHCN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    fetch_B_F_datas(adapter.getItem(i));
                    fetch_GODatas(adapter.getItem(i));
                }
            });
        }
        catch (NullPointerException e)
        {
            Log.d(TAG,e.toString());
        }

    }

    private void fetch_B_F_datas (final String HCN)
    {
        Thread fetchACDatas_Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //GET RESPONDENT NAME
                        txtRespondentName.setText(DBConn.get_TEMP_RespondentName(String.valueOf(txtboxHCN.getText())));


                        //FLUSH THE GRID
                        removeAllRows();

                        String selectQuery = String.format("SELECT Lastname ,Firstname ,Middlename, Sex," +
                                " C1 ,C2 ,C2_a ,C3 ,C4 ,C5 ," +
                                "D6 ,D7,E8,E9 ,E10,E10_a," +
                                "E11,E12,E13,E13_a," +
                                "E14,F15,F15_a,F16,F17,F17_a,F17_b,F17_c,F17_d," +
                                "F17_BCG,F17_Penta1,F17_Penta2,F17_Penta3,F17_OPV1,F17_OPV2," +
                                "F17_OPV3,F17_HEPAB1,F17_MEASLES,F17_f," +
                                "F18,F19,F20,F21,F21_a,F22,F22_a,F22_b," +
                                "F23,F24 FROM tbl_Temp_BF_Tables WHERE HCN= %s","'"+HCN+"'");  //2
                        SQLiteDatabase db = DBConn.getReadableDatabase();
                        Cursor cursor = db.rawQuery(selectQuery, null);

                        //tbllayout.removeView(tr);

                        while ( cursor.moveToNext() ) {
                            tr = new TableRow(MainActivity.this);
                            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            tr.setId(totalRow);
                            tr.setBackgroundResource(R.drawable.border);

                            //Loop for every Editext views
                            for (int index = 0; index < 49; index++ ) {

                                EditText txtViewTBL = new EditText(MainActivity.this);
                                txtViewTBL.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                txtViewTBL.setId(totalCell);
                                editTextsLists.add(txtViewTBL);

                                txtViewTBL.setGravity(Gravity.CENTER);
                                txtViewTBL.setText(cursor.getString(index));
                                txtViewTBL.setBackgroundResource(R.drawable.border);

                                tableRowFunctions_ACTIVITY_1(txtViewTBL,index);

                                totalCell = totalCell + 1;

                                tr.addView(txtViewTBL);

                            }
                            totalRow = totalRow + 1;
                            tbllayout_Main.addView(tr);
                        }
                        cursor.close();
                        db.close();
                        Toast.makeText(MainActivity.this, editTextsLists.size()+" :Total", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        fetchACDatas_Thread.start();

    }
    private void fetch_GODatas(final String HCN)
    {
        Thread fetch_GO_DATAs_Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //try {

                        clearAll_GO_Questions();

                        String selectQuery = String.format("SELECT * FROM  tbl_Temp_GO_Tables  WHERE HCN= %s","'"+HCN+"'");  //2

                        SQLiteDatabase db = DBConn.getReadableDatabase();
                        Cursor cursor = db.rawQuery(selectQuery, null);
                        while ( cursor.moveToNext() ) {

                            switch ( cursor.getString(3 ))
                            {
                                case "1":
                                    radioGroup_G.check(radioGroup_G.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_G.check(radioGroup_G.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_G.check(radioGroup_G.getChildAt(2).getId());
                                    break;
                            }

                            txtBoxG26.setText(cursor.getString(4));
                            txtBoxH27.setText(cursor.getString(5));

                            txtBoxH28Meat.setText(cursor.getString(6));
                            txtBoxH28Seafoods.setText(cursor.getString(7));
                            txtBoxH28Processed.setText(cursor.getString(8));
                            txtBoxH28Fruits.setText(cursor.getString(9));
                            txtBoxH28Vege.setText(cursor.getString(10));


                            //------------------------------------------------------------------------
                            switch ( cursor.getString(11) ) {
                                case "0":
                                    txtBoxH29_Others.setText(cursor.getString(12));
                                    break;
                                case "1":
                                    radioGroup_H29.check(radioGroup_H29.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_H29.check(radioGroup_H29.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_H29.check(radioGroup_H29.getChildAt(2).getId());
                                    break;
                                case "4":
                                    radioGroup_H29.check(radioGroup_H29.getChildAt(3).getId());
                                    break;
                                case "5":
                                    radioGroup_H29.check(radioGroup_H29.getChildAt(4).getId());
                                    break;
                            }
                            //------------------------------------------------------------------------
                            switch ( cursor.getString(13) ) {
                                case "1":
                                    radioGroup_I30.check(radioGroup_I30.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_I30.check(radioGroup_I30.getChildAt(1).getId());
                                    break;
                            }

                            //------------------------------------------------------------------------
                            switch ( cursor.getString(14) ) {
                                case "1":
                                    radioGroup_I31.check(radioGroup_I31.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_I31.check(radioGroup_I31.getChildAt(1).getId());
                                    break;
                            }
                            //------------------------------------------------------------------------
                            switch ( cursor.getString(15) ) {
                                case "1":
                                    radioGroup_J32.check(radioGroup_J32.getChildAt(0).getId());

                                    txtboxJ32Prawn.setText(cursor.getString(16));
                                    txtboxJ32Hito.setText(cursor.getString(17));
                                    txtboxJ32Pangasius.setText(cursor.getString(18));
                                    txtboxJ32Bangus.setText(cursor.getString(19));
                                    txtboxJ32Tilapia.setText(cursor.getString(20));
                                    txtboxJ32Others.setText(cursor.getString(21));
                                    break;
                                case "2":
                                    radioGroup_J32.check(radioGroup_J32.getChildAt(1).getId());
                                    break;
                            }


                            switch ( cursor.getString(22) ) {
                                case "1":
                                    radioGroup_K33.check(radioGroup_K33.getChildAt(0).getId());

                                    txtboxK33Dog.setText(cursor.getString(23));
                                    txtboxK33Cats.setText(cursor.getString(24));
                                    break;
                                case "2":
                                    radioGroup_K33.check(radioGroup_K33.getChildAt(1).getId());
                                    break;
                            }


                            //------------------------------------------------------------------------
                            switch ( cursor.getString(25) ) {
                                case "1":
                                    radioGroup_K33_B.check(radioGroup_K33_B.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_K33_B.check(radioGroup_K33_B.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_K33_B.check(radioGroup_K33_B.getChildAt(2).getId());
                                    break;
                            }
                            //------------------------------------------------------------------------

                            //------------------------------------------------------------------------
                            switch ( cursor.getString(26) ) {
                                case "1":
                                    radioGroup_K33_C.check(radioGroup_K33_C.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_K33_C.check(radioGroup_K33_C.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_K33_C.check(radioGroup_K33_C.getChildAt(2).getId());
                                    break;
                            }
                            //------------------------------------------------------------------------
                            //------------------------------------------------------------------------
                            switch ( cursor.getString(27) ) {
                                case "1":
                                    radioGroup_L34.check(radioGroup_L34.getChildAt(0).getId());

                                    if ( cursor.getString(28).equals("1") ) {
                                        chkboxL34A_School.setChecked(true);
                                    }
                                    if ( cursor.getString(29).equals("1") ) {
                                        chkboxL34A_GovAgencies.setChecked(true);
                                    }
                                    if ( cursor.getString(30).equals("1") ) {
                                        chkboxL34A_NonGov.setChecked(true);
                                    }
                                    if ( cursor.getString(31).length()>0 ) {
                                        txtboxL34Other.setText(String.valueOf(cursor.getString(31)));
                                    }
                                    //------------------------------------------------------------------------
                                    if ( cursor.getString(32).equals("1") ) {
                                        chkboxL34B_CBDRRM.setChecked(true);
                                    }
                                    if ( cursor.getString(33).equals("1") ) {
                                        chkboxL34B_FirstAid.setChecked(true);
                                    }
                                    if ( cursor.getString(34).equals("1") ) {
                                        chkboxL34B_BLS.setChecked(true);
                                    }
                                    if ( cursor.getString(35).equals("1") ) {
                                        chkboxL34B_Fire.setChecked(true);
                                    }
                                    if ( cursor.getString(36).equals("1") ) {
                                        chkboxL34B_Search.setChecked(true);
                                    }
                                    if ( cursor.getString(37).length()>0 ) {
                                        txtboxL34BOthers.setText(String.valueOf(cursor.getString(37)));
                                    }

                                    break;
                                case "2":
                                    radioGroup_L34.check(radioGroup_L34.getChildAt(1).getId());
                                    break;
                                case "-1":
                                    radioGroup_L34.check(radioGroup_L34.getChildAt(1).getId());
                                    break;
                            }
                            //------------------------------------------------------------------------


                            //------------------------------------------------------------------------
                            switch ( cursor.getString(38) )
                            {
                                case "1":
                                    radioGroup_L35.check( radioGroup_L35.getChildAt(0).getId());
                                    if ( cursor.getString(39).equals("1") ) {
                                        chkboxL35Radio.setChecked(true);
                                    }
                                    if ( cursor.getString(40).equals("1") ) {
                                        chkboxL35TV.setChecked(true);
                                    }

                                    if ( cursor.getString(41).equals("1") ) {
                                        chkboxL35Pub.setChecked(true);
                                    }

                                    if ( cursor.getString(42).equals("1") ) {
                                        chkboxL35SMS.setChecked(true);
                                    }

                                    if ( cursor.getString(43).equals("1") ) {
                                        chkboxL35Siren.setChecked(true);
                                    }

                                    if ( cursor.getString(44).equals("1") ) {
                                        chkboxL35Internet.setChecked(true);
                                    }
                                    if ( cursor.getString(45).length()>0) {
                                        txtBoxL35AOthers.setText(String.valueOf(cursor.getString(45)));
                                    }
                                    break;
                                case "2":
                                    radioGroup_L35.check(radioGroup_L35.getChildAt(1).getId());
                                    break;

                            }

                            //--------------------------------------------------------------------------------------------------

                            //----------------------------------------------------------------------------------------------

                            if ( cursor.getString(46).equals("1") ) {
                                chkboxM36Roads.setChecked(true);
                            }
                            if ( cursor.getString(47).equals("1") ) {
                                chkboxM36Drainage.setChecked(true);
                            }

                            if ( cursor.getString(48).equals("1") ) {
                                chkboxM36School.setChecked(true);
                            }

                            if ( cursor.getString(49).equals("1") ) {
                                chkboxM36Health.setChecked(true);
                            }

                            if ( cursor.getString(50).equals("1") ) {
                                chkboxM36DayCare.setChecked(true);
                            }

                            if ( cursor.getString(51).equals("1") ) {
                                chkboxM36Water.setChecked(true);
                            }

                            if ( cursor.getString(52).equals("1") ) {
                                chkboxM36Multi.setChecked(true);
                            }

                            if ( cursor.getString(53).equals("1") ) {
                                chkboxM36Flood.setChecked(true);
                            }
                            if ( cursor.getString(54).equals("1") ) {
                                chkboxM36Govern.setChecked(true);
                            }

                            if ( cursor.getString(55).equals("1") ) {
                                chkboxM36Bridges.setChecked(true);
                            }
                            if ( cursor.getString(56).equals("1") ) {
                                chkboxM36StreetLights.setChecked(true);
                            }
                            if ( cursor.getString(57).equals("1") ) {
                                chkboxM36SolarDrier.setChecked(true);
                            }

                            if ( cursor.getString(58).equals("1") ) {
                                chkboxM36Court.setChecked(true);
                            }
                            if ( cursor.getString(59).equals("1") ) {
                                chkboxM36Basketball.setChecked(true);
                            }
                            if ( cursor.getString(60).length()>0) {
                                txtboxM36Others.setText(String.valueOf(cursor.getString(60)));
                            }
                            //----------------------------------------------------------------------------------------------

                            //---------------------------------------------------------------------------------------------
                            //RESET COUNTER
                            counterM37_checkboxes = 1;

                            if ( cursor.getString(61).equals("1") ) {
                                chkboxM37Roads.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Roads);

                            }
                            if ( cursor.getString(62).equals("1") ) {
                                chkboxM37Drainage.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Drainage);
                            }

                            if ( cursor.getString(63).equals("1") ) {
                                chkboxM37School.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37School);
                            }

                            if ( cursor.getString(64).equals("1") ) {
                                chkboxM37Health.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Health);
                            }

                            if ( cursor.getString(65).equals("1") ) {
                                chkboxM37DayCare.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37DayCare);
                            }

                            if ( cursor.getString(66).equals("1") ) {
                                chkboxM37Water.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Water);
                            }

                            if ( cursor.getString(67).equals("1") ) {
                                chkboxM37Multi.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Multi);
                            }

                            if ( cursor.getString(68).equals("1") ) {
                                chkboxM37Flood.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Flood);
                            }
                            if ( cursor.getString(69).equals("1") ) {
                                chkboxM37Govern.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Govern);
                            }

                            if ( cursor.getString(70).equals("1") ) {
                                chkboxM37Bridges.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Bridges);
                            }
                            if ( cursor.getString(71).equals("1") ) {
                                chkboxM37StreetLights.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37StreetLights);
                            }
                            if ( cursor.getString(72).equals("1") ) {
                                chkboxM37SolarDrier.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37SolarDrier);
                            }

                            if ( cursor.getString(73).equals("1") ) {
                                chkboxM37Court.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Court);
                            }
                            if ( cursor.getString(74).equals("1") ) {
                                chkboxM37Basketball.setChecked(true);
                                check_CounterM37_checkboxes(chkboxM37Basketball);
                            }
                            if ( cursor.getString(75).length()>0) {
                                txtboxM37Others.setText(String.valueOf(cursor.getString(75)));
                            }
                            //----------------------------------------------------------------------------------------------
                            //----------------------------------------------------------------------------------------------
                            switch ( cursor.getString(76) )
                            {
                                case "0":
                                    radioGroup_N38.check(radioGroup_N38.getChildAt(1).getId());
                                    break;
                                case "1":
                                    radioGroup_N38.check(radioGroup_N38.getChildAt(0).getId());

                                    switch ( cursor.getString(77) )
                                    {
                                        case "1":
                                            radioGroup_N38A.check(radioGroup_N38A.getChildAt(0).getId());
                                            break;
                                        case "2":
                                            radioGroup_N38A.check(radioGroup_N38A.getChildAt(1).getId());
                                            break;
                                        case "3":
                                            radioGroup_N38A.check(radioGroup_N38A.getChildAt(2).getId());
                                            break;
                                        case "4":
                                            radioGroup_N38A.check(radioGroup_N38A.getChildAt(3).getId());
                                            break;
                                        case "5":
                                            radioGroup_N38A.check(radioGroup_N38A.getChildAt(4).getId());
                                            break;
                                    }
                                    break;
                                case "2":
                                    radioGroup_N38.check(radioGroup_N38.getChildAt(1).getId());
                                    break;
                            }

                            //----------------------------------------------------------------------------------------------
                            switch ( cursor.getString(78) )
                            {

                                case "1":
                                    radioGroup_N38B.check(radioGroup_N38B.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_N38B.check(radioGroup_N38B.getChildAt(1).getId());
                                    break;
                            }

                            //----------------------------------------------------------------------------------------------
                            switch ( cursor.getString(79) )
                            {
                                case "1":
                                    radioGroup_N38C.check(radioGroup_N38C.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_N38C.check(radioGroup_N38C.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_N38C.check(radioGroup_N38C.getChildAt(2).getId());
                                    break;
                            }

                            //----------------------------------------------------------------------------------------------
                            switch ( cursor.getString(80) )
                            {
                                case "1":
                                    radioGroup_N38D.check(radioGroup_N38D.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_N38D.check(radioGroup_N38D.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_N38D.check(radioGroup_N38D.getChildAt(2).getId());
                                    break;
                                case "4":
                                    radioGroup_N38D.check(radioGroup_N38D.getChildAt(3).getId());
                                    break;
                            }

                            //----------------------------------------------------------------------------------------------
                            switch ( cursor.getString(81) )
                            {
                                case "0":
                                    txtBoxN38EOthers.setText(String.valueOf(cursor.getString(82)));
                                    break;
                                case "1":
                                    radioGroup_N38E.check( radioGroup_N38E.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_N38E.check( radioGroup_N38E.getChildAt(1).getId());
                                    break;
                                case "3":
                                    radioGroup_N38E.check( radioGroup_N38E.getChildAt(2).getId());
                                    break;
                                case "4":
                                    radioGroup_N38E.check( radioGroup_N38E.getChildAt(3).getId());
                                    break;
                            }
                            //----------------------------------------------------------------------------------------------
                            switch ( cursor.getString(83) )
                            {
                                case "1":
                                    radioGroup_N38F.check(radioGroup_N38F.getChildAt(0).getId());
                                    break;
                                case "2":
                                    radioGroup_N38F.check( radioGroup_N38F.getChildAt(1).getId());
                                    break;

                            }

                            txtboxO39Education.setText(cursor.getString(84));
                            txtboxO39Peace.setText(cursor.getString(85));
                            txtboxO39Health.setText(cursor.getString(86));
                            txtboxO39Labor.setText(cursor.getString(87));
                            txtboxO39Economic.setText(cursor.getString(88));
                            txtboxO39Transport.setText(cursor.getString(89));
                            txtboxO39House.setText(cursor.getString(90));
                            txtboxO39Social.setText(cursor.getString(91));
                            txtboxO39Infrastructure.setText(cursor.getString(92));
                            txtboxO39Environment.setText(cursor.getString(93));
                        }
                        cursor.close();


                        //validations();

                        db.close();
                    }

                    /**
                     catch ( Exception e )
                     {
                     Log.i(tag,"ErrorMsg:"+ e.toString());
                     }
                     }
                     **/

                });
            }
        });
        fetch_GO_DATAs_Thread.start();
    }

    private void addCustomTableLayoutRows()
    {
        //WE NEED TO USE A THREAD FOR FASTER EXECUTION
        Thread createTableRowThread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ( txtboxHCN.length() <6 ) {
                            Snackbar.make(coordinatorLayout,"Household Control Number must be 6 numbers!",Snackbar.LENGTH_LONG).show();
                        } else if (check_ifEmptyTableCell()) {
                            Snackbar.make(coordinatorLayout,editTextsLists.size() + ": Total Please fill empty boxes!",Snackbar.LENGTH_LONG).show();
                        }
                        else if ( txtRespondentName.getText().toString().equals(""))
                        {
                            Snackbar.make(coordinatorLayout,"Responded Name empty!",Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            int i;

                            tr = new TableRow(MainActivity.this);
                            tr.setId(totalRow);
                            tr.setBackgroundResource(R.drawable.border);

                            //Loop for every Editext views
                            for ( i = 0; i < 49; i++ ) {

                                // create a new textview
                                txtViewTBL = new EditText(MainActivity.this);
                                txtViewTBL.setId(View.generateViewId());
                                editTextsLists.add(txtViewTBL);
                                txtViewTBL.setGravity(Gravity.CENTER);
                                txtViewTBL.setText("0");
                                totalCell = totalCell + 1;

                                tableRowFunctions_ACTIVITY_1(txtViewTBL,i);

                                tr.addView(txtViewTBL);

                            }
                            tbllayout_Main.addView(tr);
                            totalRow = totalRow + 1;
                            Snackbar.make(coordinatorLayout, totalCell + ": Total Cell: " + editTextsLists.size()+":Total size", Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
        createTableRowThread.start();
    }

    private void tableRowFunctions_ACTIVITY_1 (final EditText txtViewTBL, int i)
    {
        try {


            //Only Lastname(0),Firstname(1) and Middlenae(2) are needed a character inputs
            //B3
            if (i == 0) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            initInflateCustomDialogs_EditText(view, R.layout.dialog_custom_edittext, R.id.txtbox_Custom_Edittext,
                                    R.id.txtView_Edittext_Custom, InputType.TYPE_TEXT_VARIATION_PERSON_NAME, "Enter Lastname");
                            return true;
                        }
                        return false;
                    }
                });

            }
            //B3
            else if (i == 1) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            initInflateCustomDialogs_EditText(view, R.layout.dialog_custom_edittext, R.id.txtbox_Custom_Edittext,
                                    R.id.txtView_Edittext_Custom, InputType.TYPE_TEXT_VARIATION_PERSON_NAME, "Enter Firstname");
                            return true;
                        }
                        return false;
                    }
                });

            }
            //B3
            else if (i == 2) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            initInflateCustomDialogs_EditText(view, R.layout.dialog_custom_edittext, R.id.txtbox_Custom_Edittext,
                                    R.id.txtView_Edittext_Custom, InputType.TYPE_TEXT_VARIATION_PERSON_NAME, "Enter Middlename");
                            return true;
                        }
                        return false;
                    }
                });

            } else if (i == 3) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            initInflateCustomDialogsHere(view, R.layout.dialog_sex_choices, R.id.rdGroup_dialog_Sex_Choice);
                            return true;
                        }

                        return false;
                    }
                });

            }

            //C1
            else if (i == 4) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);

                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            initInflateCustomDialogsHere(view, R.layout.dialogcustom_b_placeofbirth, R.id.rdGroup_dialogcustom_B_PlaceofBirth);
                            return true;
                        }

                        return false;
                    }
                });
            }
            //C2
            else if (i == 5) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            final EditText editText = view.findViewById(view.getId());
                            if (getValueFromSelectedTableRow_SelectedCell(view, 4).equals("000000000")) {
                                editText.setText("0");
                                Snackbar.make(coordinatorLayout, "Place of Birth: Outside Philippines", Snackbar.LENGTH_LONG).show();
                            } else {
                                initInflateCustomDialogsHere(view, R.layout.dialogcustom_b_birthregistry, R.id.rdGroup_dialogcustom_B_BirthRegistry);
                                txtViewTBL.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        if (editText.getText().toString().equals("2")) {
                                            indexes = new Integer[]{6};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            indexes = new Integer[]{6};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });

                            }

                            return true;
                        }
                        return false;
                    }
                });
            }
            //C2.a
            else if (i == 6) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 5).equals("2") || getValueFromSelectedTableRow_SelectedCell(view, 4).equals("2")) {
                                initInflateCustomDialog_OthersHere(view, R.layout.dialog_c_unregisteredbirth, R.id.rdGroup_dialog_UnregisteredBirth, R.id.txtViewOthers_C_Unregistered);

                            } else {

                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Birth is already registered!", Snackbar.LENGTH_LONG).show();
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }

            //AGE LIMIT (YEARS)
            //C3
            else if (i == 7) {

                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);

                //HERE WE SANDWITCH ALERTDIALOG WITH ADDTEXTLISTENER TO THE ONTOUCHLISTENER
                //AS THE PERFECT SOLUTION FOR OUR AGE ANCHORED QUESTIONS
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            //READY THE CUSTOM DIALOG
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            final View alertView = getLayoutInflater().inflate(R.layout.dialog_custom_edittext, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            TextView title = alertView.findViewById(R.id.txtView_Edittext_Custom);
                            final EditText editText = view.findViewById(view.getId());

                            title.setText("Enter Age");
                            editText.setText(editText.getText().toString());
                            final EditText custom_Edittext = alertView.findViewById(R.id.txtbox_Custom_Edittext);

                            //SET THE ADDTEXTLISTENER
                            custom_Edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                            custom_Edittext.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    if (custom_Edittext.length() <= 3) {
                                        editText.setText(String.valueOf(custom_Edittext.getText()));


                                        //VALUE 0
                                        if (custom_Edittext.getText().toString().length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) == 0) {
                                            indexes = new Integer[]{8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 41, 42, 43, 44, 45, 46, 47, 48};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        } else {
                                            indexes = new Integer[]{8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 41, 42, 43, 44, 45, 46, 47, 48};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        }

                                        //VALUE IS 0 and ABOVE
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).equals("")) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) > 0) {
                                            indexes = new Integer[]{24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        } else {
                                            indexes = new Integer[]{24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        }

                                        //C4-C5
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).equals("")) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) >= 10) {

                                            indexes = new Integer[]{8, 9};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            indexes = new Integer[]{8, 9};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }

                                        //D6 - D7
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) < 25 &&
                                                Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 2) {

                                            indexes = new Integer[]{10, 11};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            indexes = new Integer[]{10, 11};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }

                                        //E8-E14
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) <= 16) {
                                            indexes = new Integer[]{12, 13, 14, 15, 16, 17, 18, 19, 20};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        } else {
                                            indexes = new Integer[]{12, 13, 14, 15, 16, 17, 18, 19, 20};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        }
                                        //F15-F16
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) >= 0) {
                                            indexes = new Integer[]{21, 22, 23};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            indexes = new Integer[]{21, 22, 23};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }

                                        //F18-F19
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) <= 6) {
                                            //F18-F19
                                            indexes = new Integer[]{39, 40};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {

                                            indexes = new Integer[]{39, 40};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }

                                        //F20
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) <= 7 &&
                                                Integer.parseInt(custom_Edittext.getText().toString()) > 0) {

                                            indexes = new Integer[]{41};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            indexes = new Integer[]{41};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }


                                        //F21-F21.a
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) <= 49 &&
                                                Integer.parseInt(custom_Edittext.getText().toString()) >= 10) {

                                            indexes = new Integer[]{42, 43};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            //F21-F21.a
                                            indexes = new Integer[]{42, 43};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }

                                        //F22-F24
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Age must be provided!", Snackbar.LENGTH_LONG).show();
                                        } else if (Integer.parseInt(custom_Edittext.getText().toString()) <= 49 &&
                                                Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 &&
                                                        custom_Edittext.getText().toString().equals("4")) {

                                            indexes = new Integer[]{44, 45, 46, 47, 48};
                                            setDefaultValue_AnchorQuestions(view, indexes, "");
                                        } else {
                                            //F22-F24
                                            indexes = new Integer[]{44, 45, 46, 47, 48};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }

                                    } else {
                                        custom_Edittext.setInputType(InputType.TYPE_NULL);
                                        Snackbar.make(coordinatorLayout, "Max Limit", Snackbar.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });

                            dialog.show();
                            return true;
                        }
                        return false;
                    }
                });

            }
            //C4
            else if (i == 8) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 9) {
                                initInflateCustomDialog_OthersHere(view,
                                        R.layout.dialog_d_marriagetype,
                                        R.id.rdGroup_dialog_MarriageType,
                                        R.id.txtViewOthers_D_Marriage);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //C5
            else if (i == 9) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 9) {
                                initInflateCustomDialogsHere(view,
                                        R.layout.dialog_e_migration,
                                        R.id.rdGroup_dialog_E_Migration);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //D6
            else if (i == 10) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            //WE DONT WANT TO CALL THE METHOD HERE BACAUSE WERE
                            // USING AUTOCOMPLETE_EDITTEXT TO LOAD DATA FROM DATABASEFROM
                            //SO GO MANUAL
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            final View alertView = getLayoutInflater().inflate(R.layout.dialog_school_list, null);
                            mBuilder.setView(alertView);
                            final AlertDialog dialog = mBuilder.create();

                            final AutoCompleteTextView autoCompleteTextView = alertView.findViewById(R.id.AutoComplete_txtViewSchoolCodes);
                            Button button = alertView.findViewById(R.id.btnSearchSchoolCode);
                            final EditText editText = view.findViewById(view.getId());
                            final EditText editText_School_Other = alertView.findViewById(R.id.txtViewOthers_New_School);
                            Button btnEnterNewSchool = alertView.findViewById(R.id.btnAddSchoolCode);
                            final RadioGroup radioGroup = alertView.findViewById(R.id.rdGroup_dialog_Custom_YesNo_School);


                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if ((Integer.valueOf(getValueFromSelectedTableRow_SelectedCell(view, 7))) >= 3 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) < 25) {

                                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        RadioButton radioButton = alertView.findViewById(radioGroup.getCheckedRadioButtonId());
                                        editText.setText(String.valueOf(radioButton.getHint()));
                                        dialog.dismiss();
                                    }
                                });
                                loadDatabaseSchoolCodesToAdapter(autoCompleteTextView, editText);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (autoCompleteTextView.getText().toString().length() == 0) {
                                            Snackbar.make(coordinatorLayout, "Please fill in the school!", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            editText.setText(String.valueOf(DBConn.getSchoolCodes(String.valueOf(autoCompleteTextView.getText()))));
                                            dialog.dismiss();
                                        }

                                    }
                                });

                                ///IF NEW SCHOOL IS ON SPOT
                                btnEnterNewSchool.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (editText_School_Other.getText().toString().length() <= 4) {
                                            Snackbar.make(coordinatorLayout, "Please fill the school name!", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            boolean check = DBConn.insert_NewSchoolList(editText_School_Other.getText().toString(),
                                                    editText_School_Other.getText().toString());
                                            if (check) {
                                                editText.setText(editText_School_Other.getText().toString());
                                                dialog.dismiss();

                                            } else {
                                                Snackbar.make(coordinatorLayout, "Inserting New School Failed", Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });

                                dialog.show();
                            } else if ((Integer.valueOf(getValueFromSelectedTableRow_SelectedCell(view, 7))) >= 24) {
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                                editText.setText("97");
                            } else if ((getValueFromSelectedTableRow_SelectedCell(view, 6)).equals("0")) {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            } else {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;

                    }
                });
            }
            //D7
            else if (i == 11) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (getValueFromSelectedTableRow_SelectedCell(view, 10).equals("0") || getValueFromSelectedTableRow_SelectedCell(view, 10).length() == 0) {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "School is empty!", Snackbar.LENGTH_LONG).show();
                            } else if (getValueFromSelectedTableRow_SelectedCell(view, 10).equals("97")) {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "School is not applicable!", Snackbar.LENGTH_LONG).show();
                            } else if ((Integer.valueOf(getValueFromSelectedTableRow_SelectedCell(view, 7))) >= 3 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) < 25) {

                                initInflateCustomDialog_OthersHere(view, R.layout.dialog_f_modetransport,
                                        R.id.rdGroup_dialog_F_ModeTransport,
                                        R.id.txtViewOthers_f_Transport);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "School is empty!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }

            //E8
            else if (i == 12) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14) {
                                initInflateCustomDialogsHere(view, R.layout.dialog_h_fishing, R.id.rdGroup_dialog_H_Fishing);

                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E9
            else if (i == 13) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14) {
                                initInflateCustomDialog_OthersHere(view, R.layout.dialog_f_modetransport, R.id.rdGroup_dialog_F_ModeTransport, R.id.txtViewOthers_f_Transport);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E10
            else if (i == 14) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 17) {
                                //WE DONT WANT TO CALL THE METHOD HERE BACAUSE WERE
                                // USING AUTOCOMPLETE_EDITTEXT TO LOAD DATA FROM DATABASEFROM
                                //SO GO MANUAL
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                                final View alertView = getLayoutInflater().inflate(R.layout.dialog_cooperative_list, null);
                                mBuilder.setView(alertView);
                                final AlertDialog dialog = mBuilder.create();

                                final AutoCompleteTextView autoCompleteTextView = alertView.findViewById(R.id.AutoComplete_txtViewCooperativeCodes);
                                final EditText editText_Cooperation_Other = alertView.findViewById(R.id.txtViewOthers_New_Cooperative);
                                Button btnGetCoop_Code = alertView.findViewById(R.id.btnSearchCooperativeCode);
                                Button btnEnterNewCoop = alertView.findViewById(R.id.btnAddCooperativeCode);

                                final EditText editText_CooperationName = view.findViewById(view.getId());
                                final RadioGroup radioGroup = alertView.findViewById(R.id.rdGroup_dialog_Custom_YesNo_Cooperative);
                                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        RadioButton radioButton = alertView.findViewById(radioGroup.getCheckedRadioButtonId());
                                        editText_CooperationName.setText(String.valueOf(radioButton.getHint()));
                                        dialog.dismiss();

                                    }
                                });

                                loadDatabaseCooperationCodesToAdapter(autoCompleteTextView, editText_CooperationName);
                                btnGetCoop_Code.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        editText_CooperationName.setText(String.valueOf(DBConn.getCooperationCodes(String.valueOf(autoCompleteTextView.getText()))));
                                        dialog.dismiss();
                                        indexes = new Integer[]{15};
                                        setDefaultValue_AnchorQuestions(view, indexes, "97");
                                    }
                                });

                                // IF NEW COOP ON SPOT
                                btnEnterNewCoop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (editText_Cooperation_Other.getText().toString().length() <= 4) {
                                            Snackbar.make(coordinatorLayout, "Please fill the cooperative name!", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            boolean check = DBConn.insert_NewCooperativeList(editText_Cooperation_Other.getText().toString(),
                                                    editText_Cooperation_Other.getText().toString());
                                            if (check) {
                                                editText_CooperationName.setText(editText_Cooperation_Other.getText().toString());
                                                dialog.dismiss();
                                                indexes = new Integer[]{15};
                                                setDefaultValue_AnchorQuestions(view, indexes, "97");
                                            } else {
                                                Snackbar.make(coordinatorLayout, "Inserting New Cooperative Failed", Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                });

                                dialog.show();
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;

                        }
                        return false;
                    }
                });
            }
            //E10.a
            else if (i == 15) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());

                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 17) {
                                if (getValueFromSelectedTableRow_SelectedCell(view, 14).equals("97")) {
                                    initInflateCustomDialog_OthersHere(view, R.layout.dialog_i_typeofcooperative, R.id.rdGroup_dialog_I_TypeofCooperation, R.id.txtViewOthers_I_Cooperation);
                                } else {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Already a member!!", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E11
            else if (i == 16) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14) {
                                initInflateCustomDialog_OthersHere(view,
                                        R.layout.dialog_j_credit_facility,
                                        R.id.rdGroup_dialog_J_CREDIT_FACILITY,
                                        R.id.txtViewOthers_J_CREDIT);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E12
            else if (i == 17) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14) {

                                initInflateCustomDialog_OthersHere(view,
                                        R.layout.dialog_k_livelihood,
                                        R.id.rdGroup_dialog_K_Livelihood,
                                        R.id.txtViewOthers_K_LIVELIHOOD);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E13
            else if (i == 18) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);

                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            final EditText editText = view.findViewById(view.getId());

                            //CHECK IF AGE QUESTION IS EMPTY
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            }
                            //IF AGE IS GREATER THAN 14
                            else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14)
                            {
                                // CHECK IF E12 IS EMPTY
                                if (getValueFromSelectedTableRow_SelectedCell(view, 17).length() == 0)
                                {
                                    Snackbar.make(coordinatorLayout, "E12 must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else
                                    {
                                        initInflateCustomDialogsHere(view, R.layout.dialog_l_finance, R.id.rdGroup_dialog_L_Finance);
                                        txtViewTBL.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (editText.getText().toString().equals("2")) {
                                                    indexes = new Integer[]{19};
                                                    setDefaultValue_AnchorQuestions(view, indexes, "97");
                                                }
                                                else
                                                {
                                                    indexes = new Integer[]{19};
                                                    setDefaultValue_AnchorQuestions(view, indexes, "");
                                                }

                                            }
                                        });
                                }
                            }

                            else {

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E13.a
            else if (i == 19) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());

                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14) {
                                if (getValueFromSelectedTableRow_SelectedCell(view, 18).length() == 0) {
                                    Snackbar.make(coordinatorLayout, "E13 empty!", Snackbar.LENGTH_LONG).show();
                                } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 18)) == 2) {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable", Snackbar.LENGTH_LONG).show();
                                } else {
                                    initInflateCustomDialogsHere(view, R.layout.dialog_m_amount, R.id.rdGroup_dialog_M_Amount);
                                }
                            } else {

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //E14
            else if (i == 20) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) > 14) {
                                //INFLATE CUSTOM DIALOG HERE
                                //WE DONT WANT TO CALL THE METHOD HERE
                                //SO GO MANUAL
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                                final View alertView = getLayoutInflater().inflate(R.layout.dialog_e16_business_present, null);
                                mBuilder.setView(alertView);
                                final AlertDialog dialog = mBuilder.create();

                                final RadioGroup rdGroup_dialogCustom = alertView.findViewById(R.id.rdGroup_dialog_E16_business);
                                final EditText viewEditTextTable = view.findViewById(view.getId());
                                final EditText editText_Capitalization = alertView.findViewById(R.id.txtViewOthers_e16_Business);
                                editText_Capitalization.setInputType(InputType.TYPE_CLASS_NUMBER);
                                rdGroup_dialogCustom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        RadioButton radioButton = alertView.findViewById(rdGroup_dialogCustom.getCheckedRadioButtonId());
                                        viewEditTextTable.setText(String.valueOf(radioButton.getHint()));
                                        dialog.dismiss();
                                    }
                                });
                                editText_Capitalization.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

                                editText_Capitalization.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        if (rdGroup_dialogCustom.getCheckedRadioButtonId() == -1) {
                                            viewEditTextTable.setText(editText_Capitalization.getText().toString());
                                        } else {
                                            rdGroup_dialogCustom.clearCheck();
                                            viewEditTextTable.setText(editText_Capitalization.getText().toString());
                                        }
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                    }
                                });

                                dialog.show();
                                return true;
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit", Snackbar.LENGTH_LONG).show();
                            }

                        }

                        return false;
                    }
                });
            }
            //F15
            else if (i == 21) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            final EditText editText = view.findViewById(view.getId());
                            initInflateCustomDialogsHere(view, R.layout.dialog_custom_radiochoose, R.id.rdGroup_dialog_Custom_YesNo);
                            txtViewTBL.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if (editText.getText().toString().equals("2")) {
                                        indexes = new Integer[]{22};
                                        setDefaultValue_AnchorQuestions(view, indexes, "97");
                                    }
                                    else if (editText.getText().toString().equals("1")) {
                                        indexes = new Integer[]{22};
                                        setDefaultValue_AnchorQuestions(view, indexes, "");
                                    }
                                    else
                                    {
                                        indexes = new Integer[]{22};
                                        setDefaultValue_AnchorQuestions(view, indexes, "97");
                                    }

                                }
                            });
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F15.a
            else if (i == 22) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            final EditText editText = view.findViewById(view.getId());
                            if (getValueFromSelectedTableRow_SelectedCell(view, 21).equals("1")) {
                                initInflateCustomDialogsHere(view, R.layout.dialog_n_tbdiagnosis, R.id.rdGroup_dialog_N_TB);

                            }
                            else if (getValueFromSelectedTableRow_SelectedCell(view,21).length() == 0)
                            {
                                Snackbar.make(coordinatorLayout, "Please answer F15 Question", Snackbar.LENGTH_LONG).show();
                            }

                            else {

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Skip this!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F16
            else if (i == 23) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            initInflateCustomDialog_OthersHere(view,
                                    R.layout.dialog_f_modetransport,
                                    R.id.rdGroup_dialog_F_ModeTransport,
                                    R.id.txtViewOthers_f_Transport);
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17
            else if (i == 24) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG, "F17");

                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.a
            else if (i == 25) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());

                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {

                                if (getValueFromSelectedTableRow_SelectedCell(view, 24).length() == 0) {
                                    Snackbar.make(coordinatorLayout, "F17 must be provided!", Snackbar.LENGTH_LONG).show();
                                } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 24)) == 2) {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable!", Snackbar.LENGTH_LONG).show();
                                } else {
                                    //INFLATE CUSTOM DIALOG HERE
                                    //WE DONT WANT TO CALL THE METHOD HERE BACAUSE WERE USING TWO EDITTEXT FROM THE CUSTOM DIALOG
                                    //SO GO MANUAL
                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                                    final View alertView = getLayoutInflater().inflate(R.layout.dialog_o_placeofdelivery, null);
                                    mBuilder.setView(alertView);
                                    final AlertDialog dialog = mBuilder.create();

                                    final RadioGroup rdGroup_dialogCustom = alertView.findViewById(R.id.rdGroup_dialog_O_PlaceofDelivery);
                                    final EditText viewEditTextTable = view.findViewById(view.getId());
                                    final EditText editTextOtherPublic = alertView.findViewById(R.id.txtViewOthers_O_Public);
                                    final EditText editTextOtherPrivate = alertView.findViewById(R.id.txtViewOthers_O_Private);
                                    rdGroup_dialogCustom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                            RadioButton radioButton = alertView.findViewById(rdGroup_dialogCustom.getCheckedRadioButtonId());
                                            viewEditTextTable.setText(String.valueOf(radioButton.getHint()));
                                            dialog.dismiss();
                                        }
                                    });
                                    editTextOtherPublic.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                                    editTextOtherPrivate.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                                    editTextOtherPublic.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            if (rdGroup_dialogCustom.getCheckedRadioButtonId() == -1) {
                                                viewEditTextTable.setText(editTextOtherPublic.getText().toString());
                                            } else {
                                                rdGroup_dialogCustom.clearCheck();
                                                viewEditTextTable.setText(editTextOtherPublic.getText().toString());
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });

                                    editTextOtherPrivate.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            if (rdGroup_dialogCustom.getCheckedRadioButtonId() == -1) {
                                                viewEditTextTable.setText(editTextOtherPrivate.getText().toString());
                                            } else {
                                                rdGroup_dialogCustom.clearCheck();
                                                viewEditTextTable.setText(editTextOtherPrivate.getText().toString());
                                            }

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });

                                    dialog.show();
                                }

                            } else {

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.b
            else if (i == 26) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogsHere(view,
                                        R.layout.dialog_p_birthattendant,
                                        R.id.rdGroup_dialog_P_BirthAttendant);
                                return true;
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }

                        }
                        return false;
                    }
                });
            }
            //F17.c
            else if (i == 27) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Did " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " undergo newborn screening?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }

                        return false;
                    }
                });
            }
            //F17.d
            else if (i == 28) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogsHere(view,
                                        R.layout.dialog_q_child_immunization,
                                        R.id.rdGroup_dialog_Q_Child_Immunization);
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E1
            else if (i == 29) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received BCG vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }

                });
            }
            //F17.E2
            else if (i == 30) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received Penta1 vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E3
            else if (i == 31) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received Penta2  vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E4
            else if (i == 32) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received Penta3 vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E5
            else if (i == 33) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received OPV1 vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E6
            else if (i == 34) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received OPV2 vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E7
            else if (i == 35) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received OPV3 vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.E8
            else if (i == 36) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received HEPAB1 vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }

            //F17.E9
            else if (i == 37) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " received Measles vaccination?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F17.f
            else if (i == 38) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Was " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " exclusively breastfed?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F18
            else if (i == 39) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 6) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Is " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " currently sick with measles?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F19
            else if (i == 40) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(final View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if (Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 6) {
                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Was " + String.format(getValueFromSelectedTableRow_SelectedCell(view, 1)) + " given Vitamin A supplementation 6 months prior?");
                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F20
            else if ( i == 41 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 ) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 7 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 1 ) {

                                initInflateCustomDialogs_RadioGroupYESNO(view,
                                        R.layout.dialog_custom_radiochoose,
                                        R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                        "Did "+ String.format(getValueFromSelectedTableRow_SelectedCell(view,1))+" have dental check-up in the past 6 months?");

                            } else {
                                EditText editText = view.findViewById(view.getId());
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F21
            else if ( i == 42 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            final EditText editText = view.findViewById(view.getId());
                            if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 ) {

                                initInflateCustomDialogsHere(view,
                                        R.layout.dialog_r_avoidpregnancy,
                                        R.id.rdGroup_dialog_R_AvoidPregnancy);

                                txtViewTBL.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged (CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged (CharSequence s, int start, int before, int count) {
                                        if (getValueFromSelectedTableRow_SelectedCell(view, 42).length()==0)
                                        {
                                            Snackbar.make(coordinatorLayout, "F21 must be provided!", Snackbar.LENGTH_LONG).show();
                                        }
                                        else if (editText.getText().toString().equals("98"))
                                        {
                                            indexes = new Integer[] {43};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }
                                        else  if (Integer.parseInt(editText.getText().toString())<=4)
                                        {
                                            indexes = new Integer[] {43};
                                            setDefaultValue_AnchorQuestions(view, indexes, "97");
                                        }
                                        else
                                        {

                                        }
                                    }

                                    @Override
                                    public void afterTextChanged (Editable s) {

                                    }
                                });

                            } else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) == 0 ) {

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit! Not Applicable", Snackbar.LENGTH_LONG).show();
                            }

                            else if (getValueFromSelectedTableRow_SelectedCell(view, 42).length()==0)
                            {
                                Snackbar.make(coordinatorLayout, "F21 must be provided!", Snackbar.LENGTH_LONG).show();
                            }
                            else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 42)) == 98 ||
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 42)) == 99)
                            {
                                indexes = new Integer[] {43};
                                setDefaultValue_AnchorQuestions(view, indexes, "97");

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Not applicable ", Snackbar.LENGTH_LONG).show();
                            }
                            else {

                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }

            //F21.a
            else if ( i == 43 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());

                            if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 ) {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 )
                            {
                                if (getValueFromSelectedTableRow_SelectedCell(view, 42).length()==0)
                                {
                                    Snackbar.make(coordinatorLayout, "F21 must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 42)) <= 4 ) {
                                    Snackbar.make(coordinatorLayout, "Not applicable for natural methods!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 42)) == 98 ||
                                        Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 42)) == 99)
                                {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not applicable ", Snackbar.LENGTH_LONG).show();
                                }
                                else {

                                    initInflateCustomDialogsHere(view,
                                            R.layout.dialog_s_methodplace,
                                            R.id.rdGroup_dialog_S_PlaceMethodObtained);
                                }
                            } else {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F22
            else if ( i == 44 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        EditText editText = view.findViewById(view.getId());
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 ) {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            } else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 ) {

                                if (getValueFromSelectedTableRow_SelectedCell(view, 3).length()==0)
                                {
                                    Snackbar.make(coordinatorLayout, "Sex must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 3)) == 4 ) {
                                    initInflateCustomDialogsHere(view,
                                            R.layout.dialog_p_birthattendant,
                                            R.id.rdGroup_dialog_P_BirthAttendant);
                                } else {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable for male", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //F22.a
            else if ( i == 45 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener((view, motionEvent) -> {
                    if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        EditText editText = view.findViewById(view.getId());

                        if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 ) {
                            Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                        } else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 ) {
                            if (getValueFromSelectedTableRow_SelectedCell(view, 44).length()==0)
                            {
                                Snackbar.make(coordinatorLayout, "F22 must be provided!", Snackbar.LENGTH_LONG).show();
                            }
                            else if ( getValueFromSelectedTableRow_SelectedCell(view, 44).length() == 0 )
                            {
                                Snackbar.make(coordinatorLayout, "Please answer F22: Is __ currently pregnant?", Snackbar.LENGTH_LONG).show();
                            }
                            else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 44)) == 97 )
                            {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Not Currently Pregnant", Snackbar.LENGTH_LONG).show();
                            }
                            else
                            {
                                if (getValueFromSelectedTableRow_SelectedCell(view, 3).length()==0)
                                {
                                    Snackbar.make(coordinatorLayout, "Sex must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 3)) == 4 )
                                {
                                    initInflateCustomDialogs_RadioGroupYESNO(view,
                                            R.layout.dialog_custom_radiochoose,
                                            R.id.rdGroup_dialog_Custom_YesNo, R.id.txtViewRadioG,
                                            "Was "+ String.format(getValueFromSelectedTableRow_SelectedCell(view,1))+" given Tetanus Toxoid/Tetanus Diptheria vaccine?");
                                }
                                else
                                {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable for male", Snackbar.LENGTH_LONG).show();
                                }

                            }
                        } else {
                            editText.setText("97");
                            Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                        }
                        return true;
                    }
                    return false;
                });
            }
            //F22.b
            else if ( i == 46 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());

                            if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 )
                            {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            }
                            else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 )
                            {
                                if (getValueFromSelectedTableRow_SelectedCell(view, 3).length()==0)
                                {
                                    Snackbar.make(coordinatorLayout, "Sex must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 3)) == 4 )
                                {
                                    initInflateCustomDialogs_EditText(view, R.layout.dialog_custom_edittext, R.id.txtbox_Custom_Edittext,
                                            R.id.txtView_Edittext_Custom, InputType.TYPE_CLASS_NUMBER, "Enter number");
                                }
                                else
                                {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable for male", Snackbar.LENGTH_LONG).show();
                                }

                            }
                            else
                            {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }

            //F23
            else if ( i == 47 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());
                            if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 )
                            {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            }
                            else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 )
                            {
                                if (getValueFromSelectedTableRow_SelectedCell(view, 3).length()==0)
                                {
                                    Snackbar.make(coordinatorLayout, "Sex must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 3)) == 4 )
                                {
                                    initInflateCustomDialogs_EditText(view, R.layout.dialog_custom_edittext, R.id.txtbox_Custom_Edittext,
                                            R.id.txtView_Edittext_Custom, InputType.TYPE_CLASS_NUMBER, "Enter number");
                                } else
                                {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable for male", Snackbar.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }

                            return true;
                        }
                        return false;
                    }
                });
            }
            //F24
            else if ( i == 48 ) {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_NULL);
                txtViewTBL.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch (final View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            EditText editText = view.findViewById(view.getId());
                            if ( getValueFromSelectedTableRow_SelectedCell(view, 7).length() == 0 )
                            {
                                Snackbar.make(coordinatorLayout, "Please enter the age!", Snackbar.LENGTH_LONG).show();
                            }
                            else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) <= 49 &&
                                    Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 7)) >= 10 )
                            {

                                if (getValueFromSelectedTableRow_SelectedCell(view, 3).length()==0)
                                {
                                    Snackbar.make(coordinatorLayout, "Sex must be provided!", Snackbar.LENGTH_LONG).show();
                                }
                                else if ( Integer.parseInt(getValueFromSelectedTableRow_SelectedCell(view, 3)) == 4 )
                                {
                                    initInflateCustomDialogs_RadioGroupYESNO(view,
                                            R.layout.dialog_custom_radiochoose_2,
                                            R.id.rdGroup_dialog_Custom_YesNo_2, R.id.txtViewRadioG,
                                            "Does "+ String.format(getValueFromSelectedTableRow_SelectedCell(view,1))+" want additional children?");
                                }
                                else
                                {
                                    editText.setText("97");
                                    Snackbar.make(coordinatorLayout, "Not Applicable for male", Snackbar.LENGTH_LONG).show();
                                }

                            } else {
                                editText.setText("97");
                                Snackbar.make(coordinatorLayout, "Age Limit!", Snackbar.LENGTH_LONG).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
            //The rest are number codes
            else {
                txtViewTBL.setBackgroundResource(R.drawable.border);
                txtViewTBL.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                txtViewTBL.setPadding(25, 25, 25, 25);
                txtViewTBL.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }
        catch ( Exception e)
        {

        }
    }

    private void removeLastTableRow()
    {
        try {
            // Remove Last row
            //TOTAL DEFAULT ROW = 2
            if ( totalRow >2 )
            {
                tbllayout_Main.removeView(tbllayout_Main.getChildAt(totalRow-1));
                totalRow = totalRow - 1;
                totalCell = totalCell - 49;

                //HERE THE CALCULATION
                // LOOP GOAL IS TO GET THE CURRENT BASE CELL VALUE WAY UP TO MAX CELL VALUE AND REMOVE THEM
                // ---> THE TOTAL ROW WHICH IS 2 in DEFAULT
                // ---> Current Total ROW - 3, 3 is TO GET THE LAST ROW TOTAL (EX. current TOTAL ROW = (4-3) *49
                //---> 49 is the total cell per row
                for (int i = ((totalRow-3) * 49 ); i < totalCell ; i++)
                {
                    editTextsLists.remove(i);

                }
                Snackbar.make(coordinatorLayout, editTextsLists.size() +":Total Row: " + totalRow, Snackbar.LENGTH_LONG).show();
            }

            if (totalRow ==2)
            {
                editTextsLists.clear();
                Snackbar.make(coordinatorLayout, editTextsLists.size() +":Total Row: " + totalRow, Snackbar.LENGTH_LONG).show();
            }
        }
        catch ( Exception e )
        {
            Log.i(tag,"REMOVE_LAST_ROWErrorMsg:"+ e);
            //WE SET TO CLEAR THE LIST IF THE CALCULATION ABOVE REACH -1
            editTextsLists.clear();
        }
    }

    private void removeAllRows()
    {
        if ( tbllayout_Main.getChildCount()+1 >2 ) {
            for(int i = tbllayout_Main.getChildCount()+1; i >1;i--)
            {
                tbllayout_Main.removeView(tbllayout_Main.getChildAt(i));

            }

            totalRow =2;
            totalCell = 0;
            editTextsLists.clear();
        }
    }

    private void clearAll_AFTER_SUBMISSION()
    {
        // CLEAR ALL ENTRYS AFTER SUBMISSION
        txtboxHCN.setText("");
        txtRespondentName.setText("");
        removeAllRows();

        radioGroup_G.clearCheck();
        txtBoxG26.setText("");
        txtBoxH27.setText("");

        txtBoxH28Meat.setText("");
        txtBoxH28Seafoods.setText("");
        txtBoxH28Processed.setText("");
        txtBoxH28Fruits.setText("");
        txtBoxH28Vege.setText("");

        radioGroup_H29.clearCheck();
        txtBoxH29_Others.setText("");

        radioGroup_I30.clearCheck();
        radioGroup_I31.clearCheck();
        radioGroup_J32.check(radioGroup_J32.getChildAt(1).getId());
        radioGroup_K33.check(radioGroup_K33.getChildAt(1).getId());
        radioGroup_L34.check(radioGroup_L34.getChildAt(1).getId());
        radioGroup_L35.check(radioGroup_L35.getChildAt(1).getId());

         int[] checkboxesM36_Act1 = {R.id.chkboxM36_Roads, R.id.chkboxM36_Drainage, R.id.chkboxM36_School,R.id.chkboxM36_Health,R.id.chkboxM36_DayCare,R.id.chkboxM36_Water,
                 R.id.chkboxM36_Multi,R.id.chkboxM36_Flood,R.id.chkboxM36_Government,R.id.chkboxM36_Bridges,R.id.chkboxM36_StreetLights,R.id.chkboxM36_Solar,R.id.chkboxM36_Covered,
                 R.id.chkboxM36_Basketball};

         for (int m36=0;m36 < checkboxesM36_Act1.length;m36++)
         {
             CheckBox m36ChkBoxes = findViewById(checkboxesM36_Act1[m36]);
             m36ChkBoxes.setChecked(false);
         }

        int[] checkboxesM37_Act1 = {R.id.chkboxM37_Roads, R.id.chkboxM37_Drainage, R.id.chkboxM37_School,R.id.chkboxM37_Health,R.id.chkboxM37_DayCare,R.id.chkboxM37_Water,
                R.id.chkboxM37_Multi,R.id.chkboxM37_Flood,R.id.chkboxM37_Government,R.id.chkboxM37_Bridges,R.id.chkboxM37_StreetLights,R.id.chkboxM37_Solar,R.id.chkboxM37_Covered,
                R.id.chkboxM37_Basketball};

        for (int m37=0;m37 < checkboxesM37_Act1.length;m37++)
        {
            CheckBox m37ChkBoxes = findViewById(checkboxesM37_Act1[m37]);
            m37ChkBoxes.setChecked(false);
        }

        radioGroup_N38.check(radioGroup_N38.getChildAt(1).getId());

        txtboxO39Economic.setText("");
        txtboxO39Education.setText("");
        txtboxO39Environment.setText("");
        txtboxO39Health.setText("");
        txtboxO39House.setText("");
        txtboxO39Infrastructure.setText("");
        txtboxO39Labor.setText("");
        txtboxO39Peace.setText("");
        txtboxO39Social.setText("");
        txtboxO39Transport.setText("");

        validations();
        txtboxHCN.requestFocus();
    }

    private void clearAll_GO_Questions()
    {
        // CLEAR ALL ENTRYS AFTER SUBMISSION
        radioGroup_G.clearCheck();
        txtBoxG26.setText("");
        txtBoxH27.setText("");

        txtBoxH28Meat.setText("");
        txtBoxH28Seafoods.setText("");
        txtBoxH28Processed.setText("");
        txtBoxH28Fruits.setText("");
        txtBoxH28Vege.setText("");

        radioGroup_H29.clearCheck();
        txtBoxH29_Others.setText("");

        radioGroup_I30.clearCheck();
        radioGroup_I31.clearCheck();
        radioGroup_J32.check(radioGroup_J32.getChildAt(1).getId());
        radioGroup_K33.check(radioGroup_K33.getChildAt(1).getId());
        radioGroup_L34.check(radioGroup_L34.getChildAt(1).getId());
        radioGroup_L35.check(radioGroup_L35.getChildAt(1).getId());

        int[] checkboxesM36_Act1 = {R.id.chkboxM36_Roads, R.id.chkboxM36_Drainage, R.id.chkboxM36_School,R.id.chkboxM36_Health,R.id.chkboxM36_DayCare,R.id.chkboxM36_Water,
                R.id.chkboxM36_Multi,R.id.chkboxM36_Flood,R.id.chkboxM36_Government,R.id.chkboxM36_Bridges,R.id.chkboxM36_StreetLights,R.id.chkboxM36_Solar,R.id.chkboxM36_Covered,
                R.id.chkboxM36_Basketball};

        for (int m36=0;m36 < checkboxesM36_Act1.length;m36++)
        {
            CheckBox m36ChkBoxes = findViewById(checkboxesM36_Act1[m36]);
            m36ChkBoxes.setChecked(false);
        }

        int[] checkboxesM37_Act1 = {R.id.chkboxM37_Roads, R.id.chkboxM37_Drainage, R.id.chkboxM37_School,R.id.chkboxM37_Health,R.id.chkboxM37_DayCare,R.id.chkboxM37_Water,
                R.id.chkboxM37_Multi,R.id.chkboxM37_Flood,R.id.chkboxM37_Government,R.id.chkboxM37_Bridges,R.id.chkboxM37_StreetLights,R.id.chkboxM37_Solar,R.id.chkboxM37_Covered,
                R.id.chkboxM37_Basketball};

        for (int m37=0;m37 < checkboxesM37_Act1.length;m37++)
        {
            CheckBox m37ChkBoxes = findViewById(checkboxesM37_Act1[m37]);
            m37ChkBoxes.setChecked(false);
        }

        radioGroup_N38.check(radioGroup_N38.getChildAt(1).getId());

        txtboxO39Economic.setText("");
        txtboxO39Education.setText("");
        txtboxO39Environment.setText("");
        txtboxO39Health.setText("");
        txtboxO39House.setText("");
        txtboxO39Infrastructure.setText("");
        txtboxO39Labor.setText("");
        txtboxO39Peace.setText("");
        txtboxO39Social.setText("");
        txtboxO39Transport.setText("");

        validations();
        txtboxHCN.requestFocus();
    }


    private boolean check_ifEmptyTableCell()
    {
        //TOTAL DEFAULT ROW = 2
        if ( totalRow == 2 )
        {
            return false;
        }
        else
        {
            String[] strings = new String[editTextsLists.size()];
            for (int i = 0; i < editTextsLists.size(); i++) {
                strings[ i ] = editTextsLists.get(i).getText().toString();

                if (strings[i].length()==0)
                {
                    return true;
                }
            }
            return false;
        }

    }

    private void saveToFinalDatabase()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Submit entry");
        alertDialog.setMessage("Are you sure?");
        alertDialog.setIcon(R.drawable.citycen);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                final String[] strings = new String[ editTextsLists.size() ];

                validate_EMPTY_O39Edittext();

                if ( txtboxHCN.getText().toString().equals("") )
                {
                    txtboxHCN.requestFocus();
                    Snackbar.make(coordinatorLayout,"Household Control Number empty!",Snackbar.LENGTH_LONG).show();
                }
                else if (DBConn.checkHHIDDuplicationFINAL(txtboxHCN.getText().toString()))
                {
                    txtboxHCN.requestFocus();
                    Snackbar.make(coordinatorLayout,"This HCN is already submitted!",Snackbar.LENGTH_LONG).show();
                }
                else if (check_ifEmptyTableCell()) {
                    Snackbar.make(coordinatorLayout,editTextsLists.size() + ": Total Please fill empty boxes!",Snackbar.LENGTH_LONG).show();
                }
                else if ( txtRespondentName.getText().toString().equals(""))
                {
                    txtRespondentName.requestFocus();
                    Snackbar.make(coordinatorLayout,"Responded Name empty!",Snackbar.LENGTH_LONG).show();
                }
                else if (totalRow == 2)
                {
                    Snackbar.make(coordinatorLayout,"Please fill out A - F Questions!",Snackbar.LENGTH_LONG).show();
                }

                else if (txtBoxG26.getText().toString().length() < 3)
                {
                    txtBoxG26.requestFocus();
                    Snackbar.make(coordinatorLayout,"G26 Year Constructed must be filled in!",Snackbar.LENGTH_LONG).show();
                }
                else if (txtBoxH27.getText().toString().equals("") || txtBoxH27.getText().toString().equals("0"))
                {
                    txtBoxH27.requestFocus();
                    Snackbar.make(coordinatorLayout,"H27 must be filled in!",Snackbar.LENGTH_LONG).show();

                }
                else if (mySettings.getBarangayCode(MainActivity.this).equals("null") || mySettings.getBarangayCode(MainActivity.this).equals(""))
                {
                    Snackbar.make(coordinatorLayout,"Barangay Code is empty! Please choose your designated barangay code",Snackbar.LENGTH_LONG).show();
                }
                else if (checkO39_Duplicates)
                {
                    txtboxO39Education.requestFocus();
                    Snackbar.make(coordinatorLayout,"O39 Priority Number must be unique!",Snackbar.LENGTH_LONG).show();
                }
                else
                {

                    for ( int i = 0; i < editTextsLists.size(); i++ )
                    {
                        strings[ i ] = editTextsLists.get(i).getText().toString();
                    }

                    collectGOAnswers();


                    Tasks.executeInBackground(MainActivity.this, new BackgroundWork<Object>() {
                        @Override
                        public Object doInBackground() throws Exception {

                            DBConn.insertDataBF(strings, totalRow, txtboxHCN.getText().toString(), mySettings.getCurrentDate(), mySettings.getBarangayCode(MainActivity.this));
                            DBConn.insertDataGO(integersRadioButtons, stringsEditText, stringsCheckBoxes,
                                    txtboxHCN.getText().toString(), mySettings.getCurrentDate(), mySettings.getBarangayCode(MainActivity.this));
                            DBConn.insertRespondentName(txtboxHCN.getText().toString(),mySettings.getCurrentDate(), txtRespondentName.getText().toString());
                            DBConn.insertTransactionHere(mySettings.getCurrentDate(), mySettings.getCurrentTime(),
                                    "Household Control Number " + txtboxHCN.getText().toString() + " is inserted by " + mySettings.getInterviewer(MainActivity.this));
                            return null;
                        }
                    }, new Completion<Object>() {
                        @Override
                        public void onSuccess(Context context, Object result) {
                            Snackbar.make(coordinatorLayout,"Entry Submitted Successfully",Snackbar.LENGTH_LONG).show();
                            DBConn.remove_TEMP_HCN_AFTER_SUBMIT(txtboxHCN.getText().toString());


                            clearAll_AFTER_SUBMISSION();
                            loadDatabaseHHIDToAdapter();


                        }

                        @Override
                        public void onError(Context context, Exception e) {
                            Snackbar.make(coordinatorLayout,e.toString()+" Entry Submission Failed",Snackbar.LENGTH_LONG).show();
                            Log.d(TAG,e.toString());
                        }
                    });

                }

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();


    }

    private void saveToTempDatabase()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Save and Continue");
        alertDialog.setMessage("Are you sure?");
        alertDialog.setIcon(R.drawable.citycen);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final String[] strings = new String[ editTextsLists.size()];
                boolean checker_Duplicate = DBConn.checkHHIDDuplicationTEMP(txtboxHCN.getText().toString());
                boolean check_FINAL_HCN = DBConn.checkHHIDDuplicationFINAL(txtboxHCN.getText().toString());

                if ( txtboxHCN.getText().toString().equals("") )
                {
                    txtboxHCN.requestFocus();
                    Snackbar.make(coordinatorLayout,"Household Control Number empty!",Snackbar.LENGTH_LONG).show();
                }
                else if (check_FINAL_HCN)
                {
                    txtboxHCN.requestFocus();
                    Snackbar.make(coordinatorLayout,"This HCN is already submitted!",Snackbar.LENGTH_LONG).show();
                }
                else if (checker_Duplicate)
                {
                    //IF HCN DATA IS ALREADY ON THE TEMPORARY TABLE
                    //UPDATE IT

                    for ( int i = 0; i < editTextsLists.size(); i++ )
                    {
                        strings[ i ] = editTextsLists.get(i).getText().toString();
                    }
                    collectGOAnswers();


                    Tasks.executeInBackground(MainActivity.this, new BackgroundWork<Object>() {
                        @Override
                        public Object doInBackground() throws Exception {
                            //FIRST REMOVE IT
                            DBConn.remove_TEMP_HCN_AFTER_SUBMIT(txtboxHCN.getText().toString());

                            //INSERT IT AGAIN
                            checkBFTablesInsertTEMP= DBConn.insert_TEMPDataBF(strings, totalRow, txtboxHCN.getText().toString(), mySettings.getCurrentDate());
                            checkGOTablesInsertTEMP= DBConn.insert_TEMPDataGO(integersRadioButtons, stringsEditText, stringsCheckBoxes,
                                    txtboxHCN.getText().toString(), mySettings.getCurrentDate(), mySettings.getBarangayCode(MainActivity.this));
                            DBConn.insert_TEMPRespondentName(txtboxHCN.getText().toString(),mySettings.getCurrentDate(), txtRespondentName.getText().toString());
                            DBConn.insertTransactionHere(mySettings.getCurrentDate(), mySettings.getCurrentTime(),
                                    "Temporary Household Control Number " + txtboxHCN.getText().toString() + " is updated by " + mySettings.getInterviewer(MainActivity.this));
                            return null;
                        }
                    }, new Completion<Object>() {
                        @Override
                        public void onSuccess(Context context, Object result) {
                            Snackbar.make(coordinatorLayout,"Temporary Entry Updated Successfully",Snackbar.LENGTH_LONG).show();
                            loadDatabaseHHIDToAdapter();
                        }

                        @Override
                        public void onError(Context context, Exception e) {
                            Snackbar.make(coordinatorLayout,e.toString()+" Updating Entry Failed",Snackbar.LENGTH_LONG).show();
                            Log.d(TAG,e.toString());
                        }
                    });

                }
                else if (totalRow == 2)
                {
                    Snackbar.make(coordinatorLayout,"Please fill out A - F Questions!",Snackbar.LENGTH_LONG).show();
                }
                else if ( txtRespondentName.getText().toString().equals(""))
                {
                    txtRespondentName.requestFocus();
                    Snackbar.make(coordinatorLayout,"Responded Name empty!",Snackbar.LENGTH_LONG).show();
                }

                else
                {
                    for ( int i = 0; i < editTextsLists.size(); i++ )
                    {
                        strings[ i ] = editTextsLists.get(i).getText().toString();
                    }
                    collectGOAnswers();

                    Tasks.executeInBackground(MainActivity.this, new BackgroundWork<Object>() {
                        @Override
                        public Object doInBackground() throws Exception {
                            checkBFTablesInsertTEMP= DBConn.insert_TEMPDataBF(strings, totalRow, txtboxHCN.getText().toString(), mySettings.getCurrentDate());
                            checkGOTablesInsertTEMP= DBConn.insert_TEMPDataGO(integersRadioButtons, stringsEditText, stringsCheckBoxes,
                                    txtboxHCN.getText().toString(), mySettings.getCurrentDate(), mySettings.getBarangayCode(MainActivity.this));
                            DBConn.insert_TEMPRespondentName(txtboxHCN.getText().toString(),mySettings.getCurrentDate(), txtRespondentName.getText().toString());
                            DBConn.insertTransactionHere(mySettings.getCurrentDate(), mySettings.getCurrentTime(),
                                    "Temporary Household Control Number " + txtboxHCN.getText().toString() + " is inserted by " + mySettings.getInterviewer(MainActivity.this));
                            return null;
                        }
                    }, new Completion<Object>() {
                        @Override
                        public void onSuccess(Context context, Object result) {
                            Snackbar.make(coordinatorLayout,"Temporary Entry Saved Successfully",Snackbar.LENGTH_LONG).show();
                            loadDatabaseHHIDToAdapter();
                        }

                        @Override
                        public void onError(Context context, Exception e) {
                            //Snackbar.make(coordinatorLayout,e.toString()+"Temporary Entry Submission Failed",Snackbar.LENGTH_LONG).show();
                            Log.d(TAG,e.toString());
                        }
                    });

                }

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void collectGOAnswers ()
    {

        //-----------HERE TO COLLECT THE DATA IN G to O Questions-------
        //------------------------------------------------------------------------
        switch ( radioGroup_G.getCheckedRadioButtonId() ) {
            case R.id.rdBoxG25_MajorRepair:
                RadioButtonsGOList.add(0, 1);
                break;
            case R.id.rdBoxG25_Dilapidated:
                RadioButtonsGOList.add(0, 2);
                break;
            case R.id.rdBoxG25_SoundStructure:
                RadioButtonsGOList.add(0, 3);
                break;
            case - 1:
                RadioButtonsGOList.add(0, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_H29.getCheckedRadioButtonId() ) {
            case R.id.rdboxH29_A:
                RadioButtonsGOList.add(1, 1);
                break;
            case R.id.rdboxH29_B:
                RadioButtonsGOList.add(1, 2);
                break;
            case R.id.rdboxH29_C:
                RadioButtonsGOList.add(1, 3);
                break;
            case R.id.rdboxH29_D:
                RadioButtonsGOList.add(1, 4);
                break;
            case R.id.rdboxH29_E:
                RadioButtonsGOList.add(1, 5);
                break;
            case - 1:
                RadioButtonsGOList.add(1, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_I30.getCheckedRadioButtonId() ) {
            case R.id.rdboxL30_A:
                RadioButtonsGOList.add(2, 1);
                break;
            case R.id.rdboxL30_B:
                RadioButtonsGOList.add(2, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(2, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_I31.getCheckedRadioButtonId() ) {
            case R.id.rdboxL31_A:
                RadioButtonsGOList.add(3, 1);
                break;
            case R.id.rdboxL31_B:
                RadioButtonsGOList.add(3, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(3, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_J32.getCheckedRadioButtonId() ) {
            case R.id.rdboxJ32_A:
                RadioButtonsGOList.add(4, 1);
                break;
            case R.id.rdboxJ32_B:
                RadioButtonsGOList.add(4, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(4, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_K33.getCheckedRadioButtonId() ) {
            case R.id.rdboxK33_YES:
                RadioButtonsGOList.add(5, 1);
                break;
            case R.id.rdboxK33_NO:
                RadioButtonsGOList.add(5, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(5, 0);
                break;
        }

        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_K33_B.getCheckedRadioButtonId() ) {
            case R.id.rdboxK33B_Cage:
                RadioButtonsGOList.add(6, 1);
                break;
            case R.id.rdboxK33B_Tying:
                RadioButtonsGOList.add(6, 2);
                break;
            case R.id.rdboxK33B_Free:
                RadioButtonsGOList.add(6, 3);

                break;
            case - 1:
                RadioButtonsGOList.add(6, 0);
                break;
        }

        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_K33_C.getCheckedRadioButtonId() ) {
            case R.id.rdboxK33C_Yes:
                RadioButtonsGOList.add(7, 1);
                break;
            case R.id.rdboxK33C_But:
                RadioButtonsGOList.add(7, 2);
                break;
            case R.id.rdboxK33C_No:
                RadioButtonsGOList.add(7, 3);
                break;
            case - 1:
                RadioButtonsGOList.add(7, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_L34.getCheckedRadioButtonId() ) {
            case R.id.rdboxL34_Yes:
                RadioButtonsGOList.add(8, 1);
                break;
            case R.id.rdboxL34_No:
                RadioButtonsGOList.add(8, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(8, 0);
                break;
        }
        //------------------------------------------------------------------------

        switch ( radioGroup_L35.getCheckedRadioButtonId() ) {
            case R.id.rdboxL35_Yes:
                RadioButtonsGOList.add(9, 1);
                break;

            case R.id.rdboxL35_No:
                RadioButtonsGOList.add(9, 2);
                break;

            case - 1:
                RadioButtonsGOList.add(9, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_N38.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38_Yes:
                RadioButtonsGOList.add(10, 1);
                break;

            case R.id.rdboxN38_No:
                RadioButtonsGOList.add(10, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(10, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_N38A.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38A_Daily:
                RadioButtonsGOList.add(11, 1);
                break;

            case R.id.rdboxN38A_Weekly:
                RadioButtonsGOList.add(11, 2);
                break;
            case R.id.rdboxN38A_Monthly:
                RadioButtonsGOList.add(11, 3);
                break;
            case R.id.rdboxN38A_Quarterly:
                RadioButtonsGOList.add(11, 4);
                break;
            case R.id.rdboxN38A_Annually:
                RadioButtonsGOList.add(11, 5);
                break;
            case - 1:
                RadioButtonsGOList.add(11, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_N38B.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38B_Yes:
                RadioButtonsGOList.add(12, 1);
                break;
            case R.id.rdboxN38B_No:
                RadioButtonsGOList.add(12, 2);
                break;
            case - 1:
                RadioButtonsGOList.add(12, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_N38C.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38C_Early:
                RadioButtonsGOList.add(13, 1);
                break;
            case R.id.rdboxN38C_Expected:
                RadioButtonsGOList.add(13,2);
                break;
            case R.id.rdboxN38C_Beyond:
                RadioButtonsGOList.add(13, 3);
                break;
            case - 1:
                RadioButtonsGOList.add(13, 0);
                break;
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        switch ( radioGroup_N38D.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38D_All:
                RadioButtonsGOList.add(14, 1);
                break;

            case R.id.rdboxN38D_Majority:
                RadioButtonsGOList.add(14, 2);
                break;

            case R.id.rdboxN38D_OnlyFew:
                RadioButtonsGOList.add(14, 3);
                break;

            case R.id.rdboxN38D_None:
                RadioButtonsGOList.add(14, 4);
                break;

            case - 1:
                RadioButtonsGOList.add(14, 0);
                break;
        }
        //------------------------------------------------------------------------


        //------------------------------------------------------------------------
        switch ( radioGroup_N38E.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38E_Customer:
                RadioButtonsGOList.add(15, 1);
                break;

            case R.id.rdboxN38E_Behavioral:
                RadioButtonsGOList.add(15, 2);
                break;

            case R.id.rdboxN38E_Time:
                RadioButtonsGOList.add(15, 3);
                break;

            case R.id.rdboxN38E_Mastery:
                RadioButtonsGOList.add(15, 4);
                break;

            case - 1:
                RadioButtonsGOList.add(15, 0);
                break;
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        switch ( radioGroup_N38F.getCheckedRadioButtonId() ) {
            case R.id.rdboxN38F_Yes:
                RadioButtonsGOList.add(16, 1);
                break;

            case R.id.rdboxN38F_No:
                RadioButtonsGOList.add(16, 2);
                break;

            case - 1:
                RadioButtonsGOList.add(16, 0);
                break;
        }
        //------------------------------------------------------------------------



        //------------------------------------------------------------------------
        if ( TextUtils.isEmpty(txtBoxG26.getText().toString()) ) {
            EditTextGOList.add(0, "0");
        } else {
            EditTextGOList.add(0, txtBoxG26.getText().toString());
        }
        if ( TextUtils.isEmpty(txtBoxH27.getText().toString()) ) {
            EditTextGOList.add(1, "0");
        } else {
            EditTextGOList.add(1, txtBoxH27.getText().toString());
        }

        //////////////////////////

        if ( TextUtils.isEmpty(txtBoxH28Meat.getText().toString()) ) {
            EditTextGOList.add(2, "0");
        } else {
            EditTextGOList.add(2, txtBoxH28Meat.getText().toString());
        }

        if ( TextUtils.isEmpty(txtBoxH28Seafoods.getText().toString()) ) {
            EditTextGOList.add(3, "0");
        } else {
            EditTextGOList.add(3, txtBoxH28Seafoods.getText().toString());
        }
        if ( TextUtils.isEmpty(txtBoxH28Processed.getText().toString()) ) {
            EditTextGOList.add(4, "0");
        } else {
            EditTextGOList.add(4, txtBoxH28Processed.getText().toString());
        }
        if ( TextUtils.isEmpty(txtBoxH28Fruits.getText()) ) {
            EditTextGOList.add(5, "0");
        } else {
            EditTextGOList.add(5, txtBoxH28Fruits.getText().toString());
        }
        if ( TextUtils.isEmpty(txtBoxH28Vege.getText()) ) {
            EditTextGOList.add(6, "0");
        } else {
            EditTextGOList.add(6, txtBoxH28Vege.getText().toString());
        }
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------


        if ( TextUtils.isEmpty(txtBoxH29_Others.getText().toString()) ) {
            EditTextGOList.add(7, "N/A");
        } else {
            EditTextGOList.add(7, txtBoxH29_Others.getText().toString());
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        if ( TextUtils.isEmpty(txtboxJ32Prawn.getText().toString()) ) {
            EditTextGOList.add(8, "0");
        } else {
            EditTextGOList.add(8, txtboxJ32Prawn.getText().toString());
        }
        if ( TextUtils.isEmpty(txtboxJ32Hito.getText().toString()) ) {
            EditTextGOList.add(9, "0");
        } else {
            EditTextGOList.add(9, txtboxJ32Hito.getText().toString());
        }
        if ( TextUtils.isEmpty(txtboxJ32Pangasius.getText().toString()) ) {
            EditTextGOList.add(10, "0");
        } else {
            EditTextGOList.add(10, txtboxJ32Pangasius.getText().toString());
        }
        if ( TextUtils.isEmpty(txtboxJ32Bangus.getText().toString()) ) {
            EditTextGOList.add(11, "0");
        } else {
            EditTextGOList.add(11, txtboxJ32Bangus.getText().toString());
        }
        if ( TextUtils.isEmpty(txtboxJ32Tilapia.getText().toString()) ) {
            EditTextGOList.add(12, "0");
        } else {
            EditTextGOList.add(12, txtboxJ32Tilapia.getText().toString());
        }
        if ( TextUtils.isEmpty(txtboxJ32Others.getText().toString()) ) {
            EditTextGOList.add(13, "0");
        } else {
            EditTextGOList.add(13, txtboxJ32Others.getText().toString());
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------

        if ( TextUtils.isEmpty(txtboxK33Dog.getText().toString()) ) {
            EditTextGOList.add(14, "0");
        } else {
            EditTextGOList.add(14, txtboxK33Dog.getText().toString());
        }
        if ( TextUtils.isEmpty(txtboxK33Cats.getText().toString()) ) {
            EditTextGOList.add(15, "0");
        } else {
            EditTextGOList.add(15, txtboxK33Cats.getText().toString());
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------

        if ( TextUtils.isEmpty(txtboxL34Other.getText().toString()) ) {
            EditTextGOList.add(16, "N/A");
        } else {
            EditTextGOList.add(16, txtboxL34Other.getText().toString());
        }

        //------------------------------------------------------------------------

        if ( TextUtils.isEmpty(txtboxL34BOthers.getText().toString()) ) {
            EditTextGOList.add(17, "N/A");
        } else {
            EditTextGOList.add(17, txtboxL34BOthers.getText().toString());
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        if ( TextUtils.isEmpty(txtBoxL35AOthers.getText().toString()) ) {
            EditTextGOList.add(18, "N/A");
        } else {
            EditTextGOList.add(18, txtBoxL35AOthers.getText().toString());
        }

        //------------------------------------------------------------------------
        EditText txtBoxM36A_Others = findViewById(R.id.txtboxM36A_Others);
        if ( TextUtils.isEmpty(txtBoxM36A_Others.getText().toString()) ) {
            EditTextGOList.add(19, "N/A");
        } else {

            EditTextGOList.add(19, txtBoxM36A_Others.getText().toString());
        }

        //------------------------------------------------------------------------
        EditText txtBoxM37_Others = findViewById(R.id.txtboxM37_Others);
        if ( TextUtils.isEmpty(txtBoxM37_Others.getText().toString()) ) {
            EditTextGOList.add(20, "N/A");
        } else {
            EditTextGOList.add(20, txtBoxM37_Others.getText().toString());
        }

        //------------------------------------------------------------------------
        txtBoxN38EOthers = findViewById(R.id.txtboxN38E_Others);
        if ( TextUtils.isEmpty(txtBoxN38EOthers.getText().toString()) ) {
            EditTextGOList.add(21, "N/A");
        } else {
            EditTextGOList.add(21, txtBoxN38EOthers.getText().toString());
        }


        //------------------------------------------------------------------------

        if ( TextUtils.isEmpty(txtboxO39Education.getText().toString()) ) {
            EditTextGOList.add(22, "0");
        } else {
            EditTextGOList.add(22, txtboxO39Education.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Peace.getText().toString()) ) {
            EditTextGOList.add(23, "0");
        } else {
            EditTextGOList.add(23, txtboxO39Peace.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Health.getText().toString()) ) {
            EditTextGOList.add(24, "0");
        } else {
            EditTextGOList.add(24, txtboxO39Health.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Labor.getText().toString()) ) {
            EditTextGOList.add(25, "0");
        } else {
            EditTextGOList.add(25, txtboxO39Labor.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Economic.getText().toString()) ) {
            EditTextGOList.add(26, "0");
        } else {
            EditTextGOList.add(26, txtboxO39Economic.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Transport.getText().toString()) ) {
            EditTextGOList.add(27, "0");
        } else {
            EditTextGOList.add(27, txtboxO39Transport.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39House.getText().toString()) ) {
            EditTextGOList.add(28, "0");
        } else {
            EditTextGOList.add(28, txtboxO39House.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Social.getText().toString()) ) {
            EditTextGOList.add(29, "0");
        } else {
            EditTextGOList.add(29, txtboxO39Social.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Infrastructure.getText().toString()) ) {
            EditTextGOList.add(30, "0");
        } else {
            EditTextGOList.add(30, txtboxO39Infrastructure.getText().toString());
        }

        if ( TextUtils.isEmpty(txtboxO39Environment.getText().toString()) ) {
            EditTextGOList.add(31, "0");
        } else {
            EditTextGOList.add(31, txtboxO39Environment.getText().toString());
        }
        //------------------------------------------------------------------------

        //CHECKBOXES LISTS
        //------------------------------------------------------------------------------------------------
        if (chkboxL34A_School.isChecked())
        {
            CheckboxesGOList.add(0, "1");
        }
        else
        {
            CheckboxesGOList.add(0, "0");
        }

        if (chkboxL34A_GovAgencies.isChecked())
        {
            CheckboxesGOList.add(1, "1");
        }
        else
        {
            CheckboxesGOList.add(1, "0");
        }

        if (chkboxL34A_NonGov.isChecked())
        {
            CheckboxesGOList.add(2, "1");
        }
        else
        {
            CheckboxesGOList.add(2, "0");
        }


        //------------------------------------------------------------------------
        CheckBox L34B_CBDRRM, L34B_FirstAid, L34B_BLS, L34B_Fire, L34B_Search;
        L34B_CBDRRM = findViewById(R.id.chkboxL34_BRCRP);
        L34B_FirstAid = findViewById(R.id.chkboxL34B_FirstAid);
        L34B_BLS = findViewById(R.id.chkboxL34B_BLS);
        L34B_Fire = findViewById(R.id.chkboxL34B_FireFight);
        L34B_Search = findViewById(R.id.chkboxL34B_SearchRescue);


        if (L34B_CBDRRM.isChecked())
        {
            CheckboxesGOList.add(3, "1");
        }
        else
        {
            CheckboxesGOList.add(3, "0");
        }

        if (L34B_FirstAid.isChecked())
        {
            CheckboxesGOList.add(4, "1");
        }
        else
        {
            CheckboxesGOList.add(4, "0");
        }

        if (L34B_BLS.isChecked())
        {
            CheckboxesGOList.add(5, "1");
        }
        else
        {
            CheckboxesGOList.add(5, "0");
        }

        if (L34B_Fire.isChecked())
        {
            CheckboxesGOList.add(6, "1");
        }
        else
        {
            CheckboxesGOList.add(6, "0");
        }

        if (L34B_Search.isChecked()) {
            CheckboxesGOList.add(7, "1");
        }
        else
        {
            CheckboxesGOList.add(7, "0");
        }


        //------------------------------------------------------------------------


        if (chkboxL35Radio.isChecked()) {
            CheckboxesGOList.add(8, "1");
        } else {
            CheckboxesGOList.add(8, "0");
        }

        if (chkboxL35TV.isChecked()) {
            CheckboxesGOList.add(9, "1");
        } else {
            CheckboxesGOList.add(9, "0");
        }

        if (chkboxL35Pub.isChecked()) {
            CheckboxesGOList.add(10, "1");
        } else {
            CheckboxesGOList.add(10, "0");
        }

        if (chkboxL35SMS.isChecked()) {
            CheckboxesGOList.add(11, "1");
        } else {
            CheckboxesGOList.add(11, "0");
        }

        if (chkboxL35Siren.isChecked()) {
            CheckboxesGOList.add(12, "1");
        } else {
            CheckboxesGOList.add(12, "0");
        }

        if (chkboxL35Internet.isChecked()) {
            CheckboxesGOList.add(13, "1");
        } else {
            CheckboxesGOList.add(13, "0");
        }

        //---------------------------------------------------------------------------



        chkboxM36Roads = findViewById(R.id.chkboxM36_Roads);
        if (chkboxM36Roads.isChecked()) {
            CheckboxesGOList.add(14, "1");
        } else {
            CheckboxesGOList.add(14, "0");
        }
        chkboxM36Drainage = findViewById(R.id.chkboxM36_Drainage);
        if (chkboxM36Drainage.isChecked()) {
            CheckboxesGOList.add(15, "1");
        } else {
            CheckboxesGOList.add(15, "0");
        }
        chkboxM36School = findViewById(R.id.chkboxM36_School);
        if (chkboxM36School.isChecked()) {
            CheckboxesGOList.add(16, "1");
        } else {
            CheckboxesGOList.add(16, "0");
        }
        chkboxM36Health = findViewById(R.id.chkboxM36_Health);
        if (chkboxM36Health.isChecked()) {
            CheckboxesGOList.add(17, "1");
        } else {
            CheckboxesGOList.add(17, "0");
        }
        chkboxM36DayCare = findViewById(R.id.chkboxM36_DayCare);
        if (chkboxM36DayCare.isChecked()) {
            CheckboxesGOList.add(18, "1");
        } else {
            CheckboxesGOList.add(18, "0");
        }
        chkboxM36Water = findViewById(R.id.chkboxM36_Water);
        if (chkboxM36Water.isChecked()) {
            CheckboxesGOList.add(19, "1");
        } else {
            CheckboxesGOList.add(19, "0");
        }
        chkboxM36Multi = findViewById(R.id.chkboxM36_Multi);
        if (chkboxM36Multi.isChecked()) {
            CheckboxesGOList.add(20, "1");
        } else {
            CheckboxesGOList.add(20, "0");
        }
        chkboxM36Flood = findViewById(R.id.chkboxM36_Flood);
        if (chkboxM36Flood.isChecked()) {
            CheckboxesGOList.add(21, "1");
        } else {
            CheckboxesGOList.add(21, "0");
        }
        chkboxM36Govern = findViewById(R.id.chkboxM36_Government);
        if (chkboxM36Govern.isChecked()) {
            CheckboxesGOList.add(22, "1");
        } else {
            CheckboxesGOList.add(22, "0");
        }
        chkboxM36Bridges = findViewById(R.id.chkboxM36_Bridges);
        if (chkboxM36Bridges.isChecked()) {
            CheckboxesGOList.add(23, "1");
        } else {
            CheckboxesGOList.add(23, "0");
        }
        chkboxM36StreetLights = findViewById(R.id.chkboxM36_StreetLights);
        if (chkboxM36StreetLights.isChecked()) {
            CheckboxesGOList.add(24, "1");
        } else {
            CheckboxesGOList.add(24, "0");
        }
        chkboxM36SolarDrier = findViewById(R.id.chkboxM36_Solar);
        if (chkboxM36SolarDrier.isChecked()) {
            CheckboxesGOList.add(25, "1");
        } else {
            CheckboxesGOList.add(25, "0");
        }
        chkboxM36Court = findViewById(R.id.chkboxM36_Covered);
        if (chkboxM36Court.isChecked()) {
            CheckboxesGOList.add(26, "1");
        } else {
            CheckboxesGOList.add(26, "0");
        }
        chkboxM36Basketball = findViewById(R.id.chkboxM36_Basketball);
        if (chkboxM37Basketball.isChecked()) {
            CheckboxesGOList.add(27, "1");
        } else {
            CheckboxesGOList.add(27, "0");
        }

        //------------------------------------------------------------------------

        //------------------------------------------------------------------------



        chkboxM37Roads = findViewById(R.id.chkboxM37_Roads);
        if (chkboxM37Roads.isChecked()) {
            CheckboxesGOList.add(28, "1");
        } else {
            CheckboxesGOList.add(28, "0");
        }
        chkboxM37Drainage = findViewById(R.id.chkboxM37_Drainage);
        if (chkboxM37Drainage.isChecked()) {
            CheckboxesGOList.add(29, "1");
        } else {
            CheckboxesGOList.add(29, "0");
        }
        chkboxM37School = findViewById(R.id.chkboxM37_School);
        if (chkboxM37School.isChecked()) {
            CheckboxesGOList.add(30, "1");
        } else {
            CheckboxesGOList.add(30, "0");
        }
        chkboxM37Health = findViewById(R.id.chkboxM37_Health);
        if (chkboxM37Health.isChecked()) {
            CheckboxesGOList.add(31, "1");
        } else {
            CheckboxesGOList.add(31, "0");
        }
        chkboxM37DayCare = findViewById(R.id.chkboxM37_DayCare);
        if (chkboxM37DayCare.isChecked()) {
            CheckboxesGOList.add(32, "1");
        } else {
            CheckboxesGOList.add(32, "0");
        }
        chkboxM37Water = findViewById(R.id.chkboxM37_Water);
        if (chkboxM37Water.isChecked()) {
            CheckboxesGOList.add(33, "1");
        } else {
            CheckboxesGOList.add(33, "0");
        }
        chkboxM37Multi = findViewById(R.id.chkboxM37_Multi);
        if (chkboxM37Multi.isChecked()) {
            CheckboxesGOList.add(34, "1");
        } else {
            CheckboxesGOList.add(34, "0");
        }
        chkboxM37Flood = findViewById(R.id.chkboxM37_Flood);
        if (chkboxM37Flood.isChecked()) {
            CheckboxesGOList.add(35, "1");
        } else {
            CheckboxesGOList.add(35, "0");
        }
        chkboxM37Govern = findViewById(R.id.chkboxM37_Government);
        if (chkboxM37Govern.isChecked()) {
            CheckboxesGOList.add(36, "1");
        } else {
            CheckboxesGOList.add(36, "0");
        }
        chkboxM37Bridges = findViewById(R.id.chkboxM37_Bridges);
        if (chkboxM37Bridges.isChecked()) {
            CheckboxesGOList.add(37, "1");
        } else {
            CheckboxesGOList.add(37, "0");
        }
        chkboxM37StreetLights = findViewById(R.id.chkboxM37_StreetLights);
        if (chkboxM37StreetLights.isChecked()) {
            CheckboxesGOList.add(38, "1");
        } else {
            CheckboxesGOList.add(38, "0");
        }
        chkboxM37SolarDrier = findViewById(R.id.chkboxM37_Solar);
        if (chkboxM37SolarDrier.isChecked()) {
            CheckboxesGOList.add(39, "1");
        } else {
            CheckboxesGOList.add(39, "0");
        }
        chkboxM37Court = findViewById(R.id.chkboxM37_Covered);
        if (chkboxM37Court.isChecked()) {
            CheckboxesGOList.add(40, "1");
        } else {
            CheckboxesGOList.add(40, "0");
        }
        chkboxM37Basketball = findViewById(R.id.chkboxM37_Basketball);
        if (chkboxM37Basketball.isChecked()) {
            CheckboxesGOList.add(41, "1");
        } else {
            CheckboxesGOList.add(41, "0");
        }


        integersRadioButtons = new Integer[ RadioButtonsGOList.size()];
        stringsEditText = new String[ EditTextGOList.size() ];
        stringsCheckBoxes = new String[ CheckboxesGOList.size() ];


        for ( int i = 0; i < RadioButtonsGOList.size(); i++ ) {
            integersRadioButtons[ i ] = RadioButtonsGOList.get(i);
        }
        for ( int i = 0; i < EditTextGOList.size(); i++ ) {
            stringsEditText[ i ] = EditTextGOList.get(i);
        }
        for ( int i = 0; i < CheckboxesGOList.size(); i++ ) {
            stringsCheckBoxes[ i ] = CheckboxesGOList.get(i);
        }

    }

    private void validations()
    {
        try
        {


            radioGroup_H29.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup group, int checkedId) {
                    if ( radioGroup_H29.getCheckedRadioButtonId() == - 1 ) {
                    } else {
                        txtBoxH29_Others.getText().clear();
                    }

                }
            });

            txtBoxH29_Others.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged (CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged (Editable editable) {
                    if (txtBoxH29_Others.getText().toString().equals("N/A"))
                    {

                    }
                    else
                    {
                        if ( radioGroup_H29.getCheckedRadioButtonId() == - 1 ) {

                        } else {
                            radioGroup_H29.clearCheck();
                        }
                    }

                }
            });

            RadioButton rdbJ32A_YES = findViewById(R.id.rdboxJ32_A);
            if (rdbJ32A_YES.isChecked())
            {
                txtboxJ32Prawn.setEnabled(true);
                txtboxJ32Hito.setEnabled(true);
                txtboxJ32Pangasius.setEnabled(true);
                txtboxJ32Bangus.setEnabled(true);
                txtboxJ32Tilapia.setEnabled(true);
                txtboxJ32Others.setEnabled(true);
            }
            else
            {
                txtboxJ32Prawn.setEnabled(false);
                txtboxJ32Hito.setEnabled(false);
                txtboxJ32Pangasius.setEnabled(false);
                txtboxJ32Bangus.setEnabled(false);
                txtboxJ32Tilapia.setEnabled(false);
                txtboxJ32Others.setEnabled(false);

                txtboxJ32Prawn.setText("");
                txtboxJ32Hito.setText("");
                txtboxJ32Pangasius.setText("");
                txtboxJ32Bangus.setText("");
                txtboxJ32Tilapia.setText("");
                txtboxJ32Others.setText("");
            }

            radioGroup_J32.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup group, int checkedId) {
                    switch ( radioGroup_J32.getCheckedRadioButtonId() ) {
                        case R.id.rdboxJ32_A:
                            txtboxJ32Prawn.setEnabled(true);
                            txtboxJ32Hito.setEnabled(true);
                            txtboxJ32Pangasius.setEnabled(true);
                            txtboxJ32Bangus.setEnabled(true);
                            txtboxJ32Tilapia.setEnabled(true);
                            txtboxJ32Others.setEnabled(true);
                            break;
                        case R.id.rdboxJ32_B:
                            txtboxJ32Prawn.setEnabled(false);
                            txtboxJ32Hito.setEnabled(false);
                            txtboxJ32Pangasius.setEnabled(false);
                            txtboxJ32Bangus.setEnabled(false);
                            txtboxJ32Tilapia.setEnabled(false);
                            txtboxJ32Others.setEnabled(false);

                            txtboxJ32Prawn.setText("");
                            txtboxJ32Hito.setText("");
                            txtboxJ32Pangasius.setText("");
                            txtboxJ32Bangus.setText("");
                            txtboxJ32Tilapia.setText("");
                            txtboxJ32Others.setText("");
                            break;

                    }
                }
            });

            RadioButton rdbK33_YES = findViewById(R.id.rdboxK33_YES);
            if (rdbK33_YES.isChecked())
            {
                txtboxK33Dog.setEnabled(true);
                txtboxK33Cats.setEnabled(true);
                for ( int i = 0; i < radioGroup_K33_B.getChildCount(); i++ ) {
                    radioGroup_K33_B.getChildAt(i).setEnabled(true);
                }

                for ( int i = 0; i < radioGroup_K33_C.getChildCount(); i++ ) {
                    radioGroup_K33_C.getChildAt(i).setEnabled(true);
                }
            }
            else
            {
                txtboxK33Dog.setEnabled(false);
                txtboxK33Cats.setEnabled(false);

                txtboxK33Dog.setText("");
                txtboxK33Cats.setText("");

                for ( int i = 0; i < radioGroup_K33_B.getChildCount(); i++ ) {
                    radioGroup_K33_B.getChildAt(i).setEnabled(false);
                }

                for ( int i = 0; i < radioGroup_K33_C.getChildCount(); i++ ) {
                    radioGroup_K33_C.getChildAt(i).setEnabled(false);
                }
            }

            radioGroup_K33.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup group, int checkedId) {
                    switch ( radioGroup_K33.getCheckedRadioButtonId() ) {
                        case R.id.rdboxK33_YES:
                            txtboxK33Dog.setEnabled(true);
                            txtboxK33Cats.setEnabled(true);
                            for ( int i = 0; i < radioGroup_K33_B.getChildCount(); i++ ) {
                                radioGroup_K33_B.getChildAt(i).setEnabled(true);

                            }

                            for ( int i = 0; i < radioGroup_K33_C.getChildCount(); i++ ) {
                                radioGroup_K33_C.getChildAt(i).setEnabled(true);
                            }

                            break;

                        case R.id.rdboxK33_NO:
                            txtboxK33Dog.setEnabled(false);
                            txtboxK33Cats.setEnabled(false);

                            txtboxK33Dog.setText("");
                            txtboxK33Cats.setText("");

                            for ( int i = 0; i < radioGroup_K33_B.getChildCount(); i++ ) {
                                radioGroup_K33_B.getChildAt(i).setEnabled(false);
                                radioGroup_K33_B.clearCheck();
                            }

                            for ( int i = 0; i < radioGroup_K33_C.getChildCount(); i++ ) {
                                radioGroup_K33_C.getChildAt(i).setEnabled(false);
                                radioGroup_K33_C.clearCheck();
                            }
                            break;
                    }
                }
            });

            RadioButton rdb_L34_YES = findViewById(R.id.rdboxL34_Yes);
            if (rdb_L34_YES.isChecked())
            {
                txtboxL34Other.setEnabled(true);
                txtBoxL35AOthers.setEnabled(true);

                chkboxL34A_School.setEnabled(true);
                chkboxL34A_GovAgencies.setEnabled(true);
                chkboxL34A_NonGov.setEnabled(true);

                chkboxL34B_CBDRRM.setEnabled(true);
                chkboxL34B_FirstAid.setEnabled(true);
                chkboxL34B_BLS.setEnabled(true);
                chkboxL34B_Fire.setEnabled(true);
                chkboxL34B_Search.setEnabled(true);

                txtboxL34BOthers.setEnabled(true);
            }
            else
            {
                txtboxL34Other.setEnabled(false);
                txtBoxL35AOthers.setEnabled(false);

                chkboxL34A_School.setEnabled(false);
                chkboxL34A_GovAgencies.setEnabled(false);
                chkboxL34A_NonGov.setEnabled(false);

                chkboxL34A_School.setChecked(false);
                chkboxL34A_GovAgencies.setChecked(false);
                chkboxL34A_NonGov.setChecked(false);

                chkboxL34B_CBDRRM.setEnabled(false);
                chkboxL34B_FirstAid.setEnabled(false);
                chkboxL34B_BLS.setEnabled(false);
                chkboxL34B_Fire.setEnabled(false);
                chkboxL34B_Search.setEnabled(false);

                chkboxL34B_CBDRRM.setChecked(false);
                chkboxL34B_FirstAid.setChecked(false);
                chkboxL34B_BLS.setChecked(false);
                chkboxL34B_Fire.setChecked(false);
                chkboxL34B_Search.setChecked(false);

                txtboxL34BOthers.setEnabled(false);
                txtboxL34BOthers.setText("");

            }

            radioGroup_L34.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup group, int checkedId) {
                    switch ( radioGroup_L34.getCheckedRadioButtonId() ) {
                        case R.id.rdboxL34_Yes:
                            txtboxL34Other.setEnabled(true);
                            txtBoxL35AOthers.setEnabled(true);

                            chkboxL34A_School.setEnabled(true);
                            chkboxL34A_GovAgencies.setEnabled(true);
                            chkboxL34A_NonGov.setEnabled(true);

                            chkboxL34B_CBDRRM.setEnabled(true);
                            chkboxL34B_FirstAid.setEnabled(true);
                            chkboxL34B_BLS.setEnabled(true);
                            chkboxL34B_Fire.setEnabled(true);
                            chkboxL34B_Search.setEnabled(true);

                            txtboxL34BOthers.setEnabled(true);
                            break;

                        case R.id.rdboxL34_No:
                            txtboxL34Other.setEnabled(false);
                            txtBoxL35AOthers.setEnabled(false);

                            chkboxL34A_School.setEnabled(false);
                            chkboxL34A_GovAgencies.setEnabled(false);
                            chkboxL34A_NonGov.setEnabled(false);

                            chkboxL34A_School.setChecked(false);
                            chkboxL34A_GovAgencies.setChecked(false);
                            chkboxL34A_NonGov.setChecked(false);

                            chkboxL34B_CBDRRM.setEnabled(false);
                            chkboxL34B_FirstAid.setEnabled(false);
                            chkboxL34B_BLS.setEnabled(false);
                            chkboxL34B_Fire.setEnabled(false);
                            chkboxL34B_Search.setEnabled(false);

                            chkboxL34B_CBDRRM.setChecked(false);
                            chkboxL34B_FirstAid.setChecked(false);
                            chkboxL34B_BLS.setChecked(false);
                            chkboxL34B_Fire.setChecked(false);
                            chkboxL34B_Search.setChecked(false);

                            txtboxL34BOthers.setEnabled(false);
                            txtboxL34BOthers.setText("");

                            break;

                    }
                }
            });


            txtBoxL35AOthers.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged (CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged (Editable editable) {
                    if (txtBoxL35AOthers.getText().toString().equals("N/A"))
                    {

                    }
                    else
                    {
                        chkboxL35Radio.setChecked(false);
                        chkboxL35TV.setChecked(false);
                        chkboxL35Pub.setChecked(false);
                        chkboxL35SMS.setChecked(false);
                        chkboxL35Internet.setChecked(false);
                    }


                }
            });

            RadioButton rdb_L35_YES = findViewById(R.id.rdboxL35_Yes);
            if (rdb_L35_YES.isChecked())
            {
                chkboxL35Radio.setEnabled(true);
                chkboxL35TV.setEnabled(true);
                chkboxL35Pub.setEnabled(true);
                chkboxL35SMS.setEnabled(true);
                chkboxL35Siren.setEnabled(true);
                chkboxL35Internet.setEnabled(true);
                txtBoxL35AOthers.setEnabled(true);
            }
            else
            {
                chkboxL35Radio.setEnabled(false);
                chkboxL35TV.setEnabled(false);
                chkboxL35Pub.setEnabled(false);
                chkboxL35SMS.setEnabled(false);
                chkboxL35Siren.setEnabled(false);
                chkboxL35Internet.setEnabled(false);
                txtBoxL35AOthers.setEnabled(false);

                chkboxL35Radio.setChecked(false);
                chkboxL35TV.setChecked(false);
                chkboxL35Pub.setChecked(false);
                chkboxL35SMS.setChecked(false);
                chkboxL35Siren.setChecked(false);
                chkboxL35Internet.setChecked(false);
                txtBoxL35AOthers.setText("");

            }


            radioGroup_L35.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup group, int checkedId) {
                    switch ( radioGroup_L35.getCheckedRadioButtonId() ) {
                        case R.id.rdboxL35_Yes:
                            chkboxL35Radio.setEnabled(true);
                            chkboxL35TV.setEnabled(true);
                            chkboxL35Pub.setEnabled(true);
                            chkboxL35SMS.setEnabled(true);
                            chkboxL35Siren.setEnabled(true);
                            chkboxL35Internet.setEnabled(true);
                            txtBoxL35AOthers.setEnabled(true);
                            break;
                        case R.id.rdboxL35_No:
                            chkboxL35Radio.setEnabled(false);
                            chkboxL35TV.setEnabled(false);
                            chkboxL35Pub.setEnabled(false);
                            chkboxL35SMS.setEnabled(false);
                            chkboxL35Siren.setEnabled(false);
                            chkboxL35Internet.setEnabled(false);
                            txtBoxL35AOthers.setEnabled(false);

                            chkboxL35Radio.setChecked(false);
                            chkboxL35TV.setChecked(false);
                            chkboxL35Pub.setChecked(false);
                            chkboxL35SMS.setChecked(false);
                            chkboxL35Siren.setChecked(false);
                            chkboxL35Internet.setChecked(false);
                            txtBoxL35AOthers.setText("");
                            break;
                    }
                }
            });

            //CHECK L38 CHECKBOXES
            if ( chkboxL35Radio.isChecked())
            {
                txtBoxL35AOthers.setText("");
            }

            if ( chkboxL35TV.isChecked())
            {
                txtBoxL35AOthers.setText("");
            }
            if ( chkboxL35Pub.isChecked())
            {
                txtBoxL35AOthers.setText("");
            }
            if ( chkboxL35SMS.isChecked())
            {
                txtBoxL35AOthers.setText("");
            }

            if ( chkboxL35Internet.isChecked())
            {
                txtBoxL35AOthers.setText("");
            }

            //***************************************************************************

            chkboxM37Roads.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Roads);
                }
            });


            chkboxM37Drainage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Drainage);
                }
            });
            chkboxM37School.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37School);
                }
            });
            chkboxM37Health.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Health);
                }
            });
            chkboxM37DayCare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37DayCare);
                }
            });
            chkboxM37Water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Water);
                }
            });
            chkboxM37Multi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Multi);
                }
            });

            chkboxM37Flood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Flood);
                }
            });

            chkboxM37Govern.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Govern);
                }
            });
            chkboxM37Bridges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Bridges);
                }
            });
            chkboxM37StreetLights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37StreetLights);
                }
            });
            chkboxM37SolarDrier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37SolarDrier);
                }
            });
            chkboxM37Court.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Court);
                }
            });
            chkboxM37Basketball.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_CounterM37_checkboxes(chkboxM37Basketball);
                }
            });


            RadioButton rdb_N38_YES = findViewById(R.id.rdboxN38_Yes);
            if (rdb_N38_YES.isChecked())
            {
                for ( int i = 0; i < radioGroup_N38A.getChildCount(); i++ ) {
                    radioGroup_N38A.getChildAt(i).setEnabled(true);
                }

                for ( int i = 0; i < radioGroup_N38B.getChildCount(); i++ ) {
                    radioGroup_N38B.getChildAt(i).setEnabled(true);
                }

                for ( int i = 0; i < radioGroup_N38C.getChildCount(); i++ ) {
                    radioGroup_N38C.getChildAt(i).setEnabled(true);
                }

                for ( int i = 0; i < radioGroup_N38D.getChildCount(); i++ ) {
                    radioGroup_N38D.getChildAt(i).setEnabled(true);
                }

                for ( int i = 0; i < radioGroup_N38E.getChildCount(); i++ ) {
                    radioGroup_N38E.getChildAt(i).setEnabled(true);
                }

                for ( int i = 0; i < radioGroup_N38F.getChildCount(); i++ ) {
                    radioGroup_N38F.getChildAt(i).setEnabled(true);
                }

                txtBoxN38EOthers.setEnabled(true);
            }
            else
            {
                for ( int i = 0; i < radioGroup_N38A.getChildCount(); i++ ) {
                    radioGroup_N38A.getChildAt(i).setEnabled(false);
                    radioGroup_N38A.clearCheck();
                }

                for ( int i = 0; i < radioGroup_N38B.getChildCount(); i++ ) {
                    radioGroup_N38B.getChildAt(i).setEnabled(false);
                    radioGroup_N38B.clearCheck();
                }

                for ( int i = 0; i < radioGroup_N38C.getChildCount(); i++ ) {
                    radioGroup_N38C.getChildAt(i).setEnabled(false);
                    radioGroup_N38C.clearCheck();
                }

                for ( int i = 0; i < radioGroup_N38D.getChildCount(); i++ ) {
                    radioGroup_N38D.getChildAt(i).setEnabled(false);
                    radioGroup_N38D.clearCheck();
                }

                for ( int i = 0; i < radioGroup_N38E.getChildCount(); i++ ) {
                    radioGroup_N38E.getChildAt(i).setEnabled(false);
                    radioGroup_N38E.clearCheck();
                }

                for ( int i = 0; i < radioGroup_N38F.getChildCount(); i++ ) {
                    radioGroup_N38F.getChildAt(i).setEnabled(false);
                    radioGroup_N38F.clearCheck();
                }

                txtBoxN38EOthers.setEnabled(false);
                txtBoxN38EOthers.setText("");
            }


            radioGroup_N38.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup radioGroup, int checkedID) {
                    switch ( radioGroup_N38.getCheckedRadioButtonId() ) {
                        case R.id.rdboxN38_Yes:
                            for ( int i = 0; i < radioGroup_N38A.getChildCount(); i++ ) {
                                radioGroup_N38A.getChildAt(i).setEnabled(true);

                            }

                            for ( int i = 0; i < radioGroup_N38B.getChildCount(); i++ ) {
                                radioGroup_N38B.getChildAt(i).setEnabled(true);

                            }

                            for ( int i = 0; i < radioGroup_N38C.getChildCount(); i++ ) {
                                radioGroup_N38C.getChildAt(i).setEnabled(true);

                            }

                            for ( int i = 0; i < radioGroup_N38D.getChildCount(); i++ ) {
                                radioGroup_N38D.getChildAt(i).setEnabled(true);

                            }

                            for ( int i = 0; i < radioGroup_N38E.getChildCount(); i++ ) {
                                radioGroup_N38E.getChildAt(i).setEnabled(true);

                            }

                            for ( int i = 0; i < radioGroup_N38F.getChildCount(); i++ ) {
                                radioGroup_N38F.getChildAt(i).setEnabled(true);

                            }

                            txtBoxN38EOthers.setEnabled(true);
                            break;

                        case R.id.rdboxN38_No:
                            for ( int i = 0; i < radioGroup_N38A.getChildCount(); i++ ) {
                                radioGroup_N38A.getChildAt(i).setEnabled(false);
                                radioGroup_N38A.clearCheck();
                            }

                            for ( int i = 0; i < radioGroup_N38B.getChildCount(); i++ ) {
                                radioGroup_N38B.getChildAt(i).setEnabled(false);
                                radioGroup_N38B.clearCheck();
                            }

                            for ( int i = 0; i < radioGroup_N38C.getChildCount(); i++ ) {
                                radioGroup_N38C.getChildAt(i).setEnabled(false);
                                radioGroup_N38C.clearCheck();
                            }

                            for ( int i = 0; i < radioGroup_N38D.getChildCount(); i++ ) {
                                radioGroup_N38D.getChildAt(i).setEnabled(false);
                                radioGroup_N38D.clearCheck();
                            }

                            for ( int i = 0; i < radioGroup_N38E.getChildCount(); i++ ) {
                                radioGroup_N38E.getChildAt(i).setEnabled(false);
                                radioGroup_N38E.clearCheck();
                            }

                            for ( int i = 0; i < radioGroup_N38F.getChildCount(); i++ ) {
                                radioGroup_N38F.getChildAt(i).setEnabled(false);
                                radioGroup_N38F.clearCheck();
                            }

                            txtBoxN38EOthers.setEnabled(false);
                            txtBoxN38EOthers.setText("");
                            break;
                    }

                }
            });


            radioGroup_N38E.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (RadioGroup radioGroup, int i) {
                    if ( txtBoxN38EOthers.length() > 0 ) {
                        txtBoxN38EOthers.getText().clear();
                    }
                }
            });

            txtBoxN38EOthers.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged (CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged (Editable editable) {
                    if (txtBoxN38EOthers.getText().toString().equals("N/A"))
                    {

                    }
                    else
                    {
                        if ( radioGroup_N38E.getCheckedRadioButtonId() == - 1 ) {

                        } else {
                            radioGroup_N38E.clearCheck();
                        }
                    }
                }
            });

            //------------------ HERE IS O43 LIST QUESTIONS --------------------------------------------------

            txtboxO39Education.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(
                                new EditText[] {txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic, txtboxO39Transport, txtboxO39House, txtboxO39Social,
                                txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Education.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {
                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Peace.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Health, txtboxO39Labor, txtboxO39Economic, txtboxO39Transport, txtboxO39House, txtboxO39Social,
                                txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Peace.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Health.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Labor, txtboxO39Economic, txtboxO39Transport, txtboxO39House, txtboxO39Social,
                                txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Health.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Labor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Economic, txtboxO39Transport, txtboxO39House, txtboxO39Social,
                                txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Labor.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        }
                        else
                        {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Economic.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor,
                                txtboxO39Transport, txtboxO39House, txtboxO39Social, txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Economic.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        }
                        else
                        {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }
                @Override
                public void afterTextChanged (Editable s) {
                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Transport.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic,
                                txtboxO39House, txtboxO39Social, txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Transport.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39House.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic,
                                txtboxO39Transport, txtboxO39Social, txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39House.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else
                        {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Social.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic,
                                txtboxO39Transport, txtboxO39House, txtboxO39Infrastructure, txtboxO39Environment}, Integer.parseInt(txtboxO39Social.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Infrastructure.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic,
                                txtboxO39Transport, txtboxO39House, txtboxO39Social, txtboxO39Environment}, Integer.parseInt(txtboxO39Infrastructure.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });

            //---------------------------------------------------------------------------------------------------------------------
            txtboxO39Environment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged (CharSequence s, int start, int before, int count) {
                    try {
                        boolean fieldCheck = validateO39_Edittext_values(new EditText[] {txtboxO39Education, txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic,
                                txtboxO39Transport, txtboxO39House, txtboxO39Social, txtboxO39Infrastructure}, Integer.parseInt(txtboxO39Environment.getText().toString()));
                        if (!fieldCheck) {
                            Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                            checkO39_Duplicates = true;
                        } else {
                            checkO39_Duplicates = false;
                        }
                    } catch ( NumberFormatException e ) {
                    }
                }

                @Override
                public void afterTextChanged (Editable s) {

                }
            });
            //---------------------------------------------------------------------------------------------------------------------------------
        }
        catch ( Exception e )
        {
            Log.i(tag ,"GOCollection:"+ e);
        }
    }


    private void check_CounterM37_checkboxes(CheckBox checkBox)
    {
        if (checkBox.isChecked())
        {

            if(counterM37_checkboxes <=3)
            {
                counterM37_checkboxes= counterM37_checkboxes + 1;
                checkBox.setChecked(true);
                Snackbar.make(coordinatorLayout,counterM37_checkboxes +" Limit",Snackbar.LENGTH_LONG).show();
            }
            else
            {
                checkBox.setChecked(false);
                Snackbar.make(coordinatorLayout,"Maximum Limit",Snackbar.LENGTH_LONG).show();
            }

        }
        else
        {
            counterM37_checkboxes = counterM37_checkboxes -1;
            Snackbar.make(coordinatorLayout,counterM37_checkboxes +" Limit",Snackbar.LENGTH_LONG).show();
        }
    }

    private void validate_EMPTY_O39Edittext()
    {
        try {
            boolean fieldCheck = validateO39_Edittext_Empty_Values(new EditText[] {txtboxO39Peace, txtboxO39Health, txtboxO39Labor, txtboxO39Economic, txtboxO39Transport, txtboxO39House, txtboxO39Social,
                    txtboxO39Infrastructure, txtboxO39Environment,txtboxO39Education});
            if (!fieldCheck)
            {
                Snackbar.make(coordinatorLayout, "This number is already entered!", Snackbar.LENGTH_LONG).show();
                checkO39_Duplicates = true;
            }
            else
            {
                checkO39_Duplicates = false;
            }
        }
        catch ( NumberFormatException e )
        {
        }
    }

    private boolean validateO39_Edittext_Empty_Values(EditText[] txtboxes)
    {
        for(int i = 0; i <  txtboxes.length; i++){
            EditText currentField =  txtboxes[i];
            if (currentField.getText().toString().equals(""))
            {
                checkO39_Duplicates =true;
                return false;
            }

        }
        checkO39_Duplicates = false;
        return true;
    }

    private boolean validateO39_Edittext_values (EditText[] txtboxes, int valueBox)
    {
        for(int i = 0; i <  txtboxes.length; i++){
            EditText currentField =  txtboxes[i];
            if (currentField.getText().toString().equals(""))
            {
                checkO39_Duplicates =true;
                return false;
            }
            else if(Integer.parseInt(currentField.getText().toString())== valueBox){
                checkO39_Duplicates =true;
                return false;
            }
        }
        checkO39_Duplicates = false;
        return true;
    }

    private void callDialog_settings()
    {
        Thread thread_DialogSettings = new Thread(new Runnable() {
            @Override
            public void run()
            {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //HERE IS THE CUSTOM DIALOG FOR THE SETTINGS WITH POSITIVE BUTTON
                        try {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                            View alertView = getLayoutInflater().inflate(R.layout.dialog_setting_enter_new_barangay, null);
                            mBuilder.setView(alertView);


                            txtboxSettings_BarangayCode = alertView.findViewById(R.id.txtBoxSettings_Barangay);
                            txtboxSettings_BarangayCode.setInputType(InputType.TYPE_NULL);
                            txtViewSettings_BarangayCode = alertView.findViewById(R.id.txtViewSetting_BarangayCode);

                            mBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick (DialogInterface dialog, int which) {
                                    mySettings.setBarangayCode((txtViewSettings_BarangayCode.getText().toString()));
                                    mySettings.setBarangayCode(MainActivity.this, String.valueOf(txtViewSettings_BarangayCode.getText()));

                                    Toast.makeText(MainActivity.this, "Saving New Settings", Toast.LENGTH_SHORT).show();


                                    //DISPLAY CHANGES IN NAVIGATION
                                    txtNavHeader.setText("Interviewer: "+mySettings.getInterviewer(MainActivity.this));
                                    txtNavHeader2.setText("Deployed at Brgy. " +DBConn.getBarangayName(mySettings.getBarangayCode(MainActivity.this)) );

                                    DBConn.insertTransactionHere(mySettings.getCurrentDate(),mySettings.getCurrentTime(),
                                            "Changes in settings, Now deployed at "+DBConn.getBarangayName(mySettings.getBarangayCode(MainActivity.this)));

                                }
                            });

                            txtboxSettings_BarangayCode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick (View view) {
                                    AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(MainActivity.this);
                                    final View alertView1 = getLayoutInflater().inflate(R.layout.dialog_barangay_choices, null);
                                    mBuilder1.setView(alertView1);
                                    final AlertDialog dialog1 = mBuilder1.create();

                                    final RadioGroup rdGroup_dialog_Barangays = alertView1.findViewById(R.id.rdGroup_dialog_Barangays);

                                    rdGroup_dialog_Barangays.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged (RadioGroup radioGroup, int i) {
                                            RadioButton radioButton = alertView1.findViewById(rdGroup_dialog_Barangays.getCheckedRadioButtonId());
                                            txtboxSettings_BarangayCode.setText(String.valueOf(radioButton.getText()));
                                            txtViewSettings_BarangayCode.setText(String.valueOf(radioButton.getHint()));
                                            dialog1.dismiss();
                                        }
                                    });
                                    dialog1.show();
                                }
                            });

                            txtboxSettings_BarangayCode.setText(String.valueOf(mySettings.getBarangayCode(MainActivity.this)));


                            final AlertDialog dialog = mBuilder.create();
                            dialog.show();
                        }
                        catch ( Exception e )
                        {
                            Log.i(tag,"ErrorMsg:"+ e);
                        }
                    }
                });
            }
        });
        thread_DialogSettings.start();

    }


}
