package nitish.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.get_app_id))
                // if defined
                .clientKey(getString(R.string.get_client_key))
                .server(getString(R.string.get_server_url))
                .build()
        );

    }
}
