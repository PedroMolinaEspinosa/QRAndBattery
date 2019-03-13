package com.example.qrbattery;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BatteryFragment extends Fragment {
    View view;
    TextView tv_charging, tv_batterypercent, tv_kindcharging, tv_scale;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.battery_fragment, container, false);

        tv_batterypercent = view.findViewById(R.id.tv_batteryporcent);
        tv_charging = view.findViewById(R.id.charging);
        tv_kindcharging = view.findViewById(R.id.kind);

        tv_scale = view.findViewById(R.id.tv_scale);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getContext().registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        tv_charging.setText(isCharging + "");

        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        if (usbCharge) {
            tv_kindcharging.setText("Estás cargando con USB");
        } else if(acCharge){
            tv_kindcharging.setText("Estás cargando con Corriente alterna");
        }else{
            tv_kindcharging.setText("Illo, que se está descargando");
        }

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        tv_batterypercent.setText(level + "");
        tv_scale.setText(scale + "");
       /* float batteryPct = level / (float) scale;
        tv_pcts.setText(batteryPct + "");*/
        return view;
    }
}
