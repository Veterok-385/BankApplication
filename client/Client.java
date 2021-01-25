package client;

import java.util.Scanner;
import bank.Account;
import bank.User;

public class Client {
	
	public void controllerClient (User user) {
		System.out.println("�� ���� �������������� ��� � ����� �����, " + user.getName() + " " 
				+ user.getPatronymic() + ".");
		
		Account account = new Account();
		do {
			if(!account.isOwnershipAccount(user)) {         // ��� �������� ������ � ������������
				System.out.println("� ��� ���� ��� �������� ������ � ����� �����.");
				System.out.println("�������� ���������� ��������:");
				System.out.println("1. ������� ����");
				System.out.println("2. ����� � ���������� ����");
				System.out.println("3. ����� �� ���������");
				boolean forExit = false;
				try {
					switch (extracted().nextInt()) {
					case 1:
						account = account.openAccount(user);
						//noCorrectInput = false;
						break;	
					case 2:
						//noCorrectInput = false;
						forExit = true;
						break;
					case 3:
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
			} else {
				System.out.println("� ��� ������� �������� ����� � ����� �����.");
				System.out.println("�������� ���������� ��������:");
				System.out.println("1. ����������� �������� �����");
				System.out.println("2. ������� ����� ����");
				System.out.println("3. ����� � ���������� ����");
				System.out.println("4. ����� �� ���������");
				boolean forExit = false;
					//try {
					switch (extracted().nextInt()) {
					case 1:
						System.out.println("�� ���� ��� ������� ��������� �����:");
						account.ownershipAccount(user);
						do {
							System.out.println("�������� ���� ��� ��������� (\"0\" - ��� ������ � ���������� ����).");
							forExit = false;
							try {
								account = new Account();
								int dataInput = extracted().nextInt();
								if (dataInput == 0) {									
									forExit  = true;
									break;
								} else {
									account = account.checkAccount(dataInput, user);
									if (!(account==null)) {									
										workWithAccount(account);
										break;
									}											
								}
							} catch(Exception e) {
								System.out.println("������������ ���� ������.");
							}	
							if (forExit) {
								break;
							}
						} while(true);						
						break;
					case 2:
						forExit = false;
						account = account.openAccount(user);						
						break;
					case 3:		
						forExit = true;
						break;
					case 4:
						System.out.println("������ ���������� ���������.");
						System.exit(0);
					default:
						System.out.println("������������ ���� ������.");   	
					}	
				if (forExit) {
					break;
				}
			}
		} while (true);		
	}
	
	private void workWithAccount(Account account) {		
		do {
			System.out.printf("���� �:\t%05d.\n", account.getAccountNumber());
			if (account.getFrozenAccount()) {
				System.out.println("���� ���������!");
			}
			System.out.printf("������:\t%.2f ���.\n", account.getBalance());
			System.out.println("�������� ���������� ��������:");
			System.out.println("1. ��������� ������");
			System.out.println("2. ������� ��������");
			System.out.println("3. ���������� ������� �����");
			System.out.println("4. ������� ����");				
			System.out.println("5. ����� � ���������� ����");
			System.out.println("6. ����� �� ���������");
			boolean forExit = false;
			try {
				switch (extracted().nextInt()) {
				case 1:
					account = account.depositBalance(account);
					System.out.println("������� ������� \"Enter\"");
					extracted().nextLine();
					break;					
				case 2:
					account.cashWithdrawal(account);
					System.out.println("������� ������� \"Enter\"");
					extracted().nextLine();
					break;
				case 3:
					System.out.println(account.getHistoryAccount());
					System.out.println("������� ������� \"Enter\"");
					extracted().nextLine();
					break;
				case 4:
					account.closeAccount(account);
					return;				
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
