package sg.edu.np.mad.snatch;

import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
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

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    // Custom Info Window for a marker when the marker is selected
    private final View mWindow;
    private Context mContext;
    private String foodCourt;

    public CustomInfoWindowAdapter(Context context, String aFoodCourt) {
        foodCourt = aFoodCourt;
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView noOfUsersTextView = (TextView)view.findViewById(R.id.noOfPplTextView);
        TextView showMoreTextView = (TextView)view.findViewById(R.id.showMoreTextView);
        PieChart pieChart = (PieChart)view.findViewById(R.id.chart1);
        // Set up the pie chart to show number of users in Food Court and capacity of Food Court
        setupPieChart(pieChart);
        TextView apiFoodCourtNameTextView = (TextView)view.findViewById(R.id.apiFoodCourtNameTextView);
        apiFoodCourtNameTextView.setText(foodCourt);

        noOfUsersTextView.setText(GetNumPpl(foodCourt) + " user(s) currently");
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    private void setupPieChart(PieChart chart) {
        int numOfUsers = 0;

        // Retrieves the number of people in the selcted Food Court
        numOfUsers = GetNumPpl(foodCourt);
        List<PieEntry> pieEntries = new ArrayList<>();
        // Inserting of data for the Pie Chart
        PieEntry pieEntry = new PieEntry(numOfUsers, "No. of Users");
        PieEntry pieEntry1 = new PieEntry(getFoodCourtCapacity(foodCourt)-numOfUsers, "No. of Empty seats");

        // Adding of the data into the Pie Chart
        pieEntries.add(pieEntry);
        pieEntries.add(pieEntry1);

        PieDataSet dataSet = new PieDataSet(pieEntries, null);

        // Setting the colours of the different portions
        // Green - Empty Seats, Red - Number of users currently
        dataSet.setColors(Color.rgb(212, 44, 44), Color.rgb(12, 201, 47));
        PieData data = new PieData(dataSet);

        // Setting the text labels to size 0
        chart.setCenterTextSize(0);
        chart.setEntryLabelTextSize(0);
        chart.setDescription(null);
        chart.setData(data);
        dataSet.setDrawValues(false);

        // Removing the legend
        chart.getLegend().setEnabled(false);
        chart.invalidate();
    }

    public int GetNumPpl(String foodCourt) {
        StrictMode.ThreadPolicy policy = new
        StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        int numOfPpl = 0;
        try {
            // Retrieving the API URL for the selected Food Court
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

            // Retrieving the data from the Web API
            // API data format:
            // <string xmlns="http://tempuri.org/">[{"value":0,"color":"#00ff00","label":"Comfy"}]</string>

            // Get the String element/<string> tag
            NodeList nodeList = doc.getElementsByTagName("string");
            // Since it is an array, get the first item, index 0
            Element bodyElement = (Element) nodeList.item(0);
            // Get all child nodes
            NodeList bodyList = bodyElement.getChildNodes();
            Node nodeContent = (Node) bodyList.item(0);
            // Since it is XML, convert to JSON
            String xmlString = nodeContent.getNodeValue();
            JSONArray objArray = new JSONArray(xmlString);
            JSONObject obj = objArray.getJSONObject(0);
            // Getting the "value" attribute's value, which is the number of users connected
            String numOfPpl1 = obj.getString("value");
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

    // Getting the capacity of the Food Court
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
