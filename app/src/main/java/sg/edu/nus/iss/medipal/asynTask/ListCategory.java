package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.dao.CategoryDAO;
import sg.edu.nus.iss.medipal.pojo.Category;

/**
 * Created by zhiguo on 14/3/17.
 */

public class ListCategory extends AsyncTask<Void, Void, ArrayList<Category>> {

    ArrayList<Category> categorys;
    private CategoryDAO categoryDAO;

    public ListCategory(Context context) {

        this.categoryDAO = new CategoryDAO(context);
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... arg0) {
        categorys = categoryDAO.getCategorys();
        return categorys;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> categoryList) {
        categorys = categoryList;

        if (categoryList == null) { categorys = new ArrayList<Category>(); }

        categoryDAO.close();

    }

}
