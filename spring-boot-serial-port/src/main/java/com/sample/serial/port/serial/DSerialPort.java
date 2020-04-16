package com.sample.serial.port.serial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.*;

public class DSerialPort implements Runnable, SerialPortEventListener {

    private String appName = "串口通讯测试";
    private int timeout = 2000;// open 端口时的等待时间，延迟时间(毫秒数)
    private int threadTime = 0;

    private CommPortIdentifier commPort;
    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    //当前接收COM口的数据
    public static String receiptDataString="";
    //当前已取的数组下标
    public static int nowDataIndex =0;
    //当前接收COM口的数据自动切割成StringList
    public static ArrayList<String> receiptDataList=new ArrayList<String>();

    /**
     * @方法名称 :listPort
     * @功能描述 :列出所有可用的串口
     * @返回值类型 :void
     */
    @SuppressWarnings("rawtypes")
    public void listPort() {
        CommPortIdentifier cpid;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();//获得端口列表

        System.out.println("now to list all Port of this PC：" + en);

        while (en.hasMoreElements()) {
            cpid = (CommPortIdentifier) en.nextElement();
            if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL) {//判断是否串口
                System.out.println(cpid.getName() + ", " + cpid.getCurrentOwner());
            }
//          if (cpid.getPortType() == CommPortIdentifier.PORT_PARALLEL) {//判断是否并行口
//              System.out.println(cpid.getName() + ", " + cpid.getCurrentOwner());
//          }
        }
    }

    /**
     * @方法名称 :selectPort
     * @功能描述 :选择一个端口，比如：COM1
     * @返回值类型 :void
     * @param portName
     */
    @SuppressWarnings("rawtypes")
    public void selectPort(String portName) {

        this.commPort = null;
        CommPortIdentifier cpid;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();

        while (en.hasMoreElements()) {
            cpid = (CommPortIdentifier) en.nextElement();
            if (cpid.getPortType() == CommPortIdentifier.PORT_SERIAL && cpid.getName().equals(portName)) {
                this.commPort = cpid;
                break;
            }
        }

        openPort();
    }

    /**
     * @方法名称 :openPort
     * @功能描述 :打开SerialPort
     * @返回值类型 :void
     */
    private void openPort() {
        if (commPort == null)
            log(String.format("无法找到串口！"));
        else {
            log("端口选择成功，当前端口：" + commPort.getName() + ",现在实例化 SerialPort:");

            try {
                serialPort = (SerialPort) commPort.open(appName, timeout);//打开端口
                serialPort.setSerialPortParams(2400, SerialPort.DATABITS_7, SerialPort.STOPBITS_1,SerialPort.PARITY_EVEN);
                //设置参数
                log("实例 SerialPort 成功！");
            } catch (PortInUseException e) {
                throw new RuntimeException(String.format("端口'%1$s'正在使用中！", commPort.getName()));
            }catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @方法名称 :checkPort
     * @功能描述 :检查端口是否正确连接
     * @返回值类型 :void
     */
    public void checkPort() {
        if (commPort == null)
            throw new RuntimeException("没有选择端口，请使用 " + "selectPort(String portName) 方法选择端口");

        if (serialPort == null) {
            throw new RuntimeException("SerialPort 对象无效！");
        }
    }

    /**
     * @方法名称 :write
     * @功能描述 :向端口发送数据，请在调用此方法前 先选择端口，并确定SerialPort正常打开！
     * @返回值类型 :void
     * @param message
     */
    public void write(String message) {
        checkPort();

        try {
            outputStream = new BufferedOutputStream(serialPort.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取端口的OutputStream出错：" + e.getMessage());
        }

        try {
            outputStream.write(message.getBytes());
            log("信息发送成功！");
        } catch (IOException e) {
            throw new RuntimeException("向端口发送信息时出错：" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }
    public void write(byte[]  message) {
        checkPort();

        try {
            outputStream = new BufferedOutputStream(serialPort.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取端口的OutputStream出错：" + e.getMessage());
        }

        try {
            outputStream.write(message);
            log("信息发送成功！");
        } catch (IOException e) {
            throw new RuntimeException("向端口发送信息时出错：" + e.getMessage());
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }
    /**
     * @方法名称 :startRead
     * @功能描述 :开始监听从端口中接收的数据
     * @返回值类型 :void
     * @param time
     *            监听程序的存活时间，单位为秒，0 则是一直监听
     */
    public void startRead(int time) {
        checkPort();

        try {
            inputStream = new BufferedInputStream(serialPort.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取端口的InputStream出错：" + e.getMessage());
        }

        try {
            serialPort.addEventListener(this);//向SerialPort对象中添加串口事件监听器
        } catch (TooManyListenersException e) {
            throw new RuntimeException(e.getMessage());
        }

        serialPort.notifyOnDataAvailable(true);//设置串口有数据的事件true有效,false无效

        log(String.format("开始监听来自'%1$s'的数据--------------", commPort.getName()));
        if (time > 0) {
            this.threadTime = time * 1000;
//          this.threadTime = time* 10;
            Thread t = new Thread(this);
            t.start();
            log(String.format("监听程序将在%1$d秒后关闭。。。。", threadTime));
        }
    }
    /**
     * @方法名称 :startRead
     * @功能描述 :开始监听从端口中接收的数据
     * @返回值类型 :void
     * @param time
     *            监听程序的存活时间，单位为秒，0 则是一直监听
     */
    public void read(int time) {
        checkPort();

        try {
            inputStream = new BufferedInputStream(serialPort.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取端口的InputStream出错：" + e.getMessage());
        }

        try {
            serialPort.addEventListener(this);//向SerialPort对象中添加串口事件监听器
        } catch (TooManyListenersException e) {
            throw new RuntimeException(e.getMessage());
        }

        serialPort.notifyOnDataAvailable(true);//设置串口有数据的事件true有效,false无效

        log(String.format("开始监听来自'%1$s'的数据--------------", commPort.getName()));
        if (time > 0) {
            this.threadTime = time * 1000;
//          this.threadTime = time* 10;
            Thread t = new Thread(this);
            t.start();
            log(String.format("监听程序将在%1$d秒后关闭。。。。", threadTime));
        }
    }
    /**
     * @方法名称 :close
     * @功能描述 :关闭 SerialPort
     * @返回值类型 :void
     */
    public void close() {
        serialPort.close();
        serialPort = null;
        commPort = null;
    }

    public void log(String msg) {
        System.out.println(appName + " --> " + msg);
    }

    /**
     * 数据接收的监听处理函数
     */
    @Override
    public void serialEvent(SerialPortEvent arg0) {
        switch (arg0.getEventType()) {
        case SerialPortEvent.BI:/* Break interrupt,通讯中断 */
        case SerialPortEvent.OE:/* Overrun error，溢位错误 */
        case SerialPortEvent.FE:/* Framing error，传帧错误 */
        case SerialPortEvent.PE:/* Parity error，校验错误 */
        case SerialPortEvent.CD:/* Carrier detect，载波检测 */
        case SerialPortEvent.CTS:/* Clear to send，清除发送 */
        case SerialPortEvent.DSR:/* Data set ready，数据设备就绪 */
        case SerialPortEvent.RI:/* Ring indicator，响铃指示 */
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*
                                                     * Output buffer is
                                                     * empty，输出缓冲区清空
                                                     */
            break;
        case SerialPortEvent.DATA_AVAILABLE:/*
                                             * Data available at the serial
                                             * port，端口有可用数据。读到缓冲数组，输出到终端
                                             */
            byte[] readBuffer = new byte[1024];
            String readStr = "";
            String s2 = "";

            try {

                while (inputStream.available() > 0) {
                    inputStream.read(readBuffer);
                    readStr += new String(readBuffer).trim();
                }

                s2 = new String(readBuffer).trim();
                //接收的精华再这里
                //1。readStr为当次读入的，一般设备是1位1位读，模拟的时候就很多位，但是不重要
                //2。receiptDataString是用来缓存输入字符串的
                //3。receiptDataString.length()==XX这里可以设定你要接受的长度，然后接收指定数据
                //4。超长或者不符合长度，你可以看情况抛弃数据或者清空，或者累加
                //5。接受成功的数据，放入receiptDataList供获取调用
                //6。nowDataIndex是当前数组的下标，可以参考PortController中对数据获取的方法
                log("接收端口COM->返回数据(长度为" + readStr.length() + ")：数据" + s2);
                receiptDataString+=readStr;
                log("receiptDataString->长度" + receiptDataString.length() + ")，数据" + receiptDataString);
                if(receiptDataString.length()==58){
                    receiptDataList.add(receiptDataString);
                    receiptDataString="";
                    log("校验通过,数据接收成功");
                }else if(receiptDataString.length()>100){
                    receiptDataString="";
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(threadTime);
            serialPort.close();
            log(String.format("端口'%1$s'监听关闭了！", commPort.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        DSerialPort sp = new DSerialPort();
        //sp.listPort();
        sp.selectPort("COM1");
        sp.write("210.36.16.166");
        //sp.write("2");
        //sp.startRead(120);
    }
}
