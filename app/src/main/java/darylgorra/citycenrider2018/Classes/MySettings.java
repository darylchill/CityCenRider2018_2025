package darylgorra.citycenrider2018.Classes;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Originaly Created by Daryl on 03/12/2018.
 * HERE IS THE SHARED PREFERENCES FOR OUR RIDER APP FOUND
 * SHARED PREFERENCES IS WHAT WE USED TO SAVED OUR SETTINGS HERE
 */

public class MySettings {

    private boolean LoginCheck = false;
    private boolean load_DatabaseFinish = false;
    private static String serverIP = "192.168.1.3";
    private static String BarangayCode = "0";
    public static String InterviewerName;
    public static String SupervisorID;
    private static int UploadID =0;
    private static String MasterPass="CityCen2018";
    private static String Current_FS_Pass;
    private static String RiderVersion;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");




    public String getCurrentTime()
    {
        String time = timeFormat.format(Calendar.getInstance().getTime());
        return time;
    }
    public String getCurrentDate()
    {
        String date = dateFormat.format(new Date());
        return date;
    }


    public Boolean getLoginCheck(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final boolean loadDefaultCheckLogin = pref.getBoolean("LoginCheck",LoginCheck);
        return loadDefaultCheckLogin;
    }

    public Boolean getLogin_LoadDatabase(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final boolean loadDefaultCheckDatabase = pref.getBoolean("LoginDatabaseCheck",load_DatabaseFinish);
        return loadDefaultCheckDatabase;
    }

    public String getBarangayCode(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final String loadDefaultBarangayCode = pref.getString("BarangayCode",BarangayCode);
        return loadDefaultBarangayCode;
    }

    public String getInterviewer(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final String loadDefaultInterviewer = pref.getString("Interviewer",InterviewerName);
        return loadDefaultInterviewer;
    }

    public String getSupervisorID(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final String loadDefaultSupervisor = pref.getString("SupervisorID",SupervisorID);
        return loadDefaultSupervisor;
    }

    public int getUploadNo(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final int loadDefaultUploadNo = pref.getInt("UploadNo", UploadID);
        return loadDefaultUploadNo;
    }

    public String getMasterPass(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final String loadDefaultMaster = pref.getString("MasterPass",MasterPass);
        return loadDefaultMaster;
    }

    public String getCurrentRIDER_Version_Avail(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final String loadDefaultRider_Version = pref.getString("RiderVersion",RiderVersion);
        return loadDefaultRider_Version;
    }
    public String getCurrent_FS_PASS(Context context)
    {
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        final String load_Current_FS_Pass = pref.getString("Current_FS_Pass",Current_FS_Pass);
        return load_Current_FS_Pass;
    }


    public void setBarangayCode(String BarangayCode)
    {
        this.BarangayCode = BarangayCode;
    }

    public void setInterviewerName(String interviewerName)
    {
        this.InterviewerName = interviewerName;
    }

    public void setLoginCheck(Context context, boolean LoginCheck)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("LoginCheck",LoginCheck) ;
        editor.commit();
    }

    public void setMasterPass(Context context,String MasterPass)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("MasterPass",MasterPass) ;
        editor.commit();
    }

    public void setLogin_DatabaseCheck(Context context, boolean Login_DatabaseCheck)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("LoginDatabaseCheck",Login_DatabaseCheck) ;
        editor.commit();
    }
    public void setBarangayCode(Context context, String barangayCode)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("BarangayCode",barangayCode) ;
        editor.commit();
    }


    public void setInterviewer(Context context, String Interviewer)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Interviewer",Interviewer) ;
        editor.commit();
    }

    public void setSupervisorID(Context context, String supervisorID)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("SupervisorID",supervisorID) ;
        editor.commit();
    }

    public void setUploadNo(Context context, int UploadNo)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("UploadNo",UploadNo) ;
        editor.commit();
    }

    public void setRiderVersion_Available(Context context, String RiderVersion)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("RiderVersion",RiderVersion) ;
        editor.commit();
    }

    public void setCurrent_FS_Pass(Context context, String Current_FS_Pass)
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Current_FS_Pass",Current_FS_Pass) ;
        editor.commit();
    }




}
