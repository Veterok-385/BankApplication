package client;

import java.util.Scanner;
import bank.Account;
import bank.User;

public class Client {
	
	public void controllerClient (User user) {
		System.out.println("Мы рады приветствовать Вас в нашем банке, " + user.getName() + " " 
				+ user.getPatronymic() + ".");
		
		Account account = new Account();
		do {
			if(!account.isOwnershipAccount(user)) {         // нет открытых счетов у пользователя
				System.out.println("У Вас пока нет открытых счетов в нашем банке.");
				System.out.println("Выберите дальнейшие действия:");
				System.out.println("1. Открыть счет");
				System.out.println("2. Выход в предыдущее меню");
				System.out.println("3. Выйти из программы");
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
						System.out.println("Работа приложения завершена.");
						System.exit(0);
					default:
						System.out.println("Некорректный ввод данных.");   	
					}	
				} catch (Exception e) {
					System.out.println("Некорректный ввод данных."); 
				}
				if (forExit) {
					break;
				}
			} else {
				System.out.println("У Вас имеются открытые счета в нашем банке.");
				System.out.println("Выберите дальнейшие действия:");
				System.out.println("1. Просмотреть открытые счета");
				System.out.println("2. Открыть новый счет");
				System.out.println("3. Выход в предыдущее меню");
				System.out.println("4. Выйти из программы");
				boolean forExit = false;
					//try {
					switch (extracted().nextInt()) {
					case 1:
						System.out.println("На Ваше имя открыты следующие счета:");
						account.ownershipAccount(user);
						do {
							System.out.println("Выберите счет для просмотра (\"0\" - для выхода в предыдущее меню).");
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
								System.out.println("Некорректный ввод данных.");
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
						System.out.println("Работа приложения завершена.");
						System.exit(0);
					default:
						System.out.println("Некорректный ввод данных.");   	
					}	
				if (forExit) {
					break;
				}
			}
		} while (true);		
	}
	
	private void workWithAccount(Account account) {		
		do {
			System.out.printf("Счет №:\t%05d.\n", account.getAccountNumber());
			if (account.getFrozenAccount()) {
				System.out.println("СЧЕТ ЗАМОРОЖЕН!");
			}
			System.out.printf("Баланс:\t%.2f руб.\n", account.getBalance());
			System.out.println("Выберите дальнейшие действия:");
			System.out.println("1. Пополнить баланс");
			System.out.println("2. Вывести наличные");
			System.out.println("3. Посмотреть историю счета");
			System.out.println("4. Закрыть счет");				
			System.out.println("5. Выход в предыдущее меню");
			System.out.println("6. Выйти из программы");
			boolean forExit = false;
			try {
				switch (extracted().nextInt()) {
				case 1:
					account = account.depositBalance(account);
					System.out.println("нажмите клавишу \"Enter\"");
					extracted().nextLine();
					break;					
				case 2:
					account.cashWithdrawal(account);
					System.out.println("нажмите клавишу \"Enter\"");
					extracted().nextLine();
					break;
				case 3:
					System.out.println(account.getHistoryAccount());
					System.out.println("нажмите клавишу \"Enter\"");
					extracted().nextLine();
					break;
				case 4:
					account.closeAccount(account);
					return;				
				case 5:
					forExit = true;
					break;
				case 6:
					System.out.println("Работа приложения завершена.");
					System.exit(0);
				default:
					System.out.println("Некорректный ввод данных.");					
				}
			} catch (Exception e) {
				System.out.println("Некорректный ввод данных.");
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
