
1、需要下载的包mfz-rxtx-2.2-20081207-win-x64.zip，网盘下载地址：https://pan.baidu.com/s/1o6zLmTc
2、串口模拟工具下载地址：链接: https://pan.baidu.com/s/1uii3uoWwHp71MlkxjKjZag 密码: 957h
       先安装vspd.exe, 在运行vspdconfig.exe, 创建模拟串口


开发配置如下：  %java_home%是jdk的路径

    将rxtxSerial.dll复制到%java_home%/jre/bin

    将RXTXcomm.jar复制到%java_home%/jre/lib/ext

注意：不管是用第一种还是第二种方法，都要把对应的.jar文件复制到项目中的lib下


打包运行配置：
Java桌面程序打包成exe可执行文件, 参考https://www.cnblogs.com/timao/p/10868524.html
打包完成后，需要把jre目录拷贝到exe同一目录下
打包后启动需要把rxtxParallel.dll、rxtxSerial.dll文件拷贝到C:\Windows\System32下

   
