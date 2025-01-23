package darylgorra.citycenrider2018.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Originaly Created by Daryl on 10/23/16.
 * THIS CLASS IS USED TO INSERT DATA USING SQLLITE DATABASE
 */

public class databaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CityCenRider.db";
    //
    private static final String TABLE_NAME_TEMP_BF = "tbl_Temp_BF_Tables";
    private static final String TABLE_NAME_TEMP_GO = "tbl_Temp_GO_Tables";
    private static final String TABLE_NAME_TEMP_RespondentNames = "tbl_Temp_RespondentNames";
    //
    private static final String TABLE_NAME_BF = "tbl_BF_Tables";
    private static final String TABLE_NAME_GO = "tbl_GO_Tables";
    private static final String TABLE_NAME_RespondentNames = "tbl_RespondentNames";
    private static final String TABLE_NAME_InterviewerProfile= "tbl_InterviewerProfile";
    private static final String TABLE_NAME_SupervisorProfile= "tbl_SupervisorProfile";
    private static final String TABLE_NAME_SchoolCodes = "tbl_SchoolCodes";
    private static final String TABLE_NAME_CooperationCodes = "tbl_CooperationsCodes";
    private static final String TABLE_NAME_BarangayCodes = "tbl_BarangayCodes";
    private static final String TABLE_NAME_TransactionLog = "tbl_transactionLog";

    private static final String TAG = "CityCenRiderDatabase";


    //-------QUERIES HERE--------------------------
    //TEMPORARY TABLES
    private static final String create_TEMP_BFTable;
    static
    {
        create_TEMP_BFTable = ("CREATE TABLE " + TABLE_NAME_TEMP_BF + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Lastname TEXT,Firstname TEXT,Middlename TEXT, Sex INTEGER," +
                "C1 INTEGER,C2 INTEGER,C2_a INTEGER,C3 INTEGER,C4 INTEGER,C5 INTEGER," +
                "D6 INTEGER,D7 INTEGER,E8 INTEGER,E9 INTEGER,E10 INTEGER,E10_a INTEGER," +
                "E11 INTEGER,E12 INTEGER,E13 INTEGER,E13_a INTEGER," +
                "E14 INTEGER,F15 INTEGER,F15_a INTEGER,F16 INTEGER,F17 INTEGER,F17_a INTEGER,F17_b INTEGER,F17_c INTEGER,F17_d INTEGER," +
                "F17_BCG INTEGER,F17_Penta1 INTEGER,F17_Penta2 INTEGER,F17_Penta3 INTEGER,F17_OPV1 INTEGER,F17_OPV2 INTEGER," +
                "F17_OPV3 INTEGER,F17_HEPAB1 INTEGER,F17_MEASLES INTEGER,F17_f INTEGER," +
                "F18 INTEGER,F19 INTEGER,F20 INTEGER,F21 INTEGER,F21_a INTEGER,F22 INTEGER,F22_a INTEGER,F22_b INTEGER," +
                "F23 INTEGER,F24 INTEGER,HCN TEXT,DateEntered TEXT)");
    }

    private static final String create_TEMP_GOTable;
    static
    {
        create_TEMP_GOTable = ("CREATE TABLE " + TABLE_NAME_TEMP_GO + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HCN TEXT,DateEntered TEXT,G25 INTEGER,G26 INTEGER,H27 INTEGER," +
                "H28Meat INTEGER,H28Seafoods INTEGER,H28Processed INTEGER," +
                "H28Fruits INTEGER,H28Vegetables INTEGER,H29 INTEGER,H29_OTHERS TEXT," +
                "I30 INTEGER,I31 INTEGER,J32 INTEGER," +
                "J32_Prawn INTEGER,J32_Hito INTEGER,J32_Pangasius INTEGER,J32_Bangus INTEGER,J32_Tilapia INTEGER,J32_Others TEXT," +
                "K33 INTEGER,K33_Dogs INTEGER,K33_Cats INTEGER,K33B INTEGER,K33C INTEGER," +
                "L34 INTEGER,L34A_School,L34A_GovAgencies, L34A_NonGov,L34A_OTHERS TEXT," +
                "L34B_CBDRRM INTEGER,L34B_FirstAid INTEGER, L34B_BLS INTEGER,L34B_Fire INTEGER,L34B_Search INTEGER,L34B_OTHERS TEXT," +
                "L35 INTEGER,L35_RADIO INTEGER,L35_TV INTEGER,L35_PUB INTEGER,L35_SMS INTEGER,L35_SIREN INTEGER,L35_INTERNET INTEGER,L35_OTHER TEXT," +
                "M36_Roads INTEGER,M36_Drainage INTEGER,M36_School INTEGER,M36_Health INTEGER,M36_DayCare INTEGER,M36_WaterSystem INTEGER," +
                "M36_MultiPurpose INTEGER,M36_FloodControl INTEGER,M36_Government INTEGER,M36_Bridges INTEGER,M36_StreetLights INTEGER," +
                "M36_SolarDrier INTEGER,M36_CoveredCourt INTEGER,M36_Basketball INTEGER,M36_OTHERS TEXT," +
                "M37_Roads INTEGER,M37_Drainage INTEGER,M37_School INTEGER,M37_Health INTEGER,M37_DayCare INTEGER,M37_WaterSystem INTEGER," +
                "M37_MultiPurpose INTEGER,M37_FloodControl INTEGER,M37_Government INTEGER,M37_Bridges INTEGER,M37_StreetLights INTEGER," +
                "M37_SolarDrier INTEGER,M37_CoveredCourt INTEGER,M37_Basketball INTEGER,M37_OTHERS TEXT," +
                "N38 INTEGER,N38_A INTEGER,N38_B INTEGER,N38_C INTEGER,N38_D INTEGER,N38_E INTEGER,N38E_OTHER TEXT,N38_F INTEGER," +
                "O39_Educ INTEGER,O39_PO INTEGER,O39_Health INTEGER,O39_Labor INTEGER,O39_Economic INTEGER,O39_Transport INTEGER," +
                "O39_House INTEGER,O39_Social INTEGER,O39_Infra INTEGER,O39_Environment INTEGER,BarangayCode TEXT)");
    }

    private static final String create_TEMP_Respondent;
    static
    {
        create_TEMP_Respondent = ("CREATE TABLE " + TABLE_NAME_TEMP_RespondentNames + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HCN TEXT,DateEntered TEXT,RespondentName TEXT)");
    }

    //OFFICIAL TABLES
    private static final String create_BFTable;
    static
    {
        create_BFTable = ("CREATE TABLE " + TABLE_NAME_BF + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HCN TEXT,DateEntered TEXT,Lastname TEXT,Firstname TEXT,Middlename TEXT,Sex INTEGER," +
                "C1 INTEGER,C2 INTEGER,C2_a INTEGER,C3 INTEGER,C4 INTEGER,C5 INTEGER," +
                "D6 INTEGER,D7 INTEGER,E8 INTEGER,E9 INTEGER,E10 INTEGER,E10_a INTEGER," +
                "E11 INTEGER,E12 INTEGER,E13 INTEGER,E13_a INTEGER," +
                "E14 INTEGER,F15 INTEGER,F15_a INTEGER,F16 INTEGER,F17 INTEGER,F17_a INTEGER,F17_b INTEGER,F17_c INTEGER,F17_d INTEGER," +
                "F17_BCG INTEGER,F17_Penta1 INTEGER,F17_Penta2 INTEGER,F17_Penta3 INTEGER,F17_OPV1 INTEGER,F17_OPV2 INTEGER," +
                "F17_OPV3 INTEGER,F17_HEPAB1 INTEGER,F17_MEASLES INTEGER,F17_f INTEGER," +
                "F18 INTEGER,F19 INTEGER,F20 INTEGER,F21 INTEGER,F21_a INTEGER,F22 INTEGER,F22_a INTEGER,F22_b INTEGER," +
                "F23 INTEGER,F24 INTEGER,BarangayCode TEXT)");
    }

    private static final String create_GOTable;
    static
    {
        create_GOTable = ("CREATE TABLE " + TABLE_NAME_GO + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HCN TEXT,DateEntered TEXT,G25 INTEGER,G26 INTEGER,H27 INTEGER," +
                "H28Meat INTEGER,H28Seafoods INTEGER,H28Processed INTEGER," +
                "H28Fruits INTEGER,H28Vegetables INTEGER,H29 INTEGER,H29_OTHERS TEXT," +
                "I30 INTEGER,I31 INTEGER,J32 INTEGER," +
                "J32_Prawn INTEGER,J32_Hito INTEGER,J32_Pangasius INTEGER,J32_Bangus INTEGER,J32_Tilapia INTEGER,J32_Others TEXT," +
                "K33 INTEGER,K33_Dogs INTEGER,K33_Cats INTEGER,K33B INTEGER,K33C INTEGER," +
                "L34 INTEGER,L34A_School,L34A_GovAgencies, L34A_NonGov,L34A_OTHERS TEXT," +
                "L34B_CBDRRM INTEGER,L34B_FirstAid INTEGER, L34B_BLS INTEGER,L34B_Fire INTEGER,L34B_Search INTEGER,L34B_OTHERS TEXT," +
                "L35 INTEGER,L35_RADIO INTEGER,L35_TV INTEGER,L35_PUB INTEGER,L35_SMS INTEGER,L35_SIREN INTEGER,L35_INTERNET INTEGER,L35_OTHER TEXT," +
                "M36_Roads INTEGER,M36_Drainage INTEGER,M36_School INTEGER,M36_Health INTEGER,M36_DayCare INTEGER,M36_WaterSystem INTEGER," +
                "M36_MultiPurpose INTEGER,M36_FloodControl INTEGER,M36_Government INTEGER,M36_Bridges INTEGER,M36_StreetLights INTEGER," +
                "M36_SolarDrier INTEGER,M36_CoveredCourt INTEGER,M36_Basketball INTEGER,M36_OTHERS TEXT," +
                "M37_Roads INTEGER,M37_Drainage INTEGER,M37_School INTEGER,M37_Health INTEGER,M37_DayCare INTEGER,M37_WaterSystem INTEGER," +
                "M37_MultiPurpose INTEGER,M37_FloodControl INTEGER,M37_Government INTEGER,M37_Bridges INTEGER,M37_StreetLights INTEGER," +
                "M37_SolarDrier INTEGER,M37_CoveredCourt INTEGER,M37_Basketball INTEGER,M37_OTHERS TEXT," +
                "N38 INTEGER,N38_A INTEGER,N38_B INTEGER,N38_C INTEGER,N38_D INTEGER,N38_E INTEGER,N38E_OTHER TEXT,N38_F INTEGER," +
                "O39_Educ INTEGER,O39_PO INTEGER,O39_Health INTEGER,O39_Labor INTEGER,O39_Economic INTEGER,O39_Transport INTEGER," +
                "O39_House INTEGER,O39_Social INTEGER,O39_Infra INTEGER,O39_Environment INTEGER,BarangayCode TEXT)");
    }

    private static final String create_Respondent;
    static
    {
        create_Respondent = ("CREATE TABLE " + TABLE_NAME_RespondentNames + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HCN TEXT,DateEntered TEXT,RespondentName TEXT)");
    }

    private static final String create_InterviewerProfile;
    static
    {
        create_InterviewerProfile = ("CREATE TABLE " + TABLE_NAME_InterviewerProfile + " (No INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID TEXT, Name TEXT,Field_SupervisorID TEXT, BarangayCode TEXT)");
    }

    private static final String create_SupervisorProfile;
    static
    {
        create_SupervisorProfile = ("CREATE TABLE " + TABLE_NAME_SupervisorProfile + " (No INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Field_SupervisorID TEXT, Name TEXT,Pass TEXT)");
    }

    private static final String create_SchoolCodes;
    static
    {
        create_SchoolCodes = ("CREATE TABLE " + TABLE_NAME_SchoolCodes + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Codes TEXT,School TEXT)");
    }

    private static final String create_CooperationCodes;
    static
    {
        create_CooperationCodes = ("CREATE TABLE " + TABLE_NAME_CooperationCodes + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Codes TEXT,Cooperation TEXT)");
    }

    private static final String create_BarangayCode;
    static
    {
        create_BarangayCode = ("CREATE TABLE " + TABLE_NAME_BarangayCodes + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Codes TEXT,Barangay TEXT)");
    }

    private static final String create_transactionLog;
    static
    {
        create_transactionLog= ("CREATE TABLE " + TABLE_NAME_TransactionLog +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Date TEXT,Time TEXT,TransactionLog TEXT)");
    }

    //-------------------------------------------------------
    public databaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_TEMP_BFTable);
        db.execSQL(create_TEMP_GOTable);
        db.execSQL(create_TEMP_Respondent);
        db.execSQL(create_BFTable);
        db.execSQL(create_GOTable);
        db.execSQL(create_Respondent);
        db.execSQL(create_SchoolCodes);
        db.execSQL(create_InterviewerProfile);
        db.execSQL(create_SupervisorProfile);
        db.execSQL(create_CooperationCodes);
        db.execSQL(create_BarangayCode);
        db.execSQL(create_transactionLog);
    }

    public void onCreate_ALTERNATE( SQLiteDatabase db)
    {
        db.execSQL(create_InterviewerProfile);
        db.execSQL(create_SupervisorProfile);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEMP_BF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEMP_GO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEMP_RespondentNames);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RespondentNames);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SchoolCodes);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_InterviewerProfile);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SupervisorProfile);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CooperationCodes);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BarangayCodes);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TransactionLog);
        onCreate(db);
    }

    // METHODS FOR USED

    public void dropThisTables()
    {
        long checkInsert = 0;
        SQLiteDatabase db= null;
        try
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_InterviewerProfile);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SupervisorProfile);
            onCreate_ALTERNATE(db);

            db.execSQL(create_InterviewerProfile);
            db.execSQL(create_SupervisorProfile);

        }
        catch ( Exception e )
        {
            //Error
        }

    }
    public String getDataBaseName()
    {
        String name = DATABASE_NAME;
        return name;
    }
    //TEMPORARY TABLES
    public String getTableName_TEMP_BFTable()
    {
        String tableName = TABLE_NAME_TEMP_BF;
        return tableName;
    }
    public String getTableName_TEMP_GOTable()
    {
        String tableName = TABLE_NAME_TEMP_GO;
        return tableName;
    }
    public String getTableName_TEMP_RespondentNames ()
    {
        String tableName = TABLE_NAME_TEMP_RespondentNames;
        return tableName;
    }

    public String getTableName_BFTable()
    {
        String tableName = TABLE_NAME_BF;
        return tableName;
    }
    public String getTableName_GOTable()
    {
        String tableName = TABLE_NAME_GO;
        return tableName;
    }
    public String getTableName_RespondentNames ()
    {
        String tableName = TABLE_NAME_RespondentNames;
        return tableName;
    }
    public String getTableName_SchoolCodes()
    {
        String tableName = TABLE_NAME_SchoolCodes;
        return tableName;
    }
    public String getTableName_CooperationsCodes()
    {
        String tableName = TABLE_NAME_CooperationCodes;
        return tableName;
    }

    public String getTableName_InterviewerProfile()
    {
        String tableName = TABLE_NAME_InterviewerProfile;
        return tableName;
    }

    public String getTABLE_NAME_SupervisorProfile()
    {
        String tableName = TABLE_NAME_SupervisorProfile;
        return tableName;
    }

    public String getTableName_BarangayCodes()
    {
        String tableName = TABLE_NAME_BarangayCodes;
        return tableName;
    }
    public String getTableName_TransactionLog()
    {
        String tableName = TABLE_NAME_TransactionLog;
        return tableName;
    }
    public String getDateToday()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        Log.i("Date",date);
        return  date;
    }

    //TEMPORARY INSERT QUERIES
    public boolean insert_TEMPDataBF (String[] txtViewAll, int totalRow, String HCN, String DateEntered) {
        int CellStart = 0;
        long checkInsert = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues  contentValues = new ContentValues();
        for (int row = 0; row < totalRow - 2; row++)
        {

            contentValues.put("Lastname", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("Firstname", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("Middlename", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("Sex", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C2_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C4", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C5", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("D6", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("D7", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E8", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E9", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E10", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E10_a",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E11", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E12", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E13", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E13_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E14", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F15", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F15_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F16", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_b", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_c", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_d", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_BCG", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta1",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta2",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta3",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV1",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV2",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV3",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_HEPAB1",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_MEASLES",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_f",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F18", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F19", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F20", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F21", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F21_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22_a",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22_b",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F23", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F24", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("HCN", HCN);
            contentValues.put("DateEntered", DateEntered);


            checkInsert = db.insert(getTableName_TEMP_BFTable(), null, contentValues);

        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insert_TEMPDataGO(Integer[] integersListRadioButtons, String[] stringListEditText,
                                     String[] stringListCheckboxes, String HCN, String DateEntered,String barangayCode)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HCN", HCN.toString());
        contentValues.put("DateEntered", DateEntered);
        contentValues.put("G25",integersListRadioButtons[0]);
        contentValues.put("G26",stringListEditText[0].toString());
        contentValues.put("H27",stringListEditText[1].toString());

        contentValues.put("H28Meat",stringListEditText[2].toString());
        contentValues.put("H28Seafoods",stringListEditText[3].toString());
        contentValues.put("H28Processed",stringListEditText[4].toString());
        contentValues.put("H28Fruits",stringListEditText[5].toString());
        contentValues.put("H28Vegetables",stringListEditText[6].toString());

        contentValues.put("H29",integersListRadioButtons[1]);
        contentValues.put("H29_OTHERS",stringListEditText[7].toString());

        contentValues.put("I30",integersListRadioButtons[2]);
        contentValues.put("I31",integersListRadioButtons[3]);

        contentValues.put("J32",integersListRadioButtons[4]);
        contentValues.put("J32_Prawn",stringListEditText[8]);
        contentValues.put("J32_Hito",stringListEditText[9]);
        contentValues.put("J32_Pangasius",stringListEditText[10]);
        contentValues.put("J32_Bangus",stringListEditText[11]);
        contentValues.put("J32_Tilapia",stringListEditText[12]);
        contentValues.put("J32_Others",stringListEditText[13]);

        contentValues.put("K33",integersListRadioButtons[5]);
        contentValues.put("K33_Dogs",stringListEditText[14]);
        contentValues.put("K33_Cats",stringListEditText[15]);
        contentValues.put("K33B",integersListRadioButtons[6]);
        contentValues.put("K33C",integersListRadioButtons[7]);

        contentValues.put("L34",integersListRadioButtons[8]);
        contentValues.put("L34A_School",stringListCheckboxes[0]);
        contentValues.put("L34A_GovAgencies",stringListCheckboxes[1]);
        contentValues.put("L34A_NonGov",stringListCheckboxes[2]);
        contentValues.put("L34A_OTHERS",stringListEditText[16].toString());
        contentValues.put("L34B_CBDRRM",stringListCheckboxes[3]);
        contentValues.put("L34B_FirstAid",stringListCheckboxes[4]);
        contentValues.put("L34B_BLS",stringListCheckboxes[5]);
        contentValues.put("L34B_Fire",stringListCheckboxes[6]);
        contentValues.put("L34B_Search",stringListCheckboxes[7]);
        contentValues.put("L34B_OTHERS",stringListEditText[17].toString());

        contentValues.put("L35",integersListRadioButtons[9]);
        contentValues.put("L35_RADIO",stringListCheckboxes[8].toString());
        contentValues.put("L35_TV",stringListCheckboxes[9].toString());
        contentValues.put("L35_PUB",stringListCheckboxes[10].toString());
        contentValues.put("L35_SMS",stringListCheckboxes[11].toString());
        contentValues.put("L35_SIREN",stringListCheckboxes[12].toString());
        contentValues.put("L35_INTERNET",stringListCheckboxes[13].toString());
        contentValues.put("L35_OTHER",stringListEditText[18].toString());

        contentValues.put("M36_Roads",stringListCheckboxes[14].toString());
        contentValues.put("M36_Drainage",stringListCheckboxes[15].toString());
        contentValues.put("M36_School",stringListCheckboxes[16].toString());
        contentValues.put("M36_Health",stringListCheckboxes[17].toString());
        contentValues.put("M36_DayCare",stringListCheckboxes[18].toString());
        contentValues.put("M36_WaterSystem",stringListCheckboxes[19].toString());
        contentValues.put("M36_MultiPurpose",stringListCheckboxes[20].toString());
        contentValues.put("M36_FloodControl",stringListCheckboxes[21].toString());
        contentValues.put("M36_Government",stringListCheckboxes[22].toString());
        contentValues.put("M36_Bridges",stringListCheckboxes[23].toString());
        contentValues.put("M36_StreetLights",stringListCheckboxes[24].toString());
        contentValues.put("M36_SolarDrier",stringListCheckboxes[25].toString());
        contentValues.put("M36_CoveredCourt",stringListCheckboxes[26].toString());
        contentValues.put("M36_Basketball",stringListCheckboxes[27].toString());
        contentValues.put("M36_OTHERS",stringListEditText[19].toString());

        contentValues.put("M37_Roads",stringListCheckboxes[28].toString());
        contentValues.put("M37_Drainage",stringListCheckboxes[29].toString());
        contentValues.put("M37_School",stringListCheckboxes[30].toString());
        contentValues.put("M37_Health",stringListCheckboxes[31].toString());
        contentValues.put("M37_DayCare",stringListCheckboxes[32].toString());
        contentValues.put("M37_WaterSystem",stringListCheckboxes[33].toString());
        contentValues.put("M37_MultiPurpose",stringListCheckboxes[34].toString());
        contentValues.put("M37_FloodControl",stringListCheckboxes[35].toString());
        contentValues.put("M37_Government",stringListCheckboxes[36].toString());
        contentValues.put("M37_Bridges",stringListCheckboxes[37].toString());
        contentValues.put("M37_StreetLights",stringListCheckboxes[38].toString());
        contentValues.put("M37_SolarDrier",stringListCheckboxes[39].toString());
        contentValues.put("M37_CoveredCourt",stringListCheckboxes[40].toString());
        contentValues.put("M37_Basketball",stringListCheckboxes[41].toString());
        contentValues.put("M37_OTHERS",stringListEditText[20].toString());

        contentValues.put("N38",integersListRadioButtons[10]);
        contentValues.put("N38_A",integersListRadioButtons[11]);
        contentValues.put("N38_B",integersListRadioButtons[12]);
        contentValues.put("N38_C",integersListRadioButtons[13]);
        contentValues.put("N38_D",integersListRadioButtons[14]);
        contentValues.put("N38_E",integersListRadioButtons[15]);
        contentValues.put("N38E_OTHER",stringListEditText[21]);
        contentValues.put("N38_F",integersListRadioButtons[16]);

        contentValues.put("O39_Educ",stringListEditText[22]);
        contentValues.put("O39_PO",stringListEditText[23]);
        contentValues.put("O39_Health",stringListEditText[24]);
        contentValues.put("O39_Labor",stringListEditText[25]);
        contentValues.put("O39_Economic",stringListEditText[26]);
        contentValues.put("O39_Transport",stringListEditText[27]);
        contentValues.put("O39_House",stringListEditText[28]);
        contentValues.put("O39_Social",stringListEditText[29]);
        contentValues.put("O39_Infra",stringListEditText[30]);
        contentValues.put("O39_Environment",stringListEditText[31]);
        contentValues.put("BarangayCode",barangayCode);


        try
        {
            checkInsert = db.insert(getTableName_TEMP_GOTable(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Temporary GO Table:"+e.toString());
            db.endTransaction();
        }



        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean insert_TEMPRespondentName (String HCN, String date, String respondent)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HCN", HCN);
        contentValues.put("DateEntered", date);
        contentValues.put("RespondentName", respondent);

        try
        {
            checkInsert = db.insert(getTableName_TEMP_RespondentNames(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Temporary Respondent Table:"+e.toString());
            db.endTransaction();
        }



        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insert_NewCooperativeList(String code,String name)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Codes",code);
        contentValues.put("Cooperation",name);
        try
        {
            db.insert(getTableName_CooperationsCodes(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Insert New Cooperation Table:"+e.toString());
            db.endTransaction();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insert_NewSchoolList(String code,String name)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Codes",code);
        contentValues.put("School",name);
        try
        {
            db.insert(getTableName_SchoolCodes(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Insert New School Table:"+e.toString());
            db.endTransaction();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //FINAL INSERT QUERIES
    public boolean insertDataBF (String[] txtViewAll, int totalRow, String HCN, String DateEntered, String barangayCode)
    {
        int CellStart=0;
        long checkInsert = 0;

        SQLiteDatabase db= this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues=new ContentValues();
        for (int row = 0; row < totalRow-2; row++)
        {
            contentValues.put("HCN", HCN);

            contentValues.put("DateEntered", DateEntered);

            contentValues.put("Lastname", txtViewAll[CellStart]);
            CellStart=CellStart+1;
            contentValues.put("Firstname",txtViewAll[CellStart]);
            CellStart=CellStart+1;
            contentValues.put("Middlename",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("Sex", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C2_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C4", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C5", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("D6", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("D7", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E8", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E9", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E10", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E10_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E11", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E12", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E13", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E13_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E14", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F15", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F15_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F16", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_b", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_c", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_d", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_BCG", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_HEPAB1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_MEASLES", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_f", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F18", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F19", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F20", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F21", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F21_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22_b", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F23", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F24", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("BarangayCode", barangayCode);


            checkInsert = db.insert(getTableName_BFTable(), null, contentValues);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();



        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean insertDataGO(Integer[] integersListRadioButtons, String[] stringListEditText,
                                String[] stringListCheckboxes, String HCN, String DateEntered,String barangayCode)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HCN", HCN.toString());
        contentValues.put("DateEntered", DateEntered);
        contentValues.put("G25",integersListRadioButtons[0]);
        contentValues.put("G26",stringListEditText[0].toString());
        contentValues.put("H27",stringListEditText[1].toString());

        contentValues.put("H28Meat",stringListEditText[2].toString());
        contentValues.put("H28Seafoods",stringListEditText[3].toString());
        contentValues.put("H28Processed",stringListEditText[4].toString());
        contentValues.put("H28Fruits",stringListEditText[5].toString());
        contentValues.put("H28Vegetables",stringListEditText[6].toString());

        contentValues.put("H29",integersListRadioButtons[1]);
        contentValues.put("H29_OTHERS",stringListEditText[7].toString());

        contentValues.put("I30",integersListRadioButtons[2]);
        contentValues.put("I31",integersListRadioButtons[3]);

        contentValues.put("J32",integersListRadioButtons[4]);
        contentValues.put("J32_Prawn",stringListEditText[8]);
        contentValues.put("J32_Hito",stringListEditText[9]);
        contentValues.put("J32_Pangasius",stringListEditText[10]);
        contentValues.put("J32_Bangus",stringListEditText[11]);
        contentValues.put("J32_Tilapia",stringListEditText[12]);
        contentValues.put("J32_Others",stringListEditText[13]);

        contentValues.put("K33",integersListRadioButtons[5]);
        contentValues.put("K33_Dogs",stringListEditText[14]);
        contentValues.put("K33_Cats",stringListEditText[15]);
        contentValues.put("K33B",integersListRadioButtons[6]);
        contentValues.put("K33C",integersListRadioButtons[7]);

        contentValues.put("L34",integersListRadioButtons[8]);
        contentValues.put("L34A_School",stringListCheckboxes[0]);
        contentValues.put("L34A_GovAgencies",stringListCheckboxes[1]);
        contentValues.put("L34A_NonGov",stringListCheckboxes[2]);
        contentValues.put("L34A_OTHERS",stringListEditText[16].toString());
        contentValues.put("L34B_CBDRRM",stringListCheckboxes[3]);
        contentValues.put("L34B_FirstAid",stringListCheckboxes[4]);
        contentValues.put("L34B_BLS",stringListCheckboxes[5]);
        contentValues.put("L34B_Fire",stringListCheckboxes[6]);
        contentValues.put("L34B_Search",stringListCheckboxes[7]);
        contentValues.put("L34B_OTHERS",stringListEditText[17].toString());

        contentValues.put("L35",integersListRadioButtons[9]);
        contentValues.put("L35_RADIO",stringListCheckboxes[8].toString());
        contentValues.put("L35_TV",stringListCheckboxes[9].toString());
        contentValues.put("L35_PUB",stringListCheckboxes[10].toString());
        contentValues.put("L35_SMS",stringListCheckboxes[11].toString());
        contentValues.put("L35_SIREN",stringListCheckboxes[12].toString());
        contentValues.put("L35_INTERNET",stringListCheckboxes[13].toString());
        contentValues.put("L35_OTHER",stringListEditText[18].toString());

        contentValues.put("M36_Roads",stringListCheckboxes[14].toString());
        contentValues.put("M36_Drainage",stringListCheckboxes[15].toString());
        contentValues.put("M36_School",stringListCheckboxes[16].toString());
        contentValues.put("M36_Health",stringListCheckboxes[17].toString());
        contentValues.put("M36_DayCare",stringListCheckboxes[18].toString());
        contentValues.put("M36_WaterSystem",stringListCheckboxes[19].toString());
        contentValues.put("M36_MultiPurpose",stringListCheckboxes[20].toString());
        contentValues.put("M36_FloodControl",stringListCheckboxes[21].toString());
        contentValues.put("M36_Government",stringListCheckboxes[22].toString());
        contentValues.put("M36_Bridges",stringListCheckboxes[23].toString());
        contentValues.put("M36_StreetLights",stringListCheckboxes[24].toString());
        contentValues.put("M36_SolarDrier",stringListCheckboxes[25].toString());
        contentValues.put("M36_CoveredCourt",stringListCheckboxes[26].toString());
        contentValues.put("M36_Basketball",stringListCheckboxes[27].toString());
        contentValues.put("M36_OTHERS",stringListEditText[19].toString());

        contentValues.put("M37_Roads",stringListCheckboxes[28].toString());
        contentValues.put("M37_Drainage",stringListCheckboxes[29].toString());
        contentValues.put("M37_School",stringListCheckboxes[30].toString());
        contentValues.put("M37_Health",stringListCheckboxes[31].toString());
        contentValues.put("M37_DayCare",stringListCheckboxes[32].toString());
        contentValues.put("M37_WaterSystem",stringListCheckboxes[33].toString());
        contentValues.put("M37_MultiPurpose",stringListCheckboxes[34].toString());
        contentValues.put("M37_FloodControl",stringListCheckboxes[35].toString());
        contentValues.put("M37_Government",stringListCheckboxes[36].toString());
        contentValues.put("M37_Bridges",stringListCheckboxes[37].toString());
        contentValues.put("M37_StreetLights",stringListCheckboxes[38].toString());
        contentValues.put("M37_SolarDrier",stringListCheckboxes[39].toString());
        contentValues.put("M37_CoveredCourt",stringListCheckboxes[40].toString());
        contentValues.put("M37_Basketball",stringListCheckboxes[41].toString());
        contentValues.put("M37_OTHERS",stringListEditText[20].toString());

        contentValues.put("N38",integersListRadioButtons[10]);
        contentValues.put("N38_A",integersListRadioButtons[11]);
        contentValues.put("N38_B",integersListRadioButtons[12]);
        contentValues.put("N38_C",integersListRadioButtons[13]);
        contentValues.put("N38_D",integersListRadioButtons[14]);
        contentValues.put("N38_E",integersListRadioButtons[15]);
        contentValues.put("N38E_OTHER",stringListEditText[21]);
        contentValues.put("N38_F",integersListRadioButtons[16]);

        contentValues.put("O39_Educ",stringListEditText[22]);
        contentValues.put("O39_PO",stringListEditText[23]);
        contentValues.put("O39_Health",stringListEditText[24]);
        contentValues.put("O39_Labor",stringListEditText[25]);
        contentValues.put("O39_Economic",stringListEditText[26]);
        contentValues.put("O39_Transport",stringListEditText[27]);
        contentValues.put("O39_House",stringListEditText[28]);
        contentValues.put("O39_Social",stringListEditText[29]);
        contentValues.put("O39_Infra",stringListEditText[30]);
        contentValues.put("O39_Environment",stringListEditText[31]);
        contentValues.put("BarangayCode",barangayCode);


        try
        {
            checkInsert = db.insert(getTableName_GOTable(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"GO Table:"+e.toString());
            db.endTransaction();
        }



        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean insertRespondentName (String HCN, String date, String respondent)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HCN", HCN);
        contentValues.put("DateEntered", date);
        contentValues.put("RespondentName", respondent);

        try
        {
            checkInsert = db.insert(getTableName_RespondentNames(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Respondent Table:"+e.toString());
            db.endTransaction();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertSchoolCodes()
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Codes","PS-01");
        contentValues.put("School","A&B Shalom Children Casa (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-02");
        contentValues.put("School","A. Biscayda Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-03");
        contentValues.put("School","Arizala Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-04");
        contentValues.put("School","Aspang Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);
        contentValues.put("Codes","PS-05");
        contentValues.put("School","Balite Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);
        contentValues.put("Codes","PS-06");
        contentValues.put("School","Baluan Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-07");
        contentValues.put("School","Balunto Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-08");
        contentValues.put("School","Banisil Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-09");
        contentValues.put("School","Batomelong Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-10");
        contentValues.put("School","Bawing Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-11");
        contentValues.put("School","Biao Pre-School (Aspang ES Extn)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-12");
        contentValues.put("School","Blagan Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-13");
        contentValues.put("School","Bula Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-14");
        contentValues.put("School","Bula Christian School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-15");
        contentValues.put("School","Calumpang Central SDA Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-16");
        contentValues.put("School","Canon Migliaccio School, Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-17");
        contentValues.put("School","Changco Elementary School (Pre-School) and Badjao Pre-School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-18");
        contentValues.put("School","City Alliance Church Nursery Kindergarten School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-19");
        contentValues.put("School","Conel Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-20");
        contentValues.put("School","Daanbanwang Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-21");
        contentValues.put("School","Dadiangas Heights Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-22");
        contentValues.put("School","Dadiangas South Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-23");
        contentValues.put("School","Dadiangas West Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-24");
        contentValues.put("School","Dadiangas East Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-25");
        contentValues.put("School","Dadiangas Learning Center, Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-26");
        contentValues.put("School","Dadiangas North  Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-27");
        contentValues.put("School","Datal Salvan Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-28");
        contentValues.put("School","Datu Acad Dalid Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-29");
        contentValues.put("School","F. Oringo Sr. Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-30");
        contentValues.put("School","Fatima Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-31");
        contentValues.put("School","FVR Village Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-32");
        contentValues.put("School","General Santos First Step Learning Center Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-33");
        contentValues.put("School","Gensan Chiong Hua School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-34");
        contentValues.put("School","Gensan Christ Life Academy Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-35");
        contentValues.put("School","Goldenstate College Pre-School Dept. Inc.");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-36");
        contentValues.put("School","GS Hope Christian School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-37");
        contentValues.put("School","GSC Elementary School for the Arts (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-38");
        contentValues.put("School","GSC SDA Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-39");
        contentValues.put("School","GSC SPED Integrated School (Public Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-40");
        contentValues.put("School","GSC SPED Integrated School (Private Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-41");
        contentValues.put("School","H. Bayan Sr. Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-42");
        contentValues.put("School","H.N. Cahilsot Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-43");
        contentValues.put("School","Habitat Comm. Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-44");
        contentValues.put("School","Holy Trinity College of GSC (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-45");
        contentValues.put("School","I. Santiago Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-46");
        contentValues.put("School","J. Catolico Sr. Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-47");
        contentValues.put("School","J. Divinagracia Sr. Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-48");
        contentValues.put("School","JP Laurel Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-49");
        contentValues.put("School","Katangawan Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-50");
        contentValues.put("School","Klolang Pre-School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-51");
        contentValues.put("School","Labangal Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-52");
        contentValues.put("School","Lagao 2nd Bo. Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-53");
        contentValues.put("School","Lagao Alliance School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-54");
        contentValues.put("School","Lagao Central Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-55");
        contentValues.put("School","Lanton Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-56");
        contentValues.put("School","Ligaya Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-57");
        contentValues.put("School","Living Stone Center of Learning Inc.");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-58");
        contentValues.put("School","Lozano Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-59");
        contentValues.put("School","Mabuhay Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-60");
        contentValues.put("School","Maligaya Elementary School (Aspang Extn)(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-61");
        contentValues.put("School","Maranatha Christian Academy of GSC, Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-62");
        contentValues.put("School","Marlins Smartkids Schoolhouse, Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-63");
        contentValues.put("School","Montessori School of GSC (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-64");
        contentValues.put("School","NASA Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-65");
        contentValues.put("School","New Era University, GSC Branch (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-66");
        contentValues.put("School","New Guadalupe Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-67");
        contentValues.put("School","New Mabuhay Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-68");
        contentValues.put("School","New Society Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-69");
        contentValues.put("School","Notre Dame - Siena College of GSC (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-70");
        contentValues.put("School","Notre Dame of Dad. University - IBED Espina (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-71");
        contentValues.put("School","Notre Dame of Dad. University - IBED Lagao (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-72");
        contentValues.put("School","O.T. Santos Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-73");
        contentValues.put("School","P. Acharon Sr. C Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-74");
        contentValues.put("School","P. Kindat Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-75");
        contentValues.put("School","Pao Pao Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-76");
        contentValues.put("School","Passionist Sisters' School (Inc.) (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-77");
        contentValues.put("School","Ramon Magsaysay Memorial Colleges (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-78");
        contentValues.put("School","Romana C. Acharon Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-79");
        contentValues.put("School","Saavedra Saway Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-80");
        contentValues.put("School","Saludin Anas Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-81");
        contentValues.put("School","Sampaguita Childrens Learning Center (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-82");
        contentValues.put("School","Sampaguita Educational Institute, Inc. (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-83");
        contentValues.put("School","San Jose Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-84");
        contentValues.put("School","Sarif Mucsin Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-85");
        contentValues.put("School","Shalom Crest Wizard Academy (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-86");
        contentValues.put("School","Shiloh Christian Learning Center (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-87");
        contentValues.put("School","Sinawal Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-88");
        contentValues.put("School","St. Louise de Manillac Montessori (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-89");
        contentValues.put("School","St. Salvador del Mundo Montessori School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-90");
        contentValues.put("School","Stratford International School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-91");
        contentValues.put("School","The Heritage Academy of the Phils.(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-92");
        contentValues.put("School","The Quantum Academy, Inc.(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-93");
        contentValues.put("School","Tinagacan Elementary School (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-94");
        contentValues.put("School","UCCP Pag-Ibig Christian School of Lagao, Inc.(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-95");
        contentValues.put("School","Upper Calumpang SDA Elementary School Inc.(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-96");
        contentValues.put("School","Upper Labay Elementary School(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-97");
        contentValues.put("School","Upper Tambler Elementary School I (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-98");
        contentValues.put("School","Upper Tambler Elementary School II (Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-99");
        contentValues.put("School","Victoria Child Montessori, Inc.(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-100");
        contentValues.put("School","WAMY Academy, Inc.(Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-101");
        contentValues.put("School","Others-within GenSan-(Private Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-102");
        contentValues.put("School","Others-within GenSan-(Public Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-103");
        contentValues.put("School","Others-outside Gensan (Private Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","PS-104");
        contentValues.put("School","Others-outside Gensan (Public Pre-School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);


        //ELEMENTARY SCHOOLS LIST
        contentValues.put("Codes","ES-01");
        contentValues.put("School","Arizala Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-02");
        contentValues.put("School","Aspang Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-03");
        contentValues.put("School","Bagong Silang P/S");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-04");
        contentValues.put("School","Balakayo Prim. School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-05");
        contentValues.put("School","Balite Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-06");
        contentValues.put("School","Baluan Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-07");
        contentValues.put("School","Balunto Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);
        contentValues.put("Codes","ES-08");
        contentValues.put("School","Banisil Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-09");
        contentValues.put("School","Batomelong Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-10");
        contentValues.put("School","Bawing Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-11");
        contentValues.put("School","Biao P/S (Aspang E/S Extn)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-12");
        contentValues.put("School","Biscayda Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-13");
        contentValues.put("School","B'lagan Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-14");
        contentValues.put("School","Bula Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-15");
        contentValues.put("School","Bula Christian School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-16");
        contentValues.put("School","Calumpang Central SDA Elementary School ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-17");
        contentValues.put("School","Canon Migliaccio School, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-18");
        contentValues.put("School","Changco ES (Formerly Bawing E/S Extn.)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-19");
        contentValues.put("School","Conel Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-20");
        contentValues.put("School","Daan Banwang Prim. School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-21");
        contentValues.put("School","Dadiangas Heights Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-22");
        contentValues.put("School","Dadiangas South Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-23");
        contentValues.put("School","Dadiangas West Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-24");
        contentValues.put("School","Dadiangas East Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-25");
        contentValues.put("School","Dadiangas North Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-25");
        contentValues.put("School","Dadiangas North Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-25");
        contentValues.put("School","Dadiangas North Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-26");
        contentValues.put("School","Datal Salvan Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-27");
        contentValues.put("School","Datu Acad Dalid Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-28");
        contentValues.put("School","Fatima Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-29");
        contentValues.put("School","Francisco Oringo Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-30");
        contentValues.put("School","FVR Village Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-31");
        contentValues.put("School","Gen. Santos First Step Learning Center Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-32");
        contentValues.put("School","Gensan Chong Hua School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-33");
        contentValues.put("School","Gensan Christ Life Academy, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-34");
        contentValues.put("School","Goldenstate College-Pre School and Elementary Dept., Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-35");
        contentValues.put("School","GS Hope Christian School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-36");
        contentValues.put("School","GSC Elementary School for the Arts");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-37");
        contentValues.put("School","GSC SDA Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-38");
        contentValues.put("School","GSC SPED Integrated School (Elem Dept.) ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-39");
        contentValues.put("School","H. Bayan Sr. Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-40");
        contentValues.put("School","H.N. Cahilsot Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-41");
        contentValues.put("School","H.N. Cahilsot Elementary School II");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-42");
        contentValues.put("School","Habitat Community Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-43");
        contentValues.put("School","Holy Trinity College Elementary Dept.");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-44");
        contentValues.put("School","I. Solis Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-45");
        contentValues.put("School","Ireneo Santiago Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-46");
        contentValues.put("School","J.P. Laurel Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-47");
        contentValues.put("School","Jose C. Catolico Sr. Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-48");
        contentValues.put("School","Jose Divinagracia Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-49");
        contentValues.put("School","Katangawan Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-50");
        contentValues.put("School","K'lolang P/S");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-51");
        contentValues.put("School","Labangal Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-52");
        contentValues.put("School","Labu SDA Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-53");
        contentValues.put("School","Lagao 2nd Bo. Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-54");
        contentValues.put("School","Lagao Alliance School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-55");
        contentValues.put("School","Lagao Central Elem. School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-56");
        contentValues.put("School","Lanton Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-57");
        contentValues.put("School","Ligaya Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-58");
        contentValues.put("School","Living Stone Center of Learning, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-59");
        contentValues.put("School","Lozano Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-60");
        contentValues.put("School","Mabuhay Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-61");
        contentValues.put("School","Maligaya E/S (Aspang E/S Extn)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-62");
        contentValues.put("School","Maranatha Christian Academy of GSC, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-63");
        contentValues.put("School","Montessori School of GSC, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-64");
        contentValues.put("School","NASA Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-65");
        contentValues.put("School","New Era University, GSC Branch (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-66");
        contentValues.put("School","New Guadalupe Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-67");
        contentValues.put("School","New Mabuhay Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-68");
        contentValues.put("School","New Society Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-69");
        contentValues.put("School","Notre Dame of Dad. College-IBED Espina (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-70");
        contentValues.put("School","Notre Dame of Dad. College-IBED Lagao (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-71");
        contentValues.put("School","Notre Dame-Siena College of General Santos City (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-72");
        contentValues.put("School","O.T. Santos Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-73");
        contentValues.put("School","P. Kindat Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-74");
        contentValues.put("School","Paopao Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-75");
        contentValues.put("School","Passionist Sisters' School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-76");
        contentValues.put("School","Pedro Acharon Sr. Central Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-77");
        contentValues.put("School","Ramon Magsaysay Memorial Colleges - Elem. Dept.");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-78");
        contentValues.put("School","Romana C. Acharon Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-79");
        contentValues.put("School","Saavedra Saway Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-80");
        contentValues.put("School","Saludin Anas Elem. School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-81");
        contentValues.put("School","Sampaguita Childrens Learning Center (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-82");
        contentValues.put("School","Sampaguita Educational Institute, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-83");
        contentValues.put("School","San Jose Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-84");
        contentValues.put("School","Sarif Mucsin Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-85");
        contentValues.put("School","Shalom Crest Wizard Academy (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-86");
        contentValues.put("School","Sinawal Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-87");
        contentValues.put("School","St. Louise de Manillac Montessori (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-88");
        contentValues.put("School","St. Salvador del Mundo Montessori School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-89");
        contentValues.put("School","Stratford International School (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-90");
        contentValues.put("School","The Heritage Academy of the Philippines (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-91");
        contentValues.put("School","The Quantum Academy, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-92");
        contentValues.put("School","Tinagacan Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-93");
        contentValues.put("School","UCCP-Pag-ibig Christian School of Lagao, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-94");
        contentValues.put("School","Upper Calumpang SDA Elem. Sch, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-95");
        contentValues.put("School","Upper Labay Elementary School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-96");
        contentValues.put("School","Upper Tambler Elementary School 1");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-97");
        contentValues.put("School","Upper Tambler Elementary School 2");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-98");
        contentValues.put("School","Victoria Child Montessori, Inc. (Elementary) ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-99");
        contentValues.put("School","WAMY Academy, Inc. (Elementary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-100");
        contentValues.put("School","Others Within GenSan (Private Elementary School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-101");
        contentValues.put("School","Others Within GenSan (Public Elementary School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-102");
        contentValues.put("School","Others Outside Gensan (Private Elementary School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","ES-103");
        contentValues.put("School","Othes Outside Gensan (Public Elementary School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        //HIGH SCHOOL ELEMENTARY
        contentValues.put("Codes","HS-01");
        contentValues.put("School","AG Busano Sr. High School - Main");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-02");
        contentValues.put("School","Baluan High School (Buayan NHS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-03");
        contentValues.put("School","Banisil National High School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-04");
        contentValues.put("School","Bawing National High School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-05");
        contentValues.put("School","Buayan National High School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-06");
        contentValues.put("School","Bula National School of Fisheries");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-07");
        contentValues.put("School","Christ The King High School of Labu");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-08");
        contentValues.put("School","Dadiangas North HS (new)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-09");
        contentValues.put("School","Datu Balunto HS (ISNHS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-10");
        contentValues.put("School","Divine Institute, Inc.");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-11");
        contentValues.put("School","Elias Buscano Sr. HS (AG Busano Sr. HS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-12");
        contentValues.put("School","Emmanuel College of General Santos City");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-13");
        contentValues.put("School","Engracia L. Valdomar NHS");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-14");
        contentValues.put("School","Fatima High School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-15");
        contentValues.put("School","General Santos City High School (main)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-16");
        contentValues.put("School","General Santos Hope Christian School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-17");
        contentValues.put("School","Gensan Chong Hua School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-18");
        contentValues.put("School","GSC Natl. Sch. Of Arts & Trades ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-19");
        contentValues.put("School","GSC SPED Integrated School ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-20");
        contentValues.put("School","Holy Trinity College of GSC - HS Department");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-21");
        contentValues.put("School","Ireneo Santiago NHS of Metro Dadiangas");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-22");
        contentValues.put("School","Johnny Ang National High School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-23");
        contentValues.put("School","Labangal National High School (Main)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-24");
        contentValues.put("School","Lagao National High School ( Main)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-25");
        contentValues.put("School","Lagao National High School (Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-26");
        contentValues.put("School","Lanton High School (City High Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-27");
        contentValues.put("School","Ligaya High School (Labangal NHS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-28");
        contentValues.put("School","Maranatha Christian Academy of GSC, Inc.");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-29");
        contentValues.put("School","Mindanao State University - CETD");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-30");
        contentValues.put("School","Montessori School of GSC");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-31");
        contentValues.put("School","New Era University");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-32");
        contentValues.put("School","New Society National High School ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-33");
        contentValues.put("School","Notre Dame of Dadiangas - IBED Espina Campus");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-34");
        contentValues.put("School","Notre Dame of Dadiangas - IBED Lagao Campus");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-35");
        contentValues.put("School","Notre Dame-Siena College of GSC (High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-36");
        contentValues.put("School","Paopao High School (Labangal NHS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-37");
        contentValues.put("School","Passionist Sisters School (High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-38");
        contentValues.put("School","RMMC - High School Department");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-39");
        contentValues.put("School","Samboang-Ngilay High School (Batomelong HS)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-40");
        contentValues.put("School","Shalom Crest Wizard Academy, Inc. (High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-41");
        contentValues.put("School","Stratford International School (High School) ");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-42");
        contentValues.put("School","The Heritage Academy of the Philippines (High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-43");
        contentValues.put("School","The Quantum Academy, Inc. (High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-44");
        contentValues.put("School","Tinagacan High School (AG Busano Sr. HS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-45");
        contentValues.put("School","Upper Labay High School (Labangal NHS Annex)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-46");
        contentValues.put("School","Others Within GenSan (Private High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-47");
        contentValues.put("School","Others Within GenSan (Public High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-48");
        contentValues.put("School","Others Outside Gensan (Private High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","HS-49");
        contentValues.put("School","Others Outside Gensan (Public High School)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        //---------------TERTIARY SCHOOLS------------------------------------------------------------
        contentValues.put("Codes","TS-01");
        contentValues.put("School","AMA Computer College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-02");
        contentValues.put("School","AMA Computer Learning Center");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-03");
        contentValues.put("School","Brokenshire College SOCSARGEN");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-04");
        contentValues.put("School","Divine Mercy Information Technology");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-05");
        contentValues.put("School","Doña Lourdes Institute of Technology");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-06");
        contentValues.put("School","Emmanuel College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-07");
        contentValues.put("School","General Santos City School of Arts and Trade");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-08");
        contentValues.put("School","General Santos College of Technology");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-09");
        contentValues.put("School","General Santos Doctors Medical School Foundation");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-10");
        contentValues.put("School","General Santos Foundation College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-11");
        contentValues.put("School","Golden State College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-12");
        contentValues.put("School","Hi-Tech Institute of Technology");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-13");
        contentValues.put("School","Holy Trinity College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-14");
        contentValues.put("School","Joji Ilagan College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-15");
        contentValues.put("School","Mindanao Polytechnic College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-16");
        contentValues.put("School","Mindanao State University - GSC");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-17");
        contentValues.put("School","Nikki Louise College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-18");
        contentValues.put("School","Notre Dame of Dadiangas University");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-19");
        contentValues.put("School","Philippine Institute of Technology");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-20");
        contentValues.put("School","Philtech School of Arts and Trades");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-21");
        contentValues.put("School","Pyra Tech Computer School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-22");
        contentValues.put("School","Ramon Magsaysay Memorial Colleges");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-23");
        contentValues.put("School","Saint John Bosco College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-24");
        contentValues.put("School","SOCSARGEN Institute of Paramedics Science and Technology");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-25");
        contentValues.put("School","Stratford International School");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-26");
        contentValues.put("School","STI College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-27");
        contentValues.put("School","South Point College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-28");
        contentValues.put("School","South Ranex College");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-29");
        contentValues.put("School","Others within GenSan (Private-Tertiary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-30");
        contentValues.put("School","Others within GenSan (Public-Tertiary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-31");
        contentValues.put("School","Outside Gensan (Private-Tertiary)");
        db.insert(getTableName_SchoolCodes(), null, contentValues);

        contentValues.put("Codes","TS-32");
        contentValues.put("School","Outside Gensan (Public-Tertiary)");


        try
        {
            db.insert(getTableName_SchoolCodes(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"School Table:"+e.toString());
            db.endTransaction();
        }

        db.close();

        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCooperationCodes()
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Codes","CO-01");
        contentValues.put("Cooperation","8K Labor Service Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-02");
        contentValues.put("Cooperation","ACTSS Labor Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-03");
        contentValues.put("Cooperation","Agribest Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-04");
        contentValues.put("Cooperation","Allied Employees Multipurpose Cooperative (AEMCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-05");
        contentValues.put("Cooperation","AMMAL Consumers Cooperative (AMMALCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-06");
        contentValues.put("Cooperation","Amsua Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-07");
        contentValues.put("Cooperation","Apopong Lagao Jeepney Operators and Drivers Transport Service Cooperative (ALJODTSC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-08");
        contentValues.put("Cooperation","Bagong Buhay Producers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-09");
        contentValues.put("Cooperation","Baluan Creek Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-10");
        contentValues.put("Cooperation","Barangay Fatima Rural Waterworks Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-11");
        contentValues.put("Cooperation","Barangay Fatima Rural Waterworks Service Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-12");
        contentValues.put("Cooperation","Batingao Katribu Marketing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-13");
        contentValues.put("Cooperation","Bawing Cultural Community Development Cooperative (BCCDC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-14");
        contentValues.put("Cooperation","Bio Organic Advocates Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-15");
        contentValues.put("Cooperation","Biocrest Multipurpose Cooperative (Biocoop)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-16");
        contentValues.put("Cooperation","Blue Bay Resources Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-17");
        contentValues.put("Cooperation","Buayan Quarry Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-18");
        contentValues.put("Cooperation","Cabuay Pioneers Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-19");
        contentValues.put("Cooperation","Celebes Canning Employees Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-20");
        contentValues.put("Cooperation","Centro Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-21");
        contentValues.put("Cooperation","City Food Terminal Multipurpose Cooperative (CFTEMCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-22");
        contentValues.put("Cooperation","Civil Aviation Authority of the Philippines Employees Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-23");
        contentValues.put("Cooperation","CP Grand Farmers Producers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-24");
        contentValues.put("Cooperation","Damalerio Group of Companies Employees Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-25");
        contentValues.put("Cooperation","DENR Employees Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-26");
        contentValues.put("Cooperation","Dynamic Talent Management (DTM) Manpower Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-27");
        contentValues.put("Cooperation","Eljo Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-28");
        contentValues.put("Cooperation","Employees Village Vendors Marketing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-29");
        contentValues.put("Cooperation","First Progressive Marketing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-30");
        contentValues.put("Cooperation","Fish Marketers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-31");
        contentValues.put("Cooperation","Fishport Workers Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-32");
        contentValues.put("Cooperation","FNHS Personnel Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-33");
        contentValues.put("Cooperation","Fokol Tribal Farmers Producers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-34");
        contentValues.put("Cooperation","FVR Malagat Tambler Transport Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-35");
        contentValues.put("Cooperation","GA Diamond Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-36");
        contentValues.put("Cooperation","General Employees Multipurpose Cooperative (GEMPC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-37");
        contentValues.put("Cooperation","General Santos City Government Employees Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-38");
        contentValues.put("Cooperation","General Santos City High School Teachers and Employees Consumers Cooperative (GSCHS-TECCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-39");
        contentValues.put("Cooperation","General Santos City Meat Vendors Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-40");
        contentValues.put("Cooperation","General Santos City Teachers and Employees Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-41");
        contentValues.put("Cooperation","General Santos Coca-Cola Multipurpose Cooperative (GSCCMPC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-42");
        contentValues.put("Cooperation","General Santos Cooperative Federation (GENCOFED)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-43");
        contentValues.put("Cooperation","General Santos Harbor Pilots Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-44");
        contentValues.put("Cooperation","General Santos Secondary School Teachers Consumers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-45");
        contentValues.put("Cooperation","General Santos Transport Cooperative (GensanTransco)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-46");
        contentValues.put("Cooperation","Generals Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-47");
        contentValues.put("Cooperation","Genesis Producers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-48");
        contentValues.put("Cooperation","GenMar Transport Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-49");
        contentValues.put("Cooperation","Gensan Bio-Tech Innovative Marketing Cooperative (GENBIM)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-50");
        contentValues.put("Cooperation","Gensan Bio-Tech Innovative Marketing Cooperative (GENBIM)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-51");
        contentValues.put("Cooperation","Gensan Gardeners Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-52");
        contentValues.put("Cooperation","Gensan Hall Of Justice Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-53");
        contentValues.put("Cooperation","Gensan Manpower Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-54");
        contentValues.put("Cooperation","Gensan Seed Growers Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-55");
        contentValues.put("Cooperation","Gensan Taxi Transport Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-56");
        contentValues.put("Cooperation","Gensan-Palimbang Transport Service Cooperative (GPTRANSCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-57");
        contentValues.put("Cooperation","Gentuna Employees Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-58");
        contentValues.put("Cooperation","GKI Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-59");
        contentValues.put("Cooperation","Green Harvest Marketing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-60");
        contentValues.put("Cooperation","GSC Progressive Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-61");
        contentValues.put("Cooperation","GSC Umbrella Fish Landing Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-62");
        contentValues.put("Cooperation","GSCNSSAT-Teachers and Employees Consumers Cooperative (GSCNSSAT-TECCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-63");
        contentValues.put("Cooperation","GSFPC Employees Multipurpose Cooperative (GEMPCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-64");
        contentValues.put("Cooperation","Health Care Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-65");
        contentValues.put("Cooperation","Home Builders Housing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-66");
        contentValues.put("Cooperation","Human Resources Multipurpose Cooperative (HUREMPCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-67");
        contentValues.put("Cooperation","Independent Cassava Resource (INCARE) Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-68");
        contentValues.put("Cooperation","Kaiser Center for Training and Advocacy Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-69");
        contentValues.put("Cooperation","KPS Officers and Employees Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-70");
        contentValues.put("Cooperation","Krismubil Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-71");
        contentValues.put("Cooperation","Lagao Drivers Operators Transport Cooperative (LADOTRANSCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-72");
        contentValues.put("Cooperation","LandBank- Gen. Santos Employees Investment Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-73");
        contentValues.put("Cooperation","Lanton Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-74");
        contentValues.put("Cooperation","LBN Manpower Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-75");
        contentValues.put("Cooperation","Mabuhay Transport Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-76");
        contentValues.put("Cooperation","Maklab-as Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-77");
        contentValues.put("Cooperation","Malayang Silangan Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-78");
        contentValues.put("Cooperation","Mamre Marketing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-79");
        contentValues.put("Cooperation","MBT Farm Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-80");
        contentValues.put("Cooperation","Metro Gensan Multipurpose Cooperative (MGMPC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-81");
        contentValues.put("Cooperation","MFE Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-82");
        contentValues.put("Cooperation","MMC Employees Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-83");
        contentValues.put("Cooperation","Modelong Tricycle Drivers in Gensan Transport Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-84");
        contentValues.put("Cooperation","Molave Vegetable Producers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-85");
        contentValues.put("Cooperation","MPS Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-86");
        contentValues.put("Cooperation","MSU Gensan Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-87");
        contentValues.put("Cooperation","Nelfi R12 Credit Coop");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-88");
        contentValues.put("Cooperation","New Mabuhay Elementary School Teachers Consumers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-89");
        contentValues.put("Cooperation","New Society National High School Consumers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-90");
        contentValues.put("Cooperation","Next Level Training and Advocacy Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-91");
        contentValues.put("Cooperation","Olano Homeowners Susi Sa Pag-unlad Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-92");
        contentValues.put("Cooperation","OSD Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-93");
        contentValues.put("Cooperation","Our Lady of Penafrancia Workers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-94");
        contentValues.put("Cooperation","Panabo Multi-purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-95");
        contentValues.put("Cooperation","Philippine Financial Ventures Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-96");
        contentValues.put("Cooperation","Philippine Ports General Santos City Employees Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-97");
        contentValues.put("Cooperation","Productive Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-98");
        contentValues.put("Cooperation","Purok 11-C Vendors Marketing Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-99");
        contentValues.put("Cooperation","PWD Gensan Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-100");
        contentValues.put("Cooperation","R.O. Diagan Cooperative Hospital");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-101");
        contentValues.put("Cooperation","RD Employees Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-102");
        contentValues.put("Cooperation","Red Cluster Drivers and Operators Transport Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-103");
        contentValues.put("Cooperation","Region XII Philippine Coconut Authority Employees Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-104");
        contentValues.put("Cooperation","RSA Fishermen/Dependents Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-105");
        contentValues.put("Cooperation","RTC 12 Consumers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-106");
        contentValues.put("Cooperation","Santa Catalina Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-107");
        contentValues.put("Cooperation","Saavedra Saway Teachers Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-108");
        contentValues.put("Cooperation","San Andres Fishing Industries Employees Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-109");
        contentValues.put("Cooperation","Sarangani Province Agrarian Reform Employees Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-110");
        contentValues.put("Cooperation","Small Entrepreneurs Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-111");
        contentValues.put("Cooperation","Small Scale Business Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-112");
        contentValues.put("Cooperation","SOCOTECO II Employees Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-113");
        contentValues.put("Cooperation","SOCSARGEN County Hospital Employees Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-114");
        contentValues.put("Cooperation","SOCSARGEN Electricity Users Consumers Cooperative (SOCSARGEN-EUCC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-115");
        contentValues.put("Cooperation","SOCSKSARGEN Cain Agriculture Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-116");
        contentValues.put("Cooperation","South Star Development Cooperative (SSDC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-117");
        contentValues.put("Cooperation","Southgen Shunamite Credit Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-118");
        contentValues.put("Cooperation","SSS Gensan Employees Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-119");
        contentValues.put("Cooperation","Sunline Labor Services Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-120");
        contentValues.put("Cooperation","Sunrise Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-121");
        contentValues.put("Cooperation","Tabunaway Consumers Cooperative (TCC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-122");
        contentValues.put("Cooperation","Talisay Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-123");
        contentValues.put("Cooperation","Tambler Jeepney Operators and Drivers Transport Service Cooperative (TAJODTSCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-124");
        contentValues.put("Cooperation","Tambler Manpower Cooperative & Services");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-125");
        contentValues.put("Cooperation","Tinagacan Agrarian Reform Beneficiaries Cooperative (TARBC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-126");
        contentValues.put("Cooperation","Tnalak Labor Service Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-127");
        contentValues.put("Cooperation","Tribal Farmers of Balnabu Producers Cooperative (TRIFAB COOPERATIVE)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-128");
        contentValues.put("Cooperation","Tri-Media Central Mindanao Service Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-129");
        contentValues.put("Cooperation","Tuna Exporters Multipurpose Cooperative (TEMPCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-130");
        contentValues.put("Cooperation","United Engineering Multipurpose Cooperative (UNEMPCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-131");
        contentValues.put("Cooperation","United Fabricators and Designers Producers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-132");
        contentValues.put("Cooperation","United Grains Retailers of Sargen Marketing Cooperative (UGRESMCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-133");
        contentValues.put("Cooperation","United Technicians, Electricians Multipurpose Cooperative (UTEMCO)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-134");
        contentValues.put("Cooperation","UOMA Consumers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-135");
        contentValues.put("Cooperation","Upper Labay Agro-Industrial Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-136");
        contentValues.put("Cooperation","USWAG Consumers Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-137");
        contentValues.put("Cooperation","Verdant Multipurpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-138");
        contentValues.put("Cooperation","Vineyard Agrarian Reform Beneficiaries Multi-Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-139");
        contentValues.put("Cooperation","Wealthlinks Multi-Purpose Cooperative (WMPC)");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-140");
        contentValues.put("Cooperation","Winch Multi Purpose Cooperative");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-141");
        contentValues.put("Cooperation","Workers Cooperative of the Philippines");
        db.insert(getTableName_CooperationsCodes(), null, contentValues);

        contentValues.put("Codes","CO-142");
        contentValues.put("Cooperation","Yam Agrarian Reform Farmers Cooperative");


        try
        {
            db.insert(getTableName_CooperationsCodes(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Cooperation Table:"+e.toString());
            db.endTransaction();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertBarangayCodes()
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Codes","126303029");
        contentValues.put("Barangay","Apopong");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303003");
        contentValues.put("Barangay","Baluan");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303032");
        contentValues.put("Barangay","Batomelong");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303005");
        contentValues.put("Barangay","Bula");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303004");
        contentValues.put("Barangay","Buayan");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303033");
        contentValues.put("Barangay","Calumpang");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303034");
        contentValues.put("Barangay","City Heights");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303006");
        contentValues.put("Barangay","Conel");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303007");
        contentValues.put("Barangay","Dadiangas East");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303035");
        contentValues.put("Barangay","Dadiangas North");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303036");
        contentValues.put("Barangay","Dadiangas South");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303037");
        contentValues.put("Barangay","Dadiangas West");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303038");
        contentValues.put("Barangay","Fatima");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303009");
        contentValues.put("Barangay","Katangawan");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303012");
        contentValues.put("Barangay","Labangal");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303011");
        contentValues.put("Barangay","Lagao");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303015");
        contentValues.put("Barangay","Ligaya");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303016");
        contentValues.put("Barangay","Mabuhay");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303023");
        contentValues.put("Barangay","San Isidro");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303039");
        contentValues.put("Barangay","Olympog");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303024");
        contentValues.put("Barangay","San Jose");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303030");
        contentValues.put("Barangay","Siguel");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303026");
        contentValues.put("Barangay","Sinawal");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303027");
        contentValues.put("Barangay","Tambler");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303028");
        contentValues.put("Barangay","Tinagacan");
        db.insert(getTableName_BarangayCodes(), null, contentValues);

        contentValues.put("Codes","126303031");
        contentValues.put("Barangay","Upper Labay");


        try
        {
            db.insert(getTableName_BarangayCodes(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Barangay Table:"+e.toString());
            db.endTransaction();
            db.close();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertInterviewerProfile()
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 1 - MODE - BAYANIHAN
        // EACH FI's and FS WILL HAVE A DIFFERENT BARANGAY EVERY DAY
        //DEFAULT BARANGAY - KATANGAWAN

        ///////1
        contentValues.put("ID","01-01");
        contentValues.put("Name","Aina Sherrine Bentaib");
        contentValues.put("Field_SupervisorID","FS1-01");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-02");
        contentValues.put("Name","Eula Nikka Mapa");
        contentValues.put("Field_SupervisorID","FS1-01");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-03");
        contentValues.put("Name","Daisyrie Salitrero");
        contentValues.put("Field_SupervisorID","FS1-01");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
         */

        //2
        contentValues.put("ID","01-04");
        contentValues.put("Name","Maizura Aiko Bio");
        contentValues.put("Field_SupervisorID","FS1-02");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-05");
        contentValues.put("Name","Judylan Chiva");
        contentValues.put("Field_SupervisorID","FS1-02");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-06");
        contentValues.put("Name","Cynie Jane Degayo");
        contentValues.put("Field_SupervisorID","FS1-02");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //3
        contentValues.put("ID","01-07");
        contentValues.put("Name","Shiela Mae Esteban");
        contentValues.put("Field_SupervisorID","FS1-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-08");
        contentValues.put("Name","Rowena Jimenea");
        contentValues.put("Field_SupervisorID","FS1-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-09");
        contentValues.put("Name","Niño Tampus");
        contentValues.put("Field_SupervisorID","FS1-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //4
        contentValues.put("ID","01-10");
        contentValues.put("Name","Michael Cayson");
        contentValues.put("Field_SupervisorID","FS1-04");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-11");
        contentValues.put("Name","Angeli Dasal");
        contentValues.put("Field_SupervisorID","FS1-10");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-12");
        contentValues.put("Name","Nomar Diaz");
        contentValues.put("Field_SupervisorID","FS1-04");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        //5
        contentValues.put("ID","01-13");
        contentValues.put("Name","Romy Red Fajardo");
        contentValues.put("Field_SupervisorID","FS1-05");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-14");
        contentValues.put("Name","Gilbert Fronteras");
        contentValues.put("Field_SupervisorID","FS1-05");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-15");
        contentValues.put("Name","Agnes Ignacio");
        contentValues.put("Field_SupervisorID","FS1-05");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //6
        /*
        contentValues.put("ID","01-16");
        contentValues.put("Name","Rodney Bagundol");
        contentValues.put("Field_SupervisorID","FS1-06");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-17");
        contentValues.put("Name","Laurence Gray Castino");
        contentValues.put("Field_SupervisorID","FS1-06");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-18");
        contentValues.put("Name","Norhanifa Ibrahim");
        contentValues.put("Field_SupervisorID","FS1-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //7
        contentValues.put("ID","01-19");
        contentValues.put("Name","Anna Marie Lacara");
        contentValues.put("Field_SupervisorID","FS1-09");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-20");
        contentValues.put("Name","Benedict Peñol");
        contentValues.put("Field_SupervisorID","FS1-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-21");
        contentValues.put("Name","Marie Angeli Presto");
        contentValues.put("Field_SupervisorID","FS1-07");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //8
        contentValues.put("ID","01-22");
        contentValues.put("Name","Raul Angelo Presto");
        contentValues.put("Field_SupervisorID","FS1-08");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-23");
        contentValues.put("Name","Bernadette Tiala");
        contentValues.put("Field_SupervisorID","FS1-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-24");
        contentValues.put("Name","Jaydee Ycong");
        contentValues.put("Field_SupervisorID","FS1-08");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //9
        /*
        contentValues.put("ID","01-25");
        contentValues.put("Name","Jenafel Balunto");
        contentValues.put("Field_SupervisorID","FS1-09");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-26");
        contentValues.put("Name","Shiela Macondan");
        contentValues.put("Field_SupervisorID","FS1-09");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-27");
        contentValues.put("Name","Analie Pendaton");
        contentValues.put("Field_SupervisorID","FS1-09");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //10
        contentValues.put("ID","01-28");
        contentValues.put("Name","Wilhelmina Agonoy");
        contentValues.put("Field_SupervisorID","FS1-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-29");
        contentValues.put("Name","Grace Colegado");
        contentValues.put("Field_SupervisorID","FS1-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-30");
        contentValues.put("Name","Maria Lualhati Togonon");
        contentValues.put("Field_SupervisorID","FS1-10");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-31");
        contentValues.put("Name","Cherry Mae Clariza");
        contentValues.put("Field_SupervisorID","FS1-10");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //11
        contentValues.put("ID","01-32");
        contentValues.put("Name","Marites Dungca");
        contentValues.put("Field_SupervisorID","FS1-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-33");
        contentValues.put("Name","Jewell Mae Lumen");
        contentValues.put("Field_SupervisorID","FS1-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-34");
        contentValues.put("Name","Merlinda Aloro");
        contentValues.put("Field_SupervisorID","FS1-01");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-35");
        contentValues.put("Name","Johanna Yap");
        contentValues.put("Field_SupervisorID","FS1-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //12
        /*
        contentValues.put("ID","01-36");
        contentValues.put("Name","Ralph Adrian Pardillo");
        contentValues.put("Field_SupervisorID","FS1-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-37");
        contentValues.put("Name","Joseph Kervin Parreñas");
        contentValues.put("Field_SupervisorID","FS1-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-38");
        contentValues.put("Name","Karl Xander Cubilo");
        contentValues.put("Field_SupervisorID","FS1-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-39");
        contentValues.put("Name","Rosemin Lim");
        contentValues.put("Field_SupervisorID","FS1-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //13
        /*
        contentValues.put("ID","01-40");
        contentValues.put("Name","Libby Ann Besinga");
        contentValues.put("Field_SupervisorID","FS1-13");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-41");
        contentValues.put("Name","Emeriza Gica");
        contentValues.put("Field_SupervisorID","FS1-13");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-42");
        contentValues.put("Name","Karen Kaye Encabo");
        contentValues.put("Field_SupervisorID","FS1-13");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */


        //14
        /*
        contentValues.put("ID","01-43");
        contentValues.put("Name","Aiza Danial");
        contentValues.put("Field_SupervisorID","FS1-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-44");
        contentValues.put("Name","Michelle Alingasa");
        contentValues.put("Field_SupervisorID","FS1-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-45");
        contentValues.put("Name","Bai Nashrhea Diale");
        contentValues.put("Field_SupervisorID","FS1-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //15
        contentValues.put("ID","01-46");
        contentValues.put("Name","Necca Garnace");
        contentValues.put("Field_SupervisorID","FS1-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-47");
        contentValues.put("Name","Janice Piang");
        contentValues.put("Field_SupervisorID","FS1-15");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-48");
        contentValues.put("Name","Titamae Santiago");
        contentValues.put("Field_SupervisorID","FS1-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-49");
        contentValues.put("Name","Michelle Gumanit");
        contentValues.put("Field_SupervisorID","FS1-08");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //16
        contentValues.put("ID","01-50");
        contentValues.put("Name","Juvey Legarte");
        contentValues.put("Field_SupervisorID","FS1-16");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-51");
        contentValues.put("Name","Meriam Rodriguez");
        contentValues.put("Field_SupervisorID","FS1-16");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-52");
        contentValues.put("Name","Aisha Mae Villajuan");
        contentValues.put("Field_SupervisorID","FS1-16");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //17
        contentValues.put("ID","01-53");
        contentValues.put("Name","Mary Grace Echavez");
        contentValues.put("Field_SupervisorID","FS1-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-54");
        contentValues.put("Name","Joanna Lao");
        contentValues.put("Field_SupervisorID","FS1-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-55");
        contentValues.put("Name","Krischen Anas");
        contentValues.put("Field_SupervisorID","FS1-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //18
        contentValues.put("ID","01-56");
        contentValues.put("Name","Jazel Talon");
        contentValues.put("Field_SupervisorID","FS1-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*
        contentValues.put("ID","01-57");
        contentValues.put("Name","Kevin Laurette Dawaton");
        contentValues.put("Field_SupervisorID","FS1-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","01-58");
        contentValues.put("Name","Shairen Timcang");
        contentValues.put("Field_SupervisorID","FS1-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-59");
        contentValues.put("Name", "Alma E. Dalid");
        contentValues.put("Field_SupervisorID","FS1-10");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // FS TO GRANT INTERVIEW
        contentValues.put("ID","01-0001");
        contentValues.put("Name", "Dayanara M. Bentaib");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-0002");
        contentValues.put("Name", "Jomar U. Haber");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-0003");
        contentValues.put("Name", "Maria Theresa D. Balongan");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-0004");
        contentValues.put("Name", "Noel L. Poquita");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-0005");
        contentValues.put("Name", "Fritzie Z. Salazar");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-0006");
        contentValues.put("Name", "Maricel A. Busano");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-0007");
        contentValues.put("Name", "Raquel C. Payongayong");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-0008");
        contentValues.put("Name", "Rachel Fe M. Cortez");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-0009");
        contentValues.put("Name", "Josefina C. Lapasaran");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-0010");
        contentValues.put("Name", "Abegail C. Pangcoga");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","01-0011");
        contentValues.put("Name", "Joan B. Dumpa");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-0012");
        contentValues.put("Name", "Geca C. Omas-as");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","01-0013");
        contentValues.put("Name", "Geralyn P. Masaglang");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 2
        //1 - LABANGAL

        contentValues.put("ID","02-01");
        contentValues.put("Name","Norhanie E. Maubpon");
        contentValues.put("Field_SupervisorID","FS2-01");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-02");
        contentValues.put("Name","Sittie Rohanie M. Omar");
        contentValues.put("Field_SupervisorID","FS2-01");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-03");
        contentValues.put("Name","Tsunami Rayman");
        contentValues.put("Field_SupervisorID","FS2-01");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //2
        contentValues.put("ID","02-04");
        contentValues.put("Name","Karen S. Dagcutan");
        contentValues.put("Field_SupervisorID","FS2-02");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-05");
        contentValues.put("Name","Ivy B. Ladaran");
        contentValues.put("Field_SupervisorID","FS2-02");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-06");
        contentValues.put("Name","Noraiza T. Pina");
        contentValues.put("Field_SupervisorID","FS2-02");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-07");
        contentValues.put("Name","Salvacion B. Tabliso");
        contentValues.put("Field_SupervisorID","FS2-02");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //3
        contentValues.put("ID","02-08");
        contentValues.put("Name","Ceasar Jerami A. Cabando");
        contentValues.put("Field_SupervisorID","FS2-03");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-09");
        contentValues.put("Name","Hasrona G. Condi");
        contentValues.put("Field_SupervisorID","FS2-03");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-10");
        contentValues.put("Name","Mary Jane R. Gonzales");
        contentValues.put("Field_SupervisorID","FS2-03");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //4
        contentValues.put("ID","02-11");
        contentValues.put("Name","Mary Jane D. Aguirre");
        contentValues.put("Field_SupervisorID","FS2-04");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-12");
        contentValues.put("Name","Chester D. Caspillo");
        contentValues.put("Field_SupervisorID","FS2-04");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-13");
        contentValues.put("Name","Jelyn Ruda");
        contentValues.put("Field_SupervisorID","FS2-04");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //5
        contentValues.put("ID","02-14");
        contentValues.put("Name","Erwin T. Branzuela");
        contentValues.put("Field_SupervisorID","FS2-05");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-15");
        contentValues.put("Name","Malia M. Gunda");
        contentValues.put("Field_SupervisorID","FS2-05");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-16");
        contentValues.put("Name","Vaniza O. Samad");
        contentValues.put("Field_SupervisorID","FS2-05");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //6
        contentValues.put("ID","02-17");
        contentValues.put("Name","Dennis M. Alforque");
        contentValues.put("Field_SupervisorID","FS2-06");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-18");
        contentValues.put("Name","Regin L. Costiniano");
        contentValues.put("Field_SupervisorID","FS2-06");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-19");
        contentValues.put("Name","Gemma T. Salas");
        contentValues.put("Field_SupervisorID","FS2-06");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-20");
        contentValues.put("Name","Romnick C. Salig");
        contentValues.put("Field_SupervisorID","FS2-06");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


/////////////////////// REMOVE

        contentValues.put("ID","02-21");
        contentValues.put("Name","Joeven Amande");
        contentValues.put("Field_SupervisorID","FS2-06");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-22");
        contentValues.put("Name","Era Marie D. Montera");
        contentValues.put("Field_SupervisorID","FS2-01");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-23");
        contentValues.put("Name","Norshaila A. Guiomala");
        contentValues.put("Field_SupervisorID","FS2-03");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-25");
        contentValues.put("Name","Angelo P. Salgarino");
        contentValues.put("Field_SupervisorID","FS2-04");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-26");
        contentValues.put("Name","Nikke A. Antoque");
        contentValues.put("Field_SupervisorID","FS2-04");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-27");
        contentValues.put("Name","Jaycel Heramis");
        contentValues.put("Field_SupervisorID","FS2-05");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-28");
        contentValues.put("Name","Kareen Gay B. Wagas");
        contentValues.put("Field_SupervisorID","FS2-05");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /*REMOVE
         */

        //CALUMPANG

        //7
        contentValues.put("ID","02-33");
        contentValues.put("Name","Roxana Roxette M. Suarez");
        contentValues.put("Field_SupervisorID","FS2-07");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-34");
        contentValues.put("Name","Emma C. Andebor");
        contentValues.put("Field_SupervisorID","FS2-07");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-36");
        contentValues.put("Name","Marcelo Moreno Jr.");
        contentValues.put("Field_SupervisorID","FS2-07");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //8
        contentValues.put("ID","02-37");
        contentValues.put("Name","Ryan Jay A. Bajao");
        contentValues.put("Field_SupervisorID","FS2-08");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-38");
        contentValues.put("Name","Mae Famela D. Carriedo");
        contentValues.put("Field_SupervisorID","FS2-08");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-39");
        contentValues.put("Name","Mary Jane Artajo");
        contentValues.put("Field_SupervisorID","FS2-08");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-40");
        contentValues.put("Name","Myrna Sabarillo");
        contentValues.put("Field_SupervisorID","FS2-08");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //9
        contentValues.put("ID","02-42");
        contentValues.put("Name","Kathleen Karlotte S. Millan");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-44");
        contentValues.put("Name","Iris Jane Marcellones");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-32");
        contentValues.put("Name","Charity G. Obasa");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-29");
        contentValues.put("Name","Mary Ann L. Cutamora");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //10
        contentValues.put("ID","02-49");
        contentValues.put("Name","Mulaika L. Banac");
        contentValues.put("Field_SupervisorID","FS2-10");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-52");
        contentValues.put("Name","Jun Rey P. Estrera");
        contentValues.put("Field_SupervisorID","FS2-10");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-50");
        contentValues.put("Name","Rosita F. Perez");
        contentValues.put("Field_SupervisorID","FS2-10");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-51");
        contentValues.put("Name","Ronelyn Genesa C. Regalado");
        contentValues.put("Field_SupervisorID","FS2-10");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //11
        contentValues.put("ID","02-59");
        contentValues.put("Name","Cherry Marigon");
        contentValues.put("Field_SupervisorID","FS2-11");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-60");
        contentValues.put("Name","Jona Marigon");
        contentValues.put("Field_SupervisorID","FS2-11");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-57");
        contentValues.put("Name","Judy Ann C. Octal");
        contentValues.put("Field_SupervisorID","FS2-11");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //12
        contentValues.put("ID","02-56");
        contentValues.put("Name","Daryl Jhun B. Ipulan");
        contentValues.put("Field_SupervisorID","FS2-12");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-55");
        contentValues.put("Name","Warven S. Literatus");
        contentValues.put("Field_SupervisorID","FS2-12");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-54");
        contentValues.put("Name","Edniel Aldrin M. Millado");
        contentValues.put("Field_SupervisorID","FS2-12");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //13
        contentValues.put("ID","02-65");
        contentValues.put("Name","Romel T. Abejuela");
        contentValues.put("Field_SupervisorID","FS2-13");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-66");
        contentValues.put("Name","Lovely S. Ytac");
        contentValues.put("Field_SupervisorID","FS2-13");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //14
        contentValues.put("ID","02-62");
        contentValues.put("Name","Christian B. Cadelina");
        contentValues.put("Field_SupervisorID","FS2-14");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-61");
        contentValues.put("Name","Feliz Joy E. Hingabay");
        contentValues.put("Field_SupervisorID","FS2-14");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-68");
        contentValues.put("Name","Jilly T. Lariosa");
        contentValues.put("Field_SupervisorID","FS2-14");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-30");
        contentValues.put("Name","Christian Dave B. Cuarentas");
        contentValues.put("Field_SupervisorID","FS2-14");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-24");
        contentValues.put("Name","Meline R. Canada");
        contentValues.put("Field_SupervisorID","FS2-14");
        contentValues.put("BarangayCode","126303012"); // LABANGAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //FS HAS POWER TO INTERVIEW
        contentValues.put("ID","02-0001");
        contentValues.put("Name", "Cherry Grace L. Regalado");
        contentValues.put("Field_SupervisorID","FS001");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

    /*
    //REMOVE
      contentValues.put("ID","02-35");
        contentValues.put("Name","Hazelene Jhel Arnado");
        contentValues.put("Field_SupervisorID","FS2-10");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-31");
        contentValues.put("Name","Shanerin E. Juanday");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-45");
        contentValues.put("Name","Michelle Mae B. Paldo");
        contentValues.put("Field_SupervisorID","FS2-13");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-46");
        contentValues.put("Name","Agnes N. Matillac");
        contentValues.put("Field_SupervisorID","FS2-13");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-47");
        contentValues.put("Name","Jocelyn J. Deligero");
        contentValues.put("Field_SupervisorID","FS2-13");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-48");
        contentValues.put("Name","Marilyn B. Betira");
        contentValues.put("Field_SupervisorID","FS2-13");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-53");
        contentValues.put("Name","Johana Grace V. Gonzales");
        contentValues.put("Field_SupervisorID","FS2-15");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-58");
        contentValues.put("Name","Baldomero B. Cuyno II");
        contentValues.put("Field_SupervisorID","FS2-16");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-63");
        contentValues.put("Name","Kiegon V. Pijera");
        contentValues.put("Field_SupervisorID","FS2-17");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-64");
        contentValues.put("Name","Allemay R. Orano");
        contentValues.put("Field_SupervisorID","FS2-17");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","02-67");
        contentValues.put("Name","Analie D. Ebrada");
        contentValues.put("Field_SupervisorID","FS2-18");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

         /*REMOVE
        contentValues.put("ID","02-41");
        contentValues.put("Name","Lany C. Berame");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","02-43");
        contentValues.put("Name","Evangeline G. Maluya");
        contentValues.put("Field_SupervisorID","FS2-09");
        contentValues.put("BarangayCode","126303033"); // CALUMPANG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 3
        contentValues.put("ID","03-01");
        contentValues.put("Name","Elfie Dela Cruz");
        contentValues.put("Field_SupervisorID","FS3-01");
        contentValues.put("BarangayCode","126303035"); // Dad North
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-02");
        contentValues.put("Name","Angie Timkang");
        contentValues.put("Field_SupervisorID","FS3-01");
        contentValues.put("BarangayCode","126303035"); // Dad North
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-03");
        contentValues.put("Name","Sambrod Utto");
        contentValues.put("Field_SupervisorID","FS3-01");
        contentValues.put("BarangayCode","126303035"); // Dad North
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-04");
        contentValues.put("Name","Maria Joan Isidro");
        contentValues.put("Field_SupervisorID","FS3-02");
        contentValues.put("BarangayCode","126303007"); // Dad EAST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-05");
        contentValues.put("Name","Gelyn Lopez");
        contentValues.put("Field_SupervisorID","FS3-02");
        contentValues.put("BarangayCode","126303007"); // Dad EAST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-06");
        contentValues.put("Name","Mark John Manguhig");
        contentValues.put("Field_SupervisorID","FS3-02");
        contentValues.put("BarangayCode","126303007"); // Dad EAST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-07");
        contentValues.put("Name","Menchie Reguila");
        contentValues.put("Field_SupervisorID","FS3-03");
        contentValues.put("BarangayCode","126303037"); // Dad WEST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-08");
        contentValues.put("Name","Lilac Dela China");
        contentValues.put("Field_SupervisorID","FS3-03");
        contentValues.put("BarangayCode","126303037"); // Dad WEST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-09");
        contentValues.put("Name","Sittie Julayfa Tumbaga");
        contentValues.put("Field_SupervisorID","FS3-03");
        contentValues.put("BarangayCode","126303037"); // Dad WEST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-10");
        contentValues.put("Name","Gerlie Baliad");
        contentValues.put("Field_SupervisorID","FS3-04");
        contentValues.put("BarangayCode","126303037"); // Dad WEST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-11");
        contentValues.put("Name","Paharodden Casim");
        contentValues.put("Field_SupervisorID","FS3-04");
        contentValues.put("BarangayCode","126303037"); // Dad WEST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-12");
        contentValues.put("Name","Ruby Anne Palaca");
        contentValues.put("Field_SupervisorID","FS3-04");
        contentValues.put("BarangayCode","126303037"); // Dad WEST
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-13");
        contentValues.put("Name","Lilian Flores");
        contentValues.put("Field_SupervisorID","FS3-05");
        contentValues.put("BarangayCode","126303036"); // Dad SOUTH
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-14");
        contentValues.put("Name","Alayssa Radie");
        contentValues.put("Field_SupervisorID","FS3-05");
        contentValues.put("BarangayCode","126303036"); // Dad SOUTH
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-15");
        contentValues.put("Name","Lady Fe Estafia");
        contentValues.put("Field_SupervisorID","FS3-05");
        contentValues.put("BarangayCode","126303036"); // Dad SOUTH
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-16");
        contentValues.put("Name","Rachelle Obseñares");
        contentValues.put("Field_SupervisorID","FS3-05");
        contentValues.put("BarangayCode","126303036"); // Dad SOUTH
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-17");
        contentValues.put("Name","Elisa Agustines");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034"); // HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-18");
        contentValues.put("Name","Maryknoll Lucero");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-19");
        contentValues.put("Name","Darbel Karelle");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-20");
        contentValues.put("Name","Jasper Delos Santos");
        contentValues.put("Field_SupervisorID","FS3-07");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-21");
        contentValues.put("Name","Krence Angel Buaya");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-22");
        contentValues.put("Name","Eunie Cabante");
        contentValues.put("Field_SupervisorID","FS3-07");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-23");
        contentValues.put("Name","Anna Marie Torino");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-24");
        contentValues.put("Name","Ellen Mae Berida");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-25");
        contentValues.put("Name","Roer Jaco");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303034");// HEIGHTS
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //TEAM 3B
        contentValues.put("ID","03-26");
        contentValues.put("Name","Niel Bert Acuña");
        contentValues.put("Field_SupervisorID","FS3-09");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-27");
        contentValues.put("Name","Fredeline Belarmino");
        contentValues.put("Field_SupervisorID","FS3-09");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-28");
        contentValues.put("Name","Liezell Caserial");
        contentValues.put("Field_SupervisorID","FS3-09");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-29");
        contentValues.put("Name","Argylle Doctora");
        contentValues.put("Field_SupervisorID","FS3-09");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-30");
        contentValues.put("Name","Junjielyn Egot");
        contentValues.put("Field_SupervisorID","FS3-15");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-31");
        contentValues.put("Name","Andy Niel Gan");
        contentValues.put("Field_SupervisorID","FS3-17");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-32");
        contentValues.put("Name","Thereza Mae Guinocan");
        contentValues.put("Field_SupervisorID","FS3-13");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-33");
        contentValues.put("Name","Jason Malantas");
        contentValues.put("Field_SupervisorID","FS3-09");
        contentValues.put("BarangayCode","126303026");// SINAWAL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        contentValues.put("ID","03-34");
        contentValues.put("Name","Aisa Ganade");
        contentValues.put("Field_SupervisorID","FS3-11");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-35");
        contentValues.put("Name","Care Angelika Rabanes");
        contentValues.put("Field_SupervisorID","FS3-16");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-36");
        contentValues.put("Name","Joselito Bidas Jr.");
        contentValues.put("Field_SupervisorID","FS3-15");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-37");
        contentValues.put("Name","Shiela Mae Mondrano");
        contentValues.put("Field_SupervisorID","FS3-11");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-38");
        contentValues.put("Name","Bernando Ganade");
        contentValues.put("Field_SupervisorID","FS3-12");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-39");
        contentValues.put("Name","Krystal Joy Gabuya");
        contentValues.put("Field_SupervisorID","FS3-12");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-40");
        contentValues.put("Name","Janette Apolinario");
        contentValues.put("Field_SupervisorID","FS3-12");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-41");
        contentValues.put("Name","Nory Grace Orapa");
        contentValues.put("Field_SupervisorID","FS3-12");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-42");
        contentValues.put("Name","Abdul Satar Diahing");
        contentValues.put("Field_SupervisorID","FS3-13");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-43");
        contentValues.put("Name","Sarrah Jean Lagahan");
        contentValues.put("Field_SupervisorID","FS3-13");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-44");
        contentValues.put("Name","Jonalyn Mahinay");
        contentValues.put("Field_SupervisorID","FS3-13");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //14
        contentValues.put("ID","03-45");
        contentValues.put("Name","Joniel Dituya");
        contentValues.put("Field_SupervisorID","FS3-15");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-46");
        contentValues.put("Name","Ana Mae Pelesco");
        contentValues.put("Field_SupervisorID","FS3-14");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-47");
        contentValues.put("Name","Harold Mambahin");
        contentValues.put("Field_SupervisorID","FS3-09");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //15
        contentValues.put("ID","03-48");
        contentValues.put("Name","Jennifer Duguil");
        contentValues.put("Field_SupervisorID","FS3-16");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-49");
        contentValues.put("Name","Rozelle Macalandag");
        contentValues.put("Field_SupervisorID","FS3-13");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-50");
        contentValues.put("Name","ZendyRelle Diana");
        contentValues.put("Field_SupervisorID","FS3-15");
        contentValues.put("BarangayCode","126303029");// APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //16
        contentValues.put("ID","03-51");
        contentValues.put("Name","Paul Glazer Dichos");
        contentValues.put("Field_SupervisorID","FS3-16");
        contentValues.put("BarangayCode","126303029");// .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-52");
        contentValues.put("Name","Minchelle Paderes");
        contentValues.put("Field_SupervisorID","FS3-16");
        contentValues.put("BarangayCode","126303029");// .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-53");
        contentValues.put("Name","Maryknoll Bourne Fernandez");
        contentValues.put("Field_SupervisorID","FS3-16");
        contentValues.put("BarangayCode","126303029");// .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-54");
        contentValues.put("Name","Cynthia Mae Rellin");
        contentValues.put("Field_SupervisorID","FS3-16");
        contentValues.put("BarangayCode","126303029");// .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //17
        contentValues.put("ID","03-55");
        contentValues.put("Name","Rhyna Chiesa Bahonsua");
        contentValues.put("Field_SupervisorID","FS3-17");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-56");
        contentValues.put("Name","Shelonie Grace Bonita");
        contentValues.put("Field_SupervisorID","FS3-15");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-57");
        contentValues.put("Name","Vanessa Estrella");
        contentValues.put("Field_SupervisorID","FS3-17");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-58");
        contentValues.put("Name","Ryan Mark Bacongga");
        contentValues.put("Field_SupervisorID","FS3-17");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /////////TEAM 3 FS TO INTERVIEW WITH THEIR PARTNER FS

        contentValues.put("ID","03-0001");
        contentValues.put("Name","Merlyn Casabuena");
        contentValues.put("Field_SupervisorID","FS3-05");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-0002");
        contentValues.put("Name","Rosebell Estocapio");
        contentValues.put("Field_SupervisorID","FS3-05");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-0003");
        contentValues.put("Name","Chimae Aunzo");
        contentValues.put("Field_SupervisorID","FS3-04");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-0004");
        contentValues.put("Name","Karen Maghanoy");
        contentValues.put("Field_SupervisorID","FS3-03");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-0005");
        contentValues.put("Name","Leilan Jane Nebres");
        contentValues.put("Field_SupervisorID","FS3-02");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-0006");
        contentValues.put("Name","Jonisa Bongabong");
        contentValues.put("Field_SupervisorID","FS3-08");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","03-0007");
        contentValues.put("Name","Jerome Sinangote");
        contentValues.put("Field_SupervisorID","FS3-06");
        contentValues.put("BarangayCode","126303029");// .APOPONG
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //////NEW FI's WITH SCHOOL
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // CRONESIA 1-5
        // EACH FI's and FS WILL HAVE A DIFFERENT BARANGAY EVERY DAY
        //DEFAULT BARANGAY - KATANGAWAN

        ///////1
        contentValues.put("ID","FI-01");
        contentValues.put("Name","Era Joie Tano");
        contentValues.put("Field_SupervisorID","FS-01");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-02");
        contentValues.put("Name","John Eric Capion");
        contentValues.put("Field_SupervisorID","FS-01");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-03");
        contentValues.put("Name","Shiela Mae Forro");
        contentValues.put("Field_SupervisorID","FS-02");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-04");
        contentValues.put("Name","Edilien Jadraque");
        contentValues.put("Field_SupervisorID","FS-02");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-05");
        contentValues.put("Name","Joylyn Palmares");
        contentValues.put("Field_SupervisorID","FS-05");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        ////////////////////////////////////////////////////////////////////////////
        // MMG SCHOOL 6-
        contentValues.put("ID","FI-06");
        contentValues.put("Name","Cinderella Marie Abordo");
        contentValues.put("Field_SupervisorID","FS-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-16");
        contentValues.put("Name","Irish Guendez Joy Panding");
        contentValues.put("Field_SupervisorID","FS-03");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //FS-04
        contentValues.put("ID","FI-09");
        contentValues.put("Name","JeeFrance Demetrio");
        contentValues.put("Field_SupervisorID","FS-04");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-11");
        contentValues.put("Name","Jennifer Gozon");
        contentValues.put("Field_SupervisorID","FS-04");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-15");
        contentValues.put("Name","Nordielyn Nabella");
        contentValues.put("Field_SupervisorID","FS-04");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-10");
        contentValues.put("Name","Kodie Mark Embolodi");
        contentValues.put("Field_SupervisorID","FS-04");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //FS-05
        contentValues.put("ID","FI-12");
        contentValues.put("Name","Mohammed Minanga");
        contentValues.put("Field_SupervisorID","FS-05");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-13");
        contentValues.put("Name","Princess Jean Malangkad");
        contentValues.put("Field_SupervisorID","FS-05");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //FS-06

        contentValues.put("ID","FI-07");
        contentValues.put("Name","Nora Alim");
        contentValues.put("Field_SupervisorID","FS-06");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-08");
        contentValues.put("Name","Rachel Bandoy");
        contentValues.put("Field_SupervisorID","FS-06");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-14");
        contentValues.put("Name","Ralph Jeric Judis");
        contentValues.put("Field_SupervisorID","FS-06");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-07
        contentValues.put("ID","FI-17");
        contentValues.put("Name","Lucy May Romeo");
        contentValues.put("Field_SupervisorID","FS-07");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-18");
        contentValues.put("Name","Lovella Sanchez");
        contentValues.put("Field_SupervisorID","FS-07");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-19");
        contentValues.put("Name","Ramie Lee Benjamin");
        contentValues.put("Field_SupervisorID","FS-07");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-08
        contentValues.put("ID","FI-20");
        contentValues.put("Name","Glicerio Rios");
        contentValues.put("Field_SupervisorID","FS-08");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-21");
        contentValues.put("Name","Swetzel Wating");
        contentValues.put("Field_SupervisorID","FS-08");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-09
        // STRATFORD

        contentValues.put("ID","FI-22");
        contentValues.put("Name","Elle Jean Alcibar");
        contentValues.put("Field_SupervisorID","FS-09");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-23");
        contentValues.put("Name","Lety Inderio");
        contentValues.put("Field_SupervisorID","FS-09");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-10
        contentValues.put("ID","FI-24");
        contentValues.put("Name","Emma Tobias");
        contentValues.put("Field_SupervisorID","FS-10");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-25");
        contentValues.put("Name","Girlie Marie Valle");
        contentValues.put("Field_SupervisorID","FS-10");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-11
        contentValues.put("ID","FI-26");
        contentValues.put("Name","Mellan Stepane");
        contentValues.put("Field_SupervisorID","FS-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-27");
        contentValues.put("Name","Arnes Evida");
        contentValues.put("Field_SupervisorID","FS-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-28");
        contentValues.put("Name","Frances Tiffany Pagulong");
        contentValues.put("Field_SupervisorID","FS-11");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-12
        contentValues.put("ID","FI-29");
        contentValues.put("Name","Angelo Barrientos");
        contentValues.put("Field_SupervisorID","FS-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-30");
        contentValues.put("Name","Katherine Ann Inderio");
        contentValues.put("Field_SupervisorID","FS-12");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);






        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 4
        //1
        contentValues.put("ID","04-01");
        contentValues.put("Name","Marie Mae Villalobos");
        contentValues.put("Field_SupervisorID","FS4-01");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-02");
        contentValues.put("Name","Airene Halungkay");
        contentValues.put("Field_SupervisorID","FS4-01");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-03");
        contentValues.put("Name","Reynaldo Mangubat Jr.");
        contentValues.put("Field_SupervisorID","FS4-01");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //2
        contentValues.put("ID","04-04");
        contentValues.put("Name","Josephine Lomarda");
        contentValues.put("Field_SupervisorID","FS4-02");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-05");
        contentValues.put("Name","Ruben L. Nicolas");
        contentValues.put("Field_SupervisorID","FS4-02");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-06");
        contentValues.put("Name","Ailyn Humangpang");
        contentValues.put("Field_SupervisorID","FS4-02");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //3
        /*
        contentValues.put("ID","04-07");
        contentValues.put("Name","Angelou Hechanova");
        contentValues.put("Field_SupervisorID","FS4-03");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","04-08");
        contentValues.put("Name","Joey Kawing");
        contentValues.put("Field_SupervisorID","FS4-03");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-09");
        contentValues.put("Name","Judy Ann Chiva");
        contentValues.put("Field_SupervisorID","FS4-03");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //4
        /*
        contentValues.put("ID","04-10");
        contentValues.put("Name","Jellymie Ann To-os");
        contentValues.put("Field_SupervisorID","FS4-04");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);
        */

        contentValues.put("ID","04-11");
        contentValues.put("Name","Norpiya Solaiman");
        contentValues.put("Field_SupervisorID","FS4-03");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-12");
        contentValues.put("Name","Mosmaira Acob");
        contentValues.put("Field_SupervisorID","FS4-03");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //5
        contentValues.put("ID","04-13");
        contentValues.put("Name","Lenore Elumba");
        contentValues.put("Field_SupervisorID","FS4-04");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-14");
        contentValues.put("Name","Violy Ann Grajido");
        contentValues.put("Field_SupervisorID","FS4-04");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-15");
        contentValues.put("Name","Rosel Grace Cruz");
        contentValues.put("Field_SupervisorID","FS4-04");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //6
        contentValues.put("ID","04-16");
        contentValues.put("Name","Aldrin Reyes");
        contentValues.put("Field_SupervisorID","FS4-05");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-17");
        contentValues.put("Name","Girly Jeth Ampan");
        contentValues.put("Field_SupervisorID","FS4-01");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-18");
        contentValues.put("Name","Joan Becoy");
        contentValues.put("Field_SupervisorID","FS4-05");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //7
        contentValues.put("ID","04-19");
        contentValues.put("Name","Loida Cheryle Mazo");
        contentValues.put("Field_SupervisorID","FS4-07");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-20");
        contentValues.put("Name","Kristyn Pril Solatorio");
        contentValues.put("Field_SupervisorID","FS4-07");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-021");
        contentValues.put("Name","Christine Jane Patricio");
        contentValues.put("Field_SupervisorID","FS4-07");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        /* FI's RESIGNS
        //4
            contentValues.put("ID","04-01");
            contentValues.put("Name","Recelita Maway");
            contentValues.put("Field_SupervisorID","FS4-01");
            contentValues.put("BarangayCode","126303038");// FATIMA
            db.insert(getTableName_InterviewerProfile(), null, contentValues);

            contentValues.put("ID","04-02");
            contentValues.put("Name","Honnielyn Ngalon");
            contentValues.put("Field_SupervisorID","FS4-01");
            contentValues.put("BarangayCode","126303038");// FATIMA
            db.insert(getTableName_InterviewerProfile(), null, contentValues);

            contentValues.put("ID","04-03");
            contentValues.put("Name","Ivy Sargado");
            contentValues.put("Field_SupervisorID","FS4-01");
            contentValues.put("BarangayCode","126303038");// FATIMA
            db.insert(getTableName_InterviewerProfile(), null, contentValues);

            contentValues.put("ID","04-04");
            contentValues.put("Name","Kristine Kaye Eding");
            contentValues.put("Field_SupervisorID","FS4-01");
            contentValues.put("BarangayCode","126303038");// FATIMA
            db.insert(getTableName_InterviewerProfile(), null, contentValues);


            //3

            contentValues.put("ID","04-09");
            contentValues.put("Name","Vivien Joy Boniel");
            contentValues.put("Field_SupervisorID","FS4-03");
            contentValues.put("BarangayCode","126303038");// FATIMA
            db.insert(getTableName_InterviewerProfile(), null, contentValues);

            contentValues.put("ID","04-010");
            contentValues.put("Name","Nice Momo");
            contentValues.put("Field_SupervisorID","FS4-03");
            contentValues.put("BarangayCode","126303038");// FATIMA
            db.insert(getTableName_InterviewerProfile(), null, contentValues);
            */

        contentValues.put("ID","04-08");
        contentValues.put("Name","Antonette Villareal");
        contentValues.put("Field_SupervisorID","FS4-03");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);




        //SAN JOSE
        //8
        //1
        /*
            contentValues.put("ID","04-21");
            contentValues.put("Name","Cindy Grace Barde");
            contentValues.put("Field_SupervisorID","FS4-08");
            contentValues.put("BarangayCode","126303024");// SAN JOSE
            db.insert(getTableName_InterviewerProfile(), null, contentValues);
            */

        contentValues.put("ID","04-22");
        contentValues.put("Name","Rosalin Labao");
        contentValues.put("Field_SupervisorID","FS4-08");
        contentValues.put("BarangayCode","126303024");// SAN JOSE
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

            /*
            contentValues.put("ID","04-23");
            contentValues.put("Name","Mercedetha Silak");
            contentValues.put("Field_SupervisorID","FS4-08");
            contentValues.put("BarangayCode","126303024");// SAN JOSE
            db.insert(getTableName_InterviewerProfile(), null, contentValues);
            */

        //9
        //2
        contentValues.put("ID","04-24");
        contentValues.put("Name","Jay Mari Silak");
        contentValues.put("Field_SupervisorID","FS4-08");
        contentValues.put("BarangayCode","126303024");// SAN JOSE
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-25");
        contentValues.put("Name","Novelyn Papasin");
        contentValues.put("Field_SupervisorID","FS4-08");
        contentValues.put("BarangayCode","126303024");// SAN JOSE
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-26");
        contentValues.put("Name","Raquel Purgo");
        contentValues.put("Field_SupervisorID","FS4-08");
        contentValues.put("BarangayCode","126303024");// SAN JOSE
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //SIGUEL
        //10
        //1
        contentValues.put("ID","04-27");
        contentValues.put("Name","Blessa Guhiling");
        contentValues.put("Field_SupervisorID","FS4-10");
        contentValues.put("BarangayCode","126303030");// SIGUEL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

            /*
            contentValues.put("ID","04-28");
            contentValues.put("Name","Eliseo Cabbab");
            contentValues.put("Field_SupervisorID","FS4-10");
            contentValues.put("BarangayCode","126303030");// SIGUEL
            db.insert(getTableName_InterviewerProfile(), null, contentValues);
            */

        contentValues.put("ID","04-29");
        contentValues.put("Name","Jaybert Baladad");
        contentValues.put("Field_SupervisorID","FS4-10");
        contentValues.put("BarangayCode","126303030");// SIGUEL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //11
        //2
        contentValues.put("ID","04-30");
        contentValues.put("Name","Jowie Padron");
        contentValues.put("Field_SupervisorID","FS4-11");
        contentValues.put("BarangayCode","126303030");// SIGUEL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","04-31");
        contentValues.put("Name","Germalyn Magbanua");
        contentValues.put("Field_SupervisorID","FS4-11");
        contentValues.put("BarangayCode","126303030");// SIGUEL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","04-32");
        contentValues.put("Name","Dianico Daniel");
        contentValues.put("Field_SupervisorID","FS4-11");
        contentValues.put("BarangayCode","126303030");// SIGUEL
        db.insert(getTableName_InterviewerProfile(), null, contentValues);



        //TAMBLER
        //12
        //1
        contentValues.put("ID","04-33");
        contentValues.put("Name","Lucia Lumbos");
        contentValues.put("Field_SupervisorID","FS4-12");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

            /*REMOVE
            contentValues.put("ID","04-34");
            contentValues.put("Name","Aliman Amando");
            contentValues.put("Field_SupervisorID","FS4-14");
            contentValues.put("BarangayCode","126303027");// TAMBLER
            db.insert(getTableName_InterviewerProfile(), null, contentValues);
            */

        contentValues.put("ID","04-35");
        contentValues.put("Name","Meriam Dela Rosa");
        contentValues.put("Field_SupervisorID","FS4-12");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-36");
        contentValues.put("Name","Venus Cauba");
        contentValues.put("Field_SupervisorID","FS4-12");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //13
        //2
        contentValues.put("ID","04-37");
        contentValues.put("Name","Maricel Billiones");
        contentValues.put("Field_SupervisorID","FS4-13");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","04-38");
        contentValues.put("Name","Hannah Subaan");
        contentValues.put("Field_SupervisorID","FS4-13");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        contentValues.put("ID","04-39");
        contentValues.put("Name","Marife Catarman");
        contentValues.put("Field_SupervisorID","FS4-13");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //14
        //3
        contentValues.put("ID","04-40");
        contentValues.put("Name","Aniza Manson");
        contentValues.put("Field_SupervisorID","FS4-14");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-41");
        contentValues.put("Name","Neddie Catarman");
        contentValues.put("Field_SupervisorID","FS4-14");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","04-42");
        contentValues.put("Name","Angel Lee Rulete");
        contentValues.put("Field_SupervisorID","FS4-14");
        contentValues.put("BarangayCode","126303027");// TAMBLER
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS TO FI
        contentValues.put("ID","04-43");
        contentValues.put("Name", "Randyn Demonteverde");
        contentValues.put("Field_SupervisorID","FS4-05");
        contentValues.put("BarangayCode","126303038");// FATIMA
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 5
        contentValues.put("ID","05-01");
        contentValues.put("Name","Glendale Alipoon");
        contentValues.put("Field_SupervisorID","FS5-01");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-02");
        contentValues.put("Name","Chessa Mae Ogsoc");
        contentValues.put("Field_SupervisorID","FS5-03");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-03");
        contentValues.put("Name","Vrylle Rovee Gabriel");
        contentValues.put("Field_SupervisorID","FS5-01");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //2
        contentValues.put("ID","05-04");
        contentValues.put("Name","Relyn Ramos");
        contentValues.put("Field_SupervisorID","FS5-02");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-05");
        contentValues.put("Name","Renee Rose Ramos");
        contentValues.put("Field_SupervisorID","FS5-02");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-06");
        contentValues.put("Name","Carla Chua");
        contentValues.put("Field_SupervisorID","FS5-02");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //3
        contentValues.put("ID","05-07");
        contentValues.put("Name","Stephen Abiner");
        contentValues.put("Field_SupervisorID","FS5-03");
        contentValues.put("BarangayCode","126303011");// BALUAN .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-08");
        contentValues.put("Name","Lovely Rose Marcelino");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-09");
        contentValues.put("Name","John Ray Culaba");
        contentValues.put("Field_SupervisorID","FS5-03");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //4
        contentValues.put("ID","05-010");
        contentValues.put("Name","Veronica Cara-an");
        contentValues.put("Field_SupervisorID","FS5-04");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-011");
        contentValues.put("Name","Evelyn Cambarijan");
        contentValues.put("Field_SupervisorID","FS5-04");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-012");
        contentValues.put("Name","Arnel Rodis");
        contentValues.put("Field_SupervisorID","FS5-04");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //5
        contentValues.put("ID","05-013");
        contentValues.put("Name","Ivie Carael");
        contentValues.put("Field_SupervisorID","FS5-05");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-014");
        contentValues.put("Name","Joemar Celestino");
        contentValues.put("Field_SupervisorID","FS5-05");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-015");
        contentValues.put("Name","Charlene Ombrosa");
        contentValues.put("Field_SupervisorID","FS5-05");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //6
        contentValues.put("ID","05-016");
        contentValues.put("Name","Jumel Paul De Leon");
        contentValues.put("Field_SupervisorID","FS5-15");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-017");
        contentValues.put("Name","Jeramie Pangilan");
        contentValues.put("Field_SupervisorID","FS5-09");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-018");
        contentValues.put("Name","Vicente Agosto Jr,");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //7
        contentValues.put("ID","05-019");
        contentValues.put("Name","Art Quelvin Pagco");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-020");
        contentValues.put("Name","Zyrill Jam Desesto");
        contentValues.put("Field_SupervisorID","FS5-02");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-021");
        contentValues.put("Name","Kenneth Galido");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //8
        contentValues.put("ID","05-022");
        contentValues.put("Name","Jenelyn Silagan");
        contentValues.put("Field_SupervisorID","FS5-08");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-023");
        contentValues.put("Name","Sonny Jay Sevilla");
        contentValues.put("Field_SupervisorID","FS5-08");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-024");
        contentValues.put("Name","Jeveanne Fernandez");
        contentValues.put("Field_SupervisorID","FS5-08");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //9
        contentValues.put("ID","05-025");
        contentValues.put("Name","Ma. Lorena Paran");
        contentValues.put("Field_SupervisorID","FS5-09");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-026");
        contentValues.put("Name","Honelyn Torrefiel");
        contentValues.put("Field_SupervisorID","FS5-09");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-027");
        contentValues.put("Name","Cherry Ann Mayo");
        contentValues.put("Field_SupervisorID","FS5-09");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //10
        contentValues.put("ID","05-028");
        contentValues.put("Name","Rose Del Carmen");
        contentValues.put("Field_SupervisorID","FS5-10");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-029");
        contentValues.put("Name","Debbie Ampatin");
        contentValues.put("Field_SupervisorID","FS5-10");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-030");
        contentValues.put("Name","Gerimil Gimotea");
        contentValues.put("Field_SupervisorID","FS5-10");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //11
        contentValues.put("ID","05-031");
        contentValues.put("Name","Daizy Telen");
        contentValues.put("Field_SupervisorID","FS5-11");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-032");
        contentValues.put("Name","Walter Garcia Jr.");
        contentValues.put("Field_SupervisorID","FS5-11");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-033");
        contentValues.put("Name","Hermilindo Rabanos");
        contentValues.put("Field_SupervisorID","FS5-11");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //12
        contentValues.put("ID","05-034");
        contentValues.put("Name","Jerald Cabreros");
        contentValues.put("Field_SupervisorID","FS5-12");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-035");
        contentValues.put("Name","Janiya Andang");
        contentValues.put("Field_SupervisorID","FS5-12");
        contentValues.put("BarangayCode","126303003");// BALUAN .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-036");
        contentValues.put("Name","Saimona Cabugatan");
        contentValues.put("Field_SupervisorID","FS5-12");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        //13
        contentValues.put("ID","05-037");
        contentValues.put("Name","Melanie Tampos");
        contentValues.put("Field_SupervisorID","FS5-13");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-038");
        contentValues.put("Name","Patrick Ray Mosquite");
        contentValues.put("Field_SupervisorID","FS5-13");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-039");
        contentValues.put("Name","Mary Roxan Gelanan");
        contentValues.put("Field_SupervisorID","FS5-13");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //14
        contentValues.put("ID","05-040");
        contentValues.put("Name","Ana Jocelyn Cera");
        contentValues.put("Field_SupervisorID","FS5-02");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-041");
        contentValues.put("Name","Jerald Rabanos");
        contentValues.put("Field_SupervisorID","FS5-13");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-042");
        contentValues.put("Name","Annie Rose Mejares");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //15
        contentValues.put("ID","05-043");
        contentValues.put("Name","Jan Wilfred Booc");
        contentValues.put("Field_SupervisorID","FS5-15");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-044");
        contentValues.put("Name","Christine Joy Belo");
        contentValues.put("Field_SupervisorID","FS5-15");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-045");
        contentValues.put("Name","Crislyn Tindan");
        contentValues.put("Field_SupervisorID","FS5-15");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //16
        contentValues.put("ID","05-046");
        contentValues.put("Name","Jobert Roco");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-047");
        contentValues.put("Name","Ellaine Tweety Lacay");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-048");
        contentValues.put("Name","Jerico Morcillo");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //17
        contentValues.put("ID","05-049");
        contentValues.put("Name","Krisan Lapura");
        contentValues.put("Field_SupervisorID","FS5-17");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-050");
        contentValues.put("Name","Jenny Rose Cautivar");
        contentValues.put("Field_SupervisorID","FS5-17");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-051");
        contentValues.put("Name","Billy Rey Panes");
        contentValues.put("Field_SupervisorID","FS5-17");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //18
        contentValues.put("ID","05-052");
        contentValues.put("Name","Jerrold Barrientos");
        contentValues.put("Field_SupervisorID","FS5-18");
        contentValues.put("BarangayCode","126303003");// BALUAN .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-053");
        contentValues.put("Name","Princess Joy Bernabe");
        contentValues.put("Field_SupervisorID","FS5-13");
        contentValues.put("BarangayCode","126303003");// BALUAN .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-054");
        contentValues.put("Name","Rose Masulong");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303011");// LAGAO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //19
        contentValues.put("ID","05-055");
        contentValues.put("Name","Ellen Barrientos");
        contentValues.put("Field_SupervisorID","FS5-13");
        contentValues.put("BarangayCode","126303003");// BALUAN .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-056");
        contentValues.put("Name","Aisa Rama");
        contentValues.put("Field_SupervisorID","FS5-16");
        contentValues.put("BarangayCode","126303003");// BALUAN .
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","05-057");
        contentValues.put("Name","Jophet Mahinay");
        contentValues.put("Field_SupervisorID","FS5-19");
        contentValues.put("BarangayCode","126303023");// SAN ISIDRO
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        /////////////////////////////////////////////
        //////FI's WITH SCHOOLS
        //FS 13
        contentValues.put("ID","FI-31");
        contentValues.put("Name","April Cabradilla");
        contentValues.put("Field_SupervisorID","FS-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-32");
        contentValues.put("Name","Ara Angelica Lagman");
        contentValues.put("Field_SupervisorID","FS-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS 14
        contentValues.put("ID","FI-33");
        contentValues.put("Name","Mharnie Matantu");
        contentValues.put("Field_SupervisorID","FS-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-34");
        contentValues.put("Name","Mary Joy Montilla");
        contentValues.put("Field_SupervisorID","FS-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS 15
        contentValues.put("ID","FI-35");
        contentValues.put("Name","Vanessa Oval Bontigao");
        contentValues.put("Field_SupervisorID","FS-15");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-36");
        contentValues.put("Name","Crisza Mae Denzon");
        contentValues.put("Field_SupervisorID","FS-15");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-37");
        contentValues.put("Name","Mark John Galanida");
        contentValues.put("Field_SupervisorID","FS-15");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS 16
        contentValues.put("ID","FI-38");
        contentValues.put("Name","Camill Miral");
        contentValues.put("Field_SupervisorID","FS-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-39");
        contentValues.put("Name","Ivy Ann Momo");
        contentValues.put("Field_SupervisorID","FS-16");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-40");
        contentValues.put("Name","Hanna Grace Napoles");
        contentValues.put("Field_SupervisorID","FS-14");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS 17
        contentValues.put("ID","FI-41");
        contentValues.put("Name","Razel Navarro");
        contentValues.put("Field_SupervisorID","FS-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-42");
        contentValues.put("Name","Roellyn Joy Rodriguez");
        contentValues.put("Field_SupervisorID","FS-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-43");
        contentValues.put("Name","Honey Mae Rosales");
        contentValues.put("Field_SupervisorID","FS-17");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS 18
        contentValues.put("ID","FI-44");
        contentValues.put("Name","Norjina Salic");
        contentValues.put("Field_SupervisorID","FS-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-45");
        contentValues.put("Name","Jeysa Vecio");
        contentValues.put("Field_SupervisorID","FS-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-46");
        contentValues.put("Name","Renalou Tayona");
        contentValues.put("Field_SupervisorID","FS-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        //FS-19
        contentValues.put("ID","FI-47");
        contentValues.put("Name","Yvonne Sumuba");
        contentValues.put("Field_SupervisorID","FS-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-48");
        contentValues.put("Name","John Paul Castillon");
        contentValues.put("Field_SupervisorID","FS-18");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);

        contentValues.put("ID","FI-49");
        contentValues.put("Name", "Louwie Mae Plenos");
        contentValues.put("Field_SupervisorID","FS-13");
        contentValues.put("BarangayCode","126303009"); // KATANGAWAN
        db.insert(getTableName_InterviewerProfile(), null, contentValues);


        try
        {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"InterviewerProfile Table:"+e.toString());
            db.endTransaction();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insert_Supervisor_Profile() {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 1
        contentValues.put("Field_SupervisorID", "FS1-01");
        contentValues.put("Name", "Dayanara M. Bentaib");
        contentValues.put("Pass", "dayan194");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS1-02");
        contentValues.put("Name", "Jomar U. Haber");
        contentValues.put("Pass", "jomar123");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS1-03");
        contentValues.put("Name", "Maria Theresa D. Balongan");
        contentValues.put("Pass", "gha10678");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /*
        contentValues.put("Field_SupervisorID", "FS1-04");
        contentValues.put("Name", "Fritz F. Erquita");
        contentValues.put("Pass", "fritz29");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */

        contentValues.put("Field_SupervisorID", "FS1-05");
        contentValues.put("Name", "Noel L. Poquita");
        contentValues.put("Pass", "beauty78");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /*
        contentValues.put("Field_SupervisorID", "FS1-06");
        contentValues.put("Name", "Mikee F. Gales");
        contentValues.put("Pass", "mfg0203");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-07");
        contentValues.put("Name", "Kate Melody B. Gurra");
        contentValues.put("Pass", "ydolem02");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */

        contentValues.put("Field_SupervisorID", "FS1-08");
        contentValues.put("Name", "Fritzie Z. Salazar");
        contentValues.put("Pass", "godgrace");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-09");
        contentValues.put("Name", "Maricel A. Busano");
        contentValues.put("Pass", "mab701");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-10");
        contentValues.put("Name", "Raquel C. Payongayong");
        contentValues.put("Pass", "110874rp");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-11");
        contentValues.put("Name", "Rachel Fe M. Cortez");
        contentValues.put("Pass", "jamal410");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-12");
        contentValues.put("Name", "Josefina C. Lapasaran");
        contentValues.put("Pass", "jcl31971");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /*
        contentValues.put("Field_SupervisorID", "FS1-13");
        contentValues.put("Name", "Claire G. Panes");
        contentValues.put("Pass", "clara888");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */

        contentValues.put("Field_SupervisorID", "FS1-14");
        contentValues.put("Name", "Abegail C. Pangcoga");
        contentValues.put("Pass", "abby0724");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /*FS TO FI
        contentValues.put("Field_SupervisorID", "FS1-15");
        contentValues.put("Name", "Alma E. Dalid");
        contentValues.put("Pass", "100174ac");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */

        contentValues.put("Field_SupervisorID", "FS1-16");
        contentValues.put("Name", "Joan B. Dumpa");
        contentValues.put("Pass", "shang5");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-17");
        contentValues.put("Name", "Geca C. Omas-as");
        contentValues.put("Pass", "sweet18");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS1-18");
        contentValues.put("Name", "Geralyn P. Masaglang");
        contentValues.put("Pass", "gelyn29");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 2
        //LABANGAL
        contentValues.put("Field_SupervisorID", "FS2-01");
        contentValues.put("Name", "Ryan S. Ali");
        contentValues.put("Pass", "raeeza");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-02");
        contentValues.put("Name", "Christy Joy B Ayuban");
        contentValues.put("Pass", "proverbs36");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-03");
        contentValues.put("Name", "Charita B. Belosillo");
        contentValues.put("Pass", "chaver22");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS2-04");
        contentValues.put("Name", "John Marc F. Macas");
        contentValues.put("Pass", "makie/00");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-05");
        contentValues.put("Name", "Cherrylen E. Orilla");
        contentValues.put("Pass", "orilla14");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-06");
        contentValues.put("Name", "Reward Tancinco");
        contentValues.put("Pass", "drawer89");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

          /*REMOVE
        contentValues.put("Field_SupervisorID", "FS2-04");
        contentValues.put("Name", "Claudine V. De Manuel");
        contentValues.put("Pass", "claud1995");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-07");
        contentValues.put("Name", "John Paulo C. Paghubasan");
        contentValues.put("Pass", "brod06");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */


        //CALUMPANG

        contentValues.put("Field_SupervisorID", "FS2-07");
        contentValues.put("Name", "Carlota C. Lebumfacil");
        contentValues.put("Pass", "uau07726");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS2-08");
        contentValues.put("Name", "Wilfredo H. Canania Jr.");
        contentValues.put("Pass", "christ/01");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-09");
        contentValues.put("Name", "Josephine A. Cachuela");
        contentValues.put("Pass", "verbjo");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS2-10");
        contentValues.put("Name", "Almliet M. Rodriguez");
        contentValues.put("Pass", "110485j");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-11");
        contentValues.put("Name", "Nancy L. Sarit");
        contentValues.put("Pass", "heart14");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-12");
        contentValues.put("Name", "Joel L. Yu");
        contentValues.put("Pass", "counted18");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-13");
        contentValues.put("Name", "John Michael C. Fermosa");
        contentValues.put("Pass", "jmtcfs81");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS2-14");
        contentValues.put("Name", "June B. Javier");
        contentValues.put("Pass", "junie625");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);



        contentValues.put("Field_SupervisorID", "FS2-15");
        contentValues.put("Name", "Cherry Grace L. Regalado");
        contentValues.put("Pass", "cherryla");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


         /* REMOVE
        contentValues.put("Field_SupervisorID", "FS2-09");
        contentValues.put("Name", "Precy Joy Dunque");
        contentValues.put("Pass", "joyjoy17");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */



        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 3
        contentValues.put("Field_SupervisorID", "FS3-01");
        contentValues.put("Name", "Merlyn Casabuena");
        contentValues.put("Pass", "princess");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-02");
        contentValues.put("Name", "Rosebell Estocapio");
        contentValues.put("Pass", "rd041678");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-03");
        contentValues.put("Name", "Chimae Aunzo");
        contentValues.put("Pass", "kamaru");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-04");
        contentValues.put("Name", "Karen Maghanoy");
        contentValues.put("Pass", "khen2324");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-05");
        contentValues.put("Name", "Leilan Jane Nebres");
        contentValues.put("Pass", "leijane1");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-06");
        contentValues.put("Name", "Jonisa Bongabong");
        contentValues.put("Pass", "y4ever16");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-07");
        contentValues.put("Name", "Angelique Jane Deocampo");
        contentValues.put("Pass", "aaange22");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-08");
        contentValues.put("Name", "Jerome Sinangote");
        contentValues.put("Pass", "rome144");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-09");
        contentValues.put("Name", "Rizza Casauay");
        contentValues.put("Pass", "casauayr");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-10");
        contentValues.put("Name", "Rosemae Jay Sandoval");
        contentValues.put("Pass", "sandoval");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-11");
        contentValues.put("Name", "Wella Marie Catalbas");
        contentValues.put("Pass", "yliza29");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-12");
        contentValues.put("Name", "Baileen Toledo");
        contentValues.put("Pass", "leen04");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-13");
        contentValues.put("Name", "Anthonel Burgos");
        contentValues.put("Pass", "fabian1");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-14");
        contentValues.put("Name", "Ladylyn Muninio");
        contentValues.put("Pass", "lasumu21");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-15");
        contentValues.put("Name", "Ofelia Neri");
        contentValues.put("Pass", "pinky22");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-16");
        contentValues.put("Name", "Divina Tenorio");
        contentValues.put("Pass", "gerald96");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS3-17");
        contentValues.put("Name", "Maria Aliyana Supilanas");
        contentValues.put("Pass", "ae0102");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///NEW FS WITH SCHOOL
        contentValues.put("Field_SupervisorID", "FS-01");
        contentValues.put("Name", "Rowena Candido");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-02");
        contentValues.put("Name", "Greg Ian Revilla");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /////////////////////////////////////////////////////////////////////////////////
        // MMG
        contentValues.put("Field_SupervisorID", "FS-03");
        contentValues.put("Name", "Arlene Macaraeg");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-04");
        contentValues.put("Name", "Lyndon Bregente");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-05");
        contentValues.put("Name", "Beverly Himpiso");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS-06");
        contentValues.put("Name", "Michelle Mae Mainar");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-07");
        contentValues.put("Name", "Faith Poral");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-08");
        contentValues.put("Name", "Kharen Rivera");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        ///////////////////////////////////////////////////////////////////////////////////////////
        // STRATFORD

        contentValues.put("Field_SupervisorID", "FS-09");
        contentValues.put("Name", "Kimberly Inderio");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-10");
        contentValues.put("Name", "Raymark Tobias");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-11");
        contentValues.put("Name", "Mark Harold Almuete");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-12");
        contentValues.put("Name", "Aprille Lindsay Lapura");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 4
        //FATIMA

        /*
        //CHANGE TO FI
        contentValues.put("Field_SupervisorID", "FS4-01");
        contentValues.put("Name", "Ruben Nicolas");
        contentValues.put("Pass", "labrador");  //////////////////////
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */

        contentValues.put("Field_SupervisorID", "FS4-01");
        contentValues.put("Name", "Lilybeth Cuevas");
        contentValues.put("Pass", "dlricota");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-02");
        contentValues.put("Name", "Maria Elena Jugarap");
        contentValues.put("Pass", "leni0567");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-03");
        contentValues.put("Name", "Janine Cuello");
        contentValues.put("Pass", "jcuello");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);





        /*
        contentValues.put("Field_SupervisorID", "FS4-04");
        contentValues.put("Name", "Pj Barreo");
        contentValues.put("Pass", "pij1997");
        */

        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        contentValues.put("Field_SupervisorID", "FS4-04");
        contentValues.put("Name", "Ariel Taluse");
        contentValues.put("Pass", "atdaback");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-05");
        contentValues.put("Name", "Tetchi Ordesta");
        contentValues.put("Pass", "senchi2x");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /* FS TO FI
        contentValues.put("Field_SupervisorID", "FS4-06");
        contentValues.put("Name", "Randyn Demonteverde");
        contentValues.put("Pass", "ordanza");   ////////////////////////////////
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);
        */


        contentValues.put("Field_SupervisorID", "FS4-07");
        contentValues.put("Name", "Hazel Mae Pepito");
        contentValues.put("Pass", "pepito24");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        //SAN JOSE

        contentValues.put("Field_SupervisorID", "FS4-08");
        contentValues.put("Name", "Charlyne P. Peralta");
        contentValues.put("Pass", "pancho");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-09");
        contentValues.put("Name", "Isnaira T. Mulod");
        contentValues.put("Pass", "aadose12");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        //SIGUEL

        contentValues.put("Field_SupervisorID", "FS4-10");
        contentValues.put("Name", "Aileen Jugarap");
        contentValues.put("Pass", "adj0601");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-11");
        contentValues.put("Name", "Concepcion Galo");
        contentValues.put("Pass", "galo168");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        //TAMBLER

        contentValues.put("Field_SupervisorID", "FS4-12");
        contentValues.put("Name", "Catherine B. Mistoso");
        contentValues.put("Pass", "borreros");  /////////////////////////////////////////////////////////
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-13");
        contentValues.put("Name", "Hazel C. Subaan");
        contentValues.put("Pass", "1021azle");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS4-14");
        contentValues.put("Name", "Marvelous J. Rarangol");
        contentValues.put("Pass", "baya14");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TEAM 5
        contentValues.put("Field_SupervisorID", "FS5-01");
        contentValues.put("Name", "Karl Francis Pineda");
        contentValues.put("Pass", "fs1987");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-02");
        contentValues.put("Name", "Jude Anthony Bernal");
        contentValues.put("Pass", "kyle0513");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-03");
        contentValues.put("Name", "Donn Gerard Delos Santos");
        contentValues.put("Pass", "ata7477");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-04");
        contentValues.put("Name", "Cherrie Grace Odal");
        contentValues.put("Pass", "grace10");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-05");
        contentValues.put("Name", "Juzyl Junco");
        contentValues.put("Pass", "72594");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-06");
        contentValues.put("Name", "Stephanie Kayne Uy");
        contentValues.put("Pass", "80396");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-07");
        contentValues.put("Name", "Mikko Torzar");
        contentValues.put("Pass", "mikko1994");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-08");
        contentValues.put("Name", "Eduardo Tolentino");
        contentValues.put("Pass", "lucita");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-09");
        contentValues.put("Name", "Deography De Guma");
        contentValues.put("Pass", "chanyeol");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-10");
        contentValues.put("Name", "Janeth Mayoral");
        contentValues.put("Pass", "janeth80");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-11");
        contentValues.put("Name", "Louis Joseph Baluran");
        contentValues.put("Pass", "fs1998");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-12");
        contentValues.put("Name", "Daisy Mae Ranque");
        contentValues.put("Pass", "dmsr1201");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-13");
        contentValues.put("Name", "Catherine Sanchez");
        contentValues.put("Pass", "mako926");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-14");
        contentValues.put("Name", "Peter Leary Brown");
        contentValues.put("Pass", "sycris02");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-15");
        contentValues.put("Name", "Jennie Noble");
        contentValues.put("Pass", "jens16");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-16");
        contentValues.put("Name", "Marivic Fernandez");
        contentValues.put("Pass", "0611msf");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-17");
        contentValues.put("Name", "Rosalyn Magalit");
        contentValues.put("Pass", "rose123");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-18");
        contentValues.put("Name", "Ethel Melevo");
        contentValues.put("Pass", "thel85");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS5-19");
        contentValues.put("Name", "Romejane Mae Finlac");
        contentValues.put("Pass", "chester6");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        //////////////////////////////////////////////////////////////////////////////////////
        //NEW FS's WITH SCHOOL
        contentValues.put("Field_SupervisorID", "FS-13");
        contentValues.put("Name", "Kia Fueconcillo");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-14");
        contentValues.put("Name", "Sheila Mae Sarangaya");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-15");
        contentValues.put("Name", "Elah Birao");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);

        contentValues.put("Field_SupervisorID", "FS-16");
        contentValues.put("Name", "Glenn Gallego");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);




        contentValues.put("Field_SupervisorID", "FS-18");
        contentValues.put("Name", "Jennovie Jay Serentas");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        contentValues.put("Field_SupervisorID", "FS-19");
        contentValues.put("Name", "Mary Jane Toroctocon");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);




        ///ADMIN ACCOUNT
        contentValues.put("Field_SupervisorID", "FS001");
        contentValues.put("Name", "TEAM_ATT");
        db.insert(getTABLE_NAME_SupervisorProfile(), null, contentValues);


        try {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (Exception e) {
            Log.i(TAG, "InterviewerProfile Table:" + e.toString());
            db.endTransaction();
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }



    public boolean insertTransactionHere(String date, String time, String transactionLog)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Date", date);
        contentValues.put("Time", time);
        contentValues.put("TransactionLog", transactionLog);

        try
        {
            db.insert(getTableName_TransactionLog(), null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Transaction Table:"+e.toString());

        }

        if (checkInsert == -1)
        {
            return false;
        } else {
            return true;
        }
    }



    //UPDATE QUERIES
    public boolean updateDataBF(String[] txtViewAll, int totalRow, String HCN, String DateEntered,String barangayCode)
    {
        int CellStart=0;
        long checkInsert = 0;
        SQLiteDatabase db =  this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues;
        for (int row = 0; row < totalRow-2; row++)
        {
            db = this.getWritableDatabase();
            contentValues = new ContentValues();

            contentValues.put("DateEntered", DateEntered);
            contentValues.put("Lastname", txtViewAll[CellStart]);
            CellStart=CellStart+1;
            contentValues.put("Firstname",txtViewAll[CellStart]);
            CellStart=CellStart+1;
            contentValues.put("Middlename",txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("Sex", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C2_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C4", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("C5", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("D6", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("D7", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E8", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E9", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E10", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E10_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E11", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E12", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E13", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E13_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("E14", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F15", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F15_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F16", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_b", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_c", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_d", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_BCG", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_Penta3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV2", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_OPV3", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_HEPAB1", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_MEASLES", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F17_f", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F18", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F19", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F20", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F21", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F21_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22_a", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F22_b", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F23", txtViewAll[CellStart]);
            CellStart = CellStart + 1;
            contentValues.put("F24", txtViewAll[CellStart]);
            CellStart=CellStart+1;
            contentValues.put("BarangayCode", barangayCode);


            checkInsert = db.update(getTableName_BFTable(), contentValues, " HCN=?",new String[] {HCN});

        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateDataGO(Integer[] integersListRadioButtons, String[] stringListEditText,
                                String[] stringListCheckboxes, String HCN, String DateEntered,String barangayCode)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("G25",integersListRadioButtons[0]);
        contentValues.put("G26",stringListEditText[0].toString());
        contentValues.put("H27",stringListEditText[1].toString());

        contentValues.put("H28Meat",stringListEditText[2].toString());
        contentValues.put("H28Seafoods",stringListEditText[3].toString());
        contentValues.put("H28Processed",stringListEditText[4].toString());
        contentValues.put("H28Fruits",stringListEditText[5].toString());
        contentValues.put("H28Vegetables",stringListEditText[6].toString());

        contentValues.put("H29",integersListRadioButtons[1]);
        contentValues.put("H29_OTHERS",stringListEditText[7].toString());

        contentValues.put("I30",integersListRadioButtons[2]);
        contentValues.put("I31",integersListRadioButtons[3]);

        contentValues.put("J32",integersListRadioButtons[4]);
        contentValues.put("J32_Prawn",stringListEditText[8]);
        contentValues.put("J32_Hito",stringListEditText[9]);
        contentValues.put("J32_Pangasius",stringListEditText[10]);
        contentValues.put("J32_Bangus",stringListEditText[11]);
        contentValues.put("J32_Tilapia",stringListEditText[12]);
        contentValues.put("J32_Others",stringListEditText[13]);

        contentValues.put("K33",integersListRadioButtons[5]);
        contentValues.put("K33_Dogs",stringListEditText[14]);
        contentValues.put("K33_Cats",stringListEditText[15]);
        contentValues.put("K33B",integersListRadioButtons[6]);
        contentValues.put("K33C",integersListRadioButtons[7]);

        contentValues.put("L34",integersListRadioButtons[8]);
        contentValues.put("L34A_School",stringListCheckboxes[0]);
        contentValues.put("L34A_GovAgencies",stringListCheckboxes[1]);
        contentValues.put("L34A_NonGov",stringListCheckboxes[2]);
        contentValues.put("L34A_OTHERS",stringListEditText[16].toString());
        contentValues.put("L34B_CBDRRM",stringListCheckboxes[0]);
        contentValues.put("L34B_FirstAid",stringListCheckboxes[1]);
        contentValues.put("L34B_BLS",stringListCheckboxes[2]);
        contentValues.put("L34B_Fire",stringListCheckboxes[3]);
        contentValues.put("L34B_Search",stringListCheckboxes[4]);
        contentValues.put("L34B_OTHERS",stringListEditText[17].toString());

        contentValues.put("L35",integersListRadioButtons[9]);
        contentValues.put("L35_RADIO",stringListCheckboxes[5].toString());
        contentValues.put("L35_TV",stringListCheckboxes[6].toString());
        contentValues.put("L35_PUB",stringListCheckboxes[7].toString());
        contentValues.put("L35_SMS",stringListCheckboxes[8].toString());
        contentValues.put("L35_SIREN",stringListCheckboxes[9].toString());
        contentValues.put("L35_INTERNET",stringListCheckboxes[10].toString());
        contentValues.put("L35_OTHER",stringListEditText[18].toString());

        contentValues.put("M36_Roads",stringListCheckboxes[11].toString());
        contentValues.put("M36_Drainage",stringListCheckboxes[12].toString());
        contentValues.put("M36_School",stringListCheckboxes[13].toString());
        contentValues.put("M36_Health",stringListCheckboxes[14].toString());
        contentValues.put("M36_DayCare",stringListCheckboxes[15].toString());
        contentValues.put("M36_WaterSystem",stringListCheckboxes[16].toString());
        contentValues.put("M36_MultiPurpose",stringListCheckboxes[17].toString());
        contentValues.put("M36_FloodControl",stringListCheckboxes[18].toString());
        contentValues.put("M36_Government",stringListCheckboxes[19].toString());
        contentValues.put("M36_Bridges",stringListCheckboxes[20].toString());
        contentValues.put("M36_StreetLights",stringListCheckboxes[21].toString());
        contentValues.put("M36_SolarDrier",stringListCheckboxes[22].toString());
        contentValues.put("M36_CoveredCourt",stringListCheckboxes[23].toString());
        contentValues.put("M36_Basketball",stringListCheckboxes[24].toString());
        contentValues.put("M36_OTHERS",stringListEditText[19].toString());

        contentValues.put("M37_Roads",stringListCheckboxes[25].toString());
        contentValues.put("M37_Drainage",stringListCheckboxes[26].toString());
        contentValues.put("M37_School",stringListCheckboxes[27].toString());
        contentValues.put("M37_Health",stringListCheckboxes[28].toString());
        contentValues.put("M37_DayCare",stringListCheckboxes[29].toString());
        contentValues.put("M37_WaterSystem",stringListCheckboxes[30].toString());
        contentValues.put("M37_MultiPurpose",stringListCheckboxes[31].toString());
        contentValues.put("M37_FloodControl",stringListCheckboxes[32].toString());
        contentValues.put("M37_Government",stringListCheckboxes[33].toString());
        contentValues.put("M37_Bridges",stringListCheckboxes[34].toString());
        contentValues.put("M37_StreetLights",stringListCheckboxes[35].toString());
        contentValues.put("M37_SolarDrier",stringListCheckboxes[36].toString());
        contentValues.put("M37_CoveredCourt",stringListCheckboxes[37].toString());
        contentValues.put("M37_Basketball",stringListCheckboxes[38].toString());
        contentValues.put("M37_OTHERS",stringListEditText[20].toString());

        contentValues.put("N38",integersListRadioButtons[10]);
        contentValues.put("N38_A",integersListRadioButtons[11]);
        contentValues.put("N38_B",integersListRadioButtons[12]);
        contentValues.put("N38_C",integersListRadioButtons[13]);
        contentValues.put("N38_D",integersListRadioButtons[14]);
        contentValues.put("N38_E",integersListRadioButtons[15]);
        contentValues.put("N38E_OTHER",stringListEditText[21]);
        contentValues.put("N38_F",integersListRadioButtons[16]);

        contentValues.put("O39_Educ",stringListEditText[22]);
        contentValues.put("O39_PO",stringListEditText[23]);
        contentValues.put("O39_Health",stringListEditText[24]);
        contentValues.put("O39_Labor",stringListEditText[25]);
        contentValues.put("O39_Economic",stringListEditText[26]);
        contentValues.put("O39_Transport",stringListEditText[27]);
        contentValues.put("O39_House",stringListEditText[28]);
        contentValues.put("O39_Social",stringListEditText[29]);
        contentValues.put("O39_Infra",stringListEditText[30]);
        contentValues.put("O39_Environment",stringListEditText[31]);
        contentValues.put("BarangayCode",barangayCode);

        try
        {
            checkInsert = db.update(getTableName_GOTable(), contentValues, " HCN=?",new String[] {HCN});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            db.endTransaction();
        }



        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateRespondentName (String HCN, String date, String respondent)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("HCN", HCN);
        contentValues.put("DateEntered", date);
        contentValues.put("RespondentName", respondent);

        try
        {
            checkInsert = db.update(getTableName_RespondentNames(), contentValues," HCN=?",new String[] {HCN});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Respondent Table:"+e.toString());
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean update_INTERVIEWER_NEW_SUPERVISOR (String InterviewerCode, String NEW_SUPERVISOR_CODE)
    {
        long checkInsert = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ID",InterviewerCode);
        contentValues.put("Field_SupervisorID",NEW_SUPERVISOR_CODE);



        try
        {
            checkInsert = db.update(getTableName_InterviewerProfile(), contentValues," ID=?",new String[] {InterviewerCode});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        catch ( Exception e )
        {
            Log.i(TAG,"Interviewer Profile Table:"+e.toString());
        }


        if (checkInsert == -1) {
            return false;
        } else {
            return true;
        }
    }



    //DELETE TEMP TABLE ENTRY
    public void remove_TEMP_HCN_AFTER_SUBMIT(String HCN)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        db.execSQL("DELETE FROM " + getTableName_TEMP_BFTable()+ " WHERE HCN='"+HCN+"'");
        db.execSQL("DELETE FROM " + getTableName_TEMP_GOTable()+ " WHERE HCN='"+HCN+"'");
        db.execSQL("DELETE FROM " + getTableName_TEMP_RespondentNames()+ " WHERE HCN='"+HCN+"'");

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();


    }

    //DELETE SUBMITTED TABLE ENTRY
    public void remove_FINAL_HCN_SUBMIT(String HCN)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        db.execSQL("DELETE FROM " + getTableName_BFTable()+ " WHERE HCN='"+HCN+"'");
        db.execSQL("DELETE FROM " + getTableName_GOTable()+ " WHERE HCN='"+HCN+"'");
        db.execSQL("DELETE FROM " + getTableName_RespondentNames()+ " WHERE HCN='"+HCN+"'");

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    //DELETE HCN IN ALL TABLE ENTRY
    //FIRST GET THE NUMBER OF HCN IN B-F TABLES
    public String[] getAll_TEMP_HCN_ID(String HCN)
    {
        String arrData[] = null;
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL;
        strSQL = "SELECT ID FROM tbl_Temp_BF_Tables WHERE HCN='"+HCN+"'";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arrData;
    }

    public String[] getAll_FINAL_HCN_ID(String HCN)
    {
        String arrData[] = null;
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL;
        strSQL = "SELECT ID FROM tbl_BF_Tables WHERE HCN='"+HCN+"'";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arrData;
    }



    public void remove_ANNOYING_HCN(String HCN,String[] TEMP_HCN,String[] FINAL_HCN)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try
        {
            db.execSQL("DELETE FROM " + getTableName_TEMP_BFTable()+ " WHERE ID between '"+TEMP_HCN[0]+"' AND  '"+TEMP_HCN[TEMP_HCN.length-1]+"' ");
            db.execSQL("DELETE FROM " + getTableName_TEMP_GOTable()+ " WHERE HCN='"+HCN+"'");
            db.execSQL("DELETE FROM " + getTableName_TEMP_RespondentNames()+ " WHERE HCN='"+HCN+"'");
        }
        catch ( Exception e )
        {

        }

        try
        {
            db.execSQL("DELETE FROM " + getTableName_BFTable()+ " WHERE ID between '"+FINAL_HCN[0]+"' AND  '"+FINAL_HCN[FINAL_HCN.length-1]+"' ");
            db.execSQL("DELETE FROM " + getTableName_GOTable()+ " WHERE HCN='"+HCN+"'");
            db.execSQL("DELETE FROM " + getTableName_RespondentNames()+ " WHERE HCN='"+HCN+"'");
        }
        catch ( Exception e )
        {

        }


        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }


    public String[] getAllHHIDData()
    {
        String arrData[] = null;
        Cursor cursor = null;

        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL;
        strSQL = "SELECT DISTINCT "+ getTableName_BFTable()+".HCN FROM "+ getTableName_BFTable()+
                " INNER JOIN "+ getTableName_GOTable()+" ON "+ getTableName_BFTable()+".HCN = "+ getTableName_GOTable()+".HCN";

        cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return arrData;
    }
    public String[] getAllSchoolNames()
    {
        String arrData[] = null;
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = "SELECT tbl_SchoolCodes.School FROM tbl_SchoolCodes";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arrData;
    }
    public String[] getAllCooperationNames()
    {
        String arrData[] = null;
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL;
        strSQL = "SELECT Cooperation FROM tbl_CooperationsCodes";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arrData;
    }
    public String[] getAllBarangayNames()
    {
        String arrData[] = null;
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = "SELECT tbl_BarangayCodes.Barangay FROM tbl_BarangayCodes";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return arrData;
    }
    public String getBarangayName(String Barangay)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_BarangayCodes.Barangay FROM tbl_BarangayCodes WHERE tbl_BarangayCodes.Codes=%s","'"+Barangay+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }
    public String getSchoolCodes(String School)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data

        if (School.contains("'"))
        {
            School = School.replace("'","''");
            String strSQL = String.format("SELECT DISTINCT tbl_SchoolCodes.Codes FROM tbl_SchoolCodes WHERE tbl_SchoolCodes.School=%s","'"+School+"'");
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    return cursor.getString(0);
                }
                cursor.close();
            }
            else
            {
                cursor.close();
            }
            db.close();
            return null;

        }
        else
        {
            String strSQL = String.format("SELECT DISTINCT tbl_SchoolCodes.Codes FROM tbl_SchoolCodes WHERE tbl_SchoolCodes.School=%s","'"+School+"'");
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    return cursor.getString(0);
                }
                cursor.close();
            }
            else
            {
                cursor.close();
            }
            db.close();
            return null;

        }
    }

    public String getCooperationCodes(String cooperative)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data

        if (cooperative.contains("'")) {
            cooperative= cooperative.replace("'", "''");

            String strSQL = String.format("SELECT DISTINCT tbl_CooperationsCodes.Codes FROM tbl_CooperationsCodes WHERE tbl_CooperationsCodes.Cooperation=%s","'"+cooperative+"'");

            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    return cursor.getString(0);
                }
                cursor.close();
            }
            else
            {
                cursor.close();
            }
            db.close();
            return null;

        }else
        {
            String strSQL = String.format("SELECT DISTINCT tbl_CooperationsCodes.Codes FROM tbl_CooperationsCodes WHERE tbl_CooperationsCodes.Cooperation=%s","'"+cooperative+"'");

            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    return cursor.getString(0);
                }
                cursor.close();
            }
            else
            {
                cursor.close();
            }
            db.close();
            return null;
        }



    }

    public String[] getAll_Interviewer_Barangay(String barangayCode)
    {
        String arrData[] = null;
        Cursor cursor = null;

        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String selectQuery =
                String.format("SELECT  tbl_InterviewerProfile.Name"
                        +" FROM tbl_InterviewerProfile WHERE "
                        +" tbl_InterviewerProfile.BarangayCode=%s","'"+barangayCode+"'");

        cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return arrData;
    }

    public String getInterviewer_ID(String interviewer)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_InterviewerProfile.ID FROM tbl_InterviewerProfile " +
                " WHERE tbl_InterviewerProfile.Name=%s","'"+interviewer+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }


    public String getInterviewer_SupervisorID(String supervisorName)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_SupervisorProfile.Field_SupervisorID FROM tbl_SupervisorProfile " +
                " WHERE tbl_SupervisorProfile.Name=%s","'"+supervisorName+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }

    public String getInterviewer_SupervisorPass(String supervisorID)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_SupervisorProfile.Pass FROM tbl_SupervisorProfile " +
                " WHERE tbl_SupervisorProfile.Field_SupervisorID=%s","'"+supervisorID+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }


    public String getInterviewer_SupervisorName(String interviewerID)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT  tbl_SupervisorProfile.Name FROM tbl_SupervisorProfile " +
                "INNER JOIN tbl_InterviewerProfile ON tbl_SupervisorProfile.Field_SupervisorID = tbl_InterviewerProfile.Field_SupervisorID" +
                " WHERE tbl_InterviewerProfile.ID=%s","'"+interviewerID+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }

    public String getInterviewer_SupervisorID_BY_InterviewerName(String interviewername)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_SupervisorProfile.Field_SupervisorID FROM tbl_SupervisorProfile " +
                "INNER JOIN tbl_InterviewerProfile ON tbl_SupervisorProfile.Field_SupervisorID = tbl_InterviewerProfile.Field_SupervisorID" +
                " WHERE tbl_InterviewerProfile.Name=%s","'"+interviewername+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }

    public String getRespondentName(String HCN)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT DISTINCT "+getTableName_RespondentNames()+".RespondentName FROM " +getTableName_RespondentNames()+
                " WHERE "+getTableName_RespondentNames()+".HCN=%s","'"+HCN+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;

    }






    public boolean check_finalDatabaseifEmpty()
    {
        boolean checker;
        SQLiteDatabase db = this.getReadableDatabase();; // Read Data
        String strSQL = "SELECT DISTINCT tbl_BF_Tables.HCN FROM tbl_BF_Tables " +
                "INNER JOIN tbl_GO_Tables ON tbl_BF_Tables.HCN = tbl_GO_Tables.HCN";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            cursor.close();
            checker=false;

        }
        else
        {
            cursor.close();
            checker=true;

        }
        db.close();
        return checker;
    }

    public boolean checkHHIDDuplicationTEMP(String HCN)
    {
        boolean checker;
        SQLiteDatabase db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_TEMP_BF_Tables.HCN FROM tbl_TEMP_BF_Tables " +
                "INNER JOIN tbl_TEMP_GO_Tables ON tbl_TEMP_BF_Tables.HCN = tbl_TEMP_GO_Tables.HCN " +
                "WHERE tbl_TEMP_BF_Tables.HCN=%s", "'"+HCN+"'");
        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            checker=true;

        }
        else
        {
            cursor.close();
            checker=false;
        }
        db.close();
        return checker;

    }

    public boolean checkHHIDDuplicationFINAL(String HCN)
    {
        boolean checker;
        SQLiteDatabase db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT DISTINCT tbl_BF_Tables.HCN FROM" +
                " tbl_BF_Tables INNER JOIN tbl_GO_Tables ON tbl_BF_Tables.HCN = tbl_GO_Tables.HCN WHERE tbl_BF_Tables.HCN=%s", "'"+HCN+"'");
        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            checker=true;

        }
        else
        {
            cursor.close();
            checker=false;
        }
        db.close();
        return checker;

    }

    public boolean check_TEMP_DatabaseifEmpty()
    {
        boolean checker;
        SQLiteDatabase db = this.getReadableDatabase();; // Read Data
        String strSQL = "SELECT DISTINCT tbl_TEMP_BF_Tables.HCN FROM tbl_TEMP_BF_Tables " +
                "INNER JOIN tbl_TEMP_GO_Tables ON tbl_TEMP_BF_Tables.HCN = tbl_TEMP_GO_Tables.HCN";

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            cursor.close();
            checker=false;
        }
        else
        {
            cursor.close();
            checker=true;
        }
        db.close();
        return checker;
    }

    public String[] getAll_TEMP_HHIDData()
    {
        String arrData[] = null;
        Cursor cursor = null;

        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL;
        strSQL = "SELECT DISTINCT "+ getTableName_TEMP_BFTable()+".HCN FROM "+ getTableName_TEMP_BFTable()+
                " INNER JOIN "+ getTableName_TEMP_GOTable()+" ON "+ getTableName_TEMP_BFTable()+".HCN = "+ getTableName_TEMP_GOTable()+".HCN";

        cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return arrData;
    }

    public String get_TEMP_RespondentName(String HCN)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT DISTINCT "+getTableName_TEMP_RespondentNames()+".RespondentName FROM " +getTableName_TEMP_RespondentNames()+
                " WHERE "+getTableName_TEMP_RespondentNames()+".HCN=%s","'"+HCN+"'");

        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                return cursor.getString(0);
            }
            cursor.close();
        }
        else
        {
            cursor.close();
        }
        db.close();
        return null;
    }




    public boolean check_Entry_BY_DATE(String date)
    {
        boolean checker;
        SQLiteDatabase db = this.getReadableDatabase();; // Read Data
        String strSQL = String.format("SELECT COUNT(tbl_BF_Tables.HCN) FROM tbl_BF_Tables " +
                "INNER JOIN tbl_GO_Tables ON tbl_BF_Tables.HCN = tbl_GO_Tables.HCN " +
                "WHERE tbl_BF_Tables.DateEntered=%s", "'"+date+"'");
        Cursor cursor = db.rawQuery(strSQL, null);

        if (cursor.getCount() >0) {
            cursor.close();
            checker=true;

        }
        else
        {
            cursor.close();
            checker=false;
        }
        db.close();
        return checker;

    }

    public String[] getAllHHIDData_BY_DATE(String date)
    {
        String arrData[] = null;
        Cursor cursor = null;

        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String strSQL;
        strSQL =String.format("SELECT DISTINCT tbl_BF_Tables.HCN FROM tbl_BF_Tables " +
                "INNER JOIN tbl_GO_Tables ON tbl_BF_Tables.HCN = tbl_GO_Tables.HCN WHERE tbl_BF_Tables.DateEntered ==%s","'"+date+"'");



        cursor = db.rawQuery(strSQL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                arrData = new String[cursor.getCount()];
                int i = 0;
                do {
                    arrData[i] = cursor.getString(0);
                    i++;
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();
        return arrData;
    }


}
