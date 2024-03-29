package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramMaskPositionDTO {

	private String point;
	private Float x_shift;
	private Float y_shift;
	private Float scale;

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public Float getX_shift() {
		return x_shift;
	}

	public void setX_shift(Float x_shift) {
		this.x_shift = x_shift;
	}

	public Float getY_shift() {
		return y_shift;
	}

	public void setY_shift(Float y_shift) {
		this.y_shift = y_shift;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "TelegramMaskPositionDTO [point=" + point + ", x_shift=" + x_shift + ", y_shift=" + y_shift + ", scale="
				+ scale + "]";
	}

}
