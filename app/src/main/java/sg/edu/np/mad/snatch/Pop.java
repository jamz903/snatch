package sg.edu.np.mad.snatch;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Pop extends Activity {

    String foodCourt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custompopup);

        foodCourt = getIntent().getStringExtra("FoodCourt");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.9), (int)(height*.6));

        TextView closeButtonTextView;
        PieChart pieChart = findViewById(R.id.chart2);
        setupPieChart(pieChart);
        closeButtonTextView = (TextView)findViewById(R.id.closeButtonTextView);
        closeButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public int GetNumPpl(String foodCourt) {
        StrictMode.ThreadPolicy policy = new
        StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        int numOfPpl = 0;
        try {
            if (foodCourt.equals("Munch")) {
                url = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System%20Campus%3EBlk%2073%3ELevel%201%3EMunch");
            }
            else if (foodCourt.equals("Makan Place")) {
                url = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System%20Campus%3EBlk%2051%3ELevel%202%20-%20Canteen%3ECoverageArea-B51L02");
            }
            else if (foodCourt.equals("Poolside")) {
                url = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System%20Campus%3EBlk%2018%3ELevel%201%20-%20Canteen%3ECoverageArea-B18L01");
            }
            else {
                url = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System%20Campus%3EBlk%2022%3ELevel%201%3ECoverageArea-B22L01");
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("string");
            Element bodyElement = (Element) nodeList.item(0);
            NodeList bodyList = bodyElement.getChildNodes();
            Node nodeContent = (Node) bodyList.item(0);
            String xmlString = nodeContent.getNodeValue();
            Log.d("Content", xmlString);
            JSONArray objArray = new JSONArray(xmlString);
            JSONObject obj = objArray.getJSONObject(0);
            String numOfPpl1 = obj.getString("value");
            Log.d("Content", numOfPpl1);
            numOfPpl = Integer.parseInt(numOfPpl1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return numOfPpl;
    }

    private void setupPieChart(PieChart chart) {
        int numOfUsers = 0;

        numOfUsers = GetNumPpl(foodCourt);

        List<PieEntry> pieEntries = new ArrayList<>();
        PieEntry pieEntry = new PieEntry(numOfUsers, "No. of Users");
        PieEntry pieEntry1 = new PieEntry(getFoodCourtCapacity(foodCourt)-numOfUsers, "No. of Empty seats");
        pieEntries.add(pieEntry);
        pieEntries.add(pieEntry1);

        PieDataSet dataSet = new PieDataSet(pieEntries, null);
        dataSet.setColors(Color.rgb(212, 44, 44), Color.rgb(12, 201, 47));
        PieData data = new PieData(dataSet);
        dataSet.setValueTextSize(15);

        chart.setEntryLabelColor(ColorTemplate.rgb("#000000"));
        chart.setCenterTextSize(50);
        chart.setData(data);
        chart.setDescription(null);
        chart.getLegend().setTextSize(15);
        chart.invalidate();
    }

    public int getFoodCourtCapacity(String foodCourt) {
        int capacity = 0;
        if (foodCourt.equals("Makan Place")) {
            capacity = 500;
        }
        else if (foodCourt.equals("Food Club")) {
            capacity = 400;
        }
        else if (foodCourt.equals("Poolside")) {
            capacity = 250;
        }
        else {
            capacity = 200;
        }
        return capacity;
    }
}