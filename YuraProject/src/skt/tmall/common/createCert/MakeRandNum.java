package skt.tmall.common.createCert;

public class MakeRandNum {
	private static final int CNT = 10000;
	//A, B, C 세개의 세트를 각각 생성한다.
	private static final String DB_TYPE_CD = "C";
	private boolean[] checkRandNum = null;
	private String[] randNumArr = null;

	public MakeRandNum() {
		checkRandNum = new boolean[CNT];
		randNumArr = new String[CNT];
		init();
	}

	private void init() {
		// 10미만의 숫자는 미리 설정하여 랜덤숫자에 포함되지 않도록 한다.
		for(int i=0; i<10; i++) {
			checkRandNum[i] = true;
			randNumArr[i] = "000"+i;
		}

		for(int i=10; i<randNumArr.length; i++) {
			int randNum = getRandNum();
			while(checkRandNum[randNum] ) {
				randNum = getRandNum();
			}
			checkRandNum[randNum] = true;
			randNumArr[i] = getZeroNumStr(randNum + 1);
		}
	}

	private String getZeroNumStr(int intNum) {
		String returnVal = intNum + "";

		if(intNum<10) {
			returnVal = "000" + intNum;
		} else if(intNum<100) {
			returnVal = "00" + intNum;
		} else if(intNum<1000) {
			returnVal = "0" + intNum;
		}

		return returnVal;
	}

	// 랜덤한 숫자를 생성한다.
	private int getRandNum() {
		int randNum = (int)(Math.random() * CNT);
		return randNum;
	}

	public String[] getRandNumArr() {
		return this.randNumArr;
	}

	public static void main(String[] args) {
		MakeRandNum makeRandNum = new MakeRandNum();
		String[] randNumArr = makeRandNum.getRandNumArr();
		StringBuilder sb = new StringBuilder();
		for(int i=10; i<randNumArr.length; i++) {
			sb.append(DB_TYPE_CD+","+randNumArr[i]+","+(i-9)+"\n");
		}

		String fileName = "e:\\project\\temp\\cert_pool\\randNumC.txt";

		MyFileWriter fileWriter = new MyFileWriter();
		fileWriter.write(sb.toString(), fileName);

		MyFileReader fileReader = new MyFileReader();
		String readContent = fileReader.getFileContent(fileName);

		String[] test = readContent.split("\n");
		System.out.println(test.length);

		System.out.println("success");
	}
}
