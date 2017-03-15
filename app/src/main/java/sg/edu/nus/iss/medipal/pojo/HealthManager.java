package sg.edu.nus.iss.medipal.pojo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.medipal.asynTask.AddCategory;
import sg.edu.nus.iss.medipal.asynTask.AddMedicine;
import sg.edu.nus.iss.medipal.asynTask.DeleteMedicine;
import sg.edu.nus.iss.medipal.asynTask.ListCategory;
import sg.edu.nus.iss.medipal.asynTask.ListMedicine;

/**
 * Created by zhiguo on 15/3/17.
 */

public class HealthManager {

    private ArrayList<Medicine> medicines;
    private ArrayList<Category> categorys;

    private AddMedicine     taskAddMedicine;
    private ListMedicine    taskListMedicine;
    private DeleteMedicine  taskDeleteMedicine;
    private AddCategory     taskAddCategory;
    private ListCategory    taskListCategory;

    public HealthManager(){
        this.medicines  =   new ArrayList<Medicine>();
        this.categorys =   new ArrayList<Category>();

    }

    public Medicine getMedicine(int id){
        Iterator<Medicine> i = medicines.iterator();

        while(i.hasNext()){
            Medicine m = i.next();
            if( m.getId() == id)
            {
                return m;
            }
        }

        return null;
    }

    //SQLite get medicine list
    public List<Medicine> getMedicines(Context context) throws ExecutionException, InterruptedException {
        taskListMedicine = new ListMedicine(context);
        taskListMedicine.execute((Void)null);

        try{
            medicines = taskListMedicine.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if(medicines == null){
            medicines = new ArrayList<Medicine>();
        }

        return new ArrayList<Medicine>( medicines);

    }

    //SQLite add medicine
    public Medicine addMedicine(int id, String medicine_name, String medicine_des, int cateId,
                                int reminderId, Boolean reminder, int quantity, int dosage,
                                String dateIssued, int expireFactor,Context context){

        Medicine medicine = new Medicine(id, medicine_name, medicine_des,cateId, reminderId, reminder, quantity, dosage, dateIssued, expireFactor);

        taskAddMedicine = new AddMedicine(context);
        taskAddMedicine.execute(medicine);

        return medicine;

    }

    //SQLite delete medicine

    public void deleteMedicine(int id,Context context){

        Medicine m = getMedicine(id);

        if(m != null)
        {
            taskDeleteMedicine = new DeleteMedicine(context);
            taskDeleteMedicine.execute(m);
        }

    }


    public Category getCategory(int id){

        Iterator<Category> i = categorys.iterator();

        while(i.hasNext()){
            Category c = i.next();
            if( c.getId() == id)
            {
                return c;
            }
        }

        return null;
    }

    //SQLite get Category list
    public List<Category> getCategorys(Context context) throws ExecutionException, InterruptedException {
        taskListCategory = new ListCategory(context);
        taskListCategory.execute((Void)null);

        try{
            categorys = taskListCategory.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        if(categorys == null){
            categorys = new ArrayList<Category>();
        }

        return new ArrayList<Category>(categorys);

    }

    //SQLite add medicine
    public Category addCategory(int id, String category_name,String category_code,String category_des,Context context){

        Category category = new Category(id, category_name,category_code,category_des);

        taskAddCategory = new AddCategory(context);
        taskAddCategory.execute(category);

        return category;

    }

}
