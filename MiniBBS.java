package chap15;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MiniBBS {
	// FILEに掲示板を作りたいディレクトリを指定してください
	final static String FILE = "/Library/MiniBBS/minibbs.txt";

	// メインメソッド
	public static void main(String[] args) {
		runBBS();
	}

	// 氏名を取得し、返すメソッド
	private static String getName() {
		Scanner stdin = new Scanner(System.in);
		while (true) {
			System.out.println("氏名を入力してください");
			System.out.print(">>");
			String name = stdin.nextLine();
			if (name.contains(",")) {
				System.out.println("氏名に「,」は入れないでください");
				continue;
			}
			if (name.equals("")) {
				return "匿名";
			} else {
				return name;
			}
		}
	}

	// メッセージを取得し、返すメソッド
	private static String getMessage() {
		Scanner stdin = new Scanner(System.in);
		while (true) {
			System.out.println("メッセージを書き込んでください");
			System.out.print(">>");
			String message = stdin.nextLine();
			if (message.contains(",")) {
				System.out.println("メッセージに「,」を入れないでください");
				continue;
			}
			if (message.equals("")) {
				System.out.println("メッセージが書きこまれていません。");
				System.out.println("最低でも一文字は書き込んでください");
				continue;
			} else {
				return message;
			}
		}
	}

	// メッセージを書き込んだ時点の日時を取得し、返すメソッド
	private static String getDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	// 書き込んだメッセージを指定したディレクトリにファイルとして保存するメソッド
	private static void saveMessage(String name, String dateTime, String message) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
			bw.write(name + "," + dateTime + "," + message.length() + "," + message);
			bw.newLine();
			System.out.println(name + "さん、メッセージを書き込みました");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 指定したディレクトリのファイルからメッセージを読み込み、新しい書き込みから表示するメソッド
	private static void showMessage() {
		try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
			List<String> array = new ArrayList<>();
			String s;
			while ((s = br.readLine()) != null) {
				array.add(s);
			}
			Collections.reverse(array);
			for (int i = 0; i < array.size(); i++) {
				String[] message = array.get(i).split(",");
				System.out.println("氏名：" + message[0] + "　登録日：" + message[1]);
				System.out.println("-----------------------------------------------------------------");
				System.out.println("(" + message[2] + ")" + message[3]);
				System.out.println("-----------------------------------------------------------------");
			}
		} catch (FileNotFoundException e) {
			System.out.println("まだ何も書き込みがされていません。");
			System.out.println("書き込みを行ってから読み込みを行ってください");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// メニューを表示させ、掲示板を動かすメソッド
	private static void runBBS() {
		Scanner stdin = new Scanner(System.in);
		System.out.println("簡易掲示板へようこそ。");
		while (true) {
			System.out.println("menu：0.終了　1.書き込み　2.読み込み");
			System.out.print(">>");
			String menu = stdin.nextLine();
			if (menu.equals("1")) {
				String name = getName();
				String dateTime = getDateTime();
				String message = getMessage();
				saveMessage(name, dateTime, message);
			} else if (menu.equals("2")) {
				showMessage();
			} else if (menu.equals("0")) {
				System.out.println("簡易掲示板を閉じます。");
				break;
			} else {
				System.out.println("無効な入力です。半角で0～2を入力してください。");
			}
		}
	}
}
