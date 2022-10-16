package com.springboot.coronovirustracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.springboot.coronovirustracker.models.Locationstats;

@Service
public class coronovirussourceget {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<Locationstats> allStats = new ArrayList<>();

    public List<Locationstats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<Locationstats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(httpResponse.body());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            Locationstats locationstats = new Locationstats();
            locationstats.setState(record.get("Province/State"));
            locationstats.setCountry(record.get("Country/Region"));
            int latestCases=Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases=Integer.parseInt(record.get(record.size() - 2));

            locationstats.setLatestTotalCases(latestCases);
            locationstats.setDiffFromPreviousDay(latestCases-prevDayCases);
            System.out.println(locationstats);
            newStats.add(locationstats);
            // System.out.println(state);
        }
        this.allStats = newStats;
    }
}
