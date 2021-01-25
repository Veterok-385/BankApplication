package bank;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;


public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int accountNumber;
	private String login;
	private double balance;
	private String historyAccount;
	private boolean frozenAccount;

// ����� ��� �������� �����		
	public Account openAccount(User user) {
        accountNumber = 0;
        do {
            accountNumber++;
        } while (new File("accounts",accountNumber + ".acc").exists());
        Account account = new Account();
        account.login = user.getLogin();
        Date date = new Date();
        account.historyAccount = "";
        account.accountNumber = accountNumber;
        account.historyAccount = account.historyAccount + String.format
        		("%te.%<tm.%<tY �. %<tR - �������� ������ ����� � %05d, ����� �� ����� ���������� %.2f ���.", date, accountNumber, balance);
        account.balance = 0;
        account.setFrozenAccount(false);
        System.out.printf(user.getName() + " " + user.getPatronymic() + ", ��� ��� ������ ����� ���� � ����� �����. "
        		+ "����� ����� � %05d, ����� �� ����� ����� ���������� %.2f ���. \n", accountNumber, balance);
		System.out.println("������� ������� \"Enter\"");
		extracted().nextLine();
// ������������ �������  Account
        Serialization serialization = new Serialization();
        serialization.serializationAccount(account);
		return account = serialization.deserializationAccount(account.getAccountNumber() + ".acc");
	}
// 	����� ���������, ���� �� � ������� ���� ���� ����, ���������� true, ���� ����
	public boolean isOwnershipAccount(User user) {
		boolean isOwnershipAccount = false;
		Serialization serialization = new Serialization();
        File folder = new File("accounts");
        if (folder.exists()){
            for (File file : folder.listFiles()) {
        		Account account = serialization.deserializationAccount(file.getName());
            	if (user.getLogin().equals(account.getLogin())) {
            		isOwnershipAccount = true;
            	}
            }
        }
		return isOwnershipAccount;
	}
// 	����� ������� ������ ������, ������������� �������
	public void ownershipAccount(User user) {
		Serialization serialization = new Serialization();
        File folder = new File("accounts");
        if (folder.exists()){
            for (File file : folder.listFiles()) {
        		Account account = serialization.deserializationAccount(file.getName());
            	if (user.getLogin().equals(account.getLogin())) {
            		System.out.printf("� %05d", account.getAccountNumber());
            		if (account.getFrozenAccount() == true) {
            			System.out.println(" - ���������.");
            		}
            		else {
            			System.out.println(".");
            		}
            	}
            }
        }
	}
// ����� ���������, ����������� �� ���� ������� � ���������� ������ ������ Account (���� �� ����������� �� ���������� null)
	public Account checkAccount(int accountNumber, User user) {		
		Account account = new Account();
		Serialization serialization = new Serialization();
		File file = new File("accounts", accountNumber + ".acc");
		try {
			if (!file.exists()) {
				System.out.println("������ ���� ��� �� �����������."); // �� ����� ���� 
																			//"����� � ����� ������� �� ����������."
				return account = null;
			}		
			else 
				account = serialization.deserializationAccount(accountNumber + ".acc");
		} catch (Exception e) {
			return account = null;
		}
		if (account.getLogin().equals(user.getLogin())) {	
			return account;
		}		
		System.out.println("������ ���� ��� �� �����������.");
		return account = null;
	}
