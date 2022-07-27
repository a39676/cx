package demo.tool.bbtOrder.hsbc.pojo.vo;

public class RandomIdDataVO {

	private String randomIdNumber;
	private String randomLastname;
	private String randomFirstname;
	private String randomPhoneNumber;

	public String getRandomIdNumber() {
		return randomIdNumber;
	}

	public void setRandomIdNumber(String randomIdNumber) {
		this.randomIdNumber = randomIdNumber;
	}

	public String getRandomLastname() {
		return randomLastname;
	}

	public void setRandomLastname(String randomLastname) {
		this.randomLastname = randomLastname;
	}

	public String getRandomFirstname() {
		return randomFirstname;
	}

	public void setRandomFirstname(String randomFirstname) {
		this.randomFirstname = randomFirstname;
	}

	public String getRandomPhoneNumber() {
		return randomPhoneNumber;
	}

	public void setRandomPhoneNumber(String randomPhoneNumber) {
		this.randomPhoneNumber = randomPhoneNumber;
	}

	@Override
	public String toString() {
		return "RandomIdDataVO [randomIdNumber=" + randomIdNumber + ", randomLastname=" + randomLastname
				+ ", randomFirstname=" + randomFirstname + ", randomPhoneNumber=" + randomPhoneNumber + "]";
	}

}
