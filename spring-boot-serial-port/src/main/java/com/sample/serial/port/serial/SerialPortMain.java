package com.sample.serial.port.serial;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.sample.serial.port.util.Convert;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 注意：
 * VM arguments 添加
 * -Dfile.encoding=gbk
 */
@SuppressWarnings("restriction")
public class SerialPortMain extends JFrame implements ActionListener,SerialPortEventListener{

	private static final long serialVersionUID = -3478807170545926241L;
	private String headStr = "41 59 A9 6E 83 8E 4D F5 BD EC D4 E2 D8 E9 40 F1"
			.replaceAll(" ", "");
	private String cdStr = "2E";
	//04
	private String cdStr2 = "04";
	private String set_orderStr = "00", read_orderStr = "01";

	private JFrame frmGprs;
	private JTextField txtCmnet;
	private JTextField ipTf;
	private JTextField mubiaoportTf;
	private JTextField xintiaotimeTf;
	private JTextField xintiaobaoTf;
	private JTextField zhucebaoTf;
	private JCheckBox hex1, hex2;
	private JComboBox<Object> portCombox, rateCombox, dataCombox, stopCombox,
			parityCombox, netprotocolCombo, apncomBox, gongzuomoshiCombox;
	// TODO
	private Button openPortBtn, closePortBtn, sendMsgBtn, backBtn,
			readallBtn, setallBtn, atentmBtn, plusBtn, showallBtn,
			cleanCountBtn, cleanReadBtn, cleanSendBtn;
	private TextArea sendTa;
	private TextArea readTa;
	private JLabel statusLb;
	private String portname, rate, data, stop, parity;
	protected int sendCount, reciveCount;
	protected String mesg;
	protected InputStream inputStream = null;
	protected OutputStream outputStream = null;
	protected List<String> portList;
	protected SerialPort serialPort;
	protected Enumeration<?> ports;
	protected CommPortIdentifier portId;
	private String plusErr = "[提示]：命令执行失败，请排查以下情况："
			+ "\n"
			+ "① 当前在数据透传模式下且已进入+++A配置状态，回到通讯状态请点击 'AT+ENTM' "
			+ "\n"
			+ "② 当前在串口命令、HttpdClient或短信透传模式下且进入了通讯状态，这种情况+++A无效，需要重启设备，重启之后按提示在5秒内配置"
			+ "\n" + "③ 电源线或串口线接触不良" + "\n";
	private String atErr = "[提示]：命令执行失败，请排查以下情况：" + "\n"
			+ "① 当前已进入通讯状态，在数据透传模式下进入配置状态，请点击+++A" + "\n" + "② 电源线或串口线接触不良"
			+ "\n";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new SerialPortMain();
	}

	/**
	 * Create the application.
	 */
	public SerialPortMain() {

		frmGprs = new JFrame("串口通信");
//		frmGprs.setIconImage(Toolkit.getDefaultToolkit().getImage(
//				"E:\\Workspace\\EclipseWorkspace\\testgprs\\lib\\02.png"));
		frmGprs.setSize(748, 657);
		frmGprs.setResizable(false);
		frmGprs.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//frmGprs.setVisible(true);
		frmGprs.setLocation(390, 140);
		frmGprs.getContentPane().setLayout(null);
		scanPorts();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		/************************************************** JSeparator *****************************************************************/
		JSeparator separator1 = new JSeparator();
		separator1.setBounds(30, 31, 325, 1);
		separator1.setOrientation(JSeparator.HORIZONTAL);
		frmGprs.getContentPane().add(separator1);
		JSeparator separator3 = new JSeparator();
		separator3.setBounds(30, 530, 325, 1);
		separator1.setOrientation(JSeparator.HORIZONTAL);
		frmGprs.getContentPane().add(separator3);
		JSeparator separator2 = new JSeparator();
		separator2.setBounds(354, 31, 1, 500);
		separator2.setOrientation(JSeparator.VERTICAL);
		frmGprs.getContentPane().add(separator2);
		JSeparator separator4 = new JSeparator();
		separator4.setBounds(30, 31, 1, 500);
		separator4.setOrientation(JSeparator.VERTICAL);
		frmGprs.getContentPane().add(separator4);
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.HORIZONTAL);
		separator_2.setBounds(30, 211, 325, 1);
		frmGprs.getContentPane().add(separator_2);
		/************************************* RIGHT **********************************************************/
		JSeparator separator5 = new JSeparator();
		separator5.setOrientation(SwingConstants.VERTICAL);
		separator5.setBounds(386, 31, 1, 500);
		frmGprs.getContentPane().add(separator5);
		JSeparator separator7 = new JSeparator();
		separator7.setOrientation(SwingConstants.VERTICAL);
		separator7.setBounds(711, 31, 1, 500);
		frmGprs.getContentPane().add(separator7);
		JSeparator separator6 = new JSeparator();
		separator6.setOrientation(SwingConstants.HORIZONTAL);
		separator6.setBounds(386, 31, 325, 1);
		frmGprs.getContentPane().add(separator6);
		JSeparator separator8 = new JSeparator();
		separator8.setOrientation(SwingConstants.HORIZONTAL);
		separator8.setBounds(386, 531, 325, 1);
		frmGprs.getContentPane().add(separator8);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.HORIZONTAL);
		separator.setBounds(387, 352, 325, 1);
		frmGprs.getContentPane().add(separator);
		/************************************* MENU **********************************************************/
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 794, 21);
		frmGprs.getContentPane().add(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenu menu_1 = new JMenu("File");
		menuBar.add(menu_1);
		/************************************* LEFT **********************************************************/
		JLabel apnLb = new JLabel("APN帐号");
		apnLb.setBounds(48, 50, 61, 21);
		frmGprs.getContentPane().add(apnLb);
		apncomBox = new JComboBox<Object>(new String[] { "移动", "联通", "其它" });
		apncomBox.setBounds(151, 50, 69, 21);
		apncomBox.setSelectedIndex(0);
		apncomBox.addActionListener(this);
		frmGprs.getContentPane().add(apncomBox);
		txtCmnet = new JTextField();
		txtCmnet.setHorizontalAlignment(SwingConstants.CENTER);
		// TODO
		// txtCmnet.setText("CMNET");
		txtCmnet.setText(getApn());
		txtCmnet.setEditable(false);
		txtCmnet.setBounds(230, 50, 66, 21);
		frmGprs.getContentPane().add(txtCmnet);
		txtCmnet.setColumns(10);

		JLabel lblPortnum = new JLabel("串    口    号");
		lblPortnum.setBounds(48, 83, 96, 15);
		frmGprs.getContentPane().add(lblPortnum);
		portCombox = new JComboBox<Object>((String[]) portList.toArray(new String[0]));
		portCombox.setSelectedIndex(0);
		portCombox.setBounds(151, 80, 145, 21);
		portCombox.addActionListener(this);
		frmGprs.getContentPane().add(portCombox);
		JLabel boteLb = new JLabel("设备波特率");
		boteLb.setBounds(48, 108, 87, 21);
		frmGprs.getContentPane().add(boteLb);
		rateCombox = new JComboBox<Object>(new Integer[] { 2400, 4800, 9600, 14400,
				19200, 38400, 576000, 115200 });
		rateCombox.setBounds(151, 108, 145, 21);
		rateCombox.setSelectedIndex(7);
		rateCombox.addActionListener(this);
		frmGprs.getContentPane().add(rateCombox);

		JLabel sanLb = new JLabel("校验/数据/停止");
		sanLb.setBounds(48, 139, 87, 21);
		frmGprs.getContentPane().add(sanLb);
		parityCombox = new JComboBox<Object>(new String[] { "NONE", "ODD", "EVEN" });
		parityCombox.setBounds(151, 139, 61, 21);
		parityCombox.setSelectedIndex(0);
		parityCombox.addActionListener(this);
		frmGprs.getContentPane().add(parityCombox);
		dataCombox = new JComboBox<Object>(new Integer[] { 7, 8 });
		dataCombox.setBounds(221, 139, 40, 21);
		dataCombox.setSelectedIndex(1);
		dataCombox.addActionListener(this);
		frmGprs.getContentPane().add(dataCombox);
		stopCombox = new JComboBox<Object>(new String[] { "1", "2" });
		stopCombox.setBounds(261, 139, 35, 21);
		stopCombox.setSelectedIndex(0);
		stopCombox.addActionListener(this);
		frmGprs.getContentPane().add(stopCombox);

		JLabel ipLb = new JLabel("工作模式");
		ipLb.setBounds(48, 170, 87, 21);
		frmGprs.getContentPane().add(ipLb);
		gongzuomoshiCombox = new JComboBox<Object>(new String[] { "数据透传模式", "AT指令集模式",
				"串口命令模式", "Httpd Client模式", "短信透传模式" });
		gongzuomoshiCombox.setBounds(151, 170, 145, 21);
		gongzuomoshiCombox.setSelectedIndex(0);
		gongzuomoshiCombox.addActionListener(this);
		frmGprs.getContentPane().add(gongzuomoshiCombox);

		JLabel label_3 = new JLabel("目标IP或域名");
		label_3.setBounds(48, 232, 87, 21);
		frmGprs.getContentPane().add(label_3);
		ipTf = new JTextField();
		ipTf.setBounds(151, 232, 145, 21);
		ipTf.setText("192.168.0.254");
		frmGprs.getContentPane().add(ipTf);
		// textField.setColumns(10);

		JLabel netprotocolLb = new JLabel("网络协议");
		netprotocolLb.setBounds(48, 272, 87, 21);
		frmGprs.getContentPane().add(netprotocolLb);
		netprotocolCombo = new JComboBox<Object>(new String[] { "TCP", "UDP" });
		netprotocolCombo.setBounds(151, 272, 145, 21);
		// netprotocolCombo.setSelectedIndex(0);
		netprotocolCombo.addActionListener(this);
		frmGprs.getContentPane().add(netprotocolCombo);

		JLabel mubiaoportLb = new JLabel("目标端口");
		mubiaoportLb.setBounds(48, 312, 87, 21);
		frmGprs.getContentPane().add(mubiaoportLb);
		mubiaoportTf = new JTextField();
		// mubiaoportTf.setColumns(10);
		mubiaoportTf.setBounds(151, 312, 145, 21);
		mubiaoportTf.setText("10001");
		frmGprs.getContentPane().add(mubiaoportTf);

		JLabel xintiaotimeLb = new JLabel("心跳时间");
		xintiaotimeLb.setBounds(48, 352, 87, 21);
		frmGprs.getContentPane().add(xintiaotimeLb);
		xintiaotimeTf = new JTextField();
		// xintiaotimeTf.setColumns(10);
		xintiaotimeTf.setBounds(151, 352, 145, 21);
		xintiaotimeTf.setText("300000");
		frmGprs.getContentPane().add(xintiaotimeTf);
		JLabel lblSeconds = new JLabel("秒");
		lblSeconds.setBounds(306, 355, 31, 21);
		frmGprs.getContentPane().add(lblSeconds);

		JLabel xintiaobaoLb = new JLabel("心跳包格式");
		xintiaobaoLb.setBounds(48, 392, 87, 21);
		frmGprs.getContentPane().add(xintiaobaoLb);
		xintiaobaoTf = new JTextField();
		// xintiaobaoTf.setColumns(10);
		xintiaobaoTf.setBounds(151, 392, 145, 21);
		xintiaobaoTf.setText("LOG");
		frmGprs.getContentPane().add(xintiaobaoTf);
		hex1 = new JCheckBox("hex");
		hex1.setBounds(301, 391, 49, 23);
		hex1.addActionListener(this);
		frmGprs.getContentPane().add(hex1);

		JLabel zhucebaoLb = new JLabel("注册包格式");
		zhucebaoLb.setBounds(48, 432, 87, 21);
		frmGprs.getContentPane().add(zhucebaoLb);
		zhucebaoTf = new JTextField();
		zhucebaoTf.setColumns(10);
		zhucebaoTf.setBounds(151, 432, 145, 21);
		zhucebaoTf.setText("LOGIN:1001");
		frmGprs.getContentPane().add(zhucebaoTf);
		hex2 = new JCheckBox("hex");
		hex2.setBounds(301, 431, 49, 23);
		hex2.addActionListener(this);
		frmGprs.getContentPane().add(hex2);

		JLabel label_9 = new JLabel("信息提示区");
		label_9.setBounds(396, 50, 69, 21);
		frmGprs.getContentPane().add(label_9);
		readTa = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
		readTa.setEditable(true);
		readTa.setBounds(396, 81, 306, 260);
		frmGprs.getContentPane().add(readTa);

		JLabel label_10 = new JLabel("信息发送区");
		label_10.setBounds(397, 363, 68, 21);
		frmGprs.getContentPane().add(label_10);
		sendTa = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		// sendTa.setEnabled(false);
		// sendTa.setEditable(false);
		sendTa.setEnabled(true);
		sendTa.setEditable(true);
		sendTa.setBounds(395, 391, 306, 124);
		frmGprs.getContentPane().add(sendTa);

		/****************************************** 底部Button *********************************************/
		showallBtn = new Button("完整显示 ");
		showallBtn.setBounds(48, 487, 87, 30);
		showallBtn.addActionListener(this);
		showallBtn.setEnabled(false);
		frmGprs.getContentPane().add(showallBtn);
		/******************************************/
		plusBtn = new Button("+++ A");
		plusBtn.setBounds(145, 487, 87, 30);
		plusBtn.addActionListener(this);
		frmGprs.getContentPane().add(plusBtn);
		/******************************************/
		atentmBtn = new Button("AT + ENTM");
		atentmBtn.setBounds(242, 487, 87, 30);
		atentmBtn.addActionListener(this);
		frmGprs.getContentPane().add(atentmBtn);
		/******************************************/
		setallBtn = new Button("配置全部参数 ");
		setallBtn.setBounds(30, 566, 85, 30);
		setallBtn.addActionListener(this);
		frmGprs.getContentPane().add(setallBtn);
		/******************************************/
		readallBtn = new Button("读取设备参数");
		readallBtn.setBounds(132, 566, 85, 30);
		readallBtn.addActionListener(this);
		frmGprs.getContentPane().add(readallBtn);
		/******************************************/
		backBtn = new Button("恢复出厂配置");
		backBtn.setBounds(234, 566, 85, 30);
		backBtn.addActionListener(this);
		frmGprs.getContentPane().add(backBtn);
		/*****************************************
		 * restartBtn = new Button("重启设备"); restartBtn.setBounds(336, 566, 85,
		 * 30); restartBtn.addActionListener(this);
		 * frmGprs.getContentPane().add(restartBtn);
		 */
		cleanCountBtn = new Button("清空计数");
		cleanCountBtn.setBounds(336, 566, 85, 30);
		cleanCountBtn.addActionListener(this);
		frmGprs.getContentPane().add(cleanCountBtn);
		/*********************************************************************************************************/
		openPortBtn = new Button("打开端口");
		openPortBtn.setBounds(437, 566, 87, 30);
		openPortBtn.addActionListener(this);
		frmGprs.getContentPane().add(openPortBtn);
		sendMsgBtn = new Button("发送");
		sendMsgBtn.setBounds(539, 566, 87, 30);
		frmGprs.getContentPane().add(sendMsgBtn);
		sendMsgBtn.addActionListener(this);
		closePortBtn = new Button("关闭端口");
		closePortBtn.setBounds(636, 566, 87, 30);
		closePortBtn.addActionListener(this);
		frmGprs.getContentPane().add(closePortBtn);
		statusLb = new JLabel();
		statusLb.setBounds(0, 614, 742, 15);
		statusLb.setText(initStatus());
		statusLb.setOpaque(true);
		frmGprs.getContentPane().add(statusLb);

		cleanReadBtn = new Button("清空信息提示区");
		cleanReadBtn.setBounds(575, 41, 107, 30);
		cleanReadBtn.addActionListener(this);
		frmGprs.getContentPane().add(cleanReadBtn);

		cleanSendBtn = new Button("清空信息发送区");
		cleanSendBtn.setBounds(575, 354, 107, 30);
		cleanSendBtn.addActionListener(this);
		frmGprs.getContentPane().add(cleanSendBtn);
		
		
		frmGprs.setVisible(true);
	}

	/*********************************************扫描本机可用的端口************************************************************/
	public void scanPorts() {
		portList = new ArrayList<String>();
		Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier portId;
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				String name = portId.getName();
				if (!portList.contains(name)) {
					portList.add(name);
				}
			}
		}
		if (null == portList || portList.isEmpty()) {
			showErrMesgbox("未找到可用的串行端口号,程序无法启动!");
			System.exit(0);
		}
	}
	/*********************************************底部状态栏显示信息内容获取************************************************************/
	public String initStatus() {
		portname = portCombox.getSelectedItem().toString();
		rate = rateCombox.getSelectedItem().toString();
		data = dataCombox.getSelectedItem().toString();
		stop = stopCombox.getSelectedItem().toString();
		parity = parityCombox.getSelectedItem().toString();
		StringBuffer str = new StringBuffer("当前串口号:");
		str.append(portname).append(" 波特率:");
		str.append(rate).append(" 数据位:");
		str.append(data).append(" 停止位:");
		str.append(stop).append(" 校验位:");
		str.append(parity);
		String s = str.toString();
		return s;
	}
	/*********************************************打开端口************************************************************/
	public void openSerialPort() {
		// 获取要打开的端口
		try {
			portId = CommPortIdentifier.getPortIdentifier(portname);
		} catch (NoSuchPortException e) {
			showErrMesgbox("抱歉,没有找到" + portname + "串行端口号!");
			// setComponentsEnabled(true);
			return;
		}
		// 打开端口
		try {
			serialPort = (SerialPort) portId.open("JavaRs232", 2000);
			statusLb.setText(portname + "串口已经打开!");
		} catch (PortInUseException e) {
			showErrMesgbox(portname + "端口已被占用,请检查!");
			// setComponentsEnabled(true);
			return;
		}
		// 设置端口参数
		try {
			int rate = Integer.parseInt(this.rate);
			int data = Integer.parseInt(this.data);
			int stop = stopCombox.getSelectedIndex() + 1;
			int parity = parityCombox.getSelectedIndex();
			serialPort.setSerialPortParams(rate, data, stop, parity);
		} catch (UnsupportedCommOperationException e) {
			showErrMesgbox(e.getMessage());
		}
		// 打开端口的IO流管道
		try {
			outputStream = serialPort.getOutputStream();
			inputStream = serialPort.getInputStream();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		// 给端口添加监听器
		try {
			serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			showErrMesgbox(e.getMessage());
		}
		serialPort.notifyOnDataAvailable(true);
	}
	/****************************** 关闭串口并清除发送和信息提示区的内容************************************************************/
	public void closeSerialPort() {
		try {
			if (outputStream != null)
				outputStream.close();
			if (serialPort != null)
				serialPort.close();
			serialPort = null;
			statusLb.setText(portname + "串口已经关闭!");
			sendCount = 0;
			reciveCount = 0;
			sendTa.setText("");
			readTa.setText("");
		} catch (Exception e) {
			showErrMesgbox(e.getMessage());
		}
	}

	/****************************** 设置各组件的开关状态 （没用注销了）************************************************************/
	/**
	 * public void setComponentsEnabled(boolean enabled) {
	 * openPortBtn.setEnabled(enabled); portCombox.setEnabled(enabled);
	 * rateCombox.setEnabled(enabled); dataCombox.setEnabled(enabled);
	 * stopCombox.setEnabled(enabled); parityCombox.setEnabled(enabled);
	 * apncomBox.setEnabled(enabled); gongzuomoshiCombox.setEnabled(enabled); }
	 */
	/****************************** 设置各按钮的开关状态 ************************************************************/
	public void setBtnsEnabled(boolean enabled) {
		// restartBtn.setEnabled(enabled);
		backBtn.setEnabled(enabled);
		readallBtn.setEnabled(enabled);
		setallBtn.setEnabled(enabled);
		atentmBtn.setEnabled(enabled);
		plusBtn.setEnabled(enabled);
	}
	/********************************* **********APN帐号提示Textfield区域根据选择的ComboBox显示信息******************************************/
	public String getApn() {
		String valueANP = apncomBox.getSelectedItem().toString();
		return valueANP;
	}

	/********************************************************** 显示错误或警告信息******************************************************************/
	public void showErrMesgbox(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	/**************************************************************** 端口事件监听***************************************************************/
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
			showErrMesgbox("通讯终端！请检查连接！");
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			try {
				Thread.sleep(80);
				System.out.println("sleep 80");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] readBuffer = new byte[1024];
			try {
				while (inputStream.available() > 0) {
					inputStream.read(readBuffer);
				}
				String s = new String(readBuffer).trim();
				//String b2h =Convert2.bytes2HexString(s.getBytes());
				//String b2h = Convert.bytesToHexString(s.getBytes());
				//System.out.println(b2h);
				
				
				StringBuilder receivedMsg = new StringBuilder("[接收]：");
				receivedMsg.append(s).append("\n");

				readTa.append(receivedMsg.toString());
				reciveCount++;
				statusLb.setText("  发送: " + sendCount + "  接收: " + reciveCount);
			} catch (IOException e) {
				showErrMesgbox(e.getMessage());
			}
			//TODO
		}
	}

	/************************************************* 各组件行为事件监听 ******************************************************/
	
	public void actionPerformed(ActionEvent e) {
		/***********************************底部的信息提示标签显示的数据******************************************************/
		if (e.getSource() == portCombox || e.getSource() == rateCombox
				|| e.getSource() == dataCombox || e.getSource() == stopCombox
				|| e.getSource() == parityCombox || e.getSource() == apncomBox) {
			statusLb.setText(initStatus());
			txtCmnet.setText(getApn());
		}
		/***********************************清空提示区数据***********************************************************/
		if (e.getSource() == cleanReadBtn) {
			readTa.setText("");
		}
		/********************************清空发送区数据**********************************************************/
		if (e.getSource() == cleanSendBtn) {//
			sendTa.setText("");
		}
		/**************************************十六进制转换心跳包格式****************************************************/
		if (e.getSource() == hex1) {//
			if (hex1.isSelected()) {
				xintiaobaoTf.setText(string2HexString(xintiaobaoTf.getText())
						.toUpperCase());
			} else {
				xintiaobaoTf.setText(hexString2String(xintiaobaoTf.getText()));
			}
		}
		/***********************************十六进制转换注册报格式*******************************************************/
		if (e.getSource() == hex2) {//
			if (hex2.isSelected()) {
				zhucebaoTf.setText(string2HexString(zhucebaoTf.getText())
						.toUpperCase());
			} else {
				zhucebaoTf.setText(hexString2String(zhucebaoTf.getText()));
			}
		}
		/*********************************打开	关闭 串口按钮*******************************************************/
		if (e.getSource() == openPortBtn) {//
			// setComponentsEnabled(false);
			openSerialPort();
		}
		if (e.getSource() == closePortBtn) {//
			if (serialPort != null) {
				closeSerialPort();
			}
			// setComponentsEnabled(true);
		}
		/****************************如果点击清空计数器按钮*************************************************************/
		if (e.getSource() == cleanCountBtn) {
			if (serialPort == null) {
				showErrMesgbox("请先打开串行端口!");
				return;
			}
			sendcleanBtnSeriaPort();
		}
		/*********************************如果点击发送TextField按钮*******************************************************/
		if (e.getSource() == sendMsgBtn) {
			if (serialPort == null) {
				showErrMesgbox("请先打开串行端口!");
				return;
			}
			mesg = sendTa.getText();
			if (null == mesg || mesg.isEmpty()) {
				showErrMesgbox("请输入你要发送的内容!");
				return;
			}
			sendDataToSeriaPort();
		}
		/***********************************************发送+++ A ************************************************/
		if (e.getSource() == plusBtn) {
			if (serialPort == null) {
				showErrMesgbox("请先打开串行端口!");
				return;
			}
			sendplusBtnSeriaPort();
			setBtnsEnabled(false);
			try {
				Thread.sleep(3000);
				if (sendCount != reciveCount) {
					readTa.append(plusErr);
				} else if (sendCount == reciveCount) {
					sendABtnSeriaPort();
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			setBtnsEnabled(true);
		}
		/*********************************************发送AT+ENTM ********************************************************/
		if (e.getSource() == atentmBtn) {
			if (serialPort == null) {
				showErrMesgbox("请先打开串行端口!");
				return;
			}
			sendatentmBtnSeriaPort();
			setBtnsEnabled(false);
			try {
				Thread.sleep(3000);
				if (sendCount != reciveCount) {
					readTa.append(atErr);
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			setBtnsEnabled(true);
		}
		/**************************************读取全部参数*****************************************************************/
		// TODO
		if (e.getSource() == readallBtn) {
			if (serialPort == null) {
				showErrMesgbox("请先打开串行端口!");
				return;
			}
			sendreadallBtnSeriaPort();
//			setBtnsEnabled(false);
//			setBtnsEnabled(true);
		}
		/*********************************设置全部参数*************************************************************/
		if (e.getSource() == setallBtn) {
			// TODO
			if (serialPort == null) {
				showErrMesgbox("请先打开串行端口!");
				return;
			}
			sendsetallBtnSeriaPort();
//			setBtnsEnabled(false);
//			setBtnsEnabled(true);
		}
	}
	/*********************************** 发送按钮监听函数 **********************************************************************************/
	public void sendDataToSeriaPort() {
		try {
			sendCount++;
			outputStream.write(HexString2Bytes(mesg));
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + sendTa.getText() + "\n");
	}
	
	/*********************************** 配置全部参数发送按钮监听函数 **********************************************************************************/
	public String getMesg1() {
		StringBuffer str = new StringBuffer(getCrc());
		str.append(getcrcStr());
		return str.toString();
	}

	public void sendsetallBtnSeriaPort() {
		try {
			mesg = getMesg1().toUpperCase();
			sendCount++;
			//outputStream.write(mesg.getBytes());
			//outputStream.write(Convert2.hexString2Bytes(mesg));
			outputStream.write(HexString2Bytes(mesg));
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + mesg + "\n");
	}

	/*********************************** 读取配置参数按钮监听函数 **********************************************************************************/
	public String getMesg2() {
		StringBuffer str = new StringBuffer(getCrc2());
		str.append(getcrcStr2());
		return str.toString();
	}
	public void sendreadallBtnSeriaPort() {
		try {
			mesg=getMesg2().toUpperCase();
			sendCount++;
			//TODO
			//outputStream.write(Convert2.hexString2Bytes(mesg));
			outputStream.write(Convert.hexStringToByte(mesg));
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + mesg + "\n");
	}

	/*********************************** 恢复出厂配置（暂时没用） **********************************************************************************/
	public void sendbackBtnSeriaPort() {
		try {
			sendCount++;
			outputStream.write(mesg.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + mesg + "\n");
	}
	/*********************************** 清空计数器 **********************************************************************************/
	public void sendcleanBtnSeriaPort() {
		sendCount = 0;
		reciveCount = 0;
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
	}
	/*********************************** 发送+++ A **********************************************************************************/
	public void sendplusBtnSeriaPort() {
		try {
			mesg = "+++";
			sendCount++;
			outputStream.write(mesg.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + mesg + "\n");
	}

	public void sendABtnSeriaPort() {
		try {
			mesg = "A";
			sendCount++;
			outputStream.write(mesg.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + mesg + "\n");
	}

	/*********************************** 发送AT+ENTM **********************************************************************************/
	public void sendatentmBtnSeriaPort() {
		try {
			mesg = "AT+ENTM";
			sendCount++;
			outputStream.write(mesg.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			showErrMesgbox(e.getMessage());
		}
		statusLb.setText("  发送: " + sendCount + "接收: " + reciveCount);
		readTa.append("[发送]：" + mesg + "\n");
	}

	
	
	
	
	/*************************设置配置参数要获取的十六进制数据1 ********************************************************************************************************************************************/
	/**** 固定 + 长度+命令+APN账号 +设备波特率 +工作模式 +网络协议 +目标端口+自动重启时间+目标IP或域名+CRC校验 */
	// TODO
	public String getapnStr() {
		String s = "";
		int i = apncomBox.getSelectedIndex();
		if (i == 0)
			s = "00";
		else if (i == 1)
			s = "01";
		else if (i == 2)
			s = "02";
		return s;
	}

	public String getrateStr() {
		// TODO
		String s = null;
		int i = rateCombox.getSelectedIndex();
		// 00 2400；01 4800 ；02 9600 ；03 14400 ；04 19200 ；05 38400 ；06 57600 ；07
		// 1152500
		if (i == 0)
			s = "00";
		else if (i == 1)
			s = "01";
		else if (i == 2)
			s = "02";
		else if (i == 3)
			s = "03";
		else if (i == 4)
			s = "04";
		else if (i == 5)
			s = "05";
		else if (i == 6)
			s = "06";
		else if (i == 7)
			s = "07";
		return s;
	}

	public String getmodeStr() {
		// TODO
		String s = null;
		int i = gongzuomoshiCombox.getSelectedIndex();
		// 00 数据透传模式； 01 AT命令模式
		if (i == 0)
			s = "00";
		else if (i == 1)
			s = "01";
		return s;
	}

	public String getnetStr() {
		// TODO
		String s = null;
		int i = netprotocolCombo.getSelectedIndex();
		if (i == 0)
			s = "00";
		else if (i == 1)
			s = "01";
		return s;
	}

	/************************************************* 进制转换 ******************************************************/
	/**
	 *  从十六进制字符串到字节数组转换  
	 */
	private final static byte[] hex = "0123456789ABCDEF".getBytes();  
	private static int parse(char c) {  
	    if (c >= 'a')  
	        return (c - 'a' + 10) & 0x0f;  
	    if (c >= 'A')  
	        return (c - 'A' + 10) & 0x0f;  
	    return (c - '0') & 0x0f;  
	} 
	public static byte[] HexString2Bytes(String hexstr) {  
	    byte[] b = new byte[hexstr.length() / 2];  
	    int j = 0;  
	    for (int i = 0; i < b.length; i++) {  
	        char c0 = hexstr.charAt(j++);  
	        char c1 = hexstr.charAt(j++);  
	        b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
	    }  
	    return b;  
	}  

	/**
	 *  从字节数组到十六进制字符串转换  
	 * @param b
	 * @return
	 */
	public static String Bytes2HexString(byte[] b) {  
	    byte[] buff = new byte[2 * b.length];  
	    for (int i = 0; i < b.length; i++) {  
	        buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
	        buff[2 * i + 1] = hex[b[i] & 0x0f];  
	    }  
	    return new String(buff);  
	}
	/***********************************************************************************************************************/
	
	
	
	
	public String string2HexString(String strPart) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < strPart.length(); i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			hexString.append(strHex);
		}
		return hexString.toString();
	}

	public static String hexString2String(String src) {
		String temp = "";
		for (int i = 0; i < src.length() / 2; i++) {
			temp = temp
					+ (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
							16).byteValue();
		}
		return temp;
	}

	public String intToHexString(int a, int len) {
		len <<= 1;
		String hexString = Integer.toHexString(a);
		int b = len - hexString.length();
		if (b > 0) {
			for (int i = 0; i < b; i++) {
				hexString = "0" + hexString;
			}
		}
		return hexString;
	}

	public static byte[] hexString2Bytes1(String src) {
		// int l = src.length() / 2;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			ret[i] = (byte) Integer
					.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();

		}
		return ret;
	}

	public static String CRC_CCITT(int flag, byte[] bytes) {
		int crc = 0x00; // initial value
		int polynomial = 0x1021;
		switch (flag) {
		case 1:
			crc = 0x00;
			break;
		case 2:
			crc = 0xFFFF;
			break;
		case 3:
			crc = 0x1D0F;
			break;
		}
		for (int index = 0; index < bytes.length; index++) {
			byte b = bytes[index];
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}
		crc &= 0xffff;
		String data = Integer.toHexString(crc);
		return data;
	}

	/****************************************设置配置参数要获取的十六进制数据2*****************/
	public String getportStr() {
		// TODO
		int i = Integer.parseInt(mubiaoportTf.getText());
		String s = intToHexString(i, 2);
		return s;
	}

	public String getrestartTime() {
		// TODO
		int i = Integer.parseInt(xintiaotimeTf.getText());
		String s = intToHexString(i, 4);
		return s;
	}

	public String getipStr() {
		// TODO
		String s1 = string2HexString(ipTf.getText());
		StringBuffer sbf = new StringBuffer(s1);
		int l = 32 - s1.length() / 2;
		for (int i = 0; i < l; i++) {
			sbf.append("00");
		}
		return sbf.toString();
	}
	public String getCrc() {
		StringBuffer str = new StringBuffer(headStr);
		/**** 固定 + 长度+命令+APN账号 +设备波特率 +工作模式 +网络协议 +目标端口+自动重启时间+目标IP或域名+CRC校验 */
		str.append(cdStr);
		str.append(set_orderStr);
		str.append(getapnStr());
		str.append(getrateStr());
		str.append(getmodeStr());
		str.append(getnetStr());
		str.append(getportStr());
		str.append(getrestartTime());
		str.append(getipStr());
		return str.toString();
	}

	public String getcrcStr() {
		// TODO
		/**** 固定 + 长度+命令+APN账号 +设备波特率 +工作模式 +网络协议 +目标端口+自动重启时间+目标IP或域名+CRC校验 */
		StringBuffer str = new StringBuffer(headStr);
		str.append(cdStr);
		str.append(set_orderStr);
		str.append(getapnStr());
		str.append(getrateStr());
		str.append(getmodeStr());
		str.append(getnetStr());
		str.append(getportStr());
		str.append(getrestartTime());
		str.append(getipStr());
		String s = str.toString();
		byte[] b = hexString2Bytes1(s);
		String s2 = CRC_CCITT(1, b);
		return s2;
	}
	/***************************************读取全部配置参数要获取的十六进制数据****************************************************************************************************/
	public String getCrc2() {
		
		/*****固定	+	长度		+	命令		+	CRC */
		/*****固定	+	长度		+	命令	*/
		StringBuffer str = new StringBuffer(headStr);
		str.append(cdStr2);
		str.append(read_orderStr);
		return str.toString();
	}
	public String getcrcStr2() {
		// TODO
		/*****固定	+	长度		+	命令		+	CRC */
		/*****固定	+	长度		+	命令	*/
		StringBuffer str = new StringBuffer(headStr);
		str.append(cdStr2);
		str.append(read_orderStr);
		String s = str.toString();
		byte[] b = hexString2Bytes1(s);
		String s2 = CRC_CCITT(1, b);
		return s2;
	}
}
