package com.example.alexandre.runtimepermissions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //onRequestPermissionsResult
        private int RUNTIME_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_request_permissions = (Button) findViewById(R.id.btn_request_permissions);
        btn_request_permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCameraAccessAllowed()) {
                    Toast.makeText(v.getContext(), "you already have the permission to access Camera", Toast.LENGTH_LONG).show();
                    return;
                }
                requestCameraPermission();
            }
        });
    }
    private boolean isCameraAccessAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            showAlertDialog();
        }
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, RUNTIME_PERMISSION_CODE);

    }
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==RUNTIME_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can use the camera", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "oops you just denied permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showAlertDialog () {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Runtime Permissions");
        alertDialogBuilder.setMessage("This is tutorial for runtime Permission. This needs permission of accessing your device Camera. " + "Please grant permission" ).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int d) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
