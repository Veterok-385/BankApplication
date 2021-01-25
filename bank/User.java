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

// конструктор без аргументов    
    public User() {
    	// конструктор без аргументов
    }

// конструктор с аргументами, которые запишутся в поля объекта класса
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

// метод, позволяющий пользователю зарегистрироваться и ввести свои данные     
	public User registrationUser(boolean a) {		
		setCheckAministration(a);
		System.out.println("Введите фамилию:");
		setSurname(extracted().nextLine());
		System.out.println("Введите имя:");
		setName(extracted().nextLine());
		System.out.println("Введите отчество:");
		setPatronymic(extracted().nextLine());
// следующий блок обрабатывает ввод даты рождения и исключает неверно введенные данные**		
		yearOfBirth = -1;
		do {
			if (!(yearOfBirth == -1) && yearOfBirth <= (Calendar.getInstance().get(Calendar.YEAR) - 101)) 
				System.out.println("Вам больше 100 лет, к сожалению наш банк не"
						+ " может с Вами сотрудничать. Уточните, пожалуста год Вашего рождения.");
			else if (yearOfBirth > Calendar.getInstance().get(Calendar.YEAR))
				System.out.println("Похоже, вы еще не родились. Уточните, пожалуста год Вашего рождения.");		
			else if (yearOfBirth >= (Calendar.getInstance().get(Calendar.YEAR) - 17))
				System.out.println("Вы несовершеннолетний, к сожалению наш банк не" 
						+ " может с Вами сотрудничать. Уточните, пожалуста год Вашего рождения.");
			System.out.println("Введите год рождения:");
			try {
				setYearOfBirth(extracted().nextInt());
			}
			catch(Exception e) {
				System.out.println("Год рождения должен состоять из цифр.");
			}
		} while (yearOfBirth <= (Calendar.getInstance().get(Calendar.YEAR) - 
				101) || yearOfBirth >= (Calendar.getInstance().get(Calendar.YEAR) - 18));
//****************************************************************************************
		if (a) 
			setPlaceOfWork("Банк");
		else {
			System.out.println("Укажите место работы: ");
			setPlaceOfWork(extracted().nextLine());
		}
//следующий код записывает логин и пароль в файл verification.dat**************************	
		String password;
		do {
		System.out.println("Придумайте логин:");
		setLogin(extracted().nextLine());
		System.out.println("Придумайте пароль:");
		password = extracted().nextLine();
		}
		while (!(new Serialization()).serializationUsers(login, password));		
//******************************************************************************************
//следующий код сериализует обьект класса User в файл с именем "(значение login) + .usr" и сохраняет в папке "accounts"
		Serialization serialization = new Serialization();
		serialization.serializationUsers(checkAministration, login, surname, 
				name, patronymic, yearOfBirth, placeOfWork);
		return serialization.deserializationUsers(login);
//******************************************************************************************
	}
	
// метод для входа в учетную запись	
	String entryUser() throws IOException {
        Properties properties = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("verification.dat");
        } catch (FileNotFoundException e) {
            // Игнорировать отсутствующий файл
        }
        // Загрузить существующие учетные данные.
        if (fileInputStream != null) {
            properties.load(fileInputStream);
            fileInputStream.close();
        }
        //try {
        System.out.println("Для идентификации пользователя в системе введите логин: ");
        String inputName = bufferedReader.readLine();
        System.out.println("Введите пароль:");
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
	
// Метод выводит список пользователей
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
    			administrator = " - администратор банка.";
        	System.out.printf("%d. %s %s %s (Логин: %s)%s\n", serialNumber, user.getSurame(), 
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
