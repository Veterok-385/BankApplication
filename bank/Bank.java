package bank;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import administrator.Administrator;
import client.Client;

public class Bank {

	public static void main(String[] args) throws IOException {
// ������������ ������� �� ���������� "���" ��� �� "������� �������� �����������, ��������� � �������"
//  ������� � 5 - ������������ ����������, ����������� ������ �����		
		System.out.println("����� ���������� � ����������, ����������� ������ �����.");
		
		File checkExistFileVerificationDat = new File("verification.dat");
		User user = new User();
// �� ������� ����� "verification.dat" ��������� �����, ����������� �� ��������� �����
		boolean firstRunProgram = false;
		if (!checkExistFileVerificationDat.exists()) {
			System.out.println("������, ��������� �������� �������.");
			firstRunProgram = true;
		}
		String login;
		Serialization serialization = new Serialization();
		do {
			if (firstRunProgram) {
				System.out.println("����������������� � �������� ��������������.");
				user = user.registrationUser(true);
				login = user.getLogin();
			} else {	
				if (user == null) {
					user = new User();
				}
				login = user.entryUser();
				user = serialization.deserializationUsers(login);
			}
			if (user.getCheckAministration()) {
				Administrator administrator = new Administrator();
				administrator.controllerAdministrator(user);	//*************�������� ���������� � ����� controllerAdministrator	
				user = null;
			}			
			boolean noCorrectInput = true;	
			String message = "������������ � ������ �������� ������� �� ����������.\n�������� ���������� ��������:";
			do {
				if (login.equals("UserIsNoVerification")) {
					System.out.println(message);
					System.out.println("1. ���������������� ������ ������������");
					System.out.println("2. ����� ��� ������� �������� �������");			
					System.out.println("3. ����� �� ���������");
					try {
						switch (extracted().nextInt()) {
						case 1:
							System.out.println("����������� ������ ������������:");
							user = user.registrationUser(false);
							login = "";
							break;
		            	
						case 2:
							user = null;
							noCorrectInput = false;
							break;
						case 3:
							System.out.println("������ ���������� ���������.");
							System.exit(0);
						default:
							System.out.println("������������ ����.");
							message = "�������� ���������� ��������:";
							break;						
						}	
					} catch (Exception e) {
						System.out.println("������������ ����.");
						message = "�������� ���������� ��������:";
					}
				} else {
					noCorrectInput = false;
				}
			} while (noCorrectInput);
			Client client = new Client();
			if (!(user == null)) {
				client.controllerClient(user);//*************�������� ���������� � ����� controllerClient
			} 
		} while (true);	
	}
// ��������������� ����� ������ � �������� ������ Scanner
	private static Scanner extracted() {
		return new Scanner(System.in);
	}
}
