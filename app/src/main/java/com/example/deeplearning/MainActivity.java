package com.example.deeplearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.*;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;
    Boolean startCanny = false;
    Boolean startYolo = false;
    int counter = 0;

    public void YOLO(View Button)
    {
       if (startYolo = false)
       {
           startYolo = true;
       }
       else {
           startYolo = true;
       }
    }


  /*  public void Canny(View Button)
    {
         if (startCanny == false)
         {
             startCanny = true;
         }
         else
         {
             startCanny = false;
         }
    }


   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraBridgeViewBase = (JavaCameraView)findViewById(R.id.cameraView);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);


        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
                switch (status){
                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView();
                        break;
                        default:
                            super.onManagerConnected(status);
                            break;
                }
            }
        };
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat frame = inputFrame.rgba();
        //if (counter % 2 == 0)
       // {
         //   Core.flip(frame,frame,1);
          //  Imgproc.cvtColor(frame,frame,Imgproc.COLOR_RGBA2GRAY);
       // }
      //  counter = counter + 1;
        if (startCanny == true) {
            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2GRAY);
            // Imgproc.blur(frame,frame,new Size(3,3));
            Imgproc.Canny(frame, frame, 100, 80);
        }


        return frame;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Toast.makeText(this, "There is a problem ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase!=null)
            cameraBridgeViewBase.disableView();
    }
}
