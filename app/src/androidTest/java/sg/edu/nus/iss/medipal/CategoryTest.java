package sg.edu.nus.iss.medipal;

import android.database.SQLException;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.pojo.Category;

import static junit.framework.Assert.assertEquals;

/**
 * Created by zhiguo on 26/3/17.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryTest extends BeforeTestSetUp {


    public void testInsert() throws SQLException{

        Category category_original = new Category("Test","TST","des",false);

        //Insert a data
        categoryDAO.insert(category_original);

        //Get the data from DB again
        ArrayList<Category> category_list = categoryDAO.getCategorys();
        Category category_result= category_list.get(category_list.size()-1);

        //Test whether the data has successfully insert and saved in DB
        assertEquals("Test", category_result.getCategory_name());
        assertEquals("TST", category_result.getCategory_code());
        assertEquals("des", category_result.getCategory_des());
        assertEquals(false, (boolean)category_result.getRemind());

    }

    public void testUpdate() throws SQLException{

        Category category1 = new Category("Test2","HAL","des",false);
        //Insert a data
        categoryDAO.insert(category1);
        ArrayList<Category> category_list = categoryDAO.getCategorys();
        Category category2 = category_list.get(category_list.size()-1);

        //Set new property for this obejct
        category2.setCategory_name("ISS-TEST");
        category2.setCategory_code("ABC");
        category2.setCategory_des("Welcome1");
        category2.setRemind(true);

        //Update the property data to DB
        categoryDAO.update(category2);
        ArrayList<Category> category_list1 = categoryDAO.getCategorys();
        Category category3 = category_list.get(category_list1.size()-1);

        //Test the update result
        assertEquals("ISS-TEST", category3.getCategory_name());
        assertEquals("ABC", category3.getCategory_code());
        assertEquals("Welcome1", category3.getCategory_des());
        assertEquals(true, (boolean)category3.getRemind());
    }

    public void testGetCategorys() throws SQLException{

        Category category1 = new Category("Test3","HAB","des3",true);
        categoryDAO.insert(category1);
        Category category2 = new Category("Test4","HAC","des4",false);
        categoryDAO.insert(category2);

        List<Category> categoryList = categoryDAO.getCategorys();

        Category category3 = categoryList.get(categoryList.size()-2);
        Category category4 = categoryList.get(categoryList.size()-1);

        assertEquals("TEST3", category3.getCategory_name());
        assertEquals("HAB", category3.getCategory_code());
        assertEquals("des3", category3.getCategory_des());
        assertEquals(true, (boolean)category3.getRemind());

        assertEquals("TEST4", category4.getCategory_name());
        assertEquals("HAC", category4.getCategory_code());
        assertEquals("des4", category4.getCategory_des());
        assertEquals(false, (boolean)category4.getRemind());

    }

}
