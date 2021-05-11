package com.youming.demoProtocolBuffer;

import com.youming.demoProtocolBuffer.pb.StudentPB.Student;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello Google Protocol Buffer");
		Student.Builder buidler = Student.newBuilder();
        buidler.setName("Frank");
        buidler.setNumber(123456);
        buidler.setHobby("music");
        Student student = buidler.build();
        System.out.println(student.toString());
        //反序列化
        byte[] buf = student.toByteArray();
        try {
            Student student1 = Student.parseFrom(buf);
            System.out.println(student1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }   
	}

}
