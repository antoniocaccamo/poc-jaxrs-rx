package me.antoniocaccamo.jaxrs.jaxrsrx;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;
import me.antoniocaccamo.jaxrs.rx.JaxrsRxApplication;
import me.antoniocaccamo.jaxrs.rx.adapter.RateServiceAdapter;
import me.antoniocaccamo.jaxrs.rx.endpoint.exception.InternalErrorException;
import me.antoniocaccamo.jaxrs.rx.model.ExchangeRatesResponse;
import me.antoniocaccamo.jaxrs.rx.service.rate.RateServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = JaxrsRxApplication.class)
class JaxrsRxApplicationTests {

	@Mock
	RateServiceAdapter mockAdapter;

	private RateServiceImpl sut;
	private String baseCurrency = "EUR";
	private String counterCurrency = "USD";

	@BeforeEach
	public void setup() {

		MockitoAnnotations.initMocks(this);

		sut = new RateServiceImpl(mockAdapter);
	}

	@AfterEach
	public void tearDown() {

		sut = null;
	}

	@Test
	public void testHappyPath() {

		setup();
		ExchangeRatesResponse expectedResponse = createResponse();


		doReturn(Single.just(expectedResponse)).when(mockAdapter).getExchangeRates(baseCurrency);

		TestSubscriber<ExchangeRatesResponse> testSubscriber = new TestSubscriber<ExchangeRatesResponse>();

		sut.getExchangeRates(baseCurrency).toFlowable().subscribe(testSubscriber);

		testSubscriber.assertValue(expectedResponse);
	}

	@Test
	public void testSadPath() {

		doReturn(Single.error(new InternalErrorException())).when(mockAdapter).getExchangeRates(baseCurrency);

		TestSubscriber<ExchangeRatesResponse> testSubscriber = new TestSubscriber<ExchangeRatesResponse>();

		sut.getExchangeRates(baseCurrency).toFlowable().subscribe(testSubscriber);

		testSubscriber.assertError(InternalErrorException.class);
	}

	@Test
	public void testSubscription() {

		doReturn(Single.never()).when(mockAdapter).getExchangeRates(baseCurrency);

		TestSubscriber<ExchangeRatesResponse> testSubscriber = new TestSubscriber<ExchangeRatesResponse>();

		sut.getExchangeRates(baseCurrency).toFlowable().subscribe(testSubscriber);

		testSubscriber.assertSubscribed();
	}

	@Test
	public void testComplete() {

		ExchangeRatesResponse expectedResponse = createResponse();

		doReturn(Single.just(expectedResponse)).when(mockAdapter).getExchangeRates(baseCurrency);

		TestSubscriber<ExchangeRatesResponse> testSubscriber = new TestSubscriber<ExchangeRatesResponse>();

		sut.getExchangeRates(baseCurrency).toFlowable().subscribe(testSubscriber);

		testSubscriber.assertComplete();
	}

	private ExchangeRatesResponse createResponse() {

		ExchangeRatesResponse response = new ExchangeRatesResponse();
		response.setBase(baseCurrency);
		response.setDate("date");

		HashMap<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
		rates.put(counterCurrency, new BigDecimal("1.0661"));

		return response;
	}

}
