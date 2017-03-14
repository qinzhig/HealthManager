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
        ArrayList<Category> categoryList = categoryDAO.getCategorys();
        return categoryList;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> memList) {
        categorys = memList;

        if (memList == null) { categorys = new ArrayList<Category>(); }

    }

    public ArrayList<Category> getCategoryList() {
        return this.categorys;
    }
}
