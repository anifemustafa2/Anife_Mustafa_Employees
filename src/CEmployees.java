import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//Клас за съхраняване данни за служителите
public class CEmployees {

 private int m_nEmpID; //Променлива за номер на служител
 private int m_nProjectID; //Променлива за номер на проект
 private String m_DateFrom; //Дата на започване на проекта
 private String m_DateTo; //Дата на приключване на проекта

 public CEmployees() {} //Подразбиращ конструктор
 public CEmployees(int nEmpID, int nProjectID, String DateFrom, String DateTo) //Експлицитен конструктор
 {
  m_nEmpID = nEmpID;
  m_nProjectID = nProjectID;
  m_DateFrom = DateFrom;
  m_DateTo = DateTo;
 }

 public void setEmpID(int nEmpID) //Задаване на номер на служител
 {
  m_nEmpID = nEmpID;
 }

 public int getEmpID() //Вземане на номер на служител
 {
  return m_nEmpID;
 }

 public void setProjectID(int nProjectID) //Задаване номер на проект
 {
  m_nProjectID = nProjectID;
 }

 public int getProjectID() //Вземане номер на проект
 {
  return m_nProjectID;
 }

 public void setDateFrom(String DateFrom) //Задаване дата на започване на проект
 {
  m_DateFrom = DateFrom;
 }

 public String getDateFrom() //Вземане дата на започване на проект
 {
  return m_DateFrom;
 }

 public void setDateTo(String DateTo) //Задаване дата на приключване на проект
 {
  m_DateTo = DateTo;
 }

 public String getDateTo() //Вземане дата на приключване на проект
 {
  return m_DateTo;
 }

 @Override
 public String toString() //Превръщане на данните на обекта в String
 {
  return "EmployeeID=" + m_nEmpID + ", ProjectID=" + m_nProjectID + ", DateFrom=" + m_DateFrom + ", DateTo=" + m_DateTo + "]\n";
 }

 @Override
 public int hashCode()
 {
  int hash = 3;
  hash = 41 * hash + this.m_nEmpID;
  hash = 41 * hash + this.m_nProjectID;
  hash = 41 * hash + Objects.hashCode(this.m_DateFrom);
  hash = 41 * hash + Objects.hashCode(this.m_DateTo);
  return hash;
 }

 @Override
 public boolean equals(Object obj) //Метод за сравняване на обекти
 {
  if (this == obj) {
   return true;
  }
  if (obj == null) {
   return false;
  }
  if (getClass() != obj.getClass()) {
   return false;
  }
  final CEmployees other = (CEmployees) obj;
  if (this.m_nEmpID != other.m_nEmpID) {
   return false;
  }
  if (this.m_nProjectID != other.m_nProjectID) {
   return false;
  }
  if (!Objects.equals(this.m_DateFrom, other.m_DateFrom)) {
   return false;
  }
  if (!Objects.equals(this.m_DateTo, other.m_DateTo)) {
   return false;
  }
  return true;
 }

 private static void printArray(CEmployees[] anArray) //Метод за извеждане на масива от обекти
 { 
  System.out.println(Arrays.toString(anArray));
 }

 public static void main(String[] args) {

  CEmployees[] arrEmp = new CEmployees[10]; //Масив от тип CEmployees 
  CEmployees emp = new CEmployees(); //Инстанция на CEmployees

  try {
   File empFile = new File("Employees.txt"); //Създаване на файлова променлива
   Scanner empReader = new Scanner(empFile); //Прочитане на файла
   int EmpID, ProjectID;
   String DateFrom, DateTo;
   int index = 0;
   
   while (empReader.hasNext()) {
    EmpID = empReader.nextInt(); //Присвояване на стойностите от файла
    ProjectID = empReader.nextInt();
    DateFrom = empReader.next();
    DateTo = empReader.next();

    arrEmp[index] = new CEmployees(EmpID, ProjectID, DateFrom, DateTo); //Прибавяне на елементите от файла в масива
    index++;
   }
   empReader.close();
  } catch (FileNotFoundException fnf) {
   System.out.println("File not found");
  }

  printArray(arrEmp); //Извеждане на масива след запълването

  Date DateTo = new Date();
  Map < Integer, Integer > daysMap = new HashMap < Integer, Integer > ();
  SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd"); //Задаване на формата на датата от файла
  
  try {
   Date DateFrom = myFormat.parse(emp.getDateFrom()); //Превръщаме датата от String в Date
   for (int i = 0; i < arrEmp.length; i++) //Обхождаме масива за да видим дали има NULL стойности за DateTo
   {
    if (emp.getDateTo().equals("NULL")) 
    {
     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     Date today = Calendar.getInstance().getTime();//Ако има вземаме днешната дата
     String reportDate = df.format(today); //И присвояваме на променливата
     emp.setDateTo(reportDate);
     DateTo = myFormat.parse(reportDate); //След това превръщаме стринга в Date
    } else
     DateTo = myFormat.parse(emp.getDateTo()); //Ако няма NULL стойности превръщаме датите в тип Date

    long diff = DateFrom.getTime() - DateTo.getTime(); //Изчисляваме дните работа върху проект

    daysMap.put(emp.getProjectID(), Math.toIntExact(diff)); //В Мар съхраняваме номер на проект и дни работа върху него
   }
  } catch (ParseException e) {}

  int daysOnProject = daysMap.get(0);//Обхождаме Мар-а за да намерим най-много дни работа върху проект
  for (int i = 0; i < daysMap.size(); i++) {
   for (int j = 0; j < daysMap.size(); j++) {
    if (daysMap.get(i) == daysMap.get(j)) {
     if (daysMap.get(i) > daysMap.get(j))
      daysOnProject = daysMap.get(i);
    }
   }
  }

  int secDaysOnProject=daysMap.get(0);; //Обхождаме Мар-а за да намерим вторите най-много дни работа 
   for (int i = 0; i < daysMap.size(); i++) {
   for (int j = 0; j < daysMap.size(); j++) {
    if (daysMap.get(i) == daysMap.get(j)) {
     if (daysMap.get(i) > daysMap.get(j)&&daysMap.get(i)<daysOnProject)
      secDaysOnProject = daysMap.get(i);
    }
   }
  }
   //Извеждаме резултата
   System.out.println("Employees who worked for the longest time on the same project "+daysOnProject+" and "+secDaysOnProject);
 }
}