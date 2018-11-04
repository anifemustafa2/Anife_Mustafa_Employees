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

//���� �� ����������� ����� �� �����������
public class CEmployees {

 private int m_nEmpID; //���������� �� ����� �� ��������
 private int m_nProjectID; //���������� �� ����� �� ������
 private String m_DateFrom; //���� �� ��������� �� �������
 private String m_DateTo; //���� �� ����������� �� �������

 public CEmployees() {} //����������� �����������
 public CEmployees(int nEmpID, int nProjectID, String DateFrom, String DateTo) //����������� �����������
 {
  m_nEmpID = nEmpID;
  m_nProjectID = nProjectID;
  m_DateFrom = DateFrom;
  m_DateTo = DateTo;
 }

 public void setEmpID(int nEmpID) //�������� �� ����� �� ��������
 {
  m_nEmpID = nEmpID;
 }

 public int getEmpID() //������� �� ����� �� ��������
 {
  return m_nEmpID;
 }

 public void setProjectID(int nProjectID) //�������� ����� �� ������
 {
  m_nProjectID = nProjectID;
 }

 public int getProjectID() //������� ����� �� ������
 {
  return m_nProjectID;
 }

 public void setDateFrom(String DateFrom) //�������� ���� �� ��������� �� ������
 {
  m_DateFrom = DateFrom;
 }

 public String getDateFrom() //������� ���� �� ��������� �� ������
 {
  return m_DateFrom;
 }

 public void setDateTo(String DateTo) //�������� ���� �� ����������� �� ������
 {
  m_DateTo = DateTo;
 }

 public String getDateTo() //������� ���� �� ����������� �� ������
 {
  return m_DateTo;
 }

 @Override
 public String toString() //���������� �� ������� �� ������ � String
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
 public boolean equals(Object obj) //����� �� ���������� �� ������
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

 private static void printArray(CEmployees[] anArray) //����� �� ��������� �� ������ �� ������
 { 
  System.out.println(Arrays.toString(anArray));
 }

 public static void main(String[] args) {

  CEmployees[] arrEmp = new CEmployees[10]; //����� �� ��� CEmployees 
  CEmployees emp = new CEmployees(); //��������� �� CEmployees

  try {
   File empFile = new File("Employees.txt"); //��������� �� ������� ����������
   Scanner empReader = new Scanner(empFile); //��������� �� �����
   int EmpID, ProjectID;
   String DateFrom, DateTo;
   int index = 0;
   
   while (empReader.hasNext()) {
    EmpID = empReader.nextInt(); //����������� �� ����������� �� �����
    ProjectID = empReader.nextInt();
    DateFrom = empReader.next();
    DateTo = empReader.next();

    arrEmp[index] = new CEmployees(EmpID, ProjectID, DateFrom, DateTo); //��������� �� ���������� �� ����� � ������
    index++;
   }
   empReader.close();
  } catch (FileNotFoundException fnf) {
   System.out.println("File not found");
  }

  printArray(arrEmp); //��������� �� ������ ���� �����������

  Date DateTo = new Date();
  Map < Integer, Integer > daysMap = new HashMap < Integer, Integer > ();
  SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd"); //�������� �� ������� �� ������ �� �����
  
  try {
   Date DateFrom = myFormat.parse(emp.getDateFrom()); //���������� ������ �� String � Date
   for (int i = 0; i < arrEmp.length; i++) //��������� ������ �� �� ����� ���� ��� NULL ��������� �� DateTo
   {
    if (emp.getDateTo().equals("NULL")) 
    {
     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     Date today = Calendar.getInstance().getTime();//��� ��� ������� �������� ����
     String reportDate = df.format(today); //� ����������� �� ������������
     emp.setDateTo(reportDate);
     DateTo = myFormat.parse(reportDate); //���� ���� ���������� ������� � Date
    } else
     DateTo = myFormat.parse(emp.getDateTo()); //��� ���� NULL ��������� ���������� ������ � ��� Date

    long diff = DateFrom.getTime() - DateTo.getTime(); //����������� ����� ������ ����� ������

    daysMap.put(emp.getProjectID(), Math.toIntExact(diff)); //� ��� ����������� ����� �� ������ � ��� ������ ����� ����
   }
  } catch (ParseException e) {}

  int daysOnProject = daysMap.get(0);//��������� ���-� �� �� ������� ���-����� ��� ������ ����� ������
  for (int i = 0; i < daysMap.size(); i++) {
   for (int j = 0; j < daysMap.size(); j++) {
    if (daysMap.get(i) == daysMap.get(j)) {
     if (daysMap.get(i) > daysMap.get(j))
      daysOnProject = daysMap.get(i);
    }
   }
  }

  int secDaysOnProject=daysMap.get(0);; //��������� ���-� �� �� ������� ������� ���-����� ��� ������ 
   for (int i = 0; i < daysMap.size(); i++) {
   for (int j = 0; j < daysMap.size(); j++) {
    if (daysMap.get(i) == daysMap.get(j)) {
     if (daysMap.get(i) > daysMap.get(j)&&daysMap.get(i)<daysOnProject)
      secDaysOnProject = daysMap.get(i);
    }
   }
  }
   //��������� ���������
   System.out.println("Employees who worked for the longest time on the same project "+daysOnProject+" and "+secDaysOnProject);
 }
}