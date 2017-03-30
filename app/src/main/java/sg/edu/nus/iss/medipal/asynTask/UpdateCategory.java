package sg.edu.nus.iss.medipal.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import sg.edu.nus.iss.medipal.dao.CategoryDAO;
import sg.edu.nus.iss.medipal.pojo.Category;

/**
 * Created by zhiguo on 18/3/17.
 */

public class UpdateCategory extends AsyncTask<Category, Void, Long> {

    Category category = null;
    private CategoryDAO categoryDAO;

    public UpdateCategory(Context context) {
        this.categoryDAO = new CategoryDAO(context);
    }

    @Override
    protected Long doInBackground(Category... params) {
        long result = categoryDAO.update(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)
        {
            Log.v("DBSave","____________________-------------------Update Medicine successfully!");
        }

        if (categoryDAO != null)
        {
            categoryDAO.close();
        }

    }
}
