package com.getmotivation.getmotivation.provagrafico;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    BarChart barchart;
    Calendar calendar;
    Date today,yesterday,beforeYesterday,meno3Today,meno9Today;
    String oggi,ieri,avantIeri,meno3daOggi,meno9daOggi;
    String[] stringheGiorni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barchart = findViewById(R.id.BarChartPassi);

        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        calendar = Calendar.getInstance();


        Date[] giorni = {meno3Today,beforeYesterday,yesterday,today };
        stringheGiorni = new String[4];
        stringheGiorni[0] = meno3daOggi;
        stringheGiorni[1] =avantIeri;
        stringheGiorni[2] =ieri;
        stringheGiorni[3] =oggi;

        calendar.add(Calendar.DATE,-4);

        for(int i=0; i<4;i++) {
//            if(i==0){
//                calendar.add(Calendar.DATE,0);
//            }
//            else{
            calendar.add(Calendar.DATE,+1);//}
            giorni[i] = calendar.getTime();
            stringheGiorni[i]= dataFormat.format(giorni[i]);
            Log.d("Date girni registrate",stringheGiorni[i]);
        }

        calendar.add(Calendar.DATE,-9);
        meno9Today = calendar.getTime();
        meno9daOggi= dataFormat.format(meno9Today);
        Log.d("Date girni registrate",meno9daOggi);

        calendar.add(Calendar.DATE,+9);

//        String[] giorniAllaRovescia = {meno3daOggi,avantIeri,ieri,oggi};
//        stringheGiorni = giorniAllaRovescia;


        barchart.setDrawBarShadow(false);
        barchart.setDrawValueAboveBar(true);
        barchart.setMaxVisibleValueCount(50);
        barchart.setPinchZoom(false);
        barchart.setDrawGridBackground(true);
//        barchart.getAxisLeft().setDrawLabels(false);
        barchart.getAxisRight().setDrawLabels(false);
//        barchart.getXAxis().setDrawLabels(false);
//        barchart.setVisibleXRangeMaximum();
//        barchart.setVisibleYRange();


        barchart.getLegend().setEnabled(false);
        Description desc = new Description();
        desc.setText("");
        barchart.setDescription(desc);
//        barchart.getDescription().setPosition(1,50);


        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, 40f));
        barEntries.add(new BarEntry(2, 44f));
        barEntries.add(new BarEntry(3, 30f));
        barEntries.add(new BarEntry(4, 36f));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Day");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        LimitLine obPassi = new LimitLine(40f);
        obPassi.setLineColor(Color.RED);
        //obPassi.setTypeface(Typeface.DEFAULT_BOLD);
        obPassi.setLabel("obbiettivo passi");
        obPassi.setLineWidth(4f);
        YAxis leftAxis = barchart.getAxisLeft();
        //il comando per togliere le linee orizzontali non mi funziona
        //leftAxis.setDrawGridLines(false);  //non sapendo come rimuovere le linee della griglia orizzontali ho scelto di aggiungere in seguito quelle verticali
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(obPassi);
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart);
        XAxis xAxis = barchart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);

        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);


        barchart.setData(data);

    }

    public class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;
        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }
        @Override
        public String getFormattedValue(float value) {
            return stringheGiorni[((int)value-1)];  //qua sono indicati i valori restituiti sull'asseX
        }
    }
}
