import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class CameraUtility {
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    public static void captureImage(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public static void handleActivityResult(int requestCode, int resultCode, @Nullable Intent data, Activity activity) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            try {
                assert data != null;
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                saveImageToFile(imageBitmap, activity);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "Failed to load image", Toast.LENGTH_SHORT).show();
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            // Handle the case where the user cancels taking a picture
            Toast.makeText(activity, "Picture was not taken", Toast.LENGTH_SHORT).show();
        } else {
            // Handle other cases, such as if there's an error
            Toast.makeText(activity, "Failed to capture image", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveImageToFile(Bitmap bitmap, Activity activity) {
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageUrl = null;
        if (storageDir != null) {
            String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
            File imageFile = new File(storageDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                imageUrl = imageFile.getAbsolutePath();
                Toast.makeText(activity, "Image saved: " + imageUrl, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
