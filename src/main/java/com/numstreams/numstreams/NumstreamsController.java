package com.numstreams.numstreams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class NumstreamsController {
    @RequestMapping("/")
    public String getHelp(@RequestParam String id) {
        return "sever:port?id=1,2,3,4,5 // returns square root of sum of squares of max 3 values";
    }

    @RequestMapping(value = "dist", method = RequestMethod.POST)
    public ResponseEntity<Object> getMessage(@RequestBody String numsIn) {
        try {
            // TODO add proper data model class to handle input and get rid of loop
            JSONObject obj = new JSONObject(numsIn);
            JSONArray dataArray = obj.getJSONArray("data");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < dataArray.length(); ++i) {
                String s = dataArray.get(i).toString();
                list.add(s);
            }

            double ret = list.stream().map(a -> Double.parseDouble(a)).sorted(Comparator.reverseOrder()).limit(3).reduce(0.0, (a, b) -> a + Math.pow(b, 2));
            JSONObject json = new JSONObject();
            json.put("output", Math.sqrt(ret));
            return new ResponseEntity<Object>(json.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