// ����� ��� ���������� �������
	public Account depositBalance(Account account) {
		double addSum = 0;
		if (account.getFrozenAccount() == false) {
			do {
				try {
					System.out.println("��� ���������� ������� ������� ����� (� ���.):\t\t(\"0\" - ��� ������ � ���������� ����).");			
					addSum = extracted().nextDouble();		
					if (addSum == 0) {
						return account;					
					}
					else if (addSum < 0) {					
						System.out.println("�������� ����� ������ ���� �������������.");	
					}
					else {	
						addSum = (Math.round(addSum*100))*0.01;
						account.setBalance(account.getBalance() + addSum);
						break;
					}
				} catch (Exception e) {
					System.out.println("������������ ���� ������."); 
				}
			} while (true);
		} else {
			System.out.println("�� �� ������ ��������� ������ ��������.\n���� ���������!");
			return account;
		}
        Date date = new Date();
		String addHistoryAccount = String.format("\n%te.%<tm.%<tY �. %<tR - ���������� ������� ����� � %05d "
				+ "�� ����� %s ���., ������ ����� ���������� %.2f ���.", date, account.getAccountNumber(),
				addSum, account.getBalance());
		System.out.printf("�� ��������� ���� � %05d, �� ����� %.2f ���., ������ ����� ���������� %.2f ���.\n", 
				account.getAccountNumber(), addSum, account.getBalance());
		account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
		Serialization serialization = new Serialization();
		serialization.serializationAccount(account);
		return account = serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
	}
// ����� ��� ������ ��������
	public Account cashWithdrawal(Account account) {
		double addSum = 0;
		if (account.getFrozenAccount() == false) {
			do {
				try {
					System.out.println("������� ����� ��� ������ �������� (� ���.):\t\t(\"0\" - ��� ������ � ���������� ����).");			
					addSum = extracted().nextDouble();			
					if (addSum == 0) {
						return account;					
					}
					else if (addSum < 0) {
						System.out.println("�� �� ������ ����� ������������� �����.");					
					} else {
						if (account.getBalance() < addSum) {
							System.out.println("�� ����� ����� ������������ ������� ��� ������ ������ �����.");						
						} else {
							addSum = (Math.round(addSum*100))*0.01;
							account.setBalance(account.getBalance() - addSum);
						break;
						}					
					}
				} catch (Exception e) {
					System.out.println("������������ ���� ������."); 
				}
			} while (true);
		} else {
			System.out.println("�� �� ������ ��������� ������ ��������.\n���� ���������!");
			return account;
		}
        Date date = new Date();
		String addHistoryAccount = String.format("\n%te.%<tm.%<tY �. %<tR - ������ �������� �� ����� � %05d "
				+ "�� ����� %.2f ���., ������ ����� ���������� %.2f ���.", date, account.getAccountNumber(),
				addSum, account.getBalance());
		System.out.printf("�� ����� �������� �� ����� � %05d, �� ����� %.2f ���., ������ ����� ���������� %.2f ���.\n", 
				account.getAccountNumber(), addSum, account.getBalance());
		account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
		Serialization serialization = new Serialization();
		serialization.serializationAccount(account);
		return account = serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
	}
// ����� ��� �������� �����	
	public void closeAccount(Account account) {
		do {		
			boolean forExit = false;
			if (account.getBalance() > 0) {
				System.out.printf("�� ����� ����� ��� ������� �������� � ������� %.2f ���.\n", account.getBalance());
				System.out.println("�������� ���������� ��������:");
				System.out.println("1. ����� ��������");
				System.out.println("2. ������� ���� �� ������ �� ��������� ��������");
				System.out.println("3. ����� � ���������� ����");
				try {
					switch (extracted().nextInt()) {
						case 1:
							cashWithdrawal(account);
							forExit = true;
							break;
						case 2:		
							forExit = true;
							break;
						case 3:
							return;
						default:
							System.out.println("������������ ���� ������.");
					}
				} catch (Exception e) {
					System.out.println("������������ ���� ������."); 
				}	
			} else {
				break;
			}	
			if (forExit) {
				break;
			}
		} while (true);
        Date date = new Date();
        Serialization serialization = new Serialization();
        User user = new User();
        user = serialization.deserializationUsers(account.getLogin());
		String addHistoryAccount = String.format("\n%te.%<tm.%<tY �. %<tR - �������� ����� � %05d "
				+ ", ������ ����� ���������� %.2f ���.\n���� ����������� ������������: %s %s %s, �����: %s", date, 
				account.getAccountNumber(), account.getBalance(), user.getSurame(), user.getName(), 
				user.getPatronymic(), user.getLogin());
		System.out.printf("C��� � %05d, ������ ����� %.2f ���., ������.\n", 
				account.getAccountNumber(), account.getBalance());
		account.setLogin("CLOSE_ACCOUNT");
		account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
		serialization.serializationAccount(account);
		serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
	}
	
