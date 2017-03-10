package com.example.huangxi.drawcircleapp;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Display;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    DrawCircleView drawCircleView ;
    TextView indictor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawCircleView = (DrawCircleView)findViewById(R.id.drawview);
        drawCircleView.setOnTouchListener(drawCircleView);
        drawCircleView.setColor(Color.BLACK);
        drawCircleView.setMode(Mode.Draw);
        setUpBoundForDrawView();

        indictor = (TextView)findViewById(R.id.indicator);

    }

    private  void setUpBoundForDrawView(){
//        int [] location = new int [2];
//        drawCircleView.getLocationOnScreen(location);
//        int width = drawCircleView.getWidth();
//        int height = drawCircleView.getHeight();
//        drawCircleView.boundBottom = location[1]+height;
//        drawCircleView.boundTop = location[1];
//        drawCircleView.boundLeft = location[0];
//        drawCircleView.boundRigth = location[0]+width;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        drawCircleView.boundBottom = size.y-150;
        drawCircleView.boundLeft = 0;
        drawCircleView.boundRigth = size.x;
        drawCircleView.boundTop = 0;
    }

    public void changeMode(View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.modemenu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);

        popup.show();

    }

    public void changeColor(View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.colormenu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.draw:
                drawCircleView.setMode(Mode.Draw);
                break;
            case R.id.delete:
                drawCircleView.setMode(Mode.Delete);
                break;
            case R.id.move:
                drawCircleView.setMode(Mode.Move);
                break;
            case R.id.black:
                drawCircleView.setColor(Color.BLACK);
                break;
            case R.id.green:
                drawCircleView.setColor(Color.GREEN);
                break;
            case R.id.red:
                drawCircleView.setColor(Color.RED);
                break;
            case R.id.blue:
                drawCircleView.setColor(Color.BLUE);
                break;
            case R.id.yellow:
                drawCircleView.setColor(Color.YELLOW);
                break;
            default:
                break;
        }
        indictor.setText(drawCircleView.getMode().toString());
        return true;
    }
}
