import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.Scene;
import javafx.stage.Stage;
import myfileio.MyFileIO;

public class ListController {
	private ArrayList<Employee> employees;
	private static final boolean DEBUG = true;
	MyFileIO fileIOref = new MyFileIO();
	Employee newEmployee;
	
	public ListController () {
		Employee.resetStaticID();
		employees = new ArrayList<Employee>();
		loadEmployeeData();
	}

	// adds a new employee
	String addEmployee(String firstName, String lastName, String ssn, String age, String pronouns, String salary, String years, String dept) {
		// TODO #7
		// controller needs to convert the numeric string data -
		// use Integer.parseInt() or Double.parseDouble() for ints and doubles
		// years should be int, salary should be a double....
		// Then, add the new employee to the employees list!
		// for initial demo and debugging, set DEBUG to true;

		try {
			if("".equals(firstName) || "".equals(lastName) || "".equals(ssn) || "".equals(age) || "".equals(salary) || "".equals(years) || "".equals(dept)) {
				return "alert";
			} else if("".equals(firstName) || "".equals(lastName)) {
				return "noNameError";
			} else if(Integer.parseInt(age) < 16) {
				return "underAgeError";
			} else if(Integer.parseInt(age) < Integer.parseInt(years) + 16) {
				return "ageError";
			} else if(duplicateSSN(ssn)) {
				return "ssnError";
			} else if(!correctSSN(ssn)) {
				return "ssnError";
			} else if(Double.parseDouble(salary) < 30000) {
				return "salaryError";
			} else if(Integer.parseInt(age) > 80) {
				System.out.println("Interesting! You are hiring someone above the age of 80?");
			}
			employees.add(new Employee(firstName, lastName, ssn, Integer.parseInt(age), pronouns, Double.parseDouble(salary), Integer.parseInt(years), dept));
			if (DEBUG) System.out.println(employees);
			return "";


		} catch(NumberFormatException e) {
			return "numberFormatException";
		}
					
	}
	
	// returns a string array of the employee information to be viewed
	String[] getEmployeeDataStr() {
		String [] tempEmployeeData = new String[employees.size()];
		for(int i = 0; i < employees.size(); i++) {
			tempEmployeeData[i] = employees.get(i).toString();
		}
		return tempEmployeeData;
	}
		
	void saveEmployeeData() {
		fileIOref.createEmptyFile("empDB.dat");
		sortByID();
		try {
			File databaseFile = fileIOref.getFileHandle("empDB.dat");
			if(databaseFile.canWrite()) {
				BufferedWriter bw = fileIOref.openBufferedWriter(databaseFile);
				for(int i = 0; i < employees.size(); i++) {
					bw.append(employees.get(i).getEmpID() + "|,|" + employees.get(i).getFirstName() + "|,|" + employees.get(i).getLastName() + "|,|" + employees.get(i).getSSN() + "|,|" + 
				employees.get(i).getAge() + "|,|" + employees.get(i).getPronouns() + "|,|"+ String.format("%.2f", ((long) (employees.get(i).getSalary() * 100)/100.0)) + "|,|" + employees.get(i).getYears() + "|,|" + employees.get(i).getDept());
					bw.newLine();
				}
				bw.flush();
				bw.close();	
			}
		} catch (IOException e) {
			System.out.println("Saving data FAILED");
		}
		
		//BufferedWriter br = new BufferedWriter();
	}
	
	void loadEmployeeData() {
		String[] addEmployeeVariables;
		String currLine = null;
		try {
			File databaseFile = fileIOref.getFileHandle("empDB.dat");
			if(fileIOref.checkTextFile(databaseFile, true) != MyFileIO.FILE_OK) {
				return;
			}
			BufferedReader br = fileIOref.openBufferedReader(databaseFile);
			
			while((currLine = br.readLine()) != null) {
				addEmployeeVariables = currLine.split("\\|,\\|");
				addEmployee(addEmployeeVariables[1], addEmployeeVariables[2], addEmployeeVariables[3], addEmployeeVariables[4], addEmployeeVariables[5]
						, addEmployeeVariables[6], addEmployeeVariables[7], addEmployeeVariables[8]);
				Employee lastEmployee = employees.get(employees.size() - 1);
				lastEmployee.setEmpID(Integer.parseInt(addEmployeeVariables[0]));
			}
			fileIOref.closeFile(br);
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Loading data FAILED");
			
		}
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}
	
	void sortByID() {
		Collections.sort(employees, new ByID());
	}
	
	private class ByID implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			return Integer.compare(o1.getEmpID(), o2.getEmpID());
		}
	}
	
	void sortByName() {
		Collections.sort(employees, new ByName());
	}
	
	private class ByName implements Comparator<Employee> {
		int last;
		int first;
		public int compare(Employee o1, Employee o2) {
			last = o1.getLastName().compareToIgnoreCase(o2.getLastName());
			if(last == 0) {
				first = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
				return first;
			} else {
				return last;
			}
		}
	}
	
	void sortBySalary() {
		Collections.sort(employees, new BySalary());
	}
	
	private class BySalary implements Comparator<Employee> {
		public int compare(Employee o1, Employee o2) {
			return Double.compare(((int) o1.getSalary()), ((int)o2.getSalary()));
		}
	}
	
	public int getNumEmployees() {
		return employees.size();
	}
	
    boolean correctSSN(String str) {
    	String[] ssnFormat = str.split("\\-");
    	if(ssnFormat[0].matches("[0-9]{3}")) {
    		if(ssnFormat[1].matches("[0-9]{2}")) {
    			if(ssnFormat[2].matches("[0-9]{4}")) {
    				return true;
    			}
    		}
    	}
		return false;
    	
//    	if(str.matches("\\[0-9]{3}-\\[0-9]{2}-\\[0-9]{4}")) {
//    		return true;
//    	} else {
//    		return false;
//    	}
    	//Has errors with adding 4 in the front (checked with regex101)
    }
    
    boolean duplicateSSN(String str) {
    	for(int i = 0; i < employees.size(); i++) {
    		if(employees.get(i).getSSN().equals(str)) {
    			return true;
    		}
    	}
    	return false;
    }

	
}
 
