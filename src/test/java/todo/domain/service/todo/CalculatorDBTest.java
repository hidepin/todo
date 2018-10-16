package todo.domain.service.todo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorDBTest {

	@Test
	public void multiplyで2と5の乗算結果が取得できる() {
		Calculator calc = new Calculator();
		int expected = 10;
		int actual = calc.multiply(2, 5);
		assertThat(actual, is(expected));
	}

	@Test
	public void multiplyで2と2の乗算結果が取得できる() {
		Calculator calc = new Calculator();
		int expected = 4;
		int actual = calc.multiply(2, 2);
		assertThat(actual, is(expected));
	}

}
