package darylgorra.citycenrider2018.Classes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Daryl on 1/16/17.
 * THE FOLLOWING codes is NEEDED FOR API 21 ABOVE BECAUSE OF GOOGLE'S NEW  SECURITY FEATURE - SYSTEM PERMISSION
 */

public class systemPermissionList
{

    public static final  int Permissions_All_Code=0;

    //HERE IS THE CURRENT LIST OF SYSTEM PERMISSIONS
    public static final String REQUEST_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String REQUEST_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String REQUEST_ACCESS_NETWORKLOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String REQUEST_ACCESS_GPSLOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String REQUEST_ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    public static final String REQUEST_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String REQUEST_INTERNET = Manifest.permission.INTERNET;




    public static boolean hasPermissions(Context context, String... permissions) {

        if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


}
