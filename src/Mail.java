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
	private final String username = "senderID@naver.com"; // ���̹� ���̵� �Է����ּ���. @nave.com�� �Է����� ���ñ���.
	private final String password = "mailPW"; // ���̹� �̸��� ��й�ȣ�� �Է����ּ���.

	public static Mail getInstance() {

		if (instance == null) {
			instance = new Mail();
		}
		return instance;
	}

	public Mail() {
		String host = "smtp.naver.com";
		int port = 465; // smtp ��Ʈ��ȣ

		Properties props = System.getProperties(); // ������ ��� ���� ��ü ���� // SMTP ���� ���� ����
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		// �� ȯ�� ������ "���� ����"�� ����� �� �޽����� �����.
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		session.setDebug(true); // for debug logging
		msg = new MimeMessage(session);
	}

	public void sendMail() {
		// ���� ȯ�� ������ �����ؾ� �Ѵ�.
		// ���� ���� �ּҸ� IP �Ǵ� ������ ������ �����Ѵ�.
		try {
			// �߽���, ������, ������, ����, ���� ���� ���� �����Ѵ�
			msg.setFrom(new InternetAddress(username, "sendername"));//�߽��� ���� �� �̸�
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("user1@naver.com", "user1"));//������ ���� �� �̸�
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("user2@gmail.com", "user2"));
			msg.setSubject("���� �κ� �Դϴ�.");
			msg.setText("���� �κ��Դϴ�.");

			
			//���ϵ� ���� ����
			msg.setFileName(MimeUtility.encodeText("�׸�.png"));
			DataSource source = new FileDataSource("filepath+filename.png");

			msg.setDataHandler(new DataHandler(source));

			
			// ������ �߼�
			Transport.send(msg);
			System.out.println("���� �߼�!");
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("err!");
		}
	}
}
