package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean checkAministration;
	private String login;
	private String surname;
	private String name;
    private String patronymic;
    private int yearOfBirth;
    private String placeOfWork;

// ����������� ��� ����������    
    public User() {
    	// ����������� ��� ����������
    }

// ����������� � �����������, ������� ��������� � ���� ������� ������
    User(boolean checkAministration, String login, 
			String surname, String name, String patronymic, 
			int yearOfBirth, String placeOfWork) {
    	this.checkAministration = checkAministration;
    	this.login = login;
    	this.surname = surname;
    	this.name = name;
    	this.patronymic = patronymic;
    	this.yearOfBirth = yearOfBirth;
    	this.placeOfWork = placeOfWork;
    }

// �����, ����������� ������������ ������������������ � ������ ���� ������     
	public User registrationUser(boolean a) {		
		setCheckAministration(a);
		System.out.println("������� �������:");
		setSurname(extracted().nextLine());
		System.out.println("������� ���:");
		setName(extracted().nextLine());
		System.out.println("������� ��������:");
		setPatronymic(extracted().nextLine());
// ��������� ���� ������������ ���� ���� �������� � ��������� ������� ��������� ������**		
		yearOfBirth = -1;
		do {
			if (!(yearOfBirth == -1) && yearOfBirth <= (Calendar.getInstance().get(Calendar.YEAR) - 101)) 
				System.out.println("��� ������ 100 ���, � ��������� ��� ���� ��"
						+ " ����� � ���� ������������. ��������, ��������� ��� ������ ��������.");
			else if (yearOfBirth > Calendar.getInstance().get(Calendar.YEAR))
				System.out.println("������, �� ��� �� ��������. ��������, ��������� ��� ������ ��������.");		
			else if (yearOfBirth >= (Calendar.getInstance().get(Calendar.YEAR) - 17))
				System.out.println("�� ������������������, � ��������� ��� ���� ��" 
						+ " ����� � ���� ������������. ��������, ��������� ��� ������ ��������.");
			System.out.println("������� ��� ��������:");
			try {
				setYearOfBirth(extracted().nextInt());
			}
			catch(Exception e) {
				System.out.println("��� �������� ������ �������� �� ����.");
			}
		} while (yearOfBirth <= (Calendar.getInstance().get(Calendar.YEAR) - 
				101) || yearOfBirth >= (Calendar.getInstance().get(Calendar.YEAR) - 18));
//****************************************************************************************
		if (a) 
			setPlaceOfWork("����");
		else {
			System.out.println("������� ����� ������: ");
			setPlaceOfWork(extracted().nextLine());
		}
//��������� ��� ���������� ����� � ������ � ���� verification.dat**************************	
		String password;
		do {
		System.out.println("���������� �����:");
		setLogin(extracted().nextLine());
		System.out.println("���������� ������:");
		password = extracted().nextLine();
		}
		while (!(new Serialization()).serializationUsers(login, password));		
//******************************************************************************************
//��������� ��� ����������� ������ ������ User � ���� � ������ "(�������� login) + .usr" � ��������� � ����� "accounts"
		Serialization serialization = new Serialization();
		serialization.serializationUsers(checkAministration, login, surname, 
				name, patronymic, yearOfBirth, placeOfWork);
		return serialization.deserializationUsers(login);
//******************************************************************************************
	}
	
// ����� ��� ����� � ������� ������	
	String entryUser() throws IOException {
        Properties properties = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("verification.dat");
        } catch (FileNotFoundException e) {
            // ������������ ������������� ����
        }
        // ��������� ������������ ������� ������.
        if (fileInputStream != null) {
            properties.load(fileInputStream);
            fileInputStream.close();
        }
        //try {
        System.out.println("��� ������������� ������������ � ������� ������� �����: ");
        String inputName = bufferedReader.readLine();
        System.out.println("������� ������:");
        String inputPass = bufferedReader.readLine();
        try {
            String pass = (String) properties.get(inputName);
            if (pass.equals(inputPass)) {
                return inputName;
            }
            else {
                return "UserIsNoVerification";
            }
        } catch (Exception e) {
            return "UserIsNoVerification";
        }
	}
	
// ����� ������� ������ �������������
	public void listUsers() {
		Serialization serialization = new Serialization();
        File folderUsr = new File("users");
        User user = new User();
    	int serialNumber = 1;
    	for (File fileUsr : folderUsr.listFiles()) {
    		user = serialization.deserializationUsers(fileUsr.getName().substring(0, 
						(fileUsr.getName().length()-4)));        			
    		String administrator = "";
    		if (user.checkAministration) 
    			administrator = " - ������������� �����.";
        	System.out.printf("%d. %s %s %s (�����: %s)%s\n", serialNumber, user.getSurame(), 
        			user.getName(), user.getPatronymic(), user.getLogin(), administrator);
        	serialNumber++;
    	}	

	}
	
	private Scanner extracted() {
		return new Scanner(System.in);
	}
	
	void setCheckAministration(boolean a) {
		checkAministration = a;
	}	

	boolean getCheckAministration() {
		return checkAministration;
	}
	
	void setLogin(String a) {
		login = a;
	}	

	String getLogin() {
		return login;
	}	
	
	void setSurname(String a) {
		surname = a;
	}
	
	public String getSurame() {
		return surname;
	}
	
	void setName(String a) {
		name = a;
	}
	
	public String getName() {
		return name;
	}
	
	void setPatronymic(String a) {
		patronymic = a;
	}
	
	public String getPatronymic() {
		return patronymic;		
	}
	
	void setYearOfBirth(int a) {
		yearOfBirth = a;
	}
	
	public int getYearOfBirth() {
		return yearOfBirth;		
	}
	
	void setPlaceOfWork(String a) {
		placeOfWork = a;
	}
	
	public String getPlaceOfWork() {
		return placeOfWork;		
	}
}
