package sg.edu.nus.iss.medipal;

import android.database.SQLException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.ArrayList;

import sg.edu.nus.iss.medipal.pojo.Category;

import static junit.framework.Assert.assertEquals;

/**
 * Created by zhiguo on 26/3/17.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryTest extends BeforeTestSetUp {


    public void testInsert() throws SQLException{

        Category category_original = new Category("Test","TST","des",false);

        categoryDAO.insert(category_original);
        ArrayList<Category> category_list = categoryDAO.getCategorys();

        Category category_result= category_list.get(4);

        assertEquals("Test", category_result.getCategory_name());
        assertEquals("TST", category_result.getCategory_code());
        assertEquals("des", category_result.getCategory_des());
        assertEquals(false, (boolean)category_result.getRemind());

    }

    public void testUpdate() throws SQLException{
        Category category1 = new Category("Test","TST","des",false);
        categoryDAO.insert(category1);

        ArrayList<Category> category_list = categoryDAO.getCategorys();
        Category category2 = category_list.get(category_list.size()-1);
        

        Category category_result= category_list.get(4);

        assertEquals("Test", category_result.getCategory_name());
        assertEquals("TST", category_result.getCategory_code());
        assertEquals("des", category_result.getCategory_des());
        assertEquals(false, (boolean)category_result.getRemind());
    }

}
