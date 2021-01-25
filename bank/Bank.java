package bank;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import administrator.Administrator;
import client.Client;

public class Bank {

	public static void main(String[] args) throws IOException {
// Обязательное задание по дисциплине "ООП" ЧОУ ВО "Курский институт менеджмента, экономики и бизнеса"
//  задание № 5 - разработайте приложение, имитирующее работу банка		
		System.out.println("Добро пожаловать в приложение, имитирующее работу банка.");
		
		File checkExistFileVerificationDat = new File("verification.dat");
		User user = new User();
// по наличию файла "verification.dat" программа судит, запускалась ли программа ранее
		boolean firstRunProgram = false;
		if (!checkExistFileVerificationDat.exists()) {
			System.out.println("Похоже, программа запущена впервые.");
			firstRunProgram = true;
		}
		String login;
		Serialization serialization = new Serialization();
		do {
			if (firstRunProgram) {
				System.out.println("Зарегистрируйтесь в качестве администратора.");
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
				administrator.controllerAdministrator(user);	//*************передача управления в метод controllerAdministrator	
				user = null;
			}			
			boolean noCorrectInput = true;	
			String message = "Пользователя с такими учетными данными не существует.\nВыберите дальнейшие действия:";
			do {
				if (login.equals("UserIsNoVerification")) {
					System.out.println(message);
					System.out.println("1. Зарегистрировать нового пользователя");
					System.out.println("2. Войти под другими учетными данными");			
					System.out.println("3. Выйти из программы");
					try {
						switch (extracted().nextInt()) {
						case 1:
							System.out.println("Регистрация нового пользователя:");
							user = user.registrationUser(false);
							login = "";
							break;
		            	
						case 2:
							user = null;
							noCorrectInput = false;
							break;
						case 3:
							System.out.println("Работа приложения завершена.");
							System.exit(0);
						default:
							System.out.println("Некорректный ввод.");
							message = "Выберите дальнейшие действия:";
							break;						
						}	
					} catch (Exception e) {
						System.out.println("Некорректный ввод.");
						message = "Выберите дальнейшие действия:";
					}
				} else {
					noCorrectInput = false;
				}
			} while (noCorrectInput);
			Client client = new Client();
			if (!(user == null)) {
				client.controllerClient(user);//*************передача управления в метод controllerClient
			} 
		} while (true);	
	}
// вспомогательный метод работы с объектом класса Scanner
	private static Scanner extracted() {
		return new Scanner(System.in);
	}
}
