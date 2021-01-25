package bank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

public class Serialization {	

// ��������� ����� ���������� ����� � ������ � ���� verification.dat, ���������� false ��� ��������� �������
	boolean serializationUsers(String login, String password) {
        FileInputStream fileInputStream = null;	
        Properties properties = new Properties();

        try {
            fileInputStream = new FileInputStream("verification.dat");
        } catch (FileNotFoundException e) {
            // ������������ ������������� ����
        }
        try {
            if (fileInputStream != null) {
                properties.load(fileInputStream);
                fileInputStream.close();
            }
        }
        catch (IOException e) {
            System.out.println("������ ������ �����.");
        }
        if (!(properties.get(login) == null)) {
            System.out.println("����� �����.");
            return false;
        }
        try {
        	properties.put(login, password);
        	FileOutputStream fout = new FileOutputStream("verification.dat");
        	properties.store(fout, "Verification users");
        	fout.close();
        	return true;
        }
        catch (Exception e) {
        	System.out.println("���������� ��� ������ ����� verification.dat");
        return false;
        }
	}	
// ��������� ����� ����������� ������ ������ User � ���� � ������ "�������� _login + .usr" � ��������� � ����� "users"
	void serializationUsers(boolean checkAministration, String login, 
			String surname, String name, String patronymic, 
			int yearOfBirth, String placeOfWork) {
		User user = new User(checkAministration, login, surname, 
				name, patronymic, yearOfBirth, placeOfWork);
		File accounts = new File("users");
        if (!accounts.exists()) {
           accounts.mkdir();               
        }
        File acc = new File ("users", login+".usr");
        try ( ObjectOutputStream objOStrm =
                      new ObjectOutputStream(new FileOutputStream(acc)) ) {
            objOStrm.writeObject(user);
        }
        catch(IOException �) {
            System.out.println("���������� �� ����� ������������ ������� User: " + �) ;
        }
	}
// ��������� ����� ������������� ������ ������ User �� ����� � ������ "��������_login + .usr" �� ����� "users"	
	public User deserializationUsers(String login) {
        User user = new User();
        File file = new File("users", login + ".usr");
        try (ObjectInputStream objIStrm = new ObjectInputStream(new FileInputStream(file))) {
            user = (User)objIStrm.readObject();
        } catch (Exception e) {
            //System.out.println("���������� �� ����� �������������� UserRegistration: " + e);
            //System.exit(0);
        }
        return user;
	}
// ��������� ����� ����������� ������ ������ Account � ���� � ������ "��������_accountNumber + .acc" � ��������� � ����� "accounts"
	void serializationAccount (Account account) {
        File file = new File("accounts");
        if (!file.exists()) {
           if (file.mkdir()) {               
           } 
        } 
        file = new File ("accounts", account.getAccountNumber()+".acc");
        try ( ObjectOutputStream objOStrm =
                      new ObjectOutputStream(new FileOutputStream(file)) ) {
            objOStrm.writeObject(account);
        }
        catch(IOException �) {
            System.out.println("���������� �� ����� ������������ ������� BankAccount: " + �) ;
        }    
	}
// ��������� ����� ������������� ������ ������ Account �� ����� � ������ "��������_accountNumber + .acc" �� ����� "accounts"
	public Account deserializationAccount(String nameFileAccount) {
		Account account = new Account();
        File file = new File("accounts", nameFileAccount);
        try ( ObjectInputStream objIStrm =
                      new ObjectInputStream(new FileInputStream(file)) ) {
            account = (Account) objIStrm.readObject();
        }
        catch(Exception e) {
            return account = null;
        }
        return account;
	}
}

	
