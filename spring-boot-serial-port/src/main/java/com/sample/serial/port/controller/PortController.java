package com.sample.serial.port.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.serial.port.serial.DSerialPort;

@RestController
@RequestMapping("/port")
public class PortController {
	DSerialPort sp = new DSerialPort();

	String nowCom = "";

	@RequestMapping("/index")
	public String index() {
		return "/port";
	}

	@RequestMapping("/initCom")
	public String initCom(String comCode) {
		sp.listPort();
		sp.selectPort(comCode);
		sp.startRead(0);
		nowCom = comCode;
		DSerialPort.receiptDataString = "";
		DSerialPort.nowDataIndex = 0;
		DSerialPort.receiptDataList = new ArrayList<String>();
		return "success";
	}

	@RequestMapping("/writeCom")
	public String writeCom(String comData) {
		System.out.println("#comData->" + comData);
		sp.checkPort();
		// sp.selectPort(comCode);
		sp.write(comData);
		return "传输数据成功";
	}

	@RequestMapping("/getDataFromCom")
	public String getDataFromCom() {
		String dataStr = "";
		System.out.println(DSerialPort.receiptDataList.size());
		if (DSerialPort.receiptDataList.size() > DSerialPort.nowDataIndex) {
			dataStr = DSerialPort.receiptDataList.get(DSerialPort.nowDataIndex);
			DSerialPort.nowDataIndex++;
		}
		return dataStr;
	}

	public static int receiptNumber = 0;

	@RequestMapping("/closeCom")
	public String closeCom(String comCode, String comData) {
		System.out.println("#comCode->" + comCode + "#comData->" + comData);
		if (sp != null)
			sp.close();
		return "传输数据成功";
	}

	@RequestMapping("/startScan")
	public String startScan() {
		// 开始
		byte[] b = new byte[3];
		b[0] = (byte) 0x02;
		b[1] = (byte) 0x53;
		b[2] = (byte) 0x03;
		// sp.selectPort(nowCom);
		sp.write(b);
		return "启动扫描成功";
	}

	@RequestMapping("/stopScan")
	public String stopScan() {
		byte[] b2 = new byte[3];
		b2[0] = (byte) 0x02;
		b2[1] = (byte) 0x52;
		b2[2] = (byte) 0x03;
		// sp.selectPort(nowCom);
		sp.write(b2);
		return "停止扫描成功";
	}
}
