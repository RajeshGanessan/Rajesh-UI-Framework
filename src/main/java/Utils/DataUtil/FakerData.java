package Utils.DataUtil;

import java.util.Locale;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;
import com.github.javafaker.PhoneNumber;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

public final class FakerData {

	private  Faker faker;
	private  StringJoiner joiner;
	private  ThreadLocalRandom current;

	public String getEmpName() {

		faker = new Faker();
		joiner = new StringJoiner(" ");
		joiner.add(faker.name().firstName()).add(faker.name().lastName());
		return joiner.toString();
	}

	// to Generate Random phoneNumber
	public String getPhoneNumber() {
		faker = new Faker(new Locale("en-IND"));
		PhoneNumber number = faker.phoneNumber();
		String phoneNumber = number.phoneNumber().replace("\\+91", "").replace("-","");
		if(phoneNumber.startsWith("00"))  getPhoneNumber();
		return phoneNumber;
	}

	// to generate random ID
	public String generateId() {

		 Random rand = new Random();
		int randomDigit = rand.nextInt(500);
		return "MSE" + "-" + randomDigit;
	}

	// getting fundingRoundName
	public String getFundingRoundName() {
		FakeValuesService fakeValueService = new FakeValuesService(new Locale("en-GB"), new RandomService());
		return fakeValueService.numerify("Seed-round###");
	}

	// generating Postman
	public  String getPostMoney() {
		current =  ThreadLocalRandom.current();
		double random = current.nextDouble(30.5, 40.5);
		double roundedRandom = Math.round(random * 100D) / 100D;
		return String.valueOf(roundedRandom);
	}

	// to Generate shareHolder
	public  String getFundingShares() {
			current = ThreadLocalRandom.current();
			int fundingShares = current.nextInt(100000);
			return String.valueOf(fundingShares);
	}

	// to generate Adress
	public String getAddress() {
		faker = new Faker(new Locale("en-IND"));
		joiner = new StringJoiner(",");
		joiner.add(faker.address().buildingNumber()).add(faker.address().streetAddress())
				.add(faker.address().cityName()).add("India").add(faker.address().zipCode());
		return joiner.toString();
	}

	// to generate EmailAddress
	public String getEmailAddress() {
		StringBuilder strbuilder = new StringBuilder();
		strbuilder.append("success+").append(generateId()).append("@simulator.amazonses.com");
		return strbuilder.toString();
	}
}
