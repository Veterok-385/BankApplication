package administrator;

import java.util.Scanner;

//import java.io.File;

import bank.Account;
import bank.Serialization;
import bank.User;
import client.Client;

public class Administrator {
	
	public void controllerAdministrator (User user) {
		System.out.println(user.getName() + " " + user.getPatronymic() + ", Вы являетесь управляющим нашего банка.");
		do {
			System.out.println("Выберите дальнейшие действия:");
			System.out.println("1. Просмотреть список пользователей");
			System.out.println("2. Просмотреть список счетов");
			System.out.println("3. Выйти в предыдущее меню");
			System.out.println("4. Зарегистрировать новую учетную запись администратора");
			System.out.println("5. Войти в качестве клиента");
			System.out.println("6. Просмотреть свои учетные данные");
			System.out.println("7. Выход из программы");
			try {
				switch (extracted().nextInt()) {
				case 1:	
					user.listUsers();
					do {
						try {						
							System.out.println("Введите логин, данные которого хотите посмотреть (\"0\" - для выхода в предыдущее меню).");
							String inputLogin = extracted().nextLine();
							if (inputLogin.equals("0")) {								
								break;
							}
							Serialization serialization = new Serialization();
							User userClient = serialization.deserializationUsers(inputLogin);
							if (userClient.getYearOfBirth()==0) {
								System.out.println("Некорректный ввод данных.");
							} else {
								System.out.printf("Фамилия: %s\nИмя: %s\nОтчество: %s\nГод рождения: %d\nМесто работы: %s\n"
										+ "(нажмите клавишу \"Enter\")", userClient.getSurame(), userClient.getName(), userClient.getPatronymic(), 
										userClient.getYearOfBirth(), userClient.getPlaceOfWork());
								extracted().nextLine();
							}
						} catch(Exception e) {
							System.out.println("Некорректный ввод данных.");
						}
					} while (true);
					break;			
				case 2:	
					Account account = new Account();
					account.listAccounts();
					do {
						System.out.println("Введите номер счета для дальнейшей работы(\"0\" - для выхода в предыдущее меню).");
						Serialization serialization = new Serialization();
						try {
							int inputNumberAccount = extracted().nextInt();
							if (inputNumberAccount == 0) {
								break;
							} else {
								workWithAccount(serialization.deserializationAccount(inputNumberAccount + ".acc"));
							}	
						} catch (Exception e) {
							System.out.println("Некорректный ввод данных.");
						}
					} while (true);
					break;				
				case 3:
					return;				
				case 4:
					System.out.println("Зарегистрируйтесь в качестве администратора.");
					user = user.registrationUser(true);
					break;
				case 5:
					Client client = new Client();
					client.controllerClient(user);
					break;
				case 6:
					System.out.printf("Фамилия: %s\nИмя: %s\nОтчество: %s\nГод рождения: %d\nМесто работы: %s\n"
							+ "(нажмите клавишу \"Enter\")", user.getSurame(), user.getName(), user.getPatronymic(), 
							user.getYearOfBirth(), user.getPlaceOfWork());
					extracted().nextLine();
					break;
				case 7:
					System.out.println("Работа приложения завершена.");
					System.exit(0);
				default:
					System.out.println("Некорректный ввод данных.");					
				}
			} catch (Exception e) {
				System.out.println("Некорректный ввод данных.");
			}
		} while(true);
	}	
	
	private void workWithAccount(Account account) {		
		do {
			System.out.printf("Счет №:\t%05d.\n", account.getAccountNumber());
			if (account.getLogin().equals("CLOSE_ACCOUNT")) {
				System.out.println("СЧЕТ ЗАКРЫТ!");
			}
			System.out.printf("Баланс:\t%.2f руб.\n", account.getBalance());
			System.out.println("Выберите дальнейшие действия:");
			System.out.println("1. Посмотреть данные владельца");
			System.out.println("2. Заморозить счет");
			System.out.println("3. Разморозить счет");
			System.out.println("4. Посмотреть историю счета");
			System.out.println("5. Выход в предыдущее меню");
			System.out.println("6. Выйти из программы");
			boolean forExit = false;
			try {
				switch (extracted().nextInt()) {
				case 1:
					if (account.getLogin().equals("CLOSE_ACCOUNT")) {
						System.out.println("CЧЕТ ЗАКРЫТ! Владельца нет.");
						System.out.println("нажмите клавишу \"Enter\"");
						extracted().nextLine();
						break;
					}
					User userClient = new User();
					Serialization serialization = new Serialization(); 
					userClient = serialization.deserializationUsers(account.getLogin());
					System.out.printf("Фамилия: %s\nИмя: %s\nОтчество: %s\nГод рождения: %d\nМесто работы: %s\n"
							+ "(нажмите клавишу \"Enter\")", userClient.getSurame(), userClient.getName(), userClient.getPatronymic(), 
							userClient.getYearOfBirth(), userClient.getPlaceOfWork());
					extracted().nextLine();
					break;					
				case 2:
					account.frozenAccount(account);
					System.out.println("нажмите клавишу \"Enter\"");
					extracted().nextLine();
					break;
				case 3:
					account.outFrozenAccount(account);
					System.out.println("нажмите клавишу \"Enter\"");
					extracted().nextLine();
					break;					
				case 4:
					System.out.println(account.getHistoryAccount());
					System.out.println("нажмите клавишу \"Enter\"");
					extracted().nextLine();
					break;			
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