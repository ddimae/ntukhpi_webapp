package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class InstituteList extends ArrayList<Institute> {

	private static final long serialVersionUID = 1L;
	
	private static InstituteList instance;
	
    
    private InstituteList() {
    	    	
    }

    public static InstituteList getInstance() {
        if (instance == null) {
            instance = new InstituteList();
            instance.add(new model.Institute(1,220,"Навчально-науковий інститут комп\'ютерного моделювання, прикладної фізики та математики"
            		                ,"КМПФМ",LocalDate.of(2022, 1 , 1)));
            instance.add(new model.Institute(2,350,"Навчально-науковий інститут комп\'ютерних наук та інформаційних технологій"
	                ,"КНІТ",LocalDate.of(2022, 1 , 1)));
            instance.add(new model.Institute(3,400,"Навчально-науковий інститут соціально-гуманітарних технологій"
	                ,"СГТ",LocalDate.of(2020, 7 , 1)));
        }
        return instance;
    }
    
    

}
