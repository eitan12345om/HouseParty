package com.houseparty.houseparty;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by jacksonkurtz on 5/18/18.
 */
@Deprecated
public class RetrieveImage extends AsyncTask<String, Void, String > {

    private Exception exception;

    protected String doInBackground(String... args ) {
        String imageURL = "";
        try {
            Log.d("doInBackgroundArg", args[0] );
            Document doc = Jsoup.connect(args[0]).timeout(10000).get();
            for (Element element : doc.select("img")) {
                Log.d("ImageForLoop", element.toString() );
            }
        }
        catch( Exception e) {
            Log.d("Retrieve Image", e.toString());

        }
        return imageURL;
    }
}
