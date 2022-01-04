package com.devpk.torchlightvsflashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private ImageView torch;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        torch = findViewById(R.id.torch);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        torchLight();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(getApplicationContext(), "Camera Permission Required ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest,
                                                                   PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();

    }

    private void torchLight() {
        torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!state) {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraID = cameraManager.getCameraIdList()[0];
                        Log.d("torchLight ", "" + cameraID);
                        cameraManager.setTorchMode(cameraID, true);
                        state = true;
                        torch.setImageResource(R.drawable.torchon);
                    } catch (Exception exception) {
                        Toast.makeText(getApplicationContext(), "" + exception, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraID = cameraManager.getCameraIdList()[0];
                        Log.d("torchLight ", "" + cameraID);
                        cameraManager.setTorchMode(cameraID, false);
                        state = false;
                        torch.setImageResource(R.drawable.torchoff);
                    } catch (Exception exception) {
                        Toast.makeText(getApplicationContext(), "" + exception, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}