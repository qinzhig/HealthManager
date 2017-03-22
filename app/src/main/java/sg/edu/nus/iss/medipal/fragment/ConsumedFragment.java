package sg.edu.nus.iss.medipal.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.SearchAdapter;


public class ConsumedFragment extends Fragment implements SearchView.OnQueryTextListener {

    SearchView Searchconsumed;
    List<String> list = new ArrayList<String>();
    private android.support.v7.widget.SearchView.SearchAutoComplete edit;
    ListView Listconsumed;
    SearchAdapter<String> searchAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

            View fragmentView = inflater.inflate(R.layout.fragment_consumed,container,false);
            Searchconsumed = (SearchView)fragmentView.findViewById(R.id.Searchconsumed);
            Listconsumed = (ListView) fragmentView.findViewById(R.id.Listconsumed);

            searchAdapter = new SearchAdapter<String>(this, list);

            Listconsumed.setAdapter(searchAdapter);
            Listconsumed.setTextFilterEnabled(true);

            Searchconsumed.setQueryHint("Please input medince name or category to query");
            Searchconsumed.setIconified(false);
            Searchconsumed.setIconifiedByDefault(false);
            System.out.println("two part of view");

            Searchconsumed.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String queryText) {
                    if (TextUtils.isEmpty(queryText)) {
                        searchAdapter.getFilter().filter(null);
                    } else {
                        searchAdapter.getFilter().filter(queryText);
                    }
                    return true;
                }
            });

        FloatingActionButton fab2 = (FloatingActionButton)fragmentView.findViewById(R.id.fab_add2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



        return inflater.inflate(R.layout.fragment_consumed, container, false);
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
