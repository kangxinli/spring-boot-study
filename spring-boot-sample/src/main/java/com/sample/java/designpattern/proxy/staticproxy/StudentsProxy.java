package com.sample.java.designpattern.proxy.staticproxy;

/**
 * 学生代理类，也实现了Person接口，保存一个学生实体，这样既可以代理学生产生行为
 *
 */
public class StudentsProxy implements Person {

	// 被代理的学生
	Student stu;
	
	public StudentsProxy(Person stu) {
        // 只代理学生对象
        if(stu.getClass() == Student.class) {
            this.stu = (Student)stu;
        }
    }
	
	@Override
	public void giveMoney() {
		stu.giveMoney();
	}

}
