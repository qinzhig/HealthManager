package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import sg.edu.nus.iss.medipal.R;

/**
 * Created by levis on 3/27/2017.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener  {
    private TextView _contactAddressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        _contactAddressView = (TextView) findViewById(R.id.about_contactaddress);
        _contactAddressView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == _contactAddressView) {

            String contactAddress = _contactAddressView.getText().toString();
            String subject = "";
            String body  = "";

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(contactAddress));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);

            emailIntent.setType("message/rfc822");

            startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
        }
    }
}
