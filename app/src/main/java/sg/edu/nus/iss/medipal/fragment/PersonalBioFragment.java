package sg.edu.nus.iss.medipal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddEditPersonalBioActivity;
import sg.edu.nus.iss.medipal.activity.MainActivity;
import sg.edu.nus.iss.medipal.manager.PersonalBioManager;
import sg.edu.nus.iss.medipal.pojo.PersonalBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/19/2017.
 * Description: This fragment takes care of the view of Personal Bio
 */

public class PersonalBioFragment extends Fragment{

    private TextView name, dob, idNo, address, postalCode, height, bloodType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View personalBioFragment;
        FloatingActionButton aFab;
        personalBioFragment = inflater.inflate(R.layout.fragment_view_personalbio, container, false);

        PersonalBio personalBio =
                new PersonalBioManager().getpersonalBio(getContext());

        name = (TextView) personalBioFragment.findViewById(R.id.nameVal);
        dob = (TextView) personalBioFragment.findViewById(R.id.dobVal);
        idNo = (TextView) personalBioFragment.findViewById(R.id.idNoVal);
        address = (TextView) personalBioFragment.findViewById(R.id.addressVal);
        postalCode = (TextView) personalBioFragment.findViewById(R.id.postalVal);
        height = (TextView) personalBioFragment.findViewById(R.id.heightVal);
        bloodType = (TextView) personalBioFragment.findViewById(R.id.bloodTypeVal);

        if (null != personalBio) {
            name.setText(personalBio.getName());
            name.setTag(personalBio.getId());
            dob.setText(MediPalUtility.
                    convertDateToString(personalBio.getDob(),
                            "dd MMM yyyy"));
            idNo.setText(personalBio.getIdNo());
            address.setText(personalBio.getAddress());
            postalCode.setText(String.valueOf(personalBio.getPostalCode()));
            height.setText(String.valueOf(personalBio.getHeight()));
            bloodType.setText(personalBio.getBloodType());

            ((MainActivity) getActivity()).setActionBarTitle("personal Bio");
        }

        aFab = (FloatingActionButton)personalBioFragment.findViewById(R.id.fab);
        aFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personalBioIntent = new Intent(getContext(), AddEditPersonalBioActivity.class);
                personalBioIntent.putExtra("firstTime",false);
                getActivity().startActivityForResult(personalBioIntent,3);

            }
        });

        return personalBioFragment;
    }

}
