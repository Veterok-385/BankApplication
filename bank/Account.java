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

// Метод для открытия счета		
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
        		("%te.%<tm.%<tY г. %<tR - Открытие нового счета № %05d, сумма на счете составляет %.2f руб.", date, accountNumber, balance);
        account.balance = 0;
        account.setFrozenAccount(false);
        System.out.printf(user.getName() + " " + user.getPatronymic() + ", для Вас открыт новый счет в нашем банке. "
        		+ "Номер счета № %05d, сумма на Вашем счете составляет %.2f руб. \n", accountNumber, balance);
		System.out.println("нажмите клавишу \"Enter\"");
		extracted().nextLine();
// Сериализация объекта  Account
        Serialization serialization = new Serialization();
        serialization.serializationAccount(account);
		return account = serialization.deserializationAccount(account.getAccountNumber() + ".acc");
	}
// 	Метод проверяет, есть ли у клиента хоть один счет, возвращает true, если есть
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
// 	Метод выводит номера счетов, принадлежащих клиенту
	public void ownershipAccount(User user) {
		Serialization serialization = new Serialization();
        File folder = new File("accounts");
        if (folder.exists()){
            for (File file : folder.listFiles()) {
        		Account account = serialization.deserializationAccount(file.getName());
            	if (user.getLogin().equals(account.getLogin())) {
            		System.out.printf("№ %05d", account.getAccountNumber());
            		if (account.getFrozenAccount() == true) {
            			System.out.println(" - ЗАМОРОЖЕН.");
            		}
            		else {
            			System.out.println(".");
            		}
            	}
            }
        }
	}
