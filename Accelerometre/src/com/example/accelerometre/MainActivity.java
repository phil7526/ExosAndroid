package com.example.accelerometre;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.util.*;
import android.hardware.*;

import java.util.*;

public class MainActivity extends ActionBarActivity {
private TextView tvx;
private TextView tvy;
private TextView tvz;
private TextView cal;
private TextView capteurs;
private ListView lCapteurs;
private double x,y,z,c;
public SensorManager sensorManager;
public Sensor accelerometer;
public String txt = new String();
public Filtre fx = new Filtre();
public Filtre fy = new Filtre();
public Filtre fz = new Filtre();

private ArrayList<String> lcapArray= new ArrayList<String>();

private final SensorEventListener sensorEventListener = new SensorEventListener()
{
	
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		;
	}
	public void onSensorChanged(SensorEvent event)
	{
		x = event.values[0];
		y = event.values[1];
		z = event.values[2];
		c=Math.sqrt(x*x+y*y+z*z);
		x=x/c;
		y=y/c;
		z=z/c;
		x = fx.FiltrePasseBas(x);
		y = fy.FiltrePasseBas(y);
		z = fz.FiltrePasseBas(z);

		
	}
};

private void updateGUI() 
{
	runOnUiThread(new Runnable()
	{
		public void run()
		{
			
			tvx.setText(Double.toString(x));
			tvy.setText(Double.toString(y));
			tvz.setText(Double.toString(z));
			cal.setText(Double.toString(c));

		}
	});
	
	
}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		tvx = (TextView)findViewById(R.id.tvX);
		tvy = (TextView)findViewById(R.id.tvY);
		tvz = (TextView)findViewById(R.id.tvZ);
		cal = (TextView)findViewById(R.id.tvCalibre);
		capteurs = (TextView)findViewById(R.id.tvListeCapteurs);
		lCapteurs =(ListView)findViewById(R.id.listCapteurs);
		ArrayAdapter<String> lcapAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lcapArray);

		lCapteurs.setAdapter(lcapAdapter);
		tvz.setText("voir");
		sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	
		sensorManager.registerListener(sensorEventListener, accelerometer,SensorManager.SENSOR_DELAY_GAME);
		List<Sensor> listEquip = sensorManager.getSensorList(Sensor.TYPE_ALL);
		for (int  i=0; i<listEquip.size(); i++)
		{
			
		lcapArray.add(new String(listEquip.get(i).getName() + " Vendor: " + listEquip.get(i).getVendor() + " Max Range: "
		+ listEquip.get(i).getMaximumRange() + " Min Delay : "
		+ listEquip.get(i).getMinDelay() + " Res : " + listEquip.get(i).getResolution() + "\n"));
		
		}
		//capteurs.setText(txt);
		Timer updateTimer = new Timer("forceUpdate");
		updateTimer.scheduleAtFixedRate(new TimerTask() { public void run() {updateGUI();}}, 0, 100);

		
	}
	
	@Override
	protected void onPause() {
	  super.onPause();
	 sensorManager.unregisterListener(sensorEventListener, accelerometer);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
