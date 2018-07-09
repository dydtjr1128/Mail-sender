import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class Mail {
	private static MimeMessage msg;
	private static Mail instance = null;
	private final String username = "senderID@naver.com"; // 네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
	private final String password = "mailPW"; // 네이버 이메일 비밀번호를 입력해주세요.

	public static Mail getInstance() {

		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}

	public Mail() {
		String host = "smtp.naver.com";
		int port = 465; // smtp 포트번호

		Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성 // SMTP 서버 정보 설정
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		// 위 환경 정보로 "메일 세션"을 만들고 빈 메시지를 만든다.
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		session.setDebug(true); // for debug logging
		msg = new MimeMessage(session);
	}

	public void sendMail() {
		// 먼저 환경 정보를 설정해야 한다.
		// 메일 서버 주소를 IP 또는 도메인 명으로 지정한다.
		try {
			// 발신자, 수신자, 참조자, 제목, 본문 내용 등을 설정한다
			msg.setFrom(new InternetAddress(username, "sendername"));//발신자 메일 및 이름
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("user1@naver.com", "user1"));//수신자 메일 및 이름
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("user2@gmail.com", "user2"));
			msg.setSubject("제목 부분 입니다.");
			msg.setText("본문 부분입니다.");

			
			//파일도 같이 전송
			msg.setFileName(MimeUtility.encodeText("그림.png"));
			DataSource source = new FileDataSource("filepath+filename.png");

			msg.setDataHandler(new DataHandler(source));

			
			// 메일을 발송
			Transport.send(msg);
			System.out.println("메일 발송!");
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("err!");
		}
	}
}