// ����� ������� ������ ������
		public void listAccounts() {
			Serialization serialization = new Serialization();
	        File folderAcc = new File("accounts");
	        File folderUsr = new File("users");
	        if (folderAcc.exists()){
	            for (File fileAcc : folderAcc.listFiles()) {
	        		Account account = serialization.deserializationAccount(fileAcc.getName());     		
	        		System.out.printf("� %05d", account.getAccountNumber());
	        		User user = new User();
	        		for (File fileUsr : folderUsr.listFiles()) {
	        			user = serialization.deserializationUsers(fileUsr.getName().substring(0, 
        						(fileUsr.getName().length()-4)));
	        			if  (user.getLogin().equals(account.getLogin())) 	        				
	        				break;	        			
	        		}	      
	        		if (account.getLogin().equalsIgnoreCase("CLOSE_ACCOUNT")) {
	        			System.out.printf(" ����������� ������������: %s %s %s (login: %s). ���� ������.\n", 
		            			user.getSurame(), user.getName(), user.getPatronymic(), user.getLogin());
	        		} else {	        			
	        			System.out.printf(" ����������� ������������: %s %s %s (�����: %s).", 
	            			user.getSurame(), user.getName(), user.getPatronymic(), account.getLogin());
	        			if (account.getFrozenAccount()) {
	        				System.out.println(" ���� ���������.");
	        			} else {
	        				System.out.println("");
	        			}
	        		}
	            }
	        } 
		}
		
// ����� ��� ��������� �����
	public void frozenAccount(Account account) {
		if (account.getLogin().equals("CLOSE_ACCOUNT")) {
			System.out.println("������ ���� ������ ����������. ���� ��� ������.");
			return;
		} else if (account.getFrozenAccount() == true){
			System.out.println("������ ���� ������ ����������. ���� ��� ���������.");
			return;
		} else {
			account.setFrozenAccount(true);
			System.out.printf("���� � %05d ���������!\n", account.getAccountNumber());
			Date date = new Date();
		    Serialization serialization = new Serialization();
		    String addHistoryAccount = String.format("\n%te.%<tm.%<tY �. %<tR - ���� � %05d "
						+ ", ���������. �� ���������� ������ ��������� ������ � ������� ��������.", date, 
						account.getAccountNumber());			
			account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
			serialization.serializationAccount(account);
			serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
		}
	}
	
	// ����� ��� ���������� �����
		public void outFrozenAccount(Account account) {
			if (account.getLogin().equals("CLOSE_ACCOUNT")) {
				System.out.println("������ ���� ������ �����������. ���� ��� ������.");
				return;
			} else if (!account.getFrozenAccount() == true){
				System.out.println("������ ���� ������ �����������. ���� �� ���������.");
				return;
			} else {
				account.setFrozenAccount(false);
				System.out.printf("���� � %05d ����������!\n", account.getAccountNumber());
				Date date = new Date();
			    Serialization serialization = new Serialization();
			    String addHistoryAccount = String.format("\n%te.%<tm.%<tY �. %<tR - ���� � %05d "
							+ ", ����������. ������ ����� ��������� ������ � ������� ��������.", date, 
							account.getAccountNumber());			
				account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
				serialization.serializationAccount(account);
				serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
			}
		}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	void setAccountNumber(int a) {
		accountNumber = a;
	}	
	
	void setLogin(String a) {
		login = a;
	}	

	public String getLogin() {
		return login;
	}	
		
	void setBalance(double a) {
		balance = a;
	}
	
	public double getBalance() {
		return balance;
	}
	
	void setHistoryAccount(String a) {
		historyAccount = a;
	}
	
	public String getHistoryAccount() {
		return historyAccount;
	}
	
	void setFrozenAccount(boolean a) {
		frozenAccount = a;
	}
	
	public boolean getFrozenAccount() {
		return frozenAccount;
	}
			
	private Scanner extracted() {
		return new Scanner(System.in);
	}
}

