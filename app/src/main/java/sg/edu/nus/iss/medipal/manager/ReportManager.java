package sg.edu.nus.iss.medipal.manager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.pojo.HealthBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by divah on 3/20/2017.
 */

public class ReportManager {

    private static HealthBioManager healthBioManager = null;

    public static TableRow addHeaders(String[] headerArr,
                                      Context context) {

        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        for (String header : headerArr) {
            TextView tv = new TextView(context);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(header);
            rowHeader.addView(tv);
        }

        return rowHeader;
    }

    public static TableLayout addContent(Context context,
                                         TableLayout tableLayout) {

        healthBioManager
                = new HealthBioManager();

        List<HealthBio> healthBioList =
                healthBioManager.getHealthBio(context);

        for (HealthBio healthBio : healthBioList) {

            TableRow rowData = new TableRow(context);
            String condition = healthBio.getCondition();
            String strtDate = MediPalUtility.
                    covertDateToString(healthBio.getStartDate());
            String conditionType = "";
            if (healthBio.getConditionType() == 'C') {
                conditionType = "Condition";
            } else {
                conditionType = "Allergy";
            }

            String[] rowArr = {condition, strtDate, conditionType};

            for (String row : rowArr) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(row);
                rowData.addView(tv);
            }
            tableLayout.addView(rowData);
        }

        return tableLayout;
    }

    public static String addHealthBioToCsv(String[] headerArr,
                                           Context context) {
        StringBuilder healthBioStr = new StringBuilder();
        for (String header : headerArr) {
            healthBioStr.append(header);

            if (!header.equals(headerArr[headerArr.length - 1])) {
                healthBioStr.append(",");
            }
        }

        healthBioStr.append("\n");

        healthBioManager =
                new HealthBioManager();

        List<HealthBio> healthBioList =
                healthBioManager.getHealthBio(context);

        for (HealthBio healthBio : healthBioList) {
            String condition = healthBio.getCondition();
            String strtDate = MediPalUtility.
                    covertDateToString(healthBio.getStartDate());
            String conditionType = "";
            if (healthBio.getConditionType() == 'C') {
                conditionType = "Condition";
            } else {
                conditionType = "Allergy";
            }

            healthBioStr.append(condition).append(",");
            healthBioStr.append(strtDate).append(",");
            healthBioStr.append(conditionType);

            healthBioStr.append("\n");
        }

        return healthBioStr.toString();

        /*String columnString = "\"Condition\",\"Start Date\",\"Condition Type\"";
        String dataString = "\"dd\"";
        String combinedString = columnString + "\n" + dataString;*/
    }
}

