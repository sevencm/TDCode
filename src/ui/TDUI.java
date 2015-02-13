package ui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import core.Matrix2ImageWriter;

public class TDUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPanel panelTemp = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TDUI frame = new TDUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TDUI() {
		setDefaultCloseOperation(3);
		setBounds(100, 100, 450, 500);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		JLabel label = new JLabel("内容/网址");
		label.setBounds(12, 32, 61, 17);
		this.contentPane.add(label);

		this.textField = new JTextField();
		this.textField.setBounds(84, 30, 334, 21);
		this.contentPane.add(this.textField);
		this.textField.setColumns(10);

		JButton btnNewButton = new JButton("生成二维码");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = TDUI.this.textField.getText();
				System.out.println(content);

				MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
				Map hints = new HashMap();
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				BitMatrix bitMatrix = null;
				try {
					bitMatrix = multiFormatWriter.encode(content,
							BarcodeFormat.QR_CODE, 400, 400, hints);
				} catch (WriterException e1) {
					e1.printStackTrace();
				}
				BufferedImage image = Matrix2ImageWriter
						.toBufferedImage(bitMatrix);
				System.out.println(image);
				if (TDUI.this.panelTemp != null) {
					TDUI.this.contentPane.remove(TDUI.this.panelTemp);
				}
				JPanel panel = new MyPanel(image);
				TDUI.this.panelTemp = panel;
				panel.setBounds(26, 75, 426, 475);
				TDUI.this.contentPane.add(panel);
				TDUI.this.contentPane.invalidate();
				TDUI.this.contentPane.repaint();
			}
		});
		btnNewButton.setBounds(311, 63, 107, 27);
		this.contentPane.add(btnNewButton);
	}

	class MyPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		Image image = null;

		public void paint(Graphics g) {
			try {
				g.drawImage(this.image, 0, 0, 400, 400, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public MyPanel(Image i) {
			setOpaque(false);
			this.image = i;
		}
	}
}
