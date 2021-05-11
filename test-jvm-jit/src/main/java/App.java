
/**
 * 执行以下命令，可以看到JVM进行编译的日志
 * java -XX:+PrintCompilation -cp .; App
 * */
public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello jit");
		int sum = 0;
		for (int i = 0; i < 200; i++) {
			sum += i;
		}
	}

}