// Метод проверяет, принадлежит ли счет клиенту и возвращает объект класса Account (если не принадлежит то возвращает null)
	public Account checkAccount(int accountNumber, User user) {		
		Account account = new Account();
		Serialization serialization = new Serialization();
		File file = new File("accounts", accountNumber + ".acc");
		try {
			if (!file.exists()) {
				System.out.println("Данный счет Вам не принадлежит."); // На самом деле 
																			//"Счета с таким номером не существует."
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
		System.out.println("Данный счет Вам не принадлежит.");
		return account = null;
	}
// Метод для пополнения баланса
	public Account depositBalance(Account account) {
		double addSum = 0;
		if (account.getFrozenAccount() == false) {
			do {
				try {
					System.out.println("Для пополнения баланса введите сумму (в руб.):\t\t(\"0\" - для выхода в предыдущее меню).");			
					addSum = extracted().nextDouble();		
					if (addSum == 0) {
						return account;					
					}
					else if (addSum < 0) {					
						System.out.println("Вводимая сумма должна быть положительной.");	
					}
					else {	
						addSum = (Math.round(addSum*100))*0.01;
						account.setBalance(account.getBalance() + addSum);
						break;
					}
				} catch (Exception e) {
					System.out.println("Некорректный ввод данных."); 
				}
			} while (true);
		} else {
			System.out.println("Вы не можете выполнить данную операцию.\nСЧЕТ ЗАМОРОЖЕН!");
			return account;
		}
        Date date = new Date();
		String addHistoryAccount = String.format("\n%te.%<tm.%<tY г. %<tR - Пополнение баланса счета № %05d "
				+ "на сумму %s руб., баланс счета составляет %.2f руб.", date, account.getAccountNumber(),
				addSum, account.getBalance());
		System.out.printf("Вы пополнили счет № %05d, на сумму %.2f руб., баланс счета составляет %.2f руб.\n", 
				account.getAccountNumber(), addSum, account.getBalance());
		account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
		Serialization serialization = new Serialization();
		serialization.serializationAccount(account);
		return account = serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
	}
// Метод для снятия наличных
	public Account cashWithdrawal(Account account) {
		double addSum = 0;
		if (account.getFrozenAccount() == false) {
			do {
				try {
					System.out.println("Введите сумму для снятия наличных (в руб.):\t\t(\"0\" - для выхода в предыдущее меню).");			
					addSum = extracted().nextDouble();			
					if (addSum == 0) {
						return account;					
					}
					else if (addSum < 0) {
						System.out.println("Вы не можете снять отрицательную сумму.");					
					} else {
						if (account.getBalance() < addSum) {
							System.out.println("На вашем счете недостаточно средств для вывода данной суммы.");						
						} else {
							addSum = (Math.round(addSum*100))*0.01;
							account.setBalance(account.getBalance() - addSum);
						break;
						}					
					}
				} catch (Exception e) {
					System.out.println("Некорректный ввод данных."); 
				}
			} while (true);
		} else {
			System.out.println("Вы не можете выполнить данную операцию.\nСЧЕТ ЗАМОРОЖЕН!");
			return account;
		}
        Date date = new Date();
		String addHistoryAccount = String.format("\n%te.%<tm.%<tY г. %<tR - Снятие наличных со счета № %05d "
				+ "на сумму %.2f руб., баланс счета составляет %.2f руб.", date, account.getAccountNumber(),
				addSum, account.getBalance());
		System.out.printf("Вы сняли наличные со счета № %05d, на сумму %.2f руб., баланс счета составляет %.2f руб.\n", 
				account.getAccountNumber(), addSum, account.getBalance());
		account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
		Serialization serialization = new Serialization();
		serialization.serializationAccount(account);
		return account = serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
	}
// Метод для закрытия счета	
	public void closeAccount(Account account) {
		do {		
			boolean forExit = false;
			if (account.getBalance() > 0) {
				System.out.printf("На Вашем счете еще имеются средства в размере %.2f руб.\n", account.getBalance());
				System.out.println("Выберите дальнейшие действия:");
				System.out.println("1. Снять наличные");
				System.out.println("2. Закрыть счет не смотря на имеющиеся средства");
				System.out.println("3. Выход в предыдущее меню");
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
							System.out.println("Некорректный ввод данных.");
					}
				} catch (Exception e) {
					System.out.println("Некорректный ввод данных."); 
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
		String addHistoryAccount = String.format("\n%te.%<tm.%<tY г. %<tR - Закрытие счета № %05d "
				+ ", баланс счета составляет %.2f руб.\nСчет принадлежал пользователю: %s %s %s, логин: %s", date, 
				account.getAccountNumber(), account.getBalance(), user.getSurame(), user.getName(), 
				user.getPatronymic(), user.getLogin());
		System.out.printf("Cчет № %05d, баланс счета %.2f руб., закрыт.\n", 
				account.getAccountNumber(), account.getBalance());
		account.setLogin("CLOSE_ACCOUNT");
		account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
		serialization.serializationAccount(account);
		serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
	}
	
// Метод выводит список счетов
		public void listAccounts() {
			Serialization serialization = new Serialization();
	        File folderAcc = new File("accounts");
	        File folderUsr = new File("users");
	        if (folderAcc.exists()){
	            for (File fileAcc : folderAcc.listFiles()) {
	        		Account account = serialization.deserializationAccount(fileAcc.getName());     		
	        		System.out.printf("№ %05d", account.getAccountNumber());
	        		User user = new User();
	        		for (File fileUsr : folderUsr.listFiles()) {
	        			user = serialization.deserializationUsers(fileUsr.getName().substring(0, 
        						(fileUsr.getName().length()-4)));
	        			if  (user.getLogin().equals(account.getLogin())) 	        				
	        				break;	        			
	        		}	      
	        		if (account.getLogin().equalsIgnoreCase("CLOSE_ACCOUNT")) {
	        			System.out.printf(" принадлежал пользователю: %s %s %s (login: %s). СЧЕТ ЗАКРЫТ.\n", 
		            			user.getSurame(), user.getName(), user.getPatronymic(), user.getLogin());
	        		} else {	        			
	        			System.out.printf(" принадлежит пользователю: %s %s %s (Логин: %s).", 
	            			user.getSurame(), user.getName(), user.getPatronymic(), account.getLogin());
	        			if (account.getFrozenAccount()) {
	        				System.out.println(" СЧЕТ ЗАМОРОЖЕН.");
	        			} else {
	        				System.out.println("");
	        			}
	        		}
	            }
	        } 
		}
		
// Метод для заморозки счета
	public void frozenAccount(Account account) {
		if (account.getLogin().equals("CLOSE_ACCOUNT")) {
			System.out.println("Данный счет нельзя заморозить. Счет уже закрыт.");
			return;
		} else if (account.getFrozenAccount() == true){
			System.out.println("Данный счет нельзя заморозить. Счет уже заморожен.");
			return;
		} else {
			account.setFrozenAccount(true);
			System.out.printf("СЧЕТ № %05d ЗАМОРОЖЕН!\n", account.getAccountNumber());
			Date date = new Date();
		    Serialization serialization = new Serialization();
		    String addHistoryAccount = String.format("\n%te.%<tm.%<tY г. %<tR - Счет № %05d "
						+ ", заморожен. До разморозки нельзя пополнять баланс и снимать наличные.", date, 
						account.getAccountNumber());			
			account.setHistoryAccount(account.getHistoryAccount() + addHistoryAccount);
			serialization.serializationAccount(account);
			serialization.deserializationAccount(account.getAccountNumber() + ".acc");		
		}
	}
	
	// Метод для разморозки счета
		public void outFrozenAccount(Account account) {
			if (account.getLogin().equals("CLOSE_ACCOUNT")) {
				System.out.println("Данный счет нельзя разморозить. Счет уже закрыт.");
				return;
			} else if (!account.getFrozenAccount() == true){
				System.out.println("Данный счет нельзя разморозить. Счет не заморожен.");
				return;
			} else {
				account.setFrozenAccount(false);
				System.out.printf("СЧЕТ № %05d РАЗМОРОЖЕН!\n", account.getAccountNumber());
				Date date = new Date();
			    Serialization serialization = new Serialization();
			    String addHistoryAccount = String.format("\n%te.%<tm.%<tY г. %<tR - Счет № %05d "
							+ ", разморожен. Теперь можно пополнять баланс и снимать наличные.", date, 
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

