package com.aziz.wuzzuf;

import com.aziz.wuzzuf.model.MainModel;
import com.aziz.wuzzuf.preprocess.FileLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class MainController {

    @RequestMapping("/load-csv")
    public  String load_csv(@RequestParam(value = "path", required = true) String path,
                            @RequestParam(value = "header") String header)  {
        Dataset<Row> df = FileLoader.load_csv(MainModel.getSession(), path, Boolean.parseBoolean(header));
        MainModel.setMainDataframe(df);
        return "file loaded";

    }

    @RequestMapping("/print-df")
    public String printDataframe(){
        StringBuilder jobs = new StringBuilder();
        jobs.append("<table style=\"width:100%\">");
        jobs.append(" <tr>")
                .append("<th>Title</th>")
                .append("<th>Company</th>")
                .append("<th>Location</th>")
                .append("<th>Type</th>")
                .append("<th>Level</th>")
                .append("<th>YearsEXP</th>")
                .append("<th>Country</th>")
                .append("<th>Skills</th>")
                .append("</tr>");
        for (Iterator<Row> it = MainModel.getMainDataframe().toLocalIterator(); it.hasNext(); ) {
            Row row = it.next();
            jobs.append(" <tr>")
                    .append("<th>").append(row.getString(0)).append("</th>")
                    .append("<th>").append(row.getString(1)).append("</th>")
                    .append("<th>").append(row.getString(2)).append("</th>")
                    .append("<th>").append(row.getString(3)).append("</th>")
                    .append("<th>").append(row.getString(4)).append("</th>")
                    .append("<th>").append(row.getString(5)).append("</th>")
                    .append("<th>").append(row.getString(6)).append("</th>")
                    .append("<th>").append(row.getString(7)).append("</th>")
                    .append("</tr>");
        }
        jobs.append("</table>");
        return jobs.toString();
    }

}
