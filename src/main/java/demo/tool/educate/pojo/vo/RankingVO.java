package demo.tool.educate.pojo.vo;

public class RankingVO implements Comparable<RankingVO> {

	private String nickname;

	private Double number;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "RankingVO [nickname=" + nickname + ", number=" + number + "]";
	}

	@Override
	public int compareTo(RankingVO o) {
		return compareByNumber(o, this);
	}

	private int compareByNumber(RankingVO o, RankingVO t) {
		if (o.getNumber() == null || t.getNumber() == null) {
			if (o.getNumber() == null && t.getNumber() == null) {
				return 0;
			} else if (o.getNumber() == null) {
				return -1;
			} else if (t.getNumber() == null) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (t.getNumber() > (o.getNumber())) {
				return -1;
			} else if (t.getNumber() < (o.getNumber())) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
