package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class DepartmentList extends ArrayList<Department> {

	private static final long serialVersionUID = 1L;
	
	private static DepartmentList instance;
	
    
    private DepartmentList() {
    	    	
    }

    public static DepartmentList getInstance() {
        if (instance == null) {
            instance = new DepartmentList();
            instance.add(new model.Department(1,221,"Кафедра програмної інженерії та інтелектуальних технологій управління"
            		                ,"ППІТУ",LocalDate.of(2022, 1 , 1)));
            instance.add(new model.Department(2,222,"Кафедра інформаційних система та технологій"
	                ,"ІСТ",LocalDate.of(2022, 1 , 1)));
            //instance.add(new model.Institute(3,400,"Навчально-науковий інститут соціально-гуманітарних технологій"
	         //       ,"СГТ",LocalDate.of(2020, 7 , 1)));
        }
        return instance;
    }

}
