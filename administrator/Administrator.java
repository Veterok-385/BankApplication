package administrator;

import java.util.Scanner;

//import java.io.File;

import bank.Account;
import bank.Serialization;
import bank.User;
import client.Client;

public class Administrator {
	
	public void controllerAdministrator (User user) {
		System.out.println(user.getName() + " " + user.getPatronymic() + ", �� ��������� ����������� ������ �����.");
		do {
			System.out.println("�������� ���������� ��������:");
			System.out.println("1. ����������� ������ �������������");
			System.out.println("2. ����������� ������ ������");
			System.out.println("3. ����� � ���������� ����");
			System.out.println("4. ���������������� ����� ������� ������ ��������������");
			System.out.println("5. ����� � �������� �������");
			System.out.println("6. ����������� ���� ������� ������");
			System.out.println("7. ����� �� ���������");
			try {
				switch (extracted().nextInt()) {
				case 1:	
					user.listUsers();
					do {
						try {						
							System.out.println("������� �����, ������ �������� ������ ���������� (\"0\" - ��� ������ � ���������� ����).");
							String inputLogin = extracted().nextLine();
							if (inputLogin.equals("0")) {								
								break;
							}
							Serialization serialization = new Serialization();
							User userClient = serialization.deserializationUsers(inputLogin);
							if (userClient.getYearOfBirth()==0) {
								System.out.println("������������ ���� ������.");
							} else {
								System.out.printf("�������: %s\n���: %s\n��������: %s\n��� ��������: %d\n����� ������: %s\n"
										+ "(������� ������� \"Enter\")", userClient.getSurame(), userClient.getName(), userClient.getPatronymic(), 
										userClient.getYearOfBirth(), userClient.getPlaceOfWork());
								extracted().nextLine();
							}
						} catch(Exception e) {
							System.out.println("������������ ���� ������.");
						}
					} while (true);
					break;			
				case 2:	
					Account account = new Account();
					account.listAccounts();
					do {
						System.out.println("������� ����� ����� ��� ���������� ������(\"0\" - ��� ������ � ���������� ����).");
						Serialization serialization = new Serialization();
						try {
							int inputNumberAccount = extracted().nextInt();
							if (inputNumberAccount == 0) {
								break;
							} else {
								workWithAccount(serialization.deserializationAccount(inputNumberAccount + ".acc"));
							}	
						} catch (Exception e) {
							System.out.println("������������ ���� ������.");
						}
					} while (true);
					break;				
				case 3:
					return;				
				case 4:
					System.out.println("����������������� � �������� ��������������.");
					user = user.registrationUser(true);
					break;
				case 5:
					Client client = new Client();
					client.controllerClient(user);
					break;
				case 6:
					System.out.printf("�������: %s\n���: %s\n��������: %s\n��� ��������: %d\n����� ������: %s\n"
							+ "(������� ������� \"Enter\")", user.getSurame(), user.getName(), user.getPatronymic(), 
							user.getYearOfBirth(), user.getPlaceOfWork());
					extracted().nextLine();
					break;
				case 7:
					System.out.println("������ ���������� ���������.");
					System.exit(0);
				default:
					System.out.println("������������ ���� ������.");					
				}
			} catch (Exception e) {
				System.out.println("������������ ���� ������.");
			}
		} while(true);
	}	
	
	private void workWithAccount(Account account) {		
		do {
			System.out.printf("���� �:\t%05d.\n", account.getAccountNumber());
			if (account.getLogin().equals("CLOSE_ACCOUNT")) {
				System.out.println("���� ������!");
			}
			System.out.printf("������:\t%.2f ���.\n", account.getBalance());
			System.out.println("�������� ���������� ��������:");
			System.out.println("1. ���������� ������ ���������");
			System.out.println("2. ���������� ����");
			System.out.println("3. ����������� ����");
			System.out.println("4. ���������� ������� �����");
			System.out.println("5. ����� � ���������� ����");
			System.out.println("6. ����� �� ���������");
			boolean forExit = false;
			try {
				switch (extracted().nextInt()) {
				case 1:
					if (account.getLogin().equals("CLOSE_ACCOUNT")) {
						System.out.println("C��� ������! ��������� ���.");
						System.out.println("������� ������� \"Enter\"");
						extracted().nextLine();
						break;
					}
					User userClient = new User();
					Serialization serialization = new Serialization(); 
					userClient = serialization.deserializationUsers(account.getLogin());
					System.out.printf("�������: %s\n���: %s\n��������: %s\n��� ��������: %d\n����� ������: %s\n"
							+ "(������� ������� \"Enter\")", userClient.getSurame(), userClient.getName(), userClient.getPatronymic(), 
							userClient.getYearOfBirth(), userClient.getPlaceOfWork());
					extracted().nextLine();
					break;					
				case 2:
					account.frozenAccount(account);
					System.out.println("������� ������� \"Enter\"");
					extracted().nextLine();
					break;
				case 3:
					account.outFrozenAccount(account);
					System.out.println("������� ������� \"Enter\"");
					extracted().nextLine();
					break;					
				case 4:
					System.out.println(account.getHistoryAccount());
					System.out.println("������� ������� \"Enter\"");
					extracted().nextLine();
					break;			
				case 5:
					forExit = true;
					break;
				case 6:
					System.out.println("������ ���������� ���������.");
					System.exit(0);
				default:
					System.out.println("������������ ���� ������.");					
				}
			} catch (Exception e) {
				System.out.println("������������ ���� ������.");
			}
			if (forExit) {
				break;
			}
		} while (true);
	}
	
	private Scanner extracted() {
		return new Scanner(System.in);
	}
}